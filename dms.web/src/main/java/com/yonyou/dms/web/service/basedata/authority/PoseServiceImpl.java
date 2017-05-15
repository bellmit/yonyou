package com.yonyou.dms.web.service.basedata.authority;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TcPoseBussPO;
import com.yonyou.dms.common.domains.PO.basedata.TcPosePO;
import com.yonyou.dms.common.domains.PO.basedata.TmCompanyPO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TmOrgPO;
import com.yonyou.dms.common.domains.PO.basedata.TrRolePosePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.web.dao.authority.PoseDao;
import com.yonyou.dms.web.domains.DTO.basedata.authority.PoseDTO;

@Service
public class PoseServiceImpl implements PoseService{
	
	@Autowired
	private PoseDao dao;

	@Override
	public PageInfoDto queryList(Map<String, String> queryParam) throws ServiceBizException {
		return dao.queryList(queryParam);
	}

	@Override
	public List<Map> getSeriesList(String id) throws ServiceBizException {
		return dao.getSeriesList(id);
	}

	@Override
	public PageInfoDto selectRole(Map<String, String> queryParam) throws ServiceBizException {
		return dao.selectRole(queryParam);
	}

	@Override
	public List<Map> getFuncList() throws ServiceBizException {
		return dao.getFuncList();
	}

	@Override
	public void checkPose(String poseCode, Map<String, Object> message) throws ServiceBizException {
		Boolean flag = dao.checkPose(poseCode);
		if(!flag){
			message.put("STATUS", 1);
		}else{
			message.put("STATUS", 2);
		}
	}
	
	/**
	 * 新增职位信息
	 */
	@Override
	public Long addPose(PoseDTO poseDto) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TcPosePO posePO = new TcPosePO();
		setPO(posePO,poseDto,1);
		posePO.saveIt();
		Long billId = posePO.getLongId();
		//新增角色权限
		if(null!=poseDto.getAddIds() && poseDto.getAddIds().length()>0){
			String[] ids = poseDto.getAddIds().split(",");
			TrRolePosePO.delete(" POSE_ID = ? ", billId);
			for (int i = 0; i < ids.length; i++) {
				TrRolePosePO po = new TrRolePosePO();
				po.setString("ROLE_ID", ids[i]);
				po.setString("POSE_ID", billId);
				po.setString("CREATE_BY", loginInfo.getUserId());
				po.setTimestamp("CREATE_DATE", new Date());
				po.saveIt();
			}
		}
		//新增职位业务范围权限
		if(OemDictCodeConstants.SYS_USER_OEM.equals(poseDto.getPoseType())){
			if(null!=poseDto.getSeriesIds() && poseDto.getSeriesIds().length()>0){
				String[] seriesIds = poseDto.getSeriesIds().split(",");
				TcPoseBussPO.delete(" POSE_ID = ? ", billId);
				for (int i = 0; i < seriesIds.length; i++) {
					TcPoseBussPO po = new TcPoseBussPO();
					po.setString("GROUP_ID", seriesIds[i]);
					po.setString("POSE_ID", billId);
					po.setString("CREATE_BY", loginInfo.getUserId());
					po.setTimestamp("CREATE_DATE", new Date());
					po.saveIt();
				}
			}
		}
		return billId;
	}
	
	/**
	 * 数据映射
	 * @param posePO
	 * @param poseDto
	 * @param i
	 */
	private void setPO(TcPosePO posePO, PoseDTO poseDto, int type) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		Long dealerOrgId = 0l;
		Long companyId = 0L;
		if(OemDictCodeConstants.SYS_USER_DEALER.equals(poseDto.getPoseType()))
		{
//			Long dealerId = poseDto.getDealerId();
//			if(null==dealerId){
//				throw new ServiceBizException("经销商公司不正确！");
//			}
//			TmOrgPO orgPo=new TmOrgPO();
//			orgPo.setLong("COMPANY_ID", companyId);
//			dealerOrgId= orgPo.getLong("ORG_ID");
//			TmDealerPO dpo = TmDealerPO.findById(dealerId);
//			dealerOrgId = dpo.getLong("DEALER_ORG_ID");
//			companyId = dpo.getLong("COMPANY_ID");
			companyId = poseDto.getCompanyId();
			List<TmOrgPO> orgList = TmOrgPO.find("COMPANY_ID = ? ", companyId);
			if(orgList != null && !orgList.isEmpty()){				
				dealerOrgId = orgList.get(0).getLong("ORG_ID");
			}
		}
		posePO.setString("POSE_CODE", poseDto.getPoseCode());
		posePO.setString("POSE_NAME", poseDto.getPoseName());
		posePO.setString("POSE_TYPE", poseDto.getPoseType());
		posePO.setString("POSE_STATUS", poseDto.getPoseStatus());
		posePO.setString("POSE_BUS_TYPE", poseDto.getPoseBusType());
		if(OemDictCodeConstants.SYS_USER_DEALER.equals(poseDto.getPoseType())){
			posePO.setLong("COMPANY_ID", companyId);
			posePO.setLong("ORG_ID", dealerOrgId);
		}else{
			posePO.setLong("COMPANY_ID", loginInfo.getCompanyId());
			posePO.setLong("ORG_ID", poseDto.getOrgId());
		}
		if(type==1){
			posePO.setInteger("CREATE_BY", loginInfo.getUserId());
			posePO.setTimestamp("CREATE_DATE", new Date());
		}else{
			posePO.setInteger("UPDATE_BY", loginInfo.getUserId());
			posePO.setTimestamp("UPDATE_DATE", new Date());
		}
	}
	
	/**
	 * 根据ID获取职位相关信息
	 */
	@Override
	public Map<String, Object> findByPose(Long id) throws ServiceBizException {
		
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TcPosePO po = TcPosePO.findById(id);
		Map<String, Object> map = po.toMap();
		
		//查询用户角色信息
		List<TrRolePosePO> roleList =  TrRolePosePO.find(" POSE_ID = ? ", id);
		String roles = "";
		if(null!=roleList && roleList.size()>0){
			for (TrRolePosePO trRolePosePO : roleList) {
				if(roles .equals("")){
					roles = trRolePosePO.getLong("ROLE_ID").toString();
				}else{
					roles = roles +","+ trRolePosePO.getLong("ROLE_ID");
				}
			}
		}
		map.put("TYPE", po.getInteger("POSE_TYPE"));
		map.put("ROLE_IDS", roles);
		loginInfo.setRoleIds(roles);
		
		//查询用户业务范围权限
		if(po.getInteger("POSE_TYPE").equals(OemDictCodeConstants.SYS_USER_OEM)){
			TmOrgPO orgPo = TmOrgPO.findById(po.getLong("ORG_ID"));
			if(orgPo != null){			
				map.put("ORG_NAME", orgPo.getString("ORG_NAME"));
			}
			//查询用户业务范围权限
			List<TcPoseBussPO> tppo = TcPoseBussPO.find(" POSE_ID = ?  ", id);
			String seriesIds = "";
			if(null!=tppo && roleList.size()>0){
				for (TcPoseBussPO tcPoseBussPO : tppo) {
					if(seriesIds .equals("")){
						seriesIds = tcPoseBussPO.getLong("GROUP_ID").toString();
					}else{
						seriesIds = seriesIds +","+ tcPoseBussPO.getLong("GROUP_ID");
					}
				}
			}
			map.put("SERIES_IDS", seriesIds);
		}else{
			TmCompanyPO companyPo = TmCompanyPO.findById(po.getLong("COMPANY_ID"));
			map.put("COMPANY_NAME", companyPo.getString("COMPANY_NAME"));
			map.put("SERIES_IDS", "");
		}
		
		//
		List<TmDealerPO> deaList = TmDealerPO.find("DEALER_ORG_ID = ?", po.getLong("ORG_ID"));
		if(deaList != null && !deaList.isEmpty()){			
			map.put("DEALER_ID", deaList.get(0).getString("DEALER_ID"));
		}
		return map;
	}
	
	/**
	 * 修改职位信息
	 */
	@Override
	public void modifyPose(Long id, PoseDTO poseDto) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TcPosePO posePO = TcPosePO.findById(id);
		setPO(posePO,poseDto,2);
		posePO.saveIt();
		//新增角色权限
		if(null!=poseDto.getAddIds() && poseDto.getAddIds().length()>0){
			String[] ids = poseDto.getAddIds().split(",");
			TrRolePosePO.delete(" POSE_ID = ? ", id);
			for (int i = 0; i < ids.length; i++) {
				TrRolePosePO po = new TrRolePosePO();
				po.setString("ROLE_ID", ids[i]);
				po.setString("POSE_ID", id);
				po.setString("CREATE_BY", loginInfo.getUserId());
				po.setTimestamp("CREATE_DATE", new Date());
				po.saveIt();
			}
		}
		//新增职位业务范围权限
		if(poseDto.getPoseType().toString().equals(String.valueOf(OemDictCodeConstants.SYS_USER_OEM))){
			if(null!=poseDto.getSeriesIds() && poseDto.getSeriesIds().length()>0){
				String[] seriesIds = poseDto.getSeriesIds().split(",");
				TcPoseBussPO.delete(" POSE_ID = ? ", id);
				for (int i = 0; i < seriesIds.length; i++) {
					TcPoseBussPO po = new TcPoseBussPO();
					po.setString("GROUP_ID", seriesIds[i]);
					po.setString("POSE_ID", id);
					po.setString("CREATE_BY", loginInfo.getUserId());
					po.setTimestamp("CREATE_DATE", new Date());
					boolean flag = po.saveIt();
					System.out.println(flag);
				}
			}
		}
	}
	
	/**
	 * 根据职位加载职位信息
	 */
	@Override
	public List<Map> getPoseRoles(Long id) throws ServiceBizException {
		return dao.getPoseRoles(id);
	}

	@Override
	public String checkPose(Map<String,String> params) {
		String result = "";
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String poseType = params.get("poseType");
		String poseCode = params.get("poseCode");
		String poseName = params.get("poseName");
		String companyId = params.get("companyId");
		if(String.valueOf(OemDictCodeConstants.SYS_USER_OEM).equals(poseType)){
			companyId = String.valueOf(loginInfo.getCompanyId());
		}
		List<TcPosePO> codeList = TcPosePO.find("POSE_CODE = ? AND COMPANY_ID = ?", poseCode,companyId);
		List<TcPosePO> nameList = TcPosePO.find("POSE_NAME = ? AND COMPANY_ID = ?", poseName,companyId);
		if(codeList != null && !codeList.isEmpty()){
			result = "poseCode_error";
		}else if(nameList != null && !nameList.isEmpty()){
			result = "poseName_error";
		}
		return result;
	}
	
	
}
