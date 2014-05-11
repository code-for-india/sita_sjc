package com.test.te;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import javax.servlet.http.*;

import com.google.appengine.api.utils.SystemProperty;

@SuppressWarnings("serial")
public class MediaComments extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
		response.getWriter().println("Got");
	}
	public void doPost(HttpServletRequest request, 
	         HttpServletResponse response)
	        throws ServletException, IOException
	  {
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				} else {
					response.setContentType("text/html");
				    PrintWriter out = response.getWriter();

				    
				    String schoolname = request.getParameter("school");
				    String comment = request.getParameter("comment");
				    double lng = Double.parseDouble(request.getParameter("lng"));
				    double lat = Double.parseDouble(request.getParameter("lat"));
				    String media = request.getParameter("media");
				    
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
							sql = "insert into media values('" + media + "','" + schoolname + "','" + comment +"'," + lat + ","+ lng +")";
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
							sql = "insert into media values('" + media + "','" + schoolname + "','" + comment +"'," + lat + ","+ lng +")";
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
}