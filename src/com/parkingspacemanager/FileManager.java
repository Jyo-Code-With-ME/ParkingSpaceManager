package com.parkingspacemanager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public final class FileManager {


        private static final String HISTORY_FOLDER ="Receipts";
        private static final String FILE_NAME = "parking_history.txt";

    private FileManager() {
        // utility class - not instantiable
    }

    public static String getHistoryFilePath() {
        return System.getProperty("user.home")
                + File.separator
                + HISTORY_FOLDER
                + File.separator
                + FILE_NAME;
    }
    // Method used to save a message to the file
        public static void log(String message) {

            // Make sure the Receipts folder exists before writing to it
            File folder = new File(
                    System.getProperty("user.home") + File.separator + HISTORY_FOLDER
            );

            if (!folder.exists()) {
                folder.mkdirs();
            }

            // FileWriter opens the file.
            // true means append instead of replacing existing content.
            try (FileWriter fileWriter = new FileWriter(getHistoryFilePath(), true);

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

