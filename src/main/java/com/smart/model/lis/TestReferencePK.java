package com.smart.model.lis;

import java.io.Serializable;

/**
 * Title: TestReferencePK
 * Description:检验项目参考范围主键
 *
 * @Author:zhou
 * @Date:2016/6/6 22:13
 * @Version:
 */

public class TestReferencePK implements Serializable {
    private static final long serialVersionUID = 3693900476343733938L;

    private String testId;      //检验项目
    private int sex;            //性别
    private int orderNo;        //序号

    public TestReferencePK() {
    }

    public TestReferencePK(String testId, int sex, int orderNo) {
        this.testId = testId;
        this.sex = sex;
        this.orderNo = orderNo;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TestReferencePK) {
            TestReferencePK pk = (TestReferencePK) obj;
            if (this.sex == pk.getSex() && this.orderNo == pk.orderNo && this.testId.equals(pk.getTestId()))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((testId == null) ? 0 : testId.hashCode());
        return result;
    }

}
