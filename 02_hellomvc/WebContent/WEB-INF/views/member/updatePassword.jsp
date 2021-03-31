<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

	<section id=enroll-container>
		<h2>비밀번호 변경</h2>
		<form 
			name="updatePwdFrm" 
			action="<%=request.getContextPath()%>/member/updatePassword" 
			method="post" >
			<table>
				<tr>
					<th>현재 비밀번호</th>
					<td><input type="password" name="password" id="password" required></td>
				</tr>
				<tr>
					<th>변경할 비밀번호</th>
					<td>
						<input type="password" name="newPassword" id="newPassword" required>
					</td>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td>	
						<input type="password" id="passwordCheck" required><br>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center;">
						<input type="submit"  value="변경" />
					</td>
				</tr>
			</table>
		</form>
	</section>
	
	<script>
		$(document.updatePwdFrm).submit(function(){
			var $p0 = $("#password");
			var $p1 = $("#newPassword");
			var $p2 = $("#passwordCheck");
			if(/^[a-zA-Z0-9!@#$$%^&*()]{4,}/.test($p1.val()) == false){
				alert("유효한 패스워드를 입력하세요.");
				$p1.select();
				return false;
			}
			
			if($p1.val() != $p2.val()){
				alert("패스워드가 일치하지 않습니다.");
				$p1.select();
				return false;
			}
			
			if($p0.val() == $p1.val()){
				alert("기존비밀번호와 신규비밀번호는 같을 수 없습니다.");
				$p1.select();
				return false;
			}
			
			return true;
			
		});
		
		//신규비밀번호 일치 검사
		/* $("#passwordCheck").blur(passwordValidate); 포커스를 잃었을때(탭으로 이동시) 검사

		function passwordValidate(){
			var $newPassword = $("#newPassword");
			var $newPasswordCheck = $("#passwordCheck");
			if($newPassword.val() != $newPasswordCheck.val()){
				alert("입력한 비밀번호가 일치하지 않습니다.");
				$newPassword.select();
				return false;
			}
			return true;	
		} */
	
	</script>
	
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
