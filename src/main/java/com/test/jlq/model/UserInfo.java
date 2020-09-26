package com.test.jlq.model;

/**
 * @author hao
 * @Date 2020/9/14
 * @Description 用户信息
 */
public class UserInfo {


    /**
     * key
     */
    private String key;

    /**
     * 编号
     */
    private long id;

    /**
     * 性别：0-女 1-男
     */
    private int gender;
    /**
     * 出生年
     */
    private int birthyear;

    /**
     * 总旅行里程
     */
    private long totalTravelLength;

    /**
     * 总旅行时间
     */
    private long totalTravelTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(int birthyear) {
        this.birthyear = birthyear;
    }

    public long getTotalTravelLength() {
        return totalTravelLength;
    }

    public void setTotalTravelLength(long totalTravelLength) {
        this.totalTravelLength = totalTravelLength;
    }

    public long getTotalTravelTime() {
        return totalTravelTime;
    }

    public void setTotalTravelTime(long totalTravelTime) {
        this.totalTravelTime = totalTravelTime;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
