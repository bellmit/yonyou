package com.yonyou.dcs.gacfca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DMSTODCS049Dao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.SADCS049Dto;
import com.yonyou.dms.DTO.gacfca.TiUsedCarReplacementIntentionDetailDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TiUsedCarReplacementIntentionDetailDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtUsedCarReplacementIntentionDetailDcsPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * DMS -> DCS
 * 二手车置换意向明细数据接口
 * @author luoyang
 * return msg 0 error 1 success	
 */
@Service
public class DMSTODCS049CloudImpl extends BaseCloudImpl implements DMSTODCS049Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(DMSTODCS049CloudImpl.class);
	private String groupId = "";
	
	@Autowired
	DMSTODCS049Dao dao;

	@Override
	public String handleExecutor(List<SADCS049Dto> list) throws Exception {
		String msg = "1";
		logger.info("*************************** 开始获取上报的二手车置换意向明细数据(DMSTODCS049)******************************");		
		saveTiTable(list);
		try {
			/****************开启事务**********************/
			beginDbService();
			
			logger.info("##################### 二手车置换意向groupId:####"+groupId+"##########################");
			//从接口表抽取本次接口数据集
			List<TiUsedCarReplacementIntentionDetailDTO> datalist = dao.queryTiUsedData(groupId);
			int size = datalist == null ? 0 :datalist.size();
			logger.info("##################### 二手车置换意向明细数据size####"+size+"##########################");
			//循环遍历数据集进行业务处理
			if(datalist != null && !datalist.isEmpty()){
				for (int i = 0; i < datalist.size(); i++) {
					TiUsedCarReplacementIntentionDetailDTO checkPo = datalist.get(i);
					logger.info("##################### 开始校验上报的二手车置换意向明细数据############################");
					//校验数据
					String message = dataCheck(checkPo);
					logger.info("##################### 校验上报的二手车置换意数据接收 ############################");
					//校验是否有错误消息
					if(Utility.testIsNotNull(message)){
						//校验失败，更新接口表
						updateTiTable(checkPo,message);
					}else{
						//校验通过，写入业务表
						insertTtTable(checkPo);
					}
				}
			}
			
			/****************提交并清理事物**********************/
			dbService.endTxn(true);
		} catch (Exception e) {
			msg = "0";
			e.printStackTrace();
			dbService.endTxn(false);//回滚事务
		} finally {
			Base.detach();
			dbService.clean();
			beginDbService();
		}
		logger.info("##################### 成功接收上报的二手车置换意向明细数据(DMSTODCS049)############################");
		return msg;
	}
	
	/**
	 * 校验上报的数据 
	 * @param checkPo
	 * @return
	 * @throws Exception
	 */
	private String dataCheck(TiUsedCarReplacementIntentionDetailDTO checkPo) {
		String message = "";

		Map dealerCodeMap = dao.getSaDcsDealerCode(checkPo.getDealerCode());
		String dealerCode = CommonUtils.checkNull(dealerCodeMap.get("DEALER_CODE"));
		if (Utility.testIsNull(dealerCode)){
			message = "DCS没有【" + checkPo.getDealerCode() + "】经销商信息！";
			return message;
		}
		//非空、长度<16
		String itemId = checkPo.getItemId();
		if (Utility.testIsNotNull(itemId)){
			if(itemId.length()>16){
				message = "ITEM_ID长度不正确："+itemId;
				return message;
			}
		}else{
			message = "ITEM_ID为空";
			return message;
		}
		//非空、长度<20
		String customerNo = checkPo.getCustomerNo();
		if (Utility.testIsNotNull(customerNo)){
			if(customerNo.length()>20){
				message = "CUSTOMER_NO长度不正确："+customerNo;
				return message;
			}
		}else{
			message = "CUSTOMER_NO为空";
			return message;
		}
		//非空、值为10181001或10181002
		String  customerType =checkPo.getCustomerType();
		if (Utility.testIsNotNull(customerType)){
			if(!customerType.equals("10181001") && !customerType.equals("10181002")){
				message = "客户类型不正确："+customerType;
				return message;
			}
		}else{
			message = "客户类型为空";
			return message;
		}
		//置换意向时间（YYYY-MM-DD）
		//不为空校验
		//取值：DMS端创建意向时间（CREATE_DATE）
		String intentionDate = checkPo.getIntentionDate();
		if (Utility.testIsNotNull(intentionDate)){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				sdf.parse(intentionDate);
			} catch (Exception e) {
				message = "置换意向时间(YYYY-MM-DD):"+intentionDate;
				return message;
			}
		}else{
			message = "置换意向时间为空";
			return message;
		}
		String intentionBrandCode = checkPo.getIntentionBrandCode();
		String intentionSeriesCode = checkPo.getIntentionSeriesCode();
		String intentionModelCode = CommonUtils.checkNull(checkPo.getIntentionModelCode().replace("-8BL", ""),"");
		if(Utility.testIsNotNull(intentionBrandCode) && Utility.testIsNotNull(intentionSeriesCode) && Utility.testIsNotNull(intentionModelCode)){
			List<Map> intentionList = dao.selectMaterial(intentionBrandCode,intentionSeriesCode,intentionModelCode);
			if(intentionList==null || intentionList.size()<=0){
				message = "意向物料无效:品牌："+intentionBrandCode+",车系："+intentionSeriesCode+",车型："+intentionModelCode;
				return message;
			}
		}
		//非空、长度为1、值在1~3之间
		String reportType = checkPo.getReportType();
		if (Utility.testIsNotNull(reportType)){
			if(reportType.length()==1){
				if(Integer.parseInt(reportType)<1 || Integer.parseInt(reportType)>3){
					message = "上报类型不正确："+reportType;
					return message;
				}
			}else{
				message = "上报类型不正确："+reportType;
				return message;
			}
		}else{
			message = "上报类型为空";
			return message;
		}
		return message;
	}
	
	/**
	 * 更新接口表数据，将错误信息更新到接口表
	 * @param checkPo
	 * @param message
	 * @throws Exception
	 */
	private void updateTiTable(TiUsedCarReplacementIntentionDetailDTO checkPo, String message) {
		TiUsedCarReplacementIntentionDetailDcsPO.update("RESULT = ?,MESSAGE = ?,UPDATE_BY = ?,UPDATE_DATE = ?", "ID = ?", 
				OemDictCodeConstants.IF_TYPE_NO,message,DEConstant.DE_UPDATE_BY,new Date(),checkPo.getId());
		
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
	 * @param checkPo
	 * @throws Exception
	 */
	private void insertTtTable(TiUsedCarReplacementIntentionDetailDTO checkPo) throws Exception {
		List<TtUsedCarReplacementIntentionDetailDcsPO> list = TtUsedCarReplacementIntentionDetailDcsPO.find("CUSTOMER_NO = ?", checkPo.getCustomerNo());
		if(null!= list && list.size()>0){
			logger.info("*************DMSTODCS049************** 更新客户信息******************************");
			Map dealerCodeMap = dao.getSaDcsDealerCode(checkPo.getDealerCode());
			String dealerCode = CommonUtils.checkNull(dealerCodeMap.get("DEALER_CODE"));
			TtUsedCarReplacementIntentionDetailDcsPO.update("DEALER_CODE = ?,CUSTOMER_PROVINCE = ?,"
					+ "CUSTOMER_CITY = ?,CUSTOMER_TYPE = ?,UPDATE_BY = ?,UPDATE_DATE = ?", 
					"CUSTOMER_NO = ?", 
					dealerCode,CommonUtils.checkNull(checkPo.getCustomerProvince()),CommonUtils.checkNull(checkPo.getCustomerCity()),
					parseCustomerType(checkPo.getCustomerType()),DEConstant.DE_UPDATE_BY,new Date(),checkPo.getCustomerNo());
			String reportType = checkPo.getReportType();//上报类型:1：新增、2：更新、3：删除
			if(reportType.equals("1")){
				logger.info("**************DMSTODCS049************* 新增上报的二手车置换意向明细数据******************************");
				TtUsedCarReplacementIntentionDetailDcsPO po = new TtUsedCarReplacementIntentionDetailDcsPO();
				po.setString("ITEM_ID",new Long(checkPo.getItemId()));
				po.setString("DEALER_CODE",dealerCode);
				po.setString("CUSTOMER_NO",checkPo.getCustomerNo());//客户编号
				po.setString("CUSTOMER_PROVINCE",CommonUtils.checkNull(checkPo.getCustomerProvince()));//客户所在地省份
				po.setString("CUSTOMER_CITY",CommonUtils.checkNull(checkPo.getCustomerCity()));//客户所在地城市
				po.setInteger("CUSTOMER_TYPE",parseCustomerType(checkPo.getCustomerType())); //客户类型
				po.setTimestamp("INTENTION_DATE",Utility.getDate(checkPo.getIntentionDate(),1)); //置换意向时间
				po.setString("INTENTION_BRAND_CODE",CommonUtils.checkNull(checkPo.getIntentionBrandCode()));//意向品牌
				po.setString("INTENTION_SERIES_CODE",CommonUtils.checkNull(checkPo.getIntentionSeriesCode()));//意向车系
				po.setString("INTENTION_MODEL_CODE",CommonUtils.checkNull(checkPo.getIntentionModelCode().replace("-8BL", ""),""));//意向车型
				po.setString("USED_CAR_BRAND_CODE",CommonUtils.checkNull(checkPo.getUsedCarBrandCode()));//二手车品牌
				po.setString("USED_CAR_SERIES_CODE",CommonUtils.checkNull(checkPo.getUsedCarSeriesCode()));//二手车车系
				po.setString("USED_CAR_MODEL_CODE",CommonUtils.checkNull(checkPo.getUsedCarModelCode()));//二手车车型
				po.setString("USED_CAR_LICENSE",CommonUtils.checkNull(checkPo.getUsedCarLicense()));//二手车车牌
				po.setString("USED_CAR_VIN",CommonUtils.checkNull(checkPo.getUsedCarVin()));//二手车VIN
				po.setDouble("USED_CAR_ASSESS_AMOUNT",Double.parseDouble(CommonUtils.checkNull(checkPo.getUsedCarAssessAmount(), "0")));//二手车评估金额
				po.setTimestamp("USED_CAR_LICENSE_DATE",Utility.getDate(checkPo.getUsedCarLicenseDate(),1));//二手车上次上牌日期
				po.setLong("USED_CAR_MILEAGE",Long.parseLong(CommonUtils.checkNull(checkPo.getUsedCarMileage(), "0")));//二手车里程数
				po.setString("USED_CAR_DESCRIBE",CommonUtils.checkNull(checkPo.getUsedCarDescribe()));//二手车描述
				po.setInteger("IS_DEL",0);
				po.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
				po.setTimestamp("CREATE_DATE",new Date());
				po.saveIt();
			}else if(reportType.equals("2")){
				logger.info("*************DMSTODCS049************** 修改上报的二手车置换意向明细数据******************************");
				List<Object> params = new ArrayList<Object>();
				params.add(Utility.getDate(checkPo.getIntentionDate(),1)); //置换意向时间
				params.add(CommonUtils.checkNull(checkPo.getIntentionBrandCode()));//意向品牌
				params.add(CommonUtils.checkNull(checkPo.getIntentionSeriesCode()));//意向车系
				params.add(CommonUtils.checkNull(checkPo.getIntentionModelCode().replace("-8BL", ""),checkPo.getIntentionModelCode()));//意向车型
				params.add(CommonUtils.checkNull(checkPo.getUsedCarBrandCode()));//二手车品牌
				params.add(CommonUtils.checkNull(checkPo.getUsedCarSeriesCode()));//二手车车系
				params.add(CommonUtils.checkNull(checkPo.getUsedCarModelCode()));//二手车车型
				params.add(CommonUtils.checkNull(checkPo.getUsedCarLicense()));//二手车车牌
				params.add(CommonUtils.checkNull(checkPo.getUsedCarVin()));//二手车VIN
				params.add(Double.parseDouble(CommonUtils.checkNull(checkPo.getUsedCarAssessAmount(), "0")));//二手车评估金额
				params.add(Utility.getDate(checkPo.getUsedCarLicenseDate(),1));//二手车上次上牌日期
				params.add(Long.parseLong(CommonUtils.checkNull(checkPo.getUsedCarMileage(), "0")));//二手车里程数
				params.add(CommonUtils.checkNull(checkPo.getUsedCarDescribe()));//二手车描述
				params.add(DEConstant.DE_UPDATE_BY);
				params.add(new Date());
				params.add(checkPo.getItemId());
				TtUsedCarReplacementIntentionDetailDcsPO.update("INTENTION_DATE = ?,INTENTION_BRAND_CODE = ?,"
						+ "INTENTION_SERIES_CODE = ?,INTENTION_MODEL_CODE = ?,USED_CAR_BRAND_CODE = ?,USED_CAR_SERIES_CODE = ?,"
						+ "USED_CAR_MODEL_CODE = ?,USED_CAR_LICENSE = ?,USED_CAR_VIN = ?,USED_CAR_ASSESS_AMOUNT = ?,"
						+ "USED_CAR_LICENSE_DATE = ?,USED_CAR_MILEAGE = ?,USED_CAR_DESCRIBE = ?,"
						+ "UPDATE_BY = ?,UPDATE_DATE = ?", "ITEM_ID = ?",params.toArray());
			}else if(reportType.equals("3")){//删除（逻辑删除）
				logger.info("***************DMSTODCS049************ 逻辑删除上报的二手车置换意向明细数据******************************");
				TtUsedCarReplacementIntentionDetailDcsPO.update("IS_DEL = ?,UPDATE_BY = ?,UPDATE_DATE = ?", 
						"ITEM_ID = ?", 
						1,DEConstant.DE_UPDATE_BY,new Date(),checkPo.getItemId());
			}
		}else{
			logger.info("***************DMSTODCS049************ 没有改记录，新增上报的二手车置换意向明细数据******************************");
			TtUsedCarReplacementIntentionDetailDcsPO po = new TtUsedCarReplacementIntentionDetailDcsPO();
			po.setLong("ITEM_ID",new Long(checkPo.getItemId()));
			Map dealerCodeMap = dao.getSaDcsDealerCode(checkPo.getDealerCode());
			String dealerCode = CommonUtils.checkNull(dealerCodeMap.get("DEALER_CODE"));
			po.setString("DEALER_CODE",dealerCode);
			po.setString("CUSTOMER_NO",checkPo.getCustomerNo());//客户编号
			po.setString("CUSTOMER_PROVINCE",CommonUtils.checkNull(checkPo.getCustomerProvince()));//客户所在地省份
			po.setString("CUSTOMER_CITY",CommonUtils.checkNull(checkPo.getCustomerCity()));//客户所在地城市
			po.setInteger("CUSTOMER_TYPE",parseCustomerType(checkPo.getCustomerType())); //客户类型
			po.setTimestamp("INTENTION_DATE",Utility.getDate(checkPo.getIntentionDate(),1)); //置换意向时间
			po.setString("INTENTION_BRAND_CODE",CommonUtils.checkNull(checkPo.getIntentionBrandCode()));//意向品牌
			po.setString("INTENTION_SERIES_CODE",CommonUtils.checkNull(checkPo.getIntentionSeriesCode()));//意向车系
			po.setString("INTENTION_MODEL_CODE",CommonUtils.checkNull(checkPo.getIntentionModelCode().replace("-8BL", ""),""));//意向车型
			po.setString("USED_CAR_BRAND_CODE",CommonUtils.checkNull(checkPo.getUsedCarBrandCode()));//二手车品牌
			po.setString("USED_CAR_SERIES_CODE",CommonUtils.checkNull(checkPo.getUsedCarSeriesCode()));//二手车车系
			po.setString("USED_CAR_MODEL_CODE",CommonUtils.checkNull(checkPo.getUsedCarModelCode()));//二手车车型
			po.setString("USED_CAR_LICENSE",CommonUtils.checkNull(checkPo.getUsedCarLicense()));//二手车车牌
			po.setString("USED_CAR_VIN",CommonUtils.checkNull(checkPo.getUsedCarVin()));//二手车VIN
			po.setDouble("USED_CAR_ASSESS_AMOUNT",Double.parseDouble(CommonUtils.checkNull(checkPo.getUsedCarAssessAmount(), "0")));//二手车评估金额
			po.setTimestamp("USED_CAR_LICENSE_DATE",Utility.getDate(checkPo.getUsedCarLicenseDate(),1));//二手车上次上牌日期
			po.setLong("USED_CAR_MILEAGE",Long.parseLong(CommonUtils.checkNull(checkPo.getUsedCarMileage(), "0")));//二手车里程数
			po.setString("USED_CAR_DESCRIBE",CommonUtils.checkNull(checkPo.getUsedCarDescribe()));//二手车描述
			po.setInteger("IS_DEL",0);
			po.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
			po.setTimestamp("CREATE_DATE",new Date());
			po.saveIt();
		}
		
	}

	/**
	 * 将下端的客户类型代码转换成上端的代码
	 * @param customerType
	 * @return
	 */
	private Integer parseCustomerType(String customerType) {
		Integer nctype = null;
		if(customerType.equals("10181001")){
			nctype = OemDictCodeConstants.CTM_TYPE_01; //个人客户
		}else if(customerType.equals("10181002")){
			nctype = OemDictCodeConstants.CTM_TYPE_02; //公司客户
		}
		return nctype;
	}

	/**
	 * 将接收到的数据存入接口表
	 * @param bodys
	 * @throws Exception
	 */
	private void saveTiTable(List<SADCS049Dto> list) throws Exception {
		logger.info("##################### 开始接收上报的二手车置换意向明细数据############################");
		try {
			TiUsedCarReplacementIntentionDetailDcsPO usedPO = null;
			/****************开启事物**********************/
			dbService();
			// 创建随机ID，作为本次上报的标识 存入全局变量
			groupId = getRamdomNum();
			for(SADCS049Dto sadcs049VO : list){
				usedPO = new TiUsedCarReplacementIntentionDetailDcsPO();
				logger.info("##################### 二手车意向经销商代码###"+sadcs049VO.getDealerCode()+"#########################");
				usedPO.setString("ITEM_ID",CommonUtils.checkNull(sadcs049VO.getItemId()));
				usedPO.setString("DEALER_CODE",CommonUtils.checkNull(sadcs049VO.getDealerCode()));
				usedPO.setString("CUSTOMER_NO",CommonUtils.checkNull(sadcs049VO.getCustomerNo()));//客户编号
				usedPO.setString("CUSTOMER_PROVINCE",CommonUtils.checkNull(sadcs049VO.getCustomerProvince()));//客户所在地省份
				usedPO.setString("CUSTOMER_CITY",CommonUtils.checkNull(sadcs049VO.getCustomerCity()));//客户所在地城市
				usedPO.setString("CUSTOMER_TYPE",CommonUtils.checkNull(sadcs049VO.getCustomerType())); //客户类型
				usedPO.setString("INTENTION_DATE",Utility.handleDate(sadcs049VO.getIntentionDate())); //置换意向时间
				usedPO.setString("INTENTION_BRAND_CODE",CommonUtils.checkNull(sadcs049VO.getIntentionBrandCode()));//意向品牌
				usedPO.setString("INTENTION_SERIES_CODE",CommonUtils.checkNull(sadcs049VO.getIntentionSeriesCode()));//意向车系
				usedPO.setString("INTENTION_MODEL_CODE",CommonUtils.checkNull(sadcs049VO.getIntentionModelCode()));//意向车型
				usedPO.setString("USED_CAR_BRAND_CODE",CommonUtils.checkNull(sadcs049VO.getUsedCarBrandCode()));//二手车品牌
				usedPO.setString("USED_CAR_SERIES_CODE",CommonUtils.checkNull(sadcs049VO.getUsedCarSeriesCode()));//二手车车系
				usedPO.setString("USED_CAR_MODEL_CODE",CommonUtils.checkNull(sadcs049VO.getUsedCarModelCode()));//二手车车型
				usedPO.setString("USED_CAR_LICENSE",CommonUtils.checkNull(sadcs049VO.getUsedCarLicense()));//二手车车牌
				usedPO.setString("USED_CAR_VIN",CommonUtils.checkNull(sadcs049VO.getUsedCarVin()));//二手车VIN
				usedPO.setString("USED_CAR_ASSESS_AMOUNT",CommonUtils.checkNull(sadcs049VO.getUsedCarAssessAmount(),"0"));//二手车评估金额
				usedPO.setString("USED_CAR_LICENSE_DATE",Utility.handleDate(sadcs049VO.getUsedCarLicenseDate()));//二手车上次上牌日期（YYYY-MM-DD）
				usedPO.setString("USED_CAR_MILEAGE",CommonUtils.checkNull(sadcs049VO.getUsedCarMileage(), "0"));//二手车里程数
				usedPO.setString("USED_CAR_DESCRIBE",CommonUtils.checkNull(sadcs049VO.getUsedCarDescribe()));//二手车描述
				usedPO.setString("GROUP_ID",groupId);//组ID（同一批数据同一个ID）
				usedPO.setString("REPORT_TYPE",CommonUtils.checkNull(sadcs049VO.getReportType()));//上报类型:1：新增、2：更新、3：删除
				usedPO.setInteger("RESULT",10041001);//校验结果:成功：10041001;失败：10041002
				usedPO.setString("MESSAGE","校验成功");//校验结果描述
				usedPO.setInteger("IS_DEL",0);
				usedPO.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
				usedPO.setTimestamp("CREATE_DATE",new Date());
				usedPO.saveIt();
			}
			/****************提交事物**********************/
			dbService.endTxn(true);
		} catch (Exception e) {
			e.printStackTrace();
			dbService.endTxn(false);//回滚事务
		} finally {
			Base.detach();
			dbService.clean();
		}
	}
	
	private String getRamdomNum(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddSS");
		Random d = new Random();
		int num = d.nextInt(1000);
		String str = sdf.format(new Date());
		str = str + String.valueOf(num);
		return str;	
	}
	

}
