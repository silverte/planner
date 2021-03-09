/******************************************************************************
 * 프로그램명	: 상품유형상세  코드
 * 설명           : Planner API에서 공통으로 사용하는 코드목록
 * ProgramID: CtlgTypDtlCd.java
 * 작성자		: P156920
 * 작성일		: 2020-07-16
 * 변경로그	:
 * CHG-CD   :   성명         :   반영일자              :   변경 내용
 * ---------- -------------- ------------- ------------------------------------  
 * ver1.01  :           :                 :
 *-----------------------------------------------------------------------------
*******************************************************************************/
package com.sktelecom.swingmsa.mcatalog.context.domain.model.enums;

import com.sktelecom.swingmsa.mcatalog.context.base.enums.EnumModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CtlgTypDtlCd implements EnumModel {
	INSU100000("보험 기본형"	, "1")
   ,CLUB200000("클럽 기본형"	, "1")
   ,ADDI400000("부가 기본형 "	, "1")
   ,B2B1300000("IOT 기본형"	, "1");

	private String value;
	private String ref;
	
	@Override
	public String getKey() {
		return name();
	}
}
