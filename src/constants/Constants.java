package constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {

    // 商品一覧で使用するカテゴリー名
    public static final List<String> PRODUCT_CATEGORY_LIST = Arrays.asList(
            "全て", "お好み焼き", "もんじゃ焼き", "鉄板焼き", "サイドメニュー", "ソフトドリンク", "お酒", "ボトル");

    // カテゴリー一覧で使用するカテゴリー名
    public static final List<String> CATEGORY_LIST = Arrays.asList(
            "お好み焼き", "もんじゃ焼き", "鉄板焼き", "サイドメニュー", "ソフトドリンク", "お酒", "ボトル");

    // 注文リストのカテゴリー名（DBの table_id を後で追加）
    public static final List<String> ORDER_CATEGORY_LIST = new ArrayList<>();

    // 提供済み履歴のカテゴリー名（DBの table_id を後で追加）
    public static final List<String> HISTORY_TABLE_LIST = new ArrayList<>();

    // クラスロード時に table_id を取得してリストに追加
    static {
        // ORDER_CATEGORY_LIST に最初のカテゴリを追加
        ORDER_CATEGORY_LIST.addAll(PRODUCT_CATEGORY_LIST);

        // HISTORY_TABLE_LIST に "全て" を追加
        HISTORY_TABLE_LIST.add("全て");

        String sql = "SELECT table_id FROM table_master";

        try (
            Connection con = util.DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                int tableId = rs.getInt("table_id");
                // 文字列に変換してリストに追加
                ORDER_CATEGORY_LIST.add(String.valueOf(tableId + "卓"));
                HISTORY_TABLE_LIST.add(String.valueOf(tableId + "卓"));
            }
        } catch (Exception e) {
            e.printStackTrace();  // 必要に応じてログ出力に変更
        }
    }
}
