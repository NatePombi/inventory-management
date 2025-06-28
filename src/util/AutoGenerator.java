package util;

import java.security.SecureRandom;

public class AutoGenerator {

    public static String idGenerator(){

        String characters = "0123456789";

        SecureRandom secureRandom = new SecureRandom();

        StringBuilder builder = new StringBuilder(4);

        for(int i = 0; i<4; i++){

             builder.append(characters.charAt(secureRandom.nextInt(characters.length())));
        }

        return builder.toString();
    }
}
