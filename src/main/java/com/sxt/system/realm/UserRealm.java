package com.sxt.system.realm;

import com.sxt.system.common.ActiveUser;
import com.sxt.system.common.Constant;
import com.sxt.system.domain.User;
import com.sxt.system.service.MenuService;
import com.sxt.system.service.RoleService;
import com.sxt.system.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

public class UserRealm extends AuthorizingRealm {

	@Autowired
	@Lazy  //只有使用的时候才会加载
	private UserService userService;

	@Autowired
	@Lazy
	private MenuService menuService;
	
	@Autowired
	@Lazy
	private RoleService roleService;

	@Override
	public String getName() {

		return this.getClass().getSimpleName();
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = token.getPrincipal().toString();
		User user = this.userService.queryUserByLoginName(userName);
		if(null!=user){
			ActiveUser activeUser = new ActiveUser();
			activeUser.setUser(user);
			//根据用户ID查询角色名称的集合
			List<String> roles = this.roleService.queryRoleNamesByUserId(user.getId());
			//根据用户id查询权限编码的集合
			List<String> permissions = this.menuService.queryPermissionCodesByUserId(user.getId());
			activeUser.setRoles(roles);
			activeUser.setPermission(permissions);
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser,user.getPwd(),
					ByteSource.Util.bytes(user.getSalt()),getName()) ;

			return info;
		}
		return null;
	}


	/**
	 * 授权方法
	 * @param principalCollection
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		ActiveUser activeUser = (ActiveUser) principalCollection.getPrimaryPrincipal();
		List<String> permission = activeUser.getPermission();
		List<String> roles = activeUser.getRoles();
		User user = activeUser.getUser();
		if(user.getType().equals(Constant.USER_TYPE_SUPER)){
			info.addStringPermission("*:*");//超级管理员所有权限都有
		}else{
			if(null!=roles&&roles.size()>0){
				info.addRoles(roles);
			}
			if(null!=permission&&permission.size()>0){
				info.addStringPermissions(permission);
			}
		}
		return info;
	}
}
