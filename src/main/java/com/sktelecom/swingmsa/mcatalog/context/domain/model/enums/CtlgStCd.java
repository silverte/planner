/******************************************************************************
 * 프로그램명	: 카탈로그상태  코드
 * 설명           : Planner API에서 공통으로 사용하는 코드목록
 * ProgramID: CtlgStCd.java
 * 작성자		: P123113
 * 작성일		: 2019-11-25
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
public enum CtlgStCd implements EnumModel {
	TEMP("임시저장", ""),
	RGST("등록완료", ""),
	GENR("Gen완료", ""),
	INSP("검증완료", ""),
	DEPL("SWG배포", ""),
	DEP_S("SWG배포", ""),
	DEP_P("SWG배포", "");
	
	private String value;
	private String ref;
	
	@Override
	public String getKey() {
		return name();
	}
}
