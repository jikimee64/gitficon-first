package order.system.core.api;

public class Parent {
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        System.out.println(instance);
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance2);
        Singleton instance3 = Singleton.getInstance();
        System.out.println(instance3);
    }
}

class Singleton {
    // Singleton의 인스턴스를 가지는 내부 정적 클래스
    private static class LazyHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    private Singleton() {
    }

    public static Singleton getInstance() {
        return LazyHolder.INSTANCE;
    }

}