package com.yonyou.dms.vehicle.service.afterSales.partMgr;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmLimiteCposPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.vehicle.dao.afterSales.partMgr.PartCeilingPriceDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtPartCeilingPriceDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtPartCeilingPricePO;

/**
 * 配件限价
 * @author Administrator
 *
 */
@Service
public class PartCeilingPriceServiceImpl implements PartCeilingPriceService{
	@Autowired
	PartCeilingPriceDao  partCeilingPriceDao;

	/**
	 * 配件限价查询
	 */
	@Override
	public PageInfoDto partCeilingPriceQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return partCeilingPriceDao.OtherMaintainQuery(queryParam);
	}

	/**
	 * 信息回显
	 */
	@Override
	public TtPartCeilingPricePO getTmLimiteById(Long id) {
		// TODO Auto-generated method stub
		return TtPartCeilingPricePO.findById(id);
	}
/**
 * 显示列表信息
 */
	@Override
	public PageInfoDto CeilingPriceQuery(Map<String, String> queryParam,String priceCode,String priceScope) {
		// TODO Auto-generated method stub
		return partCeilingPriceDao.PartCeilingPriceQuery(queryParam,priceCode,priceScope);
	}

	/**
	 * 删除
	 */
	@Override
	public void delete(Long id) {
		 TtPartCeilingPricePO.delete("ID=?", id);	
	}
	
	/**
	 * 新增
	 */
	public void add(TtPartCeilingPriceDTO ptdto) {
		   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		      TtPartCeilingPricePO ptPo=new TtPartCeilingPricePO();
			   ptPo.setString("CEILING_PRICE_NAME",ptdto.getCeilingPriceName());
			   ptPo.setString("CEILING_PRICE_SCOPE", ptdto.getCeilingPriceScope());
			   ptPo.setString("REMARK",ptdto.getRemark());
			   String ceilingPriceCode=getPartNo();
			   ptPo.setString("CEILING_PRICE_CODE",ceilingPriceCode);
			   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
			   ptPo.setDate("UPDATE_DATE", new Date());
			   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
			   ptPo.setDate("CREATE_DATE", new Date());  
		       ptPo.setInteger("IS_DOWN",OemDictCodeConstants.IF_TYPE_NO);
		       ptPo.setInteger("IS_ERR_NUM",0);
		       ptPo.setInteger("IS_SUC_NUM",0);
			   ptPo.saveIt();
		
	}
			
	
	
	
	/**
	 * 获取配件限价的最大行数
	 * @return
	 */
	public synchronized String getPartNo(){
		String sql = "select count(tpcp.ID) maxLine from TT_PART_CEILING_PRICE_dcs tpcp where "
				+ "DATE_FORMAT(tpcp.CREATE_DATE,'yyyy-MM-dd') = DATE_FORMAT(current_date,'yyyy-MM-dd')  ";
		List<Map> ps=OemDAOUtil.findAll(sql.toString(), null);
		int count=0;
		String count2=ps.get(0).get("maxLine").toString();
		count=Integer.parseInt(count2);
		String num =fillCharacter(count, 3);
		DateFormat df = new SimpleDateFormat("yyMMdd");
		return "N"+df.format(new Date())+num;				
	}
	
	public static String fillCharacter(int count,int length){
		String newStr = String.valueOf(count);
		String appendZero = "";
		if(newStr.length()<length){
			for(int i=length;i>newStr.length();i--){
				appendZero+="0";
			}
			newStr = appendZero + newStr;
		}
		return newStr ;
	}
/**
 * 更新
 */
	@Override
	public void update(Long id,TtPartCeilingPriceDTO  ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtPartCeilingPricePO ptPo = TtPartCeilingPricePO.findById(id);	
    	   ptPo.setString("CEILING_PRICE_NAME",ptdto.getCeilingPriceName());
		   ptPo.setString("CEILING_PRICE_SCOPE", ptdto.getCeilingPriceScope());
		   ptPo.setString("REMARK",ptdto.getRemark());
		   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		   ptPo.setDate("UPDATE_DATE", new Date());
		   ptPo.saveIt();
		
	}

	/**
	 * 下发
	 */
	public void xiafa(Long id) {
		TmLimiteCposPO ptPo = TmLimiteCposPO.findById(id);
	   	if(ptPo!=null){
		   ptPo.setLong("IS_DOWN",OemDictCodeConstants.IF_TYPE_YES);
		   ptPo.saveIt();
	   	}
	}


}
