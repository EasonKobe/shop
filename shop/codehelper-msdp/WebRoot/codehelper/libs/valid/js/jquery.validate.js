if (!("sns" in window)) {
    window.sns = {};
}
$.extend(sns, {
    valid: {
        init: function(form) {
            var elementValue = function(element) {
                var type = $(element).attr("type");
                var val = $(element).val();

                if (type === "radio" || type === "checkbox") {
                    return $("input[name='" + $(element).attr("name") + "']:checked").val();
                }

                if (typeof val === "string") {
                    return val.replace(/\r/g, "");
                }
                return val;
            };
            this.elementValue = elementValue;

            var check = function(element) {
                var rules = {
                    required: "data-required",
                    email: "data-email",
                    url: "data-url",
                    date: "data-date",
                    number: "data-number",
                    digits: "data-digits",
                    minlength: "data-minlength",
                    maxlength: "data-maxlength",
                    min: "data-min",
                    max: "data-max",
                    equalTo: "data-equalTo"
                };
                var val = elementValue(element);
                for (var method in rules) {
                    if (rules.hasOwnProperty(method)) {
                        var param = element.attr(rules[method] + "-param");
                        if (element.attr(rules[method]) != undefined && element.attr(rules[method]) != "" && !sns.valid.methods[method].call(this, val, element, param)) {
                            sns.valid.error(element, element.attr(rules[method]));
                            return false;
                        }
                    }
                }
                return true;
            };

            var optional = function(element) {
                var val = elementValue(element);
                return ! sns.valid.methods["required"].call(this, val, element);
            };
            this.optional = optional;

            var getLength = function(value, element) {
                if (element.is("select")) {
                    return $("option:selected", element).length;
                } else if (element.is("input")) {
                    if ((/radio|checkbox/i).test(element.attr("type"))) {
                        return $(document).find("[name='" + element.attr("name") + "']").filter(":checked").length;
                    }
                }
                return value.length;
            };
            this.getLength = getLength;

            var recover = function(element) {
            	if ($(element).attr("data-errorqtip") != undefined) {
            		$(element).popover("destroy");
                    if ($(element).attr("data-errorqtip") != "") {
                    	$(element).removeAttr("data-errorqtip");
                    	$(element).css("border", "1px solid #CCCCCC");
                    }
                }
            }
            this.recover = recover;
            
            $.fn.isValid = function() {
                var isValid = true;
                $(this).find("input, select, textarea").not(":submit, :reset, :image, [disabled], :hidden").each(function() {
                	recover($(this));
                    isValid = check($(this)) && isValid;
                });

                // ------------------------------------chosen下拉框插件支持 Start------------------------------------
                $(this).find("select").each(function() {
                    var s = $(this);
                    if (s.next("div.chzn-container").length == 1) {
                    	var a = s.next("div.chzn-container").find("a").first();
                        if (a.length == 1) {
                        	recover(a);
                        }
                        isValid = check(s) && isValid;
                    }
                });
                // ------------------------------------chosen下拉框插件支持 End------------------------------------
                return isValid;
            };

            form.find("input, select, textarea").not(":submit, :reset, :image, [disabled], :hidden").hover(function() {
                if ($(this).attr("data-errorqtip") != undefined) {
                    var bHeight = $(document).height();
                    $(this).popover({
                        placement: "bottom",
                        content: $(this).attr("data-errorqtip"),
                        trigger: "manual"
                    });
                    $(this).popover("show");

                    if ($(document).height() > bHeight) {
                        $(this).popover("destroy");
                        $(this).popover({
                            placement: "top",
                            content: $(this).attr("data-errorqtip"),
                            trigger: "manual"
                        });
                        $(this).popover("show");
                    }
                }
            },
            function() {
                if ($(this).attr("data-errorqtip") != undefined) {
                    $(this).popover("destroy");
                }
            }).focus(function() {
            	recover($(this));
                if ($(this).attr("data-prompt") != undefined) {
                    $(this).popover({
                        placement: "top",
                        content: $(this).attr("data-prompt"),
                        trigger: "manual"
                    });
                    $(this).popover("show");
                }
            }).blur(function() {
                check($(this));
            }).change(function() {
            	recover($(this));
                check($(this));
            });

            // ------------------------------------chosen下拉框插件支持 Start------------------------------------
            form.find("select").each(function() {
                if ($(this).next("div.chzn-container").length == 1) {
                    $(this).next("div.chzn-container").find("a").first().hover(function() {
                        if ($(this).attr("data-errorqtip") != undefined) {
                            var bHeight = $(document).height();
                            $(this).popover({
                                placement: "bottom",
                                content: $(this).attr("data-errorqtip"),
                                trigger: "manual"
                            });
                            $(this).popover("show");

                            if ($(document).height() > bHeight) {
                                $(this).popover("destroy");
                                $(this).popover({
                                    placement: "top",
                                    content: $(this).attr("data-errorqtip"),
                                    trigger: "manual"
                                });
                                $(this).popover("show");
                            }
                        }
                    },
                    function() {
                        if ($(this).attr("data-errorqtip") != undefined) {
                            $(this).popover("destroy");
                        }
                    });
                }
            });

            form.find("select").each(function() {
                if ($(this).next("div.chzn-container").length == 1) {
                    $(this).next("div.chzn-container").mousedown(function(evt) {
                        if ($(this).hasClass("chzn-with-drop")) {
                            var a = $(this).find("a").first();
                            if (a.length == 1 && a.attr("data-errorqtip") != undefined) {
                                a.popover("destroy");
                                if (a.attr("data-errorqtip") != "") {
	                            	a.removeAttr("data-errorqtip");
	                            	a.css("border", "1px solid #CCCCCC");
	                            }
                            }
                            var s = $(this).prev("select");
                            if (s.attr("data-prompt") != undefined) {
                                a.popover({
                                    placement: "top",
                                    content: s.attr("data-prompt"),
                                    trigger: "manual"
                                });
                                a.popover("show");
                            }
                        } else {
                            if (evt != null && $(evt.target).hasClass("search-choice-close")) {
                                $(this).prev("select").val("");
                            }
                            var a = $(this).find("a").first();
                            if (a.length == 1 && a.parent().prev("select").length == 1) {
                                a.popover("destroy");
                                if (a.attr("data-errorqtip") != "") {
	                            	a.removeAttr("data-errorqtip");
	                            	a.css("border", "1px solid #CCCCCC");
	                            }
                                check(a.parent().prev("select"));
                            }
                        }
                    });
                }
            });

            form.find("select").each(function() {
                if ($(this).next("div.chzn-container").length == 1) {
                    $(this).next("div.chzn-container").find("div.chzn-search > input").blur(function() {
                        var a = $(this).parent().parent().parent().find("a").first();
                        if (a.length == 1 && a.parent().prev("select").length == 1) {
                            a.popover("destroy");
                            if (a.attr("data-errorqtip") != "") {
                            	a.removeAttr("data-errorqtip");
                            	a.css("border", "1px solid #CCCCCC");
                            }
                            setTimeout(function() {
                            	check(a.parent().prev("select"));
                            }, 100);
                        }
                    });
                }
            });
            // ------------------------------------chosen下拉框插件支持 End------------------------------------
            
            
            var addHandler = function (element, type, handler) {
                if (element.addEventListener) {
                    element.addEventListener(type, handler, false);
                } else if (element.attachEvent) {
                    element.attachEvent("on" + type, handler);
                } else {
                    element["on" + type] = handler;
                }
            };
            
            // ------------------------------------监听表单重置事件 Start------------------------------------
            form.each(function() {
            	var oForm = this;
            	var jThis = $(this);
            	addHandler(oForm, "reset", function (e) {
            		setTimeout(function() {
            			jThis.isValid();
            		},1);
                });
            });
           // ------------------------------------监听表单重置事件 End------------------------------------
        },
        error: function(element, message) {
            if (message != undefined) {
                if (element.is("select")) {
                    if (element.next("div.chzn-container").length == 1) {
                        element = element.next("div.chzn-container").find("a").first();
                    }
                }
                if (element.attr("data-errorqtip") != "") {
                	element.css("border", "2px red solid");
                }
                element.attr("data-errorqtip", message);
            }
        },
        methods: {
            // http://docs.jquery.com/Plugins/Validation/Methods/required
            required: function(value, element) {
                if (element.is("select")) {
                    // could be an array for select-multiple or a string, both are fine this way
                    var val = $(element).val();
                    return val && val.length > 0;
                }
                if ((/radio|checkbox/i).test(element.attr("type"))) {
                    return sns.valid.getLength(value, element) > 0;
                }
                return $.trim(value).length > 0;
            },

            // http://docs.jquery.com/Plugins/Validation/Methods/email
            email: function(value, element) {
                // contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
                return sns.valid.optional(element) || /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(value);
            },

            // http://docs.jquery.com/Plugins/Validation/Methods/url
            url: function(value, element) {
                // contributed by Scott Gonzalez: http://projects.scottsplayground.com/iri/
                return sns.valid.optional(element) || /^(https?|s?ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value);
            },

            // http://docs.jquery.com/Plugins/Validation/Methods/date
            date: function(value, element) {
                return sns.valid.optional(element) || !/Invalid|NaN/.test(new Date(value).toString());
            },

            // http://docs.jquery.com/Plugins/Validation/Methods/number
            number: function(value, element) {
                return sns.valid.optional(element) || /^-?(?:\d+|\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/.test(value);
            },

            // http://docs.jquery.com/Plugins/Validation/Methods/digits
            digits: function(value, element) {
                return sns.valid.optional(element) || /^\d+$/.test(value);
            },

            // http://docs.jquery.com/Plugins/Validation/Methods/minlength
            minlength: function(value, element, param) {
                var length = $.isArray(value) ? value.length: sns.valid.getLength($.trim(value), element);
                return sns.valid.optional(element) || length >= param;
            },

            // http://docs.jquery.com/Plugins/Validation/Methods/maxlength
            maxlength: function(value, element, param) {
                var length = $.isArray(value) ? value.length: sns.valid.getLength($.trim(value), element);
                return sns.valid.optional(element) || length <= param;
            },

            // http://docs.jquery.com/Plugins/Validation/Methods/min
            min: function(value, element, param) {
                return sns.valid.optional(element) || value >= param;
            },

            // http://docs.jquery.com/Plugins/Validation/Methods/max
            max: function(value, element, param) {
                return sns.valid.optional(element) || value <= param;
            },

            // http://docs.jquery.com/Plugins/Validation/Methods/equalTo
            equalTo: function(value, element, param) {
                // bind to the blur event of the target in order to revalidate whenever the target field is updated
                var target = $(param);
                return value === sns.valid.elementValue(target);
            }
        }
    }
});