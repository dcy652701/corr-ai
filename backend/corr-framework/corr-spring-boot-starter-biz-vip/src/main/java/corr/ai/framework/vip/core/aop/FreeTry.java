package corr.ai.framework.vip.core.aop;

import java.lang.annotation.*;

/**
 * 免费试用的注解
 *
 * @author dongchengye
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface FreeTry {
    int tryCount() default 5;
}
