package com.yonyou.dcs.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.SEDCSP15DTO;
/**
 * 到货索赔上报DCS
 * @author xuqinqin
 */
public interface SEDCSP15Cloud extends BaseCloud{

	String handleExecutor(LinkedList<SEDCSP15DTO> dtoList) throws Exception;

}
