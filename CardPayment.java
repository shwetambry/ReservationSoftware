
public class CardPayment extends Payment {
	private CreditCard card;
	private double amount;
	
	public CardPayment(double amt)
	{
		super(amt);
		this.amount = amt;
	}
	public void setCard(CreditCard c)
	{
		this.card=c;
	}
	public CreditCard getCard()
	{
		return this.card;
	}
	
	public void pay()
	{
		System.out.println("Payment of "+this.amount+" been made with creditcard.");
	}
}
