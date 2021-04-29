<%@page import="java.util.Arrays"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="no1" value="${param.num1 }" scope="page"/>
<c:set var="no2" value="${param.num2 }" scope="page"/> <%-- 아래선언과 동일 --%>  
<%-- <%
	Object no2 = 200;
	pageContext.setAttribute("no2",no2);
%> --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>core Basics</title>
</head>
<body>
	<h1>JSTL</h1>
	<p>Jsp Standard Tag Library</p>
	<h1>core Basics</h1>
	<p><c:out value="${no1}"/> ${no1}</p>
	<p><c:out value="${no2}"/> ${no2}</p>
	<p><c:out value="${no1 + no2}"/> ${no1 + no2}</p><!-- 산술연산에서 null으로 0으로 치환 -->
	
	
	<h2>조건식</h2>
	<c:if test="${Integer.parseInt(no1) > Integer.parseInt(no2)}">
		${no1} > ${no2}
	</c:if>		
	<c:if test="${Integer.parseInt(no1) < Integer.parseInt(no2)}">
		${no1} &lt; ${no2}
	</c:if>
	<c:if test="${no1 eq no2}">
		${no1} = ${no2}
	</c:if>
	
	
	<c:set var="rnd" value="<%= new Random().nextInt(100) %>" />
	<p>
	<c:choose>
		<c:when test="${rnd % 5 == 0 }">인형을 뽑았습니다 </c:when>
		<c:when test="${rnd % 5 == 1 }">권총을 뽑았습니다 </c:when>
		<c:otherwise>꽝입니다. 다음 기회에.....</c:otherwise>
	</c:choose>
	</p>
	
	
	<script>
		console.log(${rnd});
	</script>
	
	
	<h2>반복문</h2>
	<c:forEach var="i" begin="1" end="6" step="1">
	<h${i}>Hello World${i}</h${i}>
	</c:forEach>
	
	
	<c:set 
		var="list" 
		value='<%= Arrays.asList("홍길동", "신사임당", "이순신") %>'/>
	<c:forEach items="${list}" var="name" varStatus="vs">
		<p>${vs.index} ${vs.count} ${name}</p>
	</c:forEach>
	
	
</body>
</html>