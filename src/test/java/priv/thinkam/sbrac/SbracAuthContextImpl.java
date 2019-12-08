package priv.thinkam.sbrac;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import priv.thinkam.sbrac.core.SbracAuthContext;
import priv.thinkam.sbrac.core.SbracRequestRole;
import priv.thinkam.sbrac.core.SbracUserRole;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author thinkam
 * @date 2019/12/8 21:16
 */
@Component
public class SbracAuthContextImpl implements SbracAuthContext {
    @Override
    public @NotNull String getCurrentUsername(@NotNull HttpServletRequest request) {
        return "u1";
    }

    @Override
    public @NotNull List<SbracRequestRole> listRequestRoles() {
        List<SbracRequestRole> sbracRequestRoleList = new ArrayList<>();
        sbracRequestRoleList.add(new SbracRequestRole("/t1", "GET", Arrays.asList("r1")));
        sbracRequestRoleList.add(new SbracRequestRole("/t2", "GET", Arrays.asList("r3")));
        return sbracRequestRoleList;
    }

    @Override
    public @NotNull List<SbracUserRole> listUserRole() {
        List<SbracUserRole> sbracUserRoleList = new ArrayList<>();
        sbracUserRoleList.add(new SbracUserRole("u1", Arrays.asList("r1", "r2")));
        return sbracUserRoleList;
    }
}
