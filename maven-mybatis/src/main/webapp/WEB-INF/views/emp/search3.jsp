<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	//사용자 요청값(String[])을 List로 변환. contains메소드 사용을 위해 List로 변환.
	String[] jobCodeArr = request.getParameterValues("jobCode");
	String[] deptCodeArr = request.getParameterValues("deptId");
	
	List<String> jobCodeList = 
			jobCodeArr != null ? Arrays.asList(jobCodeArr) : null;
	List<String> deptCodeList = 
			deptCodeArr != null ? Arrays.asList(deptCodeArr) : null;
	pageContext.setAttribute("jobCodeList", jobCodeList);
	pageContext.setAttribute("deptCodeList", deptCodeList);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mybatis실습</title>
<style>
div#emp-container{text-align:center;}
table.tbl-emp{
	margin:0 auto;
	border:1px solid; 
	border-collapse:collapse;
}
table.tbl-emp th{
	border:1px solid;
	padding:5px;
	background:lightseagreen;
}
table.tbl-emp td {
	border:1px solid;
	padding:5px;
}
div#search-container{
	padding:15px 0;
}

input#btn-search { width: 350px; background: lightslategray; color: white; box-shadow: 0px 3px 15px grey; }
table#tbl-search { margin:0 auto; }
table#tbl-search th,table#tbl-search td {padding:5px 15px;}
 table#tbl-search td {text-align:left;}



</style>
</head>
<body>
<div id="emp-container">
	<h2>사원정보 </h2>
	
	<div id="search-container">
		<form name="empSearchFrm" >
		<h3>검색</h3>
		<input type="button" value="초기화" />
		<table id="tbl-search">
			<!-- 직급조회 -->
			<tr>
				<th>직급</th>
				<td>
				<c:forEach items="${jobList}" var="job">
					<input type="checkbox" name="jobCode" id="${job.jobCode}" value="${job.jobCode}" 
					${jobCodeList.contains(job.jobCode) ? 'checked' : ''}/>
					<label for="${job.jobCode}">${job.jobName}</label>
				</c:forEach>
				</td>
			</tr>
			<!-- 부서조회(직급조회와  모두 일치하는 사원) 
				 input:checkbox + label 는 3개마다 개행할것
				 인턴사원(D0)도 조회될수있도록 할것.
			-->
			<tr>
				<th>부서</th>
				<td>
				<c:forEach items="${deptList}" var="dept" varStatus="vs">
					<input type="checkbox" name="deptId" id="${dept.deptId}" value="${dept.deptId}"
					${deptCodeList.contains(dept.deptId) ? 'checked' : ''} />
					<label for="${dept.deptId}">${dept.deptTitle}</label>
					<c:if test="${vs.count % 3 eq 0}"><br /></c:if>
				</c:forEach>
					<input type="checkbox" name="deptId" id="D0" value="D0" 
					${deptCodeList.contains('D0') ? 'checked' : ''}/>
					<label for="D0">인턴</label>
				</td>
			</tr>
			
			<tr>
				<th colspan="2">
					<input type="submit" id="btn-search" value="검색"  />
				</th>
			</tr>
		</table>
	</form>
	</div>
	
	
	<table class="tbl-emp">
		<tr>
			<th></th><!-- 1부터 넘버링 처리 -->
			<th>사번</th>
			<th>사원명</th>
			<th>주민번호</th><!--뒷6자리는 ******처리-->
			<th>성별</th>
			<th>이메일</th>
			<th>전화번호</th>
			<th>부서명</th>
			<th>직급명</th>
			<th>급여레벨</th>
			<th>급여</th><!--원화기호, 세자리마다 콤마표시-->
			<th>보너스율</th><!--percent로 표시-->
			<th>매니져 사번</th>
			<th>입사일</th><!--날짜형식 yyyy/MM/dd-->
			<th>퇴사여부</th>
		</tr>
		<!-- 조회된 데이터가 있는 경우와 없는 경우를 분기처리 하세요 -->
		<c:if test="${empty list}">
		<tr>
			<td colspan="14">등록된 사원이 존재하지않습니다.</td>
		</tr>
		</c:if>	
	
		<c:if test="${not empty list}">
		<c:forEach items="${list}" var="employee" varStatus="vs">
			<tr>
				<td>${vs.count}</td>
				<td>
					<a href="${pageContext.request.contextPath}/emp/updateEmp.do?empId=${employee.EMP_ID}">${employee.EMP_ID}</a>
				</td>
				<td>${employee.EMP_NAME}</td>
				<td>
					${fn:substring(employee.EMP_NO,0,7)}*******
					<%-- 
					<c:set var="str1" value="${employee.EMP_NO}"/>
					<c:set var="str2" value="${fn:substring(str1,7,14)}"/>
					${fn:replace(str1, str2, '*******')}
					 --%>
				</td>
				<td>${employee.GENDER}</td>
				<td>${employee.EMAIL}</td>
				<td>${employee.PHONE}</td>
				<td>${employee.DEPT_TITLE eq null ? '인턴' : employee.DEPT_TITLE}</td>
				<td>${employee.JOB_NAME}</td>
				<td>${employee.SAL_LEVEL}</td>
				<td>
					<fmt:formatNumber value="${employee.SALARY}" type="currency"/>
				</td>
				<td>
					<fmt:formatNumber value="${employee.BONUS}" type="percent"/>				
				</td>
				<td>${employee.MANAGER_ID}</td>
				<td>
					<fmt:formatDate value="${employee.HIRE_DATE}" pattern="yy/MM/dd"/>
				</td>
				<td>${employee.QUIT_YN}</td>
			</tr>
		</c:forEach>
		
		
		
		
		
		</c:if>
	
	
	
	
	</table>
</div>

</body>
</html>
