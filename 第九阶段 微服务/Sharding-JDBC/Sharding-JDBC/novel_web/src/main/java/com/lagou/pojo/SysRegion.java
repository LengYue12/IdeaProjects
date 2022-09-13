package com.lagou.pojo;

/**
 * 区域
 * */
public class SysRegion {

    private Long id;

    private String region_name;

    private String region_code;

    private int region_level;

    public SysRegion(String region_name, String region_code, int region_level) {
        this.region_name = region_name;
        this.region_code = region_code;
        this.region_level = region_level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public int getRegion_level() {
        return region_level;
    }

    public void setRegion_level(int region_level) {
        this.region_level = region_level;
    }
}
