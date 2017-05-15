package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.infoservice.dms.cgcsl.vo.RepairOrderReStatusVO;
import com.yonyou.dms.DTO.gacfca.RepairOrderReStatusDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtWrRepairPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class RepairOrderResultStatusDao extends OemBaseDAO {
	
	public static Logger logger = Logger.getLogger(RepairOrderResultStatusDao.class);
	private static final RepairOrderResultStatusDao dao = new RepairOrderResultStatusDao();
	public static final RepairOrderResultStatusDao getInstance() {
		return dao;
	}
	/**
	 * 获得取消结算返回给下端的VO列表
	 * @param vo
	 * @return
	 */
	public List<RepairOrderReStatusVO> queryRepairVO(RepairOrderReStatusVO vo) {
		
		// 检查索赔单
		try {
			Map<String, Object> map = getRepairNo(vo.getRoNo());// 检查索赔单，如果存在，则下端不能取消结算
			if (null!=map && map.size()>0) {
				List<RepairOrderReStatusVO> rvos = new ArrayList<RepairOrderReStatusVO>();
				RepairOrderReStatusVO rVo = new RepairOrderReStatusVO();
				rVo.setRoNo(vo.getRoNo());
				rVo.setRoStatus(0);// 0 不能取消结算   1 取消结算成功
				rvos.add(rVo);
				return rvos;
			}
		} catch (Exception e) {
			return null;
		}
		
		// 更改工单状态
		updateTtWrRepair(vo.getRoNo());// 更改工单为未结算状态
		
		List<RepairOrderReStatusVO> vos = setVOList(vo.getRoNo());
		
		return vos;
	}
	/**
	 * 获得取消结算返回给下端的VO列表
	 * @param vo
	 * @return
	 */
	public List<RepairOrderReStatusDTO> queryRepairDTO(RepairOrderReStatusDTO vo) {
		
		// 检查索赔单
		try {
			Map<String, Object> map = getRepairNo(vo.getRoNo());// 检查索赔单，如果存在，则下端不能取消结算
			if (null!=map && map.size()>0) {
				List<RepairOrderReStatusDTO> rvos = new ArrayList<RepairOrderReStatusDTO>();
				RepairOrderReStatusDTO rVo = new RepairOrderReStatusDTO();
				rVo.setRoNo(vo.getRoNo());
				rVo.setRoStatus(0);// 0 不能取消结算   1 取消结算成功
				rvos.add(rVo);
				return rvos;
			}
		} catch (Exception e) {
			return null;
		}
		
		// 更改工单状态
		updateTtWrRepair(vo.getRoNo());// 更改工单为未结算状态
		
		List<RepairOrderReStatusDTO> vos = setDTOList(vo.getRoNo());
		
		return vos;
	}
	/**
	 * 组装工单
	 */
	public 	List<RepairOrderReStatusVO>  setVOList(String roNo){
		List<RepairOrderReStatusVO> voList=new ArrayList<RepairOrderReStatusVO>();
		// 组装工单
		StringBuffer sql= new StringBuffer();
		//sql.append("SELECT R.REPAIR_NO,R.STATUS FROM TT_WR_REPAIR R \n" );
		sql.append("SELECT R.REPAIR_NO FROM TT_WR_REPAIR R \n" );
		sql.append(" WHERE 1 = 1 \n" );
		sql.append("   AND R.REPAIR_NO = '"+roNo+"' \n");// 工单号
		sql.append("   AND R.IS_DEL = 0 \n");// 逻辑未删除
		List<Map> reList = OemDAOUtil.findAll(sql.toString(), null);
		if (null != reList && reList.size() > 0) {
			for (Map map : reList) {
				RepairOrderReStatusVO vo=new RepairOrderReStatusVO();
				vo.setRoNo(CommonUtils.checkNull(map.get("REPAIR_NO")));
				voList.add(vo);
			}
		}
		return voList;
	}
	/**
	 * 组装工单
	 */
	public 	List<RepairOrderReStatusDTO>  setDTOList(String roNo){
		List<RepairOrderReStatusDTO> voList=new ArrayList<RepairOrderReStatusDTO>();
		// 组装工单
		StringBuffer sql= new StringBuffer();
		//sql.append("SELECT R.REPAIR_NO,R.STATUS FROM TT_WR_REPAIR R \n" );
		sql.append("SELECT R.REPAIR_NO FROM TT_WR_REPAIR R \n" );
		sql.append(" WHERE 1 = 1 \n" );
		sql.append("   AND R.REPAIR_NO = '"+roNo+"' \n");// 工单号
		sql.append("   AND R.IS_DEL = 0 \n");// 逻辑未删除
		List<Map> reList = OemDAOUtil.findAll(sql.toString(), null);
		if (null != reList && reList.size() > 0) {
			for (Map map : reList) {
				RepairOrderReStatusDTO vo=new RepairOrderReStatusDTO();
				vo.setRoNo(CommonUtils.checkNull(map.get("REPAIR_NO")));
				voList.add(vo);
			}
		}
		return voList;
	}
	/**
	 * 根据下端上报的 工单号 查询 索赔单
	 * 如果索赔单存在，则下端不能取消结算，上端要给下端返回一个不能取消结算的状态
	 */
	public Map<String, Object> getRepairNo(String roNo) throws Exception {

		StringBuffer sql= new StringBuffer();
		sql.append("SELECT C.RO_NO from TT_WR_CLAIM C WHERE 1 = 1 AND C.RO_NO = '"+roNo+"' AND C.IS_DEL = 0");
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		return map;
	}
	
	/**
	 * 更改工单为未结算状态
	 * @param repairNo
	 * @return
	 */
	public int updateTtWrRepair(String repairNo) {
		
		int flag=TtWrRepairPO.update("STATUS = ?", "REPAIR_NO = ?", OemDictCodeConstants.REPAIR_ORD_BALANCE_TYPE_01,repairNo);
		return flag;
	}
}
