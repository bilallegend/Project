<!DOCTYPE HTML>
<html>
    <head>
       <title>Reversi|MyProfile</title>
       <link rel='icon' href=http://cdn.marketplaceimages.windowsphone.com/v8/images/befe2370-568e-4a98-84e4-2a64deb3d771?imageType=ws_icon_large'>
        <link rel="stylesheet" href="../Css/Profile.css">
        <link href="https://fonts.googleapis.com/css?family=Arima+Madurai" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.js"></script>
        <script id="pro-template" type="text/x-handlebars-template"> 
			<div id="pro">
                <div>
                    <div>
                        <h2>PROFILE INFO</h2>
                        <div class="pr">
                            <div style="background:url({{photo}});background-size: 100% 100%;">
								
							</div>
                        </div>
                        <p>{{name}}</p>
                    </div>
                    <div>
                        <p>Total Match:</p>
                        <p>{{total_match}}</p>
                        <table>
                            <tr>
                                <th>Victories</th>
                                <th>Score</th>
                                <th>Defeats</th>
                            </tr>
                            <tr>
                                <td>
                                    <p>{{victories}}</p>
                                </td>
                                 <td>
                                    <p>{{score}}</p>
                                </td>
                                <td>
                                    <p>{{defeats}}</p>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
		</script>
    </head>
    <body>
        <div class="whole">
           
        </div>
        <script src='../Js/MyInfo.js'></script>
    </body>
</html>