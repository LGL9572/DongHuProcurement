package org.lgl.springboot.sys.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.lgl.springboot.sys.common.ActiverUser;
import org.lgl.springboot.sys.common.Constast;
import org.lgl.springboot.sys.domain.Permission;
import org.lgl.springboot.sys.domain.User;
import org.lgl.springboot.sys.service.PermissionService;
import org.lgl.springboot.sys.service.RoleService;
import org.lgl.springboot.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private PermissionService permissionService;

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
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname", token.getPrincipal().toString());
        User user = userService.getOne(queryWrapper);
        if (null != user) {
            ActiverUser activerUser = new ActiverUser();
            activerUser.setUser(user);
            //根据用户ID查询percode
            //查询所有的菜单
            QueryWrapper<Permission> qw = new QueryWrapper<>();
            //设置只能查询菜单
            qw.eq("type", Constast.TYPE_PERMISSION);
            qw.eq("available",Constast.AVAILABLE_TRUE);
            //根据用户ID+角色+权限去查
            Integer userId = user.getId();
            //根据用户Id查询角色
            List<Integer> currentUserRoleIds = roleService.queryUserRoleIdsByUid(userId);
            //根据角色Id查询权限
            Set<Integer> pids = new HashSet<>();
            for (Integer rid : currentUserRoleIds) {
                List<Integer> permissionIds = roleService.queryRolePermissionIdsByRid(rid);
                pids.addAll(permissionIds);
            }
            List<Permission> list = new ArrayList<>();
            //根据角色ID查询权限
            if(pids.size()>0){
                qw.in("id",pids);
                list = permissionService.list(qw);
            }
            List<String> percodes = new ArrayList<>();
            for (Permission permission : list) {
                percodes.add(permission.getPercode());
            }
            //放到
            activerUser.setPermissions(percodes);

            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
            SimpleAccount info = new SimpleAccount(activerUser, user.getPwd(), credentialsSalt, this.getName());
            return info;
        }
        return null;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ActiverUser activerUser = (ActiverUser) principals.getPrimaryPrincipal();
        User user = activerUser.getUser();
        List<String> permissions = activerUser.getPermissions();
        if(user.getType()==Constast.USER_TYPE_SUPER){
            authorizationInfo.addStringPermission("*:*");
        }else{
            if(null!=permissions&&permissions.size()>0){
                authorizationInfo.addStringPermissions(permissions);
            }
        }
        return authorizationInfo;
    }
}