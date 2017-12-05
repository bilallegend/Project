$(document).ready(function(){
	var pusher;var channel;var Data;
	$.post('/getRoom',{privacy:$("#pri").val()},function(response,status){
		Data = JSON.parse(response);	
	});
	
	pusher = new Pusher(Data.AppKey, {
        cluster: Data.cluster,
        authEndpoint: '/authorize',
        encrypted: true
    });
	
	channel	=	pusher.subscribe(Data.channelname);
	console.log(channel);
	pusher.connection.bind('connected',function(){
		 channel.bind('pusher:subscription_succeeded',function(){
			 $("#di").append("<h1>Hello</h1>");
			 console.log(" pusher:subscription_succeeded ");
		 });
		 
		 
	 });
	 
	
	 
});


