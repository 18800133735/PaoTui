package bwei.com.citysortlist.bean;

/**
 * 作者：JinCaiXia
 * 邮箱：jincaixia123@aliyun.com
 * 时间：2017/2/9
 * 文档说明：
 */

public class CityBean {
    private String CityName;
    private String NameSort;

    public CityBean(String cityName, String nameSort) {
        CityName = cityName;
        NameSort = nameSort;
    }

    public String getCityName() {
        return CityName;
    }

    public String getNameSort() {
        return NameSort;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public void setNameSort(String nameSort) {
        NameSort = nameSort;
    }
}
