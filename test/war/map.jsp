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
var map,heatmap;
function convtext(val)
{
	if(val>2)
		return '<font color="green">Good</font>';
	if(val<=2 && val > 1)
	    return '<font color="orange">Fair</font>';
	if(val<=1)
	    return '<font color="red">Poor</font>';
}
    
function setmarkerinfowindows(lat,long,schoolname,ramp,library,teacher, classroom, playground, toilet, meal, map, heatmapData, pic)
{
	  
	  var myLatlng = new google.maps.LatLng(lat, long)
	  
	  
	  var marker = new google.maps.Marker({
      position: myLatlng,
      title:"Hello World!"
      });
      
      var picdisp = [];
       for(i=0;i<pic.length;i++){
    	picdisp =   picdisp + ' <a href="' + pic[i] + '">'+ 'Picture'+i+'</a> ';
		}
      
      ramp = 4 - ramp;
      library = 4 - library;
      teacher = 4 - teacher;
      classroom = 4 - classroom;
      playground = 4 - playground;
      toilet = 4 - toilet;
      meal = 4 - meal;
       var school_rank = ((ramp+library + teacher + classroom + playground + toilet + meal)/7).toFixed(2);
  	   var wt_avg = 3 - school_rank;
      var contentString = '<div id="content">'+
      '<div id="siteNotice">'+
      '</div>'+
      '<h3 id="firstHeading" class="firstHeading">' + schoolname + '</h3>'+
      '<div id="bodyContent">'+
      '<p>School Score : <b>' + school_rank + '/3 </b> </p>'+
      '<p>Classrooms : <b>' + convtext(classroom) + ' </b> <br>'+
      'Teacher Standard : <b>' + convtext(teacher) + ' </b> <br>'+
      'Playground : <b>' + convtext(playground) + ' </b> <br>'+
      'Toilet : <b>' + convtext(toilet) + ' </b> <br>'+
      'Meal Quality : <b>' + convtext(meal) + ' </b> <br>'+
       'Ramp condition : <b>' + convtext(ramp) + '</b> <br>'+
      'Library : <b>' + convtext(library) + ' </b> </p>' +
      picdisp +
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
      new google.maps.LatLng(lat-0.1, long-0.1),
      new google.maps.LatLng(lat+0.1, long+0.1 ))
  	});
  	    
        
 	    
  	    var heatdat = [
  	    {location : myLatlng, weight : wt_avg} ];
  	    
  	    heatmapData = heatdat.concat(heatmapData);

  	    

        // To add the marker to the map, call setMap();
        marker.setMap(map);
        google.maps.event.addListener(rectangle, 'click', function() {
        infowindow.open(map);
        setTimeout(function () { infowindow.close(); }, 4000);
        });
  	    
	    
  	    marker.setVisible(false);
  	    return heatmapData;
	  }
	  
	  
	  
	  
      function initialize1() {
      
      var myLatlng = new google.maps.LatLng(18, 78)
      
        var mapOptions = {
          center: myLatlng,
          zoom: 5,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
         map = new google.maps.Map(document.getElementById("map-canvas"),
            mapOptions);
            
                  var schoolname = 'Adarsh Vidyalaya';
      var ramp = 1;
      var library = 2;
	  var teacher,classroom,playground,toilet,meal;
      
      var heatmapData = [ ];
	  	  var pic = ['http://1.bp.blogspot.com/-8WqSHTro6wo/TV5ycmgKfPI/AAAAAAAACFo/HU6B1fHZJsw/s1600/school%2Blibrary.jpg',
	  			'http://playgroundsforkids.co.uk/wp-content/uploads/2013/11/playgrounddd.jpg'];
      
 <%
 	while(result.next()){

 
     String sname = result.getString("schoolname");
     double lat = result.getDouble("lat");
	 double lng = result.getDouble("lng");
	 int ramp = result.getInt("ramp");
	 int library = result.getInt("library");
	 int teacher = result.getInt("teacher");
	 int classroom = result.getInt("classroom");
	 int playground = result.getInt("playground");
	 int toilet = result.getInt("toilet");
	 int meal = result.getInt("meal");
	 
     
     %>
   schoolname = "<%= sname %>";
   
   library = <%= library %>;
   lat = <%= lat %>;
   lng = <%= lng %>;
   ramp = <%= ramp %>;
   teacher = <%= teacher %>;
   classroom = <%= classroom %>;
   meal = <%= meal %>;
   toilet = <%= toilet %>;
   playground = <%= playground %>;
 
   heatmapData = setmarkerinfowindows(lat,lng,schoolname,ramp,library,teacher, classroom, playground, toilet, meal,map,heatmapData, pic );
   
   <%
   }
   %>  
    
      
    	 heatmap = new google.maps.visualization.HeatmapLayer({
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
<div id="panel">
<img alt="" src="http://i61.tinypic.com/2zqbxmu.jpg"></img>
	</div>
	<div id="map-canvas" />
</body>
</html>
