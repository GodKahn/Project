package login.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class LoginInfo implements Serializable {
	
	private static Map<String, ArrayList<HttpSession>> loginRecord = new HashMap<String, ArrayList<HttpSession>>();
	
	public static Map<String, ArrayList<HttpSession>> getLoginRecord() {
		return loginRecord;
	}
}
