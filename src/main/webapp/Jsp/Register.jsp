<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Register Page</title>
	<link rel="stylesheet" href="../Css/Register.css">
	<link rel="stylesheet" href="../Css/feeds.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="../Js/jquery.js"></script>
	<script src="../Js/HomePage.js"></script>
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
				<img src="../images/close.png" alt="close" class="close" id="fir-clo" />
			</div>
			 
			<div id="nam" style="display:flex;height:130px">
			<form id="data" action="upload" method="post" enctype="multipart/form-data">
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
							<img src="../images/profile.png" />
						</div>
						
						<input type="text" name="name" id="usrname_up" onkeyup="check()"/>
					</div>
					<p id="errname">username should have atleast 6</p>
					<label class="pad-top">Email Id</label>
					<div class="dv">
						<div class="im"> <i class="fa fa-envelope-o" aria-hidden="true"></i>
						</div>
						<input type="email" name="email" id="usrmail_up" onkeyup="check()"/>
					</div>
					<p id='errmail'>username should have atleast 6</p>
					
				</div>
			</div>
			<div id="sec-div">
				<label class="pad-lef">Password</label>
				<div class="dv mar-lef">
					<div class="im"> <i class="fa fa-key i" aria-hidden="true"></i>
					</div>
					<input type="password" name="pass" class="in" id="usrpassword_up" onkeyup="check()"/>
				</div>
				<p id='errpass'>username should have atleast 6</p>
				<label class="pad-top pad-lef">Confirm Password</label>
				<div class="dv mar-lef">
					<div class="im"> <i class="fa fa-key i" aria-hidden="true"></i>
					</div>
					<input type="password" class="in" id="usrconfirm_up" onkeyup="check()"/>
				</div>
				<p id='errpassword'>username should have atleast 6</p>
				<label class="pad-top pad-lef">Mobile Number</label>
				<div class="dv mar-lef">
					<div class="im"> <i class="fa fa-mobile" aria-hidden="true" id="ph"></i>
					</div>
					<input type="text/number" name="num" class="in" maxlength="10" id="usrnum_up" onkeyup="check()"/>
				</div>
				<p id='errnum'>username should have atleast 6</p>
				<input type="submit" value="Register" class="sub" id="reg" />
				
			</div>
			
		</div>
		
		<div id="signin">
			<div id="rib">LOGIN
				<img src="../images/close.png" alt="close" class="close" id="sec-clo" />
			</div>
			<label class="pad-lef">Username</label>
			<div class="dv mar-lef" style="margin-top:0px">
				<div class="im">
					<img src="../images/profile.png" id="sty" />
				</div>
				<input type="text" class="inp" id="usrname_in" onkeyup="check1()"/>
			</div>
			<p id="errnamin" class="ale">username should have atleast 6</p>
			<label class="pad-lef pa">Email</label>
			<div class="dv mar-lef" style="margin-top:0px">
				<div class="im"><i class="fa fa-envelope-o" aria-hidden="true"></i>
				</div>
				<input type="Email" class="inp" id="usrmail_in" onkeyup="check1()" />
			</div>
			<p id="errmailin" class="ale">username should have atleast 6</p>
			<label class="pad-lef pa">Password</label>
			<div class="dv mar-lef" style="margin-top:0px">
				<div class="im"> <i class="fa fa-key i" aria-hidden="true"></i>
				</div>
				<input type="Password" class="inp" id="usrpassword_in" onkeyup="check1()"/>
			</div>
			<p id="errpassin" class="ale">username should have atleast 6</p>
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
		<a href="http://gamecenterdesign.zcodeusers.com/MMAB-PROJECT/Html/Profile.html">
		<div class="profil"></div>
		</a>
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