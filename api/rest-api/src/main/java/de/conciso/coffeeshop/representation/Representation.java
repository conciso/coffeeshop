package de.conciso.coffeeshop.representation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.immutables.value.Value;

@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@Value.Style(
    jdkOnly = true,
    defaultAsDefault = true,
    allParameters = true,
    depluralize = true,
    visibility = Value.Style.ImplementationVisibility.PUBLIC,
    get = { "is*", "get*"}
)
public @interface Representation {
}
