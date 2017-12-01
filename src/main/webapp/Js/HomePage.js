$(document).ready(function(){
	$("#reg").click(function(){
		
		var name=$("#usrname_up").val();
		var email=$("#usrmail_up").val();
		var pass=$("#usrpassword_up").val();
		var num=$("#usrnum_up").val();
		var file=$("#file").val();		
		
//		 $.post("/ajax/signup",
//				 {
//			          name: name,
//			          email:email,
//			          pass:pass,
//			          confirm:$("#usrconfirm_up").val(),
//			          num:num,
//		              file:file,
//		              
//			        },
//		            
//			        
//			        
//			        function(data,status){
//			            alert("Data: " + data + "\nStatus: " + status);
//			        });
		
//        event.preventDefault(); //prevent default action 
//        var post_url = $(this).attr("action"); //get form action url
//        var request_method = $(this).attr("method"); //get form GET/POST method
//        var form_data = new FormData($("#data")[0]); //Creates new FormData object
//        //var obj={name:name,email:email,pass:pass,num:num,file:file};
//        var obj="{'name':'"+name+"','email':'"+email+"','pass':'"+pass+"','num':'"+num+"','file':'"+file+"'}"
//          console.log(obj);
//        $.ajax({
//              url : "/ajax/signup",
//              type: "post",
//              datatype:"json",
//              data : JSON.stringify(obj),
//              contenttype:"multipart/form-data",
//              cache: false,
//              processData:false
//            }).done(function(response){ //
//                  alert(response);
//                  
//            });
		
		
			  var xhttp = new XMLHttpRequest();
			  xhttp.onreadystatechange = function() {
			    if (this.readyState == 4 && this.status == 200) {
			       alert(this.responseText);
			    }
			  };
			  xhttp.open("POST", "/ajax/signup", true);
			  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			  xhttp.send("name="+name+"&email="+email+"&pass="+pass+"&num="+num+"&file="+file);
			  
//			  var xhttp = new XMLHttpRequest();
//			  xhttp.onreadystatechange = function() {
//			    if (this.readyState == 4 && this.status == 200) {
//			      document.getElementById("demo").innerHTML = this.responseText;
//			    }
//			  };
//			  xhttp.open("POST", "demo_post2.asp", true);
//			  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
//			  xhttp.send("fname=Henry&lname=Ford");
	});
		

	
//	$("#data").submit(function(event){
//        event.preventDefault(); //prevent default action 
//        var post_url = $(this).attr("action"); //get form action url
//        var request_method = $(this).attr("method"); //get form GET/POST method
//        var form_data = new FormData($("#data")[0]); //Creates new FormData object
//        console.log(form_data.values())
//        $.ajax({
//             
//              url : post_url,
//              type: request_method,
//              data : form_data,
//              cache: false,
//              processData:false
//            }).done(function(response){ //
//                  alert(response);
//                  
//              });
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
	var num=$("#usrnum_up").val();
	
	var namecheck=name.match(/^[a-z0-9]{3,30}$/gi);
    var emailcheck=email.match(/^[a-z0-9._]{3,30}@\w{4,}(.com|.in|.co.in)$/gi);
    var passcheck=pass.match(/[a-z0-9_-]{6,10}$/g);
    var numcheck=num.match(/^[0-9-()+]{10,15}/g);
   

    
	var checklist=[namecheck,emailcheck,passcheck,numcheck];
	var check=[name,email,pass,num];
	var id=["#errname","#errmail","#errpass","#errnum"];
	
	for(i=0;i<checklist.length;i++){
		
		if(checklist[i]==check[i]){
			$(id[i])[0].style.visibility="hidden";
			
		}
		else{
			$(id[i])[0].style.visibility="visible";
		}
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
	
	




