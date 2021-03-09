/******************************************************************************
 * 프로그램명	: 상품카탈로그 사용자권한 모델
 * 설명           : 상품카탈로그 사용자의 유형과 권한에 따른 제어를 위한 엔티티 
 *            - Swing UserId를 기반으로 체크 
 * ProgramID: MprdUserAuth.java
 * 작성자		: P070561
 * 작성일		: 2020-06-25
 * 변경로그	:
 * CHG-CD   :   성명         :   반영일자              :   변경 내용
 * ---------- -------------- ------------- ------------------------------------  
 * ver1.01  : P070561   : 2020.07.15      : 상품카탈로그 사용자권한 모델 Entity 최초반영                                       	
 *-----------------------------------------------------------------------------
*******************************************************************************/
package com.sktelecom.swingmsa.mcatalog.context.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import com.sktelecom.swingmsa.mcatalog.context.base.audit.AbstractAuditEntity;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.UserTyp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MprdUserAuth extends AbstractAuditEntity {

	@Id
	@Column(length = 10)
	private String userId;
	
	@Column(nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private UserTyp userTyp ;
	
	@Column(length = 10)
	private String authGrp;
	
	@Column(length = 5)
	private String authLvl;
	
}
