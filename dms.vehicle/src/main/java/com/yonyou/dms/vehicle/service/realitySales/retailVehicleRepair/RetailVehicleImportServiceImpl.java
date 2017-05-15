package com.yonyou.dms.vehicle.service.realitySales.retailVehicleRepair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsNvdrPO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.vehicle.dao.realitySales.retailVehicleRepair.RetailVehicleImportDao;
import com.yonyou.dms.vehicle.domains.DTO.retailReportQuery.TmpVsNvdrDTO;
import com.yonyou.dms.vehicle.domains.PO.retailReportQuery.TmpVsNvdrPO;

@Service
@SuppressWarnings("all") 
public class RetailVehicleImportServiceImpl implements RetailVehicleImportService {
	
	@Autowired
	private RetailVehicleImportDao dao;


	/**
	 * 导入数据记录（记录导入的数据并将验证功过的数据直接 INSERT 零售表）
	 */
	@Override
	public void insertRemark(ArrayList<TmpVsNvdrDTO> dataList) throws ServiceBizException{
		//登陆信息
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		//验证日期是否有效
		for (int i = 0; i < dataList.size(); i++) {	
			Date dateTime = new Date();
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			TmpVsNvdrDTO datr = dataList.get(i);
			String num = dataList.get(i).getRowNO().toString();	// 行号
			String vin = dataList.get(i).getVin();						// VIN码
			String date = format.format(dataList.get(i).getUploadDate());	// 零售上报日期
			
			 // 验证日期是否有效
			int result = 0;
			try {
				result = dateIsValidCheck(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (result == 1) {
				String year = date.substring(0, 4);	// 年
				String month = date.substring(4, 6);// 月
				String day = date.substring(6, 8);	// 日
				date = year + "-" + month + "-" + day;
			}
			boolean checkDate = CommonUtils.isValidTimestamp(date);
			 // 检查VIN号长度是否超出17位
			if (vin.length() > 17) {
				TmpVsNvdrPO temp = new TmpVsNvdrPO();
				temp.setInteger("LINE_NUMBER", Integer.parseInt(num));
				temp.setString("VIN", vin.substring(0, 17));
				temp.setString("CHECK_RESULT", "导入失败：VIN号过长，请检查原文件是否有换行数据。");
				temp.setInteger("CHECK_STATUS", 0);
				if (result == 1 && checkDate) {
					temp.setDate("UPLOAD_DATE", CommonUtils.parseDate(date));
				}
				temp.setLong("CREATE_BY", loginInfo.getUserId());
				temp.setDate("CREATE_DATE", dateTime);
				temp.insert();
				continue;
			}
			//查找出对应VIN码的车辆节点状态
			List<TmVehiclePO> vehiclelist = TmVehiclePO.find(" VIN = ? ", vin);
			int nodeStatus = vehiclelist.size() > 0 ? vehiclelist.get(0).getInteger("NODE_STATUS") : 0;
			//查找出对应VIN码是否已做过零售上报
			List<TtVsNvdrPO> nvdrlist = TtVsNvdrPO.find(" VIN = ? ", vin);
			//查找出对应VIN码是否出现重复数据
			int count = 0;
			for (int j = 0; j < dataList.size(); j++) {
				if (vin.equals(dataList.get(j).getVin())) {
					count++;
				}
			}
			if (vehiclelist.size() == 0) {
				TmpVsNvdrPO temp = new TmpVsNvdrPO();
				temp.setInteger("LINE_NUMBER", Integer.parseInt(num));
				temp.setString("VIN", vin);
				temp.setString("CHECK_RESULT", "导入失败：VIN号不存在！");
				temp.setInteger("CHECK_STATUS", 0);
				if (result == 1 && checkDate) {
					temp.setDate("UPLOAD_DATE", CommonUtils.parseDate(date));
				}
				temp.setLong("CREATE_BY", loginInfo.getUserId());
				temp.setDate("CREATE_DATE", dateTime);
				temp.insert();
			} else if (nodeStatus != OemDictCodeConstants.VEHICLE_NODE_08 &&	// ZBIL-一次开票
					   nodeStatus != OemDictCodeConstants.VEHICLE_NODE_10 &&	// ZDRL-中进车款确认
					   nodeStatus != OemDictCodeConstants.VEHICLE_NODE_12 &&	// ZPDU-发车出库
					   nodeStatus != OemDictCodeConstants.VEHICLE_NODE_15 &&	// ZDLD-经销商验收
					   nodeStatus != OemDictCodeConstants.VEHICLE_NODE_18 &&	// ZRL1-资源已分配
					   nodeStatus != OemDictCodeConstants.VEHICLE_NODE_19) {	// ZDRR-经销商订单确认
				TmpVsNvdrPO temp = new TmpVsNvdrPO();
				temp.setInteger("LINE_NUMBER", Integer.parseInt(num));
				temp.setString("VIN", vin);
				temp.setString("CHECK_RESULT", "导入失败：不在车厂库存！");
				temp.setInteger("CHECK_STATUS", 0);
				if (result == 1 && checkDate) {
					temp.setDate("UPLOAD_DATE", CommonUtils.parseDate(date));
				}
				temp.setLong("CREATE_BY", loginInfo.getUserId());
				temp.setDate("CREATE_DATE", dateTime);
				temp.insert();
			} else if (nvdrlist.size() > 0) {	// 检查是否已做过零售上报
				TmpVsNvdrPO temp = new TmpVsNvdrPO();
				temp.setInteger("LINE_NUMBER", Integer.parseInt(num));
				temp.setString("VIN", vin);
				temp.setString("CHECK_RESULT", "导入失败：已做过零售上报！");
				temp.setInteger("CHECK_STATUS", 0);
				if (result == 1 && checkDate) {
					temp.setDate("UPLOAD_DATE", CommonUtils.parseDate(date));
				}
				temp.setLong("CREATE_BY", loginInfo.getUserId());
				temp.setDate("CREATE_DATE", dateTime);
				temp.insert();
			} else if(count > 1) {	// 检查是否存在重复数据
				TmpVsNvdrPO temp = new TmpVsNvdrPO();
				temp.setInteger("LINE_NUMBER", Integer.parseInt(num));
				temp.setString("VIN", vin);
				temp.setString("CHECK_RESULT", "导入失败：VIN号重复出现！");
				temp.setInteger("CHECK_STATUS", 0);
				if (result == 1 && checkDate) {
					temp.setDate("UPLOAD_DATE", CommonUtils.parseDate(date));
				}
				temp.setLong("CREATE_BY", loginInfo.getUserId());
				temp.setDate("CREATE_DATE", dateTime);
				temp.insert();
			} else if (result != 1 || !checkDate) {	// 效验日期
				TmpVsNvdrPO temp = new TmpVsNvdrPO();
				temp.setInteger("LINE_NUMBER", Integer.parseInt(num));
				temp.setString("VIN", vin);
				if (!CommonUtils.checkIsNull(date)) {
					temp.setString("CHECK_RESULT", "导入失败：缺少零售上报日期！格式样例：20150101");
				}else{
					temp.setString("CHECK_RESULT", "导入失败：零售上报日期格式错误！格式样例：20150101");
				}
				temp.setInteger("CHECK_STATUS", 0);
				if (result == 1 && checkDate) {
					temp.setDate("UPLOAD_DATE", CommonUtils.parseDate(date));
				}
				temp.setLong("CREATE_BY", loginInfo.getUserId());
				temp.setDate("CREATE_DATE", dateTime);
				temp.insert();
			} else {
				//写入临时表
				TmpVsNvdrPO temp = new TmpVsNvdrPO();
				temp.setInteger("LINE_NUMBER", Integer.parseInt(num));
				temp.setString("VIN", vin);
				temp.setString("CHECK_RESULT", "导入成功！");
				temp.setInteger("CHECK_STATUS", 1);
				temp.setDate("UPLOAD_DATE", CommonUtils.parseDate(date));
				temp.setLong("CREATE_BY", loginInfo.getUserId());
				temp.setDate("CREATE_DATE", dateTime);
				temp.insert();
				//写入零售表
				TtVsNvdrPO po = new TtVsNvdrPO();
				po.setString("VIN", vin);												// VIN码
				po.setInteger("STATUS", OemDictCodeConstants.VEHICLE_REPORT_STATUS_02); // 交车上报状态（未上报）
				po.setInteger("NVDR_STATUS", OemDictCodeConstants.VEHICLE_RETAIL_STATUS_03);	// 零售交车状态（通过）
				po.setInteger("REPORT_TYPE", OemDictCodeConstants.RETAIL_REPORT_TYPE_03);	// 零售上报类型（零售补传）
				po.setLong("PATCH_UPLOAD_USER_ID", loginInfo.getUserId());				// 补传人ID
				po.setString("PATCH_UPLOAD_USER_NAME", loginInfo.getUserName());		// 补传人
				po.setDate("PATCH_UPLOAD_DATE",dateTime);								// 补传日期
				po.setLong("CREATE_BY",loginInfo.getUserId());							// 创建人
				po.setDate("CREATE_DATE", DateUtil.parseDefaultDate(date));	// 创建日期
				po.setDate("UPDATE_DATE", DateUtil.parseDefaultDate(date));	// 审批日期
				po.insert();
			}
		}
	}
	
	/**
	 * 验证日期是否有效
	 * @param str
	 * @return
	 */
	public int dateIsValidCheck(String str) throws ServiceBizException {
		
		if (CommonUtils.checkIsNull(str)) {
			return 0;
		} else if (str.length() == 8) {
			return 1;
		} else {
			return 2;
		}
	}

	/**
	 * 查询此次导入数据验证后的结果
	 */
	@Override
	public List<Map> allMessageQuery() throws ServiceBizException{
		List<Map> allMessage = dao.allMessageQuery();
		return allMessage;
	}

	/**
	 * 计算上传数据数量（和错误数据数量）
	 */
	@Override
	public Map<String, String> countDataNum() throws ServiceBizException {
		Map<String, String> count = dao.countDataNum();
		return count;
	}

	/**
	 * 清空临时表数据
	 */
	@Override
	public void delete() throws ServiceBizException {
		dao.dilete();
	}

	@Override
	public List<Map> findReeDate(Map<String, String> queryParam, LoginInfoDto loginInfo) throws ServiceBizException {
		List<Map> list = dao.queryReeDate(queryParam,loginInfo);
		return list;
	}

	/**
	 * 根据选择类型获取相应数据
	 * @param id
	 * @return
	 */
	@Override
	public List<Map> allMessageQuery(Integer id) throws ServiceBizException {
		return dao.queryReeDate(id);
	}


}
