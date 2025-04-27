package utils;

import model.entity.User;
import model.entity.UserAuthSecret;

import java.util.Random;

public class AuthSecretProvider {
    String[] simpleSymbolsAndChars = new String[] {"s", "$", "|", ">", "R", "h", "?", "[", "e", "G", "%", "{"};
    public UserAuthSecret updateSecret(User user) {
        Random rnd = new Random();
        Long codeNumber = rnd.nextLong(100000000,1000000000);
        String secret = String.valueOf(codeNumber);
        StringBuilder builder = new StringBuilder();
        int repeat = 3;
        for (; repeat > 0; repeat--) {
            for (String s : secret.split("")) {
                boolean addSymbols = rnd.nextBoolean();
                while (addSymbols) {
                    s = s + simpleSymbolsAndChars[rnd.nextInt(0, 12)];
                    addSymbols = rnd.nextBoolean();
                }
                builder.append(s);
            }
        }
        secret = builder.toString();
        return new UserAuthSecret(user, secret);
    }
}
