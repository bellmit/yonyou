package com.infoeai.eai.action.k4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.k4.K4SICommonDao;
import com.infoeai.eai.po.TiK4ClaimsNoticePO;
import com.infoeai.eai.vo.S0014VO;
import com.infoeai.eai.vo.returnVO;
import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TmCompanyPO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtClaimsNoticeDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtClaimsNoticeDetailPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
@Service
public class S0014Impl extends BaseService implements S0014 {
	private static final Logger logger = LoggerFactory.getLogger(S0014Impl.class);
	@Autowired
	K4SICommonDao dao;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String format = df.format(new Date());

	@Override
	public List<S0014VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception {

		List<S0014VO> voList = new ArrayList<S0014VO>();

		try {

			logger.info("====S0014 getXMLToVO() is START====");
			logger.info("====XML赋值到VO======");
			logger.info("====XMLSIZE:=======" + xmlList.size());

			if (xmlList != null && xmlList.size() > 0) {

				for (int i = 0; i < xmlList.size(); i++) {

					Map<String, String> map = xmlList.get(i);

					if (map != null && map.size() > 0) {

						S0014VO outVo = new S0014VO();
						outVo.setClamMonFrom(map.get("CLAM_MON_FROM")); // 索赔单开始时间
						outVo.setClamMonTo(map.get("CLAM_MON_TO")); // 索赔单结束时间
						outVo.setCcbackGAmt(map.get("CCBACK_G_AMT")); // 索赔扣款
						outVo.setBukrs(map.get("BUKRS")); // 公司代码
						outVo.setButxt(map.get("BUTXT")); // 公司名称
						outVo.setFAmt(map.get("F_AMT")); // 首保索赔金额
						outVo.setFRecs(map.get("F_RECS")); // 首保索赔记录数
						outVo.setGoodwAmt(map.get("GOODW_AMT")); // 善意索赔金额
						outVo.setGoodRecs(map.get("GOOD_RECS")); // 善意索赔记录数
						outVo.setMoparAmt(map.get("MOPAR_AMT")); // 新零件索赔金额
						outVo.setMoparRecs(map.get("MOPAR_RECS")); // 零件记录数
						outVo.setPdiAmt(map.get("PDI_AMT")); // 新车检查索赔金额
						outVo.setPdiRecs(map.get("PDI_RECS")); // 新车检查记录数
						outVo.setRecallAmt(map.get("RECALL_AMT")); // 召回索赔金额
						outVo.setReRecs(map.get("RE_RECS")); // 召回记录数
						outVo.setDealer(map.get("DEALER")); // 经销商代码
						outVo.setLifnr(map.get("LIFNR")); // 经销商代码
						outVo.setDealerName(map.get("DEALER_NAME")); // 经销商名称
						outVo.setTcrAmt(map.get("TCR_AMT")); // 运输索赔金额金额
						outVo.setTcrRecs(map.get("TCR_RECS")); // 运输索赔记录数
						outVo.setTotalAmt(map.get("TOTAL_AMT")); // 全部付款金额
						outVo.setTotalRecs(map.get("TOTAL_RECS")); // 全部付款记录数
						outVo.setVatInvoAmt(map.get("VAT_INVO_AMT")); // 增值税发票金额
						outVo.setDbnDate(map.get("DBN_DATE")); // 结算日期
						outVo.setDbnNo(map.get("DBN_NO")); // 结算流水号
						outVo.setDbnRemarks(map.get("DBN_REMARKS")); // VBN备注
						outVo.setWAmt(map.get("W_AMT")); // 保修索赔金额
						outVo.setWRecs(map.get("W_RECS")); // 保修索赔记录数
						outVo.setClaimSan(map.get("CLAIM_SAN")); // 索赔数
						outVo.setCrMemoC(map.get("CR_MEMO_C")); // 索赔付款日期
						outVo.setZlabTot(map.get("ZLAB_TOT")); // 工时
						outVo.setZpartTot(map.get("ZPART_TOT")); // 配件
						outVo.setSpecTot(map.get("SPEC_TOT")); // 特别总金额
						outVo.setTotAmt(map.get("TOT_AMT")); // 总金额
						outVo.setWarrDate(map.get("WARR_DATE")); // 保修开始日期
						outVo.setRecDate(map.get("REC_DATE")); // 维修日期
						outVo.setPartIdDes(map.get("PART_ID_DES")); // 配件描述
						outVo.setLopCausal(map.get("LOP_CAUSAL")); // LOP
						outVo.setLopsCond(map.get("LOPS_COND")); // 主因LOP
						outVo.setMRate(map.get("M_RATE")); // 加价率
						outVo.setOdom(map.get("ODOM")); // 里程
						outVo.setPartId(map.get("PART_ID")); // 配件代码
						outVo.setRemark(map.get("REMARK")); // 备注
						outVo.setTType(map.get("T_TYPE")); // 索赔类型
						outVo.setTTypeDes(map.get("T_TYPE_DES")); // 索赔类型描述
						outVo.setVhclFamilyCode(map.get("VHCL_FAMILY_CODE")); // 车系
						outVo.setVhclModelYear(map.get("VHCL_MODEL_YEAR")); // 年款
						outVo.setVin(map.get("VIN")); // 车架号
						outVo.setFailCodeDes(map.get("FAIL_CODE_DES")); // 大类描述
						outVo.setChbackRecs(map.get("CHBACK_RECS")); // 索赔扣款记录数
						outVo.setCcbackLAmt(map.get("CCBACK_L_AMT")); // 索赔扣款L
						outVo.setDFlag(map.get("D_FLAG")); // 删除标志
						outVo.setDinvoce(map.get("DINVOCE")); // 经销商发票
						outVo.setEIf(map.get("E_IF")); // E_IF
						outVo.setExtendedW(map.get("EXTENDED_W")); // 延保索赔金额
						outVo.setLocalgwAmt(map.get("LOCALGW_AMT")); // 当地商誉
						outVo.setLocalRecs(map.get("LOCAL_RECS")); // 当地商善记录数
						outVo.setOthersAmt(map.get("OTHERS_AMT")); // 其它索赔金额
						outVo.setOtherRecs(map.get("OTHER_RECS")); // 其他索赔记录数
						outVo.setClaimId(map.get("CLAIM_ID")); // 索赔编码
						outVo.setZprorat(map.get("ZPRORAT")); // 索赔帐号
						outVo.setGcsLc(map.get("GCS_LC")); // GCS索赔/本地索赔标识

						// update_date 20151208 by maxiang begin..
						outVo.setLmRmb(map.get("LM_RMB")); // 物料的费用
						outVo.setTpRmb(map.get("TP_RMB")); // 第三方费用
						outVo.setTType343(map.get("T_TYPE_343")); // 343保修类型
						outVo.setWorkhours(map.get("WORKHOURS")); // 343工时
						// update_date 20151208 by maxiang end..

						outVo.setRowId(map.get("ROW_ID")); // ROW_ID

						voList.add(outVo);

						logger.info("====outVo:====" + outVo);

					}
				}
			}

			logger.info("====S0014 getXMLToVO() is END====");
		} catch (Throwable e) {
			logger.info("==============XML赋值VO失败===================");
			logger.error(e.getMessage(), e);
			throw new Exception("S0014 XML转换处理异常！" + e);
		} finally {
			logger.info("====S0014 getXMLToVO() is finish====");
		}
		return voList;
	}

	/**
	 * 执行
	 * 
	 * @throws Throwable
	 */
	@Override
	public List<returnVO> execute(List<S0014VO> voList) throws Throwable {

		logger.info("====S0014 is begin====");

		List<returnVO> retVoList = new ArrayList<returnVO>();

		String[] returnVo = null;

		/******************** 开启事物 ********************/
		beginDbService();

		try {

			for (int i = 0; i < voList.size(); i++) {

				returnVo = S0014Check(voList.get(i)); // 校验 S0014VO 数据

				if (null == returnVo || returnVo.length <= 0) {

					k4BusinessProcess(voList.get(i)); // S0014 数据业务处理逻辑

				} else {

					/*
					 * 车辆接口表错误数据插入
					 */
					TiK4ClaimsNoticePO po = new TiK4ClaimsNoticePO();
					// po.setIfId(Long.parseLong(SequenceManager.getSequence("")));//
					// 接口ID
					po.setString("CLAM_MON_FROM", voList.get(i).getClamMonFrom()); // 索赔单开始时间
					po.setString("CLAM_MON_TO", voList.get(i).getClamMonTo()); // 索赔单结束时间
					po.setString("CCBACK_G_AMT", voList.get(i).getCcbackGAmt()); // 索赔扣款
					po.setString("BUKRS", voList.get(i).getBukrs()); // 公司代码
					po.setString("BUTXT", voList.get(i).getButxt()); // 公司名称
					po.setString("F_AMT", voList.get(i).getFAmt()); // 首保索赔金额
					po.setString("F_RECS", voList.get(i).getFRecs()); // 首保索赔记录数
					po.setString("GOODW_AMT", voList.get(i).getGoodwAmt()); // 善意索赔金额
					po.setString("GOOD_RECS", voList.get(i).getGoodRecs()); // 善意索赔记录数
					po.setString("MOPAR_AMT", voList.get(i).getMoparAmt()); // 新零件索赔金额
					po.setString("MOPAR_RECS", voList.get(i).getMoparRecs()); // 零件记录数
					po.setString("PDI_AMT", voList.get(i).getPdiAmt()); // 新车检查索赔金额
					po.setString("PDI_RECS", voList.get(i).getPdiRecs()); // 新车检查记录数
					po.setString("RECALL_AMT", voList.get(i).getRecallAmt()); // 召回索赔金额
					po.setString("RE_RECS", voList.get(i).getReRecs()); // 召回记录数
					po.setString("DEALER", voList.get(i).getDealer()); // 经销商代码
					po.setString("LIFNR", voList.get(i).getLifnr()); // 经销商代码
					po.setString("DEALER_NAME", voList.get(i).getDealerName()); // 经销商名称
					po.setString("TCR_AMT", voList.get(i).getTcrAmt()); // 运输索赔金额金额
					po.setString("TCR_RECS", voList.get(i).getTcrRecs()); // 运输索赔记录数
					po.setString("TOTAL_AMT", voList.get(i).getTotalAmt()); // 全部付款金额
					po.setString("TOTAL_RECS", voList.get(i).getTotalRecs()); // 全部付款记录数
					po.setString("VAT_INVO_AMT", voList.get(i).getVatInvoAmt()); // 增值税发票金额
					po.setString("DBN_DATE", voList.get(i).getDbnDate()); // 结算日期
					po.setString("DBN_NO", voList.get(i).getDbnNo()); // 结算流水号
					po.setString("DBN_REMARKS", voList.get(i).getDbnRemarks()); // VBN备注
					po.setString("W_AMT", voList.get(i).getWAmt()); // 保修索赔金额
					po.setString("W_RECS", voList.get(i).getWRecs()); // 保修索赔记录数
					po.setString("CLAIM_SAN", voList.get(i).getClaimSan()); // 索赔数
					po.setString("CR_MEMO_C", voList.get(i).getCrMemoC()); // 索赔付款日期
					po.setString("ZLAB_TOT", voList.get(i).getZlabTot()); // 工时
					po.setString("ZPART_TOT", voList.get(i).getZpartTot()); // 配件
					po.setString("SPEC_TOT", voList.get(i).getSpecTot()); // 特别总金额
					po.setString("TOT_AMT", voList.get(i).getTotAmt()); // 总金额
					po.setString("WARR_DATE", voList.get(i).getWarrDate()); // 保修开始日期
					po.setString("REC_DATE", voList.get(i).getRecDate()); // 维修日期
					po.setString("PART_ID_DES", voList.get(i).getPartIdDes()); // 配件描述
					po.setString("LOP_CAUSAL", voList.get(i).getLopCausal()); // LOP
					po.setString("LOPS_COND", voList.get(i).getLopsCond()); // 主因LOP
					po.setString("M_RATE", voList.get(i).getMRate()); // 加价率
					po.setString("ODOM", voList.get(i).getOdom()); // 里程
					po.setString("PART_ID", voList.get(i).getPartId()); // 配件代码
					po.setString("REMARK", voList.get(i).getRemark()); // 备注
					po.setString("T_TYPE", voList.get(i).getTType()); // 索赔类型
					po.setString("T_TYPE_DES", voList.get(i).getTTypeDes()); // 索赔类型描述
					po.setString("VHCL_FAMILY_CODE", voList.get(i).getVhclFamilyCode()); // 车系
					po.setString("VHCL_MODEL_YEAR", voList.get(i).getVhclModelYear()); // 年款
					po.setString("VIN", voList.get(i).getVin()); // 车架号
					po.setString("FAIL_CODE_DES", voList.get(i).getFailCodeDes()); // 大类描述
					po.setString("CHBACK_RECS", voList.get(i).getChbackRecs()); // 索赔扣款记录数
					po.setString("CCBACK_L_AMT", voList.get(i).getCcbackLAmt()); // 索赔扣款L
					po.setString("D_FLAG", voList.get(i).getDFlag()); // 删除标志
					po.setString("DINVOCE", voList.get(i).getDinvoce()); // 经销商发票
					po.setString("E_IF", voList.get(i).getEIf()); // E_IF
					po.setString("EXTENDED_W", voList.get(i).getExtendedW()); // 延保索赔金额
					po.setString("LOCALGW_AMT", voList.get(i).getLocalgwAmt()); // 当地商誉
					po.setString("LOCAL_RECS", voList.get(i).getLocalRecs()); // 当地商善记录数
					po.setString("OTHERS_AMT", voList.get(i).getOthersAmt()); // 其它索赔金额
					po.setString("OTHER_RECS", voList.get(i).getOtherRecs()); // 其他索赔记录数
					po.setString("CLAIM_ID", voList.get(i).getClaimId()); // 索赔编码
					po.setString("ZPRORAT", voList.get(i).getZprorat()); // 索赔帐号
					po.setString("GCS_LC", voList.get(i).getGcsLc()); // GCS索赔/本地索赔标识

					// update_date 20151208 by maxiang begin..
					po.setString("LM_RMB", voList.get(i).getLmRmb()); // 物料的费用
					po.setString("TP_RMB", voList.get(i).getTpRmb()); // 第三方费用
					po.setString("T_TYPE_343", voList.get(i).getTType343()); // 343保修类型
					po.setString("WORKHOURS", voList.get(i).getWorkhours()); // 343工时
					// update_date 20151208 by maxiang end..

					po.setString("ROW_ID", voList.get(i).getRowId()); // ROW_ID
					po.setInteger("IS_RESULT", OemDictCodeConstants.IF_TYPE_NO.toString()); // 是否成功（否）
					po.setString("IS_MESSAGE", returnVo[1]); // 错误
					po.setInteger("CREATE_BY", OemDictCodeConstants.K4_S0014); // 创建人ID
					po.setTimestamp("CREATE_DATE", format); // 创建日期
					po.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00); // 逻辑删除
					po.saveIt();

					/*
					 * 返回错误信息
					 */
					returnVO retVo = new returnVO();
					retVo.setOutput(returnVo[0]);
					retVo.setMessage(returnVo[1]);
					retVoList.add(retVo);
					logger.info("==============S0014 返回不合格数据============RowId====" + returnVo[0]);
					logger.info("==============S0014 返回不合格数据============Message====" + returnVo[1]);
				}
			}

			dbService.endTxn(true);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			dbService.endTxn(false);
			throw new Exception("S0014业务处理异常！" + e);
		} finally {
			logger.info("====S0014 is finish====");
			dbService.clean();
		}
		return retVoList;
		/******************** 结束事物 ********************/
	}

	private void k4BusinessProcess(S0014VO vo) throws Exception {

		logger.info("==============S0014 业务处理逻辑开始================");

		// 插入接口表
		TiK4ClaimsNoticePO po = new TiK4ClaimsNoticePO();
		// po.setIfId(Long.parseLong(SequenceManager.getSequence("")));// 接口ID
		po.setString("CLAM_MON_FROM", vo.getClamMonFrom()); // 索赔单开始时间
		po.setString("CLAM_MON_TO", vo.getClamMonTo()); // 索赔单结束时间
		po.setString("CCBACK_G_AMT", vo.getCcbackGAmt()); // 索赔扣款
		po.setString("BUKRS", vo.getBukrs()); // 公司代码
		po.setString("BUTXT", vo.getButxt()); // 公司名称
		po.setString("F_AMT", vo.getFAmt()); // 首保索赔金额
		po.setString("F_RECS", vo.getFRecs()); // 首保索赔记录数
		po.setString("GOODW_AMT", vo.getGoodwAmt()); // 善意索赔金额
		po.setString("GOOD_RECS", vo.getGoodRecs()); // 善意索赔记录数
		po.setString("MOPAR_AMT", vo.getMoparAmt()); // 新零件索赔金额
		po.setString("MOPAR_RECS", vo.getMoparRecs()); // 零件记录数
		po.setString("PDI_AMT", vo.getPdiAmt()); // 新车检查索赔金额
		po.setString("PDI_RECS", vo.getPdiRecs()); // 新车检查记录数
		po.setString("RECALL_AMT", vo.getRecallAmt()); // 召回索赔金额
		po.setString("RE_RECS", vo.getReRecs()); // 召回记录数
		po.setString("DEALER", vo.getDealer()); // 经销商代码
		po.setString("LIFNR", vo.getLifnr()); // 经销商代码
		po.setString("DEALER_NAME", vo.getDealerName()); // 经销商名称
		po.setString("TCR_AMT", vo.getTcrAmt()); // 运输索赔金额金额
		po.setString("TCR_RECS", vo.getTcrRecs()); // 运输索赔记录数
		po.setString("TOTAL_AMT", vo.getTotalAmt()); // 全部付款金额
		po.setString("TOTAL_RECS", vo.getTotalRecs()); // 全部付款记录数
		po.setString("VAT_INVO_AMT", vo.getVatInvoAmt()); // 增值税发票金额
		po.setString("DBN_DATE", vo.getDbnDate()); // 结算日期
		po.setString("DBN_NO", vo.getDbnNo()); // 结算流水号
		po.setString("DBN_REMARKS", vo.getDbnRemarks()); // VBN备注
		po.setString("W_AMT", vo.getWAmt()); // 保修索赔金额
		po.setString("W_RECS", vo.getWRecs()); // 保修索赔记录数
		po.setString("CLAIM_SAN", vo.getClaimSan()); // 索赔数
		po.setString("CR_MEMO_C", vo.getCrMemoC()); // 索赔付款日期
		po.setString("ZLAB_TOT", vo.getZlabTot()); // 工时
		po.setString("ZPART_TOT", vo.getZpartTot()); // 配件
		po.setString("SPEC_TOT", vo.getSpecTot()); // 特别总金额
		po.setString("TOT_AMT", vo.getTotAmt()); // 总金额
		po.setString("WARR_DATE", vo.getWarrDate()); // 保修开始日期
		po.setString("REC_DATE", vo.getRecDate()); // 维修日期
		po.setString("PART_ID_DES", vo.getPartIdDes()); // 配件描述
		po.setString("LOP_CAUSAL", vo.getLopCausal()); // LOP
		po.setString("LOPS_COND", vo.getLopsCond()); // 主因LOP
		po.setString("M_RATE", vo.getMRate()); // 加价率
		po.setString("ODOM", vo.getOdom()); // 里程
		po.setString("PART_ID", vo.getPartId()); // 配件代码
		po.setString("REMARK", vo.getRemark()); // 备注
		po.setString("T_TYPE", vo.getTType()); // 索赔类型
		po.setString("T_TYPE_DES", vo.getTTypeDes()); // 索赔类型描述
		po.setString("VHCL_FAMILY_CODE", vo.getVhclFamilyCode()); // 车系
		po.setString("VHCL_MODEL_YEAR", vo.getVhclModelYear()); // 年款
		po.setString("VIN", vo.getVin()); // 车架号
		po.setString("FAIL_CODE_DES", vo.getFailCodeDes()); // 大类描述
		po.setString("CHBACK_RECS", vo.getChbackRecs()); // 索赔扣款记录数
		po.setString("CCBACK_L_AMT", vo.getCcbackLAmt()); // 索赔扣款L
		po.setString("D_FLAG", vo.getDFlag()); // 删除标志
		po.setString("DINVOCE", vo.getDinvoce()); // 经销商发票
		po.setString("E_IF", vo.getEIf()); // E_IF
		po.setString("EXTENDED_W", vo.getExtendedW()); // 延保索赔金额
		po.setString("LOCALGW_AMT", vo.getLocalgwAmt()); // 当地商誉
		po.setString("LOCAL_RECS", vo.getLocalRecs()); // 当地商善记录数
		po.setString("OTHERS_AMT", vo.getOthersAmt()); // 其它索赔金额
		po.setString("OTHER_RECS", vo.getOtherRecs()); // 其他索赔记录数
		po.setString("CLAIM_ID", vo.getClaimId()); // 索赔编码
		po.setString("ZPRORAT", vo.getZprorat()); // 索赔帐号
		po.setString("GCS_LC", vo.getGcsLc()); // GCS索赔/本地索赔标识

		po.setDouble("TP_RMB", vo.getTpRmb()); // 第三方费用
		po.setString("T_TYPE_343", vo.getTType343()); // 343保修类型
		po.setString("WORKHOURS", vo.getWorkhours()); // 343工时

		po.setString("ROW_ID", vo.getRowId()); // ROW_ID
		po.setInteger("IS_RESULT", OemDictCodeConstants.IF_TYPE_YES.toString()); // 是否成功（是）
		po.setInteger("CREATE_BY", OemDictCodeConstants.K4_S0014); // 创建人ID
		po.setTimestamp("CREATE_DATE", format); // 创建日期
		po.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00); // 逻辑删除
		po.saveIt();

		/*
		 * 根据结算流水号查询结算单是否存在
		 */
		TtClaimsNoticeDcsPO claimsNoticePO = new TtClaimsNoticeDcsPO();
		// claimsNoticePO.setVbnNo(vo.getDbnNo());

		List<TtClaimsNoticeDcsPO> claimsNoticePOList = TtClaimsNoticeDcsPO.find("Vbn_No=?", vo.getDbnNo());

		/*
		 * 如结算单已存在，则只添加明细表
		 */
		if (null != claimsNoticePOList && claimsNoticePOList.size() > 0) {

			Long claimsBillingId = (Long) claimsNoticePOList.get(0).get("Claims_Billing_Id");

			// 插入索赔结算单业务明细表
			TtClaimsNoticeDetailPO claimsNoticeDetail = new TtClaimsNoticeDetailPO();
			// claimsNoticeDetail.setClaimsBillingDetailId(Long.parseLong(SequenceManager.getSequence("")));
			// // 主键ID
			claimsNoticeDetail.setString("Claims_Billing_Id", claimsBillingId); // 外键ID
			claimsNoticeDetail.setString("Claim_No", vo.getClaimId()); // 索赔数
			claimsNoticeDetail.setString("Claim_Number", vo.getClaimSan()); // 索赔编码
			claimsNoticeDetail.setString("Claim_Payment_Cycle", vo.getCrMemoC()); // 索赔付款日期
			claimsNoticeDetail.setString("Company_Code", vo.getBukrs()); // 公司代码
			claimsNoticeDetail.setString("Cond_Labor_Amt_Calc", Utility.getDouble(vo.getZlabTot())); // 工时
			claimsNoticeDetail.setString("Cond_Parts_Amt_Calc", Utility.getDouble(vo.getZpartTot())); // 配件
			claimsNoticeDetail.setString("Cond_Spl_Srv_Amt_Calc", Utility.getDouble(vo.getSpecTot())); // 特殊项目
			claimsNoticeDetail.setString("Cond_Total_Amt_Calc", Utility.getDouble(vo.getTotAmt())); // 总金额

			claimsNoticeDetail.setTimestamp("Date_Vhcl_In_Svc", ddMMyyyy2Date(vo.getWarrDate(), "/")); // 保修开始日期
			claimsNoticeDetail.setTimestamp("Date_Vhcl_Recd", ddMMyyyy2Date(vo.getRecDate(), "/")); // 维修日期

			claimsNoticeDetail.setString("Failed_Part_Desc", vo.getPartIdDes()); // 配件描述
			claimsNoticeDetail.setString("Gcs_Lc", vo.getGcsLc()); // GCS索赔/本地索赔标识
			claimsNoticeDetail.setString("Lop_Causal", vo.getLopCausal()); // LOP
			claimsNoticeDetail.setString("Lops_On_Condition_All", vo.getLopsCond()); // 主因LOP
			claimsNoticeDetail.setString("Mopar_Peg_Rate", Float.parseFloat(vo.getMRate())); // 加价率
			claimsNoticeDetail.setString("Odometer_Vhcl_Recd", vo.getOdom()); // 里程
			claimsNoticeDetail.setString("Part_Failed", vo.getPartId()); // 配件代码
			claimsNoticeDetail.setString("Remarks", vo.getRemark()); // 备注
			claimsNoticeDetail.setString("Svc_Dealer", vo.getDealer()); // SVC经销商
			claimsNoticeDetail.setString("Svc_Dealer_Nam", vo.getDealerName()); // SVC经销商名字
			claimsNoticeDetail.setString("Transaction_Type", vo.getTType()); // 索赔类型
			claimsNoticeDetail.setString("Transaction_Type_Desc", vo.getTTypeDes()); // 索赔类型描述
			claimsNoticeDetail.setString("VBN_NO", vo.getDbnNo()); // 结算流水号
			claimsNoticeDetail.setString("Vhcl_Family_Code", vo.getVhclFamilyCode()); // 车系
			claimsNoticeDetail.setString("Vhcl_Model_Year", vo.getVhclModelYear()); // 年款
			claimsNoticeDetail.setString("Vin", vo.getVin()); // 车架号
			claimsNoticeDetail.setString("X_FALUR_LOP_DESC", vo.getFailCodeDes()); // 大类描述
			claimsNoticeDetail.setInteger("Create_By", OemDictCodeConstants.K4_S0014); // 创建人ID
			claimsNoticeDetail.setTimestamp("Create_Date", new Date()); // 创建日期
			claimsNoticeDetail.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除

			claimsNoticeDetail.saveIt(); // 插入索赔结算单业务明细表

		} else {

			// 插入索赔结算单业务表
			TtClaimsNoticeDcsPO claimsNotice = new TtClaimsNoticeDcsPO();
			// Long claimsBillingId =
			// Long.parseLong(SequenceManager.getSequence(""));
			// claimsNotice.setClaimsBillingId(claimsBillingId); // 主键ID
			claimsNotice.setString("Cclaims_Month_Cycle_From", vo.getClamMonFrom()); // 索赔单开始时间
			claimsNotice.setString("Cclaims_Month_Cycle_To", vo.getClamMonTo()); // 索赔单结束时间
			claimsNotice.setLong("CHARGERBACK_RECS", Long.parseLong(vo.getChbackRecs())); // 索赔扣款记录数
			claimsNotice.setDouble("Claim_Chargerback_Amt", Utility.getDouble(vo.getCcbackGAmt())); // 索赔扣款
			claimsNotice.setDouble("Claim_Chargerback_Amt_L", Utility.getDouble(vo.getCcbackLAmt())); // 索赔扣款L
			claimsNotice.setString("Company_Code", vo.getBukrs()); // 公司代码
			claimsNotice.setString("Company_Name", vo.getButxt()); // 公司名称
			claimsNotice.setDouble("Cond_Labor_Amt_Calc", Utility.getDouble(vo.getZlabTot())); // 零部件金额
			claimsNotice.setDouble("Cond_Parts_Amt_Calc", Utility.getDouble(vo.getZpartTot())); // 人工金额
			claimsNotice.setDouble("Cond_Spl_Srv_Amt_Calc", Utility.getDouble(vo.getSpecTot())); // 特别总金额
			claimsNotice.setString("IS_DEL", vo.getDFlag()); // 删除标志
			claimsNotice.setString("Dealer_Invoice", vo.getDinvoce()); // 经销商发票
			claimsNotice.setString("E_If", vo.getEIf()); // E_IF
			claimsNotice.setDouble("Extended_W", Utility.getDouble(vo.getExtendedW())); // 延保索赔金额
			claimsNotice.setDouble("F_Amt", Utility.getDouble(vo.getFAmt())); // 首保索赔金额
			claimsNotice.setString("F_Recs", Long.parseLong(vo.getFRecs())); // 首保索赔记录数
			claimsNotice.setDouble("Goodwill_Amt", Utility.getDouble(vo.getGoodwAmt())); // 善意索赔金额
			claimsNotice.setLong("Goodwill_Recs", Long.parseLong(vo.getGoodRecs())); // 善意索赔记录数
			claimsNotice.setDouble("Local_Goodwill", Utility.getDouble(vo.getLocalgwAmt())); // 当地商誉
			claimsNotice.setLong("Localgoodwill_Recs", Long.parseLong(vo.getLocalRecs())); // 当地商善记录数
			claimsNotice.setDouble("Mopar_Amt", Utility.getDouble(vo.getMoparAmt())); // 新零件索赔金额
			claimsNotice.setLong("Mopar_Recs", Long.parseLong(vo.getMoparRecs())); // 零件记录数
			claimsNotice.setDouble("Others_Amt", Utility.getDouble(vo.getOthersAmt())); // 其它索赔金额
			claimsNotice.setLong("Others_Recs", Long.parseLong(vo.getOtherRecs())); // 其他索赔记录数
			claimsNotice.setDouble("Pdi_Amt", Utility.getDouble(vo.getPdiAmt())); // 新车检查索赔金额
			claimsNotice.setLong("Pdi_Recs", Long.parseLong(vo.getPdiRecs())); // 新车检查记录数
			claimsNotice.setDouble("Recall_Amt", Utility.getDouble(vo.getRecallAmt())); // 召回索赔金额
			claimsNotice.setLong("Recall_Recs", Long.parseLong(vo.getReRecs())); // 召回记录数
			claimsNotice.setString("Svc_Dealer", getDealerCodeBySwt(vo.getDealer(), 2)); // 经销商代码
			claimsNotice.setString("Svc_Dealer_Name", vo.getDealerName()); // 经销商名称
			claimsNotice.setDouble("Tcr_Amt", Utility.getDouble(vo.getTcrAmt())); // 运输索赔金额金额
			claimsNotice.setLong("Tcr_Recs", Long.parseLong(vo.getTcrRecs())); // 运输索赔记录数
			claimsNotice.setDouble("Total_Amt", Utility.getDouble(vo.getTotalAmt())); // 全部付款金额
			claimsNotice.setLong("Total_Recs", Long.parseLong(vo.getTotalRecs())); // 全部付款记录数
			claimsNotice.setDouble("Vat_Invoice_Amount", Utility.getDouble(vo.getVatInvoAmt())); // 增值税发票金额
			claimsNotice.setString("Vbn_Date", yyyyMMdd2Date(vo.getDbnDate())); // 结算日期
			claimsNotice.setString("Vbn_No", vo.getDbnNo()); // 结算流水号
			claimsNotice.setString("Vbn_Remarks", vo.getDbnRemarks()); // VBN备注
			claimsNotice.setDouble("W_Amt", Utility.getDouble(vo.getWAmt())); // 保修索赔金额
			claimsNotice.setLong("W_Recs", Long.parseLong(vo.getWRecs())); // 保修索赔记录数
			claimsNotice.setDouble("Zprorat", Utility.getDouble(vo.getZprorat())); // 索赔帐号

			claimsNotice.setDouble("Lm_Rmb", Utility.getDouble(vo.getLmRmb())); // 物料的费用
			claimsNotice.setDouble("Tp_Rmb", Utility.getDouble(vo.getTpRmb())); // 第三方费用
			claimsNotice.setString("T_Type343", vo.getTType343()); // 343保修类型
			claimsNotice.setDouble("Workhours", Utility.getDouble(vo.getWorkhours())); // 343工时

			claimsNotice.setInteger("Is_Invoice", OemDictCodeConstants.CLAIMS_INVOICE_STATUS_01); // 是否开票
			claimsNotice.setInteger("Create_By", OemDictCodeConstants.K4_S0014); // 创建人ID
			claimsNotice.setTimestamp("Create_Date", new Date()); // 创建日期
			claimsNotice.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除
			claimsNotice.saveIt();

			// 插入索赔结算单业务明细表
			TtClaimsNoticeDetailPO claimsNoticeDetail = new TtClaimsNoticeDetailPO();
			// claimsNoticeDetail.setClaimsBillingDetailId("ClaimsBillingDetailId",
			// Long.parseLong(SequenceManager.getSequence(""))); // 主键ID
			// claimsNoticeDetail.setClaimsBillingId(claimsBillingId); // 外键ID
			claimsNoticeDetail.setString("Claim_No", vo.getClaimId()); // 索赔数
			claimsNoticeDetail.setString("Claim_Number", vo.getClaimSan()); // 索赔编码
			claimsNoticeDetail.setString("Claim_Payment_Cycle", vo.getCrMemoC()); // 索赔付款日期
			claimsNoticeDetail.setString("Company_Code", vo.getBukrs()); // 公司代码
			claimsNoticeDetail.setDouble("Cond_Labor_Amt_Calc", Utility.getDouble(vo.getZlabTot())); // 工时
			claimsNoticeDetail.setDouble("Cond_Parts_Amt_Calc", Utility.getDouble(vo.getZpartTot())); // 配件
			claimsNoticeDetail.setDouble("Cond_Spl_Srv_Amt_Calc", Utility.getDouble(vo.getSpecTot())); // 特殊项目
			claimsNoticeDetail.setDouble("Cond_Total_Amt_Calc", Utility.getDouble(vo.getTotAmt())); // 总金额

			claimsNoticeDetail.setTimestamp("Date_Vhcl_In_Svc", ddMMyyyy2Date(vo.getWarrDate(), "/")); // 保修开始日期
			claimsNoticeDetail.setTimestamp("Date_Vhcl_Recd", ddMMyyyy2Date(vo.getRecDate(), "/")); // 维修日期

			claimsNoticeDetail.setString("Failed_Part_Desc", vo.getPartIdDes()); // 配件描述
			claimsNoticeDetail.setString("Gcs_Lc", vo.getGcsLc()); // GCS索赔/本地索赔标识
			claimsNoticeDetail.setString("Lop_Causal", vo.getLopCausal()); // LOP
			claimsNoticeDetail.setString("Lops_On_Condition_All", vo.getLopsCond()); // 主因LOP
			claimsNoticeDetail.setFloat("Mopar_Peg_Rate", Float.parseFloat(vo.getMRate())); // 加价率
			claimsNoticeDetail.setString("Odometer_Vhcl_Recd", vo.getOdom()); // 里程
			claimsNoticeDetail.setString("Part_Failed", vo.getPartId()); // 配件代码
			claimsNoticeDetail.setString("Remarks", vo.getRemark()); // 备注
			claimsNoticeDetail.setString("Svc_Dealer", getDealerCodeBySwt(vo.getDealer(), 1)); // 经销商代码
			claimsNoticeDetail.setString("Svc_Dealer", vo.getDealerName()); // SVC经销商名字
			claimsNoticeDetail.setString("Transaction_Type", vo.getTType()); // 索赔类型
			claimsNoticeDetail.setString("Transaction_Type_Desc", vo.getTTypeDes()); // 索赔类型描述
			claimsNoticeDetail.setString("Vbn_No", vo.getDbnNo()); // 结算流水号
			claimsNoticeDetail.setString("Vhcl_Family_Code", vo.getVhclFamilyCode()); // 车系
			claimsNoticeDetail.setString("Vhcl_Model_Year", vo.getVhclModelYear()); // 年款
			claimsNoticeDetail.setString("Vin", vo.getVin()); // 车架号
			claimsNoticeDetail.setString("X_Falur_Lop_Desc", vo.getFailCodeDes()); // 大类描述
			claimsNoticeDetail.setInteger("Create_By", OemDictCodeConstants.K4_S0014); // 创建人ID
			claimsNoticeDetail.setTimestamp("Create_Date", format); // 创建日期
			claimsNoticeDetail.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除

			claimsNoticeDetail.saveIt(); // 插入索赔结算单业务明细表

		}

		logger.info("==============S0014 业务处理逻辑结束================");

	}

	private Date yyyyMMdd2Date(String yyyyMMdd) {

		if (null == yyyyMMdd || yyyyMMdd.trim().equals("") || yyyyMMdd.toLowerCase().trim().equals("null")) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(yyyyMMdd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 根据SWT经销商代码获得DCS经销商代码
	 * 
	 * @param swt
	 * @param flag
	 * @return
	 */
	private String getDealerCodeBySwt(String swt, int flag) {

		List<TmCompanyPO> comList = null;

		List<TmDealerPO> derList = null;

		Long companyId = null;

		String dealerCode = null;

		comList = TmCompanyPO.find("Swt_Code=?", swt);
		if (comList != null && comList.size() > 0) {

			TmCompanyPO ret = comList.get(0);

			companyId = (Long) ret.get("Company_Id");

			TmDealerPO dealer = new TmDealerPO();

			if (flag == 1) {
				// 销售经销商
				dealer.setInteger("Dealer_Type", OemDictCodeConstants.DEALER_TYPE_DVS);
				derList = TmDealerPO.find("Dealer_Type=? and company_Id=?", OemDictCodeConstants.DEALER_TYPE_DVS,
						companyId);
			} else {
				// 售后经销商
				dealer.setInteger("Dealer_Type", OemDictCodeConstants.DEALER_TYPE_DWR);
				derList = TmDealerPO.find("Dealer_Type=? and company_Id=?", OemDictCodeConstants.DEALER_TYPE_DWR,
						companyId);
			}

			dealer.setLong("company_Id", companyId);

			if (derList != null && derList.size() > 0) {
				TmDealerPO code = derList.get(0);
				dealerCode = (String) code.get("Dealer_Code");
			} else {
				dealerCode = swt;
			}

		} else {
			dealerCode = swt;
		}

		return dealerCode;

	}

	private Date ddMMyyyy2Date(String ddMMyyyy, String token) {

		if (null == ddMMyyyy || ddMMyyyy.trim().equals("") || ddMMyyyy.toLowerCase().trim().equals("null")
				|| ddMMyyyy.length() != 10) {
			return null;
		}

		String[] array = ddMMyyyy.split(token);
		String dd = array[0];
		String MM = array[1];
		String yyyy = array[2];

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(yyyy + MM + dd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	private String[] S0014Check(S0014VO vo) {

		logger.info("==============S0014 校验逻辑开始================");

		String[] returnVo = new String[2];

		// ROW_ID 非空校验
		if (CheckUtil.checkNull(vo.getRowId())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "ROW_ID 为空";
			return returnVo;
		}

		// VIN号非空校验
		if (CheckUtil.checkNull(vo.getVin())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "VIN号为空";
			return returnVo;
		}

		// VIN号长度校验
		if (vo.getVin().length() != 17) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "VIN号长度与接口定义不一致：" + vo.getVin();
			return returnVo;
		}

		// 校验VIN号是否有效
		int result = doVinCheck(vo.getVin());
		if (result == OemDictCodeConstants.IF_TYPE_NO) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "VIN号不存在：" + vo.getVin();
			return returnVo;
		}

		/*
		 * // 索赔单开始时间非空校验 if (CheckUtil.checkNull(vo.getClamMonFrom())) {
		 * returnVo[0] = vo.getRowId(); returnVo[1] = "索赔单开始时间为空"; return
		 * returnVo; }
		 * 
		 * // 索赔单开始时间长度校验 if (vo.getClamMonFrom().length() != 6) { returnVo[0] =
		 * vo.getRowId(); returnVo[1] = "索赔单开始时间长度与接口定义不一致：" +
		 * vo.getClamMonFrom(); return returnVo; }
		 * 
		 * // 索赔单结束时间非空校验 if (CheckUtil.checkNull(vo.getClamMonTo())) {
		 * returnVo[0] = vo.getRowId(); returnVo[1] = "索赔单结束时间为空"; return
		 * returnVo; }
		 * 
		 * // 索赔单结束时间长度校验 if (vo.getClamMonTo().length() != 6) { returnVo[0] =
		 * vo.getRowId(); returnVo[1] = "索赔单结束时间长度与接口定义不一致：" +
		 * vo.getClamMonTo(); return returnVo; }
		 */

		// 经销商代码非空校验
		if (CheckUtil.checkNull(vo.getLifnr())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "经销商代码为空";
			return returnVo;
		}

		// 结算流水号非空校验
		if (CheckUtil.checkNull(vo.getDbnNo())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "结算流水号为空";
			return returnVo;
		}

		/*
		 * // 索赔数非空校验 if (CheckUtil.checkNull(vo.getClaimSan())) { returnVo[0] =
		 * vo.getRowId(); returnVo[1] = "索赔数为空"; return returnVo; }
		 * 
		 * // 车系非空校验 if (CheckUtil.checkNull(vo.getVhclFamilyCode())) {
		 * returnVo[0] = vo.getRowId(); returnVo[1] = "车系为空"; return returnVo; }
		 * 
		 * // 年款非空校验 if (CheckUtil.checkNull(vo.getVhclModelYear())) {
		 * returnVo[0] = vo.getRowId(); returnVo[1] = "年款为空"; return returnVo; }
		 */

		return null;
	}

	private int doVinCheck(String vin) {

		List<TmVehiclePO> list = TmVehiclePO.find("vin=?", vin);

		/*
		 * 校验车架号是否已存在
		 */
		if (list.size() > 0) {
			return OemDictCodeConstants.IF_TYPE_YES; // 是
		} else {
			return OemDictCodeConstants.IF_TYPE_NO; // 否
		}
	}

}
