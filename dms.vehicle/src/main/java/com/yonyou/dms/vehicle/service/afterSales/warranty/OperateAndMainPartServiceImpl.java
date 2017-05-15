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
import com.yonyou.dms.vehicle.dao.afterSales.warranty.OperateAndMainPartDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrOptOldptDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrOptOldptPO;

/**
 * 操作代码与主因件维护 
 * 
 * @author Administrator
 *
 */
@Service
public class OperateAndMainPartServiceImpl implements OperateAndMainPartService {
	@Autowired
	private ExcelGenerator excelService;
	
	@Autowired
	OperateAndMainPartDao optCodeDAO;

	/**
	 * 操作代码与主因件维护 查询
	 */
	@Override
	public PageInfoDto optAndMpartQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return optCodeDAO.getOptCodeQueryList(queryParam);
	}
	/**
	 * 操作代码与主因件维护 下载
	 */
	@Override
	public void optAndMpartDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = optCodeDAO.getOptCodeDownloadList(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("操作代码与主因件维护", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("OPT_CODE", "操作代码 "));
		exportColumnList.add(new ExcelExportColumn("PT_TAG", "配件标记"));
		exportColumnList.add(new ExcelExportColumn("OLDPT_CODE", "主因件代码"));
		exportColumnList.add(new ExcelExportColumn("STATUS", "状态" ));
		exportColumnList.add(new ExcelExportColumn("CREATE_BY", "创建人"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "创建时间"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_BY", "更新人"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "更新时间"));

		excelService.generateExcel(excelData, exportColumnList, "操作代码与主因件维护.xls", request, response);

	}
	/**
	 * 新增
	 */
	@Override
	public Long addOptAndMpart(TtWrOptOldptDTO ptdto) {
		TtWrOptOldptPO ptPo = new TtWrOptOldptPO();
		setOptAndMpartPo(ptPo, ptdto);
		return ptPo.getLongId();
	}

	private void setOptAndMpartPo(TtWrOptOldptPO ptPo, TtWrOptOldptDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	    if (CommonUtils.isNullOrEmpty(getApplyDataBy(ptdto,"","optMpt"))) {
			throw new ServiceBizException("操作代码已维护相应的主因件代码，不允许新增");
	    } else if (CommonUtils.isNullOrEmpty(getApplyDataBy(ptdto,"","optPtTag"))) {
			throw new ServiceBizException("操作代码已维护相应的配件标记，不允许新增");
		} else if (CommonUtils.isNullOrEmpty(getApplyDataBy(ptdto,"","mPt"))) {
			throw new ServiceBizException("主因件不存在，不允许维护");
		} else if (CommonUtils.isNullOrEmpty(getApplyDataBy(ptdto,"enable","mPt"))) {
			throw new ServiceBizException("主因件无效，不允许维护");
		} else if (CommonUtils.isNullOrEmpty(getApplyDataBy(ptdto,"","optCode"))) {
			throw new ServiceBizException("操作代码不存在，不允许维护");
		} else if (CommonUtils.isNullOrEmpty(getApplyDataBy(ptdto,"enable","optCode"))) {
			throw new ServiceBizException("操作代码无效，不允许维护");
		} else {
			if (ptdto.getPtTag() != null&&ptdto.getOldptCode() != null&&ptdto.getOptCode() != null ) {
				//获取操作代码ID
				List<Map> optMap=getApplyDataBy(ptdto,"enable","optCode");
				Long optId=(long) optMap.get(0).get("ID");
				//获取主因件代码ID
				List<Map> mPtMap=getApplyDataBy(ptdto,"enable","mPt");
				Long oldptId=(long) mPtMap.get(0).get("ID");
				
				ptPo.setLong("OPT_ID", optId);
				ptPo.setString("OPT_CODE", ptdto.getOptCode());
				ptPo.setLong("OLDPT_ID", oldptId);
				ptPo.setString("OLDPT_CODE", ptdto.getOldptCode());
				ptPo.setString("PT_TAG", ptdto.getPtTag());
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

	public List<Map> getApplyDataBy(TtWrOptOldptDTO ptdto,String statusTag,String tableTag) throws ServiceBizException {
		StringBuilder sqlSb=null;
		if(tableTag.equals("optMpt")||tableTag.equals("optPtTag")){//操作代码是否维护配件标记或主因件
			sqlSb= new StringBuilder("  SELECT OPT_CODE from TT_WR_OPT_OLDPT_DCS WHERE 1=1");
		}else if(tableTag.equals("optCode")){//操作代码是否存在、是否有效
			sqlSb = new StringBuilder("  SELECT ID,OPT_CODE FROM TT_WR_OPERATE_DCS WHERE 1=1");
		}else if(tableTag.equals("mPt")){//主因件是否存在、是否有效
			sqlSb = new StringBuilder("  SELECT ID, OLDPT_CODE FROM TT_WR_MAIN_OLDPART_DCS WHERE 1=1");
		}
		List<Object> params = new ArrayList<>();
		if(tableTag.equals("mPt")){
			sqlSb.append(" and  OLDPT_CODE=?");
			params.add(ptdto.getOldptCode());
		}
		if(tableTag.equals("optCode")){
			sqlSb.append(" and  OPT_CODE=?");
			params.add(ptdto.getOptCode());
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
	public Map<String,Object> editOptAndMpart(Long Id) throws ParseException {
		TtWrOptOldptPO po = TtWrOptOldptPO.findById(Id);
		Map<String, Object> map = po.toMap();
		map.put("OPT_CODE", map.get("OPT_CODE"));
		map.put("PT_TAG", map.get("PT_TAG"));
		map.put("OLDPT_CODE", map.get("OLDPT_CODE"));
		map.put("STATUS", map.get("STATUS"));
		return map;
	}
	/**
	 * 更新操作代码与主因件维护 信息
	 */
	@Override
	public void saveOptAndMpart(TtWrOptOldptDTO dto, BigDecimal id,LoginInfoDto loginUser) throws ServiceBizException {
		TtWrOptOldptPO updatePo = TtWrOptOldptPO.findById(id);
		
		updatePo.setString("OPT_CODE", dto.getOptCode());
		updatePo.setInteger("STATUS", dto.getStatus());
		updatePo.setBigDecimal("UPDATE_BY", loginUser.getUserId());
		updatePo.setDate("UPDATE_DATE", new Date());
		boolean flag = false;
		flag = updatePo.saveIt();
		if(flag){			
		}else{
			throw new ServiceBizException("修改操作代码与主因件 失败");
		}
	}
}
