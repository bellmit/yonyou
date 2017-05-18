package com.yonyou.dms.gacfca;
									
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS018Cloud;
import com.yonyou.dms.DTO.gacfca.ProperServiceManageDto;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.EmployeePo;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class SEDMS015CoudImpl  implements SEDMS015Coud{
		int clock = 50;//50条一发;
		 
		 @Autowired
		 SADCS018Cloud sadcs018Cloud;
		 
		@SuppressWarnings({ "unused", "rawtypes" })
		@Override
		public int getSEDMS015(String serviceAdvisor,String[] vin,String[] isSelected ) throws ServiceBizException {
			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			String msg="1";
			try{
				if(serviceAdvisor==null || serviceAdvisor==""){
					return 1;
				}
				
				List<ProperServiceManageDto> resultList=new LinkedList<ProperServiceManageDto>();;
				/*EmployeePo employeePO =new EmployeePo();
				employeePO.setString("DEALER_CODE", dealerCode);
				employeePO.setString("EMPLOYEE_NO", serviceAdvisor);*/
				List<Map> list=Base.findAll("SELECT *  FROM TM_EMPLOYEE WHERE DEALER_CODE=? AND EMPLOYEE_NO=?",new Object[]{dealerCode,serviceAdvisor});
				Map employeePO=list.get(0);
				ProperServiceManageDto vo=null; 
				
				for (int i = 1; i <= vin.length; i++){
					//循环之后resultList初始化
					if (isSelected[i-1].equals(DictCodeConstants.DICT_IS_YES)){
						vo= new ProperServiceManageDto();
						vo.setDealerCode(dealerCode);
						vo.setServiceAdviser(serviceAdvisor);
						vo.setEmployeeName((String)employeePO.get("EMPLOYEE_NAME")+"");
						if(!StringUtils.isNullOrEmpty((String)employeePO.get("MOBILE"))){
							vo.setMobile((String)employeePO.get("MOBILE")+"");
						}else if(!StringUtils.isNullOrEmpty((String)employeePO.get("PHONE"))){
							vo.setMobile((String)employeePO.get("PHONE"));
						}
						System.err.println(vin[i-1]);
						List<Map> list1 = Base.findAll(" select tm.* from tm_vehicle tv inner join tt_sales_order tt on tv.dealer_code = tt.dealer_code  and tv.vin = tt.vin inner join tm_potential_customer tm on tt.dealer_code=tm.dealer_code and tt.customer_no=tm.customer_no where tv.dealer_code=? and tv.vin=? and tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075)", new Object[]{dealerCode,vin[i-1]});
						if(list1!=null&&list1.size()>0){
							Map po = list1.get(0);
							if(!StringUtils.isNullOrEmpty(po)){
								vo.setDmsOwnerId((String)po.get("CUSTOMER_NO")+"");	
								vo.setName((String)po.get("CUSTOMER_NAME")+"");
								if(!StringUtils.isNullOrEmpty((String)po.get("CONTACTOR_MOBILE"))){
									vo.setCellphone((String)po.get("CONTACTOR_MOBILE")+"");							
								}else if(!StringUtils.isNullOrEmpty((String)po.get("CONTACTOR_PHONE"))){
									vo.setCellphone((String)po.get("CONTACTOR_PHONE")+"");
								}
							}else{//对于不是本店销售的车辆 来本店维修时绑定 传车主信息
								
								List<Map> list2 = Base.findAll("select ow.* from tm_vehicle tv inner join tm_owner ow on tv.DEALER_CODE=ow.DEALER_CODE and tv.owner_no=ow.owner_no  WHERE tv.DEALER_CODE = ? AND tv.VIN = ?", new Object[]{dealerCode,vin[i-1]});
								Map owner = list2.get(0);
								if(owner!=null){
									vo.setDmsOwnerId((String)owner.get("OWNER_NO")+"");
									vo.setName((String)owner.get("OWNER_NAME")+"");
									if(!StringUtils.isNullOrEmpty((String)owner.get("MOBILE"))){
										vo.setCellphone(owner.get("MOBILE")+"");							
									}else if(!StringUtils.isNullOrEmpty((String)owner.get("PHONE"))){
										vo.setCellphone(owner.get("PHONE")+"");
									}
									
								}
							}
						}
						
						vo.setDispatchTime(Utility.getCurrentDateTime().toString());
						
						//将所有选中的VIN上报至DCS 
						vo.setVin(vin[i-1]);
						resultList.add(vo);
						
		                if(canSubmit(i,clock,vin.length)) {
		                	//上报resultList
		                	sadcs018Cloud.handleExecutor(resultList);
		                	resultList=new LinkedList<ProperServiceManageDto>();
						}			
					}
				}
				return 1;
				
			}catch (Exception e) {
				return 0;
		    }
		}
		
		//下发50条控制
		 private boolean canSubmit(int i, int clock, int size) {
		   		if(size < clock) {
		   			if(i == size){
		   				return true;
		   				}
		   			else{
		   				return false;
		   			}
		   		} 
		   		else  {
		   			if (((i % clock) == 0) || (i == size)){
		   				return true;
		   			} else {
		   				return false;
		   			}
		   		}
		   	}	
	}
