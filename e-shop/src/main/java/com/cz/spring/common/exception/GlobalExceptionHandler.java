package com.cz.spring.common.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.wf.jwtp.exception.TokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger("GlobalExceptionHandler");

 
    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception ex, Model model, HttpServletRequest request, HttpServletResponse response) {
        String url = "error/500.html", msg = "system error, sorry", error = ex.getMessage();
        int code = 500;
        if (ex instanceof IException) {
            msg = ex.getMessage();
            code = ((IException) ex).getCode();
        } else if (ex instanceof UnauthorizedException) {
            code = 403;
            msg = "Sorry, you dont have permission to access this page";
            url = "error/403.html";
        } else if (ex instanceof TokenException) {
            msg = ex.getMessage();
            code = ((IException) ex).getCode();
            // 支持跨域
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, Authorization");
            printJSON(code, msg, error, response);
            return null;
        } else {
            logger.error(ex.getMessage(), ex);
        }
        if (isAjax(request)) {
            printJSON(code, msg, error, response);
            return null;
        }
        model.addAttribute("code", code);
        model.addAttribute("msg", msg);
        model.addAttribute("error", error);
        return url;
    }


    private boolean isAjax(HttpServletRequest request) {
        String xHeader = request.getHeader("X-Requested-With");
        return (xHeader != null && xHeader.contains("XMLHttpRequest"));
    }

    
    private void printJSON(int code, String msg, String error, HttpServletResponse response) {
        response.setContentType("application/json;charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.write("{\"code\": " + code + ", \"msg\": \"" + msg + "\", \"error\": \"" + error + "\"}");
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
