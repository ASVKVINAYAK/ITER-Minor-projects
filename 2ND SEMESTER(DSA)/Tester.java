import java.util.*;

class Author
{
	private String name;
	private String email;
	private char  gender;
	public Author(String name, String email, char gender)
	{
		this.name=name;
		this.email=email;
		this.gender=gender; 
	}
	public String getName()
	{
		return name;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public char getGender() 
	{
		return gender;
	}
	public String toString()
	{
		return "Author [name=" + name + ", email=" + email + ", gender=" + gender + "]";
	}


}
class Book
{
	private String  name;
	private double  price;
	private int qty=0;
	private Author author;
	public Book (String name, Author author, double price) 
	{
		this.name=name;
		this.author=author;
		this.price=price;  
	}
	public Book (String name, Author author, double price, int qty) 
	{
		this(name,author,price);
		this.qty=qty;
	}
	public String getName() 
	{
		return name;
	}

	public double getPrice() 
	{
		return price;
	}
	public void setPrice(double price) 
	{
		this.price = price;
	}
	public int getQty() 
	{
		return qty;
	}
	public void setQty(int qty)
	{
		this.qty = qty;
	}
	public Author getAuthor() 
	{
		return author;
	}
	public String toString() {
		return "Book [name=" + name + ", price=" + price + ", qty=" + qty + ", Author=" + author + "]";
	}

}

class Library
{
	Scanner sc=new Scanner(System.in);
	int n,i;
	Book book[];
	Library(int n)
	{
		this.n=n;
		book=new Book[n];
		getdata();
	}
	public void getdata()
	{
		int c=0;
		System.out.println("Enter details");
		Scanner scc=new Scanner(System.in);
		for(i=0;i<n;i++)
		{
			System.out.println("For Book "+(++c));
			System.out.println("Enter the Author  details ");
			System.out.println(" 1>Name\n 2>E-mail \n 3>Gender");
			Author a=new Author(scc.nextLine(),sc.next(),sc.next().charAt(0));
			System.out.println("Enter the details of the Book!\n 1>Name\n 2>Price \n 3>Quantity");
			String name=scc.nextLine();
			int price=sc.nextInt();
			int qty=sc.nextInt();
			if(qty==0) 
			{
				book[i]=new Book(name,a,price);
			}
			else
				book[i]=new Book(name,a,price,qty);
		}

	}
	public void display()
	{
		for(int x=0;x<n;x++) 
		{
			System.out.println((x+1)+" : "+book[x].toString());
		}
	}				
}


class Student 
{
	String name;
	int roll;
	Date issueDate;
	Date returnDate;
	int noofbooksissued=0;
	Scanner sc=new Scanner(System.in);
	public Student(String name, int roll, Date issueDate, Date returnDate) 
	{
		this.name = name;
		this.roll = roll;
		this.issueDate = issueDate;
		this.returnDate = returnDate;
	}
	public String getName() 
	{
		return name;
	}
	public int getRoll() 
	{
		return roll;
	}
	public String toString() 
	{
		return "Student [name=" + name + ", roll=" + roll + ", issueDate=" + issueDate + ", returnDate=" + returnDate+ "]";
	}
	public Book issueBook(Library ob){
		System.out.println("The Books present in Library are...");
		ob.display();
		if(noofbooksissued<5) 
		{
			System.out.println("Enter the Book no  you want to  issue!!!");
			int choice=sc.nextInt();
			if(choice<=0 || choice-1>=ob.n || ob.book[choice-1].getQty()==0)
			{
				System.out.println("Not Available!");
				return null;
			}
			ob.book[choice-1].setQty(ob.book[choice-1].getQty()-1);
			if(noofbooksissued==0) {
				System.out.println("Enter the date please! 1>DD 2>MM 3>YYYY ");
				int a,b,c;
				a=sc.nextInt();
				if(a<=0||a>32)
				{
					System.out.println("Not a proper date  please re enter date from(1 to 31) ");
					a=sc.nextInt();
				}
				b=sc.nextInt();
				if(b<0||b>12)
				{
					System.out.println("Not a proper month please re enter month  from(1 to 12) ");
					b=sc.nextInt();
				}
				c=sc.nextInt();
				if(c<999)
				{
					System.out.println("Not a proper ye3ar  please re enter year ( should contain 4 digit) ");
					c=sc.nextInt();
				}
				issueDate=new Date(a,b,c);
			}
			noofbooksissued++;
			return ob.book[choice-1];
		}
		else System.out.println("Sorry :( You have already issued 5 books!!!");
		return null;
	}
	public static boolean leapyear(int year) 
	{
		if(year%100!=0 && year%4==0) return true;
		if(year%100==0 && year%400==0) return true;
		return false;
	}
	public  int fine(Date issueDate,Date returnDate) {
		int count=0;
		int month[]= {31,28,31,30,31,30,31,31,30,31,30,31};
		if(issueDate.getYYYY()==returnDate.getYYYY() && issueDate.getMM()==returnDate.getMM()) 
		{
			count=returnDate.getDD()-issueDate.getDD();
			return count;
		}
		else if(issueDate.getYYYY()==returnDate.getYYYY())
		{
			for(int x=issueDate.getMM()+1;x<returnDate.getMM();x++)
			{
				if(x-1==1) 
				{
					if(leapyear(issueDate.getYYYY()))
						count=count+month[x-1]+1;
					else count=count+month[x-1];
				}
				else count=count+month[x-1];
			}
		}
		else {
			for(int x=issueDate.getYYYY()+1;x<returnDate.getYYYY();x++) 
			{
				if(leapyear(x)) count=count+366;
				else count=count+365;
			}
			for(int x=issueDate.getMM()+1;x<=12;x++) 
			{
				if(x-1==1) 
				{
					if(leapyear(issueDate.getYYYY()))
						count=count+month[x-1]+1;
					else count=count+month[x-1];
				}
				else count=count+month[x-1];
			}
			for(int x=1;x<returnDate.getMM();x++)
			{
				if(x-1==1)
				{
					if(leapyear(issueDate.getYYYY()))
						count=count+month[x-1]+1;
					else count=count+month[x-1];
				}
				else count=count+month[x-1];
			}
		}
		if(issueDate.getMM()-1==1) 
		{
			if(leapyear(issueDate.getYYYY()))
				count=count+(month[1]+1-issueDate.getDD());
			else count=count+(month[1]-issueDate.getDD());
		}
		else count=count+month[issueDate.getMM()-1]-issueDate.getDD();
		if(returnDate.getMM()-1==1) 
		{
			if(leapyear(returnDate.getYYYY()))
				count=count+(month[1]+1-issueDate.getDD());
			else count=count+(month[1]-issueDate.getDD());
		}
		else count=count+month[issueDate.getMM()-1]-issueDate.getDD();
		return count;
	}
	public void depositBook(Library ob) 
	{
		if(noofbooksissued==0) 
			System.out.println("Sorry :| You havn't issued any books yet!");
		else 
		{
			noofbooksissued--;
			System.out.println("Enter the book you want to return!");
			int choice=sc.nextInt();
			System.out.println("Please Enter your Return Date !");
			int a,b,c;
			a=sc.nextInt();
			if(a<=0||a>32)
			{
				System.out.println("Not a proper date  please re enter date from(1 to 31) ");
				a=sc.nextInt();
			}
			b=sc.nextInt();
			if(b<0||b>12)
			{
				System.out.println("Not a proper month please re enter month  from(1 to 12) ");
				b=sc.nextInt();
			}
			c=sc.nextInt();
			if(c<999)
			{
				System.out.println("Not a proper ye3ar  please re enter year ( should contain 4 digit) ");
				c=sc.nextInt();
			}
			returnDate=new Date(a,b,c);
			System.out.println("Your fine for now is : "+fine(issueDate,returnDate)); 
			ob.book[choice-1].setQty(ob.book[choice-1].getQty()+1);
		}
	}


}
class Date
{
	int dd,mm,yyyy;
	Date(int dd,int mm,int yyyy)
	{
		this.dd=dd;
		this.mm=mm;
		this.yyyy=yyyy;
	}
	public int getDD() 
	{
		return dd;
	}
	public int getMM() 
	{
		return mm;
	}
	public int getYYYY()
	{
		return yyyy;
	}
	public String toString()
	{
		return "Date [dd=" + dd + ", mm=" + mm + ", yyyy=" + yyyy + "]";
	}

}

public class Tester 
{

	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("//***************************SOA LIBRARY MANAGEMENT SYSTEM********************************//");
		System.out.println("//*************************** The world is quiet here **************************//");
		System.out.println("Updating books in the Library \n ");
		System.out.println("Enter the no of books to be updated in Library !!!");
		Library ob=new Library(sc.nextInt());
		System.out.println("List of the Books are ");
		ob.display();
		System.out.println(" \n  Student Section \n ");
		System.out.println("Enter the no of Students details !");
		int n=sc.nextInt();
		Student s[]=new Student[n];
		for(int x=0;x<s.length;x++) {
			System.out.println("Enter the Student "+(x+1)+" Data! \n  1>Name \n  2>Roll");
			//passing default dates in beginning...
			s[x]=new Student(sc.next(),sc.nextInt(),new Date(00,00,0000),new Date(00,00,0000));
			System.out.println("Enter the no of Books you Want to  Issue!!!");
			int count=sc.nextInt();
			while(count-->0) s[x].issueBook(ob);
			System.out.println("Enter the no of Books you Wanna return!!!");
			count=sc.nextInt();
			while(count-->0) s[x].depositBook(ob);
			System.out.println(s[x].toString());

		}
		System.out.println("//**************************************************************************//");


	}

}
