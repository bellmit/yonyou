package com.yonyou.dcs.gacfca;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SEDMS022DTO;
import com.yonyou.dms.common.domains.PO.basedata.FsFileuploadPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsSalesReportPO;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SADCS024CloudImpl extends BaseCloudImpl implements SADCS024Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS024CloudImpl.class);
	 @Autowired
	    private CommonNoService     commonNoService;

	@Override
	public String handleExecutor(List<SEDMS022DTO> dto) throws Exception {
		String msg = "1";
		for (SEDMS022DTO vo : dto) {
			try {
				insertFsFileuploadPO(vo);// 写入发票表
			} catch (Exception e) {
				logger.error("##########发票多次扫描上报失败###############");
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		return msg;
	}

	/**
	 * 写入发票表
	 * 
	 * @param vo
	 */
	private void insertFsFileuploadPO(SEDMS022DTO vo) {

		// 根据vin找到车辆表

		List<Map> lisTmVehiclePO = Base.findAll("select *  from  tm_vehicle_dec where vin=?", vo.getVin());
		if (lisTmVehiclePO == null || lisTmVehiclePO.size() != 1) {
			throw new ServiceBizException("#############车辆表中不存在记录############");
		}
		// 根据车辆ID到实销表去查询
		List<Map> lisTtVsSalesReportPO = Base.findAll(
				"select  *  from  TT_VS_SALES_REPORT where Vehicle_Id=? and Status=" + OemDictCodeConstants.STATUS_ENABLE,
				lisTmVehiclePO.get(0).get("Vehicle_Id").toString());
		if (lisTtVsSalesReportPO == null || lisTtVsSalesReportPO.size() != 1) {
			throw new ServiceBizException("#############实销表中不存在记录############");
		} else { // 实销表 IsOcr 修改成 2
			// TtVsSalesReportPO newTvPO = new TtVsSalesReportPO();
			// newTvPO.setLong("REPORT_ID",
			// lisTtVsSalesReportPO.get(0).get("REPORT_ID"));
			TtVsSalesReportPO.update("Is_Ocr=?", "REPORT_ID=?", 2, lisTtVsSalesReportPO.get(0).get("REPORT_ID").toString());
		}
		// 查询发票表是否存在记录H

		List<Map> listFsFileuploadPO = Base.findAll("select  *  from fs_fileupload where YWZJ=?",
				lisTtVsSalesReportPO.get(0).get("REPORT_ID"));
		if (listFsFileuploadPO == null || listFsFileuploadPO.size() == 0) {
			throw new ServiceBizException("#############发票没有扫描过############");
		}

		logger.info("###############发票开始扫描################");

		logger.info("###############发票历史数据修改备份################");
		// update FsFileuploadPO 历史数据修改备份
		// FsFileuploadPO oldPO = new FsFileuploadPO();
		// oldPO.setFjid("FJID", listFsFileuploadPO.get(0).get("FJID"));
		// FsFileuploadPO updateOldPO = new FsFileuploadPO();
		// updateOldPO.setYwzj(0L);// YWZJ 值未0 备份到 RemarkYwzj
		// updateOldPO.setRemarkYwzj(lisTtVsSalesReportPO.get(0).get("REPORT_ID"));
		// factory.update(oldPO, updateOldPO);
		FsFileuploadPO.update("YWZJ=?,REMARK_YWZJ=?", "FJID=?", 0L, lisTtVsSalesReportPO.get(0).get("REPORT_ID"),
				listFsFileuploadPO.get(0).get("FJID"));
		logger.info("###############发票新数据################");
		// update new FsFileuploadPO
		// FsFileuploadPO newPO = new FsFileuploadPO();
		// newPO.setFjid(Long.valueOf(vo.getFileId()));
		// FsFileuploadPO updateNewPO = new FsFileuploadPO();
		//
		// updateNewPO.setYwzj(lisTtVsSalesReportPO.get(0).get("Report_Id"));
		// factory.update(newPO, updateNewPO);
		
		FsFileuploadPO newpo=new FsFileuploadPO();
		 SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long intentId = commonNoService.getId("fs_fileupload");
		newpo.set("FJID", intentId);
		newpo.set("FILEURL", vo.getFileId());
		newpo.set("STATUS", 10011001);
		newpo.set("CREATE_DATE", format1.format(new Date()));
		newpo.set("CREATE_BY", 1L);
		newpo.set("YWZJ", lisTtVsSalesReportPO.get(0).get("Report_Id"));
		newpo.set("FILEID", vo.getFileId());
		
		newpo.insert();
		
		
		//FsFileuploadPO.update("Ywzj=?", "Fjid=?", lisTtVsSalesReportPO.get(0).get("Report_Id"), vo.getFileId());
		logger.info("###############发票扫描结束################");

	}

}
