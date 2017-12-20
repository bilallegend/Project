
$(document).ready(function(){
	$.post('/ajax/showresult',{},function(data){
		
		console.log(data)
		var obj=JSON.parse(data);
		
		if(obj.status=="loser"){
			document.getElementById("loser").style.backgroundImage = "url('"+obj.photo+"')";
			$("#loser").css('background-size','cover');
			$("#losername").text(obj.name);
			$("#loserwin").text(obj.win);
			$("#loserscore").text(obj.score);
		}
		else{
			document.getElementById("winner").style.backgroundImage = "url('"+obj.photo+"')";
			$("#winner").css('background-size','cover');
//			alert(obj.name);
			$("#winnername").text(obj.name);
			$("#winnerwin").text(obj.win);
			$("#winnerscore").text(obj.score);
		}
		
		if(obj.oppstatus=="loser"){
			document.getElementById("loser").style.backgroundImage = "url('"+obj.photo1+"')";
			$("#loser").css('background-size','cover');
			$("#losername").text(obj.oppname);
			$("#loserwin").text(obj.win1);
			$("#loserscore").text(obj.score1);
		}
		else{
			document.getElementById("winner").style.backgroundImage = "url('"+obj.photo1+"')";
			$("#winner").css('background-size','cover');
//			alert(obj.oppname);
			$("#winnerwin").text(obj.win1);
			$("#winnerscore").text(obj.score1);
		}
		$("#ranking").html(obj.ranking);
			});
	
	$("#back").click(function(){
		
		location.href="http://localhost:8080/home";
	});
	
	
});
