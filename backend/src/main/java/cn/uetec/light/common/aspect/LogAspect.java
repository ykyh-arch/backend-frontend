package cn.uetec.light.common.aspect;

import cn.uetec.light.common.authentication.JWTUtil;
import cn.uetec.light.common.properties.LightProperties;
import cn.uetec.light.common.utils.HttpContextUtil;
import cn.uetec.light.common.utils.IPUtil;
import cn.uetec.light.system.domain.SysLog;
import cn.uetec.light.system.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**   
 * Copyright: Copyright (c) 2019 Corporation,All Right Reserved
 * 
 * @ClassName: LogAspect.java
 * @Description: AOP 记录用户操作日志
 *
 * @version: v1.0.0
 * @author: ykyh
 * @date: 2019年9月24日 下午1:49:13 
 *
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LightProperties lightProperties;

    @Autowired
    private LogService logService;

    @Pointcut("@annotation(cn.uetec.light.common.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        long beginTime = System.currentTimeMillis();
        // 执行方法
        result = point.proceed();
        // 获取 request
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        // 设置 IP 地址
        String ip = IPUtil.getIpAddr(request);
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        if (lightProperties.isOpenAopLog()) {
            // 保存日志
            String token = (String) SecurityUtils.getSubject().getPrincipal();
            String username = "";
            if (StringUtils.isNotBlank(token)) {
                username = JWTUtil.getUsername(token);
            }

            SysLog log = new SysLog();
            log.setUsername(username);
            log.setIp(ip);
            log.setTime(time);
            logService.saveLog(point, log);
        }
        return result;
    }
}
