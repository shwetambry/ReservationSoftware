import java.util.*;

import org.apache.commons.lang.time.DateUtils;
public class Reservation {

	private Customer cust;
	private Room room;
	private String resID = null;
	private Date checkinDate;
	private Date checkoutDate;
	private Account acc;
	
	public Reservation(){}
	public Reservation(Customer c, Room r, String id, Account account)
	{
		this.cust = c;
		this.room =  r;
		this.resID=id;
		this.acc = account;
	}
	public Customer getCust()
	{
		return this.cust;
	}
	public void setCust(Customer c)
	{
		this.cust = c;
	}
	public Room getRoom()
	{
		return this.room;
	}
	public void setRoom(Room rm)
	{
		this.room = rm;
	}
	public String getResID()
	{
		return this.resID;
	}
	public void setResID(String id)
	{
		this.resID = id;
	}
	public Date getCheckinDate()
	{
		return this.checkinDate;
	}
	protected void setCheckinDate(Date dt)
	{
		this.checkinDate = dt;
		this.room.setBookedFrom(dt);
	}
	public Date getCheckoutDate()
	{
		return this.checkoutDate;
	}
	protected void setCheckoutDate(Date dt)
	{
		this.checkoutDate = dt;
		this.room.setBookedTill(dt);
	}
	@SuppressWarnings("deprecation")
	private String generateResID()
	{
		String id = "";
		id+=Integer.toString(this.room.getFloorNum());
		int tmpR = this.room.getRoomNum();
		if(tmpR > 0 && tmpR <10 )
		{
			id += "0";
		}
		id+=Integer.toString(tmpR);
		id += Integer.toString(this.checkinDate.getDate());
		id+=Integer.toString(this.checkinDate.getMonth()+1);
		id+=Integer.toString(this.checkinDate.getYear()-100);
		
		return id;			
	}
	private void generateBookingForm()
	{}
	private void cancelReservation()
	{
		setCheckinDate(null);
		setCheckoutDate(null);
		this.resID = null;
	}
	
	private boolean helperPayment(double amt)
	{
		System.out.println("What is your mode of payment? Enter Cash or Card ");
		Scanner in = new Scanner(System.in);
		String mode = in.next();
		if(mode.equals("Card")||mode.equals("card"))
		{
			return this.acc.payWCard(this.resID,amt);
		}
		else
		{
			return this.acc.payWCash(this.resID,amt);
		}
	}
	
	public void reserve(Date from, Date to)
	{
		if(room.enquiry(from, to))
		{	
			Date today = new Date();
			int howLongAgo = (int)((from.getTime()-today.getTime())/(1000*60*60*24));
			//System.out.println((from.getTime()-today.getTime())/(1000*60*60*24));
			
			
			//Room can only be reserved upto two months advance
			if(howLongAgo > 61)
			{
				System.out.println("Room can be reserved upto two months advance. Change your dates");
			}
			else
			{
				int duration = (int)((to.getTime()-from.getTime())/(1000*60*60*24));
				//Room can only be booked for seven days.
				if(duration >=7)
				{
					System.out.println("Booking can only be made for one week. ");
					
				}
				else
				{
					setCheckinDate(from);
					setCheckoutDate(to);
					this.room.setPrice();
					this.resID = generateResID();
					double cost = this.room.getPrice()*duration;
				//	System.out.println("What is the cost " + cost);
					//System.out.println("What is the resID " + this.resID);
					
					this.acc.addToAmt(this.resID,cost);
					System.out.println("_____________________________");
					System.out.println("Charging deposit for reservation" );
					System.out.println("_____________________________");
					boolean depositPaid = false;
					if(this.room.getRoomType().toString().equals("penthouseSuite"))
					{ 	
						System.out.println("Customer needs to make 10% deposit" );
						depositPaid = acc.payWCard(this.resID,.10*cost);
					}
					else
					{	System.out.println("What is your mode of payment? Enter Cash or Card ");
						Scanner in = new Scanner(System.in);
						String mode = in.next();
						if(mode.equals("Card")||mode.equals("card"))
						{
							System.out.println("Customer needs to make 10% deposit" );
							depositPaid = this.acc.payWCard(this.resID,.10*cost);
						}
						else
						{
							System.out.println("Customer needs to make 80% deposit" );
							depositPaid = this.acc.payWCash(this.resID,.8*cost);
						}
					}
					if(depositPaid)
					{
						System.out.println("Reservation done and resID is "+ this.resID);
						
					}
					else
					{
						System.out.println("Reservation not done because of unsuccessful payment. ");
						cancelReservation();
					}
					System.out.println("____________________________________");
				}
			}
		}
	}
	
	public void checkin(String resID)
	{
		//TO DO: ensure resID is valid
		System.out.println("Customer needs to provide creditcard number: ");
		Scanner in = new Scanner(System.in);
		String cc = in.next();
		System.out.println("Customer needs to provide expiration Month: ");
		int  mon = in.nextInt();
		System.out.println("Customer needs to provide expiration year: ");
		int yr = in.nextInt();
		System.out.println("Provide card type:Mastercard, Visa or AmericanExpress: ");
		String tp = in.next();
		CardType type = CardType.valueOf(tp);
		CreditCard card = new CreditCard(type,cc,mon,yr);
		cust.setCreditCardDetail(card);
		System.out.println("Customer needs to provide ID: ");
		String id = in.next();
		cust.setId(id);
		System.out.println("Customer's name: ");
		String name = in.nextLine(); 
		cust.setName(name);
		System.out.println("Customer's Gender: Male or Female ");
		String gender = in.next();
		Gender g = Gender.valueOf(gender);
		cust.setGender(g);
		System.out.println("Customer's street number and name: ");
		String street = in.nextLine();
		System.out.println("Customer's suburb: ");
		String sub = in.nextLine(); 
		System.out.println("Customer's postCode: ");
		int post = in.nextInt(); 
		Address address = new Address(street, sub, post);
		cust.setAddress(address);
		in.close();
		generateBookingForm();
		
	}
	
	public void checkout(String resID)
	{
		Date date = new Date();
		if(resID.equals(null))
		{
			System.out.println("Enter valid resID");
		}
		else if(date.after(this.checkoutDate))
		{
			extend(resID);
		}
		else
		{
			System.out.println("Checking out a customer with reservation ID: " + resID)  ;
			this.acc.generateBill(this.resID);
			if(!this.helperPayment(this.acc.getBal(this.resID)))
			{
				this.cust.setDefaulterStatus(true);
			}
			
			
		}
	}
	public void extend(String resID)
	{
		System.out.println("_____________________________");
		System.out.println("Customer wants to extend his stay. ");
		System.out.println("_____________________________");
		
		if(resID == null)
		{
			System.out.println("Reservation ID not found");
			return;
		}
		boolean clearDues = true;
		this.acc.generateBill(resID);
		double balance = acc.getBal(this.resID);
		Scanner in = new Scanner(System.in);
		System.out.println("How was deposit paid? Cash or Card  ");
		String mode = in.next();
		if(room.getRoomType().toString().equals("PenthouseSuite") && balance >=5000)
		{
			clearDues = acc.payWCard(this.resID, balance);
		}
		else if(balance >= 2000)
		{
			clearDues = acc.payWCard(this.resID,balance);
		}
		else if(mode.equals("Cash")||mode.equals("cash"))
		{
			System.out.println("Clearing outstanding dues....");
			clearDues = helperPayment(this.acc.getBal(this.resID));
		}
		if(!clearDues)
		{
			System.out.println("Reservation can not be extended because dues are not cleared. ");
		}
		else
		{
			int num = 0;
			do
			{
				System.out.println("For how many days the customer wants to extend: Enter from 1 to 7");
				num = in.nextInt();
			}while(num<1 || num > 7);
			
			Date till = DateUtils.addDays(this.checkoutDate, num);
			boolean vacancy = room.enquiry(this.checkoutDate, till);
			System.out.println("vacancy for same room " + vacancy);
			if(vacancy)
			{
				setCheckoutDate(till); 
				System.out.println("Booking is extended for the same room.");
			}
			else
			{
				//search for the vacancy in all other rooms in the database.
				boolean vac = false;
				for (int i=0;i<80;i++)
				{
					Random rand = new Random();
					int j = rand.nextInt(4);
					//randomly generating type of the room
					RoomType type = RoomType.values()[j];
					Room newroom = new Room(RoomType.familySuite, i%18 ,i/18);
					vac = newroom.enquiry(this.checkoutDate, till);
					if(vac)
					{
						System.out.println("Is customer interested in booking this room. Enter yes or no: ");
						String ans = in.next();
						if(ans.equals("yes"))
						{
							newroom.setBookedFrom(this.checkoutDate);
							newroom.setBookedTill(till);
							newroom.setPrice();
							generateResID();
							break;
						}
					}
				}//end of for loop
				if(!vac)
				{
					System.out.println("Sorry, no room is available. ");
				}
			}
		}
	}//end of method


public static void main(String[] args)
{	
	Address add= new Address("broadway","sydney",2000);
	
	Customer cust = new Customer("Smith",add, Gender.Male);
	Room room = new Room(RoomType.familySuite,4,0);
	Account account = new Account();
	Reservation reservation = new Reservation(cust,room,null,account);
	Date from = new Date(117,9,22); // The year it takes is 1900 onwards. basically current year - 1900
	Date to = new Date(117,9,25);
	//System.out.println("From date: "+from);
	Room room1 = new Room(RoomType.familySuite,4,0);
	
	account.addRes(reservation);
	reservation.reserve(from, to);
	Date newFrom = new Date(117,9,25);
	Date newTo = new Date(117,9, 28);
	Reservation reservation1 = new Reservation(cust,room,null,account);
	account.addRes(reservation1);
	reservation1.reserve(newFrom, newTo);
	System.out.println("ResID of res1 "+reservation1.getResID());
	System.out.println();
	reservation.extend(reservation.getResID());
	//System.out.println("Account details: "+account.getBal(reservation.getResID()));
	
	//System.out.println("booked from : "+room.getBookedFrom());
	//System.out.println("booked Till : "+room.getBookedTill());

	//System.out.println("ResID : "+reservation.getResID());
	
}
}












