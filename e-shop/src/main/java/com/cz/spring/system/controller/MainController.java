package com.cz.spring.system.controller;

import com.cz.spring.common.BaseController;
import com.cz.spring.common.JsonResult;
import com.cz.spring.common.utils.StringUtil;
import com.cz.spring.common.utils.UserAgentGetter;
import com.cz.spring.system.model.Authorities;
import com.cz.spring.system.model.LoginRecord;
import com.cz.spring.system.model.User;
import com.cz.spring.system.service.AuthoritiesService;
import com.cz.spring.system.service.LoginRecordService;
import com.cz.spring.system.service.UserService;
import com.wf.captcha.utils.CaptchaUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class MainController extends BaseController implements ErrorController {
    @Autowired
    private AuthoritiesService authoritiesService;
    @Autowired
    private LoginRecordService loginRecordService;
    @Autowired
    private UserService userService;

    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        if (getLoginUser() == null) {
            return "redirect:login";
        }
        List<Authorities> authorities = authoritiesService.listByUserId(getLoginUserId());
        List<Map<String, Object>> menuTree = getMenuTree(authorities, -1);
        model.addAttribute("menus", menuTree);  
        model.addAttribute("loginUser", getLoginUser());  
        return "index.html";
    }


    @GetMapping("/login")
    public String login() {
        if (getLoginUser() != null) {
            return "redirect:index";
        }
        return "login.html";
    }


    @ResponseBody
    @PostMapping("/login")
    public JsonResult doLogin(String username, String password, String code, HttpServletRequest request) {
        if (StringUtil.isBlank(username, password)) {
            return JsonResult.error("Username and password can't be blank");
        }
        if (!CaptchaUtil.ver(code, request)) {
            // CaptchaUtil.clear(request);
            return JsonResult.error("Wrong captcha");
        }
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            SecurityUtils.getSubject().login(token);
            addLoginRecord(getLoginUserId(), request);
            //登陆成功修改用户在线状态
            User u=new User();
            u.setUserId(getLoginUserId());
            u.setUserOnlineType(1);
            userService.updateById(u);
            return JsonResult.ok("login successfully");
        } catch (IncorrectCredentialsException ice) {
            return JsonResult.error("wrong passwrod");
        } catch (UnknownAccountException uae) {
            return JsonResult.error("account doesn't existed");
        } catch (LockedAccountException e) {
            return JsonResult.error("account have been banned");
        } catch (ExcessiveAttemptsException eae) {
            return JsonResult.error("try again");
        }
    }


    @RequestMapping("/console")
    public String console() {
        return "system/console.html";
    }


    @RequestMapping("/assets/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        try {
            CaptchaUtil.out(5, request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/error")
    public String error(String code) {
        if ("403".equals(code)) {
            return "error/403.html";
        } else if ("500".equals(code)) {
            return "error/500.html";
        }
        return "error/404.html";
    }

    @Override
    public String getErrorPath() {
        return "/error?code=404";
    }


    private List<Map<String, Object>> getMenuTree(List<Authorities> authorities, Integer parentId) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < authorities.size(); i++) {
            Authorities temp = authorities.get(i);
            if (temp.getIsMenu() == 0 && parentId.intValue() == temp.getParentId().intValue()) {
                Map<String, Object> map = new HashMap<>();
                map.put("menuName", temp.getAuthorityName());
                map.put("menuIcon", temp.getMenuIcon());
                map.put("menuUrl", StringUtil.isBlank(temp.getMenuUrl()) ? "javascript:;" : (temp.getMenuUrl()));
                map.put("subMenus", getMenuTree(authorities, authorities.get(i).getAuthorityId()));
                list.add(map);
            }
        }
        return list;
    }


    private void addLoginRecord(Integer userId, HttpServletRequest request) {
        UserAgentGetter agentGetter = new UserAgentGetter(request);
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setUserId(userId);
        loginRecord.setOsName(agentGetter.getOS());
        loginRecord.setDevice(agentGetter.getDevice());
        loginRecord.setBrowserType(agentGetter.getBrowser());
        loginRecord.setIpAddress(agentGetter.getIpAddr());
        loginRecordService.save(loginRecord);
    }

 
    @RequestMapping("/tpl/message")
    public String message() {
        return "system/tpl-message.html";
    }


    @RequestMapping("/tpl/password")
    public String password() {
        return "system/tpl-password.html";
    }


    @RequestMapping("/tpl/theme")
    public String theme() {
        return "system/tpl-theme.html";
    }


    @RequestMapping("/tpl/note")
    public String note() {
        return "system/tpl-note.html";
    }
}
