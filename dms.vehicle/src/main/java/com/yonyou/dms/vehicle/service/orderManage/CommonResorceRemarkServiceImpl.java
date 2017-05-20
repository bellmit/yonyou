package com.yonyou.dms.vehicle.service.orderManage;

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
import com.yonyou.dms.common.domains.PO.basedata.TmAllotSupportPO;
import com.yonyou.dms.common.domains.PO.basedata.TtResourceRemarkPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.factoryOrderDao.CommonResorceRemarkDao;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.CommonResorceRemarkDTO;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.RemarkImpDto;
import com.yonyou.dms.vehicle.domains.PO.orderManager.TmpCommonResourceRemarkPO;

@Service
@SuppressWarnings("all")
public class CommonResorceRemarkServiceImpl implements CommonResorceRemarkService {
	@Autowired
	private CommonResorceRemarkDao dao;
	@Autowired
	private ExcelGenerator excelService;
	@Autowired
	private ExcelRead<RemarkImpDto> excelReadService;
	private SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String currdate2 = sf2.format(new Date());

	@Override
	public PageInfoDto CommonResorceRemarkInt(Map<String, String> queryParam) {

		return dao.getVehicleList(queryParam);
	}

	@Override
	public void resourceSupport(CommonResorceRemarkDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String isSuppor = CommonUtils.checkNull(dto.getIsSupport());
		String isSupport = isSuppor.substring(0, 8);
		String vins = CommonUtils.checkNull(dto.getVins());
		String[] vin = vins.split(",");
		for (int i = 0; i < vin.length; i++) {
			TmAllotSupportPO taP = TmAllotSupportPO.findFirst("vin=?", vin[i]);
			if (taP != null) {
				// IS_SUPPORT为1表示额外支持（是）
				if (isSupport.equals(String.valueOf(OemDictCodeConstants.IS_SUPPORT1))) {
					// 设置状态为任务内
					taP.setInteger("IS_SUPPORT", 1);
					taP.setTimestamp("UPDATE_DATE", currdate2);
					taP.setLong("UPDATE_BY", loginInfo.getUserId());
					taP.saveIt();
				} else {
					// 设置为任务内
					taP.setInteger("IS_SUPPORT", 1);
					taP.setTimestamp("UPDATE_DATE", currdate2);
					taP.setLong("UPDATE_BY", loginInfo.getUserId());
					taP.saveIt();
				}
			} else {
				TmAllotSupportPO tasPO = new TmAllotSupportPO();
				if (isSupport.equals(String.valueOf(OemDictCodeConstants.IS_SUPPORT1))) {
					tasPO.setInteger("IS_SUPPORT", 1);
					tasPO.setTimestamp("CREATE_DATE", currdate2);
					tasPO.setLong("CREATE_BY", loginInfo.getUserId());
					tasPO.setString("VIN", vin[i]);
					tasPO.saveIt();
				}
				// else {
				// tasPO.setInteger("IS_SUPPORT", 0);
				// tasPO.setTimestamp("CREATE_DATE", currdate2);
				// tasPO.setLong("CREATE_BY", loginInfo.getUserId());
				// tasPO.setString("VIN", vin[i]);
				// tasPO.saveIt();
				// }
			}
		}
	}

	// 任务外
	public void resourceNoSupport(CommonResorceRemarkDTO dto) {

		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String isSuppor = CommonUtils.checkNull(dto.getIsSupport());
		String isSupport = isSuppor.substring(0, 8);
		String vins = CommonUtils.checkNull(dto.getVins());
		String[] vin = vins.split(",");
		for (int i = 0; i < vin.length; i++) {
			TmAllotSupportPO taP = TmAllotSupportPO.findFirst("vin=?", vin[i]);
			if (taP != null) {
				// IS_SUPPORT为1表示额外支持（是）
				if (isSupport.equals(String.valueOf(OemDictCodeConstants.IS_SUPPORT2))) {
					// 设置状态为任务外
					taP.setInteger("IS_SUPPORT", 0);
					taP.setTimestamp("UPDATE_DATE", currdate2);
					taP.setLong("UPDATE_BY", loginInfo.getUserId());
					taP.saveIt();
				} else {
					// 设置为任务外
					taP.setInteger("IS_SUPPORT", 0);
					taP.setTimestamp("UPDATE_DATE", currdate2);
					taP.setLong("UPDATE_BY", loginInfo.getUserId());
					taP.saveIt();
				}
			} else {
				TmAllotSupportPO tasPO = new TmAllotSupportPO();
				if (isSupport.equals(String.valueOf(OemDictCodeConstants.IS_SUPPORT1))) {
					tasPO.setInteger("IS_SUPPORT", 0);
					tasPO.setTimestamp("CREATE_DATE", currdate2);
					tasPO.setLong("CREATE_BY", loginInfo.getUserId());
					tasPO.setString("VIN", vin[i]);
					tasPO.saveIt();
				}
				// else {
				// tasPO.setInteger("IS_SUPPORT", 0);
				// tasPO.setTimestamp("CREATE_DATE", currdate2);
				// tasPO.setLong("CREATE_BY", loginInfo.getUserId());
				// tasPO.setString("VIN", vin[i]);
				// tasPO.saveIt();
				// }
			}
		}

	}

	// 解锁
	@Override
	public void resourceUnlock(CommonResorceRemarkDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String vins = CommonUtils.checkNull(dto.getVins());
		String[] vin = vins.split(",");
		for (int i = 0; i < vin.length; i++) {
			TtResourceRemarkPO poa = TtResourceRemarkPO.findFirst("vin=?", vin[i]);
			if (poa != null) {
				poa.setInteger("IS_LOCK", 0);
				poa.setTimestamp("UPDATE_DATE", currdate2);
				poa.setLong("UPDATE_BY", loginInfo.getUserId());
				poa.saveIt();
			}
		}

	}

	// 锁定
	@Override
	public void resourceLock(CommonResorceRemarkDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		try {
			String vins = CommonUtils.checkNull(dto.getVins());
			String[] vin = vins.split(",");
			for (int i = 0; i < vin.length; i++) {
				TtResourceRemarkPO trr = TtResourceRemarkPO.findFirst("vin=?", vin[i]);
				if (trr != null) {
					trr.setInteger("IS_LOCK", 1);
					trr.setTimestamp("UPDATE_DATE", currdate2);
					trr.setLong("UPDATE_BY", loginInfo.getUserId());
					trr.saveIt();
				} else {
					TtResourceRemarkPO trrPO = new TtResourceRemarkPO();
					trrPO.setInteger("REMARK", 0);
					trrPO.setString("VIN", vin[i]);
					trrPO.setInteger("IS_LOCK", 1);
					trrPO.setLong("UPDATE_BY", loginInfo.getUserId());
					trrPO.setTimestamp("CREATE_DATE", currdate2);
					trrPO.setTimestamp("UPDATE_DATE", currdate2);
					trrPO.saveIt();
				}

			}

		} catch (Exception e) {
			throw new ServiceBizException("锁定车辆失败");
		}
	}

	// 分配备注
	@Override
	public void resourceAllotRemark(CommonResorceRemarkDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String remark = CommonUtils.checkNull(dto.getRemark());
		String otherRemark = CommonUtils.checkNull(dto.getOtherRemark());

		String vins = CommonUtils.checkNull(dto.getVins());
		String[] vin = vins.split(",");
		int isLok = 0;
		if (remark.equals("")) {
			remark = "0";
			isLok = 0;
		} else {
			isLok = 1;
		}
		for (int i = 0; i < vin.length; i++) {

			TtResourceRemarkPO valuePO = TtResourceRemarkPO.findFirst("vin=?", vin[i]);
			if (valuePO != null) {
				valuePO.setInteger("REMARK", new Integer(remark));
				valuePO.setString("OTHER_REMARK", !otherRemark.equals("") ? otherRemark : "");
				valuePO.setInteger("IS_LOCK", new Integer(isLok));
				valuePO.setLong("UPDATE_BY", loginInfo.getUserId());
				valuePO.setTimestamp("UPDATE_DATE", currdate2);
				valuePO.saveIt();
			} else {
				TtResourceRemarkPO trrPO = new TtResourceRemarkPO();
				// trrPO.setRemarkId(new
				// Long(SequenceManager.getSequence(null)));
				trrPO.setInteger("REMARK", new Integer(remark));
				trrPO.setString("OTHER_REMARK", otherRemark);
				trrPO.setString("VIN", vin[i]);
				trrPO.setInteger("IS_LOCK", new Integer(isLok));
				trrPO.setLong("UPDATE_BY", loginInfo.getUserId());
				trrPO.setTimestamp("CREATE_DATE", currdate2);
				trrPO.setTimestamp("UPDATE_DATE", currdate2);
				trrPO.saveIt();
			}
		}

	}

	@Override
	public void download(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = dao.download(queryParam);
		excelData.put("资源分配备注下载", (List<Map>) list);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("BRAND_CODE", "品牌"));
		exportColumnList.add(new ExcelExportColumn("VPC_PORT", "VPC港口", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "车型代码"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车型描述"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰"));
		exportColumnList.add(new ExcelExportColumn("REMARK", "车辆分配备注", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("OTHER_REMARK", "其它备注"));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "车辆用途"));
		exportColumnList.add(new ExcelExportColumn("IS_LOCK", "是否锁定", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("IS_SUPPORT", "任务内/外", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("NAME", "操作人"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "操作时间"));
		excelService.generateExcel(excelData, exportColumnList, "资源分配备注下载.xls", request, response);
	}

	// 导入临时表
	@Override
	public void insertTmpCommonResourceRemark(RemarkImpDto rowDto) {
		TmpCommonResourceRemarkPO po = new TmpCommonResourceRemarkPO();
		po.setInteger("ROW_NUMBER", rowDto.getRowNO());
		po.setString("REMARK", rowDto.getRemark());
		po.setString("VIN", rowDto.getVin());
		po.setInteger("REMARK_TYPE", OemDictCodeConstants.REMARK_COMMON);
		po.saveIt();
	}

	@Override
	public ArrayList<RemarkImpDto> checkData() {
		// boolean isError = false;
		// ImportResultDto<RemarkImpDto> importResult = new
		// ImportResultDto<RemarkImpDto>();
		ArrayList<RemarkImpDto> tvypDTOList = new ArrayList<RemarkImpDto>();
		// 验证临时表重复数据
		List<Map> dumpList = dao.checkTableDump();
		if (null != dumpList && dumpList.size() > 0) {
			for (int i = 0; i < dumpList.size(); i++) {
				RemarkImpDto rowDt = new RemarkImpDto();
				rowDt.setRowNO(Integer.parseInt(dumpList.get(i).get("ROW_NUMBER").toString()));
				rowDt.setErrorMsg("与第" + dumpList.get(i).get("ROW_NUMBER") + "行数据重复!");
				tvypDTOList.add(rowDt);
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
		// 验证vin号是否在 ZGOR-车辆到港 到 ZDLD-经销商验收
		List<Map> vlList = dao.checkVins();
		if (null != vlList && vlList.size() > 0) {
			for (Map map : vlList) {
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
				String vin = CommonUtils.checkNull(map.get("VIN"));
				String remark = CommonUtils.checkNull(map.get("REMARK"));
				rowDt.setErrorMsg("备注类型:" + remark + "不存在");
				tvypDTOList.add(rowDt);
			}
			return tvypDTOList;

		}
		// 判断是否是进口车
		List<Map> importList = dao.findIsNotImport();
		if (null != importList && importList.size() > 0) {
			for (Map map : importList) {
				RemarkImpDto rowDt = new RemarkImpDto();
				rowDt.setRowNO(Integer.parseInt(map.get("ROW_NUMBER").toString()));
				String vin = CommonUtils.checkNull(map.get("VIN"));
				String remark = CommonUtils.checkNull(map.get("REMARK"));
				rowDt.setErrorMsg("VIN号:" + map.get("VIN") + "不是进口车!");
				tvypDTOList.add(rowDt);
			}
			return tvypDTOList;

		}
		return null;
	}

	// 临时表数据回显
	@Override
	public List<Map> importShowCon(Map<String, String> queryParam) {

		return dao.importShowCon();
	}

	// 导入
	@Override
	public void importSave() {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currdate2 = sf2.format(new Date());
		LazyList<TmpCommonResourceRemarkPO> tcrPO = TmpCommonResourceRemarkPO.find("REMARK_TYPE=?",
				OemDictCodeConstants.REMARK_COMMON);
		for (TmpCommonResourceRemarkPO tmpCommonResourceRemarkPO : tcrPO) {

			Integer codeId = OemDictCodeConstantsUtils.getDictCodeByName(
					OemDictCodeConstants.RESOURCE_ALLOT_DETAIL.toString(),
					tmpCommonResourceRemarkPO.get("REMARK").toString());
			String Vin = (String) tmpCommonResourceRemarkPO.get("VIN");
			LazyList<TtResourceRemarkPO> list = TtResourceRemarkPO.find("vin=?", Vin);
			if (list != null && list.size() != 0) {
				TtResourceRemarkPO trPO = list.get(0);
				String other = "";
				if (tmpCommonResourceRemarkPO.get("REMARK").toString().equals("其他用途")) {
					other = "其他用途";
				}
				String vin = (String) trPO.get("VIN");
				TtResourceRemarkPO ttpo = TtResourceRemarkPO.findFirst("vin=?", vin);
				ttpo.setInteger("IS_LOCK", 1);
				ttpo.setInteger("REMARK", codeId);
				ttpo.setString("OTHER_REMARK", other);
				ttpo.setLong("UPDATE_BY", loginInfo.getUserId());
				ttpo.setTimestamp("UPDATE_DATE", currdate2);
				ttpo.saveIt();
			} else {
				if (tmpCommonResourceRemarkPO.get("REMARK").toString().equals("其他用途")) {
					TtResourceRemarkPO tpo = new TtResourceRemarkPO();
					tpo.setInteger("IS_LOCK", 1);
					tpo.setInteger("REMARK", codeId);
					tpo.setString("VIN", Vin);
					tpo.setString("OTHER_REMARK", "其他用途");
					tpo.setLong("UPDATE_BY", loginInfo.getUserId());
					tpo.setInteger("CREATE_BY", loginInfo.getUserId());
					tpo.setTimestamp("UPDATE_DATE", currdate2);
					tpo.setTimestamp("CREATE_DATE", currdate2);
					tpo.saveIt();
				} else {
					TtResourceRemarkPO to = new TtResourceRemarkPO();
					to.setInteger("IS_LOCK", 1);
					to.setString("VIN", Vin);
					to.setInteger("REMARK", codeId);
					to.setString("OTHER_REMARK", "其他用途");
					to.setLong("UPDATE_BY", loginInfo.getUserId());
					to.setLong("CREATE_BY", loginInfo.getUserId());
					to.setTimestamp("UPDATE_DATE", currdate2);
					to.setTimestamp("CREATE_DATE", currdate2);
					to.saveIt();
				}
			}

		}

	}

}
