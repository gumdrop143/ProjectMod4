/* Module 4 Course Project
 * written by: Jennifer Grant
 * June 05, 2022
 * 
 */

package courseprojectadvjava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class Project_Main {

		private final static Logger LOGGER = Logger.getLogger(Logging.class.getName());
		public static void Logger() {
			LOGGER.info("\nHello!\nThe logger is run from the Logging Class \n");
		}
		
		// All of the required code to perform the interaction
		// fake D.B this will be back-end DB
		// Create array lists for each table in DB
		public static ArrayList<Sale_item> sale_item = new ArrayList<Sale_item>();
		
		public static void main(String[] args) throws SQLException {
			
			// Main class is the driver class to do things
			char option = 'q';
			
			do {
				// This is introductory message and options for the User to begin
				System.out.println("\nWelcome to the Inventory Control System!!  ");
				System.out.println("=============================================================================\n");
				System.out.println("Choose one of the following to perform a database interaction: \n");
				System.out.println("Press A or a to Add a New Sale Item to the database:  ");
				System.out.println("Press D or d to Delete a Sale Item from the database:  ");
				System.out.println("Press U or u to Update a Sale Item in the database:  ");
				System.out.println("Press R or r to Report on All Sale Items from the database:  ");
				System.out.println("Press G or g to Get a Sale Item Quantity report from the database:  ");
				System.out.println("Press S or s to Get a Report on Sale Item by Name:  ");
				System.out.println("Press Q or q to Quit the Inventory Control System at any time:  ");
				System.out.println("*****************************************************************************\n");
				
				// get input from user
				Scanner userInput = new Scanner(System.in);
				option = userInput.nextLine().charAt(0);	// gets first char only
				
				// If A/a is chosen then the following is performed
				if (option == 'A' || option == 'a') {
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
					AddSale_item(itemName, itemDescription, quant, list);
					
					System.out.println("\nThe Sale Item Added =  " + itemName + " \n");
					System.out.println("\nThe Sale Item Description = " + itemDescription + " \n");
					System.out.println("The Quantity = " + quant + " \n");
					System.out.println("The List Price = $" + list + " \n");
					System.out.println("*****************************************************************************\n");
				}
				
				// If R/r is chosen then the following is performed
				if (option == 'R' || option == 'r') {
					System.out.println("\nThe Report on All Sale Items in the database are Displayed below:  ");
					System.out.println("=============================================================================\n");
					System.out.println("Sale ItemId" + "\t" + "Item Name" + "\t" + "Item Description" + "\t" + "Quantity" + "\t" + "List Price\n");
					ReportSale_item(null);
					System.out.println("*****************************************************************************\n");
				}
				
				// If D/d is chosen then the following is performed
				if (option == 'D' || option == 'd') {
					System.out.println("\nEnter the ID number for the Sale Item to Delete from the database:  ");
					System.out.println("=============================================================================\n");
					String sale_itemID = userInput.nextLine();
					int id = Integer.parseInt(sale_itemID);
					RemoveSale_item(sale_itemID, id);
					System.out.println("\nThe complete record for ID number " + id + " was Deleted from database. \n");
					System.out.println("*****************************************************************************\n");
				}
								
				// If G/g is chosen then the following is performed
				if (option == 'G' || option == 'g') {
					System.out.println("\nThe Sale Item Quantity - re-order report (less than 10 items) - from the database:  ");
					System.out.println("=============================================================================\n");
					System.out.println("Sale ItemId" + "\t" + "Item Name" + "\t" + "Quantity" + "\n");
					GetSale_item(null);
					System.out.println("*****************************************************************************\n");
				}
				
				// If U/u is chosen then the following is performed
				if (option == 'U' || option == 'u') {
					System.out.println("\nEnter the ID number of the Sale item to Update:  \n");
					System.out.println("=============================================================================\n");
					String sale_itemID = userInput.nextLine();
					int id = Integer.parseInt(sale_itemID);
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
					UpdateSale_item(itemName, itemDescription, id, quant, list);
					System.out.println();
					System.out.println("*****************************************************************************\n");
				}
								
				// If S/s is chosen then the following is performed 
				// Not working correctly yet
				if (option == 'S' || option == 's') {
					System.out.println("\nThe details for Sale item name entered are displayed below:  ");
					System.out.println("=============================================================================\n");
					System.out.println("\nEnter the Name of the Sale item to View:  \n");
					String itemName = userInput.nextLine();
					GetItem(itemName);
					System.out.println("test line");
					System.out.println("*****************************************************************************\n");
				}
				
				// If Q/q is chosen then
				if (option == 'Q' || option == 'q') {
					System.out.println("\nThank you for using the Inventory Control System!!  " +
							"\nHave a Great Day!!  ");
					System.out.println("*****************************************************************************\n");
				}
				
				} while (option != 'q');
			
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
			System.out.println(itemName);
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
					System.err.println(sql.getString(1) + "\t" + sql.getString(2) + "\t" + sql.getString(3) + "\t " + sql.getString(4) + "\t" + sql.getString(5) + "\n");
					System.out.println();
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
			System.out.println(sale_itemID);
			System.out.println("\nThe Sale item was Deleted from the database Successfully!!  ");
			conn.close();
		}
		
		// Get Quantity re-order report from the database with G/g option
		public static void GetSale_item(String name) throws SQLException {
			// Connects to database
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/advjavacourseproject", "root", "R00T23");
			String query = "select * from sale_item where quantity < 10";
			
			try {
				Statement stmt = conn.createStatement();
				ResultSet sql = stmt.executeQuery(query);
				while(sql.next()) {
					System.err.println(sql.getString(1) + "\t" + sql.getString(2) + "\t" + sql.getString(4) + "\n");
					System.out.println();
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			conn.close();
		}

		
		// Select a Sale item to display from the database with S/s option
		// not working correctly yet
		public static void GetItem(String name) throws SQLException {
			Sale_item itemName = new Sale_item(name);
			sale_item.add(itemName);
			
			// Connects to database
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/advjavacourseproject", "root", "R00T23");
			String query = "select * from sale_item where itemName =?";
			
			try {
				Statement stmt = conn.createStatement();
				ResultSet sql = stmt.executeQuery(query);
				while(sql.next()) {					
					System.err.println(sql.getString(1) + "\t" + sql.getString(2) + "\t" + sql.getString(3) + "\t " + sql.getString(4) + "\t" + sql.getString(5) + "\n");
					System.out.println();
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
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

