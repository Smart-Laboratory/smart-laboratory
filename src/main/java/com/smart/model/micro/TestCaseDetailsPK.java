package com.smart.model.micro;

import java.io.Serializable;

/**
 * Title: .IntelliJ IDEA
 * Description:
 *
 * @Author:zhou
 * @Date:2016/7/17 16:35
 * @Version:
 */
public class TestCaseDetailsPK implements Serializable{
    private static final long serialVersionUID = -5948907499411742355L;
    private String testCaseId;
    private String cultureMediumId;

    public TestCaseDetailsPK(){}

    public TestCaseDetailsPK(String testCaseId,String cultureMediumId){
        this.testCaseId = testCaseId;
        this.cultureMediumId = cultureMediumId;
    }
    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public String getCultureMediumId() {
        return cultureMediumId;
    }

    public void setCultureMediumId(String cultureMediumId) {
        this.cultureMediumId = cultureMediumId;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TestCaseDetailsPK other = (TestCaseDetailsPK) obj;
        if (testCaseId == null) {
            if (other.testCaseId != null)
                return false;
        } else if (!testCaseId.equals(other.testCaseId))
            return false;
        if (cultureMediumId == null) {
            if (other.cultureMediumId != null)
                return false;
        } else if (!cultureMediumId.equals(other.cultureMediumId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((testCaseId == null) ? 0 : testCaseId.hashCode());
        result = prime * result + ((cultureMediumId == null) ? 0 : cultureMediumId.hashCode());
        return result;
    }
}
