<%@page import="java.util.ArrayList"%>
<%@page import="com.kh.person.model.vo.Person"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String str1 = "안녕";
	String str2 = new String("안녕");
	
	int big = 100;
	int small = 30;
	
	Person p1 = new Person("honggd","홍길동",'남',35,true);
	Person p2 = new Person("honggd","홍길동",'남',35,true);
	
	List<String> list = null;
	list = new ArrayList<>();
	list.add("hello world");

	pageContext.setAttribute("str1", str1);
	pageContext.setAttribute("str2", str2);
	pageContext.setAttribute("big", big);
	pageContext.setAttribute("small", small);
	pageContext.setAttribute("p1", p1);
	pageContext.setAttribute("p2", p2);
	pageContext.setAttribute("list", list);
	
	pageContext.setAttribute("emptyStr", "");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>El Operator</title>
</head>
<body>
	<h1>El Operator</h1>
	<h2>산술연산</h2>
	<p>${big + small}</p>
	<p>${big - small}</p>
	<p>${big - '20'}</p>
	<%-- <p>${big - 'ab'}</p> java.lang.NumberFormatException: For input string: "ab" --%>
	<p>${big * small}</p>
	<p>${big / small} ${big div small}</p>
	<p>${big % small} ${big mod small}</p>
	
	<%-- <p>${str1 + str2}</p> java.lang.NumberFormatException: For input string: "안녕" --%>
	<p>${str1}${str2} ${str1.concat(str2)}</p>

	<h2>비교연산</h2>
	<p>${big > small} ${big gt small}</p>
	<p>${big < small} ${big lt small}</p>
	<p>${big >= small} ${big ge small}</p> <!-- greater than or equal to -->
	<p>${big <= small} ${big le small}</p>
	<p>${big == small} ${big eq small}</p>
	<p>${big != small} ${big ne small}</p>

	<hr />
	<%-- el의 동등비교 연산은 내부적으로 equals사용 --%>
	<p><%= str1 == str2 %> ${str1 == str2} ${str1 eq str2}</p><!-- f t t -->
	<p><%= str1 != str2 %> ${str1 != str2} ${str1 ne str2}</p><!-- t f f -->

	<!-- 객체비교 -->
	<p>${p1 == p2} ${p1 eq p2}</p> <!-- t t : equals(),hashCode() 오버라이딩해야한다 -->
	
	
	<!-- 객체가 null이거나 요소가 없는지 여부 -->
	<p>${empty list} ${not empty list}</p><!-- f t-->
	<p>${empty emptyStr} ${not empty emptyStr}</p> <!-- t f-->
	
	
	
	
	
	
	
	
	
	
</body>
</html>