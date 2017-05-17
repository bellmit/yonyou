package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.SA007VO;
import com.infoservice.dms.cgcsl.vo.SADCS076VO;
import com.yonyou.dcs.dao.SADCS076Dao;
import com.yonyou.dcs.de.SADCS076;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.SA007Dto;
import com.yonyou.dms.DTO.gacfca.SADCS076DTO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
@Service
public class SADCS076Impl  extends BaseImpl  implements  SADCS076 {
	private static final Logger logger = LoggerFactory.getLogger(SADCS076Impl.class);
	
	@Autowired
	SADCS076Dao dao;
	
	@Override
	public String sendData(List<Map<String, String>> bizParams){
		try {
			if (null == bizParams || bizParams.size() == 0) {
				logger.info("##############SADCS076 GCS召回活动完成下发开始#################");
				List<Map> ServiceList = dao.queryService();
				int sendNums = 0;//下发数据量
				for(int i=0;i<ServiceList.size();i++){
					List<String> dealerList = dao.queryDealer(ServiceList.get(i).get("RECALL_NO").toString());
					LinkedList<SADCS076DTO> dataList = dao.queryVehicle(ServiceList.get(i).get("RECALL_NO").toString());
					if(null!=dataList&&dataList.size()>0){
						String vins="";
						for (int n=0;n<dataList.size();n++){
							if(n==0){
								vins="'"+dataList.get(n).getVin().toString()+"'";
							}else{
								vins=vins+",'"+dataList.get(n).getVin().toString()+"'";
							}
						}
						send(dataList,dealerList);
						
//						dbService.beginTxn();
						//批量更新已下发标记
						StringBuffer sql = new StringBuffer("");
						sql.append("update TT_RECALL_VEHICLE set GCS_SEND_STATUS = "+OemDictCodeConstants.IF_TYPE_YES+" \n");
						sql.append("where RECALL_ID="+ServiceList.get(i).get("RECALL_ID").toString()+" and VIN in ("+vins+") \n");
						OemDAOUtil.execBatchPreparement(sql.toString(),new ArrayList<Object>());
						//TtRecallVehicleDcsPO.update("GCS_SEND_STATUS = ?", "", params)
						//结束并清空事物
//						dbService.endTxn(true);
//						dbService.clean();
		    			
		    			sendNums+=dataList.size();
					}
				}	
				logger.info("#############SADCS076GCS导入车辆下发信息#############,下发了(" + sendNums + ")条数据");
				logger.info("##############SADCS076GCS导召回活动完成下发结束#################");
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private String send(LinkedList<SADCS076DTO> dataList, List<String> dmsCodes) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				List<SADCS076VO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				if(!"".equals(dmsCodes)){
					sendMsg("SEDMS076", dmsCodes, body);
					logger.info("SADCS076 GCS召回活动完成发送成功======size："+dataList.size());
				}else{
					logger.info("SADCS076 GCS召回活动完成下发失败======size："+dataList.size());
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("SADCS076  发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.error(t.getMessage(), t);
		} 
		return null;
	}
	/**
	 * 数据转换
	 * @param vos
	 * @param dataList
	 */
	private void setVos(List<SADCS076VO> vos, LinkedList<SADCS076DTO> dataList) {
		for (int i = 0; i < dataList.size(); i++) {
			SADCS076DTO dto = dataList.get(i);
			SADCS076VO vo = new SADCS076VO();
			vo.setRecallNo(dto.getRecallNo());
			vo.setRecallName(dto.getRecallName());
			vo.setVin(dto.getVin());
			vo.setDealerName(dto.getDealerName());
			vos.add(vo);
		}
	}
}
