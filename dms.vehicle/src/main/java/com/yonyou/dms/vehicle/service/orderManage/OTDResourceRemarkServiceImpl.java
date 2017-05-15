package com.yonyou.dms.vehicle.service.orderManage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.Util.OemDictCodeConstantsUtils;
import com.yonyou.dms.common.domains.PO.basedata.TtResourceRemarkPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.factoryOrderDao.OTDResourceRemarkDao;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.OTDResourceRemarkDTO;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.RemarkImpDto;
import com.yonyou.dms.vehicle.domains.PO.orderManager.TmpCommonResourceRemarkPO;

@Service
public class OTDResourceRemarkServiceImpl implements OTDResourceRemarkService {
	@Autowired
	private OTDResourceRemarkDao dao;
	@Autowired
	private ExcelGenerator excelService;

	@Override
	public PageInfoDto remarkInit(Map<String, String> queryParam) {

		return dao.remarkInit(queryParam);
	}

	@Override
	public void resourceAllotRemark(OTDResourceRemarkDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);

		String specialRemark = CommonUtils.checkNull(dto.getSpecialRemark());
		String vins = CommonUtils.checkNull(dto.getVins());
		String[] vin = vins.split(",");
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currdate2 = sf2.format(new Date());

		for (int i = 0; i < vin.length; i++) {
			TtResourceRemarkPO valuePO = TtResourceRemarkPO.findFirst("vin=?", vin[i]);
			if (valuePO != null) {
				valuePO.setInteger("SPECIAL_REMARK", new Integer(!specialRemark.equals("") ? specialRemark : "0"));
				valuePO.setLong("UPDATE_BY", loginInfo.getUserId());
				valuePO.setTimestamp("UPDATE_DATE", currdate2);
				valuePO.saveIt();
			} else {
				TtResourceRemarkPO trrPO = new TtResourceRemarkPO();
				trrPO.setInteger("SPECIAL_REMARK", new Integer(specialRemark));
				trrPO.setString("VIN", vin[i]);
				trrPO.setInteger("IS_LOCK", new Integer(0));
				trrPO.setLong("UPDATE_BY", loginInfo.getUserId());
				trrPO.setTimestamp("UPDATE_DATE", currdate2);
				trrPO.saveIt();
			}
		}

	}

	@Override
	public void download(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = dao.download(queryParam);
		excelData.put("OTD备注设定下载", (List<Map>) list);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		// exportColumnList.add(new ExcelExportColumn("VPC_PORT", "VPC港口",
		// ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "车型代码"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车型描述"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰"));
		exportColumnList.add(new ExcelExportColumn("SPECIAL_REMARK", "OTD备注", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "车辆用途"));
		exportColumnList.add(new ExcelExportColumn("IS_LOCK", "是否锁定", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("NAME", "操作人"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "操作时间"));
		excelService.generateExcel(excelData, exportColumnList, "OTD备注设定下载.xls", request, response);
	}

	@Override
	public void insertTmp(RemarkImpDto rowDto) {

		TmpCommonResourceRemarkPO po = new TmpCommonResourceRemarkPO();
		po.setInteger("ROW_NUMBER", rowDto.getRowNO());
		po.setString("REMARK", rowDto.getRemark());
		po.setString("VIN", rowDto.getVin());
		po.setInteger("REMARK_TYPE", OemDictCodeConstants.REMARK_SPECIAL);
		po.saveIt();

	}

	@Override
	public ArrayList<RemarkImpDto> checkData() {
		// 验证临时表重复数据
		ArrayList<RemarkImpDto> tvypDTOList = new ArrayList<RemarkImpDto>();
		List<Map> dumpList = dao.checkTableDump();
		if (null != dumpList && dumpList.size() > 0) {
			for (Map map : dumpList) {
				RemarkImpDto rowDto = new RemarkImpDto();
				rowDto.setRowNO(Integer.parseInt(map.get("ROW_NUMBER").toString()));
				rowDto.setErrorMsg("与第" + map.get("ROW_NUMBER").toString() + "行数据重复!");
				tvypDTOList.add(rowDto);
			}
			return tvypDTOList;
		}
		// 验证临时表VIN码是否重复
		List<Map> viList = dao.checkVinDump();
		if (null != viList && viList.size() > 0) {
			for (Map map : viList) {
				RemarkImpDto rowDt = new RemarkImpDto();
				rowDt.setRowNO(Integer.parseInt(map.get("ROW_NUMBER").toString()));
				rowDt.setErrorMsg("与第" + map.get("ROW_NUMBER") + "行VIN重复!");
				tvypDTOList.add(rowDt);
			}
			return tvypDTOList;
		}
		// 验证vin号是否存在
		List<Map> veList = dao.checkVinExists();
		if (null != veList && veList.size() > 0) {
			for (Map map : veList) {
				RemarkImpDto rowDt = new RemarkImpDto();
				rowDt.setRowNO(Integer.parseInt(map.get("ROW_NUMBER").toString()));
				String vin = CommonUtils.checkNull(map.get("VIN"));
				rowDt.setErrorMsg("VIN号:" + vin + "不存在");
				tvypDTOList.add(rowDt);
			}
			return tvypDTOList;
		}
		// 验证备注类型是否存在
		List<Map> cList = dao.checkRemarkExists();
		if (null != cList && cList.size() > 0) {
			for (Map map : cList) {
				RemarkImpDto rowDt = new RemarkImpDto();
				rowDt.setRowNO(Integer.parseInt(map.get("ROW_NUMBER").toString()));
				String remark = CommonUtils.checkNull(map.get("REMARK"));
				rowDt.setErrorMsg("备注类型:" + remark + "不存在");
				tvypDTOList.add(rowDt);
			}
			return tvypDTOList;
		}

		// 验证是否为进口车
		List<Map> gcList = dao.chekJingkouExists();
		if (null != gcList && gcList.size() > 0) {
			for (Map map : gcList) {

				RemarkImpDto rowDt = new RemarkImpDto();
				rowDt.setRowNO(Integer.parseInt(map.get("ROW_NUMBER").toString()));
				String vin = CommonUtils.checkNull(map.get("VIN"));
				String remark = CommonUtils.checkNull(map.get("REMARK"));
				rowDt.setErrorMsg("VIN号:" + map.get("VIN") + "非进口车");
				tvypDTOList.add(rowDt);
			}
			return tvypDTOList;

		}
		return null;
	}

	@Override
	public List<Map> importShow() {

		return dao.isShow();
	}

	// 导入
	@Override
	public void importTable() {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		LazyList<TmpCommonResourceRemarkPO> find = TmpCommonResourceRemarkPO.findBySQL(
				"select *from TMP_COMMON_RESOURCE_REMARK where REMARK_TYPE=?", OemDictCodeConstants.REMARK_SPECIAL);
		for (TmpCommonResourceRemarkPO tempPO : find) {
			Integer codeId = OemDictCodeConstantsUtils.getDictCodeByName(OemDictCodeConstants.OTD_REMARK.toString(),
					tempPO.get("REMARK").toString());
			LazyList<TtResourceRemarkPO> find2 = TtResourceRemarkPO.findBySQL("select *from tt_resource_remark where vin=?",
					tempPO.get("VIN"));
			if (find2.size() > 0 && find2 != null) {
				for (TtResourceRemarkPO trPO : find2) {

					trPO.setInteger("SPECIAL_REMARK", codeId);
					trPO.setInteger("UPDATE_BY", loginInfo.getUserId());
					trPO.setTimestamp("UPDATE_DATE", format);
					trPO.saveIt();
				}
			} else {
				TtResourceRemarkPO tpo = new TtResourceRemarkPO();
				tpo.setString("VIN", tempPO.get("VIN"));
				tpo.setInteger("REMARK", 0);
				tpo.setInteger("SPECIAL_REMARK", codeId);
				tpo.setInteger("IS_LOCK", 0);
				tpo.setInteger("CREATE_BY", loginInfo.getUserId());
				tpo.setInteger("UPDATE_BY", loginInfo.getUserId());
				tpo.setTimestamp("UPDATE_DATE", format);
				tpo.setTimestamp("CREATE_DATE", format);
				tpo.saveIt();
			}

		}
	}

}
