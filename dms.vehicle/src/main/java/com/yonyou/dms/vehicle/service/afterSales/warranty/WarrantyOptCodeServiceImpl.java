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
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.afterSales.warranty.WarrantyOptCodeDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TmpWrOperateDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrOperateDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrOperatePO;

/**
 * 操作代码
 * 
 * @author Administrator
 *
 */
@Service
public class WarrantyOptCodeServiceImpl implements WarrantyOptCodeService {
	@Autowired
	private ExcelGenerator excelService;
	
	@Autowired
	WarrantyOptCodeDao optCodeDAO;

	/**
	 * 操作代码查询
	 */
	@Override
	public PageInfoDto optCodeQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return optCodeDAO.getOptCodeQueryList(queryParam);
	}
	/**
	 * 操作代码下载
	 */
	@Override
	public void optCodeDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = optCodeDAO.getOptCodeDownloadList(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("操作代码维护", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("OPT_CODE", "操作代码"));
		exportColumnList.add(new ExcelExportColumn("OPT_NAME_EN", "操作代码描述"));
		exportColumnList.add(new ExcelExportColumn("OPT_NAME_CN", "操作描述(中文)"));
		exportColumnList.add(new ExcelExportColumn("STATUS", "状态" ));
		exportColumnList.add(new ExcelExportColumn("CREATE_BY", "创建人"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "创建时间"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_BY", "更新人"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "更新时间"));

		excelService.generateExcel(excelData, exportColumnList, "操作代码维护.xls", request, response);

	}
	/**
	 * 新增
	 */
	@Override
	public Long addOptCode(TtWrOperateDTO ptdto) {
		TtWrOperatePO ptPo = new TtWrOperatePO();
		setOptCodePo(ptPo, ptdto);
		return ptPo.getLongId();
	}

	private void setOptCodePo(TtWrOperatePO ptPo, TtWrOperateDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if (!CommonUtils.isNullOrEmpty(getApplyDataBy(ptdto))) {
			throw new ServiceBizException("已存在此操作代码！");
		} else {
			if (ptdto.getOptCode() != null ) {
				ptPo.setString("OPT_CODE", ptdto.getOptCode());
				ptPo.setString("OPT_NAME_EN", ptdto.getOptNameEn());
				ptPo.setString("OPT_NAME_CN", ptdto.getOptNameCn());
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

	public List<Map> getApplyDataBy(TtWrOperateDTO ptdto) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuilder sqlSb = new StringBuilder("  SELECT OPT_CODE from TT_WR_OPERATE_DCS where 1=1");
		List<Object> params = new ArrayList<>();
		sqlSb.append(" and  OPT_CODE=?");
		params.add(ptdto.getOptCode());
		List<Map> applyList = OemDAOUtil.findAll(sqlSb.toString(), params);
		return applyList;
	}
	
	@Override
	public Map<String,Object> editOptCode(Long Id) throws ParseException {
		TtWrOperatePO po = TtWrOperatePO.findById(Id);
		Map<String, Object> map = po.toMap();
		map.put("OPT_CODE", map.get("OPT_CODE"));
		map.put("OPT_NAME_EN", map.get("OPT_NAME_EN"));
		map.put("OPT_NAME_CN", map.get("OPT_NAME_CN"));
		map.put("STATUS", map.get("STATUS"));
		return map;
	}
	/**
	 * 更新操作代码信息
	 */
	@Override
	public void saveOptCode(TtWrOperateDTO dto, BigDecimal id,LoginInfoDto loginUser) throws ServiceBizException {
		TtWrOperatePO updatePo = TtWrOperatePO.findById(id);
		
		updatePo.setString("OPT_CODE", dto.getOptCode());
		updatePo.setInteger("STATUS", dto.getStatus());
		updatePo.setString("OPT_NAME_CN", dto.getOptNameCn());
		updatePo.setString("OPT_NAME_EN",dto.getOptNameEn());
		updatePo.setBigDecimal("UPDATE_BY", loginUser.getUserId());
		updatePo.setDate("UPDATE_DATE", new Date());
		boolean flag = false;
		flag = updatePo.saveIt();
		if(flag){			
		}else{
			throw new ServiceBizException("修改操作代码失败");
		}
	}
	/**
	 * 保存到临时表
	 */
	@Override
	public void insertTmpOptCode(TmpWrOperateDTO rowDto) throws ServiceBizException {
		optCodeDAO.insertTmpOptCode(rowDto);
	}
	/**
	 * 数据校验
	 */
	@Override
	public ImportResultDto<TmpWrOperateDTO> checkData(TmpWrOperateDTO rowDto) throws ServiceBizException {
		ImportResultDto<TmpWrOperateDTO> result = optCodeDAO.checkData(rowDto);
		return result;
	}
}
