package org.lgl.springboot.sys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lgl.springboot.sys.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LGL
 * @since 2020-03-18
 */
public interface RoleMapper extends BaseMapper<Role> {

    void deleteRolePermissionByRid(Serializable id);

    void deleteRoleUserByRid(Serializable id);

    List<Integer> queryRolePermissionIdsByRid(Integer roleid);

    void saveRolePermission(@Param("rid") Integer rid, @Param("pid") Integer pid);

    void deleteRoleUserByUid(@Param("id") Serializable id);

    List<Integer> queryUserRoleIdsByUid(Integer id);

    //保存用户和角色之间的关系
    void insertUserRole(@Param("uid") Integer uid,@Param("rid")Integer rid);
}
