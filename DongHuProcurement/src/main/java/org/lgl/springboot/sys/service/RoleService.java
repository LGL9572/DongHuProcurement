package org.lgl.springboot.sys.service;

import org.lgl.springboot.sys.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LGL
 * @since 2020-03-18
 */
public interface RoleService extends IService<Role> {

    List<Integer> queryRolePermissionIdsByRid(Integer roleid);

    void saveRolePermission(Integer rid, Integer[] ids);

    List<Integer> queryUserRoleIdsByUid(Integer id);
}
