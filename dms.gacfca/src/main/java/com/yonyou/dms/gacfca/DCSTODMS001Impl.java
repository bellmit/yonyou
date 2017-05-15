package com.yonyou.dms.gacfca;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.DCSTODMS001DTO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TmEntityPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 下发撞单的信息
 * @author Benzc
 * @date 2017年4月20日
 */
@Service
public class DCSTODMS001Impl implements DCSTODMS001{
	
	final Logger logger = Logger.getLogger(DCSTODMS001Impl.class);

	@SuppressWarnings("rawtypes")
	@Override
	public LinkedList<DCSTODMS001DTO> getDCSTODMS001(LinkedList dtoList,String dealerCode) throws ServiceBizException {
		LinkedList<DCSTODMS001DTO> resultList=new LinkedList<DCSTODMS001DTO>();
		//判断是否为空,循环操作，根据业务相应的修改数据
		if(dtoList != null && dtoList.size() > 0){
			logger.debug("-------------------------DCSTODMS001+撞单校验开始----------------");
			for(int i=0;i<dtoList.size();i++){
				DCSTODMS001DTO dto = new DCSTODMS001DTO();
				dto = (DCSTODMS001DTO) dtoList.get(i);
				//经销商代码为空校验
				if(!StringUtils.isNullOrEmpty(dealerCode)){
					if(!StringUtils.isNullOrEmpty(dto.getDealerCode())){
						//手机号码为空校验
						if(!StringUtils.isNullOrEmpty(dto.getTel())){
							//TmEntityPO tePO = new TmEntityPO();
                            List teList = TmEntityPO.findBySQL("SELECT * FROM TM_ENTITY WHERE DEALER_CODE=?", dto.getDealerCode());
                            if(teList != null && teList.size()>0){
                            	//撞单校验
                            	StringBuffer sql=new StringBuffer();
                            	sql.append("select * from Tm_Potential_Customer where ( Contactor_Mobile='"+dto.getTel()+"' or Contactor_Phone='"+dto.getTel()+"')"				
                        				+" and DEALER_CODE='"+dealerCode+"' AND D_KEY="+CommonConstants.D_KEY +"  ");
                        		List<Map> tplist = DAOUtil.findAll(sql.toString(),null);
                        		if(tplist.size()>0){
                        			dto.setIsHitSingle(DictCodeConstants.STATUS_IS_YES);
                        			//如果撞单的话，取销售顾问的名字和手机号（不是必填项）	
//			      					通过潜客信息，查找到销售顾问的id
                        			PotentialCusPO po= (PotentialCusPO) tplist.get(0);
                        			Long soldById = po.getLong("SOLD_BY");
                        			String soldByIdStr = soldById+"";
                        			if(!StringUtils.isNullOrEmpty(soldById) && soldByIdStr.length() == 14){
                        				//获取销售顾问的姓名和手机号
                        				StringBuffer sb = new StringBuffer();
                        				sb.append("select a.EMPLOYEE_NAME,b.user_code,a.MOBILE from tm_employee a left join tm_user b on a.dealer_code=b.dealer_code and a.EMPLOYEE_NO=b.EMPLOYEE_NO where "				
                        						+" a.DEALER_CODE='"+dto.getDealerCode()+"' AND b.user_id= "+soldById+" with ur ");
                        				List<Map> employs = DAOUtil.findAll(sb.toString(),null);
                        				if(employs.size()>0){
                        					Map bean =employs.get(0);
                        					if(!StringUtils.isNullOrEmpty(bean.get("USER_CODE"))){
                        						String userCode = (String) bean.get("USER_CODE");
                        						if(!"9997".equals(userCode)&&!"9998".equals(userCode)&&"9999".equals(userCode)){
                        							if(!StringUtils.isNullOrEmpty(bean.get("EMPLOYEE_NAME"))){
                        								dto.setSoldBy((String) bean.get("EMPLOYEE_NAME"));
                        								if(!StringUtils.isNullOrEmpty(bean.get("MOBILE"))){
                        									dto.setSoldMobile((String) bean.get("MOBILE"));
                        								}
                        							}
                        						}
                        					}
                        				}
                        			}
                        			resultList.add(dto);
                        		}else{
                        			dto.setIsHitSingle(DictCodeConstants.STATUS_IS_NOT);
                        			resultList.add(dto);
                        		}
                            }else{
                            	dto.setMessage("经销商代码（dealerCode）："+dto.getDealerCode());
                            	resultList.add(dto);
                            }
						}else{
							dto.setMessage("手机号码（customerPhone）空异常");
							resultList.add(dto);
						}
					}else{
						dto.setMessage("经销商（dealerCode）空异常");
						resultList.add(dto);
					}
				}else{
					dto.setMessage("经销商（dealerCode）空异常");
					resultList.add(dto);
				}
			}
			logger.debug("-------------------------DCSTODMS001+撞单校验结束，开始上报----------------");
		}
		return resultList;
	}


}
