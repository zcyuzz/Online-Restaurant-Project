package com.cz.spring.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.cz.spring.common.exception.BusinessException;
import com.cz.spring.shop.model.Order;
import com.cz.spring.system.dao.SysConfigMapper;
import com.cz.spring.system.model.SysConfig;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @ClassName MailUtil
 * @Description 邮件发送工具类
 * @Author dinghao
 * @Date 2020/3/17 10:09
 * @Version 1.0
 **/
@Component
@Slf4j
public class MailUtil {

    private Logger logger = LoggerFactory.getLogger(MailUtil.class);

    @Autowired
    private SysConfigMapper configMapper;

    @Autowired
    private TemplateEngine templateEngine;


    @Async
    public void thymeleafEmail(String to, String subject,String personName, Order order) throws MessagingException, UnsupportedEncodingException {
        JavaMailSenderImpl jms = new JavaMailSenderImpl();

        SysConfig config=configMapper.selectById(1);
        if (config == null) {
            throw new BusinessException("fail to send mail, wrong parameter！");
        }
        if(config.getState()==1){
            String host = config.getConfigPar1();
            String port = config.getConfigPar2();
            String from = config.getConfigPar3();
            String password = config.getConfigPar4();
            String sender = config.getConfigPar5();
            jms.setHost(host);
            jms.setPort(Integer.parseInt(port));
            jms.setUsername(from);
            jms.setPassword(password);
            jms.setDefaultEncoding("Utf-8");
            Properties p = new Properties();
            p.setProperty("mail.smtp.auth", "true");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.ssl.enable", "true");
            p.put("mail.smtp.ssl.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            p.put("mail.smtp.ssl.socketFactory.port", 465);
            jms.setJavaMailProperties(p);

            MimeMessage mimeMessage = jms.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from, sender);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            Context ctx = new Context();
            ctx.setVariable("personName", personName);
            ctx.setVariable("orderNo", order.getOrderNo());
            ctx.setVariable("nickName", order.getNickName());
            ctx.setVariable("phone", order.getPhone());
            ctx.setVariable("goodsSn", order.getGoodsSn());
            ctx.setVariable("goodsNum", order.getGoodsNum());
            ctx.setVariable("goodsMoney", order.getGoodsMoney());
            ctx.setVariable("goodsColor", order.getGoodsColor());
            ctx.setVariable("goodsSize", order.getGoodsSize());
            ctx.setVariable("madeLogo", order.getMadeLogo());
            ctx.setVariable("madeText", order.getMadeText());
            ctx.setVariable("orderStatus", order.getOrderStatus());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ctx.setVariable("tDate", sdf.format(new Date()));
            String emailText = templateEngine.process("mailTemplate", ctx);
            mimeMessageHelper.setText(emailText, true);
            jms.send(mimeMessage);

        }else{

        }

    }

}
