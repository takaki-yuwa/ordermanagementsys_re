package model.orderditails;

public class OrderDeleteToppingInfo {
    private int topping_id;
    private int topping_stock;

    // デフォルトコンストラクタ
    public OrderDeleteToppingInfo() {}

    // 引数付きコンストラクタ
    public OrderDeleteToppingInfo(int topping_id, int topping_stock) {
        this.topping_id = topping_id;
        this.topping_stock = topping_stock;
    }

    // getter
    public int getToppingId() {
        return topping_id;
    }

    public int getToppingStock() {
        return topping_stock;
    }

    // setter
    public void setToppingId(int topping_id) {
        this.topping_id = topping_id;
    }

    public void setToppingStock(int topping_stock) {
        this.topping_stock = topping_stock;
    }

    @Override
    public String toString() {
        return "OrderDetailsToppingInfo{" +
               "topping_id=" + topping_id +
               ", topping_stock=" + topping_stock +
               '}';
    }
}
