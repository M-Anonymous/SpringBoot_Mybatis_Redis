package com.demo.serviceImpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.demo.pojo.SimpleUser;
import com.demo.dao.SimpleUserDao;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SuppressWarnings({"rawtypes","unchecked"})
public class SimpleUserServiceImpl implements UserDetailsService {

	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private SimpleUserDao userDao;
	@Override 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		if(redisTemplate.opsForHash().hasKey("user",username)) {
			SimpleUser user = (SimpleUser)redisTemplate.opsForHash().get("user",username);
			return new User(user.getUsername(),user.getPassword(),user.getAuthorities());
		}else {
			SimpleUser user = userDao.findUserByUsername(username);
			if(user != null) {
				redisTemplate.opsForHash().put("user", "username", user);
				return new User(user.getUsername(),user.getPassword(),user.getAuthorities());
			}else {
				throw new UsernameNotFoundException("Username or Password is not correct");
			}
		}
	}
	
	public int addSimpleUser(SimpleUser user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		return userDao.addSimpleUser(user);
	}
}
