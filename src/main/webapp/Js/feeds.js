$(document).ready(function(){

	
	var pusher = new Pusher('63f35f26a75b722e22cf', {
        cluster: 'eu',
        authEndpoint: '/auth/live',
        encrypted: true
	});
	var channel =pusher.subscribe('presence-live');
	var channelname= 'presence-live';
	
	
	
	
	$('#feedsFlow').on('click','div[name=feedsClick]',function(){
		let divId = $(this).attr('id');
		var data = JSON.stringify({
			divId:divId
        });
		
			$.post('/ajax/getVideo', data,
	                function (msg) {
	            		console.log(msg)
//	            		view(msg)
//	                    $('#feedsFlow').append(msg.html);
	                }, "json");
	});
	
	pusher.connection.bind('connected', function () {
		socket_id = pusher.connection.socket_id;
		
		function post(data,url){
			$.post(url, data,
	                function (msg) {
	            		console.log(msg)
	            		view(msg)
//	                    $('#feedsFlow').append(msg.html);
	                }, "json");
		}
		
		
		$('button[name=toWatch]').click(function(){
			let html = $(this).html();
			var data = JSON.stringify({
            });
			post(data,'/ajax/get'+html);
			 
	                console.log(data);
	            // trigger a server-side endpoint to send the message via Pusher
	            
		});
		 channel.bind('pusher:subscription_succeeded', function (t) {
			 
			console.log('pusher:subscription_succeeded');
			 
			 
		 });
		
		 function view(data){
			 let source = $("#feeds-template").html(); 
				let template = Handlebars.compile(source);
				$('#feedsFlow').append(template(data));
		            
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