package hdgh0g.albumfinder.utils;

import api.soup.MySoup;
import api.user.User;
import api.util.CouldNotLoadException;

public class WhatCdConnectionUtils {

    final private static String SITE_NAME = "what.cd";
    final private static String LOGIN_PAGE = "login.php";

    private static boolean connected = false;

    public static boolean connect(String username, String password) {
        MySoup.setSite(SITE_NAME);
        try {
            MySoup.login(LOGIN_PAGE, username, password, true);
        } catch (CouldNotLoadException e) {
            connected = false;
            return false;
        }
        connected = true;
        return true;
    }

    public static boolean isConnected() {
        return connected;
    }

    public static User getLoggedInUser() {
        return User.fromId(MySoup.getUserId());
    }
}
