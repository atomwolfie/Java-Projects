/**
 * Class to validate card information
 */
public class CardValidator {

    /**
     * Validate full name
     */
    public boolean fullNameIsValid (String name) {
        boolean hasFName = false;

        for (int i = 0; i < name.length(); i++) {
            if (!hasFName && Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ') {
                hasFName = true;
            }
            else if (i > 1 && name.charAt(i - 1) == ' ' && Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ') {
                return true;
            }
        }

        return false;
    }

    /**
     * Validate the card number
     */
    public boolean cardNumberIsValid (String cardNumber) {
        if (cardNumber.length() != 19 && cardNumber.length() != 16) { return false; }

        if (cardNumber.length() == 16) {
            for (int i = 0; i < 16; i++) {
                if (!Character.isDigit(cardNumber.charAt(i))) { return false; }
            }
        }
        int temp = 0;
        for (int i = 4; i < 15; i += 5) {
            for (int j = temp; j < i; j++) {
                if (!Character.isDigit(cardNumber.charAt(j))) { return false; }
            }
            temp += 5;
            if (cardNumber.charAt(i) != '-') { return false; }
        }
        for (int i = 15; i < 18; i++) {
            if (!Character.isDigit(cardNumber.charAt(i))) { return false; }
        }
        return true;
    }

    public boolean expDateIsValid (int month, int year) {
        if (month < 0 || month > 12) { return false; }
        if (year < 2017) { return false; }
        return true;
    }

    public boolean crvIsValid (String crv) {
        if (crv.length() != 3) { return false; }
        for (int i = 0; i < 3; i++) {
            if (!Character.isDigit(crv.charAt(i))) { return false; }
        }
        return true;
    }
}
