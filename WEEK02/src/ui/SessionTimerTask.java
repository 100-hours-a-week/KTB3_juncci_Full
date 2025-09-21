package ui;

public class SessionTimerTask implements Runnable {
    private final int timeoutSeconds;
    private volatile boolean expired = false;
    private Thread thread;

    public SessionTimerTask(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        expired = true;
        if (thread != null) thread.interrupt();
    }

    public boolean isExpired() {
        return expired;
    }

    @Override
    public void run() {
        try {
            for (int i = timeoutSeconds; i > 0; i--) {
                Thread.sleep(1000);
                if (expired) return;
                System.out.println("[시스템] 남은 시간: " + i + "초");
            }
            System.out.println("[시스템] 세션이 만료되었습니다. 메인 메뉴로 돌아갑니다.");
            expired = true;
        } catch (InterruptedException ignored) {}
    }
}
