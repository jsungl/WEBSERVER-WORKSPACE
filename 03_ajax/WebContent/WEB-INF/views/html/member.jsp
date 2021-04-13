<%@page import="com.kh.member.model.vo.Member"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	List<Member> list = (List<Member>)request.getAttribute("list");
%>

<table>
<% for(Member member : list) { %>
	<tr>
		<td><img src="<%= request.getContextPath() %>/images/<%= member.getProfile() %>" alt="<%= member.getName() %>" /></td>
		<td><%= member.getId() %></td>
		<td><%= member.getName() %></td>
	</tr>

<% } %> 
</table>