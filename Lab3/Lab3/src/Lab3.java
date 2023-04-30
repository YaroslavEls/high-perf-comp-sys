// Програмне забезпечення високопродуктивних комп'ютерних систем
// Лабораторна робота #3
// 
// Варіант: 26
// Завдання: V = sort(d*B+Z*MM)*(MX*MT)+(B*Z)*B
// Введення-виведення:
//   1: MM, B, MX
//   2: V, Z
//   3: -
//   4: MT, d
// 
// Терещенко Ярослав Дмитрович
// ІП-04
// 30.04.2023
// Оцінка: E/D

public class Lab3 {
    public static void main(String[] args) {
        System.out.println("Main thread: started");

        Data data = new Data(1500, true);

        Thread T1 = new T1(data);
        Thread T2 = new T2(data);
        Thread T3 = new T3(data);
        Thread T4 = new T4(data);

        T1.start();
        T2.start();
        T3.start();
        T4.start();

        try {
            T1.join();
            T2.join();
            T3.join();
            T4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Main thread: finished");
    }
}
