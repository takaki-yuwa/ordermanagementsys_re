package model.order;

import java.util.List;

	public class OrderInfo {
		private int order_id;
		private String order_time;
		private int product_quantity;
		private int table_number;
		private int order_flag;
		private int product_id;
		private String product_name;
		private String category_name;
		private int product_price;
		private int product_stock;
		private List<OrderToppingInfo> order_topping;
	
		public OrderInfo(int order_id, String order_time, int product_quantity, int table_number, int order_flag, int product_id, String product_name, String category_name, int product_price, int product_stock, List<OrderToppingInfo> order_topping) {
			this.order_id = order_id;
			this.order_time = order_time;
	        this.product_quantity = product_quantity;
	        this.table_number = table_number;
	        this.order_flag = order_flag;
	        this.product_id = product_id;
	        this.product_name = product_name;
	        this.category_name = category_name;
	        this.product_price = product_price;
	        this.product_stock = product_stock;
	        this.order_topping = order_topping;
	    }
	
	    // 各フィールドに対応するゲッターを作成
	
	    public int getOrderId() {
	        return order_id;
	    }
	    
	    public String getOrderTime() {
	        return order_time;
	    }
	
	    public int getProductQuantity() {
	        return product_quantity;
	    }
	
	    public int getTableNumber() {
	        return table_number;
	    }
	
	    public int getOrderFlag() {
	        return order_flag;
	    }
	    
	    public int getProductId() {
	        return product_id;
	    }
	
	    public String getProductName() {
	        return product_name;
	    }
	
	    public String getCategoryName() {
	        return category_name;
	    }
	
	    public int getProductPrice() {
	        return product_price;
	    }
	
	    public int getProductStock() {
	        return product_stock;
	    }
	
	    public List<OrderToppingInfo> getOrderTopping() {
	        return order_topping;
	    }
	 // toString() メソッドの追加
	    @Override
	    public String toString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("OrderInfo{")
	          .append("order_id=").append(order_id)
	          .append("order_time=").append(order_time)
	          .append(", product_quantity=").append(product_quantity)
	          .append(", table_number=").append(table_number)
	          .append(", order_flag=").append(order_flag)
	          .append(", product_id='").append(product_id).append('\'')
	          .append(", product_name='").append(product_name).append('\'')
	          .append(", category_name='").append(category_name).append('\'')
	          .append(", product_price=").append(product_price)
	          .append(", product_stock=").append(product_stock)
	          .append(", order_topping=").append(order_topping)
	          .append('}');
	        return sb.toString();
	    }
	}
