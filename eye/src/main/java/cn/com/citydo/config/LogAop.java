package cn.com.citydo.config;



import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;


@Aspect
@Configuration
@Slf4j
public class LogAop
{
    /**
     * 定义切点Pointcut
     * 第一个*号：表示返回类型， *号表示所有的类型
     * 第二个*号：表示类名，*号表示所有的类
     * 第三个*号：表示方法名，*号表示所有的方法
     * 后面括弧里面表示方法的参数，两个句点表示任何参数
     */
    @Pointcut("execution(* cn.com.citydo.module.*.controller..*.*(..))")
    public void executionService() {
    
    }
    
    /**
     * 方法调用之前调用
     * @param joinPoint
     */
    @Before(value = "executionService()")
    public void doBefore(JoinPoint joinPoint)
    {
        //添加日志打印
        String requestId = UUID.randomUUID().toString().replaceAll("-","");
        MDC.put("requestId",requestId);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        try {
            log.info(methodName + " =====>@Before：请求参数为：{}", JSONObject.toJSONString(joinPoint.getArgs()));
        }catch (Exception e){
            log.error(methodName + " =====>@Before：请求参数过大");
        }
        
    }
    
    /**
     * 方法之后调用
     * @param joinPoint
     * @param returnValue 方法返回值
     */
    @AfterReturning(pointcut = "executionService()",returning="returnValue")
    public void  doAfterReturning(JoinPoint joinPoint,Object returnValue)
    {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        String res = JSONObject.toJSONString(returnValue);
        if(Strings.isNotBlank(res) && res.length() > 1100)
        {
            res = res.substring(0,500) + "·········" + res.substring(res.length()-500);
        }
        log.info(methodName + " =====>@AfterReturning：响应参数为：{}", res);
        // 处理完请求，返回内容
        MDC.clear();
    }
    
    /**
     * 统计方法执行耗时Around环绕通知
     * @param joinPoint
     * @return
     */
    @Around("executionService()")
    public Object timeAround(ProceedingJoinPoint joinPoint) throws Throwable
    {
        //获取开始执行的时间
        long startTime = System.currentTimeMillis();
        // 定义返回对象、得到方法需要的参数
        Object obj = joinPoint.proceed();
        // 获取执行结束的时间
        long endTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        // 打印耗时的信息
        log.info(methodName+ " =====>处理本次请求共耗时："+ (endTime-startTime) + "ms");
        return obj;
    }
}
