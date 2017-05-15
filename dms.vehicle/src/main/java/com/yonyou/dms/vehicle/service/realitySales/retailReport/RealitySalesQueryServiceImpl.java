package com.yonyou.dms.vehicle.service.realitySales.retailReport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsBasicParaPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsNvdrPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.vehicle.dao.realitySales.retailReport.RealitySalesQueryDao;
import com.yonyou.dms.vehicle.domains.DTO.dlrinvtyManage.TtVsNvdrDTO;

@Service
@SuppressWarnings("all")
public class RealitySalesQueryServiceImpl implements RealitySalesQueryService {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	@Autowired
	private RealitySalesQueryDao dao;
	
	/**
	 * 零售上报查询
	 */
	@Override
	public PageInfoDto realitySalesQueryQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryList(queryParam,loginInfo);
		return pgInfo;
	}

	/**
	 * 上报信息详情
	 */
	@Override
	public Map<String, Object> queryDetail(String id, String vin, LoginInfoDto loginInfo) throws ServiceBizException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map> list = dao.queryDetail(id);
		TtVsNvdrPO tvNvdrPo = new TtVsNvdrPO();
		if(null!=list && list.size()>0){
			map = list.get(0);
			tvNvdrPo.setString("VIN",vin);//地盘号
			String deliveryDate= CommonUtils.printDate(CommonUtils.currentDateTime());
			tvNvdrPo.setLong("BUSINESS_ID", loginInfo.getDealerId()); //经销商ID
			tvNvdrPo.setString("PROVINCE", CommonUtils.checkNull(map.get("REGION_NAME"),""));//省
			tvNvdrPo.setString("CITY", CommonUtils.checkNull(map.get("REGION_NAME_A"),""));//城市
			tvNvdrPo.setString("POSTAL_CODE", CommonUtils.checkNull(map.get("ZIP_CODE"),""));//邮编
			TmDealerPO tdPO = TmDealerPO.findById(loginInfo.getDealerId());
			map.put("VIN", vin);
			map.putAll(tvNvdrPo.toMap());
			map.putAll(tdPO.toMap());
			map.put("DELIVERY_DATE", deliveryDate);
		}
		return map;
		
	}

	@Override
	public void realitySalesReporting(TtVsNvdrDTO tvnDto, LoginInfoDto loginInfo)
			throws ServiceBizException {
		try {
			String type=CommonUtils.checkNull(tvnDto.getType());//批量/单次上报  ：0/1
			Long tvpDealerId = Long.parseLong(CommonUtils.checkNull(loginInfo.getDealerId(),"0")); //经销商ID	
			Integer fastRetailStatus = OemDictCodeConstants.FAST_RETAIL_STATUS_02;  //快速零售上报状态
			TtVsBasicParaPO tvbpPO = TtVsBasicParaPO.findFirst(" DEALER_ID = '"
										+ tvpDealerId
										+"' AND FAST_RETAIL_STATUS = ? ", fastRetailStatus);
			if(null!=tvbpPO){
				new ServiceBizException("上报失败,当前权限不足,如有问题请联系管理员");
			}else{
				if(type.equals("0")){   
					String vins=tvnDto.getVins().substring(0,tvnDto.getVins().length()-1);
					Date deliveryDateTime = tvnDto.getDeliveryDate();//交付时间deliveryDate
					String deliveryDate = DateUtil.formatDefaultDateTimes(deliveryDateTime);
					String vin="";
					if(vins.indexOf(",")>0){
						String[] vinsParam=vins.split(",");
						for (int i = 0; i < vinsParam.length; i++) {
							vin = vinsParam[i];
							List<TtVsNvdrPO> list = TtVsNvdrPO.find(" VIN = ? ", vin);
							int listSize = list.size();
							if(listSize==0){
								TtVsNvdrPO tvNvdrPo = new TtVsNvdrPO();
								tvNvdrPo.setString("VIN", vin);
								tvNvdrPo.setTimestamp("DELIVERY_DATE", deliveryDate);//交付时间
								tvNvdrPo.setLong("BUSINESS_ID", loginInfo.getDealerId());//经销商ID
								tvNvdrPo.setInteger("NVDR_STATUS", OemDictCodeConstants.VEHICLE_RETAIL_STATUS_02);//零售上报状态    NVDR状态
								tvNvdrPo.setDate("CREATE_DATE", CommonUtils.currentDateTime());
								tvNvdrPo.setLong("CREATE_BY", loginInfo.getUserId());
								tvNvdrPo.setLong("REPORT_TYPE", OemDictCodeConstants.RETAIL_REPORT_TYPE_01);//零售上报类型
								tvNvdrPo.setLong("STATUS",OemDictCodeConstants.VEHICLE_REPORT_STATUS_02);//交车上报状态
								tvNvdrPo.insert();
							} else {
								TtVsNvdrPO updateTvNvdrPo = TtVsNvdrPO.findFirst("VIN = ? ", vin);
								updateTvNvdrPo.setTimestamp("DELIVERY_DATE", deliveryDate);//交付时间
								updateTvNvdrPo.setLong("BUSINESS_ID", loginInfo.getDealerId());//经销商ID
								updateTvNvdrPo.setInteger("NVDR_STATUS", OemDictCodeConstants.VEHICLE_RETAIL_STATUS_02);//零售上报状态    NVDR状态
								updateTvNvdrPo.setDate("CREATE_DATE", CommonUtils.currentDateTime());
								updateTvNvdrPo.setLong("CREATE_BY", loginInfo.getUserId());
								updateTvNvdrPo.setLong("REPORT_TYPE", OemDictCodeConstants.RETAIL_REPORT_TYPE_01);//零售上报类型
								updateTvNvdrPo.setLong("STATUS",OemDictCodeConstants.VEHICLE_REPORT_STATUS_02);//交车上报状态
								updateTvNvdrPo.setInteger("IS_DEL", 0); //有效
								updateTvNvdrPo.saveIt();
							}
						}
					}else{
						vin=vins;
						TtVsNvdrPO tvnPO = new TtVsNvdrPO();
						List<TtVsNvdrPO> list = TtVsNvdrPO.find(" VIN = ? ", vin);
						int listSize = list.size();
						if(listSize==0){
							TtVsNvdrPO tvNvdrPo = new TtVsNvdrPO();
							tvNvdrPo.setString("VIN", vin);
							tvNvdrPo.setTimestamp("DELIVERY_DATE", deliveryDate);//交付时间
							tvNvdrPo.setLong("BUSINESS_ID", loginInfo.getDealerId());//经销商ID
							tvNvdrPo.setInteger("NVDR_STATUS", OemDictCodeConstants.VEHICLE_RETAIL_STATUS_02);//零售上报状态    NVDR状态
							tvNvdrPo.setDate("CREATE_DATE", CommonUtils.currentDateTime());
							tvNvdrPo.setLong("CREATE_BY", loginInfo.getUserId());
							tvNvdrPo.setLong("REPORT_TYPE", OemDictCodeConstants.RETAIL_REPORT_TYPE_01);//零售上报类型
							tvNvdrPo.setLong("STATUS",OemDictCodeConstants.VEHICLE_REPORT_STATUS_02);//交车上报状态
							tvNvdrPo.insert();
						} else { //修改
							TtVsNvdrPO updateTvNvdrPo = TtVsNvdrPO.findFirst("VIN = ? ", vin);
							updateTvNvdrPo.setTimestamp("DELIVERY_DATE", deliveryDate);//交付时间
							updateTvNvdrPo.setLong("BUSINESS_ID", loginInfo.getDealerId());//经销商ID
							updateTvNvdrPo.setInteger("NVDR_STATUS", OemDictCodeConstants.VEHICLE_RETAIL_STATUS_02);//零售上报状态    NVDR状态
							updateTvNvdrPo.setDate("CREATE_DATE", CommonUtils.currentDateTime());
							updateTvNvdrPo.setLong("CREATE_BY", loginInfo.getUserId());
							updateTvNvdrPo.setLong("REPORT_TYPE", OemDictCodeConstants.RETAIL_REPORT_TYPE_01);//零售上报类型
							updateTvNvdrPo.setLong("STATUS",OemDictCodeConstants.VEHICLE_REPORT_STATUS_02);//交车上报状态
							updateTvNvdrPo.setInteger("IS_DEL", 0); //有效
							updateTvNvdrPo.saveIt();
						}
					} 
				}else if(type.equals("1")){				
					String vin = tvnDto.getVin();;//底盘号
					Long saleType = tvnDto.getSaleType();//销售类型
					Date deliveryDateTime = tvnDto.getDeliveryDate();//交付时间
					String deliveryDate = DateUtil.formatDefaultDateTimes(deliveryDateTime);
					Date registrationDateTime = tvnDto.getRegistrationDate();//注册时间
					String registrationDate = DateUtil.formatDefaultDateTimes(registrationDateTime);
					Long dealerId = loginInfo.getDealerId();//经销商ID
					String title = tvnDto.getTitle();//头衔
					String country = tvnDto.getCountry();//国家
					String customerLanguage = tvnDto.getCustomerLanguage();//语言
					String isContact = tvnDto.getIsContact();//是否可以联系
					String nvdrId="";
					List<TtVsNvdrPO> list = TtVsNvdrPO.find("VIN = ? ", vin);
					int listSize = list.size();
					if(listSize==0){
						TtVsNvdrPO tvNvdrPo = new TtVsNvdrPO();
						tvNvdrPo.setString("VIN", vin);//底盘号
						tvNvdrPo.setLong("SALE_TYPE", saleType);//销售类型
						tvNvdrPo.setTimestamp("DELIVERY_DATE", deliveryDate);//交付时间
						tvNvdrPo.setTimestamp("REGISTRATION_DATE", registrationDateTime);//注册时间
						tvNvdrPo.setLong("BUSINESS_ID", dealerId);//经销商ID
						tvNvdrPo.setString("TITLE",title);//头衔
						tvNvdrPo.setString("COUNTRY",country);//国家
						tvNvdrPo.setString("CUSTOMER_LANGUAGE",customerLanguage);//语言
						tvNvdrPo.setString("IS_CONTACT",isContact);//是否可联系
						tvNvdrPo.setInteger("NVDR_STATUS",(OemDictCodeConstants.VEHICLE_RETAIL_STATUS_02));//零售上报状态    NVDR状态
						tvNvdrPo.setDate("CREATE_DATE",CommonUtils.currentDateTime());
						tvNvdrPo.setLong("CREATE_BY",loginInfo.getUserId());
						tvNvdrPo.setInteger("REPORT_TYPE",OemDictCodeConstants.RETAIL_REPORT_TYPE_01);//零售上报类型
						tvNvdrPo.setInteger("STATUS",OemDictCodeConstants.VEHICLE_REPORT_STATUS_02);//交车上报状态
						tvNvdrPo.insert();
						nvdrId = tvNvdrPo.getString("NVDR_ID");   
					} else { //修改
						TtVsNvdrPO tvnPO = TtVsNvdrPO.findFirst("VIN =? ", vin);
						tvnPO.setLong("SALE_TYPE", saleType);//销售类型
						tvnPO.setTimestamp("DELIVERY_DATE", deliveryDateTime);//交付时间
						tvnPO.setTimestamp("REGISTRATION_DATE", registrationDateTime);//注册时间
						tvnPO.setLong("BUSINESS_ID", dealerId);//经销商ID
						tvnPO.setString("TITLE",title);//头衔
						tvnPO.setString("COUNTRY",country);//国家
						tvnPO.setString("CUSTOMER_LANGUAGE",customerLanguage);//语言
						tvnPO.setString("IS_CONTACT",isContact);//是否可联系
						tvnPO.setInteger("NVDR_STATUS",(OemDictCodeConstants.VEHICLE_RETAIL_STATUS_02));//零售上报状态    NVDR状态
						tvnPO.setDate("CREATE_DATE",CommonUtils.currentDateTime());
						tvnPO.setLong("CREATE_BY",loginInfo.getUserId());
						tvnPO.setInteger("REPORT_TYPE",OemDictCodeConstants.RETAIL_REPORT_TYPE_01);//零售上报类型
						tvnPO.setInteger("STATUS",OemDictCodeConstants.VEHICLE_REPORT_STATUS_02);//交车上报状态
						tvnPO.setInteger("IS_DEL", 0);	//有效
						tvnPO.saveIt();
						nvdrId =tvnPO.getString("NVDR_ID");
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			ServiceBizException e1 = new ServiceBizException(e);
			logger.error(loginInfo.toString(), e1);
			throw new ServiceBizException("上报失败!");
		}
	}

}
