package org.lgl.springboot.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.lgl.springboot.sys.domain.User;
import org.lgl.springboot.sys.mapper.RoleMapper;
import org.lgl.springboot.sys.mapper.UserMapper;
import org.lgl.springboot.sys.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 老雷
 * @since 2020-03-12
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean save(User entity) {
        return super.save(entity);
    }

    @Override
    public User getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public User getOne(Wrapper<User> queryWrapper, boolean throwEx) {
        return super.getOne(queryWrapper, throwEx);
    }

    @Override
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        //根据用户ID删除用户角色中间表的数据
        roleMapper.deleteRoleUserByUid(id);
        //删除用户头像（如果是默认头像则不删除）
        return super.removeById(id);
    }

    /**
     * 保存用户和角色之间的关系
     */
    @Override
    public void saveUserRole(Integer uid, Integer[] ids) {
        //根据用户ID删除用户角色中间表的数据
        this.roleMapper.deleteRoleUserByUid(uid);
        //删除用户头像（如果是默认头像则不删除）
        if(null!=ids&&ids.length>0){
            for (Integer rid : ids) {
                this.roleMapper.insertUserRole(uid,rid);
            }
        }
    }
}
