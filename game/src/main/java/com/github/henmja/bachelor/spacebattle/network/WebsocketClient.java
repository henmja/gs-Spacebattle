package com.github.henmja.bachelor.spacebattle.network;

import com.github.henmja.bachelor.spacebattle.Application;

import javax.websocket.*;

import java.io.IOException;

@ClientEndpoint
public class WebsocketClient {
    private GameSession gameSession = Application.getGameSession();

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {
        gameSession.onOpen(session);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException, EncodeException {
        gameSession.onMessage(session, message);
    }
}
