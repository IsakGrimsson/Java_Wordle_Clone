public class SyncWaiter {
    public void waitUntilISaySo() {
        try{
            wait();
        }catch(InterruptedException ie){}
    }
    public void waitNoMore() {
        notify();
    }
}
