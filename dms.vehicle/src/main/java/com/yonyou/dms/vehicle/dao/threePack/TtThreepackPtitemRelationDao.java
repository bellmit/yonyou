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
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackPtitemRelationDTO;
import com.yonyou.dms.vehicle.domains.PO.threePack.TtThreepackPtitemRelationPO;
/**
 * 配件及项目队应关系dao
 * @author zhoushijie
 *
 */
@Repository
public class TtThreepackPtitemRelationDao extends OemBaseDAO{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	public PageInfoDto findthreePack(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("	 select  t1.ITEM_NO, t1.ITEM_NAME, t1.ITEM_REMARK ,t2.MINCLASS_NO, t2.MINCLASS_NAME,  \n");
		sql.append("	  t3.ID, t3.ITEM_ID, t3.MINCLASS_ID,t3.PART_CODE, t3.PART_NAME   \n");
		sql.append("	 from    \n");
		sql.append("	 (select ID, ITEM_NO, ITEM_NAME, ITEM_REMARK   \n");
		sql.append("     from TT_THREEPACK_ITEM_DCS   \n");
		sql.append("     where  IS_DEL="+OemDictCodeConstants.IS_DEL_00+"   ) t1   \n");
		//连接
		sql.append("     inner join   \n");
		sql.append("  (select ID, ITEM_ID, MINCLASS_NO, MINCLASS_NAME from TT_THREEPACK_ITEM_MINCLASS_DCS \n");
		sql.append("     where  IS_DEL="+OemDictCodeConstants.IS_DEL_00+"   ) t2   \n");
		//连接条件
		sql.append("     on t1.ID=t2.ITEM_ID   \n");
		sql.append("     inner join \n");
		sql.append("    (select  ITEM_ID,MINCLASS_ID,PART_CODE,PART_NAME,ID  \n");
		sql.append("     from TT_THREEPACK_PTITEM_RELATION_DCS  where  IS_DEL="+OemDictCodeConstants.IS_DEL_00+"   ) t3  \n");
		sql.append("     on t1.ID=T3.ITEM_ID AND t2.ID=t3.MINCLASS_ID  \n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("itemNo"))) {
			sql.append("   AND t1.ID = ? ");
			params.add(queryParam.get("itemNo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("itemName"))) {
			sql.append(" and t1.ITEM_NAME = ? ");
			params.add(queryParam.get("itemName"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("minclassNo"))) {
			sql.append(" and  t2.ID = ? ");
			params.add(queryParam.get("minclassNo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("minclassName"))) {
			sql.append(" and t2.MINCLASS_NAME = ? ");
			params.add(queryParam.get("minclassName"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("partCode"))) {
			sql.append(" and  t3.PART_CODE = ? ");
			params.add(queryParam.get("partCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("partName"))) {
			sql.append(" and t3.PART_NAME = ? ");
			params.add(queryParam.get("partName"));
		}
		return sql.toString();
	}
	public List<Map> findAllRelation(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuery(queryParam, params);
		List<Map>  d=OemDAOUtil.findAll(sql, params);
		return d;
	}

	private String getQuery(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("	 select t.id,t.PART_CODE,t.PART_NAME  \n");
		sql.append("     from TT_PT_PART_BASE_DCS t where IS_DEL="+OemDictCodeConstants.IS_DEL_00 +" \n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("partName"))) {
			sql.append(" and t.PART_NAME = ? ");
			params.add(queryParam.get("partName"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("partCode"))) {
			sql.append(" and t.PART_CODE = ? ");
			params.add(queryParam.get("partCode"));
		}
		return sql.toString();
	}
	/**
	 * 配件名称显示
	 */
	public List<Map> selectPartName(String partCode) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("  select t.id,t.PART_CODE,t.PART_NAME");
		sql.append("   from TT_PT_PART_BASE_DCS t where IS_DEL="+OemDictCodeConstants.IS_DEL_00 +" \n");
		sql.append("  AND t.PART_CODE = ?");
		queryParam.add(partCode);
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}
	/**
	 * 根据ID删除
	 * 
	 * @param id
	 * @throws ServiceBizException
	 */
	public void deleteById(Long id) throws ServiceBizException {
		TtThreepackPtitemRelationPO wtp = TtThreepackPtitemRelationPO.findById(id);
		wtp.setInteger("is_del", 1);
		wtp.saveIt();
	}
	/**
	 * 配件及对应关系设定新增
	 * 
	 * @param tcbdto
	 * @return
	 * @throws ServiceBizException
	 */
	public long addMinclass(TtThreepackPtitemRelationDTO tcdto) throws ServiceBizException {
		StringBuffer sb = new StringBuffer(
				"select part_code from TT_THREEPACK_PTITEM_RELATION_DCS where 1=1 and part_code=?");
		List<Object> list = new ArrayList<Object>();
		list.add(tcdto.getPartCode());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
		if (map.size() > 0) {
			throw new ServiceBizException("配件编号已存在，请重新输入！");
		} else {
			TtThreepackPtitemRelationPO tc = new TtThreepackPtitemRelationPO();
			tc.setString("minclass_id", tcdto.getMinclassNo());
			tc.setLong("item_id", tcdto.getItemNo());
			tc.setString("part_code", tcdto.getPartCode());
			tc.setString("part_name", tcdto.getPartName());
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
	 * 项目编号下拉框
	 */
	public List<Map> selectItem(Map<String, String> params) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT TB.ID,TB.ITEM_NO,TB.ITEM_NAME FROM TT_THREEPACK_ITEM_DCS TB");
		sql.append("  WHERE 1=1 \n");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}
	/**
	 * 项目名称显示
	 */
	public List<Map> selectItemName(String itemNo) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT TB.ITEM_NAME FROM TT_THREEPACK_ITEM_DCS TB");
		sql.append("  WHERE 1=1 \n");
		sql.append("  AND TB.ID = ?");
		queryParam.add(itemNo);
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}
	
	/**
	 * 小类编号下拉框
	 */
	public List<Map> selectItemMin(String itemNo) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT TB.ID,TB.MINCLASS_NO,TB.MINCLASS_NAME FROM TT_THREEPACK_ITEM_MINCLASS_DCS TB ");
		sql.append("  WHERE 1=1 \n");
		sql.append("  AND TB.ITEM_ID = ?");
		queryParam.add(itemNo);
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}
	/**
	 * 小类名称联动显示
	 */
	public List<Map> selectMinName(String minclassNo) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT TB.MINCLASS_NAME FROM TT_THREEPACK_ITEM_MINCLASS_DCS TB ");
		sql.append("  WHERE 1=1 \n");
		sql.append("  AND TB.ID = ?");
		queryParam.add(minclassNo);
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}
	
	public ImportResultDto<TtThreepackPtitemRelationDTO> checkData(TtThreepackPtitemRelationDTO list) throws Exception {
		ImportResultDto<TtThreepackPtitemRelationDTO> imp = new ImportResultDto<TtThreepackPtitemRelationDTO>();
		boolean isError = false;
		ArrayList<TtThreepackPtitemRelationDTO> err = new ArrayList<TtThreepackPtitemRelationDTO>();

		// 写入三包小类表
		TtThreepackPtitemRelationDTO trmPO = new TtThreepackPtitemRelationDTO();
		// 三包项目
		if (!StringUtils.isNullOrEmpty(list.getItemNo())) {
			if (list.getItemNo().toString().length() > 20) {
				TtThreepackPtitemRelationDTO rowDto = new TtThreepackPtitemRelationDTO();
				rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
				rowDto.setErrorMsg("三包项目代码不能大于20字段!");
				err.add(rowDto);
			} else {
				List<Object> queryParam = new ArrayList<Object>();
				StringBuffer sql = new StringBuffer();
				sql.append("select item_no from TT_THREEPACK_ITEM_DCS where item_no= ?");
				queryParam.add(list.getItemNo());
				List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
				if (resultList != null && resultList.size() > 0) {
				} else {
					TtThreepackPtitemRelationDTO rowDto = new TtThreepackPtitemRelationDTO();
					rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
					rowDto.setErrorMsg("三包项目代码不存在!");
					err.add(rowDto);
				}
			}
		} else {
			TtThreepackPtitemRelationDTO rowDto = new TtThreepackPtitemRelationDTO();
			rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
			rowDto.setErrorMsg("三包项目代码不能为空!");
			err.add(rowDto);
		}
		// 三包项目小类代码
		if (!StringUtils.isNullOrEmpty(list.getMinclassNo())) {
			if (list.getMinclassNo().toString().length() > 20) {
				TtThreepackPtitemRelationDTO rowDto = new TtThreepackPtitemRelationDTO();
				rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
				rowDto.setErrorMsg("三包项目小类代码不能大于20字段!");
				err.add(rowDto);

			} else {
				List<Object> queryParam = new ArrayList<Object>();
				StringBuffer sql = new StringBuffer();
				sql.append("select minclass_no from TT_THREEPACK_ITEM_MINCLASS_DCS where item_no= ?");
				queryParam.add(list.getMinclassNo());
				List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
				if (resultList != null && resultList.size() > 0) {
				}else {
					TtThreepackPtitemRelationDTO rowDto = new TtThreepackPtitemRelationDTO();
					rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
					rowDto.setErrorMsg("三包项目小类代码不存在!");
					err.add(rowDto);
			}
			}
		}else {
			TtThreepackPtitemRelationDTO rowDto = new TtThreepackPtitemRelationDTO();
			rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
			rowDto.setErrorMsg("三包项目小类代码不能为空!");
			err.add(rowDto);
		}
			
		// 配件代码
		if (!StringUtils.isNullOrEmpty(list.getPartCode())) {
			if (list.getPartCode().length() > 30) {
				TtThreepackPtitemRelationDTO rowDto = new TtThreepackPtitemRelationDTO();
				rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
				rowDto.setErrorMsg("配件代码不能大于60字段!");
				err.add(rowDto);
			}
		} else {
			TtThreepackPtitemRelationDTO rowDto = new TtThreepackPtitemRelationDTO();
			rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
			rowDto.setErrorMsg("配件代码不能为空!");
			err.add(rowDto);
		}
	
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.id, tt.id,t1.part_code \n");
		sql.append("FROM tt_threepack_ptitem_relation_dcs T1 \n");
		sql.append("LEFT JOIN TT_THREEPACK_ITEM_MINCLASS_DCS tt ON  tt.id=t1.minclass_id\n");
		sql.append("LEFT JOIN TT_THREEPACK_ITEM_DCS t ON t.id=tt.item_id WHERE tt.minclasee_no=?");
		queryParam.add(list.getMinclassNo());
		sql.append("and t.item_no = ? ");
		queryParam.add(list.getItemNo());
		sql.append("and  t1.part_code= ? ");
		queryParam.add(list.getPartCode());
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		if (resultList != null && resultList.size() > 0) {
			TtThreepackPtitemRelationDTO rowDto = new TtThreepackPtitemRelationDTO();
			rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
			rowDto.setErrorMsg("三包项目代码+小类代码+配件代码已经存在!");
			err.add(rowDto);
		} 
		imp.setErrorList(err);
		logger.info("*****************校验完成!************************");
		if (isError) {
			return imp;
		} else {
			return null;
		}
	}
	
	/**
	 * 修改
	 * 
	 * @param id
	 * @param user
	 * @throws ServiceBizException
	 */
	public void modifyMinclass(Long id, TtThreepackPtitemRelationDTO tcdto) throws ServiceBizException {
		TtThreepackPtitemRelationPO tc = TtThreepackPtitemRelationPO.findById(id);
		setTtThreepackPtitemRelationPO(tc, tcdto);
		tc.saveIt();
	}
	/**
	 * 设置对象属性
	 * 
	 * @param tc
	 * @param user
	 */
	private void setTtThreepackPtitemRelationPO(TtThreepackPtitemRelationPO tc, TtThreepackPtitemRelationDTO tcdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tc.setString("part_code", tcdto.getPartCode());
		tc.setString("part_name", tcdto.getPartName());
		tc.setDate("update_date", new Date());
		tc.setString("update_by", loginInfo.getUserId());

	}
	/**
	 * 修改的查询回显
	 * @param id
	 * @return
	 */
	public List<Map> findById(Long id) {
	StringBuffer sql = new StringBuffer();
	List<Object> list = new ArrayList<Object>();
	sql.append("SELECT \n");
	sql.append("       TI.ITEM_NO, \n");
	sql.append("       TI.ITEM_NAME, \n");
	sql.append("       TIM.MINCLASS_NO, \n");
	sql.append("       TIM.MINCLASS_NAME,  \n");
	sql.append("       TP.PART_CODE,  \n");
	sql.append("       TP.PART_NAME  \n");
	sql.append("  FROM TT_THREEPACK_PTITEM_RELATION_DCS TP \n");
	sql.append(" INNER JOIN TT_THREEPACK_ITEM_MINCLASS_DCS TIM ON TIM.ID = TP.MINCLASS_ID \n");
	sql.append(" INNER JOIN TT_THREEPACK_ITEM_DCS TI ON TP.ITEM_ID = TI.ID \n");
	sql.append(" WHERE TI.IS_DEL = " + OemDictCodeConstants.IS_DEL_00 + " \n");
	sql.append("   AND TIM.IS_DEL = " + OemDictCodeConstants.IS_DEL_00 + " \n");
	sql.append("   AND TP.IS_DEL = " + OemDictCodeConstants.IS_DEL_00 + " \n");
	sql.append("   AND TP.ID = ?");
	list.add(id);
	List<Map> map = OemDAOUtil.findAll(sql.toString(), list);
	return map;
	}
	
	/**
	 * 将数据插入
	 * 
	 * @param tvypDTO
	 */
	public void insert(TtThreepackPtitemRelationDTO tvypDTO) {
		TtThreepackPtitemRelationPO tvypPO = new TtThreepackPtitemRelationPO();
		// 设置对象属性
		setTtThreepackItemMinclassPO(tvypPO, tvypDTO);
		tvypPO.saveIt();
	}

	/**
	 * 设置对象属性
	 * 
	 * @param tc
	 * @param user
	 */
	private void setTtThreepackItemMinclassPO(TtThreepackPtitemRelationPO tc, TtThreepackPtitemRelationDTO tcdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sb = new StringBuffer(
				"select id from TT_THREEPACK_ITEM_DCS where 1=1 and item_no=?");
		List<Object> list = new ArrayList<Object>();
		list.add(tcdto.getItemNo());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
		StringBuffer sql = new StringBuffer(
				"select id from TT_THREEPACK_ITEM_MINCLASS_DCS where 1=1 and minclass_no=?");
		List<Object> zz = new ArrayList<Object>();
		list.add(tcdto.getMinclassNo());
		List<Map> z = OemDAOUtil.findAll(sb.toString(), zz);
		tc.setLong("minclass_id", Long.parseLong(z.get(0).get("id").toString()));
		tc.setLong("item_id",Long.parseLong(map.get(0).get("id").toString()));
		tc.setString("prat_code", tcdto.getPartCode());
		tc.setString("prat_name", tcdto.getPartName());
		tc.setDate("create_date", new Date());
		tc.setString("create_by", loginInfo.getUserId());
		tc.setLong("ver", 0);
		tc.setInteger("is_del", 0);
		tc.setInteger("is_arc", 0);

	}

}
