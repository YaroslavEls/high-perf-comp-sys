using System.Threading;

namespace Lab1
{
    class T3
    {
        public static void Run(Data data)
        {
            data.print_message(Thread.CurrentThread.Name, "started");

            data.ME = data.matrix(data.isRandom);                   // 1
            //data.print_matrix("matrix ME: ", data.ME);

            data.sem3.Release(3);                                   // 2-4
            data.sem1.WaitOne();                                    // 5
            data.sem2.WaitOne();                                    // 6
            data.sem4.WaitOne();                                    // 7

            data.mut1.WaitOne();
            int[] D = data.D;                                       // 8
            int[,] ME = data.ME;                                    // 9
            data.mut1.ReleaseMutex();

            data.operation1(D, ME, data.H * 2, data.H * 3);         // 10
            data.print_message(Thread.CurrentThread.Name, "operation 1 complete");

            data.evh3.WaitOne();                                    // 11
            data.operation2(data.H * 2, data.H * 3);                // 12
            data.print_message(Thread.CurrentThread.Name, "operation 2 complete");

            data.evh2.Set();                                        // 13
            int ai = data.operation4(data.H * 2, data.H * 3);       // 14
            data.print_message(Thread.CurrentThread.Name, "operation 4 complete");

            lock (data._lock)
            {
                data.operation5(ai);                                // 15
            }
            data.print_message(Thread.CurrentThread.Name, "operation 5 complete");

            data.sem7.Release(3);                                   // 16-18
            data.sem5.WaitOne();                                    // 19
            data.sem6.WaitOne();                                    // 20
            data.sem8.WaitOne();                                    // 21

            data.mut2.WaitOne();
            int a = data.a;                                         // 22
            int x = data.x;                                         // 23
            data.mut2.ReleaseMutex();

            data.operation6(a, x, data.H * 2, data.H * 3);          // 24
            data.print_message(Thread.CurrentThread.Name, "operation 6 complete");
            data.bar1.SignalAndWait();                              // 25

        }
    }
}
