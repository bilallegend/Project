<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Reversi|Welcome</title>
	<link rel="stylesheet" href="../Css/Register.css">
	<link rel="stylesheet" href="../Css/feeds.css">
	<link rel="stylesheet" href="../Css/Instruction.css">
	<link rel='icon' href=http://cdn.marketplaceimages.windowsphone.com/v8/images/befe2370-568e-4a98-84e4-2a64deb3d771?imageType=ws_icon_large'>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="../Js/jquery.js"></script>
	<script src="../Js/HomePage.js"></script>
	<script src="https://js.pusher.com/4.1/pusher.min.js"></script>
	<link href="https://fonts.googleapis.com/css?family=Cinzel+Decorative" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Lobster+Two:400,700i" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Macondo+Swash+Caps|Playball" rel="stylesheet">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<script id="feeds-template" type="text/x-handlebars-template"> 
			{{#data}}
				<div id={{game_id}} name='feedsClick'>
				    <div class='top-div'>  
				        <div class='profile'></div>  
				        <p>{{player_1_name}} vs   {{player_2_name}}</p>  
				    </div>  
				    <div class='feedvideo'></div>  
				    <div class='likediv'>  
				        <i class=fa fa-heart like aria-hidden=true></i>  
				        <span id='likes'></span>  
				        <span class=sec-spn></span>  
				    </div>  
				    <div class='viewdiv'>  
				         <span id='views'></span>  
				         <i class=fa fa-eye aria-hidden=true></i>  
				    </div>  
				</div>
				{{/data}}
		</script>
</head>
<body>
	<div class="whole"></div>
	<div class="blu">
		<div id="signup">

			<div id="rib" class="mar rib">REGISTER
				<img src="../Images/close.png" alt="close" class="close" id="fir-clo" />
			</div>
			 
			<div id="nam" style="display:flex;height:130px">  <%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    String url= blobstoreService.createUploadUrl("/upload");
    %>
     
    <form  id='data' action="<%=url %>" method="post" enctype="multipart/form-data">
				<div class="co">
					<label id="contanier">
						<input type="file" name="photo" id="file"/>
					</label>
					</form>
				</div>
				<div>
					<label>Name</label>
					<div class="dv">
						<div class="im">
							<img src="../Images/profile.png" />
						</div>
						
						<input type="text" name="name" id="usrname_up" />
					</div>
					<p id="errname">username should have atleast 6 should not have special characters</p>
					<label class="pad-top">Email Id</label>
					<div class="dv">
						<div class="im"> <i class="fa fa-envelope-o" aria-hidden="true"></i>
						</div>
						<input type="email" name="email" id="usrmail_up"/>
					</div>
					<p id='errmail'>Inavalid mail</p>
					
				</div>
			</div>
			<div id="sec-div">
				<label class="pad-lef">Password</label>
				<div class="dv mar-lef">
					<div class="im"> <i class="fa fa-key i" aria-hidden="true"></i>
					</div>
					<input type="password" name="pass" class="in" id="usrpassword_up"/>
				</div>
				<p id='errpass'>Password should have atleast and lesser than 11 letters can contain . or _</p>
				<label class="pad-top pad-lef">Confirm Password</label>
				<div class="dv mar-lef">
					<div class="im"> <i class="fa fa-key i" aria-hidden="true"></i>
					</div>
					<input type="password" class="in" id="usrconfirm_up" />
				</div>
				<p id='errpassword'>Password didn't match</p>
				
				<input type="submit" value="Register" class="sub" id="reg" />
				
			</div>
			
		</div>
		
		<div id="signin">

			<div id="rib" class="mar rib">LOGIN
				<img src="../Images/close.png" alt="close" class="close" id="sec-clo" />
			</div>
			<label class="pad-lef">Username</label>
			<div class="dv mar-lef" style="margin-top:0px">
				<div class="im">
					<img src="../Images/profile.png" id="sty" />
				</div>
				<input type="text" class="inp" id="usrname_in" onkeyup="check1()"/>
			</div>
			<p id="errnamin" class="ale">Invalid username</p>
			
			<label class="pad-lef pa">Password</label>
			<div class="dv mar-lef" style="margin-top:0px">
				<div class="im"> <i class="fa fa-key i" aria-hidden="true"></i>
				</div>
				<input type="Password" class="inp" id="usrpassword_in" onkeyup="check1()"/>
			</div>
			<p id="errpassin" class="ale">Invalid password</p>
			<input type="submit" value="Login" class="sub" id="login" />
		</div>
		
		<div id="menu">
			<div id="playonline">
				<p><a>PLAY ONLINE</a>
				</p>
			</div>
			<div>
				<p>PLAY GUEST</p>
			</div>
			<div>

				<p><a id="instr">INSTRUCTIONS</a>
				</p>
			</div>
			<div class="exit">
				<p>EXIT</p>
			</div>
		</div>
		<p class="sign" id="sgnup">SIGNUP</p>
		<p class="sign sgnin" id="sig">SIGNIN</p>
		
		
		<div class="menu"><i class="fa fa-bars" aria-hidden="true"></i>
		</div> <i class="fa fa-caret-right" id="aro" aria-hidden="true"></i>
		<div class="feed"> <span>Tournament ID</span>
			<p class="tournament" id="currtour"></p>
			<p>Ends in</p>
			<h1>1h 29s</h1>
			<i class="fa fa-commenting-o" aria-hidden="true" id="fed"><span>Feeds</span></i>
			<i class="fa fa-caret-left" id="leftaro" aria-hidden="true"></i>
		</div>
		    <div class="feeds">
		    	<button style="position:absolute;right: 5%;" id='quit'>Quit</button>
		    	<button type="submit" class="feedbtn feedfrstbtn" name='toWatch'>Replays</button>
                <button type="submit" class="feedbtn feedsndbtn hover" name='toWatch'>Live</button>
        		<div class="main-fee" id='feedsFlow'>
        		
        		</div>
    		</div>
	</div>
	
	
	<div class="change">
	        <p id="cha-cl" style="float:right;cursor:pointer">close</p>
            <p class="cha-pas">Change Password</p>
            
          <div>
            <input type="password" id="curr" placeholder="Current Password"/><i class="fa fa-lock" aria-hidden="true"></i>
            <input type="password" id="new" placeholder="New Password"/><i class="fa fa-lock" aria-hidden="true"></i>
            <input type="password" id="new1" placeholder="Confirm Your Password"/><i class="fa fa-lock" aria-hidden="true"></i>
          </div>
          <button class="chan" id="change">CHANGE</button>
        </div>
                                                      <!--INSTRUCTION DIV-->
 <div class="who">
            <div class="main">
                 <img src="../Images/close.png" alt="close" class="close" id="thi-clo" />
                <h1>Instruction</h1>
                <h4>During your turn,put a piece onto the grid so thai it aligns with another piece of yours on a straight line,
                while at least one oponent piece is completely surrounded and becomes your piece.</h4>
                <h4>You cannot move without Caputuring oponent pieces.</h4>
                <h4>when both players cannot move, the ggame ends. the player with the most pieces turned to his corresponding color wins.</h4>
                
            </div>
        </div>
                                                    <!--EDIT PROFILE DIV-->
         <div class="edit1">
             <p class="cha-pas">PERSONAL INFORMATION</p>
              <p id="off" style="float:right;cursor:pointer">close</p>
              <form  id='data1' action="<%=url %>" method="post" enctype="multipart/form-data">
             <div class="co wi" id="ephoto">
					<label id="contanier" class="hi">
						<input type="file" id="file" name="photo" />
					</label>
				</div>
				</form>
 
         <div id="sec-div st">
				<label class="pad-lef">Name</label>
				<div class="dv mar-lef">
					<div class="im"> <div class="im">
							<img src="../Images/profile.png" class="ima" />
						</div>
					</div>
					<input type="text" id="ename" class="in" disabled />
				</div>
				<p id="na" class="e">username should have atleast 6 should not have special characters</p>
				<label class="pad-top pad-lef">Email</label>
				<div class="dv mar-lef">
					<div class="im"><i class="fa fa-envelope-o" aria-hidden="true"></i>
					</div>
					<input type="text" id="email" class="in" />
				</div>
				<p id="mai" class="e">Inavalid mail</p>
				
				<input type="submit" value="Change" class="sub" id="editprofile" />
			</div>
         </div>
	<div class="profil" id="img"></div>
		<div class="profilediv">
                    <div>
                        <div><div id="img1"></div></div>
                        <h5 id="name">madhubalan.m</h5>
                        <p id="mail">madhubalan.m@zohouniv.com</p>
                    </div>
                    <button type="submit" class="edit" id='Edit'>Edit profile</button>
                    <button type="submit" class="edit" id='prof'>View profile</button>
                    <button type="submit" id="chan-pass">Change password</button>
                    <button type="submit" id="out">signout</button>
           </div>
           
           
           
           
</body>
</html>
