package com.yonyou.dms.manage.service.basedata.organization;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmCompanyPO;
import com.yonyou.dms.common.domains.PO.basedata.TmOrgPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.dao.organization.DealerInfoMaintainDao;
import com.yonyou.dms.manage.domains.DTO.basedata.organization.DealerInfoDTO;
import com.yonyou.f4.mvc.annotation.TxnConn;


@Service
public class DealerInfoMaintainServiceImpl implements DealerInfoMaintainService {
	
	@Autowired
	private DealerInfoMaintainDao dealerDao;

	@Override
	public PageInfoDto searchDealerInfo(Map<String, String> param) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		param.put("companyType", OemDictCodeConstants.COMPANY_TYPE_DEALER);
		PageInfoDto dto = dealerDao.searchDealerInfo(param,loginInfo);
		return dto;
	}

	@Override
	public PageInfoDto searchDealerDetail(String companyId) {
		PageInfoDto dto = dealerDao.searchDealerDetail(companyId);
		return dto;
	}

	@TxnConn
	@Override
	public boolean addDealerInfo(DealerInfoDTO dto) {
//		dto.setCompanyType(Integer.parseInt(OemDictCodeConstants.COMPANY_TYPE_DEALER));
		boolean flag1 = false;
		boolean flag2 = false;
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		//从SESSION中查询出oemCompanyId
		Long oemCompanyId=loginUser.getCompanyId();
		//判断新增的经销商信息是否已经存在相应的经销商表中
		List<Map> list = dealerDao.getDealer(dto.getDlrCode(),dto.getDlrName(),dto.getDlrNameForShort());
		long len = list.size() ;
		// 若将公司的状态修改为无效，则不用验证
		if(dto.getStatus().equals(OemDictCodeConstants.STATUS_DISABLE.toString())) {
			len = 0 ;
		}
		
		if(len > 0){
			throw new ServiceBizException("公司编号或名称或简称在主站/分站中已存在！");
		}else{
			try {				
				TmCompanyPO tmcompanyPO = new TmCompanyPO();
				int addFlag = 0;
				//dto中数据插入po
				this.setTmCompanyPO(tmcompanyPO, dto,addFlag);
				//将新增的经销商信息插入公司表
				flag1 = tmcompanyPO.saveIt();
				//获取插入记录的id
				Long companyId = (Long) tmcompanyPO.getId();
				Date currentTime = new Date();
				TmOrgPO tmorgPO = new TmOrgPO();
				tmorgPO.setString("ORG_CODE", dto.getDlrCode());
				tmorgPO.setString("ORG_NAME", dto.getDlrNameForShort());
				tmorgPO.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
				tmorgPO.setLong("CREATE_BY", loginUser.getUserId());
				tmorgPO.setTimestamp("CREATE_DATE", currentTime);
				tmorgPO.setLong("COMPANY_ID", companyId);
				if(dto.getCompanyType().equals(OemDictCodeConstants.COMPANY_TYPE_OEM)){
					tmorgPO.setInteger("ORG_TYPE", Integer.valueOf(OemDictCodeConstants.COMPANY_TYPE_OEM));
					tmorgPO.setInteger("DUTY_TYPE", OemDictCodeConstants.DUTY_TYPE_COMPANY);
				}else{
					tmorgPO.setInteger("ORG_TYPE", Integer.valueOf(OemDictCodeConstants.ORG_TYPE_DEALER));
					tmorgPO.setInteger("DUTY_TYPE", OemDictCodeConstants.DUTY_TYPE_DEALER);
				}
				//将新增的部门信息插入部门表
				flag2 = tmorgPO.saveIt();
			} catch (Exception e) {
				throw new ServiceBizException("数据库插入数据失败！");
			}
		}
		if(flag1 && flag2){			
			return true;
		}else{
			throw new ServiceBizException("数据库插入数据失败！");
		}
	}

	@Override
	public DealerInfoDTO editDealerInfoQuery(Long companyId) {
		TmCompanyPO po = TmCompanyPO.findById(companyId);
		DealerInfoDTO dto = new DealerInfoDTO();
		dto.setCompanyId(po.getLong("COMPANY_ID"));
		dto.setDlrCode(po.getString("COMPANY_CODE"));
		dto.setDlrName(po.getString("COMPANY_NAME"));
		dto.setDlrNameForShort(po.getString("COMPANY_SHORTNAME"));
		dto.setCompanyEn(po.getString("COMPANY_EN"));
		dto.setCompanyType(po.getInteger("COMPANY_TYPE"));
		dto.setProvince(po.getLong("PROVINCE_ID"));
		dto.setCity(po.getLong("CITY_ID"));
		dto.setContTel(po.getString("PHONE"));
		dto.setZipCode(po.getString("ZIP_CODE"));
		dto.setFax(po.getString("FAX"));
		dto.setDetailAddr(po.getString("ADDRESS"));
		dto.setStatus(po.getInteger("STATUS"));
		dto.setCtciCode(po.getString("CTCAI_CODE"));
		dto.setSwtCode(po.getString("SWT_CODE"));
		dto.setElinkCode(po.getString("ELINK_CODE"));
		dto.setDcCode(po.getString("CTCAI_CODE"));
		dto.setLmsCode(po.getString("LMS_CODE"));
		dto.setJecCode(po.getString("JEC_CODE"));
		dto.setFcaCode(po.getString("FCA_CODE"));
		return dto;
	}

	@Override
	public void editDealerInfo(DealerInfoDTO dto) {
		boolean flag1 = false;
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		//判断新增的经销商信息是否已经存在相应的经销商表中
		List<Map> list = dealerDao.getDealerForUpd(dto.getDlrCode(),dto.getDlrName(),dto.getDlrNameForShort());
		long len = list.size() ;
		// 若将公司的状态修改为无效，则不用验证
		if(dto.getStatus().equals(OemDictCodeConstants.STATUS_DISABLE.toString())) {
			len = 0 ;
		}
		
		if(len > 0){
			throw new ServiceBizException("公司编号或名称或简称在主站/分站中已存在！");
		}else{
			try {				
				TmCompanyPO tmcompanyPO = TmCompanyPO.findById(dto.getCompanyId());
				int updFlag = 1;
				//dto数据插入po
				this.setTmCompanyPO(tmcompanyPO, dto,updFlag);
				//将新增的经销商信息插入公司表
				flag1 = tmcompanyPO.saveIt();
				
			} catch (Exception e) {
				throw new ServiceBizException("数据库插入数据失败！");
			}
		}
		if(flag1){			
		}else{
			throw new ServiceBizException("数据库插入数据失败！");
		}
		
	}
	
	/**
	 * TmCompanyPO数据插入
	 * @param tmcompanyPO
	 * @param dto
	 * @param flag 0 为新增    1为修改
	 * @return
	 */
	public TmCompanyPO setTmCompanyPO(TmCompanyPO tmcompanyPO,DealerInfoDTO dto,int flag){
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date currentTime = new Date();
		if(flag == 0){
			Long oemCompanyId=loginUser.getCompanyId();
			tmcompanyPO.setLong("OEM_COMPANY_ID", oemCompanyId);
		}
		tmcompanyPO.setString("COMPANY_NAME", dto.getDlrName());
		tmcompanyPO.setString("COMPANY_CODE", dto.getDlrCode());
		tmcompanyPO.setString("COMPANY_SHORTNAME", dto.getDlrNameForShort());
		tmcompanyPO.setString("COMPANY_EN", dto.getCompanyEn());
		tmcompanyPO.setString("FAX", dto.getFax());
		tmcompanyPO.setString("PHONE", dto.getContTel());
		tmcompanyPO.setString("ADDRESS", dto.getDetailAddr());
		if(dto.getProvince() != null){			
			tmcompanyPO.setLong("PROVINCE_ID", dto.getProvince());
		}
		if(dto.getCity() != null){				
			tmcompanyPO.setLong("CITY_ID", dto.getCity());
		}
		if(dto.getZipCode() != null){
			tmcompanyPO.setString("ZIP_CODE", dto.getZipCode());
		}
		tmcompanyPO.setInteger("COMPANY_TYPE", Integer.parseInt(OemDictCodeConstants.COMPANY_TYPE_DEALER));
		tmcompanyPO.setInteger("STATUS", dto.getStatus());
		if(flag == 0){
			tmcompanyPO.setLong("CREATE_BY", loginUser.getUserId());
			tmcompanyPO.setTimestamp("CREATE_DATE", currentTime);
		}else{			
			tmcompanyPO.setLong("UPDATE_BY", loginUser.getUserId());
			tmcompanyPO.setTimestamp("UPDATE_DATE", currentTime);
		}
		tmcompanyPO.setString("CTCAI_CODE",dto.getCtciCode() );
		tmcompanyPO.setString("SWT_CODE",dto.getSwtCode() );
		tmcompanyPO.setString("ELINK_CODE",dto.getElinkCode() );
		tmcompanyPO.setString("DC_CODE", dto.getDcCode());
		tmcompanyPO.setString("LMS_CODE", dto.getLmsCode());
		tmcompanyPO.setString("JEC_CODE", dto.getJecCode());
		tmcompanyPO.setString("FCA_CODE", dto.getFcaCode());
		return tmcompanyPO;
	}

}
