// Name:Chenyan Geng
// USC loginid:cgeng	
// CS 455 PA3
// Spring 2013
import java.util.*;

class PolyException extends Exception{ //my own exception 
 public PolyException(){}
 public PolyException(String msg){
  super(msg);
  }
 }
 
public class PolyProg {
    public static void main(String[] args) {
	Poly[] polyarr = new Poly[10];
	for (int i = 0; i<polyarr.length; i++){  //Initialize polinimials to zero
	 polyarr[i] = new Poly();
	 }
	while(true){ 
	  System.out.print("cmd>"); 
	  Scanner in = new Scanner(System.in);
	  String inLine = in.nextLine();               
      Scanner commandScanner = new Scanner(inLine);
	  ArrayList<Integer> numArr = new ArrayList<Integer>(); //numArr stores arguments
      String command = commandScanner.next();	//command stores the action user types: create, print, add, eval, quit or help
      boolean check = false; //check wrong type of arguments input	  
	  int type; //type of command
	   while (commandScanner.hasNextInt()){ 
	     numArr.add(commandScanner.nextInt());
	    }
	   if(commandScanner.hasNext()){
	   check = true;
		}
	   try {
	    if (check == true){
		errType();
		}
		else{
		 if (command.compareToIgnoreCase("create")==0){        //When command is create 
		  type = 1;
	      if (isValidNum(numArr, type)){ 
		   doCreate(polyarr, numArr.get(0));
		  }
	    }
	    else if (command.compareToIgnoreCase("print")==0){   //When command is print
	      type = 1;
	      if (isValidNum(numArr, type)){ 
		   doPrint(polyarr, numArr.get(0));
		   }
	    }
	    else if (command.compareToIgnoreCase("add")==0){     //When command is add
	     type = 3;
	     if (isValidNum(numArr, type)){ 
	       doAdd(polyarr, numArr.get(0), numArr.get(1), numArr.get(2));
	       }
	    }
		else if (command.compareToIgnoreCase("addin")==0){     //When command is addin
	     type = 2;
	     if (isValidNum(numArr, type)){             
	       doAddIn(polyarr, numArr.get(0), numArr.get(1));
	       }
	    }
		else if (command.compareToIgnoreCase("copy")==0){     //When command is copy
	     type = 2;
	     if (isValidNum(numArr, type)){            
	       doCopy(polyarr, numArr.get(0), numArr.get(1));
	       }
	    }
		else if (command.compareToIgnoreCase("mult")==0){     //When command is mult
	     type = 3;
	     if (isValidNum(numArr, type)){ 
	       doMult(polyarr, numArr.get(0), numArr.get(1), numArr.get(2));
	       }
	    }
	    else if (command.compareToIgnoreCase("eval")==0){    //When command is eval
	     type = 1;
	     if (isValidNum(numArr, type)){ 
		  doEval(polyarr, numArr.get(0));
		  }
	    }   
	    else if (command.compareToIgnoreCase("quit")==0){    //When command is quit
	     break;                                              //exit the loop of while(true)
	    }
	    else if (command.compareToIgnoreCase("help")==0){    //When command is help
	     doHelp();
	    }
	    else{                                                 //When the command is not valid
	     try { 
		   errCommand();
		  }
		 catch (PolyException e){
	     System.out.println(e.getMessage());
		 }
	    }
		}
	   }
	   catch (PolyException e){
	    System.out.println(e.getMessage());
	   }    		
	}
   }

/**
       Create the 'num'-th polynomial in the array 'arr' containing 10 polynomials.
	   The coefficients and exponents of each term is typed in by the user.
	   Aware of the two conditions below:
	   1. Enter odd number of values: we will abandon the last value of the input and make a warning information
	   2. Exponent entered is negative: we will change it to absolute value and make a warning information
    */
private static void doCreate(Poly[] arr, int num){
  arr[num] = new Poly();                         //Release the value of arr[num] in case 
  System.out.println("Enter a space-separated sequence of coeff-power pairs terminated by <nl>"); 
  Scanner coex = new Scanner(System.in); 
  String line = coex.nextLine();           //Store the whole line entered as a String
  Scanner lineScanner = new Scanner(line); //Read from the String
  boolean go = true;                    //represents that the input is valid
  while (lineScanner.hasNext()){     //Test if there is invalid arguments in the input array
   try{
	String value = lineScanner.next();
    boolean isNum1 = value.matches("[0-9]+.[0-9]+");  //double condition
    boolean isNum2 = value.matches("[0-9]+");         //int condition
    if(!(isNum1||isNum2)){
	 go = false;
	 wrongType();
	 } 
	}
   catch (PolyException e){
    System.out.println(e.getMessage());
    }	
   }		
   lineScanner = new Scanner(line);          //re-initialize the scanner to the beginning
	  while(lineScanner.hasNext()&&go) {     //Read the values in couple if all the values are valid (go = true)
		double coe = lineScanner.nextDouble();   //Read coefficient first
		if (!lineScanner.hasNextDouble()){       //Test condition 1
		 System.out.println("WARNING: Miss the last exponent.");
		 break;
		}
		int exp = lineScanner.nextInt();         //Then read the exponent
		if (exp<0){                              //Test condition 2
		 exp = -1*exp; 
		 System.out.println("WARNING: Exponent should be positive.");
		 }
		 arr[num].addIn(new Poly(new Term(coe,exp)));  //store the new term in arr[num]
		
	}
}

/**
        Print the 'num'-th polynomial in the array 'arr' containing 10 polynomials.
    */
private static void doPrint(Poly[] arr, int num){
 System.out.println(arr[num].toFormattedString());
 }
 
/**
        Add the 'num1'-th polynomial and the 'num2'-th polynomial then
		store the sum in the 'numsum'-th polynomial.
		
    */ 
private static void doAdd(Poly[] arr, int numsum, int num1, int num2){
 arr[numsum] = arr[num1].add(arr[num2]);
 }
 
private static void doAddIn(Poly[] arr, int num1, int num2){
 arr[num1].addIn(arr[num2]);
 }
 
 private static void doCopy(Poly[] arr, int num1, int num2){
 arr[num1] = new Poly(arr[num2]);
 }
 
 private static void doMult(Poly[] arr, int num1, int num2, int num3){
 arr[num1] = arr[num2].mult(arr[num3]);
 }

/**
        Get the value of the 'num'-th polynomial in arry 'arr' at a given value of x.
		Then print the value out.
		
    */  
private static void doEval(Poly[] arr, int num){
 System.out.print("Enter a floating point value for x: ");
 Scanner e = new Scanner(System.in);  
 double x = e.nextDouble();             // get the entered value of x
 System.out.println(Double.toString(arr[num].eval(x)));
}
/**
        Print the help information
		
    */  
private static void doHelp(){
      System.out.println("There is an array of 10 polynomials whose initial value is the zero polynomial.");
	  System.out.println("Users are able to manipulate any of these polynomials by inputing the commands"); 
	  System.out.println("shown as below:");
	  System.out.println("create n       create the n-th polynomial (n belongs to [0,9]);");
	  System.out.println("print n        print the n-th polynomial (n belongs to [0,9]);");
	  System.out.println("add n1 n2 n3   n1-th polynomial = n2-th polynomial + n3-th polynomial"); 
	  System.out.println("              (n1,n2,n3 belongs to [0,9]);");
	  System.out.println("eval n         get the value of the n-th polynomial"); 
	  System.out.println("              when x in it equals a fixed value set by user (n belongs to [0,9]);");
	  System.out.println("quit           end the commands;");
	  }

/**
       Return true if the arguments are in valid condition
    */
private static boolean isValidNum(ArrayList<Integer> numArr, int type){
 boolean re = true;
 int numArg = numArr.size(); //number of arguments in array
 for (int i = 0; i<numArg; i++){
 if (numArr.get(i)>9){  //check if each argument is in [0,9]
	 System.out.println("ERROR: illegal index for a poly.  must be between 0 and 9, inclusive");
	 re = false;
	}
  }
 try{          //check the bounds of arguments
  if (numArg > type){
   re = false;
   tooMany();
  }
  else if (numArg < type){
   re = false;
   tooFew();
  }
 }
 catch (PolyException e){
  System.out.println(e.getMessage()+" Expecting "+ type);
  System.out.println("For more information type 'help'");
 }
	return re;
 }
private static void errCommand() throws PolyException{
 throw new PolyException("ERROR: Invalid command. Type 'help' for command options.");
} 
private static void wrongType() throws PolyException{
 throw new PolyException("ERROR: Wrong input type. A term is a coefficient (double) followed by an exponent (int).");
} 
private static void errType() throws PolyException{
 throw new PolyException("ERROR: Wrong input type. Type 'help' for command options.");
} 
private static void tooFew() throws PolyException{
 throw new PolyException("ERROR: Too few arguments. ");
} 
private static void tooMany() throws PolyException{
 throw new PolyException("ERROR: Too many arguments. ");
}  
}
 
