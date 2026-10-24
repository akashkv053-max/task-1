package bot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// This class is our little archivist. It writes down everything you and the bot say 
// into a text file so we have a permanent record of the conversation.
public class ConversationHistory {
    
    // We'll save the chat logs here. It goes up one directory from the compiled code, into the history folder.
    private static final String HISTORY_FILE_PATH = "../history/chat_log.txt";
    private BufferedWriter writer;

    public ConversationHistory() {
        try {
            // Setting 'true' here means we append to the file instead of overwriting it every time we start up.
            writer = new BufferedWriter(new FileWriter(HISTORY_FILE_PATH, true));
            logSystemEvent("--- NEW CHAT SESSION STARTED ---");
        } catch (IOException e) {
            System.err.println("Uh oh, couldn't open the history file! " + e.getMessage());
        }
    }

    // A helper to easily jot down who said what and exactly when they said it.
    public void log(String sender, String message) {
        if (writer != null) {
            try {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                writer.write(String.format("[%s] %s: %s\n", timestamp, sender, message));
                
                // Flush it immediately so we don't lose the message if the app suddenly crashes.
                writer.flush();
            } catch (IOException e) {
                System.err.println("Oops, failed to write to the chat log: " + e.getMessage());
            }
        }
    }

    // Just a quick way to log system-level events (like starting or stopping the bot)
    public void logSystemEvent(String event) {
        if (writer != null) {
            try {
                writer.write("\n" + event + "\n");
                writer.flush();
            } catch (IOException e) {
                System.err.println("Failed to write system event: " + e.getMessage());
            }
        }
    }

    // Always remember to close your files when you're done with them!
    public void close() {
        if (writer != null) {
            try {
                logSystemEvent("--- CHAT SESSION ENDED ---");
                writer.close();
            } catch (IOException e) {
                System.err.println("Error closing the chat log: " + e.getMessage());
            }
        }
    }
}
