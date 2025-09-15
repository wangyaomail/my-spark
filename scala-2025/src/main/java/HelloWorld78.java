public class HelloWorld78 {
//    static  int a=1;
//    int b=2;
//    static Ceshi c=new Ceshi();
//    Ceshi d=new Ceshi();

    static {
        int a = 10;
    }
    static {
        int b=10;
//        int c= a+b;
    }

    public static void main(String[] args) {
//        System.out.println("Hello World!");
        int a =1;
        final int b=1;
        Ceshi c = new Ceshi();
        final Ceshi d = new Ceshi();
        c.x=100;
        d.x=100;
        System.out.println(d.x);

        String aa = "xxx";
        String bb = "xxx";
        System.out.println(aa==bb);
        String cc = "xx"+"x";
        System.out.println(cc==aa);
        String dd = new String("xxx");
        System.out.println(dd==aa);
        String ee = "1";
        String ff = "2";
//        System.out.println(ee>ff);

        System.out.println(ee.equals(ff) ? ee : ff);
//        System.out.println(if(ee.equals(dd))  ff else dd);

        for(int i=0;i<10;i++){
            System.out.println(i);
        }
    }

}

class Ceshi{
   int x=0;
   final int y=1;
}
