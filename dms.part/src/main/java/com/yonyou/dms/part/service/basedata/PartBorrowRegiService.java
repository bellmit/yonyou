package com.yonyou.dms.part.service.basedata;

import java.text.ParseException;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.ListTtPartBorrowItemDTO;

/**
 * 借进登记
* TODO description
* @author yujiangheng
* @date 2017年5月11日
 */
public interface PartBorrowRegiService {
    PageInfoDto searchPartBorrowRegi(String borrowNo)throws ServiceBizException;
    PageInfoDto searchPartBorrowRegiItem( String borrowNo)throws ServiceBizException;
    String savePartBorrowRegi(ListTtPartBorrowItemDTO listTtPartBorrowItemDTO)throws ServiceBizException,ParseException;
    void deletePartBorrowRegi(String borrowNo)throws ServiceBizException,ParseException;
    void accountPartBorrowRegi(Map<String, String> queryParam)throws ServiceBizException,Exception;
    

}
