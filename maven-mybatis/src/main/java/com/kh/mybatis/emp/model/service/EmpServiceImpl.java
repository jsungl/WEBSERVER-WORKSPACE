package com.kh.mybatis.emp.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.emp.model.dao.EmpDao;
import com.kh.mybatis.emp.model.dao.EmpDaoImpl;
import static com.kh.mybatis.common.MybatisUtils.getSqlSession;


public class EmpServiceImpl implements EmpService {

	private EmpDao empDao = new EmpDaoImpl();
	
	
	@Override
	public List<Map<String, Object>> selectAllEmp() {
		SqlSession session = getSqlSession();
		List<Map<String, Object>> list = empDao.selectAllEmp(session);
		session.close();
		return list;
	}

}
