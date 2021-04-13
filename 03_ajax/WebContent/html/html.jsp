<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ajax - html</title>
<style>
table { 
	border-collapse:collapse;
	border : 1px solid #000;
	margin : 5px;
}
th,td {
	border : 1px solid #000;		
}

table img {
	width : 150px;
}
		
</style>

<script src = "<%= request.getContextPath() %>/js/jquery-3.6.0.js"></script>
</head>
<body>
	<h1>html</h1>
	<input type="button" value="실행" id="btn" />
	<div class="wrapper"></div>
<script>
$(btn).click(function(){
	$.ajax({
		url: "<%= request.getContextPath() %>/html",
		//dataType: "html",
		success: function(data){
			console.log(data);
			$(".wrapper").html(data);
		},
		error: function(xhr,status,err){
			console.log(xhr,status,err);
		}
	});
});

</script>	
</body>
</html>