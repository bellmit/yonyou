package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.po.TtShowroomFlowPO;
import com.yonyou.dcs.dao.SEDCS014Dao;
import com.yonyou.dms.common.domains.DTO.basedata.SA012Dto;
import com.yonyou.dms.common.domains.DTO.basedata.SA013Dto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class SEDCS014CloudImpl extends BaseCloudImpl implements SEDCS014Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SEDCS014CloudImpl.class);
	@Autowired
	SEDCS014Dao dao ;

	@Override
	public String receiveDate(List<SA013Dto> dtos) throws Exception {
		String msg = "1";
		logger.info("***************************开始获取展厅流量数据***************************");
		
		try {
			dealData(dtos);
		} catch (Exception e) {
			logger.error("***************************展厅流量数据接收失败***************************", e);
			msg = "0";
			throw new ServiceBizException(e);
		} 
		logger.info("*************************** 成功获取上报的展厅流量数据******************************");
		return msg;
	}

	/**
	 * 接口接收数据逻辑：
	 * 		星期一 上报当天（晚上11点多）的数据，星期二上报周一和周二的数据，周三上报周一，周二和周三的数据，以此类推；
	 * 		周一到周六的数据属于变动数据，不做封存处理，到上报数据为周日时将本周数据进行封存;
	 * 		如果上报的数据为月末，则封存改月的数据
	 * 		数据封存动作通过定时任务执行（ShowroomFlowReportTask）
	 * @param bodys
	 * @throws Exception
	 */
	private void dealData(List<SA013Dto> dtos) throws Exception {
		logger.info("##################### 开始处理展厅流量数据 ############################");
		logger.info("*********展厅流量数据**********"+dtos.size()+"********************");
		TtShowroomFlowPO ttShowroomFlowPO = null;
		int flag = 0;
		for (SA013Dto sa013dto : dtos) {
			flag++;
			LinkedList<SA012Dto> sa012dtoList = sa013dto.getList();
			Map dcsInfoMap = dao.getSaDcsDealerCode(sa013dto.getDealerCode());
			String dealerCode = CommonUtils.checkNull(dcsInfoMap.get("DEALER_CODE"));
			//删除接口表中未封存最大CURRENT_DATE时间对应的周一，到该时间
			if(flag==1){//一个消息文件只删除一次
				dao.delUnSealed(dealerCode);
			}
			logger.info("*********展厅流量SA012Dto数据**********"+sa012dtoList.size()+"********************");
			if (sa012dtoList != null && sa012dtoList.size() > 0) {
				for (int i = 0; i < sa012dtoList.size(); i++) {
					ttShowroomFlowPO = new TtShowroomFlowPO();
					ttShowroomFlowPO.setInteger("CALL_IN",sa013dto.getCallIn());//展厅来电车系数量（来电有效客户数量）
					ttShowroomFlowPO.setInteger("WALK_IN", sa013dto.getWalkIn());//展厅来访车系数量(进店客流)
					ttShowroomFlowPO.setInteger("NO_OD_SC",sa013dto.getNoOfSc());//销售顾问人数（销售顾问数量）
					ttShowroomFlowPO.setDate("CURRENTDATE",sa013dto.getDataTime());//当前时间(把数据时间值set给当前时间)
					ttShowroomFlowPO.setDate("DATA_TIME",sa013dto.getCurrentDate());//数据时间(上报时间)
					ttShowroomFlowPO.setString("DEALER_CODE",dealerCode);
					
					SA012Dto sa012dto = (SA012Dto) sa012dtoList.get(i);
					ttShowroomFlowPO.setString("SERIES_CODE",sa012dto.getSeriesCode());//车系代码
					ttShowroomFlowPO.setInteger("HOT_SALES_LEADS",sa012dto.getHotSalesLeads());//意向客户车系数量（HSL）
					ttShowroomFlowPO.setInteger("SALES_ORDERS",sa012dto.getSalesOrders());//销售订单车系数量（销售订单）
					ttShowroomFlowPO.setDouble("CONVERSION_RATIO",sa012dto.getConversionRatio());//销售转换比率  为 销售订单车系数量 除以 意向客户车系数量
					ttShowroomFlowPO.setInteger("TEST_DRIVE",sa012dto.getTestDrive());//试驾
					ttShowroomFlowPO.setInteger("WALK_FOUND",sa012dto.getWalkFound());//treffic有效客流，要求建档，分车系统计
					ttShowroomFlowPO.setInteger("HSL_REPLACE",sa012dto.getHslReplace());//HSL中 置换的数量 --新增置换意向客户数
					ttShowroomFlowPO.setInteger("SALES_REPLACE",sa012dto.getSalesReplace());//置换的销售订单数量--置换成交数
					ttShowroomFlowPO.setInteger("DCC_OF_HOT",sa012dto.getDccOfHot());//HSL 中DCC转入的潜客数量（DCC HSL）
					ttShowroomFlowPO.setInteger("HSL_SUMREPLACE",sa012dto.getHslSumreplace());//置换意向客户数
					ttShowroomFlowPO.setInteger("SALES_SUMREPLACE",sa012dto.getSalesSumreplace());//置换成交数
					ttShowroomFlowPO.setInteger("INVOICE_SCAN_NUM",sa012dto.getInvoiceScanNum());//发票扫描数
					ttShowroomFlowPO.setInteger("LCREOLACE_ORDERS",sa012dto.getLcreplaceOrders());//留存订单数
					ttShowroomFlowPO.setInteger("IS_SEALED",0);//是否封存
					ttShowroomFlowPO.setBigDecimal("CREATE_BY",111111L);
					ttShowroomFlowPO.setDate("CREATE_DATE",new Date());
					ttShowroomFlowPO.insert();
				}
			}
		}
	}

}
