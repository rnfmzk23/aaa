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
package egovframework.example.schl.service;

/**
 * @Class Name : SampleVO.java
 * @Description : SampleVO Class
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
public class KJG_SchlVO extends KJG_SchlDefaultVO {

	private static final long serialVersionUID = 1L;

	/** 코드번호 */
	private String schlcode;
	

	private String schlregion;
	
	/** 코드이름 */
	private String schlname;
	
	/** 내용 */
	private String schlurl;
	
	private String schldate;
	
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSchlcode() {
		return schlcode;
	}
	
	public void setSchlcode(String schlcode) {
		this.schlcode = schlcode;
	}
	
	public String getSchlregion() {
		return schlregion;
	}
	
	public void setSchlregion(String schlregion) {
		this.schlregion = schlregion;
	}
	
	public String getSchlname() {
		return schlname;
	}
	
	public void setSchlname(String schlname) {
		this.schlname = schlname;
	}
	
	public String getSchlurl() {
		return schlurl;
	}
	
	public void setSchlurl(String schlurl) {
		this.schlurl = schlurl;
	}
	
	public String getSchldate() {
		return schldate;
	}
	
	public void setSchldate(String schldate) {
		this.schldate = schldate;
	}
	
	
}
