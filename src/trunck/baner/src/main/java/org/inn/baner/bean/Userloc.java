package org.inn.baner.bean;

import java.util.Date;

public class Userloc extends UserlocKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userloc.createtime
     *
     * @mbg.generated
     */
    private Date createtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userloc.areacode
     *
     * @mbg.generated
     */
    private String areacode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userloc.locationdesc
     *
     * @mbg.generated
     */
    private String locationdesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userloc.latitude
     *
     * @mbg.generated
     */
    private String latitude;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userloc.longitude
     *
     * @mbg.generated
     */
    private String longitude;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userloc.createtime
     *
     * @return the value of userloc.createtime
     *
     * @mbg.generated
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userloc.createtime
     *
     * @param createtime the value for userloc.createtime
     *
     * @mbg.generated
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userloc.areacode
     *
     * @return the value of userloc.areacode
     *
     * @mbg.generated
     */
    public String getAreacode() {
        return areacode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userloc.areacode
     *
     * @param areacode the value for userloc.areacode
     *
     * @mbg.generated
     */
    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userloc.locationdesc
     *
     * @return the value of userloc.locationdesc
     *
     * @mbg.generated
     */
    public String getLocationdesc() {
        return locationdesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userloc.locationdesc
     *
     * @param locationdesc the value for userloc.locationdesc
     *
     * @mbg.generated
     */
    public void setLocationdesc(String locationdesc) {
        this.locationdesc = locationdesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userloc.latitude
     *
     * @return the value of userloc.latitude
     *
     * @mbg.generated
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userloc.latitude
     *
     * @param latitude the value for userloc.latitude
     *
     * @mbg.generated
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userloc.longitude
     *
     * @return the value of userloc.longitude
     *
     * @mbg.generated
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userloc.longitude
     *
     * @param longitude the value for userloc.longitude
     *
     * @mbg.generated
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}