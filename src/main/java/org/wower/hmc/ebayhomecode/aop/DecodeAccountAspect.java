package org.wower.hmc.ebayhomecode.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.wower.hmc.ebayhomecode.bean.Account;
import org.wower.hmc.ebayhomecode.exception.BusinessException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Aspect
@Component
@Order(1)
public class DecodeAccountAspect {

    @Around("execution(* org.wower.hmc.ebayhomecode.controller..*(..))")
    public Object decodeAccount(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String account = request.getParameter("account");
        if (account != null) {
            try {
                byte[] decodedBytes = Base64.getDecoder().decode(account);
                String decodedAccount = new String(decodedBytes, StandardCharsets.UTF_8);
                ObjectMapper mapper = new ObjectMapper();
                Account accountObj = mapper.readValue(decodedAccount, Account.class);

                // Check if userId, accountName, and role are not null
                if (accountObj.getUserId() == null || accountObj.getAccountName() == null || accountObj.getRole() == null) {
                    throw new BusinessException("Incorrect account parameters");
                }

                RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
                requestAttributes.setAttribute("account", accountObj, RequestAttributes.SCOPE_REQUEST);
            } catch (Exception e) {
                throw new BusinessException("Incorrect account parameters");
            }
            return joinPoint.proceed();
        } else {
            throw new BusinessException("Account is missing");
        }
    }

}