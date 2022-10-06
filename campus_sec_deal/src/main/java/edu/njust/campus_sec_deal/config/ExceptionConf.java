/**
 * @Class: ExceptionConf
 * @Date: 2022/9/20
 * @Project: campus_sec_deal
 * @Author: Colyn
 * @Builder: IntelliJ IDEA
 */
package edu.njust.campus_sec_deal.config;

import edu.njust.campus_sec_deal.utils.JsonResultUtil;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author master
 */
@RestControllerAdvice
public class ExceptionConf {

    /**
     * 参数为实体类
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JsonResultUtil<?> handleValidException(MethodArgumentNotValidException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        return new JsonResultUtil<>(ReturnCodeConf.INFO_ERR, objectError.getDefaultMessage());
    }

    /**
     * 参数为单个参数或多个参数
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public JsonResultUtil<?> handleConstraintViolationException(ConstraintViolationException e) {
        // 从异常对象中拿到ObjectError对象
        return new JsonResultUtil<>(ReturnCodeConf.INFO_ERR, e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()).get(0));
    }

}

