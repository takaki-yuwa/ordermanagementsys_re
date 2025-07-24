package model.product;

/*
 * 商品情報を保持するモデルクラス
 * 商品ID、名前、カテゴリー、価格
 */
public class ProductFormInfo {
	private int id;
	private String name;
	private String category;
	private int price;


	public ProductFormInfo(int id, String name, String category, int price) {
		this.setId(id);
		this.setName(name);
		this.setCategory(category);
		this.setPrice(price);
	}
	
	//空の情報
	public static ProductFormInfo createEmpty() {
		return new ProductFormInfo(0,"","",0);
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
}
