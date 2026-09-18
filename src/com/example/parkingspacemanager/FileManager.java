package com.example.parkingspacemanager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public final class FileManager {

    // Name of the file where parking history is stored
        private static final String FILE_NAME = "parking_history.txt";

    private FileManager() {
        // utility class - not instantiable
    }

    // Method used to save a message to the file
        public static void log(String message) {

            // FileWriter opens the file.
            // true means append instead of replacing existing content.
            try (FileWriter fileWriter = new FileWriter(FILE_NAME, true);

                 // Creating a PrintWriter object
                 PrintWriter writer = new PrintWriter(fileWriter)) {

                // Write current date/time and message to file
                writer.println(LocalDateTime.now() + " - " + message);

            } catch (IOException e) {

                // Handle file-writing errors
                System.out.println("Error writing to history file: " + e.getMessage());
            }
        }
    }

