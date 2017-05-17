package com.yonyou.dcs.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.SEDCS015DTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SEDCS015Dao extends OemBaseDAO {

	/**
	 * 下发总量
	 * @return
	 */
	public List<Map> countDo(){
		StringBuffer sql= new StringBuffer();
		sql.append("select distinct    \n");
		sql.append("    CASE WHEN TMMH.GROUP_TYPE='70391001' THEN MG1.GROUP_CODE ELSE MG2.GROUP_CODE END AS SERIES_CODE,  \n");//车系
		sql.append("    CASE WHEN TMMH.GROUP_TYPE='70391001' THEN null ELSE MG2.GROUP_CODE END AS MODEL_CODE,  \n");//车型 
		sql.append("    TMMH.MODEL_YEAR,   \n");//年款 
		sql.append("    TMMH.MANHOUR_CODE,  \n");//工时代码
		sql.append("    TMMH.MANHOUR_NAME,  \n");//工时中文名称
		sql.append("    TMMH.MANHOUR_ENGLISH_NAME,  \n");//工时英文名称 
		sql.append("    TMMH.MANHOUR_NUM,  \n");//标准工时数
		sql.append("    TMMH.ONE_CODE,   \n");//一级分类代码
		sql.append("    TMMH.TWO_CODE,  \n");//二级分类代码
		sql.append("    TMMH.THREE_CODE,  \n");//三级分类代码
		sql.append("    TMMH.FOUR_CODE,  \n"); //四级分类代码
		sql.append("    TMMH.GROUP_TYPE, \n"); //分类类型
		sql.append("    TMMH.CLIAM_NUM   \n");//索赔工时数
		sql.append("FROM TT_MMH_MAN_HOUR_DCS TMMH    \n");
		sql.append("LEFT JOIN TM_VHCL_MATERIAL_GROUP MG1 ON  MG1.GROUP_ID= TMMH.SERIES_ID   \n");
		sql.append("LEFT JOIN TM_VHCL_MATERIAL_GROUP MG2 ON  MG2.GROUP_ID= TMMH.SERIES_ID   \n");
		sql.append("WHERE TMMH.IS_SEND = "+OemDictCodeConstants.IF_TYPE_NO+" \n");
		sql.append(" 	  AND TMMH.MANHOUR_STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" \n");
		sql.append(" 	  AND MG2.PARENT_GROUP_ID = MG1.GROUP_ID\n");
		sql.append(" 	  AND MG1.GROUP_LEVEL= 2 \n");
		List<Map> listJJ = OemDAOUtil.findAll(sql.toString(), null);
		return listJJ;
	}
	
	/**
	 * 需要下发的维修工时数据
	 * @return
	 */
	public LinkedList<SEDCS015DTO> queryMoreInfo(String array) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct TMMH.MMH_ID,   \n");
		sql.append("    decode(TMMH.GROUP_TYPE,'70391001',VM.SERIES_CODE,vm2.SERIES_CODE ) SERIES_CODE,  \n");//车系  
		sql.append("    decode(TMMH.GROUP_TYPE,'70391001',null,vm2.MODEL_CODE ) MODEL_CODE,  \n");//车型  
		sql.append("    TMMH.MODEL_YEAR,  \n");//年款
		sql.append("    TMMH.MANHOUR_CODE,  \n");//工时代码 
		sql.append("    TMMH.MANHOUR_NAME,  \n");//工时中文名称
		sql.append("    TMMH.MANHOUR_ENGLISH_NAME,  \n");//工时英文名称
		sql.append("    TMMH.MANHOUR_NUM,   \n");//标准工时数
		sql.append("    TMMH.ONE_CODE,   \n");//一级分类代码
		sql.append("    TMMH.TWO_CODE,  \n");//二级分类代码
		sql.append("    TMMH.THREE_CODE,  \n");//三级分类代码
		sql.append("    TMMH.FOUR_CODE,   \n");//四级分类代码
		sql.append("    TMMH.GROUP_TYPE,  \n");//分类类型
		sql.append("    TMMH.CLIAM_NUM   \n");//索赔工时数
		sql.append("FROM TT_MMH_MAN_HOUR_DCS TMMH    \n");
		sql.append("LEFT JOIN TM_VHCL_MATERIAL_GROUP MG1 ON  MG1.GROUP_ID= TMMH.SERIES_ID   \n");
		sql.append("LEFT JOIN TM_VHCL_MATERIAL_GROUP MG2 ON  MG2.GROUP_ID= TMMH.SERIES_ID   \n");
		sql.append("WHERE TMMH.IS_SEND = "+OemDictCodeConstants.IF_TYPE_NO+" \n");
		sql.append(" 	  AND TMMH.MANHOUR_STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" \n");
		sql.append(" 	  AND MG2.PARENT_GROUP_ID = MG1.GROUP_ID\n");
		sql.append(" 	  AND MG1.GROUP_LEVEL= 2 \n");
		if(!"".equals(array)){
			sql.append(" 	  AND TMMH.MMH_ID in ("+array+") \n");
		}else{
			sql.append("   ORDER BY TMMH.MMH_ID");
			sql.append("   limit 1000 ");
		}
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<SEDCS015DTO> dtolist = new LinkedList<>();
		if(null!=list&&list.size()>0){
			for(Map map:list){
				SEDCS015DTO dto=new SEDCS015DTO();
				dto.setManhourCode(CommonUtils.checkNull(map.get("MANHOUR_CODE")));//工时代码
				dto.setManhourName(CommonUtils.checkNull(map.get("MANHOUR_NAME")));//工时中文名称
				dto.setManhourEnglishName(CommonUtils.checkNull(map.get("MANHOUR_ENGLISH_NAME")));//工时英文名称
				dto.setManhourNum(CommonUtils.checkNull(map.get("MANHOUR_NUM")));//标准工时数
				dto.setSeriesCode(CommonUtils.checkNull(map.get("SERIES_CODE")));//车系
				dto.setModelCode(CommonUtils.checkNull(map.get("MODEL_CODE")));//车型
				dto.setModelYear(CommonUtils.checkNull(map.get("MODEL_YEAR")));//年款
				dto.setOneCode(CommonUtils.checkNull(map.get("ONE_CODE")));//一级分类代码
				dto.setTwoCode(CommonUtils.checkNull(map.get("TWO_CODE")));//二级分类代码
				dto.setThreeCode(CommonUtils.checkNull(map.get("THREE_CODE")));//三级分类代码
				dto.setFourCode(CommonUtils.checkNull(map.get("FOUR_CODE")));//四级分类代码
				dto.setGroupType(CommonUtils.checkNull(map.get("GROUP_TYPE")));//分类类型
				dto.setCliamNum(CommonUtils.checkNull(map.get("CLIAM_NUM")));//索赔工时数
				dtolist.add(dto);
			}
		}
		return dtolist;
	}
	
//	protected SEDCS015DTO wrapperVO(ResultSet rs, int idx) {
//		SEDCS015DTO vo = new SEDCS015DTO();
//		vo.setSeriesCode(rs.getString("SERIES_CODE"));// 车系
//		vo.setModelCode(rs.getString("MODEL_CODE"));// 车型
//		vo.setModelYear(rs.getString("MODEL_YEAR"));// 年款
//		vo.setManhourCode(rs.getString("MANHOUR_CODE"));// 工时代码
//		vo.setManhourName(rs.getString("MANHOUR_NAME"));// 工时中文名称
//		vo.setManhourEnglishName(rs.getString("MANHOUR_ENGLISH_NAME"));// 工时英文名称
//		vo.setManhourNum(rs.getString("MANHOUR_NUM"));// 标准工时数
//		vo.setOneCode(rs.getString("ONE_CODE"));// 一级分类代码
//		vo.setTwoCode(rs.getString("TWO_CODE"));// 二级分类代码
//		vo.setThreeCode(rs.getString("THREE_CODE"));// 三级分类代码
//		vo.setFourCode(rs.getString("FOUR_CODE"));// 四级分类代码
//		vo.setGroupType(rs.getString("GROUP_TYPE"));// 分类类型
//		vo.setCliamNum(rs.getString("CLIAM_NUM"));// 分类类型
//		return vo;
//	}
	
	/**
	 * 多选下发成功后，更新下发状态，下发时间
	 * @param array
	 */
	public void updateStatue(String array){
		String updateStatus = "";
		if("".equals(array)){
			
//			updateStatus = "SELECT MMH_ID FROM TT_MMH_MAN_HOUR WHERE IS_SEND = "+OemDictCodeConstants.IF_TYPE_NO+" AND MANHOUR_STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" " +
//						   " ORDER BY MMH_ID limit 1000 ";
//			List<Map> list=OemDAOUtil.findAll(updateStatus, null);
//			if(null!=list&&list.size()>0){
//				for(Map map:list){
//					TtMmhManHourPO.update("IS_SEND = ? AND SEND_DATE = ?", "MMH_ID = ?", OemDictCodeConstants.IF_TYPE_YES,new Date(System.currentTimeMillis()),CommonUtils.checkNull(map.get("MMH_ID")));
//				}
//			}
			updateStatus = "UPDATE (SELECT * FROM TT_MMH_MAN_HOUR_DCS WHERE IS_SEND = "+OemDictCodeConstants.IF_TYPE_NO+" AND MANHOUR_STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" " +
					   " ORDER BY MMH_ID limit 1000) set IS_SEND = "+OemDictCodeConstants.IF_TYPE_YES+"," +
					   "SEND_DATE=CURRENT_TIMESTAMP";
		}else{
			updateStatus = "UPDATE TT_MMH_MAN_HOUR_DCS set IS_SEND = "+OemDictCodeConstants.IF_TYPE_YES+"," +
						   "SEND_DATE=sysdate where MMH_ID in ("+array+")";
		}
		//批量处理
		OemDAOUtil.execBatchPreparement(updateStatus,null);
	}
}
