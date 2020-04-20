package org.lgl.springboot.sys.service.impl;

import org.lgl.springboot.sys.domain.Loginfo;
import org.lgl.springboot.sys.mapper.LoginfoMapper;
import org.lgl.springboot.sys.service.LoginfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LGL
 * @since 2020-03-13
 */
@Service
@Transactional
public class LoginfoServiceImpl extends ServiceImpl<LoginfoMapper, Loginfo> implements LoginfoService {

}
