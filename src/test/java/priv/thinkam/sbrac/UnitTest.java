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
    public void shouldReturnDefaultMessage() throws Exception {
        // 有权限
        this.mockMvc.perform(get("/test/t1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("t1")));
        // 没有权限
        this.mockMvc.perform(get("/test/t2")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("没有权限")));
        this.mockMvc.perform(post("/test/t1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("没有权限")));
    }
}
