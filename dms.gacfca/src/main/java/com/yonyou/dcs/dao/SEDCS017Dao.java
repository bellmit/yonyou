package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.SEDCS017DTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SEDCS017Dao extends OemBaseDAO {

	
	/**
	 * 下发总量
	 * @return
	 */
	public List<Map> countDo(int groupType){
		StringBuffer sql= new StringBuffer();
		sql.append("select t1.GROUP_TYPE,t1.MMH_CODE MMH_CODE_ONE,t1.MMH_NAME MMH_NAME_ONE,t1.MMH_STATUS MMH_STATUS_ONE,  \n");
		sql.append(" 	   t2.MMH_CODE MMH_CODE_TWO,t2.MMH_NAME MMH_NAME_TWO,t2.MMH_STATUS MMH_STATUS_TWO, \n");
		sql.append(" 	   t3.MMH_CODE MMH_CODE_THREE,t3.MMH_NAME MMH_NAME_THREE,t3.MMH_STATUS MMH_STATUS_THREE, \n");
		sql.append(" 	   t4.MMH_CODE MMH_CODE_FOUR,t4.MMH_NAME MMH_NAME_FOUR,t4.MMH_STATUS MMH_STATUS_FOUR \n");
		sql.append("from TT_MMH_PARAMETER_DCS t1 \n");
		sql.append("left join TT_MMH_PARAMETER_DCS t2 on t1.MMH_CODE = t2.PARENT_MMH_CODE and t2.GROUP_TYPE = t1.GROUP_TYPE and t2.MMH_LEVEL = 2   \n");
		sql.append(" and t2.MMH_STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"' \n");
		sql.append("left join TT_MMH_PARAMETER_DCS t3 on t2.MMH_CODE = t3.PARENT_MMH_CODE and t3.GROUP_TYPE = t2.GROUP_TYPE and t3.MMH_LEVEL = 3  \n");
		sql.append(" and t3.MMH_STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"' \n");
		sql.append("left join TT_MMH_PARAMETER_DCS t4 on t3.MMH_CODE = t4.PARENT_MMH_CODE and t4.GROUP_TYPE = t3.GROUP_TYPE and t4.MMH_LEVEL = 4  \n");
		sql.append(" and t4.MMH_STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"' \n");
		sql.append("where t1.MMH_LEVEL = 1 \n");
		sql.append("	  and t1.MMH_STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"'   \n");
		sql.append("	  and t1.GROUP_TYPE = '"+groupType+"'   \n");
		List<Map> listJJ = OemDAOUtil.findAll(sql.toString(), null);
		return listJJ;
	}
	/**
	 * 需要下发的维修工时参数数据
	 * @return
	 */
	public LinkedList<SEDCS017DTO> queryAllInfo(int curPage,int groupType) {
		//一次下发两百条
		int s=(curPage-1)*200;
		int e=curPage*200;
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select t1.GROUP_TYPE,t1.MMH_CODE MMH_CODE_ONE,t1.MMH_NAME MMH_NAME_ONE,t1.MMH_STATUS MMH_STATUS_ONE,  \n");
		sql.append(" 	   t2.MMH_CODE MMH_CODE_TWO,t2.MMH_NAME MMH_NAME_TWO,t2.MMH_STATUS MMH_STATUS_TWO, \n");
		sql.append(" 	   t3.MMH_CODE MMH_CODE_THREE,t3.MMH_NAME MMH_NAME_THREE,t3.MMH_STATUS MMH_STATUS_THREE, \n");
		sql.append(" 	   t4.MMH_CODE MMH_CODE_FOUR,t4.MMH_NAME MMH_NAME_FOUR,t4.MMH_STATUS MMH_STATUS_FOUR \n");
		sql.append("from TT_MMH_PARAMETER_DCS t1 \n");
		sql.append("left join TT_MMH_PARAMETER_DCS t2 on t1.MMH_CODE = t2.PARENT_MMH_CODE and t2.GROUP_TYPE = t1.GROUP_TYPE and t2.MMH_LEVEL = 2  \n");
		sql.append(" and t2.MMH_STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"' \n");
		sql.append("left join TT_MMH_PARAMETER_DCS t3 on t2.MMH_CODE = t3.PARENT_MMH_CODE and t3.GROUP_TYPE = t2.GROUP_TYPE and t3.MMH_LEVEL = 3 \n");
		sql.append(" and t3.MMH_STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"' \n");
		sql.append("left join TT_MMH_PARAMETER_DCS t4 on t3.MMH_CODE = t4.PARENT_MMH_CODE and t4.GROUP_TYPE = t3.GROUP_TYPE and t4.MMH_LEVEL = 4 \n");
		sql.append(" and t4.MMH_STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"' \n");
		sql.append("where t1.MMH_LEVEL = 1 \n");
		sql.append("	  and t1.MMH_STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"'   \n");
		sql.append("	  and t1.GROUP_TYPE = ?   \n");
		sql.append("   order by t1.MMH_CODE \n");
		sql.append("   limit  "+s+","+e +"\n");
		List<Object> params = new ArrayList<Object>();
		params.add(groupType);
		List<Map> maplist=OemDAOUtil.findAll(sql.toString(), params);
		LinkedList<SEDCS017DTO> dtolist = new LinkedList<SEDCS017DTO>();
		if(null!=maplist&&maplist.size()>0){
			for(Map map:maplist){
				SEDCS017DTO dto=new SEDCS017DTO();
				dto.setMmhCodeOne(CommonUtils.checkNull(map.get("MMH_CODE_ONE")));//一级主分类代码
				dto.setMmhNameOne(CommonUtils.checkNull(map.get("MMH_NAME_ONE")));//一级主分类名称 
				dto.setMmhStatusOne(CommonUtils.checkNull(map.get("MMH_STATUS_ONE")));//一级状态
				dto.setMmhCodeTwo(CommonUtils.checkNull(map.get("MMH_CODE_TWO")));//二级主分类名称 
				dto.setMmhNameTwo(CommonUtils.checkNull(map.get("MMH_NAME_TWO")));//二级主分类名称
				dto.setMmhStatusTwo(CommonUtils.checkNull(map.get("MMH_STATUS_TWO")));//二级状态
				dto.setMmhCodeThree(CommonUtils.checkNull(map.get("MMH_CODE_THREE")));//三级主分类名称 
				dto.setMmhNameThree(CommonUtils.checkNull(map.get("MMH_NAME_THREE")));//三级主分类名称
				dto.setMmhStatusThree(CommonUtils.checkNull(map.get("MMH_STATUS_THREE")));//三级状态
				dto.setMmhCodeFour(CommonUtils.checkNull(map.get("MMH_CODE_FOUR")));//四级主分类名称 
				dto.setMmhNameFour(CommonUtils.checkNull(map.get("MMH_NAME_FOUR")));//四级主分类名称 
				dto.setMmhStatusFour(CommonUtils.checkNull(map.get("MMH_STATUS_FOUR")));//四级状态
				dto.setGroupType(CommonUtils.checkNull(map.get("GROUP_TYPE")));//分类类别
				dtolist.add(dto);
			}
		}
		return dtolist;		
	}
	
	/**
	 * 下发成功后，更新下发状态，下发时间
	 * @param currDate
	 */
	public void updateSend(){
		String updateSql =  "update TT_MMH_PARAMETER_DCS set SEND_STATUS = '"+OemDictCodeConstants.IF_TYPE_YES+"',SEND_DATE= sysdate() " +
							" where MMH_STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"' AND SEND_STATUS = '"+OemDictCodeConstants.IF_TYPE_NO+"'" ;
		//批量处理
		OemDAOUtil.execBatchPreparement(updateSql,null);
	}
	
}
