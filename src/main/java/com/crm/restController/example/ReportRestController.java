package com.crm.restController.example;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Clob;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.common.ReportType;
import com.crm.service.userProfile.UserProfileService;
import com.crm.utils.ClobToString;
import com.crm.utils.ReportUtils;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/example/report")
@Slf4j
public class ReportRestController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private UserProfileService userProfileService;

	@PostMapping
	public void report() throws JRException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> lists = userProfileService.getList(map);

		for (Map<String, Object> row : lists) {
			for (String index : row.keySet()) {
				if (row.get(index) instanceof Clob) {
					String clob = ClobToString.convert((Clob) (row.get(index)));
					clob = clob.replace(clob.split(",")[0], "");
					byte[] name = Base64.getEncoder().encode(clob.getBytes());
			        byte[] decodedString = Base64.getDecoder().decode(new String(name).getBytes());
					InputStream stream = new ByteArrayInputStream(decodedString);
					row.put(index, stream);
				}
			}
		}

		ReportUtils ru = new ReportUtils();
		ru.setResponse(response);
		ru.setFields(lists);
		ru.setJrxmlName("userProfile");
		ru.setReportName("使用者資料");
		ru.setReportType(ReportType.PDF.toString());
		ru.genReport();
	}

}
