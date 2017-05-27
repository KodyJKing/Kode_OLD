package kode;

public class Main {

    public static void main(String[] args) {
//        RopeTests.randomizedTest();
        RealTimeArrayList<String> rta = new RealTimeArrayList<String>();
//        rta.show();
//        System.out.println();
        for (int i = 0; i < 24; i++) {
            rta.push("ITEM");
//            rta.show();
//            System.out.println();
        }

        rta.show();
        System.out.println();
        for (int i = 0; i < 24; i++) {
            rta.set(i, "ELEM");
            rta.show();
            System.out.println();
        }
    }
}
