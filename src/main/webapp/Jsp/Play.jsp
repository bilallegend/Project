<!DOCTYPE HTML>
<%@ page import="HelperClasses.Cooky" %>
<%@ page import="HelperClasses.Redirecter" %>
<html>
<head>
    <title>Reversi|Online members</title>
	<link rel="stylesheet" href="../Css/Play.css">
	<link rel="stylesheet" href="../Css/Profile.css">
	<link href="https://fonts.googleapis.com/css?family=Frijole" rel="stylesheet">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<script src="https://js.pusher.com/4.1/pusher.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<link rel='icon' href=http://cdn.marketplaceimages.windowsphone.com/v8/images/befe2370-568e-4a98-84e4-2a64deb3d771?imageType=ws_icon_large'>
</head>
<body>
    <div id="ma">
        <h2>Visibility</h2>
     <div>   
       <input type="radio" name="vis" id="pri" value="private">
       <label for="pri">Private</label><br>
       <input type="radio" name="vis" id="pub" value="public">
       <label for="pub">Public</label>
      </div>
    </div>
	<main>
	    <header>
	        <div id="player"></div>
	        <div>VS</div>
	        <div id="opposite"></div>
	    </header>
	    <div>
	        <div>
	            <header>ONLINE PLAYERS</header>
	            <div id="di">
	           	
	            </div>
	        </div>
	        <div id="d" style=" text-align:center">
	             <h2>PROFILE INFO</h2>
	              <div>
                    <div id="pro"></div>
                 </div>
                <p id="name">Muthumari</p>
                <p class="tot">Total Match:</p>
                        <p class="tot">5</p>
                        <table>
                            <tr>
                                <th>Victories</th>
                                <th>Defeats</th>
                                <th>Score</th>
                            </tr>
                            <tr>
                                <td>
                                    <p id="win">25</p>
                                </td>
                                 <td>
                                    <p id="loss">20</p>
                                </td>
                                <td>
                                    <p id="score">20</p>
                                </td>
                            </tr>
                        </table>
	        </div>
	    </div>
	</main>
	
	<script>
	var privacy;
	console.log(privacy)
	var name='<%= Cooky.getContextName("gc_account", request.getCookies(),"cookie",request)%>'
	if(name=='null'|| name == null||name==""){
		location.href='<%= Redirecter.giveUrlFor(request,"/home")%>';
	}
	
	</script>
	<script src="/Js/onlinemembers.js">
	
	</script>
</body>
</html>