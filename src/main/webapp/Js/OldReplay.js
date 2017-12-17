$(document).ready(function(){
	$('.oth').html('game_'+gameId);
	$('#white').html(""+playerNames.nextplayer);
	$('#black').html(""+playerNames.firstplayer);
	StatusChanger('Waiting','Playing',$('#whi').css('color'),$('#bla').css('color'));
	var time=30;
	var moveIds= MoveIdandTimeMap.moveIds;
	var Time = MoveIdandTimeMap.Time;
	var index=0;
	
	var timeChange =function(){
		if(time >=0 && time <=30){
			$('#ti').html(time+'');
			time--;
		}
			setTimeout(timeChange,1000);
			
	};
	var delay =(30-Time[index%2])*1000;
	setTimeout(ColorChange,delay);
	
	function forLoop(array,css){
		if(array!=undefined){
			for( i of array){
				let c=$("#box"+i+" > .one").attr('class');
	         	$("#box"+i+" > .one").removeClass(c)
	         	$("#box"+i+" > div").addClass(css);
	            $("#box"+i+" > div").addClass("gete one");
			}
		}
	}
	function StatusChanger(B_Status,W_Status,B_color,W_color){
		$('#bla').html(W_Status);
		$('#whi').html(B_Status);
		$('#bla').css('color',W_color);
		$('#whi').css('color',B_color);
	}
	function ColorChange(black,white){
		black=moveIds[index];
		white=moveIds[index+1];
		time=30;
		StatusChanger($('#bla').html(),$('#whi').html(),$('#bla').css('color'),$('#whi').css('color'));
		forLoop(black,"coin1");
		forLoop(white,"coin");
		$('#countBlack').html(black.length+"");
		$('#countWhite').html(white.length+"");
		index+=2;
		delay = (30-Time[index%2])*1000;
		setTimeout(ColorChange,delay);
		
	}
	timeChange();	
});
