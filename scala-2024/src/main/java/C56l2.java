import java.lang.reflect.Method;

public class C56l2 {
    public static void main(String[] args) {
        String a = "abc";
        String b = "abc";
        System.out.println(a==b);
        a = new String("abc");
        b = new String("abc");
        System.out.println(a==b);
    }
}