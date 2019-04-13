import java.util.*;
public class Account {
	protected ArrayList<Reservation> reservations = new ArrayList<Reservation>();
	private double[] amount = new double[10];
	private double[] balance = new double[10];
	private double[] movieAndCallCharge = new double[10];
	
	public Account(){	}
	public void addRes(Reservation res)
	{
		reservations.add(res);
		int i = reservations.size()-1;
		this.amount[i] = 0;
		this.balance[i] = 0;
		this.movieAndCallCharge[i] = 0;
		
	}
	public void removeRes(String resID)
	{
		for(int i=0;i<=reservations.size();i++)
		{
			Reservation res = reservations.get(i);
			if(res.getResID().equals(resID))
			{
				reservations.remove(i);
				return;
			}
		}
		System.out.println("Reservation not found. ");
	}
	
	public double getAmt(String resID)
	{
		for(int i=0;i<reservations.size();i++)
		{
			if(reservations.get(i).getResID().equals(resID))
			{
				return  this.amount[i];
			}
		}
		System.out.println("ResID not found" );
		return -1;
		
		
	}
	public double getBal(String resID)
	{
		for(int i=0;i<reservations.size();i++)
		{
			if(reservations.get(i).getResID().equals(resID))
			{
				return this.balance[i];
			}
		}
		System.out.println("ResID not found" );
		return -1;
	}
	public void addToAmt(String resID, double amt)
	{
		for(int i=0;i<reservations.size();i++)
		{
			if(reservations.get(i).getResID().equals(resID))
			{
				this.amount[i] += amt;
				this.balance[i] += amt;
				return;
			}
		}
		System.out.println("ResID not found" );
		
	}
	public void addMovieCallCharge(String resID, double ch)
	{
		for(int i=0;i<reservations.size();i++)
		{
			if(reservations.get(i).getResID().equals(resID))
			{
				this.amount[i] += ch;
				this.movieAndCallCharge[i] += ch;
				return;
			}
		}
		System.out.println("ResID not found" );
	}
	public double getMovieCallCharge(String resID)
	{
		for(int i=0;i<reservations.size();i++)
		{
			if(reservations.get(i).getResID().equals(resID))
			{
				return this.movieAndCallCharge[i];
			}
		}
		System.out.println("ResID not found" );
		return -1;
	}
	public boolean payWCard(String resID, double amt)
	{
		boolean result = false;
		for(int i=0; i< reservations.size();i++)
		{
			if(reservations.get(i).getResID().equals(resID))
			{
				while(true)
				{	Payment p = new CardPayment(amt);
					if(p.paymentSuccessful())
					{
						p.pay();
						this.balance[i] -= amt;
						return true;
					}
					else
					{
						System.out.println("Payment unsuccessful. Does customer want to try again. yes or no: ");
						Scanner in = new Scanner(System.in);
						String response = in.next();
						if(response.equals("no"))
						{
							return false;
						}
						
					} 
				}// end of the while loop.
				
			}
		}
		System.out.println("ResID is not found.");
		return false;
		
	}
	public boolean payWCash(String resID, double bal)
	{

		for(int i=0; i< reservations.size();i++)
		{
			if(reservations.get(i).getResID().equals(resID))
			{
				Payment p = new CashPayment(bal);
				if(p.paymentSuccessful())
				{
					p.pay();
					this.balance[i] -= bal;
					return true;
				}
				else
				{
					System.out.println("Payment unsuccessful");
					return false;
				}
				
			}
		}
		System.out.println("ResID is not found.");
		return false;
	}
	public void generateBill(String resID){}
	
	public static void main(String[] args)
	{
		Address add= new Address("broadway","sydney",2000);
		Customer cust = new Customer("Smith",add, Gender.Male);
		Room room = new Room(RoomType.standardRoom,4,2);
		Account acc=new Account();
		Reservation reservation = new Reservation(cust,room,"123",acc);
		acc.addRes(reservation);
		
		System.out.println("Current balanc and amount are: "+ acc.getBal("123")+"  "+ acc.getAmt("123"));
		acc.addToAmt("123", 30.0);
		acc.addToAmt("123", 30.0);
		System.out.println("Current balanc and amount are: "+ acc.getBal("123")+"  "+ acc.getAmt("123"));
		
		Room room2 =new Room(RoomType.executiveSuite, 5,3);
		Reservation reservation2 = new Reservation(cust,room2,"456",acc);
		acc.addRes(reservation2);
		
		System.out.println("Current balanc and amount are: "+ acc.getBal("456")+"  "+ acc.getAmt("456"));
		acc.addToAmt("456", 30.0);
		System.out.println("Current balanc and amount are: "+ acc.getBal("456")+"  "+ acc.getAmt("456"));
		
	}
}
