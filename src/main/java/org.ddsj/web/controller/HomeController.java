package org.ddsj.web.controller;

import com.zyd.core.platform.web.site.SiteController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zyd
 */
@Controller
public class HomeController extends SiteController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "home";
    }
}
