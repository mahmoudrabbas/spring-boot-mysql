package com.store.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
public class LogDetailAspect {

    Logger log = LoggerFactory.getLogger(LogDetailAspect.class);

    @Pointcut(value = "execution(* com.store.controller..*(..))")
    public void forAllControllers(){}
    @Pointcut(value = "execution(* com.store.service..*(..))")
    public void forAllServices(){}
    @Pointcut(value = "execution(* com.store.repository..*(..))")
    public void forAllRepos(){}


    @Pointcut(value = "forAllControllers() || forAllServices() || forAllRepos()")
    public void forApp(){}

    @After(value = "forApp()")
    public void logDetail(JoinPoint joinPoint){
       log.info("Details :{}",joinPoint.getSignature().toLongString());
    }

}
