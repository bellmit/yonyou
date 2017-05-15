package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtPtDeliverDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtDeliverDetailDcsPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.dao.PartDeliverManageAaDao;
import com.yonyou.dms.part.domains.DTO.basedata.PartDeliverDTO;

/**
 * 直发交货单
 * @author ZhaoZ
 *@date 2017年3月27日
 */
@Service
public class PartDeliverManageServiceImpl implements PartDeliverManageService{

	@Autowired
	private  PartDeliverManageAaDao partDao;
	
	/**
	 * 交货单查询
	 */
	@Override
	public PageInfoDto checkOrderPartInfo(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.findList(queryParams);
	}

	/**
	 * 直发交货单修改回显信息
	 */
	@Override
	public Map<String, Object> findDeliverInfoByDeliverId(BigDecimal id) {
		return partDao.deliverInfoByDeliverId(id);
	}

	/**
	 * 接货清单信息查询
	 */
	@Override
	public PageInfoDto findAcceptInfoByDeliverId(BigDecimal id) {
		return partDao.acceptInfoByDeliverId(id);
	}

	/**
	 * 保存
	 */
	@Override
	public void saveDeliveryOrder(PartDeliverDTO dto, BigDecimal id, LoginInfoDto loginInfo) {
		boolean flag1 = false;
		Double netPrice=0.0;//净价
		List<Map> list = dto.getDms_table();
		if(list!=null && list.size()>0){
			for(Map map:list){
				if(!StringUtils.isNullOrEmpty(map.get("PART_CODE"))){
					TtPtDeliverDetailDcsPO tpddPo = TtPtDeliverDetailDcsPO.findById(map.get("PART_CODE"));
					Pattern pattern = Pattern.compile("[0-9]*");
					String planNum = "";
					if(map.get("PLAN_NUM")==null 
							|| map.get("PLAN_NUM")==""){
						planNum="0";
					}else{
						planNum = map.get("PLAN_NUM").toString();
					}
					boolean fla = pattern.matcher(planNum).matches();    
					if(!fla){
						throw new ServiceBizException("计划量请输入数字！");
					}
					tpddPo.setLong("PLAN_NUM", planNum);
					Double deliverAmount = (Double.parseDouble(planNum))*(tpddPo.getDouble("NET_PRICE"));//明细运单总额
					tpddPo.setDouble("DELIVER_AMOUNT", deliverAmount);
					tpddPo.setBigDecimal("UPDATE_BY", loginInfo.getUserId());
					SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					long time= System.currentTimeMillis();
					try {
						Date date = sdf.parse(sdf.format(new Date(time)));
						java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
						tpddPo.setTimestamp("UPDATE_DATE",st);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					netPrice+=deliverAmount;
					flag1 = tpddPo.saveIt();
					if(flag1){			
					}else{
						throw new ServiceBizException("修改失败！");
					}
				}
			}
		}
		BigDecimal   bd= new BigDecimal(netPrice*(1+OemDictCodeConstants.PARTBASEPRICE_TAXRATE));
		Double amount= bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  //保留2位小数
		TtPtDeliverDcsPO tpdPo = TtPtDeliverDcsPO.findById(id);
		if(tpdPo!=null){
			tpdPo.setDouble("AMOUNT", amount);//总价
			tpdPo.setDouble("NET_PRICE",netPrice);//净价
			tpdPo.setDouble("TAX_AMOUNT",amount-netPrice);//税额
			tpdPo.setBigDecimal("UPDATE_BY",loginInfo.getUserId());//修改人
			tpdPo.setInteger("IS_DCS_SEND", OemDictCodeConstants.IF_TYPE_NO);//下发状态--未下发
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long time= System.currentTimeMillis();
			try {
				Date date = sdf.parse(sdf.format(new Date(time)));
				java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
				tpdPo.setTimestamp("UPDATE_DATE",st);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			flag1 = tpdPo.saveIt();
			if(flag1){			
			}else{
				throw new ServiceBizException("修改失败！");
			}
		}
		
	}

	/**
	 * 交货单明细查询
	 */
	@Override
	public PageInfoDto queryOrderInfo(Map<String, String> queryParams) {
		return partDao.queryOrderInfos(queryParams);
	}

	/**
	 * 货运单管查詢
	 */
	@Override
	public PageInfoDto queryDeliverInit(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.getDeliverInit(queryParams);
	}

	/**
	 * 直发交货单修改回显信息
	 */
	@Override
	public PageInfoDto findDelivertInfoByDeliverId(BigDecimal id) {
		return partDao.getDelivertInfo(id);
	}

	@Override
	public List<Map> exeDeliverInfo( String deliverId, String code,Map<String, String> queryParams) throws ServiceBizException {
		return partDao.getexeDeliverInfo(deliverId,code,queryParams);
	}

}
