// Програмне забезпечення високопродуктивних комп'ютерних систем
// Лабораторна робота #1
// 
// Варіант: 30
// Завдання: Z = sort(D*(ME*MM))+(B*C)*E*x
// Введення-виведення:
//   1: B, E
//   2: D, MM
//   3: ME
//   4: C, Z, x
// 
// Терещенко Ярослав Дмитрович
// ІП-04
// 25.04.2023
// Оцінка: E/D

using System;
using System.Diagnostics;
using System.Threading;

namespace Lab1
{
    class Lab1
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Press any key to start the app...");
            Console.ReadKey();

            Data data = new Data(1000, false);

            Thread thread1 = new Thread(() => T1.Run(data)) { Name = "T1" };
            Thread thread2 = new Thread(() => T2.Run(data)) { Name = "T2" };
            Thread thread3 = new Thread(() => T3.Run(data)) { Name = "T3" };
            Thread thread4 = new Thread(() => T4.Run(data)) { Name = "T4" };

            Stopwatch timer = new Stopwatch();
            timer.Start();

            thread1.Start();
            thread2.Start();
            thread3.Start();
            thread4.Start();

            thread1.Join();
            thread2.Join();
            thread3.Join();
            thread4.Join();

            timer.Stop();

            Console.WriteLine("Main thread finished");

            Console.WriteLine("time " + timer.Elapsed);

            Console.WriteLine("Press any key to close the app...");
            Console.ReadKey();

        }
    }
}
