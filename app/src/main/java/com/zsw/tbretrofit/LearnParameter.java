package com.zsw.tbretrofit;

import com.tb.rx_retrofit.http_excuter.JsonBody;

/**
 * @描述： -
 * -
 * @作者：zhusw
 * @创建时间：17/11/20 下午4:51
 * @最后更新时间：17/11/20 下午4:51
 */
public class LearnParameter implements JsonBody{


    /**
     * title : 上汽
     * typeId : 2
     * tagIds : 4
     * pageInfo : {"pageNumber":1,"pageSize":5}
     */

    private String title;
    private String typeId;
    private String tagIds;
    private PageInfoBean pageInfo;

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getTypeId () {
        return typeId;
    }

    public void setTypeId (String typeId) {
        this.typeId = typeId;
    }

    public String getTagIds () {
        return tagIds;
    }

    public void setTagIds (String tagIds) {
        this.tagIds = tagIds;
    }

    public PageInfoBean getPageInfo () {
        return pageInfo;
    }

    public void setPageInfo (PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }

    public static class PageInfoBean {
        /**
         * pageNumber : 1
         * pageSize : 5
         */

        private int pageNumber;
        private int pageSize;

        public PageInfoBean (int pageNumber, int pageSize) {
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
        }

        public int getPageNumber () {
            return pageNumber;
        }

        public void setPageNumber (int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public int getPageSize () {
            return pageSize;
        }

        public void setPageSize (int pageSize) {
            this.pageSize = pageSize;
        }
    }
}
