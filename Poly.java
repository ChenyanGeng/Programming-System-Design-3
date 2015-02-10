// Name:Chenyan Geng
// USC loginid:cgeng	
// CS 455 PA3
// Spring 2013
import java.util.*;
import java.lang.Math;
/**
   A polynomial. Polynomials can be copied, added together, multiplied, 
   evaluated, and converted to a string form for printing.

   Version for PA3
*/
public class Poly {

    /**
       Creates the 0 polynomial
    */
    public Poly() {
    assert isValidPoly();
    }


    /**
       Creates polynomial with single term given
     */
    public Poly(Term term) {
    if (term.getCoeff()!=0){  // When test assert, this line should be deleted
    poly.add(term);
	}                         // When test assert, this line should be deleted
	assert isValidPoly();     
    }


    /**
       Creates poly that is a copy of original
       (Copy constructor)
    */
    public Poly(Poly original) {                                  
    ListIterator<Term> ori_iter= original.poly.listIterator();
	while (ori_iter.hasNext()){
	 poly.add(ori_iter.next()); //copy each element from original
	}
	assert isValidPoly();   
    }


    /**
       Returns the Poly that is the sum of this polynomial and b
       (neither poly is modified)
     */
    public Poly add(Poly b) {
	Poly sum = new Poly();    //return value
	ListIterator<Term> iter = poly.listIterator(); //iterator of poly
	while (iter.hasNext()){
	 sum.poly.add(iter.next());  //copy poly to sum
	}
	sum.addIn(b);
	assert isValidPoly();                       //Examine if this.poly is valid
    assert b.isValidPoly();                     //Examine if b is valid
    assert sum.isValidPoly();	                //Examine if sum is valid
	return sum;  // dummy code.  just to get stub to compile
    }


    /**
       Returns the value of the poly at a given value of x.
     */
    public double eval(double x) {
	double val = 0, c = 0; //val is the return value, c is the coefficient of each term
	int e = 0; //e is the exponent of each term
	ListIterator<Term> iter = poly.listIterator();
	while (iter.hasNext()){
	 Term t = iter.next(); //t is the current term
	 c = t.getCoeff();
	 e = t.getExpon();
	 val = val+c*Math.pow(x,e); //calculate the return value
	}
	return val;         // dummy code.  just to get stub to compile
    }


    /**
       Return a String version of the polynomial with the 
       following format, shown by exmaple:
       zero poly:   "0.0"
       1-term poly: "3.0x^2"
       4-term poly: "3.0x^5 + x^2 + 2.0x + 7.0"

       Poly is in a simplified form (only one term for any exponent),
       with no zero-coefficient terms, and terms are shown in
       decreasing order by exponent.
    */
    public String toFormattedString() {
	ListIterator<Term> iter = poly.listIterator();
	String s = new String();   //return value
	if (poly.size()==0){               //Condition of zero poly
	 s = "0.0.";
	}
	else{
	int i = 0;
	while (iter.hasNext()){
	 int exp = iter.next().getExpon();
	 iter.previous();
	 double coe = iter.next().getCoeff();
	 if(exp==0){   //Condition when expon = 0
	  if (i==0){
	   s = Double.toString(coe);
	   i++;
	  }
	  else {
	   s = s+"+"+coe;
	   }
	 }
	 else if(exp==1){  //Condition when expon = 1
	  if (i==0){
	   s = coe+"x";
	   i++;
	  }
	  else{
	   s = s+"+"+coe+"x";
	  }
	  }
	  else if(coe==1){   //Condition when coeff = 1
	  if (i==0){
	   s = "x^"+exp;
	   i++;
	  }
	  else{
	   s = s+"+"+"x^"+exp;
	  }
	 }
	  else if(coe==-1){   //Condition when coeff = -1
	  if (i==0){
	   s = "-x^"+exp;
	   i++;
	  }
	  else{
	   s = s+"+"+"-x^"+exp;
	  }
	 }
	 else{                                    //Usual conditons
	  if (i==0){
	   s = coe+"x^"+exp;
	   i++;
	  }
	  else{
	   s = s+"+"+coe+"x^"+exp;
	  }
	 }
	}
	}
	return s;        // dummy code.  just to get stub to compile
    }


    /**
       Returns the Poly that is the product of this polynomial and b
       (neither poly is modified)
     */
    public Poly mult(Poly b) {
	Poly product = new Poly();
	ListIterator<Term> iter = poly.listIterator(); 
	ListIterator<Term> iterb = b.poly.listIterator(); 
	if ((poly.size()!=0)&&(b.poly.size()!=0)){
	while(iter.hasNext()){
	 Term p = iter.next();//current poly term
	 while(iterb.hasNext()){
	 Poly newnew = formAPoly(p,iterb.next());
	  product.addIn(newnew);
	 }
	 iterb = b.poly.listIterator();//set the iterator of b back to the front 
	}
	}
	assert isValidPoly();                       //Examine if this.poly is valid
    assert b.isValidPoly();                     //Examine if b is valid
    assert product.isValidPoly();	                //Examine if sum is valid
	return product;  // dummy code.  just to get stub to compile
    }


    /**
       Adds b to this poly.  (mutator)
       (b is unchanged)
    */
    public void addIn(Poly b) {
	ListIterator<Term> iter = poly.listIterator();  
	ListIterator<Term> iter_end = poly.listIterator(poly.size());
	ListIterator<Term> iterb = b.poly.listIterator();
	if (poly.size()==0){
	 while (iterb.hasNext()){
	  iter_end.add(iterb.next());
	 }
	}
	else if((b.poly.size()!=0)&&(poly.size()!=0)){
	int bfirst = iterb.next().getExpon();
	int polylast = iter_end.previous().getExpon();
	iterb.previous();
	if (bfirst <polylast){
	 iter_end.next();
	 while (iterb.hasNext()){
	  iter_end.add(iterb.next());
	 }
	}
	else{
	 while (iterb.hasNext()&&iter.hasNext()){ 
	  double bcoe = iterb.next().getCoeff();
	  int bexp = iterb.previous().getExpon();
	  double polycoe = iter.next().getCoeff();
	  int polyexp = iter.previous().getExpon();
	  int val = compareExpon(polyexp,bexp);
	  if (val == -1){
       iter.add(new Term(bcoe,bexp));
	   iterb.next();   
	  }
	  else if (val == 1){
	   iter.next();	
	  }
	  else if (val == 0){
       double newcoe; 
	   newcoe = polycoe+bcoe;
		iter.set(new Term(newcoe,polyexp));
	   iter.next();
	   iterb.next(); 
	  }  
	 }
	 while (iterb.hasNext()){
	  iter.add(iterb.next());
	 }
	 iter = poly.listIterator();  // When test assert, this line should be deleted
	 while (iter.hasNext()){// When test assert, this line should be deleted
	  if (iter.next().getCoeff()==0){// When test assert, this line should be deleted
	   iter.remove();// When test assert, this line should be deleted
	   }// When test assert, this line should be deleted
	   }// When test assert, this line should be deleted
	}
    }
	assert isValidPoly();                       //Examine if this.poly is valid
    assert b.isValidPoly();                     //Examine if b is valid
    }



    // **************************************************************
    //  PRIVATE METHOD(S)

    /**
       Returns true iff the poly data is in a valid state.
    */
    private boolean isValidPoly() {
	boolean check = true;
	ListIterator<Term> iter = poly.listIterator(); 
	Term current = iter.next();
	while(iter.hasNext()){ 
	 Term future = iter.next();
	 if (current.getCoeff()==0){   //Condition when coefficient of any terms equals to 0
	  check = false;
	  System.out.println("Coefficients should not be 0");
	  break;
	 }
	 else if (current.getExpon()<0){  //Condition when exponent of terms are negative
	  check = false;
	  System.out.println("Exponents should be positive");
	  break;
	 }  	 
	 else if (current.getExpon()<=future.getExpon()){  //Condition when exponent of terms are not in a large to small order
	  check = false;
	  System.out.println("Exponents should be in large to small order");
	  break;
	  }
	 current = future;
	 }
	return check;     // dummy code.  just to get stub to compile
    }

	/**
       Returns the compare result of x and y.
	   if x>y returns 1,
	   if x<y returns -1,
	   if x=y returns 0.
    */
	private int compareExpon(int x, int y){ 
	 int re = 0;
	 if (x>y){
	  re = 1;
	  }
	 else if (x<y){
	  re = -1;
	 }
	 return re;
	 }
	 
	/**
       Form the multiplied result of term x and y and return it.
    */ 
	 private Poly formAPoly(Term x, Term y){ 
	 int newexp;
	 double newcoe;
	 newexp = x.getExpon()+y.getExpon();
	 newcoe = x.getCoeff()*y.getCoeff();
	 Poly newpoly = new Poly(new Term(newcoe,newexp));
	 return newpoly;
	 }


    // **************************************************************
    //  PRIVATE INSTANCE VARIABLE(S)
    private LinkedList<Term> poly = new LinkedList<Term>();
	/* Representation Invariants
   The exponents in Terms of poly should go from large to small in order;
   The exponents in Terms of poly should be positive.
   Coefficient = 0 in Terms of poly is not allowed;
   Polysize>=0;
*/
}
