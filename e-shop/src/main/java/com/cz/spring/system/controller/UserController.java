package com.cz.spring.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cz.spring.common.BaseController;
import com.cz.spring.common.JsonResult;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.common.exception.BusinessException;
import com.cz.spring.common.shiro.EndecryptUtil;
import com.cz.spring.common.utils.PoiUtil;
import com.cz.spring.common.utils.StringUtil;
import com.cz.spring.system.model.Role;
import com.cz.spring.system.model.User;
import com.cz.spring.system.service.RoleService;
import com.cz.spring.system.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {
    private static final String DEFAULT_PSW = "123456";  
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequiresPermissions("user:view")
    @RequestMapping
    public String user(Model model) {
        List<Role> roles = roleService.list();
        model.addAttribute("roles", roles);
        return "system/user.html";
    }


    @RequiresPermissions("user:view")
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<User> list(HttpServletRequest request) {
        return userService.listUser(new PageParam(request).setDefaultOrder(null, new String[]{"create_time"}));
    }


    @RequiresPermissions("user:view")
    @ResponseBody
    @RequestMapping("/listByIm")
    public JsonResult listByIm(HttpServletRequest request){
        List<User> userList=userService.list();
        Map<String,Object> result=new HashMap<>();
        List<Map<String,Object>> li=new ArrayList<>();
        List<Map<String,Object>> li2=new ArrayList<>();
        for(User u:userList){
            if(u.getUsername().equals("admin")){
                Map<String,Object> map=new HashMap<>();
                map.put("id",u.getUserId());
                map.put("username","help center");
                map.put("status","online");
                map.put("avatar",u.getAvatar());
                result.put("mine",map);
            }else{
                Map<String,Object> map2=new HashMap<>();
                map2.put("id",u.getUserId());
                map2.put("username",u.getNickName());
                map2.put("avatar",u.getAvatar());
                li.add(map2);
            }
        }
        Map<String,Object> map4=new HashMap<>();
        map4.put("groupname","help center");
        map4.put("id",0);
        map4.put("list",li);
        li2.add(map4);

        result.put("friend",li2);
        return JsonResult.ok(0,"search successfully").put("data", result);
    }

    @RequiresPermissions("user:update")
    @ResponseBody
    @RequestMapping("/add")
    public JsonResult add(User user, String roleIds) {
        user.setState(0);
        user.setUserType(0);
        user.setPassword(EndecryptUtil.encrytMd5(DEFAULT_PSW, 3));
        if (userService.addUser(user, getRoleIds(roleIds))) {
            return JsonResult.ok("add successfully, default password is" + DEFAULT_PSW);
        }
        return JsonResult.error("failed to add");
    }


    @RequiresPermissions("user:update")
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(User user, String roleIds) {
        if (userService.updateUser(user, getRoleIds(roleIds))) {
            return JsonResult.ok("update successfully");
        }
        return JsonResult.error("failed to update");
    }


    private List<Integer> getRoleIds(String rolesStr) {
        List<Integer> roleIds = new ArrayList<>();
        if (rolesStr != null) {
            String[] split = rolesStr.split(",");
            for (String t : split) {
                try {
                    roleIds.add(Integer.parseInt(t));
                } catch (Exception e) {
                }
            }
        }
        return roleIds;
    }


    @RequiresPermissions("user:update")
    @ResponseBody
    @RequestMapping("/updateState")
    public JsonResult updateState(Integer userId, Integer state) {
        if (userId == null) {
            return JsonResult.error("userid can't be blank");
        }
        if (state == null || (state != 0 && state != 1)) {
            return JsonResult.error("state is wrong");
        }
        User user = new User();
        user.setUserId(userId);
        user.setState(state);
        if (userService.updateById(user)) {
            return JsonResult.ok();
        }
        return JsonResult.error();
    }


    @RequiresPermissions("user:update")
    @ResponseBody
    @RequestMapping("/delete")
    public JsonResult delete(Integer userId) {
        if (userId == null) {
            return JsonResult.error("userid can't be blank");
        }
        if (userService.removeById(userId)) {
            return JsonResult.ok("delete successfully");
        }
        return JsonResult.error("failed to delete");
    }


    @RequiresPermissions("user:update")
    @ResponseBody
    @RequestMapping("/restPsw")
    public JsonResult resetPsw(Integer userId) {
        if (userId == null) {
            return JsonResult.error("userid can't be blank");
        }
        User user = new User();
        user.setUserId(userId);
        user.setPassword(EndecryptUtil.encrytMd5(DEFAULT_PSW, 3));
        if (userService.updateById(user)) {
            return JsonResult.ok("reset successfully, default password is" + DEFAULT_PSW);
        }
        return JsonResult.error("failed to reset");
    }


    @ResponseBody
    @RequestMapping("/updatePsw")
    public JsonResult updatePsw(String oldPsw, String newPsw) {
        if (StringUtil.isBlank(oldPsw, newPsw)) {
            return JsonResult.error("parameter can't be blank");
        }
        if (getLoginUser() == null) {
            return JsonResult.error("you need to login");
        }
        if (!getLoginUser().getPassword().equals(EndecryptUtil.encrytMd5(oldPsw, 3))) {
            return JsonResult.error("old password is incorrect");
        }
        User user = new User();
        user.setUserId(getLoginUser().getUserId());
        user.setPassword(EndecryptUtil.encrytMd5(newPsw, 3));
        if (userService.updateById(user)) {
            return JsonResult.ok("update successfully");
        }
        return JsonResult.error("failed to update");
    }

    @Transactional(rollbackFor = Exception.class)
    @RequiresPermissions("user:update")
    @ResponseBody
    @RequestMapping("/import")
    public JsonResult importData(MultipartFile file) throws IOException {
        List<Role> roleList = roleService.list();
        List<String[]> list = PoiUtil.importData(file.getInputStream(), 1, 0);
        for (int i = 0; i < list.size(); i++) {
            String[] strs = list.get(i);
            User user = new User();
            user.setPassword(EndecryptUtil.encrytMd5("123456", 3));
            user.setUsername(strs[0]);
            user.setNickName(strs[1]);
            user.setSex(strs[2]);
            String[] split = strs[3].split(",");
            List<Integer> roleIds = new ArrayList<>();
            for (String roleName : split) {
                Role role = getRoleByName(roleList, roleName);
                if (role == null) {
                    throw new BusinessException(  (i + 2) + "row role" + roleName + "doesn't exist");
                }
                roleIds.add(role.getRoleId());
            }
            userService.addUser(user, roleIds);
        }
        return JsonResult.ok("import successfully");
    }

    private Role getRoleByName(List<Role> roleList, String roleName) {
        for (Role role : roleList) {
            if (role.getRoleName().equals(roleName)) {
                return role;
            }
        }
        return null;
    }
}
