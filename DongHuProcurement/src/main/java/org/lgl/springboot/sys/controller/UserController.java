package org.lgl.springboot.sys.controller;


import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.lgl.springboot.sys.common.*;
import org.lgl.springboot.sys.domain.Dept;
import org.lgl.springboot.sys.domain.Role;
import org.lgl.springboot.sys.domain.User;
import org.lgl.springboot.sys.service.DeptService;
import org.lgl.springboot.sys.service.RoleService;
import org.lgl.springboot.sys.service.UserService;
import org.lgl.springboot.sys.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LGL
 * @since 2020-03-12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;
    /**
     * 用户全查询
     */
    @RequestMapping("loadAllUser")
    public DataGridView loadAllUser(UserVo userVo){
        IPage<User> page = new Page<>(userVo.getPage(),userVo.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getLoginname()),"loginname",userVo.getLoginname())
                .or().eq(StringUtils.isNotBlank(userVo.getName()),"name",userVo.getName());
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getAddress()),"address",userVo.getAddress());
        queryWrapper.eq("type", Constast.USER_TYPE_NORMAL);//查询系统用户
        queryWrapper.eq(userVo.getDeptid()!=null,"deptid",userVo.getDeptid());
        this.userService.page(page,queryWrapper);
        List<User> list = page.getRecords();
        for (User user:list) {
            Integer deptid = user.getDeptid();
            if(deptid!=null){
                Dept one = deptService.getById(deptid);
                user.setDeptname(one.getTitle());
            }
            Integer mgr = user.getMgr();
            if(mgr != null){
                User one = this.userService.getById(mgr);
                user.setLeadername(one.getName());
            }
        }
        return new DataGridView(page.getTotal(),list);
    }

    /**
     * 计算排序码
     */
    @RequestMapping("loadMaxOrderNum")
    public Map<String,Object> loadMaxOrderNum(){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<User> page = new Page<>(1,1);
        List<User> list = this.userService.page(page,queryWrapper).getRecords();
        if(list.size()>0){
            map.put("value",list.get(0).getOrdernum()+1);
        }else{
            map.put("value",1);
        }
        return map;
    }

    /**
     * 根据用户ID查询领导
     */
    @RequestMapping("loadUsersByDeptId")
    public DataGridView loadUsersByDeptId(Integer deptid){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(deptid!=null,"deptid",deptid);
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        queryWrapper.eq("type",Constast.USER_TYPE_NORMAL);
        List<User> list = this.userService.list(queryWrapper);
        return new DataGridView(list);
    }

    /**
     * 将用户名转换成拼音
     */
    @RequestMapping("changeChineseToPinyin")
    public Map<String,Object> changeChineseToPinyin(String username){
        Map<String,Object> map = new HashMap<>();
        if(null!=username){
            map.put("value", HanyuPinyinUtil.toHanyuPinyin(username));
        }else{
            map.put("value","");
        }
        return map;
    }
    /**
     * 添加
     */
    @RequestMapping("addUser")
    public ResultObj addUser(UserVo userVo){
        try {
            //设置类型
            userVo.setType((Integer) Constast.USER_TYPE_NORMAL);
            userVo.setHiredate(new Date());
            String salt = IdUtil.simpleUUID().toUpperCase();
            userVo.setSalt(salt);//设置盐
            //设置加密
            userVo.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD,salt,2).toString());
            this.userService.save(userVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 删除
     */
    @RequestMapping("deleteUser")
    public ResultObj deleteUser(Integer id){
        try {
            this.userService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 根据用户id查询一个用户
     */
    @RequestMapping("loadUserById")
    public DataGridView loadUserById(Integer id){
        return new DataGridView(this.userService.getById(id));
    }

    /**
     * 修改
     */
    @RequestMapping("updateUser")
    public ResultObj updateUser(UserVo userVo){
        try {
            this.userService.updateById(userVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 重置用户密码
     */
    @RequestMapping("resetPwd")
    public ResultObj resetPwd(Integer id){
        try {
            User user = new User();
            user.setId(id);
            String salt = IdUtil.simpleUUID().toUpperCase();
            user.setSalt(salt);//设置盐
            //设置加密
            user.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD,salt,2).toString());
            this.userService.updateById(user);
            return ResultObj.RESET_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.RESET_ERROR;
        }
    }
    /**
     * 根据用户ID查询角色并选择已拥有的角色
     */
    @RequestMapping("initRoleByUserId")
    public DataGridView initRoleByUserId(Integer id){
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        //1.查询所有可用的角色
        List<Map<String, Object>> listMaps = this.roleService.listMaps(queryWrapper);
        //2.查询当前用户拥有的Id集合
        List<Integer> currentUserRoleIds = this.roleService.queryUserRoleIdsByUid(id);
        for (Map<String, Object> map:listMaps) {
            Boolean LAY_CHECKED = false;
            Integer roleId = (Integer) map.get("id");
            for (Integer rid : currentUserRoleIds) {
                if(rid==roleId){
                    LAY_CHECKED = true;
                    break;
                }
            }
            map.put("LAY_CHECKED",LAY_CHECKED);
        }
        return new DataGridView(Long.valueOf(listMaps.size()),listMaps);
    }

    /**
     * 保存用户和角色之间的关系
     */
    @RequestMapping("saveUserRole")
    public ResultObj saveUserRole(Integer uid,Integer[] ids){
        try {
            this.userService.saveUserRole(uid,ids);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DISPATCH_EPPOR;
        }
    }
    /**
     * 修改用户的密码
     * @param oldPassword  用户的原密码
     * @param newPwdOne     用户第一次输入的新密码
     * @param newPwdTwo     用户第二次输入的新密码
     * @return
     */
    @RequestMapping("changePassword")
    public ResultObj changePassword(String oldPassword,String newPwdOne,String newPwdTwo){
        //1.先通过session获得当前用户的ID
        User user =(User) WebUtils.getSession().getAttribute("user");
        //2.将oldPassword加盐并散列两次在和数据库中的密码进行对比
        Integer userId = user.getId();
        User user1 = userService.getById(userId);
        //2.1获得该用户的盐
        String salt = user1.getSalt();
        //2.2通过用户输入的原密码，从数据库中查出的盐，散列次数生成新的旧密码
        String oldPassword2 = new Md5Hash(oldPassword,salt,Constast.HASHITERATIONS).toString();
        if (oldPassword2.equals(user1.getPwd())){
            if (newPwdOne.equals(newPwdTwo)){
                //3.生成新的密码
                String newPassword = new Md5Hash(newPwdOne,salt,Constast.HASHITERATIONS).toString();
                user1.setPwd(newPassword);
                userService.updateById(user1);
                return ResultObj.UPDATE_SUCCESS;
            }else {
                return ResultObj.UPDATE_ERROR;
            }
        }else {
            return ResultObj.UPDATE_ERROR;
        }
    }
    /**
     * 返回当前已登录的user
     * @return
     */
    @RequestMapping("getNowUser")
    public User getNowUser(){
        //1.获取当前session中的user
        User user = (User) WebUtils.getSession().getAttribute("user");
        System.out.println("*****************************************");
        System.out.println(user);
        return user;
    }


    /**
     * 修改用户
     * @param userVo
     * @return
     */
    @RequestMapping("updateUserInfo")
    public ResultObj updateUserInfo(UserVo userVo){
        try {
            //用户头像不是默认图片
            if (!(userVo.getImgpath()!=null&&userVo.getImgpath().equals(Constast.DEFAULT_IMG_GOODS))){
                if (userVo.getImgpath().endsWith("_temp")){
                    String newName = AppFileUtils.renameFile(userVo.getImgpath());
                    userVo.setImgpath(newName);
                    //删除原先的图片
                    String oldPath = userService.getById(userVo.getId()).getImgpath();
                    AppFileUtils.removeFile(oldPath);
                }
            }
            userService.updateById(userVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
}

