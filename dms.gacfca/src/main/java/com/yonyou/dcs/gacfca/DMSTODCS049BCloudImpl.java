package com.yonyou.dcs.gacfca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DMSTODCS049BDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.SADCS049Dto;
import com.yonyou.dms.DTO.gacfca.TiUsedCarReplacementIntentionDetailBDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TiUsedCarReplacementIntentionDetailDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtUsedCarReplacementIntentionDetailDcsPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class DMSTODCS049BCloudImpl extends BaseCloudImpl implements DMSTODCS049BCloud {
	private static final Logger logger = LoggerFactory.getLogger(DMSTODCS049BCloudImpl.class);
	private String groupId = "";
	@Autowired
	DMSTODCS049BDao dao ;

	@Override
	public String handleExecutor(List<SADCS049Dto> dtos) throws Exception {
		String msg = "1";
		beginDbService();
		try {
			logger.info("====开始获取二手车置换意向明细接收开始====");
			saveTiTable(dtos);
			
			logger.info("##################### 二手车置换意向groupId:####"+groupId+"##########################");
			//从接口表抽取本次接口数据集
			List<TiUsedCarReplacementIntentionDetailBDTO> datalist = dao.queryTiUsedData(groupId);
			logger.info("##################### 二手车置换意向明细数据size####"+datalist.size()+"##########################");
			//循环遍历数据集进行业务处理
			for (int i = 0; i < datalist.size(); i++) {
				TiUsedCarReplacementIntentionDetailBDTO dto = datalist.get(i);
				logger.info("##################### 开始校验上报的二手车置换意向明细数据############################");
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
			dbService.endTxn(true);
		} catch (Exception e) {
			logger.error("开始获取二手车置换意向明细接收失败", e);
			msg = "0";
			dbService.endTxn(false);
		} finally{
			Base.detach();
			dbService.clean();
		}
		logger.info("*************************** 成功获取上报的二手车置换意向明细数据******************************");
		return msg;
	}

	// 处理上报数据
	private void saveTiTable(List<SADCS049Dto> dtos) throws Exception {
		logger.info("##################### 开始处理二手车置换意向明细 ############################");
		TiUsedCarReplacementIntentionDetailDcsPO usedPO = null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHH24mmss");
		// 创建随机ID，作为本次上报的标识 存入全局变量
		groupId = sdf.format(new Date(System.currentTimeMillis()));
		for (SADCS049Dto dto : dtos) {
			usedPO = new TiUsedCarReplacementIntentionDetailDcsPO();
			usedPO.setString("ITEM_ID",dto.getItemId());
			logger.info("##################### 二手车意向经销商代码###"+dto.getDealerCode()+"#########################");
			usedPO.setString("DEALER_CODE",dto.getDealerCode());
			usedPO.setString("CUSTOMER_NO",dto.getCustomerNo());//客户编号
			usedPO.setString("CUSTOMER_PROVINCE",dto.getCustomerProvince());//客户所在地省份
			usedPO.setString("CUSTOMER_CITY",dto.getCustomerCity());//客户所在地城市
			usedPO.setString("CUSTOMER_TYPE",dto.getCustomerType());//客户类型
			usedPO.setString("INTENTION_DATE",Utility.handleDate(dto.getIntentionDate()));//置换意向时间
			usedPO.setString("INTENTION_BRAND_CODE",dto.getIntentionBrandCode());//意向品牌
			usedPO.setString("INTENTION_SERIES_CODE",dto.getIntentionSeriesCode());//意向车系
			usedPO.setString("INTENTION_MODEL_CODE",dto.getIntentionModelCode());//意向车型
			usedPO.setString("USED_CAR_BRAND_CODE",dto.getUsedCarBrandCode());//二手车品牌
			usedPO.setString("USED_CAR_SERIES_CODE",dto.getUsedCarSeriesCode());//二手车车系
			usedPO.setString("USED_CAR_MODEL_CODE",dto.getUsedCarModelCode());//二手车车型
			usedPO.setString("USED_CAR_LICENSE",dto.getUsedCarLicense());//二手车车牌
			usedPO.setString("USED_CAR_VIN",dto.getUsedCarVin());//二手车VIN
			usedPO.setString("USED_CAR_ASSESS_AMOUNT",dto.getUsedCarAssessAmount());//二手车评估金额
			usedPO.setString("USED_CARD_LICENSE_DATE",Utility.handleDate(dto.getUsedCarLicenseDate()));//二手车上次上牌日期（YYYY-MM-DD）
			usedPO.setString("USED_CARD_MILEAGE",dto.getUsedCarMileage()+"");//二手车里程数
			usedPO.setString("USED_CARD_DESCRIBE",dto.getUsedCarDescribe());//二手车描述
			usedPO.setString("GROUP_ID",groupId);//组ID（yyyyMMddHH24mmss）同一批数据同一个ID
			usedPO.setString("REPORT_TYPE",dto.getReportType()+"");//上报类型:1：新增、2：更新、3：删除
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
	public void updateTiTable(TiUsedCarReplacementIntentionDetailBDTO dto,String message) throws Exception{
		
		List<Object> params = new ArrayList<Object>();
		params.add(OemDictCodeConstants.IF_TYPE_NO);//校验结果:成功：10041001;失败：10041002
		params.add(message);
		params.add(DEConstant.DE_UPDATE_BY);
		params.add(dto.getId());
		TiUsedCarReplacementIntentionDetailDcsPO.update("RESULT = ? AND MESSAGE = ? AND UPDATE_BY = ? AND UPDATE_DATE = ?","ID = ?",params.toArray());
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
	public void insertTtTable(TiUsedCarReplacementIntentionDetailBDTO dto) throws Exception{
		 List<Object> relatList = new ArrayList<Object>();
         relatList.add(dto.getCustomerNo());       
         List<TtUsedCarReplacementIntentionDetailDcsPO> list = TtUsedCarReplacementIntentionDetailDcsPO.findBySQL("select * from TT_USED_CAR_REPLACEMENT_INTENTION_DETAIL where CUSTOMER_NO= ? ",relatList.toArray());           
         
		if(null!= list && list.size()>0){
			logger.info("*************DMSTODCS049************** 更新客户信息******************************");
			String dealerCode = dao.getSaDcsDealerCode1(dto.getDealerCode());
			TtUsedCarReplacementIntentionDetailDcsPO updatePo = TtUsedCarReplacementIntentionDetailDcsPO.findById(list.get(0).get("ID"));
			updatePo.setString("DEALERT_CODE",dealerCode);
			updatePo.setString("CUSTOMER_PROVINCE",CommonUtils.checkNull(dto.getCustomerProvince()));//客户所在地省份
			updatePo.setString("CUSTOMER_CITY",CommonUtils.checkNull(dto.getCustomerCity()));//客户所在地城市
			updatePo.setString("CUSTOMER_TYPE",parseCustomerType(dto.getCustomerType()));//客户类型
			updatePo.setBigDecimal("UPDATE_BY", DEConstant.DE_UPDATE_BY);
			updatePo.setDate("UPDATE_DATE", new Date(System.currentTimeMillis()));
			updatePo.insert();
			String reportType = dto.getReportType();//上报类型:1：新增、2：更新、3：删除
			
			relatList.add(dto.getItemId()); 
			if(reportType.equals("1")){
				logger.info("**************DMSTODCS049************* 新增上报的二手车置换意向明细数据******************************");
				TtUsedCarReplacementIntentionDetailDcsPO po = new TtUsedCarReplacementIntentionDetailDcsPO();
				po.setLong("ITEM_ID",dto.getItemId());
				po.setString("DEALER_CODE",dealerCode);
				po.setString("CUSTOMER_NO",dto.getCustomerNo());//客户编号
				po.setString("CUSTOMER_PROVINCE",CommonUtils.checkNull(dto.getCustomerProvince()));//客户所在地省份
				po.setString("CUSTOMER_CITY",CommonUtils.checkNull(dto.getCustomerCity()));//客户所在地城市
				po.setString("CUSTOMER_TYPE",parseCustomerType(dto.getCustomerType()));//客户类型
				po.setString("INTENTION_DATE",Utility.getDate(dto.getIntentionDate(),1));//置换意向时间
				po.setString("INTENTION_BRAND_CODE",CommonUtils.checkNull(dto.getIntentionBrandCode()));//意向品牌
				po.setString("INTENTION_SERIES_CODE",CommonUtils.checkNull(dto.getIntentionSeriesCode()));//意向车系
				po.setString("INTENTION_MODEL_CODE",CommonUtils.checkNull(dto.getIntentionModelCode().replace("-8BL", ""),""));//意向车型
				po.setString("USED_CAR_BRAND_CODE",CommonUtils.checkNull(dto.getUsedCarBrandCode()));//二手车品牌
				po.setString("USED_CAR_SERIES_CODE",CommonUtils.checkNull(dto.getUsedCarSeriesCode()));//二手车车系
				po.setString("USED_CAR_MODEL_CODE",CommonUtils.checkNull(dto.getUsedCarModelCode()));//二手车车型
				po.setString("USED_CAR_LICENSE",CommonUtils.checkNull(dto.getUsedCarLicense()));//二手车车牌
				po.setString("USED_CAR_VIN",CommonUtils.checkNull(dto.getUsedCarVin()));//二手车VIN
				po.setDouble("USED_CAR_ASSESS_AMOUNT",Double.parseDouble(CommonUtils.checkNull(dto.getUsedCarAssessAmount(), "0d")));//二手车评估金额
				po.setDate("USED_CARD_LICENSE_DATE",Utility.getDate(dto.getUsedCarLicenseDate(),1));//二手车上次上牌日期（YYYY-MM-DD）
				po.setLong("USED_CARD_MILEAGE",Long.parseLong(CommonUtils.checkNull(dto.getUsedCarMileage(), "0L")));//二手车里程数
				po.setString("USED_CARD_DESCRIBE",CommonUtils.checkNull(dto.getUsedCarDescribe()));//二手车描述
				po.setInteger("IS_DEL",0);
				po.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
				po.setDate("CREATE_DATE",new Date(System.currentTimeMillis()));
				po.insert();
			}else if(reportType.equals("2")){
				logger.info("*************DMSTODCS049************** 修改上报的二手车置换意向明细数据******************************");
				List<Object> params = new ArrayList<Object>();
				params.add(Utility.getDate(dto.getIntentionDate(),1)); //置换意向时间
				params.add(CommonUtils.checkNull(dto.getIntentionBrandCode()));//意向品牌
				params.add(CommonUtils.checkNull(dto.getIntentionSeriesCode()));//意向车系
				params.add(CommonUtils.checkNull(dto.getIntentionModelCode().replace("-8BL", ""),dto.getIntentionModelCode()));//意向车型
				params.add(CommonUtils.checkNull(dto.getUsedCarBrandCode()));//二手车品牌
				params.add(CommonUtils.checkNull(dto.getUsedCarSeriesCode()));//二手车车系
				params.add(CommonUtils.checkNull(dto.getUsedCarModelCode()));//二手车车型
				params.add(CommonUtils.checkNull(dto.getUsedCarLicense()));//二手车车牌
				params.add(CommonUtils.checkNull(dto.getUsedCarVin()));//二手车VIN
				params.add(Double.parseDouble(CommonUtils.checkNull(dto.getUsedCarAssessAmount(), "0")));//二手车评估金额
				params.add(Utility.getDate(dto.getUsedCarLicenseDate(),1));//二手车上次上牌日期
				params.add(Long.parseLong(CommonUtils.checkNull(dto.getUsedCarMileage(), "0")));//二手车里程数
				params.add(CommonUtils.checkNull(dto.getUsedCarDescribe()));//二手车描述
				params.add(DEConstant.DE_UPDATE_BY);
				params.add(new Date(System.currentTimeMillis()));
				params.add(dto.getItemId());
				TtUsedCarReplacementIntentionDetailDcsPO.update("INTENTION_DATE = ?,INTENTION_BRAND_CODE = ?,"
						+ "INTENTION_SERIES_CODE = ?,INTENTION_MODEL_CODE = ?,USED_CAR_BRAND_CODE = ?,USED_CAR_SERIES_CODE = ?,"
						+ "USED_CAR_MODEL_CODE = ?,USED_CAR_LICENSE = ?,USED_CAR_VIN = ?,USED_CAR_ASSESS_AMOUNT = ?,"
						+ "USED_CAR_LICENSE_DATE = ?,USED_CAR_MILEAGE = ?,USED_CAR_DESCRIBE = ?,"
						+ "UPDATE_BY = ?,UPDATE_DATE = ?", "ITEM_ID = ?",params.toArray());
			}else if(reportType.equals("3")){//删除（逻辑删除）
				logger.info("***************DMSTODCS049************ 逻辑删除上报的二手车置换意向明细数据******************************");
				TtUsedCarReplacementIntentionDetailDcsPO.update("IS_DEL = ?,UPDATE_BY = ?,UPDATE_DATE = ?", 
						"ITEM_ID = ?", 
						1,DEConstant.DE_UPDATE_BY,new Date(System.currentTimeMillis()),dto.getItemId());
			}
		}else{
			logger.info("***************DMSTODCS049************ 没有改记录，新增上报的二手车置换意向明细数据******************************");
			TtUsedCarReplacementIntentionDetailDcsPO po = new TtUsedCarReplacementIntentionDetailDcsPO();
			String dealerCode = dao.getSaDcsDealerCode1(dto.getDealerCode());
			po.setLong("ITEM_ID",dto.getItemId());
			po.setString("DEALER_CODE",dealerCode);
			po.setString("CUSTOMER_NO",dto.getCustomerNo());//客户编号
			po.setString("CUSTOMER_PROVINCE",CommonUtils.checkNull(dto.getCustomerProvince()));//客户所在地省份
			po.setString("CUSTOMER_CITY",CommonUtils.checkNull(dto.getCustomerCity()));//客户所在地城市
			po.setString("CUSTOMER_TYPE",parseCustomerType(dto.getCustomerType()));//客户类型
			po.setString("INTENTION_DATE",Utility.getDate(dto.getIntentionDate(),1));//置换意向时间
			po.setString("INTENTION_BRAND_CODE",CommonUtils.checkNull(dto.getIntentionBrandCode()));//意向品牌
			po.setString("INTENTION_SERIES_CODE",CommonUtils.checkNull(dto.getIntentionSeriesCode()));//意向车系
			po.setString("INTENTION_MODEL_CODE",CommonUtils.checkNull(dto.getIntentionModelCode().replace("-8BL", ""),""));//意向车型
			po.setString("USED_CAR_BRAND_CODE",CommonUtils.checkNull(dto.getUsedCarBrandCode()));//二手车品牌
			po.setString("USED_CAR_SERIES_CODE",CommonUtils.checkNull(dto.getUsedCarSeriesCode()));//二手车车系
			po.setString("USED_CAR_MODEL_CODE",CommonUtils.checkNull(dto.getUsedCarModelCode()));//二手车车型
			po.setString("USED_CAR_LICENSE",CommonUtils.checkNull(dto.getUsedCarLicense()));//二手车车牌
			po.setString("USED_CAR_VIN",CommonUtils.checkNull(dto.getUsedCarVin()));//二手车VIN
			po.setDouble("USED_CAR_ASSESS_AMOUNT",Double.parseDouble(CommonUtils.checkNull(dto.getUsedCarAssessAmount(), "0d")));//二手车评估金额
			po.setDate("USED_CARD_LICENSE_DATE",Utility.getDate(dto.getUsedCarLicenseDate(),1));//二手车上次上牌日期（YYYY-MM-DD）
			po.setLong("USED_CARD_MILEAGE",Long.parseLong(CommonUtils.checkNull(dto.getUsedCarMileage(), "0L")));//二手车里程数
			po.setString("USED_CARD_DESCRIBE",CommonUtils.checkNull(dto.getUsedCarDescribe()));//二手车描述
			po.setInteger("IS_DEL",0);
			po.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
			po.setDate("CREATE_DATE",new Date(System.currentTimeMillis()));
			po.insert();
		}
	}
	
	/**
	 * 校验上报的数据
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String dataCheck(TiUsedCarReplacementIntentionDetailBDTO dto) throws Exception {
		String message = "";

		String dealerCode = dao.getSaDcsDealerCode1(dto.getDealerCode());
		if (Utility.testIsNull(dealerCode)) {
			message = "DCS没有【" + dto.getDealerCode() + "】经销商信息！";
			return message;
		}
		// 非空、长度<16
		String itemId = dto.getItemId();
		if (Utility.testIsNotNull(itemId)) {
			if (itemId.length() > 16) {
				message = "ITEM_ID长度不正确：" + itemId;
				return message;
			}
		} else {
			message = "ITEM_ID为空";
			return message;
		}
		// 非空、长度<20
		String customerNo = dto.getCustomerNo();
		if (Utility.testIsNotNull(customerNo)) {
			if (customerNo.length() > 20) {
				message = "CUSTOMER_NO长度不正确：" + customerNo;
				return message;
			}
		} else {
			message = "CUSTOMER_NO为空";
			return message;
		}
		// 非空、值为10181001或10181002
		String customerType = dto.getCustomerType();
		if (Utility.testIsNotNull(customerType)) {
			if (!customerType.equals("10181001") && !customerType.equals("10181002")) {
				message = "客户类型不正确：" + customerType;
				return message;
			}
		} else {
			message = "客户类型为空";
			return message;
		}
		// 置换意向时间（YYYY-MM-DD）
		// 不为空校验
		// 取值：DMS端创建意向时间（CREATE_DATE）
		String intentionDate = dto.getIntentionDate();
		if (Utility.testIsNotNull(intentionDate)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				sdf.parse(intentionDate);
			} catch (Exception e) {
				message = "置换意向时间(YYYY-MM-DD):" + intentionDate;
				return message;
			}
		} else {
			message = "置换意向时间为空";
			return message;
		}
		String intentionBrandCode = dto.getIntentionBrandCode();
		String intentionSeriesCode = dto.getIntentionSeriesCode();
		String intentionModelCode = CommonUtils.checkNull(dto.getIntentionModelCode().replace("-8BL", ""), "");
		if (Utility.testIsNotNull(intentionBrandCode) && Utility.testIsNotNull(intentionSeriesCode)
				&& Utility.testIsNotNull(intentionModelCode)) {
			
	         List<Map> bcList = dao.getMaterialInfo(intentionBrandCode,1); // 品牌
	         List<Map> scList = dao.getMaterialInfo(intentionSeriesCode,2); // 车系
	         List<Map> mcList = dao.getMaterialInfo(intentionModelCode,3);// 车型 
	         
			
			
			if (bcList == null || bcList.size() <= 0||scList == null || scList.size() <= 0||mcList == null || mcList.size() <= 0) {
				message = "意向物料无效:品牌：" + intentionBrandCode + ",车系：" + intentionSeriesCode + ",车型："
						+ intentionModelCode;
				return message;
			}
		}
		// 非空、长度为1、值在1~3之间
		String reportType = dto.getReportType();
		if (Utility.testIsNotNull(reportType)) {
			if (reportType.length() == 1) {
				if (Integer.parseInt(reportType) < 1 || Integer.parseInt(reportType) > 3) {
					message = "上报类型不正确：" + reportType;
					return message;
				}
			} else {
				message = "上报类型不正确：" + reportType;
				return message;
			}
		} else {
			message = "上报类型为空";
			return message;
		}
		return message;
	}
	/**
	 * 将下端的客户类型代码转换成上端的代码
	 * @param customerType
	 * @return
	 */
	private  Integer parseCustomerType(String customerType){
		Integer nctype = null;
		if(customerType.equals("10181001")){
			nctype = OemDictCodeConstants.CTM_TYPE_01; //个人客户
		}else if(customerType.equals("10181002")){
			nctype = OemDictCodeConstants.CTM_TYPE_02; //公司客户
		}
		return nctype;
	}

	/**
	 * 测试接入口
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DMSTODCS049BCloudImpl sadcs049 = new DMSTODCS049BCloudImpl();
			List<SADCS049Dto> voList = new ArrayList<SADCS049Dto>();
			for (int i = 10; i <= 11; i++) {
				SADCS049Dto vo = new SADCS049Dto();
				vo.setItemId(1234567890L+i);
				vo.setDealerCode("CJ770700");
				vo.setCustomerProvince("130000");//省份
				vo.setCustomerCity("1306");//城市
				vo.setCustomerType(10181001);//客户类型
				vo.setIntentionDate(Utility.getCurrentDateTime());//置换意向时间
				vo.setIntentionBrandCode("CHRYSLER");//意向品牌
				vo.setIntentionSeriesCode("300C");//意向车系
				vo.setIntentionModelCode("LXCL4822G");//意向车型
				vo.setUsedCarBrandCode("DODGE");//二手车品牌
				vo.setUsedCarSeriesCode("JOURNEY");//二手车车系
				vo.setUsedCarModelCode("JCDP4926J");//二手车车型
				vo.setUsedCarLicense("京LG3296");//二手车车牌
				vo.setUsedCarVin("LTNGY14A780000802");//二手车VIN
				vo.setUsedCarAssessAmount(11.2);//二手车评估金额
				vo.setUsedCarLicenseDate(new Date(System.currentTimeMillis()));;//二手车上次上牌时间
				vo.setUsedCarMileage(i+1200L);//二手车里程数
				vo.setUsedCarDescribe("insert测试byhuyu");//二手车描述
				vo.setReportType(1);// 报表类型
				voList.add(vo);
			}
			sadcs049.execute(voList);
			System.out.println("End..");
		} catch (Exception e) {
			throw new ServiceBizException(e);
		} 
	}
	private void execute(List<SADCS049Dto> voList) throws Exception{
		try {
			TiUsedCarReplacementIntentionDetailDcsPO usedPO = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			// 创建日期格式化成ID，作为本次上报的标识 存入全局变量
			groupId = sdf.format(new Date(System.currentTimeMillis()));
			for (SADCS049Dto dto : voList) {
				usedPO = new TiUsedCarReplacementIntentionDetailDcsPO();
				usedPO.setString("ITEM_ID",dto.getItemId());
				logger.info("##################### 二手车意向经销商代码###"+dto.getDealerCode()+"#########################");
				usedPO.setString("DEALER_CODE",dto.getDealerCode());
				usedPO.setString("CUSTOMER_NO",dto.getCustomerNo());//客户编号
				usedPO.setString("CUSTOMER_PROVINCE",dto.getCustomerProvince());//客户所在地省份
				usedPO.setString("CUSTOMER_CITY",dto.getCustomerCity());//客户所在地城市
				usedPO.setString("CUSTOMER_TYPE",dto.getCustomerType());//客户类型
				usedPO.setString("INTENTION_DATE",Utility.handleDate(dto.getIntentionDate()));//置换意向时间
				usedPO.setString("INTENTION_BRAND_CODE",dto.getIntentionBrandCode());//意向品牌
				usedPO.setString("INTENTION_SERIES_CODE",dto.getIntentionSeriesCode());//意向车系
				usedPO.setString("INTENTION_MODEL_CODE",dto.getIntentionModelCode());//意向车型
				usedPO.setString("USED_CAR_BRAND_CODE",dto.getUsedCarBrandCode());//二手车品牌
				usedPO.setString("USED_CAR_SERIES_CODE",dto.getUsedCarSeriesCode());//二手车车系
				usedPO.setString("USED_CAR_MODEL_CODE",dto.getUsedCarModelCode());//二手车车型
				usedPO.setString("USED_CAR_LICENSE",dto.getUsedCarLicense());//二手车车牌
				usedPO.setString("USED_CAR_VIN",dto.getUsedCarVin());//二手车VIN
				usedPO.setString("USED_CAR_ASSESS_AMOUNT",dto.getUsedCarAssessAmount());//二手车评估金额
				usedPO.setString("USED_CARD_LICENSE_DATE",Utility.handleDate(dto.getUsedCarLicenseDate()));//二手车上次上牌日期（YYYY-MM-DD）
				usedPO.setString("USED_CARD_MILEAGE",dto.getUsedCarMileage()+"");//二手车里程数
				usedPO.setString("USED_CARD_DESCRIBE",dto.getUsedCarDescribe());//二手车描述
				usedPO.setString("GROUP_ID",groupId);//组ID（yyyyMMddHH24mmss）同一批数据同一个ID
				usedPO.setString("REPORT_TYPE",dto.getReportType()+"");//上报类型:1：新增、2：更新、3：删除
				usedPO.setInteger("RESULT",OemDictCodeConstants.IF_TYPE_YES);//校验结果:成功：10041001;失败：10041002
				usedPO.setString("MESSAGE","校验成功");//校验结果描述
				usedPO.setInteger("IS_DEL",0);
				usedPO.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
				usedPO.setDate("CREATE_DATE",new Date(System.currentTimeMillis()));
				usedPO.insert();
			}
			logger.info("##################### 成功接收上报的二手车置换意向明细数据############################");
			/****************提交并清理事物**********************/
			
			logger.info("##################### 二手车置换意向groupId:####"+groupId+"##########################");
			//从接口表抽取本次接口数据集
			List<TiUsedCarReplacementIntentionDetailBDTO> datalist = dao.queryTiUsedData(groupId);
			logger.info("##################### 二手车置换意向明细数据size####"+datalist.size()+"##########################");
			//循环遍历数据集进行业务处理
			for (int i = 0; i < datalist.size(); i++) {
				TiUsedCarReplacementIntentionDetailBDTO checkPo = datalist.get(i);
				logger.info("##################### 开始校验上报的二手车置换意向明细数据############################");
				//校验数据
				String message = dataCheck(checkPo);
				logger.info("##################### 校验上报的二手车置换意向明细数据接收 ############################");
				//校验是否有错误消息
				if(Utility.testIsNotNull(message)){
					//校验失败，更新接口表
					updateTiTable(checkPo,message);
				}else{
					//校验通过，写入业务表
					insertTtTable(checkPo);
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
