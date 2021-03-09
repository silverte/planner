/******************************************************************************
 * 프로그램명	: 기획항목 Controller
 * 설명           : Client에 통해 요청받은 매핑정보 정의
 * ProgramID: PlannerController.java
 * 작성자		: P123113
 * 작성일		: 2019-11-25
 * 변경로그	:
 * CHG-CD     :   성명         :   반영일자              :   변경 내용
 * ---------- -------------- ------------- ------------------------------------  
 * ver1.01-1  : P156920   : 2020.07.15      : 기획자, 관리자 역할에 따른 권한체크 API
 * ver1.01-2  :    		  :                 : 기획항목ID 채번 API
 * ver1.01-3  : 		  : 		        : 매핑여부에 따른 정보항목여부 체크 API
 * ver1.01-4  : 		  : 		        : swingIf에서 호출하는 상품목록조회 API
 *-----------------------------------------------------------------------------
*******************************************************************************/
package com.sktelecom.swingmsa.mcatalog.context.application.sp.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.YesNoClCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.service.PlannerService;

import io.swagger.annotations.ApiOperation;

@RestController
//@RequestMapping("/api/v1")
public class PlannerRestController implements PlannerService {

	@Autowired
	private PlannerService plannerService;

	@Override
	@PostMapping("/api/v1/findPlanItmList")
	@ApiOperation(value = "기획항목 목록 조회",notes="")
	public Map<String, Object> findPlanItmList(@RequestBody Map<String, Object> params) {
		return plannerService.findPlanItmList(params);
	}
	
	@Override
	@PostMapping("/api/v1/findProdList")
	@ApiOperation(value = "상품목록조회",notes="")
	public Map<String, Object> findProdList(@RequestBody Map<String, Object> params) {
		return plannerService.findProdList(params);
	}

	@Override
	@PostMapping({"/api/v1/findCtlgInfo"})
	@ApiOperation(value = "카탈로그정보조회",notes="")
	public Map<String, Object> findCtlgInfo(@RequestBody Map<String, Object> params) {	
		return plannerService.findCtlgInfo(params);
	}

	@Override
	@PostMapping("/api/v1/findCtlgInfoHst")
	@ApiOperation(value = "카탈로그정보이력조회",notes="")
	public Map<String, Object> findCtlgInfoHst(@RequestBody Map<String, Object> params) {
		return plannerService.findCtlgInfoHst(params);
	}

	@Override
	@PostMapping("/api/v1/saveCtlgInfo")
	@ApiOperation(value = "카탈로그정보 저장",notes="")
	public Map<String, Object> saveCtlgInfo(@RequestBody Map<String, Object> params) {
		return plannerService.saveCtlgInfo(params);
	}
	
	@Override
	@PostMapping("/api/v1/updateCtlgStCd")
	@ApiOperation(value = "카탈로그상태변경",notes="")
	public Map<String, Object> updateCtlgStCd(@RequestBody Map<String, Object> params) {
		return plannerService.updateCtlgStCd(params);
	}

	@Override
	@PostMapping("/api/v1/findPlanItmClCdList")
	@ApiOperation(value = "기획항목분류코드목록 조회",notes="")
	public Map<String, Object> findPlanItmClCdList(@RequestBody Map<String, Object> params) {
		return plannerService.findPlanItmClCdList(params);
	}

	@Override
	@PostMapping("/api/v1/findPlanItmMgmtList")
	@ApiOperation(value = "기획항목관리용 기획항목목록 조회",notes="")
	public Map<String, Object> findPlanItmMgmtList(@RequestBody Map<String, Object> params) {
		return plannerService.findPlanItmMgmtList(params);
	}

	@Override
	@PostMapping("/api/v1/savePlanItmInfo")
	@ApiOperation(value = "기획항목 저장",notes="")
	public Map<String, Object> savePlanItmInfo(@RequestBody Map<String, Object> params) {
		return plannerService.savePlanItmInfo(params);
	}
	
	@Override
	@PostMapping("/api/v1/savePlanItmClCdInfo")
	@ApiOperation(value = "기획항목분류코드 저장",notes="")
	public Map<String, Object> savePlanItmClCdInfo(@RequestBody Map<String, Object> params) {
		return plannerService.savePlanItmClCdInfo(params);
	}
	
	@Override
	@PostMapping("/api/v1/deleteCtlgInfo")
	@ApiOperation(value = "카탈로그정보 전체 삭제",notes="")
	public Map<String, Object> deleteCtlgInfo(@RequestBody Map<String, Object> params) {
		return plannerService.deleteCtlgInfo(params);
	}

	//ver1.01-1	
	@Override
	@PostMapping("/api/v1/findNewId")
	@ApiOperation(value = "채번",notes="")
	public Map<String, Object> findNewId(@RequestBody Map<String, Object> params) {
		return plannerService.findNewId(params);
	}
	
	//ver1.01-2 
	@Override
	@PostMapping("/api/v1/getUserType")
	@ApiOperation(value = "기획자, 관리자 역할에 따른 권한체크",notes="")
	public Map<String, Object> getUserType(@RequestBody Map<String, Object> params) {
		return plannerService.getUserType(params);
	}

		
	/************************************************************************************************************************
	 * client Proxy
	 ************************************************************************************************************************/	
	
	//ver1.01-3
	@Override
	@PostMapping("/client/manager/updatePlanItmLst")
	@ApiOperation(value = "정보항목여부 Validation",notes="Planner에서 호출하는 매핑정보 저장시 정보항목 여부 Y->N 업뎃")
	public void updateMprdPlanItmLst(@RequestParam(value="planItmId") String planItmId
			 						,@RequestParam(value="infoItmYn") String infoItmYn
			 						,@RequestParam(value="userId") String userId)  throws Exception{
		plannerService.updateMprdPlanItmLst(planItmId,infoItmYn,userId);
	}

	//ver1.01-4
	@Override
	@GetMapping("/client/SwingIf/findProdList")
	@ApiOperation(value = "상품목록조회",notes="swingIf에서 호출하는 상품목록조회")
	public List<String> findSwingIfProdlist(@RequestParam(value="ctlgId") String ctlgId){
		return plannerService.findSwingIfProdlist(ctlgId);
	}


}
