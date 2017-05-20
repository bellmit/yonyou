package com.yonyou.dms.vehicle.service.allot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.Util.DomManager;
import com.yonyou.dms.common.Util.QueryConditionBean;
import com.yonyou.dms.common.domains.PO.basedata.TmAllotGrandPO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.allot.ResourceAllotAuditDao;

@SuppressWarnings({"rawtypes",("unchecked")})
@Service
public class ResourceAllotAuditServiceImpl implements ResourceAllotAuditService {
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceAllotAuditServiceImpl.class);
	
	@Autowired
	ResourceAllotAuditDao dao;

	
	@Override
	public List<Map> getArea() throws ServiceBizException {
		return dao.getArea2();
	}

	@Override
	public List<Map> getSeries() throws ServiceBizException {
		return dao.getSeries();
	}

	@Override
	public List<Map> getAllotDate(String string) throws ServiceBizException {
		return dao.getAllotDate2(string);
	}
	
	/**
	 * 审核查询
	 */
	@Override
	public Map<String, Object> findAll(Map<String, String> queryParam) throws ServiceBizException {
		Map<String, Object> resultMap = new HashMap<>();
		try {		
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			String actionType = CommonUtils.checkNull(queryParam.get("actionType"));
			
			String groupId = CommonUtils.checkNull(queryParam.get("groupId"));
			String parOrgId=CommonUtils.checkNull(queryParam.get("parOrgId"));
			String orgId=CommonUtils.checkNull(queryParam.get("TOR.ORG_ID"));
			String allotDate=CommonUtils.checkNull(queryParam.get("allotDate"));	
			String auditType=CommonUtils.checkNull(queryParam.get("auditType"));
			
			String grand30="0";
			String grand36="0";
			List<TmAllotGrandPO> taList = TmAllotGrandPO.findAll(); 
			for(int i=0;i<taList.size();i++){
				TmAllotGrandPO PO = taList.get(i);
				if(PO.getInteger("GRAND_TYPE")==30){
					grand30 = CommonUtils.checkNull(PO.getLong("GRAND_ID"));
				}
				if(PO.getInteger("GRAND_TYPE")==36){
					grand36 = CommonUtils.checkNull(PO.getLong("GRAND_ID"));
				}
			}
			if(groupId.equals(grand36)){
				groupId=grand30+","+grand36;
			}
			
			List<QueryConditionBean> param = new ArrayList<QueryConditionBean>();			
			param.add(new QueryConditionBean("tvmg2.GROUP_ID", "in", groupId));
			param.add(new QueryConditionBean("tor.PARENT_ORG_ID", "=", parOrgId));
			param.add(new QueryConditionBean("tor.ORG_ID", "=", orgId));
			allotDate=allotDate.replaceAll("-","");
			param.add(new QueryConditionBean("tar.ALLOT_DATE", "=", allotDate));
			String allotMonthDate=allotDate.substring(0, allotDate.length()-2);
			String conditionWhere = DomManager.getConditionsWhere(param);
		
			List<Map> list = dao.findResourceAllotList(conditionWhere,auditType,loginInfo);
			List<Map> sList = new ArrayList<Map>();	
			List<Map> gapList = new ArrayList<Map>();			
			List<Map> serGroup = new ArrayList<Map>();
			List<Map> colorGroup = new ArrayList<Map>();	
			List<Map> sList_temp = new ArrayList<Map>();
			if(list.size()>0){
				sList_temp = dao.findHasNumListBySeries(groupId,allotDate,parOrgId,orgId,auditType,loginInfo);
				gapList = dao.findTotalGapListBySeries(groupId,parOrgId,orgId,allotDate,allotMonthDate,auditType,loginInfo);
				serGroup = dao.findResourceAllotListBySeries(conditionWhere,auditType,loginInfo);
				colorGroup = dao.findResourceAllotListByColor(conditionWhere,auditType,loginInfo);
				int total=0;//
			    List<Map> list30=new ArrayList<Map>();
			    List<Map> list36=new ArrayList<Map>();
			    List<Map> tList30=new ArrayList<Map>();
			    List<Map> otList30=new ArrayList<Map>();
			    List<Map> tList36=new ArrayList<Map>();
			    List<Map> otList36=new ArrayList<Map>();
			    List<Map<String, Object>> gapList_30 = new ArrayList<Map<String, Object>>();
				for(int i=0;i<gapList.size();i++){
					Map<String, Object> bean = gapList.get(i);	
					if(bean.get("SERIES_ID").toString().equals(grand30)){
						gapList_30.add(bean);
						Map map30=new HashMap();
						Map tMap30=new HashMap();
						Map otMap30=new HashMap();
						if(!(bean.get("SERIES_NAME").toString().equals("TOTAL"))&&!(bean.get("SERIES_NAME").toString().equals("ORG_TOTAL"))){															
							map30.put("ALLOT_NUM", Integer.parseInt(CommonUtils.checkNull(bean.get("ALLOT_NUM"))));
							map30.put("ORG_ID", bean.get("ORG_ID"));
							map30.put("DEALER_ID", bean.get("DEALER_ID"));
							map30.put("SERIES_NAME", bean.get("SERIES_NAME"));
							map30.put("ALLOT_MONTH_NUM", bean.get("ALLOT_MONTH_NUM"));
							list30.add(map30);
						}else if(bean.get("SERIES_NAME").toString().equals("TOTAL")){
							tMap30.put("ORG_ID", bean.get("ORG_ID"));
							tMap30.put("DEALER_ID", bean.get("DEALER_ID"));
							tMap30.put("SERIES_NAME", bean.get("SERIES_NAME"));
							tMap30.put("ALLOT_MONTH_NUM", Integer.parseInt(CommonUtils.checkNull(bean.get("ALLOT_MONTH_NUM"))));
							tMap30.put("ALLOT_NUM", Integer.parseInt(CommonUtils.checkNull(bean.get("ALLOT_NUM"))));
							tList30.add(tMap30);
						}else if(bean.get("SERIES_NAME").toString().equals("ORG_TOTAL")){
							otMap30.put("ORG_ID", bean.get("ORG_ID"));
							otMap30.put("DEALER_ID", bean.get("DEALER_ID"));
							otMap30.put("SERIES_NAME", bean.get("SERIES_NAME"));
							otMap30.put("ALLOT_MONTH_NUM", Integer.parseInt(CommonUtils.checkNull(bean.get("ALLOT_MONTH_NUM"))));
							otMap30.put("ALLOT_NUM", Integer.parseInt(CommonUtils.checkNull(bean.get("ALLOT_NUM"))));
							otList30.add(otMap30);
						}
					}
					if(bean.get("SERIES_ID").toString().equals(grand36)){
						Map map36=new HashMap();
						Map tMap36=new HashMap();
						Map otMap36=new HashMap();
						if(!(bean.get("SERIES_NAME").toString().equals("TOTAL"))&&!(bean.get("SERIES_NAME").toString().equals("ORG_TOTAL"))){															
							map36.put("ALLOT_NUM", Integer.parseInt(CommonUtils.checkNull(bean.get("ALLOT_NUM"))));
							map36.put("ORG_ID", bean.get("ORG_ID"));
							map36.put("DEALER_ID", bean.get("DEALER_ID"));
							map36.put("SERIES_NAME", bean.get("SERIES_NAME"));
							map36.put("ALLOT_MONTH_NUM", bean.get("ALLOT_MONTH_NUM"));
							list36.add(map36);
						}else if(bean.get("SERIES_NAME").toString().equals("TOTAL")){
							tMap36.put("ORG_ID", bean.get("ORG_ID"));
							tMap36.put("DEALER_ID", bean.get("DEALER_ID"));
							tMap36.put("SERIES_NAME", bean.get("SERIES_NAME"));
							tMap36.put("ALLOT_MONTH_NUM", Integer.parseInt(CommonUtils.checkNull(bean.get("ALLOT_MONTH_NUM"))));
							tMap36.put("ALLOT_NUM", Integer.parseInt(CommonUtils.checkNull(bean.get("ALLOT_NUM"))));
							tList36.add(tMap36);
						}else if(bean.get("SERIES_NAME").toString().equals("ORG_TOTAL")){
							otMap36.put("ORG_ID", bean.get("ORG_ID"));
							otMap36.put("DEALER_ID", bean.get("DEALER_ID"));
							otMap36.put("SERIES_NAME", bean.get("SERIES_NAME"));
							otMap36.put("ALLOT_MONTH_NUM", Integer.parseInt(CommonUtils.checkNull(bean.get("ALLOT_MONTH_NUM"))));
							otMap36.put("ALLOT_NUM", Integer.parseInt(CommonUtils.checkNull(bean.get("ALLOT_NUM"))));
							otList36.add(otMap36);
						}
					}				
					
				}
				for(int i=0;i<otList30.size();i++){
					Map map30=otList30.get(i);
					for(int k=0;k<otList36.size();k++){
						Map map36=otList36.get(k);
						if(map30.get("ORG_ID").toString().equals(map36.get("ORG_ID").toString())){
							total=Integer.parseInt(map30.get("ALLOT_MONTH_NUM").toString())+Integer.parseInt(map36.get("ALLOT_MONTH_NUM").toString());
							for(int j=0;j<gapList.size();j++){
								Map<String, Object> bean1 = gapList.get(j);
								if(bean1.get("SERIES_ID").toString().equals(grand36)&&map36.get("ORG_ID").toString().equals(bean1.get("ORG_ID").toString())
										&&bean1.get("DEALER_ID").toString().equals(map36.get("DEALER_ID").toString())
										&&!(map36.get("ORG_ID").toString().equals("0"))&&map36.get("DEALER_ID").toString().equals("0")){																					
									bean1.put("ALLOT_MONTH_NUM", total);	
									bean1.put("ALLOT_NUM", (Integer.parseInt(map30.get("ALLOT_NUM").toString())+Integer.parseInt(map36.get("ALLOT_NUM").toString())));

								if(bean1.get("SERIES_ID").toString().equals(grand30)){
									bean1.remove("SERIES_ID");
								}
								}
							}
						}
					}
				}
				
				
				for(int i=0;i<list30.size();i++){
					Map map30=list30.get(i);
					for(int k=0;k<list36.size();k++){
						Map map36=list36.get(k);
						if(map30.get("ORG_ID").toString().equals(map36.get("ORG_ID").toString())&&map30.get("DEALER_ID").toString().equals(map36.get("DEALER_ID").toString())){
							total=Integer.parseInt(map30.get("ALLOT_MONTH_NUM").toString())+Integer.parseInt(map36.get("ALLOT_MONTH_NUM").toString());
							for(int j=0;j<gapList.size();j++){
								Map<String, Object> bean1 = gapList.get(j);
								if(bean1.get("SERIES_ID").toString().equals(grand36)&&map36.get("ORG_ID").toString().equals(bean1.get("ORG_ID").toString())
								&&map36.get("DEALER_ID").toString().equals(bean1.get("DEALER_ID").toString())
								        &&!(bean1.get("ORG_ID").toString().equals("0"))&&!(bean1.get("DEALER_ID").toString().equals("0"))){																					
									bean1.put("ALLOT_MONTH_NUM", total);	
									bean1.put("ALLOT_NUM", (Integer.parseInt(map30.get("ALLOT_NUM").toString())+Integer.parseInt(map36.get("ALLOT_NUM").toString())));

								if(bean1.get("SERIES_ID").toString().equals(grand30)){
									bean1.remove("SERIES_ID");
								}
								}
							}
						}
					}
				}

				for(int i=0;i<tList30.size();i++){
					Map tMap30=tList30.get(i);
					for(int k=0;k<tList36.size();k++){
						Map tMap36=tList36.get(k);
						if(tMap30.get("ORG_ID").toString().equals(tMap36.get("ORG_ID").toString())){
							total=Integer.parseInt(tMap30.get("ALLOT_MONTH_NUM").toString())+Integer.parseInt(tMap36.get("ALLOT_MONTH_NUM").toString());
							for(int j=0;j<gapList.size();j++){
								Map<String, Object> bean1 = gapList.get(j);
								if(bean1.get("SERIES_ID").toString().equals(grand36)&&tMap36.get("ORG_ID").toString().equals(bean1.get("ORG_ID").toString())&&(tMap36.get("ORG_ID").toString().equals("0"))){
										bean1.put("ALLOT_MONTH_NUM", total);
										bean1.put("ALLOT_NUM", (Integer.parseInt(tMap30.get("ALLOT_NUM").toString())+Integer.parseInt(tMap36.get("ALLOT_NUM").toString())));
								if(bean1.get("SERIES_ID").toString().equals(grand30)){
										bean1.remove("SERIES_ID");
									}
								}
							}
						}
					}
				}
				
				//将大切诺基3.0的数据加到3.6上
				for(int i=0;i<gapList.size();i++){
					Map<String, Object> bean36 = gapList.get(i);
					if(bean36.get("SERIES_ID").toString().equals(grand36)){
//						int rateNum = 0;
						for(int j=0;j<gapList.size();j++){
							Map<String, Object> bean30 = gapList.get(j);
							if(bean30.get("SERIES_ID").toString().equals(grand30)&&bean30.get("ORG_ID").toString().equals(bean36.get("ORG_ID").toString())&&bean30.get("DEALER_ID").toString().equals(bean36.get("DEALER_ID").toString())){
								gapList.get(i).put("NUM1", new Integer(bean36.get("NUM1").toString())+new Integer(bean30.get("NUM1").toString()));
								gapList.get(i).put("NUM2", new Integer(bean36.get("NUM2").toString())+new Integer(bean30.get("NUM2").toString()));
								gapList.get(i).put("NUM3", new Integer(bean36.get("NUM3").toString())+new Integer(bean30.get("NUM3").toString()));
								gapList.get(i).put("NUM4", new Integer(bean36.get("NUM4").toString())+new Integer(bean30.get("NUM4").toString()));
								gapList.get(i).put("NUM5", new Integer(bean36.get("NUM5").toString())+new Integer(bean30.get("NUM5").toString()));
								//gapList.get(i).put("ALLOT_NUM", new Integer(bean36.get("ALLOT_NUM").toString())+new Integer(bean30.get("ALLOT_NUM").toString()));
							}
						}
						//大切诺基-资源满足率
						/*rateNum+=new Integer(bean36.get("NUM1").toString())+new Integer(bean36.get("NUM2").toString())+new Integer(bean36.get("NUM3").toString())+new Integer(bean36.get("NUM33").toString())+new Integer(bean36.get("NUM4").toString())+new Integer(bean36.get("NUM5").toString())+new Integer(bean36.get("ALLOT_NUM").toString());
						gapList.get(i).put("RATE",String.format("%.0f",rateNum*100/new Float(bean36.get("SALE_AMOUNT").toString())));
						 */
					}
				}
			}
			//资源满足率
			for(int i=0;i<gapList.size();i++){
				Map bean=gapList.get(i);
				if(bean.get("SALE_AMOUNT").toString().equals("0")){
					gapList.get(i).put("RATE",0);			
				}else{					
					float num = new Float(bean.get("NUM1").toString())+new Float(bean.get("NUM2").toString())+new Float(bean.get("NUM3").toString())+new Float(bean.get("NUM33").toString())+new Float(bean.get("NUM4").toString())+new Float(bean.get("NUM5").toString())+new Float(bean.get("ALLOT_NUM").toString());
					gapList.get(i).put("RATE", String.format("%.0f",num*100/new Float(bean.get("SALE_AMOUNT").toString())));
				}			
			}
			//大切诺基-分配比例
			for(int i=0;i<serGroup.size();i++){
				Map<String, Object> bean36 = serGroup.get(i);
				if(bean36.get("SERIES_ID").toString().equals(grand36)){
					for(int j=0;j<serGroup.size();j++){
						Map<String, Object> bean30 = serGroup.get(j);
						if(bean30.get("SERIES_ID").toString().equals(grand30)){
							serGroup.get(i).put("SER_NUM", new Integer(bean36.get("SER_NUM").toString())+new Integer(bean30.get("SER_NUM").toString()));
						}
					}
				}
			}
			
		
			List<Map<String, Object>> serGroup_30 = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> colorGroup_30 = new ArrayList<Map<String, Object>>();
			
			DecimalFormat df = new DecimalFormat("");
//			Long startTime = System.currentTimeMillis();
			List orgTotalList = new ArrayList();
			List dlrTotalList = new ArrayList();	
			List orgTotalList2 = new ArrayList();
		/*	for(int i=0;i<gapList.size();i++){
				Map<String, Object> bean = gapList.get(i);	
				if(bean.get("SERIES_ID").toString().equals(grand30)){
					gapList_30.add(bean);
				}
			}*/
			for(int i=0;i<serGroup.size();i++){
				Map<String, Object> bean = serGroup.get(i);	
				if(bean.get("SERIES_ID").toString().equals(grand30)){
					serGroup_30.add(bean);
				}
			}
			for(int i=0;i<colorGroup.size();i++){
				Map<String, Object> bean = colorGroup.get(i);	
				if(bean.get("SERIES_ID").toString().equals(grand30)){
					colorGroup_30.add(bean);
				}
			}
			for(int i=0;i<gapList.size();i++){
				Map<String, Object> bean = gapList.get(i);	
				if(!orgTotalList2.contains(bean.get("ORG_ID2"))&&!bean.get("ORG_ID2").toString().equals("")){
					orgTotalList2.add(bean.get("ORG_ID2"));
				}
				
				if(!orgTotalList.contains(bean.get("ORG_NAME"))&&!bean.get("ORG_NAME").toString().equals("")){
					orgTotalList.add(bean.get("ORG_NAME"));
				}
				
				if(!dlrTotalList.contains(bean.get("DEALER_NAME"))&&!bean.get("DEALER_NAME").toString().equals("")){
					dlrTotalList.add(bean.get("DEALER_NAME"));
				}		
				for(int j=0;j<serGroup.size();j++){
					Map<String, Object> sBean = serGroup.get(j);		
					if(sBean.get("SERIES_ID").toString().equals(bean.get("SERIES_ID").toString())||bean.get("SERIES_NAME").toString().equals("ORG_TOTAL")){
						int allotNum=new Integer(gapList.get(i).get("ALLOT_NUM").toString());					
						int totalNum=0;
						if(bean.get("SERIES_NAME").toString().equals("ORG_TOTAL")){
							totalNum=new Integer(gapList.get(i).get("GAP").toString());
						}else{
							totalNum=new Integer(sBean.get("SER_NUM").toString());
						}
						if(totalNum>0){
							gapList.get(i).put("ALLOT_RATE", df.format(allotNum*100/totalNum)+"%");
						}else{
							gapList.get(i).put("ALLOT_RATE", "0%");
						}		
						break;
					}		
					if(sBean.get("SERIES_ID").toString().equals(bean.get("SERIES_ID").toString())||bean.get("SERIES_NAME").toString().equals("TOTAL")){
						int allotNum=new Integer(gapList.get(i).get("ALLOT_NUM").toString());					
						int totalNum=0;
						if(bean.get("SERIES_NAME").toString().equals("TOTAL")){
							totalNum=new Integer(gapList.get(i).get("GAP").toString());
						}else{
							totalNum=new Integer(sBean.get("SER_NUM").toString());
						}
						if(totalNum>0){
							gapList.get(i).put("ALLOT_RATE", df.format(allotNum*100/totalNum)+"%");
						}else{
							gapList.get(i).put("ALLOT_RATE", "0%");
						}		
						break;
					}	
				}
			}			
			List<Map> dqList = new ArrayList<Map>();
			List<Map> xqList = new ArrayList<Map>();
			List<Map> dlrList = new ArrayList<Map>();
			List<Map> gList = new ArrayList<Map>();
			List<Map> cList = new ArrayList<Map>();
			List<Map> cList2 = new ArrayList<Map>();
			List<Map> gList_30 = new ArrayList<Map>();
			List<Map> cList_30 = new ArrayList<Map>();
			List<Map> cList2_30 = new ArrayList<Map>();
			List<Map> cList_big = new ArrayList<Map>();
			List<Map> cList_big_30 = new ArrayList<Map>();
			for(int i=0;i<list.size();i++){
				Map bean = (Map) list.get(i);	
				Map dqMap = new HashMap();
				dqMap.put("SERIES_ID", bean.get("SERIES_ID"));
				dqMap.put("SERIES_NAME", bean.get("SERIES_NAME"));
				dqMap.put("ORG_ID2", bean.get("ORG_ID2"));
				dqMap.put("ORG_NAME2", bean.get("ORG_NAME2"));
				if(!dqList.contains(dqMap)){
					dqList.add(dqMap);
				}
				
				
				Map xqMap = new HashMap();
				xqMap.put("SERIES_ID", bean.get("SERIES_ID"));
				xqMap.put("SERIES_NAME", bean.get("SERIES_NAME"));
				xqMap.put("ORG_ID2", bean.get("ORG_ID2"));
				xqMap.put("ORG_ID", bean.get("ORG_ID"));
				xqMap.put("ORG_NAME", bean.get("ORG_NAME"));
				if(!xqList.contains(xqMap)){
					xqList.add(xqMap);
				}
				
				Map dlrMap = new HashMap();
				dlrMap.put("SERIES_ID", bean.get("SERIES_ID"));
				dlrMap.put("SERIES_NAME", bean.get("SERIES_NAME"));
				dlrMap.put("ORG_ID", bean.get("ORG_ID"));
				dlrMap.put("ORG_NAME", bean.get("ORG_NAME"));
				dlrMap.put("DEALER_NAME", bean.get("DEALER_NAME"));
				dlrMap.put("ORG_ID2", bean.get("ORG_ID2"));
				if(!dlrList.contains(dlrMap)){
					dlrList.add(dlrMap);
				}
				
				Map gMap = new HashMap();
				gMap.put("SERIES_ID", bean.get("SERIES_ID"));
				gMap.put("SERIES_NAME", bean.get("SERIES_NAME"));
				gMap.put("GROUP_ID", bean.get("GROUP_ID"));
				gMap.put("GROUP_NAME", bean.get("GROUP_NAME"));
				gMap.put("STANDARD_OPTION", bean.get("STANDARD_OPTION"));
				gMap.put("FACTORY_OPTIONS", bean.get("FACTORY_OPTIONS"));
				gMap.put("LOCAL_OPTION", bean.get("LOCAL_OPTION"));
				gMap.put("MODEL_YEAR", bean.get("MODEL_YEAR"));
				if(bean.get("SERIES_ID").toString().equals(grand30)){						
					if(!gList_30.contains(gMap)){
						gList_30.add(gMap);
					}	
				}else{
					if(!gList.contains(gMap)){
						gList.add(gMap);
					}
				}
				
				Map cMap = new HashMap();
				cMap.put("SERIES_ID", bean.get("SERIES_ID"));
				cMap.put("SERIES_NAME", bean.get("SERIES_NAME"));
				cMap.put("GROUP_ID", bean.get("GROUP_ID"));
				cMap.put("GROUP_NAME", bean.get("GROUP_NAME"));
				cMap.put("COLOR_NAME", bean.get("COLOR_NAME"));
				if(bean.get("SERIES_ID").toString().equals(grand30)){	
					if(!cList_30.contains(cMap)){
						cList_30.add(cMap);
					}	
				}else{
					if(!cList.contains(cMap)){
						cList.add(cMap);
					}
				}	
				
				Map cMap2 = new HashMap();
				cMap2.put("SERIES_ID", bean.get("SERIES_ID"));
				cMap2.put("SERIES_NAME", bean.get("SERIES_NAME"));
				cMap2.put("ORG_ID2", bean.get("ORG_ID2"));
				cMap2.put("ORG_ID", bean.get("ORG_ID"));
				cMap2.put("GROUP_ID", bean.get("GROUP_ID"));
				cMap2.put("GROUP_NAME", bean.get("GROUP_NAME"));
				cMap2.put("COLOR_NAME", bean.get("COLOR_NAME"));
				if(bean.get("SERIES_ID").toString().equals(grand30)){	
					if(!cList2_30.contains(cMap2)){
						cList2_30.add(cMap2);
					}	
				}else{
					if(!cList2.contains(cMap2)){
						cList2.add(cMap2);
					}
				}	
				Map cMap_big = new HashMap();
				cMap_big.put("SERIES_ID", bean.get("SERIES_ID"));
				cMap_big.put("SERIES_NAME", bean.get("SERIES_NAME"));
				cMap_big.put("ORG_ID2", bean.get("ORG_ID2"));
				cMap_big.put("GROUP_ID", bean.get("GROUP_ID"));
				cMap_big.put("GROUP_NAME", bean.get("GROUP_NAME"));
				cMap_big.put("COLOR_NAME", bean.get("COLOR_NAME"));
				if(bean.get("SERIES_ID").toString().equals(grand30)){						
					if(!cList_big_30.contains(cMap_big)){
						cList_big_30.add(cMap_big);
					}	
				}else{
					if(!cList_big.contains(cMap_big)){
						cList_big.add(cMap_big);
					}
				}	
			}
			List colorList = new ArrayList();
			for(int i=0;i<gList.size();i++){
				Map gMap = gList.get(i);
//				int colorNum = 0;
				for(int j=0;j<cList.size();j++){
					Map cMap = cList.get(j);
					if(cMap.get("SERIES_ID").toString().equals(gMap.get("SERIES_ID").toString())&&
							cMap.get("GROUP_ID").equals(gMap.get("GROUP_ID"))&&
							cMap.get("GROUP_NAME").equals(gMap.get("GROUP_NAME"))){
						if(!colorList.contains(cMap.get("COLOR_NAME"))){
							colorList.add(cMap.get("COLOR_NAME"));
						}
					}
				}
				gList.get(i).put("colorNum", colorList.size());
				colorList.clear();
			}
			//大切诺基3.0需要单独处理
			for(int i=0;i<gList_30.size();i++){
				Map gMap = gList_30.get(i);
//				int colorNum = 0;
				for(int j=0;j<cList_30.size();j++){
					Map cMap = cList_30.get(j);
					if(cMap.get("SERIES_NAME").toString().equals(gMap.get("SERIES_NAME").toString())&&
							cMap.get("GROUP_ID").equals(gMap.get("GROUP_ID"))&&
							cMap.get("GROUP_NAME").equals(gMap.get("GROUP_NAME"))){
						if(!colorList.contains(cMap.get("COLOR_NAME"))){
							colorList.add(cMap.get("COLOR_NAME"));
						}
					}
				}
				gList_30.get(i).put("colorNum", colorList.size());
				colorList.clear();
			}
			//小区汇总
			for(int i=0;i<cList2.size();i++){
				Map<String, Object> bean = cList2.get(i);	
				for(int j=0;j<colorGroup.size();j++){
					Map<String, Object> sBean = colorGroup.get(j);	
					if(sBean.get("SERIES_ID").toString().equals(bean.get("SERIES_ID").toString())&&
							sBean.get("SERIES_NAME").toString().equals("ORG_TOTAL")&&
							sBean.get("ORG_ID").toString().equals(bean.get("ORG_ID").toString())&&
							sBean.get("GROUP_ID").toString().equals(bean.get("GROUP_ID").toString())&&
							sBean.get("GROUP_NAME").equals(bean.get("GROUP_NAME"))&&
							sBean.get("COLOR_NAME").equals(bean.get("COLOR_NAME"))){
						cList2.get(i).put("SERIES_ORG_TOTAL","ORG_TOTAL");
						cList2.get(i).put("ORG_COLOR_TOTAL", sBean.get("COLOR_NUM"));
						break;
					}
				}
			}
			//大切诺基3.0需要单独处理
			for(int i=0;i<cList2_30.size();i++){
				Map<String, Object> bean = cList2_30.get(i);	
				for(int j=0;j<colorGroup_30.size();j++){
					Map<String, Object> sBean = colorGroup_30.get(j);	
					if(sBean.get("SERIES_ID").toString().equals(bean.get("SERIES_ID").toString())&&
							sBean.get("SERIES_NAME").toString().equals("ORG_TOTAL")&&
							sBean.get("ORG_ID").toString().equals(bean.get("ORG_ID").toString())&&
							sBean.get("GROUP_ID").toString().equals(bean.get("GROUP_ID").toString())&&
							sBean.get("GROUP_NAME").equals(bean.get("GROUP_NAME"))&&
							sBean.get("COLOR_NAME").equals(bean.get("COLOR_NAME"))){
						cList2_30.get(i).put("SERIES_ORG_TOTAL","ORG_TOTAL");
						cList2_30.get(i).put("ORG_COLOR_TOTAL", sBean.get("COLOR_NUM"));
						break;
					}
				}
			}
			//大区汇总
			for(int i=0;i<cList_big.size();i++){
				Map<String, Object> bean = cList_big.get(i);	
				for(int j=0;j<colorGroup.size();j++){
					Map<String, Object> sBean = colorGroup.get(j);	
					if(sBean.get("SERIES_ID").toString().equals(bean.get("SERIES_ID").toString())&&
							sBean.get("SERIES_NAME").toString().equals("BIG_ORG_TOTAL")&&
							sBean.get("ORG_ID2").toString().equals(bean.get("ORG_ID2").toString())&&
							sBean.get("GROUP_ID").toString().equals(bean.get("GROUP_ID").toString())&&
							sBean.get("GROUP_NAME").equals(bean.get("GROUP_NAME"))&&
							sBean.get("COLOR_NAME").equals(bean.get("COLOR_NAME"))){
						cList_big.get(i).put("SERIES_BIG_ORG_TOTAL","ORG_TOTAL");
						cList_big.get(i).put("ORG_COLOR_TOTAL", sBean.get("COLOR_NUM"));
						break;
					}
				}
			}
			//大切诺基3.0需要单独处理
			for(int i=0;i<cList_big_30.size();i++){
				Map<String, Object> bean = cList_big_30.get(i);	
				for(int j=0;j<colorGroup_30.size();j++){
					Map<String, Object> sBean = colorGroup_30.get(j);	
					if(sBean.get("SERIES_ID").toString().equals(bean.get("SERIES_ID").toString())&&
							sBean.get("SERIES_NAME").toString().equals("BIG_ORG_TOTAL")&&
							sBean.get("ORG_ID2").toString().equals(bean.get("ORG_ID2").toString())&&
							sBean.get("GROUP_ID").toString().equals(bean.get("GROUP_ID").toString())&&
							sBean.get("GROUP_NAME").equals(bean.get("GROUP_NAME"))&&
							sBean.get("COLOR_NAME").equals(bean.get("COLOR_NAME"))){
						cList_big_30.get(i).put("SERIES_BIG_ORG_TOTAL","ORG_TOTAL");
						cList_big_30.get(i).put("ORG_COLOR_TOTAL", sBean.get("COLOR_NUM"));
						break;
					}
				}
			}
			//车系汇总
			for(int i=0;i<cList.size();i++){
				Map<String, Object> bean = cList.get(i);	
				for(int j=0;j<colorGroup.size();j++){
					Map<String, Object> sBean = colorGroup.get(j);	
					if(sBean.get("SERIES_ID").toString().equals(bean.get("SERIES_ID").toString())&&
							sBean.get("SERIES_NAME").toString().equals("TOTAL")&&
							sBean.get("GROUP_ID").toString().equals(bean.get("GROUP_ID").toString())&&
							sBean.get("GROUP_NAME").equals(bean.get("GROUP_NAME"))&&
							sBean.get("COLOR_NAME").equals(bean.get("COLOR_NAME"))){
						cList.get(i).put("SERIES_TOTAL","TOTAL");
						cList.get(i).put("COLOR_TOTAL", sBean.get("COLOR_NUM"));
						break;
					}
				}
			}	
			//大切诺基3.0需要单独处理
			for(int i=0;i<cList_30.size();i++){
				Map<String, Object> bean = cList_30.get(i);	
				for(int j=0;j<colorGroup_30.size();j++){
					Map<String, Object> sBean = colorGroup_30.get(j);	
					if(sBean.get("SERIES_ID").toString().equals(bean.get("SERIES_ID").toString())&&
							sBean.get("SERIES_NAME").toString().equals("TOTAL")&&
							sBean.get("GROUP_ID").toString().equals(bean.get("GROUP_ID").toString())&&
							sBean.get("GROUP_NAME").equals(bean.get("GROUP_NAME"))&&
							sBean.get("COLOR_NAME").equals(bean.get("COLOR_NAME"))){
						cList_30.get(i).put("SERIES_TOTAL","TOTAL");
						cList_30.get(i).put("COLOR_TOTAL", sBean.get("COLOR_NUM"));
						break;
					}
				}
			}	
			int grand30Flag = 0;
			int grand36Flag = 0;
			for(int i=0;i<sList_temp.size();i++){
				Map<String, Object> bean = sList_temp.get(i);
				Map<String, Object> sDB = new HashMap<String, Object>();
				sDB.put("SERIES_ID", bean.get("SERIES_ID"));
				sDB.put("SERIES_CODE", bean.get("SERIES_CODE"));
				sDB.put("SERIES_NAME", bean.get("SERIES_NAME"));
				sDB.put("STATUS", bean.get("STATUS"));
				if(!bean.get("SERIES_ID").toString().equals(grand30)){						
					if(!sList.contains(sDB)){
						sList.add(sDB);
					}
				}
				if(bean.get("SERIES_ID").toString().equals(grand30)){
					grand30Flag = 1;
				}
				if(bean.get("SERIES_ID").toString().equals(grand36)){
					grand36Flag = 1;
				}
			}
			if(grand36Flag==0&&grand30Flag>0){
				Map<String, Object> sDB = new HashMap<String, Object>();
				sDB.put("SERIES_ID", grand30);
				sDB.put("SERIES_CODE", "");
				sDB.put("SERIES_NAME", "大切诺基");
				sList.add(sDB);
			}
			int totalList=orgTotalList2.size()+orgTotalList.size()+dlrTotalList.size()+1;
			resultMap.put("totalList", totalList);
			resultMap.put("sList", sList);
			resultMap.put("dqList", dqList);
			resultMap.put("xqList", xqList);
			resultMap.put("dlrList", dlrList);
			resultMap.put("gapList", gapList);
			resultMap.put("gList", gList);
			resultMap.put("cList", cList);
			resultMap.put("cList2", cList2);
			resultMap.put("serGroup", serGroup);
			resultMap.put("gList_30", gList_30);
			resultMap.put("cList_30", cList_30);
			resultMap.put("cList2_30", cList2_30);
			resultMap.put("grand30", grand30);
			resultMap.put("list", list);	
			resultMap.put("cList_big_30", cList_big_30);
			resultMap.put("cList_big", cList_big);
			resultMap.put("auditType", auditType);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error( e.toString());
			ServiceBizException e1 = new ServiceBizException("资源分配审核(销售总监)");
			throw e1;
		}
		return resultMap;
	}
	
	public Map<String, List<Map>> getExcelData(Map result){
		List<Map> sList = (List<Map>) result.get("sList");
		List<Map> dqList = (List<Map>) result.get("dqList");
		List<Map> xqList = (List<Map>) result.get("xqList");
		List<Map> dlrList = (List<Map>) result.get("dlrList");
		List<Map> gapList = (List<Map>) result.get("gapList");
		List<Map> gList = (List<Map>) result.get("gList");
		List<Map> cList = (List<Map>) result.get("cList");
		List<Map> cList2 = (List<Map>) result.get("cList2");
		List<Map> list = (List<Map>) result.get("list");
		List<Map> cList_big = (List<Map>) result.get("cList_big");
		
		Map<String, List<Map>> returnResult = new HashMap<String, List<Map>>();
		List<Map> mapList = null;
		for(int i=0;i<sList.size();i++){
			mapList = new ArrayList<Map>();
	    	Map<String, Object> sMap = sList.get(i);
	    	String seriesName = CommonUtils.checkNull(sMap.get("SERIES_NAME"));
	    	//设置每一个sheet名称
		    //数据
		    for(int x=0;x<dqList.size();x++){
		    	Map<String, Object> dqMap = dqList.get(x);
		    	if(dqMap.get("SERIES_ID").toString().equals(sMap.get("SERIES_ID").toString())){
		    	    for(int j=0;j<xqList.size();j++){
				    	Map<String, Object> xqMap = xqList.get(j);
				    	if(xqMap.get("SERIES_ID").toString().equals(dqMap.get("SERIES_ID").toString())&&
				    			xqMap.get("ORG_ID2").toString().equals(dqMap.get("ORG_ID2").toString())){
				    		for(int k=0;k<dlrList.size();k++){
				    			Map<String, Object> dlrMap = dlrList.get(k);
				    			if(dlrMap.get("ORG_ID2").toString().equals(xqMap.get("ORG_ID2").toString())&&
				    					dlrMap.get("ORG_NAME").equals(xqMap.get("ORG_NAME"))&&
				    					dlrMap.get("SERIES_ID").toString().equals(xqMap.get("SERIES_ID").toString())){
								    for(int p=0;p<gapList.size();p++){
								    	Map<String, Object> gapMap = gapList.get(p);
								    	if(gapMap.get("ORG_ID2").equals(dlrMap.get("ORG_ID2"))&&
								    			gapMap.get("ORG_NAME").equals(dlrMap.get("ORG_NAME"))&&
								    			gapMap.get("SERIES_ID").toString().equals(dlrMap.get("SERIES_ID").toString())&&
								    			gapMap.get("DEALER_NAME").equals(dlrMap.get("DEALER_NAME"))){
											Map dealerMap = gapMap;

										    //颜色数量
										    for(int t=0;t<gList.size();t++){
										    	Map<String, Object> gMap = gList.get(t);
										    	if(gMap.get("SERIES_ID").toString().equals(dlrMap.get("SERIES_ID").toString())){
										    		for(int q=0;q<cList.size();q++){
										    			Map<String, Object> cMap = cList.get(q);
										    			if(cMap.get("SERIES_ID").toString().equals(gMap.get("SERIES_ID").toString())&&
										    				cMap.get("GROUP_ID").toString().equals(gMap.get("GROUP_ID").toString())&&
									    					cMap.get("GROUP_NAME").toString().equals(gMap.get("GROUP_NAME").toString())&&
									    					!cMap.get("GROUP_ID").toString().equals("0")){
										    				String str = "0";
										    				for(int l=0;l<list.size();l++){
												    			Map<String, Object> rMap = list.get(l);
												    			if(rMap.get("SERIES_ID").toString().equals(dlrMap.get("SERIES_ID").toString())&&
											    					rMap.get("DEALER_NAME").equals(dlrMap.get("DEALER_NAME"))&&
											    					rMap.get("GROUP_ID").toString().equals(cMap.get("GROUP_ID").toString())&&
											    					rMap.get("GROUP_NAME").equals(cMap.get("GROUP_NAME"))&&
											    					rMap.get("COLOR_NAME").equals(cMap.get("COLOR_NAME"))){
												    				str =  rMap.get("NUM").toString();
												    				break;
												    			}
												    		}
										    				dealerMap.put(cMap.get("GROUP_ID")+"_"+cMap.get("COLOR_NAME"), str);
										    			}
										    		}
										    	}
										    }
										    mapList.add(dealerMap);
								    	}
								    }
								    							   
								    //大切诺基3.0需要单独处理
//								    for(int p=0;p<gList_30.size();p++){
//								    	Map<String, Object> gMap = gList_30.get(p);
//								    	if(gMap.get("SERIES_NAME").toString().equals(dlrMap.get("SERIES_NAME").toString())){
//								    		for(int q=0;q<cList_30.size();q++){
//								    			Map<String, Object> cMap = cList_30.get(q);
//								    			if(cMap.get("SERIES_NAME").toString().equals(gMap.get("SERIES_NAME").toString())&&
//								    					cMap.get("GROUP_ID").toString().equals(gMap.get("GROUP_ID").toString())&&
//								    					cMap.get("GROUP_NAME").toString().equals(gMap.get("GROUP_NAME").toString())&&
//								    					!cMap.get("GROUP_ID").toString().equals("0")){
//								    				int tempStr=0;
//								    				for(int l=0;l<list.size();l++){
//										    			Map<String, Object> rMap = list.get(l);
//										    			if(rMap.get("SERIES_NAME").toString().equals(dlrMap.get("SERIES_NAME").toString())&&
//										    					rMap.get("DEALER_NAME").equals(dlrMap.get("DEALER_NAME"))&&
//										    					rMap.get("GROUP_ID").toString().equals(cMap.get("GROUP_ID").toString())&&
//										    					rMap.get("GROUP_NAME").equals(cMap.get("GROUP_NAME"))&&
//										    					rMap.get("COLOR_NAME").equals(cMap.get("COLOR_NAME"))){
//										    				List gps = (List) hashMap.get(rMap.get("SERIES_ID").toString());
//										    				if(gps!=null&&gps.size()>0){
//								    							for(int cc=0;cc<gps.size();cc++){
//								    								if(gps.get(cc).toString().equals(rMap.get("GROUP_ID").toString())){
//								    									wsheet.addCell(new  Label(num++, n, rMap.get("NUM").toString(), wcf1));
//								    								}
//								    							}
//								    						}else{
//								    							wsheet.addCell(new  Label(num++, n, "0", wcf1));
//								    						}							    				
//								    						tempStr++;
//										    			}
//										    		}
//										    		if(tempStr==0){
//								    					wsheet.addCell(new  Label(num++, n, "0", wcf1));
//								    				}
//								    			}
//								    		}
//								    	}
//								    }	
				    			}
				    		}	
						   	//小区汇总	 
				    		Map smallOrgMap = null;
				    		for(int k=0;k<gapList.size();k++){
				    			Map<String, Object> gapMap = gapList.get(k);
				    			if(gapMap.get("SERIES_ID").toString().equals(sMap.get("SERIES_ID").toString())&&gapMap.get("ORG_ID2").toString().equals(xqMap.get("ORG_ID2").toString())&&gapMap.get("ORG_ID").toString().equals(xqMap.get("ORG_ID").toString())&&
				    					gapMap.get("SERIES_NAME").toString().equals("ORG_TOTAL")){
				    				gapMap.put("DEALER_NAME", xqMap.get("ORG_NAME"));
				    				smallOrgMap = gapMap;
				    			}
				    		}	    	
				    		//小区颜色汇总
				    		for(int k=0;k<gList.size();k++){
				    			Map<String, Object> gMap = gList.get(k);
				    			if(gMap.get("SERIES_ID").toString().equals(sMap.get("SERIES_ID").toString())){
				    				for(int p=0;p<cList2.size();p++){
				    					Map<String, Object> cMap = cList2.get(p);
				    					if(cMap.get("SERIES_ORG_TOTAL")!=null&&
				    							cMap.get("SERIES_ORG_TOTAL").equals("ORG_TOTAL")&&
				    							cMap.get("SERIES_ID").toString().equals(gMap.get("SERIES_ID").toString())&&
				    							cMap.get("ORG_ID2").toString().equals(xqMap.get("ORG_ID2").toString())&&
				    							cMap.get("ORG_ID").toString().equals(xqMap.get("ORG_ID").toString())&&
				    							cMap.get("GROUP_ID").toString().equals(gMap.get("GROUP_ID").toString())&&
				    							cMap.get("GROUP_NAME").toString().equals(gMap.get("GROUP_NAME"))){		
						    				smallOrgMap.put(cMap.get("GROUP_ID")+"_"+cMap.get("COLOR_NAME"), cMap.get("ORG_COLOR_TOTAL").toString());
				    					}
				    				}
				    			}
				    		}
				    		if(smallOrgMap!=null){
				    			mapList.add(smallOrgMap);
				    		}
				    		//大切诺基3.0需要单独处理
//				    		for(int k=0;k<gList_30.size();k++){
//				    			Map<String, Object> gMap = gList_30.get(k);
//				    			if(gMap.get("SERIES_NAME").toString().equals(sMap.get("SERIES_NAME").toString())){
//				    				for(int p=0;p<cList2_30.size();p++){
//				    					Map<String, Object> cMap = cList2_30.get(p);
//				    					if(cMap.get("SERIES_ORG_TOTAL")!=null&&cMap.get("SERIES_ORG_TOTAL").equals("ORG_TOTAL")&&cMap.get("SERIES_NAME").toString().equals(gMap.get("SERIES_NAME").toString())&&cMap.get("ORG_ID2").toString().equals(xqMap.get("ORG_ID2").toString())&&cMap.get("ORG_ID").toString().equals(xqMap.get("ORG_ID").toString())&&
//				    							cMap.get("GROUP_ID").toString().equals(gMap.get("GROUP_ID").toString())&&cMap.get("GROUP_NAME").equals(gMap.get("GROUP_NAME"))&&!cMap.get("GROUP_ID").toString().equals("0")){
//				    						wsheet.addCell(new  Label(num++, n, cMap.get("ORG_COLOR_TOTAL").toString(), wcf));
//				    						orgColorNum+=1;
//				    					}
//				    				}
//				    			}
//				    		}
				    	}
				    }
		    	    //大区汇总
		    	    Map bigOrgMap = null;
		    	    for(int j=0;j<gapList.size();j++){
				    	Map<String, Object> gapMap = gapList.get(j);
				    	if(gapMap.get("SERIES_ID").toString().equals(dqMap.get("SERIES_ID").toString())&&
				    			gapMap.get("ORG_ID2").toString().equals(dqMap.get("ORG_ID2").toString())&&
				    			gapMap.get("SERIES_NAME").toString().equals("BIG_ORG_TOTAL")){
				    		gapMap.put("DEALER_NAME", dqMap.get("ORG_NAME2"));
				    		bigOrgMap = gapMap;
				    	}
		    	    }
		    	    //大区颜色汇总
		    	    for(int j=0;j<gList.size();j++){
		    	    	Map<String, Object> gMap = gList.get(j);
		    	    	if(gMap.get("SERIES_ID").toString().equals(sMap.get("SERIES_ID").toString())){
		    	    		for(int k=0;k<cList_big.size();k++){
		    	    			Map<String, Object> cMap = cList_big.get(k);
		    	    			if(cMap.get("SERIES_BIG_ORG_TOTAL")!=null&&
		    	    					cMap.get("SERIES_BIG_ORG_TOTAL").toString().equals("ORG_TOTAL")&&
		    	    					cMap.get("SERIES_ID").toString().equals(gMap.get("SERIES_ID").toString())&&
		    	    					cMap.get("ORG_ID2").toString().equals(dqMap.get("ORG_ID2").toString())&&
		    	    					cMap.get("GROUP_ID").toString().equals(gMap.get("GROUP_ID").toString())&&
		    	    					cMap.get("GROUP_NAME").toString().equals(gMap.get("GROUP_NAME"))){
		    	    				bigOrgMap.put(cMap.get("GROUP_ID")+"_"+cMap.get("COLOR_NAME"), cMap.get("ORG_COLOR_TOTAL").toString());
		    	    			}
		    	    		}
		    	    		
		    	    	}
		    	    }
		    	    if(bigOrgMap!=null){
		    	    	mapList.add(bigOrgMap);
		    	    }
		    	    //大切诺基3.0需要单独处理
//		    	    for(int j=0;j<gList_30.size();j++){
//		    	    	Map<String, Object> gMap = gList_30.get(j);
//		    	    	if(gMap.get("SERIES_NAME").toString().equals(sMap.get("SERIES_NAME").toString())){
//		    	    		for(int k=0;k<cList_big_30.size();k++){
//		    	    			Map<String, Object> cMap = cList_big_30.get(k);
//		    	    			if(cMap.get("SERIES_BIG_ORG_TOTAL")!=null&&cMap.get("SERIES_BIG_ORG_TOTAL").toString().equals("ORG_TOTAL")&&cMap.get("SERIES_NAME").toString().equals(gMap.get("SERIES_NAME").toString())&&cMap.get("ORG_ID2").toString().equals(dqMap.get("ORG_ID2").toString())&&
//		    	    					cMap.get("GROUP_ID").toString().equals(gMap.get("GROUP_ID").toString())&&cMap.get("GROUP_NAME").equals(gMap.get("GROUP_NAME"))&&!cMap.get("GROUP_ID").toString().equals("0")){
//		    	    				wsheet.addCell(new  Label(num++, n, cMap.get("ORG_COLOR_TOTAL").toString(), wcf));
//		    	    			}
//		    	    		}
//		    	    	}
//		    	    }
		    	}
		    }
		    //汇总
		    Map totalMap = null;
		    for(int j=0;j<gapList.size();j++){
		    	Map<String, Object> gapMap = gapList.get(j);
		    	if(gapMap.get("SERIES_ID").toString().equals(sMap.get("SERIES_ID").toString())&&gapMap.get("SERIES_NAME").toString().equals("TOTAL")){
		    		gapMap.put("DEALER_NAME", "汇总");
		    		totalMap = gapMap;
		    		break;
		    	}
		    }
		    for(int j=0;j<gList.size();j++){
		    	Map<String, Object> gMap = gList.get(j);
		    	if(gMap.get("SERIES_ID").toString().equals(sMap.get("SERIES_ID").toString())){
		    		for(int k=0;k<cList.size();k++){
		    			Map<String, Object> cMap = cList.get(k);
			    		String str = "0";
		    			if(cMap.get("SERIES_TOTAL")!=null&&
		    					cMap.get("SERIES_TOTAL").toString().equals("TOTAL")&&
		    					cMap.get("SERIES_ID").toString().equals(gMap.get("SERIES_ID").toString())&&
		    					cMap.get("GROUP_ID").equals(gMap.get("GROUP_ID"))&&
		    					cMap.get("GROUP_NAME").equals(gMap.get("GROUP_NAME"))&&
		    					!cMap.get("GROUP_ID").toString().equals("0")){
		    				totalMap.put(cMap.get("GROUP_ID")+"_"+cMap.get("COLOR_NAME").toString(), cMap.get("COLOR_TOTAL").toString());
		    			}
		    		}
		    	}
		    }
		    if(totalMap!=null){
		    	mapList.add(totalMap);
		    }
		    returnResult.put(seriesName, mapList);
		    //大切诺基3.0需要单独处理
//		    for(int j=0;j<gList_30.size();j++){
//		    	Map<String, Object> gMap = gList_30.get(j);
//		    	if(gMap.get("SERIES_NAME").toString().equals(sMap.get("SERIES_NAME").toString())){
//		    		for(int k=0;k<cList_30.size();k++){
//		    			Map<String, Object> cMap = cList_30.get(k);
//		    			if(cMap.get("SERIES_TOTAL")!=null&&cMap.get("SERIES_TOTAL").toString().equals("TOTAL")&&cMap.get("SERIES_NAME").toString().equals(gMap.get("SERIES_NAME").toString())&&
//		    					cMap.get("GROUP_ID").equals(gMap.get("GROUP_ID"))&&cMap.get("GROUP_NAME").equals(gMap.get("GROUP_NAME"))&&!cMap.get("GROUP_ID").toString().equals("0")){
//		    				wsheet.addCell(new  Label(num++, n, cMap.get("COLOR_TOTAL").toString(), wcf));
//		    			}
//		    		}	
//		    	}
//		    }
	    }
		
		return returnResult;
	}
	
	public Map<String, List<ExcelExportColumn>> getColumnData(Map<String, Object> result){
		List<Map> sList = (List<Map>) result.get("sList");
		List<Map> gList = (List<Map>) result.get("gList");
		List<Map> cList = (List<Map>) result.get("cList");
		
		Map<String, List<ExcelExportColumn>> map = new HashMap<String, List<ExcelExportColumn>>();
		for(int i=0;i<sList.size();i++){
	    	Map<String, Object> sMap = sList.get(i);
	    	String seriesName = CommonUtils.checkNull(sMap.get("SERIES_NAME"));
	    	
	    	List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
			exportColumnList.add(new ExcelExportColumn("DEALER_NAME","区域"));
		    exportColumnList.add(new ExcelExportColumn("SALE_AMOUNT","批售目标"));
		    exportColumnList.add(new ExcelExportColumn("NUM1","月初结转"));
		    exportColumnList.add(new ExcelExportColumn("NUM2","全国池已定"));
		    exportColumnList.add(new ExcelExportColumn("NUM22","全国池未定"));
		    exportColumnList.add(new ExcelExportColumn("NUM3","区域池已定"));
		    exportColumnList.add(new ExcelExportColumn("NUM33","区域池未定"));
		    exportColumnList.add(new ExcelExportColumn("NUM4","指派资源"));
		    exportColumnList.add(new ExcelExportColumn("NUM5","期货订单(一次开票)"));
		    exportColumnList.add(new ExcelExportColumn("GAP","当前Gap"));
		    exportColumnList.add(new ExcelExportColumn("ALLOT_NUM","本次分配"));
		    exportColumnList.add(new ExcelExportColumn("ALLOT_MONTH_NUM","本月分配"));
		    exportColumnList.add(new ExcelExportColumn("RATE","资源满足率(分配资源/批售目标)"));
		    
		    for(int j=0;j<gList.size();j++){
		    	Map<String, Object> gMap = gList.get(j);
		    	if(gMap.get("SERIES_ID").toString().equals(sMap.get("SERIES_ID").toString())){
		    		for(int k=0;k<cList.size();k++){
		    			Map<String, Object> cMap = cList.get(k);
		    			if(cMap.get("SERIES_TOTAL")!=null&&
		    					cMap.get("SERIES_TOTAL").toString().equals("TOTAL")&&
		    					cMap.get("SERIES_ID").toString().equals(gMap.get("SERIES_ID").toString())&&
		    					cMap.get("GROUP_ID").equals(gMap.get("GROUP_ID"))&&
		    					cMap.get("GROUP_NAME").equals(gMap.get("GROUP_NAME"))&&
		    					!cMap.get("GROUP_ID").toString().equals("0")){
		    				exportColumnList.add(new ExcelExportColumn(CommonUtils.checkNull(cMap.get("GROUP_ID"))+"_"+CommonUtils.checkNull(cMap.get("COLOR_NAME")),CommonUtils.checkNull(cMap.get("GROUP_NAME"))+CommonUtils.checkNull(cMap.get("COLOR_NAME"))));
		    			}
		    		}
		    	}
		    }	
		    map.put(seriesName, exportColumnList);
		}
		
		return map;
	}
	
}
