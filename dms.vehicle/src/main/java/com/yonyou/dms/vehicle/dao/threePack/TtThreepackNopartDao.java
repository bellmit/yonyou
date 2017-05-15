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
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackNopartDTO;
import com.yonyou.dms.vehicle.domains.PO.threePack.TtThreepackNopartPO;

@Repository
public class TtThreepackNopartDao extends OemBaseDAO{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	public PageInfoDto findthreePack(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("	 select ID, PART_CODE, PART_NAME, PART_TYPE,REMARK, CREATE_BY,   \n");
		sql.append("     CREATE_DATE, UPDATE_BY, UPDATE_DATE, VER, IS_DEL, IS_ARC \n");
		sql.append("     from TT_THREEPACK_NOPART_DCS  \n");
		sql.append("     where  IS_DEL="+OemDictCodeConstants.IS_DEL_00+"      \n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("partCode"))) {
			sql.append("   AND PART_CODE = ? ");
			params.add("%"+queryParam.get("partCode")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("partName"))) {
			sql.append("   AND PART_NAME = ? ");
			params.add("%"+queryParam.get("partName")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("partType"))) {
			sql.append("   AND PART_TYPE  like(?) ");
			params.add("%"+queryParam.get("partType")+"%");
		}
		return sql.toString();
	}
	/**
	 * 根据ID删除
	 * 
	 * @param id
	 * @throws ServiceBizException
	 */
	public void deleteById(Long id) throws ServiceBizException {
		TtThreepackNopartPO wtp = TtThreepackNopartPO.findById(id);
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
	public void modifyMinclass(Long id, TtThreepackNopartDTO tcdto) throws ServiceBizException {
		TtThreepackNopartPO tc = TtThreepackNopartPO.findById(id);
		StringBuffer sb = new StringBuffer(
				"select part_type,part_code from TT_THREEPACK_NOPART_DCS where 1=1 and is_del='0' and part_code=?");
		List<Object> zz = new ArrayList<Object>();
		zz.add(tcdto.getPartCode());
		sb.append("and part_type=?");
		zz.add(tcdto.getPartType());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), zz);
		if (map.size() > 1) {
			throw new ServiceBizException("配件编号+配件类型已存在，请重新输入！");
		}else{
		setTtThreepackNopartPO(tc, tcdto);
		tc.saveIt();
	}
}
	/**
	 * 设置对象属性
	 * 
	 * @param tc
	 * @param user
	 */
	private void setTtThreepackNopartPO(TtThreepackNopartPO tc, TtThreepackNopartDTO tcdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tc.setString("part_code", tcdto.getPartCode());
		tc.setString("part_name", tcdto.getPartName());
		tc.setString("part_type", tcdto.getPartType());
		tc.setString("remark", tcdto.getRemark());
		tc.setDate("update_date", new Date());
		tc.setString("update_by", loginInfo.getUserId());

	}
	
	/**
	 * 配件及对应关系设定新增
	 * 
	 * @param tcbdto
	 * @return
	 * @throws ServiceBizException
	 */
	public long addMinclass(TtThreepackNopartDTO tcdto) throws ServiceBizException {
		StringBuffer sb = new StringBuffer(
				"select part_type,part_code from TT_THREEPACK_NOPART_DCS where 1=1 and part_code=?");
		List<Object> list = new ArrayList<Object>();
		list.add(tcdto.getPartCode());
		sb.append("and part_type=?");
		list.add(tcdto.getPartType());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
		if (map.size() > 0) {
			throw new ServiceBizException("配件编号+配件类型已存在，请重新输入！");
		} else {
			TtThreepackNopartPO tc = new TtThreepackNopartPO();
			tc.setString("part_code", tcdto.getPartCode());
			tc.setString("part_name", tcdto.getPartName());
			tc.setString("part_type", tcdto.getPartType());
			tc.setString("remark", tcdto.getRemark());
			tc.setDate("create_date", new Date());
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			tc.setDate("update_date", new Date());
			tc.setString("create_by", loginInfo.getUserId());
			tc.setLong("ver", 0);
			tc.setInteger("is_del", 0);
			tc.setInteger("is_arc", 0);
			tc.saveIt();
			return (Long) tc.getLongId();
		}
	}
	
	public ImportResultDto<TtThreepackNopartDTO> checkData(TtThreepackNopartDTO list) throws Exception {
		ImportResultDto<TtThreepackNopartDTO> imp = new ImportResultDto<TtThreepackNopartDTO>();
		boolean isError = false;
		ArrayList<TtThreepackNopartDTO> err = new ArrayList<TtThreepackNopartDTO>();

		
		TtThreepackNopartDTO trmPO = new TtThreepackNopartDTO();
		// 配件类别
		if (!StringUtils.isNullOrEmpty(list.getPartType())) {
			if (list.getPartType().length() > 30) {
				TtThreepackNopartDTO rowDto = new TtThreepackNopartDTO();
				rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
				rowDto.setErrorMsg("配件类别不能大于20字段!");
				err.add(rowDto);
			} 
		} else {
			TtThreepackNopartDTO rowDto = new TtThreepackNopartDTO();
			rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
			rowDto.setErrorMsg("配件类别不能为空!");
			err.add(rowDto);
		}
		// 配件编号
		if (!StringUtils.isNullOrEmpty(list.getPartCode())) {
			if (list.getPartCode().length() > 20) {
				TtThreepackNopartDTO rowDto = new TtThreepackNopartDTO();
				rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
				rowDto.setErrorMsg("配件编码不能大于20字段!");
				err.add(rowDto);
			} else {
				List<Object> queryParam = new ArrayList<Object>();
				StringBuffer sql = new StringBuffer();
				sql.append("select part_code from TT_PT_PART_BASE_DCS where part_code= ?");
				queryParam.add(list.getPartCode());
				List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
				if (resultList != null && resultList.size() > 0) {
				}else {
					TtThreepackNopartDTO rowDto = new TtThreepackNopartDTO();
					rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
					rowDto.setErrorMsg("配件编码不存在!");
					err.add(rowDto);
				}
				}
		}else {
			TtThreepackNopartDTO rowDto = new TtThreepackNopartDTO();
			rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
			rowDto.setErrorMsg("配件编码不能为空!");
			err.add(rowDto);
		}
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(
				"select part_type,part_code from TT_THREEPACK_NOPART_DCS where 1=1 and part_code=?");
		List<Object> tcdto = new ArrayList<Object>();
		tcdto.add(list.getPartCode());
		sb.append("and part_type=?");
		tcdto.add(list.getPartType());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), tcdto);
		if (map.size() > 0) {
			TtThreepackNopartDTO rowDto = new TtThreepackNopartDTO();
			rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
			rowDto.setErrorMsg("配件类型+配件代码已经存在!");
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
	 * 将数据插入
	 * 
	 * @param tvypDTO
	 */
	public void insert(TtThreepackNopartDTO tvypDTO) {
		TtThreepackNopartPO tvypPO = new TtThreepackNopartPO();
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
	private void setTtThreepackItemMinclassPO(TtThreepackNopartPO tc, TtThreepackNopartDTO tcdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tc.setString("part_code", tcdto.getPartCode());
		tc.setString("part_name", tcdto.getPartName());
		tc.setString("part_type", tcdto.getPartType());
		tc.setString("remark", tcdto.getRemark());
		tc.setDate("create_date", new Date());
		tc.setDate("update_date", new Date());
		tc.setString("create_by", loginInfo.getUserId());
		tc.setLong("ver", 0);
		tc.setInteger("is_del", 0);
		tc.setInteger("is_arc", 0);

	}
}
