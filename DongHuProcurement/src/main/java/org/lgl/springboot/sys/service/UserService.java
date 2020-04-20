package org.lgl.springboot.sys.service;

import org.lgl.springboot.sys.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 老雷
 * @since 2020-03-12
 */
public interface UserService extends IService<User> {

    void saveUserRole(Integer uid, Integer[] ids);
}
