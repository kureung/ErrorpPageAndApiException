package hello.exception.api.apicontroller.advice;

import hello.exception.api.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/advice")
public class ApiAdviceController {

    /**
     * 일반 컨트롤러
     * 해당 컨트롤러의 API 예외처리는 ExControllerAdvice 컨트롤러에서 글로벌로 담당한다.
     */

    @GetMapping("/{id}")
    public MemberDto getMember(@PathVariable String id) {
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }

        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }

        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }


    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }

}
