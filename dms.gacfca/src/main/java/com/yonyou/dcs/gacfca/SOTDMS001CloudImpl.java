package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.PtShiporderDao;
import com.yonyou.dms.DTO.gacfca.PtDlyInfoDetailDto;
import com.yonyou.dms.DTO.gacfca.PtDlyInfoDto;
import com.yonyou.dms.common.domains.PO.basedata.TiPtShiporderPO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;

/**
 * DMS产品、销售人员、经销商信息验证接口
 * @author luoyang
 *
 */
@Service
public class SOTDMS001CloudImpl extends BaseCloudImpl implements SOTDMS001Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDMS001CloudImpl.class);
	
	@Autowired
	PtShiporderDao dao;
	
//	@Autowired
//	SOTDMS001 sotdms001;
	
	/**
	 * 执行下发动作
	 */
	public String execute(){
		logger.info("================DMS产品、销售人员、经销商信息验证接口执行开始（SOTDMS001）====================");
		LinkedList<PtDlyInfoDto> vos = getDataList();
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================DMS产品、销售人员、经销商信息验证接口（SOTDMS001）下发结束：下发了(" + size + ")条数据====================");
		return null;
		
	}

	/**
	 * 下发数据方法
	 * @param list
	 * @param dealerCode
	 */
	private void sendData(PtDlyInfoDto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				LinkedList<PtDlyInfoDto> list = new LinkedList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				logger.info("向DEALER:" + dto.getDealerCode()+ "发送" + dto.getPtDlyInfoDetailList().size() + "条明细数据");
				//下发操作
//				sotdms001.			
				// 将 IS_SCAN=0或者null的字段更新为1
				Date currentTime = new Date();
				LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				TiPtShiporderPO.update("IS_DOWN = 1,UPDATE_BY= ?,UPDATE_DATE= ?", "IS_DOWN = 0 OR IS_DOWN IS NULL ",loginUser.getUserId(), currentTime);
				
				if(flag==1){
					logger.info("================DMS产品、销售人员、经销商信息验证接口下发成功（SOTDMS001）====================");
				}else{
					logger.info("================DMS产品、销售人员、经销商信息验证接口下发失败（SOTDMS001）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================DMS产品、销售人员、经销商信息验证接口下发经销商无业务范围（SOTDMS001）====================");
			}
		} catch (Exception e) {
			logger.info("================DMS产品、销售人员、经销商信息验证接口接口下发异常（SOTDMS001）====================");
		}
		
	}

	@Override
	public LinkedList<PtDlyInfoDto> getDataList() {
		LinkedList<PtDlyInfoDto> finalDlyInfoList = new LinkedList<PtDlyInfoDto>();
		List<PtDlyInfoDto> ptPtShiporderInfoVos = dao.queryPtShiporderPOInfo();
		if (null == ptPtShiporderInfoVos || ptPtShiporderInfoVos.size() == 0) {
			return null;
		}
		int totalItem = 0;
		for (int i = 0; i < ptPtShiporderInfoVos.size(); i++) {
			PtDlyInfoDto ptShiporderInfoVo = ptPtShiporderInfoVos.get(i);
			List<PtDlyInfoDetailDto> ptDlyInfoDetailVo = dao.queryPtShiporderDetailInfo(ptShiporderInfoVo.getDealerCode(),
					ptShiporderInfoVo.getDeliverynote(),ptShiporderInfoVo.getElinkorderno(),ptShiporderInfoVo.getInvoiccreationdate());
			if (ptDlyInfoDetailVo != null && ptDlyInfoDetailVo.size() > 0) {
				totalItem+=ptDlyInfoDetailVo.size();
				// 组装成hashmap
				HashMap<Integer, PtDlyInfoDetailDto> detailHashMap = new HashMap<Integer, PtDlyInfoDetailDto>();
				for (int n = 0; n < ptDlyInfoDetailVo.size(); n++) {
					PtDlyInfoDetailDto detailVO = (PtDlyInfoDetailDto) ptDlyInfoDetailVo.get(n);
					detailHashMap.put(n, detailVO);
				}
				ptShiporderInfoVo.setPtDlyInfoDetailList(detailHashMap);
				finalDlyInfoList.add(ptShiporderInfoVo);
			}
		}
		logger.info("====接收展厅销售人员信息接口下发结束====,共下发了(" + ptPtShiporderInfoVos.size()+ ")条数据,"+totalItem+"条明细数据。");
		return finalDlyInfoList;
	}

}
