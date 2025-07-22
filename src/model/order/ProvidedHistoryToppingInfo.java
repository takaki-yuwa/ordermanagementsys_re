package model.order;

public class ProvidedHistoryToppingInfo {
	private int topping_id;
	private int topping_quantity;
	private String topping_name;
	private int topping_price;
	private int topping_stock;

	public ProvidedHistoryToppingInfo(int topping_id, int topping_quantity, String topping_name, int topping_price, int topping_stock) {
		this.topping_id = topping_id;
		this.topping_quantity = topping_quantity;
		this.topping_name = topping_name;
		this.topping_price = topping_price;
		this.topping_stock = topping_stock;
	}

	public int getToppingId() {
		return topping_id;
	}
	
	public int getToppingQuantity() {
		return topping_quantity;
	}

	public String getToppingName() {
		return topping_name;
	}

	public int getToppingPrice() {
		return topping_price;
	}

	public int getToppingStock() {
		return topping_stock;
	}
	@Override
    public String toString() {
        return "ProvidedHistoryToppingInfo{" +
                "topping_id=" + topping_id +
                ", topping_quantity=" + topping_quantity +
                ", topping_name='" + topping_name + '\'' +
                ", topping_price=" + topping_price +
                ", topping_stock=" + topping_stock +
                '}';
    }
}
