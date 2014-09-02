package org.ddhg.web.controller;

import com.zyd.core.platform.web.site.SiteController;
import org.ddhg.entity.Navigation;
import org.ddhg.service.NavigationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

/**
 * @author zyd
 */
@Controller
public class HomeController extends SiteController {

    private NavigationService navigationService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        List<Navigation> navigationList = navigationService.getTopNavigation();
        return "home";
    }

    @Inject
    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }
}
