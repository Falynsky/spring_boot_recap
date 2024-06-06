package pl.falynsky.course1.test;

public interface TestingInter {

     String rtes = "sadasd";
    static void dsa() {
        System.out.println("Hello from interface");
    }

     default void sdassa(){
        System.out.println("Hello from interface");

         Class<? extends TestClass> clazz = new TestClass().getClass();
         clazz.getDeclaredMethods();
    }

    void test();
}


class TestClass implements TestingInter {

    @Override
    public void test() {
        rtes.getBytes();
        System.out.println("Hello from test");
        sdassa();
        TestingInter.dsa();
    }
}
