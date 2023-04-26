using System;
using System.Threading;

namespace Lab1
{
    public class Data
    {
        public int N;
        public int P;
        public int H;
        public bool isRandom;

        public int[] A;
        public int a;

        public int[] B;
        public int[] E;
        public int[] D;
        public int[,] MM;
        public int[,] ME;
        public int[] C;
        public int[] Z;
        public int x;

        public Semaphore sem1 = new Semaphore(0, 3);
        public Semaphore sem2 = new Semaphore(0, 3);
        public Semaphore sem3 = new Semaphore(0, 3);
        public Semaphore sem4 = new Semaphore(0, 3);

        public Semaphore sem5 = new Semaphore(0, 3);
        public Semaphore sem6 = new Semaphore(0, 3);
        public Semaphore sem7 = new Semaphore(0, 3);
        public Semaphore sem8 = new Semaphore(0, 3);

        public readonly object _lock = new object();

        public Mutex mut1 = new Mutex();
        public Mutex mut2 = new Mutex();

        public EventWaitHandle evh1 = new EventWaitHandle(false, EventResetMode.ManualReset);
        public EventWaitHandle evh2 = new EventWaitHandle(false, EventResetMode.ManualReset);
        public EventWaitHandle evh3 = new EventWaitHandle(false, EventResetMode.ManualReset);

        public Barrier bar1 = new Barrier(4);

        public Data(int N, bool isRandom)
        {
            this.N = N;
            this.P = 4;
            this.H = N / P;
            this.a = 0;
            this.A = new int[N];
            this.Z = new int[N];
            this.isRandom = isRandom;
        }

        public void print_message(string thread_name, string message)
        {
            lock (Console.In)
            {
                Console.WriteLine(thread_name + ": " + message);
            }
        }

        public void print_number(string name, int number)
        {
            lock (Console.In)
            {
                Console.WriteLine(name);
                Console.WriteLine(number);
            }
        }

        public void print_vector(string name, int[] vector)
        {
            lock (Console.In)
            {
                Console.WriteLine(name);
                for (int i = 0; i < N; i++)
                {
                    Console.Write(vector[i] + " ");
                }
                Console.WriteLine();
            }
        }

        public void print_matrix(string name, int[,] matrix)
        {
            lock (Console.In)
            {
                Console.WriteLine(name);
                for (int i = 0; i < N; i++)
                {
                    for (int j = 0; j < N; j++)
                    {
                        Console.Write(matrix[i, j] + " ");
                    }
                    Console.WriteLine();
                }
            }
        }

        public int number(bool rand)
        {
            if (!rand)
            {
                return 1;
            }
            Random random = new Random();
            int number = random.Next(-10, 10);
            return number;
        }

        public int[] vector(bool rand)
        {
            int[] vector = new int[N];
            Random random = new Random();
            for (int i = 0; i < N; i++)
            {
                if (!rand)
                {
                    vector[i] = 1;
                    continue;
                }
                vector[i] = random.Next(-10, 10);
            }
            return vector;
        }

        public int[,] matrix(bool rand)
        {
            int[,] matrix = new int[N, N];
            Random random = new Random();
            for (int i = 0; i < N; i++)
            {
                for (int j = 0; j < N; j++)
                {
                    if (!rand)
                    {
                        matrix[i, j] = 1;
                        continue;
                    }
                    matrix[i, j] = random.Next(-10, 10);
                }
            }
            return matrix;
        }

        // Ah = sort(D * (ME * MMh))
        public void operation1(int[] D, int[,] ME, int lowerBound, int upperBound)
        {
            int[] tmpArr = new int[upperBound - lowerBound];
            int index = 0;
            for (int i = lowerBound; i < upperBound; i++)
            {
                int tmp1 = 0;
                for (int j = 0; j < N; j++)
                {
                    int tmp2 = 0;
                    for (int k = 0; k < N; k++)
                    {
                        tmp2 += ME[k, j] * MM[i, k];
                    }
                    tmp1 += tmp2 * D[j];
                }
                tmpArr[index] = tmp1;
                index++;      
            }
            Array.Sort(tmpArr);
            index = 0;
            for (int i = lowerBound; i < upperBound; i++)
            {
                A[i] = tmpArr[index];
                index++;
            }
        }

        // A2h = sort(Ah, Ah)
        public void operation2(int lowerBound, int upperBound)
        {
            int[] tmpArr = new int[upperBound - lowerBound];
            Array.ConstrainedCopy(A, lowerBound, tmpArr, 0, upperBound - lowerBound);
            Array.Sort(tmpArr);
            int index = 0;
            for (int i = lowerBound; i < upperBound; i++)
            {
                A[i] = tmpArr[index];
                index++;
            }
        }

        // A = sort(A2h, A2h)
        public void operation3()
        {
            Array.Sort(A);
        }

        // ai = Bh * Ch
        public int operation4(int lowerBound, int upperBound)
        {
            int tmp = 0;
            for (int i = lowerBound; i < upperBound; i++)
            {
                tmp += B[i] * C[i];
            }
            return tmp;
        }

        // a = a + ai
        public void operation5(int ai)
        {
            a += ai;
        }

        // Zh = Ah + (a * Eh * x)
        public void operation6(int a, int x, int lowerBound, int upperBound)
        {
            for (int i = lowerBound; i < upperBound; i++)
            {
                Z[i] = A[i] + (a * E[i] * x);
            }
        }
    }
}
