package com.kh.mybatis.emp.model.service;

import java.util.List;
import java.util.Map;

import com.kh.mybatis.emp.model.dao.EmpDao;
import com.kh.mybatis.emp.model.dao.EmpDaoImpl;

public class EmpServiceImpl implements EmpService {

	private EmpDao empDao = new EmpDaoImpl();
	
	
	@Override
	public List<Map<String, Object>> selectAllEmp() {
		return null;
	}

}
