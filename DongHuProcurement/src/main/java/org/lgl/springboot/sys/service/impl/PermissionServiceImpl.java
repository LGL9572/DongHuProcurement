package org.lgl.springboot.sys.service.impl;

import org.lgl.springboot.sys.domain.Permission;
import org.lgl.springboot.sys.mapper.PermissionMapper;
import org.lgl.springboot.sys.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 老雷
 * @since 2020-03-13
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public boolean removeById(Serializable id) {
        PermissionMapper permissionMapper = this.getBaseMapper();
        //根据权限或者菜单ID删除权限表和角色的关系表里面的数据
        permissionMapper.deleteRolePermissionByPid(id);
        //删除权限表的数据
        return super.removeById(id);
    }
}
