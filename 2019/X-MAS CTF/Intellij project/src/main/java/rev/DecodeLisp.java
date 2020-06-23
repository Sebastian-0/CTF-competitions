package rev;

import java.util.ArrayList;
import java.util.List;

public class DecodeLisp {

    private List<Integer> idx = new ArrayList<>();

    public void frob(String str, int start, int end) {
        int mean = (start + end) / 2;
        if (start < end - 1) {
            idx.add(mean);
//            System.out.print(str.charAt(mean));
//            System.out.print("/");

            frob(str, start, mean);
            frob(str, mean, end);
        }
    }

    public static void main(String[] args) {
        String out = "47/22/9/55/-41/59/39/97/-38/-38/108/42/41/-47/-46/-38/-38/22/46/110/22/46/23/20/45/46/47/20/-45/46/103";
//        String out = "-38/27/104/21/111/28/-38/22/105/-38/29/19/19/-38/20/104/21/27/20/18/110/31/105/25/-38/27/22/110/31/0";
        String[] tokens = out.split("/");

        char[] decoded = new char[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            int num = Integer.parseInt(tokens[i]);
            decoded[i] = (char)((num ^ 42) + '0');
        }

//        System.out.println(((-46) ^ 42) + '0');
//        System.out.println((88) ^ 42 - '0');

        DecodeLisp lisp;
        String testString = " ";
        while (true) {
            lisp = new DecodeLisp();
            lisp.frob(testString, 0, testString.length());
            if (lisp.idx.size() == tokens.length) {
                break;
            }
            testString += " ";
        }

        char[] str = new char[tokens.length + 1];
        for (int i = 0; i < tokens.length; i++) {
            str[lisp.idx.get(i)] = decoded[i];
        }

        System.out.println(String.valueOf(str));

//        String flag = "your flag is in another castle";
////        String flag = "you";
//        new DecodeLisp().frob(flag, 0, flag.length());
//        System.out.println("0");
    }
}
