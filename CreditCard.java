
public class CreditCard {

	private String cardNum;
	private int expMonth;
	private int expYear;
	private CardType type;
	
	public CreditCard(){}
	public CreditCard(CardType tp,String cn,int mon,int yr)
	{
		this.cardNum = cn;
		this.expMonth  = mon;
		this.expYear = yr;
		this.type = tp;
	}
}
