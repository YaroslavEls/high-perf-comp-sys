// Програмне забезпечення високопродуктивних комп'ютерних систем
// Лабораторна робота #5
//
// Варіант: 17
// Завдання: A=(B*C)*Z-(D+Z)*(MX*MR)
// Введення-виведення:
// 1: B
// 2: MX, A
// 3: Z, D
// 4: C, MR
// Структура:
// 2 (кільцева)
//
// Терещенко Ярослав Дмитрович
// ІП-04
// 06.06.2023
// Оцінка: E/D

#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>

bool isRandom = true;
const int N = 8;
int H = N / 4;
int a, ai, V[N], B[N], MX[N][N], A[N], Z[N], D[N], C[N], MR[N][N];

int value(bool random) {
    if (random) {
        return rand() % 10;
    }
    return 1;
}

void vector(int vec[N], bool random) {
    for (int i = 0; i < N; i++) {
        vec[i] = random ? rand() % 10 : 1;
    }
}

void matrix(int mat[N][N], bool random) {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            mat[i][j] = random ? rand() % 10 : 1;
        }
    }
}

int operation1(int start, int end) {
    int sum = 0;
    for (int i = start; i <= end; i++) {
        sum = sum + B[i] * C[i];
    }
    return sum;
}

int operation2() {
    return a + ai;
}

void operation3(int start, int end) {
    for (int i = start; i <= end; i++) {
        V[i] = D[i] + Z[i];
    }
}

void operation4(int start, int end) {
    int index = 0;
    for (int i = start; i <= end; i++) {
        int tmp1 = 0;
        for (int j = 0; j < N; j++) {
            int tmp2 = 0;
            for (int k = 0; k < N; k++) {
                tmp2 += MX[k][j] * MR[i][k];
            }
            tmp1 += tmp2 * V[j];
        }
        A[i] = a * Z[i] - tmp1;
        index++;
    }
}

int main(int argc, char** argv) {
    int rank, size;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if (rank == 0) {
        vector(B, isRandom);

        MPI_Send(&B[H], 3*H, MPI_INT, 1, 0, MPI_COMM_WORLD);
        MPI_Recv(&C[0], 3*H, MPI_INT, 3, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Send(&C[H], 2*H, MPI_INT, 1, 0, MPI_COMM_WORLD);
    } 
    else if (rank == 1) {
        matrix(MX, isRandom);

        MPI_Recv(&B[H], 3*H, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Send(&B[2*H], 2*H, MPI_INT, 2, 0, MPI_COMM_WORLD);
        MPI_Recv(&C[H], 2*H, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Send(&C[2*H], H, MPI_INT, 2, 0, MPI_COMM_WORLD);
    }
    else if (rank == 2) {
        vector(Z, isRandom);
        vector(D, isRandom);

        MPI_Recv(&B[2*H], 2*H, MPI_INT, 1, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Send(&B[3*H], H, MPI_INT, 3, 0, MPI_COMM_WORLD);
        MPI_Recv(&C[2*H], H, MPI_INT, 1, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
    }
    else if (rank == 3) {
        vector(C, isRandom);
        matrix(MR, isRandom);

        MPI_Recv(&B[3*H], H, MPI_INT, 2, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Send(&C[0], 3*H, MPI_INT, 0, 0, MPI_COMM_WORLD);
    }

    int start = H * rank;
    int end = H * (rank + 1) - 1;
    ai = operation1(start, end);

    if (rank == 1) {
        MPI_Recv(&a, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
    }
    else if (rank == 2) {
        MPI_Recv(&a, 1, MPI_INT, 1, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
    }
    else if (rank == 3) {
        MPI_Recv(&a, 1, MPI_INT, 2, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
    }

    a = operation2();

    if (rank == 0) {
        MPI_Send(&a, 1, MPI_INT, 1, 0, MPI_COMM_WORLD);
        MPI_Recv(&a, 1, MPI_INT, 3, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Send(&a, 1, MPI_INT, 1, 0, MPI_COMM_WORLD);
    }
    else if (rank == 1) {
        MPI_Send(&a, 1, MPI_INT, 2, 0, MPI_COMM_WORLD);
        MPI_Recv(&a, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Send(&a, 1, MPI_INT, 2, 0, MPI_COMM_WORLD);
    }
    else if (rank == 2) {
        MPI_Send(&a, 1, MPI_INT, 3, 0, MPI_COMM_WORLD);
        MPI_Recv(&a, 1, MPI_INT, 1, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
    }
    else if (rank == 3) {
        MPI_Send(&a, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
    }

    if (rank == 0) {
        MPI_Recv(&D[0], H, MPI_INT, 1, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Recv(&Z[0], H, MPI_INT, 1, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE); 
    }
    else if (rank == 1) {
        MPI_Recv(&D[0], 2*H, MPI_INT, 2, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Recv(&Z[0], 2*H, MPI_INT, 2, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Send(&D[0], H, MPI_INT, 0, 0, MPI_COMM_WORLD);
        MPI_Send(&Z[0], H, MPI_INT, 0, 0, MPI_COMM_WORLD);
    }
    else if (rank == 2) {
        MPI_Send(&D[3*H], H, MPI_INT, 3, 0, MPI_COMM_WORLD);
        MPI_Send(&Z[3*H], H, MPI_INT, 3, 0, MPI_COMM_WORLD);
        MPI_Send(&D[0], 2*H, MPI_INT, 1, 0, MPI_COMM_WORLD);
        MPI_Send(&Z[0], 2*H, MPI_INT, 1, 0, MPI_COMM_WORLD);
    }
    else if (rank == 3) {
        MPI_Recv(&D[3*H], H, MPI_INT, 2, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Recv(&Z[3*H], H, MPI_INT, 2, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
    }

    operation3(start, end);

    if (rank == 0) {
        MPI_Recv(&V[H], 2*H, MPI_INT, 1, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Recv(&MX, N*N, MPI_INT, 1, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Send(&V[0], 3*H, MPI_INT, 3, 0, MPI_COMM_WORLD);
        MPI_Send(&MX, N*N, MPI_INT, 3, 0, MPI_COMM_WORLD);
        MPI_Recv(&V, N, MPI_INT, 1, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Recv(&MR[0], H*N, MPI_INT, 1, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
    }
    else if (rank == 1) {
        MPI_Recv(&V[2*H], H, MPI_INT, 2, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Send(&V[H], 2*H, MPI_INT, 0, 0, MPI_COMM_WORLD);
        MPI_Send(&MX, N*N, MPI_INT, 0, 0, MPI_COMM_WORLD);
        MPI_Recv(&V, N, MPI_INT, 2, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Recv(&MR[0], 2*H*N, MPI_INT, 2, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Send(&V, N, MPI_INT, 0, 0, MPI_COMM_WORLD);
        MPI_Send(&MR[0], H*N, MPI_INT, 0, 0, MPI_COMM_WORLD);
    }
    else if (rank == 2) {
        MPI_Send(&V[2*H], H, MPI_INT, 1, 0, MPI_COMM_WORLD);
        MPI_Recv(&V, N, MPI_INT, 3, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Recv(&MX, N*N, MPI_INT, 3, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Recv(&MR[0], 3*H*N, MPI_INT, 3, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Send(&V, N, MPI_INT, 1, 0, MPI_COMM_WORLD);
        MPI_Send(&MR[0], 2*H*N, MPI_INT, 1, 0, MPI_COMM_WORLD);
    }
    else if (rank == 3) {
        MPI_Recv(&V[0], 3*H, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Recv(&MX, N*N, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Send(&V, N, MPI_INT, 2, 0, MPI_COMM_WORLD);
        MPI_Send(&MX, N*N, MPI_INT, 2, 0, MPI_COMM_WORLD);
        MPI_Send(&MR[0], 3*H*N, MPI_INT, 2, 0, MPI_COMM_WORLD);
    }

    operation4(start, end);

    if (rank == 0) {
        MPI_Send(&A[0], H, MPI_INT, 1, 0, MPI_COMM_WORLD);
    }
    else if (rank == 1) {
        MPI_Recv(&A[0], H, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Recv(&A[2*H], 2*H, MPI_INT, 2, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
    }
    else if (rank == 2) {
        MPI_Recv(&A[3*H], H, MPI_INT, 3, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
        MPI_Send(&A[2*H], 2*H, MPI_INT, 1, 0, MPI_COMM_WORLD);
    }
    else if (rank == 3) {
        MPI_Send(&A[3*H], H, MPI_INT, 2, 0, MPI_COMM_WORLD);
    }

    if (rank == 1) {
        printf("result vector A:\n");
        for (int i = 0; i < N; i++) {
            printf("%d ", A[i]);
        }
    }

    MPI_Finalize();
    return 0;
}