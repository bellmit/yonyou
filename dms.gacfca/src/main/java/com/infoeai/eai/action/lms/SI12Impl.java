package com.infoeai.eai.action.lms;

import java.text.SimpleDateFormat;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.wsServer.SI12.InfoPO;
import com.yonyou.dcs.dao.SI12Dao;
import com.yonyou.dms.common.domains.PO.basedata.TiSalesLeadsCustomerPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;

/***
 * 功能说明：改功能主要是对用webService传过来的结果集进行解析
 * @author ZRM
 *create_date:2013-05-28
 */

@Service
public class SI12Impl extends BaseService implements SI12 {
	private static Logger logger = LoggerFactory.getLogger(SI12Impl.class);
	
	@Autowired
	SI12Dao dao;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public String execute(Object request) throws Exception {
		//对通过的数据解析
		String returnResult = "0";   //返回1表示成功,0表示失败
		try {
			logger.info("====SI12 is begin====");
			beginDbService();
			
			InfoPO[] si_rq = (InfoPO[])request;
			for(int i=0;i<si_rq.length;i++){
				//创建一个新的对象
				TiSalesLeadsCustomerPO salesLead = new TiSalesLeadsCustomerPO();
				//Long id = Long.parseLong(SequenceManager.getSequence(null));
				//salesLead.setNid(id);
				salesLead.setLong("ID",new Long(si_rq[i].getID()));
				
				//校验数据类型是否符合
				if(null!=si_rq[i].getDmsCustomerID() && !"".equals(si_rq[i].getDmsCustomerID()) && !"0".equals(si_rq[i].getDmsCustomerID()) 
						&& !"Null".equals(si_rq[i].getDmsCustomerID())){
					salesLead.setString("Dms_Customer_Id",si_rq[i].getDmsCustomerID());
				}
				
				if(null==si_rq[i].getCreateDate() || "".equals(si_rq[i].getCreateDate()) || "0".equals(si_rq[i].getCreateDate())
						|| "Null".equals(si_rq[i].getCreateDate())){
					returnResult = si_rq[i].getID()+" lms传递的CreateDate为空 "+si_rq[i].getCreateDate();
					logger.info(si_rq[i].getID()+" lms传递的CreateDate为空 "+si_rq[i].getCreateDate());
					continue;
				}else{
					salesLead.setTimestamp("Create_Date", sdf.parse(si_rq[i].getCreateDate().substring(0,10)));
				}
				
				if(null==si_rq[i].getName() || "".equals(si_rq[i].getName()) || "0".equals(si_rq[i].getName()) 
						|| "Null".equals(si_rq[i].getName())){
					returnResult = "lms传递的Name为空";
					logger.info("lms传递的Name为空");
					continue;
				}else{
					salesLead.setString("Name", si_rq[i].getName());
				}
				
				if(null==si_rq[i].getGender() || "".equals(si_rq[i].getGender()) || "0".equals(si_rq[i].getGender()) 
						|| "Null".equals(si_rq[i].getGender())){
					returnResult = "lms传递的Gender为空";
					logger.info("lms传递的Gender为空");
					continue;
				}else{
					salesLead.setString("Gender", si_rq[i].getGender());
				}
				
				if(null!=si_rq[i].getPhone() && !"".equals(si_rq[i].getPhone()) && !"0".equals(si_rq[i].getPhone())
						 && !"Null".equals(si_rq[i].getPhone())){
					salesLead.setString("Phone", si_rq[i].getPhone());
				}
				
				if(null!=si_rq[i].getTelephone() && !"".equals(si_rq[i].getTelephone()) && !"0".equals(si_rq[i].getTelephone())
						 && !"Null".equals(si_rq[i].getTelephone())){
					salesLead.setString("Tele_phone", si_rq[i].getTelephone());
				}
				
				if(null!=si_rq[i].getEmail() && !"".equals(si_rq[i].getEmail()) && !"0".equals(si_rq[i].getEmail())
						 && !"Null".equals(si_rq[i].getEmail())){
					salesLead.setString("Email", si_rq[i].getEmail());
				}
				
				if(null!=si_rq[i].getProvinceID() && !"".equals(si_rq[i].getProvinceID()) && !"0".equals(si_rq[i].getProvinceID())
						 && !"Null".equals(si_rq[i].getProvinceID())){
					Long tempId = dao.getLocalId(si_rq[i].getProvinceID());
					if(null!=tempId){
						salesLead.setLong("Province_Id", tempId);
					}else{
						returnResult = "lms传递的ProvinceId在DCS找不到对应的省份";
						logger.info("lms传递的ProvinceId在DCS找不到对应的省份");
						continue;
					}
				}
				
				if(null!=si_rq[i].getCityID() && !"".equals(si_rq[i].getCityID()) && !"0".equals(si_rq[i].getCityID())
						&& !"Null".equals(si_rq[i].getCityID())){
					Long tempId = dao.getLocalId( si_rq[i].getCityID());
					if(null!=tempId){
						salesLead.setInteger("City_Id", Integer.parseInt(tempId.toString()));
					}else{
						returnResult = "lms传递的CityID在DCS找不到对应的城市";
						logger.info("lms传递的CityID在DCS找不到对应的城市");
						continue;
					}
				}
				
				if(null!=si_rq[i].getAddress() && !"".equals(si_rq[i].getAddress()) && !"0".equals(si_rq[i].getAddress())
						 && !"Null".equals(si_rq[i].getAddress())){
					if(si_rq[i].getAddress().length()>100){
						returnResult = "lms传递的Address的长度超过100";
						logger.info("lms传递的Address的长度超过100");
						continue;
					}else{
						salesLead.setString("Address", si_rq[i].getAddress());
					}
				}
				
				if(null!=si_rq[i].getPostCode() && !"".equals(si_rq[i].getPostCode()) && !"0".equals(si_rq[i].getPostCode())
						 && !"Null".equals(si_rq[i].getPostCode())){
					salesLead.setLong("Post_Code", new Long(si_rq[i].getPostCode()));
				}
				
				if(null!=si_rq[i].getSocialityAccount() && !"".equals(si_rq[i].getSocialityAccount()) && !"0".equals(si_rq[i].getSocialityAccount())
						&& !"Null".equals(si_rq[i].getSocialityAccount())){
					if(si_rq[i].getSocialityAccount().length()>20){
						returnResult = "lms传递的SocialityAccount的长度超过20";
						logger.info("lms传递的SocialityAccount的长度超过20");
						continue;
					}else{
						salesLead.setString("Sociality_Account", si_rq[i].getSocialityAccount());
					}
				}
				
				if(null!=si_rq[i].getBirthday() && !"".equals(si_rq[i].getBirthday()) && !"0".equals(si_rq[i].getBirthday())
						&& !"Null".equals(si_rq[i].getBirthday())){
					salesLead.setDate("Birthday", sdf.parse(si_rq[i].getBirthday().substring(0,10)));
				}
				
				//品牌必填
				if(null==si_rq[i].getBrandID() || "".equals(si_rq[i].getBrandID()) || "0".equals(si_rq[i].getBrandID())
						|| "Null".equals(si_rq[i].getBrandID())){
					returnResult = "lms传递的BrandId为空";
					logger.info("lms传递的BrandId为空");
					continue;
				}else{
					Long tempId = dao.getGroupId(si_rq[i].getBrandID());
					if(null!=tempId){
						salesLead.setLong("Brand_Id", tempId);
					}else{
						returnResult = "lms传递的BrandId在DCS找不到对应的品牌";
						logger.info("lms传递的BrandId在DCS找不到对应的品牌");
						continue;
					}
				}
				
				//车型必填
				if(null==si_rq[i].getModelID() || "".equals(si_rq[i].getModelID()) || "0".equals(si_rq[i].getModelID())
						|| "Null".equals(si_rq[i].getModelID())){
					returnResult = "lms传递的ModelID为空";
					logger.info("lms传递的ModelID为空");
					continue;
				}else{
					Long tempId = dao.getGroupId(si_rq[i].getModelID());
					if(null!=tempId){
						salesLead.setLong("Model_Id", tempId);
					}else{
						returnResult = "lms传递的ModelId在DCS找不到对应的车型";
						logger.info("lms传递的ModelId在DCS找不到对应的车型");
						continue;
					}
				}

				//车款必填
				if(null==si_rq[i].getCarStyleID() || "".equals(si_rq[i].getCarStyleID()) || "0".equals(si_rq[i].getCarStyleID())
						|| "Null".equals(si_rq[i].getCarStyleID())){
					returnResult = "lms传递的CarStyleID为空";
					logger.info("lms传递的CarStyleID为空");
					continue;
				}else{
					Long tempId = dao.getGroupId(si_rq[i].getCarStyleID());
					if(null!=tempId){
						salesLead.setLong("Car_Style_Id", tempId);
					}else{
						returnResult = "lms传递的CarStyleID在DCS找不到对应的车款";
						logger.info("lms传递的CarStyleID在DCS找不到对应的车款");
						continue;
					}
				}
				
				//颜色以字符串的形式保存，传到下端
				if(null!=si_rq[i].getIntentCarColor() && !"".equals(si_rq[i].getIntentCarColor()) && !"0".equals(si_rq[i].getIntentCarColor())
						 && !"Null".equals(si_rq[i].getIntentCarColor())){
					salesLead.setString("Intent_Car_Color", si_rq[i].getIntentCarColor());
				}
				
				//翻译成DMS的字段
				if(null!=si_rq[i].getOpportunityLevelID() && !"".equals(si_rq[i].getOpportunityLevelID())  && !"0".equals(si_rq[i].getOpportunityLevelID())
						 && !"Null".equals(si_rq[i].getOpportunityLevelID())){
					String codeId = dao.getRelationIdInfo( si_rq[i].getOpportunityLevelID(),OemDictCodeConstants.DCC_LEVEL.toString());
					if(null!=codeId){
						salesLead.setLong("Opportunity_Level_Id", new Long(codeId));
					}else{
						returnResult = "lms传递的OpportunityLevelID在DCS找不到对应的意向客户级别";
						logger.info("lms传递的OpportunityLevelID在DCS找不到对应的意向客户级别");
						continue;
					}
				}
				
				//吸引顾客原因，1376购车因素
				if(0!=si_rq[i].getConsiderationID().length()){
					String codeId = dao.getRelationIdInfo(si_rq[i].getConsiderationID(),"1376");
					if(null!=codeId){
						salesLead.setLong("Consideration_Id", new Long(codeId));
					}else{
						returnResult = "找不到对应的吸引顾客原因代码!";
						logger.info("找不到对应的吸引顾客原因代码!");
						continue;
					}
				}
				
				if(null!=si_rq[i].getSalesConsultant() && !"".equals(si_rq[i].getSalesConsultant()) && !"0".equals(si_rq[i].getSalesConsultant())
						 && !"Null".equals(si_rq[i].getSalesConsultant())){
					salesLead.setString("Sales_Consultant", si_rq[i].getSalesConsultant());
				}
				
				if(null!=si_rq[i].getMediaTypeId() && !"".equals(si_rq[i].getMediaTypeId()) && !"0".equals(si_rq[i].getMediaTypeId())
						 && !"Null".equals(si_rq[i].getMediaTypeId())){
					String codeId = dao.getRelationIdInfo( si_rq[i].getMediaTypeId(),"1298");
					if(null!=codeId){
						salesLead.setLong("Media_Type_Id", new Long(codeId));
					}else{
						returnResult = "lms传递的mediaType在DCS找不到对应的媒体类型";
						logger.info("lms传递的mediaType在DCS找不到对应的媒体类型");
						continue;
					}
				}
				
				if(null!=si_rq[i].getMediaNameId() && !"".equals(si_rq[i].getMediaNameId())  && !"0".equals(si_rq[i].getMediaNameId()) 
						 && !"Null".equals(si_rq[i].getMediaNameId()) ){
					String codeId = dao.getRelationIdInfo(si_rq[i].getMediaNameId(),"1000");
					if(null!=codeId){
						salesLead.setLong("Media_Name_Id", new Long(codeId));
					}else{
						returnResult = "lms传递的mediaName在DCS找不到对应的媒体名称";
						logger.info("lms传递的mediaName在DCS找不到对应的媒体名称");
						continue;
					}
				}
				
				if(null!=si_rq[i].getDealerUserName() && !"".equals(si_rq[i].getDealerUserName()) && !"0".equals(si_rq[i].getDealerUserName())
						 && !"Null".equals(si_rq[i].getDealerUserName())){
					salesLead.setString("Dealer_User_Name", si_rq[i].getDealerUserName());
				}
				
				if(null!=si_rq[i].getDealerRemark() && !"".equals(si_rq[i].getDealerRemark()) && !"0".equals(si_rq[i].getDealerRemark())
						 && !"Null".equals(si_rq[i].getDealerRemark())){
					salesLead.setString("Dealer_Remark", si_rq[i].getDealerRemark());
				}
				
				if(null==si_rq[i].getDealerCode() || "".equals(si_rq[i].getDealerCode()) || "0".equals(si_rq[i].getDealerCode())
						 || "Null".equals(si_rq[i].getDealerCode())){
					returnResult = "lms传递的dealerCode为空";
					logger.info("lms传递的dealerCode为空");
					continue;
				}else{
					String dealerCode = dao.getLocalDealerCode(si_rq[i].getDealerCode());
					if(null!=dealerCode){
						salesLead.setString("Dealer_Code", dealerCode);
					}else{
						returnResult = "lms传递的dealerCode在DCS找不到对应的dealerCode";
						logger.info("lms传递的dealerCode在DCS找不到对应的dealerCode");
						continue;
					}
					salesLead.setString("Dealer_Code", si_rq[i].getDealerCode());
				}
				
				salesLead.setString("Is_Scan", "0");
				salesLead.setLong("Create_By", new Long(80000001));
				salesLead.saveIt();
				
				returnResult = "1";
				
				//手动提交事务
				if(i%100==0){
					dbService.endTxn(true);
					Base.detach();
					dbService.clean();
	                
	        		/******************************结束并清空事物*********************/
	                /******************************开启事物*********************/
					beginDbService();
	    			
				}
			}
			dbService.endTxn(true);
			
    		/******************************结束事物*********************/
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			
			dbService.endTxn(false);
		}finally{
			logger.info("====SI12 is finish====");
			dbService.clean();
			
		}
		//调用下端：下发DCC潜在客户信息
		//SADCS003 osc = new SADCS003();
		//osc.execute();
		return returnResult;
	}
}
