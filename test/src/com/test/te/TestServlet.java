package com.test.te;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import javax.servlet.http.*;
import com.google.appengine.api.utils.SystemProperty;

@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				final String JDBC_DRIVER="com.mysql.jdbc.GoogleDriver";  
				final String DB_URL="jdbc:google:mysql://utssrc:testdb/TEST?user=root";
				java.sql.Connection conn = null;
				Statement  stmt = null;
				ResultSet   rs = null;
			
				//  Database credentials
				final String USER = "root";
				final String PASS = "riorobles";
				try{
					PrintWriter out = resp.getWriter();	          // Register JDBC driver
					Class.forName("com.mysql.jdbc.GoogleDriver");

	          // Open a connection
					conn =  DriverManager.getConnection(DB_URL);

	          // Execute SQL query
					stmt = (Statement) conn.createStatement();
					String sql;
					sql = "SELECT schoolcode, pincode FROM data";
					rs = stmt.executeQuery(sql);
					while(rs.next()){
	              //Retrieve by column name
						int id  = rs.getInt("schoolcode");
						int val = rs.getInt("pincode");
						out.println("ID: " + id + "<br>");
						out.println(", Val: " + val + "<br>");
	              
					}
					rs.close();
					stmt.close();
					conn.close();
	      } catch(Exception e){
	    	 resp.getWriter().println(e.toString());
	      }
		} else {
			final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
			final String DB_URL="jdbc:mysql://localhost:3306/TEST?";
			java.sql.Connection conn = null;
			Statement  stmt = null;
			ResultSet   rs = null;
		
			//  Database credentials
			final String USER = "root";
			final String PASS = "riorobles";
			try{
				PrintWriter out = resp.getWriter();	          // Register JDBC driver
				Class.forName("com.mysql.jdbc.Driver");

          // Open a connection
				conn =  DriverManager.getConnection(DB_URL, USER, PASS);

          // Execute SQL query
				stmt = (Statement) conn.createStatement();
				String sql;
				sql = "SELECT schoolcode, pincode FROM data";
				rs = stmt.executeQuery(sql);
				while(rs.next()){
              //Retrieve by column name
					int id  = rs.getInt("schoolcode");
					int val = rs.getInt("pincode");
					out.println("ID: " + id + "<br>");
					out.println(", Val: " + val + "<br>");
              
				}
				rs.close();
				stmt.close();
				conn.close();
      } catch(Exception e){
    	 resp.getWriter().println(e.toString());
      }

			
		}
	}
	public void doPost(HttpServletRequest request, 
	         HttpServletResponse response)
	        throws ServletException, IOException
	  {
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();

	    out.println("<h2>Button Clicked</h2>");

	    int schoolcode = Integer.parseInt(request.getParameter("schoolcode"));
	    int pincode = Integer.parseInt(request.getParameter("pincode"));
	    int ramp = Integer.parseInt(request.getParameter("ramp"));
	    int library = Integer.parseInt(request.getParameter("library"));

	    if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
	    	
	    } else {
	    	
	    	final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
			final String DB_URL="jdbc:mysql://localhost:3306/TEST?";
			java.sql.Connection conn = null;
			Statement  stmt = null;
			ResultSet   rs = null;
		
			//  Database credentials
			final String USER = "root";
			final String PASS = "riorobles";
			String sql;
			try{
				Class.forName("com.mysql.jdbc.Driver");
				conn =  DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = (Statement) conn.createStatement();
				sql = "insert into data values(" + schoolcode + "," + pincode + "," + ramp +"," + library + ")";
				stmt.executeUpdate(sql);
			}catch(Exception e) {
				response.getWriter().println(e.toString());
			}

	    	
	    }
	    	    
	    out.close();
	  }
}
