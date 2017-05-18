package com.yonyou.dms.retail.dao.basedata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.Util.DateUtil;
import com.yonyou.dms.common.domains.PO.basedata.TmLoanRatMaintainPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.domains.DTO.basedata.TmLoanRatMaintainDTO;

/**
 * 贴息利率维护
 * 
 * @author Administrator
 *
 */
@Repository
@SuppressWarnings("rawtypes")
public class TmLoanRatMainTainDao extends OemBaseDAO {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 查询方法
	 * 
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	public PageInfoDto getlist(Map<String, String> queryParam) throws Exception {
		logger.info("进入查询方法");
		List<Object> params = new ArrayList<Object>();
		String sql = findDiscountRateInfo(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * 贴息利率信息查询
	 * 
	 * @return
	 */
	public String findDiscountRateInfo(Map<String, String> queryParam, List<Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();

		sql.append(
				"SELECT CONCAT(ROUND(TLRM.RATE,2),'%') AS RATE,VM1.GROUP_NAME AS BRAND_GROUP_NAME,VM2.GROUP_NAME AS SERIES_GROUP_NAME,VM3.GROUP_NAME AS STYLE_GROUP_NAME, TLRM.ID,TLRM.BRAND_GROUP_ID,TLRM.SERIES_GROUP_ID,TLRM.CODE_ID,tb.BANK_NAME,\n");
		sql.append(
				" CASE WHEN TLRM.IS_SCAN=0 THEN '未下发' ELSE'已下发' END IS_SCAN,TLRM.INSTALLMENT_NUMBER,TLRM.STYLE_GROUP_ID, TLRM.SEND_BY,DATE_FORMAT(TLRM.SEND_DATE,'%Y-%m-%d %H:%i:%s') AS SEND_DATE,TU.NAME, \n");
		sql.append(
				"concat(TLRM.DPM_S,'%','-',TLRM.DPM_E,'%') AS DPM_S,DATE_FORMAT(TLRM.EFFECTIVE_DATE_S,'%Y-%m-%d ') AS EFFECTIVE_DATE_S,DATE_FORMAT(TLRM.EFFECTIVE_DATE_E,'%Y-%m-%d ') AS EFFECTIVE_DATE_E \n");
		sql.append("        FROM TM_LOAN_RAT_MAINTAIN TLRM \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP VM1 ON TLRM.BRAND_GROUP_ID = VM1.GROUP_CODE \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP VM2 ON TLRM.SERIES_GROUP_ID = VM2.GROUP_CODE \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP VM3 ON TLRM.STYLE_GROUP_ID = VM3.GROUP_CODE \n");
		sql.append("  inner join TC_BANK tb on TLRM.CODE_ID=tb.ID  \n");
		sql.append("  left join TC_USER TU on  TU.USER_ID = TLRM.SEND_BY  \n");
		sql.append("  WHERE 1=1 AND VM1.GROUP_LEVEL = 1 AND VM2.GROUP_LEVEL = 2 and TLRM.IS_VALID = \n");
		sql.append(OemDictCodeConstants.STATUS_ENABLE);
		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandName"))) {
			String brandId = queryParam.get("brandName");
			String brandCode = getBrandCode(brandId);
			sql.append(" and TLRM.BRAND_GROUP_ID = ?");
			params.add(brandCode);
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			String seriesId = queryParam.get("seriesName");
			String seriesCode = getSeriesCode2(seriesId);
			sql.append(" and TLRM.SERIES_GROUP_ID = ?");
			params.add(seriesCode);
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			String groupId = queryParam.get("groupName");
			String groupCode = getGroupCode(groupId);
			sql.append(" and STYLE_GROUP_ID = ? ");
			params.add(groupCode);
		}
		//有效日期
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDateTB"))) {
			sql.append(" and TLRM.EFFECTIVE_DATE_S >= '"+queryParam.get("beginDateTB")+"' ");
			sql.append(" and TLRM.EFFECTIVE_DATE_S <= '"+queryParam.get("endDateTB")+"' ");
		}
		//银行名称
		if (!StringUtils.isNullOrEmpty(queryParam.get("bankName"))) {
			sql.append(" and TLRM.CODE_ID IN ('"+queryParam.get("bankName")+"') ");
		}
		//分期期数
		if (!StringUtils.isNullOrEmpty(queryParam.get("installmentNumber"))) {
			sql.append(" and TLRM.INSTALLMENT_NUMBER IN ('"+queryParam.get("installmentNumber")+"') ");
		}
		logger.info("进入查询方法SQL : " + sql.toString() +"--"+ params.toString());
		return sql.toString();
	}

	/**
	 * 新增
	 * 
	 * @param tcbdto
	 * @throws ServiceBizException
	 */

	public void addTmLoan(TmLoanRatMaintainDTO dto, LoginInfoDto loginInfo) throws ServiceBizException {
		String brandId = dto.getBrandGroupcode();
		String brandCode = getBrandCode(brandId);
		String seriesId = dto.getSeriesGroupcode();
		String seriesCode = getSeriesCode2(seriesId);
		String groupId = dto.getStyleGroupcode();
		String groupCode = getGroupCode(groupId);
		StringBuffer sb = new StringBuffer("SELECT TLRM.ID FROM TM_LOAN_RAT_MAINTAIN TLRM "
				+ " WHERE  TLRM.BRAND_GROUP_ID = '" + brandCode + "'  AND CODE_ID =	'" + dto.getCodeId()
				+ "'  AND INSTALLMENT_NUMBER =	'" + dto.getInstallmentNumber() + "' AND  TLRM.SERIES_GROUP_ID = '"
				+ seriesCode + "' AND  TLRM.STYLE_GROUP_ID = '" + groupCode
				+ "' AND  (( TLRM.DPM_S <= '" + dto.getDpmS() + "' AND '" + dto.getDpmS() + "' <= TLRM.DPM_E ) "
				+ " OR ( TLRM.DPM_S <= '" + dto.getDpmE() + "' AND '" + dto.getDpmE() + "' <= TLRM.DPM_E )"
				+ " OR ( TLRM.DPM_S >= '" + dto.getDpmS() + "' AND '" + dto.getDpmE() + "' >= TLRM.DPM_E )) "
				+ " AND (( TLRM.EFFECTIVE_DATE_S <= DATE_FORMAT('" + DateUtil.yyyyMMdd2Str(dto.getEffectiveDateS())
				+ "','%Y-%m-%d') AND DATE_FORMAT('" + DateUtil.yyyyMMdd2Str(dto.getEffectiveDateS())
				+ "','%Y-%m-%d') <= TLRM.EFFECTIVE_DATE_E )" + " OR ( TLRM.EFFECTIVE_DATE_S <=  DATE_FORMAT('"
				+ DateUtil.yyyyMMdd2Str(dto.getEffectiveDateE()) + "','%Y-%m-%d') AND DATE_FORMAT('"
				+ DateUtil.yyyyMMdd2Str(dto.getEffectiveDateE()) + "','%Y-%m-%d') <= TLRM.EFFECTIVE_DATE_E )"
				+ " OR ( TLRM.EFFECTIVE_DATE_S >=  DATE_FORMAT('" + DateUtil.yyyyMMdd2Str(dto.getEffectiveDateS())
				+ "','%Y-%m-%d') AND DATE_FORMAT('" + DateUtil.yyyyMMdd2Str(dto.getEffectiveDateE())
				+ "','%Y-%m-%d') >= TLRM.EFFECTIVE_DATE_E )) AND TLRM.IS_VALID = "
				+ OemDictCodeConstants.STATUS_ENABLE + " AND TLRM.RATE = " + dto.getRate());
		System.out.println("查询SQL" + sb);
		List<Object> list = new ArrayList<Object>();
		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
		if (map.size() > 0) {
			throw new ServiceBizException("新增数据重复！请检查数据！");
		} else {
			TmLoanRatMaintainPO lap = new TmLoanRatMaintainPO();
			lap.setString("BRAND_GROUP_Id", brandCode);
			lap.setString("SERIES_GROUP_ID", seriesCode);
			lap.setString("STYLE_GROUP_ID", groupCode);
			lap.setString("CODE_ID", dto.getCodeId());
			lap.setInteger("INSTALLMENT_NUMBER", dto.getInstallmentNumber());
			lap.setFloat("DPM_S", dto.getDpmS());
			lap.setFloat("DPM_E", dto.getDpmE());
			lap.setDouble("RATE", dto.getRate());
			lap.setDate("EFFECTIVE_DATE_S", dto.getEffectiveDateS());
			lap.setDate("EFFECTIVE_DATE_E", dto.getEffectiveDateE());
			lap.setLong("CREATE_BY", loginInfo.getUserId());
			lap.setDate("CREATE_DATE", new Date());
			lap.setInteger("IS_VALID", 10011001);
			lap.setString("IS_SCAN", "0");
			lap.insert();
		}
	}

	/**
	 * 根据ID删除
	 * 
	 * @param id
	 * @throws ServiceBizException
	 */
	public void deleteChargeById(Long id) throws ServiceBizException {
		try {
			TmLoanRatMaintainPO trmPO = TmLoanRatMaintainPO.findById(id);
			//删除之后需要重新下发一次啊
			if(trmPO!=null){
				trmPO.setString("IS_SCAN", "0");
				trmPO.setInteger("IS_VALID", Integer.valueOf(OemDictCodeConstants.STATUS_DISABLE.toString()));
				trmPO.saveIt();
			}else{
				throw new ServiceBizException("贴息利率信息不存在！");
			}
		}catch(Exception e){
			logger.info(e.toString());
			throw new ServiceBizException("贴息利率信息删除失败！");
		}
	}

	/**
	 * 下载
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String sql = findDiscountRateInfo(queryParam, params);
		return OemDAOUtil.findAll(sql.toString(), params);

	}

	/**
	 * 插入数据到临时表
	 * 
	 * @param tvypDTO
	 */
	public TmLoanRatMaintainDTO insertTmpVsYearlyPlan(TmLoanRatMaintainDTO tvypDTO) {
		TmLoanRatMaintainPO tvypPO = new TmLoanRatMaintainPO();
		// 设置对象属性
		setTmLoanRatMaintainPO(tvypPO, tvypDTO);
		tvypPO.saveIt();
		return null;
	}

	public void setTmLoanRatMaintainPO(TmLoanRatMaintainPO retailBank, TmLoanRatMaintainDTO retailBankDTO) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		retailBank.setString("BRAND_GROUP_ID", retailBankDTO.getBrandGroupcode());
		retailBank.setString("SERIES_GROUP_ID", retailBankDTO.getSeriesGroupcode());
		retailBank.setString("STYLE_GROUP_ID", retailBankDTO.getStyleGroupcode());
		retailBank.setString("CODE_ID", retailBankDTO.getCodeId());
		retailBank.setInteger("INSTALLMENT_NUMBER", retailBankDTO.getInstallmentNumber());
		retailBank.setFloat("DPM_S", retailBankDTO.getDpmS());
		retailBank.setFloat("DPM_E", retailBankDTO.getDpmE());
		retailBank.setDouble("RATE", retailBankDTO.getRate());
		retailBank.setDate("EFFECTIVE_DATE_S", retailBankDTO.getEffectiveDateS());
		retailBank.setDate("EFFECTIVE_DATE_E", retailBankDTO.getEffectiveDateE());
		retailBank.setInteger("IS_VALID", 10011001);
		retailBank.setString("CREATE_BY", loginInfo.getUserId());
		retailBank.setDate("CREATE_DATE", new Date());

	}

	public boolean checkData(ArrayList<TmLoanRatMaintainDTO> dataList) throws Exception {
		
		
		return true;
	}

	/** * 判断字符串是否是整数 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			if (Integer.parseInt(value) >= 0) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/** * 判断字符串是否是浮点数 */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains(".")) {
				if (Double.parseDouble(value) >= 0) {
					return true;
				} else {
					return false;
				}
			}
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/** * 判断字符串是否是数字 */
	public static boolean isNumber(String value) {
		if (!StringUtils.isNullOrEmpty(value)) {
			return isInteger(value) || isDouble(value);
		}
		return false;
	}

	/** * 判断字符串是否是Long类型 */
	public static boolean isLong(String value) {
		if (!StringUtils.isNullOrEmpty(value)) {
			try {
				Long.parseLong(value);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}

	/** * 是否是合法日期格式 */
	public static boolean isDate(String value) {
		boolean flag = false;
		if (!!StringUtils.isNullOrEmpty(value)) {
			return flag;
		}
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat df2 = new SimpleDateFormat("%Y-%m-%d");
		SimpleDateFormat df3 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		SimpleDateFormat df4 = new SimpleDateFormat("%Y-%m-%d hh:mm:ss");
		@SuppressWarnings("unused")
		Date d = null;
		try {
			d = df1.parse(value);
			flag = true;
		} catch (ParseException e) {

		}
		try {
			if (!flag) {
				d = df2.parse(value);
				flag = true;
			}
		} catch (ParseException e) {

		}
		try {
			if (!flag) {
				d = df3.parse(value);
				flag = true;
			}
		} catch (ParseException e) {

		}
		try {
			if (!flag) {
				d = df4.parse(value);
				flag = true;
			}
		} catch (ParseException e) {

		}
		return flag;
	}

	/**
	 * 国产化判断
	 * @param groupCode
	 * @return
	 * @throws Exception
	 */
	public List<Map> isLocalization(String groupCode) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select 1 from TM_VHCL_MATERIAL_GROUP  \n");
		sql.append("where GROUP_LEVEL = 2  \n");
		sql.append("and  GROUP_CODE =   '");
		sql.append(groupCode);
		sql.append("'    AND GROUP_TYPE in (" + OemDictCodeConstants.GROUP_TYPE_IMPORT + ","
				+ OemDictCodeConstants.GROUP_TYPE_DOMESTIC + ") \n");
		return OemDAOUtil.findAll(sql.toString(), params);
	}

	public List<Map> findBank() {
		List<Object> params = new ArrayList<>();
		params.add(OemDictCodeConstants.STATUS_ENABLE);
		return OemDAOUtil.findAll("select * from TC_BANK WHERE STATUS= ?" , params);
	}
	
	/**
	 * 多选/全量删除
	 * by wangJian 2016-04-13
	 */
	public void delAll(TmLoanRatMaintainDTO tldto){
		try {
			String command = tldto.getFlag();
			if(command.equals("0")){//全量删除
				List<TmLoanRatMaintainPO> trmPO = TmLoanRatMaintainPO.findAll();
				for(int i = 0; i<trmPO.size();i++){
					//删除之后需要重新下发一次啊
					trmPO.get(i).setString("is_scan", "0");
					trmPO.get(i).setInteger("IS_VALID", OemDictCodeConstants.STATUS_DISABLE);
					trmPO.get(i).saveIt();
				}
			}else{
				String ids = tldto.getIds();
				String[] id = ids.split(",");
				for(int i=0;i<id.length;i++){
					TmLoanRatMaintainPO trmPO = TmLoanRatMaintainPO.findFirst("  ID = ? ", Long.valueOf(id[i]));
						//删除之后需要重新下发一次啊
					trmPO.setString("IS_SCAN", "0");
					trmPO.setInteger("IS_VALID", OemDictCodeConstants.STATUS_DISABLE);
					trmPO.saveIt();
					
				}
				
			}
		} catch (Exception e) {
			System.out.println(e);
			throw new ServiceBizException("贴息利率信息多选/全量删除失败！");
		}
	}

	public List<Map> countDo() {
		StringBuffer sql= new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT TLRM.ID,TLRM.CODE_ID,\n" );
		sql.append("       TC.BTC_CODE as BANK_ID ,\n" );
		sql.append("       TLRM.DPM_S ,\n" );
		sql.append("       TLRM.DPM_E,\n" );
		sql.append("       TLRM.RATE,\n" );
		sql.append("       TLRM.EFFECTIVE_DATE_S,\n" );
		sql.append("       TLRM.EFFECTIVE_DATE_E,\n" );
		sql.append("       TLRM.CREATE_DATE,\n" );
		sql.append("       TLRM.CREATE_BY,\n" );
		sql.append("       TLRM.UPDATE_DATE,\n" );
		sql.append("       TLRM.UPDATE_BY,\n" );
		sql.append("       TLRM.IS_VALID,\n" );
		sql.append("       TLRM.BRAND_GROUP_ID,\n" );
		sql.append("       TLRM.SERIES_GROUP_ID, \n" );
		sql.append("       TLRM.STYLE_GROUP_ID, \n" );
		sql.append("       TLRM.INSTALLMENT_NUMBER \n" );
		sql.append("          FROM TM_LOAN_RAT_MAINTAIN TLRM ,TC_BANK TC\n " );
		sql.append("       WHERE TC.ID=TLRM.CODE_ID AND TLRM.IS_SCAN = ?");
		params.add("0");
		List<Map> listJJ = OemDAOUtil.findAll(sql.toString(), params);
		return listJJ;
	}

	public List<Map> findDetail(TmLoanRatMaintainPO po) {
		SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
		String sql = " SELECT TLRM.ID FROM TM_LOAN_RAT_MAINTAIN TLRM "+
		" WHERE  TLRM.BRAND_GROUP_ID = '" + po.getString("BRAND_GROUP_ID") +
		"'  AND CODE_ID =	'"+po.getString("CODE_ID")+
		"'  AND INSTALLMENT_NUMBER =	'"+po.getInteger("INSTALLMENT_NUMBER")+
		"' AND  TLRM.SERIES_GROUP_ID = '" + po.getString("SERIES_GROUP_ID") +
		"' AND  TLRM.STYLE_GROUP_ID = '" + po.getString("STYLE_GROUP_ID") +
		"' AND  (( TLRM.DPM_S <= '" + po.getDouble("DPM_S") + "' AND '" + po.getDouble("DPM_S") + "' <= TLRM.DPM_E ) "+
		" OR ( TLRM.DPM_S <= '" + po.getDouble("DPM_E") + "' AND '" + po.getDouble("DPM_E") +"' <= TLRM.DPM_E )" +
		" OR ( TLRM.DPM_S >= '" + po.getDouble("DPM_S") + "' AND '" + po.getDouble("DPM_E") +"' >= TLRM.DPM_E )) "+
		" AND (( TLRM.EFFECTIVE_DATE_S <= DATE_FORMAT('" + dfm.format(po.getDate("EFFECTIVE_DATE_S")) + "','%Y-%m-%d') AND DATE_FORMAT('" +  dfm.format(po.getDate("EFFECTIVE_DATE_S")) +"','%Y-%m-%d') <= TLRM.EFFECTIVE_DATE_E )" +
		" OR ( TLRM.EFFECTIVE_DATE_S <=  DATE_FORMAT('" + dfm.format(po.getDate("EFFECTIVE_DATE_E")) + "','%Y-%m-%d') AND DATE_FORMAT('" + dfm.format(po.getDate("EFFECTIVE_DATE_E")) +"','%Y-%m-%d') <= TLRM.EFFECTIVE_DATE_E )" +
		" OR ( TLRM.EFFECTIVE_DATE_S >=  DATE_FORMAT('" + dfm.format(po.getDate("EFFECTIVE_DATE_S")) + "','%Y-%m-%d') AND DATE_FORMAT('" + dfm.format(po.getDate("EFFECTIVE_DATE_E")) +"','%Y-%m-%d') >= TLRM.EFFECTIVE_DATE_E )) AND TLRM.IS_VALID = "+OemDictCodeConstants.STATUS_ENABLE+
		" AND TLRM.RATE = "+po.getDouble("RATE");
		System.out.println(sql.toString());
		return OemDAOUtil.findAll(sql.toString(), null);
	}
}