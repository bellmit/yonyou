package com.infoeai.eai.action.k4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.k4.K4SICommonDao;
import com.infoeai.eai.vo.S0008XmlVO;
import com.yonyou.dms.common.domains.PO.basedata.TiK4VsNvdrPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Service
public class S0008Impl extends BaseService implements S0008 {
	private static final Logger logger = LoggerFactory.getLogger(S0008Impl.class);
	@Autowired
	K4SICommonDao dao;

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String format = df.format(new Date());

	@Override
	public List<S0008XmlVO> getInfo() throws Exception {

		logger.info("========== S0008经销商SO导入处理开始 ==========");

		List<S0008XmlVO> s0008List = null;

		try {

			/******************** S0008抽取数据 开启事物 ********************/
			beginDbService();
			s0008List = getS0008Info(); // 抽取数据逻辑

			dbService.endTxn(true);

			/******************** S0008抽取数据 关闭事物 ********************/

			logger.info("========== S0008经销商SO导入处理结束 ==========");

			return s0008List;

		} catch (Exception e) {
			dbService.endTxn(false);
			throw new Exception("========== S0008查询数据异常 ==========" + e.getMessage(), e.getCause());
		} finally {
			Base.detach();
			dbService.clean();
		}

	}

	/**
	 * 发送成功修改状态
	 * 
	 * @param list_zpod
	 */
	public List<S0008XmlVO> updateVoMethod(List<S0008XmlVO> list_s0008) throws Exception {

		try {

			beginDbService();
			if (null != list_s0008 && list_s0008.size() > 0) {

				/******************** UPDATE 开启事物 ********************/


				for (int i = 0; i < list_s0008.size(); i++) {

					// 更新接口表的发送状态
					TiK4VsNvdrPO setPo = TiK4VsNvdrPO.findFirst("vin=? and ACTION_CODE=? and ACTION_TIME=?",
							list_s0008.get(i).getVIN(), list_s0008.get(i).getACTION_CODE(),
							list_s0008.get(i).getACTION_TIME());
					setPo.setString("Row_Id", list_s0008.get(i).getROW_ID());
					setPo.setInteger("Is_Result", OemDictCodeConstants.IF_TYPE_YES);
					setPo.setInteger("Update_By", OemDictCodeConstants.K4_S0008);
					setPo.setTimestamp("Update_Date", format);
					setPo.saveIt();
				}

				dbService.endTxn(true);

				/******************** UPDATE 结束事物 ********************/
			}

			logger.info("========== S0008经销商SO导入处理更新方法结束 ==========");

			return null;

		} catch (Exception e) {
			dbService.endTxn(false);
			throw new Exception("========== S0008修改状态处理异常 ==========" + e.getMessage());
		} finally {
			Base.detach();
			dbService.clean();
		}
	}

	/**
	 * 获取S0008信息
	 */
	private List<S0008XmlVO> getS0008Info() {

		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT T.ACTION_CODE, -- 交易代码 \n");
		sql.append("       T.ACTION_DATE, -- 交易日期 \n");
		sql.append("       T.ACTION_TIME, -- 交易时间 \n");
		sql.append("       T.INSPECTION_DATE, -- 验收日期 \n");
		sql.append("       T.VIN, -- 车架号 \n");
		sql.append("       C.FCA_CODE AS DEALER_CODE, -- 经销商代码 \n");
		sql.append("       T.VEHICLE_USE, -- 车辆用途 \n");
		sql.append("       T.SALE_DATE, -- 零售日期 \n");
		sql.append("       T.REGISTRATION_NUMBER, -- 登记号码 \n");
		sql.append("       T.REGISTRATION_DATE, -- 登记日期 \n");
		sql.append("       CD.CODE_DESC AS CTM_TYPE, -- 个人 OR 组织 \n");
		sql.append("       T.CTM_NAME, -- 抬头 \n");
		sql.append("       T.COMPANY_NAME, -- 公司名称 \n");
		sql.append("       T.FIRST_NAME, -- 姓 \n");
		sql.append("       T.SECOND_NAME, -- 名 \n");
		sql.append("       T.TEL_NUMBER1, -- 联系电话1 \n");
		sql.append("       T.TEL_NUMBER2, -- 联系电话2 \n");
		sql.append("       T.MOBILE_PHONE, -- 移动电话 \n");
		sql.append("       T.FAX_NUMBER, -- 传真号码 \n");
		sql.append("       T.E_MAIL, -- 邮箱地址 \n");
		sql.append("       T.ADDRESS, -- 联系地址 \n");
		sql.append("       R1.REGION_NAME AS COUNTRY, -- 国家 \n");
		sql.append("       R2.REGION_NAME AS PROVINCE, -- 省份 \n");
		sql.append("       R3.REGION_NAME AS CITY, -- 城市 \n");
		sql.append("       T.POSTAL_CODE, -- 邮政编码 \n");
		sql.append("       T.BIRTHDAY, -- 生日 \n");
		sql.append("       T.FLG, -- 标识 \n");

		// sql.append(" NVL(CASE WHEN TRIM(T.ROW_ID) <> '' THEN T.ROW_ID ELSE
		// NULL END, \n");
		sql.append("           'S0008' || year(now()) || month(now())|| right(T.IF_ID, 9) AS ROW_ID \n");

		sql.append("  FROM TI_K4_VS_NVDR T \n");
		sql.append("  LEFT JOIN TC_CODE_DCS CD ON CD.CODE_ID = T.CTM_TYPE \n");
		sql.append("  LEFT JOIN TM_COMPANY C ON C.COMPANY_CODE = T.DEALER_CODE \n");
		sql.append("  LEFT JOIN TM_REGION R1 ON R1.REGION_CODE = T.COUNTRY \n");
		sql.append("  LEFT JOIN TM_REGION R2 ON R2.REGION_CODE = T.PROVINCE \n");
		sql.append("  LEFT JOIN TM_REGION R3 ON R3.REGION_CODE = T.CITY \n");
		sql.append(" WHERE T.IS_DEL = '" + OemDictCodeConstants.IS_DEL_00 + "' \n");
		sql.append("   AND T.ACTION_CODE = 'ZPOD' \n"); // 车辆验收
		sql.append("   AND T.IS_RESULT = '" + OemDictCodeConstants.IF_TYPE_NO + "' \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		List<S0008XmlVO> ll = new ArrayList<S0008XmlVO>();

		S0008XmlVO bean = new S0008XmlVO();
		if (null != list || list.size() >= 0) {
			for (Map map : list) {
				bean.setACTION_CODE(CommonUtils.checkNull(map.get("ACTION_CODE"))); // 交易代码
				bean.setACTION_DATE(CommonUtils.checkNull(map.get("ACTION_DATE"))); // 交易日期
				bean.setACTION_TIME(CommonUtils.checkNull(map.get("ACTION_TIME"))); // 验收日期
				bean.setINSPECTION_DATE(CommonUtils.checkNull(map.get("INSPECTION_DATE"))); // 验收日期
				bean.setVIN(CommonUtils.checkNull(map.get("VIN"))); // 车架号
				bean.setDEALER_CODE(CommonUtils.checkNull(map.get("DEALER_CODE"))); // 经销商代码
				bean.setVEHICLE_USE(CommonUtils.checkNull(map.get("VEHICLE_USE"))); // 车辆用途
				bean.setSALE_DATE(CommonUtils.checkNull(map.get("SALE_DATE"))); // 零售日期
				bean.setREGISTRATION_NUMBER(CommonUtils.checkNull(map.get("REGISTRATION_NUMBER"))); // 登记号码
				bean.setREGISTRATION_DATE(CommonUtils.checkNull(map.get("REGISTRATION_DATE"))); // 登记日期
				bean.setCTM_TYPE(CommonUtils.checkNull(map.get("CTM_TYPE"))); // 个人 or 组织
				bean.setCTM_NAME(CommonUtils.checkNull(map.get("CTM_NAME"))); // 抬头
				bean.setCOMPANY_NAME(CommonUtils.checkNull(map.get("COMPANY_NAME"))); // 公司名称
				bean.setFIRST_NAME(CommonUtils.checkNull(map.get("FIRST_NAME"))); // 姓
				bean.setSECOND_NAME(CommonUtils.checkNull(map.get("SECOND_NAME"))); // 名
				bean.setTEL_NUMBER1(CommonUtils.checkNull(map.get("TEL_NUMBER1"))); // 联系电话1
				bean.setTEL_NUMBER2(CommonUtils.checkNull(map.get("TEL_NUMBER2"))); // 联系电话2
				bean.setMOBILE_PHONE(CommonUtils.checkNull(map.get("MOBILE_PHONE"))); // 移动电话
				bean.setFAX_NUMBER(CommonUtils.checkNull(map.get("FAX_NUMBER"))); // 传真
				bean.setE_MAIL(CommonUtils.checkNull(map.get("E_MAIL"))); // 邮箱地址
				bean.setADDRESS(CommonUtils.checkNull(map.get("ADDRESS"))); // 联系地址
				bean.setCOUNTRY(CommonUtils.checkNull(map.get("COUNTRY"))); // 国家
				bean.setPROVINCE(CommonUtils.checkNull(map.get("PROVINCE"))); // 省份
				bean.setCITY(CommonUtils.checkNull(map.get("CITY"))); // 城市
				bean.setPOSTAL_CODE(CommonUtils.checkNull(map.get("POSTAL_CODE"))); // 邮编
				bean.setBIRTHDAY(CommonUtils.checkNull(map.get("BIRTHDAY"))); // 生日
				bean.setFLG(CommonUtils.checkNull(map.get("FLG"))); // 标识
				bean.setROW_ID(CommonUtils.checkNull(map.get("ROW_ID"))); // ROW_ID
				ll.add(bean);
			}

		} else {
			ll.add(bean);
		}
		return ll;

	}

}
