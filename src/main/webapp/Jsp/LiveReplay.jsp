
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../Css/board.css" />
    <link href="https://fonts.googleapis.com/css?family=Barlow+Semi+Condensed:400,500" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="../Js/jquery.js"></script>
    <title>Othello Board</title>
</head>
<body>
    <div class="whole-div">
        <div class="boardback">
		    <div class="boradinrdiv">
			    <div class="inrdiv"></div>
		    </div>
	    </div>
	    <h1 class="oth"></h1>
         <div class="time">00 <span>Sec</span><span class="it">Remaining sec.</span></div>
         <div class="play-sta">
             <div>
                 <div class="prf"></div>
                 <div>
                     <p class="pla" id="black">Anulakshmi</p>
                     <p class=" pla play" id="bla">Playing</p>
                 </div>
             </div>
             <div class="top">
                 <div class="prf"></div>
                 <div>
                     <p class="pla" id="white">Anulakshmi</p>
                     <p class=" pla play colo" id="whi">Playing</p>
                 </div>
             </div>
         </div>
<!--scroe part-->
          <div class="play-sta scor">
             <div>
                <div class="gete one coin1"></div>
                 <div>
                   <span class="int">X</span>  
                    <span class="int pad">20</span>  
                 </div>
             </div>
             <div class="top">
                 <div class="gete one coin"></div>
                 <div>
                   <span class="int colo">X</span>   
                    <span class="int colo pad">20</span>  
                 </div>
             </div>
         </div>
        <div id="setshwdiv"> 
            <img src="../Images/sound.png" alt="sound" class="soundimg" />
            <a href="http://gamecenterdesign.zcodeusers.com/MMAB-PROJECT/Html/Instruction.html"><img src="../Images/help.png" alt="help" class="hlpimg" /></a>
            <a href="http://gamecenterdesign.zcodeusers.com/MMAB-PROJECT/Html/Register.html"><img src="../Images/home.png" alt="home" class="homeimg" /></a>
        </div>
        <div id="setdiv">
            <img src="../Images/sett.png" />
        </div>
    </div>
    <script>
    var gameId= '<%=request.getAttribute("LiveId")%>';
    $('.oth').html(gameId);
    </script>
    <script src='../Js/LiveReplay.js'>
    </script>
</body>
</html>