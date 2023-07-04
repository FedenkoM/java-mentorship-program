package org.spring.data.access.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Aspect
public class MethodLoggingAspect {

    @Around("@annotation(org.spring.core.program.annotation.LogMethod)")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object returnValue = joinPoint.proceed();
        StringBuilder message = new StringBuilder(joinPoint.getTarget().getClass().getSimpleName());
        message.append(" | Method: ");
        message.append(joinPoint.getSignature().getName());
        long totalTime = System.currentTimeMillis() - startTime;
        message.append(" totalTime=").append(totalTime).append("ms");
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            message.append(" args=[ | ");
            Arrays.asList(args).forEach(arg -> message.append(arg).append(" | "));
            message.append("]");
        }
        if (returnValue instanceof Collection) {
            message.append(", returning=").append(((Collection<?>) returnValue).size()).append(" instance(s)");
        } else {
            message.append(", returning=").append(returnValue.toString());
        }
        log.info(message.toString());
        return returnValue;
    }

}
