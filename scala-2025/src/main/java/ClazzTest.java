public class ClazzTest {
    public static int a=2;
    public static int aa(){
        int a=1;
        System.out.println(a+10);
        return a+20;
    }
    public static int bb(){
        int b=1;
        System.out.println(a+b);
        return b+30;
    }

    public static void main(String[] args) {
//        System.out.println(aa());
//        System.out.println(bb());
        String a = "xxx";
        String b = "xxx";
        System.out.println(a==b);
        String c="xx"+"x";
        System.out.println(a==c);
        String d = new String("xxx");
        System.out.println(a==d);
        System.out.println(a.equals(d));
//        System.out.println(a>c);
        String x = a.equals(b) ? "a" : "b" ;

        System.out.println(a.equals(b) ? "a" : "b" );

        for(int i=0;i<10;i++){
            System.out.println(i);
        }
    }
}
