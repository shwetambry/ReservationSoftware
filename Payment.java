import java.util.Scanner;
public class Payment {
	
	private double amount;
	public Payment(double amt)
	{
		this.amount = amt;
	}
	public double getAmt()
	{
		return this.amount;
	}
	
	public void pay(){}
	public boolean paymentSuccessful()
	{
		boolean success = false;
		Scanner in = new Scanner(System.in);
		System.out.println("Was the payment successful.Enter yes or no:");
		String status = in.next();
		if(status.equals("yes"))
		{
			success = true;
		}
		
		return success;
	}

}