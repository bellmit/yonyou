/**
 * @Title: ClaimRejectedSendDao.java 
 * @Description:索赔单驳回下发
 * @remark
 */

package com.yonyou.dcs.dao;

import java.util.Date;
import java.util.LinkedList;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.ClaimRejectedImpDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtWrClaimDcsDTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;

@Repository
public class ClaimRejectedSendDao extends OemBaseDAO{
private static final ClaimRejectedSendDao dao = new ClaimRejectedSendDao();
	
	public static final ClaimRejectedSendDao getInstance() {
		return dao;
	}
	/**
	 * 获取下发VO
	 * @param claimpo
	 * @return
	 */
	public LinkedList<ClaimRejectedImpDTO> getClaimRejectedVo(TtWrClaimDcsDTO claimDto) {
		LinkedList<ClaimRejectedImpDTO> vos = new LinkedList<ClaimRejectedImpDTO>();
		ClaimRejectedImpDTO vo = new ClaimRejectedImpDTO();
		vo.setDealerId(claimDto.getDealerId());
		vos.add(vo);
		return vos;
	}	
	/**
	 * 获取索赔删除下发的VO
	 * @param claimpo
	 * @return
	 */
	public LinkedList<ClaimRejectedImpDTO> getClaimRejectedVo2(TtWrClaimDcsDTO claimDto) {
		LinkedList<ClaimRejectedImpDTO> vos = new LinkedList<ClaimRejectedImpDTO>();
		ClaimRejectedImpDTO vo = new ClaimRejectedImpDTO();
		vo.setRoNo(claimDto.getRoNo());//工单号
		vo.setIsDel(1);//已删除 上端1为已删除
		vo.setDealerId(claimDto.getDealerId());//特约店编号
		vos.add(vo);
		return vos;
	}	
}
