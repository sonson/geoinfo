/** Initialize the Google map. */
function init() {
  $("#data").hide();
  $("#working").hide();
	
  var latlng = new google.maps.LatLng(48.1, 14);
  var myOptions = {
    zoom: 3,
    center: latlng,
    mapTypeId: google.maps.MapTypeId.ROADMAP
    };
  var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

  var marker = new google.maps.Marker({
    position: latlng, 
    map: map, 
    title:"Country"
    });
  
  google.maps.event.addListener(map, "click", function(event) {
    marker.setPosition(event.latLng);
    $("#data").fadeOut();
    $("#working").fadeIn();

    var url = "http://localhost:9998/country/" + event.latLng.lat() + "/" + event.latLng.lng();
    var request = $.ajax({
            url: url,
            dataType: 'xml',
        })
        .done(function(xml) {
        	var name = $(xml).find("name").text();
        	$("#name").html("Country " + name);
        })
        .fail(function(jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        }
    );
    
    url = url + "/indicators";
    var request = $.ajax({
        url: url,
        dataType: 'xml',
    })
    .done(function(xml) {
    	var birthRate = $(xml).find("birthRate").text();
    	$("#birthRate").html(birthRate);
    	
    	var deathRate = $(xml).find("deathRate").text();
    	$("#deathRate").html(deathRate);
    	
    	var lifeExpectancyFemale= $(xml).find("lifeExpectancyFemale").text();
    	$("#lifeExpectancyFemale").html(lifeExpectancyFemale);
    	
    	var lifeExpectancyMale= $(xml).find("lifeExpectancyMale").text();
    	$("#lifeExpectancyMale").html(lifeExpectancyMale);
    	$("#working").hide();
    	$("#data").fadeIn();
    })
    .fail(function(jqXHR, textStatus) {
        alert("Request2 failed: " + textStatus);
    }
);
    
    
  });
}


