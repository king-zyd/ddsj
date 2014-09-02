package org.ddhg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Constance.Zhuang
 * Date: 14-9-1
 */
@Entity
@Table(name = "navigation")
public class Navigation extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Navigation parentNav;

    @Column(name = "index")
    private int index;

    @OneToMany(mappedBy = "parentNav", fetch = FetchType.EAGER)
    private Set<Navigation> childNavigationSet = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Navigation getParentNav() {
        return parentNav;
    }

    public void setParentNav(Navigation parentNav) {
        this.parentNav = parentNav;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Set<Navigation> getChildNavigationSet() {
        return childNavigationSet;
    }

    public void setChildNavigationSet(Set<Navigation> childNavigationSet) {
        this.childNavigationSet = childNavigationSet;
    }
}
