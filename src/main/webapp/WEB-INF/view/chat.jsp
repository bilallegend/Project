<!DOCTYPE HTML>
<%@ page import="com.example.appengine.pusher.ChatServlet" %>
<%@ page import="com.example.appengine.pusher.PusherService" %>
<%@ page import="java.util.Date" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../static/chat.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://js.pusher.com/4.1/pusher.min.js"></script>
</head>
<body>
<div id="chat_widget_container">
    <div id="chat_widget_main_container">
        <div id="chat_widget_messages_container">
            <div id="chat_widget_messages">
            </div>
        </div>
        <div id="chat_widget_online">
            <p>Room(<span id="chat_room_name"></span>)</p>
            <p>Online (<span id="chat_widget_counter">0</span>)</p>
            <ul id="chat_widget_online_list">
                <li></li>
            </ul>
        </div>
        <div class="clear"></div>
        <div id="chat_widget_input_container">
            <form method="post" id="chat_widget_form">
                <input type="text" id="chat_widget_input"/>
                <input type="submit" value="Chat" id="chat_widget_button"/>
            </form>
        </div>
    </div>
</div>
<script>

    <% String room = (String) request.getAttribute("room");
    if (room == null || room.length() == 0 || room.equals("null")) {
      room = "chat-" + String.valueOf(Math.round(new Date().getTime() + (Math.random() * 100)));
      request.setAttribute("room", room);
    }
    %>
    var room = '<%= (String)request.getAttribute("room")%>';
    var roomLink = '<%= ChatServlet.getUriWithChatRoom(request, (String)request.getAttribute("room")) %>';
    $('#chat_room_name').html("<a href=\"" + roomLink + "\">" + room + "<a>");

    var socket_id = null;
    // add presence prefix for authenticated presence channels.
    var channel_name = "private-" + room;
    function updateOnlineCount() {
        $('#chat_widget_counter').html($('.chat_widget_member').length);
    }

    // Connect to Pusher with auth endpoint on your server for private/presence channels
    // (default auth endpoint : /pusher/auth)
    var pusher = new Pusher('<%= PusherService.APP_KEY %>', {
        cluster: '<%= PusherService.CLUSTER %>',
        authEndpoint: '/authorize',
        encrypted: true
    });
	console.log(pusher);
    // Subscribe to the chat room presence channel, eg. "presence-my-room"
    var channel = pusher.subscribe(channel_name);
    console.log(channel);
    // bind to successful Pusher connection
    pusher.connection.bind('connected', function () {

        // show chat window once logged in and successfully connected
        $('#chat_widget_main_container').show();
        // ...
        // bind to successful subscription
        channel.bind('pusher:subscription_succeeded', function (members) {
            // receive list of members on this channel
            console.log(" pusher:subscription_succeeded ");
            var whosonline_html = '';
            console.log(members);
            updateOnlineCount();
        });
        // presence channel receive events when members are added / removed
        channel.bind('pusher:member_added', function (member) {
        	console.log(member);
            // track member additions to channel
            $('#chat_widget_online_list').append('<li class="chat_widget_member" ' +
                'id="chat_widget_member_' + member.id + '">'
                + member.info.displayName + '</li>');
            updateOnlineCount();
        });
        channel.bind('pusher:member_removed', function (member) {
        	console.log(" pusher:member_removed ");
            // track member removals from channel
            $('#chat_widget_member_' + member.id).remove();
            updateOnlineCount();
        });

        function handleMessage(data) {
            $('#chat_widget_messages').append(data.message + '<br />');
        }

        // subscribe to new messages in the chat application
        channel.bind('new_message', function (data) {
            handleMessage(data);
        });

        // track socket_id to exclude recipient in subscription
        socket_id = pusher.connection.socket_id;

        // submit the message to /chat
        $('#chat_widget_form').submit(function () {
            var chat_widget_input = $('#chat_widget_input'),
                chat_widget_button = $('#chat_widget_button'),
                message = chat_widget_input.val(); //get the value from the text input
            var data = JSON.stringify({
                message: message,
                channel_id: channel_name,
                socket_id: socket_id
            });
                console.log(data);
            // trigger a server-side endpoint to send the message via Pusher
            $.post('/message', data,
                function (msg) {
            		console.log(msg)
                    chat_widget_button.show(); //show the chat button
                    if (msg.status == "SUCCESS") {
                        chat_widget_input.val('');
                        handleMessage(msg); //display the message
                    } else {
                        alert("Error sending chat message : " + msg.status);
                    }
                }, "json");

            return false;
        });
    });
</script>
</body>
</html>
