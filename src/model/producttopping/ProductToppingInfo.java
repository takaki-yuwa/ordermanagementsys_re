package model.producttopping;

/*
 * 商品トッピング情報を保持するモデルクラス
 * 商品トッピングID、トッピングID、商品ID
 */
public class ProductToppingInfo {
	private int product_topping_id;
	private int topping_id;
	private int product_id;

	public ProductToppingInfo(int p_t_id, int t_id, int p_id) {
		this.setProduct_topping_id(p_t_id);
		this.setTopping_id(t_id);
		this.setProduct_id(p_id);
	}

	public int getProduct_topping_id() {
		return product_topping_id;
	}

	public int getTopping_id() {
		return topping_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_topping_id(int product_topping_id) {
		this.product_topping_id = product_topping_id;
	}

	public void setTopping_id(int topping_id) {
		this.topping_id = topping_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
}
