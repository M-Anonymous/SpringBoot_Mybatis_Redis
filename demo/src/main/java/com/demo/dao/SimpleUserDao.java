package com.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import com.demo.pojo.SimpleUser;

@Mapper
public interface SimpleUserDao {
	
	@Select("select * from user where username = #{username}")
	public SimpleUser findUserByUsername(String username);
	
	@Insert("insert into user(username,password,phone) values(#{username},#{password},#{phone})")
	public int addSimpleUser(SimpleUser user);

}
