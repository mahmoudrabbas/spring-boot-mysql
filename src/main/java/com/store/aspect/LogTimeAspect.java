package com.store.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogTimeAspect {
    Logger logger = LoggerFactory.getLogger(LogTimeAspect.class);

    @Around(value = "execution(* com.store.service..*(..))")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object returnedValue = joinPoint.proceed();
        logger.info("This method: {} {} {} takes {} ",joinPoint.getSignature(), joinPoint.getArgs(), joinPoint.getKind() ,System.currentTimeMillis()-start+" ms" );
        return returnedValue;
    }

}
