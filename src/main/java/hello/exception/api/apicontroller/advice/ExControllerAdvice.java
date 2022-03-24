package hello.exception.api.apicontroller.advice;

import hello.exception.api.exception.UserException;
import hello.exception.api.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    /**
     * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
     * @ControllerAdvice 는 대상으로 지정된 대상의 여러 컨트롤러에 @ExceptionHandler, @InitBinder 기능을 부여한다.
     *  - @InitBinder : 해당 컨트롤러에 들어오는 요청에 대해 추가적인 설정을 할 때 사용한다
     *               : 해당 컨트롤러의 모든 요청 전에 @InitBinder 를 선언한 메서드가 실행된다.
     *
     * @ControllerAdvice 는 대상을 지정하지 않으면 모든 컨트롤러에 적용된다. (글로벌 적용)
     *
     *
     *
     * * 대상 지정 방법 *
     * @RestControllerAdvice(annotations = RestController.class)
     *   - @RestController 애노테이션이 붙은 컨트롤러만 적용한다.
     *
     * @Controller("org.example.controllers)
     *  - 해당 패키지에 있는 모든 컨트롤러만 적용한다.
     *  - 종종 쓰인다.
     *
     * @ControllerAdvice(assignableType = {ControllerInterface.class, AbstractController.class})
     *  - 해당 클래스 또는 자식 클래스나 구현 클래스까지 적용한다.
     *  - 한 꺼번에 여러 클래스 지정 가능.
     *
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

}
