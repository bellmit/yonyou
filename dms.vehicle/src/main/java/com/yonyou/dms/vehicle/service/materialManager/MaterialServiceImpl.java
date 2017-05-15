package com.yonyou.dms.vehicle.service.materialManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.de.CLDCS002;
import com.yonyou.dcs.de.CLDCS003;
import com.yonyou.dcs.gacfca.CLDCS002Cloud;
import com.yonyou.dcs.gacfca.CLDCS003Cloud;
import com.yonyou.dms.common.domains.PO.basedata.MaterialGroupRPO;
import com.yonyou.dms.common.domains.PO.basedata.MaterialInPO;
import com.yonyou.dms.common.domains.PO.basedata.MaterialPO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.materialManager.MaterialDao;
import com.yonyou.dms.vehicle.dao.materialManager.MaterialGroupDao;
import com.yonyou.dms.vehicle.domains.DTO.materialManager.MaterialDTO;

@Service
public class MaterialServiceImpl implements MaterialService {
	
	@Autowired
	MaterialGroupDao mgDao;
	
	@Autowired
	MaterialDao dao;
	
	@Autowired
	private CLDCS002 cldcs002;
	
	@Autowired
	private CLDCS002Cloud cldcs002Cloud;
	
	@Autowired
	private CLDCS003 cldcs003;
	
	@Autowired
	private CLDCS003Cloud cldcs003Cloud;
	
	/**
	 * 加载查询
	 */
	@Override
	public PageInfoDto queryList(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryList(queryParam);
		return pgInfo;
	}
	
	/**
	 * 下载查询
	 */
	@Override
	public List<Map> queryMaterialGroupForExport(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> list = dao.queryMaterialGroupForExport(queryParam);
		return list;
	}
	/**
	 * 根据ID获取物料组信息
	 */
	@Override
	public MaterialPO getById(Long id) throws ServiceBizException {
		return MaterialPO.findById(id);
	}
	/**
	 * 根据ID获取物料关系信息
	 */
	@Override
	public MaterialGroupRPO getGroupById(Long id) throws ServiceBizException {
		return MaterialGroupRPO.findFirst("MATERIAL_ID = ?",id);
	}
	
	/**
	 * 新增物料组信息
	 */
	@Override
	public Long addMaterialGroup(MaterialDTO mgDto) throws ServiceBizException {
		MaterialPO mgPO = new MaterialPO();
		//验证是否有物料信息存在
		Boolean flag = dao.checkCode(mgDto);
		if(!flag){
			throw new ServiceBizException("物料已存在，请重新输入！");
		}
		setPO(mgPO,mgDto,1);
		mgPO.saveIt();
		Long billId = mgPO.getLongId();
		mgDto.setMaterialId(billId);
		
		//插入物料组关系表
		MaterialGroupRPO mgrPO = new MaterialGroupRPO();
		mgrPO.setLong("MATERIAL_ID", billId);
		setDetailPO(mgrPO, mgDto, 1);
		mgrPO.saveIt();
		
		//写入物料接口表
		MaterialInPO po = new MaterialInPO();
		setInPo(po,mgDto,1);
		po.saveIt();
		
		return billId;
	}
	
	/**
	 * 物料数据映射
	 * @param mgPO
	 * @param mgDto
	 */
	private void setPO(MaterialPO mgPO, MaterialDTO mgDto,int type) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		mgPO.setString("COMPANY_ID", new Long("2010010100070674"));
		mgPO.setString("MATERIAL_CODE", mgDto.getMaterialCode());
		mgPO.setString("MATERIAL_NAME", mgDto.getMaterialName());
		mgPO.setString("COLOR_CODE", mgDto.getColorCode());
		mgPO.setString("COLOR_NAME", mgDto.getColorName());
		mgPO.setString("TRIM_CODE", mgDto.getTrimCode());
		mgPO.setString("TRIM_NAME", mgDto.getTrimName());
		mgPO.setString("IS_SALES", mgDto.getIsSales());
		mgPO.setInteger("STATUS", mgDto.getStatus());
		mgPO.setInteger("IS_EC", mgDto.getIsEc());
		if(type==1){
			mgPO.setInteger("CREATE_BY", loginInfo.getUserId());
			mgPO.setTimestamp("CREATE_DATE", new Date());
		}else{
			mgPO.setInteger("UPDATE_BY", loginInfo.getUserId());
			mgPO.setTimestamp("UPDATE_DATE", new Date());
		}
		
	}
	
	/**
	 * 物料关系数据映射
	 * @param mgPO
	 * @param mgDto
	 */
	private void setDetailPO(MaterialGroupRPO mgrPO, MaterialDTO mgDto,int type) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		mgrPO.setString("GROUP_ID", mgDto.getGroupId());
		if(type==1){
			mgrPO.setInteger("CREATE_BY", loginInfo.getUserId());
			mgrPO.setTimestamp("CREATE_DATE", new Date());
		}else{
			mgrPO.setInteger("UPDATE_BY", loginInfo.getUserId());
			mgrPO.setTimestamp("UPDATE_DATE", new Date());
		}
		
	}
	/**
	 * 物料接口数据映射
	 * @param mgPO
	 * @param mgDto
	 */
	private void setInPo(MaterialInPO miPO, MaterialDTO mgDto,int type) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		miPO.setLong("MATERIAL_ID", mgDto.getMaterialId());
		miPO.setString("COLOUR_CODE", mgDto.getColorCode());
		miPO.setString("COLOUR_NAME", mgDto.getColorName());
		if(mgDto.getStatus()==DictCodeConstants.STATUS_IS_VALID){
			miPO.setInteger("STATUS", 1);
		}else{
			miPO.setInteger("STATUS", 0);
		}
		miPO.setInteger("IS_SCAN", 0);
		TmVhclMaterialGroupPO po1 = TmVhclMaterialGroupPO.findById(mgDto.getGroupId());
		miPO.setString("CARSTYLE_ID", po1.getString("TREE_CODE"));
		miPO.setInteger("CARSTYLE_LEVEL", po1.getInteger("GROUP_LEVEL"));
		miPO.setString("CARSTYLE_NAME", po1.getString("GROUP_NAME"));
		TmVhclMaterialGroupPO po2 = TmVhclMaterialGroupPO.findById(po1.getString("PARENT_GROUP_ID"));
		miPO.setString("MODEL_ID", po2.getString("TREE_CODE"));
		miPO.setInteger("MODEL_LEVEL", po2.getInteger("GROUP_LEVEL"));
		miPO.setString("MODEL_CODE", po2.getString("GROUP_CODE"));
		miPO.setString("MODEL_NAME", po2.getString("GROUP_NAME"));
		TmVhclMaterialGroupPO po3 = TmVhclMaterialGroupPO.findById(po2.getString("PARENT_GROUP_ID"));
		miPO.setString("SERIES_ID", po3.getString("TREE_CODE"));
		miPO.setInteger("SERIES_LEVEL", po3.getInteger("GROUP_LEVEL"));
		miPO.setString("SERIES_CODE", po3.getString("GROUP_CODE"));
		miPO.setString("SERIES_NAME", po3.getString("GROUP_NAME"));
		TmVhclMaterialGroupPO po4 = TmVhclMaterialGroupPO.findById(po3.getString("PARENT_GROUP_ID"));
		miPO.setString("BRAND_ID", po4.getString("TREE_CODE"));
		miPO.setInteger("BRAND_LEVEL", po4.getInteger("GROUP_LEVEL"));
		miPO.setString("BRAND_CODE", po4.getString("GROUP_CODE"));
		miPO.setString("BRAND_NAME", po4.getString("GROUP_NAME"));
		if(type==1){
			miPO.setInteger("CREATE_BY", loginInfo.getUserId());
			miPO.setTimestamp("CREATE_DATE", new Date());
		}else{
			miPO.setInteger("UPDATE_BY", loginInfo.getUserId());
			miPO.setTimestamp("UPDATE_DATE", new Date());
		}
		
	}

	/**
	 * 修改物料组信息
	 */
	@Override
	public void ModifyMaterialGroup(Long id, MaterialDTO mgDto) throws ServiceBizException {
		mgDto.setMaterialId(id);
	   //先修改物料表数据
	   MaterialPO po = MaterialPO.findById(id);
	   setPO(po,mgDto,2);
	   po.saveIt();
	   //修改关系表
	   MaterialGroupRPO po2 = getGroupById(id);
	   setDetailPO(po2, mgDto, 2);
	   po2.saveIt();
	   //修改接口表
	   MaterialInPO po3 = MaterialInPO.findFirst(" MATERIAL_ID = ? ", id);
	   if(po3 == null){
		   po3 = new MaterialInPO();
	   }
	   setInPo(po3,mgDto,2);
	   po3.saveIt();
	}

	/**
	 * 查询所有经销商
	 */
	@Override
	public List<Map> getDealerList(Map<String, String> queryParams) {
		List<Map> list = dao.getDealerList(queryParams);
		return list;
	}

	/**
	 * 查询业务范围
	 */
	@Override
	public List<Map> getDealerBuss() {
		return dao. getDealerBussInfo();
	}

	/**
	 * 物料组下发
	 */
	@Override
	public void sendMaterialGroup(MaterialDTO dto) {
		Integer isAllDealer = dto.getIsAllDealer();
		String dealerId = dto.getDealerIds();
		String buss = dto.getBussIds();
		//获取经销商Code
		List<String> dealerCodes = new ArrayList<String>();
		//获取页面传过来的物料ID
		String[] bussIds = buss.split(",");
		if(isAllDealer.intValue() == 0){
			List<TmDealerPO> list = TmDealerPO.findAll();
			if(list != null && !list.isEmpty()){
				for(TmDealerPO po : list){
					dealerCodes.add(po.getString("DEALER_CODE"));
				}
			}
		}else{
			//获取页面传过来的经销商Id
			String[] dealerIds = dealerId.split(",");
			if(dealerIds != null && dealerIds.length > 0){
				for(String id :dealerIds){
					dealerCodes.add(id);
				}
			}
		}
		try {
			String errorCodesA = cldcs002.sendData(dealerCodes, bussIds);
			String erroeCodesB = cldcs002Cloud.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException("物料组下发失败！", e);
		}
	}

	/**
	 * 物料下发
	 */
	@Override
	public void sendMaterial(MaterialDTO dto) {
		Integer isAllDealer = dto.getIsAllDealer();
		String dealerId = dto.getDealerIds();
		String buss = dto.getBussIds();
		//获取经销商Code
		List<String> dealerCodes = new ArrayList<String>();
		//获取页面传过来的物料ID
		String[] bussIds = buss.split(",");
		if(isAllDealer.intValue() == 0){
			List<TmDealerPO> list = TmDealerPO.findAll();
			if(list != null && !list.isEmpty()){
				for(TmDealerPO po : list){
					dealerCodes.add(po.getString("DEALER_CODE"));
				}
			}
		}else{
			//获取页面传过来的经销商Id
			String[] dealerIds = dealerId.split(",");
			if(dealerIds != null && dealerIds.length > 0){
				for(String id :dealerIds){
					dealerCodes.add(id);
				}
			}
		}
		try {
			String errorCodesA = cldcs003.sendData(dealerCodes, bussIds);
			String erroeCodesB = cldcs003Cloud.execute(dealerCodes, bussIds);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException("物料下发失败！", e);
		}
		
	}

}
