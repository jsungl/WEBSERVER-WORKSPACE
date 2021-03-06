package common.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SessionCounterListener
 *
 */
@WebListener
public class SessionCounterListener implements HttpSessionListener {

	private static int activeSessions; //인스턴스 변수 초기화하지않아도 0이다
	
	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
    	activeSessions++;
    	System.out.println("세션 생성 : 현재 세션수는 [" + activeSessions + "]개 입니다.");
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     * 세션무효화나 타임아웃이 발생했을때 호출
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
         if(activeSessions > 0) 
        	 activeSessions--;
         
         System.out.println("세션 해제 : 현재 세션수는 [" + activeSessions + "]개 입니다.");
    }
	
}
