package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.BookingLimitDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.PackageTypeDTO;
import com.yonyou.dms.repair.domains.PO.basedata.BookingLimitPO;
import com.yonyou.dms.repair.domains.PO.basedata.PackageTypePO;

/**
 * 组合类别
 * 
 * @author sunqinghua
 * @date 2017年3月22日
 */
@Service
public class PackageTypeServiceImpl implements PackageTypeService{

	@Override
	public PageInfoDto queryPackageType(Map<String, String> queryParam) throws ServiceBizException {
	    StringBuilder sqlSb = new StringBuilder("SELECT DEALER_CODE,PACKAGE_TYPE_CODE,PACKAGE_TYPE_NAME FROM tm_package_type WHERE 1=1");
        List<Object> params = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("packageTypeCode"))) {
            sqlSb.append(" and PACKAGE_TYPE_CODE = ?");
            params.add(queryParam.get("packageTypeCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("packageTypeName"))) {
            sqlSb.append(" and PACKAGE_TYPE_NAME like ?");
            params.add("%" + queryParam.get("packageTypeName") + "%");
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(), params);
        return pageInfoDto;
	}

	@Override
	public Map findPackageTypeById(String id) throws ServiceBizException {
		List<Object> params = new ArrayList<>();
        params.add(id);
    	StringBuilder sqlSb = new StringBuilder("SELECT DEALER_CODE,PACKAGE_TYPE_CODE,PACKAGE_TYPE_NAME FROM tm_package_type WHERE 1=1");
		sqlSb.append(" and PACKAGE_TYPE_CODE = ? ");
		Map first = DAOUtil.findFirst(sqlSb.toString(),params);
		return first;
	}

	@Override
	public String addPackageType(PackageTypeDTO pyto) throws ServiceBizException {
		PackageTypePO packageTypePO = new PackageTypePO();
		CheckPackageType(pyto);
		setPackageTypePO(packageTypePO, pyto);
		packageTypePO.saveIt();
		return null;
	}

	@Override
	public void modifyPackageType(String id, PackageTypeDTO pyto) throws ServiceBizException {
		PackageTypePO packageTypePO = PackageTypePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
		String workgroupname = pyto.getPackageTypeName();
        StringBuffer sbstr= new StringBuffer("SELECT DEALER_CODE,PACKAGE_TYPE_CODE,PACKAGE_TYPE_NAME FROM tm_package_type WHERE PACKAGE_TYPE_NAME = ? ");
        List<Object> params = new ArrayList<>();
        params.add(workgroupname);
        if(DAOUtil.findAll(sbstr.toString(), params).size() >0){
            throw new ServiceBizException("该组合类别名称已存在！");
        }
        packageTypePO.setString("PACKAGE_TYPE_NAME", pyto.getPackageTypeName());
		packageTypePO.saveIt();
	}

    /**
    * 检查组合类别
    * @author zhanshiwei
    * @date 2016年10月13日
    * @param limidto
    */
    	
    public void CheckPackageType(PackageTypeDTO pyto){
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("SELECT DEALER_CODE,PACKAGE_TYPE_CODE,PACKAGE_TYPE_NAME FROM tm_package_type WHERE 1=1 ");
        if (!StringUtils.isNullOrEmpty(pyto.getPackageTypeCode())) {
            sqlSb.append(" and PACKAGE_TYPE_CODE = ?");
            params.add(pyto.getPackageTypeCode());
        }
        if (!StringUtils.isNullOrEmpty(pyto.getPackageTypeName())) {
            sqlSb.append(" and PACKAGE_TYPE_NAME like ?");
            params.add( "%" + pyto.getPackageTypeName() + "%");
        }
        if(DAOUtil.findAll(sqlSb.toString(), params).size()>0){
            throw new ServiceBizException("已存在此组合类别!");
        }
    }
    
    /**
     * 设置PackageTypePO属性
     * 
     * @author sqh
     * @date 2017年3月22日
     * @param typo
     * @param pyto
     */

    public void setPackageTypePO(PackageTypePO typo, PackageTypeDTO pyto) {
    	typo.setString("PACKAGE_TYPE_CODE", pyto.getPackageTypeCode());
    	typo.setString("PACKAGE_TYPE_NAME", pyto.getPackageTypeName());
    }

}
