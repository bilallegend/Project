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
			
			var obj=JSON.parse(data);
		
			$("#first").html(obj.members);
			$("#di").html(obj.extra);
//			$("#join").style.visibility=obj.join;
			
		});
		$("#join").click(function(){
			
			//$(this).style.visibility="hidden";
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
			
					$("#first").html(obj.members);
					$("#di").html(obj.extra);
				//	$("#join").style.visibility=obj.join;
				});
				
			}
			else{
				alert("You should be signed in to join");
			}
			
		});
				
	
});