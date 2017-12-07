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
	
	
	
	var pusher = new Pusher('c551d3cca2fce9983539', {
        cluster: 'ap2',
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
		var notifyChannel = pusher.subscribe('presence-MyNotification-'+socket_id);
		var notifyChannelname='presence-MyNotification-'+socket_id;
		console.log(socket_id+"  connection")
        // bind to successful subscription
		var myID=null;
		var childId=null;
		
		notifyChannel.bind('pusher:subscription_succeeded',function(members){
			console.log(" pusher:subscription_succeeded notifyChannelname");
		});
		
		
        channel.bind('pusher:subscription_succeeded', function (members) {
            // receive list of members on this channel
            console.log(" pusher:subscription_succeeded ");
            $("#di").html("");
            childId=getChildren();
            console.log(childId);
           myID=members.myID;
           onPrivacyChange();
            console.log(members);
            
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
        	if(data.id !== undefined){
        		$('#'+data.id).remove();
        	}
        		$('#di').append(data.html);
        	
        	
        }

        // subscribe to new messages in the chat application
        channel.bind('new_message', function (data) {
            handleMessage(data);
        });
        
        notifyChannel.bind('GameReq', function (data) {
            alert(data);
        });
        
        channel.bind('privacyHandle', function (data) {
        	if(data.redir== undefined){
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
                        if (msg.status == "SUCCESS" && msg.redir == undefined) {
                        	alert("success again");
                        	
                           // handleMessage(msg); //display the message
                        } else if(msg.redir !== undefined){
                        	
                            alert("Error sending chat message : " + msg.status);
                            location.href=msg.redir;
                        }
                    }, "json");
        	 return false;
        }
        
        $('button').click(function(){
//        	let parentID= $(this).parent().attr('id');
//        	let parentID=$(this).attr('id');
//        	console.log(parentID);
//        	let data = JSON.stringify({
//        		parentID:parentID,
//                socket_id: socket_id
//            });
        	alert($(this).attr('name'));
//        	reqUser(data,'/ajax/req');
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


