package com.smart.model.micro;

import javax.persistence.*;

/**
 * Title: DrugGroup
 * Description: 微生物--药敏组
 *
 * @Author:zhou
 * @Date:2016/7/6 17:28
 * @Version:
 */
@Entity
@Table(name = "LAB_MICRO_DRUGGROUP")
public class DrugGroup {
    private Long id;
    private String name;             //名称
    private int state;              //状态
    private String spellCode;       //拼音码

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DRUGGROUP")
    @SequenceGenerator(name = "SEQ_DRUGGROUP", sequenceName = "druggroup_sequences", allocationSize = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSpellCode() {
        return spellCode;
    }

    public void setSpellCode(String spellCode) {
        this.spellCode = spellCode;
    }

}
