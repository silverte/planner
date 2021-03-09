/******************************************************************************
 * 프로그램명	: 기획항목분류목록 모델
 * 설명           : 기획항목의 분류체계 엔티티 정의 
 * ProgramID: MprdPlanItmClLst.java
 * 작성자		: P123113
 * 작성일		: 2019-11-25
 * 변경로그	:
 * CHG-CD   :   성명         :   반영일자              :   변경 내용
 * ---------- -------------- ------------- ------------------------------------  
 *
 *-----------------------------------------------------------------------------
*******************************************************************************/
package com.sktelecom.swingmsa.mcatalog.context.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.sktelecom.swingmsa.mcatalog.context.base.audit.AbstractAuditEntity;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.YesNoClCd;
import com.sktelecom.swingmsa.mcatalog.context.util.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MprdPlanItmClLst extends AbstractAuditEntity {

	@Id
	@Column(nullable = false, length = 10)
	private String planItmClCd;
	
	@Column(length = 100)
	private String planItmClNm;
	
	@Column(nullable = false)
	private Integer clDispSeq;
	
	@Column(nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private YesNoClCd useYn;
	
	@Transient	
	private String useYnNm;	
	public String getUseYnNm() {
		return Utils.getEnumValue(YesNoClCd.class, useYn);
	}
}
