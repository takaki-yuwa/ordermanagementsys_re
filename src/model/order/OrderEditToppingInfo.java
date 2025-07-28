package model.order;

public class OrderEditToppingInfo {
	private int topping_id;
	private String topping_name;
	private int topping_stock;

	public OrderEditToppingInfo(int topping_id, String topping_name, int topping_stock) {
		this.topping_id = topping_id;
		this.topping_name = topping_name;
		this.topping_stock = topping_stock;
	}

	public String getToppingName() {
		return topping_name;
	}
	
	public int getToppingId() {
	    return topping_id;
	}
	public int getToppingStock() {
	    return topping_stock;
	}

	@Override
	public String toString() {
	    return "OrderEditToppingInfo{" +
	            "topping_id=" + topping_id +
	            ", topping_name='" + topping_name + '\'' +
	            ", topping_stock=" + topping_stock +
	            '}';
    }

}
