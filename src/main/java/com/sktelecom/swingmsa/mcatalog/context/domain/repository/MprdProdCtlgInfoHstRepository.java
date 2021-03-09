/******************************************************************************
 * 프로그램명	: 상품카탈로그정보이력 Repository 
 * 설명           : 상품카탈로그정보이력 엔티티에 접근 하기 위한 소스
 * ProgramID: MprdProdCtlgInfoHstRepository.java
 * 작성자		: P123113
 * 작성일		: 2019-11-25
 * 변경로그	:
 * CHG-CD   :   성명         :   반영일자              :   변경 내용
 * ---------- -------------- ------------- ------------------------------------  
 * ver1.01  :           :                 :
 *-----------------------------------------------------------------------------
*******************************************************************************/
package com.sktelecom.swingmsa.mcatalog.context.domain.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sktelecom.swingmsa.mcatalog.context.domain.model.MprdProdCtlgInfoHst;

@RepositoryRestResource
public interface MprdProdCtlgInfoHstRepository extends PagingAndSortingRepository<MprdProdCtlgInfoHst, String>, QuerydslPredicateExecutor<MprdProdCtlgInfoHst> {
	
	//카탈로그 정보 삭제
	@Modifying
	@Transactional
	void deleteByCtlgId(String ctlgId);	
}
