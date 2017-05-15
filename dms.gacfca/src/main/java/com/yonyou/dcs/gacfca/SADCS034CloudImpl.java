package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.po.TiCoOverTotalReportPO;
import com.yonyou.dcs.dao.ActivityResultDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiCoOverTotalReportDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
@Service
public class SADCS034CloudImpl extends BaseCloudImpl implements SADCS034Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS034CloudImpl.class);
	@Autowired
	ActivityResultDao dao ;
	
	@Override
	public String receiveDate(List<TiCoOverTotalReportDTO> dtos) throws Exception {
		String msg = "1";
		
		try {
			logger.info("*************** SADCS034Cloud超过90,60天未交车订单且未交车原因为空接收开始 *******************");
			//先做删除，再做插入
			//删除
			deleteData(dtos);
			
			//插入
			for (TiCoOverTotalReportDTO dto : dtos) {
				insertData(dto);
			}
			logger.info("*************** SADCS034Cloud超过90,60天未交车订单且未交车原因为空上报完成 ********************");
			
		} catch (Exception e) {
			logger.error("*************** SADCS034Cloud超过90,60天未交车订单且未交车原因为空上报异常 *****************", e);
			msg = "0";
			throw new ServiceBizException(e);
		} 
		return msg;
	}
	/**
	 * 删除操作
	 * @param bodys
	 */
	private void deleteData(List<TiCoOverTotalReportDTO> dtos) {
		
		try {
			StringBuffer sql=new StringBuffer();
			StringBuffer seriesCodes=new StringBuffer();
			String dlrCode="";
			for (TiCoOverTotalReportDTO dto :dtos) {
				Map<String, Object> map = dao.getSaDcsDealerCode(dto.getDealerCode());
				dlrCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
				seriesCodes.append("'"+dto.getSeriesCode()+"',");
			}
			String serCodeString=seriesCodes.toString().substring(0, seriesCodes.lastIndexOf(","));
			sql.append("select* from TI_CO_OVER_TOTAL_REPORT where 1=1 ");
			sql.append(" AND DEALER_CODE='"+dlrCode+"'");
			sql.append(" AND SERIES_CODE in ("+serCodeString+")");
			List<TiCoOverTotalReportPO> dellist = TiCoOverTotalReportPO.findBySQL(sql.toString(), null);
			for(TiCoOverTotalReportPO delPo:dellist){
				delPo.delete();
			}
//			TiCoOverTotalReportPO.delete("DELETE FROM TI_CO_OVER_TOTAL_REPORT WHERE DEALER_CODE = ? AND ", params)
		} catch (Exception e) {
			logger.info("超过90,60天未交车订单且未交车原因为空的订删除失败", e);
			throw new ServiceBizException(e);
		} 
	}
	/**
	 * 写入数据 - 超过60天未交车订单且未交车原因为空的订单接收
	 * @param vo
	 * @throws Exception
	 */
	private void insertData(TiCoOverTotalReportDTO dto) throws Exception {
		logger.info("====超过90,60天未交车订单且未交车原因为空的订单接收开始====");
		try {
			Map<String, Object> map = dao.getSaDcsDealerCode(dto.getDealerCode());
			String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
			TiCoOverTotalReportPO po=new TiCoOverTotalReportPO();
			po.setString("DEALER_CODE",dealerCode);
			po.setInteger("OVER_CUSTOMER",dto.getOverCustomer());
			po.setInteger("OVER_ORDER",dto.getOverOrder());
			po.setInteger("VALID_CUSTOMER_NUM",dto.getValidCustomerNum());
			po.setInteger("VALID_ORDER_NUM",dto.getValidOrderNum());
			po.setString("SERIES_COE",dto.getSeriesCode());
			po.setBigDecimal("",DEConstant.DE_CREATE_BY);//创建人
			po.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));//创建日期
			po.insert();
			logger.info("====超过90,60天未交车订单且未交车原因为空的订接收结束====");
		} catch (Exception e) {
			logger.error("超过90,60天未交车订单且未交车原因为空的订接收失败", e);
			throw new ServiceBizException(e);
		}
		
	}
	
}
