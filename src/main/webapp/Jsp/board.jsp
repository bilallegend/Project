<!DOCTYPE html>
<html>
<head>
   <title>Reversi|Play</title>
	<style id="stndz-style">
	div[class*="item-container-obpd"], 
	a[data-redirect*="paid.outbrain.com"],
	 a[onmousedown*="paid.outbrain.com"] { display: none !important; } a div[class*="item-container-ad"] { height: 0px !important; overflow: hidden !important; position: absolute !important; } 
	 div[data-item-syndicated="true"] { display: none !important; } 
	 .grv_is_sponsored { display: none !important; }
	  .zergnet-widget-related { display: none !important; } </style>
	  <link rel='icon' href=http://cdn.marketplaceimages.windowsphone.com/v8/images/befe2370-568e-4a98-84e4-2a64deb3d771?imageType=ws_icon_large'>
    <link rel="stylesheet" href="../Css/board.css" />
    <link rel='icon' href='http://image.rakuten.co.jp/wood-l/cabinet/sa01/toi/sa-syoku-02-03-4.jpg'>
    <link href="https://fonts.googleapis.com/css?family=Barlow+Semi+Condensed:400,500" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="../Js/jquery.js"></script>
    <script src="../Js/Game.js"></script>
    <script src="https://js.pusher.com/4.1/pusher.min.js"></script>
    <title>Reversi|GamePlay</title>
</head>
<body>
    <div class="whole-div">
        <div class="boardback">
		    <div class="boradinrdiv">
			    <div class="inrdiv"></div>
		    </div>
	    </div>
	    <h1 class="oth"></h1>
         <div class="time"><time id='ti'></time> <span id="sec">Sec</span><span class="it" id="re" >It's Remaining For Your Turn.</span></div>
         <div class="play-sta">
             <div>
                 <div class="prf" id="blaimg"></div>
                 <div>
                     <p class="pla" id="black"></p>
                     <p class=" pla play" id="bla"></p>
                 </div>
             </div>
             <div class="top">
                 <div class="prf" id="whiimg"></div>
                 <div>
                     <p class="pla" id="white"></p>
                     <p class=" pla play colo" id="whi"></p>
                 </div>
             </div>
         </div>
<!--scroe part-->
          <div class="play-sta scor">
             <div>
                <div class="gete one coin1"></div>
                 <div>
                   <span class="int">X</span>  
                    <span class="int pad" id="blackcount"></span>  
                 </div>
             </div>
             <div class="top">
                 <div class="gete one coin"></div>
                 <div>
                   <span class="int colo">X</span>   
                    <span class="int colo pad" id="whitecount"></span>  
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
        <div class="ale">
               
           </div>
    </div>
</body>
</html>