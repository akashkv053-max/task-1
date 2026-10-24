package bot;

// This class acts as the "brain" for understanding what the user actually means.
// It cleans up their messy typing and looks for specific keywords to figure out their intent.
public class NLPProcessor {

    // First, we need to clean up the text so our bot doesn't get confused by capital letters or punctuation.
    private String normalizeText(String input) {
        if (input == null) return "";
        
        // Convert everything to lowercase and strip out weird characters, leaving just letters, numbers, and spaces.
        return input.toLowerCase().replaceAll("[^a-z0-9\\s]", "").trim();
    }

    // This method takes the user's sentence and tries to categorize it into a specific "intent".
    public String extractIntent(String rawInput) {
        String cleanInput = normalizeText(rawInput);

        // If they didn't really say anything, just return unknown.
        if (cleanInput.isEmpty()) {
            return "unknown";
        }

        // --- RULE-BASED MATCHING ---
        // Let's check for some common greeting keywords first.
        if (cleanInput.matches(".*\\b(hi|hello|hey|greetings|howdy|sup)\\b.*")) {
            return "greeting";
        }
        
        // Did they ask about the date or time?
        if (cleanInput.matches(".*\\b(time|date|day|today|clock)\\b.*")) {
            return "datetime";
        }
        
        // Did they ask about the weather?
        if (cleanInput.matches(".*\\b(weather|rain|sunny|hot|cold|temperature)\\b.*")) {
            return "weather";
        }

        // Are they asking a classic FAQ? Let's check for creator/name questions.
        if (cleanInput.matches(".*\\b(who made you|who created you|your creator|who is your boss)\\b.*")) {
            return "faq_creator";
        }
        if (cleanInput.matches(".*\\b(what are you|who are you|your name)\\b.*")) {
            return "faq_identity";
        }

        // Did they say goodbye?
        if (cleanInput.matches(".*\\b(bye|goodbye|exit|quit|stop|cya)\\b.*")) {
            return "exit";
        }

        // If we made it this far, the bot has no clue what they're talking about!
        return "unknown";
    }
}
