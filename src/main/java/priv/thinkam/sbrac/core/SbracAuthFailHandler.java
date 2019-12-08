package priv.thinkam.sbrac.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * sbrac权限校验不通过处理器接口
 *
 * @author thinkam
 * @date 2019/12/8 20:58
 */
public interface SbracAuthFailHandler {
    /**
     * 处理权限校验不通过
     *
     * @param request  request
     * @param response response
     */
    void handle(HttpServletRequest request, HttpServletResponse response);
}
