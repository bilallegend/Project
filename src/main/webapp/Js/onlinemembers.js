$(document).ready(function(){
	
	
         $.post("ajax/load",function(data){
		
		var o=JSON.parse(data);
		document.getElementById("player").style.background="url('"+o.photo+"')";
		document.getElementById("player").style.backgroundSize="contain";
	});
	$(document).on("click",".img",function(){

		var id=$(this).parent().attr('id');
	
		var name=$("#"+id+"name").text();
		$.post("/ajax/getdetail",{name:name},function(data){
			
			var object=JSON.parse(data);
			console.log(object);
			$("#win").text(object.win);
			$("#score").text(object.score);
			$("#loss").text(object.score);
			$("#name").text(name);
			if(object.photo==undefined){
				document.getElementById("pro").style.background="url('../Images/pr.png')";
			}
			else{
			document.getElementById("pro").style.background="url('"+object.photo+"')";
			}
			document.getElementById("pro").style.backgroundSize="contain";
			$(".tot").text(Number(object.win)+Number(object.loss));
			
		});
		
	});
	
	//Pusher Connection
	
	if(privacy=="null" || privacy == ""||privacy == null ||privacy=="public"){
    	$('input:radio[name=vis]')[1].checked=true;
    	privacy = "public";
    }else if(privacy=="private"){
    	$('input:radio[name=vis]')[0].checked=true;
    }
	
	function getChildren(){
		let id=[];
		$('#di div').each(function(e){
			id.push(this.id);
		});
		return id;
	}
	
	
	
	var pusher = new Pusher('63f35f26a75b722e22cf', {
        cluster: 'eu',
        authEndpoint: '/auth',
        encrypted: true
	});
	
	console.log(pusher);
	socket_id = pusher.connection.socket_id;
	console.log(socket_id+" ")
	var channel = pusher.subscribe('presence-online-room');
	var channelname= 'presence-online-room';
	
	console.log(channel);
	pusher.connection.bind('connected', function () {
        // ...
		socket_id = pusher.connection.socket_id;
		var notifyChannel = pusher.subscribe('private-MyNotification-'+socket_id);
		var notifyChannelname='private-MyNotification-'+socket_id;
		console.log(socket_id+"  connection")
        // bind to successful subscription
		var myID=null;
		var childId=null;
		var name=null;
		notifyChannel.bind('pusher:subscription_succeeded',function(members){
			console.log(" pusher:subscription_succeeded notifyChannelname");
		});
		
		
        channel.bind('pusher:subscription_succeeded', function (t) {
            // receive list of members on this channel
            console.log(" pusher:subscription_succeeded ");
            $("#di").html("");
            childId=getChildren();
            console.log(childId);
           myID=t.myID;
           name = t.me.info.displayName;
           onPrivacyChange();
            console.log(t);
            
        });
        
        
     // presence channel receive events when members are added / removed
        channel.bind('pusher:member_added', function (member) {
        	console.log(" pusher:member_added ");
        	console.log(member);
        });
        channel.bind('pusher:member_removed', function (member) {
        	console.log(" pusher:member_removed ");
        	alert('hi');
        	var data=JSON.stringify({
            	id:member.id,
            	channel_id: 'presence-online-room',
                socket_id: socket_id
            });
        	$.post('/ajax/delete',data,function(data,status){
        		console.log(data.status);
        		console.log(status);
        	},'json');
        	
        	console.log(member);
        	onPrivacyChange()
        });
        
        
        function handleMessage(data) {
        	if(data.name == name){
//        		alert("same ")
        	}else{
	        	if(data.name !== undefined){
	        		$('#'+data.id).remove();
	        	}
	        		$('#di').append(data.html);
        	}
        	
        }

        // subscribe to new messages in the chat application
        channel.bind('new_message', function (data) {
            handleMessage(data);
        });
        
        notifyChannel.bind('GameReq', function (data) {
//            alert(data);
            $('main').append(data.msg);
        });
        
        function gameRedirect(data){
        	 if(data.redir!==undefined){
             	location.href=data.redir;
             }
        }
        
        
        notifyChannel.bind('GameResp', function (data) {
//        	alert(data.redir);
        	location.href=data.redir;
//        	 gameRedirect(data);
        });
        
        channel.bind('privacyHandle', function (data) {
        	console.log(data.name,name);
        	if(name == data.name){
        		if(data.privacy=='public'){
	        		$('input:radio[name=vis]')[1].checked=true;
	            	privacy = "public";
        		}else{
        			$('input:radio[name=vis]')[0].checked=true;
	            	privacy = "private";
        		}
        		
        	}
        	
        	if(data.redir== undefined && name!= data.name){
        		handleMessage(data);
        	} 
        });
        // track socket_id to exclude recipient in subscription
        

        // submit the message to /chat
        
        
        function onPrivacyChange(){
        	$("#di").html("");
        	privacy = $('input:radio[name=vis]:checked').val();
        	var data = JSON.stringify({
            	privacy:privacy,
                channel_id: 'presence-online-room',
                socket_id: socket_id
            });
                console.log(data);
            // trigger a server-side endpoint to send the message via Pusher
            $.post('/ajax/pri', data,
                function (msg) {
            		console.log(msg)
                    if (msg.status == "SUCCESS" && msg.redir == undefined) {
//                    	alert("success");
                    	handleMessage(msg);
                    	
//                        handleMessage(msg); //display the message
                    } else if(msg.redir !== undefined){
                    	
//                        alert("Error sending chat message : " + msg.status);
                        location.href=msg.redir;
                    }
                }, "json");

            return false;
        }
        
        
        function post(data,ajaxUrl){
        	$.post(ajaxUrl, data,
                    function (msg) {
                		console.log(msg)
                		if(msg.reply!==undefined){
//                			alert(msg.reply);
                		}
                		
                		if (msg.status == "SUCCESS" && msg.redir == undefined) {
//                        	alert("post again");
                        	
                           // handleMessage(msg); //display the message
                        } else if(msg.redir !== undefined){
                        	
//                            alert("Error sending chat message : " + msg.status);
                            location.href=msg.redir;
                        }
                    }, "json");
        	 return false;
        }
        
        $('body').on('click','button[name=accept]',function(){
        	let Reply = $(this).html();
//        	alert(Reply);
        	if(Reply=="Yes" || Reply=="No"){
        		let data = JSON.stringify({
                    socket_id: socket_id,
                    reply:Reply
                });
            	post(data,'/ajax/accept');
        	}
        	
        });
        
        $("#di").on("click", "button.req", function(){
//        	alert($(this).parent().attr('id'));
        	var parentid=$(this).parent().attr('id');
        	var src=document.getElementById("img"+parentid).style.backgroundImage;
        	//alert(src);
        	document.getElementById("opposite").style.background=src;
        	document.getElementById("opposite").style.backgroundSize="contain";
        	let data = JSON.stringify({
                socket_id: socket_id,
                parentID:$(this).parent().attr('id')
            });
        	post(data,'/ajax/req');
        	
        });
        
        
         
        
        $('input:radio[name=vis]').click(function () { 	
        	privacy = $('input:radio[name=vis]:checked').val();
        	let data = JSON.stringify({
        		privacy:privacy,
                channel_id: 'presence-online-room',
                socket_id: socket_id,
                again:true
            });
        	post(data,'/ajax/pri');
        });
		
	});

});


