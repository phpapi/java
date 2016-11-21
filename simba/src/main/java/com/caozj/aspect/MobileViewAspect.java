package com.caozj.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.caozj.annotation.MobileViewAnnotation;

/**
 * 用来替换不同设备的返回页面view的切面实现
 * 
 * @author caozj
 *
 */
@Aspect
@Component
public class MobileViewAspect {

  @Around(value = "@annotation(mbv)")
  public Object view(ProceedingJoinPoint pjd, MobileViewAnnotation mbv) throws Throwable {
    Object result = pjd.proceed();
    if (!(result instanceof String)) {
      return result;
    }
    if (!pjd.getTarget().getClass().getName().endsWith("Controller")) {
      return result;
    }
    String deviceType = pjd.getArgs()[0].toString();
    if ("mobile".equals(deviceType)) {
      result = "mobile/" + result;
    } else if ("tablet".equals(deviceType)) {
      result = "tablet/" + result;
    }
    return result;
  }
}
