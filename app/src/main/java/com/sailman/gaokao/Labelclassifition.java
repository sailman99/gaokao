/*
 *
 *  *
 *  *  *
 *  *  *  * ===================================
 *  *  *  * Copyright (c) 2016.
 *  *  *  * 作者：安卓猴
 *  *  *  * 微博：@安卓猴
 *  *  *  * 博客：http://sunjiajia.com
 *  *  *  * Github：https://github.com/opengit
 *  *  *  *
 *  *  *  * 注意**：如果您使用或者修改该代码，请务必保留此版权信息。
 *  *  *  * ===================================
 *  *  *
 *  *  *
 *  *
 *
 */

package com.sailman.gaokao;

/**
 * Created by Administrator on 2017/7/25.
 */

public class Labelclassifition {
    private Integer labelclassifitionID;
    private String  labelclassifitionName;


    public Labelclassifition(Integer labelclassifitionID, String labelclassifitionName){
        this.labelclassifitionID=labelclassifitionID;
        this.labelclassifitionName=labelclassifitionName;
    }
    @Override
    public String toString(){
        return this.labelclassifitionName;
    }
    public Integer getLabelclassifitionID() {
        return labelclassifitionID;
    }

    public void setLabelclassifitionID(Integer labelclassifitionID) {
        this.labelclassifitionID = labelclassifitionID;
    }

    public String getLabelclassifitionName() {
        return labelclassifitionName;
    }

    public void setLabelclassifitionName(String labelclassifitionName) {
        this.labelclassifitionName = labelclassifitionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Labelclassifition)) return false;

        Labelclassifition that = (Labelclassifition) o;

        if (!getLabelclassifitionID().equals(that.getLabelclassifitionID())) return false;
        return getLabelclassifitionName().equals(that.getLabelclassifitionName());

    }

    @Override
    public int hashCode() {
        int result = getLabelclassifitionID().hashCode();
        result = 31 * result + getLabelclassifitionName().hashCode();
        return result;
    }
}
