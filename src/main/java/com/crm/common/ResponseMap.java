package com.crm.common;

import java.sql.Clob;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.crm.utils.ClobToString;

public class ResponseMap {
	
	public static ResponseEntity<Result<List<Map<String,Object>>>> getResult(List<Map<String,Object>> lists){
		
		Result<List<Map<String,Object>>> result = new Result<List<Map<String,Object>>> ();
		
		if(null==lists) {
			result.setStatus(HttpStatus.empty);
			result.setMessage("查無資料!!!");
		}else {
			
			for(Map<String,Object> row : lists ) {
				for(String index : row.keySet() ) {
					if( row.get(index) instanceof Clob ) {
						String clob = ClobToString.convert((Clob)(row.get(index)));
						row.put(index, clob);
					}
				}
			}
			result.setData(lists);
			result.setStatus(HttpStatus.ok);
			result.setMessage("查詢成功!");
		}
		
		
		
		return ResponseEntity.ok(result);
	}
	
}
