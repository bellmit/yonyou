/**
 * @Title: DealerRebateImportDao.java
 * @Description:
 * @Copyright: Copyright (c) 2013
 * @Company: http://autosoft.ufida.com
 * @Date: 2013-5-17
 * @author niehao
 * @version 1.0
 * @remark
 */
package com.infoeai.eai.dao.ctcai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infoeai.eai.vo.Dcs2CtcaiVO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@SuppressWarnings("rawtypes")
@Repository
public class SI03Dao extends OemBaseDAO{

	/**
	 * 功能说明:查询返利导入信息——TI_REBATE 创
	 * 创建人: zhangRM 
	 * 创建日期: 2013-05-21
	 * @return
	 */
	public List<Dcs2CtcaiVO> getSI01Info() {
		List<Dcs2CtcaiVO> resultList =  new ArrayList<Dcs2CtcaiVO>();
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT REBATE_ID, \n");
		sql.append("         'FLRT' ACTION_CODE, \n");    //固定交易代码
		sql.append("         DATE_FORMAT (create_date, '%Y%m%d') ACTION_DATE, \n");
		sql.append("         DATE_FORMAT (create_date, '%H%i%s') ACTION_TIME, \n");
		sql.append("         (SELECT ctcai_code FROM TM_COMPANY WHERE company_code = tr.DEALER_CODE) DEALER_CODE, \n");
		sql.append("         REBATE_CODE, \n");
		sql.append("         (select CODE_DESC from TT_VS_REBATE_TYPE where CODE_ID=REBATE_TYPE) REBATE_TYPE, \n");
		sql.append("         REBATE_AMOUNT, \n");
		sql.append("         (select acnt from tc_user where user_id = tr.create_by) OPERATOR, \n");
		sql.append("         REMARK  \n");
		sql.append("    FROM TI_REBATE tr \n");
		sql.append("    where (scan_status = '0' or scan_status is null)\n");
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		
		for (Map map : list) {
			Dcs2CtcaiVO bean = new Dcs2CtcaiVO();
			bean.setSequenceId(Long.parseLong(CommonUtils.checkNull(map.get("REBATE_ID"),"0")));
			bean.setActionCode(CommonUtils.checkNull(map.get("ACTION_CODE")));
			bean.setActionDate(CommonUtils.checkNull(map.get("ACTION_DATE")));
			bean.setActionTime(CommonUtils.checkNull(map.get("ACTION_TIME")));
			bean.setDealerCode(CommonUtils.checkNull(map.get("DEALER_CODE")));
			bean.setRebateCode(CommonUtils.checkNull(map.get("REBATE_CODE")));
			bean.setRebateType(CommonUtils.checkNull(map.get("REBATE_TYPE")));
			bean.setRebateAmount(CommonUtils.checkNull(map.get("REBATE_AMOUNT")));
			bean.setOperator(CommonUtils.checkNull(map.get("OPERATOR")));
			bean.setRemark(CommonUtils.checkNull(map.get("REMARK")));
			resultList.add(bean);
		}
		return resultList;
	}
}
