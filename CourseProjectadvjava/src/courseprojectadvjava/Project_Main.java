/* Module 5 Course Project
 * written by: Jennifer Grant
 * June 17, 2022
 * COP3805C Advanced Java Programming
 * 
 */

package courseprojectadvjava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class Project_Main {
		// Instantiate logger method from another class
		private final static Logger LOGGER = Logger.getLogger(Logging.class.getName());
		public static void Logger() {
			LOGGER.info("\nHello!\nThe logger is run from the Logging Class \n");
		}
		
		// All of the required code to perform the interaction
		// Create array lists for each table in DB
		public static ArrayList<Sale_item> sale_item = new ArrayList<Sale_item>();
		
		public static void main(String[] args) throws SQLException {
			
			// Main class is the driver class to do things
			char option = 'q';
			
			do {
				// This is introductory message and options menu for the User to begin
				System.out.println("\n=============================================================================");
				System.out.println("               Welcome to the Inventory Control System!!  ");
				System.out.println("=============================================================================\n");
				System.out.println("Choose one of the following to perform a database interaction:  ");
				System.out.println("-----------------------------------------------------------------------------\n");
				System.out.println("Press A or a to Add a New Sale Item to the database:  ");
				System.out.println("Press D or d to Delete a Sale Item from the database:  ");
				System.out.println("Press U or u to Update a Sale Item in the database:  ");
				System.out.println("Press R or r to Report on All Sale Items from the database:  ");
				System.out.println("Press G or g to Get a Sale Item Quantity report from the database:  ");
				System.out.println("Press S or s to Get a Report on Sale Item by Name:  ");
				System.out.println("Press Q or q to Quit the Inventory Control System at any time:  ");
				System.out.println("\n*****************************************************************************\n");
				
				// get input from user
				Scanner userInput = new Scanner(System.in);
				option = userInput.nextLine().charAt(0);	// gets first char only
				
				// If A/a is chosen then the following is performed
				if (option == 'A' || option == 'a') {
					System.out.println("\n=============================================================================");
					System.out.println("               Welcome to Add Sale Items to the database!!  ");
					System.out.println("=============================================================================\n");
					// Prompt user for Input
					System.out.println("\nEnter the Name of the New Sale Item here:  \n");
					String itemName = userInput.nextLine();
					System.out.println("\nEnter the Description of the New Sale Item here:  \n");
					String itemDescription = userInput.nextLine();
					System.out.println("\nEnter the quantity of the New Sale Item here:  \n");
					String quantity = userInput.nextLine();
					int quant = Integer.parseInt(quantity);
					System.out.println("\nEnter the List Price of the New Sale Item here:  \n");
					String listPrice = userInput.nextLine();
					double list = Double.parseDouble(listPrice);
					// Method to add items to array list
					AddSale_item(itemName, itemDescription, quant, list);
					
					System.out.println("\n----------------------------------------------------------------------------");
					System.out.println("\nThe Sale Item Added =  " + itemName + " \n");
					System.out.println("The Sale Item Description = " + itemDescription + " \n");
					System.out.println("The Quantity = " + quant + " \n");
					System.out.println("The List Price = $" + list + " \n");
					System.out.println("*****************************************************************************\n");
				}
				
				// If R/r is chosen then the following is performed
				if (option == 'R' || option == 'r') {
					System.out.println("\n======================================================================================");
					System.out.println("        The Report on All Sale Items in the database are Displayed below:  ");
					System.out.println("======================================================================================\n");
					System.out.println("Sale ItemId" + "\t" + "Item Name" + "\t" + "Item Description" + "\t" + "Quantity" + "\t" + "List Price\n");
					// Method to perform report actions
					ReportSale_item(null);
					// Method to print current date and time
					LocalDateTime timestamp = LocalDateTime.now();
					DateTimeFormatter ftimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd    HH:mm:ss");
					String fView = timestamp.format(ftimestamp);
					System.out.println("\nReport Created:  " + fView);
					System.out.println("***************************************************************************************\n");
				}
				
				// If D/d is chosen then the following is performed
				if (option == 'D' || option == 'd') {
					System.out.println("\n=========================================================================================");
					System.out.println("              Welcome to Delete Sale Items from the database!!  ");
					System.out.println("=========================================================================================\n");
					// Prompts user for Input
					System.out.println("\nEnter the ID number for the Sale Item to Delete from the database:  ");
					String sale_itemID = userInput.nextLine();
					int id = Integer.parseInt(sale_itemID);
					// Method to perform delete actions
					RemoveSale_item(sale_itemID, id);
					System.out.println("\n---------------------------------------------------------------------------------------\n");
					System.out.println(" The complete record for the Sale Item ID number  " + id + "  was Deleted from database. \n");
					System.out.println("*****************************************************************************************\n");
				}
								
				// If G/g is chosen then the following is performed
				if (option == 'G' || option == 'g') {
					System.out.println("\n======================================================================================");
					System.out.println("  The Sale Item Quantity - Re-order Report (less than 15 items) - from the database: ");
					System.out.println("======================================================================================\n");
					System.out.println("Sale ItemId" + "\t" + "Item Name" + "\t" + "Quantity" + "\n");
					// Method to perform Get actions
					GetSale_item(null);
					// Method to print current date and time
					LocalDateTime timestamp = LocalDateTime.now();
					DateTimeFormatter ftimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd    HH:mm:ss");
					String fView = timestamp.format(ftimestamp);
					System.out.println("\nReport Created:  " + fView);
					System.out.println("**************************************************************************************\n");
				}
				
				// If U/u is chosen then the following is performed
				if (option == 'U' || option == 'u') {
					System.out.println("\n=============================================================================");
					System.out.println("              Welcome to Update Sale Items from the database!!  ");
					System.out.println("=============================================================================\n");
					// Prompts user for Input to choose an item
					System.out.println("\nEnter the ID number of the Sale item to Update:  \n");
					System.out.println("-----------------------------------------------------------------------------\n");
					String sale_itemID = userInput.nextLine();
					int id = Integer.parseInt(sale_itemID);
					// Prompts user Input for updates to be performed
					System.out.println("\nEnter the Name of the Sale Item here:  \n");
					String itemName = userInput.nextLine();
					System.out.println("\nEnter the Description of the Sale Item here:  \n");
					String itemDescription = userInput.nextLine();
					System.out.println("\nEnter the quantity of the Sale Item here:  \n");
					String quantity = userInput.nextLine();
					int quant = Integer.parseInt(quantity);
					System.out.println("\nEnter the List Price of the Sale Item here:  \n");
					String listPrice = userInput.nextLine();
					double list = Double.parseDouble(listPrice);
					// Method to perform Update actions
					UpdateSale_item(itemName, itemDescription, id, quant, list);
					System.out.println();
					System.out.println("*****************************************************************************\n");
				}
								
				// If S/s is chosen then the following is performed 
				if (option == 'S' || option == 's') {
					System.out.println("\n=========================================================================================");
					System.out.println("        The details for Sale item are displayed below:  ");
					System.out.println("=========================================================================================\n");
					// Prompts user Input to choose an item by ID number to view report on item
					System.out.println("\nEnter the Sale Item ID number to View item details:  \n");
					String sale_itemID = userInput.nextLine();
					int id = Integer.parseInt(sale_itemID);
					System.out.println("\n-----------------------------------------------------------------------------------------\n");
					System.out.println("\nSale ItemId" + "\t" + "Item Name" + "\t" + "Item Description" + "\t" + "Quantity" + "\t" + "List Price\n");
					// Method to perform Select actions
					GetItem(sale_itemID, id);
					// Method to print current date and time
					LocalDateTime timestamp = LocalDateTime.now();
					DateTimeFormatter ftimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd    HH:mm:ss");
					String fView = timestamp.format(ftimestamp);
					System.out.println("\nReport Created:  " + fView);
					System.out.println("*****************************************************************************************\n");
				}
				
				// If Q/q is chosen then the program will close and display goodbye message
				if (option == 'Q' || option == 'q') {
					System.out.println("\n=============================================================================");
					System.out.println("          Thank you for using the Inventory Control System!!  \n");
					System.out.println("                          Have a Great Day!!  \n");
					System.out.println("*****************************************************************************\n");
				}
				
				} while (option != 'q');    // end do-while loop
			
		}
		
		// The 6 database interactions are listed here
		// Add New Sale items to the database with A/a option
		public static void AddSale_item(String name, String description, Integer quant, Double list) throws SQLException {
			Sale_item itemName = new Sale_item(name); 	//initialize item as new object
			sale_item.add(itemName);
			
			// Connects to database
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/advjavacourseproject", "root", "R00T23");
			PreparedStatement sql = conn.prepareStatement("insert into sale_item(itemName, itemDescription, quantity, listPrice) values(?,?,?,?)");
			sql.setString(1,  name);
			sql.setString(2, description);
			sql.setInt(3,  quant);
			sql.setDouble(4, list);
			
			sql.executeUpdate();
			
			System.out.println("\nThe new item was Added to the database Successfully!!  ");
			conn.close();
		}

		
		// Report on All Sale items from the database by ID number with R/r option
		public static void ReportSale_item(String name) throws SQLException {
			// Connects to database
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/advjavacourseproject", "root", "R00T23");
			String query = "select * from sale_item";
			
			try {
				Statement stmt = conn.createStatement();
				ResultSet sql = stmt.executeQuery(query);
				while(sql.next()) {					
					System.err.println("   "+sql.getString(1) + "            " + sql.getString(2) + "         " + sql.getString(3) + "            " + sql.getString(4) + "             " + sql.getString(5) + "\n");
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
			conn.close();
		}
		
		// Delete a record from the database with E/e option
		public static void RemoveSale_item(String name, Integer id) throws SQLException {
			Sale_item sale_itemID = new Sale_item(name);
			sale_item.remove(sale_itemID);
			
			// Connects to database
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/advjavacourseproject", "root", "R00T23");
			String query = "delete from sale_item where sale_itemID = ?";
			PreparedStatement sql = null;
			sql = conn.prepareStatement(query);
			sql.setInt(1, id);
			
			sql.executeUpdate();
			System.out.println("\nThe Sale item was Deleted from the database Successfully!!  ");
			conn.close();
		}
		
		// Get Quantity re-order report from the database with G/g option
		public static void GetSale_item(String name) throws SQLException {
			// Connects to database
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/advjavacourseproject", "root", "R00T23");
			String query = "select * from sale_item where quantity < 15";
			
			try {
				Statement stmt = conn.createStatement();
				ResultSet sql = stmt.executeQuery(query);
				while(sql.next()) {
					System.err.println("   "+sql.getString(1) + "            " + sql.getString(2) + "          " + sql.getString(4) + "\n");
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			conn.close();
		}

		
		// Select a Sale item to display from the database with S/s option
		public static void GetItem(String name, Integer id) throws SQLException {
			
			// Connects to database
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/advjavacourseproject", "root", "R00T23");
			String query = "select * from sale_item where sale_itemID = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet sql = stmt.executeQuery();
			
			while(sql.next()) {
				System.err.println("   "+sql.getString(1) + "            " + sql.getString(2) + "         " + sql.getString(3) + "            " + sql.getString(4) + "             " + sql.getString(5) + "\n");
			}
				conn.close();
		}
		
		
		// Update a Sale item in the database
		public static void UpdateSale_item(String name, String description, Integer id, Integer quant, Double list) throws SQLException {
			Sale_item itemName = new Sale_item(name); 	//initialize item as new object
			sale_item.add(itemName);
			// Connects to database
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/advjavacourseproject", "root", "R00T23");
			String query = "UPDATE sale_item SET itemName=?, itemDescription=?, quantity=?, listPrice=? WHERE sale_itemID=?";
			
			PreparedStatement sql = conn.prepareStatement(query);
			
			sql.setString(1, name);
			sql.setString(2, description);
			sql.setInt(3, quant);
			sql.setDouble(4, list);
			sql.setInt(5, id);
			sql.executeUpdate();
			System.out.println();
			System.out.println("\nThe Sale item was Updated in the database Successfully!!  ");
			conn.close();
		}
		

}

