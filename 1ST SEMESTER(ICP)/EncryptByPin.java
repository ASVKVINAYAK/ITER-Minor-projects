//A S V K Vinayak
//1941012434
//CSE-P
import java.util.Scanner;
public class EncryptByPin {
	public static void main(String args[]) {
		//This part generates the key...
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the no of digit of each integer!");
		int n=sc.nextInt();
		System.out.println("Enter the 4 "+n+"-digt integers!");
		int a=sc.nextInt();
		int b=sc.nextInt();
		int c=sc.nextInt();
		int d=sc.nextInt();
		sc.nextLine();
		String key="";
		while(a!=0) {
			key=Math.min(Math.min(a%10,b%10),Math.min(c%10,d%10))+key;
			a/=10;
			b/=10;
			c/=10;
			d/=10;
		}
		System.out.println("The key generated is "+key);
		
		//This part encrypts the message...
		System.out.println("Enter the Message!");
		String m=sc.nextLine();
		m=m.replaceAll(" ","");
		m=m.toLowerCase();
		String e="";
		for(int x=0;x<m.length();x++) {
			int count=(m.charAt(x)+Integer.parseInt(key.substring(x%n,x%n+1)));
			if(count>122)
				// Here 97 is the ascii value of 'a'...
				e=e+(char)((count-122)+(97-1));
			else	
		    	e=e+(char)count;
		}
		
		//This part displays the encrypted message...
		System.out.println(e.toUpperCase());
	}
}

