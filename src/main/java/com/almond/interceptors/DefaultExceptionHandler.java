package com.almond.interceptors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.almond.common.data.ResponseResult;
import com.almond.common.domain.CommonResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<CommonResponse> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		// If the exception is annotated with @ResponseStatus rethrow it and let 
		// the framework handle it - like the OrderNotFoundException example 
		// at the start of this post. 
		// AnnotationUtils is a Spring Framework utility class. 
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;

    	CommonResponse res = new CommonResponse();
    	res.setResult(ResponseResult.ERROR);
    	res.setMessage("문제가 발생하였습니다.");
    	return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
