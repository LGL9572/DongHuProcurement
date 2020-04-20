package org.lgl.springboot.sys.mapper;

import org.apache.ibatis.annotations.Param;
import org.lgl.springboot.sys.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 老雷
 * @since 2020-03-13
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    //根据权限或者菜单ID删除权限表和角色的关系表里面的数据
    void deleteRolePermissionByPid(@Param("id") Serializable id);
}
