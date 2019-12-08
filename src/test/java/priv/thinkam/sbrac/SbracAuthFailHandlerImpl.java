package priv.thinkam.sbrac;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import priv.thinkam.sbrac.core.SbracAuthFailHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author thinkam
 * @date 2019/12/8 21:45
 */
@Component
@Slf4j
public class SbracAuthFailHandlerImpl implements SbracAuthFailHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write("{\"code\": \"-1\", \"msg\":\"没有权限，请联系开发人员\"}");
            writer.flush();
        } catch (Exception e) {
            log.error("系统内部错误", e);
        }
    }

}
