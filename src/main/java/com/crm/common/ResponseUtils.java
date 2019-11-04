package com.crm.common;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.crm.model.entity.TbPath;
import com.crm.service.path.PathService;

@Component
public class ResponseUtils {

	@Autowired
	private AutowireCapableBeanFactory autowireCapableBeanFactory;

	private final String QUERY_SUCCESS = "查詢成功!";
	private final String INSERT_SUCCESS = "新增成功!";
	private final String UPDATE_SUCCESS = "更新成功!";
	private final String DELETE_SUCCESS = "刪除成功!";

	//回傳驗證
	public ResponseEntity<Result<?>> valid(BindingResult br) {
		StringBuilder sb = new StringBuilder();
		for (ObjectError index : br.getAllErrors()) {
			sb.append(index.getDefaultMessage() + "<br />");
		}
		return ResponseEntity.ok(new Result<String>(HttpStatus.valid, sb.toString()));
	}

	//新刪修共用方法
	public ResponseEntity<Result<?>> resultModify(String message, Object service, String func, Object form,
			BindingResult br) throws Exception {
		if (br != null) {
			if (br.hasErrors()) {
				return valid(br);
			}
		}
		Class<?> clazz = Class.forName(service.getClass().getName());
		Object classInit = clazz.newInstance();
		autowireCapableBeanFactory.autowireBean(classInit);
		Method method = clazz.getMethod(func, form.getClass());

		Object[] parameters = new Object[1];
		parameters[0] = form;
		method.invoke(classInit, parameters);

		return ResponseEntity.ok(new Result<String>(HttpStatus.ok, message));
	}

	//回傳list結果的共用方法
	public ResponseEntity<Result<?>>  resultQueryList(String message, Object service, String func, Object form,
			BindingResult br) throws Exception {
		if (br != null) {
			if (br.hasErrors()) {
				return valid(br);
			}
		}
		Class<?> clazz = Class.forName(service.getClass().getName());
		Object classInit = clazz.newInstance();
		autowireCapableBeanFactory.autowireBean(classInit);
		List<?> lists = null;
		if(form!=null) {
			Method method = clazz.getMethod(func, form.getClass());
			Object[] parameters = new Object[1];
			parameters[0] = form;
			lists = (List<?>) method.invoke(classInit, parameters);
		}else {
			Method method = clazz.getMethod(func);
			lists = (List<?>) method.invoke(classInit);
		}
		return ResponseEntity.ok(new Result<List<?>>(HttpStatus.ok, message, lists));
	}
	
	//回傳STRING結果的共用方法
	public ResponseEntity<Result<?>>  resultQueryStr(String message, Object service, String func, Object form,
			BindingResult br) throws Exception {
		if (br != null) {
			if (br.hasErrors()) {
				return valid(br);
			}
		}
		Class<?> clazz = Class.forName(service.getClass().getName());
		Object classInit = clazz.newInstance();
		autowireCapableBeanFactory.autowireBean(classInit);
		String str = null;
		if(form!=null) {
			Method method = clazz.getMethod(func, form.getClass());
			Object[] parameters = new Object[1];
			parameters[0] = form;
			str = String.valueOf(method.invoke(classInit));
		}else {
			Method method = clazz.getMethod(func);
			str = String.valueOf(method.invoke(classInit));
		}
		return ResponseEntity.ok(new Result<String>(HttpStatus.ok, message, str));
	}
	
	/*
	 * Object service 要執行的class
	 * String func 要執行的方法名稱
	 * Object form 要帶入方法的參數
	 * BindingResult br 驗證訊息物件
	 * */
	public ResponseEntity<Result<?>> save(Object service, String func, Object form, BindingResult br)
			throws Exception {
		return resultModify(INSERT_SUCCESS, service, func, form, br);
	}

	/*
	 * Object service 要執行的class
	 * String func 要執行的方法名稱
	 * Object form 要帶入方法的參數
	 * BindingResult br 驗證訊息物件
	 * */
	public ResponseEntity<Result<?>> update(Object service, String func, Object form, BindingResult br)
			throws Exception {
		return resultModify(UPDATE_SUCCESS,service,func,form,br);	
	}

	public ResponseEntity<Result<?>> update(Object service, String func, Object form)
			throws Exception {
		return resultModify(UPDATE_SUCCESS,service,func,form,null);	
	}
	
	/*
	 * Object service 要執行的class
	 * String func 要執行的方法名稱
	 * Object form 要帶入方法的參數
	 * */
	public ResponseEntity<Result<?>> delete(Object service, String func, Object form) throws Exception {
		return resultModify(DELETE_SUCCESS,service,func,form,null);
	}

	/*
	 * Object service 要執行的class
	 * String func 要執行的方法名稱
	 * Object form 要帶入方法的參數
	 * BindingResult br 驗證訊息物件
	 * */
	public ResponseEntity<Result<?>> delete(Object service, String func, Object form, BindingResult br)
			throws Exception {
		return resultModify(DELETE_SUCCESS,service,func,form,br);
	}

	/*
	 * Object service 要執行的class
	 * String func 要執行的方法名稱
	 * */
	public ResponseEntity<Result<?>> query(Object service, String func) throws Exception {
		return resultQueryList(QUERY_SUCCESS,service,func,null,null);
	}

	/*
	 * Object service 要執行的class
	 * String func 要執行的方法名稱
	 * Object form 要帶入方法的參數
	 * */
	public ResponseEntity<Result<?>> query(Object service, String func, Object form) throws Exception {
		return resultQueryList(QUERY_SUCCESS,service,func,form,null);
	}


	
	
	public ResponseEntity<Result<?>> queryStr(Object service, String func) throws Exception {
		return resultQueryStr(QUERY_SUCCESS,service,func,null,null);
	}

	public ResponseEntity<Result<?>> queryStr(Object service, String func, Object form) throws Exception {
		return resultQueryStr(QUERY_SUCCESS,service,func,form,null);
	}

	

}
