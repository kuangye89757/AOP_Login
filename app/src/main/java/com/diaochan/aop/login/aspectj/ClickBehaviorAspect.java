package com.diaochan.aop.login.aspectj;

import android.os.SystemClock;
import android.util.Log;

import com.diaochan.aop.login.annotation.ClickBehavior;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect //定义切面类
public class ClickBehaviorAspect {

    private static final String TAG = "diaochan >>>";
    
    //1.找到需要处理的切入点 
    //execution 以方法【执行时】作为切入点，触发Aspect类
    //(这里用到ClickBehavior注解的地方都算切入点，所以通过执行该注解触发Aspect类)
    //* *(..)) 表示该类的所有方法
    @Pointcut("execution(@com.diaochan.aop.login.annotation.ClickBehavior * *(..))")
    public void methodPointCut(){
        
    }
    

    /**
     * 2.对切入点如何处理 (@Before()、@After()、@Around())
     * 
     * 这里会对 @ClickBehavior修饰的方法的前和后进行注入 （通过aspectj而非javac）
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("methodPointCut()")
    public Object methodJoin(ProceedingJoinPoint joinPoint) throws Throwable{
        //获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        
        //获取方法所属的类名 
        String className = methodSignature.getDeclaringType().getSimpleName();
        
        //获取方法名 （area）
        String methodName = methodSignature.getName();
        
        //获取方法的注解值（需要统计的用户行为,这里是 @ClickBehavior("我的专区")的值）
        String funName = methodSignature.getMethod()
                .getAnnotation(ClickBehavior.class).value();
        
        // 统计方法的执行时间，统计用户点击某功能行为 （保存本地，每隔xx天上报服务器）
        long before = SystemClock.currentThreadTimeMillis();
        Log.e(TAG,"ClickBehavior Method start >>>");
        Object result = joinPoint.proceed(); //ClickBehavior修饰的方法的执行
        
        long during = SystemClock.currentThreadTimeMillis() - before;
        Log.e(TAG,"ClickBehavior Method end >>>");

        Log.e(TAG,String.format("统计了%s功能，在%s类的%s方法，用时%d ms",funName,className,methodName,during));
        
        return result;
    }
    
    
}
