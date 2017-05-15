package com.yonyou.dms.manage.service.basedata.organization;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmOrgPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.dao.organization.SgmOrgMaintainDao;
import com.yonyou.dms.manage.domains.DTO.basedata.organization.SgmOrgDTO;
import com.yonyou.dms.manage.domains.PO.basedata.TmOrgBusinessAreaPO;
import com.yonyou.f4.mvc.annotation.TxnConn;

@Service
public class SgmOrgMaintainServiceImpl implements SgmOrgMaintainService {
	
	@Autowired
	private SgmOrgMaintainDao sgmOrgDao;

	@Override
	public PageInfoDto searchSgmOrg(Map<String, String> param) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto dto = sgmOrgDao.searchSgmOrg(param,loginInfo);
		return dto;
	}

	@Override
	@TxnConn
	public void addSgmOrg(SgmOrgDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long companyId = loginInfo.getCompanyId();
		Date currentTime = new Date();
		boolean flag = false;
		if(dto.getDeptDesc() == null){
			dto.setDeptDesc("");
		}
		
		List<Map> codeList = sgmOrgDao.isDeptCode(companyId,dto.getDeptCode(),dto.getBusiType());
		if(codeList != null && codeList.size() > 0){
			throw new ServiceBizException("部门代码不能重复！");
		}
		
		List<Map> nameList = sgmOrgDao.isDeptName(companyId, dto.getDeptCode(),dto.getBusiType());
		if(nameList != null && nameList.size() > 0){
			throw new ServiceBizException("部门名称不能重复！");
		}
		
		try {
			//取groupLevel等级PO
			TmOrgPO pOrg = TmOrgPO.findById(dto.getOrgId());
			TmOrgPO org = new TmOrgPO();
			org.setLong("PARENT_ORG_ID", dto.getOrgId());
			org.setLong("COMPANY_ID", loginInfo.getCompanyId());
			org.setString("ORG_CODE", dto.getDeptCode());
			org.setString("ORG_NAME", dto.getDeptName());
			org.setInteger("BUSS_TYPE", dto.getBusiType());
			org.setInteger("STATUS", dto.getDeptStatus());
			org.setInteger("DUTY_TYPE", dto.getDeptType());
			org.setString("ORG_DESC", dto.getDeptDesc());
			org.setTimestamp("CREATE_DATE", currentTime);
			org.setLong("CREATE_BY", loginInfo.getUserId());
			org.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);
			if(pOrg != null){
				org.setInteger("ORG_LEVEL", pOrg.getInteger("ORG_LEVEL")+1);
			}
			flag = org.saveIt();
			if(flag){
			}else{				
				throw new ServiceBizException("数据库插入数据失败！");
			}
		} catch (Exception e) {
			throw new ServiceBizException("数据库插入数据失败！");
		}
				
	}
	

	@Override
	public void editSgmOrg(SgmOrgDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long companyId = loginInfo.getCompanyId();
		Date currentTime = new Date();
		List<Map> list1 = sgmOrgDao.getOrgIdByOrgName(companyId, dto.getDeptCode(), dto.getDeptName(), dto.getBusiType());
		boolean flag = false;
		
		if(list1 != null && list1.size() > 0){
			throw new ServiceBizException("部门名称不能重复！");
		}
		
		if(dto.getDeptDesc() == null){
			dto.setDeptDesc("");
		}
		
		try {
			//需要修改的org
			TmOrgPO org = TmOrgPO.findById(dto.getDeptId());
			Integer statusOld = org.getInteger("STATUS");
			if( dto.getOrgId().equals(org.getLong("PARENT_ORG_ID")) ){// 上级部门没有修改
				org.setLong("PARENT_ORG_ID", dto.getOrgId());
				org.setLong("COMPANY_ID", loginInfo.getCompanyId());
				org.setString("ORG_CODE", dto.getDeptCode());
				org.setString("ORG_NAME", dto.getDeptName());
				org.setInteger("BUSS_TYPE", dto.getBusiType());
				org.setInteger("STATUS", dto.getDeptStatus());
				org.setInteger("DUTY_TYPE", dto.getDeptType());
				org.setString("ORG_DESC", dto.getDeptDesc());
				org.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);
				org.setTimestamp("UPDATE_DATE", currentTime);
				org.setLong("UPDATE_BY", loginInfo.getUserId());
				flag = org.saveIt();
				if(!flag){
					throw new ServiceBizException("数据库插入数据失败！");
				}
			}else{// 上级部门已做修改
				//原上级部门
				TmOrgPO pOrgOld = TmOrgPO.findById(org.getLong("PARENT_ORG_ID"));
				Long oldParentId = org.getLong("PARENT_ORG_ID");
				//现上级部门
				TmOrgPO pOrgCur = TmOrgPO.findById(dto.getOrgId());
				Integer orgLevel=new Integer(99);
				if(pOrgCur != null){
					orgLevel = pOrgCur.getInteger("ORG_LEVEL") + 1;
				}
				org.setLong("PARENT_ORG_ID", dto.getOrgId());
				org.setLong("COMPANY_ID", loginInfo.getCompanyId());
				org.setString("ORG_CODE", dto.getDeptCode());
				org.setString("ORG_NAME", dto.getDeptName());
				org.setInteger("BUSS_TYPE", dto.getBusiType());
				org.setInteger("STATUS", dto.getDeptStatus());
				org.setInteger("DUTY_TYPE", dto.getDeptType());
				org.setString("ORG_DESC", dto.getDeptDesc());
				org.setInteger("ORG_LEVEL", orgLevel);
				org.setString("TREE_CODE", "");
				org.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);
				org.setTimestamp("UPDATE_DATE", currentTime);
				org.setLong("UPDATE_BY", loginInfo.getUserId());
				flag = org.saveIt();
				if(!flag){
					throw new ServiceBizException("数据库插入数据失败！");
				}
				
				List<Object> param = new ArrayList<>();
				param.add(oldParentId);
				// 如果待修改的部门有下级部门则将其所有下级部门的上级部门改为现在待修改部门的上级部门
				List<TmOrgPO> list = TmOrgPO.findBySQL("SELECT * FROM TM_ORG t WHERE t.PARENT_ORG_ID = ? ",param );
				if(list != null && list.size() > 0){					
					for(int i = 0; i < list.size(); i++){
						TmOrgPO po = list.get(i);
						po.setLong("PARENT_ORG_ID", oldParentId);
						po.setInteger("ORG_LEVEL", orgLevel+1);
						po.setString("TREE_CODE", "");
						// 用户修改了部门的状态
						if(!dto.getDeptStatus().equals(statusOld)){
							po.setInteger("STATUS", dto.getDeptStatus());
						}
						boolean flag1 = po.saveIt();
						if(!flag1){
							throw new ServiceBizException("数据库插入数据失败！");
						}
					}
				}
				
				//删除原业务范围
//				List<TmOrgBusinessAreaPO> tp = TmOrgBusinessAreaPO.findBySQL("SELECT * FROM TM_ORG_BUSINESS t WHERE t.ORG_ID = '"+dto.getDeptId()+"'", new ArrayList<>());
//				for(int i = 0; i < tp.size(); i++){
//					TmOrgBusinessAreaPO po = tp.get(i);
//					boolean flag2 = po.delete();
//					if(!flag2){
//						throw new ServiceBizException("数据库插入数据失败！");
//					}
//				}
				TmOrgBusinessAreaPO.delete("ORG_ID = ? ", dto.getDeptId());
				
				String areaIds = dto.getAreaIds();
				if(StringUtils.isNotBlank(areaIds)){
					String[] area = areaIds.split(",");
					for(int i = 0; i < area.length; i ++){						
						TmOrgBusinessAreaPO tp1=new TmOrgBusinessAreaPO();
						Long areaId=Long.valueOf(area[i]);
						tp1.setLong("ORG_ID", dto.getDeptId());
						tp1.setLong("AREA_ID", areaId);
						tp1.setLong("CREATE_BY", loginInfo.getUserId());
						tp1.setTimestamp("CREATE_DATE", currentTime);
						boolean flag3 = tp1.saveIt();
						if(!flag3){
							throw new ServiceBizException("数据库插入数据失败！");
						}
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException("数据库插入数据失败！");
		}
		
	}
	
	private TmOrgPO setTmOrgPO(TmOrgPO pOrg,TmOrgPO org,SgmOrgDTO dto,int flag){
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date currentTime = new Date();
		
		org.setLong("PARENT_ORG_ID", dto.getOrgId());
		org.setLong("COMPANY_ID", loginInfo.getCompanyId());
		org.setString("ORG_CODE", dto.getDeptCode());
		org.setString("ORG_NAME", dto.getOrgName());
		org.setInteger("BUSS_TYPE", dto.getBusiType());
		org.setInteger("STATUS", dto.getDeptStatus());
		org.setInteger("DUTY_TYPE", dto.getDeptType());
		org.setString("ORG_DESC", dto.getDeptDesc());
		org.setInteger("ORG_LEVEL", pOrg.getInteger("ORG_LEVEL") + 1);
		if(flag == 0){
			org.setTimestamp("CREATE_DATE", currentTime);
			org.setLong("CREATE_BY", loginInfo.getUserId());
		}else{
			org.setTimestamp("UPDATE_DATE", currentTime);
			org.setLong("UPDATE_BY", loginInfo.getUserId());
		}
		return org;
	}

	@Override
	public SgmOrgDTO editSgmOrgInit(Long orgId) {
		TmOrgPO po = TmOrgPO.findById(orgId);
		TmOrgPO fpo = TmOrgPO.findById(po.getLong("PARENT_ORG_ID"));
		SgmOrgDTO dto = new SgmOrgDTO();
		dto.setDeptId(po.getLong("ORG_ID"));
		dto.setOrgId(po.getLong("PARENT_ORG_ID"));
		dto.setOrgName(fpo.getString("ORG_NAME"));
		dto.setDeptCode(po.getString("ORG_CODE"));
		dto.setDeptName(po.getString("ORG_NAME"));
		dto.setDeptStatus(po.getInteger("STATUS"));
		dto.setDeptType(po.getInteger("DUTY_TYPE"));
		dto.setBusiType(po.getInteger("BUSS_TYPE"));
		dto.setDeptDesc(po.getString("ORG_DESC"));
		return dto;
	}


}
