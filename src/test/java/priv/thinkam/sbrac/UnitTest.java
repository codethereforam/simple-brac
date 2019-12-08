package priv.thinkam.sbrac;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author thinkam
 * @date 2019/12/8 21:49
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TestController.class)
public class UnitTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test1() throws Exception {
        SbracAuthContextImpl.username = "u1";
        // 有权限
        this.mockMvc.perform(get("/t1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("t1")));
        this.mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("login")));
        this.mockMvc.perform(post("/register")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("register")));
        // 没有权限
        this.mockMvc.perform(get("/t2")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("没有权限")));
        this.mockMvc.perform(post("/t1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("没有权限")));
    }

    @Test
    public void test2() throws Exception {
        SbracAuthContextImpl.username = "u2";
        // 有权限
        this.mockMvc.perform(get("/t1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("t1")));
        this.mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("login")));
        this.mockMvc.perform(post("/register")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("register")));
        this.mockMvc.perform(get("/t2")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("t2")));
        this.mockMvc.perform(post("/t1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("t1")));
    }

    @Test
    public void test3() throws Exception {
        // 无角色
        SbracAuthContextImpl.username = "u3";
        // 没有权限
        this.mockMvc.perform(get("/t1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("没有权限")));
    }
}
