public class T3 extends Thread {
    private final Data data;

    public T3(Data data) {
        this.data = data;
    }

    public void run() {
        data.printMessage("T3", "started");

        data.M2.WaitIn();                                           // 1-3

        int d3 = data.M1.get_d();                                   // 4
        int[] Z3 = data.M1.get_Z();                                 // 5

        data.M1.operation1(d3, Z3, data.H*2, data.H*3);             // 6
        data.printMessage("T3", "operation 1 complete");

        data.M3.WaitSort3();                                        // 7

        data.M1.operation2(data.H*2, data.H*4);                     // 8
        data.printMessage("T3", "operation 2 complete");

        data.M3.SignalSort2();                                      // 9

        int a3 = data.M1.operation4(data.H*2, data.H*3);            // 10
        data.printMessage("T3", "operation 4 complete");

        data.M1.calc_a(a3);                                         // 11
        data.printMessage("T3", "operation 5 complete");

        data.M2.SignalA();                                          // 12-14
        data.M2.WaitA();                                            // 15-17

        int[] A3 = data.M1.get_A();                                 // 18
        int[][] MX3 = data.M1.get_MX();                             // 19
        a3 = data.M1.get_a();                                       // 20

        data.M1.operation6(A3, MX3, a3, data.H*2, data.H*3);        // 21
        data.printMessage("T3", "operation 6 complete");

        data.M2.SignalOut();                                        // 22
    }
}
