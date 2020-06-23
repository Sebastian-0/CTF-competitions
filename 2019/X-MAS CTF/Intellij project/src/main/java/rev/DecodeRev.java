package rev;

public class DecodeRev {
    public static void main(String[] args) {
        String in = "5b 2e 4e 42 50 78 36 37 6d 34 37 5c 32 36 5c 61 37 67 5c 37 34 5c 6f 32 60 30 6d 36 30 5c 60 6b 30 60 68 32 6d 35 7e";

        String[] tokens = in.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String token : tokens) {
            int num = Integer.parseInt(token, 16);
            sb.append((char)(num^0x3));
        }

        System.out.println(sb.toString());
    }
}
