package login.test;

import java.io.*;
import java.util.*;

import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;
import org.json.JSONObject;

import com.member_profile.model.MemberProfileService;

import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.OnClose;
import javax.servlet.http.Part;
import javax.websocket.CloseReason;

@ServerEndpoint("/MemberChatServer/{myName}")
public class MemberChatServer {

	private static Map<String, Set<Session>> friendListSessions = Collections.synchronizedMap(new HashMap<String, Set<Session>>());
	private static Map<String, Set<Session>> groupBuySessions = Collections.synchronizedMap(new HashMap<String, Set<Session>>());

	@OnOpen
	public void onOpen(@PathParam("myName") String myName, Session userSession) throws IOException {

		if ("MB".equals(myName.substring(0, 2))) {

			if (friendListSessions.containsKey(myName)) {

				friendListSessions.get(myName).add(userSession);

			} else {

				Set<Session> userSessions = Collections.synchronizedSet(new HashSet<Session>());
				userSessions.add(userSession);
				friendListSessions.put(myName, userSessions);

			}

		} else if ("MO".equals(myName.substring(0, 2))) {

			if (groupBuySessions.containsKey(myName)) {

				groupBuySessions.get(myName).add(userSession);

			} else {

				Set<Session> userSessions = Collections.synchronizedSet(new HashSet<Session>());
				userSessions.add(userSession);
				groupBuySessions.put(myName, userSessions);

			}

		}

	}


	@OnMessage
	public void onMessage(Session userSession, String message) {

		JSONObject jsonObj = null;

		try {

			jsonObj = new JSONObject(message);

			if ("talkMessage".equals((String) jsonObj.get("action"))) {

				MemberProfileService mps = new MemberProfileService();

				boolean isOnline = false;

				String senderName = mps.getMyProfile((String) jsonObj.get("senderNumber")).getMem_name();

				jsonObj.put("senderName", senderName);

				if (friendListSessions.containsKey((String) jsonObj.get("targetNumber"))) {

					isOnline = true;

					for (Session targetSession : friendListSessions.get((String) jsonObj.get("targetNumber"))) {

						if (targetSession.isOpen()) {

							synchronized (targetSession) {

								targetSession.getAsyncRemote().sendText(jsonObj.toString());

							}

						}

					}

				}

				for (Session senderSession : friendListSessions.get((String) jsonObj.get("senderNumber"))) {

					if (senderSession.isOpen()) {

						synchronized (senderSession) {

							senderSession.getAsyncRemote().sendText(jsonObj.toString());
						}

						if (!isOnline) {

							JSONObject sendTargetOffline = new JSONObject();

							sendTargetOffline.put("action", "sendIsRead");
							sendTargetOffline.put("senderNumber", (String) jsonObj.get("targetNumber"));
							sendTargetOffline.put("message", "N");

							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							synchronized (senderSession) {

								senderSession.getAsyncRemote().sendText(sendTargetOffline.toString());
							}

						}

					}

				}

			} else if ("sendIsRead".equals((String) jsonObj.get("action"))) {

				if (friendListSessions.containsKey((String) jsonObj.get("targetNumber"))) {

					for (Session targetSession : friendListSessions.get((String) jsonObj.get("targetNumber"))) {

						if (targetSession.isOpen()) {

							synchronized (targetSession) {

								targetSession.getAsyncRemote().sendText(jsonObj.toString());

							}

						}

					}

				}

			} else if ("groupBuyTalk".equals((String) jsonObj.get("action"))) {

				if (groupBuySessions.containsKey((String) jsonObj.get("merOr"))) {

					jsonObj.put("mySessionID", userSession.getId());

					for (Session targetSession : groupBuySessions.get((String) jsonObj.get("merOr"))) {

						if (targetSession.isOpen()) {

							synchronized (targetSession) {

								targetSession.getAsyncRemote().sendText(jsonObj.toString());

							}

						}

					}

				}

			} else if ("groupBuysendIsRead".equals((String) jsonObj.get("action"))) {

				if (groupBuySessions.containsKey((String) jsonObj.get("merOr"))) {

					for (Session targetSession : groupBuySessions.get((String) jsonObj.get("merOr"))) {

						if (!jsonObj.has("targetSessionID")) {

							synchronized (targetSession) {

								targetSession.getAsyncRemote().sendText(jsonObj.toString());

							}

						} else if (targetSession.getId().equals((String) jsonObj.get("targetSessionID"))
								&& targetSession.isOpen()) {

							synchronized (targetSession) {

								targetSession.getAsyncRemote().sendText(jsonObj.toString());

							}

						}

					}

				}

			} else if ("GroupBuySendImg".equals((String) jsonObj.get("action"))) {

				if (groupBuySessions.containsKey((String) jsonObj.get("merOr"))) {

					jsonObj.put("action", "groupBuyTalk");
					jsonObj.put("mySessionID", userSession.getId());
					jsonObj.put("sendImg", jsonObj.get("message"));
					jsonObj.remove("message");

					for (Session targetSession : groupBuySessions.get((String) jsonObj.get("merOr"))) {

						if (targetSession.isOpen()) {

							synchronized (targetSession) {

								targetSession.getAsyncRemote().sendText(jsonObj.toString());

							}

						}

					}

				}

			} else if ("sendImg".equals((String) jsonObj.get("action"))) {

				MemberProfileService mps = new MemberProfileService();

				boolean isOnline = false;

				String senderName = mps.getMyProfile((String) jsonObj.get("senderNumber")).getMem_name();

				jsonObj.put("action", "talkMessage");
				jsonObj.put("senderName", senderName);
				jsonObj.put("sendImg", jsonObj.get("message"));
				jsonObj.remove("message");

				if (friendListSessions.containsKey((String) jsonObj.get("targetNumber"))) {

					isOnline = true;

					for (Session targetSession : friendListSessions.get((String) jsonObj.get("targetNumber"))) {

						if (targetSession.isOpen()) {

							targetSession.getAsyncRemote().sendText(jsonObj.toString());

						}

					}

				}

				for (Session senderSession : friendListSessions.get((String) jsonObj.get("senderNumber"))) {

					if (senderSession.isOpen()) {

						senderSession.getAsyncRemote().sendText(jsonObj.toString());

					}

					if (!isOnline) {

						JSONObject sendTargetOffline = new JSONObject();

						sendTargetOffline.put("action", "sendIsRead");
						sendTargetOffline.put("senderNumber", (String) jsonObj.get("targetNumber"));
						sendTargetOffline.put("message", "N");

						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						synchronized (senderSession) {

							senderSession.getAsyncRemote().sendText(sendTargetOffline.toString());
						}

					}

				}

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		e.printStackTrace();
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason, @PathParam("myName") String myName) {

		if (friendListSessions.containsKey(myName)) {

			if (friendListSessions.get(myName).contains(userSession)) {

				friendListSessions.get(myName).remove(userSession);

			}

		}

		if (groupBuySessions.containsKey(myName)) {

			if (groupBuySessions.get(myName).contains(userSession)) {

				groupBuySessions.get(myName).remove(userSession);

			}

		}

	}

	public static Map<String, Set<Session>> getFriendListSessions() {
		return friendListSessions;
	}

	public static Map<String, Set<Session>> getGroupBuySessions() {
		return groupBuySessions;
	}

}
