
public class Transaccion implements Runnable {

    public static Object lockingObject = new Object();
    private LockManager lockManager = LockManager.getInstance();

    private Cuenta account;
    private double monto;
    String operacion;

    public Transaccion(Cuenta account, String operacion, double monto) {
        this.account = account;
        this.monto = monto;
        this.operacion = operacion;
    }

    @Override
    public void run() {

        if (operacion.equals("D")) {
            synchronized (lockingObject) {
                lockManager.lock(account);
                account.depositar(monto);
                System.out.println(Thread.currentThread().getName() + "|Depositando: " + monto);

            }
        } else if (operacion.equals("R")) {
            synchronized (lockingObject) {
                lockManager.lock(account);
                account.retirar(monto);
                System.out.println(Thread.currentThread().getName() + "|Retirando: " + monto);
                lockManager.unLockAll();
            }
        } else {
            synchronized (lockingObject) {
                waiting();
            }
        }

    }

    public void waiting() {

        while (lockManager.isLocked()) {
            try {
                System.out.println(Thread.currentThread().getName() + " ... waiting");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}

