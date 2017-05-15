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
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackItemMinclassDTO;
import com.yonyou.dms.vehicle.domains.PO.threePack.TtThreepackItemMinclassPO;
/**
 * 三包小类设定dao
 * @author Administrator
 *
 */
@Repository
public class TtThreepackItemMinclassDao extends OemBaseDAO {

	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	public PageInfoDto findthreePack(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TIM.ID, \n");
		sql.append("       TI.ITEM_NO, \n");
		sql.append("       TI.ITEM_NAME, \n");
		sql.append("       TI.ITEM_REMARK, \n");
		sql.append("       TIM.MINCLASS_NO, \n");
		sql.append("       TIM.MINCLASS_NAME  \n");
		sql.append("  FROM TT_THREEPACK_ITEM_DCS TI \n");
		sql.append(" INNER JOIN TT_THREEPACK_ITEM_MINCLASS_DCS TIM ON TIM.ITEM_ID = TI.ID \n");
		sql.append(" WHERE TI.IS_DEL = " + OemDictCodeConstants.IS_DEL_00 + " \n");
		sql.append("   AND TIM.IS_DEL = " + OemDictCodeConstants.IS_DEL_00 + " \n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("itemNo"))) {
			sql.append("   AND TI.ID = ? ");
			params.add(queryParam.get("itemNo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("itemName"))) {
			sql.append(" and TI.ITEM_NAME = ? ");
			params.add(queryParam.get("itemName"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("minclassNo"))) {
			sql.append(" and  TIM.MINCLASS_NO = ? ");
			params.add(queryParam.get("minclassNo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("minclassName"))) {
			sql.append(" and TIM.MINCLASS_NAME = ? ");
			params.add(queryParam.get("minclassName"));
		}
		return sql.toString();
	}

	
	public List<Map> findById(Long id) {
	StringBuffer sql = new StringBuffer();
	List<Object> list = new ArrayList<Object>();
	sql.append("SELECT \n");
	sql.append("       TI.ITEM_NO, \n");
	sql.append("       TI.ITEM_NAME, \n");
	sql.append("       TIM.MINCLASS_NO, \n");
	sql.append("       TIM.MINCLASS_NAME  \n");
	sql.append("  FROM TT_THREEPACK_ITEM_DCS TI \n");
	sql.append(" INNER JOIN TT_THREEPACK_ITEM_MINCLASS_DCS TIM ON TIM.ITEM_ID = TI.ID \n");
	sql.append(" WHERE TI.IS_DEL = " + OemDictCodeConstants.IS_DEL_00 + " \n");
	sql.append("   AND TIM.IS_DEL = " + OemDictCodeConstants.IS_DEL_00 + " \n");
	sql.append("   AND TIM.ID = ?");
	list.add(id);
	List<Map> map = OemDAOUtil.findAll(sql.toString(), list);
	return map;
	}
	/**
	 * 修改
	 * 
	 * @param id
	 * @param user
	 * @throws ServiceBizException
	 */
	public void modifyMinclass(Long id, TtThreepackItemMinclassDTO tcdto) throws ServiceBizException {
		TtThreepackItemMinclassPO tc = TtThreepackItemMinclassPO.findById(id);
		setTtThreepackItemMinclassPO2(tc, tcdto);
		tc.saveIt();
	}
	/**
	 * 设置对象属性
	 * 
	 * @param tc
	 * @param user
	 */
	private void setTtThreepackItemMinclassPO2(TtThreepackItemMinclassPO tc, TtThreepackItemMinclassDTO tcdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tc.setString("minclass_name", tcdto.getMinclassName());
		tc.setString("minclass_no", tcdto.getMinclassNo());
		tc.setDate("update_date", new Date());
		tc.setString("update_by", loginInfo.getUserId());

	}
	/**
	 * 将数据插入
	 * 
	 * @param tvypDTO
	 */
	public void insert(TtThreepackItemMinclassDTO tvypDTO) {
		TtThreepackItemMinclassPO tvypPO = new TtThreepackItemMinclassPO();
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
	private void setTtThreepackItemMinclassPO(TtThreepackItemMinclassPO tc, TtThreepackItemMinclassDTO tcdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sb = new StringBuffer(
				"select id from TT_THREEPACK_ITEM_DCS where 1=1 and item_no=?");
		List<Object> list = new ArrayList<Object>();
		list.add(tcdto.getItemNo());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
		tc.setString("minclass_name", tcdto.getMinclassName());
		tc.setString("minclass_no", tcdto.getMinclassNo());
		tc.setLong("item_id",Long.parseLong(map.get(0).get("id").toString()));
		tc.setDate("create_date", new Date());
		tc.setString("create_by", loginInfo.getUserId());
		tc.setLong("ver", 0);
		tc.setInteger("is_del", 0);
		tc.setInteger("is_arc", 0);

	}

	/**
	 * 三包项目小类设定新增
	 * 
	 * @param tcbdto
	 * @return
	 * @throws ServiceBizException
	 */
	public long addMinclass(TtThreepackItemMinclassDTO tcdto) throws ServiceBizException {
		StringBuffer sb = new StringBuffer(
				"select minclass_no from TT_THREEPACK_ITEM_MINCLASS_DCS where 1=1 and minclass_no=?");
		List<Object> list = new ArrayList<Object>();
		list.add(tcdto.getMinclassNo());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
		if (map.size() > 0) {
			throw new ServiceBizException("小类项目编号已存在，请重新输入！");
		} else {
			TtThreepackItemMinclassPO tc = new TtThreepackItemMinclassPO();
			tc.setString("minclass_name", tcdto.getMinclassName());
			tc.setString("minclass_no", tcdto.getMinclassNo());
			tc.setLong("item_id", tcdto.getItemNo());
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
		TtThreepackItemMinclassPO wtp = TtThreepackItemMinclassPO.findById(id);
		wtp.setInteger("is_del", 1);
		wtp.saveIt();
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

	public ImportResultDto<TtThreepackItemMinclassDTO> checkData(TtThreepackItemMinclassDTO list) throws Exception {
		ImportResultDto<TtThreepackItemMinclassDTO> imp = new ImportResultDto<TtThreepackItemMinclassDTO>();
		boolean isError = false;
		ArrayList<TtThreepackItemMinclassDTO> err = new ArrayList<TtThreepackItemMinclassDTO>();

		// 写入三包小类表
		TtThreepackItemMinclassDTO trmPO = new TtThreepackItemMinclassDTO();
		// 三包项目
		if (!StringUtils.isNullOrEmpty(list.getItemNo())) {
			if (list.getItemNo().toString().length() > 20) {
				TtThreepackItemMinclassDTO rowDto = new TtThreepackItemMinclassDTO();
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
					TtThreepackItemMinclassDTO rowDto = new TtThreepackItemMinclassDTO();
					rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
					rowDto.setErrorMsg("三包项目代码不存在!");
					err.add(rowDto);
				}
			}
		} else {
			TtThreepackItemMinclassDTO rowDto = new TtThreepackItemMinclassDTO();
			rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
			rowDto.setErrorMsg("三包项目代码不能为空!");
			err.add(rowDto);
		}
		// 三包项目小类代码
		if (!StringUtils.isNullOrEmpty(list.getMinclassNo())) {
			if (list.getMinclassNo().toString().length() > 20) {
				TtThreepackItemMinclassDTO rowDto = new TtThreepackItemMinclassDTO();
				rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
				rowDto.setErrorMsg("三包项目小类代码不能大于20字段!");
				err.add(rowDto);

			} 
		}else {
			TtThreepackItemMinclassDTO rowDto = new TtThreepackItemMinclassDTO();
			rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
			rowDto.setErrorMsg("三包项目小类代码不能为空!");
			err.add(rowDto);
		}
		// 三包项目小类名称
		if (!StringUtils.isNullOrEmpty(list.getMinclassName())) {
			if (list.getMinclassName().length() > 60) {
				TtThreepackItemMinclassDTO rowDto = new TtThreepackItemMinclassDTO();
				rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
				rowDto.setErrorMsg("三包项目小类代码不能大于60字段!");
				err.add(rowDto);

			}
		} else {
			TtThreepackItemMinclassDTO rowDto = new TtThreepackItemMinclassDTO();
			rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
			rowDto.setErrorMsg("三包项目小类名称不能为空!");
			err.add(rowDto);
		}
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.id, tt.minclass_no FROM TT_THREEPACK_ITEM_MINCLASS_DCS tt  LEFT JOIN TT_THREEPACK_ITEM_DCS t ON t.id=tt.item_id WHERE  tt.minclass_no= ?");
		queryParam.add(list.getMinclassNo());
		sql.append("and t.item_id = ? ");
		queryParam.add(list.getItemId());
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		if (resultList != null && resultList.size() > 0) {
			TtThreepackItemMinclassDTO rowDto = new TtThreepackItemMinclassDTO();
			rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
			rowDto.setErrorMsg("三包项目代码+小类代码已经存在!");
			err.add(rowDto);
		} else {
		
		}
		imp.setErrorList(err);
		logger.info("*****************校验完成!************************");
		if (isError) {
			return imp;
		} else {
			return null;
		}
	}
}
