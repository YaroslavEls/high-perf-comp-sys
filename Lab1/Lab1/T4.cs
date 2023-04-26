using System.Threading;

namespace Lab1
{
    class T4
    {
        public static void Run(Data data)
        {
            data.print_message(Thread.CurrentThread.Name, "started");

            data.C = data.vector(data.isRandom);                    // 1
            data.x = data.number(data.isRandom);
            //data.print_vector("vector C: ", data.C);
            //data.print_number("number x: ", data.x);

            data.sem4.Release(3);                                   // 2-4
            data.sem1.WaitOne();                                    // 5
            data.sem2.WaitOne();                                    // 6
            data.sem3.WaitOne();                                    // 7

            data.mut1.WaitOne();
            int[] D = data.D;                                       // 8
            int[,] ME = data.ME;                                    // 9
            data.mut1.ReleaseMutex();

            data.operation1(D, ME, data.H * 3, data.H * 4);         // 10
            data.print_message(Thread.CurrentThread.Name, "operation 1 complete");

            data.evh3.Set();                                        // 11
            int ai = data.operation4(data.H * 3, data.H * 4);       // 12
            data.print_message(Thread.CurrentThread.Name, "operation 4 complete");

            lock (data._lock)
            {
                data.operation5(ai);                                // 13
            }
            data.print_message(Thread.CurrentThread.Name, "operation 5 complete");

            data.sem8.Release(3);                                   // 14-16
            data.sem5.WaitOne();                                    // 17
            data.sem6.WaitOne();                                    // 18
            data.sem7.WaitOne();                                    // 19

            data.mut2.WaitOne();
            int a = data.a;                                         // 20
            int x = data.x;                                         // 21
            data.mut2.ReleaseMutex();

            data.operation6(a, x, data.H * 3, data.H * 4);          // 22
            data.print_message(Thread.CurrentThread.Name, "operation 6 complete");
            data.bar1.SignalAndWait();                              // 23-25

            data.print_vector("result vector Z: ", data.Z);         // 26

        }
    }
}
