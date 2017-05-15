package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.OemBaseModel;

/**
 * 物料接口表
 * @author 夏威
 * 2017-1-19
 */
@Table("ti_material_group")
@IdName("SEQUENCE_ID")
public class MaterialInPO extends OemBaseModel{
	
}
