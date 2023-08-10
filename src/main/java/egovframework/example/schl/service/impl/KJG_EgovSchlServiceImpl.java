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
package egovframework.example.schl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.schl.service.KJG_SchlDefaultVO;
import egovframework.example.schl.service.KJG_SchlVO;
import egovframework.example.schl.service.KJG_EgovSchlService;



/**
 * @Class Name : EgovSampleServiceImpl.java
 * @Description : Sample Business Implement Class
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

@Service("schlService")
public class KJG_EgovSchlServiceImpl extends EgovAbstractServiceImpl implements KJG_EgovSchlService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KJG_EgovSchlServiceImpl.class);

	/** SampleDAO */
	// TODO ibatis 사용
	@Resource(name = "schlDAO")
	private KJG_SchlDAO schlDAO;
	// TODO mybatis 사용
	//  @Resource(name="sampleMapper")
	//	private SampleMapper sampleDAO;

	/** ID Generation */
	@Resource(name = "egovIdGnrService")
	private EgovIdGnrService egovIdGnrService;

	/**
	 * 글을 등록한다.
	 * @param vo - 등록할 정보가 담긴 SampleVO
	 * @return 등록 결과
	 * @exception Exception
	 */
	@Override
	public String insertSchl(KJG_SchlVO vo) throws Exception {
//		LOGGER.debug(vo.toString());
//
//		/** ID Generation Service */
//		String schl = egovIdGnrService.getNextStringId();
//		vo.setSchl(schl);
//		LOGGER.debug(vo.toString());

		schlDAO.insertSchl(vo);
		return "redirect:egovSchlList.do";
	}

	/**
	 * 글을 수정한다.
	 * @param vo - 수정할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	 */
	@Override
	public void updateSchl(KJG_SchlVO vo) throws Exception {
		schlDAO.updateSchl(vo);
	}

	/**
	 * 글을 삭제한다.
	 * @param vo - 삭제할 정보가 담긴 SampleVO
	 * @return void형
	 * @exception Exception
	 */
	@Override
	public void deleteSchl(KJG_SchlVO vo) throws Exception {
		schlDAO.deleteSchl(vo);
	}

	/**
	 * 글을 조회한다.
	 * @param vo - 조회할 정보가 담긴 SampleVO
	 * @return 조회한 글
	 * @exception Exception
	 */
	@Override
	public KJG_SchlVO selectSchl(KJG_SchlVO vo) throws Exception {
		KJG_SchlVO resultVO = schlDAO.selectSchl(vo);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		return resultVO;
	}

	/**
	 * 글 목록을 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 목록
	 * @exception Exception
	 */
	@Override
	public List<?> selectSchlList(KJG_SchlDefaultVO schlsearchVO) throws Exception {
		return schlDAO.selectSchlList(schlsearchVO);
	}

	/**
	 * 글 총 갯수를 조회한다.
	 * @param searchVO - 조회할 정보가 담긴 VO
	 * @return 글 총 갯수
	 * @exception
	 */
	@Override
	public int selectSchlListTotCnt(KJG_SchlDefaultVO schlsearchVO) {
		return schlDAO.selectSchlListTotCnt(schlsearchVO);
	}
	
}
