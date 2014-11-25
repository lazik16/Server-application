/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var wsUri = "ws://apsync.duckdns.org/ROPR/anth";
var output;
var message;

function init()
{
    output = document.getElementById("outputBox");
    testWebSocket();
}

function testWebSocket()
{
    websocket = new WebSocket(wsUri);
    
    websocket.onopen = function(evt) {
        onOpen(evt)
    };
    websocket.onclose = function(evt) {
        onClose(evt)
    };
    websocket.onmessage = function(evt) {
        onMessage(evt)
    };
    websocket.onerror = function(evt) {
        onError(evt)
    };
}

function onOpen(evt)
{
    writeToScreen("CONNECTED");
}

function onClose(evt)
{
    writeToScreen("DISCONNECTED");
}

function onMessage(evt)
{
    //var data = Handler.getResult(evt.data);
    writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data + '</span>');
}

function onError(evt)
{
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function doSend()
{
    message = document.getElementById("control:inputBox").value;
    websocket.send(message);
}

function writeToScreen(message)
{
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}

window.addEventListener("load", init, false);