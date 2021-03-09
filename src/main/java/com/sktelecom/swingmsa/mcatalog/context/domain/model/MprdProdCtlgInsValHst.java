/******************************************************************************
 * 프로그램명	: 상품카탈로그입력값이력 모델
 * 설명           : 상품카탈로그입력값이력 엔티티 정의 
 * ProgramID: MprdProdCtlgInsValHst.java
 * 작성자		: P123113
 * 작성일		: 2019-11-25
 * 변경로그	:
 * CHG-CD   :   성명         :   반영일자              :   변경 내용
 * ---------- -------------- ------------- ------------------------------------  
 * ver1.01  : P156920   : 2020.07.15      : planItmId Type변경(NUMBER->VARCHAR)                                    	
 *-----------------------------------------------------------------------------
*******************************************************************************/
package com.sktelecom.swingmsa.mcatalog.context.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.sktelecom.swingmsa.mcatalog.context.base.audit.AbstractAuditEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@IdClass(MprdProdCtlgInsValHstId.class)
@NoArgsConstructor
@AllArgsConstructor
public class MprdProdCtlgInsValHst extends AbstractAuditEntity {

	@Id
	@Column(nullable = false, length = 15)
	private String ctlgInsValId;
	
	@Id
	@Column(nullable = false, length = 10)
	private String ctlgVerNum;
	
	@Column(nullable = false, length = 10)
	private String ctlgId;
		
	@Column(nullable = false, length = 10)
	private String prodId;
	
	@Column(nullable = false)
	private String planItmId;				//ver1.01
	
	@Column(nullable = false)
	private Integer insValSerNum;
	
	@Column(length = 4000)
	private String insVal;	
}
