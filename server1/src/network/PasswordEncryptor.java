package network;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordEncryptor {
    private final String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public String saltGenerator() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(15);

        for (int i = 0; i < 15; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public String hashPassword(String password) {

        try {
            MessageDigest mg = MessageDigest.getInstance("SHA-512");
            byte[] pswd = mg.digest(password.getBytes("UTF-8"));
            BigInteger bg = new BigInteger(1, pswd);

            return bg.toString(16);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

}
