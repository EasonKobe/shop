		var lyr1 = $('<div class="PUI-ajax-block" style="z-index: 1000; border: medium none; margin: 0px; padding: 0px; ' +
			'width: 100%; height: 100%; top: 0px; left: 0px; background-color: rgb(0, 0, 0);' +
			'cursor: wait; position: fixed;"> ' +
			'</div>');
		lyr1.css('opacity', '0.3');
		var lyr2 = $('<div class="PUI-ajax-block" style="z-index: 1011; position: fixed; padding: 5px; margin: 0px; display: none;' +
			'width: 150px; top: 40%; left:' + ($(window).width() - 166) / 2 + 'px; text-align: center; color: rgb(0, 0, 0); ' +
			'border: 3px solid #777777; ' +
			'background-color: rgb(255, 255, 255); cursor: wait;">' +
			'<img style="margin-right: 3px; margin-top: -5px; vertical-align: middle;" src="codehelper/images/busy.gif"> ' + "处理中,请稍后..." +
			'</div>');
		
		var f = true;
		var block = function() {
			lyr1.css({"cursor": "wait"});
			if ($(".PUI-ajax-block").length == 0 || $(".PUI-ajax-block:eq(0):hidden").length == 1) {
				lyr1.appendTo("body");
				lyr2.appendTo("body");
				$(".PUI-ajax-block").fadeIn(true ? 200 : 0);
			} else {
				f = false;
			}
		};
		window.block = block;
		var unblock = function() {
			f = true;
			setTimeout(function() {
				if (f) {
					lyr1.css({"cursor": "default"});
					$(".PUI-ajax-block").fadeOut(true ? 400 : 0);
				}
			}, true ? 400 : 0);
		};
		window.unblock = unblock;
		$(document).ajaxStart(block).ajaxStop(unblock);
		$(window).resize(function () {
			if ($(".PUI-ajax-block").length != 0) {
				$(".PUI-ajax-block:eq(1)").css({
					top: '40%',
					left: ($(window).width() - 166) / 2
		        });
			}
	    });
	