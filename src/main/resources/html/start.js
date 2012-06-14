/** Initialize the Google map. */
function init() {
    jQuery.support.cors = true;
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
    var url = "http://localhost:9998/country/" + event.latLng.lat() + "/" + event.latLng.lng();
    var request = $.ajax({
            url: url,
//            dataType: 'json', // Doesn't work -> Use XML
            dataType: 'xml',
        })
        .done(function(xml) {
        	var name = $(xml).find("name").text();
        	$("#name").html("Country " + name);
        })
        .fail(function(jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        });
  });
}


