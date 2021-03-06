<%-- page 지시어 directive. import구문도 모아작성가능(따로작성도가능). --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>

<%

	//jsp 스크립팅 요소 : scriptlet (완전자바공간)
	int sum = 0;
	for(int i = 0; i <= 10; i++){
		sum += i;
	}
	
	
	Date now = new Date();
	//long now = System.currentTimeMillis();//unix time
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jsp - basic</title>
<script>
	window.onload = function(){
		var sum = 0;
		for(var i = 1; i <= 10; i++)
			sum += i;
		document.querySelector("#sum").innerText = sum;
		
		var now = new Date();
		document.querySelector("#now").innerText = now;
		document.querySelector("#now2").innerText = now.getTime();
		
	};
	
	
</script>
</head>
<body>
	<h1>Basic</h1>
	<%-- jsp주석은 java파일 변환시 제거된다 --%>
	<!-- html주석은 client까지 전달된다 -->
	<p>java로 계산된 결과 : <%= sum %></p>  <%-- jsp스크립팅 요소 : 출력식 (;을 찍지않는다)--%>
	<p>java로 계산된 결과 : <% out.print(sum); %></p>  <%-- 위와 같은 구문 --%>
	
	<p>client-side : javascript로 계산된 결과 : <span id="sum"></span></p>
	
	<hr />
	<p>server-side : 현재시각<%= now %>(<%= now.getTime() %>)</p>
	<p>client-side : 현재시각 <span id="now"></span>(<span id="now2"></span>)</p>
</body>
</html>