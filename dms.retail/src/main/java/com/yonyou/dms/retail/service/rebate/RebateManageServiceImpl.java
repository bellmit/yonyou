package com.yonyou.dms.retail.service.rebate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.service.monitor.Utility;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.retail.dao.rebate.RebateDao;
import com.yonyou.dms.retail.domains.DTO.rebate.TtRebateCalculateTmpDTO;
import com.yonyou.dms.retail.domains.PO.rebate.RebateManagePO;
/**
 * 
* @ClassName: RebateManageServiceImpl 
* @Description: 
* @author zhengzengliang 
* @date 2017年3月28日 下午7:14:54 
*
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Service
public class RebateManageServiceImpl implements RebateManageService{
	@Autowired
	RebateDao dao;

	@Override
	public PageInfoDto getRebateManage(Map<String, String> queryParam) {
		return dao.findRebateManage(queryParam);
	}

	@Override
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws ServiceBizException {
		
		return dao.queryEmpInfoforExport(queryParam);
	}

	@Override
	public void deleteTtRebateCalculateTmp() throws ServiceBizException {
		dao.deleteTtRebateCalculateTmp();
	}

	@Override
	public void insertTmprebateCalculate(TtRebateCalculateTmpDTO rowDto) throws ServiceBizException {
		dao.insertTmprebateCalculate(rowDto);
	}

	@Override
	public ImportResultDto<TtRebateCalculateTmpDTO> checkData(LoginInfoDto loginInfo)
			throws ServiceBizException {
		//查询临时表数据
		List<Map> dataList = dao.findTmpList(loginInfo);
		ArrayList<TtRebateCalculateTmpDTO> tvypDTOList = new ArrayList<TtRebateCalculateTmpDTO>();
		ImportResultDto<TtRebateCalculateTmpDTO> importResult = new ImportResultDto<TtRebateCalculateTmpDTO>();
		importResult.setSucess(true);
		boolean isError = true;
		if(dataList.size()>0){
			for(int i=0;i<dataList.size();i++){
				String businessPolicyName= CommonUtils.checkNull(dataList.get(i).get("BUSINESS_POLICY_NAME"));//商务政策名称
				String startMonth= CommonUtils.checkNull(dataList.get(i).get("START_MONTH"));//起始月
				String endMonth= CommonUtils.checkNull(dataList.get(i).get("END_MONTH"));//结束月
				String dealerCode= CommonUtils.checkNull(dataList.get(i).get("DEALER_CODE"));//经销商代码
				String vin= CommonUtils.checkNull(dataList.get(i).get("VIN"));//vin
				if (Utility.testIsNull(businessPolicyName)) {
					isError = false;
					TtRebateCalculateTmpDTO rowDto = new TtRebateCalculateTmpDTO();
					rowDto.setRowNO(Integer.parseInt(dataList.get(i).get("ROW_NO").toString()));
					rowDto.setErrorMsg("商务政策名称为空");
					tvypDTOList.add(rowDto);
					importResult.setSucess(false);
					importResult.setErrorList(tvypDTOList);
				}
				if (Utility.testIsNull(startMonth)) {
					isError = false;
					TtRebateCalculateTmpDTO rowDto = new TtRebateCalculateTmpDTO();
					rowDto.setRowNO(Integer.parseInt(dataList.get(i).get("ROW_NO").toString()));
					rowDto.setErrorMsg("起始月为空");
					tvypDTOList.add(rowDto);
					importResult.setSucess(false);
					importResult.setErrorList(tvypDTOList);
				}
				if (Utility.testIsNull(endMonth)) {
					isError = false;
					TtRebateCalculateTmpDTO rowDto = new TtRebateCalculateTmpDTO();
					rowDto.setRowNO(Integer.parseInt(dataList.get(i).get("ROW_NO").toString()));
					rowDto.setErrorMsg("结束月为空");
					tvypDTOList.add(rowDto);
					importResult.setSucess(false);
					importResult.setErrorList(tvypDTOList);
				}
				if (Utility.testIsNull(dealerCode)) {
					isError = false;
					TtRebateCalculateTmpDTO rowDto = new TtRebateCalculateTmpDTO();
					rowDto.setRowNO(Integer.parseInt(dataList.get(i).get("ROW_NO").toString()));
					rowDto.setErrorMsg("经销商代码为空");
					tvypDTOList.add(rowDto);
					importResult.setSucess(false);
					importResult.setErrorList(tvypDTOList);
				}
				if (Utility.testIsNull(vin)) {
					isError = false;
					TtRebateCalculateTmpDTO rowDto = new TtRebateCalculateTmpDTO();
					rowDto.setRowNO(Integer.parseInt(dataList.get(i).get("ROW_NO").toString()));
					rowDto.setErrorMsg("VIN为空");
					tvypDTOList.add(rowDto);
					importResult.setSucess(false);
					importResult.setErrorList(tvypDTOList);
				}else{
					//检查VIN是否为17位
					if(vin.length()!=17){
						isError = false;
						TtRebateCalculateTmpDTO rowDto = new TtRebateCalculateTmpDTO();
						rowDto.setRowNO(Integer.parseInt(dataList.get(i).get("ROW_NO").toString()));
						rowDto.setErrorMsg("VIN:"+vin+"不为17位");
						tvypDTOList.add(rowDto);
						importResult.setSucess(false);
						importResult.setErrorList(tvypDTOList);
					}
				}
			}
			// 检查数据是否正确
			if (isError) {
				List<Map> li = dao.checkData3(loginInfo);
				for (Map<String, Object> m : li) {
					if (m.get("DEALER_CODE_DESC").toString().equals("")) {
						TtRebateCalculateTmpDTO rowDto = new TtRebateCalculateTmpDTO();
						rowDto.setRowNO(Integer.parseInt(m.get("ROW_NO").toString()));
						rowDto.setErrorMsg("经销商代码：" + m.get("DEALER_CODE") + " 不存在。");
						tvypDTOList.add(rowDto);
						importResult.setSucess(false);
						importResult.setErrorList(tvypDTOList);
					}
					if (m.get("VIN_DESC").toString().equals("")) {
						TtRebateCalculateTmpDTO rowDto = new TtRebateCalculateTmpDTO();
						rowDto.setRowNO(Integer.parseInt(m.get("ROW_NO").toString()));
						rowDto.setErrorMsg("车架号：" + m.get("VIN") + " 不存在。");
						tvypDTOList.add(rowDto);
						importResult.setSucess(false);
						importResult.setErrorList(tvypDTOList);
					}
				}
			}
		}
		return importResult;
	}

	@Override
	public List<Map> findTmpList(LoginInfoDto loginInfo) throws ServiceBizException {
		List<Map> dataList = dao.findTmpList(loginInfo);
		return dataList;
	}

	@Override
	public void deleteDyn(String businessPolicyName, String startMonth, String endMonth) throws ServiceBizException {
		dao.deleteDyn(businessPolicyName, startMonth, endMonth);
	}

	@Override
	public void deleteSta(String businessPolicyName, String startMonth, String endMonth) throws ServiceBizException {
		dao.deleteSta(businessPolicyName, startMonth, endMonth);
	}

	@Override
	public List<RebateManagePO> selectRebateManage(String businessPolicyName, String startMonth, String endMonth)
			throws ServiceBizException {
		List<RebateManagePO> list = dao.selectRebateManage(businessPolicyName,startMonth,endMonth);
		return list;
	}

	@Override
	public void insertRebateStat(Long logId, LoginInfoDto loginInfo) throws ServiceBizException {
		dao.insertRebateStat(logId, loginInfo);
	}

	@Override
	public void P_REBATE_CAL_DYN(LoginInfoDto loginInfo) throws ServiceBizException {
		dao.P_REBATE_CAL_DYN(loginInfo);
	}

	@Override
	public void insertRebateManage(String businessPolicyName, String businessPolicyType, 
			LoginInfoDto loginInfo, String uploadWay, Long logId, String startMonth, String endMonth)
					throws ServiceBizException {
		RebateManagePO tcmp=new RebateManagePO();
		tcmp.setString("BUSINESS_POLICY_NAME", businessPolicyName);
		if("销售".equals(businessPolicyType)){
			tcmp.setInteger("BUSINESS_POLICY_TYPE", 91181001);
		}else if("售后".equals(businessPolicyType)){
			tcmp.setInteger("BUSINESS_POLICY_TYPE", 91181002);
		}else if("网络".equals(businessPolicyType)){
			tcmp.setInteger("BUSINESS_POLICY_TYPE", 91181003);
		}else{
			tcmp.setInteger("BUSINESS_POLICY_TYPE", Integer.parseInt(businessPolicyType));
		}
		tcmp.setTimestamp("CREATE_DATE", new Date());
		tcmp.setLong("CREATE_BY", loginInfo.getUserId());
		tcmp.setTimestamp("UPDATE_DATE", new Date());
		tcmp.setLong("UPDATE_BY", loginInfo.getUserId());
		tcmp.setInteger("UPLOAD_WAY", Integer.parseInt(uploadWay));
	//	tcmp.setLong("LOG_ID", logId);
		tcmp.setString("START_MONTH", startMonth);
		tcmp.setString("END_MONTH", endMonth);
		tcmp.saveIt();
		
		//插入固定列数据表
		dao.insertRebateStat(tcmp.getLong("LOG_ID"),loginInfo);
	}

	@Override
	public List<Map> selectTtRebateCalculateManage(String logId) throws ServiceBizException {
		List<Map> lsit = dao.selectTtRebateCalculateManage(logId);
		return lsit;
	}

	@Override
	public List<Map> selectReUploadStartMonth(String logId) throws ServiceBizException {
		List<Map> lsit = dao.selectReUploadStartMonth(logId);
		return lsit;
	}

	@Override
	public List<Map> selectReUploadEndYearList(String logId) throws ServiceBizException {
		List<Map> lsit = dao.selectReUploadEndYear(logId);
		return lsit;
	}

	@Override
	public List<Map> selectReUploadEndMonthList(String logId) throws ServiceBizException {
		List<Map> lsit = dao.selectReUploadEndMonth(logId);
		return lsit;
	}

	@Override
	public List<Map> selectReUploadgetBusinessPolicyName(String logId) throws ServiceBizException {
		List<Map> lsit = dao.selectReUploadgetBusinessPolicyName(logId);
		return lsit;
	}

	@Override
	public List<Map> selectReUploadgetgetBusinessPolicyType(String logId) throws ServiceBizException {
		List<Map> lsit = dao.selectReUploadgetgetBusinessPolicyType(logId);
		return lsit;
	}

	
}
