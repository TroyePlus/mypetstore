package org.csu.mypetstore.domain;

import java.util.Date;

public class Delivery {
    private int orderId;
    private Date deliveryTime;
    private String courierName;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryDate) {
        this.deliveryTime = deliveryDate;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public Delivery(int orderId, Date deliveryTime, String courierName) {
        this.orderId = orderId;
        this.deliveryTime = deliveryTime;
        this.courierName = courierName;
    }

    public Delivery() {
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "orderId=" + orderId +
                ", deliveryTime=" + deliveryTime +
                ", courierName='" + courierName + '\'' +
                '}';
    }
}
