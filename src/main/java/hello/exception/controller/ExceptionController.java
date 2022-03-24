package hello.exception.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

@Controller
public class ExceptionController {


    @GetMapping("/error/-ex")
    public void errorEx() {
        throw new RuntimeException("예외 발생!");
    }

    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "4040 오류!");
    }

}
