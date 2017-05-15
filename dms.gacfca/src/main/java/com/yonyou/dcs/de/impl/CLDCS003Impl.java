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

import com.infoservice.dms.cgcsl.vo.ProductModelPriceVO;
import com.yonyou.dcs.dao.SaleMaterialPriceDao;
import com.yonyou.dcs.de.CLDCS003;
import com.yonyou.dcs.gacfca.CLDCS003Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.ProductModelPriceDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
/**
 * 
* @ClassName: CLDCS003Impl 
* @Description: 车型价格信息下发
* @author zhengzengliang 
* @date 2017年4月5日 上午11:57:17 
*
 */
@Service
public class CLDCS003Impl extends BaseImpl implements CLDCS003{
	
	private static final Logger logger = LoggerFactory.getLogger(CLDCS003Impl.class);
	
	@Autowired
	CLDCS003Cloud CLDCS003Cloud ;
	
	@Autowired
	SaleMaterialPriceDao saleMaterialPriceDao ;
	
	@Override
	public String sendData(List<String> dealerList, String[] groupId) throws Exception {
		logger.info("================产品主数据下发开始（CLDCS003）====================");
		try {
			if(null==dealerList || dealerList.size()==0){
				dealerList = saleMaterialPriceDao.getAllDcsCode(1);
			}
			for (int i = 0; i < dealerList.size(); i++) {
				String dealerCode = dealerList.get(i);
				send(CLDCS003Cloud.getDataList(groupId,dealerCode),dealerCode);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("================产品主数据下发结束（CLDCS003）====================");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private void send(LinkedList<ProductModelPriceDTO> dataList, String dealerCode) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				List<ProductModelPriceVO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				String dmsCode = CommonUtils.checkNull(saleMaterialPriceDao.getDmsDealerCode(dealerCode).get("DMS_CODE"));
				if(!"".equals(dmsCode)){
					sendAMsg("CLDMS003", dmsCode, body);
					logger.info("CLDMS003发送成功=====entityCode"+dmsCode+"===size："+dataList.size());
				}else{
					logger.info("CLDMS003发送失败=====entityCode"+dmsCode+"===size："+dataList.size());
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("CLDMS003发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.error(t.getMessage(), t);
		} finally {
		}
	}
	
	/**
	 * 数据转换
	 * @param vos
	 * @param dataList
	 */
	private void setVos(List<ProductModelPriceVO> vos, LinkedList<ProductModelPriceDTO> dataList) {
		for (int i = 0; i < dataList.size(); i++) {
			ProductModelPriceDTO dto = dataList.get(i);
			ProductModelPriceVO vo = new ProductModelPriceVO();
			vo.setBrandCode(dto.getBrandCode());
			vo.setBrandName(dto.getBrandName());
			vo.setSeriesCode(dto.getSeriesCode());
			vo.setSeriesName(dto.getSeriesName());
			vo.setModelCode(dto.getModelCode());
			vo.setModelName(dto.getModelName());
			vo.setConfigCode(dto.getConfigCode());
			vo.setConfigName(dto.getConfigName());
			vo.setColorCode(dto.getColorCode());
			vo.setColorName(dto.getColorName());
			vo.setProductCode(dto.getProductCode());
			vo.setProductName(dto.getProductName());
			vo.setOemDirectivePrice(dto.getOemDirectivePrice());
			vo.setPurchasePrice(dto.getPurchasePrice());
			vo.setMininumPrice(dto.getMininumPrice());
			vo.setModelYear(dto.getModelYear());
			vos.add(vo);
		}
	}
	

}
