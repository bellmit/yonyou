package com.yonyou.dms.vehicle.service.materialManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmpVhclMaterialGroupDcsPO;
import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.dao.materialManager.MaterialGroupDao;
import com.yonyou.dms.vehicle.domains.DTO.basedata.MaterialGroupImportDTO;
import com.yonyou.dms.vehicle.domains.DTO.materialManager.MaterialGroupDTO;

@Service
public class MaterialGroupServiceImpl implements MaterialGroupService {
	
	@Autowired
	MaterialGroupDao dao;
	
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
	 * 下载明细查询
	 */
	@Override
	public List<Map> queryMaterialGroupDetailForExport(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> list = dao.queryMaterialGroupDetailForExport(queryParam);
		return list;
	}
	
	/**
	 * 上级物料组弹出窗口
	 */
	@Override
	public PageInfoDto selectMaterialGroupWin(Map<String, String> queryParam,int type) throws ServiceBizException {
		PageInfoDto pgInfo = dao.selectMaterialGroupWin(queryParam,type);
		return pgInfo;
	}
	
	/**
	 * 根据ID获取物料组信息
	 */
	@Override
	public TmVhclMaterialGroupPO getById(Long id) throws ServiceBizException {
		return TmVhclMaterialGroupPO.findById(id);
	}
	
	/**
	 * 新增物料组信息
	 */
	@Override
	public Long addMaterialGroup(MaterialGroupDTO mgDto) throws ServiceBizException {
		TmVhclMaterialGroupPO mgPO = new TmVhclMaterialGroupPO();
		//设置对象属性
		Boolean flag = dao.checkCode(mgDto);
		if(!flag){
			throw new ServiceBizException("物料代码已存在，请重新输入！");
		}
		setPO(mgPO,mgDto,1);
		if(!StringUtils.isNullOrEmpty(mgDto.getParentGroupId())){
			TmVhclMaterialGroupPO mgPO2 = TmVhclMaterialGroupPO.findById(mgDto.getParentGroupId());
			mgPO.setString("TREE_CODE", dao.getTreeCode(mgPO2.getString("TREE_CODE")));
			mgPO.setInteger("GROUP_LEVEL", mgDto.getGroupLevel()+1);
			mgPO.setLong("PARENT_GROUP_ID", mgDto.getParentGroupId());
		}else{
			mgPO.setString("TREE_CODE","A01");
			mgPO.setLong("PARENT_GROUP_ID", new Long(-1));
			mgPO.setInteger("GROUP_LEVEL", 1);
		}
		mgPO.saveIt();
		Long billId = mgPO.getLongId();
		return billId;
	}
	
	/**
	 * 数据映射
	 * @param mgPO
	 * @param mgDto
	 */
	private void setPO(TmVhclMaterialGroupPO mgPO, MaterialGroupDTO mgDto,int type) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		mgPO.setString("GROUP_CODE", mgDto.getGroupCode());
		mgPO.setString("OEM_COMPANY_ID", new Long("2010010100070674"));
		mgPO.setString("GROUP_NAME", mgDto.getGroupName());
		mgPO.setInteger("STATUS", mgDto.getStatus());
		mgPO.setInteger("GROUP_TYPE", mgDto.getGroupType());
		mgPO.setString("WX_ENGINE", mgDto.getWxEngine());
		mgPO.setInteger("OILE_TYPE", mgDto.getOileType());
		mgPO.setInteger("IF_FOREC", mgDto.getIfForec());
		mgPO.setInteger("IS_EC", mgDto.getIsEc());
		mgPO.setString("MODEL_YEAR",mgDto.getModelYear());
		mgPO.setString("FACTORY_OPTIONS",mgDto.getFactoryOptions());
		mgPO.setString("STANDARD_OPTION",mgDto.getStandardOption());
		mgPO.setString("LOCAL_OPTION",mgDto.getLocalOption());
		mgPO.setString("OTHER_OPTION",mgDto.getOtherOption());
		mgPO.setString("PC_MODLE_CODE",mgDto.getPcModleCode());
		mgPO.setString("SPECIAL_SERIE_CODE",mgDto.getSpecialSerieCode());
		mgPO.setString("LOW_CPOS_CODE",mgDto.getLowCposCode());
		mgPO.setString("PC_MODLE",mgDto.getPcModle());
		mgPO.setString("GEAR",mgDto.getGear());
		mgPO.setString("ENGINE",mgDto.getEngine());
		mgPO.setString("TRANSMISSION",mgDto.getTransmission());
		mgPO.setString("ENGINE_DESC",mgDto.getEngineDesc());
		mgPO.setString("TRANSMISSION_DESC",mgDto.getTransmissionDesc());
		if(type==1){
			mgPO.setInteger("CREATE_BY", loginInfo.getUserId());
			mgPO.setTimestamp("CREATE_AT", new Date());
		}else{
			mgPO.setInteger("UPDATE_BY", loginInfo.getUserId());
			mgPO.setTimestamp("UPDATE_AT", new Date());
		}
		
	}

	/**
	 * 修改物料组信息
	 */
	@Override
	public void ModifyMaterialGroup(Long id, MaterialGroupDTO mgDto) throws ServiceBizException {
		TmVhclMaterialGroupPO po = TmVhclMaterialGroupPO.findById(id);
	   if(!po.getLong("PARENT_GROUP_ID").equals(mgDto.getParentGroupId())){
		   TmVhclMaterialGroupPO mgPO2 = TmVhclMaterialGroupPO.findById(mgDto.getParentGroupId());
		   po.setString("TREE_CODE", dao.getTreeCode(mgPO2.getString("TREE_CODE")));
		   po.setInteger("GROUP_LEVEL", mgDto.getGroupLevel()+1);
		   po.setLong("PARENT_GROUP_ID", mgDto.getParentGroupId());
	   }
	   //设置对象属性
	   setPO(po,mgDto,2);
	   po.saveIt();
	}

	@Override
	public ArrayList<MaterialGroupImportDTO> checkData(ArrayList<MaterialGroupImportDTO> dataList) {
		//保存车款代码，判断重复
		List<String> codeList = new ArrayList<>();
		//结果列表，保存存在错误的数据
		ArrayList<MaterialGroupImportDTO> resultList = new ArrayList<>();
		if(dataList != null){
			for(int i = 0; i < dataList.size(); i++){
				MaterialGroupImportDTO dto = dataList.get(i);
				TmpVhclMaterialGroupDcsPO po = new TmpVhclMaterialGroupDcsPO();
				StringBuffer errorInfoBuffer = new StringBuffer("第" + dto.getRowNO() + "行：");
				codeList.add(dto.getGroupCode());
				// 开始组装临时表数据
				po = fzTmpData(dto,po,errorInfoBuffer);
				// 判断EXCEL
				if(codeList.contains(dto.getGroupCode())){
					//groupCode重复
					errorInfoBuffer.append(" 车款代码：" + dto.getGroupCode() + "有重复数据；");
					dto.setErrorMsg(errorInfoBuffer.toString());
				}
				boolean flag = po.insert();
				//错误信息不为空，保存
				if(StringUtils.isNullOrEmpty(dto.getErrorMsg())){
					resultList.add(dto);
				}
			}
		}
		return resultList;
	}

	private TmpVhclMaterialGroupDcsPO fzTmpData(MaterialGroupImportDTO dto,
			TmpVhclMaterialGroupDcsPO po,StringBuffer errorInfoBuffer) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date currentTime = new Date();
		List<Map<String,TmVhclMaterialGroupPO>> vhclInfoList = null;
		//检查配件号在配件主数据中是否已经存在
		if(!StringUtils.isNullOrEmpty(dto.getGroupCode())){
			TmVhclMaterialGroupPO vhclMaterialGroupInfo = null;
			List<TmVhclMaterialGroupPO> list = TmVhclMaterialGroupPO.find("GROUP_CODE = ?", dto.getGroupCode());
			if(list != null && !list.isEmpty()){
				vhclMaterialGroupInfo = list.get(0);
			}
			if(vhclMaterialGroupInfo != null){
				String groupName = CommonUtils.checkNull(vhclMaterialGroupInfo.getString("GROUP_NAME"));
				String modelYear = CommonUtils.checkNull(vhclMaterialGroupInfo.getString("MODEL_YEAR"));
				String factoryOptions = CommonUtils.checkNull(vhclMaterialGroupInfo.getString("FACTORY_OPTIONS"));
				String standardOption = CommonUtils.checkNull(vhclMaterialGroupInfo.getString("STANDARD_OPTION"));
				String localOption = CommonUtils.checkNull(vhclMaterialGroupInfo.getString("LOCAL_OPTION"));
				if(!groupName.equals(dto.getGroupName()) || !modelYear.equals(dto.getModelYear()) || !factoryOptions.equals(dto.getFactoryOpitons())
						|| standardOption.equals(dto.getStandardOption()) || localOption.equals(dto.getLocalOption())){
					po.setString("GROUP_CODE", dto.getGroupCode());
					po.setString("GROUP_NAME", dto.getGroupName());
					po.setString("MODEL_YEAR", dto.getModelYear());
					po.setString("FACTORY_OPTIONS", dto.getFactoryOpitons());
					po.setString("STANDARD_OPTION", dto.getStandardOption());
					po.setString("LOCAL_OPTION", dto.getLocalOption());
					po.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
					po.setLong("CREATE_BY", loginUser.getUserId());
					po.setTimestamp("CREATE_DATE", currentTime);
					po.setLong("UPDATE_BY", loginUser.getUserId());
					po.setTimestamp("UPDATE_DATE", currentTime);
				}else{
					po.setString("GROUP_CODE", dto.getGroupCode());
					po.setString("GROUP_NAME", dto.getGroupName());
					po.setString("MODEL_YEAR", dto.getModelYear());
					po.setString("FACTORY_OPTIONS", dto.getFactoryOpitons());
					po.setString("STANDARD_OPTION", dto.getStandardOption());
					po.setString("LOCAL_OPTION", dto.getLocalOption());
					po.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
					po.setLong("CREATE_BY", loginUser.getUserId());
					po.setTimestamp("CREATE_DATE", currentTime);
					po.setLong("UPDATE_BY", loginUser.getUserId());
					po.setTimestamp("UPDATE_DATE", currentTime);
					errorInfoBuffer.append("车款编号 "+ dto.getGroupCode() +" 不存在；");
					dto.setErrorMsg(errorInfoBuffer.toString());
				}
			}else{
				po.setString("GROUP_CODE", dto.getGroupCode());
				po.setString("GROUP_NAME", dto.getGroupName());
				po.setString("MODEL_YEAR", dto.getModelYear());
				po.setString("FACTORY_OPTIONS", dto.getFactoryOpitons());
				po.setString("STANDARD_OPTION", dto.getStandardOption());
				po.setString("LOCAL_OPTION", dto.getLocalOption());
				po.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
				po.setLong("CREATE_BY", loginUser.getUserId());
				po.setTimestamp("CREATE_DATE", currentTime);
				po.setLong("UPDATE_BY", loginUser.getUserId());
				po.setTimestamp("UPDATE_DATE", currentTime);
				errorInfoBuffer.append("车款编号不允许为空；");
				dto.setErrorMsg(errorInfoBuffer.toString());
			}
		}
		return po;
	}

	@Override
	public void importSave() {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date currentTime = new Date();
		List<TmpVhclMaterialGroupDcsPO> list = 	TmpVhclMaterialGroupDcsPO.findAll();
		if(list != null && !list.isEmpty()){
			for(int i = 0; i < list.size(); i++){
				TmpVhclMaterialGroupDcsPO dpo = list.get(i);
				TmVhclMaterialGroupPO po = TmVhclMaterialGroupPO.findFirst("GROUP_CODE = ?", dpo.getString("GROUP_CODE"));
				if(po != null){
					po.setString("GROUP_CODE", dpo.getString("GROUP_CODE"));
					po.setString("GROUP_NAME", dpo.getString("GROUP_NAME"));
					po.setString("MODEL_YEAR", dpo.getString("MODEL_YEAR"));
					po.setString("FACTORY_OPTIONS", dpo.getString("FACTORY_OPTIONS"));
					po.setString("STANDARD_OPTION", dpo.getString("STANDARD_OPTION"));
					po.setString("LOCAL_OPTION", dpo.getString("LOCAL_OPTION"));
					po.setInteger("STATUS", dpo.getInteger("STATUS"));
					po.setLong("UPDATE_BY", loginUser.getUserId());
					po.setTimestamp("UPDATE_AT", currentTime);
					po.saveIt();
				}
			}
		}
	}


}
