window.onload=function(){
	let index=0;
	$(".b_i_lbtn").click(()=>{
		let num=$(".b_i_sm").children().length;
		let imgArr=$(".b_i_sm").children("img");
		imgArr[index].className="";
		if(index==0){
			index=num-1;
		}else{
			index-=1;
		}
		imgArr[index].className="b_i_sld";
		$(".b_i_mi").attr("src", $(imgArr[index]).attr("src"));
	});
	$(".b_i_rbtn").click(()=>{
		let num=$(".b_i_sm").children().length;
		let imgArr=$(".b_i_sm").children("img");
		imgArr[index].className="";
		if(index==(num-1)){
			index=0;
		}else{
			index+=1;
		}
		imgArr[index].className="b_i_sld";
		$(".b_i_mi").attr("src", $(imgArr[index]).attr("src"));
	});

	let num=$(".b_i_sm").children().length;
	let imgArr=$(".b_i_sm").children("img");
	for(let i=0;i<num;i++){
		$(imgArr[i]).mouseover(()=>{
			index=i;
			chooseImg();
			imgArr[i].className="b_i_sld";
			$(".b_i_mi").attr("src", $(imgArr[i]).attr("src"));
		})
	}
}

function chooseImg(){
	let num=$(".b_i_sm").children().length;
	let imgArr=$(".b_i_sm").children("img");
	for(let i=0;i<num;i++){
		imgArr[i].className="";
	}
}