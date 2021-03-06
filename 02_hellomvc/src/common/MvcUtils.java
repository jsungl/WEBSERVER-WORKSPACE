package common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MvcUtils {

	/**
	 * 단방향 암호화 알고리즘
	 * - md5
	 * - sha1 : 160byte
	 * - sha256 : 256byte
	 * - sha512 : 512byte
	 * 
	 * byte가 높을수록 보안성이 좋다
	 * 
	 * 
	 * 1. MessageDigest : 단방향 암호화 처리
	 * 
	 * 2. Base64 인코딩처리 : 암호화된 byte[](이진데이터)를 64개의 문자로 변환
	 * 
	 */
	public static String getSha512(String password) {
		String encryptedPassword = null;
		
		//1. 암호화
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		byte[] bytes = null;
		try {
			bytes = password.getBytes("UTF-8"); //byte배열로 변환
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		md.update(bytes);
		byte[] encryptedBytes = md.digest(); //암호화처리 
		//System.out.println("암호화 처리 후 : " + new String(encryptedBytes)); //글자가 깨진다
		
		
		
		//2. 문자 인코딩 처리
		encryptedPassword = Base64.getEncoder().encodeToString(encryptedBytes);
		//System.out.println("인코딩 처리 후 : " + encryptedPassword);
		
		return encryptedPassword;
	}

	
	
	/**
	 *  1. totalContents : 총 컨텐츠 수
	 * 	2. totalPage : 전체페이지수(totalContents와 numPerPage를 통해 구한다)
	 * 	3. pageBarSize : 페이지바에 표시할 페이지 개수 ex) < 1 2 3 4 5 >
	 * 	4. pageNo : 페이지넘버를  출력할 증감변수
	 *	5. pageStart ~ pageEnd : pageNo의 범위 
	 */
	public static String getPageBar(int cPage, int numPerPage, int totalContents, String url) {
		StringBuilder pageBar = new StringBuilder();
		int totalPage = (int)Math.ceil((double)totalContents / numPerPage); //총 12page
		int pageBarSize = 5;
		//System.out.println("urlIndexof@MvcUtils = " + url.indexOf("?")); 
		//cPage이외의 다른 사용자입력값이 있는경우대비 (/mvc/admin/memberFinder?type=id&kw=abc&cPage=  )
		url = (url.indexOf("?") > -1) ? url + "&" : url + "?";
		
		
		/**
		 * 1 2 3 4 5 ---> 1
		 * 6 7 8 9 10 ---> 6
		 * 11 12 13 14 15 ---> 11
		 */
		
		
		int pageStart = ((cPage - 1) / pageBarSize) * pageBarSize + 1; // 1 6 11
		int pageEnd = pageStart + pageBarSize - 1;// 5 10 15
		
		//증감변수는 pageStart부터 시작
		int pageNo = pageStart;
		
		//1. 이전
		if(pageNo == 1) {

		}else {
			//pageNo가 6또는 11인경우
			pageBar.append("<a href='" + url + "cPage=" + (pageNo - 1) +  "'/>prev</a>\n");
		}
		//2. pageNo
		while(pageNo <= pageEnd && pageNo <= totalPage) {
			if(pageNo == cPage) {
				//pageNo가 현재페이지와 같다면 링크만들필요없음(현재페이지인경우)
				pageBar.append("<span class='cPage'>" + pageNo + "</span>\n");
			}else {
				pageBar.append("<a href='" + url + "cPage=" + pageNo +  "'/>" + pageNo +  "</a>\n");
			}
			
			pageNo++;
		}
		
		
		//3. 다음
		if(pageNo > totalPage) {
			//마지막페이지가 포함된 페이지바인 경우
			//마지막페이지인경우 
			
		}else {
			pageBar.append("<a href='" + url + "cPage=" + pageNo +  "'/>next</a>\n");
		}
		
		
		return pageBar.toString();
	}



	public static String convertLineFeedToBr(String content) {
		return content.replaceAll("\\n", "<br/>");
	}



	public static String escapeHtml(String str) {
		return str.replaceAll("<","&lt;")
					.replaceAll(">", "&gt;");
	}

}
