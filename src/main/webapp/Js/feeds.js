$(document).ready(function(){

	var pusher = new Pusher('63f35f26a75b722e22cf', {
        cluster: 'eu',
        authEndpoint: '/auth',
        encrypted: true
	});
	
	var channel = pusher.subscribe('presence-live');
	var channelname= 'presence-live';
	
	pusher.connection.bind('connected', function () {
		socket_id = pusher.connection.socket_id;
		 channel.bind('pusher:subscription_succeeded', function (t) {
			 
			 var data = JSON.stringify({
	            });
	                console.log(data);
	            // trigger a server-side endpoint to send the message via Pusher
	            $.post('/ajax/getLive', data,
	                function (msg) {
	            		console.log(msg)
	            		view(msg)
//	                    $('#feedsFlow').append(msg.html);
	                }, "json");
			 
			 
		 });
		
		 function view(data){
			 $('#feedsFlow').append(msg.html);
		 }
		 
		 channel.bind('pusher:member_added', function (member){
			 
		 });
		 
		 
		 channel.bind('pusher:member_removed', function (member) {
			 console.log("member");
			 console.log(member);
			 
		 });
		 
		 channel.bind('PlayLive',function(data){
			 view(data)
			 
		 });
		 
	});
	
});