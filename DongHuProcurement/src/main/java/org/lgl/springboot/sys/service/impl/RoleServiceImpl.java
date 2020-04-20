package org.lgl.springboot.sys.service.impl;

import org.lgl.springboot.sys.domain.Role;
import org.lgl.springboot.sys.mapper.RoleMapper;
import org.lgl.springboot.sys.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LGL
 * @since 2020-03-18
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public boolean removeById(Serializable id) {
        //根据角色ID删除sys_role_permission
        this.getBaseMapper().deleteRolePermissionByRid(id);
        //根据角色ID删除sys_role_user
        this.getBaseMapper().deleteRoleUserByRid(id);

        return super.removeById(id);
    }

    /**
     * 根据角色ID查询当前角色拥有的权限和菜单ID
     * @param roleid
     * @return
     */
    @Override
    public List<Integer> queryRolePermissionIdsByRid(Integer roleid) {
        return this.getBaseMapper().queryRolePermissionIdsByRid(roleid);
    }

    /**
     * 保存角色和菜单权限之间的关系
     * @param rid
     * @param ids
     */
    @Override
    public void saveRolePermission(Integer rid, Integer[] ids) {
        RoleMapper roleMapper = this.getBaseMapper();
        //根据角色ID删除sys_role_user
        roleMapper.deleteRolePermissionByRid(rid);
        if(ids!=null&&ids.length>0){
            for (Integer pid:ids) {
                roleMapper.saveRolePermission(rid,pid);
            }
        }
    }

    /**
     * 查询当前用户拥有的Id集合
     */
    @Override
    public List<Integer> queryUserRoleIdsByUid(Integer id) {
        return this.getBaseMapper().queryUserRoleIdsByUid(id);
    }
}
