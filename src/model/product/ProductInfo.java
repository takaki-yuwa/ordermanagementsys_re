package model.product;

/*
 * 商品情報を保持するモデルクラス
 * 商品ID、名前、カテゴリー、価格、在庫、表示フラグ
 */
public class ProductInfo {
	private int id;
	private String name;
	private String category;
	private int price;
	private int stock;
	private int visible_flag;

	public ProductInfo(int id, String name, String category, int price, int stock, int visible_flag) {
		this.setId(id);
		this.setName(name);
		this.setPrice(price);
		this.setCategory(category);
		this.setStock(stock);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public int getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}

	public int getVisible_flag() {
		return visible_flag;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void setVisible_flag(int visible_flag) {
		this.visible_flag = visible_flag;
	}
}
