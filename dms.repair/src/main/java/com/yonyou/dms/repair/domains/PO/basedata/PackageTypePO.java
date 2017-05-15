package com.yonyou.dms.repair.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 组合类别
 * 
 * @author sunqinghua
 * @date 2017年3月22日
 */
@Table("TM_PACKAGE_TYPE")
@CompositePK({"DEALER_CODE","PACKAGE_TYPE_CODE"})
public class PackageTypePO extends BaseModel{

}
