package com.score.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ReportGenerator 
{
	private BufferedOutputStream outputStream;
	WritableWorkbook workbook;
	WritableSheet worksheet;
	
	public ReportGenerator(String fileName) throws IOException
	{
		this.outputStream = new BufferedOutputStream(new FileOutputStream(fileName));
		this.workbook = Workbook.createWorkbook(outputStream);
		this.worksheet = workbook.createSheet("评分结果", 0);
	}
	
	public void addLabel(String labelName, int row, int column) throws RowsExceededException, WriteException
	{
		Label label = new Label(column, row, labelName);
		this.worksheet.addCell(label);
	}
	
	public void addNumber(double value, int row, int column) throws RowsExceededException, WriteException
	{
		Number number = new Number(column, row, value);
		this.worksheet.addCell(number);
	}
	
	public void generateReport() throws IOException, WriteException
	{
		this.workbook.write();
		this.workbook.close();
		this.outputStream.flush();
		this.outputStream.close();
	}
}
