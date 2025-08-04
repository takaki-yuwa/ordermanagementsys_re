package model.orderditails;

import java.util.List;

public class OrderDeleteInfo {
	private int order_id;
	private int product_id;
	private int product_quantity;
	private List<OrderDeleteToppingInfo> order_topping;

	// デフォルトコンストラクタ
	public OrderDeleteInfo() {
	}

	// 必要な場合のコンストラクタ
	public OrderDeleteInfo(int order_id, int product_id, int product_quantity, List<OrderDeleteToppingInfo> order_topping) {
		this.order_id = order_id;
		this.product_id = product_id;
		this.product_quantity = product_quantity;
		this.order_topping = order_topping;
	}

	// getter
	public int getOrderId() {
		return order_id;
	}

	public int getProductId() {
		return product_id;
	}

	public int getProductQuantity() {
		return product_quantity;
	}

	public List<OrderDeleteToppingInfo> getOrderTopping() {
		return order_topping;
	}

	// setter
	public void setOrderId(int order_id) {
		this.order_id = order_id;
	}

	public void setProductId(int product_id) {
		this.product_id = product_id;
	}

	public void setProductQuantity(int product_quantity) {
		this.product_quantity = product_quantity;
	}

	public void setOrderTopping(List<OrderDeleteToppingInfo> order_topping) {
		this.order_topping = order_topping;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("OrderDetailsInfo{")
				.append("order_id=").append(order_id)
				.append(", product_id=").append(product_id)
				.append(", product_quantity=").append(product_quantity)
				.append(", order_topping=").append(order_topping)
				.append('}');
		return sb.toString();
	}
}
