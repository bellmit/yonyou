package com.yonyou.dcs.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SEDCS014Dao extends OemBaseDAO {
	/**
	 * 删除接口表中未封存最大CURRENTDATE时间对应的周一，到该时间
	 * @return
	 * @throws Exception
	 */
	public void delUnSealed(String dealerCode) {
		StringBuffer sql= new StringBuffer();
		if (Utility.testIsNotNull(dealerCode)) {
			sql.append("delete TT_SHOWROOM_FLOW_DCS where ID in(	\n" );
			sql.append("   select ID from TT_SHOWROOM_FLOW_DCS_DCS tt	\n" );
			sql.append("   	where DATE_FORMAT(tt.CURRENTDATE,'%Y-%m-%d')>=(	\n" );
			sql.append("   			select DATE_FORMAT(tf.cuDate,'%Y-%m-%d') from(	\n" );
			sql.append("				select max(tsf.CURRENTDATE)-(DAYOFWEEK_ISO(max(tsf.CURRENTDATE))-1) days cuDate	\n" );
			sql.append("					from TT_SHOWROOM_FLOW_DCS tsf	\n");
			sql.append("			)tf	\n");
			sql.append("		)	\n");
			sql.append("		and	\n");
			sql.append("		DATE_FORMAT(tt.CURRENTDATE,'%Y-%m-%d')<=(	\n" );
			sql.append("			select DATE_FORMAT(tf.cuDate,'%Y-%m-%d') from(	\n");
			sql.append("				select max(tsf.CURRENTDATE) cuDate from TT_SHOWROOM_FLOW_DCS tsf	\n");
			sql.append("			)tf	\n");
			sql.append("		)	\n");
			sql.append("		and tt.IS_SEALED=0	\n");
			sql.append("	) and dealer_Code='"+dealerCode+"'	\n");
			OemDAOUtil.execBatchPreparement(sql.toString(), null);
//			List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
//			if(null!=list&&list.size()>0){
//				for(Map map:list){
//					TtShowroomFlowPO.delete("ID = ? AND DEALER_CODE = ?",CommonUtils.checkNull(map.get("ID")), dealerCode);
//				}
//				
//			}
		}
	}
	
	/**
	 * 获取当月月末
	 * @return
	 */
	public String getThisMonthDate() {
		StringBuffer sql= new StringBuffer();
		sql.append("select   DATE_FORMAT(last_day(curdate()),'%Y-%m-%d') thisMonthDate\n");
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		return CommonUtils.checkNull(map.get("thisMonthDate")).toString();
	}
	
	/**
	 * 将重复数据逻辑删除
	 * 逻辑删除原因：对于月末，会有月封存的逻辑，如果月末处于残缺周中，下端还是会上报周一至当天的数据上来，故将当周周一至上月月末的数据逻辑删除
	 * @return
	 * @throws Exception
	 */
	public void updateEndMonth() {
		StringBuffer sql= new StringBuffer();
		sql.append("update TT_SHOWROOM_FLOW_DCS set IS_DEL=1 where ID in(	\n" );
		sql.append("  select ID from TT_SHOWROOM_FLOW_DCS tsfr	\n" );
		sql.append("	where DATE_FORMAT(tsfr.CURRENTDATE,'%Y-%m-%d')>=DATE_FORMAT(sysdate-(7-DAYOFWEEK_ISO(sysdate)) days,'%Y-%m-%d')	\n" );
		sql.append("   	and DATE_FORMAT(tsfr.CURRENTDATE,'%Y-%m-%d')<=DATE_FORMAT(LAST_DAY(sysdate-1 month),'%Y-%m-%d')	\n" );
		sql.append("   	and not exists(	\n" );
		sql.append("   			select 1 from TT_SHOWROOM_FLOW_DCS tsf	\n" );
		sql.append("   				where DATE_FORMAT(tsf.CURRENTDATE,'%Y-%m-%d')>=DATE_FORMAT(sysdate-(7-DAYOFWEEK_ISO(sysdate)) days,'%Y-%m-%d')	\n" );
		sql.append("   				and DATE_FORMAT(tsf.CURRENTDATE,'%Y-%m-%d')<=DATE_FORMAT(LAST_DAY(sysdate-1 month),'%Y-%m-%d')	\n" );
		sql.append("   				and DATE_FORMAT(tsf.CREATE_DATE,'%Y-%m-%d')=DATE_FORMAT(LAST_DAY(sysdate-1 month),'%Y-%m-%d')	\n" );
		sql.append("   				and tsf.ID=tsfr.ID	\n" );
		sql.append("			)	\n");
		sql.append(")	\n");
		OemDAOUtil.execBatchPreparement(sql.toString(), null);
//		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
//		if(null!=list&&list.size()>0){
//			for(Map map:list){
//				TtShowroomFlowPO.update("IS_DEL = ? ","ID = ?",1, CommonUtils.checkNull(map.get("ID")));
//			}
//			
//		}
	}

}
