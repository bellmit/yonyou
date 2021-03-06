package com.infoeai.eai.action.saleOfTool;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.salesOfTool.TiDealerInfoVerifyDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.common.domains.PO.basedata.TiDealerInfoVerifyDcsPO;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.jsonSerializer.JSONUtil;

/**
 * DCS->EAI
 * 经销商信息验证接口DCS->EAI
 * @author luoyang
 *
 */
@Service
public class SOTDCSEAI003Impl extends BaseService implements SOTDCSEAI003 {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDCSEAI003Impl.class);
	
	@Autowired
	TiDealerInfoVerifyDao dao;

	@Override
	public void doGet(ServletRequest req, ServletResponse res) throws Exception {
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out=res.getWriter();
		boolean isSuc=false;
		try {
			//开启事务
			beginDbService();
			TiDealerInfoVerifyDcsPO.update("IS_SCAN = ?", "IS_SCAN = '9' ","0");
			Map<String,Object> beginAndEndDate=dao.beginAndEndDate();
			List<Map> list=dao.searchDataForJSON();
			int size = list == null ? 0 : list.size();
			logger.info("================经销商信息验证接口DCS<->EAI==========开始"+size);
			if(size==0){
				out.write("");
				out.flush();
				out.close();
			}else{
				Map<String,Object> jsonObj=new HashMap<String,Object>();
				if(beginAndEndDate != null && !beginAndEndDate.isEmpty()){					
					jsonObj.put("BeginDate", CommonUtils.checkNull(beginAndEndDate.get("BEGIN_DATE")));
					jsonObj.put("EndDate", CommonUtils.checkNull(beginAndEndDate.get("END_DATE")));
				}
				jsonObj.put("DataCount", size);
				String sjon = JSONUtil.objectToJson(list);
				jsonObj.put("DataValues", sjon);
				out.write(jsonObj.toString());
				out.flush();
				out.close();
				isSuc=true;
				logger.info("================经销商信息验证接口DCS<->EAI==========成功");
			}
			if(isSuc){
				TiDealerInfoVerifyDcsPO.update("IS_SCAN = ?,UPDATE_DATE = ?,UPDATE_BY = ?", "IS_SCAN = 0",
						"1",new Date(),DEConstant.DE_UPDATE_BY);
			}
			//关闭事务
			dbService.endTxn(true);
		} catch (Exception e) {
			logger.info("================经销商信息验证接口DCS<->EAI==========异常"+e.getMessage());
			e.printStackTrace();
			out.write("");
			out.flush();
			out.close();
			dbService.endTxn(false);
		} finally{
			if(out!=null){
				out.flush();
				out.close();
			}
			Base.detach();
			dbService.clean();
			logger.info("================经销商信息验证接口DCS<->EAI==========结束");
		}
		
	}

	@Override
	public void doPost(ServletRequest req, ServletResponse resp) throws Exception {
		try {
			doGet(req,resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
