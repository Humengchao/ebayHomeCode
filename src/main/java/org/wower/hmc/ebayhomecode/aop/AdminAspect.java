package org.wower.hmc.ebayhomecode.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.wower.hmc.ebayhomecode.bean.Account;
import org.wower.hmc.ebayhomecode.exception.BusinessException;

@Aspect
@Component
public class AdminAspect {

    @Around("execution(* org.wower.hmc.ebayhomecode.controller.AdminController.addUser(..))")
    public Object checkAccountRole(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Account account = (Account) requestAttributes.getAttribute("account", RequestAttributes.SCOPE_REQUEST);
        if (!Account.ROLE_ADMIN.equals(account.getRole())) {
            throw new BusinessException("Account role is not admin");
        }

        return joinPoint.proceed();
    }
}
