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

package com.verne42.little.sisters;

import com.verne42.little.sisters.services.CSVImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;


/**
 * Main class for Little Sisters Application.
 */
@SpringBootApplication
@EnableNeo4jRepositories("com.verne42.little.sisters.repositories")
public class V42LittleSistersApplication 
{

	public V42LittleSistersApplication(CSVImportService csvImportService)
	{
		this.csvImportService = csvImportService;
	}


	public static void main(String[] args)
	{
		SpringApplication.run(V42LittleSistersApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void importCSVAfterStartup()
	{
		// Check if the import profile is active.
		if (Arrays.asList(this.environment.getActiveProfiles()).contains("oley.import.csv"))
		{
			LOGGER.info("CSV Import Profile active, Importing from: " +
				this.csvImportService.getCsvRootDir());
			try
			{
				this.csvImportService.importFromCSV();
				LOGGER.info("Successfully Imported CSV Files");
			}
			catch (IOException e)
			{
				e.printStackTrace();
				LOGGER.info("Unable to import the files!");
			}
		}
		else
		{
			LOGGER.info("CSV Import Profile disable. Look docs on how to import from csv files.");
		}
	}

	private final CSVImportService csvImportService;

	@Autowired
	private Environment environment;

	public static final Logger LOGGER = Logger.getLogger(
		V42LittleSistersApplication.class.getName());
}