package com.infoeai.eai.common.parsetxt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.infoeai.eai.DTO.Dcs2SwtDTO;
import com.infoeai.eai.DTO.Swt2Dcs01DTO;
import com.infoeai.eai.common.EAIConstant;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.common.TXTFactory;
import com.infoeai.eai.dao.ctcai.SICommonDao;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

public class SWTParse01 extends TXTFactory {
	public static Logger logger = Logger.getLogger(SICommonDao.class);
	ReadFileTxt rdt = new ReadFileTxt();

	/**
	 * 输入：filePath文件路径 解析SWT->DCS文件，工厂订单车辆状态跟踪01(节点状态:13个,除了ZPDU)
	 * 输出：Swt2Dcs01PO.java
	 */
	@Override
	public List<Swt2Dcs01DTO> readTxt(String filePath,String childFile,String fileName) {
		// txt另存为
		rdt.copyFile(filePath, "E:/Interface/dcssftp/out/"+childFile+"/Archive/"+fileName);
		List<Swt2Dcs01DTO> sdp = new ArrayList<Swt2Dcs01DTO>();
		String str = rdt.readFile(filePath, "GBK");
//		// 读取5000多台车的配置文件
		String vehicle5000 = rdt.readFile("E:/Interface/vehicle5000.txt", "GBK");
		
		if (str != null && str.length() != -1) {
			// row获取txt行数
			String[] row = str.split("\n");
			try {
				// 获取数据从第2行开始，到倒数第2行
				for (int i = 1; i < row.length - 1; i++) {
					// 截取数据值
					String[] str2 = row[i].replace("\\", " @ ").split("@");
					Swt2Dcs01DTO po = new Swt2Dcs01DTO();
					po.setActionCode(CommonUtils.checkNull(str2[0].trim()));
					po.setActionDate(CommonUtils.checkNull(str2[1].trim()));
					po.setActionTime(CommonUtils.checkNull(str2[2].trim()));
					po.setVin(CommonUtils.checkNull(str2[3].trim()));
					
					if(fileName.contains("ZBIL")){
						po.setEngenNO(CommonUtils.checkNull(str2[4].trim()));
						po.setProductDate(CommonUtils.checkNull(str2[5].trim()));
						po.setPrimaryStatus(CommonUtils.checkNull(str2[6].trim()));
						po.setSecondaryStatus(CommonUtils.checkNull(str2[7].trim()));
						po.setShipDate(CommonUtils.checkNull(str2[8].trim()));
						po.setEta(CommonUtils.checkNull(str2[9].trim()));
						//如果传递过来的车存在配置文件，则将做其先去“-8BL”再补“-B”处理
						String model = CommonUtils.checkNull(str2[10].trim().replace("-8BL", ""));
						if(vehicle5000.contains(CommonUtils.checkNull(str2[3].trim()))){
							model = model+"-B";
						}
						po.setModel(model);
						po.setModelYear(CommonUtils.checkNull(str2[11].trim()));
						po.setColour(CommonUtils.checkNull(str2[12].trim()));
						po.setTrim(CommonUtils.checkNull(str2[13].trim()));
						po.setFactoryStandardOptions(CommonUtils.checkNull(str2[14].trim()));
						po.setFactoryOptions(CommonUtils.checkNull(str2[15].trim()));
						po.setLocalOptions(CommonUtils.checkNull(str2[16].trim()));
						po.setSoldTo(CommonUtils.checkNull(str2[17].trim()));
						po.setInvoiceNumber(CommonUtils.checkNull(str2[18].trim()));
						//新增车辆用途
						if(str2[19]!=null){
							po.setVehicleUsage(CommonUtils.checkNull(str2[19].trim()));
						}
						//新增中进采购价与零售价
						//Retail price-建议零售价-前面的价格
						//wholesale price-中进批发价-后面的价格
						po.setWholesalePrice(CommonUtils.checkNull(str2[21].trim()));
						po.setRetailPrice(CommonUtils.checkNull(str2[20].trim()));
						// 车辆VPC港口归属字段 add by wujinbiao
						if (str2[22].trim().equals("13922")) {
							po.setVpcPort(OemDictCodeConstants.VPC_PORT_01);
						} else if (str2[22].trim().equals("20992")) {
							po.setVpcPort(OemDictCodeConstants.VPC_PORT_02);
						}
					}else{
						po.setPrimaryStatus(CommonUtils.checkNull(str2[4].trim()));
						po.setSecondaryStatus(CommonUtils.checkNull(str2[5].trim()));
						po.setShipDate(CommonUtils.checkNull(str2[6].trim()));
						po.setEta(CommonUtils.checkNull(str2[7].trim()));
						//如果传递过来的车存在配置文件，则将做其先去“-8BL”再补“-B”处理
						String model = CommonUtils.checkNull(str2[8].trim().replace("-8BL", ""));
						if(vehicle5000.contains(CommonUtils.checkNull(str2[3].trim()))){
							model = model+"-B";
						}
						po.setModel(model);
						po.setModelYear(CommonUtils.checkNull(str2[9].trim()));
						po.setColour(CommonUtils.checkNull(str2[10].trim()));
						po.setTrim(CommonUtils.checkNull(str2[11].trim()));
						po.setFactoryStandardOptions(CommonUtils.checkNull(str2[12].trim()));
						po.setFactoryOptions(CommonUtils.checkNull(str2[13].trim()));
						po.setLocalOptions(CommonUtils.checkNull(str2[14].trim()));
						po.setSoldTo(CommonUtils.checkNull(str2[15].trim()));
						po.setInvoiceNumber(CommonUtils.checkNull(str2[16].trim()));
						if (fileName.contains("ZFSC") || fileName.contains("ZFSN")) {
							// 车辆VPC港口归属字段 add by wujinbiao
							if (str2[17].trim().equals("13922")) {
								po.setVpcPort(OemDictCodeConstants.VPC_PORT_01);
							} else if (str2[17].trim().equals("20992")) {
								po.setVpcPort(OemDictCodeConstants.VPC_PORT_02);
							}
						}
					}
					sdp.add(po);
				}
			} catch (Exception e) {
				logger.info("txt内部数据错误,获取失败：请检查txt数据是否丢失！" + e);
				sdp = null;
			}
		} else {
			System.out.println("请检查txt文件是否有数据！！");
		}
		return sdp;
	}

	/**
	 * 输入：filePath文件路径 生产DCS->SWT文件，销售订单导入(节点状态2个,ZRL1和ZDRR) 生产成功或失败：0-成功，1-失败
	 * Dcs2SwtPO.java
	 */
	@Override
	public int writeTxt(String Uname, List obj) {
		List<Dcs2SwtDTO> list = obj;
		SWTParse01 parse =  new SWTParse01();
		String fileName = parse.fileFullName(Uname);
		StringBuffer str = null;
		if (list.size() != -1) {
			rdt.append(fileName, parse.timeFile(Uname), "GBK");// 添加内容头信息
			for (Dcs2SwtDTO dcp : list) {
				str = new StringBuffer();
				
				str.append(dcp.getVin()).append("\\");
				str.append(dcp.getActionCode()).append("\\");
				str.append(dcp.getActionDate()).append("\\");
				str.append(dcp.getDealerCode()).append("\\");
				str.append(dcp.getDealerName()).append("\\");
				str.append(CommonUtils.checkNull(dcp.getShippingAddress())).append("\\");
				str.append(CommonUtils.checkNull(dcp.getContactMethod())).append("\\");
				str.append(dcp.getPaymentType()).append("\\");
				str.append(CommonUtils.checkNull(dcp.getFinalNetAmount())).append("\\");
				str.append(dcp.getModel()).append("\\");
				str.append(dcp.getModelYear()).append("\\");
				str.append(dcp.getColourCode()).append("\\");
				str.append(dcp.getTrimCode()).append("\\");
				str.append(dcp.getStandardOptions()).append("\\");
				str.append(dcp.getFactoryOptions()).append("\\");
				str.append(dcp.getLocalOptions()).append("\\");
				str.append(dcp.getVehicleUsage()).append("\\");;
				if(dcp.getWholesalePrice()!=null)
				{
					str.append(dcp.getWholesalePrice());
				}
				str.append("\\");;
				String log = str.toString();
				rdt.append(fileName, log, "GBK");
				this.setFileName(fileName);
			}
			rdt.append(fileName, "TRL\\" + (list.size()+2), "GBK");// 添加内容尾信息
		} else {
			return EAIConstant.WRITE_FAIL;
		}

		return EAIConstant.WRITE_SUCCESS;
	}
	private String fileName;
	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	SimpleDateFormat df= new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat df2 = new SimpleDateFormat("HHmmss");
	
	/**
	 * fileFullName 文件全名, 包括全路径
	 * **/
	public  String fileFullName(String Uname){
		String contentTime = df.format(new Date());
		String contentTime2 = df2.format(new Date());
		StringBuffer str = new StringBuffer();
		str.append("E:/Interface/ctcai/in/"+Uname+"-1/");
		str.append("INDCS").append(Uname+"-1").append(contentTime).append(contentTime2).append(".txt");
		return str.toString();
	}
	
	/**
	 * txt文件名拼接公共方法
	 * **/
	public  String timeFile(String Uname){
		// new Date()为获取当前系统时间
		String contentTime = df.format(new Date());
		String contentTime2 = df2.format(new Date());
		StringBuffer str = new StringBuffer();
		str.append("DCS\\CTCAI\\").append(Uname).append("\\IN\\").append(contentTime).append("\\").append(contentTime2);
		return str.toString();
	}

}
