package hospital.vital.api.infra.exceptions;
import hospital.vital.api.domain.ValidacaoExecepition;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Breno
 */
@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao:: new).toList());
    }

    private record DadosErroValidacao(String campo, String mensagem){
        public DadosErroValidacao (FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

    @ExceptionHandler(ValidacaoExecepition.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoExecepition ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
