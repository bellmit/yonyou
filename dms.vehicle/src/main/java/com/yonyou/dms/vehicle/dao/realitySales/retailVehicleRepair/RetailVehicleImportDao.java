package com.yonyou.dms.vehicle.dao.realitySales.retailVehicleRepair;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.vehicle.domains.PO.retailReportQuery.TmpVsNvdrPO;

@Repository
@SuppressWarnings("all")
public class RetailVehicleImportDao extends OemBaseDAO{

	public void dilete() {
		TmpVsNvdrPO.deleteAll();
	}

	public List<Map> allMessageQuery() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT N.LINE_NUMBER, -- 行号 \n");
		sql.append("       N.VIN, -- VIN号 \n");
		sql.append("       N.CHECK_RESULT, -- 验证结果 \n");
		sql.append("       N.CHECK_STATUS, -- 状态\n");
		sql.append("       N.UPLOAD_DATE -- 零售上报日期 \n");
		sql.append("  FROM TMP_VS_NVDR N \n");
		sql.append(" ORDER BY N.LINE_NUMBER ASC \n");
		List<Map> allMessage = OemDAOUtil.findAll(sql.toString(), null);
		return allMessage;
	}

	public Map<String, String> countDataNum() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(A.ID) AS NUM, -- 全部数据数量 \n");
		sql.append("       COUNT(B.ID) AS NUM1 -- 错误数据数量 \n");
		sql.append("  FROM TMP_VS_NVDR A \n");
		sql.append("  LEFT JOIN TMP_VS_NVDR B ON B.ID = A.ID AND B.CHECK_STATUS = 0 \n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}

	public List<Map> queryReeDate(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT N.VIN, -- VIN号 \n");
		sql.append("       N.UPLOAD_DATE, -- 补传日期 \n");
		sql.append("       N.LINE_NUMBER, -- 行号 \n");
		sql.append("       N.CHECK_RESULT -- 效验结果 \n");
		sql.append("  FROM TMP_VS_NVDR N \n");
		sql.append(" WHERE N.CHECK_STATUS = 0 \n");
		sql.append(" ORDER BY N.LINE_NUMBER ASC \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 根据选择类型获取相应数据
	 * @param id
	 * @return
	 */
	public List<Map> queryReeDate(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT N.LINE_NUMBER, -- 行号 \n");
		sql.append("       N.VIN, -- VIN号 \n");
		sql.append("       N.CHECK_RESULT, -- 验证结果 \n");
		sql.append("       N.CHECK_STATUS, -- 状态\n");
		sql.append("       N.UPLOAD_DATE -- 零售上报日期 \n");
		sql.append("  FROM TMP_VS_NVDR N \n");
		if(OemDictCodeConstants.DISPALY_DATA_TEYP1.equals(id)){
			sql.append("  WHERE 1=1 \n");
		}
		if(OemDictCodeConstants.DISPALY_DATA_TEYP2.equals(id)){
			sql.append("  WHERE N.CHECK_STATUS = 1 \n");
		}
		if(OemDictCodeConstants.DISPALY_DATA_TEYP3.equals(id)){
			sql.append("  WHERE N.CHECK_STATUS = 0 \n");
		}
		sql.append(" ORDER BY N.LINE_NUMBER ASC \n");
		List<Map> allMessage = OemDAOUtil.findAll(sql.toString(), null);
		return allMessage;
	}
	


}
