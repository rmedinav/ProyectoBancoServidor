
public class Cuenta {

    private int userID;
    private double balance;

    public Cuenta() {
        balance = 0;
        userID = 0;
    }
    public Cuenta(int userID, double balance) {
        
        this.userID = userID;
        this.balance = balance;
    }
    
     public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Cuenta(double initial_balance) {
        this.balance = initial_balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double amount) {
        balance = amount;
    }

    public void depositar(double amount) {
        balance = balance + amount;
    }

    public void retirar(double amount) {
        if (balance >= amount) {
            balance = balance - amount;
        }
        else{
            System.out.println("Saldo Insuficiente, operacion no realizada");
        }
    }

}
