package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.afterSales.warranty.WarrantyWorkHourDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWorrkHourDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrWorrkHourPO;

/**
 * 保修标准工时
 * 
 * @author Administrator
 *
 */
@Service
public class WarrantyWorkHourServiceImpl implements WarrantyWorkHourService {
	@Autowired
	private ExcelGenerator excelService;
	
	@Autowired
	WarrantyWorkHourDao workHourDAO;

	/**
	 * 保修标准工时查询
	 */
	@Override
	public PageInfoDto workHourQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return workHourDAO.getWorkHourQueryList(queryParam);
	}
	/**
	 * 保修标准工时下载
	 */
	@Override
	public void workHourDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = workHourDAO.getWorkHourDownloadList(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("保修标准工时维护", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("MVS", "MVS"));
		exportColumnList.add(new ExcelExportColumn("OPT_CODE", "操作代码"));
		exportColumnList.add(new ExcelExportColumn("STATUS", "状态" ));
		exportColumnList.add(new ExcelExportColumn("WORK_HOUR", "标准工时数"));
		exportColumnList.add(new ExcelExportColumn("CREATE_BY", "创建人"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "创建时间"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_BY", "更新人"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "更新时间"));

		excelService.generateExcel(excelData, exportColumnList, "保修标准工时维护.xls", request, response);

	}
	/**
	 * 新增
	 */
	@Override
	public Long addWorkHour(TtWrWorrkHourDTO ptdto) {
		TtWrWorrkHourPO ptPo = new TtWrWorrkHourPO();
		setworkHourPo(ptPo, ptdto);
		return ptPo.getLongId();
	}

	private void setworkHourPo(TtWrWorrkHourPO ptPo, TtWrWorrkHourDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if (!CommonUtils.isNullOrEmpty(getApplyDataBy(ptdto,"","workHour"))) {
			throw new ServiceBizException("该组MVS和操作代码已维护标准工时数，不允许重复维护");
		} else if (CommonUtils.isNullOrEmpty(getApplyDataBy(ptdto,"","mvs"))) {
			throw new ServiceBizException("MVS不存在，不允许维护");
		} else if (CommonUtils.isNullOrEmpty(getApplyDataBy(ptdto,"enable","mvs"))) {
			throw new ServiceBizException("MVS无效，不允许维护");
		} else if (CommonUtils.isNullOrEmpty(getApplyDataBy(ptdto,"","optCode"))) {
			throw new ServiceBizException("操作代码不存在，不允许维护");
		} else if (CommonUtils.isNullOrEmpty(getApplyDataBy(ptdto,"enable","optCode"))) {
			throw new ServiceBizException("操作代码无效，不允许维护");
		} else {
			if (ptdto.getWorkHour() != null&&ptdto.getMvs() != null&&ptdto.getOptCode() != null ) {
				//获取操作代码ID
				List<Map> map=getApplyDataBy(ptdto,"enable","optCode");
				Long optId=(long) map.get(0).get("ID");
				ptPo.setString("MVS", ptdto.getMvs());
				ptPo.setString("OPT_CODE", ptdto.getOptCode());
				ptPo.setLong("OPT_ID", optId);
				ptPo.setString("WORK_HOUR", ptdto.getWorkHour());
				ptPo.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
				ptPo.setLong("CREATE_BY", loginInfo.getUserId());
				ptPo.setDate("CREATE_DATE", new Date());
				ptPo.setInteger("IS_DEL", 0);
				ptPo.saveIt();

			} else {
				throw new ServiceBizException("未填写完整重要信息，请输入！");
			}
		}

	}

	public List<Map> getApplyDataBy(TtWrWorrkHourDTO ptdto,String statusTag,String tableTag) throws ServiceBizException {
		StringBuilder sqlSb=null;
		if(tableTag.equals("workHour")){
			sqlSb= new StringBuilder("  SELECT OPT_CODE,MVS from TT_WR_WORRKHOUR_DCS WHERE 1=1");
		}else if(tableTag.equals("optCode")){
			sqlSb = new StringBuilder("  SELECT ID,OPT_CODE FROM TT_WR_OPERATE_DCS WHERE 1=1");
		}else if(tableTag.equals("mvs")){
			sqlSb = new StringBuilder("  SELECT MVS FROM TM_VHCL_MATERIAL_GROUP WHERE 1=1");
		}
		List<Object> params = new ArrayList<>();
		if(!tableTag.equals("mvs")){
			sqlSb.append(" and  OPT_CODE=?");
			params.add(ptdto.getOptCode());
		}
		if(!tableTag.equals("optCode")){
			sqlSb.append(" and  MVS=?");
			params.add(ptdto.getMvs());
		}
		if(statusTag.equals("enable")){
			sqlSb.append(" and  Status=?");
			params.add(OemDictCodeConstants.STATUS_ENABLE);
		}
		
		System.out.println(sqlSb.toString()+"  params:"+params.toString());
		List<Map> applyList = OemDAOUtil.findAll(sqlSb.toString(), params);
		return applyList;
	}
	@Override
	public Map<String,Object> editWorkHour(Long Id) throws ParseException {
		TtWrWorrkHourPO po = TtWrWorrkHourPO.findById(Id);
		Map<String, Object> map = po.toMap();
		map.put("OPT_CODE", map.get("OPT_CODE"));
		map.put("OPT_NAME_EN", map.get("OPT_NAME_EN"));
		map.put("OPT_NAME_CN", map.get("OPT_NAME_CN"));
		map.put("STATUS", map.get("STATUS"));
		return map;
	}
	/**
	 * 更新保修标准工时信息
	 */
	@Override
	public void saveWorkHour(TtWrWorrkHourDTO dto, BigDecimal id,LoginInfoDto loginUser) throws ServiceBizException {
		TtWrWorrkHourPO updatePo = TtWrWorrkHourPO.findById(id);
		updatePo.setString("WORK_HOUR", dto.getWorkHour());
		updatePo.setInteger("STATUS", dto.getStatus());
		updatePo.setBigDecimal("UPDATE_BY", loginUser.getUserId());
		updatePo.setDate("UPDATE_DATE", new Date());
		boolean flag = false;
		flag = updatePo.saveIt();
		if(flag){			
		}else{
			throw new ServiceBizException("修改保修标准工时失败");
		}
	}
//	/**
//	 * 保存到临时表
//	 */
//	@Override
//	public void insertTmpworkHour(TmpWrOperateDTO rowDto) throws ServiceBizException {
//		warrantyworkHourDAO.insertTmpworkHour(rowDto);
//	}
//	/**
//	 * 数据校验
//	 */
//	@Override
//	public ImportResultDto<TmpWrOperateDTO> checkData(TmpWrOperateDTO rowDto) throws ServiceBizException {
//		ImportResultDto<TmpWrOperateDTO> result = warrantyworkHourDAO.checkData(rowDto);
//		return result;
//	}
}
