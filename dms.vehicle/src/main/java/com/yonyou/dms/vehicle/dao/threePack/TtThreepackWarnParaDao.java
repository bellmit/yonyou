package com.yonyou.dms.vehicle.dao.threePack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackWarnParaDTO;
import com.yonyou.dms.vehicle.domains.PO.threePack.TtThreepackWarnParaPO;
/**
 * 三包预警dao
 * @author zhoushijie
 *
 */
@Repository
public class TtThreepackWarnParaDao extends OemBaseDAO{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	public PageInfoDto findthreePack(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("	 select t2.ID, t1.ITEM_NO, t1.ITEM_NAME, t1.ITEM_REMARK ,  \n");
		sql.append("	  t2.LEGAL_STANDARD, t2.WARN_STANDARD, t2.YELLOW_STANDARD, t2.ORANGE_STANDARD, t2.RED_STANDARD   \n");
		sql.append("	 from    \n");
		sql.append("	 (select ID, ITEM_NO, ITEM_NAME, ITEM_REMARK   \n");
		sql.append("     from TT_THREEPACK_ITEM_DCS   \n");
		sql.append("     where  IS_DEL="+OemDictCodeConstants.IS_DEL_00+"   ) t1   \n");
		//连接
		sql.append("     inner join   \n");
		sql.append("  (select ID, ITEM_ID, ITEM_NO, LEGAL_STANDARD, WARN_STANDARD, YELLOW_STANDARD, ORANGE_STANDARD, RED_STANDARD \n" );
		sql.append("  from TT_THREEPACK_WARN_PARA_DCS \n");
		sql.append("     where  IS_DEL="+OemDictCodeConstants.IS_DEL_00+"   ) t2   \n");
		//连接条件
		sql.append("     on t1.ID=t2.ITEM_ID   \n");
		sql.append(" where   1=1 \n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("itemNo"))) {
			sql.append(" and t2.ITEM_ID= ? ");
			params.add(queryParam.get("itemNo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("itemName"))) {
			sql.append(" and t1.ITEM_NAME= ? ");
			params.add(queryParam.get("itemName"));
		}
		return sql.toString();
	}
	
	/**
	 * 三包项目小类设定新增
	 * 
	 * @param tcbdto
	 * @return
	 * @throws ServiceBizException
	 */
	public long addMinclass(TtThreepackWarnParaDTO tcdto) throws ServiceBizException {
		StringBuffer sb = new StringBuffer(
				"select item_no from TT_THREEPACK_WARN_PARA_DCS where 1=1 and is_del='0' and item_id=?");
		List<Object> list = new ArrayList<Object>();
		list.add(tcdto.getItemNo());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
		if (map.size() > 0) {
			throw new ServiceBizException("项目编号已存在，请重新输入！");
		} else {
			TtThreepackWarnParaPO tc = new TtThreepackWarnParaPO();
			tc.setLong("item_id", tcdto.getItemNo());
			StringBuffer sql = new StringBuffer(
					"select item_no from TT_THREEPACK_ITEM_DCS where 1=1 and id=?");
			List<Object> zz = new ArrayList<Object>();
			zz.add(tcdto.getItemNo());
			List<Map> map2 = OemDAOUtil.findAll(sql.toString(), zz);
			map2.get(0);
			tc.setLong("item_no", map2.get(0).get("item_no"));
			tc.setLong("legal_standard", tcdto.getLegalStandard());
			tc.setLong("warn_standard", tcdto.getWarnStandard());
			tc.setLong("yellow_standard", tcdto.getYellowStandard());
			tc.setLong("orange_standard", tcdto.getOrangeStandard());
			tc.setLong("red_standard", tcdto.getRedStandard());
			tc.setDate("create_date", new Date());
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			tc.setString("create_by", loginInfo.getUserId());
			tc.setLong("ver", 0);
			tc.setInteger("is_del", 0);
			tc.setInteger("is_arc", 0);
			tc.saveIt();
			return (Long) tc.getLongId();
		}
	}

	/**
	 * 根据ID删除
	 * 
	 * @param id
	 * @throws ServiceBizException
	 */
	public void deleteChargeById(Long id) throws ServiceBizException {
		TtThreepackWarnParaPO wtp = TtThreepackWarnParaPO.findById(id);
		wtp.setInteger("is_del", 1);
		wtp.saveIt();
	}
	/**
	 * 修改
	 * 
	 * @param id
	 * @param user
	 * @throws ServiceBizException
	 */
	public void modifyMinclass(Long id, TtThreepackWarnParaDTO tcdto) throws ServiceBizException {
		TtThreepackWarnParaPO tc = TtThreepackWarnParaPO.findById(id);
		setTt(tc, tcdto);
		tc.saveIt();
	}
	/**
	 * 设置对象属性
	 * 
	 * @param tc
	 * @param user
	 */
	private void setTt(TtThreepackWarnParaPO tc, TtThreepackWarnParaDTO tcdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tc.setLong("legal_standard", tcdto.getLegalStandard());
		tc.setLong("warn_standard", tcdto.getWarnStandard());
		tc.setLong("yellow_standard", tcdto.getYellowStandard());
		tc.setLong("orange_standard", tcdto.getOrangeStandard());
		tc.setLong("red_standard", tcdto.getRedStandard());
		tc.setDate("update_date", new Date());
		tc.setString("update_by", loginInfo.getUserId());

	}
	public List<Map> findById(Long id) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("	 select t2.ID, t1.ITEM_NO, t1.ITEM_NAME, t1.ITEM_REMARK ,  \n");
		sql.append("	  t2.LEGAL_STANDARD, t2.WARN_STANDARD, t2.YELLOW_STANDARD, t2.ORANGE_STANDARD, t2.RED_STANDARD   \n");
		sql.append("	 from    \n");
		sql.append("	 (select ID, ITEM_NO, ITEM_NAME, ITEM_REMARK   \n");
		sql.append("     from TT_THREEPACK_ITEM_DCS   \n");
		sql.append("     where  IS_DEL="+OemDictCodeConstants.IS_DEL_00+"   ) t1   \n");
		//连接
		sql.append("     inner join   \n");
		sql.append("  (select ID, ITEM_ID, ITEM_NO, LEGAL_STANDARD, WARN_STANDARD, YELLOW_STANDARD, ORANGE_STANDARD, RED_STANDARD \n" );
		sql.append("  from TT_THREEPACK_WARN_PARA_DCS \n");
		sql.append("     where  IS_DEL="+OemDictCodeConstants.IS_DEL_00+"   ) t2   \n");
		//连接条件
		sql.append("     on t1.ID=t2.ITEM_ID   \n");
		sql.append(" where  t2.ID = ? \n");
		params.add(id);
		List<Map> map = OemDAOUtil.findAll(sql.toString(), params);
		return map;
		
	}
}
