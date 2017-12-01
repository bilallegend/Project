$(document).ready(function(){
	var name;
    var  webSocket =new WebSocket('ws://anulakshmim-5073.zcodeusers.com/websocket/'+name);
    webSocket.onerror = function(event) {
            onError(event)
          };
    webSocket.onopen = function(event) {
            onOpen(event)
          };
    webSocket.onmessage = function(event) {
            onMessage(event)
          };
    webSocket.onclose=function(event){
              alert('hi');
          }
    function send() {
             webSocket.send("mo");
          } 
    function onMessage(event) {
     
            if((event.data).indexOf("wants to play with you")!=-1){
               document.getElementById('demo').innerHTML+="<div class='d1'><p>"+event.data+"</p><br><br><button onclick='accept("+(event.data)[0]+")'>Accept</button><button onclick='cancle("+(event.data)[0]+")'>Cancle</button></div>";
               }
            else if((event.data).indexOf("gamecenter.zcodeusers.com")!=-1){    
                location.href="http://gamecenter.zcodeusers.com/game";
               }
            else if((event.data).indexOf("refused to play with you")!=-1){
              alert(event.data);
               }
            else{
                var l=(event.data).split("&");
                document.getElementById('demo').innerHTML="";
                for(i=1;i<l.length-1;i++){
                var l1=l[i].split(",");
                  if(l1[1]!=l[l.length-1]){ 
                     document.getElementById('demo').innerHTML+= "<br><br><div class='d' onclick='invite("+l1[1]+")'><center>"+ l1[0]+"</center></div>";
                     }
                   }
                }
          }

    function onOpen(event) {
            console.log('Connection established');
          }
    function onError(event) {
            alert(event.data);
          }
    function invite(id){
            webSocket.send(id);
          }
    function accept(i){
            webSocket.send("ok&"+i);
          }
    function cancle(j){
            webSocket.send("no&"+j);
          }
	
});