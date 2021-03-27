<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	//jsp scriptlet 자바공간
	System.out.println(123);

	//사용자 입력값 가져오기
	// request, response에 선언없이 접근가능(servlet에서 넘겨줬으니까)
	String mainMenu = request.getParameter("main_menu");
	String sideMenu = request.getParameter("side_menu");
	String drinkMenu = request.getParameter("drink_menu");
	
	
	//저장된 속성가져오기
	String price = request.getAttribute("price").toString(); //꺼내면 object 타입이므로 String에 담을수없다 -> downcasting
	//int price = (int)reuest.getAttribute("price");
	System.out.println("price@jsp = " + price);
%>


	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<style>
	#main_menu {
		font-size:2em;
		color:navy;
	}
	#side_menu {
		font-size:1.5em;
		color:purple;
	}
	#drink_menu {
		color:yellowgreen;
	}
	#price {
		font-size:2em;
		color:brown;
	}
	
	</style>
	</head>
	<body>
		<h2>감사합니다.</h2>
		<br />
		<span id="main_menu"><%= mainMenu %></span>,
		<span id="side_menu"><%= sideMenu %></span>,
		<span id="drink_menu"><%= drinkMenu %></span>을/를 주문하셨습니다
		<br />총 결제금액은
		<span id="price"> <%= price %>원 </span> 입니다. 
	</body>
	</html>