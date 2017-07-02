package ru.pnspace;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/chat")
public class ChatEndpoint {

    @OnMessage
    public void onMessage(final Session session, final String msg) {
        try {

            cashing(session, msg);
            newsletter(session, msg);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void newsletter(final Session session, final String msg)
            throws IOException {

        for (Session ses : session.getOpenSessions()) {
            if (ses.isOpen()) {
                ses.getBasicRemote().sendText(msg);
            }
        }
    }

    private void cashing(final Session session, final String msg)
            throws IOException {

        final CashSingleton cash = CashSingleton.getInstance();

        if (cash.sessionIsNew(session)) {
            sendHistory(session, cash);
            cash.put(session, msg);
        } else {
            cash.add(session, msg);
        }
    }

    private void sendHistory(final Session session, final CashSingleton cash)
            throws IOException {

        for (String msg  : cash.getAll()) {
            session.getBasicRemote().sendText(msg);
        }
    }
}
