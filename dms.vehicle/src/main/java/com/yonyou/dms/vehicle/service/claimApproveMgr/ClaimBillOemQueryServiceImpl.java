package com.yonyou.dms.vehicle.service.claimApproveMgr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtWrClaimPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.claimApproveMgr.ClaimBillOemQueryDao;
import com.yonyou.dms.vehicle.domains.PO.claimApproveMgr.TtWrClaimAccountDcsPO;
import com.yonyou.dms.vehicle.domains.PO.claimApproveMgr.TtWrClaimaccountDetailDcsPO;


/**
 * 索赔申请单结算
 * @author ZhaoZ
 * @date 2017年4月28日
 */
@SuppressWarnings("all")
@Service
public class ClaimBillOemQueryServiceImpl implements ClaimBillOemQueryService{

	@Autowired
	ClaimBillOemQueryDao  claimDao;
	/**
	 * 索赔申请单结算查询
	 */
	@Override
	public PageInfoDto threePackItemList(Map<String, String> queryParams) throws ServiceBizException {
		return claimDao.threePackItems(queryParams);
	}
	
	/**
	 * 索赔申请单结算新增
	 */
	@Override
	public PageInfoDto claimBillOemAddList(String claimNos) throws ServiceBizException {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time= System.currentTimeMillis();
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto dto = new PageInfoDto();
		if("".equals(claimNos)){
			throw new ServiceBizException("无法获得申请单编号，请联系管理员！");
		}
		String claimNoIns = compileStr(claimNos);
		Long oemCompanyId = OemDAOUtil.getOemCompanyId(logonUser);
		List<Map> ps = claimDao.getClaimBillOemList(claimNoIns,oemCompanyId);
		if(ps!=null&&ps.size()>0){
			//查询所有的申请单
			List<Map> getTtWrClaimList = claimDao.getTtWrClaimList(claimNoIns, oemCompanyId);
			
			List<Map> ttWrClaimAccountBeanPOs = new ArrayList<>();
			//所有结算的申请单id
			List<Object> claimIdList = new ArrayList<Object>();
			String claimIds = "";
			for (int i = 0; i<ps.size(); i++)
			{
				Map map = ps.get(i);
				//组装住实体
				Double partFee = 0.0;
				if(map.get("PART_FEE")!=null){
					 partFee =  Double.valueOf(map.get("PART_FEE").toString());//配件金额
				}
				Integer claimnoNum = 0;
				if(map.get("CLAIMNO_NUM")!=null){
					claimnoNum = Integer.valueOf(map.get("CLAIMNO_NUM").toString()) ;//申请单数
				}
				//Long balanceId = map.get("BALANCE_ID");//结算单ID
				Double deductFee = 0.0;
				if( map.get("DEDUCT_FEE")!=null){
					deductFee = Double.valueOf(map.get("DEDUCT_FEE").toString()) ;//扣款金额
				}
				Long dealerId = null;
				if(map.get("DEALER_ID")!=null){
					dealerId = Long.valueOf(map.get("DEALER_ID").toString()) ;//经销商ID
				}
				
				String dealerName = (String) map.get("DEALER_NAME");//经销商I
				String dealerCode = (String) map.get("DEALER_CODE");//经销商I
				Double claimApplyFee = 0.0;
				if(map.get("CLAIM_APPLY_FEE")!=null){
					claimApplyFee = Double.valueOf(map.get("CLAIM_APPLY_FEE").toString()) ;//申请金额
				}
				Double labourFee = 0.0;
				if(map.get("LABOUR_FEE")!=null){
					labourFee = Double.valueOf(map.get("LABOUR_FEE").toString()) ;//工时金额
				}
				Double taxFee = 0.0;
				if(map.get("TAX_FEE")!=null){
					taxFee = Double.valueOf(map.get("TAX_FEE").toString()) ;//税额
				}
				//String balanceNo = map.get("BALANCE_NO");//结算单号
				//Date balanceDate = map.get("BALANCE_DATE");//结算日期
				Double otherFee = 0.0;
				if(map.get("OTHER_FEE")!=null){
					otherFee = Double.valueOf(map.get("OTHER_FEE").toString()) ;//其他金额
				}
				Double balanceFee =0.0;
				if(map.get("BALANCE_FEE")!=null){
					balanceFee = Double.valueOf(map.get("BALANCE_FEE").toString());//结算金额
				}
				
				String sql ="SELECT MAX(BALANCE_ID) balanceId FROM tt_wr_claim_account_dcs";
				Map mapA = OemDAOUtil.findFirst(sql,null);
				
				//TtWrClaimPO TtWrClaimPO = new TtWrClaimPO();
				TtWrClaimAccountDcsPO accountPO = new TtWrClaimAccountDcsPO();
				accountPO.setDouble("CLAIM_APPLY_FEE",claimApplyFee);
				accountPO.setInteger("CLAIMNO_NUM",claimnoNum);
				accountPO.setDouble("PART_FEE",partFee);
				
				accountPO.setDouble("DEDUCT_FEE",deductFee);
				accountPO.setLong("DEALER_ID",dealerId);
				
				accountPO.setDouble("LABOUR_FEE",labourFee);
				accountPO.setDouble("TAX_FEE",taxFee);
				accountPO.setDouble("OTHER_FEE",otherFee);
				accountPO.setDouble("BALANCE_FEE",balanceFee);
				String balanceId = mapA.get("balanceId").toString();
				Long balanceNo = Long.valueOf(balanceId)+1;
				accountPO.setString("BALANCE_NO",balanceNo.toString());//单号暂时用id
				accountPO.setDate("BALANCE_DATE",new Date());
				accountPO.setLong("CREATE_BY",logonUser.getUserId());
				try {
					Date date = sdf.parse(sdf.format(new Date(time)));
					java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
					accountPO.setTimestamp("CREATE_DATE",st);
				} catch (ParseException e) {
					e.printStackTrace();
				} 
				accountPO.setLong("OEM_COMPANY_ID",oemCompanyId);
				accountPO.insert();
				TtWrClaimAccountDcsPO accountPO1 = TtWrClaimAccountDcsPO.findFirst("BALANCE_NO = ?", balanceNo.toString());
				Map mapB = accountPO1.toMap();
				mapB.put("dealerCode", dealerCode);
				mapB.put("dealerName", dealerName);
				mapB.put("Fee",balanceFee-deductFee);
				ttWrClaimAccountBeanPOs.add(mapB);
				//组装从实体
				if(getTtWrClaimList!=null&&getTtWrClaimList.size()>0){
					for (Map mapClaim:getTtWrClaimList){
						Long DEALER_ID = null;
						if(mapClaim.get("DEALER_ID")!=null){
							DEALER_ID = Long.valueOf(mapClaim.get("DEALER_ID").toString()) ;
						}
						Long CLAIM_ID = null;
						if(mapClaim.get("CLAIM_ID")!=null){
							
							CLAIM_ID = Long.valueOf(mapClaim.get("CLAIM_ID").toString());
							//添加申请单id
							claimIdList.add(CLAIM_ID);
							if("".equals(claimIds)){
								claimIds=claimIds+mapClaim.get("CLAIM_ID").toString();
								
							}else{
								claimIds=claimIds+","+mapClaim.get("CLAIM_ID").toString();
							}
							
						}
							
						if(dealerId!=null&&dealerId.equals(DEALER_ID)){
							TtWrClaimaccountDetailDcsPO ttWrClaimaccountDetailPO = new TtWrClaimaccountDetailDcsPO();
							ttWrClaimaccountDetailPO.setLong("BALANCE_ID",Long.parseLong(mapA.get("balanceId").toString())+1);
							ttWrClaimaccountDetailPO.setLong("CLAIM_ID",CLAIM_ID);
							ttWrClaimaccountDetailPO.setLong("CREATE_BY",logonUser.getUserId());
							try {
								Date date = sdf.parse(sdf.format(new Date(time)));
								java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
								ttWrClaimaccountDetailPO.setTimestamp("CREATE_DATE",st);
							} catch (ParseException e) {
								e.printStackTrace();
							} 
							
							ttWrClaimaccountDetailPO.insert();
						}
						
					}
				}
			}
			
			LazyList<TtWrClaimPO> poList = TtWrClaimPO.find("CLAIM_ID IN (?)", claimIds);
			for(TtWrClaimPO po:poList){
				po.setLong("UPDATE_BY",logonUser.getUserId());
				try {
					Date date = sdf.parse(sdf.format(new Date(time)));
					java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
					po.setTimestamp("UPDATE_DATE",st);
				} catch (ParseException e) {
					e.printStackTrace();
				} 
				po.setInteger("STATUS", OemDictCodeConstants.CLAIM_STATUS_07);
				po.saveIt();
			}
			dto.setRows(ttWrClaimAccountBeanPOs);
			ttWrClaimAccountBeanPOs.size();
			dto.setTotal((long) ttWrClaimAccountBeanPOs.size());
		
		}
		return dto;
	}

	/**
	 * (将带逗号的字符串组装成带单引号)
	 * @param claimNos
	 * @return
	 */
	private String compileStr(String str) {
		StringBuilder strBuilder = new StringBuilder();
		if (isNull(str)) {
			throw new IllegalArgumentException("Invalid str, str == " + str);
		}
		String[] ss = str.split(",");
		for (int i = 0; i < ss.length; i++) {
			if (i == ss.length - 1) {
				strBuilder.append("'").append(ss[i]).append("'");
				break;
			}
			if (!isNull(ss[i])) {
				strBuilder.append("'").append(ss[i]).append("'").append(",");
			}
		}
		return strBuilder.toString();
	}
	public static boolean isNull(String str){
		return (str== null || "".equals(str.trim()));
	}

	/**
	 * 索赔结算单查询
	 */
	@Override
	public PageInfoDto claimBillQueryList(Map<String, String> queryParams) throws ServiceBizException {
		return claimDao.claimBills(queryParams);
	}

	/**
	 * 索赔结算单明细查询
	 */
	@Override
	public PageInfoDto claimBillOemDetail(Long balanceId) throws ServiceBizException {
		return claimDao.claimBillOemDetails(balanceId);
	}

	/**
	 * 索赔结算单dlr查询
	 */
	@Override
	public PageInfoDto claimBillQueryDLRList(Map<String, String> queryParams) throws ServiceBizException {
		return claimDao.claimBilldlrs(queryParams);
	}

}
