package custom.view;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target( { ElementType.FIELD })
public @interface InjectView {
    int value() default -1;
    String tag() default "";
}