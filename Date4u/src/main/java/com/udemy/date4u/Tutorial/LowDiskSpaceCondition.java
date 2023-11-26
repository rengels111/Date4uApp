package com.udemy.date4u.Tutorial;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.unit.DataSize;

import java.io.File;

public class LowDiskSpaceCondition implements Condition {
    @Override
    public boolean matches( ConditionContext __, AnnotatedTypeMetadata ___ ) {  // Da wir die beiden Parameter nicht
        // brauchen, markieren wir sie provisorisch mit "__" und nutzen sie nicht.
        return DataSize.ofBytes(new File("C:/").getFreeSpace()).toGigabytes() < 10;
    }
}

