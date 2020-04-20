///* At least two constants start with STAR
enum Secret {
    STAR, CRASH, START, // ...
}
//*/

public class Main {

    public static void main(String[] args) {
        Secret[] secrets = Secret.values();
        int count = 0;
        for (Secret secret: secrets) {
            String secretName = secret.name();
            count = secretName.startsWith("STAR") ? count + 1 : count;
        }
        System.out.println(count);
    }
}

