/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.example.schl.web;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.example.schl.service.KJG_SchlDefaultVO;
import egovframework.example.schl.service.KJG_SchlVO;
import egovframework.example.schl.service.KJG_EgovSchlService;

/**
 * @Class Name : EgovSampleController.java
 * @Description : EgovSample Controller Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Controller
public class KJG_EgovSchlController {

	/** EgovSampleService */
	@Resource(name = "schlService")
	private KJG_EgovSchlService schlService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;

	/**
	 * 글 목록을 조회한다. (pageing)
	 * @param searchVO - 조회할 정보가 담긴 SampleDefaultVO
	 * @param model
	 * @return "egovSampleList"
	 * @exception Exception
	 */
	@RequestMapping(value = "/egovSchlList.do")
	public String selectSchlList(@ModelAttribute("schlsearchVO") KJG_SchlDefaultVO schlsearchVO, ModelMap model) throws Exception {

		/** EgovPropertyService.sample */
		schlsearchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		schlsearchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing setting */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(schlsearchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(schlsearchVO.getPageUnit());
		paginationInfo.setPageSize(schlsearchVO.getPageSize());

		schlsearchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		schlsearchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		schlsearchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<?> schlList = schlService.selectSchlList(schlsearchVO);
		model.addAttribute("resultschlList", schlList);

		int totCnt = schlService.selectSchlListTotCnt(schlsearchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "KJG_schl/KJG_egovSchlList";
	}

	/**
	 * 글 등록 화면을 조회한다.
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param model
	 * @return "egovSampleRegister"
	 * @exception Exception
	 */
	@RequestMapping(value = "/addSchl.do", method = RequestMethod.GET)
	public String addSchlView(@ModelAttribute("schlsearchVO") KJG_SchlDefaultVO schlsearchVO, Model model) throws Exception {
		model.addAttribute("schlVO", new KJG_SchlVO());
		return "KJG_schl/KJG_egovSchlRegister";
	}

	/**
	 * 글을 등록한다.
	 * @param kJG_SampleVO - 등록할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	 */
	@RequestMapping(value = "/addSchl.do", method = RequestMethod.POST)
	public String addSchl(@ModelAttribute("schlsearchVO") KJG_SchlDefaultVO schlsearchVO, KJG_SchlVO SchlVO, BindingResult bindingResult, Model model, SessionStatus status)
			throws Exception {

		// Server-Side Validation
		beanValidator.validate(SchlVO, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("schlVO", SchlVO);
			return "KJG_schl/KJG_egovSchlRegister";
		}

		schlService.insertSchl(SchlVO);
		status.setComplete();
		return "forward:/egovSchlList.do";
	}

	/**
	 * 글 수정화면을 조회한다.
	 * @param id - 수정할 글 id
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param model
	 * @return "egovSampleRegister"
	 * @exception Exception
	 */
	@RequestMapping("/updateSchlView.do")
	public String updateSchlView(@RequestParam("selectedSchl") String schlcode, @ModelAttribute("schlsearchVO") KJG_SchlDefaultVO schlsearchVO, Model model) throws Exception {
		KJG_SchlVO kJG_SchlVO = new KJG_SchlVO();
		kJG_SchlVO.setSchlcode(schlcode);
		// 변수명은 CoC 에 따라 sampleVO
		model.addAttribute(selectSchl(kJG_SchlVO, schlsearchVO));
		return "KJG_schl/KJG_egovSchlRegister";
	}

	/**
	 * 글을 조회한다.
	 * @param kJG_SampleVO - 조회할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return @ModelAttribute("sampleVO") - 조회한 정보
	 * @exception Exception
	 */
	public KJG_SchlVO selectSchl(KJG_SchlVO kJG_SchlVO, @ModelAttribute("schlsearchVO") KJG_SchlDefaultVO schlsearchVO) throws Exception {
		return schlService.selectSchl(kJG_SchlVO);
	}

	/**
	 * 글을 수정한다.
	 * @param kJG_SampleVO - 수정할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	 */
	@RequestMapping("/updateSchl.do")
	public String updateSchl(@ModelAttribute("schlsearchVO") KJG_SchlDefaultVO schlsearchVO, KJG_SchlVO kJG_SchlVO, BindingResult bindingResult, Model model, SessionStatus status)
			throws Exception {

		beanValidator.validate(kJG_SchlVO, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("schlVO", kJG_SchlVO);
			return "KJG_schl/KJG_egovSchlRegister";
		}

		schlService.updateSchl(kJG_SchlVO);
		status.setComplete();
		return "forward:/egovSchlList.do";
	}

	/**
	 * 글을 삭제한다.
	 * @param kJG_SampleVO - 삭제할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	 */
	@RequestMapping("/deleteSchl.do")
	public String deleteSample(KJG_SchlVO kJG_SchlVO, @ModelAttribute("schlsearchVO") KJG_SchlDefaultVO schlsearchVO, SessionStatus status) throws Exception {
		schlService.deleteSchl(kJG_SchlVO);
		status.setComplete();
		return "forward:/egovSchlList.do";
	}

}
