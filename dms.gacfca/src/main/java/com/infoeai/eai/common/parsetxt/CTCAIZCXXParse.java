package com.infoeai.eai.common.parsetxt;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.infoeai.eai.DTO.Ctcai2DcsZCXXDTO;
import com.infoeai.eai.common.EAIConstant;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.common.TXTFactory;
import com.infoeai.eai.dao.ctcai.SICommonDao;
import com.yonyou.dms.function.utils.common.CommonUtils;


public class CTCAIZCXXParse extends TXTFactory {
	public static Logger logger = Logger.getLogger(SICommonDao.class);
	ReadFileTxt rdt = new ReadFileTxt();
	
	@Override
	public List<Ctcai2DcsZCXXDTO> readTxt(String filePath,String childFile,String fileName) {
		
		rdt.copyFile(filePath, "E:/Interface/ctcai/out/ZCXX/Archive/"+fileName);
		List<Ctcai2DcsZCXXDTO> dcp = new ArrayList<Ctcai2DcsZCXXDTO>();
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
					Ctcai2DcsZCXXDTO po = new Ctcai2DcsZCXXDTO();
					po.setVin(CommonUtils.checkNull(str2[0].trim()));	//VIN号
					po.setDealerCode(CommonUtils.checkNull(str2[1].trim()));	//Dealer Code
					po.setVehiclePrice(CommonUtils.checkNull(str2[2].trim()));	//标准车价
					po.setUseRebates(CommonUtils.checkNull(str2[3].trim()));	//使用返利
					po.setPayPrice(CommonUtils.checkNull(str2[4].trim()));		//应付车价
					po.setPayDate(CommonUtils.checkNull(str2[5].trim()));		//付款日期
					po.setOrderDate(CommonUtils.checkNull(str2[6].trim()));		//下单日期
					po.setLibraryDate(CommonUtils.checkNull(str2[7].trim()));	//出库日期
					po.setDistributionDate(CommonUtils.checkNull(str2[8].trim()));	//起运日期
					po.setTransitPosition(CommonUtils.checkNull(str2[9].trim()));	//在途位置
					po.setActionDate(CommonUtils.checkNull(str2[10].trim()));		//交易日期 车辆到店日期(物流上报）
					po.setOrderNumber(CommonUtils.checkNull(str2[11].trim()));		//订单编号
					dcp.add(po);
				}
			} catch (Exception e) {
				logger.info("txt内部数据错误,获取失败：请检查txt数据是否丢失！" + e);
				e.printStackTrace();
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
