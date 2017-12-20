$(document).ready(function(){
	var count=2;
	var time=30;
	var blackwin=true;
	var pusher = new Pusher('63f35f26a75b722e22cf', {
        cluster: 'eu',
        authEndpoint: '/auth/live',
        encrypted: true
	});
	
	var channel = pusher.subscribe('presence-live-'+gameId);
	
	pusher.connection.bind('connected',function(pusher){
		
		var timeChange =function(){
			if(time >=0 && time <=30){
				$('#ti').html(time+'');
				time--;
			}
				setTimeout(timeChange,1000);
				
		};
		
		function ale(data){
			$('.ale').css('visibility','visible')
			$('.ale').html(data);
			setTimeout(function(){location.href='/home'},1000)
		}
		
		channel.bind('pusher:member_removed',function(member){
			let data = JSON.stringify({
				game_id:gameId,
				id:member.id
	        });
			$.post('/ajax/memberRemove',data,function(data){
				if(data.count!=undefined){
					if(blackwin){
						ale("\tMatch Finished...!\nWinner is "+$('#black').html());
					}else{
						ale("\tMatch Finished...!\nWinner is "+$('#white').html());
					}
					
					
				}				
			},'json');
		});
		
		
		channel.bind('pusher:subscription_succeeded',function(){
			console.log('pusher:subscription_succeeded = presence-live-'+gameId);
			let data = JSON.stringify({
				game_id:gameId
	        });
			$.post('/ajax/LiveDetails',data,function(data){
				if(data.alert!=undefined){
					location.href=data.redir
//					alert(data.alert);
					
				}else{
					ColorChange(data.Blackcoins,data.Whitecoins);
					$('#black').html(''+data.Black);
					$('#bla').html(''+data[data.Black]);
					$('#whi').html(''+data[data.White]);
					$('#white').html(''+data.White);
					 
				}				
			},'json');
		});
		
		channel.bind('addNew',function(data){
			time=30;
			console.log('addNew....Working......./');
			console.log(data);
			let id=data.color[0].toLowerCase().substring(0,3);
			let id2= data.oppcolor[0].toLowerCase().substring(0,3);
			
			
			$('#'+id).html(data.player1status[0]);
	    	$('#'+id2).html(data.player2status[0]);
			ColorChange(data.black,data.white);
			
		});
		function StatusChanger(B_Status,W_Status,B_color,W_color){
			$('#bla').html(W_Status);
			$('#whi').html(B_Status);
			$('#bla').css('color',W_color);
			$('#bla').css('color',B_color);
		}
		
		function forLoop(array,css){
			for( i of array){
				let c=$("#box"+i+" > .one").attr('class');
           	 
             	$("#box"+i+" > .one").removeClass(c)
             	  $("#box"+i+" > div").addClass(css);
                  $("#box"+i+" > div").addClass("gete one");
			}
		}
		function ColorChange(black,white){
			forLoop(black,"coin1");
			forLoop(white,'coin');
			if(black.length > white.length){
				blackwin=true;
			}else{
				blackwin=false;
			}
			
			$('#countBlack').html(black.length+"");
			$('#countWhite').html(white.length+"");
			StatusChanger($('#bla').html(),$('#whi').html(),$('#bla').css('color'),$('#whi').css('color'));
			
		}
		
		
		
		
	});
});
