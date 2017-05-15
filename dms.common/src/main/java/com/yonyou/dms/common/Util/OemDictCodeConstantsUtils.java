package com.yonyou.dms.common.Util;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yonyou.dms.common.domains.DTO.basedata.TcCodeDTO;
import com.yonyou.dms.framework.domains.DTO.baseData.OemDictDto;
import com.yonyou.dms.framework.service.cache.impl.OemDictCacheServiceImpl;
import com.yonyou.dms.function.common.OemDictCodeConstants;

/**
 * 常量转换
 * 
 * @author
 *
 */
@Component
public class OemDictCodeConstantsUtils {

	static OemDictCacheServiceImpl<List<OemDictDto>> oemDictCacheSerivce;

	@Resource(name = "OemDictCache")
	public void setOemDictCacheSerivce(OemDictCacheServiceImpl<List<OemDictDto>> oemDictCacheSerivce) {
		OemDictCodeConstantsUtils.oemDictCacheSerivce = oemDictCacheSerivce;
	}

	/**
	 * 根据数据字典类型和数据描述，返回数据字典的codeID
	 * 
	 * @param type
	 * @param codeDesc
	 * @return
	 */
	public static Integer getDictCodeByName(String type, String codeDesc) {
		Map<Integer, List<OemDictDto>> dictMap = oemDictCacheSerivce.getAllData();
		List<OemDictDto> codeList = dictMap.get(Integer.parseInt(type));
		Integer codeId = 0;
		for (OemDictDto code : codeList) {
			if (code.getCode_desc().equals(codeDesc)) {
				codeId = code.getCode_id();
			}
		}
		return codeId;
	}

	/**
	 * 根据数据字典类型和数据描述，返回数据字典的codeID
	 * 
	 * @param type
	 * @param codeDesc
	 * @return
	 */
	public static String getDictDescById(String type, String id) {
		Map<Integer, List<OemDictDto>> dictMap = oemDictCacheSerivce.getAllData();
		List<OemDictDto> codeList = dictMap.get(Integer.parseInt(type));
		String codeDesc = "";
		for (OemDictDto code : codeList) {
			if (code.getCode_id().equals(Integer.valueOf(id))) {
				codeDesc = code.getCode_desc();
			}
		}
		return codeDesc;
	}

	public static String getIf_type(Integer IfType) {

		if (IfType.equals(OemDictCodeConstants.IF_TYPE_YES)) {
			return "1";// 是
		}

		if (IfType.equals(OemDictCodeConstants.IF_TYPE_NO)) {
			return "0";// 否
		}

		return "";
	}

	public static String Lifcycle(Integer lifcycle) {

		if (lifcycle.equals(OemDictCodeConstants.LIFCYCLE1)) {
			return "1";
		}

		if (lifcycle.equals(OemDictCodeConstants.LIFCYCLE2)) {
			return "2";//
		}
		if (lifcycle.equals(OemDictCodeConstants.LIFCYCLE3)) {
			return "3";//
		}
		if (lifcycle.equals(OemDictCodeConstants.LIFCYCLE4)) {
			return "4";//
		}
		if (lifcycle.equals(OemDictCodeConstants.LIFCYCLE5)) {
			return "5";//
		}

		return "";
	}

	/**
	 * 
	 * @param IS_SUPPORT任务人外
	 * @return 0代表任务外 1代表任务内
	 */
	public static String getISSUPPORT(Integer IS_SUPPORT) {
		// 任务内
		if (IS_SUPPORT.equals(OemDictCodeConstants.IS_SUPPORT1)) {
			return "1";
		}
		// 任务外
		if (IS_SUPPORT.equals(OemDictCodeConstants.IS_SUPPORT2)) {
			return "0";
		}

		return "";
	}

	// 确认状态常量转换
	public static String getConfirmStatus(Integer confirmStatus) {
		if (confirmStatus.equals(OemDictCodeConstants.EC_CONFIRM_STATUS_01)) {
			return "1";
		}

		if (confirmStatus.equals(OemDictCodeConstants.EC_CONFIRM_STATUS_02)) {
			return "2";
		}

		return "";
	}

	// 分配状态常量转换
	public static String getStoreType(Integer storeType) {

		if (storeType.equals(OemDictCodeConstants.storeType1)) {
			return "1";
		}

		if (storeType.equals(OemDictCodeConstants.IS_SUPPORT2)) {
			return "0";
		}

		return "";
	}

	public static String getSelctStore(Integer selctStore) {

		if (selctStore.equals(OemDictCodeConstants.selctStore1)) {
			return "1";
		}

		if (selctStore.equals(OemDictCodeConstants.selctStore2)) {
			return "2";
		}
		// 默认返回1
		return "1";
	}
	
	/**
	 * 根据数据字典的类型，返回这个类型对应的数据字典对象的数组
	 * @param type
	 * @return
	 */
	public static OemDictDto[] getDictArrayByType(String type){
		Map<Integer, List<OemDictDto>> dictMap = oemDictCacheSerivce.getAllData();
		List<OemDictDto> codeList = dictMap.get(Integer.parseInt(type));
		OemDictDto[] tcArray = new OemDictDto[codeList.size()];
		return (OemDictDto[]) codeList.toArray(tcArray);
	}

}
