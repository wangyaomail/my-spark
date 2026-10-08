public class L03 {
    public static void main(String[] args) {
        Stu s = new Stu();
        s.setName("John");
        System.out.println(s.getName());
        s.name = "Mike";
        System.out.println(s.name);
    }
}

class Stu{
    String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
