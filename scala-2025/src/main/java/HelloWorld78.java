public class HelloWorld78 {
//    static  int a=1;
//    int b=2;
//    static Ceshi c=new Ceshi();
//    Ceshi d=new Ceshi();


    public static void main(String[] args) {
//        System.out.println("Hello World!");
        int a =1;
        final int b=1;
        Ceshi c = new Ceshi();
        final Ceshi d = new Ceshi();
        c.x=100;
        d.x=100;
        System.out.println(d.x);
    }
}

class Ceshi{
   int x=0;
   final int y=1;
}
