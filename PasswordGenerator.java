import java.util.random.RandomGenerator;
import java.lang.Integer;
import java.security.SecureRandom;

class PasswordGenerator {
    final String NUMBERS     = "0123456789";
    final String LOWER_CHARS = "abcdefghijklmnopqrstuvxyzw";
    final String UPPER_CHARS = "ABCDEFGHIJKLMNOPQRSTUVXYZW";
    final String SPECIAL     = "#$%&@^`~<>*+!?=";

    void printUsage() {
        System.out.printf("Usage: PasswordGenerator [options]\n");
        System.out.printf("Options:\n");
        System.out.printf("  -len <length>   Length of the generated password\n");
        System.out.printf("  -lower          Use lowercase characters\n");
        System.out.printf("  -upper          Use uppercase characters\n");
        System.out.printf("  -numeric        Use numbers\n");
        System.out.printf("  -special        Use special characters\n");
        System.out.printf("  -all            Include every group of characters\n");
        System.out.printf("  -custom <chars> Use custom character set\n");
    }

    public void main(String[] args) {
        int pwdLength = 32;

        boolean useCustomCharsOption = false;

        boolean useNumericChars = false;
        boolean useLowerChars = false;
        boolean useUpperChars = false;
        boolean useSpecialChars = false;
        boolean useCustomCharsSet = false;

        String customCharsSet = "";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "help":
                case "-h":
                case "--help":
                    printUsage();
                    System.exit(1);
                    break;
                case "-len":
                    if (i+1 >= args.length) {
                        System.out.printf("Expected length for '-len' option\n\n");
                        printUsage();
                        System.exit(1);
                    }
                    try {
                        int len = Integer.parseInt(args[i+1], 10);
                        pwdLength = len;
                        i++;
                    } catch (java.lang.NumberFormatException e) {
                        System.out.println("NumberFormatException");
                        System.out.println("Error: " + e.getMessage());
                        System.out.println("");
                        printUsage();
                        System.exit(1);
                    }
                    break;
                case "-lower":
                    useLowerChars = true;
                    useCustomCharsOption = true;
                    break;
                case "-upper":
                    useUpperChars = true;
                    useCustomCharsOption = true;
                    break;
                case "-numeric":
                    useNumericChars = true;
                    useCustomCharsOption = true;
                    break;
                case "-special":
                    useSpecialChars = true;
                    useCustomCharsOption = true;
                    break;
                case "-all":
                    useCustomCharsOption = true;
                    useNumericChars = true;
                    useLowerChars = true;
                    useUpperChars = true;
                    useSpecialChars = true;
                    break;
                case "-custom":
                    if (i+1 >= args.length || args[i+1].isEmpty() || args[i+1].startsWith("-")) {
                        System.out.printf("Expected character set for '-custom' option\n\n");
                        printUsage();
                        System.exit(1);
                    }
                    useCustomCharsSet = true;
                    customCharsSet = args[i+1];
                    i++;
                    break;
                default:
                    System.out.printf("Unknow option '%s'\n\n", args[i]);
                    printUsage();
                    System.exit(1);
                    break;
            };
        }

        SecureRandom rndGen = new SecureRandom();

        StringBuilder charPool = new StringBuilder();

        if (useCustomCharsOption) {
            if (useNumericChars) charPool.append(NUMBERS);
            if (useLowerChars)   charPool.append(LOWER_CHARS);
            if (useUpperChars)   charPool.append(UPPER_CHARS);
            if (useSpecialChars) charPool.append(SPECIAL);
        } else if (useCustomCharsSet) {
            charPool.append(customCharsSet);
        } else {
            charPool.append(NUMBERS);
            charPool.append(LOWER_CHARS);
        }

        String pwd = new String();

        for (int i = 0; i < pwdLength; i++) {
            pwd += charPool.charAt(rndGen.nextInt(charPool.length()));
        }

        System.out.println(pwd);
    }
}
