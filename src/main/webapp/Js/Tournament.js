$(document).ready(function(){
	
	
		var cookie=document.cookie;
		var list=cookie.split("; ");
		var usercookie="0";
		var count=0;
		for(i=0;i<list.length;i++){
			var l=list[i].split("=");
			if(l[0]=="gc_account"){
				usercookie=l[1];
				count+=1;
				break;
			}
		}
		$.post("/ajax/joinedmembers",{num:usercookie},function(data,status){
			
			console.log(data);
			console.log(data[2053]+" "+data[2054])
			var obj=JSON.parse(data);
			console.log(obj);
			$("#first").html(obj.members);
			$("#di").html(obj.extra);
			$("#name").text(obj.currtour);
			document.getElementById("join").style.visibility=obj.join;
			if((obj.extra).length>10){
				for(k=1;k<9;k++){
					if(obj[k+""].contains("null")){
						document.getElementById(k).style.backgroundImage = "url('')";
					}
					else{
					document.getElementById(k).style.backgroundImage = "url('"+obj[k+""]+"')";
					}
				}
			}
			
		});
		$("#join").click(function(){
			
			document.getElementById("join").style.visibility="hidden";
			var cookie=document.cookie;
			var list=cookie.split("; ");
			var usercookie="";
			var count=0;
			for(i=0;i<list.length;i++){
				var l=list[i].split("=");
				if(l[0]=="gc_account"){
					usercookie=l[1];
					count+=1;
					break;
				}
			}
			
			if(count==1){
				
				$.post("/ajax/join",{num:usercookie},function(data,status){
					
					var obj=JSON.parse(data);
					console.log(obj);
					$("#first").html(obj.members);
					$("#di").html(obj.extra);
					document.getElementById("join").style.visibility=obj.join;
					if((obj.extra).length>10){
						for(k=1;k<5;k++){
							document.getElementById(k).style.backgroundImage = "url('"+obj[k+""]+"')";
						}
					}
				});
				
			}
			else{
				alert("You should be signed in to join");
			}
			
		});
				
	
});