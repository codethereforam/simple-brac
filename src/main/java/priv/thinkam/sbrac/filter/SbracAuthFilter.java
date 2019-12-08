package priv.thinkam.sbrac.filter;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import priv.thinkam.sbrac.core.SbracAuthContext;
import priv.thinkam.sbrac.core.SbracAuthFailHandler;
import priv.thinkam.sbrac.core.SbracRequestRole;
import priv.thinkam.sbrac.core.SbracUserRole;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Sbrac权限过滤器
 *
 * @author thinkam
 * @date 2019/12/8
 */
@Slf4j
@Order(1)
@WebFilter("/*")
@Component
class SbracAuthFilter extends OncePerRequestFilter {
    /**
     * map {username: role names}在缓存中的key
     */
    public static final String SBRAC_USERNAME_ROLE_NAMES_MAP_CACHE_KEY = "sbrac-username-role-names-map";
    /**
     * map {http url + "$" + http method: role names}再缓存中的key
     */
    public static final String SBRAC_REQUEST_ROLE_NAMES_MAP_CACHE_KEY = "sbrac-request-role-names-map";
    /**
     * context path
     */
    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * 不经过sbrac权限校验的url
     */
    @Value("${sbrac.non-validate-urls}")
    private String[] nonValidateUrls;

    private Set<String> nonValidateUrlSet;

    /**
     * 不经过sbrac权限校验的角色
     */
    @Value("${sbrac.non-validate-roles}")
    private String[] nonValidateRoles;

    private Set<String> nonValidateRoleSet;

    @PostConstruct
    private void init() {
        nonValidateUrlSet = (nonValidateUrls.length == 0) ? new HashSet<>() : Collections.unmodifiableSet(
                Arrays.stream(nonValidateUrls).collect(Collectors.toSet()));
        nonValidateRoleSet = (nonValidateRoles.length == 0) ? new HashSet<>() : Collections.unmodifiableSet(
                Arrays.stream(nonValidateRoles).collect(Collectors.toSet()));
    }

    private final SbracAuthFailHandler sbracAuthFailHandler;
    private final SbracAuthContext sbracAuthContext;

    public SbracAuthFilter(SbracAuthContext sbracAuthContext, @Nullable SbracAuthFailHandler sbracAuthFailHandler) {
        this.sbracAuthContext = sbracAuthContext;
        this.sbracAuthFailHandler = sbracAuthFailHandler;
    }


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String noContextPathRequestUrl = this.getNoContextPathRequestUrl(request);
        for (String nonValidateUrl : nonValidateUrlSet) {
            if (noContextPathRequestUrl.startsWith(nonValidateUrl)) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        if (this.validateAuth(request)) {
            filterChain.doFilter(request, response);
        } else {
            if (sbracAuthFailHandler != null) {
                sbracAuthFailHandler.handle(request, response);
            }
        }
    }

    private boolean validateAuth(HttpServletRequest request) {
        // 获取当前用户名
        String currentUsername = sbracAuthContext.getCurrentUsername(request);
        // 当前用户的角色
        List<String> currentRoleNames = this.getUsernameRoleNamesMap(request).get(currentUsername);
        // 特殊角色不校验权限
        if (!Collections.disjoint(nonValidateRoleSet, currentRoleNames)) {
            return true;
        }
        List<String> permitRoles = this.getRequestRoleNamesMap(request).get(
                this.getJoinedHttpUrlAndMethod(this.getNoContextPathRequestUrl(request), request.getMethod()));
        // todo: url支持正则表达式匹配
        // currentRoleNames和permitRoles有共同元素返回true，表示校验权限通过
        return permitRoles != null && !Collections.disjoint(currentRoleNames, permitRoles);
    }

    @NotNull
    private String getNoContextPathRequestUrl(HttpServletRequest request) {
        return request.getRequestURI().replaceFirst(contextPath, "");
    }

    /**
     * 获取{@code map {username: role names}}。先从session中获取，获取不到去调用sbracAuthContext中的方法
     * todo: 缓存到内存还是其他地方
     *
     * @param request request
     * @return {@code map {username: role names}}
     */
    @NotNull
    private Map<String, List<String>> getUsernameRoleNamesMap(HttpServletRequest request) {
        Map<String, List<String>> usernameRoleNamesMap = (Map<String, List<String>>) request.getSession()
                .getAttribute(SBRAC_USERNAME_ROLE_NAMES_MAP_CACHE_KEY);
        if (usernameRoleNamesMap == null) {
            usernameRoleNamesMap = sbracAuthContext.listUserRole().stream().collect(Collectors.toMap(SbracUserRole::getUsername, SbracUserRole::getRoleNames));
            request.getSession().setAttribute(SBRAC_USERNAME_ROLE_NAMES_MAP_CACHE_KEY, usernameRoleNamesMap);
        }
        return usernameRoleNamesMap;
    }

    /**
     * 获取{@code map {http url + "$" + http method: role names}}。先从session中获取，获取不到去调用sbracAuthContext中的方法
     * todo: 缓存到内存还是其他地方
     *
     * @param request request
     * @return {@code map {http url + "$" + http method: role names}}
     */
    @NotNull
    private Map<String, List<String>> getRequestRoleNamesMap(HttpServletRequest request) {
        Map<String, List<String>> requestRoleNamesMap = (Map<String, List<String>>) request.getSession()
                .getAttribute(SBRAC_REQUEST_ROLE_NAMES_MAP_CACHE_KEY);
        if (requestRoleNamesMap == null) {
            requestRoleNamesMap = sbracAuthContext.listRequestRoles().stream().collect(Collectors.toMap(
                    rr -> this.getJoinedHttpUrlAndMethod(rr.getRequestUrl(), rr.getRequestMethod()),
                    SbracRequestRole::getRoleNames));
            request.getSession().setAttribute(SBRAC_REQUEST_ROLE_NAMES_MAP_CACHE_KEY, requestRoleNamesMap);
        }
        return requestRoleNamesMap;
    }

    private String getJoinedHttpUrlAndMethod(String httpUrl, String httpMethod) {
        return httpUrl + "$" + httpMethod;
    }
}
