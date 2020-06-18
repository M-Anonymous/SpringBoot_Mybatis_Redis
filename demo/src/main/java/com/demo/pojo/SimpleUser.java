package com.demo.pojo;

import java.io.Serializable;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.ArrayList;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor // 提供无参数构造函数
@AllArgsConstructor // 提供有参数构造函数
public class SimpleUser implements UserDetails, Serializable {

	private static final long serialVersionUID  = 1L;
	private String username;
	private String password;
	private String phone;
	private String email;
	private boolean status = true;
	private String roles; // 用集合 mybatis 无法读取 Set 列表
	
	public SimpleUser(String username, String password, String phone) {
		super();
		this.username = username;
		this.password = password;
		this.phone = phone;
	}
	
	// 取消对 authorities 的序列化
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<GrantedAuthority> authorities = new ArrayList<>();
		if(!getRoles().isEmpty()) {
			String[] roles = getRoles().split(",");
			for(String role : roles) {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
			}
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return status;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return status;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return status;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
