package com.zyplayer.doc.manage.framework.config.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("1".equals(username)) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_1"));
			String pwd = DigestUtils.md5DigestAsHex("1".getBytes());
			return new MyUserDetails(1, "1", pwd, true, authorities);
		}
		throw new UsernameNotFoundException("用户名 '" + username + "'没有找到！");
	}
}