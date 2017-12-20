<!DOCTYPE HTML>
<html>

<head>
   <title>Reversi|Winner</title>
   <link rel='icon' href=http://cdn.marketplaceimages.windowsphone.com/v8/images/befe2370-568e-4a98-84e4-2a64deb3d771?imageType=ws_icon_large'>
	<link rel="stylesheet" href="../Css/Winner.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="../Js/winner.js"></script>
</head>

<body>
	<div class="whole">
		<div>
			<div id="scor">
				<div>
					<div>HIGH SCORE</div>
					<div class="frst" id="ranking">
						
					
					</div>
				</div>
				<div id="sec">
					<div>
						<ul id="fir-ul">
							<li>
								<div id="loser"></div>
							</li>
							<li id="losername"></li>
							<li>
								<img src="../Images/star1.png">
								<div id="loserscore"></div>
							</li>
							<li>
								<div>
									<img src="../Images/cup.png">
									<div id="loserwin"></div>
								</div>
							</li>
						</ul>
						
					</div>
					<div>
						<div id="winner"></div>
						<p id="winnername"></p>
					</div>
					<div>
						<li style="float:left">
							<img src="../Images/star1.png">
							<div id="winnerscore">1234</div>
						</li>
						<li style="float:right">
							<div>
								<img src="../Images/cup.png">
								<div id="winnerwin">1</div>
							</div>
						</li>
					</div>
				</div>
			</div>
			<button id="back">Back To Home</button>
		</div>
	</div>
</body>

</html>