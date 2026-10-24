package bot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// Once the NLP Processor figures out what the user wants, this class decides exactly what to say back.
public class ResponseEngine {
    
    // We use a HashMap to store quick answers to frequently asked questions and static responses.
    private Map<String, String[]> responses;
    private Random random;

    public ResponseEngine() {
        random = new Random();
        responses = new HashMap<>();
        
        // Let's set up some varied responses so the bot doesn't sound like a broken record!
        responses.put("greeting", new String[]{
            "Hello there! How can I help you today?",
            "Hey! Nice to see you. What's on your mind?",
            "Hi! Hope you're having a great day. What do you need?"
        });
        
        responses.put("faq_creator", new String[]{
            "I was created by a brilliant AI Engineer using Java 17!",
            "A super smart developer built me to help you out.",
            "My creator prefers to remain mysterious, but they love Java."
        });
        
        responses.put("faq_identity", new String[]{
            "I am an intelligent Java Chatbot, here to assist you!",
            "I'm just a few lines of code trying to make your day better.",
            "I'm your friendly neighborhood chatbot."
        });
        
        responses.put("unknown", new String[]{
            "I'm sorry, I didn't quite catch that. Could you rephrase it?",
            "Hmm, I'm still learning and don't know how to respond to that yet.",
            "I'm not sure what you mean. Try asking me about the weather or the time!"
        });
        
        responses.put("exit", new String[]{
            "Goodbye! Have a fantastic rest of your day.",
            "See you later! It was nice chatting with you.",
            "Bye! Come back soon."
        });
    }

    // This is the main method the ChatBot calls to get a reply.
    public String getResponse(String intent, String userName) {
        
        // Some responses need to be calculated on the spot, like the date and time.
        if (intent.equals("datetime")) {
            return getDateTimeResponse();
        }
        
        // Or checking the weather (which is just a placeholder for now, but still dynamic!)
        if (intent.equals("weather")) {
            return getWeatherResponse(userName);
        }

        // If it's a standard static response, we pull a random one from our HashMap
        String[] possibleReplies = responses.getOrDefault(intent, responses.get("unknown"));
        
        // Pick a random reply from the list so it feels natural
        String reply = possibleReplies[random.nextInt(possibleReplies.length)];
        
        // A nice little touch: if they are greeting us, let's include their name!
        if (intent.equals("greeting") && userName != null && !userName.isEmpty()) {
            return reply + " It's great to meet you, " + userName + "!";
        }
        
        return reply;
    }

    // Fetches the real, live system time and formats it nicely.
    private String getDateTimeResponse() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy 'at' hh:mm a");
        return "Right now, it is " + now.format(formatter) + ".";
    }

    // We don't actually have an internet connection to check the weather, so we just make it up!
    private String getWeatherResponse(String userName) {
        String[] weatherConditions = {"sunny and beautiful", "raining cats and dogs", "a bit cloudy", "snowing heavily", "quite windy"};
        String condition = weatherConditions[random.nextInt(weatherConditions.length)];
        return "I can't look out a window, but my sensors guess it's " + condition + " outside, " + userName + "!";
    }
}
