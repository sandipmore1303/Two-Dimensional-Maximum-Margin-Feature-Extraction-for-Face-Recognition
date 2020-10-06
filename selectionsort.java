/**
 * @(#)selectionsort.java
 *
 *
 * @author 
 * @version 1.00 2009/12/11
 */


public class selectionsort {
	 static int N=3 ;
     //static double [] x=new double[N];
     static double [] x={1,2,3};
     static int[] INDEX=new int[N];
     
    public selectionsort() {
    	
    }
    public static void main(String args[]) {
    	int i,j;
    	double  temp ;
    	
    	//x[] =i;
    	System.out.println("\nBefore Sort\n");
        for ( i = 0; i < N; i++)
        {
          INDEX[i]=i;
         System.out.println("  "+x[i]+"  "+INDEX[i]);
        }
       for ( i=0; i<x.length-1; i++) {
        int maxIndex = i;      // Index of largest remaining value.
        for (j=i+1; j<x.length; j++) {
            if (x[maxIndex] < x[j]) {
                maxIndex = j;  // Remember index of new minimum
            }
        }
       if (maxIndex != i)
        { 
            //...  Exchange current element with smallest remaining.
            temp = x[i];
            x[i] = x[maxIndex];
            x[maxIndex] = temp;
            INDEX[i]=maxIndex;
            INDEX[maxIndex]=i;
            
        }
      }
    
    
    
     System.out.println("\nAfter Sort\n");
        for ( i = 0; i < N; i++)
        {
         
         System.out.println("  "+x[i]+"  "+INDEX[i]);
        }
}
    
    
}