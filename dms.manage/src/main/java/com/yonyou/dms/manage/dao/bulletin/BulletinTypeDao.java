package com.yonyou.dms.manage.dao.bulletin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.manage.domains.PO.bulletin.TmBltntypeRolePO;

@Repository
public class BulletinTypeDao extends OemBaseDAO {

	public PageInfoDto search(Map<String, String> param) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(" SELECT TYPE_ID , TYPENAME , REMARK , 	CASE STATUS WHEN 0 THEN '注销' WHEN 1 THEN '可用' ELSE '' END AS STATUS \n");
		sql.append("FROM TM_BULLETIN_TYPE WHERE 1=1 \n");
		return OemDAOUtil.pageQuery(sql.toString(), queryParam);
	}

	public List<Map> checkType(String typename) {
		List<Object> queryParam = new ArrayList<Object>();
		String sql = "select * from TM_BULLETIN_TYPE where TYPENAME = ?";
		queryParam.add(typename);
		return OemDAOUtil.findAll(sql, queryParam);
	}

	public List<Map> getTypeUser(Long typeId) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT distinct TU.USER_ID, TU.ACNT, TU.NAME \n");
		sql.append("FROM TM_BLTNTYPE_ROLE TBR, \n");
		sql.append("     TC_USER TU \n");
		sql.append(" WHERE TU.USER_ID = TBR.EMPLOYEE_ID \n");
		sql.append("   AND TBR.TYPE_ID = ? \n");
		queryParam.add(typeId);
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

	public PageInfoDto searchUser(Map<String, String> param) {
		String acnt = param.get("acnt");
		String name = param.get("name");
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer query = new StringBuffer();
		query.append("SELECT TU.USER_ID, TU.ACNT, TU.NAME \n");
		query.append("FROM TM_COMPANY TC, \n");
		query.append("     TC_USER TU \n");
		query.append(" WHERE TU.COMPANY_ID = TC.COMPANY_ID \n");
		query.append("   AND TC.COMPANY_TYPE = '"+ OemDictCodeConstants.COMPANY_TYPE_OEM +"' \n");
		if(StringUtils.isNotBlank(acnt)) { // 拼查询职位的SQL
	        	query.append(" AND upper(TU.ACNT) LIKE ? \n");
	        	queryParam.add("%"+acnt.toUpperCase()+"%");
	     }
		if(StringUtils.isNotBlank(name)) { // 拼查询职位的SQL
	        	query.append(" AND upper(TU.NAME) LIKE ? \n");
	        	queryParam.add("%"+name.toUpperCase()+"%");
	     }
		return OemDAOUtil.pageQuery(query.toString(), queryParam);
	}

	/**
	 * 修改时校验重复
	 * @param typename
	 * @param typeId
	 * @return
	 */
	public List<Map> checkType(String typename, Long typeId) {
		List<Object> queryParam = new ArrayList<Object>();
		String sql = "select * from TM_BULLETIN_TYPE where TYPENAME = ? and TYPE_ID != ?";
		queryParam.add(typename);
		queryParam.add(typeId);
		return OemDAOUtil.findAll(sql, queryParam);
	}

	/**
	 * 插入数据之前先删除原有数据
	 * @param typeId
	 */
	public void delTypeRoleBeforeUpd(Long typeId) {
		List<Object> param = new ArrayList<Object>();
		String sql = "delete from tm_bltntype_role where type_id = ? ";
		param.add(typeId);
		OemDAOUtil.execBatchPreparement(sql, param);
		
	}
	
	public List<TmBltntypeRolePO> getTypeRoleForDel(Long typeId){
		String sql = "select * from tm_bltntype_role where type_id = ?";
		return TmBltntypeRolePO.findBySQL(sql, typeId);
	}
	
	public TmBltntypeRolePO getTypeRolePO(Long userId, Long typeId){
		String sql = "select * from tm_bltntype_role where EMPLOYEE_ID = ? and TYPE_ID = ? ";
		List<TmBltntypeRolePO> list = TmBltntypeRolePO.findBySQL(sql, userId,typeId);
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	public TmBltntypeRolePO getTypeRolePO(Long userId){
		String sql = "select * from tm_bltntype_role where EMPLOYEE_ID = ? ";
		List<TmBltntypeRolePO> list = TmBltntypeRolePO.findBySQL(sql, userId);
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

}
