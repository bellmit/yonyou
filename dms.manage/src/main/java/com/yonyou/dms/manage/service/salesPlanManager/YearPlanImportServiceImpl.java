package com.yonyou.dms.manage.service.salesPlanManager;




import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.dao.salesPlanManager.YearPlanImportDao;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TmpVsYearlyPlanDTO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TmpVsYearlyPlanPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsYearlyPlanDetailPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsYearlyPlanPO;

/**
 * 
* @ClassName: YearPlanImportServiceImpl 
* @Description: 年度目标上传service实现层 
* @author zhengzengliang
* @date 2017年2月10日 下午4:10:54 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class YearPlanImportServiceImpl implements YearPlanImportService{

	@Autowired
	private YearPlanImportDao yearPlanImportDao;

	@Override
	public void deleteTmpVsYearlyPlan() throws ServiceBizException {
		yearPlanImportDao.deleteTmpVsYearlyPlan();
	}

	@Override
	public void insertTmpVsYearlyPlan(TmpVsYearlyPlanDTO rowDto) throws ServiceBizException {
		yearPlanImportDao.insertTmpVsYearlyPlan(rowDto);
	}

	
	@Override
	public ImportResultDto<TmpVsYearlyPlanDTO> checkData(TmpVsYearlyPlanDTO rowDto) throws ServiceBizException {
		ImportResultDto<TmpVsYearlyPlanDTO> result = yearPlanImportDao.checkData(rowDto);
		return result;
	}

	@Override
	public List<Map> oemSelectTmpYearPlan(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> list = yearPlanImportDao.oemSelectTmpYearPlan(queryParam);
		return list;
	}

	
	@Override
	public int getTmpTtVsYearlyPlanTotal(Map<String, String> queryParam) throws ServiceBizException {
		int total = yearPlanImportDao.getTmpTtVsYearlyPlanTotal(queryParam);
		return total;
	}

	@Override
	public List<Map> checkImportData(String string) throws ServiceBizException {
		List<Map> list  = yearPlanImportDao.checkImportData(string);
		return list;
	}

	@Override
	public List<TtVsYearlyPlanPO> selectTtVsYearlyPlan(String year) throws ServiceBizException {
		List<TtVsYearlyPlanPO> tvypList = yearPlanImportDao.selectTtVsYearlyPlan(year);
		return tvypList;
	}

	@Override
	public void deleteTtVsYearlyPlanDetail(TtVsYearlyPlanDetailPO detailPo) throws ServiceBizException {
		yearPlanImportDao.deleteTtVsYearlyPlanDetail(detailPo);
	}

	@Override
	public void clearUserYearlyPlan(TtVsYearlyPlanPO actPo) throws ServiceBizException {
		yearPlanImportDao.clearUserYearlyPlan(actPo);
	}

	@Override
	public List<TmpVsYearlyPlanPO> selectTmpVsYearlyPlan(String year) throws ServiceBizException {
		List<TmpVsYearlyPlanPO> tvypList = yearPlanImportDao.selectTmpVsYearlyPlan(year);
		return tvypList;
	}

	
	@Override
	public List<Map> findMaxPlanVer(String year, String planType) throws ServiceBizException {
		List<Map> list = yearPlanImportDao.findMaxPlanVer(year,planType);
		return list;
	}

	@Override
	public TtVsYearlyPlanPO getYearlyPlanPo(int plan, String planType, TmpVsYearlyPlanPO po, String year)
			throws ServiceBizException {
		Map tmdPo = yearPlanImportDao.selectUnique(po);
		
		TtVsYearlyPlanPO ttPo = new TtVsYearlyPlanPO();
		
		Long groupId =  yearPlanImportDao.getGroupId(po);
		List<Map> planList = yearPlanImportDao.findExistData(tmdPo,groupId,year);
		if(planList.size() != 0){
			TtVsYearlyPlanPO vPo = new TtVsYearlyPlanPO();
			yearPlanImportDao.setTtVsYearlyPlan(vPo,year,tmdPo,plan,planType);
			vPo.insert();
			ttPo = vPo;
		}else{
			yearPlanImportDao.setTtVsYearlyPlan2(ttPo,year,tmdPo,planType);
			ttPo.insert();
		}
		
		return ttPo;
	}

	// 插入数据到明细表
	@Override
	public void insertDetailPo(String groupCode, Long planId, Integer month, Integer saleAmt, Long userId)
			throws ServiceBizException {
		TtVsYearlyPlanDetailPO detailPo = new TtVsYearlyPlanDetailPO();
		yearPlanImportDao.setDetailPo(detailPo, groupCode, planId, month, saleAmt, userId);
		detailPo.insert();
	}

	
	
	

	

}
