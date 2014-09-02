package org.ddhg.repository;

import com.zyd.core.database.JPAAccess;
import org.ddhg.entity.Navigation;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * User: Constance.Zhuang
 * Date: 14-9-1
 */
@Repository
public class NavigationRepository {

    private JPAAccess jpaAccess;

    public List<Navigation> getTopNavigation() {
        CriteriaBuilder cb = jpaAccess.criteriaBuilder();
        CriteriaQuery<Navigation> query = cb.createQuery(Navigation.class);
        Root<Navigation> root = query.from(Navigation.class);
        Predicate parentIdPredicate = cb.equal(root.get("parentNav").get("id"), 1);
        query.where(parentIdPredicate);
        query.orderBy(cb.asc(root.get("index")));
        query.select(root);
        return jpaAccess.find(query);
    }

    @Inject
    public void setJpaAccess(JPAAccess jpaAccess) {
        this.jpaAccess = jpaAccess;
    }
}
