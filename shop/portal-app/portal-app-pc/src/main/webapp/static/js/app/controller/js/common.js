window.onload=function(){

	// 判断当前页面位置
	if(getScrollTop()>0){
		headAnimation();
	}

	window.addEventListener("scroll", function(){
		if(getScrollTop()>0){
			headAnimation();
		}else{
			$(".head").css("height", "100px");
			$(".logo").css("margin-top", "21.5px");
			$(".h_rcnt").css("padding", "39px 0px");
		}
	})
}

function getScrollTop(){
	return document.documentElement.scrollTop||document.body.scrollTop;
}

function headAnimation(){
	$(".head").css("height", "60px");
	$(".logo").css("margin-top", "0px");
	$(".h_rcnt").css("padding", "18px 0px");
}