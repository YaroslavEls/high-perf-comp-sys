public class Monitor2 {
    private int F1 = 0;
    private int F2 = 0;
    private int F3 = 0;

    public synchronized void SignalIn() {
        F1++;
        if (F1 == 3) notifyAll();
    }

    public synchronized void WaitIn() {
        try {
            if (F1 < 3) wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void SignalOut() {
        F2++;
        if (F2 == 3) notifyAll();
    }

    public synchronized void WaitOut() {
        try {
            if (F2 < 3) wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void SignalA() {
        F3++;
        if (F3 == 4) notifyAll();
    }

    public synchronized void WaitA() {
        try {
            if (F3 < 4) wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
