package model.orderditails;

public class OrderDeleteToppingInfo {
	private int topping_id;
	private int topping_quantity;

	// デフォルトコンストラクタ
	public OrderDeleteToppingInfo() {
	}

	// 引数付きコンストラクタ
	public OrderDeleteToppingInfo(int topping_id, int topping_quantity) {
		this.topping_id = topping_id;
		this.topping_quantity = topping_quantity;
	}

	// getter
	public int getToppingId() {
		return topping_id;
	}

	public int getToppingQuantity() {
		return topping_quantity;
	}

	// setter
	public void setToppingId(int topping_id) {
		this.topping_id = topping_id;
	}

	public void setToppingQuantity(int topping_quantity) {
		this.topping_quantity = topping_quantity;
	}

	@Override
	public String toString() {
		return "OrderDetailsToppingInfo{" +
				"topping_id=" + topping_id +
				", topping_quantity=" + topping_quantity +
				'}';
	}
}
