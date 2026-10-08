import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class L04 {
    public static void main(String[] args) {
        System.out.println(L041.instance.a);

        Map map = new HashMap<>();
//        Map map2 = new Map<>();
        Set set = new HashSet<>();
//        Set set2 = new Set<>();

        L042 l1 = new  L042();
        System.out.println(l1.name);
        l1.name="李四";
        System.out.println(l1.name);

        L042 l2 = new  L042();
        System.out.println(l2.getName());
        l2.setName("李四");
        System.out.println(l2.getName());
    }
}

class L041 {
    public int a = 10;
    private L041(){}
    static L041 instance=null;
    static{
        instance=new L041();
    }
}

class L042{
    public String name = "张三";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
