package model.orderditails;

import java.util.List;

public class OrderUpdateInfo {
	private int order_id;
	private int order_price;
	private int product_quantity;
	private int product_id;
	private int product_stock;
	private List<OrderUpdateToppingInfo> order_topping;

	// デフォルトコンストラクタ
	public OrderUpdateInfo() {
	}

	// 必要な場合のコンストラクタ
	public OrderUpdateInfo(int order_id, int order_price, int product_quantity, int product_id, int product_stock,
			List<OrderUpdateToppingInfo> order_topping) {
		this.order_id = order_id;
		this.order_price = order_price;
		this.product_quantity = product_quantity;
		this.product_id = product_id;
		this.product_stock = product_stock;
		this.order_topping = order_topping;
	}

	// getter
	public int getOrderId() {
		return order_id;
	}

	public int getOrderPrice() {
		return order_price;
	}

	public int getProductQuantity() {
		return product_quantity;
	}

	public int getProductId() {
		return product_id;
	}

	public int getProductStock() {
		return product_stock;
	}

	public List<OrderUpdateToppingInfo> getOrderTopping() {
		return order_topping;
	}

	// setter
	public void setOrderId(int order_id) {
		this.order_id = order_id;
	}

	public void setOrderPrice(int order_price) {
		this.order_price = order_price;
	}

	public void setProductQuantity(int product_quantity) {
		this.product_quantity = product_quantity;
	}

	public void setProductId(int product_id) {
		this.product_id = product_id;
	}

	public void setProductStock(int product_stock) {
		this.product_stock = product_stock;
	}

	public void setOrderTopping(List<OrderUpdateToppingInfo> order_topping) {
		this.order_topping = order_topping;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("OrderDetailsInfo{")
				.append("order_id=").append(order_id)
				.append("order_price=").append(order_price)
				.append(", product_quantity=").append(product_quantity)
				.append(", product_id=").append(product_id)
				.append(", product_stock=").append(product_stock)
				.append(", order_topping=").append(order_topping)
				.append('}');
		return sb.toString();
	}
}
