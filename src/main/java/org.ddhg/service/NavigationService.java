package org.ddhg.service;

import org.ddhg.entity.Navigation;
import org.ddhg.repository.NavigationRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * User: Constance.Zhuang
 * Date: 14-9-1
 */
@Service
public class NavigationService {

    private NavigationRepository navigationRepository;

    public List<Navigation> getTopNavigation() {
        return navigationRepository.getTopNavigation();
    }

    @Inject
    public void setNavigationRepository(NavigationRepository navigationRepository) {
        this.navigationRepository = navigationRepository;
    }
}
