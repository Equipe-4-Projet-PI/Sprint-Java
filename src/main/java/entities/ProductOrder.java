package entities;

public class ProductOrder {
    int Id_Order,Id_Product;
    String Title,OrderDate;
    Double Price;

    public ProductOrder() {
    }

    public ProductOrder(int id_Order, int id_Product, String title, String orderDate, Double price) {
        Id_Order = id_Order;
        Id_Product = id_Product;
        Title = title;
        OrderDate = orderDate;
        Price = price;
    }

    public ProductOrder(int id_Product, String title, String orderDate, Double price) {
        Id_Product = id_Product;
        Title = title;
        OrderDate = orderDate;
        Price = price;
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "Id_Order=" + Id_Order +
                ", Id_Product=" + Id_Product +
                ", Title='" + Title + '\'' +
                ", OrderDate='" + OrderDate + '\'' +
                ", Price=" + Price +
                '}';
    }

    public int getId_Order() {
        return Id_Order;
    }

    public void setId_Order(int id_Order) {
        Id_Order = id_Order;
    }

    public int getId_Product() {
        return Id_Product;
    }

    public void setId_Product(int id_Product) {
        Id_Product = id_Product;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }
}
