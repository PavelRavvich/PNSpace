// Соединяемся с сервером по WEB сокету
var wsUri = "ws://" + document.location.hostname + ":" + document.location.port + document.location.pathname + "chat";
var websocket = new WebSocket(wsUri);

websocket.onopen = function (evt) {
    writeToLog("Подключен к серверу: " + evt + " " + wsUri);
};
websocket.onmessage = function (evt) {
    var data = evt.data;
    data = jQuery.parseJSON(data);

    writeToLog("Пришел ответ с сервера: " + data[0]['name'] + " число: "+data[0]['value']);
    renderToGraph(data[0]['value']);
};
websocket.onerror = function (evt) {
};
function writeToLog(text) {
    $("#log").append("<p>"+text+"</p>");
}

function socketDataSend (data) {
    websocket.send(data);
}

function renderToGraph(value) {
    if (value > 100) {
        value = 100;
    }
    $(".graph").append($("<li></li>").css({"height": value + "%"}))
}

$(document).ready(function (e) {
   $("#form").submit(function (e) {
       e.preventDefault();
       var data = $(this).serializeArray();
       socketDataSend(JSON.stringify(data));
   })
});