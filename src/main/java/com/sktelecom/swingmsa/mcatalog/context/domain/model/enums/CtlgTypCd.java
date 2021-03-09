/******************************************************************************
 * 프로그램명	: 상품유형  코드
 * 설명           : Planner API에서 공통으로 사용하는 코드목록
 * ProgramID: CtlgTypCd.java
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
public enum CtlgTypCd implements EnumModel {
	INSU("보험상품", "PLANNER"),
	CLUB("클럽상품", "PLANNER"),
	ADDI("부가상품", "PLANNER"),
	B2B1("IoT선납요금제", "ADMIN");
	
	private String value;
	private String ref;
	
	@Override
	public String getKey() {
		return name();
	}
}
