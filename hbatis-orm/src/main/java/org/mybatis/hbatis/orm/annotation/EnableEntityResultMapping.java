package org.mybatis.hbatis.orm.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 仅Hbatis有效
 * @className: EntityResultMapping  
 * @description: 注解方法是否有响应 
 * @author Administrator  | www.integriti.cn
 * @date 2018年6月8日  
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableEntityResultMapping {
	
}
