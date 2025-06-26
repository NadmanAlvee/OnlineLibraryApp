package com.aiublibrary.libraryapp.service;
import com.aiublibrary.libraryapp.model.User;

import org.json.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class UserManager {

    private static final String USERS_FILE_NAME = "users.json";
    private List<User> users;
    private String usersFilePath;

    public UserManager() {
        users = new ArrayList<>();
        initializeUsersFile();
        loadUsers();
    }

    private void initializeUsersFile() {
        File file = new File("src/main/resources/" + USERS_FILE_NAME);
        usersFilePath = file.getAbsolutePath();

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[]");
                }
                System.out.println("Created new users.json at: " + usersFilePath);
            } catch (IOException e) {
                System.err.println("Error creating users.json at: " + usersFilePath + " - " + e.getMessage());
            }
        } else {
            System.out.println("Located users.json at: " + usersFilePath);
        }
    }

    private void loadUsers() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(usersFilePath)));
            if (content.trim().isEmpty() || content.trim().equals("[]")) {
                users = new ArrayList<>();
                return;
            }
            JSONArray jsonUsers = new JSONArray(content);
            users = new ArrayList<>();
            for (int i = 0; i < jsonUsers.length(); i++) {
                JSONObject userObj = jsonUsers.getJSONObject(i);
                String username = userObj.getString("username");
                String password = userObj.getString("password");
                users.add(new User(username, password));
            }
            System.out.println("Users loaded: " + users.size());
        } catch (IOException e) {
            System.err.println("Error reading users.json from: " + usersFilePath + " - " + e.getMessage());
            users = new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error parsing users.json from: " + usersFilePath + " - " + e.getMessage());
            users = new ArrayList<>();
        }
    }

    private void saveUsers() {
        JSONArray jsonUsers = new JSONArray();
        for (User user : users) {
            JSONObject userObj = new JSONObject();
            userObj.put("username", user.getUsername());
            userObj.put("password", user.getPassword());
            jsonUsers.put(userObj);
        }

        try (FileWriter file = new FileWriter(usersFilePath)) {
            file.write(jsonUsers.toString(4));
            file.flush();
            System.out.println("Users saved to " + usersFilePath);
        } catch (IOException e) {
            System.err.println("Error writing users.json to: " + usersFilePath + " - " + e.getMessage());
        }
    }

    public boolean registerUser(String username, String password) {
        if (findUserByUsername(username).isPresent()) {
            System.out.println("Registration failed: Username '" + username + "' already exists.");
            return false;
        }
        User newUser = new User(username, password);
        users.add(newUser);
        saveUsers();
        System.out.println("User '" + username + "' registered successfully.");
        return true;
    }

    public boolean authenticateUser(String username, String password) {
        Optional<User> foundUser = findUserByUsername(username);
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            if (user.getPassword().equals(password)) {
                System.out.println("User '" + username + "' authenticated successfully.");
                return true;
            }
        }
        System.out.println("Authentication failed for user '" + username + "'.");
        return false;
    }

    private Optional<User> findUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }
}
