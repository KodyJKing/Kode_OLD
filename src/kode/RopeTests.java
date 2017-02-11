package kode;

import java.util.Random;

class RopeTests {

    private static final String TEST_STRING = "The quick brown fox jumps over the lazy dog.";
    private static final Random random = new Random();

    public static void test(){
        Rope r = new Rope();

        r.insert(TEST_STRING, 0);
        r.print();
        System.out.println(r.toString());
        assert r.toString().equals(TEST_STRING);

        System.out.println("---");

        r.remove(0, r.size());
        r.print();
        System.out.println(r.toString());
        assert r.toString().equals("");

        System.out.println("---");

        r.insert("Hello world!", 0);
        r.print();
        System.out.println(r.toString());
        assert r.toString().equals("Hello world!");
    }

    public static void randomizedTest(){
        Rope r = new Rope();
        StringBuilder b = new StringBuilder();
        r.insert(TEST_STRING, 0);
        b.append(TEST_STRING);

        assert r.toString().equals(b.toString());

        r.print();
        for(int i = 0; i < 1000; i++){


            String s = Integer.toString(random.nextInt(10));
            int ind = random.nextInt(b.length() + 1);
            b.insert(ind, s);
            r.insert(s, ind);

            String bs = b.toString();
            String rs = b.toString();

            System.out.println(bs);
            System.out.println(rs + "\n");
            assert rs.equals(bs);

            ind = b.length() == 0 ? 0 : random.nextInt(b.length());
            b.delete(ind, ind + 1);
            r.remove(ind, ind + 1);

            bs = b.toString();
            rs = b.toString();

            System.out.println(bs);
            System.out.println(rs + "\n");
            assert rs.equals(bs);
        }

        r.print();
    }

    private static final int TEST_CHARS = 500000;

    public static void performance(){
        System.out.println("Testing Rope performance.");
        long start = System.currentTimeMillis();
        Rope r = new Rope();
        for(int i = 0; i < TEST_CHARS; i++){
            r.insert("0", 0);
        }
        System.out.println("Ended in " + (System.currentTimeMillis() - start) + " miliseconds.\n");
    }

    public static void compare(){
        performance();

        System.out.println("Testing StringBuilder performance.");
        long start = System.currentTimeMillis();
        StringBuilder b = new StringBuilder();
        for(int i = 0; i < TEST_CHARS; i++){
            b.insert(0, '0');
        }

        System.out.println("Ended in " + (System.currentTimeMillis() - start) + " miliseconds.\n");
        System.out.println("Testing String performance.");
        start = System.currentTimeMillis();
        String s = "";
        for(int i = 0; i < TEST_CHARS; i++){
            s = "0".concat(s);
        }
        System.out.println("Ended in " + (System.currentTimeMillis() - start) + " miliseconds.\n");
    }
}
