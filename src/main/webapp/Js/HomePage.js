$(document).ready(function(){
	$("#reg").click(function(){
		
		var name=$("#usrname_up").val();
		var email=$("#usrmail_up").val();
		var pass=$("#usrpassword_up").val();
		var num=$("#usrnum_up").val();
		var file=$("#file").val();		
		
		 $.post("/ajax/signup",
				 {
			          name: name,
			          email:email,
			          pass:pass,
			          confirm:$("#usrconfirm_up").val(),
			          num:num,
		              
		              
			        },
		            
			        
			        
			        function(data,status){
			            alert("Data: " + data + "\nStatus: " + status);
			            $("#data").submit();
			            
			            if(data=="Signup Successful"){
			            	
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
			            }
			        });
		

			  

	});
		

	
//	$("#data").submit(function(event){
////        event.preventDefault(); //prevent default action 
////        var post_url = $(this).attr("action"); //get form action url
////        var request_method = $(this).attr("method"); //get form GET/POST method
////        var form_data = new FormData($("#data")[0]); //Creates new FormData object
////        console.log(form_data.values())
////        $.ajax({
////             
////              url : post_url,
////              type: request_method,
////              data : form_data,
////              cache: false,
////              processData:false
////            }).done(function(response){ //
////                  alert(response);
////                  
////              });
//		alert('Anu');
//       });
	
	$("#login").click(function(){
		
		 $.post("/ajax/signin",
			        {
			          name: $("#usrname_in").val(),
			          email:$("#usrmail_in").val(),
			          pass:$("#usrpassword_in").val()
			          
			        },
			        function(data,status){
			            alert("Data: " + data + "\nStatus: " + status);
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
	
	$.post("/ajax/signin",
	        {
	          name: $("#usrname_in").val(),
	          email:$("#usrmail_in").val(),
	          pass:$("#usrpassword_in").val()
	          
	        },
	        function(data,status){
	        	
	        	if(data=="200"){
	        		console.log("Login Successful");
	        		$("#errnamin")[0].style.visibility="hidden";
	        		$("#errmailin")[0].style.visibility="hidden";
	        		$("#errpassin")[0].style.visibility="hidden";
	        	}
	        	else if(data=="150"){
	        		console.log("Invalid password");
	        		$("#errpassin")[0].style.visibility="visible";
	        	}
	        	else if(data=="100"){
	        		console.log("Invalid mail");
	        		$("#errnamin")[0].style.visibility="hidden";
	        		$("#errpassin")[0].style.visibility="hidden";
	        		$("#errmailin")[0].style.visibility="visible";
	        	}
	        	else if(data=="50"){
	        		console.log("Invalid username");
	        		
	        		$("#errnamin")[0].style.visibility="visible";
	        		$("#errmailin")[0].style.visibility="hidden";
	        		$("#errpassin")[0].style.visibility="hidden";
	        	}
	        	
	        });
	
}
	
	




