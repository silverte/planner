/******************************************************************************
 * 프로그램명	: 상품카탈로그입력값이력 Repository
 * 설명           : 상품카탈로그입력값이력 엔티티에 접근 하기 위한 소스
 * ProgramID: MprdProdCtlgInsValHstRepository.java
 * 작성자		: P123113
 * 작성일		: 2019-11-25
 * 변경로그	:
 * CHG-CD   :   성명         :   반영일자              :   변경 내용
 * ---------- -------------- ------------- ------------------------------------  
 * ver1.01  :           :                 :
 *-----------------------------------------------------------------------------
*******************************************************************************/
package com.sktelecom.swingmsa.mcatalog.context.domain.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sktelecom.swingmsa.mcatalog.context.domain.model.MprdProdCtlgInsValHst;

@RepositoryRestResource
public interface MprdProdCtlgInsValHstRepository extends PagingAndSortingRepository<MprdProdCtlgInsValHst, Long>, QuerydslPredicateExecutor<MprdProdCtlgInsValHst> {
	
	//ctlgID의 이력정보 조회
	List<MprdProdCtlgInsValHst> findByCtlgId(@Param("ctlgId") String ctlgId);
	
	//ctlgID의 이력정보 삭제
	@Modifying
	@Transactional
	void deleteByCtlgId(String ctlgId);	
}
