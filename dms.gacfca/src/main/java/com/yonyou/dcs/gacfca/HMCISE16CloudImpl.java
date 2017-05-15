package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.ClaimRejectedImpVO;
import com.infoservice.dms.cgcsl.vo.ClaimRejectedVO;
import com.yonyou.dcs.dao.ClaimRejectedSendDao;
import com.yonyou.dms.DTO.gacfca.ClaimRejectedDTO;
import com.yonyou.dms.DTO.gacfca.ClaimRejectedImpDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtWrClaimDcsDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.f4.common.database.DBService;
/**
 * @Title: HMCISE16CloudImpl
 * @Description:索赔单驳回下发
 * @author xuqinqin 
 * @date 2017年5月4日 
 */
@Service
public class HMCISE16CloudImpl extends BaseCloudImpl implements HMCISE16Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(HMCISE16CloudImpl.class);
	
	@Autowired 
	DBService dbService;
	
	@Autowired
	ClaimRejectedSendDao dao  ;


	/**
	 * 下发数据方法
	 * @param list
	 * @param dealerCode
	 */
	@Override
	public String sendAllData(TtWrClaimDcsDTO dto){
		try {
			logger.info("==============HMCISE16售后服务索赔单驳回下发开始================");
			LinkedList<ClaimRejectedDTO> vos = getDataList(dto);
			if(null!=vos && vos.size()>0){
				//下发操作
//				int flag = (vos,CommonUtils.checkNull(dao.getDmsDealerCode(dealerCode).get("DMS_CODE")));
//				if(flag==1){
//					logger.info("====================HMCISE16售后服务索赔单驳回下发成功========================");
//				}else{
//					logger.info("================HMCISE16售后服务索赔单驳回下发失败====================");
//				}
			}else{
				logger.info("====HMCISE16售后服务索赔单驳回下发结束====,无数据！ ");
			}
		} catch (Exception e) {
			logger.info("================HMCISE16售后服务索赔单驳回下发异常====================");
		}
		return null;
	}
	/**
	 * 获取数据方法
	 * 供新老接口获取数据方法
	 */
	@Override
	public LinkedList<ClaimRejectedDTO> getDataList(TtWrClaimDcsDTO dto) throws ServiceBizException {
		
		LinkedList<ClaimRejectedImpDTO> dtosi = new LinkedList<ClaimRejectedImpDTO>();
		LinkedList<ClaimRejectedDTO> dtos = new LinkedList<ClaimRejectedDTO>(); 
		dtosi = dao.getClaimRejectedVo(dto);
		if (dtosi == null || dtosi.size() <= 0) {
			logger.info("====HMCISE16售后服务索赔单驳回下发结束====,无数据！ ");
			return null;
		}
		List<String> dmsCodes = new ArrayList<String>();
		for (ClaimRejectedImpDTO rejectevo : dtosi) {
			try {
				if (rejectevo!=null && rejectevo.getDealerId()!=null) {
					Map<String, Object> dmsDealer = dao.getDmsDealerCodeForDealerId(rejectevo.getDealerId());
					if (dmsDealer!=null && dmsDealer.size()>0) {
						String entityCode = CommonUtils.checkNull(dmsDealer.get("DMS_CODE"));
						String dcsCode = CommonUtils.checkNull(dmsDealer.get("DCS_CODE"));//上端经销商CODE
						if (!"".equals(entityCode)) {
							dmsCodes.add(entityCode);//可下发的经销商列表
							ClaimRejectedDTO vo = new ClaimRejectedDTO();
							String downRoNo=rejectevo.getRoNo().replace(dcsCode,"").replace("-","").trim();
							vo.setRoNo(downRoNo);
							vo.setRoStatus(rejectevo.getRoStatus());
							vo.setDealerCode(dcsCode);
							dtos.add(vo);
						}
					}
				}
			} catch (Exception e) {
				logger.info("Cann't send to " + dao.getDealerCode(rejectevo.getDealerId()), e);
			}
		}
		return dtos;
	}
	/**
	 * 获取下发经销商(entityCode)
	 */
	@Override
	public List<String> getEntityCodeList(TtWrClaimDcsDTO dto)throws ServiceBizException {
		List<String> dmsCodes = new ArrayList<String>();
		LinkedList<ClaimRejectedImpDTO> vosi = new LinkedList<ClaimRejectedImpDTO>();
		vosi = dao.getClaimRejectedVo(dto);
		if (vosi == null || vosi.size() <= 0) {
			logger.info("====HMCISE16售后服务索赔单驳回下发结束====,无数据！ ");
			return null;
		}
		for (ClaimRejectedImpDTO rejectevo : vosi) {
			try {
				if (rejectevo!=null && rejectevo.getDealerId()!=null) {
					Map<String, Object> dmsDealer = dao.getDmsDealerCodeForDealerId(rejectevo.getDealerId());
					if (dmsDealer!=null && dmsDealer.size()>0) {
						String entityCode = CommonUtils.checkNull(dmsDealer.get("DMS_CODE"));
						if (!"".equals(entityCode)) {
							dmsCodes.add(entityCode);//可下发的经销商列表
						}
					}
				}
			} catch (Exception e) {
				logger.info("Cann't send to " + dao.getDealerCode(rejectevo.getDealerId()), e);
			}
		}
		return dmsCodes;
	}
}
