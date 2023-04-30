public class T2 extends Thread {
    private final Data data;

    public T2(Data data) {
        this.data = data;
    }

    public void run() {
        data.printMessage("T2", "started");

        data.M1.set_Z(Data.vector());                               // 1

        data.M2.SignalIn();                                         // 2-4
        data.M2.WaitIn();                                           // 5-6

        int d2 = data.M1.get_d();                                   // 7
        int[] Z2 = data.M1.get_Z();                                 // 8

        data.M1.operation1(d2, Z2, data.H, data.H*2);               // 9
        data.printMessage("T2", "operation 1 complete");

        data.M3.SignalSort1();                                      // 10

        int a2 = data.M1.operation4(data.H, data.H*2);              // 11
        data.printMessage("T2", "operation 4 complete");

        data.M1.calc_a(a2);                                         // 12
        data.printMessage("T2", "operation 5 complete");

        data.M2.SignalA();                                          // 13-15
        data.M2.WaitA();                                            // 16-18

        int[] A2 = data.M1.get_A();                                 // 19
        int[][] MX2 = data.M1.get_MX();                             // 20
        a2 = data.M1.get_a();                                       // 21

        data.M1.operation6(A2, MX2, a2, data.H, data.H*2);          // 22
        data.printMessage("T2", "operation 6 complete");

        data.M2.WaitOut();                                          // 23-25

        data.printVector("result vector V:", data.M1.get_V());      // 26
    }
}
