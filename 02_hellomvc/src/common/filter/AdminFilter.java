package common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet Filter implementation class AdminFilter
 */
//@WebFilter({ "/admin/memberList", "/admin/memberFinder","/admin/memberRoleUpdate" })
@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest)request;
		HttpServletResponse httpRes = (HttpServletResponse)response;
		
		HttpSession session = httpReq.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");

		if(loginMember == null) { 
			session.setAttribute("msg", "로그인후 사용할수있습니다.");
			httpRes.sendRedirect(httpReq.getContextPath());
			return; //servlet을 실행하지않는다
		}else if(!MemberService.ADMIN_ROLE.equals(loginMember.getMemberRole())) {
			session.setAttribute("msg", "관리자 계정이 아닙니다.");
			httpRes.sendRedirect(httpReq.getContextPath());
			return;
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}


}
