package com.infoeai.eai.common;

import java.util.List;

public abstract class TXTFactory {
	
	public static TXTFactory getInstance(String id) {
		String classname = "";
		if(EAIConstant.INTERFACT_SI01.equals(id) || EAIConstant.INTERFACT_SI03.equals(id)){//swt
			classname = EAIConstant.SWT_PARSE01_TXT;//工厂订单车辆状态跟踪-01/销售订单导入
		}else if(EAIConstant.INTERFACT_SI02.equals(id)){//swt
			classname = EAIConstant.SWT_PARSE02_TXT;//工厂订单车辆状态跟踪-02
		}else if(EAIConstant.INTERFACT_SI04.equals(id) || EAIConstant.INTERFACT_SI05.equals(id)){//ctcai
			classname = EAIConstant.CTCAI_PARSE01_TXT;//经销商返利明细导入/车辆返利使用结果回传
		}else if(EAIConstant.INTERFACT_SI06.equals(id)){//ctcai
			classname = EAIConstant.CTCAI_PARSE01_TXT;//红票返利使用结果回传
		}
		TXTFactory factory = null;
		try {
			factory = (TXTFactory) Class.forName(classname).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return factory;
	}
	
	/**
	 * 读取txt文件，生产业务实体
	 * add by dengweili 20130521
	 * @param filePath txt文件路径
	 * @return 业务实体对象PO/Bean
	 */
	protected abstract List readTxt(String filePath,String childFile,String fileName) ;
	
	/**
	 * 将业务实体，写入txt文件
	 * @param id 接口ID
	 * @param obj 业务实体
	 * @return 写入成功或失败：0-成功，1-失败
	 */
	protected abstract int writeTxt(String Uname,List obj);

}
