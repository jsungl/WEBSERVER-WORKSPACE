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

}
