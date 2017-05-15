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
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackNovehicleDTO;
import com.yonyou.dms.vehicle.domains.PO.threePack.TtThreepackNovehiclePO;

/**
 * 非三包车辆维护dao
 * @author zhoushijie
 *
 */
@Repository
public class ThreePackNoVehicleDao extends OemBaseDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	public PageInfoDto findItem(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select TN.ID,TN.VIN, E.LICENSE_NO,R.CTM_NAME,R.MAIN_PHONE,VW.MODEL_NAME,DATE_FORMAT(E.PURCHASE_DATE, '%Y-%m-%d') as PURCHASE_DATE,TN.REMARK \n");
		sql.append("FROM TT_THREEPACK_NOVEHICLE_DCS TN \n" );
		sql.append(" left join TM_VEHICLE_DEC E on TN.VIN = E.VIN \n" );
		sql.append(" left join  TT_VS_SALES_REPORT T on T.VEHICLE_ID = E.VEHICLE_ID \n" );
		sql.append(" left join  ("+getVwMaterialSql()+") VW on E.MATERIAL_ID = VW.MATERIAL_ID \n" );
		sql.append(" left join   TT_VS_CUSTOMER R on  T.CTM_ID = R.CTM_ID \n");
		sql.append(" WHERE  TN.IS_DEL="+OemDictCodeConstants.IS_DEL_00+" \n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append("   AND TN.VIN = ? ");
			params.add(queryParam.get("vin"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("licenseNo"))) {
			sql.append("   AND E.LICENSE_NO = ? ");
			params.add(queryParam.get("licenseNo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ctmName"))) {
			sql.append("   AND R.CTM_NAME = ? ");
			params.add(queryParam.get("ctmName"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("mainPhone"))) {
			sql.append("   AND R.MAIN_PHONE = ? ");
			params.add(queryParam.get("mainPhone"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelName"))) {
			sql.append("   AND VW.MODEL_NAME = ? ");
			params.add(queryParam.get("modelName"));
		}
		sql.append(" ) lero");
		return sql.toString();
	}
	/**
	 * 修改
	 * 
	 * @param id
	 * @param user
	 * @throws ServiceBizException
	 */
	public void modifynoVehicle(Long id, TtThreepackNovehicleDTO tcdto) throws ServiceBizException {
		TtThreepackNovehiclePO tc = TtThreepackNovehiclePO.findById(id);
		setTtThreepackItem(tc, tcdto);
		tc.saveIt();
	}
	/**
	 * 设置对象属性
	 * 
	 * @param tc
	 * @param user
	 */
	private void setTtThreepackItem(TtThreepackNovehiclePO tc, TtThreepackNovehicleDTO tcdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tc.setString("remark", tcdto.getRemark());
		tc.setDate("update_date", new Date());
		tc.setLong("update_by", loginInfo.getUserId());
	}
	
	public List<Map> findById(Long id) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select TN.ID,TN.VIN, E.LICENSE_NO,R.CTM_NAME,R.MAIN_PHONE,VW.MODEL_NAME,DATE_FORMAT(E.PURCHASE_DATE,'%y-%m-%d')AS PURCHASE_DATE ,TN.REMARK \n");
		sql.append("FROM TT_THREEPACK_NOVEHICLE_DCS TN \n" );
		sql.append(" left join TM_VEHICLE_DEC E on TN.VIN = E.VIN \n" );
		sql.append(" left join  TT_VS_SALES_REPORT T on T.VEHICLE_ID = E.VEHICLE_ID \n" );
		sql.append(" left join ("+getVwMaterialSql()+") VW on E.MATERIAL_ID = VW.MATERIAL_ID \n" );
		sql.append(" left join   TT_VS_CUSTOMER R on  T.CTM_ID = R.CTM_ID \n");
		sql.append(" WHERE  TN.IS_DEL="+OemDictCodeConstants.IS_DEL_00+" \n");
		sql.append("   AND TN.ID = ? ");
		params.add(id);
		sql.append(" ) lero");
		List<Map> map = OemDAOUtil.findAll(sql.toString(), params);
		return map;
			
		}
	
	/**
	 * VIN号查询
	 * @param basecodePO
	 * @param condition
	 * @param curPage
	 * @param yangshaolong
	 * @return
	 * @throws Exception
	 */
		
		public PageInfoDto   partQuery(Map<String, String> queryParam) throws Exception {
			List<Object> params = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer();
			sql.append(" select  E.VEHICLE_ID, E.VIN, E.LICENSE_NO, R.CTM_NAME,R.MAIN_PHONE,VW.MODEL_NAME,DATE_FORMAT(E.PURCHASE_DATE,'%Y-%m-%d') as PURCHASE_DATE \n");
		    sql.append(" FROM TT_VS_SALES_REPORT T, TT_VS_CUSTOMER R, TM_VEHICLE_DEC E,("+getVwMaterialSql()+") VW\n");
			sql.append(" WHERE  T.CTM_ID = R.CTM_ID AND T.VEHICLE_ID = E.VEHICLE_ID AND E.MATERIAL_ID = VW.MATERIAL_ID \n");
			if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sql.append("   AND E.VIN = ? ");
				params.add(queryParam.get("vin"));
			}
			PageInfoDto  pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
			return pageInfoDto;
		}
		
		
		/**
		 * 根据VIN获得车辆详细信息
		 * @param params
		 * @return
		 * @throws BizException 
		 */

		public List<Map> quertVehicleDetailInfo(String  vin) {
			List<Object> param=new ArrayList<Object>();//参数
			StringBuffer sqlStr= new StringBuffer();
			sqlStr.append(" select  E.VEHICLE_ID, E.VIN, E.LICENSE_NO, R.CTM_NAME,R.MAIN_PHONE,VW.MODEL_NAME,DATE_FORMAT(E.PURCHASE_DATE,'%y-%m-%d') AS PURCHASE_DATE \n");
		    sqlStr.append(" FROM TT_VS_SALES_REPORT T, TT_VS_CUSTOMER R, TM_VEHICLE_DEC E,("+getVwMaterialSql()+") VW\n");
			sqlStr.append(" WHERE  T.CTM_ID = R.CTM_ID AND T.VEHICLE_ID = E.VEHICLE_ID AND E.MATERIAL_ID = VW.MATERIAL_ID \n");
			if (!StringUtils.isNullOrEmpty(vin)) {
			sqlStr.append("   AND E.VIN = ? ");
			param.add(vin);
			}
			List<Map> resultList = OemDAOUtil.findAll(sqlStr.toString(), param);
			return resultList;
		}
		
		/**
		 * 非三包车辆新增
		 * 
		 * @param tcbdto
		 * @return
		 * @throws ServiceBizException
		 */
		public long addnoVehicle(TtThreepackNovehicleDTO tcdto) throws ServiceBizException {
			StringBuffer sb = new StringBuffer(
					"select vin from TT_THREEPACK_NOVEHICLE_DCS where is_del='0' and vin=? ");
			List<Object> list = new ArrayList<Object>();
			list.add(tcdto.getVin());
			List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
			if (map.size() > 0) {
				throw new ServiceBizException("vin号已存在，请重新输入！");
			} else {
				TtThreepackNovehiclePO tc = new TtThreepackNovehiclePO();
				tc.setString("vin", tcdto.getVin());
				tc.setString("remark", tcdto.getRemark());
				tc.setDate("create_date", new Date());
				LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				tc.setLong("create_by", loginInfo.getUserId());
				tc.setInteger("ver", 0);
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
			TtThreepackNovehiclePO wtp = TtThreepackNovehiclePO.findById(id);
			wtp.setInteger("is_del", 1);
			wtp.saveIt();
		}
		/**
		 * 导入校验
		 * @param list
		 * @return
		 * @throws Exception
		 */
		public ImportResultDto<TtThreepackNovehicleDTO> checkData(TtThreepackNovehicleDTO list) throws Exception {
			ImportResultDto<TtThreepackNovehicleDTO> imp = new ImportResultDto<TtThreepackNovehicleDTO>();
			boolean isError = false;
			ArrayList<TtThreepackNovehicleDTO> err = new ArrayList<TtThreepackNovehicleDTO>();

			
			TtThreepackNovehicleDTO trmPO = new TtThreepackNovehicleDTO();
			// VIN总表校验
			if (!StringUtils.isNullOrEmpty(list.getVin())) {
				if (list.getVin().length() > 20) {
					TtThreepackNovehicleDTO rowDto = new TtThreepackNovehicleDTO();
					rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
					rowDto.setErrorMsg("VIN代码不能大于20字段!");
					err.add(rowDto);
				} else {
					List<Object> queryParam = new ArrayList<Object>();
					StringBuffer sql = new StringBuffer();
					sql.append("select vin from TM_VEHICLE where vin= ?");
					queryParam.add(list.getVin());
					List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
					if (resultList != null && resultList.size() > 0) {
					} else {
						TtThreepackNovehicleDTO rowDto = new TtThreepackNovehicleDTO();
						rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
						rowDto.setErrorMsg("VIN代码不存在!");
						err.add(rowDto);
					}
				}
			} else {
				TtThreepackNovehicleDTO rowDto = new TtThreepackNovehicleDTO();
				rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
				rowDto.setErrorMsg("VIN代码不能为空!");
				err.add(rowDto);
			}
			
		
			// VIN导入表校验
			List<Object> queryParam = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT vin FROM TT_THREEPACK_NOVEHICLE_DCS tt where vin= ?");
			queryParam.add(list.getVin());
			List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
			if (resultList != null && resultList.size() > 0) {
				TtThreepackNovehicleDTO rowDto = new TtThreepackNovehicleDTO();
				rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
				rowDto.setErrorMsg("VIN代码已经存在!");
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
		public long insert(TtThreepackNovehicleDTO tcdto) throws ServiceBizException {
			TtThreepackNovehiclePO tc = new TtThreepackNovehiclePO();
			tc.setString("vin", tcdto.getVin());
			tc.setString("remark", tcdto.getRemark());
			tc.setDate("create_date", new Date());
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			tc.setLong("create_by", loginInfo.getUserId());
			tc.setInteger("ver", 0);
			tc.setInteger("is_del", 0);
			tc.setInteger("is_arc", 0);
			tc.saveIt();
			return (Long) tc.getLongId();
		}
}
