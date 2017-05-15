package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.OemBaseModel;

/**
 * 物料表
 * @author 夏威
 * 2017-1-16
 */
@Table("tm_vhcl_material")
@IdName("MATERIAL_ID")
public class MaterialPO extends OemBaseModel{

}
