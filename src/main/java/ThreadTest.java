public class ThreadTest extends Thread {
    private String string;


    public static void main(String[] args) {
        ThreadTest test = new ThreadTest("1");
        ThreadTest tset = new ThreadTest("2");
        test.start();
        tset.start();
    }

    public ThreadTest(String s) {
        string = s;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(string);
        }
    }
}
