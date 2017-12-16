$(document).ready(function(){
	$('#white').html(playerNames.nextplayer);
	$('#black').html(playerNames.firstplayer);
	StatusChanger('Waiting','Playing');
	var time=30;
	var moveIds= MoveIdandTimeMap.moveIds;
	var Time = MoveIdandTimeMap.Time;
	var index=0;
	
	var timeChange =function(){
		if(time >=0 && time <=30){
			$('time').html(time+'');
			time--;
		}else{
			setTimeout(timeChange,1000);
		}	
	};
	
	setTimeout(ColorChange(moveIds[index],moveIds[index+1]),(30-Time[index%2])*1000);
	
	function forLoop(array,css){
		for( i of array){
			let c=$("#box"+i+" > .one").attr('class');
         	$("#box"+i+" > .one").removeClass(c)
         	$("#box"+i+" > div").addClass(css);
            $("#box"+i+" > div").addClass("gete one");
		}
	}
	function StatusChanger(B_Status,W_Status,B_color,W_color){
		$('#bla').html(W_Status);
		$('#whi').html(B_Status);
		$('#bla').css('color',W_color);
		$('#bla').css('color',B_color);
	}
	function ColorChange(black,white){
		time=30;
		StatusChanger($('#bla').html(),$('#whi').html());
		forLoop(black,"coin1");
		forLoop(white,"coin");
		$('#countBlack').html(black.length+"");
		$('#countBlack').html(white.length+"");
		index+=2;
		setTimeout(ColorChange(moveIds[index],moveIds[index+1]), (30-Time[index%2])*1000);
		
	}
	timeChange();	
});
