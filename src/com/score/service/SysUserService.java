package com.score.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import com.score.bean.SysUser;
import com.score.bean.SysUserOut;
import com.score.dao.SysUserDao;
import com.score.util.ErrorType;

@Transactional
public class SysUserService 
{
	private SysUserDao sysUserDao;
	
	@Transactional
	public SysUserOut findCurrentUser()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = (String) request.getSession().getAttribute("SPRING_SECURITY_LAST_USERNAME");
		if (username == null)
			return null;
		SysUserOut userOut = findUserOutByUsername(username);
		return userOut;
	}
	
	@Transactional
	public SysUser findByUsername(String username)
	{
		return this.sysUserDao.findByUsername(username);
	}

	@Transactional
	public SysUserOut findUserOutByUsername(String username)
	{
		return this.sysUserDao.findUserOutByUsername(username);
	}
	
	@Transactional
	public SysUserOut queryUserOutById(Long id)//这个函数可以用来一次返回所有的TA
	{
		return this.sysUserDao.queryUserOutById(id);
	}
	
	@Transactional
	public SysUserOut queryUserOutWithCheckItemById(Long id)
	{
		return this.sysUserDao.queryUserOutWithCheckItemById(id);
	}
	
	@Transactional
	public List<SysUserOut> queryAllUser()
	{
		return this.sysUserDao.queryAllUser();
	}
	
	@Transactional
	public List<SysUserOut> queryAllUserOut()
	{
		return this.sysUserDao.queryAllUserOut();
	}
	
	@Transactional
	public boolean changePasswordById(Long userId, String oldPassword, String newPassword)
	{
		SysUser user = this.sysUserDao.queryById(userId);
		if (user.getPassword().equals(oldPassword))
		{
			user.setPassword(newPassword);
			this.sysUserDao.merge(user);
			return true;
		}
		return false;
	}
	
	@Transactional
	public Long queryUserNumber()
	{
		return this.sysUserDao.queryUserNumber();
	}
	
	@Transactional
	public ErrorType appToResetUserPassword(String username)
	{
		SysUser sysUser = findByUsername(username);
		if (sysUser == null)
			return ErrorType.USER_NOT_EXIST;
		if (sysUser.getMailAddress() == null)
		{
			return ErrorType.NO_MAIL_REGISTERED;
		}
		if (sysUser.getResetPasswordTime() != null)
		{
			Date now = new Date();
			long diff = now.getTime() - sysUser.getResetPasswordTime().getTime();    
			long seconds = diff / 1000;
			if (seconds < 60)
				return ErrorType.RESET_CODE_APPLY_SO_CLOSE;
		}
		Random r = new Random();
		char[] b = new char[200];
		for (int i = 0; i < b.length; i++)
		{
			b[i] = (char) (r.nextDouble() * 26 + 65);
		}
		sysUser.setResetPasswordCode(String.valueOf(b));
		sysUser.setResetPasswordTime(new Date());
		this.sysUserDao.merge(sysUser);
		return ErrorType.NO_ERROR;
	}
	
	@Transactional
	public ErrorType resetUserPassword(String username, String password, String resetCode)
	{
		SysUser sysUser = findByUsername(username);
		if (sysUser == null)
			return ErrorType.USER_NOT_EXIST;
		if (sysUser.getResetPasswordCode() == null || !sysUser.getResetPasswordCode().equals(resetCode))
		{
			return ErrorType.RESET_CODE_WRONG;
		}
		Date now = new Date();
		long diff = now.getTime() - sysUser.getResetPasswordTime().getTime();    
		long seconds = diff / 1000;
		if (seconds > 3600 * 48)
			return ErrorType.RESET_OUT_OF_DATE;
		sysUser.setPassword(password);
		sysUser.setResetPasswordCode(null);
		sysUser.setResetPasswordTime(null);
		this.sysUserDao.merge(sysUser);
		return ErrorType.NO_ERROR;
	}
	
	public void setSysUserDao(SysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}
}
