<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/header.jsp" %>
		<h1>Content</h1>
		<p><%= name %>님 반갑습니다</p>
		<a href="/web/jsp/another.jsp">another.jsp</a>
		<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quod ipsum aliquam vel laborum earum deserunt sed voluptas impedit pariatur possimus reprehenderit maiores mollitia aperiam iusto explicabo? Velit ipsam mollitia magnam.</p>
		<script>
			$(function() {
				$("h1").css("color","deeppink");				
			});
		</script>
<%@ include file="/jsp/footer.jsp" %>	
		
		
		
		
		<!--  맨밑에서 css적용하는것이 편할수있다 
		<script>
			$("h1").css("color","deeppink");
		</script> 
		-->