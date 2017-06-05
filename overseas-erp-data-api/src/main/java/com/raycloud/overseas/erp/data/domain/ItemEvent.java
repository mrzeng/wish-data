package com.raycloud.overseas.erp.data.domain;


import java.io.Serializable;


/**
 * Created by zhanxf on 16/8/15.
 */
public class ItemEvent implements Serializable{


        private static final long serialVersionUID = 8437264170695941007L;

        private String type;//事件类型

        private String time;//事件时间

        private String eventDesc;//里程碑描述

        public ItemEvent() {
        }

//        public ItemEvent(ItemMilestone mark) {
//            if(mark.getInsertDate()!=null){
//                time = new SimpleDateFormat("yyyy-MM-dd").format(mark.getInsertDate());
//            }
//            if(mark.getType()!=null){
//                if(mark.getType().equals("changePrice")){
//                    if(mark.getJsonDesc().indexOf("changeWishPrice")>-1 || mark.getJsonDesc().indexOf("changeWishFreightPrice")>-1){
//                        type = "changeWishPrice";
//                    }else if(mark.getJsonDesc().indexOf("changeSellerFreightPrice")>-1 || mark.getJsonDesc().indexOf("changeSellerPrice")>-1){
//                        type = "changeSellerPrice";
//                    }else if(mark.getJsonDesc().indexOf("changeOriginalPrice")>-1){
//                        type = "changeOriginalPrice";
//                    }
//                }else if(mark.getType().equals("changeTag")){
//                    type = mark.getJsonDesc().indexOf("changeWishTag")>-1?"changeWishTag":"changeCustomerTag";
//                }else if(mark.getType().equals("wishRecommendPass")){
//                    type = "authGet";
//                }else if(mark.getType().equals("wishRecommendCancel")){
//                    type = "authCancle";
//                }else{
//                    type = mark.getType();
//                }
//            }
//
//        }


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getEventDesc() {
            return eventDesc;
        }

        public void setEventDesc(String eventDesc) {
            this.eventDesc = eventDesc;
        }

}
