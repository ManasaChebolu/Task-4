package EmployeeMaven;

import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

public class EmployeeMaven {
@SuppressWarnings("resource")
public static void main(String[] args) throws SQLException  {
	Scanner sc=new Scanner(System.in);
	Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/employees","root","Chebolu@03");
	Logger logger=Logger.getLogger("Employees");
    String input;
    int empId,empSalary;
    String empName,empType,sql,tableName;
	do {
    	System.out.print("1.Create ,2.Modify ,3.Delete ,4.List all employees");
        int n=sc.nextInt();
    switch(n) {
    	case 1:System.out.println("Enter empId");
		       empId=sc.nextInt();
		       sc.nextLine();
		       System.out.println("Enter empName");
		       empName=sc.nextLine();
		       System.out.println("Enter empSalary");
		       empSalary=sc.nextInt();
		       sc.nextLine();
		       System.out.println("Enter empType permanent /parttime/contract");
		       empType=sc.nextLine();
		       if(empType.equals("permanent") || empType.equals("parttime") ||empType.equals("contract")) {
		    	tableName = empType;
			    sql = String.format("INSERT INTO %s VALUES (?,?,?,?) ", tableName);
		        PreparedStatement  insertstmt=conn.prepareStatement(sql);    	     	       	    	  
  		           insertstmt.setInt(1, empId);
  		           insertstmt.setString(2, empName);
  		           insertstmt.setInt(3, empSalary);
  		           insertstmt.setString(4, empType); 		          
  		           insertstmt.executeUpdate();	 
		       }
    			else 
    				System.out.println("No Such Type");
    	      logger.log(Level.INFO, "Employee info is added...");
    	      break;
    	case 2:System.out.println("Want to modify employee detail in permanent /parttime/contract");
    	       System.out.println("Enter empType permanent /parttime/contract");
    	       sc.nextLine();
    	       empType=sc.nextLine();
		       if(empType.equals("permanent") || empType.equals("parttime") ||empType.equals("contract")) {
		           System.out.print("Enter empId to modify: ");
	               empId=sc.nextInt();
	               System.out.println("Enter empSalary to modify");
			       empSalary=sc.nextInt();
			       sc.nextLine();
			       tableName= empType;
    	           sql=String.format("update %s set empSalary=? where empId=?",tableName);
    	           PreparedStatement updatestmt=conn.prepareStatement(sql);          	  
                   updatestmt.setInt(1,empSalary);
                   updatestmt.setInt(2,empId);
                   updatestmt.executeUpdate();       
                }
		       else
		    	   System.out.println("No Such Type");
	            logger.log(Level.INFO, "Employee details updated...");
	            break;
    	case 3:System.out.println("Want to delete employee in permanent /parttime/contract");
               System.out.println("Enter empType permanent /parttime/contract");
               sc.nextLine();
	           empType=sc.nextLine();
	           if(empType.equals("permanent") || empType.equals("parttime") ||empType.equals("contract")) {
		           System.out.print("Enter empId to modify: ");
	               empId=sc.nextInt();
	               tableName= empType;
	               sql=String.format("delete from %s where empId=?",tableName);
	               PreparedStatement deletestmt=conn.prepareStatement(sql);
	               deletestmt.setInt(1,empId);
	               deletestmt.executeUpdate();
	           }
	           else
	        	   System.out.println("No Such Type");
	           logger.log(Level.INFO, "Employee details deleted...");
	           sc.nextLine();
	           break;   
    	case 4:PreparedStatement selectpermanentstmt=conn.prepareStatement("select *from permanent");
    		   ResultSet rs=selectpermanentstmt.executeQuery();   		 
    		   JSONArray permanentArray=new JSONArray();
    		   while(rs.next()) {
    			    JSONObject permanent=new JSONObject(); 
    			     System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4));
    			    permanent.put("empId",rs.getInt("empId"));
 			        permanent.put("empName",rs.getString("empName"));
 			        permanent.put("empSalary",rs.getInt("empSalary"));
 			        permanent.put("empType",rs.getString("empType"));
 			        permanentArray.put(permanent);
    		   }  		   
    		   PreparedStatement selectparttimestmt=conn.prepareStatement("select *from parttime");
    		   ResultSet rs1=selectparttimestmt.executeQuery();   		     		 
    		   JSONArray parttimeArray=new JSONArray();
    		   while(rs1.next()) {
    			   JSONObject parttime=new JSONObject();
    			    System.out.println(rs1.getInt("empId")+" "+rs1.getString("empName")+" "+rs1.getInt("empSalary")+" "+rs1.getString("empType"));
    			     parttime.put("empId",rs1.getInt("empId"));
    			     parttime.put("empName",rs1.getString("empName"));
    			     parttime.put("empSalary",rs1.getInt("empSalary"));
    			     parttime.put("empType",rs1.getString("empType"));
    			     parttimeArray.put(parttime); 
    		   }		   
    		   PreparedStatement selectcontractstmt=conn.prepareStatement("select *from contract");
    		   ResultSet rs2=selectcontractstmt.executeQuery();  		 
    		   JSONArray contractArray=new JSONArray();
    		   while(rs2.next()) {
    			   JSONObject contract=new JSONObject();
    			   System.out.println(rs2.getInt("empId")+" "+rs2.getString("empName")+" "+rs2.getInt("empSalary")+" "+rs2.getString("empType"));
    			     contract.put("empId",rs2.getInt("empId"));
    			     contract.put("empName",rs2.getString("empName"));
    			     contract.put("empSalary",rs2.getInt("empSalary"));
    			     contract.put("empType",rs2.getString("empType"));
    			     contractArray.put(contract);
    		   }
    		   logger.log(Level.INFO, "All Employee details....");
    		   if(permanentArray.length() > 0 || contractArray.length()>0 || parttimeArray.length()>0) {
    		           System.out.println(permanentArray);
    		           System.out.println(contractArray);
    		           System.out.println(parttimeArray);
    		   }
    		   sc.nextLine();
               break;
       default: System.out.println("No such operation");   
                sc.nextLine();
               break;
    }
    System.out.println("Do you want to continue[yes/no]:");
    input=sc.nextLine();
    }while(input.equals("yes")); 
	conn.close();
	}
}