package com.score.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.score.bean.SystemState;
import com.score.service.CalculateScoreService;
import com.score.service.GenerateReportService;
import com.score.service.SystemStateService;
import com.score.util.ErrorType;
import com.score.util.FieldErrorGenerator;

@SuppressWarnings("serial")
public class GenerateReportAction extends ActionSupport 
{
	private CalculateScoreService calculateScoreService;
	
	private GenerateReportService generateReportService;
	
	private SystemStateService systemStateService;
	
	private SystemState systemState;

	@Transactional
	public InputStream getDownloadFile() throws FileNotFoundException 
	{ 
		String path = ServletActionContext.getServletContext().getRealPath("/") + "usr_in\\report\\report.xls";
		InputStream inputStream = new FileInputStream(path);
		return inputStream;
	}

	@Transactional
	public String execute()
	{
		this.systemState = this.systemStateService.getSystemState();
		if (this.systemStateService.generateReport() != SystemStateService.CheckResult.Suitable)
		{
			FieldErrorGenerator.addFieldError(this, ErrorType.SYSTEM_STATE_NOT_SUITABLE);
			return Action.INPUT;
		}
		calculateScoreService.calculateTotalScore();
		try {
			String path = ServletActionContext.getServletContext().getRealPath("/");
			File f = new File(path + "usr_in\\report");
			f.mkdir();
			this.generateReportService.generateReport(path + "usr_in\\report\\report.xls");
			return Action.SUCCESS;
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Action.ERROR;
	}

	public void setGenerateReportService(GenerateReportService generateReportService) {
		this.generateReportService = generateReportService;
	}

	public void setSystemStateService(SystemStateService systemStateService) {
		this.systemStateService = systemStateService;
	}

	public void setCalculateScoreService(CalculateScoreService calculateScoreService) {
		this.calculateScoreService = calculateScoreService;
	}

	public SystemState getSystemState() {
		return systemState;
	}

}
