package ru.avalon.model;

import java.time.LocalDate;
import java.util.Objects;

public class Order {

    private final int id;
    private final LocalDate creationDate;
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
        this.shipmentDate = orderBuilder.getShipmentDate();
    }

    public int getId() {
        return id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public char getOrderState() {
        return orderState;
    }

    public LocalDate getShipmentDate() {
        return shipmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        if (id != order.id) return false;
        if (orderState != order.orderState) return false;
        if (!creationDate.equals(order.creationDate)) return false;
        if (!customerName.equals(order.customerName)) return false;
        if (!Objects.equals(customerPhone, order.customerPhone))
            return false;
        if (!Objects.equals(customerEmail, order.customerEmail))
            return false;
        if (!customerAddress.equals(order.customerAddress)) return false;
        return Objects.equals(shipmentDate, order.shipmentDate);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + customerName.hashCode();
        result = 31 * result + (customerPhone != null ? customerPhone.hashCode() : 0);
        result = 31 * result + (customerEmail != null ? customerEmail.hashCode() : 0);
        result = 31 * result + customerAddress.hashCode();
        result = 31 * result + (int) orderState;
        result = 31 * result + (shipmentDate != null ? shipmentDate.hashCode() : 0);
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
        private LocalDate shipmentDate;

        public OrderBuilder id(int id) {
            this.id = id;
            return this;
        }

        public OrderBuilder creationDate() {
            this.creationDate = LocalDate.now();
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

        public OrderBuilder shipmentDate() {
            if (orderState == 'S') {
                shipmentDate = LocalDate.now();
            }
            else {
                shipmentDate = null;
            }
            return this;
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

        public LocalDate getShipmentDate() {
            return shipmentDate;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
