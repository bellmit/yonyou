package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.MaintainWorkTypePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.MaintainWorkTypeDTO;

/**
 * 组合类别
 * 
 * @author sunqinghua
 * @date 2017年3月22日
 */
@Service
public class MaintainWorkTypeServiceImpl implements MaintainWorkTypeService {

	/**
	 * @author chenwei
	 * @date 2017年3月23日
	 * @param labourPositionCode
	 * @return 查询结果
	 * @throws ServiceBizException(non-Javadoc)
	 * @see com.yonyou.dms.part.service.basedata.MaintainWorkTypeService#findByPrimaryKey(java.lang.String)
	 */
	@Override
	public MaintainWorkTypePO findByPrimaryKey(String labourPositionCode) throws ServiceBizException {
		// TODO Auto-generated method stub
		MaintainWorkTypePO wtpo = MaintainWorkTypePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
				labourPositionCode);
		return wtpo;
	}

	/**
	 * 维修工位查询
	 * 
	 * @author chenwei
	 * @date 2017年3月23日
	 * @param queryParam
	 * @return 查询
	 */
	@Override
	public PageInfoDto searchMaintainWorkType(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder(
				"select r.DEALER_CODE,r.LABOUR_POSITION_CODE,r.LABOUR_POSITION_NAME,r.LABOUR_POSITION_TYPE,r.WORKGROUP_CODE,w.WORKGROUP_NAME,r.IS_MANY_POSITION,r.REPAIR_CAPABILITY from TM_REPAIR_POSITION r left join TM_WORKGROUP w on r.WORKGROUP_CODE = w.WORKGROUP_CODE where 1=1 ");
		List<Object> maintainWorkTypeSql = new ArrayList<Object>();
		if (!StringUtils.isNullOrEmpty(queryParam.get("labourPositionCode"))) {
			sb.append(" and r.LABOUR_POSITION_CODE = ?");
			maintainWorkTypeSql.add(queryParam.get("labourPositionCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("labourPositionName"))) {
			sb.append(" and r.LABOUR_POSITION_NAME like ? ");
			maintainWorkTypeSql.add("%" + queryParam.get("labourPositionName") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("labourPositionType"))) {
			sb.append(" and r.LABOUR_POSITION_TYPE = ? ");
			maintainWorkTypeSql.add(queryParam.get("labourPositionType"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("workGroupCode"))) {
			sb.append(" and r.WORKGROUP_CODE = ?");
			maintainWorkTypeSql.add(queryParam.get("workGroupCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("isManyPosition"))) {
			sb.append(" and r.IS_MANY_POSITION = ? ");
			maintainWorkTypeSql.add(Integer.parseInt(queryParam.get("isManyPosition")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("repairCapability"))) {
			sb.append(" and r.REPAIR_CAPABILITY = ? ");
			maintainWorkTypeSql.add(Integer.parseInt(queryParam.get("repairCapability")));
		}
		PageInfoDto id = DAOUtil.pageQuery(sb.toString(), maintainWorkTypeSql);
		return id;
	}

	/**
	 * 维修工位新增界面实现
	 * 
	 * @author 陈伟
	 * @date 2017年3月23日
	 * @param repair
	 * @return 新增
	 */
	@Override
	public String insertMaintainWorkTypePo(MaintainWorkTypeDTO maintainWorkTypeto) throws ServiceBizException {
		// TODO Auto-generated method stub
		MaintainWorkTypePO maintainWorkTypePO = new MaintainWorkTypePO();
		CheckMaintainWorkType(maintainWorkTypeto);
		setMaintainWorkTypePO(maintainWorkTypePO,maintainWorkTypeto);
		maintainWorkTypePO.saveIt();
		return maintainWorkTypePO.getString("LABOUR_POSITION_CODE");
	}

	/**
	 * 更新
	 * @author chenwei
	 * @date 2017年3月23日
	 * @param labourPositionCode 
	 * @param maintainWorkTypeto
	 * @throws ServiceBizException(non-Javadoc)
	 * @see com.yonyou.dms.part.service.basedata.MaintainWorkTypeService#updateMaintainWorkType(java.lang.String, com.yonyou.dms.repair.domains.DTO.basedata.MaintainWorkTypeDTO)
	 */
	@Override
	public void updateMaintainWorkType(String labourPositionCode, MaintainWorkTypeDTO maintainWorkTypeto)
			throws ServiceBizException {
		// TODO Auto-generated method stub
		MaintainWorkTypePO lap = MaintainWorkTypePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),labourPositionCode);
		lap.setString("LABOUR_POSITION_NAME", maintainWorkTypeto.getLabourPositionName());
		lap.setInteger("LABOUR_POSITION_TYPE", maintainWorkTypeto.getLabourPositionType());
		lap.setInteger("IS_MANY_POSITION", maintainWorkTypeto.getIsManyPosition());
		lap.setInteger("REPAIR_CAPABILITY", maintainWorkTypeto.getRepairCapability());
	    lap.saveIt();
	}

	@Override
	public void deleteMaintainWorkTypeById(Long id) throws ServiceBizException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Map> queryStore(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 检查组合类别
	 * 
	 * @author chenwei
	 * @date 2017年3月23日
	 * @param limidto
	 */

	public void CheckMaintainWorkType(MaintainWorkTypeDTO pyto) {
		StringBuffer sb = new StringBuffer(
				"select DEALER_CODE,LABOUR_POSITION_CODE,LABOUR_POSITION_NAME,LABOUR_POSITION_TYPE,WORKGROUP_CODE,IS_MANY_POSITION,REPAIR_CAPABILITY from TM_REPAIR_POSITION where 1=1 and LABOUR_POSITION_CODE=?");
		List<Object> list = new ArrayList<Object>();
		list.add(pyto.getLabourPositionCode());
		List<Map> map = DAOUtil.findAll(sb.toString(), list);
		StringBuffer sb2 = new StringBuffer(
				"select DEALER_CODE,LABOUR_POSITION_CODE,LABOUR_POSITION_NAME,LABOUR_POSITION_TYPE,WORKGROUP_CODE,IS_MANY_POSITION,REPAIR_CAPABILITY from TM_REPAIR_POSITION where 1=1 and LABOUR_POSITION_NAME=?");
		List<Object> list2 = new ArrayList<Object>();
		list2.add(pyto.getLabourPositionName());
		List<Map> map2 = DAOUtil.findAll(sb2.toString(), list2);
		if (map.size() > 0 || map2.size() > 0) {
			throw new ServiceBizException("维修工位代码或名称不能重复！");
		}
	}
	
	/**
     * 设置PackageTypePO属性
     * 
     * @author sqh
     * @date 2017年3月22日
     * @param typo
     * @param pyto
     */

    public void setMaintainWorkTypePO(MaintainWorkTypePO typo, MaintainWorkTypeDTO pyto) {
    	typo.setString("DEALER_CODE", pyto.getDealerCode());
    	typo.setString("LABOUR_POSITION_CODE", pyto.getLabourPositionCode());
    	typo.setString("LABOUR_POSITION_NAME", pyto.getLabourPositionName());
    	typo.setInteger("LABOUR_POSITION_TYPE", pyto.getLabourPositionType());
    	typo.setString("WORKGROUP_CODE", pyto.getWorkGroupCode());
    	typo.setInteger("IS_MANY_POSITION", pyto.getIsManyPosition());
    	typo.setInteger("REPAIR_CAPABILITY", pyto.getRepairCapability());
    }

}
