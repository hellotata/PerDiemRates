package com.example.android.perdiem;

/**
 * Created by richardta on 10/12/16.
 */

public class jObject implements java.io.Serializable, Comparable<jObject> {
    private String city;
    private String county;
    private String jan;
    private String feb;
    private String mar;
    private String apr;
    private String may;
    private String jun;
    private String jul;
    private String aug;
    private String sep;
    private String oct;
    private String nov;
    private String dec;
    private String MIE;


    public jObject(String city, String county, String jan, String feb, String mar, String apr, String may, String jun, String jul, String aug, String sep, String oct, String nov, String dec, String MIE) {
        this.city = city;
        this.county = county;
        this.jan = jan;
        this.feb = feb;
        this.mar = mar;
        this.apr = apr;
        this.may = may;
        this.jun = jun;
        this.jul = jul;
        this.aug = aug;
        this.sep = sep;
        this.oct = oct;
        this.nov = nov;
        this.dec = dec;
        this.MIE = MIE;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getJan() {
        return jan;
    }

    public String getFeb() {
        return feb;
    }

    public String getMar() {
        return mar;
    }

    public String getApr() {
        return apr;
    }

    public String getMay() {
        return may;
    }

    public String getJun() {
        return jun;
    }

    public String getJul() {
        return jul;
    }

    public String getAug() {
        return aug;
    }

    public String getSep() {
        return sep;
    }

    public String getOct() {
        return oct;
    }

    public String getNov() {
        return nov;
    }

    public String getDec() {
        return dec;
    }

    public String getMIE() {
        return MIE;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public int compareTo(jObject object) {
        return this.getCity().compareTo(object.getCity());
    }
}
