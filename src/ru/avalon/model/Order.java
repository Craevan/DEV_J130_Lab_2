package ru.avalon.model;

import ru.avalon.utils.DateUtil;

import java.time.LocalDate;

public class Order {

    private int id;
    private LocalDate creationDate;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String customerAddress;
    private char orderState;
    private LocalDate shipmentDate;

    // Чтобы не создавать конструктор с кучей идущих подряд String, реализуем Builder
    private Order(OrderBuilder orderBuilder) {
        this.id = orderBuilder.getId();
        this.creationDate = orderBuilder.getCreationDate();
        this.customerName = orderBuilder.getCustomerName();
        this.customerPhone = orderBuilder.getCustomerPhone();
        this.customerEmail = orderBuilder.getCustomerEmail();
        this.customerAddress = orderBuilder.getCustomerAddress();
        this.orderState = orderBuilder.getOrderState();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        if (id != order.id) return false;
        if (orderState != order.orderState) return false;
        if (!creationDate.equals(order.creationDate)) return false;
        if (!customerName.equals(order.customerName)) return false;
        return customerAddress.equals(order.customerAddress);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + customerName.hashCode();
        result = 31 * result + customerAddress.hashCode();
        result = 31 * result + (int) orderState;
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", customerName='" + customerName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", orderState=" + orderState +
                ", shipmentDate=" + shipmentDate +
                '}';
    }

    public static class OrderBuilder {

        private int id;
        private LocalDate creationDate;
        private String customerName;
        private String customerPhone;
        private String customerEmail;
        private String customerAddress;
        private char orderState;

        public OrderBuilder id(int id) {
            this.id = id;
            return this;
        }

        public OrderBuilder creationDate(String date) {
            this.creationDate = LocalDate.parse(date, DateUtil.DTF);
            return this;
        }


        public OrderBuilder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public OrderBuilder customerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
            return this;
        }

        public OrderBuilder customerEmail(String customerEmail) {
            this.customerEmail = customerEmail;
            return this;
        }

        public OrderBuilder customerAddress(String customerAddress) {
            this.customerAddress = customerAddress;
            return this;
        }

        public OrderBuilder orderState(char orderState) {
            switch (orderState) {
                case 'C':
                case 'S':
                case 'P':
                    this.orderState = orderState;
                    return this;
                default:
                    throw new IllegalArgumentException("order state must be 'P' or 'S' or 'C'");
            }
        }

        private int getId() {
            return id;
        }

        private LocalDate getCreationDate() {
            return creationDate;
        }

        private String getCustomerName() {
            return customerName;
        }

        private String getCustomerPhone() {
            return customerPhone;
        }

        private String getCustomerEmail() {
            return customerEmail;
        }

        private String getCustomerAddress() {
            return customerAddress;
        }

        private char getOrderState() {
            return orderState;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
