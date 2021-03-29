import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import makejson.GuestBookJson;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GuestBookJson gb = new GuestBookJson();
		JSONArray arr = gb.findAll(3);
		System.out.println("hello world");
		for(int i=0;i<arr.size();i++) {
			JSONObject obj = (JSONObject) arr.get(i);
			System.out.println(obj.toJSONString(obj));
		}

	  

	}

}
