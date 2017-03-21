package jdbc.project;
import java.sql.*;
import java.util.Scanner;
import java.util.InputMismatchException;

public class JDBCProject {
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/JDBC Project";

    public static void main(String[] args) {
    JDBCProject project = new JDBCProject();
    Connection conn = null;
    boolean run = true;
    Scanner in = new Scanner(System.in);
    
    try{
    Class.forName("org.apache.derby.jdbc.ClientDriver");
    conn = DriverManager.getConnection(DB_URL);
    System.out.println("Welcome to JDBC Project demo");
    }
    catch(Exception e){
        System.err.print("Database connection error, please edit connection");
    }
    
    while(run){
    try{
    in.reset();
    int selection = 0;
    System.out.println("\n");
    System.out.println("Please select an option from the menu below");
    System.out.println("1) List all writing groups"
            + "\n2) List a particular writing groups info"
            + "\n3) List all publishers"
            + "\n4) List a particular publishers info"
            + "\n5) List all book titles"
            + "\n6) List all book info"
            + "\n7) Insert a new book"
            + "\n8) Remove a book"
            + "\n9) Update publisher"
            + "\n0) Exit");

    selection = Integer.parseInt(in.nextLine());
    
    switch(selection){
        case 1:
           project.listAllWritingGroups(conn);
           break;
        case 2:
            project.listWritingGroupInfo(conn);
            break;
        case 3:
            project.listAllPublishers(conn);
            break;
        case 4:
            project.listPublisherInfo(conn);
            break;
        case 5:
            project.listAllBooks(conn);
            break;
        case 6:
            project.listBookInfo(conn);
            break;
        case 7:
            project.insertBook(conn);
            break;
        case 8:
            project.removeBook(conn);
            break;
        case 9:
            project.updatePublisher(conn);
            break;
        case 0:
            run = false;
            break;   
    }
    }
    catch(Exception menuException){
        System.err.print("Invalid menu selection, please try again");
    }
    }
    }
    
    public void listAllWritingGroups(Connection conn){
        try{
            Statement stmt = conn.createStatement();
            String query = "SELECT groupName FROM WritingGroup";
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Printing all Group Names..");
            
            while(rs.next()){
                String groupName = rs.getString("groupName");
                System.out.println("\n"+groupName);
            }
            stmt.close();
            rs.close();
            }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void listWritingGroupInfo(Connection conn){
        try{
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the name of the group");
            String input = in.nextLine();
            String ps = "SELECT * FROM WritingGroup WHERE groupName = ?";
            PreparedStatement PStmt = conn.prepareStatement(ps);
            PStmt.setString(1, input);
            ResultSet rs = PStmt.executeQuery();
            
            if(rs.next()){
                System.out.println("\nPrinting info for the group "+input);
                do{
                String groupName = rs.getString("groupName");
                String headWriter = rs.getString("headWriter");
                Date date = rs.getDate("yearFormed");
                String subject = rs.getString("subject");
                
                System.out.println("\nGroup Name:   "+groupName);
                System.out.println("\nHead Writer:  "+headWriter);
                System.out.println("\nDate Formed:  "+date);
                System.out.println("\nSubject:      "+subject);
                }while(rs.next());
            }else{
            System.err.print("Error, "+input+" was not found");
            }
            PStmt.close();
            rs.close();
            }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void listAllPublishers(Connection conn){
           try{
            Statement stmt = conn.createStatement();
            String query = "SELECT publisherName FROM Publisher";
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Printing all Publisher Names..");
            
            while(rs.next()){
                String pubName = rs.getString("publisherName");
                System.out.println("\n"+pubName);
            }
            stmt.close();
            rs.close();
            }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void listPublisherInfo(Connection conn){
        try{
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the name of the Publisher");
            String input = in.nextLine();
            String ps = "SELECT * FROM publisher WHERE publisherName = ?";
            PreparedStatement PStmt = conn.prepareStatement(ps);
            PStmt.setString(1, input);
            ResultSet rs = PStmt.executeQuery();
            
            if(rs.next()){
            System.out.println("Printing the info for "+input);
            do{
                String publisherName = rs.getString("publisherName");
                String publisherAddress = rs.getString("publisherAddress");
                String publisherPhone = rs.getString("publisherPhone");
                String publisherEmail = rs.getString("publisherEmail");
                
                System.out.println("\nPublisher Name:           "+publisherName);
                System.out.println("\nPublisher Address:        "+publisherAddress);
                System.out.println("\nPublisher Phone Number:   "+publisherPhone);
                System.out.println("\nPublisher Email Address:  "+publisherEmail);
            }while(rs.next());
            }
            else{
                System.err.print("Error, "+input+" was not found");
                }
            PStmt.close();
            rs.close();
            }
        catch(SQLException e){
            System.err.print("Database Error");
        }
    }
    
    public void listAllBooks(Connection conn){
         try{
            Statement stmt = conn.createStatement();
            String query = "SELECT bookTitle FROM Book";
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Printing all Book Titles..");
            
            while(rs.next()){
                String bookTitle = rs.getString("bookTitle");
                System.out.println("\n"+bookTitle);
            }
            stmt.close();
            rs.close();
            }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void listBookInfo(Connection conn){
        try{
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the name of the book");
            String input = in.nextLine();
            String ps = "SELECT * FROM book NATURAL JOIN publisher NATURAL JOIN writingGroup WHERE bookTitle = ?";
            PreparedStatement PStmt = conn.prepareStatement(ps);
            PStmt.setString(1, input);
            ResultSet rs = PStmt.executeQuery();
            
            if(rs.next()){
            System.out.println("Printing the info for "+input);
            do{
                String groupName = rs.getString("groupName");
                String headWriter = rs.getString("headWriter");
                Date date = rs.getDate("yearFormed");
                String subject = rs.getString("subject");
                String publisherName = rs.getString("publisherName");
                String publisherAddress = rs.getString("publisherAddress");
                String publisherPhone = rs.getString("publisherPhone");
                String publisherEmail = rs.getString("publisherEmail");
                String bookTitle = rs.getString("bookTitle");
                int yearPublished = rs.getInt("yearPublished");
                int pages = rs.getInt("numberPages");
                
                System.out.println("\nBook Title:               "+bookTitle);
                System.out.println("\nYear Published:           "+yearPublished);
                System.out.println("\nNumber of Pages:          "+pages);
                System.out.println("\nPublisher Name:           "+publisherName);
                System.out.println("\nPublisher Address:        "+publisherAddress);
                System.out.println("\nPublisher Phone Number:   "+publisherPhone);
                System.out.println("\nPublisher Email Address:  "+publisherEmail);
                System.out.println("\nGroup Name:               "+groupName);
                System.out.println("\nHead Writer:              "+headWriter);
                System.out.println("\nDate Formed:              "+date);
                System.out.println("\nSubject:                  "+subject);
            }while(rs.next());
            }
            else{
                System.err.print("Error, "+input+" was not found");
            }
            PStmt.close();
            rs.close();
            }
        catch(SQLException e){
            System.err.print("Database Error");
        }
    }
    
    public void insertBook(Connection conn){
         try{
            Scanner in = new Scanner(System.in);
            String update = "INSERT INTO book VALUES(?,?,?,?,?)";
            PreparedStatement PS = conn.prepareStatement(update);
            System.out.println("Please enter the book title");
            PS.setString(1, in.nextLine());
            
            System.out.println("Please enter the name of the group that wrote the book");
            PS.setString(2, in.nextLine());
            
            System.out.println("Please enter the name of the publisher");
            PS.setString(3, in.nextLine());
            
            System.out.println("Please enter the year the book was published");
            PS.setInt(4, in.nextInt());
            
            System.out.println("Please enter the number of pages in the book");
            PS.setInt(5, in.nextInt());
            
            System.out.println("the book has been added to the database!");
            PS.executeUpdate();
            PS.close(); 
            }
         catch(InputMismatchException e){
             System.err.print("Invalid input, please try again");
         }
         catch(SQLException sql){
             System.err.print("That book title already exists in the database!");
         }
    }
    
    public void removeBook(Connection conn){
        try{
            Scanner in = new Scanner(System.in);
            String remove = "DELETE FROM book WHERE booktitle = ?";
            PreparedStatement PS = conn.prepareStatement(remove);
            System.out.println("Please enter the title of the book to remove");
            String book = in.nextLine();
            PS.setString(1, book);
            int check = PS.executeUpdate();
            
            if(check > 0){
                System.out.println(book+" has been removed from the database!");
            }
            else{
                System.err.print(book+" was not found in the database");
            }
            
            }
        catch(SQLException e){
            System.err.print("Database Error");
        }
    }
    
    public void updatePublisher(Connection conn){
        try{
            Scanner in = new Scanner(System.in);
            String insert = "INSERT INTO publisher VALUES(?, ?, ?, ?)";
      
            PreparedStatement PS = conn.prepareStatement(insert);
            System.out.println("Please enter the name of the publisher");
            String pubName = in.nextLine();
            PS.setString(1, pubName);
            System.out.println("Please enter the address of the publisher");
            PS.setString(2, in.nextLine());
            System.out.println("Please enter the phone number of the publisher");
            PS.setString(3, in.nextLine());            
            System.out.println("Please enter the email of the publisher");
            PS.setString(4, in.nextLine());
            
            PS.executeUpdate();
            System.out.println("The publisher has been added to the database");
            PS.close();
            
            String update = "UPDATE book SET publisherName = ? WHERE publisherName = ?";
            PreparedStatement Psupdt = conn.prepareStatement(update);
            Psupdt.setString(1, pubName);
            System.out.println("Please enter the name of the old publisher to update");
            String input = in.nextLine();
            Psupdt.setString(2, input);
            int check = Psupdt.executeUpdate();
            if(check > 0){
             System.out.println("Update sucessful!");
            }
            else{
                System.err.print(input+" was not found, Update unsuccessful");
            }
            
            Psupdt.close();
            }
        catch(SQLException e){
           System.err.print("That publisher already exists, please try again");
        }
    }
}
