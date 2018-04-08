package com.webischia.serveranalysis.Service;

/**
 * Created by f on 08.04.2018.
 */

public interface LoginService {
    public void loginCheck(final String username, final String password);
    void saveUser(String username);
    }
