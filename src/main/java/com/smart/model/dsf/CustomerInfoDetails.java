package com.smart.model.dsf;

import com.smart.model.BaseObject;

import javax.persistence.*;

/**
 * Created by zjn on 2016/8/4.
 */

@Entity
@Table(name = "DSF_CUSTOMER_PEOPLEINFO")
public class CustomerInfoDetails extends BaseObject {
    private Long serialnumber;
    private String customerid;
    private String name;
    private String age;
    private String sex;
    private String position;
    private String hobby;
    private String birthday;
    private String worktelephone;
    private String homephone;
    private String phonenumber;
    private String scepticsofcompany;
    private String besttimetovisit;
    private String bestplacetovisit;
    private String bestcallroute;
    private String maritalstatus;
    private String spousename;
    private String spouseoccupation;
    private String spousehobby;
    private String remarks;

    public CustomerInfoDetails() {
    }

    public CustomerInfoDetails(Long serialnumber, String customerid, String name, String age, String sex, String position, String hobby, String birthday, String worktelephone, String homephone, String phonenumber, String scepticsofcompany, String besttimetovisit, String bestplacetovisit, String bestcallroute, String maritalstatus, String spousename, String spouseoccupation, String spousehobby, String remarks) {
        this.serialnumber = serialnumber;
        this.customerid = customerid;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.position = position;
        this.hobby = hobby;
        this.birthday = birthday;
        this.worktelephone = worktelephone;
        this.homephone = homephone;
        this.phonenumber = phonenumber;
        this.scepticsofcompany = scepticsofcompany;
        this.besttimetovisit = besttimetovisit;
        this.bestplacetovisit = bestplacetovisit;
        this.bestcallroute = bestcallroute;
        this.maritalstatus = maritalstatus;
        this.spousename = spousename;
        this.spouseoccupation = spouseoccupation;
        this.spousehobby = spousehobby;
        this.remarks = remarks;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_people_seq")
    @SequenceGenerator(name = "customer_people_seq", sequenceName = "DSF_CUSTOMER_PEOPLE_SEQ", allocationSize = 1)
    @Column(name = "SERIALNUMBER")
    public Long getSerialnumber() {
        return serialnumber;
    }
    @Column(name = "CUSTOMERID")
    public String getCustomerid() {
        return customerid;
    }
    @Column(name = "NAME")
    public String getName() {
        return name;
    }
    @Column(name = "AGE")
    public String getAge() {
        return age;
    }
    @Column(name = "SEX")
    public String getSex() {
        return sex;
    }
    @Column(name = "POSITION")
    public String getPosition() {
        return position;
    }
    @Column(name = "HOBBY")
    public String getHobby() {
        return hobby;
    }
    @Column(name = "BIRTHDAY")
    public String getBirthday() {
        return birthday;
    }
    @Column(name = "WORKTELEPHONE")
    public String getWorktelephone() {
        return worktelephone;
    }
    @Column(name = "HOMEPHONE")
    public String getHomephone() {
        return homephone;
    }
    @Column(name = "PHONENUMBER")
    public String getPhonenumber() {
        return phonenumber;
    }
    @Column(name = "SCEPTICSOFCOMPANY")
    public String getScepticsofcompany() {
        return scepticsofcompany;
    }
    @Column(name = "BESTTIMETOVISIT")
    public String getBesttimetovisit() {
        return besttimetovisit;
    }
    @Column(name = "BESTPLACETOVISIT")
    public String getBestplacetovisit() {
        return bestplacetovisit;
    }
    @Column(name = "BESTCALLROUTE")
    public String getBestcallroute() {
        return bestcallroute;
    }
    @Column(name = "MARITALSTATUS")
    public String getMaritalstatus() {
        return maritalstatus;
    }
    @Column(name = "SPOUSENAME")
    public String getSpousename() {
        return spousename;
    }
    @Column(name = "SPOUSEOCCUPATION")
    public String getSpouseoccupation() {
        return spouseoccupation;
    }
    @Column(name = "SPOUSEHOBBY")
    public String getSpousehobby() {
        return spousehobby;
    }
    @Column(name = "REMARKS")
    public String getRemarks() {
        return remarks;
    }

    public void setSerialnumber(Long serialnumber) {
        this.serialnumber = serialnumber;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setWorktelephone(String worktelephone) {
        this.worktelephone = worktelephone;
    }

    public void setHomephone(String homephone) {
        this.homephone = homephone;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setScepticsofcompany(String scepticsofcompany) {
        this.scepticsofcompany = scepticsofcompany;
    }

    public void setBesttimetovisit(String besttimetovisit) {
        this.besttimetovisit = besttimetovisit;
    }

    public void setBestplacetovisit(String bestplacetovisit) {
        this.bestplacetovisit = bestplacetovisit;
    }

    public void setBestcallroute(String bestcallroute) {
        this.bestcallroute = bestcallroute;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public void setSpousename(String spousename) {
        this.spousename = spousename;
    }

    public void setSpouseoccupation(String spouseoccupation) {
        this.spouseoccupation = spouseoccupation;
    }

    public void setSpousehobby(String spousehobby) {
        this.spousehobby = spousehobby;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
