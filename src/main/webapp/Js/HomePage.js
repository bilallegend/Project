$(document).ready(function(){
	
	
	
	var pusher = new Pusher('63f35f26a75b722e22cf', {
        cluster: 'eu',
        authEndpoint: '/auth/live',
        encrypted: true
	});
	var channel =pusher.subscribe('presence-live');
	var channelname= 'presence-live';
		
	
	
	   $("#playonline").click(function(){
		 $.post("/ajax/getOnlineMembers",{},function(data,status){
			 if(data!="null"){
				location.href=data;
			 }
			
		});
	});


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
	$.post("/ajax/profile",{num:usercookie},function(data,status){
		
		
		console.log(data);
		if(data.indexOf("<!DOCTYPE")==-1){
		var obj=JSON.parse(data);
	   alert(obj);
		if(obj.mail!=""&&obj.name!=""){
			
			$("#signup").css({
    			"transform": "scale(0,0)",
    			"transition": "1.5s"
    		});
    		setTimeout(function () {
    	        $(".whole").css("filter","blur(0px)");
    			$("#menu").css({
    				"transform": "scale(1,1)",
    				"transition": "1s"
    			});
    			$("#sgnup").css("display", "none");
    			$("#sig").css("display", "none");
    			$(".profil").css("display", "block");
    			$(".menu").css("display", "block");
    		}, 700);
			var src="http://localhost:8080"+obj.photo;
			
		document.getElementById("img").style.backgroundImage = "url('"+src+"')";
		document.getElementById("img1").style.backgroundImage = "url('"+src+"')";
		$("#name").text(obj.name);
		$("#mail").text(obj.mail);
		}
		
		$("#currtour").text(obj.currtour);
		}
	});
	
	$("#reg").click(function(){
		
		var name=$("#usrname_up").val();
		var email=$("#usrmail_up").val();
		var pass=$("#usrpassword_up").val();
		var num=$("#usrnum_up").val();

		var file=$("#file").val();	
		

		
		

		//if($("#errname").css("visibility")=="hidden"&&$("#errmail").css("visibility")=="hidden"&&$("#errpass").css("visibility")=="hidden"&&$("#errnum").css("visibility")=="hidden"&&$("#errpassword").css("visibility")=="hidden"){
		 $.post("/signup",

				 {
			          name: name,
			          email:email,
			          pass:pass,
			          confirm:$("#usrconfirm_up").val(),
			          num:num,
		              
		              
			        },
		            			        
			        
			        function(data,status){
			            
			            console.log(typeof(data));
			            var resultobj=JSON.parse(data);
			        
			            //(resultobj.status);
			            
			            if(resultobj.status=="Signup Successful"){

        		 $("#data").submit();
			            }
			            else if(resultobj.status=="Invalid data"){
			            	
			            	var list=(resultobj.errors).split("&");
			            	for(i=1;i<list.length;i++){
			            		
			            		$(list[i])[0].style.visibility="visible";
			            	}
			            	
			            	
			            }
			            
			        });
	});
		

	

	$("#login").click(function(){
		
		 $.post("/signin",
			        {
			          name: $("#usrname_in").val(),
			          email:$("#usrmail_in").val(),
			          pass:$("#usrpassword_in").val()
			          
			        },
			        function(data,status){
			           
			           
			            var resultobj=JSON.parse(data);
			            var l=["errnamin","errpassin","errmailin"];
			            var s=["50","150","100"];
			            var i=s.indexOf(resultobj.status);
			            if(i!=-1){
			            	$("#"+l[0])[0].style.visibility="hidden";
			            	$("#"+l[1])[0].style.visibility="hidden";
			            	$("#"+l[2])[0].style.visibility="hidden";
			                $("#"+l[i])[0].style.visibility="visible";
			            }
			            
			            if(resultobj.status=="200"){
			            $("#signin").css({
			    			"transform": "scale(0,0)",
			    			"transition": "1.5s"
			    		});
			    		setTimeout(function () {
			    		    $(".whole").css("filter","blur(0px)");
			    			$("#menu").css({
			    				"transform": "scale(1,1)",
			    				"transition": "1s"
			    			});
			    			$("#sgnup").css("display", "none");
			    			$("#sig").css("display", "none");
			    			$(".profil").css("display", "block");
			    			$(".menu").css("display", "block");
			    		}, 700);
			    		$("#name").text(resultobj.name);
		        		$("#mail").text(resultobj.mail);
		        		document.getElementById("img").style.backgroundImage = "url('"+resultobj.photo+"')";
		        		document.getElementById("img1").style.backgroundImage = "url('"+resultobj.photo+"')";
			        }
			        });
	});
	
	$(".tournament").click(function(){
		
		location.href="http://localhost:8080/tournament";
	});
	
	$("#Edit").click(function(){
		
		$("#menu").css('display','none');
		$(".edit1").css('display','block');
		$.post("ajax/edit1",{},function(data){
			
			console.log(data);
			var detail=JSON.parse(data);
			$("#ename").val(detail.name);
			$("#email").val(detail.mail);
			$("#enumber").val(detail.number);
			document.getElementById("ephoto").style.background="url('"+detail.photo+"')";
			
		});
		
	});
	
	$("#editprofile").click(function(){
		
		var namecheck=($("#ename").val()).match(/^[a-z0-9]{3,30}$/gi);
	    var emailcheck=($("#email").val()).match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/gi);
	   // var passcheck=pass.match(/[a-z0-9_-]{6,10}$/g);
	    var numcheck=($("#enumber").val()).match(/^[0-9-()+]{10,15}/g);
	    
	   

      var li=[$("#ename").val(),$("#email").val(),$("#enumber").val()];
	    
		var checklist=[namecheck,emailcheck,numcheck];
		
		var id=["#na","#mai","#no"];
		var c=0;
		for(i=0;i<checklist.length;i++){
			if(checklist[i]+""==li[i]){
				
				c+=1;
			}
			else{
				
				$(id[i])[0].style.visibility="visible";
			}
			
		}
		if(c==3){
			$.post("ajax/changeprofile",{name:$("#ename").val(),mail:$("#email").val(),number:$("#enumber").val()},function(data){
				
				var result=JSON.parse(data);
				console.log(result);
				if(result.status=="ok"){
					$("#data1").submit();
				}
				else{
					
					var list=(resul.errors).split("&");
	            	for(i=1;i<list.length;i++){
	            		
	            		$(list[i])[0].style.visibility="visible";
	            	}
				}
				
			});
			
		}
		
		
	})
	
	
	
	$('#feedsFlow').on('click','div[name=feedsClick]',function(){
		let divId = $(this).attr('id');
		var data = JSON.stringify({
			divId:divId
        });
		
			$.post('/ajax/getVideo', data,
	                function (msg) {
	            		console.log(msg)
	            		location.href='http://localhost:8080/home'+msg.redir;
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

var check=function(){
	
	var name=$("#usrname_up").val();
	var email=$("#usrmail_up").val();
	var pass=$("#usrpassword_up").val();
	var confirm=$("#usrconfirm_up").val();
	var num=$("#usrnum_up").val();
	
	var namecheck=name.match(/^[a-z0-9]{3,30}$/gi);
    var emailcheck=email.match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/gi);
    var passcheck=pass.match(/[a-z0-9_-]{6,10}$/g);
    var numcheck=num.match(/^[0-9-()+]{10,15}/g);


    
	var checklist=[namecheck,emailcheck,passcheck,numcheck];
	var check=[name,email,pass,num];
	var id=["#errname","#errmail","#errpass","#errnum","#errpassword"];
	
	for(i=0;i<checklist.length;i++){
		
		if(checklist[i]==check[i]){
			$(id[i])[0].style.visibility="hidden";
			
		}
		else{
			$(id[i])[0].style.visibility="visible";
		}
	}
	
	if(pass==confirm){
		$(id[4])[0].style.visibility="hidden";
		
	}
	else{
		$(id[4])[0].style.visibility="visible";
	}
	
	
	
}

var check1=function(){
	
//	$.post("/ajax/signin",
//	        {
//	          name: $("#usrname_in").val(),
//	          email:$("#usrmail_in").val(),
//	          pass:$("#usrpassword_in").val()
//	          
//	        },
//	        function(data,status){
//	        	console.log(data +"vg");
//	        	var resultobj=JSON.parse(data);
//	        	
//	        	if(resultobj.status=="200"){
//	        		console.log("Login Successful");
//	        		$("#errnamin")[0].style.visibility="hidden";
//	        		$("#errmailin")[0].style.visibility="hidden";
//	        		$("#errpassin")[0].style.visibility="hidden";
//	        	}
//	        	else if(resultobj.status=="150"){
//	        		console.log("Invalid password");
//	        		$("#errpassin")[0].style.visibility="visible";
//	        	}
//	        	else if(resultobj.status=="100"){
//	        		console.log("Invalid mail");
//	        		$("#errnamin")[0].style.visibility="hidden";
//	        		$("#errpassin")[0].style.visibility="hidden";
//	        		$("#errmailin")[0].style.visibility="visible";
//	        	}
//	        	else if(resultobj.status=="50"){
//	        		console.log("Invalid username");
//	        		
//	        		$("#errnamin")[0].style.visibility="visible";
//	        		$("#errmailin")[0].style.visibility="hidden";
//	        		$("#errpassin")[0].style.visibility="hidden";
//	        	}
//	        	
//	        });
//	
	
	
	
}
	
	




