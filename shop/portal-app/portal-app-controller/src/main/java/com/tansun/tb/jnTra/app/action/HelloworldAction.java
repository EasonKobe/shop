package com.tansun.tb.jnTra.app.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeedev.msdp.core.web.action.BaseAppAction;

@Controller
@RequestMapping(value = "/")
public class HelloworldAction extends BaseAppAction {

    @RequestMapping(value = {"/helloworld"})
    @ResponseBody
    public String helloworld() {
        return "hello world, jnTra-app";
    }

}
