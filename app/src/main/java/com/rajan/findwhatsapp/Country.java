package com.rajan.findwhatsapp;

public class Country {

    public int selected;
    private int id;
    private String name;
    private String code;
    private String flag;
    private int dCode;

    public Country(int selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getdCode() {
        return dCode;
    }

    public void setdCode(int dCode) {
        this.dCode = dCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Country) {
            Country country = (Country) o;
            if (country.selected == 1) {
                return true;
            }
        }
        return super.equals(o);
    }
}
