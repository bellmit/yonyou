package com.infoeai.eai.action.ctcai;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.DTO.Ctcai2DcsZJXXDTO;
import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.common.parsetxt.CTCAIZJXXParse;
import com.infoeai.eai.dao.ctcai.SICommonDao;
import com.yonyou.dms.common.domains.PO.basedata.TiCtcaiFundInfoPO;

/**
 * FTP
 * CTCAI->DCS
 * ZJXX-CTCAI将资金信息发送给DCS
 * @author luoyang
 *
 */
@Service
public class SI36Impl extends BaseService implements SI36 {
	
	private static final Logger logger = LoggerFactory.getLogger(SI36Impl.class);
	String srcPath = "E:/Interface/ctcai/out/ZJXX"; // 接口对应的文件路径
	ReadFileTxt rdt = new ReadFileTxt();
	
	@Autowired
	SICommonDao dao;

	@Override
	public String execute() throws Exception {
		logger.info("====SI36 is begin====");
		CTCAIZJXXParse zjxxParse = new CTCAIZJXXParse();
		
		// 扫描目标文件夹文件
		File srcFolder = new File(srcPath);
		if (!srcFolder.exists()) {
			logger.info("=============文件夹不存在不执行任何操作=================");
			return "1";
		}
		File[] filesList = srcFolder.listFiles(); // 获取文件夹下面的所有文件
		if (null != filesList && filesList.length > 0) {
			for (int j = 0; j < filesList.length; j++) {
				// 剔除包含备份文件夹的结果集
				if (filesList[j].getName().equals("Archive")) {
					continue;
				}
				// 传入文件路径获取解析后的结果集
				List<Ctcai2DcsZJXXDTO> list = zjxxParse.readTxt(srcPath + "/"
						+ filesList[j].getName(), "", filesList[j].getName());
				// 对接收的PO集合进行处理
				if (null == list) {
					logger.info("=============文件解析错误！=================");
					continue;
				}
				try {
					/****************************** 开启事物 *********************/
					beginDbService();
					
					// 新增前清除历史资金信息
					TiCtcaiFundInfoPO.deleteAll();
					Date currentTime = new Date();
					for (int i = 0; i < list.size(); i++) {
						Ctcai2DcsZJXXDTO tempPO = new Ctcai2DcsZJXXDTO();
						tempPO = list.get(i);
						TiCtcaiFundInfoPO fundPo = new TiCtcaiFundInfoPO();
						String dealerCode = dao.getDealerCodeByInfo(tempPO.getDealerCode(), "ctcai");
						fundPo.setString("DEALERCODE",dealerCode);
						if (tempPO.getBalance() != null && tempPO.getBalance().length() > 0) {
							fundPo.setFloat("BALANCE",Float.valueOf(tempPO.getBalance()));
						}
						if (tempPO.getCtcaiAcceptancesBalance() != null && tempPO.getCtcaiAcceptancesBalance().length() > 0) {
							fundPo.setFloat("CTCAI_ACCEPTANCES_BALANCE",Float.valueOf(tempPO.getCtcaiAcceptancesBalance()));
						}
						if (tempPO.getCtcaiAcceptancesDiscountBalance() != null && tempPO.getCtcaiAcceptancesDiscountBalance().length() > 0) {
							fundPo.setFloat("CTCAI_ACCEPTANCES_DISCOUNT_BALANCE",Float.valueOf(tempPO.getCtcaiAcceptancesDiscountBalance()));
						}
						if (tempPO.getDealerAcceptancesBalance() != null && tempPO.getDealerAcceptancesBalance().length() > 0) {
							fundPo.setFloat("DEALER_ACCEPTANCES_BALANCE",Float.valueOf(tempPO.getDealerAcceptancesBalance()));
						}
						if (tempPO.getDealerAcceptancesDiscountBalance() != null && tempPO.getDealerAcceptancesDiscountBalance().length() > 0) {
							fundPo.setFloat("DEALER_ACCEPTANCES_DISCOUNT_BALANCE",Float.valueOf(tempPO.getDealerAcceptancesDiscountBalance()));
						}
						if (tempPO.getRebatesBalance() != null&& tempPO.getRebatesBalance().length() > 0) {
							fundPo.setFloat("REBATES_BALANCE",Float.valueOf(tempPO.getRebatesBalance()));
						}
						fundPo.setLong("CREATE_BY",new Long(80000002));
						fundPo.setTimestamp("CREATE_DATE",currentTime);
						fundPo.saveIt();
					}
					
					dbService.endTxn(true);
					rdt.DelFile(srcPath + "/" + filesList[j].getName());
					/****************************** 结束事物 *********************/
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					e.printStackTrace();
					dbService.endTxn(false);
				} finally {
					Base.detach();
					dbService.clean();
				}
				
			}
		}else {
			logger.info("=============指定文件夹为空=================");
		}
		logger.info("====SI36 is finish====");
		return null;
	}


}
