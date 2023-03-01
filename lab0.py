# Програмне забезпечення високопродуктивних комп'ютерних систем
# Лабораторна робота #0
# 
# 1.20 D = MIN(A + B) * (B + C) *(MA*MD)
# 2.29 MF = (MG + MH)*(MK * ML)*(MG + ML)
# 3.9  O = SORT(P)*(MR * MS)
# 
# Терещенко Ярослав Дмитрович
# ІП-04
# 28.02.2023

import threading
import numpy as np

class Data():
    def get_input(N, type):
        if type == 'vector':
            t = input(f'vector, {N} values: ')
            t = list(map(int, t.split(' ')))
            return np.array(t)
        elif type == 'matrix':
            arr = []
            for i in range(1, N+1):
                t = input(f'matrix, row {i}, {N} values: ')
                t = list(map(int, t.split(' ')))
                arr.append(t)
            return np.array(arr)

    def fill_matrix(i, j, value=None):
        if value:
            return np.full((i, j), value)
        return np.random.rand(i, j).round(1)

    def F1(A, B, C, MA, MD):
        first = np.min(A + B)
        second = B + C
        third = np.dot(MA, MD)
        return np.dot((first * second).T, third)

    def F2(MG, MH, MK, ML):
        first = MG + MH
        second = np.dot(MK, ML)
        third = MG + ML
        return np.dot(np.dot(first, second), third)

    def F3(P, MR, MS):
        first = np.sort(P)
        second = np.dot(MR, MS)
        return np.dot(first.T, second)

def proc1_func(mode, lock):
    lock.acquire()
    print('T1 begins')
    if mode == 'hand':
        N = int(input('dimensions (N): '))
        A = Data.get_input(N, 'vector')
        B = Data.get_input(N, 'vector')
        C = Data.get_input(N, 'vector')
        MA = Data.get_input(N, 'matrix')
        MD = Data.get_input(N, 'matrix')
        lock.release()
    elif mode == 'auto':
        lock.release()
        N = 1000
        A = Data.fill_matrix(N, 1)
        B = Data.fill_matrix(N, 1)
        C = Data.fill_matrix(N, 1)
        MA = Data.fill_matrix(N, N)
        MD = Data.fill_matrix(N, N)
    res = Data.F1(A, B, C, MA, MD)
    lock.acquire()
    print(f'T1 calculation finished: {res}')
    lock.release()

def proc2_func(mode, lock):
    lock.acquire()
    print('T2 begins')
    if mode == 'hand':
        N = int(input('dimensions (N): '))
        MG = Data.get_input(N, 'matrix')
        MH = Data.get_input(N, 'matrix')
        MK = Data.get_input(N, 'matrix')
        ML = Data.get_input(N, 'matrix')
        lock.release()
    elif mode == 'auto':
        lock.release()
        N = 1000
        MG = Data.fill_matrix(N, N)
        MH = Data.fill_matrix(N, N)
        MK = Data.fill_matrix(N, N)
        ML = Data.fill_matrix(N, N)
    res = Data.F2(MG, MH, MK, ML)
    lock.acquire()
    print(f'T2 calculation finished: {res}')
    lock.release()

def proc3_func(mode, lock):
    lock.acquire()
    print('T3 begins')
    if mode == 'hand':
        N = int(input('dimensions (N): '))
        P = Data.get_input(N, 'vector')
        MR = Data.get_input(N, 'matrix')
        MS = Data.get_input(N, 'matrix')
        lock.release()
    elif mode == 'auto':
        lock.release()
        N = 1000
        P = Data.fill_matrix(N, 1)
        MR = Data.fill_matrix(N, N)
        MS = Data.fill_matrix(N, N)
    res = Data.F3(P, MR, MS)
    lock.acquire()
    print(f'T3 calculation finished: {res}')
    lock.release()

def main():
    print('Main process beginning')
    lock = threading.Lock()
    t1 = threading.Thread(target=proc1_func, args=('auto', lock))
    t2 = threading.Thread(target=proc2_func, args=('auto', lock))
    t3 = threading.Thread(target=proc3_func, args=('auto', lock))
    t1.start()
    t2.start()
    t3.start()
    t1.join()
    t2.join()
    t3.join()
    print('Main process finished')


if __name__ == "__main__":
    main()