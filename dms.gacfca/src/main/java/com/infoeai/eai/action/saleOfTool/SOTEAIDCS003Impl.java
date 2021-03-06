package com.infoeai.eai.action.saleOfTool;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.EAIConstant;
import com.infoeai.eai.common.HttpUtil;
import com.infoeai.eai.dao.salesOfTool.TiDealerInfoVerifyDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * EAI->DCS
 * 经销商信息验证接口
 * @author luoyang
 *
 */
@Service
public class SOTEAIDCS003Impl extends BaseService implements SOTEAIDCS003 {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTEAIDCS003Impl.class);
	
	@Autowired
	TiDealerInfoVerifyDao dao;

	@Override
	public void doGet(ServletRequest req, ServletResponse res) throws Exception {
		//==========================接收EAI数据===============================
		String returnResult = EAIConstant.DEAL_FAIL;
		ByteArrayOutputStream baStream = null;
		InputStream ins = null;
		logger.info("=============EAI传输的dcs================开始===========");
		try {
			ins = req.getInputStream();
			byte[] temp = new byte[1024];
			baStream = new ByteArrayOutputStream();
			int count = 0;
			while((count = ins.read(temp))!=-1) {
				baStream.write(temp, 0, count);
			}
			if(baStream.size()!=0){
				logger.info("=========EAI传输的JSON String ======>>" + new String(baStream.toByteArray(),"UTF-8"));
				String strJSON=new String(baStream.toByteArray(),"UTF-8");
				if (strJSON != null &&strJSON.startsWith("\ufeff")) {
					strJSON = strJSON.substring(1);
				}
				JSONObject json=JSONObject.fromObject(strJSON);
				
				String startDate=json.getString("BeginData");
				String endDate=json.getString("EndData");
				logger.info("============EAI传输的BeginData=========="+startDate);
				logger.info("============EAI传输的EndData============"+endDate);
				if(json.getInt("DataCount")!=0){
					Map<String,String> pKey=new HashMap<String,String>();
					pKey.put("DEALER_CODE", "DealerCode");
					JSONArray jsonArray=json.getJSONArray("DataValues");
					try {
						beginDbService();
						returnResult=dao.JSONArrayToPO(jsonArray, pKey);
						dbService.endTxn(true);
					} catch (Exception e) {
						e.printStackTrace();
						dbService.endTxn(false);
					} finally {
						Base.detach();
						dbService.clean();
					}
				}
				logger.info("===========数据操作返回结果： " + returnResult );
				logger.info("=============EAI传输到dcs数据传输验证表(APP)================成功");
			}else{
				logger.info("=============EAI传输到dcs数据传输验证表(APP)================无数据");
			}
		} catch (Exception e) {
			logger.info("===========返回数据操作返回：========= " + returnResult );
			logger.info("=============EAI传输到dcs数据传输验证表(APP)================异常==========="+e);
			e.printStackTrace();
		}finally{
			if(baStream != null){
				baStream.close();
			}
			if(ins != null){
				ins.close();
			}
			logger.info("=============EAI传输到dcs数据传输验证表(APP)================结束===========");
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
	
	public static void main(String[] args) throws Exception {
		JSONObject json = new JSONObject();
		json.put("BeginData", "");
		json.put("EndData", "");
		json.put("DataCount", "2");
		JSONArray a = new JSONArray();
		JSONObject b = new JSONObject();
		JSONObject c = new JSONObject();
		b.put("DealerCode", "10036");
		c.put("DealerCode", "10037");
		a.add(b);
		a.add(c);
		json.put("DataValues", a);
		System.out.println(json);
		HttpUtil.httpPostToJson("http://127.0.0.1:8080/dms.web/gacfca/SOTEAIDCS003", json.toString());
	}

}
