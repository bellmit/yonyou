package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.ProperServManInfoDTO;
import com.yonyou.dms.DTO.gacfca.SADMS019DTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.EmployeePo;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 将符合条件的客户经理上传至微信平台 (特定字段发生变动则上传)
 * @author Administrator
 *
 */
@Service
public class SADMS019CoudImpl implements SADMS019Coud {
	final Logger logger = Logger.getLogger(SADMS019CoudImpl.class);

	@Override
	public LinkedList<ProperServManInfoDTO> getSADMS019(SADMS019DTO sadms019DTO) {
		logger.info("==========SADMS019Impl执行===========");
		try{
			if (sadms019DTO.getUpdateStatus() == null) {
				logger.debug("sadms019DTO.updateStatus 为空,方法中断");
				return null;
			}
			LinkedList<ProperServManInfoDTO> resultList=new LinkedList<ProperServManInfoDTO>();
			for (int i = 0; i < sadms019DTO.getUpdateStatus().length; i++) {
				switch(sadms019DTO.getUpdateStatus()[i].trim()){
				case "A":	//调用新增方法
					resultList.add(addProperServManInfoDTO(sadms019DTO,i));
					break;
				case "B":	//调用更新方法
					resultList.add(updateProperServManInfoDTO(sadms019DTO,i));
					break;
				}
			}
			return resultList;
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e);
			return null;
		}finally{
			logger.info("==========SADMS019Impl结束===========");
		}

	}

	/**
	 * @description 新增 如果是客服经理 并且启用 并且有手机或电话
	 * @param sadms019DTO
	 * @param index
	 * @return
	 * @throws Exception 
	 */
	private ProperServManInfoDTO addProperServManInfoDTO(SADMS019DTO sadms019DTO,Integer index) throws Exception{
		ProperServManInfoDTO vo=null;
		if (Utility.testString(sadms019DTO.getEmployeeNo()[index]) 
				&& (Utility.testString(sadms019DTO.getMobile()[index])|| Utility.testString(sadms019DTO.getPhone()[index]))
				&& CommonConstants.DICT_IS_YES.equals(sadms019DTO.getIsServiceAdvisor()[index]) 
				&& CommonConstants.DICT_IS_YES.equals(sadms019DTO.getIsValid()[index])){

			vo= new ProperServManInfoDTO();
			vo.setEmployeeName(sadms019DTO.getEmployeeName()[index]);
			vo.setDealerCode(sadms019DTO.getDealerCode());
			vo.setIsServiceAdvisor(Utility.getInt(CommonConstants.DICT_IS_YES));
			vo.setIsValid(Utility.getInt(CommonConstants.DICT_IS_YES));
			vo.setServiceAdviser(sadms019DTO.getEmployeeNo()[index]);
			vo.setIsDefaultWxSa(Utility.getInt(sadms019DTO.getIsDefaultManager()[index]));
			vo.setEmployeeStatus("A");
			if (Utility.testString(sadms019DTO.getMobile()[index])){
				vo.setMobile(sadms019DTO.getMobile()[index]);
			} else {
				vo.setMobile(sadms019DTO.getPhone()[index]);	
			}
		}
		return vo;
	}

	/**
	 * @description 修改 如果 这几个主要字段发生变化 则上传 是否启用前台不会改，是离职的action改的
	 * @param sadms019DTO
	 * @param index
	 * @return
	 * @throws Exception
	 */
	private ProperServManInfoDTO updateProperServManInfoDTO(SADMS019DTO sadms019DTO,Integer index) throws Exception{
		ProperServManInfoDTO properServManInfoDTO = null;
		if (Utility.testString(sadms019DTO.getEmployeeNo()[index])){
			logger.debug("from EmployeePo DEALER_CODE = "+sadms019DTO.getDealerCode()+" and EMPLOYEE_NO = "+sadms019DTO.getEmployeeNo()[index]+" and IS_SERVICE_ADVISOR = "+Utility.getInt(sadms019DTO.getIsServiceAdvisor()[index]));
			LazyList<EmployeePo> employeePos = EmployeePo.findBySQL("DEALER_CODE = ? and EMPLOYEE_NO = ? and IS_SERVICE_ADVISOR = ?", 
					sadms019DTO.getDealerCode(),sadms019DTO.getEmployeeNo()[index],Utility.getInt(sadms019DTO.getIsServiceAdvisor()[index]));
			//查不出数据 就是是否客户经理字段改过了
			if (employeePos==null || employeePos.isEmpty() && CommonConstants.DICT_IS_YES.equals(sadms019DTO.getIsValid()[index])){
				properServManInfoDTO= new ProperServManInfoDTO();
				properServManInfoDTO.setEmployeeName(sadms019DTO.getEmployeeName()[index]);
				properServManInfoDTO.setDealerCode(sadms019DTO.getDealerCode());
				if (CommonConstants.DICT_IS_YES.equals(sadms019DTO.getIsServiceAdvisor()[index])){
					properServManInfoDTO.setIsServiceAdvisor(Utility.getInt(CommonConstants.DICT_IS_YES));
				}else {
					properServManInfoDTO.setIsServiceAdvisor(Utility.getInt(CommonConstants.DICT_IS_NO));
				}
				properServManInfoDTO.setIsValid(Utility.getInt(CommonConstants.DICT_IS_YES));
				properServManInfoDTO.setServiceAdviser(sadms019DTO.getEmployeeNo()[index]);
				properServManInfoDTO.setIsDefaultWxSa(Utility.getInt(sadms019DTO.getIsDefaultManager()[index]));
				if (Utility.testString(sadms019DTO.getMobile()[index])){
					properServManInfoDTO.setMobile(sadms019DTO.getMobile()[index]);
				} else {
					properServManInfoDTO.setMobile(sadms019DTO.getPhone()[index]);	
				}
				properServManInfoDTO.setEmployeeStatus("U");
				return properServManInfoDTO;
			} else if (CommonConstants.DICT_IS_YES.equals(sadms019DTO.getIsValid()[index]) && CommonConstants.DICT_IS_YES.equals(sadms019DTO.getIsServiceAdvisor()[index])){
				//能查出数据 并且是客户经理 再看有没有其他字段改变 无效或者不是客服的就没意义了
				int flag=0;//如果变成1  就表示上传重要信息字段变过了
				properServManInfoDTO= new ProperServManInfoDTO();
				EmployeePo employeePo = employeePos.get(0);
				properServManInfoDTO.setEmployeeName(sadms019DTO.getEmployeeName()[index]);
				properServManInfoDTO.setDealerCode(sadms019DTO.getDealerCode());
				properServManInfoDTO.setIsServiceAdvisor(Utility.getInt(CommonConstants.DICT_IS_YES));
				properServManInfoDTO.setIsValid(Utility.getInt(CommonConstants.DICT_IS_YES));
				properServManInfoDTO.setServiceAdviser(sadms019DTO.getEmployeeNo()[index]);
				properServManInfoDTO.setIsDefaultWxSa(Utility.getInt(sadms019DTO.getIsDefaultManager()[index]));
				properServManInfoDTO.setEmployeeStatus("U");
				if (!employeePo.getString("EMPLOYEE_NAME").equals(sadms019DTO.getEmployeeName()[index]) ){
					flag=1;
					if (Utility.testString(sadms019DTO.getMobile()[index])){
						properServManInfoDTO.setMobile(sadms019DTO.getMobile()[index]);
					} else {
						properServManInfoDTO.setMobile(sadms019DTO.getPhone()[index]);	
					}
				}
				if (Utility.testString(sadms019DTO.getMobile()[index]) 
						&& (employeePo.getString("MOBILE")==null || !employeePo.getString("MOBILE").equals(sadms019DTO.getMobile()[index]))){
					flag=1;
					properServManInfoDTO.setMobile(sadms019DTO.getMobile()[index]);
				} 
				if (!Utility.testString(sadms019DTO.getMobile()[index]) && Utility.testString(sadms019DTO.getPhone()[index])
						&& (employeePo.getString("PHONE")==null || !employeePo.getString("PHONE").equals(sadms019DTO.getPhone()[index]) || Utility.testString(employeePo.getString("MOBILE")))){
					flag=1;
					properServManInfoDTO.setMobile(sadms019DTO.getPhone()[index]);	
				}
				//判断是否默认微信绑定客户经理是否修改
				if(Utility.getInt(sadms019DTO.getIsDefaultManager()[index]) != employeePo.getInteger("IS_DEFAULT_MANAGER")){
					flag = 1;
					if (Utility.testString(sadms019DTO.getMobile()[index])){
						properServManInfoDTO.setMobile(sadms019DTO.getMobile()[index]);
					} else {
						properServManInfoDTO.setMobile(sadms019DTO.getMobile()[index]);	
					}
				}
				if (flag==1){
					return properServManInfoDTO;
				}
			}
		}
		return null;
	}

}
