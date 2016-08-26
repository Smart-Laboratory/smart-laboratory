package com.smart.model.dsf;

import com.smart.model.BaseObject;
import org.hibernate.search.annotations.DocumentId;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zjn on 2016/8/20.
 */
@Entity
@Table(name = "DSF_CONTROLTESTITEMS")
public class DSF_ControlTestItems extends BaseObject implements Serializable {
    private Long id;
    private String customerid;
    private String localitems;
    private String customeritems;
    private String customeritemsname;
    private String localitemsname;

    public DSF_ControlTestItems() {
    }

    public DSF_ControlTestItems(Long id, String customerid, String localitems, String customeritems,String customeritemsname) {
        this.id = id;
        this.customerid = customerid;
        this.localitems = localitems;
        this.customeritems = customeritems;
        this.customeritemsname = customeritemsname;
    }
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DSF_CONTROLTESTITEMS")
    @SequenceGenerator(name = "SEQ_DSF_CONTROLTESTITEMS", sequenceName = "DSF_CONTROLTESTITEMS_SEQ", allocationSize = 1)
    @DocumentId
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getLocalitems() {
        return localitems;
    }

    public void setLocalitems(String localitems) {
        this.localitems = localitems;
    }

    public String getCustomeritems() {
        return customeritems;
    }

    public void setCustomeritems(String customeritems) {
        this.customeritems = customeritems;
    }

    public String getCustomeritemsname() {
        return customeritemsname;
    }

    public void setCustomeritemsname(String customeritemsname) {
        this.customeritemsname = customeritemsname;
    }

    public String getLocalitemsname() {
        return localitemsname;
    }

    public void setLocalitemsname(String localitemsname) {
        this.localitemsname = localitemsname;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
