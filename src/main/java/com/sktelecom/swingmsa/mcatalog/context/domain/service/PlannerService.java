/******************************************************************************
 * 프로그램명	: 기획항목 서비스
 * 설명           : 기획항목들의 서비스 클래스
 * ProgramID: PlannerService.java
 * 작성자		: P123113
 * 작성일		: 2019-11-25
 * 변경로그	:
 * CHG-CD     :   성명          :   반영일자              :   변경 내용
 * ---------- -------------- ------------- ------------------------------------  
 * ver1.01-1  : P156920    :  2020.07.15     : 기획자, 관리자 역할에 따른 권한체크 API 
 * ver1.01-2  :            :       			 : 채번기능 추가 
 * ver1.01-3  :            :                 : vailidation 체크(매핑O -> Y,매핑 X -> N)
 * ver1.01-4  : 		   : 		         : swingIf에서 호출하는 상품목록조회  
 *-----------------------------------------------------------------------------
*******************************************************************************/
package com.sktelecom.swingmsa.mcatalog.context.domain.service;

import java.util.List;
import java.util.Map;

import com.sktelecom.swingmsa.mcatalog.context.domain.model.MprdPlanItmLst;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.MprdProdCtlgInfo;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.YesNoClCd;

public interface PlannerService {
	
	// 사용자권한체크
	Map<String, Object> getUserType(Map<String, Object> params);									//ver1.01-1
	
	//상품기획 사용
	Map<String, Object> findPlanItmList(Map<String, Object> params);	
	Map<String, Object> findProdList(Map<String, Object> params);	
	Map<String, Object> findCtlgInfo(Map<String, Object> params);	
	Map<String, Object> findCtlgInfoHst(Map<String, Object> params);	
	Map<String, Object> saveCtlgInfo(Map<String, Object> params);		
	Map<String, Object> updateCtlgStCd(Map<String, Object> params);	
	Map<String, Object> deleteCtlgInfo(Map<String, Object> params);
	
	//기획항목관리 사용
	Map<String, Object> findPlanItmClCdList(Map<String, Object> params);
	Map<String, Object> findPlanItmMgmtList(Map<String, Object> params);
	Map<String, Object> savePlanItmInfo(Map<String, Object> params);	
	Map<String, Object> savePlanItmClCdInfo(Map<String, Object> params);
	
	//채번
	Map<String, Object> findNewId(Map<String, Object> params);										//ver1.01-2
	
	//Proxy Client  
	void updateMprdPlanItmLst(String planItmId,String infoItmYn,String userId) throws Exception;	//ver1.01-3
	List<String>  findSwingIfProdlist(String ctlgId);												//ver1.01-4
}
