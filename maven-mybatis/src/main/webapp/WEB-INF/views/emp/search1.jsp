<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
</style>
</head>
<body>
<div id="emp-container">
	<h2>사원정보 </h2>
	
	<div id="search-container">
		<form name="empSearchFrm">
			<select name="searchType" required>
				<option value="">검색타입</option>
				<!-- required여부를 판단할 value="" 반드시 있어야함.-->
				<option value="emp_id">사번</option>
				<option value="emp_name">사원명</option>
				<option value="email">이메일</option>
				<option value="phone">전화번호</option>
			</select>
			<input type="search" name="searchKeyword" required/>	
			<input type="submit" value="검색" />
		</form>
	</div>
	
	
	<table class="tbl-emp">
		<tr>
			<th></th><!-- 1부터 넘버링 처리 -->
			<th>사번</th>
			<th>사원명</th>
			<th>주민번호</th><!--뒷6자리는 ******처리-->
			<th>이메일</th>
			<th>전화번호</th>
			<th>부서코드</th>
			<th>직급코드</th>
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
				<td>${employee.EMP_ID}</td>
				<td>${employee.EMP_NAME}</td>
				<td>
					${fn:substring(employee.EMP_NO,0,7)}*******
					<%-- 
					<c:set var="str1" value="${employee.EMP_NO}"/>
					<c:set var="str2" value="${fn:substring(str1,7,14)}"/>
					${fn:replace(str1, str2, '*******')}
					 --%>
				</td>
				<td>${employee.EMAIL}</td>
				<td>${employee.PHONE}</td>
				<td>${employee.DEPT_CODE}</td>
				<td>${employee.JOB_CODE}</td>
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
