package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dcs.dao.SaleMaterialGroupDao;
import com.yonyou.dms.DTO.gacfca.CLDMS002Dto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.CLDMS002;

/**
 * 2017-03-27
 * @author 夏威
 * 车型组主数据（品牌、车系、车型、配置）
 */
@Service
public class CLDCS002CloudImpl extends BaseCloudImpl implements CLDCS002Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(CLDCS002CloudImpl.class);
	
	@Autowired 
	SaleMaterialGroupDao dao;
	
	@Autowired
	CLDMS002 cldms;
	
	@Autowired
	DeCommonDao deCommonDao;
	
	/**
	 * 获取数据方法
	 * 供新老接口获取数据方法
	 */
	@Override
	public LinkedList<CLDMS002Dto> getDataList(String[] groupId,String dealerCode) throws ServiceBizException {
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
		LinkedList<CLDMS002Dto> vos = null;
		if(!"".equals(CommonUtils.checkNull(dealerCode))){
			//剔除选中的车系中不属于经销商范围的车系
			groupId = dao.getDealerByGroupId(groupId,dealerCode);
			if(null != groupId && groupId.length!=0){
				vos = dao.selectMaterialGroupInfo(groupId);
			}
		}else{
			if(null != groupId && groupId.length!=0){
				vos = dao.selectMaterialGroupInfo(groupId);
			}else{
				vos = dao.queryMaterialGroupInfo();
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================经销商:"+dealerCode+"下发数据大小："+ size +"====================");
		return vos;
	}

	/**
	 * 执行下发动作
	 */
	@Override
	public String execute() throws ServiceBizException {
		logger.info("================车型组主数据下发执行开始（CLDCS002）====================");
		List<String> errCodes = null;
		//第一步：获取所有经销商信息
		List<String> dealerList = dao.getAllDcsCode(0);
		if(null==dealerList || dealerList.size()==0){
			return null;
		}
		//第二步：循环获取经销商 所有拥有的业务范围数据并下发
		errCodes = sendData(dealerList,null);
		logger.info("================车型组主数据下发执行结束（CLDCS002）====================");
		return errCodes.toString();
	}
	
	/**
	 * 下发数据方法
	 * @param list
	 * @param dealerCode
	 */
	public List<String> sendData(List<String> dealerList,String[] groupIds){
		List<String> errCodes = new ArrayList<String>();
		try {
			// 第二步：循环获取经销商 所有拥有的业务范围数据并下发
			for (int i = 0; i < dealerList.size(); i++) {
				String dealerCode = dealerList.get(i);
				// 待下发物料组取得
				LinkedList<CLDMS002Dto> vos = getDataList(groupIds, dealerCode);
				// 店端经销商代码取得
				Map dmsDealer = deCommonDao.getDmsDealerCode(dealerCode);
				// 店端代码存在且业务范围内物料存在的场合，下发物料组
				if ((!StringUtils.isNullOrEmpty(dmsDealer.get("DMS_CODE"))) && vos != null && vos.size()>0) {
					int flag = cldms.getCLDMS002(vos, dmsDealer.get("DMS_CODE").toString());
					if (flag == 1) {
						logger.info("================车型组主数据下发成功（CLDCS002）====================");
					} else {
						logger.info("================车型组主数据下发失败（CLDCS002）====================");
						errCodes.add(dealerCode);
					}
				} else {
					// 经销商无业务范围
					logger.info("================车型组主数据下发经销商无业务范围（CLDCS002）====================");
				}
			}

		} catch (Exception e) {
			logger.info("================车型组主数据下发异常（CLDCS002）====================");
			errCodes.add("物料組下发失败，请联系管理员！");
//			throw new BizException(e.getMessage());
		}
		return errCodes;
	}
	
	
}
