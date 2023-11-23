package com.FoodDelivery.api.exceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import com.FoodDelivery.domain.exceptions.NegocioException;
import com.FoodDelivery.domain.exceptions.ResourceEmUsoException;
import com.FoodDelivery.domain.exceptions.ResourceExistenteException;
import com.FoodDelivery.domain.exceptions.ResourceNaoExistenteException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class Handler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL
            = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se "
            + "o problema persistir, entre em contato com o administrador do sistema.";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        HttpStatus httpStatus = (HttpStatus) status;
        String detail = "Dados inválidos.";

        List<Problem.FieldError> fieldErrors = ex.getFieldErrors().stream()
                .map(fieldError -> {
                    String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
                    return fieldErrorBuilder(fieldError.getField(), message).build();
                })
                .toList();

        Problem problem = problemBuilder(httpStatus, problemType, detail)
                .userMessage("Dados inválidos.")
                .fieldErrors(fieldErrors)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
        String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

        ex.printStackTrace();

        Problem problem = problemBuilder(status, problemType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        HttpStatus httpStatus = (HttpStatus) status;
        String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.",
                ex.getRequestURL());

        Problem problem = problemBuilder(httpStatus, problemType, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpStatus httpStatus = (HttpStatus) status;

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, httpStatus, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        Problem problem = problemBuilder(status, problemType, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        HttpStatus httpStatus = (HttpStatus) status;

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, httpStatus, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, httpStatus, request);
        }

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

        Problem problem = problemBuilder(httpStatus, problemType, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", path);

        Problem problem = problemBuilder(status, problemType, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
            HttpMessageNotWritableException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        return handleExceptionInternal(ex, null, headers, status, request);
    }

    @ExceptionHandler(ResourceNaoExistenteException.class)
    public ResponseEntity<?> ResourceNaoExistenteHandler(
            ResourceNaoExistenteException ex, WebRequest request
    ) {

        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = ex.getMessage();
        Problem problem = problemBuilder(HttpStatus.NOT_FOUND,
                problemType,
                detail).build();

        return handleExceptionInternal(ex,
                problem,
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
                                                       HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        Problem problem = problemBuilder(status, problemType, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(ResourceExistenteException.class)
    public ResponseEntity<?> ResourceExistenteHandler(
            ResourceExistenteException ex, WebRequest request
    ) {
        ProblemType problemType = ProblemType.ENTIDADE_JA_CADASTRADA;
        String detail = ex.getMessage();
        Problem problem = problemBuilder(HttpStatus.CONFLICT,
                problemType,
                detail).build();

        return handleExceptionInternal(ex,
                problem,
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request);
    }

    @ExceptionHandler(ResourceEmUsoException.class)
    public ResponseEntity<?> ResourceEmUsoHandler(
            ResourceEmUsoException ex, WebRequest request
    ) {
        ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
        String detail = ex.getMessage();
        Problem problem = problemBuilder(HttpStatus.CONFLICT,
                problemType,
                detail).build();

        return handleExceptionInternal(ex,
                problem,
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> NegocioExceptionHandler(
            NegocioException ex, WebRequest request
    ) {
        ProblemType problemType = ProblemType.ERRO_NEGOCIO;
        String detail = ex.getMessage();
        Problem problem = problemBuilder(HttpStatus.BAD_REQUEST,
                problemType,
                detail).build();

        return handleExceptionInternal(ex,
                ex.getMessage(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        HttpStatus status = (HttpStatus) statusCode;

        if (body == null) {
            body = new Problem.BuilderClass()
                    .status(status.value())
                    .title(status.getReasonPhrase())
                    .build();
        } else if (body instanceof String) {
            body = new Problem.BuilderClass()
                    .status(status.value())
                    .title((String) body)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private Problem.BuilderClass problemBuilder(HttpStatus status, ProblemType problemType,
                                            String detail) {

        return new Problem.BuilderClass()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }

    private Problem.FieldError.FieldErrorBuilder fieldErrorBuilder (String field, String userMessage) {
        return new Problem.FieldError.FieldErrorBuilder()
                .field(field)
                .userMessage(userMessage);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }
}
