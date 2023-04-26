using System.Threading;

namespace Lab1
{
    class T1
    {
        public static void Run(Data data)
        {
            data.print_message(Thread.CurrentThread.Name, "started");

            data.B = data.vector(data.isRandom);                    // 1
            data.E = data.vector(data.isRandom);
            //data.print_vector("vector B: ", data.B);
            //data.print_vector("vector E: ", data.E);

            data.sem1.Release(3);                                   // 2-4
            data.sem2.WaitOne();                                    // 5
            data.sem3.WaitOne();                                    // 6
            data.sem4.WaitOne();                                    // 7

            data.mut1.WaitOne();
            int[] D = data.D;                                       // 8
            int[,] ME = data.ME;                                    // 9
            data.mut1.ReleaseMutex();

            data.operation1(D, ME, 0, data.H);                      // 10
            data.print_message(Thread.CurrentThread.Name, "operation 1 complete");

            data.evh1.WaitOne();                                    // 11
            data.operation2(0, data.H * 2);                         // 12
            data.print_message(Thread.CurrentThread.Name, "operation 2 complete");

            data.evh2.WaitOne();                                    // 13
            data.operation3();                                      // 14
            data.print_message(Thread.CurrentThread.Name, "operation 3 complete");

            int ai = data.operation4(0, data.H);                    // 15
            data.print_message(Thread.CurrentThread.Name, "operation 4 complete");

            lock (data._lock)
            {
                data.operation5(ai);                                // 16
            }
            data.print_message(Thread.CurrentThread.Name, "operation 5 complete");

            data.sem5.Release(3);                                   // 17-19
            data.sem6.WaitOne();                                    // 20
            data.sem7.WaitOne();                                    // 21
            data.sem8.WaitOne();                                    // 22

            data.mut2.WaitOne();
            int a = data.a;                                         // 23
            int x = data.x;                                         // 24
            data.mut2.ReleaseMutex();

            data.operation6(a, x, 0, data.H);                       // 25
            data.print_message(Thread.CurrentThread.Name, "operation 6 complete");
            data.bar1.SignalAndWait();                              // 26

        }
    }
}
