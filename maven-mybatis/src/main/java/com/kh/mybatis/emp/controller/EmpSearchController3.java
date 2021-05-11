package com.kh.mybatis.emp.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mybatis.common.AbstractController;
import com.kh.mybatis.emp.model.service.EmpService;
import com.kh.mybatis.emp.model.service.EmpServiceImpl;

public class EmpSearchController3 extends AbstractController {

	private EmpService empService = new EmpServiceImpl();

	@Override
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("--------------doGet메소드 실행@EmpSearchController3--------------");
			//1. 사용자입력값
			String[] jobCodeArr = request.getParameterValues("jobCode"); //여러개가 올수있으므로
			String[] deptCodeArr = request.getParameterValues("deptId"); //여러개가 올수있으므로
			//List<String> deptIdList = null;
			//if(deptIdList != null) deptIdList = Arrays.asList(deptCodeArr);
			
			Map<String,Object> param = new HashMap<>();
			param.put("jobCodeArr", jobCodeArr);
			param.put("deptCodeArr", deptCodeArr);
			//param.put("deptIdList", deptIdList);
			
			
			System.out.println("param@EmpSearchController3 = " + param);
			
			//2. 업무로직
			//jobList조회(job_code, job_name)
			List<Map<String, String>> jobList = empService.selectJobList();
			System.out.println("jobList@EmpSearchController3 = " + jobList);
			//deptList조회(dept_id,dept_title)
			List<Map<String, String>> deptList = empService.selectDeptList();
			System.out.println("deptList@EmpSearchController3 = " + deptList);
			
			List<Map<String, Object>> list = empService.search3(param);
			System.out.println("list@EmpSearchController3 = " + list);
			
		
			//3. jsp위임
			request.setAttribute("list", list);
			request.setAttribute("jobList", jobList);
			request.setAttribute("deptList", deptList);
			
		
		} catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
		
		
		
		return "emp/search3";
	}
	
	
	
}
