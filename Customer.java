
public class Customer {

	private String name;
	private Address address;
	private Gender gender;
	private String identification;
	private boolean isDefaulter = false;
	private boolean isFrequent = false;
	private CreditCard creditCardDetail=null;
	
	public Customer(){}
	public Customer(String na,Address add, Gender g)
	{
		this.name=na;
		this.address=add;
		this.gender=g;
	}
	public String getName()
	{
		return name;
	}
	protected void setName(String nam)
	{
		this.name = nam;
	}
	public Address getAddress()
	{
		return address;
	}
	protected void setAddress(Address add)
	{
		this.address = add;
	}
	public Gender getGender()
	{
		return gender;
	}
	protected void setGender(Gender gen)
	{
		this.gender = gen;
	}
	public String getID()
	{
		return identification;
	}
	protected void setId(String id)
	{
		identification = id;
	}
	public boolean getDefaulterStatus()
	{
		return isDefaulter;
	}
	protected void setDefaulterStatus(boolean status)
	{
		isDefaulter = status;
	}
	public boolean getFrequentStatus()
	{
		return isFrequent;
	}
	protected void setFrequentStatus(boolean status)
	{
		isFrequent = status;
	}
	public CreditCard getCreditCardDetail()
	{
		return creditCardDetail;
	}
	protected void setCreditCardDetail(CreditCard card)
	{
		creditCardDetail = card;
	}
}
