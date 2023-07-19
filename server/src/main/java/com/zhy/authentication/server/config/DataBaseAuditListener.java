package com.zhy.authentication.server.config;


import com.zhy.authentication.server.context.Context;
import com.zhy.authentication.server.context.UserContext;
import com.zhy.authentication.server.domain.AbstractAuditingEntity;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * 类描述：
 * jpa的审计功能
 * @author msi
 * @version 1.0
 * @date 2022/1/15 4:49
 */
@Slf4j
public class DataBaseAuditListener {

    //~fields
    //==================================================================================================================
    public static final String APP_ID = "appId";
    public static final String CREATED_DATE = "createdDate";
    public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
    public static final String CREATED_BY = "createdBy";
    public static final String LAST_MODIFIED_BY = "lastModifiedBy";

    //~methods
    //==================================================================================================================

    /**
     * 新增数据时，填充应用Id,创建人，更新人，更新时间和创建时间
     */
    @PrePersist
    public void prePersist(Object object) {
        // 如果填充字段被分装在一个父类中： Class<?> aClass = object.getClass().getSuperclass();
        Class<?> aClass;
        if (object.getClass().getSuperclass() == AbstractAuditingEntity.class) {
            aClass = object.getClass().getSuperclass();
        } else {
            aClass = object.getClass();
        }
        try {
            // 填充创建用户Id
            fillAppId(object, aClass, APP_ID);
            // 填充创建用户
            fillCreateUser(object, aClass, CREATED_BY);
            // 填充更新用户id
            fillUpdateUser(object, aClass, LAST_MODIFIED_BY);
            // 填充创建时间
            fillCreateTime(object, aClass, CREATED_DATE);
            // 填充更新时间
            fillUpdateTime(object, aClass, LAST_MODIFIED_DATE);
        } catch (Exception e) {
            log.error("jpa 新增时自动填充属性时出现错误：{}", e.getMessage());
        }
    }

    /**
     * 更新数据时，填充更新人和更新时间
     */
    @PreUpdate
    public void preUpdate(Object object) {
        // 如果填充字段被分装在一个父类中： Class<?> aClass = object.getClass().getSuperclass();
        Class<?> aClass;
        if (object.getClass().getSuperclass() == AbstractAuditingEntity.class) {
            aClass = object.getClass().getSuperclass();
        } else {
            aClass = object.getClass();
        }
        try {
            // 填充更新用户Id
            fillUpdateUser(object, aClass, LAST_MODIFIED_BY);
            // 填充更新时间
            fillUpdateTime(object, aClass, LAST_MODIFIED_DATE);
        } catch (Exception e) {
            log.error("jpa 更新时自动填充属性时出现错误：{}", e.getMessage());
        }
    }


    /**
     * 新增数据之后的操作
     */
    @PostPersist
    public void postPersist(Object object)
            throws IllegalArgumentException, IllegalAccessException {
    }

    /**
     * 更新数据之后的操作
     */
    @PostUpdate
    public void postUpdate(Object object)
            throws IllegalArgumentException, IllegalAccessException {
    }

    /**
     * 填充应用Id
     *
     * @param object
     * @param aClass
     * @param propertyName 属性名（对应实体类中的属性）
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    protected void fillAppId(Object object, Class<?> aClass, String propertyName) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException, NoSuchMethodException {
        Field appId = aClass.getDeclaredField(propertyName);
        appId.setAccessible(true);

        // 没有特意设置用户id，就需要设置用户id
        Object appIdValue = appId.get(object);
        if (appIdValue == null) {
            // 获取userId值
            Context context = UserContext.get();
            if (context != null && context.getAppId() != null) {
                appId.set(object, context.getAppId());
            } else {
                // 注意：反射时，不会自动装箱和拆箱
                // 在此处使用当前用户id或默认用户id
                Long id = 0L;
                appId.set(object, id);
            }
        }
    }

    /**
     * 填充创建用户id
     *
     * @param object
     * @param aClass
     * @param propertyName 属性名（对应实体类中的属性）
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    protected void fillCreateUser(Object object, Class<?> aClass, String propertyName) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException, NoSuchMethodException {
        Field createUserId = aClass.getDeclaredField(propertyName);
        createUserId.setAccessible(true);

        // 没有特意设置用户id，就需要设置用户id
        Object userIdValue = createUserId.get(object);
        if (userIdValue == null) {
            // 获取userId值
            Context context = UserContext.get();
            if (context != null && context.getUserId() != null) {
                createUserId.set(object, context.getUserId());
            } else {
                // 注意：反射时，不会自动装箱和拆箱
                // 在此处使用当前用户id或默认用户id
                Long id = 0L;
                createUserId.set(object, id);
            }
        }
    }

    /**
     * 填充更新用户id
     *
     * @param object
     * @param aClass
     * @param propertyName 属性名（对应实体类中的属性）
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    protected void fillUpdateUser(Object object, Class<?> aClass, String propertyName) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        Field updateUserId = aClass.getDeclaredField(propertyName);
        updateUserId.setAccessible(true);

        Object userIdValue = updateUserId.get(object);
        if (userIdValue == null) {
            // 获取userId值
            Context context = UserContext.get();
            if (context != null && context.getUserId() != null) {
                // 在此处使用当前用户id或默认用户id
                updateUserId.set(object, context.getUserId());
            } else {
                // 在此处使用当前用户id或默认用户id
                Long id = 0L;
                updateUserId.set(object, id);
            }
        }

    }

    /**
     * 填充创建时间
     *
     * @param object
     * @param aClass
     * @param propertyName 属性名（对应实体类中的属性）
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    protected void fillCreateTime(Object object, Class<?> aClass, String propertyName) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        Field createTime = aClass.getDeclaredField(propertyName);
        createTime.setAccessible(true);
        // 获取time值
        Object createdTimeValue = createTime.get(object);
        if(createdTimeValue == null) {
            // 使用当前时间进行填充
            createTime.set(object, new Date());
        }
    }

    /**
     * 填充更新时间
     *
     * @param object
     * @param aClass
     * @param propertyName 属性名（对应实体类中的属性）
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    protected void fillUpdateTime(Object object, Class<?> aClass, String propertyName) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        Field updateTime = aClass.getDeclaredField(propertyName);
        updateTime.setAccessible(true);
        updateTime.set(object, new Date());
    }


}
