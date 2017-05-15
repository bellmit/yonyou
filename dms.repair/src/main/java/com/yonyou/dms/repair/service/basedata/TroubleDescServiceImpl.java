package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.MaintainWorkTypePO;
import com.yonyou.dms.common.domains.PO.basedata.TroubleDescPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.MaintainWorkTypeDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TroubleDescDTO;

/**
 * 组合类别
 * 
 * @author chenwei
 * @date 2017年3月24日
 */
@Service
public class TroubleDescServiceImpl implements TroubleDescService {

	/**
	 * @author chenwei
	 * @date 2017年3月23日
	 * @param labourPositionCode
	 * @return 查询结果
	 * @throws ServiceBizException(non-Javadoc)
	 * @see com.yonyou.dms.part.service.basedata.MaintainWorkTypeService#findByPrimaryKey(java.lang.String)
	 */
	@Override
	public TroubleDescPO findByPrimaryKey(String troubleCode) throws ServiceBizException {
		// TODO Auto-generated method stub
		TroubleDescPO wtpo = TroubleDescPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
				troubleCode);
		return wtpo;
	}

	/**
	 * 维修工位查询
	 * 
	 * @author chenwei
	 * @date 2017年3月24日
	 * @param queryParam
	 * @return 查询
	 */
	@Override
	public PageInfoDto searchTroubleDesc(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder(
				"select DEALER_CODE,TROUBLE_CODE,TROUBLE_GROUP,TROUBLE_SPELL,TROUBLE_DESC from TM_TROUBLE where 1=1 ");
		List<Object> troubleDescSql = new ArrayList<Object>();
		if (!StringUtils.isNullOrEmpty(queryParam.get("troubleCode"))) {
			sb.append(" and TROUBLE_CODE = ?");
			troubleDescSql.add(queryParam.get("troubleCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("troubleDesc"))) {
			sb.append(" and TROUBLE_DESC like ? ");
			troubleDescSql.add("%" + queryParam.get("troubleDesc") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("troubleGroup"))) {
			sb.append(" and TROUBLE_GROUP = ? ");
			troubleDescSql.add(queryParam.get("troubleGroup"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("troubleSpell"))) {
			sb.append(" and TROUBLE_SPELL = ?");
			troubleDescSql.add(queryParam.get("troubleSpell"));
		}
		PageInfoDto id = DAOUtil.pageQuery(sb.toString(), troubleDescSql);
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
	public String insertTroubleDescPO(TroubleDescDTO troubleDescto) throws ServiceBizException {
		// TODO Auto-generated method stub
		TroubleDescPO troubleDescPO = new TroubleDescPO();
		CheckTroubleDesc(troubleDescto);
		setTroubleDescPO(troubleDescPO,troubleDescto);
		troubleDescPO.saveIt();
		return troubleDescPO.getString("TROUBLE_CODE");
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
	public void updateTroubleDesc(String troubleCode, TroubleDescDTO troubleDescto)
			throws ServiceBizException {
		// TODO Auto-generated method stub
		TroubleDescPO lap = TroubleDescPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),troubleCode);
		lap.setString("TROUBLE_DESC", troubleDescto.getTroubleDesc());
		lap.setString("TROUBLE_GROUP", troubleDescto.getTroubleGroup());
		lap.setString("TROUBLE_SPELL", troubleDescto.getTroubleSpell());
	    lap.saveIt();
	}

	@Override
	public void deleteTroubleDescById(Long id) throws ServiceBizException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Map> queryTroubleDesc(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 检查组合类别
	 * 
	 * @author chenwei
	 * @date 2017年3月23日
	 * @param pyto
	 */

	public void CheckTroubleDesc(TroubleDescDTO pyto) {
		StringBuffer sb = new StringBuffer(
				"select DEALER_CODE,TROUBLE_CODE,TROUBLE_GROUP,TROUBLE_SPELL,TROUBLE_DESC from TM_TROUBLE where 1=1  and TROUBLE_CODE=?");
		List<Object> list = new ArrayList<Object>();
		list.add(pyto.getTroubleCode());
		List<Map> map = DAOUtil.findAll(sb.toString(), list);
		if (map.size() > 0) {
			throw new ServiceBizException("故障代码已经存在！");
		}
	}
	
	/**
     * 设置TroubleDescPO属性
     * 
     * @author chenwei
     * @date 2017年3月24日
     * @param typo
     * @param pyto
     */

    public void setTroubleDescPO(TroubleDescPO typo, TroubleDescDTO pyto) {
    	typo.setString("DEALER_CODE", pyto.getDealerCode());
    	typo.setString("TROUBLE_CODE", pyto.getTroubleCode());
    	typo.setString("TROUBLE_GROUP", pyto.getTroubleGroup());
    	typo.setString("TROUBLE_SPELL", pyto.getTroubleSpell());
    	typo.setString("TROUBLE_DESC", pyto.getTroubleDesc());
    }

}
