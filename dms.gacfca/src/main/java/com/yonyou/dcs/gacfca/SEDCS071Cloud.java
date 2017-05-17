package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.DiscountCouponDTO;

/**
 * @ClassName: SEDCS071Cloud 
 * @Description:获取可用卡券信息同步(DMS->DCS->DMS)
 * @author xuqinqin 
 */
public interface SEDCS071Cloud  extends BaseCloud{
	
	public List<DiscountCouponDTO> handleExecutor(List<DiscountCouponDTO> dtos) throws Exception;
	
}
