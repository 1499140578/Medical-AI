package com.example.youngtogether.bean;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int id;
    private String name;
    private String mail;
    private Date birthDay;
    private int age=0;
    private String sex;
    private String password;
    private String headIMG;
    private String phone;
    public User() {}

    public User(int id, String name, String mail, Date birthDay,int age, String sex, String password,String phone,String headIMG) {
        super();
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.birthDay=birthDay;
        this.age=age;
        this.sex = sex;
        this.password = password;
        this.phone=phone;
        this.headIMG=headIMG;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the birthDay
     */
    public Date getBirthDay() {
        return birthDay;
    }

    /**
     * @param birthDay the birthDay to set
     */
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    /**
     * @return the headIMG
     */
    public String getHeadIMG() {
        return headIMG;
    }

    /**
     * @param headIMG the headIMG to set
     */
    public void setHeadIMG(String headIMG) {
        this.headIMG = headIMG;
    }

}
