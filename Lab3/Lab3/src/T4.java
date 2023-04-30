public class T4 extends Thread {
    private final Data data;

    public T4(Data data) {
        this.data = data;
    }

    public void run() {
        data.printMessage("T4", "started");

        data.M1.set_MT(Data.matrix());                              // 1
        data.M1.set_d(Data.number());

        data.M2.SignalIn();                                         // 2-4
        data.M2.WaitIn();                                           // 5-6

        int d4 = data.M1.get_d();                                   // 7
        int[] Z4 = data.M1.get_Z();                                 // 8

        data.M1.operation1(d4, Z4, data.H*3, data.H*4);             // 9
        data.printMessage("T4", "operation 1 complete");

        data.M3.SignalSort3();                                      // 10

        int a4 = data.M1.operation4(data.H*3, data.H*4);            // 11
        data.printMessage("T4", "operation 4 complete");

        data.M1.calc_a(a4);                                         // 12
        data.printMessage("T4", "operation 5 complete");

        data.M2.SignalA();                                          // 13-15
        data.M2.WaitA();                                            // 16-18

        int[] A4 = data.M1.get_A();                                 // 19
        int[][] MX4 = data.M1.get_MX();                             // 20
        a4 = data.M1.get_a();                                       // 21

        data.M1.operation6(A4, MX4, a4, data.H*3, data.H*4);        // 22
        data.printMessage("T4", "operation 6 complete");

        data.M2.SignalOut();                                        // 23-25
    }
}
