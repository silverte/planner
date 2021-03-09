/******************************************************************************
 * 프로그램명	: 기획항목분류목록 Repository
 * 설명           : 기획항목분류목록 엔티티에 접근 하기 위한 소스
 * ProgramID: MprdPlanItmClLstRepository.java
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

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sktelecom.swingmsa.mcatalog.context.domain.model.MprdPlanItmClLst;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.YesNoClCd;

@RepositoryRestResource
public interface MprdPlanItmClLstRepository extends PagingAndSortingRepository<MprdPlanItmClLst, Long>, QuerydslPredicateExecutor<MprdPlanItmClLst> {
	
	//사용여부가 Y인 기획항목분류 리스트 조회
	List<MprdPlanItmClLst> findByUseYn(@Param("useYn") YesNoClCd useYn);
}
