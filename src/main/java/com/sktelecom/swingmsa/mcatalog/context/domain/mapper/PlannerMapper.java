/******************************************************************************
 * 프로그램명	: Planner정보 Mybatis Mapper
 * 설명           : PlannerMapper.xml과 매핑하는 소스
 * ProgramID: PlannerMapper.java
 * 작성자		: P156920
 * 작성일		: 20120-07-16
 * 변경로그	:
 * CHG-CD     :   성명         :   반영일자              :   변경 내용
 * ---------- -------------- ------------- ------------------------------------  
 * ver1.01    : P156920   : 2020.07.15      : 최초 반영 
 *-----------------------------------------------------------------------------
*******************************************************************************/
package com.sktelecom.swingmsa.mcatalog.context.domain.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.YesNoClCd;

@Mapper
public interface PlannerMapper {
	void updatePlanItmInfoYn(String planItmId,String infoItmYn,String userId);			//기획항목 리스트 - 정보항목여부 업뎃
}
