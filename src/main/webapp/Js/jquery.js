    $(document).ready(function () {
    	var count = 0;
    	var like = 0;
    	$("#go").click(function () {
    		$("#main").fadeTo(1000, 0);
    	});
    	setTimeout(function () {
    		$("#othello-div,#go-div").css("animation-play-state", "paused");
    	}, 4800);
    	setTimeout(function () {
    		$("#gif-div").css("display", "block");
    	}, 4820);
    	// /------------------------------------------ like div --------------------------------------------------------------/
    	$(".like").click(function () {
    		alert("hi");
    		like += 1;
    		alert(like);
    		if (like % 2 != 0) {
    			$(this).css("color", "red");
    		} else {
    			$(this).css("color", "rgba(221, 221, 221, 0.54)");
    		}
    	});
    	//------------------------------------------------Signout Option Div-------------------------------------------/ 
        $(".profil").click (function () {
            $(".profilediv").css("display","block");
        });
        $(".blu").click(function(){
        	    $(".profilediv").css("display","none");
        });
//-------------------------------------------- Feeds div ---------------------------------------------------------/
    	$("#aro").click(function () {
    		$(".feed").css("transform", "translateX(0)");
    		$("#aro").css("display", "none");
    // 		$("#leftaro").show(1800);
    // 		$(".whole").css("filter","blur(3px)");
    	});
    		$("#leftaro").click(function () {
    // 		$("#leftaro").hide();
    		$(".feed").css("transform", "translateX(-100%)");
    		$("#aro").show(1800);
    // 		$(".whole").css("filter","blur(0px)");
    	});
    		$("#fed").click(function () {
    		$(".whole").css("filter","blur(3px)");
    		$(".feeds").css("transform", "translateX(0)");
    // 		$("#leftaro").hide();
    		$(".feed").hide();
    	});
    		$(".feeds").click(function () {
    		$(".feeds").css("transform", "translateX(-100%)");
    		$(".feed").show();
    	   // $("#leftaro").show(1800);
    	    $(".whole").css("filter","blur(0px)");
    	});

// / ------------------------------------ The signup and signin div show animation ---------------------------------------/ 
    	$("#sgnup").click(function () {
    	    $(".feeds").css("transform", "translateX(-100%)");
    	    $(".whole").css("filter","blur(3px)");
    		$("#signup").fadeIn(1000);
    		$("#signin").css("display", "none");
    	});
    	$("#sig").click(function () {
    	    $(".feeds").css("transform", "translateX(-100%)");
    	    $(".whole").css("filter","blur(3px)");
    		$("#signin").fadeIn(1000);
    		$("#signup").css("display", "none");
    	});
    	$("#fir-clo").click(function () {
    	    $(".whole").css("filter","blur(0px)");
    		$("#signup").css("display", "none");
    	});
    	$("#sec-clo").click(function () {
    	    $(".whole").css("filter","blur(0px)");
    		$("#signin").css("display", "none");
    	});
//    	$("#reg").click(function () {
//    		$("#signup").css({
//    			"transform": "scale(0,0)",
//    			"transition": "1.5s"
//    		});
//    		setTimeout(function () {
//    	        $(".whole").css("filter","blur(0px)");
//    			$("#menu").css({
//    				"transform": "scale(1,1)",
//    				"transition": "1s"
//    			});
//    			$("#sgnup").css("display", "none");
//    			$("#sig").css("display", "none");
//    			$(".profil").css("display", "block");
//    			$(".menu").css("display", "block");
//    		}, 700);
//    	});
//    	$("#login").click(function () {
//    		$("#signin").css({
//    			"transform": "scale(0,0)",
//    			"transition": "1.5s"
//    		});
//    		setTimeout(function () {
//    		    $(".whole").css("filter","blur(0px)");
//    			$("#menu").css({
//    				"transform": "scale(1,1)",
//    				"transition": "1s"
//    			});
//    			$("#sgnup").css("display", "none");
//    			$("#sig").css("display", "none");
//    			$(".profil").css("display", "block");
//    			$(".menu").css("display", "block");
//    		}, 700);
//    	});
    	$(".exit").click(function () {
    		$("#menu").css("display", "none");
    	});
    	$(".menu").click(function () {
    		$("#menu").css("display", "block");
    	})
    	// /----------------------------------------------Flipping Action------------------------------------------------------/ 
    	// $("#setdiv").click(function(){
    	//     alert("hi");
    	//     $("#box37 > .one").css({"transform":"rotateY(90deg)","transition":"1s"})
    	// })
    	// /----------------------------------------------- Making div ---------------------------------------------------------/ 
    	for (var x = 1; x < 65; x++) {
    		document.getElementsByClassName("inrdiv")[0].innerHTML += "<div class='gete' id='box" + x + "'></div>";
    		document.getElementById('box' + x).innerHTML = "<div class='gete1 one'></div>";
    	}
    	// /---------------------------------------- The First Four Coin --------------------------------------------------------/ 
    	$("#box28 > .one").addClass("coin1");
    	$("#box29 > .one").addClass("coin");
    	$("#box37 > .one").addClass("coin1");
    	$("#box36 > .one").addClass("coin");
    	
//    	document.getElementById("#box28").style.background('black');
//    	document.getElementById("#box29").style.background('white');
//    	document.getElementById("#box37").style.background('black');
//    	document.getElementById("#box36").style.background('white');
    	
    	
    	// /------------------------------------------- Setting show Animation -------------------------------------------------/ 
    	$("#setdiv").click(function () {
    		count += 1;
    		if (count % 2 != 0) {
    			$("#setshwdiv").css({
    				"transition": "1s",
    				"opacity": "1"
    			});
    		} else {
    			$("#setshwdiv").css({
    				"transition": "1s",
    				"opacity": "0"
    			});
    		}
    	});
    	// /-----------------------------------------------------------/
    	// $(".profile").click(function(){
    	//     alert("hi");
    	//     window.location.href = "http://gamecenterdesign.zcodeusers.com/MMAB-PROJECT/Html/Profile.html";
    	// });
    });