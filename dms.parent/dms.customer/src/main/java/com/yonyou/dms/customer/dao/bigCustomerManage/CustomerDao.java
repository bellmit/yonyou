package com.yonyou.dms.customer.dao.bigCustomerManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.customer.domains.DTO.basedata.TtMysteriousTempErrorDTO;
import com.yonyou.dms.customer.domains.DTO.basedata.TtMysteriousTempExceDTO;
import com.yonyou.dms.customer.domains.PO.basedata.TtMysteriousTempErrorPO;
import com.yonyou.dms.customer.domains.PO.basedata.TtMysteriousTempExcePO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@SuppressWarnings({"rawtypes","unused","unchecked"})
@Repository
public class CustomerDao extends OemBaseDAO {
	public PageInfoDto findCustomer(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  \n");
		sql.append("  DATE_FORMAT(TMD.CREATE_DATE,'%Y-%m-%d') CREATE_DATE,\n");
		sql.append("  TMD.DEALER_CODE,   \n");
		sql.append("  TMD.DEALER_NAME,   \n");
		sql.append("  TMD.EXEC_AUTHOR,   \n");
		sql.append("  TMD.PHONE,        \n");
		sql.append("  (CASE TMDD.IS_INPUT_DMS WHEN '10011001' THEN '是' WHEN '10011002' THEN '否' WHEN '10011003' THEN '未反馈'  ELSE '' END) IS_INPUT_DMS,   \n");
		sql.append("  TMDD.INPUT_PHONE,    \n");
		sql.append("  TMDD.INPUT_NAME,    \n");
		sql.append("  DATE_FORMAT(TMDD.INPUT_DATE,'%Y-%m-%d') INPUT_DATE,         \n");
		sql.append("  DATE_FORMAT(TMDD.UPDATE_DATE,'%Y-%m-%d') UPDATE_DATE        \n");
		sql.append("FROM \n");
		sql.append("  TT_MYSTERIOUS_DATE TMD \n");
		sql.append(" LEFT JOIN \n");
		sql.append("  TT_MYSTERIOUS_DATE_DOWN TMDD \n");
		sql.append(" ON \n");
		sql.append("  TMD.MYSTERIOUS_ID = TMDD.MYSTERIOUS_ID \n");
		sql.append("WHERE 1=1\n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCodes"))) {
			sql.append(" and TMD.DEALER_CODE = ?");
			params.add(queryParam.get("dealerCodes"));
		}
		// 执行人员姓名不能 为空
		if (!StringUtils.isNullOrEmpty(queryParam.get("execAuthor"))) {
			sql.append(" and TMD.EXEC_AUTHOR  = ?");
			params.add(queryParam.get("execAuthor"));
		}
		// 留店电话不能为空
		if (!StringUtils.isNullOrEmpty(queryParam.get("phone"))) {
			sql.append(" and TMD.phone  = ?");
			params.add(queryParam.get("phone"));
		}

		// 录入时间不能为空
		if (!StringUtils.isNullOrEmpty(queryParam.get("inputDateStart"))) {
			sql.append("   AND DATE(TMDD.INPUT_DATE) >= ? \n");
			params.add(queryParam.get("inputDateStart"));
		}
		// 录入时间不能为空
		if (!StringUtils.isNullOrEmpty(queryParam.get("inputDateEnd"))) {
			sql.append("   AND DATE(TMDD.INPUT_DATE) <= ? \n");
			params.add(queryParam.get("inputDateEnd"));
		}
		// 是否反馈
		if (!StringUtils.isNullOrEmpty(queryParam.get("isInputDms"))) {
			sql.append(" and TMD.IS_INPUT_DMS  = ?");
			params.add(queryParam.get("isInputDms"));
		}
		// 上传时间不能为空
		if (!StringUtils.isNullOrEmpty(queryParam.get("updateDateStart"))) {
			sql.append("   AND DATE(TMDD.CREATE_DATE) >= ? \n");
			params.add(queryParam.get("updateDateStart"));
		}
		// 上传日期时间不能为空
		if (!StringUtils.isNullOrEmpty(queryParam.get("updateDateEnd"))) {
			sql.append("   AND DATE(TMDD.CREATE_DATE) <= ? \n");
			params.add(queryParam.get("updateDateEnd"));
		}
		return sql.toString();
	}

	/**
	 * 下载
	 * 
	 * @param param
	 * @return
	 * @throws ServiceBizException
	 */
	public List<Map> queryEmpInfoforExport(Map<String, String> param) throws ServiceBizException {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(param, params);
		return OemDAOUtil.findAll(sql.toString(), params);

	}

	/**
	 * 查询类型
	 * @param params
	 * @return
	 */
	public List<Map> selecttype(Map<String, String> params) {
		/*List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT \n");
		sql.append("      IS_INPUT_DMS \n");
		sql.append("  FROM TT_MYSTERIOUS_DATE_DOWN \n");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam); */
		List<Map> resultList = new ArrayList<Map>();
		Map m = new HashMap();
		m.put("IS_INPUT_DMS","是");
		m.put("IS_INPUT_ID", 10011001);
		resultList.add(m);
		
		Map m2 = new HashMap();
		m2.put("IS_INPUT_DMS","否");
		m2.put("IS_INPUT_ID", 10011002);
		resultList.add(m2);
		
		Map m3 = new HashMap();
		m3.put("IS_INPUT_DMS","未反馈");
		m3.put("IS_INPUT_ID", 10011003);
		resultList.add(m3);
		
		return resultList;
	}

	public ImportResultDto<TtMysteriousTempExceDTO> check(TtMysteriousTempExceDTO list) {
		boolean successFlag = true;// 验收成功true，验证失败为false
		// 从第2行开始读数据
		String dealerCode = list.getDealerCode();// 经销商代码
		String dealerName = list.getDealerName();// 经销商名称
		String execAuthor = list.getExecAuthor();// 执行人员姓名
		String phone = list.getPhone();// 留店电话
		String errorInfo = "";
		// 为空验证
		if (null == dealerCode || dealerCode.length() == 0) {
			successFlag = false;
			dealerCode = "经销商代码不能为空";
			errorInfo += dealerCode + ";";
		}
		if (null == dealerName || dealerName.length() == 0) {
			successFlag = false;
			dealerName = "经销商简称不能为空";
			errorInfo += dealerName + ";";
		}
		if (null == execAuthor || execAuthor.length() == 0) {
			successFlag = false;
			execAuthor = "执行人员姓名不能为空";
			errorInfo += execAuthor + ";";
		}
		if (null == phone || phone.length() == 0) {
			successFlag = false;
			phone = "不能为空";
			errorInfo += phone + ";";
		}
		String dealerErrorCode = "";
		// 合法验证
		if (null != dealerCode && dealerCode.length() != 0 && null != dealerName && dealerName.length() != 0) {
			List<TmDealerPO> listTmDealer = TmDealerPO.find("dealer_code = ? ", dealerCode);
			if (null == listTmDealer || listTmDealer.size() != 1) {// 如果不存在或者多条记录
																	// erroe
				successFlag = false;
				dealerErrorCode = "经销商code和经销商简称组合不正确";
				errorInfo += dealerErrorCode + ";";
			}
		}
		String phoneErrorInfo = "";
		if (null != phone && phone.length() != 0) {
			if (!isPhone(phone, 0, 0)) {// 手机号码验证格式错误
				successFlag = false;
				phoneErrorInfo = "手机格式不正确";
				errorInfo += phoneErrorInfo + ";";
			}
		}
		
		ArrayList<TtMysteriousTempExceDTO> tvypDTOList = new ArrayList<TtMysteriousTempExceDTO>();
		ImportResultDto<TtMysteriousTempExceDTO> importResult = new ImportResultDto<TtMysteriousTempExceDTO>();
		
		if (successFlag) {// 正确的
			TtMysteriousTempExceDTO sdto = new TtMysteriousTempExceDTO();
			sdto.setDealerCode(dealerCode);// 经销商code
			sdto.setDealerName(dealerName);// 经销商简称
			sdto.setExecAuthor(execAuthor);// 执行人员姓名
			sdto.setPhone(phone);// 留店电话
			TtMysteriousTempErrorPO.deleteAll();
			// 错误的信息写入临时表
			TtMysteriousTempExcePO tvypPO = new TtMysteriousTempExcePO();
			// 设置对象属性
			setTtMysteriousTempExcePO(tvypPO, sdto);
			tvypPO.saveIt();
		} else {// 验收失败的
			/*TtMysteriousTempErrorDTO ddto = new TtMysteriousTempErrorDTO();
			ddto.setDealerCode(dealerCode);// 经销商code
			ddto.setDealerName(dealerName);// 经销商简称
			ddto.setExecAuthor(execAuthor);// 执行人员姓名
			ddto.setPhone(phone);// 留店电话
			ddto.setErrorRow(Integer.parseInt(list.getRowNO().toString()));// 错误行
*/			
			TtMysteriousTempExceDTO tmtDto = new TtMysteriousTempExceDTO();
			tmtDto.setDealerCode(dealerCode);// 经销商code
			tmtDto.setDealerName(dealerName);// 经销商简称
			tmtDto.setExecAuthor(execAuthor);// 执行人员姓名
			tmtDto.setPhone(phone);// 留店电话
			if (null != errorInfo && errorInfo.length() > 0) {
				errorInfo = errorInfo.substring(0, errorInfo.lastIndexOf(";")) + ".";
				tmtDto.setErrorMsg(errorInfo);   // 错误信息
			}
			tmtDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));  // 错误行
			tvypDTOList.add(tmtDto);
			importResult.setErrorList(tvypDTOList);
			importResult.setSucess(false);
			
			/*if (null != errorInfo && errorInfo.length() > 0) {
				errorInfo = errorInfo.substring(0, errorInfo.lastIndexOf(";")) + ".";
				ddto.setErrorReason(errorInfo);// 错误信息
			}
			TtMysteriousTempErrorPO.deleteAll();
			// 错误的信息写入临时表
			TtMysteriousTempErrorPO tvypPO = new TtMysteriousTempErrorPO();
			// 设置对象属性
			setTtMysteriousTempErrorPO(tvypPO, ddto);
			tvypPO.saveIt();*/
			
			if (importResult != null) {
				throw new ServiceBizException(errorInfo, importResult.getErrorList());
			} 
		}
		return null;
	}

	/**
	 * 插入错误数据到临时表
	 * 
	 * @param tvypDTO
	 */
	public void insertTmpVsYearlyPlan(TtMysteriousTempErrorDTO tvypDTO) {
		TtMysteriousTempErrorPO tvypPO = new TtMysteriousTempErrorPO();
		// 设置对象属性
		setTtMysteriousTempErrorPO(tvypPO, tvypDTO);
		tvypPO.saveIt();
	}

	public void setTtMysteriousTempErrorPO(TtMysteriousTempErrorPO retailBank, TtMysteriousTempErrorDTO retailBankDTO) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		retailBank.setString("ERROR_ROW", retailBankDTO.getErrorRow());
		retailBank.setString("ERROR_REASON", retailBankDTO.getErrorReason());
		retailBank.setString("DEALER_CODE", retailBankDTO.getDealerCode());
		retailBank.setString("DEALER_NAME", retailBankDTO.getDealerName());
		retailBank.setString("EXEC_AUTHOR", retailBankDTO.getExecAuthor());
		retailBank.setString("PHONE", retailBankDTO.getPhone());
	}

	/**
	 * 插入正确数据到临时表
	 * 
	 * @param tvypDTO
	 */
	public void insertTmpVsYearlyPlan(TtMysteriousTempExceDTO tvypDTO) {
		TtMysteriousTempExcePO tvypPO = new TtMysteriousTempExcePO();
		// 设置对象属性
		setTtMysteriousTempExcePO(tvypPO, tvypDTO);
		tvypPO.saveIt();
	}

	public void setTtMysteriousTempExcePO(TtMysteriousTempExcePO retailBank, TtMysteriousTempExceDTO retailBankDTO) {
		retailBank.setString("DEALER_CODE", retailBankDTO.getDealerCode());
		retailBank.setString("DEALER_NAME", retailBankDTO.getDealerName());
		retailBank.setString("EXEC_AUTHOR", retailBankDTO.getExecAuthor());
		retailBank.setString("PHONE", retailBankDTO.getPhone());
	}

	/**
	 * add by @author xianchao zhang 验证电话格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isPhone(String str, int x, int y) {
		if (str.length() > 15) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			char checkCharacter = str.charAt(i);
			if ((checkCharacter >= '0' && checkCharacter <= '9') || (checkCharacter == '(') || (checkCharacter == ')')
					|| (checkCharacter == '-')) {
				// alert("合法");
				// 合法字符;数字'('')'和'-';
			} else {
				// alert("非法");
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @Title: oemSelectTmpYearPlan @Description: 临时表回显 @param @param
	 * rowDto @param @return 设定文件 @return PageInfoDto 返回类型 @throws
	 */
	public List<Map> oemSelectTmpYearPlan(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getTmpYearPlanSql(queryParam, params);
		List<Map> list = OemDAOUtil.findAll(sql, params);
		return list;
	}

	private String getTmpYearPlanSql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DEALER_CODE,\n");
		sql.append("       DEALER_NAME,\n");
		sql.append("       EXEC_AUTHOR,\n");
		sql.append("       PHONE\n");
		sql.append(" 	 FROM tt_mysterious_temp_exce \n");
		sql.append("   WHERE 1=1\n");
		return sql.toString();
	}

	/**
	 * 检查导入的数据  失败的
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	public List<Map> getQueryValidatorDataFail() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT           \n");
		sql.append(" ERROR_ROW,       \n");  //--错误行
		sql.append(" ERROR_REASON,    \n");   // --错误原因
		sql.append(" DEALER_CODE,      \n");   //--经销商code
		sql.append(" DEALER_NAME,     \n");  // --经销商简称
		sql.append(" EXEC_AUTHOR,     \n");  // --执行人员姓名
		sql.append(" PHONE            \n");  // --留店电话
		sql.append("FROM TT_MYSTERIOUS_TEMP_ERROR \n");
	//	sql.append("ORDER BY DEALER_CODE \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	
	
}
