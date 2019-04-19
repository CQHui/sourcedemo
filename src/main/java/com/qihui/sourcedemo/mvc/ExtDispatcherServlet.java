package com.qihui.sourcedemo.mvc;

import com.qihui.sourcedemo.mvc.annotation.ExtController;
import com.qihui.sourcedemo.mvc.annotation.ExtRequestMapping;
import com.qihui.sourcedemo.util.ClassUtil;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenqihui
 * @date 2019/4/19
 */
@WebServlet(urlPatterns = "/*")
public class ExtDispatcherServlet extends HttpServlet {

    // mvc bean key=beanid ,value=对象
    private ConcurrentHashMap<String, Object> mvcBeans = new ConcurrentHashMap<String, Object>();
    // mvc 请求方法 key=requestUrl,value=对象
    private ConcurrentHashMap<String, Object> mvcBeanUrl = new ConcurrentHashMap<String, Object>();
    // mvc 请求方法 key=requestUrl,value=方法
    private ConcurrentHashMap<String, String> mvcMethodUrl = new ConcurrentHashMap<String, String>();


    @Override
    public void init() throws ServletException {
        //1. 获取当前包下所有的类
        List<Class<?>> classes = ClassUtil.getClasses("com.qihui.sourcedemo");
        //2. 判断类上是否有加注解
        try {
            findClassMVCBeans(classes);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        //3.将url映射和方法关联
        handlerMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        if (StringUtils.isEmpty(requestURI)) {
            return;
        }
        Object object = mvcBeanUrl.get(requestURI);
        if (object == null) {
            resp.getWriter().println("404 not found");
        }
        String methodName = mvcMethodUrl.get(requestURI);
        if (StringUtils.isEmpty(methodName)) {
            resp.getWriter().println("not found method");
        }

        String result = (String) methodInvoke(object, methodName);
//        resp.getWriter().println(result);
        extResourceViewResolver(result, req, resp);
    }


    private void extResourceViewResolver(String pageName, HttpServletRequest req, HttpServletResponse resp) {
        String prefix = "/statics";
        String suffix = ".html";
        req.getRequestDispatcher(prefix + pageName + suffix);
    }

    public Object methodInvoke(Object object, String methodName) {
        Class<?> classInfo = object.getClass();
        try {
            Method method = classInfo.getMethod(methodName);
            Object result = method.invoke(object);
            return result;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 2.初始化当前包下所有的类,使用Java反射机制初始化对象存放在SpringMVC容器中key(beanId)-value(
    // 当前实例对象)
    public void findClassMVCBeans(List<Class<?>> classes) throws IllegalAccessException, InstantiationException {
        for (Class<?> classInfo : classes) {
            ExtController extController = classInfo.getDeclaredAnnotation(ExtController.class);
            if (extController != null) {
                // 默认类名小写 作为bean的名称
                String beanId = ClassUtil.toLowerCaseFirstOne(classInfo.getSimpleName());
                mvcBeans.put(beanId, ClassUtil.newInstance(classInfo));
            }
        }
    }

    public void handlerMapper() {
        //1. 遍历springmvc bean容器，判断类上是否有url映射
        for (Map.Entry<String, Object> entry : mvcBeans.entrySet()) {
            Object ob = entry.getValue();
            Class<?> classInfo = ob.getClass();
            //2. 判断类上是否有url映射
            ExtRequestMapping declaredAnnotation = classInfo.getDeclaredAnnotation(ExtRequestMapping.class);
            String baseUrl = null;
            if (declaredAnnotation != null) {
                baseUrl = declaredAnnotation.value();
            }
            //3.判断方法上是否有url映射
            Method[] declaredMethods = classInfo.getDeclaredMethods();
            for (Method method : declaredMethods) {
                ExtRequestMapping methodDeclaredAnnotation = method.getDeclaredAnnotation(ExtRequestMapping.class);
                if (methodDeclaredAnnotation != null) {
                    String methodurl = baseUrl + methodDeclaredAnnotation.value();
                    mvcBeanUrl.put(methodurl,  ob);
                    mvcMethodUrl.put(methodurl, method.getName());
                }
            }
        }

    }

}
