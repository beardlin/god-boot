
package net.lantrack.framework.common.exception;

import net.lantrack.framework.common.entity.ReturnEntity;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
/**
 *@Description 全球异常处理
 *@Author dahuzi
 *@Date 2019/10/28  21:43
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(GlobalException.class)
	public ReturnEntity handleGlobalException(GlobalException e){
		ReturnEntity r = new ReturnEntity();
		r.err(e.getMsg());
		return r;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ReturnEntity handlerNoFoundException(Exception e) {
		if(logger.isErrorEnabled()){
			logger.error(e.getMessage(), e);
		}
		ReturnEntity r = new ReturnEntity();
		return r.err(404, "路径不存在，请检查路径是否正确");
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public ReturnEntity handleDuplicateKeyException(DuplicateKeyException e){
		if(logger.isErrorEnabled()){
			logger.error(e.getMessage(), e);
		}
		ReturnEntity r = new ReturnEntity();
		return r.err("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public ReturnEntity handleAuthorizationException(AuthorizationException e){
		if(logger.isErrorEnabled()){
			logger.error(e.getMessage(), e);
		}
		ReturnEntity r = new ReturnEntity();
		return r.err("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(Exception.class)
	public ReturnEntity handleException(Exception e){
		if(logger.isErrorEnabled()){
			logger.error(e.getMessage(), e);
		}
		ReturnEntity r = new ReturnEntity();
		return r.err();
	}
}
