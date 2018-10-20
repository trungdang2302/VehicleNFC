package service;

public class UserService {
    public static String convertMoney(double money) {
        String base = (long) money * 1000 + "";
        String[] strings = base.split("");
        String result = "";
        int count = 0;
        for (int i = strings.length - 1; i > 0; i--) {
            count++;
            result = strings[i] + result;
            if (count == 3) {
                if (i > 1) {
                    result = "." + result;
                    count = 0;
                }
            }
        }
        result = result + " vnđ";
        return result;
    }
}
