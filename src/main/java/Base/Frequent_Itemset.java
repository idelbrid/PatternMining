package Base;

import Apriori.Candidate;
import java.util.ArrayList;
import java.util.Comparator;
//import java.util.Comparator;


public class Frequent_Itemset {
    private static int databaseLength = 1;
    private static final ItemSorter sorter = new ItemSorter();
	public int supportCount = 0;
    public double support = 0.0;
	public ArrayList<String> itemset;
//    private Comparator<String> stringComparator;

    // Base constructor
	public Frequent_Itemset(){
    }

    // Normal constructor from itemset and support count
	public Frequent_Itemset(ArrayList<String> itemset, int support_count){
		this();
		this.itemset = (ArrayList<String>) itemset.clone();
        this.itemset.sort(sorter);
		this.supportCount = support_count;
        this.support = supportCount / (float)databaseLength;

    }

    // Pull itemset and count from Candidate
	public Frequent_Itemset(Candidate c){
		this(c.itemset, c.count);
	}

	static public void setDatabaseLength(int length){
        databaseLength = length;
    }

	public int length(){
        return itemset.size();
    }

	public String toString(){
		String outstring = "{";
		for(String s : this.itemset){
			outstring += s + ", ";
		}
		if (outstring.substring(outstring.length()-2, outstring.length()).equals(", "))
                outstring = outstring.substring(0, outstring.length()-2);
		outstring += "}";

		return outstring;
	}
    // Superior itemset equality checking by assuming itemset is ordered.
	public boolean matches(Frequent_Itemset other){
        if(this.length() != other.length())
            return false;
        else{
            for(int i=0; i < this.length(); i++) {
                if (!this.itemset.get(i).equals(other.itemset.get(i)))
                    return false;
            }
            return true;
        }
	}
}