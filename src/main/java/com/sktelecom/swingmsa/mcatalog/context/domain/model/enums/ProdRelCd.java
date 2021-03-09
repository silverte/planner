/******************************************************************************
 * 프로그램명	: 생성대상상품  코드
 * 설명           : Planner API에서 공통으로 사용하는 코드목록
 * ProgramID: ProdRelCd.java
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
public enum ProdRelCd implements EnumModel {
	ORG("본상품", ""),
	RSV("예약상품", ""),
	TDAYRSV("당일예약상품", ""),
	FORCING("강제가입상품", ""),
	RVI("RVI상품", "");
	
	private String value;
	private String ref;
	
	@Override
	public String getKey() {
		return name();
	}
}
