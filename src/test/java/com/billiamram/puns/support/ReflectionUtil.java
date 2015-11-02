package com.billiamram.puns.support;

import java.lang.reflect.Field;

public class ReflectionUtil {
  public static void setField(Object target, String name, Object value) throws NoSuchFieldException, IllegalAccessException {
    Field field = target.getClass().getDeclaredField(name);
    field.setAccessible(true);

    field.set(target, value);
  }
}