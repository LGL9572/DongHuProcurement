package org.lgl.springboot.sys.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.lgl.springboot.sys.domain.Dept;
import org.lgl.springboot.sys.domain.User;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@EnableAspectJAutoProxy
public class CacheAspect {

    //打印一个日志
    private Log log = LogFactory.getLog(CacheAspect.class);

    //声明一个缓存容器
    private Map<String,Object> CACHE_CONTAINER = new HashMap<>();

    //声明切面表达式
    private static final String POINTCUT_DEPT_UPDATEBYID
            ="execution(* org.lgl.springboot.sys.service.impl.DeptServiceImpl.updateById(..))";
    private static final String POINTCUT_DEPT_ADD
            ="execution(* org.lgl.springboot.sys.service.impl.DeptServiceImpl.save(..))";
    private static final String POINTCUT_DEPT_GETONE
            ="execution(* org.lgl.springboot.sys.service.impl.DeptServiceImpl.getById(..))";
    private static final String POINTCUT_DEPT_DELETEBYID
            ="execution(* org.lgl.springboot.sys.service.impl.DeptServiceImpl.removeById(..))";

    //定义前缀表达式
    private static final String CACHE_DEPT_PROFIX="dept:";
    /**
     * 部门添加切入
     */
    @Around(value = POINTCUT_DEPT_ADD)
    public Object cacheDeptADD(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Dept o = (Dept) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if(res) {
            CACHE_CONTAINER.put(CACHE_DEPT_PROFIX + o.getId(), o);
        }
        return res;
    }
    /**
     * 查询切入
     */
    @Around(value = POINTCUT_DEPT_GETONE)
    public Object cacheDeptGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer o = (Integer) joinPoint.getArgs()[0];
        //从缓存中取
        Object res1 = CACHE_CONTAINER.get(CACHE_DEPT_PROFIX + o);
        if(res1 != null){
            log.info("已缓存里面找到部门对象"+CACHE_DEPT_PROFIX + o);
            return res1;
        }else{
            log.info("未缓存里面找到部门对象,去数据库中查询，并放入缓存");
            Dept res2 = (Dept) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_DEPT_PROFIX + res2.getId(),res2);
            return res2;
        }
    }
    /**
     * 更新切入
     */
    @Around(value = POINTCUT_DEPT_UPDATEBYID)
    public Object cacheDeptUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Dept deptVo = (Dept) joinPoint.getArgs()[0];
        //从缓存中取
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if(isSuccess){
            Dept dept = (Dept) CACHE_CONTAINER.get(CACHE_DEPT_PROFIX + deptVo.getId());
            if(null == dept){
                dept = new Dept();
                BeanUtils.copyProperties(deptVo,dept);
                log.info("部门对象缓存已跟新"+CACHE_DEPT_PROFIX + deptVo.getId());
                CACHE_CONTAINER.put(CACHE_DEPT_PROFIX + deptVo.getId(),dept);
            }
        }
        return isSuccess;
    }
    /**
     * 删除切入
     */
    @Around(value = POINTCUT_DEPT_DELETEBYID)
    public Object cacheDeptDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        //从缓存中取
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if(isSuccess){
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_DEPT_PROFIX + id);
            log.info("部门对象缓存已删除"+CACHE_DEPT_PROFIX + id);
        }
        return isSuccess;
    }


    //声明切面表达式
    private static final String POINTCUT_USER_UPDATEBYID
            ="execution(* org.lgl.springboot.sys.service.impl.UserServiceImpl.updateById(..))";
    private static final String POINTCUT_USER_ADD
            ="execution(* org.lgl.springboot.sys.service.impl.UserServiceImpl.save(..))";
    private static final String POINTCUT_USER_GETONE
            ="execution(* org.lgl.springboot.sys.service.impl.UserServiceImpl.getById(..))";
    private static final String POINTCUT_USER_DELETEBYID
            ="execution(* org.lgl.springboot.sys.service.impl.UserServiceImpl.removeById(..))";

    //定义前缀表达式
    private static final String CACHE_USER_PROFIX="user:";
    /**
     * 用户添加切入
     */
    @Around(value = POINTCUT_USER_ADD)
    public Object cacheUserADD(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        User o = (User) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if(res) {
            CACHE_CONTAINER.put(CACHE_USER_PROFIX + o.getId(), o);
        }
        return res;
    }
    /**
     * 查询切入
     */
    @Around(value = POINTCUT_USER_GETONE)
    public Object cacheUserGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer o = (Integer) joinPoint.getArgs()[0];
        //从缓存中取
        Object res1 = CACHE_CONTAINER.get(CACHE_USER_PROFIX + o);
        if(res1 != null){
            log.info("已缓存里面找到用户对象"+CACHE_USER_PROFIX + o);
            return res1;
        }else{
            log.info("未缓存里面找到用户对象,去数据库中查询，并放入缓存");
            User res2 = (User) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_USER_PROFIX + res2.getId(),res2);
            return res2;
        }
    }
    /**
     * 更新切入
     */
    @Around(value = POINTCUT_USER_UPDATEBYID)
    public Object cacheUserUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        User userVo = (User) joinPoint.getArgs()[0];
        //从缓存中取
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if(isSuccess){
            User user = (User) CACHE_CONTAINER.get(CACHE_USER_PROFIX + userVo.getId());
            if(null == user){
                user = new User();
                BeanUtils.copyProperties(userVo,user);
                log.info("用户对象缓存已跟新"+CACHE_USER_PROFIX + userVo.getId());
                CACHE_CONTAINER.put(CACHE_USER_PROFIX + userVo.getId(),user);
            }
        }
        return isSuccess;
    }
    /**
     * 删除切入
     */
    @Around(value = POINTCUT_USER_DELETEBYID)
    public Object cacheUserDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        //从缓存中取
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if(isSuccess){
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_USER_PROFIX + id);
            log.info("用户对象缓存已删除"+CACHE_USER_PROFIX + id);
        }
        return isSuccess;
    }

}
