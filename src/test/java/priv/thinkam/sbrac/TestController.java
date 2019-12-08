package priv.thinkam.sbrac;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thinkam
 * @date 2019/12/8 21:12
 */
@Slf4j
@RestController
@RequestMapping
public class TestController {

    @GetMapping("/t1")
    public String t1Get() {
        return "t1 get";
    }

    @PostMapping("/t1")
    public String t1POst() {
        return "t1 post";
    }

    @GetMapping("/t2")
    public String t2() {
        return "t2";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public String register() {
        return "register";
    }
}
