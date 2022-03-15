package automation.PageSpeedInsights;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelOperations {
	
	static File src= new File(Constants.excelFileLocation);
	static FileInputStream input;
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	static int excelRowSize;
	
	public static void readDataFromExcel() throws Exception
	{	
		countExcelRows();
		input=new FileInputStream(src);
		workbook=new XSSFWorkbook(input);
		sheet=workbook.getSheetAt(0);
		
		for(int i=1;i<excelRowSize;i++)
		{
			Main.urlsFromExcel.add(sheet.getRow(i).getCell(0).getStringCellValue());
//			System.out.println("Url from Excel "+Main.urlsFromExcel.get(i-1));
		}
		
	}
	
	public static void countExcelRows() throws Exception
	{
		input=new FileInputStream(src);
		workbook=new XSSFWorkbook(input);
		sheet=workbook.getSheetAt(0);
		int counter;
		for(counter=0; ;counter++)
		{
			try
			{
				String emptyCell=sheet.getRow(counter).getCell(0).getStringCellValue();
				if(emptyCell.equalsIgnoreCase(""))
				{
					break;
				}
			}
			catch (Exception e) 
			{
				break;
			}
		}
		excelRowSize=counter;
		System.out.println("Total Rows :"+excelRowSize);
		
		// To ways to find how many row in excel
		// 1st :
		// ***** Excel Inbuilt Function  ==  excelRowSize=sheet.getLastRowNum();  ||  excelRowSize=sheet.getPhysicalNumberOfRows();
		// 2nd
		// Traverse all excel rows starts from 0 to infinite loop, and check if there is some text in cell, if there is any text then counter++
		// if there is no text , then null pointer exception and this exception is handled by catch block and break the loop and value of last counter is your excelRowSize
		// ALSO if in excel you write some text in rows and later on deleted that text in rows, BUT excel did not delete the memory allocated to cell, 
		// So check while tranversing the rows, if that cell is empty then also break the loop and value of last counter is your excelRowSize
	}
			
	public static void writeDataToExcel(int sheetIndex, int row, int column, String urls) throws Exception
	{
		input=new FileInputStream(src);
		workbook=new XSSFWorkbook (input);
		sheet=workbook.getSheetAt(sheetIndex);
		if(sheet.getRow(row)==null)
			sheet.createRow(row).createCell(column).setCellValue(urls);
		else
			sheet.getRow(row).createCell(column).setCellValue(urls);
		input.close();
		FileOutputStream out=new FileOutputStream(src);
		workbook.write(out);
		out.close();
	}
	
	
}

