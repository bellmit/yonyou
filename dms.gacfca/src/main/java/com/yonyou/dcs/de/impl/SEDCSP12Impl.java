package com.yonyou.dcs.de.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.common.EAIConstant;
import com.infoeai.eai.po.TiPtPartBasePO;
import com.infoeai.eai.wsClient.parts.eai.P12ReturnVO;
import com.yonyou.dcs.de.SEDCSP12;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TtPtPartBaseDcsPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PartCommonDao;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class SEDCSP12Impl  implements SEDCSP12 {
	private static final Logger logger = LoggerFactory.getLogger(SEDCSP12Impl.class);
	Calendar sysdate = Calendar.getInstance();
	@Autowired
	PartCommonDao dao ;
	
	@Override
	public String dealerDCSDatdaBySap(List<P12ReturnVO> list) throws Exception {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		logger.info("====SEDCSP12 is begin====");
		
		try {
			for(int i=0;i<list.size();i++) {
				P12ReturnVO vo = list.get(i);
				//对于接受的数据  进行处理
				TtPtPartBaseDcsPO po = new TtPtPartBaseDcsPO();
				String partCode = vo.getMatnr();//配件code
				String sql="SELECT * FROM TI_REPAIR_ORDER_DCS WHERE PART_CODE = ? ";
				List <Object> params= new ArrayList<Object>();
				params.add(partCode);
				List<Map> listUpdatePo=OemDAOUtil.findAll(sql, params);//查看配件是否存在
				
				//新增
				po.setString("PART_CODE",partCode);//配件代码
				po.setString("PART_NAME",vo.getMaktx());//配件名称
				po.setInteger("PACKAGE_NUM",vo.getVormg().intValue());//包装数量
				
				//修改   更新结果
				StringBuffer upSqlV=new StringBuffer();
				//更新条件
				StringBuffer upSqlC=new StringBuffer();
				//参数
				List<Object> uparams = new ArrayList<Object>();
			
				upSqlV.append("PART_CODE = ?");
				upSqlV.append("PART_NAME = ?");
				upSqlV.append("PACKAGE_NUM = ?");
				params.add(partCode);//配件代码
				params.add(vo.getMaktx());//配件名称
				params.add(vo.getVormg().intValue());//包装数量
				
				if (null == vo.getMsehl() || "".equals(vo.getMsehl())) {
					po.setString("PART_NUIT","个");//单位
					
					upSqlV.append("PART_NUIT = ?");
					params.add("个");//单位
				} else {
					po.setString("PART_NUIT",vo.getMsehl());//单位
					
					upSqlV.append("PART_NUIT = ?");
					params.add(vo.getMsehl());//单位
				}
				// 价格不为空判断
				double price = Utility.getDouble(vo.getKbetr()+"");// MSRP=单价/定价数量 小数点后2位四舍五入
				if(vo.getKbetr()!= null && vo.getKpein()!= null && !vo.getKpein().toString().equals("0")){
					price = Utility.div(vo.getKbetr()+"", vo.getKpein()+"");
				}
				po.setDouble("PART_PRICE",price);//单价MSRP
				po.setDouble("WBP",price);//单价wbp
				po.setDouble("DNP_PRICE",Utility.getDouble(vo.getZprice()+""));//dnp price
				po.setInteger("IS_MJV",OemDictCodeConstants.IF_TYPE_YES);//是
				po.setInteger("DOWN_STATUS",0);//未下发
				
				upSqlV.append("PART_PRICE = ?");
				upSqlV.append("WBP = ?");
				upSqlV.append("DNP_PRICE = ?");
				upSqlV.append("IS_MJV = ?");
				upSqlV.append("DOWN_STATUS = ?");
				params.add(price);//单价MSRP
				params.add(price);//单价wbp
				params.add(Utility.getDouble(vo.getZprice()+""));//dnp price
				params.add(OemDictCodeConstants.IF_TYPE_YES);//是
				params.add(0);//未下发
				
				if ("D".equals(vo.getZflag())) {//状态修改无效 标示位修改1 
					po.setInteger("PART_STATUS",OemDictCodeConstants.STATUS_DISABLE);//无效的
					po.setInteger("IS_DEL",1);
					
					upSqlV.append("PART_STATUS = ?");
					upSqlV.append("IS_DEL = ?");
					params.add(OemDictCodeConstants.STATUS_DISABLE);//无效的
					params.add(1);
				} else {
					po.setInteger("PART_STATUS",OemDictCodeConstants.STATUS_ENABLE);//有效的
					
					upSqlV.append("PART_STATUS = ?");
					params.add(OemDictCodeConstants.STATUS_ENABLE);//有效的
				}
				//ZFRM:菲跃件  其他：常规件
				if ("NORM".equals(vo.getMtpos())) {
					
					po.setInteger("PART_MDOEL",OemDictCodeConstants.PART_TYPE_01);
					po.setInteger("REPORT_TYPE",OemDictCodeConstants.PART_REPORT_METHOD_01);
					
					upSqlV.append("PART_MDOEL = ?");
					upSqlV.append("REPORT_TYPE = ?");
					params.add(OemDictCodeConstants.PART_TYPE_01);
					params.add(OemDictCodeConstants.PART_REPORT_METHOD_01);
				} else if("BANS".equals(vo.getMtpos())) {
					po.setInteger("PART_MDOEL",OemDictCodeConstants.PART_TYPE_02);
					po.setInteger("REPORT_TYPE",OemDictCodeConstants.PART_REPORT_METHOD_01);
					
					upSqlV.append("PART_MDOEL = ?");
					upSqlV.append("REPORT_TYPE = ?");
					params.add(OemDictCodeConstants.PART_TYPE_02);
					params.add(OemDictCodeConstants.PART_REPORT_METHOD_01);
				} else if ("ZFRM".equals(vo.getMtpos())) {
					po.setInteger("PART_MDOEL",OemDictCodeConstants.PART_TYPE_01);
					po.setInteger("REPORT_TYPE",OemDictCodeConstants.PART_REPORT_METHOD_02);
					
					upSqlV.append("PART_MDOEL = ?");
					upSqlV.append("REPORT_TYPE = ?");
					params.add(OemDictCodeConstants.PART_TYPE_01);
					params.add(OemDictCodeConstants.PART_REPORT_METHOD_02);
				} else if ("ZCFM".equals(vo.getMtpos())) {
					po.setInteger("PART_MDOEL",OemDictCodeConstants.PART_TYPE_06);
					po.setInteger("REPORT_TYPE",OemDictCodeConstants.PART_REPORT_METHOD_02);
					
					upSqlV.append("PART_MDOEL = ?");
					upSqlV.append("REPORT_TYPE = ?");
					params.add(OemDictCodeConstants.PART_TYPE_06);
					params.add(OemDictCodeConstants.PART_REPORT_METHOD_02);
				} else if("ZCOD".equals(vo.getMtpos())) {
					po.setInteger("PART_MDOEL",OemDictCodeConstants.PART_TYPE_06);
					po.setInteger("REPORT_TYPE",OemDictCodeConstants.PART_REPORT_METHOD_01);
					
					upSqlV.append("PART_MDOEL = ?");
					upSqlV.append("REPORT_TYPE = ?");
					params.add(OemDictCodeConstants.PART_TYPE_06);
					params.add(OemDictCodeConstants.PART_REPORT_METHOD_01);
				} else {
					po.setInteger("PART_MDOEL",OemDictCodeConstants.PART_TYPE_01);
					po.setInteger("REPORT_TYPE",OemDictCodeConstants.PART_REPORT_METHOD_01);
					
					upSqlV.append("PART_MDOEL = ?");
					upSqlV.append("REPORT_TYPE = ?");
					params.add(OemDictCodeConstants.PART_TYPE_01);
					params.add(OemDictCodeConstants.PART_REPORT_METHOD_01);
				}
				if (null != listUpdatePo && listUpdatePo.size() > 0) {
					if (!CommonUtils.isEmpty(partCode)) {//配件不为空的时候  修改配件基本数据
						upSqlV.append("UPDATE_BY = ?");
						upSqlV.append("UPDATE_DATE = ?");
						uparams.add(DEConstant.DE_UPDATE_BY);
						uparams.add(new Date(System.currentTimeMillis()));
						
						upSqlC.append("PART_CODE = ?");
						params.add(partCode);
						
						TtPtPartBaseDcsPO.update(upSqlV.toString(), upSqlC.toString(), uparams.toArray());
					}
				} else {
					po.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
					po.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
					po.setInteger("IS_MOP",OemDictCodeConstants.IF_TYPE_NO);//否
					po.insert();
				}
			}
			returnResult = EAIConstant.DEAL_SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally{
			logger.info("====SEDCSP12 is finish====");
		}
		return returnResult;
	}
	public String getSapData() {
		try {
			logger.info("====SEDCSP12 TI is begin====");
			com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_BindingStub stub = 
					new com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_BindingStub();
			com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_Service lmsService = 
					new com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_ServiceLocator();
			stub = (com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZSD_SPARE_PART_BindingStub)lmsService.getZSD_SPARE_PART();
			com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZrsdSpareDiff1[] diffs = stub.zsdSparePart(null);
			logger.info("----------------调用对方服务地址："+lmsService.getZSD_SPARE_PARTAddress()+"----------------");
			if (null != diffs){
				logger.info("====SEDCSP12 TI is length===="+diffs.length);
			}
			if (null != diffs && diffs.length > 0) {//对于接受的数据  进行处理
				logger.info("====SEDCSP12 TI is length===="+diffs.length);
				for (int i=0;i<diffs.length;i++) {
					com.infoeai.eai.wsClient.parts.SparePartListForPublish.ZrsdSpareDiff1 diff = diffs[i];
					TiPtPartBasePO po = new TiPtPartBasePO();
					po.setString("MANDT",diff.getMandt());
					po.setString("MATNR",diff.getMatnr());
					po.setString("VKORG",diff.getVkorg());
					po.setString("VTWEG",diff.getVtweg());
					po.setString("MAKTX",diff.getMaktx());
					po.setString("MATKL",diff.getMatkl());
					po.setString("MTPOS",diff.getMtpos());
					po.setString("MEINS",diff.getMeins());
					po.setString("MSEHL",diff.getMsehl());
					po.setString("VORMG",diff.getVormg().toString());
					po.setString("KBETR",diff.getKbetr().toString());
					po.setString("KONWA",diff.getKonwa());
					po.setString("KPEIN",diff.getKpein().toString());
					po.setString("KMEIN",diff.getKmein());
					po.setString("ZFLAG",diff.getZflag());
					po.setString("ZPRICE",diff.getZprice().toString());
					po.setString("CREATE_DATE",sysdate.getTime());
					
					po.insert();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}finally{
			logger.info("====SEDCSP12 TI is finish====");
		}
		return null;
	}
	public String getPartBaseTI(List<P12ReturnVO> list) {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		logger.info("====SEDCSP12 TI is begin====");
		try {
			for(int i=0;i<list.size();i++) {
				P12ReturnVO vo = list.get(i);
				//对于接受的数据  进行处理
				
				TiPtPartBasePO po = new TiPtPartBasePO();
				po.setString("MANDT",vo.getMandt());
				po.setString("MATNR",vo.getMatnr());
				po.setString("VKORG",vo.getVkorg());
				po.setString("VTWEG",vo.getVtweg());
				po.setString("MAKTX",vo.getMaktx());
				po.setString("MATKL",vo.getMatkl());
				po.setString("MTPOS",vo.getMtpos());
				po.setString("MEINS",vo.getMeins());
				po.setString("MSEHL",vo.getMsehl());
				po.setString("VORMG",vo.getVormg().toString());
				po.setString("KBETR",vo.getKbetr().toString());
				po.setString("KONWA",vo.getKonwa());
				po.setString("KPEIN",vo.getKpein().toString());
				po.setString("KMEIN",vo.getKmein());
				po.setString("ZFLAG",vo.getZflag());
				po.setString("ZPRICE",vo.getZprice().toString());
				po.setString("CREATE_DATE",sysdate.getTime());
				
				po.insert();
			}
			returnResult = EAIConstant.DEAL_SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			logger.info("====SEDCSP12 TI is finish====");
		}
		return returnResult;
	}

}
