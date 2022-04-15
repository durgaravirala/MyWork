package com.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadDataFromXL {
	
	@SuppressWarnings("unlikely-arg-type")
	public void readExcel(String filePath,String fileName,String sheetName) throws IOException {

	    File file =    new File("C:\\Users\\durga"+"\\"+"TestData.xlsx");
	    List<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();

	    //Create an object of FileInputStream class to read excel file

	    FileInputStream inputStream = new FileInputStream(file);

	    Workbook workBook = null;

	    	workBook = new XSSFWorkbook(inputStream);

	    Sheet sheet = workBook.getSheet(sheetName);


	    int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

	    for (int i = 0; i < rowCount+1; i++) {
	    	ArrayList<String>[] d = (ArrayList<String>[]) new ArrayList[30];
		  	      d[i] = new ArrayList<String>();  
	  	    Row row = sheet.getRow(i);
	        for (int j = 0; j < row.getLastCellNum(); j++) {

	        	CellType type = row.getCell(j).getCellType();
	        	if(type.toString().equals("STRING"))
	        		 d[i].add(row.getCell(j).getStringCellValue());
	        	else d[i].add((int) Math. round(row.getCell(j).getNumericCellValue())+"");

	        }
	        listOfLists.add(d[i]);
	      
	    } 
	    workBook.close();
	    for(int k=0; k<listOfLists.size(); k++)
	    	  System.out.println("list of lists contains "+ listOfLists.get(k));
	}
	
	public static void main(String[] a) throws IOException {
		ReadDataFromXL readDataFromXML = new ReadDataFromXL();
		readDataFromXML.readExcel("", "TestData.xlsx", "Details");
	}
	

}
