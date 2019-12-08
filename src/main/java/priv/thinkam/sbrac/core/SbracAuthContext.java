package priv.thinkam.sbrac.core;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * sbrac权限上下文接口
 *
 * @author thinkam
 * @date 2019/12/8
 */
public interface SbracAuthContext {
    /**
     * 获取当前用户名。可以从session、token、redis等地方获取
     *
     * @param request request
     * @return 当前用户名
     */
    @NotNull
    String getCurrentUsername(@NotNull HttpServletRequest request);

    /**
     * 获取“请求url，请求http方法，角色信息”集合。可以从数据库、配置文件等地方获取。
     * <p>
     * 调用者无需考虑缓存，该方法只会别simple-brac调用一次，而后会缓存起来
     *
     * @return “请求url，请求http方法，角色信息”集合
     */
    @NotNull
    List<SbracRequestRole> listRequestRoles();

    /**
     * 获取”用户-角色信息“集合。可以从数据库、配置文件等地方获取
     * <p>
     * 调用者无需考虑缓存，该方法只会别simple-brac调用一次，而后会缓存起来
     *
     * @return ”用户-角色信息“集合
     */
    @NotNull
    List<SbracUserRole> listUserRole();
}
