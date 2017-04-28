package com.bawei.paotui.citylistview.bean;

/**
 * 姓名：李鑫琪
 * 日期：2016/12/27 12:00
 * 描述：
 */
public class City {
    public City(String cityName, String nameSort) {
        CityName = cityName;
        NameSort = nameSort;
    }

    public City() {
    }

    private String CityName;
    private String NameSort;

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getNameSort() {
        return NameSort;
    }

    public void setNameSort(String nameSort) {
        NameSort = nameSort;
    }
}
