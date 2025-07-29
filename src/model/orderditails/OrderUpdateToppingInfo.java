package model.orderditails;

public class OrderUpdateToppingInfo {
    private int topping_id;
    private int topping_quantity;
    private int topping_stock;

    // デフォルトコンストラクタ
    public OrderUpdateToppingInfo() {}

    // 引数付きコンストラクタ
    public OrderUpdateToppingInfo(int topping_id, int topping_quantity, int topping_stock) {
        this.topping_id = topping_id;
        this.topping_quantity = topping_quantity;
        this.topping_stock = topping_stock;
    }

    // getter
    public int getToppingId() {
        return topping_id;
    }

    public int getToppingQuantity() {
        return topping_quantity;
    }

    public int getToppingStock() {
        return topping_stock;
    }

    // setter
    public void setToppingId(int topping_id) {
        this.topping_id = topping_id;
    }

    public void setToppingQuantity(int topping_quantity) {
        this.topping_quantity = topping_quantity;
    }

    public void setToppingStock(int topping_stock) {
        this.topping_stock = topping_stock;
    }

    @Override
    public String toString() {
        return "OrderDetailsToppingInfo{" +
               "topping_id=" + topping_id +
               ", topping_quantity=" + topping_quantity +
               ", topping_stock=" + topping_stock +
               '}';
    }
}
