package com.aiublibrary.libraryapp;

import com.aiublibrary.libraryapp.service.UserManager;
import com.aiublibrary.libraryapp.ui.LoginPage;

import javax.swing.*;

public class LibraryApp {

    public static void main(String[] args) {
        UserManager userManager = new UserManager();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginPage loginPage = new LoginPage(userManager);
                loginPage.setVisible(true);
            }
        });
    }
}
