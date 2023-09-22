public class LamportClock {
    private int time;
    public LamportClock() {
        this.time = 0;
    }
    public synchronized int next_time() {
        return ++time;
    }
    public synchronized void update(int new_time) {
        time = Math.max(time, new_time) + 1;
    }
    public synchronized int get_time() {
        return time;
    }
}
