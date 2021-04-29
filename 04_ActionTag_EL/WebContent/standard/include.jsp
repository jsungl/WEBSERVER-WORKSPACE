<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/standard/header.jsp">
	<jsp:param value="INCLUDE" name="title"></jsp:param>
</jsp:include>
		<article>
			<h2>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sequi officiis repellendus et accusantium dolore nesciunt blanditiis temporibus molestias natus veniam eligendi voluptatum velit officia corrupti esse nam voluptatem dolorum ipsa.</h2>
			<a href="${pageContext.request.contextPath}/standard/another.jsp">another</a>
		</article>
<jsp:include page="/standard/footer.jsp"></jsp:include>	