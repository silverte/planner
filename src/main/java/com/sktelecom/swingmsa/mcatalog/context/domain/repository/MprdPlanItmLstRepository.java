/******************************************************************************
 * 프로그램명	: 기획항목리스트 Repository
 * 설명           : 기획항목리스트 엔티티에 접근 하기 위한 소스
 * ProgramID: MprdPlanItmLstRepository.java
 * 작성자		: P123113
 * 작성일		: 2019-11-25
 * 변경로그	:
 * CHG-CD   :   성명         :   반영일자              :   변경 내용
 * ---------- -------------- ------------- ------------------------------------  
 * ver1.01  : P156920   : 2020.05.08      : 1) planItmId Type변경(NUMBER->VARCHAR) 
 *-----------------------------------------------------------------------------
*******************************************************************************/
package com.sktelecom.swingmsa.mcatalog.context.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.sktelecom.swingmsa.mcatalog.context.domain.model.MprdPlanItmLst;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.PlanItmTypCd;

@RepositoryRestResource
public interface MprdPlanItmLstRepository extends PagingAndSortingRepository<MprdPlanItmLst, Long>, QuerydslPredicateExecutor<MprdPlanItmLst> {
	
	//기획항목유형코드의 기획항목리스트 조회 
	List<MprdPlanItmLst> findByPlanItmTypCd(@Param("planItmTypCd") PlanItmTypCd planItmTypCd);

	//기획항목ID의 기획항목 리스트 조회
	MprdPlanItmLst findByPlanItmId(@Param("planItmId") String planItmId);	//ver1.01
	
	//기획항목테이블의 nextKey조회 : seq 6자리 
	@Query("SELECT NVL(max(to_number(substr(planItmId,5,10)))+1,100000) FROM MprdPlanItmLst WHERE substr(planItmId,1,4) = :planItmTypCd")
	Long findNextKey(@Param("planItmTypCd") String planItmTypCd);
		
}
