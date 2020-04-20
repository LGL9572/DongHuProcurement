package org.lgl.springboot.sys.service.impl;

import org.lgl.springboot.sys.domain.Notice;
import org.lgl.springboot.sys.mapper.NoticeMapper;
import org.lgl.springboot.sys.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

}
