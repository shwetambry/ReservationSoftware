
public class Address {

	private String streetNumName;
	private String suburb;
	private int postCode;
	
	public Address(String street, String sub, int post)
	{
		streetNumName = street;
		suburb = sub;
		postCode = post;
	}
	public String getStreetNumName()
	{
		return streetNumName;
	}
	public String getSuburb()
	{
		return suburb;
	}
	public int getPostCode()
	{
		return postCode;
	}
}
