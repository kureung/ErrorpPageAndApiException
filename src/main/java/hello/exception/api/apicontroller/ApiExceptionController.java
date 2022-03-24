package hello.exception.api.apicontroller;

import hello.exception.api.exception.UserException;
import hello.exception.api.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionController {

    /**
     * @ExceptionHandler 애노테이션은 해당 컨트롤러 클래스 안에서만 작동한다.
     * @ResponseStatus 애노테이션을 통해 HTTP 상태코드를 같이 전달할 수 있다.
     */

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // HTTP 상태코드 지정 가능
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }


    @ExceptionHandler  //  파라미터의 에러 클래스와 같을 때 해당 애노테이션의 속성값인 에러 클래스 생략 가능.
    public ResponseEntity<ErrorResult> userExhHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);

    }

    // 다양한 예외를 한번에 처리 가능
    @ExceptionHandler({RuntimeException.class, IndexOutOfBoundsException.class})
    public ErrorResult ex(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("Double Exception", "중복 처리 가능");

    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }


    @GetMapping("/api/members/{id}")
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
