/******************************************************************************
 * 프로그램명	: 상품카탈로그 사용자권한 Repository
 * 설명           : 상품카탈로그 사용자의 유형과 권한에 따른 제어를 위한 레파지토리
 *            - Swing UserId를 기반으로 체크 
 * ProgramID: MprdUserAuthRepository.java
 * 작성자		: P070561
 * 작성일		: 2020-06-25
 * 변경로그	:
 * CHG-CD   :   성명         :   반영일자              :   변경 내용
 * ---------- -------------- ------------- ------------------------------------  
 * ver1.01  :                                         	
 *-----------------------------------------------------------------------------
*******************************************************************************/
package com.sktelecom.swingmsa.mcatalog.context.domain.repository;


import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.MprdUserAuth;


public interface MprdUserAuthRepository extends PagingAndSortingRepository<MprdUserAuth, String>, QuerydslPredicateExecutor<MprdUserAuth> {
	
	//UserId의 사용자 정보 조회
	MprdUserAuth findByUserId(String UserId);

}
