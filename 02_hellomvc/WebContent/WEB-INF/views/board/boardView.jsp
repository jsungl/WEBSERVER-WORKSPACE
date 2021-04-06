<%@page import="board.model.vo.Attachment"%>
<%@page import="board.model.vo.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
  
  Board board = (Board)request.getAttribute("board");
  Attachment attach = (Attachment)request.getAttribute("attach");
  int no = board.getNo();
  String title = board.getTitle();
  String writer = board.getWriter();
  int readCount = board.getReadCount();
  String content = board.getContent();





%>




<link rel="stylesheet" href="<%=request.getContextPath()%>/css/board.css" />
<section id="board-container">
	<h2>게시판</h2>
	<table id="tbl-board-view">
		<tr>
			<th>글번호</th>
			<td><%= no %></td>
		</tr>
		<tr>
			<th>제 목</th>
			<td><%= title %></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><%= writer %></td>
		</tr>
		<tr>
			<th>조회수</th>
			<td><%= readCount %></td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
				<%-- 첨부파일이 있을경우만, 이미지와 함께 original파일명 표시 --%>
				<% if(attach != null) { %>
					<img alt="첨부파일" src="<%=request.getContextPath() %>/images/file.png" width=16px>
					<a href="#"></a>
				<% } %>
			</td>
		</tr>
		<tr>
			<th>내 용</th>
			<td><%= content %></td>
		</tr>
		<tr>
			<%-- 작성자와 관리자만 마지막행 수정/삭제버튼이 보일수 있게 할 것 --%>
			<th colspan="2">
				<input type="button" value="수정하기" onclick="updateBoard()">
				<input type="button" value="삭제하기" onclick="deleteBoard()">
			</th>
		</tr>
	</table>
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
