package ru.pnspace;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pavel on 02.07.17.
 */
public class CashSingleton {

    private Map<Session, List<String>> cash = new ConcurrentHashMap<>();

    private static volatile CashSingleton instance;

    public static CashSingleton getInstance() {
        CashSingleton localInstance = instance;
        if (localInstance == null) {
            synchronized (CashSingleton.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CashSingleton();
                }
            }
        }
        return localInstance;
    }



    public boolean sessionIsNew(final Session session) {
        return cash.get(session) == null;
    }

    /**
     * For create new pair Session - Massages list.
     *
     * @param session
     * @param msg
     */
    public void put(final Session session, final String msg) {
        final List<String> massages = new ArrayList<>(100);
        massages.add(msg);
        cash.put(session, massages);
    }

    public void add(final Session session, final String msg) {
        cash.get(session).add(msg);
    }

    public void clear() {
        cash.clear();
    }

    public List<String> getAll() {
        final ArrayList<String> res = new ArrayList<>();
        final Collection<List<String>> values = cash.values();
        for (List<String> v : values) {
            res.addAll(v);
        }
        return res;
    }

}
