package com.example.coindesk.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @Column(name = "code", length = 3)
    private String code;

    @Column(name = "name_zh", nullable = false, length = 50)
    private String nameZh;

    protected Currency() {

    }

    public Currency(String code, String nameZh) {
        this.code = code;
        this.nameZh = nameZh;
    }

    public String getCode() {
        return code;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }
}