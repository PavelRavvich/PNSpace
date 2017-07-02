package ru.pnspace;

import javax.websocket.*;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;


@ClientEndpoint
public class ChatClientEndpoint {
    private CountDownLatch latch;
    private AtomicReference<String> resp = new AtomicReference<>();

    @OnOpen
    public void onOpen(Session session) {
        try {

            final Object obj = session.getUserProperties().get("cache");


            for (String msg : (List<String>) obj){
                session.getBasicRemote().sendText(msg);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @OnMessage
    public void processMessage(String message) {
        System.out.println(message);
        resp.set(message);
        latch.countDown();
    }


}
