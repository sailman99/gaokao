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

public class Chapter {
    private Integer chapterID;
    private String  chapterName;


    public Chapter(Integer chapterID, String chapterName){
        this.chapterID=chapterID;
        this.chapterName=chapterName;
    }
    @Override
    public String toString(){
        return this.chapterName;
    }

    public Integer getChapterID() {
        return chapterID;
    }

    public void setChapterID(Integer chapterID) {
        this.chapterID = chapterID;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chapter)) return false;

        Chapter chapter = (Chapter) o;

        if (!getChapterID().equals(chapter.getChapterID())) return false;
        return getChapterName().equals(chapter.getChapterName());

    }

    @Override
    public int hashCode() {
        int result = getChapterID().hashCode();
        result = 31 * result + getChapterName().hashCode();
        return result;
    }
}
