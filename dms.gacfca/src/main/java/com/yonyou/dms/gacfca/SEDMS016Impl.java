package com.yonyou.dms.gacfca;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.ProperServManInfoDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.EmployeePo;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 将符合条件的客户经理上传至微信平台
 * @author Administrator
 *
 */
@Service
public class SEDMS016Impl implements SEDMS016 {

	final Logger logger = Logger.getLogger(SEDMS016Impl.class);

	@Override
	public List<ProperServManInfoDTO> getSEDMS016(String dealerCode, long userId) {
		logger.info("==========SEDMS016Impl执行===========");
		try{
			List<ProperServManInfoDTO> resultList=new LinkedList<ProperServManInfoDTO>();
			//查询出所有手机/电话不为空的客户经理
			List<Map> list = queryAllServiceAdvisor(dealerCode);
			if(list!=null && list.size()>0){
				EmployeePo employeePo=null;
				ProperServManInfoDTO properServManInfoDTO=null;
				for(Map<String,Object> map : list){
					employeePo=new EmployeePo();
					properServManInfoDTO=new ProperServManInfoDTO();

					properServManInfoDTO.setDealerCode(dealerCode);
					properServManInfoDTO.setServiceAdviser(employeePo.get("EMPLOYEE_NO").toString());
					properServManInfoDTO.setEmployeeName(employeePo.get("EMPLOYEE_NAME").toString());
					if(Utility.testString(employeePo.get("MOBILE").toString())){
						properServManInfoDTO.setMobile(employeePo.get("MOBILE").toString());
					}else if(Utility.testString(employeePo.get("PHONE").toString())){
						properServManInfoDTO.setMobile(employeePo.get("PHONE").toString());
					}
					resultList.add(properServManInfoDTO);		
					//将发送至微信平台的员工信息的上传标识更新为是
					logger.debug("update EmployeePo set IS_UPLOAD = "+Utility.getInt(CommonConstants.DICT_IS_YES)+",UPDATE_BY = "+userId+", UPDATE_AT ="+Utility.getCurrentTimestamp()+" where DEALER_CODE = "+dealerCode+" and EMPLOYEE_NO = "+employeePo.getString("EmployeeNo"));
					EmployeePo.update("IS_UPLOAD = ?,UPDATE_BY = ?, UPDATE_AT =?", 
							"DEALER_CODE = ? and EMPLOYEE_NO = ?", 
							Utility.getInt(CommonConstants.DICT_IS_YES),userId,Utility.getCurrentTimestamp(),dealerCode,employeePo.getString("EmployeeNo"));
				}
			}
			return resultList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			logger.info("==========SEDMS016Impl结束===========");
		}
	}

	/**
	 * @description 查询所有手机号/电话不为空的经理
	 * @param entityCode
	 * @return
	 */
	private List<Map> queryAllServiceAdvisor(String entityCode){
		String sql = " SELECT  * from TM_EMPLOYEE where entity_code='"+entityCode+"' " +
				" and is_valid="+CommonConstants.DICT_IS_YES+" and ( (MOBILE is not null AND  MOBILE<>'') or (PHONE is not null  AND PHONE <> '')) " +
				" and IS_SERVICE_ADVISOR = "+CommonConstants.DICT_IS_YES+"  ";

		logger.debug(sql);
		return DAOUtil.findAll(sql, null);

	}
}
