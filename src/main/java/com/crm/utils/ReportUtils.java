package com.crm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crm.common.ReportType;

import lombok.NoArgsConstructor;
import lombok.Setter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Component
@Setter
@NoArgsConstructor
public class ReportUtils {

	private HttpServletResponse response;
	private String reportName;
	private String reportType;
	private String jrxmlName;
	private String jrxmlPath = "/report/";
	private List<?> fields;
	private Map<String, Object> params;
	private String[] sheetNames;
	
	
	public void genReport() throws JRException, IOException {

		JRDataSource jrDataSource = new JRBeanCollectionDataSource(fields);
		InputStream jasperStream = this.getClass().getResourceAsStream(jrxmlPath + jrxmlName+".jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jrDataSource);
		if (reportType.equals(ReportType.PDF.toString())) {
			this.pdf(jasperPrint);
		} else if (reportType.equals(ReportType.XLSX.toString())) {
			this.xlsx(jasperPrint);
		} else if (reportType.equals(ReportType.CSV.toString())) {
			this.csv(jasperPrint);
		} else if (reportType.equals(ReportType.WORD.toString())) {
			this.word(jasperPrint);
		}

	}
	public void csv(JasperPrint jasperPrint)throws JRException, IOException {
		response.setContentType("text/csv");
		response.setHeader("Content-disposition", "attachment; filename=" + reportName + ".csv");
		response.setHeader("Filename",reportName + ".pdf");
		JRCsvExporter  exporter = new JRCsvExporter ();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleWriterExporterOutput( response.getOutputStream()));
		exporter.exportReport();
	}
	
	public void xlsx(JasperPrint jasperPrint)throws JRException, IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + reportName + ".xlsx");
		response.setHeader("Filename",reportName + ".pdf");
		JRXlsxExporter exporter = new JRXlsxExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
		if(null!=sheetNames) {
			reportConfig.setSheetNames(sheetNames);
		}
		exporter.setConfiguration(reportConfig);
		exporter.exportReport();
	}
	
	public void word(JasperPrint jasperPrint) throws JRException, IOException {
		response.setContentType("application/msword");
		response.setHeader("Content-disposition", "attachment; filename=" + reportName + ".docx");
		response.setHeader("Filename",reportName + ".pdf");
		JRDocxExporter exporter = new JRDocxExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		exporter.exportReport();
	}

	public void pdf(JasperPrint jasperPrint) throws JRException, IOException {
		response.setContentType("application/x-pdf");
		response.setHeader("Content-disposition", "attachment; filename=" + reportName + ".pdf");
		response.setHeader("Filename",reportName + ".pdf");
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}

}
