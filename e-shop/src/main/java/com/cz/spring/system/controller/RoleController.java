package com.cz.spring.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cz.spring.common.JsonResult;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.common.utils.JSONUtil;
import com.cz.spring.common.utils.StringUtil;
import com.cz.spring.system.model.Authorities;
import com.cz.spring.system.model.Role;
import com.cz.spring.system.service.AuthoritiesService;
import com.cz.spring.system.service.RoleAuthoritiesService;
import com.cz.spring.system.service.RoleService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthoritiesService authoritiesService;
    @Autowired
    private RoleAuthoritiesService roleAuthoritiesService;

    @RequiresPermissions("role:view")
    @RequestMapping()
    public String role() {
        return "system/role.html";
    }


    @RequiresPermissions("role:view")
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<Role> list(HttpServletRequest request) {
        PageParam pageParam = new PageParam(request);
        return new PageResult<>(roleService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
    }


    @RequiresPermissions("role:update")
    @ResponseBody
    @RequestMapping("/add")
    public JsonResult add(Role role) {
        if (roleService.save(role)) {
            return JsonResult.ok("add successfully");
        }
        return JsonResult.error("failed to add");
    }


    @RequiresPermissions("role:update")
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(Role role) {
        if (roleService.updateById(role)) {
            return JsonResult.ok("update successfully");
        }
        return JsonResult.error("failed to update");
    }


    @RequiresPermissions("role:update")
    @ResponseBody
    @RequestMapping("/delete")
    public JsonResult delete(Integer roleId) {
        if (roleService.removeById(roleId)) {
            return JsonResult.ok("delete successfully");
        }
        return JsonResult.error("failed to delete");
    }


    @ResponseBody
    @GetMapping("/authTree")
    public List<Map<String, Object>> authTree(Integer roleId) {
        List<Authorities> roleAuths = authoritiesService.listByRoleId(roleId);
        List<Authorities> allAuths = authoritiesService.list(new QueryWrapper<Authorities>().orderByAsc("order_number"));
        List<Map<String, Object>> authTrees = new ArrayList<>();
        for (Authorities one : allAuths) {
            Map<String, Object> authTree = new HashMap<>();
            authTree.put("id", one.getAuthorityId());
            authTree.put("name", one.getAuthorityName() + " " + StringUtil.getStr(one.getAuthority()));
            authTree.put("pId", one.getParentId());
            authTree.put("open", true);
            authTree.put("checked", false);
            for (Authorities temp : roleAuths) {
                if (temp.getAuthorityId().equals(one.getAuthorityId())) {
                    authTree.put("checked", true);
                    break;
                }
            }
            authTrees.add(authTree);
        }
        return authTrees;
    }


    @RequiresPermissions("role:update")
    @ResponseBody
    @PostMapping("/updateRoleAuth")
    public JsonResult updateRoleAuth(Integer roleId, String authIds) {
        if (roleAuthoritiesService.updateRoleAuth(roleId, JSONUtil.parseArray(authIds, Integer.class))) {
            return JsonResult.ok("update successfully");
        }
        return JsonResult.error("failed to update");
    }
}
