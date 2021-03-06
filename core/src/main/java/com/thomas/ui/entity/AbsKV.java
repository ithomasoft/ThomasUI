package com.thomas.ui.entity;


import androidx.annotation.DrawableRes;

public abstract class AbsKV {


    private boolean isChoose = false;


    /**
     * 获取Name(显示在前端的)
     *
     * @return
     */
    public abstract String getKey();

    /**
     * 获取Value(保存在后端的)
     *
     * @return
     */
    public abstract String getValue();

    /**
     * 获取图标资源
     *
     * @return
     */
    public abstract @DrawableRes
    int getResId();


    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
