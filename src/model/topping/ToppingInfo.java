package model.topping;

/*
 * トッピング情報を保持するモデルクラス
 * トッピングID、名前、価格、在庫、表示フラグ
 */
public class ToppingInfo {
	private int id;
	private String name;
	private int price;
	private int stock;
	private int visible_flag;

	public ToppingInfo(int id, String name, int price, int stock, int visible_flag) {
		this.setId(id);
		this.setName(name);
		this.setPrice(price);
		this.setStock(stock);
		this.setVisible_flag(visible_flag);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
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
