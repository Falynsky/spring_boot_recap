package pl.falynsky.course1.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@EnableAspectJAutoProxy
public class LoggingAspect {

    @Pointcut("execution(* pl.falynsky.course1..*(..))")
    private void anyPublicMethod(){}

    @Before("anyPublicMethod()")
    public void beforeAnyPublicMethod(JoinPoint joinPoint){
        log.info(":: before :: " + joinPoint.getSignature().getName());
    }

    @After("anyPublicMethod()")
    public void afterAnyPublicMethod(JoinPoint joinPoint){
        log.info(":: after :: " + joinPoint.getSignature().getName());
    }

    @Around("execution(* pl.falynsky.course1.controller.SimpleController.*(..))")
    public Object aroundControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        Object proceed = joinPoint.proceed();
        log.info(":: Time :: " + (System.nanoTime() - start) + "ms");

        return proceed;
    }
}
