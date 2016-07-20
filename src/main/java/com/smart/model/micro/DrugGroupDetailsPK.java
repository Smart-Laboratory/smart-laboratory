package com.smart.model.micro;

import java.io.Serializable;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/8 16:40
 * @Version:
 */
public class DrugGroupDetailsPK implements Serializable {
    private static final long serialVersionUID = -2325459667495087266L;

    public DrugGroupDetailsPK(){}

    public DrugGroupDetailsPK(String groupId,String drugId){
        this.groupId = groupId;
        this.drugId = drugId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDrugId() {
        return drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DrugGroupDetailsPK other = (DrugGroupDetailsPK) obj;
        if (groupId == null) {
            if (other.groupId != null)
                return false;
        } else if (!groupId.equals(other.groupId))
            return false;
        if (drugId == null) {
            if (other.drugId != null)
                return false;
        } else if (!drugId.equals(other.drugId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((drugId == null) ? 0 : drugId.hashCode());
        result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
        return result;
    }
    private String groupId;
    private String drugId;
}
