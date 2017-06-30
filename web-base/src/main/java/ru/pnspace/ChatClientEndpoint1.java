package ru.pnspace;

import javax.websocket.*;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;


@ClientEndpoint
public class ChatClientEndpoint1 {
    public static String TEXT = "Client1 joins";
    public static CountDownLatch latch;
    public static String response;

    @OnOpen
    public void onOpen(Session session) {
        try {
            session.getBasicRemote().sendText(TEXT);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @OnMessage
    public void processMessage(String message) {
        response = message;
        latch.countDown();
    }
}
