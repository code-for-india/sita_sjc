<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="com.google.appengine.api.utils.SystemProperty" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>


<html>
<head>

<%
int flag;
ResultSet   result = null;
if (SystemProperty.environment.value() ==
    SystemProperty.Environment.Value.Production) {
    final String JDBC_DRIVER="com.mysql.jdbc.GoogleDriver";  
				final String DB_URL="jdbc:google:mysql://utssrc:testdb/TEST?user=root";
				java.sql.Connection conn = null;
				Statement  stmt = null;
					Class.forName("com.mysql.jdbc.GoogleDriver");
					conn =  DriverManager.getConnection(DB_URL);

	          		stmt = (Statement) conn.createStatement();
					String sql;
					sql = "SELECT * FROM data";
					result = stmt.executeQuery(sql);
				    

} else {

	final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
			final String DB_URL="jdbc:mysql://localhost:3306/TEST?user=root&password=riorobles";
			java.sql.Connection conn = null;
			Statement  stmt = null;
			
			Class.forName("com.mysql.jdbc.Driver");
			conn =  DriverManager.getConnection(DB_URL);
			stmt = (Statement) conn.createStatement();
				String sql;
				sql = "SELECT *FROM data";
				result = stmt.executeQuery(sql);
		
}

 %>
 



<style type="text/css">
html {
	height: 100%
}
body {
	height: 100%;
	margin: 0;
	padding: 0
}
#map-canvas {
	height: 100%
}
</style>
<script type="text/javascript"
	src="https://maps.googleapis.com/maps/api/js?libraries=visualization&sensor=false">
    </script>
<script type="text/javascript">
    
      function setmarkerinfowindows(lat,long,schoolname,ramp,library, map, heatmapData)
	  {
	  
	  var myLatlng = new google.maps.LatLng(lat, long)
	  
	  
	  var marker = new google.maps.Marker({
      position: myLatlng,
      title:"Hello World!"
      });
      

      
      var contentString = '<div id="content">'+
      '<div id="siteNotice">'+
      '</div>'+
      '<h1 id="firstHeading" class="firstHeading">' + schoolname + '</h1>'+
      '<div id="bodyContent">'+
      '<p> Ramp condition : <b>' + ramp + '/5 </b> </p>'+
      '<p> Library : <b>' + library + '/5 </b> </p>' +
      '</div>'+
      '</div>';
      
        var infowindow = new google.maps.InfoWindow({
        content: contentString,
        position: myLatlng
        
        });
        
        
          	      var rectangle = new google.maps.Rectangle({
    	strokeColor: '#FF0000',
    	strokeOpacity: 0.0,
    	strokeWeight: 2,
   		 fillColor: '#FF0000',
    	fillOpacity: 0.0,
    	map: map,
    	bounds: new google.maps.LatLngBounds(
      new google.maps.LatLng(lat-0.01, long-0.01),
      new google.maps.LatLng(lat+0.01, long+0.01 ))
  	});
  	    
        
 	    
  	    var wt_avg = 5 - (ramp+library)/2;
  	    var heatdat = [
  	    {location : myLatlng, weight : wt_avg} ];
  	    
  	    heatmapData = heatdat.concat(heatmapData);

  	    

        // To add the marker to the map, call setMap();
        marker.setMap(map);
        google.maps.event.addListener(rectangle, 'click', function() {
        infowindow.open(map);
        setTimeout(function () { infowindow.close(); }, 2000);
        });
  	    
	    
  	    marker.setVisible(false);
  	    return heatmapData;
	  }
	  
	  
	  
      function initialize1() {
      
      var myLatlng = new google.maps.LatLng(-34.397, 150.644)
      
        var mapOptions = {
          center: myLatlng,
          zoom: 12,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById("map-canvas"),
            mapOptions);
            
                  var schoolname = 'Adarsh Vidyalaya';
      var ramp = 1;
      var library = 2;
      
      var heatmapData = [ ];
      
 <%
 	while(result.next()){

 
     String sname = result.getString("schoolname");
     double lat = result.getDouble("lat");
	 double lng = result.getDouble("lng");
	 int ramp = result.getInt("ramp");
	 int library = result.getInt("library");
     
     %>
   schoolname = "<%= sname %>";
   
   library = <%= library %>;
   lat = <%= lat %>;
   lng = <%= lng %>;
   ramp = <%= ramp %>;
   heatmapData = setmarkerinfowindows(lat,lng,schoolname,ramp,library,map,heatmapData );
   
   <%
   }
   %>  
    
      
    	var heatmap = new google.maps.visualization.HeatmapLayer({
  		data: heatmapData,
  		radius: 20
		});
		heatmap.setMap(map);
  
  
     }

    function initialize() {
           
      
      	initialize1();
      }


      google.maps.event.addDomListener(window, 'load', initialize);
    </script>
</head>
<body>
	<div id="map-canvas" />
</body>
</html>
