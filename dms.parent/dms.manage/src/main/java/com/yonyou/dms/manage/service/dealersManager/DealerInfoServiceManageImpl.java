package com.yonyou.dms.manage.service.dealersManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmCompanyPO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerOrgRelationPO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TmOrgPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.dao.dlerlersManagerDao.DealerInfoManageDao;
import com.yonyou.dms.manage.domains.DTO.basedata.DealerDetailedinfoDTO;
import com.yonyou.dms.manage.domains.DTO.basedata.DealerMaintainImportDTO;
import com.yonyou.dms.manage.domains.PO.dealerManager.TiDealerInfoPO;
import com.yonyou.dms.manage.domains.PO.dealerManager.TmDealerBussPO;
import com.yonyou.dms.manage.domains.PO.dealerManager.TmDealerEdPo;
import com.yonyou.dms.manage.domains.PO.dealerManager.TmVsDealerShowroomPO;
import com.yonyou.dms.manage.domains.PO.dealerManager.TtCompanyDetailPO;

/**
 * 查询经销商
 * @author ZhaoZ
 *@date 2017年2月24日
 */
@Service
public class DealerInfoServiceManageImpl implements DealerInfoManageService{

	@Autowired
	private DealerInfoManageDao dealerDao;
	
	@Autowired
    FileStoreService fileStoreService;

	/**
	 * 经销商信息查询
	 */
	public PageInfoDto getDealers(Map<String, String> queryParams)throws  ServiceBizException {
		
		return  dealerDao.getQueryDealers(queryParams);
	}
	/**
	 * 查询经销商基本信息
	 */
	@Override
	public PageInfoDto getDealerInfos(Map<String, String> queryParams) throws ServiceBizException {
		
		return dealerDao.getQueryDealerInfos(queryParams);
	}
	/**
	 * 基本信息下载
	 */
	@Override
	public List<Map> findDealerInfousDownload(Map<String, String> queryParams) throws  ServiceBizException {
		
		return  dealerDao.getDealerInfoList(queryParams);
	}
	/**
	 * 根据PO获取经销商详细信息
	 */
	@Override
	public TmDealerEdPo getDealerDetailedInfo(Long dealerId) throws ServiceBizException {
		// 
	
		return TmDealerEdPo.findFirst("DEALER_ID = ?",dealerId);
	}
	/**
	 * 维护页面信息下载
	 */
	@Override
	public List<Map> dealerMaintainInfosDownload(Map<String, String> queryParams) throws ServiceBizException {
		
		return dealerDao. getDealerInfoList(queryParams);
	}
	/**
	 * 查询车系信息
	 * @return
	 */
	@Override
	public List<Map> findGroupDownload() throws ServiceBizException {
		return dealerDao. getGroupList();
	}
	/**
	 * 查询经销商基本信息
	 */
	@Override
	public PageInfoDto getDlrDealers(Map<String, String> queryParams, LoginInfoDto loginUser) throws ServiceBizException {
		
		return dealerDao. getDlrDealerInfo(queryParams,loginUser);
	}
	/**
	 * 查询业务范围到车系
	 * @param dealerId
	 * @return
	 */
	public List<Map> getDealerBuss() throws ServiceBizException {
		return dealerDao. getDealerBussInfo();
	}
	@Override
	public TmDealerPO getTmDealerPO(Long dealerId) throws ServiceBizException {
		return TmDealerPO.findById(dealerId);
	}
	@Override
	public Map<String, Object> getDealerOrgCodeAndId(Long dealerId) throws ServiceBizException {
		
		return dealerDao. getOrgCodesInfo(dealerId);
	}
	@Override
	public PageInfoDto queryDealerAuditInfo(Map<String, String> queryParams) throws ServiceBizException {
		return dealerDao. getDealerAuditInfo(queryParams);
	}
	//修改经销商信息
	@Override
	public void editModifyDealer(DealerDetailedinfoDTO dto,Long dealerId) throws ServiceBizException {
				
				boolean flag1 = false;
			
				LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				TmDealerPO tmdealerPO = TmDealerPO.findById(dealerId);
				flag1 = setTmDealerPO1(tmdealerPO, dto,dealerId).saveIt();
				if(flag1){			
				}else{
					throw new ServiceBizException("经销商修改数据失败！");
				}
//				StringBuffer sql = new StringBuffer();
//				sql.append("update TM_DEALER set IS_K4 = "+tmdealerPO.getLong("IS_K4")+","); 
//				sql.append("IS_FIAT = "+tmdealerPO.getLong("IS_FIAT")+", IS_CJD = "+tmdealerPO.getLong("IS_CJD")+",");
//				sql.append("UPDATE_DATE = now(), UPDATE_BY = "+loginUser.getUserId()+" where DEALER_TYPE = "+OemDictCodeConstants.DEALER_TYPE_DWR + "");
//				sql.append(" and DEALER_CODE in (select DEALER_CODE||'A' from TM_DEALER where DEALER_ID = "+dealerId+")");
//				OemDAOUtil.execBatchPreparement(sql.toString(), null);
				
				//重新保存经销商业务范围
//				TmDealerBussPO po =TmDealerBussPO.findFirst("DEALER_ID = ?",dealerId);
//				if(po!=null){
//					po.deleteCascadeShallow();
//				}
				
				//修改TmDealerBuss表
				//先删除之前存在的业务范围
				TmDealerBussPO.delete("DEALER_ID = ?", dealerId);
				//之后逐条插入新的业务范围
				if(!StringUtils.isNullOrEmpty(dto.getGroupIds())){
					String[] groupIds = dto.getGroupIds().split(",");
					if(groupIds!=null && groupIds.length>0){
						for(String groupId : groupIds){
							TmDealerBussPO tempPo = new TmDealerBussPO();
							tempPo.setLong("DEALER_ID",tmdealerPO.getId());
							tempPo.setLong("GROUP_ID",new Long(groupId));
							tempPo.setLong("CREATE_BY",loginUser.getUserId());
							tempPo.setTimestamp("CREATE_DATE",new Date());
							flag1 = tempPo.saveIt();
							if(flag1){			
							}else{
								throw new ServiceBizException("经销商修改数据失败！");
							}
						}
					}
				}
				
				
				TmDealerOrgRelationPO tdorpoCondition = TmDealerOrgRelationPO.findFirst("DEALER_ID = ?", dealerId);
				TmDealerOrgRelationPO tdorpoValue=new TmDealerOrgRelationPO();
				if(!StringUtils.isNullOrEmpty(dto.getOrgId())){
					if(tdorpoCondition!=null){
						tdorpoCondition.setLong("ORG_ID", dto.getOrgId());
						tdorpoCondition.setLong("UPDATE_BY", loginUser.getUserId());
						tdorpoCondition.setTimestamp("UPDATE_DATE", new Date());
						flag1 = tdorpoCondition.saveIt();
						if(flag1){			
						}else{
							throw new ServiceBizException("经销商修改数据失败！");
						}
					}else{
						tdorpoValue.setLong("ORG_ID",  dto.getOrgId());
						tdorpoValue.setLong("CREATE_BY", loginUser.getUserId());
						tdorpoValue.setTimestamp("CREATE_DATE", new Date());
						tdorpoValue.setLong("DEALER_ID",dealerId);
						flag1 = tdorpoValue.saveIt();
						if(flag1){			
						}else{
							throw new ServiceBizException("经销商修改数据失败！");
						}
					}
				}else{
					if(tdorpoCondition!=null){
						tdorpoCondition.deleteCascadeShallow();
					}
					
				}
				
				//入网信息
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				TmDealerPO tdPO = TmDealerPO.findById(dealerId);
				if(tdPO!=null){
					TtCompanyDetailPO comDetailPO = TtCompanyDetailPO.findFirst("COMPANY_ID = ?", tdPO.getLong("COMPANY_ID"));
					if(comDetailPO ==null){
						comDetailPO = new TtCompanyDetailPO();
					}
					comDetailPO.setString("COMPANY_ID", dto.getCompanyId());
					comDetailPO.setString("COMPANY_LEVEL", dto.getCompanyLevel());
					if(dto.getEndDate()!=null){
						comDetailPO.setDate("END_DATE", dto.getEndDate());
					}
					//将数组转化为，分割
					String[] licenseBrands = dto.getLicensedBrands();
					String licenseBrand = "";
					if(licenseBrands!= null && licenseBrands.length > 0){
						for(String id : licenseBrands){
							licenseBrand += id + ",";
						}
						licenseBrand = licenseBrand.substring(0, licenseBrand.length()-1);
					}
					comDetailPO.setString("LICENSED_BRANDS", licenseBrand);
					comDetailPO.setTimestamp("UPDATE_DATE", new Date());
					comDetailPO.setLong("UPDATE_BY",  loginUser.getUserId());
					flag1 = comDetailPO.saveIt();
					if(flag1){			
					}else{
						throw new ServiceBizException("经销商修改数据失败！");
					}
					
				}
		//更新接口表(只对销售)
		if(tdPO.getInteger("DEALER_TYPE")==OemDictCodeConstants.DEALER_TYPE_DVS){
			TiDealerInfoPO valuePo = TiDealerInfoPO.findById( tmdealerPO.getLong("COMPANY_ID"));
			TmCompanyPO tmcompanyPO = TmCompanyPO.findFirst("COMPANY_ID = ?",tmdealerPO.getLong("COMPANY_ID") );
			if(tmcompanyPO!=null && valuePo!=null){
				valuePo.setString("DEALER_NAME",tmcompanyPO.getString("DEALER_NAME"));
				valuePo.setString("DEALERAB_CN",tmcompanyPO.getString("COMPANY_SHORTNAME"));
				valuePo.setString("DEALERAB_EN",tmcompanyPO.getString("COMPANY_EN"));
				valuePo.setInteger("CITY_ID",tmcompanyPO.getBigDecimal("PROVINCE_ID"));
				valuePo.setInteger("REALLY_CITY_ID",tmcompanyPO.getBigDecimal("CITY_ID"));
				valuePo.setString("ADDRESS",tmcompanyPO.getString("ADDRESS"));
				valuePo.setString("SERVICE_TEL",tmcompanyPO.getString("PHONE"));
				valuePo.setInteger("IS_SCAN",0);
				if(tmcompanyPO.getInteger("STATUS").equals(OemDictCodeConstants.STATUS_ENABLE)){
					valuePo.setInteger("STATUS",1);
				}else{
					valuePo.setInteger("STATUS",0);
				}
				valuePo.setBigDecimal("UPDATE_BY",tmcompanyPO.getBigDecimal("UPDATE_BY"));
				valuePo.setTimestamp("UPDATE_DATE",tmcompanyPO.getDate("UPDATE_DATE"));
				flag1 = valuePo.saveIt();
				if(flag1){			
				}else{
					throw new ServiceBizException("经销商修改数据失败！");
				}
			}
		}
		if(!StringUtils.isNullOrEmpty(dto.getIsEc())){
			TmDealerEdPo tmKey= TmDealerEdPo.findById(dealerId);
			if(tmKey!=null){
				if(dto.getIsEc()!=null){
					if( dto.getDealerCode_().endsWith("A") ){
						tmKey.setInteger("IS_EC",OemDictCodeConstants.IF_TYPE_NO);
					}else{				
						tmKey.setInteger("IS_EC",Integer.parseInt(dto.getIsEc()));
					}
				}
				tmKey.setTimestamp("UPDATE_DATE",new Date());
				tmKey.setLong("UPDATE_BY",loginUser.getUserId());
				flag1 = tmKey.saveIt();
				if(flag1){			
				}else{
					throw new ServiceBizException("经销商修改数据失败！");
				}
			}
			
		}

	}
	private TtCompanyDetailPO setTtCompanyDetailPO(TtCompanyDetailPO comDetailPO, DealerDetailedinfoDTO dto)  {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		comDetailPO.setLong("COMPANY_ID", dto.getCompanyId());
		if(dto.getEndDate()!=null){
			comDetailPO.setDate("END_DATE", dto.getEndDate());
		}
		comDetailPO.setLong("CREATE_BY",loginUser.getUserId());
		comDetailPO.setTimestamp("CREATE_DATE", new Date());
		//将数组转化为，分割
		String[] licenseBrands = dto.getLicensedBrands();
		String licenseBrand = "";
		if(licenseBrands!= null && licenseBrands.length > 0){
			for(String id : licenseBrands){
				licenseBrand += id + ",";
			}
			licenseBrand = licenseBrand.substring(0, licenseBrand.length()-1);
		}
		comDetailPO.setString("LICENSED_BRANDS",licenseBrand);
		comDetailPO.setString("COMPANY_LEVEL",dto.getCompanyLevel());
		
		comDetailPO.setString("COMPANY_PHOTO", this.getRamdomId());
		comDetailPO.setString("BUSINESS_LICENSE", this.getRamdomId());
		comDetailPO.setString("ORGANIZATION_CHART", this.getRamdomId());
		comDetailPO.setString("COPIES_OF_MANDA", this.getRamdomId());
		comDetailPO.setString("TAX_CERTIFICATE", this.getRamdomId());
		comDetailPO.setString("FINANCIAL_STATEMENT", this.getRamdomId());
		comDetailPO.setString("CONTRACT_NO", this.getRamdomId());
		return comDetailPO;
	}
	
	
	/**
	 * 修改   tmdealerPO
	 * @param tmdealerPO
	 * @param dto
	 * @return
	 */
	private TmDealerPO setTmDealerPO1(TmDealerPO tmdealerPO, DealerDetailedinfoDTO dto,Long dealerId) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long oemCompanyId=loginUser.getCompanyId();
		
		tmdealerPO.setLong("OEM_COMPANY_ID", oemCompanyId);
		tmdealerPO.setString("DEALER_NAME", dto.getDealerName());
		tmdealerPO.setString("DEALER_SHORTNAME", dto.getDealerShortname());
		if(dto.getTmGroupId() != null){			
			tmdealerPO.setBigDecimal("DEALER_GROUP_ID", dto.getTmGroupId());
		}
		if(!StringUtils.isNullOrEmpty(dto.getCompanyId())){
			TmOrgPO PO = TmOrgPO.findFirst("COMPANY_ID = ?", dto.getCompanyId());
			tmdealerPO.setLong("DEALER_ORG_ID", PO.getLong("ORG_ID"));
		}
		if(dto.getDealerType() != null){	
			tmdealerPO.setInteger("DEALER_TYPE", Integer.parseInt(dto.getDealerType()));
		}
		
		if(dto.getMarketing() != null){			
			tmdealerPO.setString("MARKETING", dto.getMarketing());
		}
		if(!StringUtils.isNullOrEmpty(dto.getDealerCode_())){
			tmdealerPO.setString("DEALER_CODE", dto.getDealerCode_());
		}
		
		if(!StringUtils.isNullOrEmpty(dto.getCompanyId())){
			tmdealerPO.setLong("COMPANY_ID",dto.getCompanyId());
		}
		
		tmdealerPO.setDate("FOUND_DATE", dto.getFoundDate());
		tmdealerPO.setInteger("ACURA_GHAS_TYPE", dto.getAcuraGhasType());
		tmdealerPO.setInteger("STATUS", dto.getStatus());
		tmdealerPO.setLong("PROVINCE_ID", dto.getProvince());
		tmdealerPO.setLong("CITY_ID", dto.getCity());
		tmdealerPO.setString("ZIP_CODE", dto.getZipCode());
		tmdealerPO.setString("PHONE", dto.getPhone());
		tmdealerPO.setString("LINK_MAN", dto.getLinkMan());
		tmdealerPO.setString("LINK_MAN_MOBILE", dto.getLinkManMobile());
		tmdealerPO.setString("FAX_NO", dto.getFaxNo());
		tmdealerPO.setString("EMAIL", dto.getEmail());
		tmdealerPO.setString("TAXES_NO", dto.getTaxesNo());
		tmdealerPO.setString("ERP_CODE", dto.getErpCode());
		tmdealerPO.setString("BEGIN_BANK", dto.getBeginBank());
		tmdealerPO.setString("BANK_CODE", dto.getBankCode());
		tmdealerPO.setString("REMARK", dto.getRemark());	
		tmdealerPO.setInteger("RANGES", dto.getRange());//等级
		tmdealerPO.setString("CC_CODE", dto.getCcCode());
		tmdealerPO.setString("FAX_NO", dto.getFaxNo());
		tmdealerPO.setString("EMAIL", dto.getEmail());
		tmdealerPO.setString("TAXES_NO", dto.getTaxesNo());
		tmdealerPO.setString("ERP_CODE", dto.getErpCode());
		tmdealerPO.setString("LINE_NUMBER", dto.getLineNumber());
		if(dto.getIsEc()!=null){
			if( dto.getDealerCode_().endsWith("A") ){
				tmdealerPO.setInteger("IS_EC",OemDictCodeConstants.IF_TYPE_NO);
			}else{				
				tmdealerPO.setInteger("IS_EC",Integer.parseInt(dto.getIsEc()));
			}
		}
		if(dto.getIsK4()!=null){
			tmdealerPO.setInteger("IS_K4",Integer.parseInt(dto.getIsK4()));
		}
		if(dto.getIsFiat()!=null){
			tmdealerPO.setInteger("IS_FIAT",Integer.parseInt(dto.getIsFiat()));
		}
		if(dto.getIsCjd()!=null){
			tmdealerPO.setInteger("IS_CJD",Integer.parseInt(dto.getIsCjd()));
		}
		tmdealerPO.setString("ADDRESS", dto.getAddress());
		tmdealerPO.setString("INTEGRATED_SOCIAL", dto.getIntegratedSocial());
		tmdealerPO.setLong("UPDATE_BY", loginUser.getUserId());
		tmdealerPO.setTimestamp("UPDATE_DATE", new Date());
		return tmdealerPO;
		
	}
	
	/**
	 * 增加   tmdealerPO
	 * @param tmdealerPO
	 * @param dto
	 * @return
	 */
	private TmDealerPO setTmDealerPO2(TmDealerPO tmdealerPO, DealerDetailedinfoDTO dto,int flag) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long oemCompanyId=loginUser.getCompanyId();
		tmdealerPO.setLong("OEM_COMPANY_ID", oemCompanyId);
		tmdealerPO.setString("DEALER_NAME", dto.getDealerName());
		tmdealerPO.setString("DEALER_SHORTNAME", dto.getDealerShortname());
		if(dto.getTmGroupId() != null){			
			tmdealerPO.setBigDecimal("DEALER_GROUP_ID", dto.getTmGroupId());
		}
		tmdealerPO.setInteger("DEALER_AUDIT_STATUS", OemDictCodeConstants.DEALER_AUDIT_STATE_01);
		if(!StringUtils.isNullOrEmpty(dto.getCompanyId())){
			TmOrgPO PO = TmOrgPO.findFirst("COMPANY_ID = ?", dto.getCompanyId());
			tmdealerPO.setLong("DEALER_ORG_ID", PO.getLong("ORG_ID"));
		}
		if(dto.getMarketing() != null){			
			tmdealerPO.setString("MARKETING", dto.getMarketing());
		}
		tmdealerPO.setInteger("DEALER_STATUS", OemDictCodeConstants.DEALER_STATE_01);
		if(flag==1){
			//售后经销商
			tmdealerPO.setString("DEALER_CODE", dto.getDealerCodeA_());
			tmdealerPO.setInteger("DEALER_TYPE", OemDictCodeConstants.DEALER_TYPE_DWR);
			if(dto.getIsEc()!=null){
				tmdealerPO.setInteger("IS_EC",OemDictCodeConstants.IF_TYPE_NO);
			}
		}else{
			//销售经销商
			tmdealerPO.setString("DEALER_CODE", dto.getDealerCode_());
			tmdealerPO.setInteger("DEALER_TYPE", OemDictCodeConstants.DEALER_TYPE_DVS);
			tmdealerPO.setInteger("IS_EC",Integer.parseInt(dto.getIsEc()));
		}
	
		tmdealerPO.setLong("COMPANY_ID",dto.getCompanyId());
		tmdealerPO.setDate("FOUND_DATE", dto.getFoundDate());
		tmdealerPO.setInteger("ACURA_GHAS_TYPE", dto.getAcuraGhasType());
		tmdealerPO.setInteger("STATUS",dto.getStatus());
		if(!StringUtils.isNullOrEmpty(dto.getProvince())){
			tmdealerPO.setLong("PROVINCE_ID", dto.getProvince());
			tmdealerPO.setLong("PARENT_DEALER_D", dto.getProvince());
		}
		if(!StringUtils.isNullOrEmpty( dto.getCity())){
			tmdealerPO.setLong("CITY_ID", dto.getCity());
		}
		
		tmdealerPO.setString("ZIP_CODE", dto.getZipCode());
		tmdealerPO.setString("PHONE", dto.getPhone());
		tmdealerPO.setString("LINK_MAN", dto.getLinkMan());
		tmdealerPO.setString("LINK_MAN_MOBILE", dto.getLinkManMobile());
		tmdealerPO.setString("FAX_NO", dto.getFaxNo());
		tmdealerPO.setString("EMAIL", dto.getEmail());
		tmdealerPO.setString("TAXES_NO", dto.getTaxesNo());
		tmdealerPO.setString("ERP_CODE", dto.getErpCode());
		tmdealerPO.setString("BEGIN_BANK", dto.getBeginBank());
		tmdealerPO.setString("BANK_CODE", dto.getBankCode());
		tmdealerPO.setString("REMARK", dto.getRemark());		
		tmdealerPO.setInteger("RANGES", dto.getRange());//等级
		tmdealerPO.setString("CC_CODE", dto.getCcCode());
		tmdealerPO.setString("FAX_NO", dto.getFaxNo());
		tmdealerPO.setString("EMAIL", dto.getEmail());
		tmdealerPO.setString("TAXES_NO", dto.getTaxesNo());
		tmdealerPO.setString("ERP_CODE", dto.getErpCode());
		tmdealerPO.setString("LINE_NUMBER", dto.getLineNumber());
		tmdealerPO.setString("ADDRESS", dto.getAddress());
		if(dto.getIsK4()!=null){
			tmdealerPO.setInteger("IS_K4",Integer.parseInt(dto.getIsK4()));
		}
		if(dto.getIsFiat()!=null){
			tmdealerPO.setInteger("IS_FIAT",Integer.parseInt(dto.getIsFiat()));
		}
		if(dto.getIsCjd()!=null){
			tmdealerPO.setInteger("IS_CJD",Integer.parseInt(dto.getIsCjd()));
		}
		tmdealerPO.setLong("CREATE_BY", loginUser.getUserId());
		tmdealerPO.setTimestamp("CREATE_DATE", new Date());
		tmdealerPO.setInteger("DEALER_LEVEL",OemDictCodeConstants.DEALER_LEVEL_01);
		tmdealerPO.setString("INTEGRATED_SOCIAL", dto.getIntegratedSocial());
		return tmdealerPO;
		
	}
	private TmDealerEdPo setTmDealerEdPo(TmDealerEdPo tmdealerPO, DealerDetailedinfoDTO dto, int addFlag) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if(!StringUtils.isNullOrEmpty(dto.getLineNumber())){
			tmdealerPO.setString("LINE_NUMBER", dto.getLineNumber());
			tmdealerPO.setLong("LINE_NUMBER_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getIntegratedSocial())){
			tmdealerPO.setString("INTEGRATED_SOCIAL", dto.getIntegratedSocial());
			tmdealerPO.setLong("INTEGRATED_SOCIAL_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getOpenRange())){
			tmdealerPO.setString("OPEN_RANGE", dto.getOpenRange());
			tmdealerPO.setLong("OPEN_RANGE_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getOpenTime())){
			tmdealerPO.setString("OPEN_TIME", dto.getOpenTime());
			tmdealerPO.setLong("OPEN_TIME_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getCompanyPhone())){
			tmdealerPO.setString("COMPANY_PHONE", dto.getCompanyPhone());
			tmdealerPO.setLong("COMPANY_PHONE_F", 1);
		}
		if(!StringUtils.isNullOrEmpty( dto.getDealerName())){
			tmdealerPO.setString("DEALER_NAME", dto.getDealerName());
			tmdealerPO.setLong("DEALER_NAME_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getDealerShortname())){
			tmdealerPO.setString("DEALER_SHORTNAME", dto.getDealerShortname());
			tmdealerPO.setLong("DEALER_SHORTNAME_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getMarketing())){
			tmdealerPO.setString("MARKETING", dto.getMarketing());
			tmdealerPO.setLong("MARKETING_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getFoundDate())){
			tmdealerPO.setDate("FOUND_DATE", dto.getFoundDate());
			tmdealerPO.setLong("FOUND_DATE_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getProvince())){
			tmdealerPO.setLong("PROVINCE_ID", dto.getProvince());
			tmdealerPO.setLong("PROVINCE_ID_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getLineNumber())){
			tmdealerPO.setLong("CITY_ID", dto.getCity());
			tmdealerPO.setLong("CITY_ID_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getZipCode())){
			tmdealerPO.setString("ZIP_CODE", dto.getZipCode());
			tmdealerPO.setLong("ZIP_CODE_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getPhone())){
			tmdealerPO.setString("PHONE", dto.getPhone());
			tmdealerPO.setLong("PHONE_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getLinkMan())){
			tmdealerPO.setString("LINK_MAN", dto.getLinkMan());
			tmdealerPO.setLong("LINK_MAN_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getFaxNo())){
			tmdealerPO.setString("FAX_NO", dto.getFaxNo());
			tmdealerPO.setLong("FAX_NO_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getLinkManMobile())){
			tmdealerPO.setString("LINK_MAN_MOBILE", dto.getLinkManMobile());
			tmdealerPO.setLong("LINK_MAN_MOBILE_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getEmail())){
			tmdealerPO.setString("EMAIL", dto.getEmail());
			tmdealerPO.setLong("EMAIL_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getTaxesNo())){
			tmdealerPO.setString("TAXES_NO", dto.getTaxesNo());
			tmdealerPO.setLong("TAXES_NO_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getErpCode())){
			tmdealerPO.setString("ERP_CODE", dto.getErpCode());
			tmdealerPO.setLong("ERP_CODE_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getBeginBank())){
			tmdealerPO.setString("BEGIN_BANK", dto.getBeginBank());
			tmdealerPO.setLong("BEGIN_BANK_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getBankCode())){
			tmdealerPO.setString("BANK_CODE", dto.getBankCode());
			tmdealerPO.setLong("BANK_CODE_F", 1);
		}
		if(!StringUtils.isNullOrEmpty( dto.getAddress())){
			tmdealerPO.setString("ADDRESS", dto.getAddress());
			tmdealerPO.setLong("ADDRESS_F", 1);
		}
		if(!StringUtils.isNullOrEmpty(dto.getRemark())){
			tmdealerPO.setString("REMARK", dto.getRemark());
			tmdealerPO.setLong("REMARK_F", 1);
		}
		if(!StringUtils.isNullOrEmpty( dto.getGroupId())){
			tmdealerPO.setBigDecimal("GROUP_ID", dto.getGroupId());
			tmdealerPO.setLong("GROUP_ID_F", 1);
		}
		
		if(!StringUtils.isNullOrEmpty(loginUser.getUserId())){
			tmdealerPO.setBigDecimal("UPDATE_BY", loginUser.getUserId());
		
		}
		
		tmdealerPO.setTimestamp("UPDATE_DATE", new Date());
		tmdealerPO.setLong("COMPANY_ID_F", 1);
		return tmdealerPO;
		
	}
	
	/**
	 * 增加经销商信息
	 */
	@Override
	public void addModifyDealer(DealerDetailedinfoDTO dto) throws ServiceBizException {
			boolean flag1 = false;
			LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			TmDealerPO tmdealerPO = new TmDealerPO();
			TmDealerPO tmdealerPO1 = TmDealerPO.findFirst("DEALER_CODE = ?", dto.getDealerCode_());
			TmDealerPO tmdealerPO2 = TmDealerPO.findFirst("COMPANY_ID = ?", dto.getCompanyId());
			if(tmdealerPO1!=null){
				throw new ServiceBizException("经销商代码已存在！");
			}else if(tmdealerPO2!=null){
				throw new ServiceBizException("该经销商公司已经存在同类型的经销商！");
			}
			
			//dto中数据插入po
			tmdealerPO = setTmDealerPO2(tmdealerPO, dto,2);
		
			flag1 = tmdealerPO.saveIt();
			if(flag1){			
			}else{
				throw new ServiceBizException("增加经销商失败！");
			}
			
			TmDealerEdPo ed= new TmDealerEdPo();
			ed = setTmDealerEdPO(ed, tmdealerPO);
			
			flag1 = ed.insert();
			if(flag1){			
			}else{
				throw new ServiceBizException("增加经销商失败！");
			}
			//重新保存经销商业务范围
			TmDealerBussPO po =TmDealerBussPO.findFirst("DEALER_ID = ?", tmdealerPO.getId());
			if(po!=null){
				po.deleteCascadeShallow();
			}
			String[] groupIds = null;
			if(!StringUtils.isNullOrEmpty(dto.getGroupIds())){					
				groupIds = dto.getGroupIds().split(",");
			}
			if(groupIds!=null && groupIds.length>0){
				for(String groupId : groupIds){
					TmDealerBussPO tempPo = new TmDealerBussPO();
					tempPo.setLong("DEALER_ID",tmdealerPO.getId());
					tempPo.setLong("GROUP_ID",new Long(groupId));
					tempPo.setLong("CREATE_BY",loginUser.getUserId());
					tempPo.setTimestamp("CREATE_DATE",new Date());
					flag1 = tempPo.saveIt();
					if(flag1){			
					}else{
						throw new ServiceBizException("增加经销商失败！");
					}
				}
			}
			TtCompanyDetailPO comDetailPO = TtCompanyDetailPO.findFirst("COMPANY_ID = ? ", dto.getCompanyId());
			comDetailPO = comDetailPO == null ? new TtCompanyDetailPO() : comDetailPO;
			this.setTtCompanyDetailPO(comDetailPO, dto);
			flag1 = comDetailPO.saveIt();
			
			if(flag1){			
			}else{
				throw new ServiceBizException("增加经销商失败！");
			}
			//获得经销商与组织关系PO
			TmDealerOrgRelationPO tdorpo=new TmDealerOrgRelationPO();
			tdorpo.setLong("DEALER_ID",tmdealerPO.getLong("DEALER_ID") );
			tdorpo.setLong("ORG_ID",dto.getOrgId());
			tdorpo.setLong("CREATE_BY", tmdealerPO.getLong("CREATE_BY"));
			tdorpo.setTimestamp("CREATE_DATE", tmdealerPO.getDate("CREATE_DATE"));
			flag1 = tdorpo.saveIt();
			if(flag1){			
			}else{
				throw new ServiceBizException("增加经销商失败！");
			}
			
			//新增售后经销商
			TmDealerPO tdpoa = new TmDealerPO();
			tdpoa = setTmDealerPO2(tdpoa, dto,1);
			flag1 = tdpoa.saveIt();
			if(flag1){			
			}else{
				throw new ServiceBizException("增加经销商失败！");
			}
			TmDealerEdPo edA= new TmDealerEdPo();
			edA = setTmDealerEdPO(edA, tdpoa);
			flag1 = edA.insert();
			if(flag1){			
			}else{
				throw new ServiceBizException("增加经销商失败！");
			}
			
			TmDealerOrgRelationPO tdorpo1=new TmDealerOrgRelationPO();
			tdorpo1.setLong("DEALER_ID",tdpoa.getLong("DEALER_ID") );
			tdorpo1.setLong("ORG_ID",dto.getOrgIdA());
			tdorpo1.setLong("CREATE_BY", tdpoa.getLong("CREATE_BY"));
			tdorpo1.setTimestamp("CREATE_DATE", tdpoa.getDate("CREATE_DATE"));
			
			flag1 = tdorpo1.saveIt();
			if(flag1){			
			}else{
				throw new ServiceBizException("增加经销商失败！");
			}
			//将新增信息写入接口信息表(只写销售)
		
			TiDealerInfoPO tdiPo = new TiDealerInfoPO();
			TmCompanyPO tmcompanyPO = TmCompanyPO.findById(tmdealerPO.getLong("COMPANY_ID"));
			if(tmcompanyPO!=null){
				tdiPo.setInteger("DEALER_CODE", Long.parseLong(tmcompanyPO.getString("COMPANY_CODE")));
				tdiPo.setString("DEALER_NAME", tmcompanyPO.getString("COMPANY_NAME"));
				tdiPo.setString("DEALERAB_CN", tmcompanyPO.getString("COMPANY_SHORTNAME"));
				tdiPo.setString("DEALERAB_EN", tmcompanyPO.getString("COMPANY_EN"));
				tdiPo.setInteger("CITY_ID", tmcompanyPO.getInteger("CITY_ID"));
				tdiPo.setString("REALLY_CITY_ID", tmcompanyPO.getInteger("CITY_ID"));
				tdiPo.setString("ADDRESS", tmcompanyPO.getString("ADDRESS"));
				tdiPo.setString("SERVICE_TEL", tmcompanyPO.getString("PHONE"));
				tdiPo.setString("DEALERAB_EN", tmcompanyPO.getString("COMPANY_EN"));
				tdiPo.setInteger("STATUS",1);
				tdiPo.setInteger("IS_SCAN",0);
				tdorpo1.setLong("CREATE_BY", loginUser.getUserId());
				tdorpo1.setTimestamp("CREATE_DATE", new Date());
				
			}
			flag1 = tdorpo1.saveIt();
			if(flag1){			
			}else{
				throw new ServiceBizException("增加经销商失败！");
			}
		}
	
		private TmDealerEdPo setTmDealerEdPO(TmDealerEdPo ed, TmDealerPO tmdealerPO) {
			ed.setBigDecimal("DEALER_ID",tmdealerPO.getLong("DEALER_ID"));
			ed.setLong("OEM_COMPANY_ID",tmdealerPO.getLong("OEM_COMPANY_ID"));
			ed.setString("DEALER_NAME",tmdealerPO.getString("DEALER_NAME"));
			ed.setString("DEALER_SHORTNAME", tmdealerPO.getString("DEALER_SHORTNAME"));
			ed.setBigDecimal("DEALER_GROUP_ID", tmdealerPO.getBigDecimal("DEALER_GROUP_ID"));
			ed.setInteger("DEALER_AUDIT_STATUS", tmdealerPO.getInteger("DEALER_AUDIT_STATUS"));
			ed.setLong("DEALER_ORG_ID", tmdealerPO.getLong("DEALER_ORG_ID"));
			ed.setInteger("DEALER_TYPE", tmdealerPO.getInteger("DEALER_TYPE"));
			ed.setString("MARKETING", tmdealerPO.getString("MARKETING"));
			ed.setInteger("DEALER_STATUS", tmdealerPO.getInteger("DEALER_STATUS"));
			ed.setString("DEALER_CODE", tmdealerPO.getString("DEALER_CODE"));
			ed.setLong("COMPANY_ID",tmdealerPO.getLong("COMPANY_ID"));
			ed.setDate("FOUND_DATE", tmdealerPO.getDate("FOUND_DATE"));
			ed.setInteger("ACURA_GHAS_TYPE",tmdealerPO.getInteger("ACURA_GHAS_TYPE"));
			ed.setInteger("STATUS",tmdealerPO.getInteger("STATUS"));
			ed.setLong("PROVINCE_ID",tmdealerPO.getLong("PROVINCE_ID"));
			ed.setLong("PARENT_DEALER_D", tmdealerPO.getLong("PARENT_DEALER_D"));
			ed.setLong("CITY_ID",tmdealerPO.getLong("CITY_ID"));
			ed.setString("ZIP_CODE",tmdealerPO.getString("ZIP_CODE"));
			ed.setString("PHONE", tmdealerPO.getString("PHONE"));
			ed.setString("LINK_MAN",tmdealerPO.getString("LINK_MAN"));
			ed.setString("LINK_MAN_MOBILE", tmdealerPO.getString("LINK_MAN_MOBILE"));
			ed.setString("FAX_NO", tmdealerPO.getString("FAX_NO"));
			ed.setString("EMAIL", tmdealerPO.getString("EMAIL"));
			ed.setString("TAXES_NO",tmdealerPO.getString("TAXES_NO"));
			ed.setString("ERP_CODE", tmdealerPO.getString("ERP_CODE"));
			ed.setString("BEGIN_BANK", tmdealerPO.getString("BEGIN_BANK"));
			ed.setString("BANK_CODE", tmdealerPO.getString("BANK_CODE"));
			ed.setInteger("RANGES",tmdealerPO.getInteger("RANGES"));
			ed.setString("CC_CODE",tmdealerPO.getString("CC_CODE"));
			ed.setString("FAX_NO", tmdealerPO.getString("FAX_NO"));
			ed.setString("EMAIL", tmdealerPO.getString("EMAIL"));
			ed.setString("TAXES_NO", tmdealerPO.getString("TAXES_NO"));
			ed.setString("ERP_CODE", tmdealerPO.getString("ERP_CODE"));
			ed.setString("LINE_NUMBER",tmdealerPO.getString("LINE_NUMBER"));
			if(tmdealerPO.getInteger("DEALER_TYPE").equals(OemDictCodeConstants.DEALER_TYPE_DVS)){				
				ed.setInteger("IS_EC",tmdealerPO.getInteger("IS_EC"));
			}else{
				ed.setInteger("IS_EC",OemDictCodeConstants.IF_TYPE_NO);
			}
			
//			ed.setInteger("IS_FIAT",tmdealerPO.getInteger("IS_FIAT"));
//			ed.setInteger("IS_CJD",tmdealerPO.getInteger("IS_CJD"));
			ed.setLong("CREATE_BY", tmdealerPO.getLong("CREATE_BY"));
			ed.setTimestamp("CREATE_DATE", tmdealerPO.getDate("CREATE_DATE"));
			return ed;
		}
	
		/**
		 * 获取小区信息
		 */
		@Override
		public List<Map> getSmallOrg(Map<String, String> queryParams) throws ServiceBizException {
			return dealerDao.getSmallOrgInfos(queryParams);
		}
		/**
		 * 获取小区信息
		 */
		@Override
		public PageInfoDto getSmallOrg1(Map<String, String> queryParams) throws ServiceBizException {
			return dealerDao.getSmallOrgInfo(queryParams);
		}
		/**
		 * 获取经销商集团
		 */
		@Override
		public PageInfoDto getDealerGroupInfos(Map<String, String> queryParams) throws ServiceBizException {
			
			return dealerDao.DealerGroupInfos(queryParams);
		}
		/**
		 * 关键岗位人员信息查询
		 */
		@Override
		public PageInfoDto getDealerKeyPersonOTD(Long dealerId)throws ServiceBizException {
			
			return dealerDao.dealerKeyPersonOTDs(dealerId);
		}
		/**
		 * 经销商审核通过
		 */
		public void queryDealerPass(Map<String, String> queryParams)  {
			String dealerIds = queryParams.get("dealerId");
			dealerIds = dealerIds.replace(",", "','");
			dealerDao.updateDealerEd(dealerIds);//update 经销商基本信息表
			dealerDao.updateCompanyDetail(dealerIds);//update 经销商入网信息表
			dealerDao.updateDealerPersonal(dealerIds);//update 经销商人员表
			dealerDao.updateDealerShowroom(dealerIds);//update 经销商展厅
		}
		/**
		 * 经销商审核驳回
		 */
		@Override
		public void queryDealerReject(Map<String, String> queryParams) throws ServiceBizException {
			String dealerIds = queryParams.get("dealerId");
			dealerIds = dealerIds.replace(",", "','");
			TmDealerEdPo.update("DEALER_AUDIT_STATUS = ?,DEALER_AUDIT_DATE = ?", "DEALER_ID in ('"+dealerIds+"')", 
					OemDictCodeConstants.DEALER_AUDIT_STATE_04,new Date());
		}
		/**
		 * 根据ID审核通过
		 */
		@Override
		public void queryDealerPass(Long dealerId) throws ServiceBizException {
			
			TmDealerEdPo tmdealerEdPO =TmDealerEdPo.findById(dealerId);
			boolean flag = false;
			tmdealerEdPO.setLong("DEALER_AUDIT_STATUS",OemDictCodeConstants.DEALER_AUDIT_STATE_03);
			tmdealerEdPO.setTimestamp("DEALER_AUDIT_DATE",new Date());
			flag = tmdealerEdPO.saveIt();
			if(flag){
			}else{
				throw new ServiceBizException("审核通过失败!");
			}
		}
		/**
		 * 根据ID审核驳回
		 */
		@Override
		public void queryDealerReject(Long dealerId) throws ServiceBizException {
			TmDealerEdPo tmdealerEdPO =TmDealerEdPo.findById(dealerId);
			boolean flag = false;
			tmdealerEdPO.setLong("DEALER_AUDIT_STATUS",OemDictCodeConstants.DEALER_AUDIT_STATE_04);
			tmdealerEdPO.setTimestamp("DEALER_AUDIT_DATE",new Date());
			flag = tmdealerEdPO.saveIt();
			if(flag){
			}else{
				throw new ServiceBizException("审核驳回失败!");
			}
			
		}
		/**
		 * 基本经销商信息(dlr)
		 */
		@Override
		public Map<String, Object> queryDealerInfoDetail1(Long dealerId) throws ServiceBizException {

			TmDealerEdPo dealerPO = TmDealerEdPo.findById(dealerId);
			Map<String, Object> mapA = dealerPO.toMap();
			mapA.remove("phone");
			Long comPanyId = dealerPO.getLong("COMPANY_ID");
		
			
			TmCompanyPO companyPO = TmCompanyPO.findFirst("COMPANY_ID = ?",comPanyId);
			if(companyPO!=null){
				Map<String, Object> mapB = companyPO.toMap();
				mapB.remove("phone");
				mapA.putAll(mapB);
			}
			
		    TtCompanyDetailPO comPanyDetailPO  = TtCompanyDetailPO.findFirst("COMPANY_ID = ?", comPanyId);
		    if(comPanyDetailPO!=null){
				Map<String, Object> mapC = comPanyDetailPO.toMap();
				
				mapA.putAll(mapC);
			}
		    Map<String, Object> mapD=getDealerOrgCodeAndId(dealerId);
		    if(mapD!=null){
		    	mapA.putAll(mapD);
		    }
			return mapA;
		}
		
		/**
		 * 明细修改
		 */
		@Override
		public void editdlrModifyDealerInfos(DealerDetailedinfoDTO dto, Long dealerId) {
			boolean flag1 = false;
			
			LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			int updFlag = 1;
			
			TmDealerEdPo tmdealerEdPO = TmDealerEdPo.findById(dealerId);
			tmdealerEdPO = setTmDealerEdPo(tmdealerEdPO, dto,updFlag);
			
			flag1 = tmdealerEdPO.saveIt();
			if(flag1){			
			}else{
				throw new ServiceBizException("数据库修改数据失败！");
			}
			TmCompanyPO tmcompanyPO = TmCompanyPO.findById(tmdealerEdPO.getLong("COMPANY_ID"));
			//dto数据插入po
			if(!StringUtils.isNullOrEmpty(dto.getCompanyShortname())){
				tmcompanyPO.setString("BEGIN_BANK", dto.getCompanyShortname());
				
			}
			if(!StringUtils.isNullOrEmpty(loginUser.getUserId())){
				tmcompanyPO.setBigDecimal("UPDATE_BY", loginUser.getUserId());
			
			}
			
			tmcompanyPO.setTimestamp("UPDATE_DATE", new Date());
				
			
		
			//将新增的经销商信息插入公司表
			flag1 = tmcompanyPO.saveIt();
			if(flag1){			
			}else{
				throw new ServiceBizException("数据库修改数据失败！");
			}
			TtCompanyDetailPO tcdPO = TtCompanyDetailPO.findFirst("COMPANY_ID = ?", tmdealerEdPO.getLong("COMPANY_ID"));
			tcdPO = setTcdPO(tcdPO, dto,updFlag);
			flag1 = tcdPO.saveIt();
			if(flag1){			
			}else{
				throw new ServiceBizException("数据库修改数据失败！");
			}
			
			
		}
		/**
		 * 得到 TtCompanyDetailPO 
		 * @param tcdPO
		 * @param dto
		 * @param updFlag
		 * @return
		 */
		private TtCompanyDetailPO setTcdPO(TtCompanyDetailPO tcdPO, DealerDetailedinfoDTO dto, int updFlag) {
			if(tcdPO != null){
				if(!CommonUtils.checkNull(dto.getCompanyNo()).equals(tcdPO.getString("COMPANY_NO")) ){
					tcdPO.setString("COMPANY_NO", dto.getCompanyNo());
					tcdPO.setInteger("COMPANY_NO_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getLegalRepresentative()).equals(tcdPO.getString("LEGAL_REPRESENTATIVE")) ){
					tcdPO.setString("LEGAL_REPRESENTATIVE", dto.getLegalRepresentative());
					tcdPO.setInteger("LEGAL_REPRESENTATIVE_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getBuildStatus()).equals(tcdPO.getString("BUILD_STATUS"))){
					tcdPO.setString("BUILD_STATUS", dto.getBuildStatus());
					tcdPO.setInteger("BUILD_STATUS_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getCompletionDate()).equals(tcdPO.getString("COMPLETION_DATE"))){
					tcdPO.setDate("COMPLETION_DATE", dto.getCompletionDate());
					tcdPO.setInteger("COMPLETION_DATE_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getStartDate()).equals(tcdPO.getString("START_DATE"))){
					tcdPO.setDate("START_DATE", dto.getStartDate());
					tcdPO.setInteger("START_DATE_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getShowroomArea()).equals(tcdPO.getString("SHOWRROM_AREA"))){
					tcdPO.setInteger("SHOWRROM_AREA", dto.getShowroomArea());
					tcdPO.setInteger("SHOWRROW_AREA_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getShowroomWidth()).equals(tcdPO.getString("SHOWROOM_WIDTH"))){
					tcdPO.setDouble("SHOWROOM_WIDTH", dto.getShowroomWidth());
					tcdPO.setInteger("SHOWROOM_WIDTH_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getShowCars()).equals(tcdPO.getString("SHOW_CARS"))){
					tcdPO.setInteger("SHOW_CARS", dto.getShowCars());
					tcdPO.setInteger("SHOW_CARS_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getServiceNum()).equals(tcdPO.getString("SERVICE_NUM"))){
					tcdPO.setInteger("SERVICE_NUM", dto.getServiceNum());
					tcdPO.setInteger("SERVICE_NUM_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getMachineRepair()).equals(tcdPO.getString("MACHINE_REPAIR"))){
					tcdPO.setInteger("MACHINE_REPAIR", dto.getMachineRepair());
					tcdPO.setInteger("MACHINE_REPAIR_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getSheetSpray()).equals(tcdPO.getString("SHEET_SPRAY"))){
					tcdPO.setInteger("SHEET_SPRAY", dto.getSheetSpray());
					tcdPO.setInteger("SHEET_SPRAY_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getPreflightNum()).equals(tcdPO.getString("PREFLIGHT_NUM"))){
					tcdPO.setInteger("PREFLIGHT_NUM", dto.getPreflightNum());
					tcdPO.setInteger("PREFLIGHT_NUM_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getReservedNum()).equals(tcdPO.getString("RESERVED_NUM"))){
					tcdPO.setInteger("RESERVED_NUM", dto.getReservedNum());
					tcdPO.setInteger("RESERVED_NUM_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getStopCar()).equals(tcdPO.getString("STOP_CAR"))){
					tcdPO.setString("STOP_CAR", dto.getStopCar());
					tcdPO.setInteger("STOP_CAR_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getLand()).equals(tcdPO.getString("LAND"))){
					tcdPO.setString("LAND", dto.getLand());
					tcdPO.setInteger("LAND_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getLandNature()).equals(tcdPO.getString("LAND_NATURE"))){
					tcdPO.setString("LAND_NATURE", dto.getLandNature());
					tcdPO.setInteger("LAND_NATURE_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getPartsArea()).equals(tcdPO.getString("PARTS_AREA"))){
					tcdPO.setDouble("PARTS_AREA", dto.getPartsArea());
					tcdPO.setInteger("PARTS_ARES_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getDangerArea()).equals(tcdPO.getString("DANGER_AREA"))){
					tcdPO.setDouble("DANGER_AREA", dto.getDangerArea());
					tcdPO.setInteger("DANGER_AREA_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getRate()).equals(tcdPO.getString("RATE"))){
					tcdPO.setString("RATE", dto.getRate());
					tcdPO.setInteger("RATE_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getRegisteredCapital()).equals(tcdPO.getString("REGISTERED_CAPITAL"))){
					tcdPO.setInteger("REGISTERED_CAPITAL", dto.getRegisteredCapital());
					tcdPO.setInteger("REGISTERED_CAPITAL_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getCompanyId()).equals(tcdPO.getString("COMPANY_INVESTORS"))){
					tcdPO.setString("COMPANY_INVESTORS", dto.getCompanyInvestors());
					tcdPO.setInteger("COMPANY_INVERSTORS_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getInvestorsTel()).equals(tcdPO.getString("INVESTORS_TEL"))){
					tcdPO.setString("INVESTORS_TEL", dto.getInvestorsTel());
				}
				if(!CommonUtils.checkNull(dto.getBrands()).equals(tcdPO.getString("BRANDS"))){
					tcdPO.setString("BRANDS", dto.getBrands());
					tcdPO.setInteger("BRANDS_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getInvestorsEmial()).equals(tcdPO.getString("INVESTORS_EMIAL"))){
					tcdPO.setString("INVESTORS_EMIAL", dto.getInvestorsEmial());
//				tcdPO.setInteger("INVESTORS_EMIAL_F", 1);
				}
				if(!CommonUtils.checkNull(dto.getMaintainability()).equals(tcdPO.getString("MAINTAINABILITY"))){
					tcdPO.setInteger("MAINTAINABILITY",dto.getMaintainability());
					tcdPO.setInteger("MAINTAINABILITY_F", 1);
				}
			}else{
				tcdPO = new TtCompanyDetailPO();
				if(!StringUtils.isNullOrEmpty(dto.getCompanyNo())){
					tcdPO.setString("COMPANY_NO", dto.getCompanyNo());
				}
				if(!StringUtils.isNullOrEmpty(dto.getCompanyLevel())){					
					tcdPO.setString("COMPANY_LEVEL", dto.getCompanyLevel());
				}
				if(!StringUtils.isNullOrEmpty(dto.getLegalRepresentative())){
					tcdPO.setString("LEGAL_REPRESENTATIVE", dto.getLegalRepresentative());
				}
				if(!StringUtils.isNullOrEmpty(dto.getBuildStatus())){
					tcdPO.setString("BUILD_STATUS", dto.getBuildStatus());
				}
				if(!StringUtils.isNullOrEmpty(dto.getCompletionDate())){
					tcdPO.setDate("COMPLETION_DATE", dto.getCompletionDate());
				}
				if(!StringUtils.isNullOrEmpty(dto.getStartDate())){
					tcdPO.setDate("START_DATE", dto.getStartDate());
				}
				if(!StringUtils.isNullOrEmpty(dto.getShowroomArea())){
					tcdPO.setInteger("SHOWRROM_AREA", dto.getShowroomArea());
				}
				if(!StringUtils.isNullOrEmpty(dto.getShowroomWidth())){
					tcdPO.setDouble("SHOWROOM_WIDTH", dto.getShowroomWidth());
				}
				if(!StringUtils.isNullOrEmpty( dto.getShowCars())){
					tcdPO.setInteger("SHOW_CARS", dto.getShowCars());
				}
				if(!StringUtils.isNullOrEmpty(dto.getServiceNum())){
					tcdPO.setInteger("SERVICE_NUM", dto.getServiceNum());
				}
				if(!StringUtils.isNullOrEmpty(dto.getMachineRepair())){
					tcdPO.setInteger("MACHINE_REPAIR", dto.getMachineRepair());
				}
				if(!StringUtils.isNullOrEmpty(dto.getSheetSpray())){
					tcdPO.setInteger("SHEET_SPRAY", dto.getSheetSpray());
				}
				if(!StringUtils.isNullOrEmpty(dto.getPreflightNum())){
					tcdPO.setInteger("PREFLIGHT_NUM", dto.getPreflightNum());
				}
				if(!StringUtils.isNullOrEmpty(dto.getReservedNum())){
					tcdPO.setInteger("RESERVED_NUM", dto.getReservedNum());
				}
				if(!StringUtils.isNullOrEmpty(dto.getStopCar())){
					tcdPO.setString("STOP_CAR", dto.getStopCar());
				}
				if(!StringUtils.isNullOrEmpty(dto.getLand())){
					tcdPO.setString("LAND", dto.getLand());
				}
				if(!StringUtils.isNullOrEmpty(dto.getLandNature())){
					tcdPO.setString("LAND_NATURE", dto.getLandNature());
				}
				if(!StringUtils.isNullOrEmpty(dto.getPartsArea())){
					tcdPO.setDouble("PARTS_AREA", dto.getPartsArea());
				}
				if(!StringUtils.isNullOrEmpty(dto.getDangerArea())){
					tcdPO.setDouble("DANGER_AREA", dto.getDangerArea());
				}
				if(!StringUtils.isNullOrEmpty(dto.getRate())){
					tcdPO.setString("RATE", dto.getRate());
				}
				if(!StringUtils.isNullOrEmpty(dto.getRegisteredCapital())){
					tcdPO.setInteger("REGISTERED_CAPITAL", dto.getRegisteredCapital());
				}
				if(!StringUtils.isNullOrEmpty(dto.getCompanyInvestors())){
					tcdPO.setString("COMPANY_INVESTORS", dto.getCompanyInvestors());
				}
				if(!StringUtils.isNullOrEmpty( dto.getInvestorsTel())){
					tcdPO.setString("INVESTORS_TEL", dto.getInvestorsTel());
				}
				if(!StringUtils.isNullOrEmpty(dto.getBrands())){
					tcdPO.setString("BRANDS", dto.getBrands());
				}
				if(!StringUtils.isNullOrEmpty(dto.getInvestorsEmial())){
					tcdPO.setString("INVESTORS_EMIAL", dto.getInvestorsEmial());
				}
				if(!StringUtils.isNullOrEmpty(dto.getMaintainability())){
					tcdPO.setInteger("MAINTAINABILITY",dto.getMaintainability());
				}

			}
			
			return tcdPO;
		}
		@Override
		public void dealerInfoSubmitUpdate(Long dealerId) throws ServiceBizException {
			LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			TmDealerEdPo valuePO = TmDealerEdPo.findById(dealerId);
			valuePO.setLong("DEALER_STATUS",OemDictCodeConstants.DEALER_STATE_02);
			valuePO.setLong("DEALER_AUDIT_STATUS",OemDictCodeConstants.DEALER_AUDIT_STATE_02);
			if(loginUser.getUserId()!=null){
				valuePO.setLong("UPDATE_BY",loginUser.getUserId());
			}
		
			valuePO.setTimestamp("UPDATE_DATE",new Date());
			boolean flag = valuePO.saveIt();
			if(flag){
			}else{
				throw new ServiceBizException("经销商上报失败！");
			}
		}
		/**
		 * 查询展厅信息
		 */
		@Override
		public PageInfoDto findDealerShowroom(Long dealerId) throws ServiceBizException {
			
			return dealerDao.dealerShowroom(dealerId);
		}
		
		/**
		 * 查询经销商公司店面照片
		 */
		@Override
		public PageInfoDto findCompanyPhoto(String ywzj) throws ServiceBizException {
			
			return dealerDao.companyPhoto(ywzj);
		}
		
		/**
		 * 经销商维护  导入临时表
		 */
		@Override
		public ArrayList<DealerMaintainImportDTO> checkData() {
			ArrayList<DealerMaintainImportDTO> errorList = new ArrayList<>();
			//校验经销商代码和车系代码是否为空
			List<Map> isNullList = dealerDao.checkDealerOrGroupIsNull();
			if(null!=isNullList && isNullList.size()>0){
				for (int i = 0; i < isNullList.size(); i++) {
					Map<String, Object> map = isNullList.get(i);
					DealerMaintainImportDTO dto = new DealerMaintainImportDTO();
					dto.setRowNO(Integer.parseInt(String.valueOf(map.get("ROW_NO"))));
					dto.setErrorMsg("经销商代码和车系代码不能为空！");
					errorList.add(dto);
				}
			}
			//检查经销商代码是否正确
			if(errorList.size()==0){
				List<Map> li = dealerDao.checkDealer();
				for(Map<String,Object> m:li){
					DealerMaintainImportDTO dto = new DealerMaintainImportDTO();
					dto.setRowNO(Integer.parseInt(String.valueOf(m.get("ROW_NO"))));
					dto.setErrorMsg("经销商代码："+m.get("DEALER_CODE")+" 不存在！");
					errorList.add(dto);
				}
			}
			//检查车系代码是否正确
			if(errorList.size()==0){
				List<Map> li = dealerDao.checkGroup();
				for(Map<String,Object> m:li){
					DealerMaintainImportDTO dto = new DealerMaintainImportDTO();
					dto.setRowNO(Integer.parseInt(String.valueOf(m.get("ROW_NO"))));
					dto.setErrorMsg("车系代码："+m.get("GROUP_CODE")+" 不存在！");
					errorList.add(dto);
				}
			}
			return errorList;
		}
		
		/**
		 * 导入正式表
		 */
		@Override
		public void importSave() {
			dealerDao.importSave();
			
		}
		
		@Override
		public PageInfoDto queryCom(Map<String, String> params) {
			return dealerDao.queryCom(params);
		}
		
		/**
		 * 获取销售和售后区域
		 */
		@Override
		public List<Map> getAllOrg(String bussType) {
			List<Map> list = dealerDao.getAllOrg(bussType);
			return list;
		}
		
		@Override
		public PageInfoDto doSearchCompanyPhoto7(Map<String,String> param) {
			PageInfoDto dto = dealerDao.doSearchCompanyPhoto7(param);		
			return dto;
		}
		
		/**
		 * 附件上传
		 */
		@Override
		public void uploadFiles(DealerDetailedinfoDTO dto) {
			Long dealerId = dto.getDealerId();
			TmDealerPO dealer = TmDealerPO.findById(dealerId);
			TtCompanyDetailPO comDetail = TtCompanyDetailPO.findFirst("COMPANY_ID = ? ", dealer.getBigDecimal("COMPANY_ID"));
			Integer uploadType = dto.getUploadType();
			String num = null;
			switch (uploadType) {
			case 1:	
				num = comDetail.getString("COMPANY_PHOTO");
				num = StringUtils.isNullOrEmpty(num) ? this.getRamdomId() : num;
				comDetail.setString("COMPANY_PHOTO", num);
				break;
			case 2:
				num = comDetail.getString("BUSINESS_LICENSE");
				num = StringUtils.isNullOrEmpty(num) ? this.getRamdomId() : num;
				comDetail.setString("BUSINESS_LICENSE", num);
				break;
			case 3:
				num = comDetail.getString("ORGANIZATION_CHART");
				num = StringUtils.isNullOrEmpty(num) ? this.getRamdomId() : num;
				comDetail.setString("ORGANIZATION_CHART", num);
				break;
			case 4:
				num = comDetail.getString("COPIES_OF_MANDA");
				num = StringUtils.isNullOrEmpty(num) ? this.getRamdomId() : num;
				comDetail.setString("COPIES_OF_MANDA", num);
				break;
			case 5:
				num = comDetail.getString("TAX_CERTIFICATE");
				num = StringUtils.isNullOrEmpty(num) ? this.getRamdomId() : num;
				comDetail.setString("TAX_CERTIFICATE", num);
				break;
			case 6:
				num = comDetail.getString("FINANCIAL_STATEMENT");
				num = StringUtils.isNullOrEmpty(num) ? this.getRamdomId() : num;
				comDetail.setString("FINANCIAL_STATEMENT", num);
				break;
			case 7:
				num = comDetail.getString("CONTRACT_NO");
				num = StringUtils.isNullOrEmpty(num) ? this.getRamdomId() : num;
				comDetail.setString("CONTRACT_NO", num);
				break;
			}
			boolean flag = comDetail.saveIt();
			fileStoreService.updateFileUploadInfo(dto.getDmsFileIds(), num, DictCodeConstants.FILE_TYPE_USER_INFO);
			
			
		}
		
		private String getRamdomId(){
			int fourNo = (int) (Math.random()*9000+1000);//随机4位数
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddsss");//设置日期格式
			String timeNow = df.format(new Date());// new Date()为获取当前系统时间
			String no = timeNow+fourNo;
			return no;
		}
		
		/**
		 * 新增经销商展厅
		 */
		@Override
		public void addDealerShowRoom(DealerDetailedinfoDTO dto) {
			LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			Date currentTime = new Date();
			TmVsDealerShowroomPO po = new TmVsDealerShowroomPO();
			po = getDealerShowRoomPO(po,dto);
			po.setLong("CREATE_BY", loginUser.getUserId());
			po.setTimestamp("CREATE_DATE", currentTime);
			boolean flag = po.saveIt();
		}
		
		/**
		 * 修改经销商展厅
		 * @param dto
		 */
		@Override
		public void editDealerShowRoom(DealerDetailedinfoDTO dto) {
			LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			Date currentTime = new Date();
			TmVsDealerShowroomPO po = TmVsDealerShowroomPO.findById(dto.getDealerRoomId());
			po = getDealerShowRoomPO(po,dto);
			po.setLong("UPDATE_BY", loginUser.getUserId());
			po.setTimestamp("UPDATE_DATE", currentTime);
			boolean flag = po.saveIt();
			
		}
		
		private TmVsDealerShowroomPO getDealerShowRoomPO(TmVsDealerShowroomPO po, DealerDetailedinfoDTO dto) {
			po.setLong("DEALER_ID", dto.getDealerId());
			po.setString("ADDRESS", dto.getAddress());
			po.setInteger("ZIP_CODE", Integer.parseInt(dto.getZipCode()));
			po.setString("OPEN_TIME", dto.getOpenTime());
			po.setString("RX_PHONE", dto.getRxPhone());
			po.setString("OFFICE_PHONE", dto.getOfficePhone());
			po.setString("FAX", dto.getFax());
			po.setString("LINK_MAN", dto.getLinkMan());
			po.setString("LINK_PHONE", dto.getLinkPhone());
			po.setString("BUNINESS_TIME", dto.getBuninessTime());
			po.setDouble("SHOWROOM_WIDTH", dto.getShowroomWidth());
			po.setDouble("SHOWROOM_AREA", dto.getShowroomArea());
			//将数组转化为，分割
			String[] brands = dto.getBrand();
			String brand = "";
			if(brands!= null && brands.length > 0){
				for(String id : brands){
					brand += id + ",";
				}
				brand = brand.substring(0, brand.length()-1);
			}
			po.setString("BRAND", brand);
			po.setInteger("SHOWCAR_NUM", dto.getShowcarNum());
			po.setInteger("STOPPING_NUM", dto.getStoppingNum());
			po.setString("LAND_PRO", dto.getLandPro());
			po.setString("LAND_BUY_OF_RENT", dto.getLandBuyOfRent());
			po.setInteger("STATUS", dto.getStatus());
			po.setInteger("WEIXIU_NUM", dto.getWeixiuNum());
			po.setInteger("JIXIU_NUM", dto.getJixiuNum());
			po.setInteger("BANPEN_NUM", dto.getBanpenNum());
			po.setInteger("YUJIAN_NUM", dto.getYujianNum());
			po.setInteger("YULIU_NUM", dto.getYuliuNum());
			po.setDouble("AFTERSALE_AREA", dto.getAftersaleArea());
			po.setDouble("DANGER_AREA", dto.getDangerArea());
			return po;
		}
		@Override
		public void delDealerShowRoom(Long id) {
			LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			Date currentTime = new Date();
			TmVsDealerShowroomPO po = TmVsDealerShowroomPO.findById(id);
			po.setInteger("STATUS", OemDictCodeConstants.STATUS_DISABLE);
			po.setLong("UPDATE_BY", loginUser.getUserId());
			po.setTimestamp("UPDATE_DATE", currentTime);
			boolean flag = po.saveIt();
		}
		@Override
		public List<Map> queryComAll(Map<String, String> params) {
			return dealerDao.queryComAll(params);
		}
		@Override
		public List<Map> findSeries() {
			List<Map> list = dealerDao.queryGroupInfo();
			return list;
		}


}
