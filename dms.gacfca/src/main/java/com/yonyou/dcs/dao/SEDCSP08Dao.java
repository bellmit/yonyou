package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.infoservice.dms.cgcsl.vo.SEDCSP08DetailInfoVO;
import com.infoservice.dms.cgcsl.vo.SEDCSP08NoteInfoVO;
import com.infoservice.dms.cgcsl.vo.SEDCSP08VO;
import com.yonyou.dms.DTO.gacfca.SEDCSP08DTO;
import com.yonyou.dms.DTO.gacfca.SEDCSP08DetailInfoDTO;
import com.yonyou.dms.DTO.gacfca.SEDCSP08NoteInfoDTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.PartCommonDao;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SEDCSP08Dao extends OemBaseDAO {
	private static final Logger logger = LoggerFactory.getLogger(SEDCSP08Dao.class);

	@Autowired
	PartCommonDao commonDao;
	

	/**
	 * 到sap查询是否有数据
	 * @param vo
	 * @return
	 */
	public List<SEDCSP08VO> querySEDCSP08VOByDCS(SEDCSP08VO vo) throws Exception{
		List<SEDCSP08VO> list = new ArrayList<SEDCSP08VO>();
		SEDCSP08VO voDCS = new SEDCSP08VO();
		String IZzcliente = commonDao.getFCACodeByCompanyId(Long.parseLong(vo.getIzzcliente())); //特约店7位编码    CompanyId转换成FACCode
		// 因为接口现在IDealerUsr长度为15位，所以截取掉前面2位'20'
		String IDealerUsr = vo.getIdealerUsr();//特约店用户
		if (IDealerUsr !=null && IDealerUsr.length() > 15){
			IDealerUsr = IDealerUsr.substring(2);
		}
		List<SEDCSP08NoteInfoVO> tbVbelnList = vo.getTbVbeln();
		if (CommonUtils.isNullString(IZzcliente)) {
			logger.info("====IZzcliente 特约店7位编码 不能为空====");
			voDCS.setResultInfo("特约店7位编码不能为空");
			list.add(voDCS);
			return list;
		} 
		if (CommonUtils.isNullString(IDealerUsr)) {
			logger.info("====IDealerUsr 特约店用户 不能为空====");
			voDCS.setResultInfo("特约店用户 不能为空");
			list.add(voDCS);
			return list;
		} 
		if (null == tbVbelnList || tbVbelnList.size() < 0) {
			logger.info("====tbVbelnList 运单号 不能为空====");
			voDCS.setResultInfo("运单号 不能为空");
			list.add(voDCS);
			return list;
		}
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_BindingStub stub =
				new com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_BindingStub();
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_Service lmsService =
				new com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_ServiceLocator();
		stub = (com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_BindingStub)lmsService.getZLVSDWS_L26_OUT();
		logger.info("----------------调用对方服务地址："+lmsService.getZLVSDWS_L26_OUTAddress()+"----------------");
		String IMarca = OemDictCodeConstants.IMARCA;//品牌
		String IWerks = OemDictCodeConstants.IWERKS;//Plant (Own or External)
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26OutHolder tbOutputHeader = new 
				com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26OutHolder();
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26PosOutHolder tbOutputPosition = new 
				com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26PosOutHolder();
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfBapiret1Holder tbReturn = new 
				com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfBapiret1Holder();
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26VbelnHolder tbVbeln = new 
				com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26VbelnHolder();
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Vbeln[] vbelns = new 
				com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Vbeln[tbVbelnList.size()];
		for (int i=0;i<tbVbelnList.size();i++) {
			SEDCSP08NoteInfoVO infoVo = tbVbelnList.get(i);
			com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Vbeln vbeln = new 
					com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Vbeln();
			vbeln.setOpVbeln(infoVo.getOpVbeln());
			vbelns[i] = vbeln;
		}
		tbVbeln.value = vbelns;
		//DMS获得数据 然后传给SAP 然后在给DMS
		logger.info("====SEDCSP08 IDealerUsr ====:"+IDealerUsr);
		logger.info("====SEDCSP08 IMarca ====:"+IMarca);
		logger.info("====SEDCSP08 IWerks ====:"+IWerks);
		logger.info("====SEDCSP08 tbVbeln ====:"+tbVbeln);
		stub.zlvsdwsL26Out(
				IDealerUsr,
				IMarca, 
				IWerks,
				IZzcliente, 
				tbOutputHeader,
				tbOutputPosition, 
				tbReturn, 
				tbVbeln);
		//把返回的sap值封装到list中 然后返回给下端
		if (tbOutputHeader != null && tbOutputHeader.value.length > 0) {
					com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Out[] outs =  tbOutputHeader.value;
					com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Out out = outs[0];
					voDCS.setOpWerks(out.getOpWerks());
					voDCS.setOpZzcliente(out.getOpZzcliente());
					voDCS.setOpMarca(out.getOpMarca());
					voDCS.setOpDealerUsr(out.getOpDealerUsr());
					voDCS.setOpVbeln(out.getOpVbeln());
					voDCS.setOpAudat(out.getOpAudat());
					voDCS.setOpAuart(out.getOpAuart());
					voDCS.setOpOrdstatus(out.getOpOrdstatus());
					voDCS.setOpShipto(out.getOpShipto());
					voDCS.setOpBillto(out.getOpBillto());
					voDCS.setOpRespstaff(out.getOpRespstaff());
					voDCS.setOpNote(out.getOpNote());
					voDCS.setOpWarrDate(out.getOpWarrDate());
					voDCS.setOpVinCode(out.getOpVinCode());
					voDCS.setOpElecCode(out.getOpElecCode());
					voDCS.setOpMechCode(out.getOpMechCode());
					voDCS.setOpPlate(out.getOpPlate());
					voDCS.setOpLocDate(out.getOpLocDate());
					voDCS.setOpDelivery(out.getOpDelivery());
					voDCS.setOpRefNumber(out.getOpRefNumber());
					logger.info("====SEDCSP08 out.getOpWarrDate() ====:"+out.getOpWarrDate());
					logger.info("====SEDCSP08 out.getOpLocDate() ====:"+out.getOpLocDate());
					if (tbOutputPosition != null && tbOutputPosition.value.length > 0) {
						com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26PosOut[] posouts = tbOutputPosition.value;
						LinkedList<SEDCSP08DetailInfoVO> detailInfo = new LinkedList<SEDCSP08DetailInfoVO>();
						for (int i=0;i<tbOutputPosition.value.length;i++) {
							com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26PosOut posOut = posouts[i];
							SEDCSP08DetailInfoVO detailVo = new SEDCSP08DetailInfoVO();
							detailVo.setOpWerks(posOut.getOpWerks());
							detailVo.setOpZzcliente(posOut.getOpZzcliente());
							detailVo.setOpMarca(posOut.getOpMarca());
							detailVo.setOpDealerUsr(posOut.getOpDealerUsr());
							detailVo.setOpVbeln(posOut.getOpVbeln());
							detailVo.setOpDelCharge(posOut.getOpDelCharge().doubleValue());
							detailVo.setOpInvvbeln(posOut.getOpInvvbeln());
							detailVo.setOpInvdate(posOut.getOpInvdate());
							detailVo.setOpDelvbeln(posOut.getOpDelvbeln());
							detailVo.setOpDeldate(posOut.getOpDeldate());
							detailVo.setOpTxdeldate(posOut.getOpTxdeldate());
							detailVo.setOpKeytype(posOut.getOpKeytype());
							detailVo.setOpOrdstatus(posOut.getOpOrdstatus());
							detailVo.setOpMabnr(posOut.getOpMabnr());
							detailVo.setOpArktx(posOut.getOpArktx());
							detailVo.setOpFklmg(posOut.getOpFklmg().doubleValue());
							detailVo.setOpFklmgFga(posOut.getOpFklmgFga().doubleValue());
							detailVo.setOpRetailprice(posOut.getOpRetailprice().doubleValue());
							detailVo.setOpDiscount(posOut.getOpDiscount().doubleValue());
							detailVo.setPosnr(posOut.getPosnr());
							detailVo.setPosex(posOut.getPosex());
							detailVo.setOpInvitem(posOut.getOpInvitem());
							detailVo.setOpDelitem(posOut.getOpDelitem());
							detailInfo.add(detailVo);
						}
						voDCS.setDetailInfo(detailInfo);
					}
					list.add(voDCS);
		} else {
			com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.Bapiret1[] bapiret1s = tbReturn.value;
			if (bapiret1s != null && bapiret1s.length > 0) {
				list = new ArrayList<SEDCSP08VO>();
				for (int i=0;i<bapiret1s.length;i++) {
					voDCS = new SEDCSP08VO();
					com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.Bapiret1 bapiret1 = bapiret1s[i];
					//错误信息返回
					String errorInfo = bapiret1.getMessage();
					voDCS.setResultInfo(errorInfo);
					logger.info("sap 返回结果失败 :"+errorInfo);
					list.add(voDCS);
				}
				list.add(voDCS);
			}
		}
		return list;
	}
	
	/**
	 * 到sap查询是否有数据
	 * @param vo
	 * @return
	 */
	public List<SEDCSP08VO> querySEDCSP08VO(SEDCSP08VO vo) throws Exception{
		List<SEDCSP08VO> list = new ArrayList<SEDCSP08VO>();
		SEDCSP08VO voDCS = new SEDCSP08VO();
		String IZzcliente = 
				commonDao.getFCACodeByEntityCode(vo.getIzzcliente()); //特约店7位编码    entityCode转换成FACCode
		// 因为接口现在IDealerUsr长度为15位，所以截取掉前面2位'20'
		String IDealerUsr = vo.getIdealerUsr();//特约店用户
		if (IDealerUsr !=null && IDealerUsr.length() > 15){
			IDealerUsr = IDealerUsr.substring(2);
		}
		List<SEDCSP08NoteInfoVO> tbVbelnList = vo.getTbVbeln();
		if (CommonUtils.isNullString(IZzcliente)) {
			logger.info("====IZzcliente 特约店7位编码 不能为空====");
			voDCS.setResultInfo("特约店7位编码不能为空");
			list.add(voDCS);
			return list;
		} 
		if (CommonUtils.isNullString(IDealerUsr)) {
			logger.info("====IDealerUsr 特约店用户 不能为空====");
			voDCS.setResultInfo("特约店用户 不能为空");
			list.add(voDCS);
			return list;
		} 
		if (null == tbVbelnList || tbVbelnList.size() < 0) {
			logger.info("====tbVbelnList 运单号 不能为空====");
			voDCS.setResultInfo("运单号 不能为空");
			list.add(voDCS);
			return list;
		}
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_BindingStub stub =
				new com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_BindingStub();
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_Service lmsService =
				new com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_ServiceLocator();
		stub = (com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_BindingStub)lmsService.getZLVSDWS_L26_OUT();
		logger.info("----------------调用对方服务地址："+lmsService.getZLVSDWS_L26_OUTAddress()+"----------------");
		String IMarca = OemDictCodeConstants.IMARCA;//品牌
		String IWerks = OemDictCodeConstants.IWERKS;//Plant (Own or External)
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26OutHolder tbOutputHeader = new 
				com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26OutHolder();
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26PosOutHolder tbOutputPosition = new 
				com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26PosOutHolder();
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfBapiret1Holder tbReturn = new 
				com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfBapiret1Holder();
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26VbelnHolder tbVbeln = new 
				com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26VbelnHolder();
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Vbeln[] vbelns = new 
				com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Vbeln[tbVbelnList.size()];
		for (int i=0;i<tbVbelnList.size();i++) {
			SEDCSP08NoteInfoVO infoVo = tbVbelnList.get(i);
			com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Vbeln vbeln = new 
					com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Vbeln();
			vbeln.setOpVbeln(infoVo.getOpVbeln());
			vbelns[i] = vbeln;
		}
		tbVbeln.value = vbelns;
		//DMS获得数据 然后传给SAP 然后在给DMS
		logger.info("====SEDCSP08 IDealerUsr ====:"+IDealerUsr);
		logger.info("====SEDCSP08 IMarca ====:"+IMarca);
		logger.info("====SEDCSP08 IWerks ====:"+IWerks);
		logger.info("====SEDCSP08 tbVbeln ====:"+tbVbeln);
		stub.zlvsdwsL26Out(
				IDealerUsr,
				IMarca, 
				IWerks,
				IZzcliente, 
				tbOutputHeader,
				tbOutputPosition, 
				tbReturn, 
				tbVbeln);
		//把返回的sap值封装到list中 然后返回给下端
		if (tbOutputHeader != null && tbOutputHeader.value.length > 0) {
					com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Out[] outs =  tbOutputHeader.value;
					com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Out out = outs[0];
					voDCS.setOpWerks(out.getOpWerks());
					voDCS.setOpZzcliente(
							commonDao.getDealerEntityCodeByFCACode(out.getOpZzcliente())
							);
					voDCS.setOpMarca(out.getOpMarca());
					voDCS.setOpDealerUsr(out.getOpDealerUsr());
					voDCS.setOpVbeln(out.getOpVbeln());
					voDCS.setOpAudat(out.getOpAudat());
					voDCS.setOpAuart(out.getOpAuart());
					voDCS.setOpOrdstatus(out.getOpOrdstatus());
					voDCS.setOpShipto(out.getOpShipto());
					voDCS.setOpBillto(out.getOpBillto());
					voDCS.setOpRespstaff(out.getOpRespstaff());
					voDCS.setOpNote(out.getOpNote());
					voDCS.setOpWarrDate(out.getOpWarrDate());
					voDCS.setOpVinCode(out.getOpVinCode());
					voDCS.setOpElecCode(out.getOpElecCode());
					voDCS.setOpMechCode(out.getOpMechCode());
					voDCS.setOpPlate(out.getOpPlate());
					voDCS.setOpLocDate(out.getOpLocDate());
					voDCS.setOpDelivery(out.getOpDelivery());
					voDCS.setOpRefNumber(out.getOpRefNumber());
					logger.info("====SEDCSP08 out.getOpWarrDate() ====:"+out.getOpWarrDate());
					logger.info("====SEDCSP08 out.getOpLocDate() ====:"+out.getOpLocDate());
					if (tbOutputPosition != null && tbOutputPosition.value.length > 0) {
						com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26PosOut[] posouts = tbOutputPosition.value;
						LinkedList<SEDCSP08DetailInfoVO> detailInfo = new LinkedList<SEDCSP08DetailInfoVO>();
						for (int i=0;i<tbOutputPosition.value.length;i++) {
							com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26PosOut posOut = posouts[i];
							SEDCSP08DetailInfoVO detailVo = new SEDCSP08DetailInfoVO();
							detailVo.setOpWerks(posOut.getOpWerks());
							detailVo.setOpZzcliente(
									commonDao.getDealerEntityCodeByFCACode(posOut.getOpZzcliente())
									);
							detailVo.setOpMarca(posOut.getOpMarca());
							detailVo.setOpDealerUsr(posOut.getOpDealerUsr());
							detailVo.setOpVbeln(posOut.getOpVbeln());
							detailVo.setOpDelCharge(posOut.getOpDelCharge().doubleValue());
							detailVo.setOpInvvbeln(posOut.getOpInvvbeln());
							detailVo.setOpInvdate(posOut.getOpInvdate());
							detailVo.setOpDelvbeln(posOut.getOpDelvbeln());
							detailVo.setOpDeldate(posOut.getOpDeldate());
							detailVo.setOpTxdeldate(posOut.getOpTxdeldate());
							detailVo.setOpKeytype(posOut.getOpKeytype());
							detailVo.setOpOrdstatus(posOut.getOpOrdstatus());
							detailVo.setOpMabnr(posOut.getOpMabnr());
							detailVo.setOpArktx(posOut.getOpArktx());
							detailVo.setOpFklmg(posOut.getOpFklmg().doubleValue());
							detailVo.setOpFklmgFga(posOut.getOpFklmgFga().doubleValue());
							detailVo.setOpRetailprice(posOut.getOpRetailprice().doubleValue());
							detailVo.setOpDiscount(posOut.getOpDiscount().doubleValue());
							detailVo.setPosnr(posOut.getPosnr());
							detailVo.setPosex(posOut.getPosex());
							detailVo.setOpInvitem(posOut.getOpInvitem());
							detailVo.setOpDelitem(posOut.getOpDelitem());
							detailInfo.add(detailVo);
						}
						voDCS.setDetailInfo(detailInfo);
					}
					list.add(voDCS);
		} else {
			com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.Bapiret1[] bapiret1s = tbReturn.value;
			if (bapiret1s != null && bapiret1s.length > 0) {
				list = new ArrayList<SEDCSP08VO>();
				for (int i=0;i<bapiret1s.length;i++) {
					voDCS = new SEDCSP08VO();
					com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.Bapiret1 bapiret1 = bapiret1s[i];
					//错误信息返回
					String errorInfo = bapiret1.getMessage();
					voDCS.setResultInfo(errorInfo);
					logger.info("sap 返回结果失败 :"+errorInfo);
					list.add(voDCS);
				}
				list.add(voDCS);
			}
		}
		return list;
	}
	/**
	 * 到sap查询是否有数据
	 * @param vo
	 * @return
	 */
	public List<SEDCSP08DTO> querySEDCSP08DTO(SEDCSP08DTO vo) throws Exception{
		List<SEDCSP08DTO> list = new ArrayList<SEDCSP08DTO>();
		SEDCSP08DTO voDCS = new SEDCSP08DTO();
		String IZzcliente = 
				commonDao.getFCACodeByEntityCode(vo.getIzzcliente()); //特约店7位编码    entityCode转换成FACCode
		// 因为接口现在IDealerUsr长度为15位，所以截取掉前面2位'20'
		String IDealerUsr = vo.getIdealerUsr();//特约店用户
		if (IDealerUsr !=null && IDealerUsr.length() > 15){
			IDealerUsr = IDealerUsr.substring(2);
		}
		List<SEDCSP08NoteInfoDTO> tbVbelnList = vo.getTbVbeln();
		if (CommonUtils.isNullString(IZzcliente)) {
			logger.info("====IZzcliente 特约店7位编码 不能为空====");
			voDCS.setResultInfo("特约店7位编码不能为空");
			list.add(voDCS);
			return list;
		} 
		if (CommonUtils.isNullString(IDealerUsr)) {
			logger.info("====IDealerUsr 特约店用户 不能为空====");
			voDCS.setResultInfo("特约店用户 不能为空");
			list.add(voDCS);
			return list;
		} 
		if (null == tbVbelnList || tbVbelnList.size() < 0) {
			logger.info("====tbVbelnList 运单号 不能为空====");
			voDCS.setResultInfo("运单号 不能为空");
			list.add(voDCS);
			return list;
		}
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_BindingStub stub =
				new com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_BindingStub();
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_Service lmsService =
				new com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_ServiceLocator();
		stub = (com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZLVSDWS_L26_OUT_BindingStub)lmsService.getZLVSDWS_L26_OUT();
		logger.info("----------------调用对方服务地址："+lmsService.getZLVSDWS_L26_OUTAddress()+"----------------");
		String IMarca = OemDictCodeConstants.IMARCA;//品牌
		String IWerks = OemDictCodeConstants.IWERKS;//Plant (Own or External)
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26OutHolder tbOutputHeader = new 
				com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26OutHolder();
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26PosOutHolder tbOutputPosition = new 
				com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26PosOutHolder();
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfBapiret1Holder tbReturn = new 
				com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfBapiret1Holder();
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26VbelnHolder tbVbeln = new 
				com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.holders.TableOfZlvsdwsL26VbelnHolder();
		com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Vbeln[] vbelns = new 
				com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Vbeln[tbVbelnList.size()];
		for (int i=0;i<tbVbelnList.size();i++) {
			SEDCSP08NoteInfoDTO infoVo = tbVbelnList.get(i);
			com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Vbeln vbeln = new 
					com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Vbeln();
			vbeln.setOpVbeln(infoVo.getOpVbeln());
			vbelns[i] = vbeln;
		}
		tbVbeln.value = vbelns;
		//DMS获得数据 然后传给SAP 然后在给DMS
		logger.info("====SEDCSP08 IDealerUsr ====:"+IDealerUsr);
		logger.info("====SEDCSP08 IMarca ====:"+IMarca);
		logger.info("====SEDCSP08 IWerks ====:"+IWerks);
		logger.info("====SEDCSP08 tbVbeln ====:"+tbVbeln);
		stub.zlvsdwsL26Out(
				IDealerUsr,
				IMarca, 
				IWerks,
				IZzcliente, 
				tbOutputHeader,
				tbOutputPosition, 
				tbReturn, 
				tbVbeln);
		//把返回的sap值封装到list中 然后返回给下端
		if (tbOutputHeader != null && tbOutputHeader.value.length > 0) {
					com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Out[] outs =  tbOutputHeader.value;
					com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26Out out = outs[0];
					voDCS.setOpWerks(out.getOpWerks());
					voDCS.setOpZzcliente(
							commonDao.getDealerEntityCodeByFCACode(out.getOpZzcliente())
							);
					voDCS.setOpMarca(out.getOpMarca());
					voDCS.setOpDealerUsr(out.getOpDealerUsr());
					voDCS.setOpVbeln(out.getOpVbeln());
					voDCS.setOpAudat(out.getOpAudat());
					voDCS.setOpAuart(out.getOpAuart());
					voDCS.setOpOrdstatus(out.getOpOrdstatus());
					voDCS.setOpShipto(out.getOpShipto());
					voDCS.setOpBillto(out.getOpBillto());
					voDCS.setOpRespstaff(out.getOpRespstaff());
					voDCS.setOpNote(out.getOpNote());
					voDCS.setOpWarrDate(out.getOpWarrDate());
					voDCS.setOpVinCode(out.getOpVinCode());
					voDCS.setOpElecCode(out.getOpElecCode());
					voDCS.setOpMechCode(out.getOpMechCode());
					voDCS.setOpPlate(out.getOpPlate());
					voDCS.setOpLocDate(out.getOpLocDate());
					voDCS.setOpDelivery(out.getOpDelivery());
					voDCS.setOpRefNumber(out.getOpRefNumber());
					logger.info("====SEDCSP08 out.getOpWarrDate() ====:"+out.getOpWarrDate());
					logger.info("====SEDCSP08 out.getOpLocDate() ====:"+out.getOpLocDate());
					if (tbOutputPosition != null && tbOutputPosition.value.length > 0) {
						com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26PosOut[] posouts = tbOutputPosition.value;
						LinkedList<SEDCSP08DetailInfoDTO> detailInfo = new LinkedList<SEDCSP08DetailInfoDTO>();
						for (int i=0;i<tbOutputPosition.value.length;i++) {
							com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.ZlvsdwsL26PosOut posOut = posouts[i];
							SEDCSP08DetailInfoDTO detailVo = new SEDCSP08DetailInfoDTO();
							detailVo.setOpWerks(posOut.getOpWerks());
							detailVo.setOpZzcliente(
									commonDao.getDealerEntityCodeByFCACode(posOut.getOpZzcliente())
									);
							detailVo.setOpMarca(posOut.getOpMarca());
							detailVo.setOpDealerUsr(posOut.getOpDealerUsr());
							detailVo.setOpVbeln(posOut.getOpVbeln());
							detailVo.setOpDelCharge(posOut.getOpDelCharge().doubleValue());
							detailVo.setOpInvvbeln(posOut.getOpInvvbeln());
							detailVo.setOpInvdate(posOut.getOpInvdate());
							detailVo.setOpDelvbeln(posOut.getOpDelvbeln());
							detailVo.setOpDeldate(posOut.getOpDeldate());
							detailVo.setOpTxdeldate(posOut.getOpTxdeldate());
							detailVo.setOpKeytype(posOut.getOpKeytype());
							detailVo.setOpOrdstatus(posOut.getOpOrdstatus());
							detailVo.setOpMabnr(posOut.getOpMabnr());
							detailVo.setOpArktx(posOut.getOpArktx());
							detailVo.setOpFklmg(posOut.getOpFklmg().doubleValue());
							detailVo.setOpFklmgFga(posOut.getOpFklmgFga().doubleValue());
							detailVo.setOpRetailprice(posOut.getOpRetailprice().doubleValue());
							detailVo.setOpDiscount(posOut.getOpDiscount().doubleValue());
							detailVo.setPosnr(posOut.getPosnr());
							detailVo.setPosex(posOut.getPosex());
							detailVo.setOpInvitem(posOut.getOpInvitem());
							detailVo.setOpDelitem(posOut.getOpDelitem());
							detailInfo.add(detailVo);
						}
						voDCS.setDetailInfo(detailInfo);
					}
					list.add(voDCS);
		} else {
			com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.Bapiret1[] bapiret1s = tbReturn.value;
			if (bapiret1s != null && bapiret1s.length > 0) {
				list = new ArrayList<SEDCSP08DTO>();
				for (int i=0;i<bapiret1s.length;i++) {
					voDCS = new SEDCSP08DTO();
					com.infoeai.eai.wsClient.parts.lvmInquiringPartSoDetail.Bapiret1 bapiret1 = bapiret1s[i];
					//错误信息返回
					String errorInfo = bapiret1.getMessage();
					voDCS.setResultInfo(errorInfo);
					logger.info("sap 返回结果失败 :"+errorInfo);
					list.add(voDCS);
				}
				list.add(voDCS);
			}
		}
		return list;
	}
}
