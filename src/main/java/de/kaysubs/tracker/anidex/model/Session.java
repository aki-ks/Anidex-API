package de.kaysubs.tracker.anidex.model;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

public class Session {
    private final String token;

    public Session(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public Cookie toCookie() {
        BasicClientCookie cookie = new BasicClientCookie("anidex", getToken());
        cookie.setDomain("anidex.info");
        cookie.setPath("/");
        return cookie;
    }
}
