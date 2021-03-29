package makejson;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GuestBookJson {
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			// 1. JDBC Driver 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 2. 연결하기
			String url = "jdbc:mysql://localhost :3306/webdb?characterEncoding=utf8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "root", "1234");
		} catch (ClassNotFoundException e) {
			System.out.println("error" + e);
		}

		return conn;
	}

	@SuppressWarnings("unchecked")
	public JSONArray findAll(int limit) {
		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONArray guestbook = new JSONArray();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			// 3. SQL 준비
			String sql = "   select no, name, contents, reg_date" + "     from guestbook"
					+ " order by reg_date desc limit ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, limit);
			// 4. 바인딩

			// 5. SQL문 실행
			rs = pstmt.executeQuery();

			// 6. 데이터 가져오기
			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String contents = rs.getString(3);
				String date = rs.getString(4);

				GuestbookVo vo = new GuestbookVo();
				vo.setName(name);
				vo.setNo(no);
				vo.setContents(contents);
				vo.setDate(date);
				JSONObject obj = new JSONObject();
				obj.put("name", name);
				obj.put("no", no);
				obj.put("content", contents);
				obj.put("date", date);
				// System.out.println(obj.get("name"));
				guestbook.add(obj);
				

			}
			JSONObject obj2 = new JSONObject();
			obj2.put("guestbook", guestbook);
			try (FileWriter file = new FileWriter("C:\\Users\\User\\eclipse-workspace2\\javascript-practice\\src\\main\\webapp\\Data.Json")) {
				file.write(obj2.toJSONString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return guestbook;
	}

}
