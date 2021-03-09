/******************************************************************************
 * 프로그램명	: 실행구분  코드
 * 설명           : Planner API에서 공통으로 사용하는 코드목록
 * ProgramID: ExecClCd.java
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
public enum ExecClCd implements EnumModel {
	NONE("해당없음", ""),
	POPUP("팝업", ""),
	SVCAPI("서비스API", ""),
	SCRIPT("스크립트", "");

	private String value;
	private String ref;
	
	@Override
	public String getKey() {
		return name();
	}
}
