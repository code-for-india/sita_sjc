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
					sql = "SELECT * FROM data";
					rs = stmt.executeQuery(sql);
					while(rs.next()){
	              //Retrieve by column name
						String schoolname  = rs.getString("schoolname");
						double lat = rs.getDouble("lat");
						double lng = rs.getDouble("lng");
						int ramp = rs.getInt("ramp");
						int library = rs.getInt("library");
						out.println("ID: " + schoolname + "<br>");
						out.println(", Val: " + lat +" "+lng + "<br>");
	              
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
				resp.setContentType("text/html"); 	
			    out.println("<script type=\"text/javascript\" src=\"https://maps.googleapis.com/maps/api/js?libraries=visualization&sensor=false\"></script>");

          // Open a connection
				conn =  DriverManager.getConnection(DB_URL, USER, PASS);

          // Execute SQL query
				stmt = (Statement) conn.createStatement();
				String sql;
				sql = "SELECT *FROM data";
				rs = stmt.executeQuery(sql);
				while(rs.next()){
              //Retrieve by column name
					String schoolname  = rs.getString("schoolname");
					double lat = rs.getDouble("lat");
					double lng = rs.getDouble("lng");
					int ramp = rs.getInt("ramp");
					int library = rs.getInt("library");
					out.println("ID: " + schoolname + "<br>");
					out.println(", Val: " + lat +" "+lng + "<br>");
              
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

	    
	    String schoolname = request.getParameter("schoolname");
	    double lat = Double.parseDouble(request.getParameter("lat"));
	    double lng = Double.parseDouble(request.getParameter("lng"));
	    int ramp = Integer.parseInt(request.getParameter("ramp"));
	    int library = Integer.parseInt(request.getParameter("library"));
	    int teacher = Integer.parseInt(request.getParameter("teacher"));
	    int classroom = Integer.parseInt(request.getParameter("classroom"));
	    int playground = Integer.parseInt(request.getParameter("playground"));
	    int toilet = Integer.parseInt(request.getParameter("toilet"));
	    int meal = Integer.parseInt(request.getParameter("meal"));

	    if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
	    	final String JDBC_DRIVER="com.mysql.jdbc.GoogleDriver";  
			final String DB_URL="jdbc:google:mysql://utssrc:testdb/TEST?user=root";
			java.sql.Connection conn = null;
			Statement  stmt = null;
			ResultSet   rs = null;
		
			try{
				Class.forName("com.mysql.jdbc.GoogleDriver");
				conn =  DriverManager.getConnection(DB_URL);
				stmt = (Statement) conn.createStatement();
				String sql;
				sql = "insert into data values(" + ramp + "," + library + ",'" + schoolname +"'," + lat + ","+ lng + "," + teacher + "," + classroom + "," + playground + "," + toilet  + "," +  meal +")";
				int o = stmt.executeUpdate(sql);
				if(o != 0)
					out.println("Updated");
				conn.close();
		    	stmt.close();
			} catch(Exception e){
				response.getWriter().println(e.toString());
			}
	    	
	    } else {
	    	
	    	final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
			final String DB_URL="jdbc:mysql://localhost:3306/TEST?";
			java.sql.Connection conn = null;
			Statement  stmt = null;
				
			//  Database credentials
			final String USER = "root";
			final String PASS = "riorobles";
			String sql;
			try{
				Class.forName("com.mysql.jdbc.Driver");
				conn =  DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = (Statement) conn.createStatement();
				sql = "insert into data values(" + ramp + "," + library + ",'" + schoolname +"'," + lat + ","+ lng + "," + teacher + "," + classroom + "," + playground + "," + toilet  + "," +  meal +")";
				int o = stmt.executeUpdate(sql);
				if(o != 0)
					out.println("Updated");
				conn.close();
		    	stmt.close();
			}catch(Exception e) {
				response.getWriter().println(e.toString());
			}

	    	
	    }
	    	    
	    out.close();
	  }
}
