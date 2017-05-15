package com.yonyou.dcs.gacfca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.po.TiUsedCarReplacementRatePO;
import com.infoeai.eai.po.TtUsedCarReplacementRatePO;
import com.yonyou.dcs.dao.DMSTODCS050Dao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiUsedCarReplacementRateDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.DTO.basedata.SADCS050Dto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class DMSTODCS050CloudImpl extends BaseCloudImpl implements DMSTODCS050Cloud {
	private static final Logger logger = LoggerFactory.getLogger(DMSTODCS050CloudImpl.class);
	private String reportType = "";	// 上报类型：1:月报，2：周报
	private String reportDate = "";	// 上报日期
	private String entityCode= "";//下端经销商编号
	@Autowired
	DMSTODCS050Dao dao ;

	@Override
	public String receiveData(List<SADCS050Dto> dtos) throws Exception {
		String msg = "1";
		logger.info("*************************** 开始获取上报的二手车置换率月报（周报）数据 ******************************");
		// 原始数据写入接口表
		saveTiTable(dtos);
		logger.info("##################### 成功接收上报的二手车置换率月报（周报）数据 ############################");
		
		try {
			//从接口表抽取本次接口数据集
			List<TiUsedCarReplacementRateDTO> datalist = dao.queryTiUsedData(entityCode, reportType, reportDate);;
			logger.info("##################### 二手车置换率月报（周报）数据size####"+datalist.size()+"##########################");
			//循环遍历数据集进行业务处理
			for (int i = 0; i < datalist.size(); i++) {
				TiUsedCarReplacementRateDTO dto = datalist.get(i);
				logger.info("##################### 开始校验上报的二手车置换率月报（周报）数据 ############################");
				//校验数据
				String message = dataCheck(dto);
				logger.info("##################### 校验上报的二手车置换意数据接收 ############################");
				//校验是否有错误消息
				if(Utility.testIsNotNull(message)){
					//校验失败，更新接口表
					updateTiTable(dto,message);
				}else{
					//校验通过，写入业务表
					insertTtTable(dto);
				}
			}
		} catch (Exception e) {
			logger.error("开始获取二手车置换意向明细接收失败", e);
			msg = "0";
			throw new ServiceBizException(e);
		} 
		logger.info("*************************** 成功获取上报的二手车置换意向明细数据******************************");
		return msg;
	}

	// 处理上报数据
	private void saveTiTable(List<SADCS050Dto> dtos) throws Exception {
		logger.info("##################### 开始接收上报的二手车置换率月报（周报）数据 ############################");
		TiUsedCarReplacementRatePO usedPO = null;
		for (SADCS050Dto dto : dtos) {
			usedPO = new TiUsedCarReplacementRatePO();
			entityCode = dto.getDealerCode();// 经销商代码
			usedPO.setString("DEALER_CODE",entityCode);
			usedPO.setString("SERIES_CODE",dto.getSeriesCode());// 车系代码
			usedPO.setString("HSL_HABOD",dto.getHslHabod() + "");// HSL_HABOD
			usedPO.setString("POTENTIAL_CUSTOMER_NUM",dto.getPotentialCustomersNum() + "");// 置换潜客数
			usedPO.setString("INTENTION_NUM",dto.getIntentionNum() + "");	// 置换意向数
			usedPO.setString("SALE_NUM",dto.getSaleNum() + "");	// 零售实销数
			usedPO.setString("DEAL_NUM",dto.getDealNum() + "");	// 置换成交数
			usedPO.setString("INTENTION_RATIO",dto.getIntentionRatio() + "");	// 置换意向占比
			usedPO.setString("DEAL_RATIO",dto.getDealRatio() + "");	// 置换成交率
			usedPO.setString("CONVERSION_RATIO",dto.getConversionRatio() + "");	// 置换转化率
			reportType = dto.getReportType() + "";
			usedPO.setString("REPORT_TYPE",reportType); // 上报类型
			reportDate = Utility.handleDate(dto.getReportDate());// 上报日期
			usedPO.setString("REPORT_DATE",reportDate);
			usedPO.setInteger("RESULT",OemDictCodeConstants.IF_TYPE_YES);//校验结果:成功：10041001;失败：10041002
			usedPO.setString("MESSAGE","校验成功");//校验结果描述
			usedPO.setInteger("IS_DEL",0);
			usedPO.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
			usedPO.setDate("CREATE_DATE",new Date(System.currentTimeMillis()));
			usedPO.insert();
		}
	}
	/**
	 * 更新接口表数据，将错误信息更新到接口表
	 * @param dto
	 * @param message
	 * @throws Exception
	 */
	public void updateTiTable(TiUsedCarReplacementRateDTO dto,String message) throws Exception{
		
		List<Object> params = new ArrayList<Object>();
		params.add(OemDictCodeConstants.IF_TYPE_NO);//校验结果:成功：10041001;失败：10041002
		params.add(message);
		params.add(DEConstant.DE_UPDATE_BY);
		params.add(dto.getId());
		TiUsedCarReplacementRatePO.update("RESULT = ? AND MESSAGE = ? AND UPDATE_BY = ? AND UPDATE_DATE = ?","ID = ?",params.toArray());
	
	}
	
	/**
	 * 将校验成功的数据插入到业务表
	 * 
	 *	校验成功后，判断之前是否存在该客户信息
	 *	a.存在：首先把之前的客户信息更新为最新上报的客户信息
	 *		      再根据reportType为1：新增二手车信息
	 *		  	  reportType为2:根据customerNo和itemId来更新二手车信息
	 *		  	  reportType为3:根据customerNo和itemId来把该客户信息作废
	 *  b.不存在：将上报上来的客户信息和二手车信息插入到业务表
	 * @param dto
	 * @throws Exception
	 */
	public void insertTtTable(TiUsedCarReplacementRateDTO dto) throws Exception{
		
		TtUsedCarReplacementRatePO po = new TtUsedCarReplacementRatePO();
		String reportDate = dto.getReportDate();
		Integer reportYear = 0000;
		Integer reportMonth = 00;
		Integer reportWeek = 00;
		List<Map<String,Object>> reportList = dao.getReportList(reportDate);
		if(reportList != null && reportList.size() > 0) {
			reportYear = Integer.parseInt(CommonUtils.checkNull(reportList.get(0).get("YEAR_CODE"), "0000"));
			reportMonth = Integer.parseInt(CommonUtils.checkNull(reportList.get(0).get("MONTH_CODE"), "00"));
			reportWeek = Integer.parseInt(CommonUtils.checkNull(reportList.get(0).get("WEEK_CODE"), "00"));
		}
		po.setInteger("YEAR",reportYear);
		if(dto.getReportType().equals("1")){//月报
			po.setInteger("MONTH",reportMonth);
		}else if(dto.getReportType().equals("2")){//周报
			po.setInteger("WEEK",reportWeek);
		}
		String dealerCode = dao.getSaDcsDealerCode1(dto.getDealerCode());
		po.setString("DEALERT_CODE",dealerCode);
		po.setString("SERIES_CODE",dto.getSeriesCode());// 车系代码
		po.setLong("HSL_HABOD",Long.parseLong(CommonUtils.checkNull(dto.getHslHabod(), "0L")));// HSL_HABOD
		po.setLong("POTENTIAL_CUSTOMER_NUM",Long.parseLong(CommonUtils.checkNull(dto.getPotentialCustomersNum(), "0L")));// 置换潜客数
		po.setLong("INTENTION_NUM",Long.parseLong(CommonUtils.checkNull(dto.getIntentionNum(), "0L")));	// 置换意向数
		po.setLong("SALE_NUM",Long.parseLong(CommonUtils.checkNull(dto.getSaleNum(), "0L")));	// 零售实销数
		po.setLong("DEAL_NUM",Long.parseLong(CommonUtils.checkNull(dto.getDealNum(), "0L")));	// 置换成交数
		po.setDouble("INTENTION_RATIO",Double.parseDouble(CommonUtils.checkNull(dto.getIntentionRatio(), "0d")));	// 置换意向占比
		po.setDouble("DEAL_RATIO",Double.parseDouble(CommonUtils.checkNull(dto.getDealRatio(), "0d")));	// 置换成交率
		po.setDouble("CONVERSION_RATIO",Double.parseDouble(CommonUtils.checkNull(dto.getConversionRatio(), "0d")));	// 置换转化率
		po.setInteger("REPORT_TYPE",Integer.parseInt(CommonUtils.checkNull(dto.getReportType(), "0")));;//报表类型（月报：1，周报：2）
		po.setDate("REPORT_DATE",Utility.getDate(dto.getReportDate(),1));// 上报日期
		po.setBigDecimal("CREATE_BY", DEConstant.DE_UPDATE_BY);
		po.setDate("CREATE_DATE", new Date(System.currentTimeMillis()));
		po.insert();
		
	}
	
	/**
	 * 校验上报的数据
	 * @param checkDto
	 * @return
	 * @throws Exception
	 */
	public String dataCheck(TiUsedCarReplacementRateDTO checkDto) throws Exception{
		String message = "";
		String reportDate = checkDto.getReportDate();
		String reportType = checkDto.getReportType();
		if (Utility.testIsNotNull(reportDate)){
			if(reportDate.length()!=10){
				message = "上报日期无效(yyyy-MM-dd):"+reportDate;
				return message;
			}else{
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					sdf.parse(reportDate);
				} catch (Exception e) {
					message = "上报日期无效(yyyy-MM-dd):"+reportDate;
					return message;
				}
			}
		}else{
			message = "上报日期为空";
			return message;
		}
		if (Utility.testIsNotNull(reportType)){
			if(reportType.length()!=1){
				message = "报表类型无效:"+reportType;
				return message;
			}
		}else{
			message = "报表类型为空";
			return message;
		}
		String dealerCode = dao.getSaDcsDealerCode1(checkDto.getDealerCode());
		if (Utility.testIsNull(dealerCode)){
			message = "DCS没有【" + checkDto.getDealerCode() + "】经销商信息！";
			return message;
		}
		String seriesCode = dao.querySeriesCode(checkDto.getSeriesCode());
		if (Utility.testIsNull(seriesCode)){
			message = "DCS没有【" + checkDto.getSeriesCode() + "】车系信息！";
			return message;
		}
		return message;
	}

	/**
	 * 测试接入口
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DMSTODCS050CloudImpl sadcs050 = new DMSTODCS050CloudImpl();
			List<SADCS050Dto> voList = new ArrayList<SADCS050Dto>();
			for (int i = 0; i <= 10; i++) {
				SADCS050Dto vo = new SADCS050Dto();
				vo.setDealerCode("CJ770700");
				vo.setSeriesCode("300C");
				vo.setHslHabod(123+i);// HSL_HABOD
				vo.setPotentialCustomersNum(234+i);// 置换潜客数
				vo.setIntentionNum(345+i);// 置换意向数
				vo.setSaleNum(456+i);// 零售实销数
				vo.setDealNum(567+i);// 置换成交数
				vo.setIntentionRatio(1.1);// 置换意向占比
				vo.setDealRatio(1.2);// 置换成交率
				vo.setConversionRatio(1.3);// 置换转化率
				vo.setReportType(1);// 报表类型
				vo.setReportDate(Utility.getCurrentDateTime());
				voList.add(vo);
			}
			sadcs050.execute(voList);
			System.out.println("End..");
		} catch (Exception e) {
			throw new ServiceBizException(e);
		} 
	}
	private void execute(List<SADCS050Dto> voList) throws Exception{
		try {
			TiUsedCarReplacementRatePO usedPO = null;
			logger.info("##################### 开始接收上报的二手车置换率月报（周报）数据 ############################");
			for (SADCS050Dto dto : voList) {
				usedPO = new TiUsedCarReplacementRatePO();
				entityCode = dto.getDealerCode();// 经销商代码
				usedPO.setString("DEALER_CODE",entityCode);
				usedPO.setString("SERIES_CODE",dto.getSeriesCode());// 车系代码
				usedPO.setString("HSL_HABOD",dto.getHslHabod() + "");// HSL_HABOD
				usedPO.setString("POTENTIAL_CUSTOMER_NUM",dto.getPotentialCustomersNum() + "");// 置换潜客数
				usedPO.setString("INTENTION_NUM",dto.getIntentionNum() + "");	// 置换意向数
				usedPO.setString("SALE_NUM",dto.getSaleNum() + "");	// 零售实销数
				usedPO.setString("DEAL_NUM",dto.getDealNum() + "");	// 置换成交数
				usedPO.setString("INTENTION_RATIO",dto.getIntentionRatio() + "");	// 置换意向占比
				usedPO.setString("DEAL_RATIO",dto.getDealRatio() + "");	// 置换成交率
				usedPO.setString("CONVERSION_RATIO",dto.getConversionRatio() + "");	// 置换转化率
				reportType = dto.getReportType() + "";
				usedPO.setString("REPORT_TYPE",reportType); // 上报类型
				reportDate = Utility.handleDate(dto.getReportDate());// 上报日期
				usedPO.setString("REPORT_DATE",reportDate);
				usedPO.setInteger("RESULT",OemDictCodeConstants.IF_TYPE_YES);//校验结果:成功：10041001;失败：10041002
				usedPO.setString("MESSAGE","校验成功");//校验结果描述
				usedPO.setInteger("IS_DEL",0);
				usedPO.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
				usedPO.setDate("CREATE_DATE",new Date(System.currentTimeMillis()));
				usedPO.insert();
			}
			
			logger.info("##################### 成功接收上报的二手车置换率月报（周报）数据 ############################");
			List<TiUsedCarReplacementRateDTO> datalist = dao.queryTiUsedData(entityCode,reportType,reportDate);
			for (int i = 0; i < datalist.size(); i++) {
				TiUsedCarReplacementRateDTO checkPo = datalist.get(i);
				logger.info("##################### 开始校验上报的二手车置换率月报（周报）数据 ############################");
				String message = dataCheck(checkPo);
				logger.info("##################### 校验上报的二手车置换率月报（周报）数据接收 ############################");
				if(Utility.testIsNotNull(message)){
					updateTiTable(checkPo,message);
				}else{
					insertTtTable(checkPo);
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
