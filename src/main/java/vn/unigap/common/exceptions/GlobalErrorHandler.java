package vn.unigap.common.exceptions;

import java.util.stream.Collectors;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.MimeType;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.sentry.Sentry;
import io.sentry.SentryEvent;
import io.sentry.SentryLevel;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import vn.unigap.common.error.ErrorCode;
import vn.unigap.common.response.ApiResponse;

/**
 * GlobalErrorHandler is a class that handles global exceptions and provides
 * appropriate error responses. It extends the ResponseEntityExceptionHandler
 * class and is annotated with @ControllerAdvice to handle exceptions globally.
 * The class overrides various methods to handle specific types of exceptions
 * and generate corresponding error responses.
 */
@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {
        @ExceptionHandler(value = ApiException.class)
        public ResponseEntity<?> handleApiException(ApiException e) {
                captureException(e, e.getHttpStatus());
                return responseEntity(e.getErrorCode(), e.getHttpStatus(), e.getMessage());
        }

        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "405", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)))
        @Override
        protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
                String supportedMethods = ex.getSupportedMethods() == null ? null
                                : String.join(",", ex.getSupportedMethods());

                String msg = "Method not supported: %s, only support %s".formatted(ex.getMethod(), supportedMethods);

                captureException(ex, status);
                return responseEntity(ErrorCode.METHOD_NOT_ALLOWED, status, msg);
        }

        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "415", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)))
        @Override
        protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
                String supportedContentTypes = ex.getSupportedMediaTypes().stream().map(MimeType::toString)
                                .collect(Collectors.joining(", "));

                String msg = "MediaType not supported: %s, only support %s".formatted(ex.getContentType(),
                                supportedContentTypes);

                captureException(ex, status);
                return responseEntity(ErrorCode.UNSUPPORTED_MEDIA_TYPE, status, msg);
        }

        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "406", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)))
        @Override
        protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
                String supportedContentTypes = ex.getSupportedMediaTypes().stream().map(MimeType::toString)
                                .collect(Collectors.joining(", "));

                String msg = "MediaType not acceptable: only support %s".formatted(supportedContentTypes);

                captureException(ex, status);
                return responseEntity(ErrorCode.NOT_ACCEPTABLE, status, msg);
        }

        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)))
        @Override
        protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
                        HttpStatusCode status, WebRequest request) {
                ex.printStackTrace();

                String msg = "MissingPathVariable: variable name %s, parameter %s".formatted(ex.getVariableName(),
                                ex.getParameter().getParameterName());

                captureException(ex, status);
                return responseEntity(ErrorCode.INTERNAL_ERR, status, msg);
        }

        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)))
        @Override
        protected ResponseEntity<Object> handleMissingServletRequestParameter(
                        MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status,
                        WebRequest request) {
                String msg = "MissingServletRequestParameter: parameter name %s".formatted(ex.getParameterName());
                captureException(ex, status);
                return responseEntity(ErrorCode.BAD_REQUEST, status, msg);
        }

        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)))
        @Override
        protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
                String msg = "MissingServletRequestPart: request part name %s".formatted(ex.getRequestPartName());
                captureException(ex, status);
                return responseEntity(ErrorCode.BAD_REQUEST, status, msg);
        }

        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)))
        @Override
        protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
                String msg = "ServletRequestBinding: detail message code %s".formatted(ex.getDetailMessageCode());
                captureException(ex, status);
                return responseEntity(ErrorCode.BAD_REQUEST, status, msg);
        }

        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
                String fieldErrors = ex.getFieldErrors().stream()
                                .map(fieldError -> "%s:%s".formatted(fieldError.getObjectName(), fieldError.getField()))
                                .collect(Collectors.joining(","));

                String glObjectErrors = ex.getGlobalErrors().stream().map(ObjectError::getObjectName)
                                .collect(Collectors.joining(","));

                String msg = "MethodArgumentNotValid field errors: %s, global errors: %s".formatted(fieldErrors,
                                glObjectErrors);

                captureException(ex, status);
                return responseEntity(ErrorCode.BAD_REQUEST, status, msg);
        }

        // @ResponseStatus(value = HttpStatus.NOT_FOUND)
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)))
        @Override
        protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                        HttpStatusCode status, WebRequest request) {
                String msg = "NoHandlerFound: method %s, url %s".formatted(ex.getHttpMethod(), ex.getRequestURL());
                captureException(ex, status);
                return responseEntity(ErrorCode.NOT_FOUND, status, msg);
        }

        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "503", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class)))
        @Override
        protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
                ex.printStackTrace();
                captureException(ex, status);
                return responseEntity(ErrorCode.SERVICE_UNAVAILABLE, status, "AsyncRequestTimeout");
        }

        @Override
        protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex, HttpHeaders headers,
                        HttpStatusCode status, WebRequest request) {
                captureException(ex, status);
                return responseEntity(status.value(), status, ex.getDetailMessageCode());
        }

        @Override
        protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
                ex.printStackTrace();
                Class<?> requiredTypeClass = ex.getRequiredType();
                String requiredType = requiredTypeClass == null ? null : requiredTypeClass.getSimpleName();
                String msg = "ConversionNotSupported: required type %s".formatted(requiredType);
                captureException(ex, status);
                return responseEntity(ErrorCode.INTERNAL_ERR, status, msg);
        }

        @Override
        protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                        HttpStatusCode status, WebRequest request) {
                Class<?> requiredTypeClass = ex.getRequiredType();
                String requiredType = requiredTypeClass == null ? null : requiredTypeClass.getSimpleName();
                String msg = "ConversionNotSupported: property %s, required type %s".formatted(ex.getPropertyName(),
                                requiredType);
                captureException(ex, status);
                return responseEntity(ErrorCode.BAD_REQUEST, status, msg);
        }

        @Override
        protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
                captureException(ex, status);
                return responseEntity(ErrorCode.BAD_REQUEST, status, "HttpMessageNotReadable");
        }

        @Override
        protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
                ex.printStackTrace();
                captureException(ex, status);
                return responseEntity(ErrorCode.INTERNAL_ERR, status, "HttpMessageNotWritable");
        }

        // @ExceptionHandler(value = AuthenticationException.class)
        // public ResponseEntity<?> handleAuthException(AuthenticationException e) {
        // // e.printStackTrace();
        // captureException(e, HttpStatus.UNAUTHORIZED);
        // return responseEntity(ErrorCode.UNAUTHORIZED, HttpStatus.UNAUTHORIZED,
        // e.getMessage());
        // }

        // @ExceptionHandler(value = Exception.class)
        // public ResponseEntity<?> handleUnknownException(Exception e) {
        // e.printStackTrace();
        // captureException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        // return responseEntity(ErrorCode.INTERNAL_ERR,
        // HttpStatus.INTERNAL_SERVER_ERROR,
        // HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        // }

        private ResponseEntity<Object> responseEntity(Integer errorCode, HttpStatusCode statusCode, String msg) {
                return new ResponseEntity<>(ApiResponse.builder().errorCode(errorCode).statusCode(statusCode.value())
                                .message(msg).build(), statusCode);
        }

        private void captureException(Exception e, HttpStatusCode status) {
                SentryEvent event = new SentryEvent(e);
                if (status.is5xxServerError()) {
                        event.setLevel(SentryLevel.ERROR);
                } else {
                        event.setLevel(SentryLevel.INFO);
                }
                Sentry.captureEvent(event);
        }
}
