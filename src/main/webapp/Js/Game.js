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
    $(document).ready(function () {
    	
    	
    	
    	$(".gete").click(function () {
    		
    		 
    		
    		var id=$(this).attr("id");
    		if(id.length==5){
    		var i=id[3]+id[4];
    		}
    		else{
    			var i=id[3];	
    		}
    		gq(i,id);
    		
    		
    	});
    	var cookie=document.cookie;
    	var list=cookie.split("; ");
    	usercookie="0";
    	var count=0;
    	 gameid="";
    	for(i=0;i<list.length;i++){
    		var l=list[i].split("=");
    		if(l[0]=="gc_account"){
    			usercookie=l[1];
    			count+=1;
    			break;
    		}
    	}
    	if(usercookie="0"){
    		location.href="http://localhost:8080/home";
    	}
    	alert(usercookie);
    	
    	$.post("/ajax/checkingplayers",{num:usercookie},function(data,status){
    		alert(data);
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
    
    var connection=function(){
    	var pusher = new Pusher('63f35f26a75b722e22cf', {
            cluster: 'eu',
            //authEndpoint: '/auth',
            encrypted: true
    	});
    	 console.log(pusher);
    	 channel_name = "presence-play-" + gameid;
    	
    	 channel = pusher.subscribe(channel_name);
    	 
    	 watching_channel = pusher.subscribe("presence-watch-" + gameid);
    	 
    	 channel.bind('pusher:subscription_succeeded',function(members){
    		 
 			console.log(" pusher:subscription_succeeded channel");
 			
 		});
    	 pusher.connection.bind('connected', function () {
         // subscribe to new messages in the chat application
         channel.bind('new_message', function (data) {
            
        	 
        	 
         });
         
         channel.bind('pusher:member_removed', function (member) {
         	console.log(" pusher:member_removed ");
             // track member removals from channel
            
         });
         channel.bind('pusher:member_added', function (member) {
         	console.log(member);
             // track member additions to channel
             
         });

         // track socket_id to exclude recipient in subscription
         socket_id = pusher.connection.socket_id;
        });
    }
   
	
    var date;
    
    var save = 0;

    function gq(a) {
      console.log(a);
      onCoin.push(a);
    
        if (white.indexOf(a) == -1 && black.indexOf(a) == -1) {

            inter = document.getElementById("box" + a);
            save = Number(a);
            check(a);
            if ((count == 0) && (final == 1)) {

                count = 1;
            } else if ((count == 1) && (final == 1)) {

                count = 0;
            }
            
        } else {
            alert("Place your coin correctly")
        }
    }

    var click1 = [];
    var changecolor;

    function check(a) {
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
        var data = JSON.stringify({
            message: a,
            gameid:gameid,
            usercookie:usercookie,
            channel_id: channel_name,
            socket_id: socket_id
        });
        $.post('/ajax/move',data,function(res,status){
        	
        	
        });
        
//        colors();
    }
//
//    function colors() {
//
//        let k = Object.keys(confirm);
//        let t = save;
//        i = 0;
//        let temp;
//        while (i < k.length) {
//        	
////        	if(changecolor=="white"){
////        		$("#box"+t+" > .one").addClass("coin");
////        	}
////        	else{
////        		$("#box"+t+" > .one").addClass("coin1");
////        	}
//          //  document.getElementById("box" + t).style.background = changecolor;
//            if(changecolor=="white"){
//            	var c=$("#box"+t+" > .one").attr('class');
//           
//            	$("#box"+t+" > .one").removeClass(c)
//            	     $("#box"+t+" > div").addClass("coin");
//                      $("#box"+t+" > div").addClass("gete one");
//            	
//           
//            }
//            else if(changecolor=="black"){
//            	var c=$("#box"+t+" > .one").attr('class');
//            	 
//                 	$("#box"+t+" > .one").removeClass(c)
//                 	  $("#box"+t+" > div").addClass("coin1");
//                      $("#box"+t+" > div").addClass("gete one");
//            	
//            }
// 
//            if (color1.indexOf(t) == -1) {
//                color1.push(t);
//            }
//
//            if (color.indexOf(t) !== -1) {
//                temp = color.indexOf(t);
//                color.splice(temp, 1);
//            }
//            if (t == confirm[k[i]]) {
//                i++;
//                t = save;
//            }
//            t = (t + Number(k[i]));
//        }
//
//        if (count == 0) {
//            white = color;
//            black = color1
//        } else {
//            white = color1;
//            black = color;
//        }
//    }
//
//    function info() {
//        let tak = document.getElementById("info")
//        let p= document.getElementById("p")
//        p.innerHTML= "<pre>Black x   "+black.length +"<br>White x   "+white.length+"</pre>"
//       
//        if (count == 0) {
//            tak.innerHTML = "BLACK`s Move"
//          
//        } else {
//            tak.innerHTML = "WHITE`s Move"
//        }
//        if (white.length + black.length == 64) {
//            if (white.length > black.length) {
//                alert("White won the match")
//            } else {
//                alert("black won the match")
//            }
//          console.log(onCoin);
//        }
//    }