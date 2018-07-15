window.onload=function(){
	reset();
	window.onresize=function(){
		reset();
	}
}

function reset(){
	let h = document.documentElement.clientHeight||document.body.clientHeight;
	if(h>810){
		let t=(h-600)/2;
		let l=(960-350)/2;
		$(".b_cnt").css("position", "absolute");
		$(".b_cnt").css("top", t);
		$(".b_cnt").css("left", l);
		$(".footer").css("position", "absolute");
		$(".footer").css("bottom", "0");
		$(".router").css("position", "absolute");
		$(".router").css("bottom", Number.parseInt($(".footer").css("height"))+60);
	}else{
		$(".b_cnt").css("position", "static");
		$(".footer").css("position", "static");
		$(".router").css("position", "static");
	}
}