package se.chalmers.fitnesstracker.database.annotations;

import java.lang.annotation.*;

@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
 String name();
}
