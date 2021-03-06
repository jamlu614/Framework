package com.example.model.bean;


import com.jamlu.framework.model.bean.BaseBean;

import java.util.List;

/**
 * Created by Kang on 2017/3/16.
 */

public class HomeBean extends BaseBean {


    private List<HomesBean> homes;

    public List<HomesBean> getHomes() {
        return homes;
    }

    public void setHomes(List<HomesBean> homes) {
        this.homes = homes;
    }

    public static class HomesBean {

        private String home_title;
        private String home_houses_name;
        private String home_total;
        private String home_area;
        private String home_unit_price;
        private String home_model_type;
        private String home_orientation;
        private String home_img;
        private String home_tags;

        public String getHome_title() {
            return home_title;
        }

        public void setHome_title(String home_title) {
            this.home_title = home_title;
        }

        public String getHome_houses_name() {
            return home_houses_name;
        }

        public void setHome_houses_name(String home_houses_name) {
            this.home_houses_name = home_houses_name;
        }

        public String getHome_total() {
            return home_total;
        }

        public void setHome_total(String home_total) {
            this.home_total = home_total;
        }

        public String getHome_area() {
            return home_area;
        }

        public void setHome_area(String home_area) {
            this.home_area = home_area;
        }

        public String getHome_unit_price() {
            return home_unit_price;
        }

        public void setHome_unit_price(String home_unit_price) {
            this.home_unit_price = home_unit_price;
        }

        public String getHome_model_type() {
            return home_model_type;
        }

        public void setHome_model_type(String home_model_type) {
            this.home_model_type = home_model_type;
        }

        public String getHome_orientation() {
            return home_orientation;
        }

        public void setHome_orientation(String home_orientation) {
            this.home_orientation = home_orientation;
        }

        public String getHome_img() {
            return home_img;
        }

        public void setHome_img(String home_img) {
            this.home_img = home_img;
        }

        public String getHome_tags() {
            return home_tags;
        }

        public void setHome_tags(String home_tags) {
            this.home_tags = home_tags;
        }
    }
}
