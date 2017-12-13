$(document).ready(function(){
	
	var data=JSON.stringify({});
	
	$.post('/ajax/getMyInfo', data,
            function (respData) {
		let source = $("#pro-template").html(); 
		let template = Handlebars.compile(source);
		$('.whole').append(template(respData));
            }, "json");
	
	
});