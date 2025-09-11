public class HelloWorld56 {
    static A  a1 = new A();
    A a2 =  new A();


    public static void main(String[] args) {

        A a3 = new A();
        a3.a +=1 ;
        a3 = new A();
        a3.a +=3 ;

        final A a4 = new A();
        a4.a +=1 ;

//        a4 = new A();




        System.out.println(a3.a);
        System.out.println(a4.a);
    }
}


class A{
    int a=1;

}