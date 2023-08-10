<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
  /**
  * @Class Name : egovSampleList.jsp
  * @Description : Sample List 화면
  * @Modification Information
  *
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2009.02.01            최초 생성
  *
  * author 실행환경 개발팀
  * since 2009.02.01
  *
  * Copyright (C) 2009 by MOPAS  All right reserved.
  */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>코드관리 목록</title>
    <link type="text/css" rel="stylesheet" href="<c:url value='/css/KJG_egovframework/sample.css'/>"/>
    <script type="text/javaScript" language="javascript" defer="defer">
        <!--
        /* 글 수정 화면 function */
        function fn_egov_selectSchl(schlcode) {
        	document.listForm.selectedCode.value = schlcode;
           	document.listForm.action = "<c:url value='/updateSchlView.do'/>";
           	document.listForm.submit();
        }
        
        /* 글 등록 화면 function */
        function fn_egov_addSchlView() {
           	document.listForm.action = "<c:url value='/addSchl.do'/>";
           	document.listForm.submit();
        }
        
        /* 글 목록 화면 function */
        function fn_egov_selectSchlList() {
        	document.listForm.action = "<c:url value='/egovSchlList.do'/>";
           	document.listForm.submit();
        }
        
        /* pagination 페이지 링크 function */
        function fn_egov_link_page(pageNo){
        	document.listForm.pageIndex.value = pageNo;
        	document.listForm.action = "<c:url value='/egovSchlList.do'/>";
           	document.listForm.submit();
        }
        
        /* 글 목록 화면 function */
        function fn_egov_selectList() {
        	document.listForm.action = "<c:url value='/egovSampleList.do'/>";
           	document.listForm.submit();
        }
        
        /* 글 등록 화면 function */
        function fn_egov_selectCodeList() {
           	document.listForm.action = "<c:url value='/egovCodeList.do'/>";
           	document.listForm.submit();
        }
        
        //-->
    </script>
</head>

<body style="text-align:center; margin:0 auto; display:inline; padding-top:100px;">
    <form:form modelAttribute="schlsearchVO" id="listForm" name="listForm" method="post">
        <input type="hidden" name="selectedCode" />
        <div id="content_pop">
        	<!-- 타이틀 -->
        	<div id="title">
        		<ul>
        			<li><img src="<c:url value='/images/KJG_egovframework/KJG_example/title_dot.gif'/>" alt=""/>Code List</li>
        		</ul>
        	</div>
        	<!-- // 타이틀 -->
        	<div id="search">
        		<ul>
        			<li>
        			    <label for="searchCondition" style="visibility:hidden;"><spring:message code="search.choose" /></label>
        				<form:select path="searchCondition" cssClass="use">
        					<form:option value="1" label="코드명" />
        					<form:option value="0" label="코드" />
        				</form:select>
        			</li>
        			<li><label for="searchKeyword" style="visibility:hidden;display:none;"><spring:message code="search.keyword" /></label>
                        <form:input path="searchKeyword" cssClass="txt"/>
                    </li>
        			<li>
        	            <span class="btn_blue_l">
        	                <a href="javascript:fn_egov_selectSchlList();"><spring:message code="button.search" /></a>
        	                <img src="<c:url value='/images/KJG_egovframework/KJG_example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
        	            </span>
        	        </li>
                </ul>
        	</div>
        	<!-- List -->
        	<div id="table">
        		<table width="100%" border="0" cellpadding="0" cellspacing="0" summary="코드, 코드명, 코드설명 표시하는 테이블">
        			<caption style="visibility:hidden">학교코드, 지역, 학교명, 주소, 가입날짜</caption>
        			<%-- <colgroup>
        				<col width="40"/>
        				<col width="100"/>
        				<col width="150"/>
        				<col width="200"/>
        				
        			</colgroup> --%>
        			<tr>
        				<th align="center">No</th>
        				<th align="center">학교코드</th>
        				<th align="center">지역</th>
        				<th align="center">학교명</th>
        				<th align="center">URL</th>
        				<th align="center">가입날짜</th>
        				
        			<c:forEach var="result" items="${resultschlList}" varStatus="status">
            			<tr>
            				<td align="center" class="listtd"><c:out value="${paginationInfo.totalRecordCount+1 - ((schlsearchVO.pageIndex-1) * schlsearchVO.pageSize + status.count)}"/></td>
            				<td align="center" class="listtd"><a href="javascript:fn_egov_selectSchl('<c:out value="${result.kjgSchlId}"/>')"><c:out value="${result.kjgSchlId}"/></a></td>
            				<td align="center" class="listtd"><c:out value="${result.kjgSchlName}"/>&nbsp;</td>
            				<td align="center" class="listtd"><c:out value="${result.kjgSchlRegion}"/>&nbsp;</td>
            				<td align="center" class="listtd"><c:out value="${result.kjgSchlUrl}"/>&nbsp;</td>
            				<td align="center" class="listtd"><c:out value="${result.kjgSchlDate}"/>&nbsp;</td>
            			</tr>
        			</c:forEach>
        		</table>
        	</div>
        	<!-- /List -->
        	<div id="paging">
        		<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="fn_egov_link_page" />
        		<form:hidden path="pageIndex" />
        	</div>
        	<div id="sysbtn">
        	  <ul>
        	      <li>
        	          <span class="btn_blue_l">
        	              <a href="javascript:fn_egov_addSchlView();"><spring:message code="button.create" /></a>
                          <img src="<c:url value='/images/KJG_egovframework/KJG_example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
                      </span>
                     
                  </li>
                  <li>
                    <span class="btn_blue_l">
        	              <a href="javascript:fn_egov_selectList();">글 목록</a>
                          <img src="<c:url value='/images/KJG_egovframework/KJG_example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
                      </span>
                  </li>
                  <li>
                  	  <span class="btn_blue_l">
        	              <a href="javascript:fn_egov_selectCodeList();">코드관리</a>
                          <img src="<c:url value='/images/KJG_egovframework/KJG_example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
                      </span>
                  </li>
              </ul>
        	</div>
        </div>
    </form:form>
</body>
</html>
