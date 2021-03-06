package com.github.henmja.bachelor.spacebattle.network;

import com.github.henmja.bachelor.spacebattle.game.Spacebattle;

import org.glassfish.tyrus.client.ClientManager;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;

public class GameSession {
	private final Spacebattle game;
	private Session backend;

	private String player1;
	private String player2;
	private String player1Username = "Player 1";
	private String player2Username = "Player 2";
	private long newShot1;
	private long oldShot1;
	private long oldShot2;
	private long newShot2;

	public GameSession(Spacebattle game) throws DeploymentException {
		this.game = game;
	}

	public void connect() throws URISyntaxException, IOException,
			DeploymentException {
		ClientManager client = ClientManager.createClient();
		client.connectToServer(WebsocketClient.class, new URI(
				"ws://localhost:3001/ws"));
	}

	public void onOpen(Session session) throws IOException, EncodeException {
		backend = session;
		player1 = "";
		player2 = "";

		sendToBackend("identify", "game");
	}

	public void onMessage(Session session, String message) throws IOException,
			EncodeException {
		JsonReader jsonReader = Json.createReader(new StringReader(message));
		JsonObject jsonObj = jsonReader.readObject();
		jsonReader.close();

		if (!(jsonObj.containsKey("action") && jsonObj.containsKey("data"))) {
			return;
		}

		String action = jsonObj.getString("action");

		switch (action) {
		case "get username": {
			JsonArray client = jsonObj.getJsonArray("data");

			String id = client.get(0).toString();
			String username = client.get(1).toString();

			if (player1.equals(id)) {
				player1Username = username;
			} else if (player2.equals(id)) {
				player2Username = username;
			}

			break;
		}

		case "dropped client": {
			String id = jsonObj.getString("data");

			if (player1.equals(id)) {
				player1 = "";
			} else if (player2.equals(id)) {
				player2 = "";
			}

			sendAvailablePaddles("all");

			break;
		}

		case "play as": {
			String id = jsonObj.getString("from");
			String data = jsonObj.getString("data");

			if (data.equals("")) {
				sendAvailablePaddles(id);

				break;
			}

			if (player1.equals("") && data.equals("left")) {
				player1 = id;
			} else if (player2.equals("") && data.equals("right")) {
				player2 = id;
			}

			sendToBackend("get username", id);
			sendAvailablePaddles("all");

			break;
		}

		case "move": {
			String data = jsonObj.getJsonNumber("data").toString();

			float position;

			try {
				position = Float.parseFloat(data);
			} catch (Exception e) {
				position = -1;
			}

			if (!jsonObj.containsKey("from")) {
				return;
			}

			String from = jsonObj.getString("from");

			if (player1 != null && player1.equals(from)) {
				game.movePlayer1(position);
			} else if (player2 != null && player2.equals(from)) {
				game.movePlayer2(position);
			}

			break;
		}
		case "moveUp": {
			String data = jsonObj.getJsonNumber("data").toString();

			float position;

			try {
				position = Float.parseFloat(data);
			} catch (Exception e) {
				position = -1;
			}

			if (!jsonObj.containsKey("from")) {
				return;
			}

			String from = jsonObj.getString("from");

			if (player1 != null && player1.equals(from)) {
				game.movePlayer1Up(position);
			} else if (player2 != null && player2.equals(from)) {
				game.movePlayer2Up(position);
			}

			break;
		}
		case "moveLeft": {
			String data = jsonObj.getJsonNumber("data").toString();

			float position;

			try {
				position = Float.parseFloat(data);
			} catch (Exception e) {
				position = -1;
			}

			if (!jsonObj.containsKey("from")) {
				return;
			}

			String from = jsonObj.getString("from");

			if (player1 != null && player1.equals(from)) {
				game.movePlayer1Left(position);
			} else if (player2 != null && player2.equals(from)) {
				game.movePlayer2Left(position);
			}

			break;
		}
		case "moveRight": {
			String data = jsonObj.getJsonNumber("data").toString();

			float position;

			try {
				position = Float.parseFloat(data);
			} catch (Exception e) {
				position = -1;
			}

			if (!jsonObj.containsKey("from")) {
				return;
			}

			String from = jsonObj.getString("from");

			if (player1 != null && player1.equals(from)) {
				game.movePlayer1Right(position);
			} else if (player2 != null && player2.equals(from)) {
				game.movePlayer2Right(position);
			}

			break;
		}
		case "shoot": {

			if (!jsonObj.containsKey("from")) {
				return;
			}

			String from = jsonObj.getString("from");
			newShot1 = System.currentTimeMillis();
			newShot2 = System.currentTimeMillis();
			if (player1 != null && player1.equals(from)
					&& newShot1 - oldShot1 > 1200) {
				game.p1Shoot();
				oldShot1 = System.currentTimeMillis();

			} else if (player2 != null && player2.equals(from)
					&& newShot2 - oldShot2 > 1200) {
				game.p2Shoot();
				oldShot2 = System.currentTimeMillis();
			}

			break;
		}
		}

	}

	private void sendAvailablePaddles(String id) throws IOException,
			EncodeException {
		backend.getBasicRemote()
				.sendObject(
						Json.createObjectBuilder()
								.add("action", "pass through")
								.add("data",
										Json.createObjectBuilder()
												.add("action", "play as")
												.add("data",
														Json.createObjectBuilder()
																.add("left",
																		player1)
																.add("right",
																		player2)))
								.add("to", id).build());
	}

	private void sendToBackend(String action, String data) throws IOException,
			EncodeException {
		backend.getBasicRemote().sendObject(
				Json.createObjectBuilder().add("action", action)
						.add("data", data).build());
	}

	public String getPlayer1Username() {
		return player1Username;
	}

	public String getPlayer2Username() {
		return player2Username;
	}
}
