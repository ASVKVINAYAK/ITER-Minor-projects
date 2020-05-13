import java.util.*;
class Nodes
{
	public int coff;
	public int exp;
	Nodes next;
	Nodes(int coff,int exp){
		this.coff=coff;
		this.exp=exp;
	}
}

public class MinorProject2 
{

	public static void main(String[] args) 
	{

		Nodes p1=new Nodes(0,0);
		Nodes p2=new Nodes(0,0);
		Nodes p3=new Nodes(0,0);
		while(true)
		{
		Scanner sc=new Scanner(System.in);
		System.out.println("\t\t\t\t\t\t****MENU*****");
		System.out.println();
		System.out.println("\t\t\t\t\t0:Exit");
		System.out.println("\t\t\t\t\t1:Enter polynomial function details ");
		System.out.println("\t\t\t\t\t2:Display polynomial functions ");
		System.out.println("\t\t\t\t\t3:Add polynomial functions ");
		System.out.println("\t\t\t\t\t4:Display polynomial functions after adding  ");
		System.out.println("enter your choice ");
		int ch=sc.nextInt();
		switch(ch)
		{
		    case 0:
			System.out.println("Exiting The Program");
			sc.close();
			System.exit(0);
		    case 1:
		    	System.out.println("Enter 1st polynomial function ");
		    	p1=create();
		    	System.out.println();
		    	System.out.println("Enter 2nd polynomial function ");
		    	p2=create();
		    	System.out.println();
		    	break;
		    case 2:
		    	System.out.println("Displaying  1st polynomial functions ");
		       display(p1);
		       System.out.println("Displaying  2nd polynomial functions ");
		       display(p2);
		       System.out.println();
		       break;
		    case 3:
		      p3=add(p1,p2);
		      p3=reverse(p3);
		      System.out.println("Successfully Added the polynomial functions ");
		      System.out.println();
		      break;
		    case 4:
		    	System.out.println("Displaying polynomial functions after adding  ");
		       display(p3);
		       System.out.println();
		       break;
		    default:
				System.out.println("Wrong choice");
		
		}
		
		}
	}
	public static Nodes create()
	{
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("Enter the degree of the polynomial function ");
		int x,y,z;
		x=sc.nextInt();
		Nodes head=null,tail=null;
		head=tail=new Nodes(0,0);
		while(x>=0)
		{
			System.out.println("Enter the cofficient of x^"+x);
			z=sc.nextInt();
			tail.next=new Nodes(z,x);
			tail=tail.next;
			--x;
		}
        return head;
	}
	public static Nodes add(Nodes head1,Nodes head2) {
		if(head1==null) return head2;
		if(head2==null) return head1;
		head1 =reverse(head1);
		head2 =reverse(head2);
		Nodes res=new Nodes(0,0),trav=res;
		while(head1!=null && head2!=null) {
			if(head1.exp<head2.exp) {
				trav.next=head1;
				trav=trav.next;
				head1=head1.next;
			}
			else if(head1.exp>head2.exp) {
				trav.next=head2;
				trav=trav.next;
				head2=head2.next;
			}
			else {
				trav.next=head2;
				trav=trav.next;
				trav.coff += head1.coff;
				head1=head1.next;
				head2=head2.next;
			}
			trav.next=null;
		}
		if(head1!=null) trav.next=head1;
		if(head2!=null) trav.next=head2;
		return res.next;
	}
	public static void display(Nodes head) {
		Nodes trav=head;
		
		while(trav!=null) {
			if(trav.coff==0&&trav.exp==0&&trav.next!=null)
			{
				trav=trav.next;
			}
			else
			{
			System.out.print(trav.coff+"x"+"^"+trav.exp);
			trav=trav.next;
			if(trav!=null) System.out.print(" + ");		
			}
		}
        System.out.println();
	}
	private static Nodes reverse(Nodes head) { 
		if(head.next==null || head==null) return head;
		Nodes newhead=reverse(head.next);
		Nodes nextNode=head.next;
		head.next=null;
		nextNode.next=head;
		return newhead;
	}
}	
