/******************************************************************************
 * 프로그램명	: 상품카탈로그정보 모델
 * 설명           : 상품의 카탈로그 정보 엔티티 정의 
 *            -상품별로 하나씩 생성되며 각 상품은 카탈로그 ID로 그룹핑 가능 
 * ProgramID: MprdProdCtlgInfo.java
 * 작성자		: P123113
 * 작성일		: 2019-11-25
 * 변경로그	:
 * CHG-CD   :   성명         :   반영일자              :   변경 내용
 * ---------- -------------- ------------- ------------------------------------  
 * ver1.01  : P156920   : 2020.07.15      : 1) planItmTypDtlCd추가                                        	
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
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.CtlgStCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.CtlgTypCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.CtlgTypDtlCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.ProdRelCd;
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
public class MprdProdCtlgInfo extends AbstractAuditEntity {

	@Id
	@Column(length = 10)
	private String prodId;
	
	@Column(length = 100)
	private String prodNm;
	
	@Column(nullable = false, length = 10)
	private String ctlgId;
	
	@Column(nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private CtlgStCd ctlgStCd;
	
	@Column(nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private YesNoClCd mainProdYn;
	
	@Column(nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private ProdRelCd prodRelCd;
	
	@Column(nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private CtlgTypCd ctlgTypCd;
	
	@Column(length = 10)						
	@Enumerated(EnumType.STRING)
	private CtlgTypDtlCd ctlgTypDtlCd;		//ver1.01
	
	@Column(length = 10)
	private String ctlgVerNum;
	
	@Column(length = 8)
	private String mktgSchdDt;
	
	@Column(length = 4000)
	private String chgCtt;
	
	@Column(length = 4000)
	private String prodMainCtt;
	
	@Column(length = 10)
	private String reqrId;
	
	@Column(length = 10)
	private String reqOrgId;
	
	
	@Transient	
	private String mainProdYnNm;	
	public String getMainProdYnNm() {
		return Utils.getEnumValue(YesNoClCd.class, mainProdYn);
	}
	
	@Transient	
	private String ctlgStCdNm;	
	public String getCtlgStCdNm() {
		return Utils.getEnumValue(CtlgStCd.class, ctlgStCd);
	}
	
	@Transient	
	private String prodRelCdNm;	
	public String getProdRelCdNm() {
		return Utils.getEnumValue(ProdRelCd.class, prodRelCd);
	}
	
	@Transient	
	private String ctlgTypCdNm;	
	public String getCtlgTypCdNm() {
		return Utils.getEnumValue(CtlgTypCd.class, ctlgTypCd);
	}
}
