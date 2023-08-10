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
package egovframework.example.code.web;

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

import egovframework.example.code.service.KJG_CodeDefaultVO;
import egovframework.example.code.service.KJG_CodeVO;
import egovframework.example.code.service.KJG_EgovCodeService;

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
public class KJG_EgovCodeController {

	/** EgovSampleService */
	@Resource(name = "codeService")
	private KJG_EgovCodeService codeService;

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
	@RequestMapping(value = "/egovCodeList.do")
	public String selectCodeList(@ModelAttribute("csearchVO") KJG_CodeDefaultVO csearchVO, ModelMap model) throws Exception {

		/** EgovPropertyService.sample */
		csearchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		csearchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing setting */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(csearchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(csearchVO.getPageUnit());
		paginationInfo.setPageSize(csearchVO.getPageSize());

		csearchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		csearchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		csearchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<?> codeList = codeService.selectCodeList(csearchVO);
		model.addAttribute("resultcodeList", codeList);

		int totCnt = codeService.selectCodeListTotCnt(csearchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "KJG_code/KJG_egovCodeList";
	}

	/**
	 * 글 등록 화면을 조회한다.
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param model
	 * @return "egovSampleRegister"
	 * @exception Exception
	 */
	@RequestMapping(value = "/addCode.do", method = RequestMethod.GET)
	public String addCodeView(@ModelAttribute("csearchVO") KJG_CodeDefaultVO csearchVO, Model model) throws Exception {
		model.addAttribute("codeVO", new KJG_CodeVO());
		return "KJG_code/KJG_egovCodeRegister";
	}

	/**
	 * 글을 등록한다.
	 * @param kJG_SampleVO - 등록할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	 */
	@RequestMapping(value = "/addCode.do", method = RequestMethod.POST)
	public String addCode(@ModelAttribute("csearchVO") KJG_CodeDefaultVO searchVO, KJG_CodeVO CodeVO, BindingResult bindingResult, Model model, SessionStatus status)
			throws Exception {

		// Server-Side Validation
		beanValidator.validate(CodeVO, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("codeVO", CodeVO);
			return "KJG_code/KJG_egovCodeRegister";
		}

		codeService.insertCode(CodeVO);
		status.setComplete();
		return "forward:/egovCodeList.do";
	}

	/**
	 * 글 수정화면을 조회한다.
	 * @param id - 수정할 글 id
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param model
	 * @return "egovSampleRegister"
	 * @exception Exception
	 */
	@RequestMapping("/updateCodeView.do")
	public String updateCodeView(@RequestParam("selectedCode") String code, @ModelAttribute("csearchVO") KJG_CodeDefaultVO csearchVO, Model model) throws Exception {
		KJG_CodeVO kJG_CodeVO = new KJG_CodeVO();
		kJG_CodeVO.setCode(code);
		// 변수명은 CoC 에 따라 sampleVO
		model.addAttribute(selectCode(kJG_CodeVO, csearchVO));
		return "KJG_code/KJG_egovCodeRegister";
	}

	/**
	 * 글을 조회한다.
	 * @param kJG_SampleVO - 조회할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return @ModelAttribute("sampleVO") - 조회한 정보
	 * @exception Exception
	 */
	public KJG_CodeVO selectCode(KJG_CodeVO kJG_CodeVO, @ModelAttribute("csearchVO") KJG_CodeDefaultVO csearchVO) throws Exception {
		return codeService.selectCode(kJG_CodeVO);
	}

	/**
	 * 글을 수정한다.
	 * @param kJG_SampleVO - 수정할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	 */
	@RequestMapping("/updateCode.do")
	public String updateCode(@ModelAttribute("csearchVO") KJG_CodeDefaultVO csearchVO, KJG_CodeVO kJG_CodeVO, BindingResult bindingResult, Model model, SessionStatus status)
			throws Exception {

		beanValidator.validate(kJG_CodeVO, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("codeVO", kJG_CodeVO);
			return "KJG_code/KJG_egovCodeRegister";
		}

		codeService.updateCode(kJG_CodeVO);
		status.setComplete();
		return "forward:/egovCodeList.do";
	}

	/**
	 * 글을 삭제한다.
	 * @param kJG_SampleVO - 삭제할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	 */
	@RequestMapping("/deleteCode.do")
	public String deleteSample(KJG_CodeVO kJG_CodeVO, @ModelAttribute("csearchVO") KJG_CodeDefaultVO csearchVO, SessionStatus status) throws Exception {
		codeService.deleteCode(kJG_CodeVO);
		status.setComplete();
		return "forward:/egovCodeList.do";
	}

}
