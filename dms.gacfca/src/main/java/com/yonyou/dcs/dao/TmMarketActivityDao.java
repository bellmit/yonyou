package com.yonyou.dcs.dao;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.TmMarketActivityDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.PO.market.TmMarketActivityPO;
/**
 * 
* @ClassName: TmMarketActivityDao 
* @Description: 市场活动（活动主单、车型清单）
* @author zhengzengliang 
* @date 2017年4月5日 下午7:11:19 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class TmMarketActivityDao extends OemBaseDAO{
	
	/**
	 * 
	 * @Title: queryAllTmMarketActivity 
	 * @Description: TODO(查询所有市场活动信息维护列表 IS_DOWN为0或者为空) 
	 * @return List<TmMarketActivityVO>    返回类型 
	 * @throws
	 */
	public LinkedList<TmMarketActivityDto> queryAllTmMarketActivity() {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TMA.ID,\n" );
		sql.append("       TMA.MARKET_NO,\n" );
		sql.append("       TMA.MARKET_NAME,\n" );
		sql.append("       TMA.SERIES_CODE,\n" );
		sql.append("       TMA.SERIES_NAME,\n" );
		sql.append("       TMA.MODEL_CODE,\n" );
		sql.append("       TMA.MODEL_NAME,\n" );
		sql.append("       TMA.START_DATE,\n" );
		sql.append("       TMA.END_DATE,\n" );
		sql.append("       TMA.MARKET_FEE,\n" );
		sql.append("       TMA.IS_DOWN,\n" );
		sql.append("       TMA.VER,\n" );
		sql.append("       TMA.IS_ARC,\n" );
		sql.append("       TMA.IS_DEL\n" );
		sql.append("  FROM TM_MARKET_ACTIVITY   TMA \n" );
		sql.append("   WHERE TMA.IS_DOWN = 0 or TMA.IS_DOWN is null \n" );
		
		LinkedList<TmMarketActivityDto> list = wrapperVO(OemDAOUtil.findAll(sql.toString(),null));
		return list;
	}
	protected LinkedList<TmMarketActivityDto> wrapperVO(List<Map> rs) {
		LinkedList<TmMarketActivityDto> resultList = new LinkedList<TmMarketActivityDto>();
		try {
			if(null!=rs && rs.size()>0){
				for (int i = 0; i < rs.size(); i++) {
					TmMarketActivityDto dto = new TmMarketActivityDto();
					if(!StringUtils.isNullOrEmpty(rs.get(i).get("MARKET_FEE"))){
						dto.setMarketFee(Double.valueOf(rs.get(i).get("MARKET_FEE").toString()));
					}
					dto.setSeriesName(CommonUtils.checkNull(rs.get(i).get("SERIES_NAME")));
					dto.setMarketName(CommonUtils.checkNull(rs.get(i).get("MARKET_NAME")));
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					if(!StringUtils.isNullOrEmpty(rs.get(i).get("END_DATE"))){
						dto.setEndDate( formatter.parse(CommonUtils.checkNull(rs.get(i).get("END_DATE"))) );
					}
					dto.setModelCode(CommonUtils.checkNull(rs.get(i).get("MODEL_CODE")));
					dto.setModelName(CommonUtils.checkNull(rs.get(i).get("MODEL_NAME")));
					if(!StringUtils.isNullOrEmpty(rs.get(i).get("IS_DOWN"))){
						dto.setIsDown(Integer.valueOf(rs.get(i).get("IS_DOWN").toString()));
					}
					if(!StringUtils.isNullOrEmpty(rs.get(i).get(""))){
						dto.setIsDel(Integer.valueOf(CommonUtils.checkNull(rs.get(i).get("MODEL_NAME"))));
					}
					dto.setMarketNo(CommonUtils.checkNull(rs.get(i).get("MARKET_NO")));
					if(!StringUtils.isNullOrEmpty(rs.get(i).get("START_DATE"))){
						dto.setStartDate( formatter.parse(CommonUtils.checkNull(rs.get(i).get("START_DATE"))) );
					}
					if(!StringUtils.isNullOrEmpty(rs.get(i).get(""))){
						dto.setVer(Integer.valueOf(CommonUtils.checkNull(rs.get(i).get("VER"))));
					}
					dto.setSeriesCode(CommonUtils.checkNull(rs.get(i).get("SERIES_CODE")));
					if(!StringUtils.isNullOrEmpty(rs.get(i).get(""))){
						dto.setIsArc(Integer.valueOf(CommonUtils.checkNull(rs.get(i).get("IS_ARC"))));
					}
					resultList.add(dto);
				}
			}
		} catch (Exception e) {
			throw new ServiceBizException(e);
		}
		return resultList;
	}
	
	//将TM_MARKET_ACTIVITY中 is_down=0或者null的字段更新为1
	public int updateTmMarketActivityDownStatus() {
		int i = TmMarketActivityPO.update(" IS_DOWN = ? ", " IS_DOWN=0 or IS_DOWN is null ", 1);
		return i ;
	}

}
