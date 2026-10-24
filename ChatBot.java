package bot;

import java.util.Scanner;

// This is the main orchestrator class. It ties the brain (NLPProcessor), 
// the voice (ResponseEngine), and the memory (ConversationHistory) all together.
public class ChatBot {
    
    private Scanner scanner;
    private NLPProcessor nlp;
    private ResponseEngine engine;
    private ConversationHistory history;
    private String userName;

    public ChatBot() {
        scanner = new Scanner(System.in);
        nlp = new NLPProcessor();
        engine = new ResponseEngine();
        history = new ConversationHistory();
        userName = "Guest";
    }

    // Kicks off the whole conversation!
    public void start() {
        System.out.println("---------------------------------------------------------");
        System.out.println("                INTELLIGENT JAVA CHATBOT                 ");
        System.out.println("---------------------------------------------------------");
        
        // First things first, let's be polite and ask for their name.
        greetAndAskName();

        // This is the main "game loop" of the chatbot. It keeps listening until the user types 'exit'.
        boolean chatting = true;
        while (chatting) {
            System.out.print("\n" + userName + ": ");
            String input = scanner.nextLine();
            
            // Write down what the user said in our history book.
            history.log(userName, input);

            // Give the raw text to the NLP processor so it can figure out what they want.
            String intent = nlp.extractIntent(input);
            
            // Ask the response engine for the perfect reply.
            String response = engine.getResponse(intent, userName);

            // Output the reply to the screen.
            System.out.println("Bot: " + response);
            
            // And remember to write down our own reply in the history book!
            history.log("Bot", response);

            // If the NLP caught a goodbye keyword, we break the loop and end the chat.
            if (intent.equals("exit")) {
                chatting = false;
            }
        }
        
        // Always close your files and scanners!
        history.close();
        scanner.close();
    }

    // A simple method just to handle the very first interaction.
    private void greetAndAskName() {
        String greeting = "Hello there! I am your AI assistant. May I ask what your name is?";
        System.out.println("Bot: " + greeting);
        history.log("Bot", greeting);
        
        System.out.print("\nYou: ");
        String nameInput = scanner.nextLine().trim();
        history.log("User", nameInput);
        
        if (!nameInput.isEmpty()) {
            // Sometimes people say "My name is John", so let's just grab the last word.
            // It's a very basic trick, but it works for simple cases!
            String[] words = nameInput.split(" ");
            userName = words[words.length - 1]; 
        }
        
        String reply = "Nice to meet you, " + userName + "! You can ask me about the weather, time, or just chat. Say 'exit' to leave.";
        System.out.println("Bot: " + reply);
        history.log("Bot", reply);
    }
}
