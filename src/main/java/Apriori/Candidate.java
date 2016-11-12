package Apriori;

import java.util.ArrayList;


public class Candidate {

	public int count=0;
	public ArrayList<String> itemset = new ArrayList<String>();
	
	public Candidate(ArrayList<String> itemset){
		this.itemset = itemset;
		
		
	}

	public String toString(){
		String outstring = "";
		for(String s : this.itemset){
			outstring += s + ", ";
		}
		if(outstring.equals(""))
			outstring = "EMPTY";
		else outstring = outstring.substring(0,outstring.length()-2);
		
		return outstring;
	}
}

