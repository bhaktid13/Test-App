package com.example.test_clevertap;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.clevertap.android.sdk.CleverTapAPI;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private CleverTapAPI cleverTap;
    private EditText nameInput;
    private EditText emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize CleverTap SDK instance
        cleverTap = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);

        // Find the views by their ids
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        Button loginButton = findViewById(R.id.login_button);
        Button raiseEventButton = findViewById(R.id.raise_event);

        // Set click listeners for the buttons
        loginButton.setOnClickListener(this::onUserLogin);
        raiseEventButton.setOnClickListener(this::raiseTestEvent);

        // Raise "Product Viewed" event on application launch
        raiseProductViewedEvent();
        sendPushNotification();
    }

    private void raiseProductViewedEvent() {
        HashMap<String, Object> eventData = new HashMap<>();
        eventData.put("Product", "Casio Chronograph Watch"); // Example product name
        eventData.put("Category", "Men's Accessories"); // Example product category

        // Record the "Product Viewed" event
        cleverTap.pushEvent("Product Viewed", eventData);
    }

    private void sendPushNotification() {
        // Customize the push notification content
        HashMap<String, Object> data = new HashMap<>();
        data.put("title", "New Product Alert");
        data.put("message", "Check out our latest product!");
        // Add any other custom data as needed
        // Send the push notification
        cleverTap.pushEvent("CTNotification", data);
    }

    // Method to handle user login
    public void onUserLogin(View view) {
        // Get the user's name and email from the input fields
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();

        // Create a profile data object
        HashMap<String, Object> profileData = new HashMap<>();
        profileData.put("Name", name);
        profileData.put("Email", email);
        // Add other user profile data as needed

        // Call the onUserLogin function on the cleverTap instance
        cleverTap.onUserLogin(profileData);

        // Send push notification to greet the user
        sendGreetingPushNotification(name);
    }

    private void sendGreetingPushNotification(String userName) {
        // Customize the push notification content for greeting
        HashMap<String, Object> data = new HashMap<>();
        data.put("title", "Hi " + userName);
        data.put("message", "Welcome to our app!");

        // Send the push notification
        cleverTap.pushEvent("Greeting Push", data);
    }

    // Method to raise "TEST" event
    public void raiseTestEvent(View view) {
        // Record the "TEST" event
        cleverTap.pushEvent("TEST");
    }
}
