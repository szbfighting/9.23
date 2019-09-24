package www.com;

import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

public class Cheng {



	public static void main(String[] args) {
		String s = "123";
		String l = "1234";
		System.out.println(multiply(s, l));
	}
	
	public static String multiply(String num1, String num2) {
		ArrayList<String> list = new ArrayList<>();
		int f=0,r;
		StringBuilder result=new StringBuilder("");
		char[] num11 = num1.toCharArray();
		char[] num22 = num2.toCharArray();
		for(int i = num11.length-1;i>=0;i--){
			result.delete(0, result.length());
			for(int j = num22.length-1;j>=0;j--){
				r=(num11[i]-48)*(num22[j]-48)+f;
				f=r/10;
				r=r%10;
				result.append(r);	
				
			}
			result = result.reverse();
			for(int k = 0;k<num11.length-1-i;k++){
				result.append(0);
			}
			String s = result.toString();
			list.add(s);
		}
		/*for(String s:list){
			System.out.println(s);
		}*/
		
		String[] l = new String[list.size()];
		for(int j = 0;j<list.size();j++){
			l[j]=new String(list.get(j));
		}
		String sum=l[0];
		for(int i = 1;i<l.length;i++){
			
			sum=add(sum,l[i]);
			
		}
		
		
		return sum;
	
	}
	
	public static String add(String s,String l){
		s=new StringBuilder(s).reverse().toString();
		l=new StringBuilder(l).reverse().toString();
		char[] ss = s.toCharArray();
		char[] ll = l.toCharArray();
		
		int min;
		if(s.length()>l.length()){
			min = l.length();
		}else{
			min = s.length();
		}
		
		int r,f=0;
		StringBuilder result = new StringBuilder("");
		for(int i = 0;i<min;i++){
			r=ss[i]-48+ll[i]-48+f;
			f=r/10;
			r=r%10;
			result.append(r);
		}
		
		if(ss.length==min){
			for(int i = min;i<l.length();i++){
				
				r=ll[i]-48+f;
	
				f=r/10;
				r=r%10;
				result.append(r);
				
			}
			if(f>0){
				result.append(f);
			}
			
		}else if(ll.length==min){
			for(int i = min;i<s.length();i++){
				r=ss[i]-48+f;
				f=r/10;
				r=r%10;
				result.append(r);
				
			}
			if(f>0){
				result.append(f);
			}
		}
		
		return result.reverse().toString();
	}


}
