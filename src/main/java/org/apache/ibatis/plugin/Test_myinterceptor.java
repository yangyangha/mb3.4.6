package org.apache.ibatis.plugin;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.util.Map;
import java.util.Properties;

/**
 * Created by yangyangha on 2018/8/14.
 *
 * 测试demo
 */

@Intercepts({
        @Signature(type = Map.class, method = "get", args = {Object.class})})
public class Test_myinterceptor implements Interceptor {
    private static Log log = LogFactory.getLog(Test_myinterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("interceptor invoked...");
//        log.debug("interceptor invoked..");
        return "Always";
    }

    @Override
    public Object plugin(Object target) {
        System.out.println("interceptor plugin");
//        log.debug("interceptor plugin...");
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
