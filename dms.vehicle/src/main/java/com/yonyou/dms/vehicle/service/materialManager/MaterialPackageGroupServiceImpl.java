package com.yonyou.dms.vehicle.service.materialManager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.dao.materialManager.MaterialPackageGroupDao;
import com.yonyou.dms.vehicle.domains.DTO.materialManager.MaterialPackageGroupDTO;
import com.yonyou.dms.vehicle.domains.PO.materialManager.MaterialPackageGroupPO;

@Service
public class MaterialPackageGroupServiceImpl implements MaterialPackageGroupService {
	
	@Autowired
	MaterialPackageGroupDao dao;
	
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
	public List<Map> queryListForExport(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> list = dao.queryMaterialGroupForExport(queryParam);
		return list;
	}
	
	
	/**
	 * 根据ID获取车款组信息
	 */
	@Override
	public MaterialPackageGroupPO getById(Long id) throws ServiceBizException {
		return MaterialPackageGroupPO.findById(id);
	}
	
	/**
	 * 新增车款组信息
	 */
	@Override
	public Long addMaterialPackageGroup(MaterialPackageGroupDTO mpgDto) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);

		MaterialPackageGroupPO mpgPO = new MaterialPackageGroupPO();
		//设置对象属性
		Boolean flag = dao.checkCode(mpgDto);
		if(!flag){
			throw new ServiceBizException("车款组代码已存在，请重新输入！");
		}
		setPO(mpgPO,mpgDto,1);
		mpgPO.saveIt();
		Long billId = mpgPO.getLongId();
		//更新物料组表的PACKAGE_GROUP_ID
		if(!StringUtils.isNullOrEmpty(mpgDto.getIds())){
			String[] ids = mpgDto.getIds().split(",");
			for (int i = 0; i < ids.length; i++) {
				TmVhclMaterialGroupPO po = TmVhclMaterialGroupPO.findById(ids[i]);
				po.setLong("PACKAGE_GROUP_ID", billId);
				po.setLong("UPDATE_BY", loginInfo.getUserId());
				po.setTimestamp("UPDATE_AT", new Date());
				po.saveIt();
			}
		}
		return billId;
	}
	
	/**
	 * 车款组数据映射
	 * @param mgPO
	 * @param mgDto
	 */
	private void setPO(MaterialPackageGroupPO mpgPO, MaterialPackageGroupDTO mpgDto,int type) {
		mpgPO.setString("PACKAGE_GROUP_CODE", mpgDto.getPackageGroupCode());
		mpgPO.setString("PACKAGE_GROUP_NAME", mpgDto.getPackageGroupName());
		mpgPO.setInteger("STATUS", mpgDto.getStatus());
		
	}

	/**
	 * 修改车款组信息
	 */
	@Override
	public void ModifyMaterialPackageGroup(Long id, MaterialPackageGroupDTO mpgDto) throws ServiceBizException {
	   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	   MaterialPackageGroupPO po = MaterialPackageGroupPO.findById(id);
	   setPO(po,mpgDto,2);
	   po.saveIt();
	   String ids = mpgDto.getIds();
	   if(null!=ids && ids.length()>0){
		   String[] idss = ids.split(",");
		   for (int i = 0; i < idss.length; i++) {
			   String groupId = idss[i];
			   TmVhclMaterialGroupPO mgPo = TmVhclMaterialGroupPO.findById(groupId);
			   mgPo.setLong("PACKAGE_GROUP_ID", id);
			   mgPo.setLong("UPDATE_BY", loginInfo.getUserId());
			   mgPo.setTimestamp("UPDATE_AT", new Date());
			   mgPo.saveIt();
		   }
	   }
	   
	}

}
