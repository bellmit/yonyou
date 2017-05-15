package com.yonyou.dms.manage.service.basedata.organization;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.SysUserPosePO;
import com.yonyou.dms.common.domains.PO.basedata.TcPosePO;
import com.yonyou.dms.common.domains.PO.basedata.TcRolePO;
import com.yonyou.dms.common.domains.PO.basedata.TcUserPO;
import com.yonyou.dms.common.domains.PO.basedata.TmCompanyPO;
import com.yonyou.dms.common.domains.PO.basedata.TmOrgPO;
import com.yonyou.dms.common.domains.PO.basedata.TrRoleFuncPO;
import com.yonyou.dms.common.domains.PO.basedata.TrRolePosePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.security.MD5Util;
import com.yonyou.dms.manage.dao.organization.CarCompanyMaintainDao;
import com.yonyou.dms.manage.domains.DTO.basedata.organization.CarCompanyDTO;

@Service
public class CarCompanyMaintainServiceImpl implements CarCompanyMaintainService {
	
	@Autowired
	private CarCompanyMaintainDao carDao;

	@Override
	public PageInfoDto searchCompanyInfo(Map<String, String> param) {
		PageInfoDto dto = carDao.searchCompanyInfo(param);
		return dto;
	}

	@Override
	public void addCarCompany(CarCompanyDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date currTime = new Date(System.currentTimeMillis());
		Long userId = loginInfo.getUserId();
		boolean flag = false;
		try {
			//公司信息
			Long oemCompanyId=loginInfo.getCompanyId();
			TmCompanyPO comPo = new TmCompanyPO();
			comPo.setString("COMPANY_NAME", dto.getCompanyName());
			comPo.setString("COMPANY_CODE", dto.getCompanyCode());
			comPo.setString("COMPANY_SHORTNAME", dto.getCompanyShortName());
			comPo.setInteger("COMPANY_TYPE", dto.getCompanyType());
			if(dto.getProvince() != null){			
				comPo.setLong("PROVINCE_ID", dto.getProvince());
			}
			if(dto.getCity() != null){			
				comPo.setLong("CITY_ID", dto.getCity());
			}
			comPo.setString("PHONE", dto.getPhone());
			comPo.setString("ZIP_CODE", dto.getZipCode());
			comPo.setString("FAX", dto.getFax());
			comPo.setString("ADDRESS", dto.getAddress());
			comPo.setInteger("STATUS", dto.getStatus());
			comPo.setLong("CREATE_BY", userId);
			comPo.setTimestamp("CREATE_DATE", currTime);
			flag = comPo.saveIt();
			if(!flag){
				throw new ServiceBizException("数据库插入数据失败！");
			}
			Long companyId = (Long) comPo.getId();
			comPo.setLong("OEM_COMPANY_ID", companyId);
			flag = comPo.saveIt();
			if(!flag){
				throw new ServiceBizException("数据库插入数据失败！");
			}
			
			//组织信息
			String treeCode = "";
			List<Map> list = carDao.getOrgTreeCode();	
			if(list!= null && list.size() > 0){
				treeCode = String.valueOf( list.get(0).get("NEW_TREECODE"));
			}
			TmOrgPO orgPo = new TmOrgPO();
			orgPo.setString("ORG_CODE", dto.getOrgCode());
			orgPo.setString("ORG_NAME", dto.getOrgName());
			orgPo.setLong("CREATE_BY", userId);
			orgPo.setTimestamp("CREATE_DATE", currTime);
			orgPo.setInteger("DUTY_TYPE", OemDictCodeConstants.DUTY_TYPE_COMPANY);
			orgPo.setLong("COMPANY_ID", companyId);
			orgPo.setInteger("ORG_LEVEL", new Integer(1));
			orgPo.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);
			orgPo.setLong("PARENT_ORG_ID", new Long(-1));
			orgPo.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
			orgPo.setString("TREE_CODE", treeCode);
			flag = orgPo.saveIt();
			if(!flag){
				throw new ServiceBizException("数据库插入数据失败！");
			}
			Long orgId = (Long) orgPo.getId();
			
			//用户信息
			TcUserPO userPo = new TcUserPO();
			String userName = dto.getUserName();
			if(StringUtils.isNotBlank(userName)){
				userName = userName.toLowerCase();
			}
			userPo.setString("ACNT", userName);
			userPo.setString("NAME", userName);
			userPo.setLong("COMPANY_ID", companyId);
			userPo.setInteger("USER_STATUS", OemDictCodeConstants.STATUS_ENABLE);
			if(StringUtils.isNotBlank(dto.getPassword())){
				userPo.setString("PASSWORD", MD5Util.getEncryptedPwd(dto.getPassword()));
			}
			flag = userPo.saveIt();
			if(!flag){
				throw new ServiceBizException("数据库插入数据失败！");
			}
			Long newUserId = (Long) userPo.getId();
			
			//插入角色，职位及对应关系
			List<Map> roleList = carDao.getroleList(companyId);
			Long poseId;
			Long roleId;
			if(roleList != null && roleList.size() > 0){
				Map<String, Object> map1 = roleList.get(0);
				String roleIdTmp = String.valueOf(map1.get("ROLE_ID"));
				roleId=Long.parseLong(roleIdTmp);
				List<Map> poseList = carDao.getposeList(roleId);
				if(poseList != null && poseList.size()>0)
				{
					Map<String, Object> map2=poseList.get(0);
					String poseIdTmp = String.valueOf(map2.get("POSE_ID"));
					poseId = Long.parseLong(poseIdTmp);
				}else
				{
					poseId=insetPoseAndRelation(companyId,userId,orgId,currTime,roleId);	
				}
			}else{
				TcRolePO rolePo = new TcRolePO();
				rolePo.setLong("CREATE_BY", userId);
				rolePo.setTimestamp("CREATE_DATE", currTime);
				rolePo.setString("ROLE_DESC", "系统自动创建的角色");
				rolePo.setString("ROLE_NAME", "超级用户");
				rolePo.setInteger("ROLE_STATUS", OemDictCodeConstants.STATUS_ENABLE);
				rolePo.setInteger("ROLE_TYPE", OemDictCodeConstants.SYS_USER_OEM);
				rolePo.setLong("OEM_COMPANY_ID", companyId);
				flag = rolePo.saveIt();
				if(!flag){
					throw new ServiceBizException("数据库插入数据失败！");
				}
				roleId = (Long) rolePo.getId();
				List<Map> funcList = carDao.getfuncList();
				if(funcList != null && list.size() > 0){
					for(int i = 0 ;i < funcList.size(); i++){
						TrRoleFuncPO trfPo = new TrRoleFuncPO();
						Map<String,Object> map3 = funcList.get(i);
						String funcIdTmp = String.valueOf(map3.get("FUNC_ID"));
						Long funcId = Long.parseLong(funcIdTmp);
						trfPo.setLong("FUNC_ID", funcId);
						trfPo.setLong("ROLE_ID", roleId);
						trfPo.setLong("CREATE_BY", userId);
						trfPo.setTimestamp("CREATE_DATE", currTime);
						flag = trfPo.saveIt();
						if(!flag){
							throw new ServiceBizException("数据库插入数据失败！");
						}
					}
				}
				poseId=insetPoseAndRelation(companyId,userId,orgId,currTime,roleId);
			}
			//插入用户与职位关联表
			SysUserPosePO usPo = new SysUserPosePO();
			usPo.setLong("POSE_ID", poseId);
			usPo.setLong("USER_ID", newUserId);
			usPo.setLong("CREATE_BY", userId);
			usPo.setTimestamp("CREATE_DATE", currTime);
			flag = usPo.saveIt();
			if(!flag){
				throw new ServiceBizException("数据库插入数据失败！");
			}
			carDao.insertBusinessPara(userId, oemCompanyId, companyId);
			carDao.insertVariablePara(userId, oemCompanyId, companyId);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException("数据库插入数据失败！");
		}
	}

	private Long insetPoseAndRelation(Long companyId, Long userId, Long orgId, Date currTime, Long roleId) {
		boolean flag = false;
		//插入职位
		TcPosePO po = new TcPosePO();
		po.setLong("COMPANY_ID", companyId);
		po.setLong("ORG_ID", orgId);
		po.setLong("CREATE_BY", userId);
		po.setTimestamp("CREATE_DATE", currTime);
		po.setInteger("POSE_BUS_TYPE", OemDictCodeConstants.POSE_BUS_TYPE_SYS);
		po.setString("POSE_CODE", "SUPER_POSE");
		po.setInteger("POSE_STATUS", OemDictCodeConstants.STATUS_ENABLE);
		po.setInteger("POSE_TYPE", OemDictCodeConstants.SYS_USER_OEM);
		po.setString("REMARK", "系统自动创建的职位");
		flag = po.saveIt();
		if(!flag){
			throw new ServiceBizException("数据库插入数据失败！");
		}
		Long poseId = (Long) po.getId();
		
		//插入职位与角色对应关系
		TrRolePosePO tpo = new TrRolePosePO();
		tpo.setLong("ROLE_ID", roleId);
		tpo.setLong("POSE_ID", poseId);
		tpo.setLong("CREATE_BY", poseId);
		flag = tpo.saveIt();
		if(!flag){
			throw new ServiceBizException("数据库插入数据失败！");
		}
		return poseId;
	}

	@Override
	public CarCompanyDTO editCarCompanyInit(Long companyId) {
		TmCompanyPO po = TmCompanyPO.findById(companyId);
		CarCompanyDTO dto = new CarCompanyDTO();
		dto.setCompanyId(companyId);
		dto.setCompanyCode(po.getString("COMPANY_CODE"));
		dto.setCompanyName(po.getString("COMPANY_NAME"));
		dto.setCompanyShortName(po.getString("COMPANY_SHORTNAME"));
		dto.setProvince(po.getLong("PROVINCE_ID"));
		dto.setCity(po.getLong("CITY_ID"));
		dto.setPhone(po.getString("PHONE"));
		dto.setZipCode(po.getString("ZIP_CODE"));
		dto.setFax(po.getString("FAX"));
		dto.setAddress(po.getString("ADDRESS"));
		dto.setStatus(po.getInteger("STATUS"));
		return dto;
	}

	@Override
	public void editCarCompany(CarCompanyDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long userId = loginInfo.getUserId();
		Date currTime = new Date(System.currentTimeMillis());
		boolean flag = false;
		try {			
			TmCompanyPO comPo = TmCompanyPO.findById(dto.getCompanyId());
			comPo.setString("COMPANY_NAME", dto.getCompanyName());
			comPo.setString("COMPANY_CODE", dto.getCompanyCode());
			comPo.setString("COMPANY_SHORTNAME", dto.getCompanyShortName());
			comPo.setInteger("COMPANY_TYPE", dto.getCompanyType());
			if(dto.getProvince() != null){			
				comPo.setLong("PROVINCE_ID", dto.getProvince());
			}
			if(dto.getCity() != null){			
				comPo.setLong("CITY_ID", dto.getCity());
			}
			comPo.setString("PHONE", dto.getPhone());
			comPo.setString("ZIP_CODE", dto.getZipCode());
			comPo.setString("FAX", dto.getFax());
			comPo.setString("ADDRESS", dto.getAddress());
			comPo.setInteger("STATUS", dto.getStatus());
			comPo.setLong("UPDATE_BY", userId);
			comPo.setTimestamp("UPDATE_DATE", currTime);
			flag = comPo.saveIt();
			if(!flag){
				throw new ServiceBizException("数据库插入数据失败！");
			}		
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException("数据库插入数据失败！");
		}
	}

}
