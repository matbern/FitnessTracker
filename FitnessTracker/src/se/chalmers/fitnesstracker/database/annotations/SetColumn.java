package se.chalmers.fitnesstracker.database.annotations;

import se.chalmers.fitnesstracker.database.enums.Type;

import java.lang.annotation.*;
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SetColumn {
String name();
Type type(); 
}
