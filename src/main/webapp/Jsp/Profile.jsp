<!DOCTYPE HTML>
<html>
    <head>
        <link rel="stylesheet" href="../Css/Profile.css">
        <link href="https://fonts.googleapis.com/css?family=Arima+Madurai" rel="stylesheet">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0/handlebars.js"></script>
        <script id="pro-template" type="text/x-handlebars-template"> 
			<div id="pro">
                <div>
                    <div>
                        <h2>PROFILE INFO</h2>
                        <div>
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
                                <th>Draws</th>
                                <th>Defeats</th>
                            </tr>
                            <tr>
                                <td>
                                    <p>{{victories}}</p>
                                </td>
                                 <td>
                                    <p>{{draws}}</p>
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