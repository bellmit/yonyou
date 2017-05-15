/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.function
*
* @File name : ParameterizedTypeImpl.java
*
* @Author : zhangxianchao
*
* @Date : 2016年3月2日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年3月2日    zhangxianchao    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.function.utils.jsonSerializer;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

/*
*
* @author zhangxianchao
* ParameterizedTypeImpl
* @date 2016年3月2日
*/

public class ParameterizedTypeImpl implements ParameterizedType {

    private Type[]   actualTypeArguments;
    private Class<?> rawType;
    private Type     ownerType;

    public ParameterizedTypeImpl(Class<?> rawType, Type[] actualTypeArguments, Type ownerType){
        this.actualTypeArguments = actualTypeArguments;
        this.rawType = rawType;
        if (ownerType != null) {
            this.ownerType = ownerType;
        } else {
            this.ownerType = rawType.getDeclaringClass();
        }
        validateConstructorArguments();
    }

    private void validateConstructorArguments() {
        TypeVariable<?>[] formals = rawType.getTypeParameters();
        // check correct arity of actual type args
        if (formals.length != actualTypeArguments.length) {
            throw new MalformedParameterizedTypeException();
        }
        for (int i = 0; i < actualTypeArguments.length; i++) {
            // check actuals against formals' bounds
        }
    }

    public Type[] getActualTypeArguments() {
        return actualTypeArguments.clone();
    }

    public Class<?> getRawType() {
        return rawType;
    }

    public Type getOwnerType() {
        return ownerType;
    }

    /*
     * From the JavaDoc for java.lang.reflect.ParameterizedType "Instances of classes that implement this interface must
     * implement an equals() method that equates any two instances that share the same generic type declaration and have
     * equal type parameters."
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof ParameterizedType) {
            // Check that information is equivalent
            ParameterizedType that = (ParameterizedType) o;

            if (this == that) return true;

            Type thatOwner = that.getOwnerType();
            Type thatRawType = that.getRawType();

            return (ownerType == null ? thatOwner == null : ownerType.equals(thatOwner))
                   && (rawType == null ? thatRawType == null : rawType.equals(thatRawType))
                   && Arrays.equals(actualTypeArguments, that.getActualTypeArguments());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(actualTypeArguments) ^ (ownerType == null ? 0 : ownerType.hashCode())
               ^ (rawType == null ? 0 : rawType.hashCode());
    }

    @SuppressWarnings("rawtypes")
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (ownerType != null) {
            if (ownerType instanceof Class) sb.append(((Class) ownerType).getName());
            else sb.append(ownerType.toString());

            sb.append(".");

            if (ownerType instanceof ParameterizedTypeImpl) {
                // Find simple name of nested type by removing the
                // shared prefix with owner.
                sb.append(rawType.getName().replace(((ParameterizedTypeImpl) ownerType).rawType.getName() + "$", ""));
            } else sb.append(rawType.getName());
        } else sb.append(rawType.getName());

        if (actualTypeArguments != null && actualTypeArguments.length > 0) {
            sb.append("<");
            boolean first = true;
            for (Type t : actualTypeArguments) {
                if (!first) sb.append(", ");
                if (t instanceof Class) sb.append(((Class) t).getName());
                else sb.append(t.toString());
                first = false;
            }
            sb.append(">");
        }

        return sb.toString();
    }
}
