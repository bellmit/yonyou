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
import com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.MaterialVO;
import com.yonyou.dms.function.common.OemDictCodeConstants;


/**
 * 物料主数据信息发送接口
 * @author weixia
 *
 */
@Service
public class DCSTOLMS001Impl extends BaseService implements DCSTOLMS001{

	private static final Logger logger = LoggerFactory.getLogger(DCSTOLMS001Impl.class);
	
	@Autowired
	private BsuvMaterialDao dao;
	
	public MaterialVO[]  execute(Date from ,Date to ) throws Exception {
		logger.info("====物料主数据信息发送开始====");
		//事务开启
		beginDbService();
		
		Date startTime = new Date();
		MaterialVO[] materialList = null; 
		String exceptionMsg = "";
		Integer  flag = OemDictCodeConstants.IF_TYPE_YES;
		int dataSize = 0;
		try {
			//获取要发送数据
			List<MaterialVO> list = dao.getMaterialList(from,to);
			dataSize=list.size();
			if(null!=list && list.size()>0){
				materialList = new MaterialVO[dataSize];
				for(int i = 0 ; i < list.size() ; i ++){
					materialList[i] = list.get(i);
				}
			}
			dbService.endTxn(true);
		} catch(Exception e) {
			exceptionMsg = CommonBSUV.getErrorInfoFromException(e);
			flag = OemDictCodeConstants.IF_TYPE_NO;
			logger.error(e.getMessage(), e);
			dbService.endTxn(false);
		}finally{
			Base.detach();
			dbService.clean();
			
			beginDbService();
			try {
				CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "物料主数据信息发送：DCS->LMS", startTime, dataSize, flag,
						exceptionMsg, "", "", new Date());
				dbService.endTxn(true);
			} catch (Exception e2) {
				dbService.endTxn(false);
				logger.info("================物料主数据信息发送：DCS->LMS=========");
			}finally {
				Base.detach();
				dbService.clean();
			}
			logger.info("====物料主数据信息发送结束===");
		}
		return materialList;
	}
	
	
	

}
