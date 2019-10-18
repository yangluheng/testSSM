package com.xy.controller;

import com.xy.domain.SysLog;
import com.xy.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 基于AOP的日志类
 */
@Component
@Aspect
public class LogAop {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private SysLogService sysLogService;

    private Date visitTime; // 访问时间
    private Class clazz;// 访问的类
    private Method method; // 访问的方法
    /**
     * 前置通知
     * 主要是获取当前访问时间，执行的类是哪一个，执行的方法是哪一个
     * @param joinPoint
     */
    @Before("execution(* com.xy.controller.*.*(..))")
    public void doBefore(JoinPoint joinPoint) throws NoSuchMethodException {
        visitTime=new Date();//获取当前访问时间
        clazz=joinPoint.getTarget().getClass();//访问的类
        String methodName=joinPoint.getSignature().getName();   //获取访问的方法名称
        Object []args=joinPoint.getArgs();          //获取访问的方法的参数
        if (args==null||args.length==0){
            method=clazz.getMethod(methodName);
        }
        else {
            Class []classArgs=new Class[args.length];
            for (int i = 0; i <args.length ; i++) {
                classArgs[i]=args[i].getClass();
            }
            method=clazz.getMethod(methodName,classArgs);         //只能获取无参数的方法
        }

    }

    /**
     * 后置通知
     * @param joinPoint
     */
    @After("execution(* com.xy.controller.*.*(..))")
    public void doAfter(JoinPoint joinPoint){
        long time=new Date().getTime()-visitTime.getTime(); //获取访问时长
        //获取url
        String url="";
        if (method!=null&&clazz!=null&&clazz!=SysLogController.class){
            //1.获取类上的@RequestMapping("/syslog")
            RequestMapping classAnnotation= (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation!=null){
                String []classValue=classAnnotation.value();
                //2.获取方法上的@RequestMapping()
                RequestMapping methodAnnotation=method.getAnnotation(RequestMapping.class);
                if (methodAnnotation!=null){
                    String []methodValue=methodAnnotation.value();
                    url=classValue[0]+methodValue[0];

                    //获取ip
                    String ip=httpServletRequest.getRemoteAddr();
                    //获取当前用户
                    SecurityContext context= SecurityContextHolder.getContext();    //从上下文中获取用户
                    User user= (User) context.getAuthentication().getPrincipal();
                    String userName=user.getUsername();

                    //将相关信息封装到SysLog对象
                    SysLog sysLog=new SysLog();
                    sysLog.setExecutionTime(time);
                    sysLog.setIp(ip);
                    sysLog.setMethod("[类名] "+clazz.getName()+"[方法名] "+method.getName());
                    sysLog.setUrl(url);
                    sysLog.setUsername(userName);
                    sysLog.setVisitTime(visitTime);
                    //调用service完成日志记录操作
                    sysLogService.save(sysLog);
                }
            }
        }
    }
}
