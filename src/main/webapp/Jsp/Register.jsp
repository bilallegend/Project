<!DOCTYPE html>
<html>

<head>
	<title>Register Page</title>
	<link rel="stylesheet" href="../Css/Register.css">
	<link rel="stylesheet" href="../Css/feeds.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="../Js/jquery.js"></script>
	<link href="https://fonts.googleapis.com/css?family=Cinzel+Decorative" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Lobster+Two:400,700i" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Macondo+Swash+Caps|Playball" rel="stylesheet">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>
	<div class="whole"></div>
	<div class="blu">
		<div id="signup">
			<div id="rib">REGISTER
				<img src="../Images/close.png" alt="close" class="close" id="fir-clo" />
			</div>
			<div id="nam" style="display:flex;height:130px">
				<div class="co">
					<label id="contanier">
						<input type="file" />
					</label>
				</div>
				<div>
					<label>Name</label>
					<div class="dv">
						<div class="im">
							<img src="../Images/profile.png" />
						</div>
						<input type="text"/>
					</div>
					<p>username should have atleast 6</p>
					<label class="pad-top">Email Id</label>
					<div class="dv">
						<div class="im"> <i class="fa fa-envelope-o" aria-hidden="true"></i>
						</div>
						<input type="email" />
					</div>
					<p>username should have atleast 6</p>
				</div>
			</div>
			<div id="sec-div">
				<label class="pad-lef">Password</label>
				<div class="dv mar-lef">
					<div class="im"> <i class="fa fa-key i" aria-hidden="true"></i>
					</div>
					<input type="password" class="in" />
				</div>
				<p>username should have atleast 6</p>
				<label class="pad-top pad-lef">Confirm Password</label>
				<div class="dv mar-lef">
					<div class="im"> <i class="fa fa-key i" aria-hidden="true"></i>
					</div>
					<input type="password" class="in" />
				</div>
				<p>username should have atleast 6</p>
				<label class="pad-top pad-lef">Mobile Number</label>
				<div class="dv mar-lef">
					<div class="im"> <i class="fa fa-mobile" aria-hidden="true" id="ph"></i>
					</div>
					<input type="text/number" class="in" maxlength="10" />
				</div>
				<p>username should have atleast 6</p>
				<input type="submit" value="Register" class="sub" id="reg" />
			</div>
		</div>
		<div id="signin">
			<div id="rib">LOGIN
				<img src="../Images/close.png" alt="close" class="close" id="sec-clo" />
			</div>
			<label class="pad-lef">Username</label>
			<div class="dv mar-lef" style="margin-top:0px">
				<div class="im">
					<img src="../Images/profile.png" id="sty" />
				</div>
				<input type="text" class="inp" />
			</div>
			<p class="ale">username should have atleast 6</p>
			<label class="pad-lef pa">Email</label>
			<div class="dv mar-lef" style="margin-top:0px">
				<div class="im"><i class="fa fa-envelope-o" aria-hidden="true"></i>
				</div>
				<input type="Email" class="inp" />
			</div>
			<p class="ale">username should have atleast 6</p>
			<label class="pad-lef pa">Password</label>
			<div class="dv mar-lef" style="margin-top:0px">
				<div class="im"> <i class="fa fa-key i" aria-hidden="true"></i>
				</div>
				<input type="Password" class="inp" />
			</div>
			<p class="ale">username should have atleast 6</p>
			<input type="submit" value="Login" class="sub" id="login" />
		</div>
		<div id="menu">
			<div>
				<p><a href="http://gamecenterdesign.zcodeusers.com/MMAB-PROJECT/Html/PlayingPage.html">PLAY ONLINE</a>
				</p>
			</div>
			<div>
				<p>PLAY GUEST</p>
			</div>
			<div>
				<p><a href="http://gamecenterdesign.zcodeusers.com/MMAB-PROJECT/Html/Instruction.html">INSTRUCTIONS</a>
				</p>
			</div>
			<div class="exit">
				<p>EXIT</p>
			</div>
		</div>
		<p class="sign" id="sgnup">SIGNUP</p>
		<p class="sign sgnin" id="sig">SIGNIN</p>
	
		<div class="profil"></div>
		  <div class="profilediv">
                    <div>
                        <div><div><a href="http://gamecenterdesign.zcodeusers.com/MMAB-PROJECT/Html/Profile.html"><p>change</p></a></div></div>
                        <h5>madhubalan.m</h5>
                        <p>madhubalan.m@zohouniv.com</p>
                    </div>
                    <button type="submit" class="edit">Edit profile</button>
                    <button type="submit">Change password</button>
                    <button type="submit">signout</button>
           </div>
		<div class="menu"><i class="fa fa-bars" aria-hidden="true"></i>
		</div> <i class="fa fa-caret-right" id="aro" aria-hidden="true"></i>
		<div class="feed"> <span>Tournament ID</span>
			<p><a href="http://gamecenterdesign.zcodeusers.com/MMAB-PROJECT/Html/Tournament.html">AB-01</a></p>
			<p>Ends in</p>
			<h1>1h 29s</h1>
			<i class="fa fa-commenting-o" aria-hidden="true" id="fed"><span>Feeds</span></i>
			<i class="fa fa-caret-left" id="leftaro" aria-hidden="true"></i>
		</div>
		    <div class="feeds">
        <div class="main">
            <div>
                <div class="top-div">
                    <div class="profile"></div>
                    <p>MadhuBalan</p>
                </div>
                <div class="feedvideo"></div>
                <div class="likediv">
                    <i class="fa fa-heart like" aria-hidden="true"></i>
                    <span>0</span>
                    <span class="sec-spn">Likes</span>
                </div>
                <div class="viewdiv">
                     <span>0</span>
                     <i class="fa fa-eye" aria-hidden="true"></i>
                </div>
            </div>
            <div>
                <div class="top-div">
                    <div class="profile"></div>
                    <p>MadhuBalan</p>
                </div>
                <div class="feedvideo"></div>
                <div class="likediv">
                    <i class="fa fa-heart like" aria-hidden="true" ></i>
                    <span>0</span>
                    <span class="sec-spn">Likes</span>
                </div>
                <div class="viewdiv">
                     <span>0</span>
                     <i class="fa fa-eye" aria-hidden="true"></i>
                </div>                    
            </div>
            <div>
                <div class="top-div">
                    <div class="profile"></div>
                    <p>MadhuBalan</p>
                </div>
                <div class="feedvideo"></div>
                  <div class="likediv">
                    <i class="fa fa-heart like" aria-hidden="true"></i>
                    <span>0</span>
                    <span class="sec-spn">Likes</span>
                </div>
                <div class="viewdiv">
                     <span>0</span>
                     <i class="fa fa-eye" aria-hidden="true"></i>
                </div>                  
            </div>
        </div>
    </div>
	</div>
</body>

</html>