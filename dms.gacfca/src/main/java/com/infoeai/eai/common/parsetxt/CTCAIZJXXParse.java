package com.infoeai.eai.common.parsetxt;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.infoeai.eai.DTO.Ctcai2DcsZJXXDTO;
import com.infoeai.eai.common.EAIConstant;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.common.TXTFactory;
import com.infoeai.eai.dao.ctcai.SICommonDao;
import com.yonyou.dms.function.utils.common.CommonUtils;


public class CTCAIZJXXParse extends TXTFactory {
	public static Logger logger = Logger.getLogger(SICommonDao.class);
	ReadFileTxt rdt = new ReadFileTxt();
	
	@Override
	public List<Ctcai2DcsZJXXDTO> readTxt(String filePath,String childFile,String fileName) {
		
		rdt.copyFile(filePath, "E:/Interface/ctcai/out/ZJXX/Archive/"+fileName);
		List<Ctcai2DcsZJXXDTO> dcp = new ArrayList<Ctcai2DcsZJXXDTO>();
		// 获取txt信息
		String str = rdt.readFile(filePath, "GBK");
		if (str != null && str.length() != -1) {
			// row获取txt行数
			String[] row = str.split("\n");
			try {
				// 获取数据从第2行开始，到倒数第2行
				for (int i = 1; i < row.length - 1; i++) {
					// 截取数据值
					String[] str2 = row[i].replace("\\", " , ").split(",");
					Ctcai2DcsZJXXDTO po = new Ctcai2DcsZJXXDTO();
					po.setDealerCode(CommonUtils.checkNull(str2[0].trim()));
					po.setBalance(CommonUtils.checkNull(str2[1].trim()));
					po.setCtcaiAcceptancesBalance(CommonUtils.checkNull(str2[2].trim()));
					po.setDealerAcceptancesBalance(CommonUtils.checkNull(str2[3].trim()));
					po.setCtcaiAcceptancesDiscountBalance(CommonUtils.checkNull(str2[4].trim()));
					po.setDealerAcceptancesDiscountBalance(CommonUtils.checkNull(str2[5].trim()));
					po.setRebatesBalance(CommonUtils.checkNull(str2[6].trim()));
					dcp.add(po);
				}
			} catch (Exception e) {
				logger.info("txt内部数据错误,获取失败：请检查txt数据是否丢失！" + e);
				dcp = null;
			}
		} else {
			System.out.println("请检查txt文件是否有数据！！");
		}
		
		return dcp;
	}

	/**
	 * 输入：filePath文件路径 生产DCS->CTCAI文件，经销商返利明细导入 生产成功或失败：0-成功，1-失败
	 * Dcs2CtcaiPO.java
	 */
	@Override
	public int writeTxt(String Uname,List obj) {
		return EAIConstant.WRITE_SUCCESS;
	}
	

}
