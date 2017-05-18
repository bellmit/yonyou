package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.LicenseChangeDTO;

/**
 * @description 上传车牌变更信息
 * @author Administrator
 *
 */
public interface SEDMS002Coud {

	LinkedList<LicenseChangeDTO> getSEDMS002(String dealerCode);
}
