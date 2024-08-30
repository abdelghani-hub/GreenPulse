package services;

import models.User;
import utils.ConsoleUI;

import java.util.HashMap;

public class UserService {
    // A HashMap to store users with their uniqueID as the key
    private final HashMap<Integer, User> users = new HashMap<>();

    // Method to add a new user
    public void addUser(User user) {
        if (users.containsKey(user.getId())) {
            ConsoleUI.displayWarningMessage("ID already exists, Try an other one.");
        } else {
            users.put(user.getId(), user);
            ConsoleUI.displaySuccessMessage("User added successfully.");
        }
    }

    // Method to retrieve a user by their unique ID
    public User getUser(int id) {
        return users.get(id);
    }

    // Method to update an existing user
    public void updateUser(User updatedUser) {
        int id = updatedUser.getId();
        if (users.containsKey(id)) {
            users.put(id, updatedUser);
            ConsoleUI.displaySuccessMessage("User updated successfully.");
        } else {
            ConsoleUI.displayWarningMessage("User not found.");
        }
    }

    // Method to delete a user by their unique ID
    public boolean deleteUser(int id) {
        if (users.containsKey(id)) {
            users.remove(id);
            return true;
        }
        return false;
    }

    // List all users
    public void listAllUsers() {
        if (users.isEmpty()) {
            ConsoleUI.displayWarningMessage("No users available.");
        } else {
            for (User user : users.values()) {
                System.out.println(user);
            }
        }
    }
}
