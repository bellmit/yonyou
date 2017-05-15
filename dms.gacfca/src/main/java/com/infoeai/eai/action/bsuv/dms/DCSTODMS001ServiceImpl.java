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
import com.infoeai.eai.po.TiEcHitSinglePO;
import com.infoeai.eai.wsServer.bsuv.lms.DCSTODMS001VO;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.gacfca.DCSTODMS001;

/**
 * @author Administrator
 * **
 * 接口编号：DCSTODMS001				
 * 接口名称：官网订单线索撞单（推送DMS接口）				
 * 接口说明：DCS将线索撞单信息下发给DMS				
 * 传输方向：下发 DCS -> DMS				
 * 传输方式：DEMessage				
 * 传输频率：实时				
 * 传输上限：ALL				
 *
 *
 */
@Service
public class DCSTODMS001ServiceImpl extends BaseService implements DCSTODMS001Sercvice {
	private static final Logger logger = LoggerFactory.getLogger(DCSTODMS001ServiceImpl.class);
	@Autowired
    private  BSUVDCSTODMSDAO dao;
	
	@Autowired
    private  DCSTODMS001 dcstodms001;

	private Date startTime = new Date();	// 记录接口开始执行的时间
	@SuppressWarnings("unused")
	private Integer dataSize = 0;	// 数据数量
	private Integer status = OemDictCodeConstants.IF_TYPE_NO;	// 默认失败
	private String exceptionMsg = "";	// 错误日志
	
	@SuppressWarnings("rawtypes")
	@Override
	public String handleExecute() throws Exception {
	logger.info("========== 官网订单线索撞单数据下发DMS：执行开始 ==========");
		
	//事务开启
	beginDbService();
	int dataSize = 0;
		try {
			
			List<Map> list = dao.queryTiEcHitSingle();	// 查询待下发数据
			
			LinkedList<DCSTODMS001VO> vos = new LinkedList<DCSTODMS001VO>();
			
			if (null != list && list.size() > 0) {
				logger.info("========== 官网订单线索撞单数据下发DMS：待下发数据(" + list.size() + ")条 ==========");
				dataSize = list.size();	// 数据数量
				for(int i = 0; i < list.size(); i++) {
					String dms_code = (String) list.get(i).get("ENTITY_CODE");
					DCSTODMS001VO vo = new DCSTODMS001VO();
					vo.setEntityCode(dms_code);	// DMS端经销商代码
					vo.setTel((String) list.get(i).get("TEL"));	// 客户联系电话
					vos.add(vo);	// 添加新数据
					
					/*List<String> dmsCodes = new ArrayList<String>();
					dmsCodes.add(vos.get(0).getDealerCode());*/
					
					// 将数据生成body体
					//HashMap<String, Serializable> body = DEUtil.assembleBody(vos);
					
					// 开启DE，下发指定经销商
					dcstodms001.getDCSTODMS001(vos, dms_code);
					vos.remove(0);	// 移除上一条数据
//					DeUtility de = new DeUtility();
//					de.sendMsg("DCSTODMS001", dmsCodes, body);
					
					// 更新下发状态
					TiEcHitSinglePO setPo= TiEcHitSinglePO.findFirst(" id= ? and dealer_code= ? and tel= ? ", list.get(i).get("ID"),list.get(i).get("DEALER_CODE"),list.get(i).get("TEL"));
					setPo.setInteger("SEND_DMS",1);
					setPo.setString("UPDATE_BY",DEConstant.DE_UPDATE_BY);
					setPo.setDate("UPDATE_DATE",new Date());
					setPo.saveIt();
					
				}
				logger.info("========== 官网订单线索撞单数据下发DMS：成功下发数据(" + list.size() + ")条 ==========");
			} else {
				// 校验待下发数据为空则回滚事务
				dbService.endTxn(false);
				logger.info("========== 官网订单线索撞单数据下发DMS：无数据下发 ==========");
			}
			status = OemDictCodeConstants.IF_TYPE_YES;//执行成功
			dbService.endTxn(true);
		} catch (Exception e) {
			status = OemDictCodeConstants.IF_TYPE_NO;	// 执行失败
			exceptionMsg = CommonBSUV.getErrorInfoFromException(e);
			logger.error(e.getMessage(), e);	// 异常处理
			dbService.endTxn(false);	// 回滚事务
			logger.info("========== 官网订单线索撞单数据下发DMS：执行失败 ==========");
			throw new Exception(e);
		} finally {
			logger.info("========== 官网订单线索撞单数据下发DMS：执行结束 ==========");
			Base.detach();
			dbService.clean();	// 清理事务
			
			
			beginDbService();
			try {
				// 记录日志表
				CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "官网订单线索撞单接口：DCS->DMS", startTime, dataSize, status, exceptionMsg, null, null, new Date());
				dbService.endTxn(true);
			} catch (Exception e2) {
				dbService.endTxn(false);
				logger.info("================官网订单线索撞单接口：DCS->DMS=========");
			}finally {
				Base.detach();
				dbService.clean();
			}
		}
		
		
		
		return null;
	}

}
