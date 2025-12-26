@JdbcTypeRegistration(value = UUIDWrapperJdbcType.class)


@JavaTypeRegistration(descriptorClass = UserIdJavaType.class, javaType = UserId.class)

package com.mbd.auth;

import com.mbd.core.hibernate.UUIDWrapperJdbcType;
import com.mbd.auth.user.domain.UserId;
import com.mbd.auth.user.domain.UserId.UserIdJavaType;
import org.hibernate.annotations.JavaTypeRegistration;
import org.hibernate.annotations.JdbcTypeRegistration;
