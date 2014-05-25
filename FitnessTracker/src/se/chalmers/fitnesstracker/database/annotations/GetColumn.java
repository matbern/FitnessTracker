package se.chalmers.fitnesstracker.database.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import se.chalmers.fitnesstracker.database.enums.Type;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GetColumn {
	String name();

	Type type();
	boolean key();

}
