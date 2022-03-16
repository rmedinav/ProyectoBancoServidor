
import java.util.ArrayList;
import java.util.List;

public class LockManager {
	private static LockManager single_instance = null;
	private static List<Cuenta> accountLocks = new ArrayList<Cuenta>();

	public static LockManager getInstance() {
		if (single_instance == null)
			single_instance = new LockManager();

		return single_instance;
	}

	private LockManager() {
		accountLocks.clear();
	}

	public boolean isLocked(Cuenta account) {

		return accountLocks.contains(account);
	}
	
	public boolean isLocked() {

		return !accountLocks.isEmpty();
	}

	public void lock(Cuenta account) {
		accountLocks.add(account);
	}

	public void unLock(Cuenta account) {
		accountLocks.remove(account);
	}

	public void unLockAll() {
		accountLocks.clear();
	}

}
