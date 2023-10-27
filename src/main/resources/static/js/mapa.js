const btnUbicacionUsuario = $(".btnMiUbicacion");
// Este es el objeto que vamos a enviar al back, usamos la latitud y long para el envio
// y usamos el resto de los datos para "la factura" y autocompletar el form
let direccion = {
    latitud: "",
    longitud: "",
    calle: "",
    altura: "",
    ciudad: "",
    pais: "",
    codigoPostal: ""
},
marker,
latGeo,
longGeo,
map;



btnUbicacionUsuario.click(function () {
    window.navigator.geolocation.getCurrentPosition(function (succ) {
        latGeo = succ.coords.latitude,
        longGeo = succ.coords.longitude;
        mandarFetchAGoogle(latGeo, longGeo);
    });
})

function mandarFetchAGoogle(latitude, longitude) {
    const miKey = "AIzaSyCaPimjSNLT64mwlXQ_PGyZAz0nWKsCNGY";
    let url = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&key=` + miKey;

    fetch(url, {
        method: 'GET'
    }).then(response => response.json())
        .then(data => mostrarDatos(data));
}

function mostrarDatos(data) {
    let direcciones;//Almaceno todas las direcciones, google me trae por defecto 6 diferentes direcciones
    let direccionMaps;//Esta es la variable que voy a devolver al final, con una direccion que tenga el type = "street_address"
    if (data.status == "OK") {
        direcciones = data.results;
    } else {
        throw Exception("Algo no funco , Error=" + data.status);
    }

    direcciones.forEach(element => {
        let salir = false;
        element.types.forEach(tipoDireccion => {
            if (tipoDireccion == "street_address") {
                salir = true;
                direccionMaps = element;
            }
        })
        if (salir) {
            return;
        }
    });
    console.log(direccionMaps);
    direccion = toDirection(direccionMaps)
    console.log(direccion);
    marker.setPosition({lat : latGeo , lng : longGeo});
    //centrar el mapa
}


function initMap() {
    // The location of Uluru
    let uluru = { lat: -34.573064003040365, lng: -58.4285070382385 };
    // The map, centered at Uluru
    map = new google.maps.Map(document.querySelector(".contenedorMapa__map"), {
        zoom: 3,
        center: uluru,
    });
    // The marker, positioned at Uluru
    marker = new google.maps.Marker({
        position: uluru,
        map: map,
    });
} 

function toDirection(direction){
    direccion.altura = direction.address_components[0];
    direccion.calle = direction.address_components[1];
    direccion.ciudad = direction.address_components[2];
    direccion.pais = direction.address_components[5];
    direccion.codigoPostal = direction.plus_code.global_code;
    direccion.latitud = latGeo;
    direccion.longitud =longGeo; 
    return direccion;
}


window.initMap = initMap;