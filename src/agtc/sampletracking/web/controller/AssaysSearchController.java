/*
 * Created on Dec 1, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package agtc.sampletracking.web.controller;
import org.springframework.web.bind.RequestUtils;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import agtc.sampletracking.dao.*;
import agtc.sampletracking.model.*;
import agtc.sampletracking.web.*;
import agtc.sampletracking.web.command.*;
import agtc.sampletracking.web.searchFields.AssaySearchFields;
import agtc.sampletracking.web.searchFields.OperatorList;
import agtc.sampletracking.bus.manager.*;
import org.springframework.validation.BindException;
import java.util.*;


/**
 * @author Gloria Deng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AssaysSearchController extends BasicSearchController {
	private TestManager testManager;
	private Log log = LogFactory.getLog(TestsSearchController.class);
	
	protected ModelAndView onSubmit(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, java.lang.Object command,BindException errors) throws java.lang.Exception{
		List searchResults = handleSubmit(request,command,"assay");
		
		if (searchResults.size() < 1) {
			ModelAndView view = new ModelAndView(new RedirectView("assays.htm"));
			putMessage(request,view.getModel());
			return view;
		}

		if (searchResults.size() > 1) {
			// multiple tests found
			return new ModelAndView("assayList", "assayList", searchResults);
		}

		//return only one test
		Assay assay = (Assay) searchResults.iterator().next();

		ModelAndView view = new ModelAndView(new RedirectView(getSuccessView()));
		Map myModel = view.getModel();
		myModel.put("assayId",assay.getAssayId());
		return view;
	}
	
	protected  List performSearch(List crtList,List lgcList){
		return testManager.searchAssay(crtList,lgcList);
	}
	
	protected java.util.Map referenceData(javax.servlet.http.HttpServletRequest request)
	   throws java.lang.Exception
	{
		Map models = new HashMap();
		referenceData(request,models,"assay");
		log.debug("referenceData is called");
		return models;
	}


	/**
	 * @return
	 */
	public TestManager getTestManager() {
		return testManager;
	}


	/**
	 * @param manager
	 */
	public void setTestManager(TestManager manager) {
		testManager = manager;
	}

	

}