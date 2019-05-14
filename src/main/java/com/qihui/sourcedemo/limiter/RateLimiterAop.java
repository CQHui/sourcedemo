package com.qihui.sourcedemo.limiter;

import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author chenqihui
 * @date 2019/5/14
 */
@Aspect
@Component
public class RateLimiterAop {

    private ConcurrentHashMap<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

    @Pointcut("execution(public * com.qihui.sourcedemo.controller.*.*(..))")
    public void rateLimiter() {

    }

    @Around("rateLimiter()")
    public Object doBefore(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        ExtRateLimiter annotation = method.getAnnotation(ExtRateLimiter.class);
        if (annotation == null) {
            return proceedingJoinPoint.proceed();
        }
        double permitPerSecond = annotation.permitPerSecond();
        long timeout = annotation.timeout();
        RateLimiter rateLimiter = getRateLimiter(getRequestURI(), permitPerSecond);

        boolean tryAcquire = rateLimiter.tryAcquire(timeout, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            //降级或抛异常
            throw new RuntimeException("服务降级");
        }
        return proceedingJoinPoint.proceed();
    }


    /**
     * 获取当前请求
     * @return
     */
    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    /**
     * 获取当前请求uri
     * @return
     */
    private String getRequestURI() {
        return getRequest().getRequestURI();
    }

    private RateLimiter getRateLimiter(String uri, double permitPerSecond) {
        RateLimiter rateLimiter;
        rateLimiter = rateLimiterMap.get(uri);
        if (rateLimiter != null) {
            return rateLimiter;
        }
        rateLimiter = RateLimiter.create(permitPerSecond);
        rateLimiterMap.put(uri, rateLimiter);
        return rateLimiter;
    }

}
