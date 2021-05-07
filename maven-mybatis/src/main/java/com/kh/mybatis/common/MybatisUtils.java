package com.kh.mybatis.common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtils {
	
	/**
	 * mybatis-config.xml 설정파일 기반으로 SqlSession객체를 생성반환
	 * build-path(target/classes)로 배포된 설정파일을 읽어들임
	 * 
	 * 공장짓는이 - 공장 - SqlSession
	 * 
	 * @return
	 */

	public static SqlSession getSqlSession() {
		SqlSession session = null;
		String resource = "/mybatis-config.xml"; //target - classes에 mybatis-config.xml 
		
		//1. FactoryBuilder
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		
		//2. Factory 생성 - 설정파일 필요
		InputStream is = null;
		try {
			is = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SqlSessionFactory factory = builder.build(is);
		
		//3. SqlSession - autoCommit:boolean
		session = factory.openSession(false);
		System.out.println("session@MybatisUtils = " + session);
		return session;
	}
}
