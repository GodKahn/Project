package login.test;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.websocket.Session;

import org.json.JSONException;
import org.json.JSONObject;

import com.friend_list.model.FriendListService;
import com.member_profile.model.MemberProfileService;
import com.member_profile.model.MemberProfileVO;

public class LoginListener implements HttpSessionAttributeListener {

	private Map<String, ArrayList<HttpSession>> loginRecord = login.test.LoginInfo.getLoginRecord();

	FriendListService fls = new FriendListService();
	MemberProfileService mps = new MemberProfileService();

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {

		if ("mem_num".equals(event.getName())) {

			System.out.println("會員:[" + mps.getMyProfile((String) event.getValue()).getMem_name() + "]登入");

			if (loginRecord.containsKey((String) event.getValue())) {

				loginRecord.get((String) event.getValue()).add(event.getSession());

			} else {

				ArrayList<HttpSession> loginSesseions = new ArrayList<HttpSession>();
				loginSesseions.add(event.getSession());

				loginRecord.put((String) event.getValue(), loginSesseions);

			}

			JSONObject sendJsonObj = new JSONObject();

			try {

				sendJsonObj.put("action", "friendLogin");
				sendJsonObj.put("friendName", mps.getMyProfile((String) event.getValue()).getMem_name());
				sendJsonObj.put("friendNumber", (String) event.getValue());

			} catch (JSONException e) {
				e.printStackTrace();
			}

			for (MemberProfileVO fiends : fls.getFirends((String) event.getValue())) {

				if (loginRecord.containsKey(fiends.getMem_num())) {

					for (Session friendSession : login.test.MemberChatServer.getFriendListSessions()
							.get(fiends.getMem_num())) {

						if (friendSession.isOpen()) {

							synchronized (friendSession) {

								friendSession.getAsyncRemote().sendText(sendJsonObj.toString());

							}

						}

					}

				}

			}

		}

	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {

		if ("mem_num".equals(event.getName())) {

			String mem_num = (String) event.getValue();

			JSONObject sendJsonObj = new JSONObject();

			try {

				sendJsonObj.put("action", "friendLogout");
				sendJsonObj.put("friendName", mps.getMyProfile(mem_num).getMem_name());
				sendJsonObj.put("friendNumber", mem_num);

			} catch (JSONException e) {
				e.printStackTrace();
			}

			for (MemberProfileVO fiends : fls.getFirends(mem_num)) {

				if (login.test.MemberChatServer.getFriendListSessions().containsKey(fiends.getMem_num())) {

					for (Session friendSession : login.test.MemberChatServer.getFriendListSessions()
							.get(fiends.getMem_num())) {

						if (friendSession.isOpen()) {

							synchronized (friendSession) {

								friendSession.getAsyncRemote().sendText(sendJsonObj.toString());

							}

						}

					}

				}

			}

			if (loginRecord.containsKey((String) event.getValue())) {

				loginRecord.get((String) event.getValue()).remove(event.getSession());

				if (loginRecord.get((String) event.getValue()).isEmpty()) {

					loginRecord.remove((String) event.getValue());

				}

			}

		}

	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub

	}

}
