package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.po.TiPdicheckReportPO;
import com.yonyou.dcs.dao.SADCS073Dao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.VehicleCustomerDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TmIndustryPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCustomerPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class SADCS073CloudImpl extends BaseCloudImpl implements SADCS073Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS073CloudImpl.class);
	
	@Autowired
	SADCS073Dao dao ;
	
	@Autowired
	SADCS072Cloud cloud ;
	
	@Override
	public String receiveDate(List<VehicleCustomerDTO> dtos) throws Exception {
		String msg = "1";
		
		try {
			logger.info("*************** SADCS073Cloud 车主资料接收开始 *******************");
			for (VehicleCustomerDTO dto : dtos) {
				insertData(dto);
			}
			logger.info("*************** SADCS073Cloud 车主资料接收完成 ********************");
			
			//SADCS072Cloud 车主资料下发 
			for (VehicleCustomerDTO dto : dtos) {
				cloud.sendData(dto.getVin(), dto.getDealerCode());
			}
		} catch (Exception e) {
			logger.error("*************** SADCS073Cloud 车主资料接收异常 *****************", e);
			msg = "0";
			throw new ServiceBizException(e);
		} 
		return msg;
	}
	/**
	 * 接收上报上来的车主资料
	 * @param dto
	 * @throws Exception
	 */
	private String insertData(VehicleCustomerDTO dto) {
		try {
			TiPdicheckReportPO pdi = new TiPdicheckReportPO();
			if (Utility.testIsNotNull(dto.getDealerCode())) {
				Map<String, Object> dcsInfoMap = dao.getSaDcsDealerCode(dto.getDealerCode());
				String dealerCode = CommonUtils.checkNull(dcsInfoMap.get("DEALER_CODE"));
				pdi.setString("DEALER_CODE",dealerCode);
			}
			
			logger.info("****************开始接收vin为"+dto.getVin()+"的车主信息********************");
			String vin = CommonUtils.checkNull(dto.getVin());//vin
			Map<String, Object> vinMap = dao.getVehiceVin(vin);// 查询下端上报的VIN在上端是否存在
			if (vinMap == null) {
				return "VIN" + dto.getVin() + "不存在，接收失败！";
			}
			List<Map<String, Object>> ctmIdList = dao.searchCtmIdByVin(vin);
			if (ctmIdList!= null && ctmIdList.size()>0) {//存在则更新
				String ctmId = ctmIdList.get(0).get("CTM_ID").toString();
				
				Integer ctmType = getCode(CommonUtils.checkNull(dto.getOwnerProperty())==""?0:dto.getOwnerProperty());//客户类型
				String ctmName = CommonUtils.checkNull(dto.getOwnerName());//客户姓名
				Integer cardType = getCode(CommonUtils.checkNull(dto.getCtCode())==""?0:dto.getCtCode());//证件类型
				String cardNum = CommonUtils.checkNull(dto.getCertificateNo());//证件号码
				Integer sex = getCode(CommonUtils.checkNull(dto.getGender())==""?0:dto.getGender());//客户性别
				Integer income = getCode(CommonUtils.checkNull(dto.getFamilyIncome())==""?0:dto.getFamilyIncome());//家庭月收入
				Date birthday = dto.getBirthday();//出生年月
				String mainPhone = CommonUtils.checkNull(dto.getContactorMobile());//车主手机号码
				String otherPhone = CommonUtils.checkNull(dto.getContactorPhone());//车主电话号码
				Integer isMarried= getCode(CommonUtils.checkNull(dto.getOwnerMarriage())==""?0:dto.getOwnerMarriage());//婚姻状况
				String industryFirst = CommonUtils.checkNull(dto.getIndustryFirst());//所在行业大类
				String industrySecond = CommonUtils.checkNull(dto.getIndustrySecond());//所在行业小类
				Integer provinceCode = CommonUtils.checkNull(dto.getProvince())==""?0:dto.getProvince();//所在省份
				Integer cityCode = CommonUtils.checkNull(dto.getCity())==""?0:dto.getCity();//所在城市
				Integer townCode = CommonUtils.checkNull(dto.getDistrict())==""?0:dto.getDistrict();//所在区县
				String address = CommonUtils.checkNull(dto.getAddress());//地址
				String postCode = CommonUtils.checkNull(dto.getZipCode());//邮编
				String email = CommonUtils.checkNull(dto.getEmail());//电子邮件
				
				String dealerName = "";
				logger.info("****************根据上报经销商Code:"+dto.getDealerCode()+"查询名称********************");
				List<Map<String, Object>> dealerNameList = dao.searchDealerName(dto.getDealerCode());
				if(dealerNameList.size()>0){
					dealerName = (String) dealerNameList.get(0).get("DEALER_SHORTNAME");
				}
				logger.info("****************根据行业大类代码:"+industryFirst+"查询名称********************");
				if(!industryFirst.equals("")){
					List<TmIndustryPO> list1 = TmIndustryPO.find("INDUSTRY_CODE = ?", industryFirst);
					if(list1.size()>0){
						industryFirst = CommonUtils.checkNull(list1.get(0).get("INDUSTRY_NAME"));
					}
				}
				logger.info("****************根据行业小类代码:"+industrySecond+"查询名称********************");
				if(!industrySecond.equals("")){
					List<TmIndustryPO> list2 = TmIndustryPO.find("INDUSTRY_CODE = ?", industrySecond);;
					if(list2.size()>0){
						industrySecond =  CommonUtils.checkNull(list2.get(0).get("INDUSTRY_NAME"));
					}
				}

				// 更新结果
				StringBuffer upSqlV = new StringBuffer();
				upSqlV.append("CTM_TYPE = ?");
				upSqlV.append("CTM_NAME = ?");
				upSqlV.append("CARD_TYPE = ?");
				upSqlV.append("CARD_NUM = ?");
				upSqlV.append("SEX = ?");
				upSqlV.append("INCOME = ?");
				upSqlV.append("BIRTHDAY = ?");
				upSqlV.append("MAIN_PHONE = ?");
				upSqlV.append("OTHER_PHONE = ?");
				upSqlV.append("IS_MARRIED = ?");
				upSqlV.append("INDUSTRY_FIRST = ?");
				upSqlV.append("INDUSTRY_SECOND = ?");
				upSqlV.append("CITY = ?");
				upSqlV.append("TOWN = ?");
				upSqlV.append("ADDRESS = ?");
				upSqlV.append("POST_CODE = ?");
				upSqlV.append("EMAIL = ?");
				upSqlV.append("MAINTAIN_BY = ?");
				upSqlV.append("UPDATE_BY = ?");
				upSqlV.append("UPDATE_DATE = ?");
				// 更新条件
				StringBuffer upSqlC = new StringBuffer();
				upSqlC.append("CTM_ID = ?");
				// 参数
				List<Object> params = new ArrayList<Object>();
				params.add(OemDictCodeConstants.IF_TYPE_NO);// 校验结果:成功：10041001;失败：10041002
				params.add(1);
				params.add(DEConstant.DE_UPDATE_BY);
				params.add(new Date());
				params.add(ctmId);
				TtVsCustomerPO.update(upSqlV.toString(), upSqlC.toString(), params.toArray());
				TtVsCustomerPO cPo=TtVsCustomerPO.findById(ctmId);
				cPo.setInteger("CTM_TYPE", ctmType);
				cPo.setString("CTM_NAME", ctmName);
				cPo.setInteger("CARD_TYPE", cardType);
				cPo.setString("CARD_NUM", cardNum);
				cPo.setInteger("SEX", sex);
				cPo.setInteger("INCOME", income);
				cPo.setDate("BIRTHDAY", birthday);
				cPo.setString("MAIN_PHONE", mainPhone);
				cPo.setString("OTHER_PHONE", otherPhone);
				cPo.setInteger("IS_MARRIED", isMarried);
				cPo.setString("INDUSTRY_FIRST", industryFirst);
				cPo.setString("INDUSTRY_SECOND", industrySecond);
				cPo.setInteger("PROVINCE", provinceCode);
				cPo.setInteger("CITY", cityCode);
				cPo.setInteger("TOWN", townCode);
				
				cPo.setString("ADDRESS", address);
				cPo.setString("POST_CODE", postCode);
				cPo.setString("EMAIL", email);
				cPo.setString("MAINTAIN_BY", dealerName);
				cPo.setDate("UPDATE_DATE", new Date());
				cPo.setLong("UPDATE_BY", 2222222L);
				cPo.saveIt();
				logger.info("*******************vin为"+dto.getVin()+"的车主信息接收成功*************************");
			}
			return "success";
		} catch (Exception e) {
			throw new ServiceBizException(e);
		}
		
	}
	/**
	 * 下端Code转换成上端Code
	 * @param code
	 * @return
	 */
	private Integer getCode(Integer code) {
		// 性别
		if (code == 10061001) {
			return OemDictCodeConstants.MAN;
		}
		if (code == 10061002) {
			return OemDictCodeConstants.WOMEN;
		}
		if (code == 10061003) {
			return OemDictCodeConstants.NONO;
		}
		// 婚姻状态
		if (code == 11191001) {
			return OemDictCodeConstants.MARRIED_TYPE_01;
		}
		if (code == 11191002) {
			return OemDictCodeConstants.MARRIED_TYPE_02;
		}
		// 教育状态
		if (code == 11161001) {
			return OemDictCodeConstants.EDUCATION_TYPE_01;
		}
		if (code == 11161002) {
			return OemDictCodeConstants.EDUCATION_TYPE_02;
		}
		if (code == 11161003) {
			return OemDictCodeConstants.EDUCATION_TYPE_03;
		}
		if (code == 11161004) {
			return OemDictCodeConstants.EDUCATION_TYPE_04;
		}
		if (code == 11161005) {
			return OemDictCodeConstants.EDUCATION_TYPE_05;
		}
		if (code == 11161006) {
			return OemDictCodeConstants.EDUCATION_TYPE_06;
		}
		// 家庭月收入
		if (code == 11181001) {
			return OemDictCodeConstants.INCOME_TYPE_01;
		}
		if (code == 11181002) {
			return OemDictCodeConstants.INCOME_TYPE_02;
		}
		if (code == 11181003) {
			return OemDictCodeConstants.INCOME_TYPE_03;
		}
		if (code == 11181004) {
			return OemDictCodeConstants.INCOME_TYPE_04;
		}
		if (code == 11181005) {
			return OemDictCodeConstants.INCOME_TYPE_05;
		}
		if (code == 11181006) {
			return OemDictCodeConstants.INCOME_TYPE_06;
		}

		// 证件类型
		if (code == 12391001) {
			return OemDictCodeConstants.CARD_TYPE_01;
		}
		if (code == 12391002) {
			return OemDictCodeConstants.CARD_TYPE_04;
		}
		if (code == 12391003) {
			return OemDictCodeConstants.CARD_TYPE_02;
		}
		if (code == 12391004) {
			return OemDictCodeConstants.CARD_TYPE_05;
		}
		if (code == 12391005) {
			return OemDictCodeConstants.CARD_TYPE_03;
		}
		if (code == 12391006) {
			return OemDictCodeConstants.CARD_TYPE_06;
		}
		if (code == 12391007) {
			return OemDictCodeConstants.CARD_TYPE_07;
		}
		//客户类型
		if (code == 11901001) {
			return OemDictCodeConstants.CTM_TYPE_02;
		}
		if (code == 11901002) {
			return OemDictCodeConstants.CTM_TYPE_01;
		}
		return 0;
	}
}
