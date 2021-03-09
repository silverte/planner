/******************************************************************************
 * 프로그램명	: 상품카탈로그정보 Repository
 * 설명           : 상품카탈로그정보 엔티티에 접근 하기 위한 소스
 * ProgramID: MprdProdCtlgInfoRepository.java
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

import com.sktelecom.swingmsa.mcatalog.context.domain.model.MprdProdCtlgInfo;

@RepositoryRestResource
public interface MprdProdCtlgInfoRepository extends PagingAndSortingRepository<MprdProdCtlgInfo, String>, QuerydslPredicateExecutor<MprdProdCtlgInfo> {
	
	//카탈로그ID의 상품카탈로그리스트 조회
	List<MprdProdCtlgInfo> findByCtlgId(@Param("ctlgId") String ctlgId);	
	
	//상품ID의 상품카탈로그정보 조회
	MprdProdCtlgInfo findByProdId(@Param("prodId") String prodId);
	
	//상품카탈로그에 해당하는 상품카탈로그리스트 삭제
	@Modifying
	@Transactional
	void deleteByCtlgId(@Param("ctlgId") String ctlgId);	
}
