package com.score.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.score.bean.SysUser;
import com.score.dao.SysUserDao;

//此类需要在application_service.xml中配置，以在application_security.xml中被引用。
@Transactional
public class UserDetailServiceImpl implements UserDetailsService{
	private SysUserDao sysUserDao;


	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}


	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		if (username == null || (username = username.trim()).equals("")) {
			throw new UsernameNotFoundException("用户名为空");
		}
		SysUser uo = this.sysUserDao.findByUsername(username);
		if (uo == null)
		{
			return null;
		}
		String password = uo.getPassword();
		String role = "ROLE_"+uo.getRole();
		List<GrantedAuthority> authsList = new ArrayList<GrantedAuthority>();
		authsList.add(new GrantedAuthorityImpl(role));
		org.springframework.security.userdetails.User userdetail = new org.springframework.security.userdetails.User(
				username, password, true, true, true, true, authsList
						.toArray(new GrantedAuthority[authsList.size()]));
		return userdetail;
	}

}
