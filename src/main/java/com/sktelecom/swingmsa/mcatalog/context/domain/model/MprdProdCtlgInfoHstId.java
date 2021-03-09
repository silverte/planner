/******************************************************************************
 * 프로그램명	: 상품카탈로그정보이력 복합ID 모델
 * 설명           : 해당 테이블이 복합키일시 생성
 * ProgramID: MprdProdCtlgInfoHstId.java
 * 작성자		: P123113
 * 작성일		: 2019-11-25
 * 변경로그	:
 * CHG-CD   :   성명         :   반영일자              :   변경 내용
 * ---------- -------------- ------------- ------------------------------------  
 *                            	
 *-----------------------------------------------------------------------------
*******************************************************************************/
package com.sktelecom.swingmsa.mcatalog.context.domain.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MprdProdCtlgInfoHstId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String prodId;
	private String ctlgVerNum;
}
