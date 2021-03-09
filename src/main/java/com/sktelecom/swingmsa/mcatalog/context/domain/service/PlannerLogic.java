/******************************************************************************
 * 프로그램명	: 기획항목 로직
 * 설명           : 기획항목들의 비즈니스 클래스 
 * ProgramID: PlannerLogic.java
 * 작성자		: P123113
 * 작성일		: 2019-11-25
 * 변경로그	:
 * CHG-CD     :   성명         :   반영일자           :   변경 내용
 * ---------- -------------- ------------- ------------------------------------
 * ver1.01-1  : P156920   : 2020.07.15    : 기획자, 관리자 역할에 따른 권한체크  
 * ver1.01-2  :    		  :               : 기획항목ID 채번 기능 추가 
 * ver1.01-3  : 		  : 		      : 매핑여부에 따른 정보항목여부 체크
 * ver1.01-4  : 		  : 		      : swingIf에서 호출하는 상품목록조회 
 * ver1.01-5  :           :               : CtlgTypDtlCd추가     
 * ver2.01-1  : P156920   : 2021.01.13    : input값 Type 변경 (Map -> ArrayList)                                        	  
 *-----------------------------------------------------------------------------
*******************************************************************************/
package com.sktelecom.swingmsa.mcatalog.context.domain.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.h2.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sktelecom.swingmsa.mcatalog.context.base.exception.BizException;
import com.sktelecom.swingmsa.mcatalog.context.domain.mapper.PlannerMapper;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.MprdPlanItmClLst;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.MprdPlanItmLst;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.MprdProdCtlgInfo;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.MprdProdCtlgInfoHst;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.MprdProdCtlgInsVal;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.MprdProdCtlgInsValHst;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.MprdUserAuth;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.QMprdPlanItmClLst;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.QMprdPlanItmLst;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.QMprdProdCtlgInfo;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.QMprdProdCtlgInfoHst;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.QMprdProdCtlgInsValHst;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.CreClCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.CtlgStCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.CtlgTypCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.CtlgTypDtlCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.PlanItmTypCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.PlanItmTypDtlCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.ProdRelCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.UserTyp;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.YesNoClCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.repository.MprdPlanItmClLstRepository;
import com.sktelecom.swingmsa.mcatalog.context.domain.repository.MprdPlanItmLstRepository;
import com.sktelecom.swingmsa.mcatalog.context.domain.repository.MprdProdCtlgInfoHstRepository;
import com.sktelecom.swingmsa.mcatalog.context.domain.repository.MprdProdCtlgInfoRepository;
import com.sktelecom.swingmsa.mcatalog.context.domain.repository.MprdProdCtlgInsValHstRepository;
import com.sktelecom.swingmsa.mcatalog.context.domain.repository.MprdProdCtlgInsValRepository;
import com.sktelecom.swingmsa.mcatalog.context.domain.repository.MprdUserAuthRepository;
import com.sktelecom.swingmsa.mcatalog.context.util.Utils;

import lombok.extern.slf4j.Slf4j;


@Service("plannerLogic")
@Slf4j
public class PlannerLogic implements PlannerService {
		
	@Autowired
	private MprdProdCtlgInfoRepository mprdProdCtlgInfoRepo;
	
	@Autowired
	private MprdProdCtlgInsValRepository mprdProdCtlgInsValRepo;
	
	@Autowired
	private MprdPlanItmLstRepository mprdPlanItmLstRepo; 
	
	@Autowired
	private MprdPlanItmClLstRepository mprdPlanItmClLstRepo; 
	
	@Autowired
	private MprdProdCtlgInfoHstRepository mprdProdCtlgInfoHstRepo;
	
	@Autowired
	private MprdProdCtlgInsValHstRepository mprdProdCtlgInsValHstRepo;
	
	@Autowired
	private MprdUserAuthRepository mprdUserAuthRepo;
	
	@Autowired PlannerMapper plannerMapper;
	
	/************************************************************************************************************************
	 * Planner API
	 ************************************************************************************************************************/	
	/**
	 * 기획항목목록 조회
	 * @param	planItmTypCd    기획 상품유형코드
	 * @param	planItmTypDtlCd 기획 상세유형코드
	 */
	@Override
	@SuppressWarnings("unchecked")	
	public Map<String, Object> findPlanItmList(Map<String, Object> params) {
		
		Map<String, Object> result = new HashMap<>();
		Map<String, String> message = new HashMap<>();
		
		try {
		    
			//input1값
			ArrayList<Map<String,Object>> input1 = new ArrayList<Map<String,Object>>();	//ver2.01-1			
			input1 = (ArrayList<Map<String,Object>>) params.get("input1");	
			
			String planItmTypCd		= input1.get(0).get("planItmTypCd").toString();
			String planItmTypDtlCd 	= input1.get(0).get("planItmTypDtlCd").toString();	//ver1.01-5
			
			//기획항목목록
			Predicate predicate = QMprdPlanItmLst.mprdPlanItmLst.creClCd.eq(CreClCd.INPT)
									.and(QMprdPlanItmLst.mprdPlanItmLst.planItmTypCd.eq(PlanItmTypCd.valueOf(planItmTypCd)))
									.and(QMprdPlanItmLst.mprdPlanItmLst.planItmTypDtlCd.eq(PlanItmTypDtlCd.valueOf(planItmTypDtlCd)))	//ver1.01-5
									.and(QMprdPlanItmLst.mprdPlanItmLst.useYn.eq(YesNoClCd.Y));			
			List<MprdPlanItmLst> planItmList = (List<MprdPlanItmLst>) mprdPlanItmLstRepo.findAll(predicate);
			
			//기획항목분류목록
//			List<MprdPlanItmClLst> planItmClList = mprdPlanItmClLstRepo.findByUseYn(YesNoClCd.Y);
			List<MprdPlanItmClLst> planItmClList = (List<MprdPlanItmClLst>) mprdPlanItmClLstRepo.findAll();
			
			//기획항목 대/중분류 코드명/순번 set
			planItmList.forEach(itm -> {
				Optional<MprdPlanItmClLst> optLcl = planItmClList.stream().filter(cl -> cl.getPlanItmClCd().equals(itm.getPlanItmLclCd())).findFirst();
				if(optLcl.isPresent()) {
					itm.setPlanItmLclCdNm(optLcl.get().getPlanItmClNm());
					itm.setPlanItmLclDispSeq(optLcl.get().getClDispSeq());
				} else {
					itm.setPlanItmLclCdNm("");
					itm.setPlanItmLclDispSeq(0);
				}
				
				Optional<MprdPlanItmClLst> optMcl = planItmClList.stream().filter(cl -> cl.getPlanItmClCd().equals(itm.getPlanItmMclCd())).findFirst();
				if(optMcl.isPresent()) {
					itm.setPlanItmMclCdNm(optMcl.get().getPlanItmClNm());
					itm.setPlanItmMclDispSeq(optMcl.get().getClDispSeq());
				} else {
					itm.setPlanItmMclCdNm("");
					itm.setPlanItmMclDispSeq(0);
				}
			});		
					
			//기획항목 대분류코드순번, 중분류코드순번으로 정렬
			Comparator<MprdPlanItmLst> comparator = Comparator.comparing(MprdPlanItmLst::getPlanItmLclDispSeq).thenComparing(MprdPlanItmLst::getPlanItmMclDispSeq);
			planItmList.sort(comparator);
					
			result.put("output1", planItmList);

	        message.put("code", "0");
	        message.put("message", "정상  처리되었습니다.");
	 
	        result.put("error", message);
		} catch(Exception e) {
			throw new BizException(e);
		}
		
		return result;
	}
	
	/**
	 * 상품목록 조회
	 * @param ctlgTypCd		카탈로그 상품 유형코드
	 * @param ctlgTypDtlCd	카탈로그 상세 유형코드
	 * @param keyWord		검색명
	 */
	@Override
	@SuppressWarnings("unchecked")	
	public Map<String, Object> findProdList(Map<String, Object> params) {
		
		Map<String, Object> result = new HashMap<>();
		Map<String, String> message = new HashMap<>();
		
		try {
			
			//input1값
			ArrayList<Map<String,Object>> input1 = new ArrayList<Map<String,Object>>();	//ver2.01-1			
			input1 = (ArrayList<Map<String,Object>>) params.get("input1");	
			
			String ctlgTypCd 	= input1.get(0).get("ctlgTypCd").toString();
			String ctlgTypDtlCd = input1.get(0).get("ctlgTypDtlCd").toString();	// ver1.01-5
			String keyWord		= String.join("", "%", input1.get(0).get("keyWord").toString(), "%");
			
			// 상품목록
			Predicate predicate = QMprdProdCtlgInfo.mprdProdCtlgInfo.ctlgTypCd.eq(CtlgTypCd.valueOf(ctlgTypCd))
									.and(QMprdProdCtlgInfo.mprdProdCtlgInfo.ctlgTypDtlCd.eq(CtlgTypDtlCd.valueOf(ctlgTypDtlCd)))	// ver1.01-5
									.and(QMprdProdCtlgInfo.mprdProdCtlgInfo.mainProdYn.eq(YesNoClCd.Y))
									.and(QMprdProdCtlgInfo.mprdProdCtlgInfo.prodId.like(keyWord).or(QMprdProdCtlgInfo.mprdProdCtlgInfo.prodNm.like(keyWord)));
			List<MprdProdCtlgInfo> prodList = (List<MprdProdCtlgInfo>) mprdProdCtlgInfoRepo.findAll(predicate);
			
			result.put("output1", prodList);			

	        message.put("code", "0");
	        message.put("message", "정상 처리되었습니다.");
	 
	        result.put("error", message);
		} catch(Exception e) {
			throw new BizException(e);
		}
		
		return result;
	}
	
	/**
	 * 카탈로그정보 조회
	 * @param ctlgId 카탈로그ID
	 */
	@Override
	@SuppressWarnings("unchecked")	
	public Map<String, Object> findCtlgInfo(Map<String, Object> params) {
		
		Map<String, Object> result = new HashMap<>();
		Map<String, String> message = new HashMap<>();
		
		try {
			//input1값
			ArrayList<Map<String,Object>> input1 = new ArrayList<Map<String,Object>>();	//ver2.01-1			
			input1 = (ArrayList<Map<String,Object>>) params.get("input1");	
			
			String ctlgId = input1.get(0).get("ctlgId").toString();
			
//			// 관계상품
//			Predicate predicate1 = QMprdProdCtlgInfo.mprdProdCtlgInfo.ctlgId.eq(ctlgId)
//									.and(QMprdProdCtlgInfo.mprdProdCtlgInfo.mainProdYn.eq(YesNoClCd.N));
//			List<MprdProdCtlgInfo> prodList = (List<MprdProdCtlgInfo>) mprdProdCtlgInfoRepo.findAll(predicate1);
			// 전체상품
			List<MprdProdCtlgInfo> prodList = mprdProdCtlgInfoRepo.findByCtlgId(ctlgId);
			result.put("output1", prodList);
			
			// 상품구성정보
			List<MprdProdCtlgInsVal> ctlgInsVal = mprdProdCtlgInsValRepo.findByCtlgId(ctlgId);
			result.put("output2", ctlgInsVal);
			
			// 버전목록
			Predicate predicate2 = QMprdProdCtlgInfoHst.mprdProdCtlgInfoHst.ctlgId.eq(ctlgId)
								.and(QMprdProdCtlgInfoHst.mprdProdCtlgInfoHst.mainProdYn.eq(YesNoClCd.Y));
			OrderSpecifier<String> sortOrder = QMprdProdCtlgInfoHst.mprdProdCtlgInfoHst.ctlgVerNum.desc(); 
			List<MprdProdCtlgInfoHst> ctlgInfoHstList = (List<MprdProdCtlgInfoHst>) mprdProdCtlgInfoHstRepo.findAll(predicate2, sortOrder);
			result.put("output3", ctlgInfoHstList);
			
	        message.put("code", "0");
	        message.put("message", "정상 처리되었습니다.");
	 
	        result.put("error", message);
		} catch(Exception e) {
			throw new BizException(e);
		}
		
		return result;
	}
	
	/**
	 * 카탈로그정보 이력조회
	 * @param ctlgId
	 * @param ctlgVerNum
	 */
	@Override
	@SuppressWarnings("unchecked")	
	public Map<String, Object> findCtlgInfoHst(Map<String, Object> params) {
		
		Map<String, Object> result = new HashMap<>();
		Map<String, String> message = new HashMap<>();
		
		try {
			//input1값
			ArrayList<Map<String,Object>> input1 = new ArrayList<Map<String,Object>>();	//ver2.01-1			
			input1 = (ArrayList<Map<String,Object>>) params.get("input1");	
			
			String ctlgId 		= input1.get(0).get("ctlgId").toString();
			String ctlgVerNum 	= input1.get(0).get("ctlgVerNum").toString();
			
			// 관계상품
			Predicate predicate = QMprdProdCtlgInfoHst.mprdProdCtlgInfoHst.ctlgId.eq(ctlgId)
									.and(QMprdProdCtlgInfoHst.mprdProdCtlgInfoHst.ctlgVerNum.eq(ctlgVerNum))
									.and(QMprdProdCtlgInfoHst.mprdProdCtlgInfoHst.mainProdYn.eq(YesNoClCd.N));
			List<MprdProdCtlgInfoHst> prodRelListHst = (List<MprdProdCtlgInfoHst>) mprdProdCtlgInfoHstRepo.findAll(predicate);
			result.put("output1", prodRelListHst);
			
			// 상품구성정보
			Predicate predicateHst = QMprdProdCtlgInsValHst.mprdProdCtlgInsValHst.ctlgId.eq(ctlgId)
									.and(QMprdProdCtlgInsValHst.mprdProdCtlgInsValHst.ctlgVerNum.eq(ctlgVerNum));					
			List<MprdProdCtlgInsValHst> ctlgInsValHst = (List<MprdProdCtlgInsValHst>) mprdProdCtlgInsValHstRepo.findAll(predicateHst);
			
			result.put("output2", ctlgInsValHst);
			
	        message.put("code", "0");
	        message.put("message", "정상 처리되었습니다.");
	 
	        result.put("error", message);
		} catch(Exception e) {
			throw new BizException(e);
		}
		
		return result;
	}

	/**
	 * 카탈로그정보 저장
	 * @param mprdProdCtlgInfo
	 */
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public Map<String, Object> saveCtlgInfo(Map<String, Object> params) {
		
		Map<String, Object> result = new HashMap<>();
		Map<String, String> message = new HashMap<>();
		
		try {
			
			//ver2.01-1
			//inpu1,input2 세팅
			ArrayList<Map<String,Object>> input1 = new ArrayList<Map<String,Object>>();	
			ArrayList<Map<String,Object>> input2 = new ArrayList<Map<String,Object>>();		
			input1 = (ArrayList<Map<String,Object>>) params.get("input1");
			input2 = (ArrayList<Map<String,Object>>) params.get("input2");

		//1.상품 카탈로그 정보
			
			//1-1) 상품 카탈로그 정보 세팅
			
			List<MprdProdCtlgInfo> mprdProdCtlgInfo = (List<MprdProdCtlgInfo>) Utils.paramToList(input1, MprdProdCtlgInfo.class);
			
			MprdProdCtlgInfo baseInfo = mprdProdCtlgInfo.stream().filter(m -> m.getMainProdYn().equals(YesNoClCd.Y)).findFirst().orElse(null);
			
			String		ctlgId 		= baseInfo.getCtlgId();
			CtlgStCd	ctlgStCd 	= baseInfo.getCtlgStCd();
			String		ctlgVerNum 	= getNextVerNum(ctlgId, ctlgStCd);
			
			//1-2) 상품 카탈로그 정보 전체 삭제
			mprdProdCtlgInfoRepo.deleteByCtlgId(ctlgId);
			
			//1-3) 상품 카탈로그 정보 버전 SET
			mprdProdCtlgInfo.forEach(m -> m.setCtlgVerNum(ctlgVerNum));
			
			//1-4) 본상품 변경내용 컬럼 첫줄에 버전정보 append
			if(!ctlgVerNum.equals("")) {
				mprdProdCtlgInfo.forEach(m -> {
					if(m.getProdRelCd().equals(ProdRelCd.ORG) && ctlgStCd.equals(CtlgStCd.RGST)) {
						m.setChgCtt("[Ver. "+ctlgVerNum+"]\r\n"+m.getChgCtt());
					}
				});
			}
			
			//1-5) 상품 카탈로그 정보 저장
			mprdProdCtlgInfoRepo.saveAll(mprdProdCtlgInfo);
			
			log.info("[상품 카탈로그정보 확인 /"+ctlgId+"] "+mprdProdCtlgInfo.toString());
			
		//2. 상품 카탈로그 입력값 
			
			//2-1) 상품 카탈로그 입력값 정보 세팅
			List<MprdProdCtlgInsVal> mprdProdCtlgInsVal = (List<MprdProdCtlgInsVal>) Utils.paramToList(input2, MprdProdCtlgInsVal.class);

			//2-2) 상품 카탈로그 입력값 전체 삭제
			mprdProdCtlgInsValRepo.deleteByCtlgId(ctlgId);
			
			//2-3) PK생성 - CTLG_ID + SEQ(5)
			//      ex) NA0000665900001
			mprdProdCtlgInsVal.forEach(m -> {
				m.setCtlgInsValId(m.getCtlgId() + StringUtils.pad(String.valueOf(mprdProdCtlgInsVal.indexOf(m) + 1), 5, "0", false));				
			});
			
			//2-4) 상품 카탈로그 입력값 저장
			mprdProdCtlgInsValRepo.saveAll(mprdProdCtlgInsVal);
			
			log.info("[상품 카탈로그 입력값 확인 /"+ctlgId+"] "+mprdProdCtlgInsVal.toString());
		
		//3. 자동 기획항목 
			int idx = mprdProdCtlgInsVal.size() + 1;	// 입력항목의 seq다음부터 자동항목의 seq를 채번
			
			for(MprdProdCtlgInsVal m : mprdProdCtlgInsVal) {
				//3-1) ins_val의 plan_itm_id를 sup로 갖는 기획항목 찾기
				List<MprdProdCtlgInsVal> autoInsValList = new ArrayList<MprdProdCtlgInsVal>();
				Predicate pre1 = QMprdPlanItmLst.mprdPlanItmLst.creClCd.eq(CreClCd.AUTO)
							.and(QMprdPlanItmLst.mprdPlanItmLst.supPlanItmId.eq(m.getPlanItmId()))
							.and(QMprdPlanItmLst.mprdPlanItmLst.useYn.eq(YesNoClCd.Y));

				List<MprdPlanItmLst> autoPlanItmList = (List<MprdPlanItmLst>) mprdPlanItmLstRepo.findAll(pre1);
				System.out.println("상위기획 확인"+autoPlanItmList.toString());
				//3-2) PROD_REL_CD로 CTLG_INFO 테이블에서 PROD_ID를 구해서 INS_VAL테이블의 PROD_ID에 채우고 INS_VAL 컬럼 값은 동일값으로 채뭄				
				for(MprdPlanItmLst p : autoPlanItmList) {
					Predicate pre2 = QMprdProdCtlgInfo.mprdProdCtlgInfo.prodRelCd.eq(ProdRelCd.valueOf(p.getProdRelCd().toString()))
								.and(QMprdProdCtlgInfo.mprdProdCtlgInfo.ctlgId.eq(m.getCtlgId()));
					Optional<MprdProdCtlgInfo> ctlgInfo = mprdProdCtlgInfoRepo.findOne(pre2);
					
					MprdProdCtlgInsVal autoInsVal = new MprdProdCtlgInsVal();	
					
					autoInsVal.setCtlgInsValId(m.getCtlgId() + StringUtils.pad(String.valueOf(idx++), 5, "0", false));
					autoInsVal.setPlanItmId(p.getPlanItmId());
					autoInsVal.setCtlgId(m.getCtlgId());
					autoInsVal.setProdId(ctlgInfo.isPresent()?ctlgInfo.get().getProdId():"");
					autoInsVal.setInsVal(m.getInsVal());
					autoInsVal.setInsValSerNum(m.getInsValSerNum());
					
					autoInsValList.add(autoInsVal);
				}
				//3-3) 자동기획항목 저장 			
				mprdProdCtlgInsValRepo.saveAll(autoInsValList);
			}
  
			// 상태코드 저장(RGST)인 경우는 이력생성
			if(ctlgStCd.equals(CtlgStCd.RGST)) {
				saveCtlgHst(ctlgId, ctlgVerNum);
			}	
			
	        message.put("code", "0");
	        message.put("message", "정상 처리되었습니다.");
	 
	        result.put("error", message);
		} catch(Exception e) {	
			throw new BizException(e);
		}
		
		return result;
	}
	
	/**
	 * 이력저장
	 * @param ctlgId
	 * @param ctlgVerNum
	 * @throws Exception
	 */
	@Transactional
	private void saveCtlgHst(String ctlgId, String ctlgVerNum) throws Exception {
		
		//카탈로그정보 이력저장
		List<MprdProdCtlgInfo> ctlgInfoList = mprdProdCtlgInfoRepo.findByCtlgId(ctlgId);
		List<MprdProdCtlgInfoHst> ctlgInfoHstList = new ArrayList<MprdProdCtlgInfoHst>(); 
		ctlgInfoList.forEach(m -> {
			MprdProdCtlgInfoHst h = new MprdProdCtlgInfoHst();
			BeanUtils.copyProperties(m, h);
			ctlgInfoHstList.add(h);
		});
		mprdProdCtlgInfoHstRepo.saveAll(ctlgInfoHstList);
		
		//카탈로그입력값 이력저장
		List<MprdProdCtlgInsVal> ctlgInsValList = mprdProdCtlgInsValRepo.findByCtlgId(ctlgId);
		List<MprdProdCtlgInsValHst> ctlgInsValHstList = new ArrayList<MprdProdCtlgInsValHst>(); 
		ctlgInsValList.forEach(m -> {
			MprdProdCtlgInsValHst h = new MprdProdCtlgInsValHst();
			BeanUtils.copyProperties(m, h);
			h.setCtlgVerNum(ctlgVerNum);
			ctlgInsValHstList.add(h);
		});
		mprdProdCtlgInsValHstRepo.saveAll(ctlgInsValHstList);
	}
	
	/**
	 * 카탈로그상태변경
	 * @param ctlgId
	 * @param ctlgStCd
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> updateCtlgStCd(Map<String, Object> params) {
		
		Map<String, Object> result = new HashMap<>();
		Map<String, String> message = new HashMap<>();
		
		try {			
			// 상품 카탈로그 상태 변경
			//input1값
			ArrayList<Map<String,Object>> input1 = new ArrayList<Map<String,Object>>();	//ver2.01-1			
			input1 = (ArrayList<Map<String,Object>>) params.get("input1");	
			
			String ctlgId = input1.get(0).get("ctlgId").toString();
			String ctlgStCd = input1.get(0).get("ctlgStCd").toString();
			
			List<MprdProdCtlgInfo> infoList = mprdProdCtlgInfoRepo.findByCtlgId(ctlgId);
			infoList.forEach(m -> m.setCtlgStCd(CtlgStCd.valueOf(ctlgStCd)));
			mprdProdCtlgInfoRepo.saveAll(infoList);
			
			// 카탈로그 최종버전 이력의 상태 변경
			Predicate predicate = QMprdProdCtlgInfoHst.mprdProdCtlgInfoHst.ctlgId.eq(ctlgId)
									.and(QMprdProdCtlgInfoHst.mprdProdCtlgInfoHst.ctlgVerNum.eq(infoList.get(0).getCtlgVerNum()));
			List<MprdProdCtlgInfoHst> infoHstList = (List<MprdProdCtlgInfoHst>) mprdProdCtlgInfoHstRepo.findAll(predicate);
			infoHstList.forEach(m -> m.setCtlgStCd(CtlgStCd.valueOf(ctlgStCd)));
			mprdProdCtlgInfoHstRepo.saveAll(infoHstList);
			
	        message.put("code", "0");
	        message.put("message", "정상 처리되었습니다.");
	 
	        result.put("error", message);
		} catch(Exception e) {
			throw new BizException(e);
		}
		
		return result;
	}
	
	/**
	 * 기획항목분류코드목록 조회
	 * @param optLclCd
	 * @param optUseYn
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> findPlanItmClCdList(Map<String, Object> params) {
		
		Map<String, Object> result = new HashMap<>();
		Map<String, String> message = new HashMap<>();
		
		try {
			
			//input1값
			ArrayList<Map<String,Object>> input1 = new ArrayList<Map<String,Object>>();	//ver2.01-1			
			input1 = (ArrayList<Map<String,Object>>) params.get("input1");	
			
			Optional<Object> optLclCd = Optional.ofNullable(input1.get(0).get("planItmLclCd"));
			Optional<Object> optUseYn = Optional.ofNullable(input1.get(0).get("useYn"));
			
			Predicate condClCd = null;
			if(optLclCd.isPresent() && !optLclCd.get().toString().isEmpty()) {
				String planItmLclCd = optLclCd.get().toString();
				condClCd = QMprdPlanItmClLst.mprdPlanItmClLst.planItmClCd.startsWith(planItmLclCd.substring(0, 1))
						.and(QMprdPlanItmClLst.mprdPlanItmClLst.planItmClCd.ne(planItmLclCd));
			} else {
				condClCd = QMprdPlanItmClLst.mprdPlanItmClLst.planItmClCd.endsWith("0000");				
			}
			
			Predicate condUseYn = null;
			if(optUseYn.isPresent() && !optUseYn.get().toString().isEmpty()) {
				condUseYn = QMprdPlanItmClLst.mprdPlanItmClLst.useYn.eq(YesNoClCd.valueOf(optUseYn.get().toString()));
			}
			
			Predicate predicate = ((BooleanExpression) condClCd).and(condUseYn);
			List<MprdPlanItmClLst> planItmClList = (List<MprdPlanItmClLst>) mprdPlanItmClLstRepo.findAll(predicate);
			
			Comparator<MprdPlanItmClLst> comparator = Comparator.comparing(MprdPlanItmClLst::getClDispSeq);
			planItmClList.sort(comparator);
			
			result.put("output1", planItmClList);			

	        message.put("code", "0");
	        message.put("message", "정상 처리되었습니다.");
	 
	        result.put("error", message);
		} catch(Exception e) {
			throw new BizException(e);
		}
		
		return result;
	}
	
	/**
	 * 기획항목관리용 기획항목목록 조회
	 * @param optLclCd
	 * @param optMclCd
	 * @param optCreClCd
	 * @param optPlanItmTypCd
	 * @param optPlanItmTypDtlCd
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> findPlanItmMgmtList(Map<String, Object> params) {
		
		Map<String, Object> result = new HashMap<>();
		Map<String, String> message = new HashMap<>();
		
		try {
			
			//input1값
			ArrayList<Map<String,Object>> input1 = new ArrayList<Map<String,Object>>();	//ver2.01-1			
			input1 = (ArrayList<Map<String,Object>>) params.get("input1");	
			
			Optional<Object> optLclCd = Optional.ofNullable(input1.get(0).get("planItmLclCd"));
			Optional<Object> optMclCd = Optional.ofNullable(input1.get(0).get("planItmMclCd"));
			Optional<Object> optCreClCd = Optional.ofNullable(input1.get(0).get("creClCd"));
			Optional<Object> optPlanItmTypCd	= Optional.ofNullable(input1.get(0).get("planItmTypCd"));
			Optional<Object> optPlanItmTypDtlCd = Optional.ofNullable(input1.get(0).get("planItmTypDtlCd"));	//ver1.01-5			
			
			//기획항목 목록
			Predicate condLcl 		= null;
			Predicate condMcl 		= null;
			Predicate condCre 		= null;
			Predicate condTyp		= null;
			Predicate condtypDtl	= null;	//0701
			
			if(optLclCd.isPresent() && !optLclCd.get().toString().isEmpty()) {
				condLcl = QMprdPlanItmLst.mprdPlanItmLst.planItmLclCd.eq(optLclCd.get().toString());
			} 
			if(optMclCd.isPresent() && !optMclCd.get().toString().isEmpty()) {
				condMcl = QMprdPlanItmLst.mprdPlanItmLst.planItmMclCd.eq(optMclCd.get().toString());
			}
			if(optCreClCd.isPresent() && !optCreClCd.get().toString().isEmpty()) {
				condCre = QMprdPlanItmLst.mprdPlanItmLst.creClCd.eq(CreClCd.valueOf(optCreClCd.get().toString()));
			}
			if(optPlanItmTypCd.isPresent() && !optPlanItmTypCd.get().toString().isEmpty()) {
				condTyp = QMprdPlanItmLst.mprdPlanItmLst.planItmTypCd.eq(PlanItmTypCd.valueOf(optPlanItmTypCd.get().toString()));
			}
			if(optPlanItmTypDtlCd.isPresent() && !optPlanItmTypDtlCd.get().toString().isEmpty()) {	//ver1.01-5
				condtypDtl = QMprdPlanItmLst.mprdPlanItmLst.planItmTypDtlCd.eq(PlanItmTypDtlCd.valueOf(optPlanItmTypDtlCd.get().toString()));
			}
			
			String planItmNm = String.join("", "%", input1.get(0).get("planItmNm").toString(), "%");
			
			Predicate predicate = QMprdPlanItmLst.mprdPlanItmLst.planItmNm.like(planItmNm).and(condLcl).and(condMcl).and(condCre).and(condTyp).and(condtypDtl);	//ver1.01-5
			List<MprdPlanItmLst> planItmList = (List<MprdPlanItmLst>) mprdPlanItmLstRepo.findAll(predicate);
			
			//기획항목분류목록
//			List<MprdPlanItmClLst> planItmClList = mprdPlanItmClLstRepo.findByUseYn(YesNoClCd.Y);	
			List<MprdPlanItmClLst> planItmClList = (List<MprdPlanItmClLst>) mprdPlanItmClLstRepo.findAll();
			//기획항목 대/중분류 코드명 set
			planItmList.forEach(itm -> {
				Optional<MprdPlanItmClLst> optLcl = planItmClList.stream().filter(cl -> cl.getPlanItmClCd().equals(itm.getPlanItmLclCd())).findFirst();
				if(optLcl.isPresent()) {
					itm.setPlanItmLclCdNm(optLcl.get().getPlanItmClNm());
				} else {
					itm.setPlanItmLclCdNm("");
				}
				
				Optional<MprdPlanItmClLst> optMcl = planItmClList.stream().filter(cl -> cl.getPlanItmClCd().equals(itm.getPlanItmMclCd())).findFirst();
				if(optMcl.isPresent()) {
					itm.setPlanItmMclCdNm(optMcl.get().getPlanItmClNm());
				} else {
					itm.setPlanItmMclCdNm("");
				}
			});
						
			Comparator<MprdPlanItmLst> comparator = Comparator.comparing(MprdPlanItmLst::getPlanItmLclCd).thenComparing(MprdPlanItmLst::getPlanItmMclCd);
			planItmList.sort(comparator);
			
			result.put("output1", planItmList);			

	        message.put("code", "0");
	        message.put("message", "정상 처리되었습니다.");
	 
	        result.put("error", message);
		} catch(Exception e) {
			throw new BizException(e);
		}
		
		return result;
	}

	/**
	 * 기획항목 저장
	 * @param mprdPlanItmLst
	 */
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public Map<String, Object> savePlanItmInfo(Map<String, Object> params) {
		
		Map<String, Object> result = new HashMap<>();
		Map<String, String> message = new HashMap<>();
		
		try {			
			
			//input1값
			ArrayList<Map<String,Object>> input1 = new ArrayList<Map<String,Object>>();	//ver2.01-1			
			input1 = (ArrayList<Map<String,Object>>) params.get("input1");		
			
			MprdPlanItmLst mprdPlanItmLst = new MprdPlanItmLst();
			Utils.castFromMap(mprdPlanItmLst, input1);
			
			MprdPlanItmLst savedObj = mprdPlanItmLstRepo.save(mprdPlanItmLst);
			
			result.put("output1", savedObj);
			
	        message.put("code", "0");
	        message.put("message", "정상 처리되었습니다.");
	 
	        result.put("error", message);
		} catch(Exception e) {
			throw new BizException(e);
		}
		
		return result;
	}
	
	
	/**
	 * 기획항목분류코드 저장
	 * @param mprdPlanItmClLst
	 */
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public Map<String, Object> savePlanItmClCdInfo(Map<String, Object> params) {
		
		Map<String, Object> result = new HashMap<>();
		Map<String, String> message = new HashMap<>();
		
		try {
			
			//input1값
			ArrayList<Map<String,Object>> input1 = new ArrayList<Map<String,Object>>();	//ver2.01-1			
			input1 = (ArrayList<Map<String,Object>>) params.get("input1");	
			
			// 기획항목분류코드 저장						
			List<MprdPlanItmClLst> mprdPlanItmClLst = (List<MprdPlanItmClLst>) Utils.paramToList(input1, MprdPlanItmClLst.class);
			mprdPlanItmClLstRepo.saveAll(mprdPlanItmClLst);
			
	        message.put("code", "0");
	        message.put("message", "정상 처리되었습니다.");
	 
	        result.put("error", message);
		} catch(Exception e) {
			throw new BizException(e);
		}
		
		return result;
	}

	/**
	 * 다음 버전 구하기 - 상태에 따라 현재버전(TEMP) or 다음버전(RGST)
	 * @param ctlgId
	 * @param ctlgStCd
	 * @return
	 */
	private String getNextVerNum(String ctlgId, CtlgStCd ctlgStCd) {
		
		// 최종버전
		Predicate predicate = QMprdProdCtlgInfoHst.mprdProdCtlgInfoHst.ctlgId.eq(ctlgId)
								.and(QMprdProdCtlgInfoHst.mprdProdCtlgInfoHst.mainProdYn.eq(YesNoClCd.Y));
		OrderSpecifier<String> sordOrder = QMprdProdCtlgInfoHst.mprdProdCtlgInfoHst.ctlgVerNum.desc();
		List<MprdProdCtlgInfoHst> mprdProdCtlgInfoHst = (List<MprdProdCtlgInfoHst>) mprdProdCtlgInfoHstRepo.findAll(predicate, sordOrder);
		
		Double verNum = null;
		if(mprdProdCtlgInfoHst.size() > 0) {
			verNum = Double.valueOf(mprdProdCtlgInfoHst.get(0).getCtlgVerNum());
			if(ctlgStCd.equals(CtlgStCd.RGST)) {
				verNum += 0.1;
			}
		} else if(mprdProdCtlgInfoHst.size() == 0) {
			if(ctlgStCd.equals(CtlgStCd.RGST)) {
				verNum = 1.0;
			}
		}
		
		return verNum == null?"":String.format("%.2f", verNum);
	}

	/**
	 * 카탈로그정보 전체 삭제
	 * @param ctlgId
	 */
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public Map<String, Object> deleteCtlgInfo(Map<String, Object> params) {
		
		Map<String, Object> result = new HashMap<>();
		Map<String, String> message = new HashMap<>();
		
		try {			
			
			//input1값
			ArrayList<Map<String,Object>> input1 = new ArrayList<Map<String,Object>>();	//ver2.01-1			
			input1 = (ArrayList<Map<String,Object>>) params.get("input1");	
			
			Optional<String> optCtlgId = Optional.ofNullable(input1.get(0).get("ctlgId").toString());
			
			if(optCtlgId.get().length() == 0) {
				throw new Exception("CtlgID가 NULL입니다. CtlgID를 체크해주세요!!");
			}else {
				System.out.println("optCtlgId : "+ optCtlgId.get().toString());
				//MPRD_PROD_CTLG_INFO 삭제
				mprdProdCtlgInfoRepo.deleteByCtlgId(optCtlgId.get().toString());
				//MPRD_PROD_CTLG_INFO_HST 삭제
				mprdProdCtlgInfoHstRepo.deleteByCtlgId(optCtlgId.get().toString());
				//MPRD_PROD_CTLG_INS_VAL 삭제
				mprdProdCtlgInsValRepo.deleteByCtlgId(optCtlgId.get().toString());
				//MPRD_PROD_CTLG_INS_VAL_HST 삭제
				mprdProdCtlgInsValHstRepo.deleteByCtlgId(optCtlgId.get().toString());				
			}
	        message.put("code", "0");
	        message.put("message", "정상 처리되었습니다.");
	 
	        result.put("error", message);
		} catch(Exception e) {
			throw new BizException(e);
		}
		
		return result;
	}
	
	/**
	 * ver1.01-1
	 * 기획자, 관리자 역할에 따른 권한체크
	 * @param userId 사용자ID 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getUserType(Map<String, Object> params) {
		// TODO Auto-generated method stub

		Map<String, Object> result = new HashMap<>();
		Map<String, String> message = new HashMap<>();
				
		try {
						
			//input1값
			ArrayList<Map<String,Object>> input1 = new ArrayList<Map<String,Object>>();	//ver2.01-1			
			input1 = (ArrayList<Map<String,Object>>) params.get("input1");	
			
			String userId = input1.get(0).get("userId").toString();
			
			String userType = "PLANNER";
		
			Optional<MprdUserAuth> mprdUserAuthOpt = Optional.ofNullable(mprdUserAuthRepo.findByUserId(userId));

			// 사용자 권한체크 - 관리자로 등록된 사용자만 권한있음
			if(mprdUserAuthOpt.isPresent() && mprdUserAuthOpt.get().getUserTyp().equals(UserTyp.ADMIN)) {
				userType = "ADMIN";
			}
			
			// UI에서 값을 받을수 있도록 map으로 감싸서 리턴
			Map<String, String> objAuthYn = new HashMap<>();
			objAuthYn.put("userType", userType);
			result.put("output1", objAuthYn);
			
	        message.put("code", "0");
	        message.put("message", "정상 처리되었습니다.");
	 
	        result.put("error", message);
	        
		} catch(Exception e) {
			throw new BizException(e);
		}
		
		return result;
	}
	
	/**
	 * ver1.01-2
	 * 기획항목ID 채번기능
	 * @param planItmTypCd 기획항목유형코드(INSU,CLUB)
	 * @param newIdTypCd   채번유형코드
	 *         - PLAN(기획항목ID)
	 */
	@Override
	@SuppressWarnings("unchecked")	
	public Map<String, Object> findNewId(Map<String, Object> params) {
		
		Map<String, Object> result = new HashMap<>();
		Map<String, String> message = new HashMap<>();
		
		try {
			
			//input1값
			ArrayList<Map<String,Object>> input1 = new ArrayList<Map<String,Object>>();	//ver2.01-1			
			input1 = (ArrayList<Map<String,Object>>) params.get("input1");	

			String planItmTypCd 	= input1.get(0).get("planItmTypCd").toString();
			String newIdType 		= input1.get(0).get("newIdType").toString();	
			Long nextKey			= null;
			String newId			= null;
			
			if(newIdType.equals("PLAN")) {
				//기획항목ID
				nextKey = mprdPlanItmLstRepo.findNextKey(planItmTypCd);
				newId 	= planItmTypCd + String.valueOf(nextKey);
			}
			
			MprdPlanItmLst palnItmLst = new MprdPlanItmLst();
			palnItmLst.setNewId(newId);
			
			result.put("output1", palnItmLst);
			
	        message.put("code", "0");
	        message.put("message", "정상 처리되었습니다.");
	 
	        result.put("error", message);
		} catch(Exception e) {
			throw new BizException(e);
		}
		
		return result;
	}
	
	/************************************************************************************************************************
	 * client Method
	 ************************************************************************************************************************/
	/**
	 * ver1.01-3
	 * Planner에서 호출하는 매핑정보 저장시 정보항목 여부 Y->N 업뎃
	 * @param planItmId 기획항목ID
	 * @param infoItmYm 정보항목여부(N)
	 */
	@Override
	@Transactional	
	public void updateMprdPlanItmLst(String planItmId,String infoItmYn,String userId) throws Exception {	

		log.debug(">>>>>>>>>매핑정보 저장 및 삭제시 정보항목여부 업뎃 >>>>>>>>"+planItmId+"/"+infoItmYn);
		plannerMapper.updatePlanItmInfoYn(planItmId, infoItmYn, userId);

	}

	/**
	 * ver1.01-4
	 * swingIf에서 호출하는 상품목록조회
	 * @param ctlgId
	 */               
	public List<String> findSwingIfProdlist(String ctlgId) {
		
		List<String> prodIdLst = new ArrayList<String>();
		
		try {
			List<MprdProdCtlgInfo> mprdProdList  = mprdProdCtlgInfoRepo.findByCtlgId(ctlgId);
			for(MprdProdCtlgInfo m : mprdProdList) {
				prodIdLst.add(m.getProdId());
			}
			
		} catch(Exception e) {
			throw new BizException(e);
		}	

		return prodIdLst;
	}

	
}
