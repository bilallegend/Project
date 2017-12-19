    var inter;
	var x;
	var black = [28, 37];
	var white = [29, 36];
	var count = 0;
	var j = 0;
	var i = 0;
	var color1;
	var set = [-9, -8, -7, -1, 1, 7, 9, 8];
    var color;
	var click1 = [];
	var changecolor;
	var ok = 0;
	var confirm = {};
	var final = 0;
    var onCoin=[];
	var save = 0;
	var channel_name;
	var channel;
	var watching_channel;
	var usercookie;
	var gameid;
	var number="";
	var time=30;
	var watching_channelname;

 $(document).ready(function () {
    	
    	//Onload Checking
$.post("/ajax/checkingplayers",{},function(data,status){
    		
    		// console.log(data);
   		    var obj=JSON.parse(data);
    		gameid=obj.gameid;
    		if(obj.ok=="no"){
    			location.href=obj.url;
    		}
    		if(obj.color1=="White"){
    			if(obj.You=="player1"){
    		      $("#white").text("You");
    		      $("#black").text(obj.player2);
    			}
    			else{
    				 $("#white").text(obj.player1);
    	    		  $("#black").text("You");
    			}
    		  $("#whi").text(obj.status1);
    		  $("#bla").text(obj.status2);
    		  
    		}
    		else{
    			if(obj.You=="player1"){
    				$("#black").text("You");
        			$("#white").text(obj.player2);
    			}
    			else{
    			$("#black").text(obj.player1);
    			$("#white").text("You");
    			}
    			$("#whi").text(obj.status2);
      		    $("#bla").text(obj.status1);
    		}
    		
    		if($("#whi").text()=="Playing"){
    			$("#whi").css('color','firebrick');
    			$("#bla").css('color','white')
    		}
    		else{
    			$("#bla").css('color','firebrick');
    			$("#whi").css('color','white')
    		}
    		timeout();
    		gameid=obj.gameid;
    		$(".oth").text(obj.gameid);
    		connection();
    		
    	});
    	


    	var click=function(i){  		 
    		
    		var id=i;
    		if(id.length==5){  var i=id[3]+id[4]; }
    		else{ var i=id[3];}
    		gq(Number(i));
   		}
    	
    	var timeout= function(){ 
    		$("#ti").text(time+"");
    		time-=1;

    		let a=$("#white").text();
            let b=$("#black").text();	
            let status="";
            if(a=="You"){ status=$("#whi").text(); }
            else{ status=$("#bla").text(); }
            
            if(status=="Playing"&&time>0){ $("#ti").text(time+""); }
            else{
            	$("#ti").text("");
            	$("#sec").text("");
            	$("#re").text("");
             }
    		
    		if(time==0){
    		  if(status=="Playing"){
    			    onCoinMove("");
                	changestatus();
                }
              }
    		setTimeout(timeout,1000);
       	}
    //WHen click any coins
    	$(".gete").click(function () {
       	  let c=$(this).attr("id");
       	  //(c);
       	  click(c);
       	 
        });
    	
    
    	var cookie=document.cookie;
    	var list=cookie.split("; ");
    	usercookie="0";
//    	var count=0;
    	 gameid="";
    	for(i=0;i<list.length;i++){
    		var l=list[i].split("=");
   		if(l[0]=="gc_account"){
    			usercookie=l[1];
    			//count+=1;
    			break;
    		}
    	}
    	
    	if(usercookie=="0"){
    		location.href="http://localhost:8080/home";
    	}
    		
    	
    var connection=function(){
    	var pusher = new Pusher('63f35f26a75b722e22cf', {
            cluster: 'eu',
            authEndpoint: '/auth_player',
            encrypted: true
    	});
    	 // console.log(pusher);
    	 channel_name = "presence-play-" + gameid;
    	
    	 channel = pusher.subscribe(channel_name);
    	 
    	 watching_channel = pusher.subscribe("presence-live-" + gameid);
    	 watching_channelname = "presence-live-" + gameid;
    	 channel.bind('pusher:subscription_succeeded',function(members){
    		 
 			 console.log(" pusher:subscription_succeeded channel");
 			

 		});
    	 
    	 console.log(pusher.connection.state);
    	 pusher.connection.bind('connecting_in', function(delay) {
    		  alert("I haven't been able to establish a connection for this feature.  " +
    		        "I will try again in " + delay + " seconds.")
    	 });
    	 pusher.connection.bind('connecting', function (){
    		 console.log('connecting......');
    	 })
    	 pusher.connection.bind('connected', function () {
         // subscribe to new messages in the chat application
    		 
    		 watching_channel.bind('pusher:subscription_succeeded',function(){
    			 console.log('watching_channel pusher:subscription_succeeded'); 
    		 });
    		 
    		 watching_channel.bind('pusher:member_added',function(){
    			 console.log("member added");
    		 })
    		 channel.bind('pusher:member_added',function(member){
    			console.log('pusher:member_added'); 
    		 });
    		 channel.bind('pusher:member_removed',function(member){
     			console.log('pusher:member_removed',member); 
     			$('.ale').html('Sorry ! User exited the game ....!');
     			$('.ale').css('visibility','visible');
     			pusher.disconnect();
     			setTimeout(win,700)
     		 });
    		 
    		 
    		 channel.bind('colorchange', function (data) {
    			 console.log("im colorchange");
    			 console.log(data.id);
        	 if((data.status)[0]=="ok"){
     
        		 if((data.color)[0]=="White"){
        			 count=0;
        		 }
        		 else{
        			 count=1;
        		 }
        		 
        		if(data.status[0]=='ok'){
        	    	colorChange(data.black,data.white);
        	    	let id=data.color[0].toLowerCase().substring(0,3);
        			let id2= data.oppcolor[0].toLowerCase().substring(0,3);
        	    	$('#'+id).html(data.player1status[0]+"");
        	    	$('#'+id2).html(data.player2status[0]+"");
        	    	$('#'+id+'img').css('background-image','url(' + data.player1photo[0]) + ')';
        	    	$('#'+id2+'img').css('background-image','url(' + data.player2photo[0]) + ')';
        	    	$('#'+id+'img').css('background-size','cover');
        	    	$('#'+id2+'img').css('background-size','cover');
        	    	
        	    	time=30;
        	    	}	
        	 }
        	 else{
        		 //("Invalid move");
        	 }
        	 
        	 
         });
         
         channel.bind('statuschange',function(data){
        	 
        	 
     		if(data.status=="ok"){
    		 if( $("#whi").text()=="Playing"){
 			 $("#whi").text("Waiting");
 			 $("#bla").text("Playing");
 		   }
 		   else{
 			 $("#whi").text("Playing");
 			 $("#bla").text("Waiting");
 		    }
 		  if($("#whi").text()=="Playing"){
  			$("#whi").css('color','firebrick');
  			$("#bla").css('color','white')
  			}
  		else{
  			$("#bla").css('color','firebrick');
  			$("#whi").css('color','white')
  		  }
 		 
 		 	time=30;
 		 
     		}
        	 
         });
         
         channel.bind('redirect',function(data){
        	 
        	 location.href=data.redirect;
        	 
         });

         // track socket_id to exclude recipient in subscription
         socket_id = pusher.connection.socket_id;
         
         
         
        });
    }
    var date;
    var save = 0;

    function gq(a) {
    	
      // console.log(a);
      onCoin.push(a);
    
        if (white.indexOf(a) == -1 && black.indexOf(a) == -1) {

            // console.log(color,color1);
            save = Number(a);
            check(a);
            if ((count == 0) && (final == 1)) {

                count = 1;
            } else if ((count == 1) && (final == 1)) {

                count = 0;
            }
            
        } else {
            //("Place your coin correctly");
        }
    }

    var click1 = [];
    var changecolor;

    function check(a) {
    	// console.log(count);
        click1 = [];
        confirm = {};
        ok = 0;
        if (count == 0) {
            color = white;
            color1 = black;
            changecolor = "black";
        } else {
            color = black;
            color1 = white;
            changecolor = "white";
        }
         console.log(color,color1)
        i = 0;
        j = 0;
       
        while (i < 8) {
        	 
            if (Number(a) % 8 == 0) {
                if (set[i] == 1 || set[i] == -7 || set[i] == 9) {
                    i++;
                }
            }
            if (Number(a) % 8 == 1) {
                if (set[i] == -1 || set[i] == -9 || set[i] == 7) {
                    i++;
                }
            }
            if (color.indexOf(Number(a) + set[i]) !== -1) {
                click1.push(set[i]);
                ok = 1;
            }
            i++;
        }
        // console.log("click "+click1);
      check1(a);
     }
    var ok = 0;
    var confirm = {};
    var final = 0;

    function check1(a) {
        final = 0;
       let t = Number(save);
        j = 0;
        while (j < click1.length) {

            t = (t + click1[j]);
            if (color1.indexOf(t) !== -1) {
                final = 1;

                confirm[click1[j]] = t;
                j++;
                t = save;

            } else if (color.indexOf(t) == -1) {
                j++;
                t = save;
            } else if (t % 8 == 0 || t % 8 == 1) {
                if(click1[j] !== -8) {
                    if (click1[j] !== 8) {

                        t = save;
                        j++;
                    }
                }

            } else if (t < 0 || t > 64) { 
              t = save;
                j++;
            }

        }
        var k1=Object.keys(confirm);
        if(k1.length>0){
    		onCoinMove(a);	
    	}
    }
    
    
    function onCoinMove(id){
    	
    	number=$("#ti").text();
    	var data = JSON.stringify({
            message: id,
            message1:number,
            channel_id: channel_name,
            socket_id: socket_id,
            gameid:gameid
       });
            // console.log(data);
        
        $.post('/ajax/move', data,
            function (msg) {
        	    console.log(msg);
        	    
        		//(msg);
            }, "json");

        return false;
    }
    
    function forLoop(array,css){
    	for(i of array){
    	let c=$("#box"+i+" > .one").attr('class');
        
    	$("#box"+i+" > .one").removeClass(c);
    	     $("#box"+i+" > div").addClass(css);
              $("#box"+i+" > div").addClass("gete one");
    	}
    }
    
    function forChangetype(array){
    	let arr=[];
    	for(let str of array){
    		arr.push(Number(str));
    	}
    	return arr;
    }
    
    function colorChange(Black,White){
    	black=forChangetype(Black);white=forChangetype(White);
    	forLoop(Black,'coin1');forLoop(White,'coin');
    	info();
    }

    function info() {
       
       
        $("#blackcount").text(black.length)
        $("#whitecount").text(white.length)
       
        if (white.length + black.length == 64) {
            if (white.length > black.length) {
                alert("White won the match");
                
            } else {

               alert("black won the match");
              win();
            }
            location.href="http://localhost:8080/home/winner"; 
         
        }

        if(white.length==0){
        	 win();
        	 location.href="http://localhost:8080/home/winner";
      }        
        if(black.length==0){
        	 //("White won the match");
//        	 location.href="http://localhost:8080/winner";
        	 win();

        	 location.href="http://localhost:8080/home/winner";
        }
       
     }
     
    function win(){
    	
//    	var data=JSON.stringify({white:white.length,black:black.length,channel_id: channel_name});
//       $.post("/ajax/removecontext",data,function(msg){
//    		
//    	    
//    	   
//    	});
    	
    }
      
    	 });
    
    