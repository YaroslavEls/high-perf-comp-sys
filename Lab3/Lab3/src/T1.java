public class T1 extends Thread {
    private final Data data;

    public T1(Data data) {
        this.data = data;
    }

    public void run() {
        data.printMessage("T1", "started");

        data.M1.set_MM(Data.matrix());                              // 1
        data.M1.set_B(Data.vector());
        data.M1.set_MX(Data.matrix());

        data.M2.SignalIn();                                         // 2-4
        data.M2.WaitIn();                                           // 5-6

        int d1 = data.M1.get_d();                                   // 7
        int[] Z1 = data.M1.get_Z();                                 // 8

        data.M1.operation1(d1, Z1, 0, data.H);                      // 9
        data.printMessage("T1", "operation 1 complete");

        data.M3.WaitSort1();                                        // 10

        data.M1.operation2(0, data.H*2);                            // 11
        data.printMessage("T1", "operation 2 complete");

        data.M3.WaitSort2();                                        // 12

        data.M1.operation3();                                       // 13
        data.printMessage("T1", "operation 3 complete");

        int a1 = data.M1.operation4(0, data.H);                     // 14
        data.printMessage("T1", "operation 4 complete");

        data.M1.calc_a(a1);                                         // 15
        data.printMessage("T1", "operation 5 complete");

        data.M2.SignalA();                                          // 16-18
        data.M2.WaitA();                                            // 19-21

        int[] A1 = data.M1.get_A();                                 // 22
        int[][] MX1 = data.M1.get_MX();                             // 23
        a1 = data.M1.get_a();                                       // 24

        data.M1.operation6(A1, MX1, a1, 0, data.H);                 // 25
        data.printMessage("T1", "operation 6 complete");

        data.M2.SignalOut();                                        // 26
    }
}
