package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TmWxMaintainPackageRcvDTO;

/**
 * @description 保养套餐修改上报接口
 * @author Administrator
 *
 */
public interface SADMS099Coud {
	public List<TmWxMaintainPackageRcvDTO> getSADMS099(String dealerCode,Long userId,String packageCode,String packagePrice);
}
