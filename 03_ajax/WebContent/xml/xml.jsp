<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ajax - xml</title>
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
<script src="<%= request.getContextPath() %>/js/jquery-3.6.0.js"></script>
</head>
<body>
	<h1>XML</h1>
	<button type="button" id="btn">실행</button>
	<button type="button" id="btn-product-sample">상품xml가져오기</button>

<script>

$("#btn-product-sample").click(function(){
	$.ajax({
		url: "<%= request.getContextPath() %>/xml/sample-product.xml",
		success: function(data){
			//console.log(data);
			var $root = $(data).find(":root"); 
			//var $products = $(data).find("Product");도 가능
			var $Products = $root.find("Product");
			//console.log($products);
			var $ul = $("<ul></ul>");
			$Products.each(function(index,product){
				//console.log(index,product);
				var name = $(product).children("name").text();
				var price = $(product).children("price").text();
				//var $ol = $("<ol></ol>");
				//var $li = $("<li></li>");
				$ul.append("<li>" + name + "(" + price + ")</li>")
			});
			
			$("body").append($ul);
			//$ul.appendTo($("body")); //body태그 맨마지막에 추가
			
		},
		error: function(xhr,status,err){
			console.log(xhr,status,err);
		}
	})
})







$(btn).click(function(){
	$.ajax({
		url: "<%= request.getContextPath() %>/xml",
		//dataType: "xml", //생략가능
		success: function(data){ //java script객체로 전달(#document)
			//console.log(data);
			var $root = $(data).find(":root"); //mebers가 들어있는 jquery객체
			//console.log($root); 
			var $members = $root.find("member"); 
			console.log($members); 
			var $table = $("<table></table>"); //table태그 동적생성
			$members.each(function(index,member){
				console.log(index,member);
				var id = $(member).children("id").text(); //member태그안의 id를 찾아서 저장
				var name = $(member).children("name").text(); //member태그안의 name를 찾아서 저장
				var profile = $(member).children("profile").text();//member태그안의 profile를 찾아서 저장
				var $tr = $("<tr></tr>"); //tr태그 동적생성
				$tr.append("<td>" + (index+1) + "</td>")
				   .append("<td><img src='<%= request.getContextPath() %>/images/" + profile + "'/></td>")
				   .append("<td>" + id + "</td>")
				   .append("<td>" + name + "</td>")
				   .appendTo($table); //마지막에 table에 추가
			});
			
			$("body").append($table); //body태그에 table추가
		},
		error: function(xhr,status,err){
			console.log(xhr,status,err);
		}
	});
	
});

</script>
</body>
</html>