package com.yonyou.dms.vehicle.service.k4Order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.k4Order.DealerRebateImportDao;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.TmpVsRebateImpDTO;

/**
 * @author liujiming
 * @date 2017年3月21日
 */
@Service
@SuppressWarnings("all")
public class DealerRebateImportServiceImpl implements DealerRebateImportService {

	@Autowired
	private DealerRebateImportDao dlrRbtImportDao;

	/**
	 * 删除返利临时表数据
	 */
	@Override
	public void deleteTmpVsRebateImp() throws ServiceBizException {
		dlrRbtImportDao.deleteTmpVsRebateImp();

	}

	/**
	 * 校验临时表数据
	 */
	@Override
	public List<TmpVsRebateImpDTO> checkData() throws ServiceBizException {

		List<Map> tvriList = dlrRbtImportDao.dealerRebateImportQuery();
		if (null == tvriList || tvriList.size() < 0) {
			tvriList = new ArrayList();
		}
		Map<String, Object> tvriMap = new HashMap<String, Object>();
		List<TmpVsRebateImpDTO> tvriDTOList = new ArrayList<TmpVsRebateImpDTO>();
		ImportResultDto<TmpVsRebateImpDTO> importResult = new ImportResultDto<TmpVsRebateImpDTO>();
		for (int i = 0; i < tvriList.size(); i++) {
			tvriMap = tvriList.get(i);
			// 取得行号
			Integer rowNum = Integer.valueOf((String) tvriMap.get("ROW_DECIMAL"));
			// 校验返利金额
			List<TmpVsRebateImpDTO> dd = checkSumAmt(tvriMap, rowNum);
			if (dd != null && dd.size() != 0) {
				return dd;
			}
		}

		// 校验经销商代码是否存在
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> params2 = new ArrayList<Object>();

		List<Map> notExistsOrgList = dlrRbtImportDao.findTempDealerList(loginInfo);
		if (null != notExistsOrgList && notExistsOrgList.size() > 0) {

			for (Map map : notExistsOrgList) {
				TmpVsRebateImpDTO rowDto = new TmpVsRebateImpDTO();
				rowDto.setRowNO(Integer.parseInt(map.get("ROW_DECIMAL").toString()));
				rowDto.setErrorMsg("经销商代码不存在");
				tvriDTOList.add(rowDto);
			}
		}

		return tvriDTOList;

	}

	/*
	 * 校验返利金额
	 */
	private List<TmpVsRebateImpDTO> checkSumAmt(Map<String, Object> map, Integer rowNum) {
		List<TmpVsRebateImpDTO> tvriDTOList = new ArrayList<TmpVsRebateImpDTO>();
		TmpVsRebateImpDTO rowDto = new TmpVsRebateImpDTO();
		String string = map.get("REBATE_AMOUNT").toString();

		Boolean f = this.checkNumber(string);
		if (!f) {
			rowDto.setRowNO(rowNum);
			rowDto.setErrorMsg("返利金额必须是数字");
			tvriDTOList.add(rowDto);
		}

		return tvriDTOList;
	}

	public Boolean checkNumber(String str) {
		String regex = "-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?";
		if (str == null || !str.matches(regex)) {
			return false;
		}
		return true;
	}

	/**
	 * 向临时表插入数据
	 */
	@Override
	public void insertTmpVsRebateImp(TmpVsRebateImpDTO rowDto) throws ServiceBizException {
		dlrRbtImportDao.insertTmpVsRebateImp(rowDto);

	}

	/**
	 * 查询临时表中的数据
	 */
	@Override
	public List<Map> dealerRebateImportQuery() throws ServiceBizException {
		List<Map> list = dlrRbtImportDao.dealerRebateImportQuery();
		return list;
	}

	/**
	 * 将临时表中数据插入业务表
	 */
	@Override
	public void insertToRebate() throws ServiceBizException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());

		// 获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);

		List<Map> list = dlrRbtImportDao.dealerRebateImportQuery();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				// 插入TT_VS_REBATE记录表
				String remark = CommonUtils.checkNull(map.get("REMARK"));
				StringBuffer sqlTtRevate = new StringBuffer("");
				sqlTtRevate.append(
						" INSERT INTO TT_VS_REBATE (DEALER_CODE,REBATE_TYPE,REBATE_AMOUNT,VIN,REBATE_CODE,REMARK,Create_By,Create_Date) ");
				sqlTtRevate.append("values(" + map.get("DEALER_CODE") + "," + map.get("REBATE_TYPE").toString() + ","
						+ map.get("REBATE_AMOUNT"));
				sqlTtRevate.append(",'" + map.get("VIN") + "'," + map.get("REBATE_CODE") + ",'" + remark + "',"
						+ loginInfo.getUserId().toString() + ",current_timestamp)");
				System.out.println(sqlTtRevate.toString());

				OemDAOUtil.execBatchPreparement(sqlTtRevate.toString(), new ArrayList<>());

				// 插入TI_REBATE记录表
				StringBuffer sqlTiRevate = new StringBuffer("");
				sqlTiRevate.append(
						" INSERT INTO Ti_Rebate(DEALER_CODE,REBATE_TYPE,REBATE_AMOUNT,VIN,REBATE_CODE,REMARK,Scan_Status,Create_By,Create_Date)");
				sqlTiRevate.append("values(" + map.get("DEALER_CODE").toString() + ","
						+ map.get("REBATE_TYPE").toString() + "," + map.get("REBATE_AMOUNT").toString());
				sqlTiRevate.append(",'" + map.get("VIN").toString() + "'," + map.get("REBATE_CODE").toString() + ",'"
						+ remark + "','0'," + loginInfo.getUserId().toString() + ",current_timestamp)");

			}
		}

	}

}
