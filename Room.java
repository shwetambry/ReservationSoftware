
import java.util.*;
import org.apache.commons.lang.time.DateUtils;

public class Room {

	private RoomType type;
	private int pricePerNight;
	private int roomNum;
	private int floorNum;
	private ArrayList<Date> bookedFrom = new ArrayList<Date>();
	private ArrayList<Date> bookedTill = new ArrayList<Date>();
	public Room(){}
	
	public Room(RoomType t,int rnum, int fnum)
	{
		this.type = t;
		this.roomNum = rnum;
		try
		{	
			if(fnum <0 || fnum >7)
			{
				System.out.println("Invalid Floor number" );
			}
			else
			{
				this.floorNum = fnum;
			}
		} catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
				System.out.println("Valid floor numbers are between 0 to 7. ");
		}
		
		
	}
	public RoomType getRoomType()
	{
		return this.type;
	}
	public int getRoomNum()
	{
		return this.roomNum;
	}
	public int getFloorNum()
	{
		return this.floorNum;
	}
	public int getPrice()
	{
		return this.pricePerNight;
	}
	protected void setPrice()
	{
		switch (this.type)
		{
			case standardRoom: this.pricePerNight = 120;
			break;
			case executiveSuite: this.pricePerNight = 250;
			break;
			case familySuite: this.pricePerNight = 300;
			break;
			case penthouseSuite: this.pricePerNight =   500;
			break;
		}
		
	}
	public ArrayList<Date> getBookedFrom()
	{
		return this.bookedFrom;
	}
	public ArrayList<Date> getBookedTill()
	{
		return this.bookedTill;
	}
	protected void setBookedFrom(Date date)
	{
		this.bookedFrom.add(date);
	}
	protected void setBookedTill(Date date)
	{
		this.bookedTill.add(date);
	}
	
	
	private boolean availability(Date dt)
	{
		boolean result = false;
		if(this.bookedFrom.size() == 0)
		{
			result = true;
		}
		else
		{
			for(int i=0;i<this.bookedFrom.size();i++)
			{
				if((dt.compareTo(this.bookedFrom.get(i))<0)||(dt.compareTo(this.bookedTill.get(i))>=0))
				{
					result = true;
				}
				else
				{
					result = false;
					break;
					
				}
			}
		}
		return result;
/*		else if( (dt.compareTo(this.bookedFrom)<0) || (dt.compareTo(this.bookedTill)>=0))
		{
			return true;
		}
		else
		{
			return false;
		}
*/	}
	
	public boolean enquiry(Date from, Date till)
	{
		if(from.compareTo(till)>=0)
		{
			System.out.println("From date must come before to date");
			return false;
		}
		Date tmp = from;
        while(tmp.compareTo(till) < 0)
        {
        	if(!availability(tmp))
        	{
        		System.out.println("Sorry, the room is already booked. Please change your dates");
        		return false;
        	}
        	else
        	{
        		tmp  = DateUtils.addDays(tmp, 1);
        	}
        }
        return true;
	}
	@SuppressWarnings("deprecation")
	public static void main(String[] args)
	{
		RoomType type = RoomType.standardRoom;
		Room rm = new Room(type, 2,2);
		Date from = new Date(117,1,3);
		Date till = new Date(117,1,12);
		Room rm2 = new Room(type,3,8);
		System.out.println(rm2.getFloorNum());
		//rm.bookRoom(from, till);
		Date enqFrom = new Date(117,4,7);
		Date enqTill = new Date(117,4,8);
		int days = (int)((till.getTime()-from.getTime())/(1000*60*60*24));
		rm.setBookedFrom(from);
		rm.setBookedTill(till);
		boolean ans1 = rm.enquiry(enqFrom, enqTill);
		System.out.println("Testing booking" + rm.bookedFrom);
		///		
		System.out.println("num of days passed "+days);
		boolean ans = rm.enquiry(enqFrom,enqTill);
		System.out.println("Availability for rm " + ans1);
		//System.out.println("Availabiiiiiity for rm2" + ans1);
		Date today = new Date();
		System.out.println("The type of room is :"+rm.type.toString().equals("standardRoom"));
		System.out.println("");
		 
	}
	
	
}
