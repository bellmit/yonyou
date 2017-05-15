package com.yonyou.dms.vehicle.service.stockManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

@SuppressWarnings("rawtypes")
public interface LendAndReturnService {

    /**
     * 主页面通过slNo查询借出单详情 TODO description
     * 
     * @author yangjie
     * @date 2017年1月12日
     * @param slNo
     * @return
     */
    List<Map> findAllItem(String slNo);

    /**
     * 查询借出单详细
    * TODO description
    * @author yangjie
    * @date 2017年1月12日
    * @param query
    * @return
     */
    PageInfoDto findAllDetails(Map<String, String> query);

    /**
     * 查询所有车辆信息
    * TODO description
    * @author yangjie
    * @date 2017年1月12日
    * @param queryParam
    * @return
     */
    PageInfoDto findAllVehicleInfo(Map<String, String> queryParam);

    /**
     * 借出归还单
    * TODO description
    * @author yangjie
    * @date 2017年1月12日
    * @param map 要保存的参数
    * @param po 借出归还单
    * @param flag   新增为true，修改为false
     */
    void addOrEditItem(Map<String, Object> map, Boolean flag);

    /**
     * 借出归还明细
    * TODO description
    * @author yangjie
    * @date 2017年1月12日
    * @param map
    * @param po
     */
    void addOrEditDetails(Map<String, Object> map);

    /**
     * 归还操作
    * TODO description
    * @author yangjie
    * @date 2017年1月12日
     */
    void btnReturn(Map<String, Object> map);

}
