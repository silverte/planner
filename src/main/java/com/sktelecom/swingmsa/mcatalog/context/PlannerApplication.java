/******************************************************************************
 * 프로그램명	: 
 * 설명           : 
 * ProgramID: PlannerApplication.java
 * 작성자		: P123113
 * 작성일		: 2019-11-25
 * 변경로그	:
 * CHG-CD   :   성명         :   반영일자              :   변경 내용
 * ---------- -------------- ------------- ------------------------------------  
 * ver1.01  : P156920   : 2020.07.15      : 기획항목,카탈로그 상세유형코드 추가 
 *-----------------------------------------------------------------------------
*******************************************************************************/
package com.sktelecom.swingmsa.mcatalog.context;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import com.sktelecom.shutdown.tomcat.TomcatGracefulShutdown;
import com.sktelecom.swing.client.EnableSwingClients;
import com.sktelecom.swingmsa.mcatalog.context.base.application.ApplicationConfig;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.CreClCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.CtlgStCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.CtlgTypCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.CtlgTypDtlCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.ExecClCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.InsMthdCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.InsValTypCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.InsValUnitCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.PlanItmTypCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.PlanItmTypDtlCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.ProdRelCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.SelItmCd;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.UserTyp;
import com.sktelecom.swingmsa.mcatalog.context.domain.model.enums.YesNoClCd;
import com.sktelecom.swingmsa.mcatalog.context.mapper.EnumMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableSwingClients
@SpringBootApplication
public class PlannerApplication implements CommandLineRunner {
	
	@Autowired
	ApplicationConfig conf;
	
	@Autowired
	EnumMapper enumMapper;
	
	@Bean
    public ConfigurableServletWebServerFactory webServerFactory(final TomcatGracefulShutdown gracefulShutdown) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(gracefulShutdown);
        return factory;
    }

	public static void main(String[] args) {
		SpringApplication.run(PlannerApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		log.info("run appName ... {}", conf.getAppName());
		log.info("start Date ... {}", new Date());
		
		enumMapper.put("CRE_CL_CD"			,CreClCd.class);
		enumMapper.put("CTLG_ST_CD"			,CtlgStCd.class);
		enumMapper.put("CTLG_TYP_CD"		,CtlgTypCd.class);
		enumMapper.put("CTLG_TYP_DTL_CD"	,CtlgTypDtlCd.class);	//ver1.01		
		enumMapper.put("INS_MTHD_CD"		,InsMthdCd.class);
		enumMapper.put("INS_VAL_TYP_CD"		,InsValTypCd.class);
		enumMapper.put("INS_VAL_UNIT_CD"	,InsValUnitCd.class);
		enumMapper.put("PLAN_ITM_TYP_CD"	,PlanItmTypCd.class);
		enumMapper.put("PLAN_ITM_TYP_DTL_CD",PlanItmTypDtlCd.class); //ver1.01
		enumMapper.put("PROD_REL_CD"		,ProdRelCd.class);
		enumMapper.put("INFO_ITM_YN"		,YesNoClCd.class);
		enumMapper.put("USE_YN"				,YesNoClCd.class);
		enumMapper.put("DISP_ITM_HID_YN"	,YesNoClCd.class);
		enumMapper.put("DISP_ITM_MNDT_YN"	,YesNoClCd.class);
		enumMapper.put("DISP_ITM_READ_ONLY"	,YesNoClCd.class);
		enumMapper.put("MAIN_PROD_YN"		,YesNoClCd.class);
		enumMapper.put("SEL_ITM_CD"			,SelItmCd.class);
		enumMapper.put("EXEC_CL_CD"			,ExecClCd.class);
	}
} 