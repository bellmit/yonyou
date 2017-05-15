package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.ProperServManInfoDTO;

/**
 * @description 将符合条件的客户经理上传至微信平台
 * @author Administrator
 *
 */
public interface SEDMS016 {
	public List<ProperServManInfoDTO> getSEDMS016(String dealerCode,long userId);
}
