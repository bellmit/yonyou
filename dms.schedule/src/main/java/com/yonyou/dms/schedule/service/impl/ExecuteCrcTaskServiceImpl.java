/**
 * 
 */
package com.yonyou.dms.schedule.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.CustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.TrackingTaskPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerLinkmanInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusRelationPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesCrPO;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.schedule.domains.DTO.TmTrackingTaskDTO;
import com.yonyou.dms.schedule.domains.DTO.TtCustomerLinkmanInfoDTO;
import com.yonyou.dms.schedule.domains.DTO.TtPoCusRelationDTO;
import com.yonyou.dms.schedule.domains.DTO.TtSalesCrDTO;

/**
 * @author xhy
 * 保有客户关怀
 * @date 2017年2月23日
 */
@Service
public class ExecuteCrcTaskServiceImpl implements ExecuteCrcTaskService {

	@SuppressWarnings({ "unused", "rawtypes", "unchecked", "static-access" })
	@Override
	public int performExecute() throws ServiceBizException {
		long userID = 999999999;
	
		try{
			List<Map> customerList = new ArrayList<Map>();
			List<Map> taskList = new ArrayList<Map>();
			List<Map> list = new ArrayList<Map>();
			Map beanCustomer = null;
			Map beanTask = null;
			Map bean = null;
			String intervalDays = "";
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
			SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
			String today = f.format(new Date(System.currentTimeMillis()));
			String cusNo = "";
			String taskId = "";
			Calendar c = Calendar.getInstance();
			TtSalesCrPO salcrpo = new TtSalesCrPO();
			TtSalesCrDTO salcrConPo = new TtSalesCrDTO();
			TrackingTaskPO taskPo = new TrackingTaskPO();
			TmTrackingTaskDTO taskConPo = new TmTrackingTaskDTO();
			
			taskList =this.selectCrcTask();
			for (int i = 0; i < taskList.size(); i++){
				beanTask=   taskList.get(i);
				if((String)beanTask.get("INTERVAL_DAYS") != null){
					intervalDays = beanTask.get("INTERVAL_DAYS").toString();
				}
				if (beanTask.get("TASK_ID") != null){
					taskId = beanTask.get("TASK_ID").toString();
				}
				taskConPo.setTaskId(Long.valueOf(taskId));
				taskPo=TrackingTaskPO.findFirst("TASK_ID=?",  new Object[]{taskId});
				taskPo.setDate("CR_EXECUTE_DATE",Utility.getCurrentDateTime());
				taskPo.saveIt();
				
				customerList = selectCrcCustomer( intervalDays, today);
				for (int j = 0; j < customerList.size(); j ++){
					beanCustomer =  customerList.get(j);
					if ((Integer)beanCustomer.get("CUSTOMER_NO") != null)
						cusNo = beanCustomer.get("CUSTOMER_NO").toString();
					if (intervalDays != null && !"".equals(intervalDays)
							&& !intervalDays.equals("0"))
					{
						c.setTime(((CustomerPO) beanCustomer).getDate("CREATE_DATE"));
						c.add(c.DAY_OF_WEEK, Integer.valueOf(intervalDays));
						salcrpo.setDate("SCHEDULE_DATE",Utility.getTimeStamp(f1.format(c.getTime()).toString()));
					}
					salcrpo.setString("CREATE_TYPE",Utility.getInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
					if ((String)beanTask.get("TASK_CONTENT") != null && !"".equals(beanTask.get("TASK_CONTENT").toString()))
					{
						salcrpo.setString("CR_CONTEXT",beanTask.get("TASK_CONTENT").toString());//关怀内容
					}
					if ((String)beanTask.get("EXECUTE_TYPE") != null && !"".equals(beanTask.get("EXECUTE_TYPE").toString()))
					{
						salcrpo.setString("CR_TYPE",Integer.valueOf(beanTask.get("EXECUTE_TYPE").toString()));//关怀方式
					}
					if ((String)beanTask.get("TASK_NAME") != null && !"".equals(beanTask.get("TASK_NAME").toString()))
					{
						salcrpo.setString("CR_NAME",beanTask.get("TASK_NAME").toString());//关怀名称
					}
					
					//联系人
					TtCustomerLinkmanInfoDTO customer = new TtCustomerLinkmanInfoDTO();
					TtCustomerLinkmanInfoPO customer2 = new TtCustomerLinkmanInfoPO();
					List listcustomer=TtCustomerLinkmanInfoPO.findBySQL("select *  from tt_customer_linkman_info where customer_no=? and d_key=?", new Object[]{cusNo,CommonConstants.D_KEY});
					if (listcustomer != null && listcustomer.size() > 0)
					{
						for (int t = 0; t < listcustomer.size(); t++)
						{
							customer2 = (TtCustomerLinkmanInfoPO) listcustomer.get(0);

							if (customer2.getString("PHONE") != null && !"".equals(customer2.getString("PHONE")))
							{
								salcrpo.setString("LINK_PHONE",customer2.getString("PHONE"));
							}
							if (customer2.getString("MOBILE") != null && !"".equals(customer2.getString("MOBILE")))
							{
								salcrpo.setString("LINK_MOBILE",customer2.getString("MOBILE"));
							}
							if (customer2.getString("CONTACTOR_NAME") != null
									&& !"".equals(customer2.getString("CONTACTOR_NAME")))
							{
								salcrpo.setString("CR_LINKER",customer2.getString("CONTACTOR_NAME"));
							}
						}
					}
					
					if ((Integer)beanCustomer.get("VIN") != null && !"".equals(beanCustomer.get("VIN").toString()))
					{
						salcrpo.setString("VIN",beanCustomer.get("VIN").toString());
					}
					if ((Long)beanCustomer.get("SOLD_BY") != null && !"".equals(beanCustomer.get("SOLD_BY").toString()))
					{
						salcrpo.setLong("SOLD_BY",Long.valueOf(beanCustomer.get("SOLD_BY").toString()));
					}
					if ((Long)beanCustomer.get("OWNED_BY") != null && !"".equals(beanCustomer.get("OWNED_BY").toString()))
					{
						salcrpo.setLong("OWNED_BY",Long.valueOf(beanCustomer.get("OWNED_BY").toString()));
					}
					
					//更新保有客户关怀前先校验潜客与保有客户的关系表中退车标记是否已退车,未退车才做插入更新 modify by lj 
					TtPoCusRelationDTO rePo = new TtPoCusRelationDTO();
					rePo.setCustomerNo(cusNo);
					rePo.setVehiReturn(Utility.getInt(DictCodeConstants.DICT_IS_NO));
					rePo.setdKey(CommonConstants.D_KEY);	
					List<TtPoCusRelationPO> ListRe = TtPoCusRelationPO.findBySQL("select *  from tt_po_cus_relation where CUSTOMER_NO=? AND VEHI_RETURN=? AND D_KEY=?", new Object[]{cusNo,Utility.getInt(DictCodeConstants.DICT_IS_NO),CommonConstants.D_KEY});
					
					if (ListRe != null && ListRe.size() > 0){
						
						if (beanTask.get("CR_EXECUTE_DATE") == null){
							if(checkCustomer( cusNo, taskId).size() <= 0){
								salcrConPo.setCreatedBy(userID);
								salcrConPo.setCreatedAt(Utility.getCurrentDateTime());
								List<TtSalesCrPO> lists = TtSalesCrPO.findBySQL("select item_Id from tt_sales_cr where CREATED_BY=? AND CREATEDAT=?", new Object[]{userID,Utility.getCurrentDateTime()});
									for(int s=0;s<lists.size();s++){
										Map po=list.get(s);
										salcrpo.setInteger("ITEM_ID",(Integer)po.get("ITEM_ID"));
									}
								salcrpo.setLong("TASK_ID",Long.valueOf(taskId));
								salcrpo.setString("CUSTOMER_NO",cusNo);
								salcrpo.saveIt();
							}
						}else if ((Date)beanTask.get("CR_EXECUTE_DATE") != null && !"".equals(beanTask.get("CR_EXECUTE_DATE").toString())){
							list = checkCustomer( cusNo, taskId);
							if(list.size() > 0){
								bean =  list.get(0);
								salcrConPo.setUpdatedAt(Utility.getCurrentDateTime());
								salcrConPo.setItemId(Long.valueOf(bean.get("ITEM_ID").toString()));
								salcrConPo.setCustomerNo(cusNo);
								salcrConPo.setdKey(0);
								salcrConPo.setCrResult(null);
								updateCrcTask(salcrpo,salcrConPo );
							}
							else{
								salcrConPo.setCreatedBy(userID);
								salcrConPo.setCreatedAt(Utility.getCurrentDateTime());
								List<TtSalesCrPO> lists = TtSalesCrPO.findBySQL("select item_Id from tt_sales_cr where CREATED_BY=? AND CREATEDAT=?", new Object[]{userID,Utility.getCurrentDateTime()});
								for(int s=0;s<lists.size();s++){
									Map po=list.get(s);
									salcrpo.setInteger("ITEM_ID",(Integer)po.get("ITEM_ID"));
								}
								
								salcrpo.setLong("TASK_ID",Long.valueOf(taskId));
								salcrpo.setString("CUSTOMER_NO",cusNo);
								salcrpo.saveIt();
							}
						}	
					}
					
				}
				
			}
			return 1;	
		}catch(Exception ex){
			return 0;
		}
		
		
	
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public List selectCrcTask() throws ServiceBizException {
		
		StringBuffer sql=new StringBuffer("");
		List list=new ArrayList();
		
			sql.append(" SELECT * FROM TM_TRACKING_TASK WHERE CUSTOMER_STATUS = 13211002 AND IS_VALID = 12781001  "); 
			sql.append(	" AND (CR_EXECUTE_DATE IS NULL OR UPDATE_DATE > CR_EXECUTE_DATE) ");
			 List<Map> rsList = Base.findAll(sql.toString());
			
			
		return rsList;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public List selectCrcCustomer(String intervalDays, String today) throws ServiceBizException {
		
		StringBuffer sql=new StringBuffer("");
		List list=new ArrayList();
		
			sql.append(" SELECT A.CUSTOMER_NO, B.VIN, A.SOLD_BY, A.OWNED_BY,A.CREATE_DATE FROM ");
			sql.append(" TM_CUSTOMER A LEFT JOIN TM_VEHICLE B ON A.ENTITY_CODE = B.ENTITY_CODE AND A.CUSTOMER_NO = ");
			sql.append(" B.CUSTOMER_NO WHERE  A.CREATE_DATE + "+intervalDays+" DAY > '"+today+"'");
			 List<Map> rsList = Base.findAll(sql.toString());
				
				
				return rsList;
		
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public List checkCustomer(String customerNo, String taskId) throws ServiceBizException {
		StringBuffer sql=new StringBuffer("");
		List list=new ArrayList();
		
			sql.append(" SELECT * FROM TT_SALES_CR WHERE  CUSTOMER_NO = '"+customerNo+"' AND TASK_ID = "+taskId);
			List<Map> rsList = Base.findAll(sql.toString());
			
			
			return rsList;
	}
	
	@Override
	public void updateCrcTask(TtSalesCrPO salcrConPo, TtSalesCrDTO salcrpo) throws ServiceBizException {
		StringBuffer sql = new StringBuffer("");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
		sql.append("UPDATE TT_SALES_CR SET D_KEY=D_KEY");
		if (salcrpo.getScheduleDate() != null)
			sql.append(" ,Schedule_Date= '"+f.format(salcrpo.getScheduleDate())+"'");
		if (salcrpo.getCrContext() != null)
			sql.append(" ,Cr_Context= '"+salcrpo.getCrContext()+"'");
		if (salcrpo.getCrName() != null)
			sql.append(" ,Cr_Name= '"+salcrpo.getCrName()+"'");
		if (salcrpo.getCrType() != null)
			sql.append(" ,Cr_Type= "+salcrpo.getCrType());
		sql.append(" Where 1=1");
		
		if (salcrConPo.getInteger("D_KEY") != null)
			sql.append(" AND D_Key= "+salcrConPo.getInteger("D_KEY"));
		if (salcrConPo.getLong("ITEM_ID") != null)
			sql.append(" AND Item_Id= "+salcrConPo.getLong("ITEM_ID"));
		sql.append(" AND CR_RESULT IS NULL");
		
		Base.exec(sql.toString());
		
	}


	

}
