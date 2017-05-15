package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.dccDto;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.common.domains.PO.basedata.TiSalesLeadsCustomerPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
/**
 * 
* @ClassName: SalesLeadsCustomerDao 
* @Description: DCC潜在客户信息下发
* @author zhengzengliang 
* @date 2017年4月12日 下午4:19:48 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class SalesLeadsCustomerDao extends OemBaseDAO{
	
	/**
	 * 
	* @Title: querySalesLeadsCustomerPOInfo 
	* @Description: TODO()查询待下发的DCC潜在客户信息数据 
	* @return List<dccVO>    返回类型 
	* @throws
	 */
	public List<dccDto> querySalesLeadsCustomerPOInfo() {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT SLC.NID,\n" );
		sql.append("       SLC.ID ,\n" );
		sql.append("       SLC.DMS_CUSTOMER_ID,\n" );
		sql.append("       SLC.NAME,\n" );
		sql.append("       SLC.GENDER,\n" );
		sql.append("       SLC.PHONE,\n" );
		sql.append("       SLC.TELEPHONE,\n" );
		sql.append("       SLC.EMAIL,\n" );
		sql.append("       SLC.PROVINCE_ID,\n" );
		sql.append("       SLC.CITY_ID,\n" );
		sql.append("       SLC.ADDRESS,\n" );
		sql.append("       SLC.POST_CODE,\n" );
		sql.append("       SLC.SOCIALITY_ACCOUNT,\n" );
		sql.append("       SLC.BIRTHDAY,\n" );
		sql.append("       SLC.BRAND_ID,\n" );
		sql.append("       SLC.MODEL_ID,\n" );
		sql.append("       SLC.CAR_STYLE_ID,\n" );
		sql.append("       SLC.INTENT_CAR_COLOR,\n" );//意向车型颜色
		sql.append("       SLC.OPPORTUNITY_LEVEL_ID,\n" );
		sql.append("       SLC.CONSIDERATION_ID,\n" );
		sql.append("       SLC.SALES_CONSULTANT,\n" );
		sql.append("       SLC.MEDIA_TYPE_ID,\n" );
		sql.append("       SLC.MEDIA_NAME_ID,\n" );
		sql.append("       SLC.DEALER_USER_NAME,\n" );
		sql.append("       SLC.DEALER_REMARK,\n" );
		sql.append("       SLC.IS_SCAN,\n" );
		sql.append("       SLC.CREATE_BY,\n" );
		sql.append("       SLC.CREATE_DATE,\n" );
		sql.append("       SLC.UPDATE_BY,\n" );
		sql.append("       SLC.UPDATE_DATE,\n" );
		sql.append("       SLC.DEALER_CODE, \n" );
		sql.append("       C.DMS_CODE, \n" );
		sql.append("       VMG_MODEL.GROUP_CODE  SERIES_CODE, \n" );				//车系代码
		sql.append("       VMG_CARSTYLE.GROUP_CODE CONFIG_CODE, \n" );		//配置代码
		sql.append("       VMG_BRAND.GROUP_CODE BRAND_CODE, \n" );				//品牌代码
		sql.append("       P.GROUP_CODE MODEL_CODE \n" );				//车型代码
		sql.append("          FROM TI_SALES_LEADS_CUSTOMER_DCS SLC," );
		sql.append("          TM_VHCL_MATERIAL_GROUP VMG_MODEL, \n" );
		sql.append("          TM_VHCL_MATERIAL_GROUP VMG_CARSTYLE," );
		sql.append("          TM_VHCL_MATERIAL_GROUP VMG_BRAND, " );
		sql.append("          TM_VHCL_MATERIAL_GROUP P, " );
		sql.append("          TM_DEALER A, TM_COMPANY B, TI_DEALER_RELATION C " );
		sql.append("          WHERE  SLC.MODEL_ID=VMG_MODEL.GROUP_ID   \n" );
		sql.append("          AND SLC.CAR_STYLE_ID=VMG_CARSTYLE.GROUP_ID" );
		sql.append("          AND SLC.BRAND_ID=VMG_BRAND.GROUP_ID " );
		sql.append("          AND VMG_CARSTYLE.PARENT_GROUP_ID = P.GROUP_ID " );
		sql.append("          AND  A.COMPANY_ID = B.COMPANY_ID AND B.COMPANY_CODE = C.DCS_CODE" );
		sql.append("          AND A.DEALER_CODE = SLC.DEALER_CODE" );
		sql.append("          AND ( SLC.IS_SCAN = 0 OR SLC.IS_SCAN is null)" );
		//wjs 2015-04-09
		sql.append("          AND C.STATUS="+OemDictCodeConstants.STATUS_ENABLE);
		//测试时TI_SALES_LEADS_CUSTOMER中的CAR_STYLE_ID字段应该是TM_VHCL_MATERIAL_GROUP表中GROUP_LEVEL为4的字段，
		//MODEL_ID应该是TM_VHCL_MATERIAL_GROUP表中该条记录CAR_STYLE_ID的父级的父级（GROUP_LEVEL为2的字段）
		//BRAND_ID应该是对应的MODEL_ID在TM_VHCL_MATERIAL_GROUP表中的父级（GROUP_LEVEL为1的字段）
		List<dccDto> list = wrapperVO(OemDAOUtil.findAll(sql.toString(),null));
		return list;
	}
	protected List<dccDto> wrapperVO(List<Map> rs) {
		List<dccDto> resultList = new ArrayList<dccDto>();
		try {
			if(null!=rs && rs.size()>0){
				for (int i = 0; i < rs.size(); i++) {
					dccDto vo = new dccDto();
					Map po = rs.get(i);
					vo.setAddress(CommonUtils.checkNull(po.get("ADDRESS")));
					vo.setBirthday(DateUtil.parseDefaultDate(CommonUtils.checkNull(po.get("BIRTHDAY"))));
					vo.setBrandCode(CommonUtils.checkNull(po.get("BRAND_CODE")));//品牌代码
					vo.setSeriesCode(CommonUtils.checkNull(po.get("SERIES_CODE")));//车系代码
					vo.setConfigCode(CommonUtils.checkNull(po.get("CONFIG_CODE")));//配置代码
					vo.setModelCode(CommonUtils.checkNull(po.get("MODEL_CODE")));//车型代码
					if( po.get("CITY_ID")!= null ){
						vo.setCityId(Integer.parseInt((po.get("CITY_ID").toString())));
					}else{
						vo.setCityId(null);
					}
					if( po.get("CONSIDERATION_ID")!= null ){
						vo.setConsiderationId(Integer.parseInt((po.get("CONSIDERATION_ID").toString())));
					}else{
						vo.setConsiderationId(null);
					}
					vo.setCreateDate(DateUtil.parseDefaultDateTimeMin(CommonUtils.checkNull(po.get("CREATE_DATE"))));
					vo.setDealerRemark(CommonUtils.checkNull(po.get("DEALER_REMARK")));
					vo.setDealerUserName(CommonUtils.checkNull(po.get("DEALER_USER_NAME")));
					vo.setDmsCustomerId(CommonUtils.checkNull(po.get("DMS_CUSTOMER_ID")));
					//vo.setDownTimestamp(new Date());
					vo.setEmail(CommonUtils.checkNull(po.get("EMAIL")));
//					vo.setErrorMsg();
					vo.setGender(CommonUtils.checkNull(po.get("GENDER")));
					vo.setId(Long.valueOf(CommonUtils.checkNull(po.get("ID"))));
					vo.setNid(Long.valueOf(CommonUtils.checkNull(po.get("NID"))));
					vo.setColorCode(CommonUtils.checkNull(po.get("INTENT_CAR_COLOR")));	//暂无ColorCode数据
//					vo.setIsValid();
					if( po.get("MEDIA_NAME_ID")!= null ){
						vo.setMediaNameId(Integer.parseInt((po.get("MEDIA_NAME_ID").toString())));
					}else{
						vo.setMediaNameId(null);
					}
					if( po.get("MEDIA_TYPE_ID")!= null ){
						vo.setMediaTypeId(Integer.parseInt((po.get("MEDIA_TYPE_ID").toString())));
					}else{
						vo.setMediaTypeId(null);
					}
					vo.setName(CommonUtils.checkNull(po.get("NAME")));
					if( po.get("OPPORTUNITY_LEVEL_ID")!= null ){
						vo.setOpportunityLevelId(Integer.parseInt((po.get("OPPORTUNITY_LEVEL_ID").toString())));
					}else{
						vo.setOpportunityLevelId(null);
					}
					vo.setPhone(CommonUtils.checkNull(po.get("PHONE")));
					vo.setPostCode(CommonUtils.checkNull(po.get("POST_CODE")));
					if( po.get("PROVINCE_ID")!= null ){
						vo.setProvinceId(Integer.parseInt((po.get("PROVINCE_ID").toString())));
					}else{
						vo.setProvinceId(null);
					}
					vo.setSalesConsultant(CommonUtils.checkNull(po.get("SALES_CONSULTANT")));
					vo.setSocialityAccount(CommonUtils.checkNull(po.get("SOCIALITY_ACCOUNT")));
					vo.setTelephone(CommonUtils.checkNull(po.get("TELEPHONE")));
					vo.setEntityCode(CommonUtils.checkNull(po.get("DMS_CODE")));
					resultList.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException(e);
		}
		return resultList;
	}
	
	//将 指定nid 的TI_SALES_LEADS_CUSTOMER中 IS_SCAN=0的字段更新为1
	public int finishSalesLeadsCustomerSCANStatus(Long nid) {
		int i = TiSalesLeadsCustomerPO.update(" IS_SCAN=1 , UPDATE_BY=? , UPDATE_DATE=?  ", " NID=? AND IS_SCAN=0 ", DEConstant.DE_UPDATE_BY, new Date(), nid);
		System.out.println(i);
		return i;
	}

}
