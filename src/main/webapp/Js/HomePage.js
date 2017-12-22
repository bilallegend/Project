$(document).ready(function(){
	
	$('#quit').click(function(){
		$(".feeds").css("transform", "translateX(-100%)");
		$(".feed").show();
	    $(".whole").css("filter","blur(0px)");
	});
	
	
	var pusher = new Pusher('63f35f26a75b722e22cf', {
        cluster: 'eu',
        authEndpoint: '/auth/live',
        encrypted: true
	});
	
	var channel ;
	
	
	
	var channelname= 'presence-live';
		
	
	
	   $("#playonline").click(function(){
		 $.post("/ajax/getOnlineMembers",{},function(data,status){
			 if(data!="null"){
				location.href=data;
			 }
			
		});
	});

	$("#prof").click(function(){
		
		location.href="/home/MyInfo";
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
		
		
		
		if(data.indexOf("<!DOCTYPE")==-1){
			con();
		var obj=JSON.parse(data);
	  
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
			var src=obj.photo;
			
		document.getElementById("img").style.backgroundImage = "url('"+src+"')";
		document.getElementById("img1").style.backgroundImage = "url('"+src+"')";
		$("#name").text(obj.name);
		$("#mail").text(obj.mail);
		}
                else{
			$(".sign").css('display','block');
		}
		
		$("#currtour").text(obj.currtour);
		}
               else{
			$(".sign").css('display','block');
		}
	});
	
	$("#reg").click(function(){
		
		var name=$("#usrname_up").val();
		var email=$("#usrmail_up").val();
		var pass=$("#usrpassword_up").val();
		

		var file=$("#file").val();	
		

		
		check();

	if($("#errname").css("visibility")=="hidden"&&$("#errmail").css("visibility")=="hidden"&&$("#errpass").css("visibility")=="hidden"&&$("#errpassword").css("visibility")=="hidden"){
		 $.post("/signup",

				 {
			          name: name,
			          email:email,
			          pass:pass,
			          confirm:$("#usrconfirm_up").val()
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
	}
	});
		

	

	$("#login").click(function(){
		
		 $.post("/signin",
			        {
			          name: $("#usrname_in").val(),
			          pass:$("#usrpassword_in").val()
			          
			        },
			        function(data,status){
			           
			           
			            var resultobj=JSON.parse(data);
			            var l=["errnamin","errpassin"];
			            var s=["50","150"];
			            var i=s.indexOf(resultobj.status);
			            if(i!=-1){
			            	$("#"+l[0])[0].style.visibility="hidden";
			            	$("#"+l[1])[0].style.visibility="hidden";
			            	
			                $("#"+l[i])[0].style.visibility="visible";
			            }
			            
			            if(resultobj.status=="200"){
			            	location.href="/home";
			        }
			        });
	});
	
	$(".tournament").click(function(){
		
		location.href="http://localhost:8080/tournament";
	});
	
	$("#Edit").click(function(){
		
		$("#menu").css('display','none');
		$(".edit1").css('display','block');
		$.post("ajax/edit",{},function(data){
			
			console.log(data);
			var detail=JSON.parse(data);
			$("#ename").val(detail.name);
			$("#email").val(detail.mail);
			$("#enumber").val(detail.number);
			
                        document.getElementById("ephoto").style.background="url('"+detail.photo+"')";
		       $("#ephoto").css('background-size','cover');
			$("#ephoto").css('border-radius','50%');
			$(".hi").css('background','none');
			
		});
		
	});
	
	$("#editprofile").click(function(){
		
		var namecheck=($("#ename").val()).match(/^[a-z0-9]{3,30}$/gi);
	    var emailcheck=($("#email").val()).match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/gi);
	    
	    var im=$("#file").val();
	   

      var li=[$("#ename").val(),$("#email").val()];
	    
		var checklist=[namecheck,emailcheck];
		
		var id=["#na","#mai"];
		var c=0;
		for(i=0;i<checklist.length;i++){
			if(checklist[i]+""==li[i]){
				
				c+=1;
			}
			else{
				
				$(id[i])[0].style.visibility="visible";
			}
			
		}
		if(c==2){
			$.post("ajax/changeprofile",{name:$("#ename").val(),mail:$("#email").val()},function(data){
				
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
		
		
	});
	
	$("#out").click(function(){
		
		$.post("ajax/signout",function(data){
			
			location.href="/home";
		});
		
	});
	
	 $("#chan-pass").click(function(){
                $("#menu").css('display','none');
                $(".edit1").css('display','none')
 		$(".change").css("transform","translateY(35%)"); 
 	 });
	
	$("#change").click(function(){
		
		 var passcheck=($("#curr").val()).match(/[a-z0-9_-]{6,10}$/g);
		 var passcheck1=($("#new").val()).match(/[a-z0-9_-]{6,10}$/g);
		 var passcheck2=($("#new1").val()).match(/[a-z0-9_-]{6,10}$/g);
		  
		 if(passcheck!=null&&passcheck1!=null&&passcheck2!=null){
		 if(passcheck[0]==($("#curr").val())&&passcheck1[0]==($("#new").val())&&passcheck2[0]==($("#new1").val())&& $("#new").val()==$("#new1").val()){
		
		$.post("ajax/changepass",{ curr:$("#curr").val(),newpass:$("#new").val(),newpass1:$("#new1").val()},function(data){
			
			 var re=JSON.parse(data);
			  if(re.status=="ok"){
				  location.href="/home";
			  }
			  else{
				  alert(re.status);
			  }
			
			
		   });
		
		 }
		 else{
			 if((passcheck[0]==($("#curr").val()))==false){
				 alert("Invalid current password")
			 }
			 else if((passcheck1[0]==($("#new").val()))==false){
				 
            	 alert("Invalid new password");
			 }
             else if((passcheck2[0]==($("#new1").val()))==false){
            	 alert("Invalid new password");
			 }
             else if($("#new").val()!=$("#new1").val()){
            	 alert("Password didn't match");
             }
		 }
   }
  else{
			 if(passcheck==null){
				 alert("Invalid current password")
			 }
			 else if(passcheck1==null){
				 
            	 alert("Invalid new password");
			 }
             else if(passcheck2[0]==null){
            	 alert("Invalid new password");
			 }
		 }
		
	});
	 
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
	var subscribe=false;

	$('button[name=toWatch]').click(function(){
		let html = $(this).html();
		$("#feedsFlow").html("");
		$('button[name=toWatch]').removeClass('hover');
		$(this).addClass('hover');
		var data = JSON.stringify({
        });
		if(html=='Live' && subscribe==false){
			$('#di').html('Signup or signin to Watch live');
			return;
		}
		post(data,'/ajax/get'+html);
		 
                console.log(data);
            // trigger a server-side endpoint to send the message via Pusher
            
	});
	function post(data,url){
		$.post(url, data,
                function (msg) {
            		console.log(msg)
            		view(msg)
//                    $('#feedsFlow').append(msg.html);
                }, "json");
	}
	 
	 function view(data){
		 let source = $("#feeds-template").html(); 
			let template = Handlebars.compile(source);
			$('#feedsFlow').append(template(data));
	            
	 }
	var con=function(){
		channel =pusher.subscribe('presence-live');
		pusher.connection.bind('connected', function () {
			socket_id = pusher.connection.socket_id;
			
			 channel.bind('pusher:subscription_succeeded', function (t) {
				 console.log('pusher:subscription_succeeded');	 
				 subscribe=true;
			 });
	
			 channel.bind('pusher:subscription_error',function(member){
				 console.log("pusher:subscription_error");
				 subscribe=false;
				
			 });
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
	}

	
});

var check=function(){
	
	var name=$("#usrname_up").val();
	var email=$("#usrmail_up").val();
	var pass=$("#usrpassword_up").val();
	var confirm=$("#usrconfirm_up").val();
	
	
	var namecheck=name.match(/^[a-z0-9]{3,30}$/gi);
    var emailcheck=email.match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/gi);
    var passcheck=pass.match(/[a-z0-9_-]{6,10}$/g);
   


    
	var checklist=[namecheck,emailcheck,passcheck];
	var check=[name,email,pass];
	var id=["#errname","#errmail","#errpass","#errpassword"];
	
	for(i=0;i<checklist.length;i++){
		
		if(checklist[i]==check[i]){
			$(id[i])[0].style.visibility="hidden";
			
		}
		else{
			$(id[i])[0].style.visibility="visible";
		}
	}
	
	if(pass==confirm){
		$(id[3])[0].style.visibility="hidden";
		
	}
	else{
		$(id[3])[0].style.visibility="visible";
	}
	
	
	
}

var check1=function(){
	
}
	
	










