 $(document).ready(function () {
	 $.post("/ajax/checkingplayers",{},function(data,status){
 		alert(data);
 		console.log(data);
 		var obj=JSON.parse(data);
 		gameid=obj.gameid;
 		if(obj.color=="white"){
 		  $("#white").text(obj.player1name);
 		  $("#black").text(obj.player2name);
 		}
 		else{
 			$("#black").text(obj.player1name);
 			$("#white").text(obj.player2name);
 		}
 		
 	});
 });