package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dcs.dao.MySteriousCustomerReportDao;
import com.yonyou.dms.DTO.gacfca.SEDMS054DTO;
import com.yonyou.dms.common.domains.PO.basedata.TtMysteriousDatePO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.gacfca.SEDMS054Coud;
import com.yonyou.f4.common.database.DBService;

/**
 * 
 * Title:SEDCS054CloudImpl
 * Description: 克莱斯勒明检和神秘客业务需求BRD文档  下发
 * @author DC
 * @date 2017年4月10日 下午8:30:32
 */
@Service
public class SEDCS054CloudImpl extends BaseCloudImpl implements SEDCS054Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDCS054CloudImpl.class);
	
	@Autowired
	DBService dbService;
	
	@Autowired 
	MySteriousCustomerReportDao dao;
	
	@Autowired
	DeCommonDao deCommonDao;
	
	@Autowired
	SEDMS054Coud cldms;
	
	/**
	 * 执行下发动作
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String execute() throws Exception {
		logger.info("====克莱斯勒明检和神秘客下发开始====");
		try {
			/****************开启事物**********************/
			dbService.beginTxn(getTenantId());
			List<SEDMS054DTO> vos = getDataList();
			if (null == vos || vos.size() < 1) {
				dbService.endTxn(false);
				logger.info("====SEDCS054克莱斯勒明检和神秘客下发====,无数据！ ");
			} else {
				for(int i=0;i<vos.size();i++) {
					LinkedList<SEDMS054DTO> voList = new LinkedList<SEDMS054DTO>();
					SEDMS054DTO vo = vos.get(i);
					String dealerCode = vo.getEntityCode();//经销code
					Map map = deCommonDao.getDmsDealerCode(dealerCode);
					if (map==null || map.size()<=0) { //下发失败
						TtMysteriousDatePO updatePO = TtMysteriousDatePO.findFirst("DEALER_CODE = "+dealerCode+ " AND PHONE = ? ", vo.getPhone());//留店电话
						updatePO.setInteger("IS_DOWN", 2);//下发
						updatePO.saveIt();
						logger.info("没有维护经销商"+dealerCode);
					} else {//下发成功的
						String entityCode = CommonUtils.checkNull(map.get("DMS_CODE"));
						vo.setEntityCode(entityCode);//下端经销code
						vo.setDealerName(vo.getDealerName());//经销简称
						vo.setExecAuthor(vo.getExecAuthor());//执行人员
						vo.setPhone(vo.getPhone());//留店电话
						voList.add(vo);
						try {
							//下发给DMS
							cldms.getSEDMS054(voList, dealerCode);
							
							//下发成功之后  把状态修改成已下发
							TtMysteriousDatePO updatePO = TtMysteriousDatePO.findFirst("DEALER_CODE = "+dealerCode+ " AND PHONE = ? ", vo.getPhone());//留店电话
							updatePO.setInteger("IS_DOWN", 1);//下发
							updatePO.saveIt();
						} catch (Exception e) {
							TtMysteriousDatePO updatePO = TtMysteriousDatePO.findFirst("DEALER_CODE = "+dealerCode+ " AND PHONE = ? ", vo.getPhone());//留店电话
							updatePO.setInteger("IS_DOWN", 1);//下发
							updatePO.saveIt();
							logger.error("SEDCS054 Cann't send to EntityCode：" + vo.getEntityCode(), e);
						}
					}
				}
				logger.info("====SEDCS054SEDCS054克莱斯勒明检和神秘客下发结束====,下发了(" + vos.size() + ")条数据");
			}
			dbService.endTxn(true);
		} catch (Exception e) {
			dbService.endTxn(false);
			logger.error("SEDCS054下发失败", e);
			throw e;
		}finally {
			dbService.clean();
		}
		logger.info("====克莱斯勒明检和神秘客下发结束====");
		return null;
	}

	/**
	 * 获取数据方法
	 * 供新老接口获取数据方法
	 */
	@Override
	public LinkedList<SEDMS054DTO> getDataList() throws ServiceBizException {
		/**
		 *  获取数据逻辑：
		 * 	判断经销商是否为空
		 * 	  不为空、根据经销商查询业务范围重新给groupId赋值
		 * 	 为空      、判断groupId是否为空：不为空根据GroupID取物料信息，为空取所有
		 * 	 发送逻辑：每个经销商一次查一次
		 *  原有逻辑：
		 *  判断选中的业务范围都存在选中或所有经销商的业务范围
		 *  否则另外存储为Map
		 *  发送逻辑：分为两种情况发送
		 */
		LinkedList<SEDMS054DTO> vos = null;
		vos = dao.querySADMS054DTO();
		Integer size = vos==null?0:vos.size();
		logger.info("================下发数据大小："+ size +"====================");
		return vos;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String,String> sendData() throws Exception{
		logger.info("====克莱斯勒明检和神秘客下发开始====");
		HashMap<String,String> strMap = new HashMap<String,String>();
		List<String> dealerCodeList = new ArrayList<String>();
		int success = 0;//下发成功的
		int fail = 0; //下发失败的
		int total = 0;//总共的数据
		try {
			/****************开启事物**********************/
			dbService.beginTxn(getTenantId());
			List<SEDMS054DTO> vos = getDataList();
			if (null == vos || vos.size() < 0) {
				dbService.endTxn(false);
				logger.info("====SEDCS054克莱斯勒明检和神秘客下发====,无数据！ ");
			} else {
				total = vos.size();
				for(int i=0;i<vos.size();i++) {
					LinkedList<SEDMS054DTO> voList = new LinkedList<SEDMS054DTO>();
					SEDMS054DTO vo = vos.get(i);
					String dealerCode = vo.getEntityCode();//经销code
					Map<String, Object> map = deCommonDao.getDmsDealerCode(dealerCode);
					if (map==null || map.size()<=0) { //下发失败
						dealerCodeList.add(dealerCode);
						fail++;
						TtMysteriousDatePO updatePO = TtMysteriousDatePO.findFirst("DEALER_CODE = "+dealerCode+" AND PHONE = ? ", vo.getPhone());//留店电话
						updatePO.setInteger("IS_DOWN", 2);//下发
						updatePO.saveIt();
						logger.info("没有维护经销商"+dealerCode);
					} else {//下发成功的
						String entityCode = CommonUtils.checkNull(map.get("DMS_CODE"));
						vo.setEntityCode(entityCode);//下端经销code
						vo.setDealerName(vo.getDealerName());//经销简称
						vo.setExecAuthor(vo.getExecAuthor());//执行人员
						vo.setPhone(vo.getPhone());//留店电话
						voList.add(vo);
						try {
							//下发给DMS
							cldms.getSEDMS054(voList, dealerCode);
							//下发成功之后  把状态修改成已下发
							TtMysteriousDatePO updatePO = TtMysteriousDatePO.findFirst("DEALER_CODE = "+dealerCode+" AND PHONE = ? ", vo.getPhone());//留店电话
							updatePO.setInteger("IS_DOWN", 1);//下发
							updatePO.saveIt();
							success ++;
						} catch (Exception e) {
							dealerCodeList.add(dealerCode);
							fail++;
							TtMysteriousDatePO updatePO = TtMysteriousDatePO.findFirst("DEALER_CODE = "+dealerCode+" AND PHONE = ? ", vo.getPhone());//留店电话
							updatePO.setInteger("IS_DOWN", 2);//下发
							updatePO.saveIt();
							logger.error("SEDCS054 Cann't send to EntityCode：" + vo.getEntityCode(), e);
						}
					}
				}
				logger.info("====SEDCS054SEDCS054克莱斯勒明检和神秘客下发结束====,下发了(" + vos.size() + ")条数据");
			}
			dbService.endTxn(true);
		} catch (Exception e) {
			dbService.endTxn(false);
			logger.error("SEDCS054下发失败", e);
			throw e;
		}finally {
			dbService.clean();
		}
		logger.info("====克莱斯勒明检和神秘客下发结束====");
		if (null != dealerCodeList && dealerCodeList.size() != 0) {
			String errorCode = Arrays.toString(dealerCodeList.toArray());
			strMap.put("errorCode", errorCode);
			strMap.put("success", success+"");
			strMap.put("fail", fail+"");
			strMap.put("total", total+"");
			return strMap;
		}
		return null;
	}

}
