--====================================
--관리자(system)계정
--====================================

create user mybatis
identified by mybatis
default tablespace users;

grant connect, resource to mybatis;


--====================================
--mybatis계정
--====================================
create table student(
    no number,
    name varchar2(100) not null,
    tel char(11) not null,
    reg_date date default sysdate,
    constraint pk_student_no primary key(no)
);

create sequence seq_student_no;
--drop sequence seq_student_no;

select * from student;
--select seq_student_no.currval
--from dual;
--select seq_student_no.nextval
--from dual;

select * from user_sequences;


--oracle synonym객체
--동의어객체, 별칭객체

--mybatis계정에서 kh계정의 table접근(관리자계정이나 kh계정에서 접근권한을 부여해야됨)
select * from kh.employee; --별칭 emp
select * from kh.department; --별칭 dept
select * from kh.job; --별칭 job

--동의어(별칭) 생성
--resource role에 create synonym은 포함되어 있지않다.(권한필요)
create synonym emp for kh.employee;
create synonym dept for kh.department;
create synonym job for kh.job;
-- 간단하게 사용가능
select * from emp;
select * from dept;
select * from job;
--=================================================
--관리자계정
--=================================================
--수정권한까지
--grant all on kh.employee to mybatis; 
--조회권한만
--grant select on kh.department to mybatis; 
--grant select on kh.job to mybatis; 

--grant create synonym to mybatis;
--=================================================



--직급코드가 j1,j2,j3인 사원조회



--인턴사원까지 조회
select *
from (
		  	select
		  		e.*,
		  		d.dept_title,
		  		j.job_name
		  	from
		  		emp e
		  			left join dept d
		  				on e.dept_code = d.dept_id
                    left join job j
		  				on e.job_code = j.job_code
        ) e;









