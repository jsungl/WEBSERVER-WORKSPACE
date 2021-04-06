package common;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MvcFileRenamePolicy implements FileRenamePolicy {

	/**
	 * 벚꽃.jpg -> 20210406090909_123.jpg로 파일명 변경
	 */
	@Override
	public File rename(File f) {
		File newFile = null;
		do {
			//새파일명 생성
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_"); //밀리초까지
			DecimalFormat df = new DecimalFormat("000");
			
			//확장자 구하기
			String oldName = f.getName();
			String text = "";
			int dot = oldName.lastIndexOf("."); //.의 위치알아내기
			if(dot > -1) {
				text = oldName.substring(dot); //.jpg
			}
			
			String newName = sdf.format(new Date()) + df.format(Math.random() * 999) + text;
			System.out.println("newName@MvcFileRenamePolicy = " + newName);
			//파일객체로 변환
			newFile = new File(f.getParent(),newName);
			
			
		} while(!createNewFile(newFile)); //새로 파일을 생성하지못하면 do-while문 진행
		
		return newFile;
	}
	
	/**
	 * f.createNewFile();
	 * f가 실제 존재하지않으면 파일생성후 true를 리턴
	 * f가 이미 존재하면, 파일을 생성하지않고 IOException을 던짐
	 * @param f
	 * @return
	 */
	private boolean createNewFile(File f) {
	    try {
	      return f.createNewFile();
	    }
	    catch (IOException ignored) {
	      return false;
	    }
	  }

}
