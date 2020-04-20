package org.lgl.springboot.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.lgl.springboot.sys.domain.Dept;
import org.lgl.springboot.sys.mapper.DeptMapper;
import org.lgl.springboot.sys.service.DeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LGL
 * @since 2020-03-16
 */
@Service
@Transactional
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean updateById(Dept dept) {
        return super.updateById(dept);
    }

    @Override
    public Dept getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean save(Dept entity) {
        return super.save(entity);
    }
}
