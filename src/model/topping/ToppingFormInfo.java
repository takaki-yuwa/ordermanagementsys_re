package model.topping;

/*
 * トッピング情報を保持するモデルクラス
 * トッピングID、名前、価格
 */
public class ToppingFormInfo {
	private int id;
	private String name;
	private int price;

	public ToppingFormInfo(int id, String name, int price) {
		this.setId(id);
		this.setName(name);
		this.setPrice(price);
	}

	public ToppingFormInfo(String name, int price) {
		this.setName(name);
		this.setPrice(price);
	}

	//空の情報
	public static ToppingFormInfo createEmpty() {
		return new ToppingFormInfo(0, "", 0);
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

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
