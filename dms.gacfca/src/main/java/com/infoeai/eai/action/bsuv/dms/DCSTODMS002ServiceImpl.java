/**
 * 
 */
package com.infoeai.eai.action.bsuv.dms;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.bsuv.common.CommonBSUV;
import com.infoeai.eai.dao.bsuv.dms.BSUVDCSTODMSDAO;
import com.infoeai.eai.po.TiEcPotentialCustomerPO;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.DCSTODMS002Dto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.gacfca.DCSTODMS002Coud;

/**
 * @author Administrator
 **
 * 接口编号：DCSTODMS002				
 * 接口名称：官网订单潜客信息（推送DMS接口）				
 * 接口说明：DCS将潜客信息（有新增的和修改的信息）下发到DMS				
 * 传输方向：下发 DCS -> DMS				
 * 传输方式：DEMessage				
 * 传输频率：实时				
 * 传输上限：ALL				
 *
 */
@Service
public class DCSTODMS002ServiceImpl extends BaseService implements DCSTODMS002Service {
	private static final Logger logger = LoggerFactory.getLogger(DCSTODMS001ServiceImpl.class);
	@Autowired
    private  BSUVDCSTODMSDAO dao;
	
	@Autowired
    private  DCSTODMS002Coud dcstodms002;
	
	private Date startTime = new Date();	// 记录接口开始执行的时间
	private Integer dataSize = 0;	// 数据数量
	private Integer status = OemDictCodeConstants.IF_TYPE_NO;	// 默认失败
	private String exceptionMsg = "";	// 错误日志
	
	@Override
	public String handleExecute() throws Exception {
		logger.info("========== 官网订单潜客信息下发DMS：执行开始 ==========");
		//事务开启
		beginDbService();
		try {
			
			// 查询所有待下发或需重新下发的潜客信息数据
			List<Map> list = dao.getPotentialCustomerList();
			
			LinkedList<DCSTODMS002Dto> vos = new LinkedList<DCSTODMS002Dto>();
			
			if (null != list && list.size() > 0) {
				
				logger.info("========== 官网订单潜客信息下发DMS：待下发数据(" + list.size() + ")条 ==========");
				
				dataSize = list.size();	// 数据数量
				
				for (int i = 0; i < list.size(); i++) {
					String dms_code = (String) list.get(i).get("ENTITY_CODE");
					DCSTODMS002Dto vo = new DCSTODMS002Dto();
					vo.setEcOrderNo((String) list.get(i).get("EC_ORDER_NO"));			// 官网订单号
					vo.setEntityCode(dms_code);			// 经销商代码    [DMS端经销商代码]
					vo.setDealerName((String) list.get(i).get("DEALER_NAME"));			// 经销商名称
					vo.setCustomerName((String) list.get(i).get("CUSTOMER_NAME"));		// 客户名称
					vo.setIdCard((String) list.get(i).get("ID_CRAD"));					// 身份证
					vo.setTel((String) list.get(i).get("TEL"));						// 客户联系电话
					vo.setBrandCode((String) list.get(i).get("BRAND_CODE"));			// 意向品牌
					vo.setSeriesCode((String) list.get(i).get("SERIES_CODE"));			// 意向车系
					vo.setModelCode((String) list.get(i).get("MODEL_CODE"));			// 意向车型
					vo.setConfigCode((String) list.get(i).get("GROUP_CODE"));			// 意向车款
					vo.setModelYear((String) list.get(i).get("MODEL_YEAR"));			// 年款
					vo.setColorCode((String) list.get(i).get("COLOR_CODE"));			// 意向颜色
					vo.setTrimCode((String) list.get(i).get("TRIM_CODE"));				// 意向内饰
					vo.setRetailFinancial((String) list.get(i).get("RETAIL_FINANCIAL"));// 零售金融
					vo.setDepositAmount((Float) list.get(i).get("DEPOSI_AMOUNT"));	// 定金金额
					vo.setDepositDate((Date) list.get(i).get("DEPOSI_DATE"));		// 下定日期    [YYYY-MM-DD HH24:MI:SS]
					vo.setOperationFlag((Integer) list.get(i).get("OPERATION_FLAG"));	// 操作标识    [0：新增][1 ：修改]
					vo.setEcOrderType((Integer) list.get(i).get("EC_ORDER_TYPE"));		// 官网订单类型    [16181001：现货][16181002：期货]
					vos.add(vo);	// 添加新数据
					
//					List<String> dmsCodes = new ArrayList<String>();
//					dmsCodes.add(vos.get(0).get("DEALER_CODE"));
					
					// 将数据生成body体
//					HashMap<String, Serializable> body = DEUtil.assembleBody(vos);
					System.out.println("经销商："+dms_code);
					dcstodms002.getSADMS061(vos,dms_code);
					vos.remove(0);	// 移除上一条数据
					// 开启DE，下发指定经销商
//					DeUtility de = new DeUtility();
//					de.sendMsg("DCSTODMS002", dmsCodes, body);
					
					// 更新下发状态
					TiEcPotentialCustomerPO setPo = TiEcPotentialCustomerPO.findById((Long)list.get(i).get("ID"));
					setPo.setInteger("SEND_DMS",1);	// 发送DMS状态    [0：未发送][1：已发送][2：发送失败]
					setPo.setString("update_by",DEConstant.DE_UPDATE_BY);	// 更新人ID
					setPo.setDate("UPDATE_DATE",new Date());	// 更新日期
					setPo.saveIt();
						
					
					
				}
				logger.info("========== 官网订单潜客信息下发DMS：成功下发数据(" + list.size() + ")条 ==========");
			} else {
				logger.info("========== 官网订单潜客信息下发DMS：无数据下发 ==========");
			}
			
			status = OemDictCodeConstants.IF_TYPE_YES;	// 执行成功
			
			dbService.endTxn(true);	// 提交事务
			
		} catch (Exception e) {
			
			status = OemDictCodeConstants.IF_TYPE_NO;	// 执行失败
			
			exceptionMsg = CommonBSUV.getErrorInfoFromException(e);
			
			logger.error(e.getMessage(), e);	// 异常处理
			e.printStackTrace();
			dbService.endTxn(false);	// 回滚事务
			
			logger.info("========== 官网订单潜客信息下发DMS：执行失败 ==========");
			
		} finally {
			
			logger.info("========== 官网订单潜客信息下发DMS：执行结束 ==========");
			Base.detach();
			dbService.clean();	// 清理事务
			
			beginDbService();
			try {
				// 记录日志表
				CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "官网订单潜客信息接口：DCS->DMS", startTime, dataSize, status, exceptionMsg, null, null, new Date());
				dbService.endTxn(true);
			} catch (Exception e2) {
				dbService.endTxn(false);
				logger.info("================官网订单潜客信息接口：DCS->DMS=========");
			}finally {
				Base.detach();
				dbService.clean();
			}
			
			
		}
		
		return null;
	}

}
