$(document).ready(function(){
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
        	
//        	$('#di').append(
//        			"<div id='"+member.id+"'>"
//                    +"<div></div>"
//                    +"<div>"
//            	    +"<p>"+member.info.displayName+"</p>"
//            	    +"<p>Privacy: <span id='pri'>"+privacy+"</span></p>"
//            	    +"</div>"
//            	    +"<button class='req'>Request</button>"
//            	    +"</div>"
//        			);
         	
        	console.log(member);
        });
        channel.bind('pusher:member_removed', function (member) {
        	console.log(" pusher:member_removed ");
        	console.log(member);
        });
        
        
        function handleMessage(data) {
        	if(data.name == name){
        		alert("same ")
        	}else{
	        	if(data.name !== undefined){
	        		$('#'+data.id).remove();
	        		$('#butt'+data.id).remove();
	        		
	        	}
	        	$('body').append(data.butt);
	        		$('#di').append(data.html);
        	}
        	
        }

        // subscribe to new messages in the chat application
        channel.bind('new_message', function (data) {
            handleMessage(data);
        });
        
        notifyChannel.bind('GameReq', function (data) {
            alert(data);
            $('main').append(data.msg);
        });
        
        function gameRedirect(data){
        	 if(data.redir!==undefined){
             	location.href=data.redir;
             }
        }
        
        
        notifyChannel.bind('GameResp', function (data) {
        	alert(data.redir);
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
                    	alert("success");
                    	handleMessage(msg);
                    	
//                        handleMessage(msg); //display the message
                    } else if(msg.redir !== undefined){
                    	
                        alert("Error sending chat message : " + msg.status);
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
                			alert(msg.reply);
                		}
                		
                		else if (msg.status == "SUCCESS" && msg.redir == undefined) {
                        	alert("post again");
                        	
                           // handleMessage(msg); //display the message
                        } else if(msg.redir !== undefined){
                        	
                            alert("Error sending chat message : " + msg.status);
                            location.href=msg.redir;
                        }
                    }, "json");
        	 return false;
        }
        
        $('body').on('click','button[name=accept]',function(){
        	let Reply = $(this).html();
        	alert(Reply);
        	if(Reply=="Yes" || Reply=="No"){
        		let data = JSON.stringify({
                    socket_id: socket_id,
                    reply:Reply
                });
            	post(data,'/ajax/accept');
        	}
        	
        });
        
        $("#di").on("click", "button.req", function(){
        	alert($(this).parent().attr('id'));
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


