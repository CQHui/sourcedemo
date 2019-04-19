package com.qihui.sourcedemo.inversion;

import com.qihui.sourcedemo.service.SysUserService;
import com.qihui.sourcedemo.util.ClassUtil;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenqihui
 * @date 2019/4/17
 */
public class AnnotationApplicationContext {
    // 扫包范围
    private String packageName;
    private ConcurrentHashMap<String, Object> initBean = null;

    public AnnotationApplicationContext(String packageName) throws Exception {
        this.packageName = packageName;
        initBeans();
        initEntryField();
    }

    /**
     * 给类初始化属性
     * @throws Exception
     */
    private void initEntryField() throws Exception{
        for (Map.Entry<String, Object> entry : initBean.entrySet()) {
            Object bean = entry.getValue();
            attrAssign(bean);
        }
    }


    public void initBeans() throws Exception {
        // 1.使用反射机制获取该包下所有的类已经存在bean的注解类
        List<Class> listClassesAnnotation = findClassExistService();
        if (listClassesAnnotation == null || listClassesAnnotation.isEmpty()) {
            throw new Exception("没有需要初始化的bean");
        }
        // 2.使用Java反射机制初始化对象
        initBean = initBean(listClassesAnnotation);
    }

    // 使用beanID查找对象
    public Object getBean(String beanId) throws Exception {
        if (StringUtils.isEmpty(beanId)) {
            throw new Exception("beanId不能为空");
        }
        if (initBean == null || initBean.isEmpty()) {
            throw new Exception("初始化bean为空!");
        }
        // 3.使用beanID查找查找对应bean对象
        return initBean.get(beanId);
    }


    // 使用反射读取类的属性,赋值信息
    public void attrAssign(Object object) throws IllegalArgumentException, IllegalAccessException {
        // 1.获取类的属性是否存在 获取bean注解
        Class<? extends Object> classInfo = object.getClass();
        Field[] declaredFields = classInfo.getDeclaredFields();
        for (Field field : declaredFields) {
            ExResource exResource = field.getAnnotation(ExResource.class);
            if (exResource != null) {
                // 属性名称
                String name = field.getName();
                // 2.使用属性名称查找bean容器赋值
                Object bean = initBean.get(name);
                if (bean != null) {
                    // 私有访问允许访问
                    field.setAccessible(true);
                    // 给属性赋值
                    field.set(object, bean);
                    continue;
                }
            }
        }

    }

    // 使用反射机制获取该包下所有的类已经存在bean的注解类
    public List<Class> findClassExistService() throws Exception {
        // 1.使用反射机制获取该包下所有的类
        if (StringUtils.isEmpty(packageName)) {
            throw new Exception("扫包地址不能为空!");
        }
        // 2.使用反射技术获取当前包下所有的类
        List<Class<?>> classesByPackageName = ClassUtil.getClasses(packageName);
        // 3.存放类上有bean注入注解
        List<Class> exisClassesAnnotation = new ArrayList<Class>();
        // 4.判断该类上属否存在注解
        for (Class classInfo : classesByPackageName) {
            ExtService extService = (ExtService) classInfo.getDeclaredAnnotation(ExtService.class);
            if (extService != null) {
                exisClassesAnnotation.add(classInfo);
                continue;
            }
        }
        return exisClassesAnnotation;
    }

    // 初始化bean对象
    public ConcurrentHashMap<String, Object> initBean(List<Class> listClassesAnnotation)
            throws InstantiationException, IllegalAccessException {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap<String, Object>();
        for (Class classInfo : listClassesAnnotation) {
            // 初始化对象
            Object newInstance = classInfo.newInstance();
            // 获取父类名称
            String beanId = ClassUtil.toLowerCaseFirstOne(classInfo.getSimpleName());
            concurrentHashMap.put(beanId, newInstance);
        }
        return concurrentHashMap;
    }




    public static void main(String[] args) throws Exception {
        AnnotationApplicationContext context = new AnnotationApplicationContext("com.qihui.sourcedemo");
        SysUserService sysUserService = (SysUserService) context.getBean("sysUserServiceImpl");
        sysUserService.userOrder();
    }
}
