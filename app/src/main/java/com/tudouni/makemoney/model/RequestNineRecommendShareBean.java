package com.tudouni.makemoney.model;

import java.util.List;

/**
 * Created by ZhangPeng on 2018/5/15.
 */

public class RequestNineRecommendShareBean {
    private List<ItemsBean> items;

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public RequestNineRecommendShareBean(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {


        /**
         * itemid : 568084843649
         * source : tm
         */

        public ItemsBean(String itemid, String source) {
            this.itemid = itemid;
            this.source = source;
        }

        private String itemid;
        private String source;

        public String getItemid() {
            return itemid;
        }

        public void setItemid(String itemid) {
            this.itemid = itemid;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }
}
