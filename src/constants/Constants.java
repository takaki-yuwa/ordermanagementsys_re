package constants;

import java.util.Arrays;
import java.util.List;

public class Constants {
	//商品一覧で使用するカテゴリー名
	public static final List<String> PRODUCT_CATEGORY_LIST = Arrays.asList(
			"全て","お好み焼き","もんじゃ焼き","鉄板焼き","サイドメニュー","ソフトドリンク","お酒","ボトル");
	
	//カテゴリー一覧で使用するカテゴリー名
	public static final List<String> CATEGORY_LIST = Arrays.asList(
			"お好み焼き","もんじゃ焼き","鉄板焼き","サイドメニュー","ソフトドリンク","お酒","ボトル");
	
	//注文リストのカテゴリー名
		public static final List<String> ORDER_CATEGORY_LIST = Arrays.asList(
				"全て","お好み焼き","もんじゃ焼き","鉄板焼き","サイドメニュー","ソフトドリンク","お酒","ボトル","1卓","2卓","3卓");
		
	//提供済み履歴のカテゴリー名
		public static final List<String> HISTORY_TABLE_LIST = Arrays.asList(
				"全て","1卓","2卓","3卓");

}
