$(document).ready(function(){
	
	var pusher = new Pusher('63f35f26a75b722e22cf', {
        cluster: 'eu',
        authEndpoint: '/auth/live',
        encrypted: true
	});
	
	var channel = pusher.subscribe('presence-live-'+gameId);
	
	pusher.connection.bind('connected',function(pusher){
		
		channel.bind('pusher:subscription_succeeded',function(){
			
		});
		
		channel.bind('addNew',function(data){
			if(data.length>4){
				$('#black').html(data.B_player);$('#white').html(data.W_player);
				$('#bla').html(data.B_status);$('#whi').html(data.W_status);
			}
			ColorChange(data.Black,data.White);
			
		});
		
		function ColorChange(black,white){
			for(i of black){
				let c=$("#box"+t+" > .one").attr('class');
           	 
             	$("#box"+t+" > .one").removeClass(c)
             	  $("#box"+t+" > div").addClass("coin1");
                  $("#box"+t+" > div").addClass("gete one");
			}
			for(j of white){
				let c=$("#box"+t+" > .one").attr('class');
		           
            	$("#box"+t+" > .one").removeClass(c);
            	     $("#box"+t+" > div").addClass("coin");
                      $("#box"+t+" > div").addClass("gete one");
			}
			
		}
		
		
	});
});
