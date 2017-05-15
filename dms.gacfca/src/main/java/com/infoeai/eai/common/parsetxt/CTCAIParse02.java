package com.infoeai.eai.common.parsetxt;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.infoeai.eai.DTO.Ctcai2Dcs02DTO;
import com.infoeai.eai.common.EAIConstant;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.common.TXTFactory;
import com.infoeai.eai.dao.ctcai.SICommonDao;
import com.yonyou.dms.function.utils.common.CommonUtils;

public class CTCAIParse02 extends TXTFactory {
	public static Logger logger = Logger.getLogger(SICommonDao.class);
	ReadFileTxt rdt = new ReadFileTxt();

	/**
	 * 输入：filePath文件路径 解析CTCAI->DCS文件，红票返利使用结果回传 输出：Ctcai2Dcs01PO.java
	 */
	@Override
	public List<Ctcai2Dcs02DTO> readTxt(String filePath,String childFile,String fileName) {
		rdt.copyFile(filePath, "E:/Interface/ctcai/out/RTUR/Archive/"+fileName);
		List<Ctcai2Dcs02DTO> cdp = new ArrayList<Ctcai2Dcs02DTO>();
		String str = rdt.readFile(filePath, "GBK");
		if (str != null && str.length() != -1) {
			// row获取txt行数
			String[] row = str.split("\n");
			try {
				// 获取数据从第2行开始，到倒数第2行
				for (int i = 1; i < row.length - 1; i++) {
					// 截取数据值
					String[] str2 = row[i].replace("\\", " , ").split(",");
					Ctcai2Dcs02DTO po = new Ctcai2Dcs02DTO();
					po.setActionCode(CommonUtils.checkNull(str2[0].trim()));
					po.setActionDate(CommonUtils.checkNull(str2[1].trim()));
					po.setActionTime(CommonUtils.checkNull(str2[2].trim()));
					po.setDealerCode(CommonUtils.checkNull(str2[3].trim()));
					po.setRedTicketNumber(CommonUtils.checkNull(str2[4].trim()));
					po.setRedTicketAmount(CommonUtils.checkNull(str2[5].trim()));
					po.setRebateCode(CommonUtils.checkNull(str2[6].trim()));
					po.setUseDate(CommonUtils.checkNull(str2[7].trim()));
					po.setRemark(CommonUtils.checkNull(str2[8].trim()));
					cdp.add(po);
				}
			} catch (Exception e) {
				logger.info("txt内部数据错误,获取失败：请检查txt数据是否丢失！" + e);
				cdp = null;
			}
		} else {
			System.out.println("请检查txt文件是否有数据！！");
		}
		return cdp;
	}

	@Override
	public int writeTxt(String Uname, List obj) {
		// TODO Auto-generated method stub
		// List<Ctcai2Dcs02PO> list = obj;
		// StringBuffer str = null;
		// if(list.size()!=-1){
		// rdt.append(rdt.fileFullName(Uname), rdt.timeFile(Uname),
		// "GBK");//添加内容头信息
		// for(Ctcai2Dcs02PO dcp:list){
		// str = new StringBuffer();
		// str.append(dcp.getActionCode()).append("\\");
		// str.append(dcp.getActionDate()).append("\\").append(dcp.getActionTime()).append("\\");
		// str.append(dcp.getDealerCode()).append("\\").append(dcp.getRedTicketNumber()).append("\\");
		// str.append(dcp.getRedTicketAmount()).append("\\").append(dcp.getUseDate()).append("\\");
		// str.append(dcp.getRemark());
		// String log = str.toString();
		// rdt.append(rdt.fileFullName(Uname), log, "GBK");
		// }
		// rdt.append(rdt.fileFullName(Uname), "TRL\\"+list.size(),
		// "GBK");//添加内容尾信息
		// }else{
		// return EAIConstant.WRITE_FAIL;
		// }
		return EAIConstant.WRITE_SUCCESS;
	}

}
