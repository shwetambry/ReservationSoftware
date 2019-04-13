
public class CashPayment extends Payment {
	public double amount;
	public CashPayment(double amt)
	{
		super(amt);
		this.amount = amt;
	}
	public void pay()
	{
		System.out.println("Payment of "+this.amount+" been made with cash.");
	}
}
