<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String life = "life is very short";
	pageContext.setAttribute("life",life);


%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>El Basics</title>
</head>
<body>
	<h1>El Basics</h1>
	<%-- scope생략시 pageScope - requestScope - sessionScope - applicationScope 순으로 뒤진다 
		단, 각각의 scope에 같은 이름의 value가 담겨있다면 우선순위가 높은 scope에 값이 나온다. 그럴땐 scope를 명시할것
	--%>
	<p>${requestScope.coffee}</p>
	<%-- <p>${coffee}</p> --%>
	<p>${requestScope.serverTime}</p>
	<%-- <p>${serverTime}</p> --%>
	<p>${requestScope.honggd}</p>
	<%-- <p>${honggd}</p> --%>
	<%-- OGNL 방식 --%>
	<p>${requestScope.honggd.id}</p>
	<%-- <p>${honggd.id}</p> --%>
	<p>${requestScope.honggd.name}</p>
	<%-- <p>${honggd.name}</p> --%>
	<p>${requestScope.honggd.gender}</p>
	<%-- <p>${honggd.gender}</p> --%>
	<p>${requestScope.honggd.age}</p>
	<%-- <p>${honggd.age}</p> --%>
	<p>${requestScope.honggd.married}</p>
	<%-- <p>${honggd.married}</p> --%>
	
	<p>${sessionScope.book}</p>
	<p>${applicationScope.movie}</p>
	<p>${pageScope.life}</p> <%-- 위에서 선언후 담아놔야한다 --%>
	
	<p>[${cow}]</p> 
	<%-- 
		cow란 값이 없지만 오류가발생하지않는다. 
		EL은 NULLpointerException이 유발하지않는다
		null인경우 ""출력 
	--%>
	
	<h2>list</h2>
	<p>${list}</p>
	<p>${list[0]}</p>
	<p>${list[1]}</p>
	<p>${list[2]}</p>
	
	<h2>map</h2>
	<p>${map}</p>
	<p>${map.language}</p>
	<p>${map["Dr.jang"]}</p>
	<p>${map["Dr.jang"].name}</p>
	<p>${map["Dr.jang"]["name"]}</p>
	
	<%-- 사용자입력값 전달시(url주소뒤에 붙어서) --%>
	<h1>Param</h1>
	<p>${param.pname}</p>
	<p>${param.pcount}</p>
	<%-- <p>${param.option}</p> --%>	
	<%-- <p>${paramValues.pname[0]}</p> --%>
	<p>${paramValues.option[0]}</p>
	<p>${paramValues.option[1]}</p>
	
	
	<h1>cookie</h1>
	<p>${cookie.JSESSIONID }</p>
	<p>${cookie.JSESSIONID.value }</p>
	
	
	
	<h1>header</h1>
	<%-- request Header에 있는 값을 가져다 쓸수있다. --%>
	<p>${header.accept}</p>
	<p>${header['user-agent']}</p>
	

	<h1>pageContext</h1>
	<!--  
		getPage()
		getRequest()
			getMethod() : GET|POST
			getContextPath() : /action
		getResponse()
		getSession()
		getServletContext()
		getErrorData()

	 -->
	
	<p>${pageContext.request.method}</p>
	<p>${pageContext.request.contextPath}</p>
		
	
	
	
	
	
	
	
	


	
	
</body>
</html>