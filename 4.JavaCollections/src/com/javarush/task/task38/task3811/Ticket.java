package com.javarush.task.task38.task3811;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ua053202 on 01.09.2017.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ticket {
    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    Priority priority() default Priority.MEDIUM;
    String createdBy() default "Amigo";
    String[] tags() default {};
}
