import java.io.File;
import java.util.Arrays;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
//code will work for 92x112  images ..for testing 
 
//NOTE::::::::::::run as  java -Xms32m -Xmx128m MAIN
public class MAIN_bmp
{ public  static int nr,nc;
  public static BufferedImage image2,image3,image4;
  public  static int IMAGE_WIDTH,IMAGE_HEIGHT,NoofClasses=0,NoofSamples[],totalnooffiles=0;
  //m--->IMAGE_WIDTH,n--->IMAGE_HEIGHT
  //c--->NoofClasses,ni---->NoofSamples[i]  i.e. no of samples in class i
  //N--->totalnooffiles
  public static  File f3,f,f1,f2,f4;
  
  public static Matrix Rb0,Rb1,Rb2,Rb3;
  public static Matrix Rw0,Rw1,Rw2,Rw3;
  public static Matrix Cb0,Cb1,Cb2,Cb3;
  public static Matrix Cw0,Cw1,Cw2,Cw3; 
  public static Matrix Wmmc_r0,Wmmc_r1,Wmmc_r2,Wmmc_r3;
  public static Matrix Wmmc_c0,Wmmc_c1,Wmmc_c2,Wmmc_c3;
  public static Matrix Mi0,Mi1,Mi2,Mi3,M0,M1,M2,M3,T1,T2,T3,T4;  
  public static Matrix X0,X1,X2,X3;  
  public static  Matrix _2D2MMC0, _2D2MMC1, _2D2MMC2,_2D2MMC3;
  
  public static  double  Mi[][][][];
  public static  double M[][][];
  public static double TMi0[][];
  public static double TMi1[][];
  public static double TMi2[][];
  public static double TMi3[][];
  public static double TM0[][];
  public static double TM1[][];
  public static  double TM2[][];
  public static double TM3[][];
  public static double Aj0[][];
  public static double Aj1[][];
  public static double Aj2[][];
  public static double Aj3[][];
  public static double _1DMMC0[][];
  public static double _1DMMC1[][];
  public static double _1DMMC2[][];
  public static double _1DMMC3[][];
  public static double SMi0[][];
  public static double SMi1[][];
  public static double SMi2[][];
  public static double SMi3[][];
  public static double SM0[];
  public static double SM1[];
  public static double SM2[];
  public static double SM3[];
  public static double SAj0[][];
  public static double SAj1[][];
  public static double SAj2[][];
  public static double SAj3[][];
  
  public static Matrix Sb0,Sb1,Sb2,Sb3;
  public static Matrix Sw0,Sw1,Sw2,Sw3;
  
  
   
  public  static void main(String[] args)  throws Exception
  {
    initialize();
    step1_preparation();
    step2_2D2MMC();
    //step3_LDA();
  }   
  public  static void initialize() throws IOException
  {System.out.println("In step INITIALIZE");   
  
  f = new File("C:\\DB1");
  String[] DirinDir = f.list();
  Arrays.sort(DirinDir);
  
  for(int class_no=0;class_no<DirinDir.length;class_no++)
  { 	 
   f1 = new File("C:\\DB1\\"+DirinDir[class_no]); 
   if(f1.isDirectory()) 
   {NoofClasses++;
   }
   
  }
 System.out.println("Noofclasses="+NoofClasses);
  
  NoofSamples=new int[NoofClasses];
  //for(int i=0;i<NoofClasses;i++)
  //System.out.println(NoofSamples[i]);
  
  
  for(int class_no=0,current_class=0;class_no<DirinDir.length;class_no++)
  { 	 
   f1 = new File("C:\\DB1\\"+DirinDir[class_no]); 
   if(f1.isDirectory()) 
   {  
      String children[] = f1.list();  
      Arrays.sort(children);
      for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {
      	f2=new File("C:\\DB1\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
                	    
         	 	
        if(!children[NoofFiles].equals("Thumbs.db"))
         { totalnooffiles++;
           
          NoofSamples[current_class]++;
         }
      }
    current_class ++;
       
   }
  
  }
  
  //for(int i=0;i<NoofClasses;i++)
  //System.out.println(NoofSamples[i]);
  
       
   System.out.println("totalnooffiles:"+totalnooffiles);
   
   //Maths.sqrt
   //totalnooffiles-NoofClasses;
  
  nc=10;nr=4;   //nc=10;nr=4; for 92by112 databse 
  //nc=1;nr=1; //& nc=1;nr=1;  for 1by1 databse
   //nc=3;nr=2;
  
  
  f=new File("C:\\test.bmp");
  try{
  image2 = ImageIO.read(f); 
  } catch(IOException e){System.out.println("Cant read file C:\\test.bmp");}
  IMAGE_WIDTH=image2.getWidth();IMAGE_HEIGHT=image2.getHeight();
  Rb0=new Matrix(IMAGE_HEIGHT,IMAGE_HEIGHT);
  Rb1=new Matrix(IMAGE_HEIGHT,IMAGE_HEIGHT);
  Rb2=new Matrix(IMAGE_HEIGHT,IMAGE_HEIGHT);
  Rb3=new Matrix(IMAGE_HEIGHT,IMAGE_HEIGHT);
  Rw0=new Matrix(IMAGE_HEIGHT,IMAGE_HEIGHT);
  Rw1=new Matrix(IMAGE_HEIGHT,IMAGE_HEIGHT);
  Rw2=new Matrix(IMAGE_HEIGHT,IMAGE_HEIGHT);
  Rw3=new Matrix(IMAGE_HEIGHT,IMAGE_HEIGHT);
  Cb0=new Matrix(IMAGE_WIDTH,IMAGE_WIDTH);
  Cb1=new Matrix(IMAGE_WIDTH,IMAGE_WIDTH);
  Cb2=new Matrix(IMAGE_WIDTH,IMAGE_WIDTH);
  Cb3=new Matrix(IMAGE_WIDTH,IMAGE_WIDTH);
  Cw0=new Matrix(IMAGE_WIDTH,IMAGE_WIDTH);
  Cw1=new Matrix(IMAGE_WIDTH,IMAGE_WIDTH);
  Cw2=new Matrix(IMAGE_WIDTH,IMAGE_WIDTH);
  Cw3=new Matrix(IMAGE_WIDTH,IMAGE_WIDTH); 
  Wmmc_r0=new Matrix(IMAGE_HEIGHT,nr);//n x nr
  Wmmc_r1=new Matrix(IMAGE_HEIGHT,nr);
  Wmmc_r2=new Matrix(IMAGE_HEIGHT,nr);
  Wmmc_r3=new Matrix(IMAGE_HEIGHT,nr);
  Wmmc_c0=new Matrix(IMAGE_WIDTH,nc);//m x nc
  Wmmc_c1=new Matrix(IMAGE_WIDTH,nc);
  Wmmc_c2=new Matrix(IMAGE_WIDTH,nc);
  Wmmc_c3=new Matrix(IMAGE_WIDTH,nc);
  Mi=new double [IMAGE_WIDTH][IMAGE_HEIGHT][4][NoofClasses];
  M=new  double [IMAGE_WIDTH][IMAGE_HEIGHT][4];
  TMi0=new double[IMAGE_WIDTH][IMAGE_HEIGHT];
  TMi1=new double[IMAGE_WIDTH][IMAGE_HEIGHT];
  TMi2=new double[IMAGE_WIDTH][IMAGE_HEIGHT];
  TMi3=new double[IMAGE_WIDTH][IMAGE_HEIGHT];
  TM0=new double[IMAGE_WIDTH][IMAGE_HEIGHT];
  TM1=new double[IMAGE_WIDTH][IMAGE_HEIGHT];
  TM2=new double[IMAGE_WIDTH][IMAGE_HEIGHT];
  TM3=new double[IMAGE_WIDTH][IMAGE_HEIGHT];
  Aj0=new double[IMAGE_WIDTH][IMAGE_HEIGHT];
  Aj1=new double[IMAGE_WIDTH][IMAGE_HEIGHT];
  Aj2=new double[IMAGE_WIDTH][IMAGE_HEIGHT];
  Aj3=new double[IMAGE_WIDTH][IMAGE_HEIGHT];
  Mi0=new Matrix(IMAGE_WIDTH,IMAGE_HEIGHT);
  Mi1=new Matrix(IMAGE_WIDTH,IMAGE_HEIGHT);
  Mi2=new Matrix(IMAGE_WIDTH,IMAGE_HEIGHT);
  Mi3=new Matrix(IMAGE_WIDTH,IMAGE_HEIGHT);
  M0=new Matrix(IMAGE_WIDTH,IMAGE_HEIGHT);
  M1=new Matrix(IMAGE_WIDTH,IMAGE_HEIGHT);
  M2=new Matrix(IMAGE_WIDTH,IMAGE_HEIGHT);
  M3=new Matrix(IMAGE_WIDTH,IMAGE_HEIGHT);
  T1=new Matrix(IMAGE_WIDTH,IMAGE_HEIGHT);
  T2=new Matrix(IMAGE_HEIGHT,IMAGE_WIDTH);
  T3=new Matrix(IMAGE_HEIGHT,IMAGE_HEIGHT);
  T4=new Matrix(IMAGE_WIDTH,IMAGE_WIDTH);
  X0=new Matrix(IMAGE_WIDTH,IMAGE_HEIGHT);
  X1=new Matrix(IMAGE_WIDTH,IMAGE_HEIGHT);
  X2=new Matrix(IMAGE_WIDTH,IMAGE_HEIGHT);
  X3=new Matrix(IMAGE_WIDTH,IMAGE_HEIGHT);
  _2D2MMC0=new Matrix(nc,nr);
  _2D2MMC1=new Matrix(nc,nr);
  _2D2MMC2=new Matrix(nc,nr);
  _2D2MMC3=new Matrix(nc,nr); 
  _1DMMC0=new double[totalnooffiles][nc*nr];
  _1DMMC1=new double[totalnooffiles][nc*nr];
  _1DMMC2=new double[totalnooffiles][nc*nr];
  _1DMMC3=new double[totalnooffiles][nc*nr];
  SMi0=new double[nc*nr][NoofClasses];
  SMi1=new double[nc*nr][NoofClasses];
  SMi2=new double[nc*nr][NoofClasses];
  SMi3=new double[nc*nr][NoofClasses];
  SM0=new double[nc*nr];
  SM1=new double[nc*nr];
  SM2=new double[nc*nr];
  SM3=new double[nc*nr];
  SAj0=new double[1][nc*nr];
  SAj1=new double[1][nc*nr];
  SAj2=new double[1][nc*nr];
  SAj3=new double[1][nc*nr];
  Sb0=new Matrix(nr*nc,nr*nc);
  Sb1=new Matrix(nr*nc,nr*nc);
  Sb2=new Matrix(nr*nc,nr*nc);
  Sb3=new Matrix(nr*nc,nr*nc);
  Sw0=new Matrix(nr*nc,nr*nc);
  Sw1=new Matrix(nr*nc,nr*nc);
  Sw2=new Matrix(nr*nc,nr*nc);
  Sw3=new Matrix(nr*nc,nr*nc);

  


  
/*  for(i=0;i<totalnooffiles;i++)
  {_1DMMC0[i]=new double[nc*nr];
   _1DMMC1[i]=new double[nc*nr];
   _1DMMC2[i]=new double[nc*nr];
   _1DMMC3[i]=new double[nc*nr];
  }
  */
  }  
  public static void step1_preparation() throws IOException
   {
   System.out.println("In Step 1 (Preparation):\n");
   	
    step1_preparation_Compute_Mi();
    step1_preparation_Compute_M();
    step1_preparation_Compute_Rb_Cb();
    step1_preparation_Compute_Rw_Cw();
    
    
    
   
   }   
  public static void step1_preparation_Compute_Mi() throws IOException{
	
//File f,f1,f2;
int i=0,j=0,red,green,blue,alpha,rgb;

System.out.println("In step1_preparation_Compute_Mi\n");
f = new File("C:\\DB1");
String[] DirinDir = f.list();
Arrays.sort(DirinDir);
//process all directories to compute Mi[][][][] for given class_no
    
for(int class_no=0,current_class=0;class_no<DirinDir.length;class_no++)
  { 	 
   f1 = new File("C:\\DB1\\"+DirinDir[class_no]); 
   if(f1.isDirectory())
     {
     //System.out.println("\nDirectry Name:"+"C:\\DB1\\"+DirinDir[class_no]+"\n"); 
     String children[] = f1.list();
     Arrays.sort(children);
          
     //process all files in current_class th folder   
             
      for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {
        //get next image2 & add    
       	f2=new File("C:\\DB1\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
                	    
        try{
        	image2 = ImageIO.read(f2);
        	} catch(IOException e){System.out.println("Can't read File:"+"C:\\DB1\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);}
        //System.out.println("File Name:"+"C:\\DB1\\"+DirinDir[class_no]+"\\"+children[NoofFiles]); 	
        	  
        	 	
        //find Mi for class class_no  
        	 	
        if(!children[NoofFiles].equals("Thumbs.db"))
         {
         
          for(i=0;i<IMAGE_WIDTH;i++)
          {
          for(j=0;j<IMAGE_HEIGHT;j++)
          {
           rgb = image2.getRGB(i, j); 
           alpha = ((rgb >> 24) & 0xff); 
           red = ((rgb >> 16) & 0xff); 
           green =((rgb >> 8) & 0xff); 
           blue =((rgb ) & 0xff); 
            try  
             {	                                           
        	      Mi[i][j][0][current_class] += (alpha/255.0)*(1.0); 
                  Mi[i][j][1][current_class] += (red/255.0)*(1.0); 
                  Mi[i][j][2][current_class] +=(green/255.0)*(1.0); 
                  Mi[i][j][3][current_class] +=(blue/255.0)*(1.0); 
                  //System.out.println(" i="+i+" j= "+j+"current_class="+current_class); 
                  //System.out.println(Mi[i][j][0][current_class]+" "+Mi[i][j][1][current_class]+" "+Mi[i][j][2][current_class]+" "+Mi[i][j][3][current_class]);
             }  catch(ArrayIndexOutOfBoundsException e){ } 
          }
         }      	  	 
        }    
            
    }//end of file procesng for current class 
      
       int count=0;
        //find average/mean Mi
       for(i=0;i<IMAGE_WIDTH;i++)
        {	 	   
       for(j=0;j<IMAGE_HEIGHT;j++)
        	 {
        	 		
        	      Mi[i][j][0][current_class] /= NoofSamples[current_class]*1.0;
                 // Mi[i][j][1][current_class] /= NoofSamples[current_class]*1.0;
                  //Mi[i][j][2][current_class] /= NoofSamples[current_class]*1.0;
                  //Mi[i][j][3][current_class] /= NoofSamples[current_class]*1.0;
                  
                  System.out.println(" i="+i+" j= "+j+"current_class="+current_class); 
                  System.out.println(Mi[i][j][0][current_class]+" "+Mi[i][j][1][current_class]+" "+Mi[i][j][2][current_class]+" "+Mi[i][j][3][current_class]);
              }	
              
        }     
    
              
        current_class++;
     }	//end if
   }//end of  procesng for all classes
}        
  public static void step1_preparation_Compute_M() throws IOException
  {    System.out.println("In step1_preparation_Compute_M:\n");
  	int i=0,j=0,class_no=0;
        for(class_no=0;class_no<NoofClasses;class_no++)
  	for(i=0;i<IMAGE_WIDTH;i++)
        for(j=0;j<IMAGE_HEIGHT;j++)
         {  
          M[i][j][0]   += Mi[i][j][0][class_no];
          M[i][j][1]   += Mi[i][j][1][class_no];
          M[i][j][2]   += Mi[i][j][2][class_no];
          M[i][j][3]   += Mi[i][j][3][class_no];
          //System.out.println("In compute M");
          //System.out.println(" i="+i+" j= "+j+"class_no="+class_no); 
          //System.out.println(" "+M[i][j][0]+" "+M[i][j][1]+" "+M[i][j][2]+" "+M[i][j][3]);
         }
  	
  	for(i=0;i<IMAGE_WIDTH;i++)
        for(j=0;j<IMAGE_HEIGHT;j++)
         {  
          M[i][j][0]   /= NoofClasses;
          M[i][j][1]   /= NoofClasses;
          M[i][j][2]   /= NoofClasses;
          M[i][j][3]   /= NoofClasses;
         // System.out.println("In compute M");
          //System.out.println(" i="+i+" j= "+j+"class_no="+class_no); 
         // System.out.println(" "+M[i][j][0]+" "+M[i][j][1]+" "+M[i][j][2]+" "+M[i][j][3]);
         }
         
         
    }  
  public static void step1_preparation_Compute_Rb_Cb() throws IOException
  {
   System.out.println("In step1_preparation_Compute_Rb_Cb:\n");
   
   int i=0,j=0,class_no=0;
   for(i=0;i<IMAGE_WIDTH;i++)
        for(j=0;j<IMAGE_HEIGHT;j++)
         {  
          TM0[i][j] = M[i][j][0];
          TM1[i][j] = M[i][j][1];
          TM2[i][j] = M[i][j][2];
          TM3[i][j] = M[i][j][3];
                   
           
          //System.out.println(" i="+i+" j= "+j+"class_no="+class_no); 
          //System.out.println(" "+M[i][j][0]+" "+M[i][j][1]+" "+M[i][j][2]+" "+M[i][j][3]);
         }
   
   M0=new Matrix(TM0);
   M1=new Matrix(TM1);
   M2=new Matrix(TM2);
   M3=new Matrix(TM3);
   
  	  
   for(class_no=0;class_no<NoofClasses;class_no++)
      {//step1: compute Mi[][][][class
       for(i=0;i<IMAGE_WIDTH;i++)
        for(j=0;j<IMAGE_HEIGHT;j++)
         {  
          TMi0[i][j] = Mi[i][j][0][class_no];
          TMi1[i][j] = Mi[i][j][1][class_no];
          TMi2[i][j] = Mi[i][j][2][class_no];   
          TMi3[i][j] = Mi[i][j][3][class_no];
          }
       Mi0=new Matrix(TMi0);
       //Mi0.print(5,5);
       Mi1=new Matrix(TMi1);
       //Mi1.print(5,5);
       Mi2=new Matrix(TMi2);
       Mi3=new Matrix(TMi3);
       //Mi3.print(5,5);
       
       //Rb0=Mi0.minus(M0).transpose().times(Mi0.minus(M0));
       T1=Mi0.minus(M0);
       //T1.print(5,5);
       T2=T1.transpose();
       //T2.print(5,5);
       T3=T2.times(T1);
       //T1.print(5,5);
       T3=T3.timesEquals(1.0*NoofSamples[class_no]); 
       //T1.print(5,5);
       Rb0=Rb0.plusEquals(T3);
       //T1.print(5,5);
       T4=T1.times(T2);
       //T1.print(5,5);
       T4=T4.timesEquals(1.0*NoofSamples[class_no]); 
       	//T1.print(5,5);
       Cb0=Cb0.plusEquals(T4);
       //T1.print(5,5);
       //System.out.println("T4 rows="+T4.getRowDimension()+"T4 columns="+T4.getColumnDimension());
       
       
       
       T1=Mi1.minus(M1);
       T2=T1.transpose();
       T3=T2.times(T1);
       T3=T3.timesEquals(1.0*NoofSamples[class_no]); 
       Rb1=Rb1.plusEquals(T3);
       T4=T1.times(T2);
       T4=T4.timesEquals(1.0*NoofSamples[class_no]); 
       Cb1=Cb1.plusEquals(T4);
       
       
       T1=Mi2.minus(M2);
       T2=T1.transpose();
       T3=T2.times(T1);
       T3=T3.timesEquals(1.0*NoofSamples[class_no]); 
       Rb2=Rb2.plusEquals(T3);
       T4=T1.times(T2);
       T4=T4.timesEquals(1.0*NoofSamples[class_no]); 
       Cb2=Cb2.plusEquals(T4);
       
       
       T1=Mi3.minus(M3);
       T2=T1.transpose();
       T3=T2.times(T1);
       T3=T3.timesEquals(1.0*NoofSamples[class_no]); 
       Rb3=Rb3.plusEquals(T3);       
       T4=T1.times(T2);
       T4=T4.timesEquals(1.0*NoofSamples[class_no]); 
       Cb3=Cb3.plusEquals(T4);
       
       //System.out.println(" i="+i+" j= "+j+"class_no="+class_no); 
       //System.out.println("Rb3=");
       //Rb3.print(6,5);   
     }
   
     Rb0=Rb0.timesEquals(1.0/totalnooffiles);    
     Rb1=Rb1.timesEquals(1.0/totalnooffiles);   
     Rb2=Rb2.timesEquals(1.0/totalnooffiles);   
     Rb3=Rb3.timesEquals(1.0/totalnooffiles);  
         
     Cb0=Cb0.timesEquals(1.0/totalnooffiles);    
     Cb1=Cb1.timesEquals(1.0/totalnooffiles);   
     Cb2=Cb2.timesEquals(1.0/totalnooffiles);   
     Cb3=Cb3.timesEquals(1.0/totalnooffiles);  
     
 /*System.out.println("Rb0=");
 Rb0.print(4,4);
 System.out.println("Rb1=");
 Rb1.print(4,4);
 System.out.println("Rb2=");
 Rb2.print(4,4);
 System.out.println("Rb3=");
 Rb3.print(5,5);    
     
  
 System.out.println("Cb0=");
 Cb0.print(4,4);   
 System.out.println("Cb1=");
 Cb1.print(4,4);
 System.out.println("Cb2=");
 Cb2.print(5,5);    
 System.out.println("Cb3=");
 Cb3.print(4,4);*/
  }
  public static void step1_preparation_Compute_Rw_Cw() throws IOException
 {
File f,f1,f2;
int i=0,j=0,red,green,blue,alpha,rgb;

System.out.println("In step1_preparation_Compute_Rw_Cw\n");
f = new File("C:\\DB1");
String[] DirinDir = f.list();
Arrays.sort(DirinDir);
//process all directories to compute Mi[][][][] for given class_no
    
for(int class_no=0,current_class=0;class_no<DirinDir.length && current_class<NoofClasses;class_no++)
  { 
  for(i=0;i<IMAGE_WIDTH;i++)
  for(j=0;j<IMAGE_HEIGHT;j++)
  {TMi0[i][j] = Mi[i][j][0][current_class];
   TMi1[i][j] = Mi[i][j][1][current_class];
   TMi2[i][j] = Mi[i][j][2][current_class];
   TMi3[i][j] = Mi[i][j][3][current_class];
   }
   Mi0=new Matrix(TMi0);
   Mi1=new Matrix(TMi1);
   Mi2=new Matrix(TMi2);
   Mi3=new Matrix(TMi3);
   //Mi3.print(5,5);
       
   f1 = new File("C:\\DB1\\"+DirinDir[class_no]); 
   if(f1.isDirectory())
     {
     //System.out.println("\nDirectry Name:"+"C:\\DB1\\"+DirinDir[class_no]+"\n"); 
     String children[] = f1.list();
     Arrays.sort(children);
          
     //process all files in class_no th folder   
             
     for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {      
        /*for(i=0;i<IMAGE_WIDTH;i++)
        for(j=0;j<IMAGE_HEIGHT;j++)
         {  
          Aj0[i][j] = 0.0;
          Aj1[i][j] = 0.0;
          Aj2[i][j] = 0.0;
          Aj3[i][j] = 0.0;
         }*/
         
          
        //get next image2 & add    
       	f2=new File("C:\\DB1\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
                	    
        try{
        	image2 = ImageIO.read(f2);
        	} catch(IOException e){System.out.println("Can't read File:"+"C:\\DB1\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);}
        //System.out.println("File Name:"+"C:\\DB1\\"+DirinDir[class_no]+"\\"+children[NoofFiles]); 	
        	  
        	 	
        //find Mi for class class_no  
        	 	
       if(!children[NoofFiles].equals("Thumbs.db"))
         {        
          for(i=0;i<IMAGE_WIDTH;i++)
          for(j=0;j<IMAGE_HEIGHT;j++)
          {
           rgb = image2.getRGB(i, j); 
           alpha = ((rgb >> 24) & 0xff); 
           red = ((rgb >> 16) & 0xff); 
           green =((rgb >> 8) & 0xff); 
           blue =((rgb ) & 0xff); 
            try  {	                                           
        	      Aj0[i][j] = (alpha/255.0)*(1.0); 
                  Aj1[i][j] = (red/255.0)*(1.0); 
                  Aj2[i][j] =(green/255.0)*(1.0); 
                  Aj3[i][j] =(blue/255.0)*(1.0); 
                  //System.out.println(" i="+i+" j= "+j+"class_no="+class_no); 
                  //System.out.println(Mi[i][j][0][class_no]+" "+Mi[i][j][1][class_no]+" "+Mi[i][j][2][class_no]+" "+Mi[i][j][3][class_no]);
                  }  catch(ArrayIndexOutOfBoundsException e){ } 
          }   
          
           M0=new Matrix(Aj0);
           M1=new Matrix(Aj1);
           M2=new Matrix(Aj2);
           M3=new Matrix(Aj3);
     
       
          T1=Mi0.minus(M0);
          T2=T1.transpose();
          T3=T2.times(T1);
          Rw0=Rw0.plusEquals(T3);
          T4=T1.times(T2);
          Cw0=Cw0.plusEquals(T4);
       //System.out.println("T4 rows="+T4.getRowDimension()+"T4 columns="+T4.getColumnDimension());
       
       
       
       T1=Mi1.minus(M1);
       T2=T1.transpose();
       T3=T2.times(T1);
       Rw1=Rw1.plusEquals(T3);
       T4=T1.times(T2);
       Cw1=Cw1.plusEquals(T4);
       
       
       T1=Mi2.minus(M2);
       T2=T1.transpose();
       T3=T2.times(T1);
       Rw2=Rw2.plusEquals(T3);
       T4=T1.times(T2);
       Cw2=Cw2.plusEquals(T4);
       
       
       T1=Mi3.minus(M3);
       //System.out.println("classno="+current_class);
       //T1.print(5,5);
       T2=T1.transpose();
       T3=T2.times(T1);
       Rw3=Rw3.plusEquals(T3);       
       T4=T1.times(T2);
       Cw3=Cw3.plusEquals(T4);  
       //System.out.println("Rw3=");
      // Rw3.print(5,5);
       //System.out.println("Cw3=");
      // Cw3.print(5,5);
       }    
            
    }//end of file procesng for current class 
     
     current_class++;
   
     
     }	//end if
   }//end of  procesng for all classes  


      
      
      
     Rw0=Rw0.timesEquals(1.0/totalnooffiles);    
     Rw1=Rw1.timesEquals(1.0/totalnooffiles);   
     Rw2=Rw2.timesEquals(1.0/totalnooffiles);   
     Rw3=Rw3.timesEquals(1.0/totalnooffiles);  
         
     Cw0=Cw0.timesEquals(1.0/totalnooffiles);    
     Cw1=Cw1.timesEquals(1.0/totalnooffiles);   
     Cw2=Cw2.timesEquals(1.0/totalnooffiles);   
     Cw3=Cw3.timesEquals(1.0/totalnooffiles);    
      
     
     
    
 /*System.out.println("Rw0=");
 Rw0.print(4,4);
 System.out.println("Rw1=");
 Rw1.print(4,4);
 System.out.println("Rw2=");
 Rw2.print(4,4);
 System.out.println("Rw3=");
 Rw3.print(5,5);    
     
  
 System.out.println("Cw0=");
 Cw0.print(4,4);   
 System.out.println("Cw1=");
 Cw1.print(4,4);
 System.out.println("Cw2=");
 Cw2.print(5,5);    
 System.out.println("Cw3=");
 Cw.print(4,4);   */
      
  }  
  public static void step2_2D2MMC() throws IOException
{
    step2_2D2MMC_compute_Wmmc_r();
    step2_2D2MMC_compute_Wmmc_c();
    step2_2D2MMC_ReduceDimension(); 
   	
   	
   }
  public static void step2_2D2MMC_compute_Wmmc_r(){
    //find eigen vectors of Rb-Rw n X n i e HEGHT x HEIGHT
    
    
    
    System.out.println("In step2_2D2MMC_compute_Wmmc_r()\n");
    
    Matrix R0=Rb0.minus(Rw0);
    Matrix R1=Rb1.minus(Rw1);
    Matrix R2=Rb2.minus(Rw2);
    Matrix R3=Rb0.minus(Rw3);
    
    EigenvalueDecomposition Eig0 = R0.eig();
    Matrix D0 = Eig0.getD();
    Matrix V0 = Eig0.getV();
    
    EigenvalueDecomposition Eig1 = R1.eig();
    Matrix D1 = Eig1.getD();
    Matrix V1 = Eig1.getV();
    
    EigenvalueDecomposition Eig2 = R2.eig();
    Matrix D2 = Eig2.getD();
    Matrix V2 = Eig2.getV();
    
    EigenvalueDecomposition Eig3 = R3.eig();
    Matrix D3 = Eig3.getD();
    Matrix V3 = Eig3.getV();
    
    //V2.print(4,4);
    
    
    
    //find max (Tr(Rb-Rw)/n, 0) 
    
    
    double MAX0=R0.trace();
    double MAX1=R1.trace();
    double MAX2=R2.trace();
    double MAX3=R3.trace();
  //  System.out.println("MAX2="+MAX2);
    MAX0 =MAX0/(IMAGE_HEIGHT*1.0);
    MAX0 =(MAX0>0)?(MAX0):(0);
    MAX1 =MAX1/(IMAGE_HEIGHT*1.0);
    MAX1 =(MAX1>0)?(MAX1):(0);
    MAX2 =MAX2/(IMAGE_HEIGHT*1.0);
    MAX2 =(MAX2>0)?(MAX2):(0);
    MAX3 =MAX3/(IMAGE_HEIGHT*1.0);
    MAX3 =(MAX3>0)?(MAX3):(0);
    
    
    //for a given eigen vector wi find its dicriminant value as defined in condition 24 in paper & put it in x[i] array
    double x0[]=new double[IMAGE_HEIGHT];
    double x1[]=new double[IMAGE_HEIGHT];
    double x2[]=new double[IMAGE_HEIGHT];
    double x3[]=new double[IMAGE_HEIGHT];
    Matrix wi0=new Matrix(IMAGE_HEIGHT,1);
    Matrix wi1=new Matrix(IMAGE_HEIGHT,1);
    Matrix wi2=new Matrix(IMAGE_HEIGHT,1);
    Matrix wi3=new Matrix(IMAGE_HEIGHT,1);
    Matrix t0,t1,t2,t3;
    int i=0,j=0;
    int c[]=new int[IMAGE_HEIGHT];
    int INDEX0[]=new int[IMAGE_HEIGHT];
    int INDEX1[]=new int[IMAGE_HEIGHT];
    int INDEX2[]=new int[IMAGE_HEIGHT];
    int INDEX3[]=new int[IMAGE_HEIGHT];
    
    for(j=0;j<IMAGE_HEIGHT;j++)
         {c[j]=j;  
         }
    
    
    for(i=0;i<IMAGE_HEIGHT;i++)
    {
        //get i th eigen vector from V into wi
        wi0=V0.getMatrix(c,i,i);
        wi1=V1.getMatrix(c,i,i);
        wi2=V2.getMatrix(c,i,i);
        wi3=V3.getMatrix(c,i,i);
        //find wi s discriminant value & store in x[i]
        t0=wi0.transpose().times(R0).times(wi0);
        t1=wi1.transpose().times(R1).times(wi1);
        t2=wi2.transpose().times(R2).times(wi2);
        t3=wi3.transpose().times(R3).times(wi3);
        x0[i]=t0.get(0,0);   
        x1[i]=t1.get(0,0);   
        x2[i]=t2.get(0,0);   
        x3[i]=t3.get(0,0);   
        
        
    }
    
    
        
    
    
    //sort array x & store the corresponding original indices in matrix INDEX i.e. INDEX[i]=index of x[i] in original x array 
       
    	double  temp_double ;
        int temp_int;
        int maxIndex;
    	
    	//x[] =i;
    	
        for ( i = 0; i < IMAGE_HEIGHT; i++)
        {
          INDEX0[i]=i;
          INDEX1[i]=i;
          INDEX2[i]=i;
          INDEX3[i]=i;
         }
        // System.out.println("\nBefore Sort\n");
        
        for ( i = 0; i < IMAGE_HEIGHT; i++)
        {  
         //System.out.println("  "+x2[i]+"  "+INDEX2[i]);
        //System.out.println("X0="+x0[i]+"INDEX0="+INDEX0[i]+"X1="+x1[i]+"INDEX1="+INDEX1[i]+"X2="+x2[i]+"INDEX2="+INDEX2[i]);
        
        }
            
        
         for ( i=0; i<x0.length-1; i++)
          {   
            maxIndex = i;      // Index of largest remaining value.
           for (j=i+1; j<x0.length; j++) 
           {
            if (x0[maxIndex] < x0[j]) {
                maxIndex = j;  // Remember index of new minimum
            }
           }
           if (maxIndex != i)
           { 
            //...  Exchange current element with smallest remaining.
            temp_double = x0[i];
            x0[i] = x0[maxIndex];
            x0[maxIndex] = temp_double;
            temp_int=INDEX0[i];
            INDEX0[i]=INDEX0[maxIndex];
            INDEX0[maxIndex]=temp_int;
            
           }
            
      }
    
        
       for ( i=0; i<x1.length-1; i++)
          {   
            maxIndex = i;      // Index of largest remaining value.
           for (j=i+1; j<x1.length; j++) 
           {
            if (x1[maxIndex] < x1[j]) {
                maxIndex = j;  // Remember index of new minimum
            }
           }
           if (maxIndex != i)
           { 
            //...  Exchange current element with smallest remaining.
            temp_double = x1[i];
            x1[i] = x1[maxIndex];
            x1[maxIndex] = temp_double;
            temp_int=INDEX1[i];
            INDEX1[i]=INDEX1[maxIndex];
            INDEX1[maxIndex]=temp_int;
            
           }
             
      }        
        
        
       for ( i=0; i<x2.length-1; i++)
          {   
           maxIndex = i;      // Index of largest remaining value.
           for (j=i+1; j<x2.length; j++) 
           {
            if (x2[maxIndex] < x2[j]) {
                maxIndex = j;  // Remember index of new minimum
            }
           }
           if (maxIndex != i)
           { 
            //...  Exchange current element with smallest remaining.
            temp_double = x2[i];
            x2[i] = x2[maxIndex];
            x2[maxIndex] = temp_double;
            temp_int=INDEX2[i];
            INDEX2[i]=INDEX2[maxIndex];
            INDEX2[maxIndex]=temp_int;
            
           }
           
             
      }   
  

       for ( i=0; i<x3.length-1; i++)
          {   
           maxIndex = i;      // Index of largest remaining value.
           for (j=i+1; j<x3.length; j++) 
           {
            if (x3[maxIndex] < x3[j]) {
                maxIndex = j;  // Remember index of new minimum
            }
           }
           if (maxIndex != i)
           { 
            //...  Exchange current element with smallest remaining.
            temp_double = x3[i];
            x3[i] = x3[maxIndex];
            x3[maxIndex] = temp_double;
            temp_int=INDEX3[i];
            INDEX3[i]=INDEX3[maxIndex];
            INDEX3[maxIndex]=temp_int;
            
           }           
             
      }             
    
    
     //System.out.println("\nAfter Sort\n");
       /* for ( i = 0; i < IMAGE_HEIGHT; i++)
        {
        
        System.out.println("  "+x2[i]+"  "+INDEX2[i]);
        if(x2[i]>MAX2)
        System.out.println(" Conditon stisfieddd i="+i+"MAX2="+MAX2);
        else 
        System.out.println(" Conditon nooooot stisfieddd i="+i+"MAX2="+MAX2);
       
        
        }*/
  
    
    //using INDEX array form the Wmmc_r by taking first nr eigen vectors 
    //corresponding to first nr values of x array
         
    for(i=0;i<nr;i++)
    {//get i th column vector from V 
     wi0=V0.getMatrix(c,INDEX0[i],INDEX0[i]);
     //set it as a column vector at a position INDEX[i] of Wmmc_r
     Wmmc_r0.setMatrix(c,i,i,wi0);
     
     wi1=V1.getMatrix(c,INDEX1[i],INDEX1[i]);
     Wmmc_r1.setMatrix(c,i,i,wi1);
     
     wi2=V2.getMatrix(c,INDEX2[i],INDEX2[i]);
     Wmmc_r2.setMatrix(c,i,i,wi2);
     //wi2.print(4,4);
     //System.out.println("Index of wi2="+INDEX2[i]);
     
     wi3=V3.getMatrix(c,INDEX3[i],INDEX3[i]);
     Wmmc_r3.setMatrix(c,i,i,wi3);
    }
   
   
/* System.out.println("Wmmc_r0=");
 Wmmc_r0.print(4,4);
 System.out.println("Wmmc_r1=");
 Wmmc_r1.print(4,4);
 System.out.println("Wmmc_r2=");
 Wmmc_r2.print(4,4);
 System.out.println("Wmmc_r3=");
 Wmmc_r3.print(4,4); */
     
  
 
    
    
    
 }
  public static void step2_2D2MMC_compute_Wmmc_c(){

     System.out.println("In step2_2D2MMC_compute_Wmmc_c())\n");
    //find eigen vectors of Cb-Cw n X n i e HEGHT x HEIGHT
    
    
    Matrix C0=Cb0.minus(Cw0);
    Matrix C1=Cb1.minus(Cw1);
    Matrix C2=Cb2.minus(Cw2);
    Matrix C3=Cb0.minus(Cw3);
    
    EigenvalueDecomposition Eig0 = C0.eig();
    Matrix D0 = Eig0.getD();
    Matrix V0 = Eig0.getV();
    
    EigenvalueDecomposition Eig1 = C1.eig();
    Matrix D1 = Eig1.getD();
    Matrix V1 = Eig1.getV();
    
    EigenvalueDecomposition Eig2 = C2.eig();
    Matrix D2 = Eig2.getD();
    Matrix V2 = Eig2.getV();
    
    EigenvalueDecomposition Eig3 = C3.eig();
    Matrix D3 = Eig3.getD();
    Matrix V3 = Eig3.getV();
    
    //V2.print(4,4);
    
    
    
    //find max (Tr(Cb-Cw)/n, 0) 
    
    
    double MAX0=C0.trace();
    double MAX1=C1.trace();
    double MAX2=C2.trace();
    double MAX3=C3.trace();
    MAX0 =MAX0/(IMAGE_WIDTH*1.0);
    MAX0 =(MAX0>0)?(MAX0):(0);
    MAX1 =MAX1/(IMAGE_WIDTH*1.0);
    MAX1 =(MAX1>0)?(MAX1):(0);
    MAX2 =MAX2/(IMAGE_WIDTH*1.0);
    MAX2 =(MAX2>0)?(MAX0):(0);
    MAX3 =MAX3/(IMAGE_WIDTH*1.0);
    MAX3 =(MAX3>0)?(MAX3):(0);
    
    
    //for a given eigen vector wi find its dicriminant value as defined in condition 24 in paper & put it in x[i] array
    double x0[]=new double[IMAGE_WIDTH];
    double x1[]=new double[IMAGE_WIDTH];
    double x2[]=new double[IMAGE_WIDTH];
    double x3[]=new double[IMAGE_WIDTH];
    Matrix wi0=new Matrix(IMAGE_WIDTH,1);
    Matrix wi1=new Matrix(IMAGE_WIDTH,1);
    Matrix wi2=new Matrix(IMAGE_WIDTH,1);
    Matrix wi3=new Matrix(IMAGE_WIDTH,1);
    Matrix t0,t1,t2,t3;
    int i=0,j=0;
    int c[]=new int[IMAGE_WIDTH];
    int INDEX0[]=new int[IMAGE_WIDTH];
    int INDEX1[]=new int[IMAGE_WIDTH];
    int INDEX2[]=new int[IMAGE_WIDTH];
    int INDEX3[]=new int[IMAGE_WIDTH];
    
    for(j=0;j<IMAGE_WIDTH;j++)
         {c[j]=j;  
         }
    
    
    for(i=0;i<IMAGE_WIDTH;i++)
    {
        //get i th eigen vector from V into wi
        wi0=V0.getMatrix(c,i,i);
        wi1=V1.getMatrix(c,i,i);
        wi2=V2.getMatrix(c,i,i);
        wi3=V3.getMatrix(c,i,i);
        //find wi s discriminant value & store in x[i]
        t0=wi0.transpose().times(C0).times(wi0);
        t1=wi1.transpose().times(C1).times(wi1);
        t2=wi2.transpose().times(C2).times(wi2);
        t3=wi3.transpose().times(C3).times(wi3);
        //t0.print(6,5);
        x0[i]=t0.get(0,0);   
        x1[i]=t1.get(0,0);   
        x2[i]=t2.get(0,0);   
        x3[i]=t3.get(0,0);   
        
        
    }
    
    
        
    
    
    //sort array x & store the corresponding original indices in matrix INDEX i.e. INDEX[i]=index of x[i] in original x array 
       
    	double  temp_double ;
        int temp_int;
        int maxIndex;
    	
    	//x[] =i;
    	
        for ( i = 0; i < IMAGE_WIDTH; i++)
        {
          INDEX0[i]=i;
          INDEX1[i]=i;
          INDEX2[i]=i;
          INDEX3[i]=i;
         }
         //System.out.println("\nBefore Sort\n");
        
        for ( i = 0; i < IMAGE_WIDTH; i++)
        {  
         //System.out.println("  "+x2[i]+"  "+INDEX2[i]);
        //System.out.println("X0="+x0[i]+"INDEX0="+INDEX0[i]+"X1="+x1[i]+"INDEX1="+INDEX1[i]+"X2="+x2[i]+"INDEX2="+INDEX2[i]);
        
        }
            
        
         for ( i=0; i<x0.length-1; i++)
          {   
            maxIndex = i;      // Index of largest remaining value.
           for (j=i+1; j<x0.length; j++) 
           {
            if (x0[maxIndex] < x0[j]) {
                maxIndex = j;  // Remember index of new minimum
            }
           }
           if (maxIndex != i)
           { 
            //...  Exchange current element with smallest remaining.
            temp_double = x0[i];
            x0[i] = x0[maxIndex];
            x0[maxIndex] = temp_double;
            temp_int=INDEX0[i];
            INDEX0[i]=INDEX0[maxIndex];
            INDEX0[maxIndex]=temp_int;
            
           }
            
      }
    
        
       for ( i=0; i<x1.length-1; i++)
          {   
            maxIndex = i;      // Index of largest remaining value.
           for (j=i+1; j<x1.length; j++) 
           {
            if (x1[maxIndex] < x1[j]) {
                maxIndex = j;  // Remember index of new minimum
            }
           }
           if (maxIndex != i)
           { 
            //...  Exchange current element with smallest remaining.
            temp_double = x1[i];
            x1[i] = x1[maxIndex];
            x1[maxIndex] = temp_double;
            temp_int=INDEX1[i];
            INDEX1[i]=INDEX1[maxIndex];
            INDEX1[maxIndex]=temp_int;
            
           }
             
      }        
        
        
       for ( i=0; i<x2.length-1; i++)
          {   
           maxIndex = i;      // Index of largest remaining value.
           for (j=i+1; j<x2.length; j++) 
           {
            if (x2[maxIndex] < x2[j]) {
                maxIndex = j;  // Remember index of new minimum
            }
           }
           if (maxIndex != i)
           { 
            //...  Exchange current element with smallest remaining.
            temp_double = x2[i];
            x2[i] = x2[maxIndex];
            x2[maxIndex] = temp_double;
            temp_int=INDEX2[i];
            INDEX2[i]=INDEX2[maxIndex];
            INDEX2[maxIndex]=temp_int;
            
           }
           
             
      }   
  

       for ( i=0; i<x3.length-1; i++)
          {   
           maxIndex = i;      // Index of largest remaining value.
           for (j=i+1; j<x3.length; j++) 
           {
            if (x3[maxIndex] < x3[j]) {
                maxIndex = j;  // Remember index of new minimum
            }
           }
           if (maxIndex != i)
           { 
            //...  Exchange current element with smallest remaining.
            temp_double = x3[i];
            x3[i] = x3[maxIndex];
            x3[maxIndex] = temp_double;
            temp_int=INDEX3[i];
            INDEX3[i]=INDEX3[maxIndex];
            INDEX3[maxIndex]=temp_int;
            
           }           
             
      }             
    
    
     //System.out.println("\nAfter Sort\n");
        for ( i = 0; i < IMAGE_WIDTH; i++)
        {
         
         //System.out.println("  "+x2[i]+"  "+INDEX2[i]);
        }
  
    
    //using INDEX array form the Wmmc_r by taking first nc eigen vectors corresponding to first nc values of x array
    //of x array
     
    for(i=0;i<nc;i++)
    {//get i th column vector from V 
     wi0=V0.getMatrix(c,INDEX0[i],INDEX0[i]);
     //set it as a column vector at a position INDEX[i] of Wmmc_c
     Wmmc_c0.setMatrix(c,i,i,wi0);
     
     wi1=V1.getMatrix(c,INDEX1[i],INDEX1[i]);
     Wmmc_c1.setMatrix(c,i,i,wi1);
     
     wi2=V2.getMatrix(c,INDEX2[i],INDEX2[i]);
     Wmmc_c2.setMatrix(c,i,i,wi2);
     //wi2.print(4,4);
     //System.out.println("Index of wi2="+INDEX2[i]);
     
     wi3=V3.getMatrix(c,INDEX3[i],INDEX3[i]);
     Wmmc_c3.setMatrix(c,i,i,wi3);
    }
          
     
    
 /*System.out.println("Wmmc_c0=");
 Wmmc_c0.print(4,4);
 System.out.println("Wmmc_c1=");
 Wmmc_c1.print(4,4);
 System.out.println("Wmmc_c2=");
 Wmmc_c2.print(4,4);
 System.out.println("Wmmc_c3=");
 Wmmc_c3.print(4,4); */
     
        
}  
public static void step2_2D2MMC_ReduceDimension() throws IOException
 {//applying the projectors WMMC_R and WMMC_C, the dimensionality reduction of face image2
//can be performed WMMC_R?WMMC_C : X ? Rm×n -?WtMMC_C X WMMC_R ? Rnc×nr
 System.out.println("In step2_2D2MMC_ReduceDimension()\n");    
int i=0,j=0,red,green,blue,alpha,rgb,fileno=0;
f = new File("C:\\DB1");
String[] DirinDir = f.list();
Arrays.sort(DirinDir);
//process all directories to compute Mi[][][][] for given class_no
    
for(int class_no=0;class_no<DirinDir.length;class_no++)
  { 	 
   f1 = new File("C:\\DB1\\"+DirinDir[class_no]); 
   if(f1.isDirectory())
     {
     //System.out.println("\nDirectry Name:"+"C:\\DB1\\"+DirinDir[class_no]+"\n"); 
     String children[] = f1.list();
     Arrays.sort(children);
          
     //process all files in class_no th folder   
             
      for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {
        //get next image2 & add    
       	f2=new File("C:\\DB1\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
        f3=new File("C:\\2DMMC\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);      	    
        f4=new File("C:\\1DMMC\\"+DirinDir[class_no]+"\\"+children[NoofFiles]); 
        try{
        	image2 = ImageIO.read(f2);
            	} catch(IOException e){System.out.println("Can't read File:"+"C:\\DB1\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);}
        //System.out.println("File Name:"+"C:\\DB1\\"+DirinDir[class_no]+"\\"+children[NoofFiles]); 	
        try{
        	image3 = ImageIO.read(f3);
        	} catch(IOException e){System.out.println("Can't read File:"+"C:\\2DMMC\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);}
       try{
        	image4 = ImageIO.read(f4);
        	} catch(IOException e){System.out.println("Can't read File:"+"C:\\1DMMC\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);}
       
        	 	
        //find Mi for class class_no  
        	 	
        if(!children[NoofFiles].equals("Thumbs.db"))
         {
         
          for(i=0;i<IMAGE_WIDTH;i++)
          for(j=0;j<IMAGE_HEIGHT;j++)
          {
           rgb = image2.getRGB(i, j); 
           alpha = ((rgb >> 24) & 0xff); 
           red = ((rgb >> 16) & 0xff); 
           green =((rgb >> 8) & 0xff); 
           blue =((rgb ) & 0xff); 
            try  {X0.set(i,j,1.0*alpha/255.0);   
                  X1.set(i,j,1.0*red/255.0);   
                  X2.set(i,j,1.0*green/255.0);   
                  X3.set(i,j,1.0*blue/255.0);   
        	      }  catch(ArrayIndexOutOfBoundsException e){ } 
           } 
          
          //apply 2d2MMC on image2 X
          _2D2MMC0=Wmmc_c0.transpose().times(X0).times(Wmmc_r0);  
          _2D2MMC1=Wmmc_c1.transpose().times(X1).times(Wmmc_r1);     
          _2D2MMC2=Wmmc_c2.transpose().times(X2).times(Wmmc_r2);     
          _2D2MMC3=Wmmc_c3.transpose().times(X3).times(Wmmc_r3); 
          	
 
        //save 2DMMC into 2DMMC databse
          for(i=0;i<nc;i++)
          for(j=0;j<nr;j++)
          {
          	alpha=(int)(_2D2MMC0.get(i,j)*255.0);
          	red=(int)(_2D2MMC1.get(i,j)*255.0);
          	green=(int)(_2D2MMC2.get(i,j)*255.0);
          	blue=(int)(_2D2MMC3.get(i,j)*255.0);
          	alpha = ((alpha << 24) & 0xff000000); 
            red = ((red << 16) & 0x00ff0000); 
            green = ((green << 8) & 0x0000ff00); 
            blue = ((blue ) & 0x000000ff); 
            rgb=(alpha + red +green+blue);
            image3.setRGB(i,j,rgb);
          }
          ImageIO.write( image3, "bmp" /* "png" "jpeg" ... format desired */ ,
               f3 /* target */);
          
          //System.out.println("value of _2D2MMC=");  	
          //_2D2MMC3.print(5,5);
          // conver 2D matrix _2D2MMC into 1D (nc*nr dimensional) array for furher processing by 1DLDA
          
           _1DMMC0[fileno]= _2D2MMC0.getRowPackedCopy();    
           _1DMMC1[fileno]= _2D2MMC1.getRowPackedCopy();     
           _1DMMC2[fileno]= _2D2MMC2.getRowPackedCopy(); 
           _1DMMC3[fileno]= _2D2MMC3.getRowPackedCopy(); 
           	
          /* 	System.out.println("fileno="+fileno);
           	System.out.println("_2D2MMC0=");
            _2D2MMC0.print(4,4);
            System.out.println("_2D2MMC1=");
            _2D2MMC1.print(4,4);
            System.out.println("_2D2MMC2=");
            _2D2MMC2.print(4,4);
            System.out.println("_2D2MMC3=");
            _2D2MMC3.print(4,4);
           	for(i=0;i<nr*nc;i++)
           	{
           	System.out.println("i="+i+" _1DMMC0="+ _1DMMC0[fileno][i]);
           	System.out.println("i="+i+" _1DMMC1="+ _1DMMC1[fileno][i]);
            System.out.println("i="+i+" _1DMMC2="+ _1DMMC2[fileno][i]);
            System.out.println("i="+i+" _1DMMC3="+ _1DMMC3[fileno][i]);
           	}*/           		
           	
         //save 1DMMC into 1dMMC databse  
         for(i=0;i<nc*nr;i++)
           {
          	alpha=(int)(_1DMMC0[fileno][i]*255.0);
          	red=(int)(_1DMMC1[fileno][i]*255.0);
          	green=(int)(_1DMMC2[fileno][i]*255.0);
          	blue=(int)(_1DMMC3[fileno][i]*255.0);
          	alpha = ((alpha << 24) & 0xff000000); 
            red = ((red << 16) & 0x00ff0000); 
            green = ((green << 8) & 0x0000ff00); 
            blue = ((blue ) & 0x000000ff); 
            rgb=(alpha + red +green+blue);
            image4.setRGB(i,0,rgb);
          }
          ImageIO.write( image4, "bmp" /* "png" "jpeg" ... format desired */ ,
               f4 /* target */);           	
           	
           	fileno++;
           	
            //ImageIO.write(image2,".jpg",new File("D:\\DB1\\"+DirinDir[class_no]+"\\"+children[NoofFiles]));
          }    
               
        }//end of file procesng for current class 
    }	//end if
//   _2D2MMC0.print(5,5);//print 1 compo of reduced image2
   }//end of  procesng for all classes 
   
   System.out.println("Total files processed="+fileno);  
   	
             
 } 
  public static void  step3_LDA() throws IOException
   {System.out.println("In step3_LDA()\n");
   step3_LDA_Compute_Mi();
   step3_LDA_Compute_M();
   step3_LDA_Compute_Sb();
   step3_LDA_Compute_Sw();
   }
  public static void step3_LDA_Compute_Mi()throws IOException{
	
//File f,f1,f2;
int i=0,j=0,red,green,blue,alpha,rgb;
System.out.println("In step3_LDA_Compute_Mi\n");
f = new File("C:\\1DMMC");
String[] DirinDir = f.list();
Arrays.sort(DirinDir);
//process all directories to compute Mi[][][][] for given class_no
    
for(int class_no=0,current_class=0;class_no<DirinDir.length;class_no++)
  { 	 
   f1 = new File("C:\\1DMMC\\"+DirinDir[class_no]); 
   if(f1.isDirectory())
     {
     //System.out.println("\nDirectry Name:"+"C:\\DB1\\"+DirinDir[class_no]+"\n"); 
     System.out.println("Current class no="+current_class+"\n\n");
     
     String children[] = f1.list();
     Arrays.sort(children);
          
     //process all files in current_class th folder   
             
      for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {
        //get next image2 & add    
       	f2=new File("C:\\1DMMC\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
                	    
        try{
        	image2 = ImageIO.read(f2);
        	} catch(IOException e){System.out.println("Can't read File:"+"C:\\1DMMC\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);}
        //System.out.println("File Name:"+"C:\\DB1\\"+DirinDir[class_no]+"\\"+children[NoofFiles]); 	
        	  
        	 	
        //find SMi for class class_no  
        	 	
        if(!children[NoofFiles].equals("Thumbs.db"))
         {
         
          for(i=0;i<nc*nr;i++)
          
          {
           rgb = image2.getRGB(i, 0); 
           alpha = ((rgb >> 24) & 0xff); 
           red = ((rgb >> 16) & 0xff); 
           green =((rgb >> 8) & 0xff); 
           blue =((rgb ) & 0xff); 
            try  {	                                           
        	      SMi0[i][current_class] += (alpha/255.0)*(1.0); 
                  SMi1[i][current_class] += (red/255.0)*(1.0); 
                  SMi2[i][current_class] +=(green/255.0)*(1.0); 
                  SMi3[i][current_class]+=(blue/255.0)*(1.0); 
                  //System.out.println(" i="+i+" j= "+j+"current_class="+current_class); 
                  //System.out.println(Mi[i][j][0][current_class]+" "+Mi[i][j][1][current_class]+" "+Mi[i][j][2][current_class]+" "+Mi[i][j][3][current_class]);
                  }  catch(ArrayIndexOutOfBoundsException e){ } 
          }      	  	 
        }    
            
    }//end of file procesng for current class 
      
       
        //find average/mean SMi
       for(i=0;i<nc*nr;i++)
       	 {     	 //System.out.println(" i="+i+" j= "+j+"current_class="+current_class);		
        	      SMi0[i][current_class]  /= NoofSamples[current_class]*1.0;
                  SMi1[i][current_class]  /= NoofSamples[current_class]*1.0;
                  SMi2[i][current_class]  /= NoofSamples[current_class]*1.0;
                  SMi3[i][current_class]  /= NoofSamples[current_class]*1.0;
         }	
        //print average  SMi
        for(i=0;i<nc*nr;i++)
       	 {     	 System.out.println("i="+i+"  j="+j+"  current_class="+current_class);		
        	     System.out.println("SMi0="+SMi0[i][current_class]+" SMi1="+SMi1[i][current_class]+" SMi2="+SMi2[i][current_class]+" SMi3="+SMi3[i][current_class]);
         }
         
         current_class++;	
     }	//end if
     
     
   }//end of  procesng for all classes
} 
  public static void step3_LDA_Compute_M() throws IOException
  { System.out.println("In step3_LDA_Compute_M():\n");
  	int i=0,j=0,class_no=0;
  for(class_no=0;class_no<NoofClasses;class_no++)
  	for(i=0;i<nc*nr;i++)
         {
          SM0[i]      += SMi0[i][class_no];
          SM1[i]      += SMi1[i][class_no];
          SM2[i]      += SMi2[i][class_no];
          SM3[i]      += SMi3[i][class_no];
          //System.out.println("In compute SM");
          //System.out.println(" i="+i+" j= "+j+"class_no="+class_no); 
          //System.out.println(" "+M[i][j][0]+" "+M[i][j][1]+" "+M[i][j][2]+" "+M[i][j][3]);
         }
  	
  	for(i=0;i<nc*nr;i++)
         {  
          SM0[i]  /= NoofClasses;
          SM1[i]  /= NoofClasses;
          SM2[i]  /= NoofClasses;
          SM3[i]  /= NoofClasses;
          }
          
      //display SM
     for(i=0;i<nc*nr;i++)
         {  
          System.out.println("SM0="+SM0[i]+" SM1="+SM1[i]+" SM2="+SM2[i]+" SM3="+SM3[i]);
         
          }     
          
    }  
  public static void step3_LDA_Compute_Sb()throws IOException
  {
   System.out.println("In step3_LDA_Compute_Sb():\n");
   
   int i=0,j=0,class_no=0;
   double STM0[][]=new double[1][nc*nr];
   double STM1[][]=new double[1][nc*nr];
   double STM2[][]=new double[1][nc*nr];
   double STM3[][]=new double[1][nc*nr];
   double STMi0[][]=new double[1][nc*nr];
   double STMi1[][]=new double[1][nc*nr];
   double STMi2[][]=new double[1][nc*nr];
   double STMi3[][]=new double[1][nc*nr];
   
        for(j=0;j<nc*nr;j++)
         {  
          STM0[0][j] = SM0[j];
          STM1[0][j] = SM1[j];
          STM2[0][j] = SM2[j];
          STM3[0][j] = SM3[j];
          }
   
   M0=new Matrix(STM0);
   M1=new Matrix(STM1);
   M2=new Matrix(STM2);
   M3=new Matrix(STM3);
   
  	  
   for(class_no=0;class_no<NoofClasses;class_no++)
      {//step1: compute Mi[][][] for current class
       for(i=0;i<nc*nr;i++)
        
         {  
          STMi0[0][i] = SMi0[i][class_no];
          STMi1[0][i] = SMi1[i][class_no];
          STMi2[0][i] = SMi2[i][class_no];   
          STMi3[0][i] = SMi3[i][class_no];
          }
       Mi0=new Matrix(STMi0);
       //Mi0.print(5,5);
       Mi1=new Matrix(STMi1);
       //Mi1.print(5,5);
       Mi2=new Matrix(STMi2);
       Mi3=new Matrix(STMi3);
       //Mi3.print(5,5);
       
       //Rb0=Mi0.minus(M0).transpose().times(Mi0.minus(M0));
       T1=Mi0.minus(M0);
       //T1.print(5,5);
       T2=T1.transpose();
       //T2.print(5,5);
       T3=T2.times(T1);
       //T1.print(5,5);
       T3=T3.timesEquals(1.0*NoofSamples[class_no]); 
       //T1.print(5,5);
       Sb0=Sb0.plusEquals(T3);
       
       //Sb0.print(5,5);
      
       
       
       T1=Mi1.minus(M1);
       T2=T1.transpose();
       T3=T2.times(T1);
       T3=T3.timesEquals(1.0*NoofSamples[class_no]); 
       Sb1=Sb1.plusEquals(T3);
       
       
       
       T1=Mi2.minus(M2);
       T2=T1.transpose();
       T3=T2.times(T1);
       T3=T3.timesEquals(1.0*NoofSamples[class_no]); 
       Sb2=Sb2.plusEquals(T3);
       
       
       
       T1=Mi3.minus(M3);
       T2=T1.transpose();
       T3=T2.times(T1);
       T3=T3.timesEquals(1.0*NoofSamples[class_no]); 
       Sb3=Sb3.plusEquals(T3);       
     }
   
     Sb0=Sb0.timesEquals(1.0/totalnooffiles);    
     Sb1=Sb1.timesEquals(1.0/totalnooffiles);   
     Sb2=Sb2.timesEquals(1.0/totalnooffiles);   
     Sb3=Sb3.timesEquals(1.0/totalnooffiles);  
         
 System.out.println("Sb rows="+Sb0.getRowDimension()+"Sb columns="+Sb0.getColumnDimension());
               
 
 System.out.println("Sb0=");
 Sb0.print(4,4);
 
 System.out.println("Sb1=");
 Sb1.print(4,4);
  
 
 System.out.println("Sb2=");
 Sb2.print(5,5);    

 System.out.println("Sb3=");
 Sb3.print(5,5);     	

}
  public static void step3_LDA_Compute_Sw()throws IOException
 {
File f,f1,f2;
int i=0,j=0,red,green,blue,alpha,rgb;

System.out.println("In step3_LDA_Compute_Sw()\n");
f = new File("C:\\1DMMC");
String[] DirinDir = f.list();
Arrays.sort(DirinDir);
double STMi0[][]=new double[1][nc*nr];
double STMi1[][]=new double[1][nc*nr];
double STMi2[][]=new double[1][nc*nr];
double STMi3[][]=new double[1][nc*nr];
//process all directories to compute Mi[][][][] for given class_no
    
for(int class_no=0,current_class=0;class_no<DirinDir.length;class_no++)
  { 
  for(i=0;i<nc*nr;i++)
        
         {  
          STMi0[0][i] = SMi0[i][class_no];
          STMi1[0][i] = SMi1[i][class_no];
          STMi2[0][i] = SMi2[i][class_no];   
          STMi3[0][i] = SMi3[i][class_no];
          }
       Mi0=new Matrix(STMi0);
       //Mi0.print(5,5);
       Mi1=new Matrix(STMi1);
       //Mi1.print(5,5);
       Mi2=new Matrix(STMi2);
       Mi3=new Matrix(STMi3);
       //Mi3.print(5,5);
       
   
   //Mi3.print(5,5);
       
   f1 = new File("C:\\1DMMC\\"+DirinDir[class_no]); 
   if(f1.isDirectory())
     {
     //System.out.println("\nDirectry Name:"+"C:\\DB1\\"+DirinDir[class_no]+"\n"); 
     String children[] = f1.list();
     Arrays.sort(children);
          
     //process all files in class_no th folder   
             
     for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {      
                 
        //get next image2 & add    
       	f2=new File("C:\\1DMMC\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
                	    
        try{
        	image2 = ImageIO.read(f2);
        	} catch(IOException e){System.out.println("Can't read File:"+"C:\\1DMMC\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);}
        //System.out.println("File Name:"+"C:\\DB1\\"+DirinDir[class_no]+"\\"+children[NoofFiles]); 	
        	  
        	 	
        //find Mi for class class_no  
        	 	
       if(!children[NoofFiles].equals("Thumbs.db"))
         {        
          for(i=0;i<nc*nr;i++)
          
          {
           rgb = image2.getRGB(i, 0); 
           alpha = ((rgb >> 24) & 0xff); 
           red = ((rgb >> 16) & 0xff); 
           green =((rgb >> 8) & 0xff); 
           blue =((rgb ) & 0xff); 
            try  {	                                           
        	      SAj0[i][j] = (alpha/255.0)*(1.0); 
                  SAj1[i][j] = (red/255.0)*(1.0); 
                  SAj2[i][j] =(green/255.0)*(1.0); 
                  SAj3[i][j] =(blue/255.0)*(1.0); 
                  //System.out.println(" i="+i+" j= "+j+"class_no="+class_no); 
                  //System.out.println(Mi[i][j][0][class_no]+" "+Mi[i][j][1][class_no]+" "+Mi[i][j][2][class_no]+" "+Mi[i][j][3][class_no]);
                  }  catch(ArrayIndexOutOfBoundsException e){ } 
          }   
          
           M0=new Matrix(SAj0);
           M1=new Matrix(SAj1);
           M2=new Matrix(SAj2);
           M3=new Matrix(SAj3);
     
       
          T1=Mi0.minus(M0);
          T2=T1.transpose();
          T3=T2.times(T1);
          Sw0=Sw0.plusEquals(T3);
          
       //System.out.println("T4 rows="+T4.getRowDimension()+"T4 columns="+T4.getColumnDimension());
       
       
       
       T1=Mi1.minus(M1);
       T2=T1.transpose();
       T3=T2.times(T1);
       Sw1=Sw1.plusEquals(T3);
       
       
       
       T1=Mi2.minus(M2);
       T2=T1.transpose();
       T3=T2.times(T1);
       Sw2=Sw2.plusEquals(T3);
       
       
       
       T1=Mi3.minus(M3);
       //System.out.println("classno="+current_class);
       //T1.print(5,5);
       T2=T1.transpose();
       T3=T2.times(T1);
       Sw3=Sw3.plusEquals(T3);       
      
       }    
            
    }//end of file procesng for current class 
     
     
     
     current_class++;
   
     
     }	//end if
   }//end of  procesng for all classes  


      
      
      
     Sw0=Sw0.timesEquals(1.0/totalnooffiles);    
     Sw1=Sw1.timesEquals(1.0/totalnooffiles);   
     Sw2=Sw2.timesEquals(1.0/totalnooffiles);   
     Sw3=Sw3.timesEquals(1.0/totalnooffiles);  
         
       
     System.out.println("Sw rows="+Sw0.getRowDimension()+"Sw columns="+Sw0.getColumnDimension());
                   
 
     System.out.println("Sw0=");
     Sw0.print(4,4);
 
     System.out.println("Sw1=");
     Sw1.print(4,4);
  
 
     System.out.println("Sw2=");
     Sw2.print(5,5);    

     System.out.println("Sw3=");
     Sw3.print(5,5);    
    }  
}