/* Module 05 Course Project
 * written by: Jennifer Grant
 * June 12, 2022
 * COP3805C Advanced Java Programming
 * 
 */

package courseprojectadvjava;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

// Logging class create object
public class Logging {

		private final static Logger LOGGER = Logger.getLogger(Logging.class.getName());
		
		public static void main(String[] args) throws SecurityException, FileNotFoundException {
			try {
				// Log Manager to clear automatic handlers reset()
				LogManager.getLogManager().readConfiguration(new FileInputStream("projectLog"));
				// Set level for log manager. OFF passes nothing thru
				LOGGER.setLevel(Level.ALL);
				
				// Create a Console Handler
				ConsoleHandler cHandler = new ConsoleHandler();
				// Set level for consoleHandler. SEVERE minimize msgs for better user experience
				cHandler.setLevel(Level.SEVERE);
				LOGGER.addHandler(cHandler);
			
			} catch (SecurityException e1) {
				LOGGER.log(Level.WARNING, "This is a WARNING message!!  \n", e1);
			
			} catch (IOException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				// Create a file handler
				FileHandler fHandler = new FileHandler("C:/Users/JENI G/Documents/java_logs/projectLog.log", 2000, 5);
				// Add a formatter for the file handler
				fHandler.setFormatter(new SimpleFormatter());
				// Set level for file handler
				LOGGER.setLevel(Level.FINE);
				LOGGER.addHandler(fHandler);		// Handler for fileHandler
				
				for (int i=0; i<10; i++) {
					// logging messages
					LOGGER.log(Level.CONFIG, "Config data");
					
				} 
				
			} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "There is an error in the Logger. \n", e);
			}
			
			LOGGER.log(Level.INFO, "Welcome to the Logger \n");
			Project_Main.Logger();
			
			}

	}

