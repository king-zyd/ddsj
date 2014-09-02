package org.ddhg.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by Constance on 14-5-2.
 */
@MappedSuperclass
public abstract class CommonEntity {
    @Column(name = "create_by")
    protected String createBy;

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createDate;

    @Column(name = "update_by")
    protected String updateBy;

    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date updateDate;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @PrePersist
    public void save() {
        this.createDate = new Date();
    }

    @PreUpdate
    public void update() {
        this.updateDate = new Date();
    }
}
