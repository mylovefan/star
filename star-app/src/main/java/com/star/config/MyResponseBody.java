package com.star.config;

import com.star.commen.dto.vo.ResultVo;
import com.star.common.ErrorCodeEnum;
import com.star.common.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.net.URI;

/**
 * 捕获请求同一返回格式
 * @author zhangrc
 * @create 2020-11-29
 */
@Slf4j
@RestControllerAdvice
public class MyResponseBody implements ResponseBodyAdvice {


    @ExceptionHandler(value = {Exception.class})    //申明捕获那个异常类
    public ResultVo globalExceptionHandler(Exception e) {
        log.error("系统错误：",e);
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(ErrorCodeEnum.SYSTOM_ERROR.getCode());
        resultVo.setMessage("系统错误");
        resultVo.setTime(System.currentTimeMillis());
        resultVo.setSuccess(false);
        return resultVo;
    }


    @ExceptionHandler(value = {ServiceException.class})
    public ResultVo BusinessExceptionHandler(ServiceException e) {
        log.error("返回异常结果：",e);
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(e.getCode());
        resultVo.setMessage(e.getMessage());
        resultVo.setTime(System.currentTimeMillis());
        resultVo.setSuccess(false);
        return resultVo;
    }




    @Override
    public Object beforeBodyWrite(Object resBody, MethodParameter arg1, MediaType arg2, Class arg3, ServerHttpRequest req,
                                  ServerHttpResponse res) {
//        resBody就是controller方法中返回的值，对其进行修改后再return就可以了\
        URI uri = req.getURI();
        if (uri.toString().contains("swagge") || uri.toString().contains("error") || uri.toString().contains("api-docs")){
            return resBody;
        }
        ResultVo returnVO = new ResultVo();
        returnVO.setTime(System.currentTimeMillis());
        log.info("请求接口uri："+uri);
        log.info("返回结果：",resBody);
        try {
            //如果方法的执行结果是ReturnVO，则将该对象直接返回
            if (resBody instanceof ResultVo) {
                returnVO = (ResultVo) resBody;
            } else {
                //否则，就要封装到ReturnVO的data中
                returnVO.setData(resBody);
            }
        }  catch (Exception e) {
            log.error("系统异常",e);
            //如果出现了异常，调用异常处理方法将错误信息封装到ReturnVO中并返回
        }
        return returnVO;
//        return resBody;
    }

    @Override
    public boolean supports(MethodParameter arg0, Class arg1) {
        //这里直接返回true,表示对任何handler的responsebody都调用beforeBodyWrite方法
        return true;
    }
}
