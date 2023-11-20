package com.example.elasticjob.config;

/**
 * @author yfc
 * @date 2023/11/20 16:39
 */
public class MyThreadLocal {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setThreadLocal(String val){
        threadLocal.set(val);
    }

    public static String getThreadLocal(){
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }

}
