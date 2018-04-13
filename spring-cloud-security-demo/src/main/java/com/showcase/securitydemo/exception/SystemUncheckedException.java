package com.showcase.securitydemo.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.TreeMap;

/**
 * 应用全局的最上层异常，继承自运行时异常
 * 特性:
 * <ol>
 * <li>新增了ErrorCode,定义标准的错误码</li>
 * <li>新增了properties,将异常发生时的响应参数信息(异常上下文)填充</li>
 * </ol>
 * 使用可参考{}
 *
 * @author 尤鹏飞
 * @version 2017年01月16日09:54:09
 */
public class SystemUncheckedException  extends RuntimeException {
    private final Map<String, String> properties = new TreeMap<String, String>();

    /**
     * 关闭默认构造器
     */
    private SystemUncheckedException() {
        super();
    }

    /**
     * 直接构造
     *
     * @param message 错误信息
     */
    public SystemUncheckedException(String message) {
        super(message);
    }

    /**
     * 已有原始异常的情况下，下列构造方法仅内部使用，
     * 类之外调用使用工厂方法{@code #wrap}
     */
    public SystemUncheckedException(Throwable cause) {
        super(cause);
    }


    public final Map<String, String> getProperties() {
        return properties;
    }

    public final Object getProperty(String name) {
        return properties.get(name);
    }

    public final SystemUncheckedException setProperty(String name, String value) {
        properties.put(name, value);
        return this;
    }

    public final SystemUncheckedException setProperty(String name, Object value) {
        if (value != null) {
            properties.put(name, String.valueOf(value));
        }
        return this;
    }

    @Override
    public void printStackTrace(PrintStream s) {
        synchronized (s) {
            printStackTrace(new PrintWriter(s));
        }
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        synchronized (s) {
            s.println(this);
            s.println("\t-------------------------------");
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                s.println("\t" + entry.getKey() + "=[" + entry.getValue() + "]");
            }
            s.println("\t-------------------------------");
            StackTraceElement[] trace = getStackTrace();
            for (StackTraceElement aTrace : trace) {
                s.println("\tat " + aTrace);
            }

            Throwable ourCause = getCause();
            if (ourCause != null) {
                ourCause.printStackTrace(s);
            }
            s.flush();
        }
    }

    /**
     * 将异常栈信息打印到String中
     *
     * @return 字符串
     */
    public final String getStackTraceAsString() {

        return ExceptionUtil.getStackTraceAsString(this);
    }

    private static class ExceptionUtil {

        /**
         * Migrated from Guava.
         *
         * Returns a string containing the result of
         * {@link Throwable#toString() toString()}, followed by the full, recursive
         * stack trace of {@code throwable}. Note that you probably should not be
         * parsing the resulting string; if you need programmatic access to the stack
         * frames, you can call {@link Throwable#getStackTrace()}.
         */
        public static String getStackTraceAsString(Throwable throwable) {
            StringWriter stringWriter = new StringWriter();
            throwable.printStackTrace(new PrintWriter(stringWriter));
            return stringWriter.toString();
        }

    }
}
