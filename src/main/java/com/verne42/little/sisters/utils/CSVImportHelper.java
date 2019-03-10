//						Verne42
//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \\|     |// '.
//                 / \\|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \\\  -  /// |   |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
// We code with compassion and love.


package com.verne42.little.sisters.utils;


import org.slf4j.LoggerFactory;
import java.io.*;


/**
 * This class abstracts the file reading from the resources.
 */
public abstract class CSVImportHelper
{
	// -----------------------------------------------------------------------------------
	// Section: Constructor
	// -----------------------------------------------------------------------------------

	public CSVImportHelper(File file)
	{
		this.file = file;
	}

	// -----------------------------------------------------------------------------------
	// Section: Helper methods
	// -----------------------------------------------------------------------------------

	/**
	 * This method abstracts the read of @{@link CSVImportHelper#file} and calls on
	 * 	@{@link CSVImportHelper#addRecord(java.lang.String[])}
	 * @throws IOException If it fails to read the file, or the file is not found.
	 */
	public void importCSV() throws IOException
	{
		String line;
		LoggerFactory.getLogger(this.getClass()).info(
			"Importing csv file: " + this.file.getAbsolutePath());

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file)))
		{
			// Remove header
			if (this.hasHeader)
			{
				line = bufferedReader.readLine();
			}

			line = bufferedReader.readLine();
			while (line != null)
			{
				this.addRecord(line.split(this.cvsSplitBy));

				// Read next line
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
		}
	}


	/**
	 * This method gets the country from the file path.
	 * Convention: {country}/{chamber}.
	 *
	 * @return The string of the country.
	 */
	public String getCountry()
	{
		return file.toPath().getParent().getParent().getFileName().toString();
	}


	/**
	 * This method gets the chamber from the file path.
	 * Convention: {country}/{chamber}.
	 *
	 * @return The string of the chamber.
	 */
	public String getChamber()
	{
		return file.toPath().getParent().getFileName().toString();
	}

	/**
	 * This method should be implemented to add each record @param properties.
	 *
	 * @param properties Record properties.
	 */
	public abstract void addRecord(String[] properties);

	// -----------------------------------------------------------------------------------
	// Section: Getter and setters
	// -----------------------------------------------------------------------------------

	public String getCvsSplitBy()
	{
		return cvsSplitBy;
	}


	public void setCvsSplitBy(String cvsSplitBy)
	{
		this.cvsSplitBy = cvsSplitBy;
	}


	public boolean isHasHeader()
	{
		return hasHeader;
	}


	public void setHasHeader(boolean hasHeader)
	{
		this.hasHeader = hasHeader;
	}


	public String getListSplitBy()
	{
		return listSplitBy;
	}


	public void setListSplitBy(String listSplitBy)
	{
		this.listSplitBy = listSplitBy;
	}


	public String getNullString()
	{
		return nullString;
	}


	public void setNullString(String nullString)
	{
		this.nullString = nullString;
	}


	public File getFile()
	{
		return file;
	}


	public void setFile(File file)
	{
		this.file = file;
	}


	private boolean hasHeader = true;

	/**
	 * String for null values in the csv.
	 */
	private String nullString = "null";

	/**
	 * Regex to split the CSV value into a list.
	 */
	private String listSplitBy = "\\|";

	/**
	 * Regex to split the CSV file. defaults ","
	 */
	private String cvsSplitBy = ",";

	/**
	 * The CSV file.
	 */
	private File file;
}
