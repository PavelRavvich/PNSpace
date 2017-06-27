package ru.pnspace;

/**
 * Created by pavel on 28.06.17.
 */
public class Hello {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
    }
}
