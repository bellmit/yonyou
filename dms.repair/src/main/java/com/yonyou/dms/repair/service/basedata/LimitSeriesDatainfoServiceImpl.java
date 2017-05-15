package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.TMLimitSeriesDatainfoDTO;
import com.yonyou.dms.common.domains.PO.basedata.LimitSeriesDatainfoPO;
import com.yonyou.dms.common.domains.PO.basedata.MaintainWorkTypePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.MaintainWorkTypeDTO;

/**
 * 限价车系及维修类型
 * 
 * @author chenwei
 * @date 2017年3月27日
 */
@Service
public class LimitSeriesDatainfoServiceImpl implements LimitSeriesDatainfoService {

	/**
	 * @author chenwei
	 * @date 2017年3月27日
	 * @param labourPositionCode
	 * @return 查询结果
	 * @throws ServiceBizException(non-Javadoc)
	 * @see com.yonyou.dms.repair.service.basedata.LimitSeriesDatainfoService#findByPrimaryKey(java.lang.String)
	 */
	@Override
	public LimitSeriesDatainfoPO findByPrimaryKey(String itemId) throws ServiceBizException {
		// TODO Auto-generated method stub
		LimitSeriesDatainfoPO wtpo = LimitSeriesDatainfoPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
				itemId);
		return wtpo;
	}

	/**
	 * 限价车系及维修类型查询
	 * 
	 * @author chenwei
	 * @date 2017年3月27日
	 * @param queryParam
	 * @return 查询
	 */
	@Override
	public PageInfoDto searchLimitSeriesDatainfo(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder(
				"select s.DEALER_CODE,CASE WHEN (s.ITEM_ID IS NOT NULL AND LENGTH(TRIM(s.ITEM_ID))>12) THEN SUBSTRING(s.ITEM_ID,13) END AS ITEM_ID,s.BRAND_CODE,s.SERIES_CODE,s.REPAIR_TYPE_CODE,s.LIMIT_PRICE_RATE,");
		sb.append("case when s.IS_VALID=12781001 then 10571001 END AS IS_VALID,case when s.OEM_TAG=12781001 then 10571001 end as OEM_TAG,");
		sb.append("b.BRAND_NAME,t.SERIES_NAME from TM_LIMIT_SERIES_DATAINFO s left join TM_BRAND b on s.BRAND_CODE = b.BRAND_CODE left join TM_SERIES t on t.SERIES_CODE = s.SERIES_CODE where 1=1 ");
		List<Object> limitSeriesDatainfoSql = new ArrayList<Object>();
		if (!StringUtils.isNullOrEmpty(queryParam.get("itemId"))) {
			sb.append(" and s.ITEM_ID = ?");
			limitSeriesDatainfoSql.add(Integer.parseInt(queryParam.get("itemId")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sb.append(" and s.BRAND_CODE = ? ");
			limitSeriesDatainfoSql.add(queryParam.get("brandCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
			sb.append(" and s.SERIES_CODE = ? ");
			limitSeriesDatainfoSql.add(queryParam.get("seriesCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("repairTypeCode"))) {
			sb.append(" and s.REPAIR_TYPE_CODE = ?");
			limitSeriesDatainfoSql.add(queryParam.get("repairTypeCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("limitPriceRate"))) {
			sb.append(" and s.LIMIT_PRICE_RATE = ? ");
			limitSeriesDatainfoSql.add(Double.parseDouble(queryParam.get("limitPriceRate")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("isValid"))) {
			sb.append(" and s.IS_VALID = ? ");
			limitSeriesDatainfoSql.add(Integer.parseInt(queryParam.get("isValid")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("oemTag"))) {
			sb.append(" and s.OEM_TAG = ? ");
			limitSeriesDatainfoSql.add(Integer.parseInt(queryParam.get("oemTag")));
		}
		PageInfoDto id = DAOUtil.pageQuery(sb.toString(), limitSeriesDatainfoSql);
		return id;
	}

	/**
	 * 限价车系及维修类型新增界面实现
	 * 
	 * @author 陈伟
	 * @date 2017年3月23日
	 * @param repair
	 * @return 新增
	 */
	@Override
	public String insertLimitSeriesDatainfoPo(TMLimitSeriesDatainfoDTO limitSeriesDatainfoto) throws ServiceBizException {
		// TODO Auto-generated method stub
		return null;
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
	public void updateLimitSeriesDatainfo(String itemId, TMLimitSeriesDatainfoDTO limitSeriesDatainfoto)
			throws ServiceBizException {
		// TODO Auto-generated method stub
		/*MaintainWorkTypePO lap = MaintainWorkTypePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),labourPositionCode);
		lap.setString("LABOUR_POSITION_NAME", maintainWorkTypeto.getLabourPositionName());
		lap.setInteger("LABOUR_POSITION_TYPE", maintainWorkTypeto.getLabourPositionType());
		lap.setInteger("IS_MANY_POSITION", maintainWorkTypeto.getIsManyPosition());
		lap.setInteger("REPAIR_CAPABILITY", maintainWorkTypeto.getRepairCapability());
	    lap.saveIt();*/
	}

	@Override
	public void deleteLimitSeriesDatainfoById(Long id) throws ServiceBizException {
		// TODO Auto-generated method stub

	}

	@Override
    public List<Map> queryLimitSeriesDatainfo(Map<String, String> queryParam) throws ServiceBizException {
        // TODO Auto-generated method stub
        List<Object> list = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("SELECT DEALER_CODE,LIMIT_PRICE_RATE,ITEM_ID,BRAND_CODE,REPAIR_TYPE_CODE,SERIES_CODE,IS_VALID,OEM_TAG,D_KEY FROM TM_LIMIT_SERIES_DATAINFO where 1=1 ");
        if(StringUtils.isNullOrEmpty(queryParam.get("repairTypeCode"))){
            sql.append(" AND REPAIR_TYPE_CODE = ? ");
            list.add("repairTypeCode");
        }
        if(StringUtils.isNullOrEmpty(queryParam.get("brandCode"))){
            sql.append(" AND BRAND_CODE = ? ");
            list.add(queryParam.get("brandCode"));
        }
        if(StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))){
            sql.append(" AND SERIES_CODE = ? ");
            list.add(queryParam.get("seriesCode"));
        }
        if(StringUtils.isNullOrEmpty(queryParam.get("isValid"))){
            sql.append(" AND IS_VALID = ? ");
            list.add(queryParam.get("isValid"));
        }
        if(StringUtils.isNullOrEmpty(queryParam.get("oemTag"))){
            sql.append(" AND OEM_TAG = ? ");
            list.add(queryParam.get("oemTag"));
        }
        if(StringUtils.isNullOrEmpty(queryParam.get("dKey"))){
            sql.append(" AND D_KEY = ? ");
            list.add(queryParam.get("dKey"));
        }
        return DAOUtil.findAll(sql.toString(), list);
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
