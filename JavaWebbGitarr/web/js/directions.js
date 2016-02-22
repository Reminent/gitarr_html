function initMap() {
    var origin_place_id = null;
    var destination_place_id = null;
    var travel_mode = google.maps.TravelMode.WALKING;
    var map = new google.maps.Map(document.getElementById('map'), {
        mapTypeControl: false,
        center: {
            lat: 62.39,
            lng: 17.28
        },
        zoom: 10
    });
    var directionsService = new google.maps.DirectionsService;
    var directionsDisplay = new google.maps.DirectionsRenderer;
    directionsDisplay.setMap(map);

    var start = document.getElementById('origin-input');
    var end = document.getElementById('destination-input');
    var modes = document.getElementById('mode-selector');

    map.controls[google.maps.ControlPosition.TOP_LEFT].push(start);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(end);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(modes);

    var origin_autocomplete = new google.maps.places.Autocomplete(start);
    origin_autocomplete.bindTo('bounds', map);
    var destination_autocomplete = new google.maps.places.Autocomplete(end);
    destination_autocomplete.bindTo('bounds', map);

    function setupClickListener(id, mode) {
        var radioButton = document.getElementById(id);
        radioButton.addEventListener('click', function() {
            travel_mode = mode;
        });
    }
    setupClickListener('changemode-walking', google.maps.TravelMode.WALKING);
    setupClickListener('changemode-transit', google.maps.TravelMode.TRANSIT);
    setupClickListener('changemode-driving', google.maps.TravelMode.DRIVING);

    function expandViewportToFitPlace(map, place) {
        if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
        } else {
            map.setCenter(place.geometry.location);
            map.setZoom(17);
        }
    }

    origin_autocomplete.addListener('place_changed', function() {
        var place = origin_autocomplete.getPlace();
        if (!place.geometry) {
            window.alert("Autocomplete's returned place contains no geometry");
            return;
        }
        expandViewportToFitPlace(map, place);

        origin_place_id = place.place_id;
        route(origin_place_id, destination_place_id, travel_mode, directionsService, directionsDisplay);
    });

    destination_autocomplete.addListener('place_changed', function() {
        var place = destination_autocomplete.getPlace();
        if (!place.geometry) {
            window.alert("Autocomplete's returned place contains no geometry");
            return;
        }
        expandViewportToFitPlace(map, place);

        destination_place_id = place.place_id;
        route(origin_place_id, destination_place_id, travel_mode, directionsService, directionsDisplay);

    });

    function route(origin_place_id, destination_place_id, travel_mode, directionsService, directionsDisplay) {
        if (!origin_place_id /*|| !destination_place_id*/) {
            return;
        }
        var start = document.getElementById('origin-input').value;
        var end = "Ågatan 13, Sundsvall, Sverige";  //document.getElementById('destination-input').value;
        directionsService.route({
            origin: start,
            destination: end,
            travelMode: travel_mode
        }, function(response, status) {
            if (status === google.maps.DirectionsStatus.OK) {
                directionsDisplay.setDirections(response);
                directionsDisplay.setPanel(document.getElementById('right-panel'));
            } else {
                window.alert('Directions request failed due to ' + status);
            }
        });

    }
}

