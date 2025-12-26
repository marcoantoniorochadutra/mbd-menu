package com.mbd.core.hibernate;

import org.hibernate.type.descriptor.jdbc.UUIDJdbcType;


public class UUIDWrapperJdbcType extends UUIDJdbcType {

    public static final UUIDWrapperJdbcType INSTANCE = new UUIDWrapperJdbcType();
}
