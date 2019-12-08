package priv.thinkam.sbrac.config;

import org.springframework.context.annotation.Import;
import priv.thinkam.sbrac.core.SbracAuthContext;
import priv.thinkam.sbrac.core.SbracAuthFailHandler;

import java.lang.annotation.*;

/**
 * 使用该注解启用simple-brac
 * <p>
 * 用法：
 * <ol>
 * <li>配置类加{@link EnableSbrac}注解</li>
 * <li>实现{@link SbracAuthContext}接口，实现其中的方法，并注册到spring bean容器中</li>
 * <li>实现{@link SbracAuthFailHandler}处理校验失败的情况，比如返回JSON或HTML，并注册到spring bean容器中。也可以不实现，那鉴权就会返回空白页</li>
 * <li>配置文件配置"sbrac.non-validate-urls"不经过brac权限校验的url，用逗号隔开</li>
 * </ol>
 *
 * @author thinkam
 * @date 2019/12/8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SbracConfig.class)
public @interface EnableSbrac {

}