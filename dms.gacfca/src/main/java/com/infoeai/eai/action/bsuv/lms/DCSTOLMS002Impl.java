package com.infoeai.eai.action.bsuv.lms;

import java.util.Date;
import java.util.List;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.bsuv.common.CommonBSUV;
import com.infoeai.eai.dao.bsuv.lms.BsuvMaterialDao;
import com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.DealerVo;
import com.yonyou.dms.function.common.OemDictCodeConstants;


/**
 * 经销商基础数据发送
 * @author weixia
 *
 */
@Service
public class DCSTOLMS002Impl extends BaseService implements DCSTOLMS002{

	private static final Logger logger = LoggerFactory.getLogger(DCSTOLMS002Impl.class);
	
	@Autowired
	private BsuvMaterialDao dao;
	
	
	public DealerVo[] execute() throws Exception {
		logger.info("====经销商基础数据发送开始====");
		//事务开启
		beginDbService();
		
		DealerVo[] dealerList = null; 
		Date startTime = new Date();
		String exceptionMsg = "";
		Integer  flag = OemDictCodeConstants.IF_TYPE_YES;
		int dataSize = 0;
		try {
			//获取经销商基础数据
			List<DealerVo> list = dao.getDealerList();
			dealerList  = new DealerVo[list.size()];
			
			dataSize = list.size();
			//转换传送数据方式
			if(null!=list && list.size()>0){
				for(int i = 0 ; i < list.size() ; i ++){
					dealerList[i]  = list.get(i);
				}
			}
			dbService.endTxn(true);
		} catch(Exception e) {
			flag = OemDictCodeConstants.IF_TYPE_NO;
			exceptionMsg = CommonBSUV.getErrorInfoFromException(e);
			logger.info(e.getMessage(), e);
			dbService.endTxn(false);
		}finally{
			Base.detach();
			dbService.clean();
			
			
			
			beginDbService();
			try {
				CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "经销商基础数据发送：DCS->LMS", startTime, dataSize, flag,
						exceptionMsg, "", "", new Date());
				dbService.endTxn(true);
			} catch (Exception e2) {
				dbService.endTxn(false);
				logger.info("================经销商基础数据发送：DCS->LMS=========");
			}finally {
				Base.detach();
				dbService.clean();
			}
			logger.info("====官网物料车系信息发送结束===");
		}
		return dealerList;
	}
	
	
	

}
