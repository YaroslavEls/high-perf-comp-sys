public class Monitor3 {
    private int F1 = 0;
    private int F2 = 0;
    private int F3 = 0;

    public synchronized void SignalSort1() {
        F1++;
        if (F1 == 1) notifyAll();
    }

    public synchronized void WaitSort1() {
        try {
            if (F1 < 1) wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void SignalSort2() {
        F2++;
        if (F2 == 1) notifyAll();
    }

    public synchronized void WaitSort2() {
        try {
            if (F2 < 1) wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void SignalSort3() {
        F3++;
        if (F3 == 1) notifyAll();
    }

    public synchronized void WaitSort3() {
        try {
            if (F3 < 1) wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
