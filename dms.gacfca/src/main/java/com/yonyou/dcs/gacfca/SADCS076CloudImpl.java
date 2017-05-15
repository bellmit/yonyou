package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SADCS076Dao;
import com.yonyou.dms.DTO.gacfca.SADCS076DTO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
@Service
public class SADCS076CloudImpl extends BaseCloudImpl implements SADCS076Cloud{
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS076CloudImpl.class);
	
	@Autowired
	SADCS076Dao dao;

	/**
	 * 下发数据方法
	 * @param list
	 * @param dealerCode
	 */
	@Override
	public String sendData(List<Map<String, String>> bizParams){
		try {
			if (null == bizParams || bizParams.size() == 0) {
				logger.info("##############SADCS076Cloud   GCS召回活动完成下发开始#################");
				List<Map> ServiceList = dao.queryService();
				int sendNums = 0;//下发数据量
				for(int i=0;i<ServiceList.size();i++){
					List<String> dealerList = dao.queryDealer(ServiceList.get(i).get("RECALL_NO").toString());
					List<SADCS076DTO> dataList = dao.queryVehicle(ServiceList.get(i).get("RECALL_NO").toString());
					if(null!=dataList&&dataList.size()>0){
						String vins="";
						for (int n=0;n<dataList.size();n++){
							if(n==0){
								vins="'"+dataList.get(n).getVin().toString()+"'";
							}else{
								vins=vins+",'"+dataList.get(n).getVin().toString()+"'";
							}
						}
						if(null!=dataList && dataList.size()>0){
							for(int j=0;j<dealerList.size();j++){
								//下发操作
//								int flag = (dataList,dealerList.get(j));
//								if(flag==1){
//									logger.info("====================SADCS076Cloud  GCS召回活动完成下发成功========================");
//								}else{
//									logger.info("================SADCS076Cloud  GCS召回活动完成下发失败====================");
//								}
							}

						}else{
							logger.info("====SADCS076Cloud  GCS召回活动完成下发结束====,无数据！ ");
						}
						
						dbService.beginTxn();
						//批量更新已下发标记
						StringBuffer sql = new StringBuffer("");
						sql.append("update TT_RECALL_VEHICLE set GCS_SEND_STATUS = "+OemDictCodeConstants.IF_TYPE_YES+" \n");
						sql.append("where RECALL_ID="+ServiceList.get(i).get("RECALL_ID").toString()+" and VIN in ("+vins+") \n");
						OemDAOUtil.execBatchPreparement(sql.toString(),new ArrayList<Object>());
						//TtRecallVehicleDcsPO.update("GCS_SEND_STATUS = ?", "", params)
						//结束并清空事物
						dbService.endTxn(true);
						dbService.clean();
		    			
		    			sendNums+=dataList.size();
		    			
					}
					logger.info("#############SADCS076Cloud  GCS导入车辆下发信息#############,下发了(" + sendNums + ")条数据");
				}	
				logger.info("##############SADCS076Cloud  GCS导召回活动完成下发结束#################");
			}

		} catch (Exception e) {
			logger.info("##############SADCS076Cloud  GCS导召回活动完成下发异常#################");
		}
		return null;
	}

}
