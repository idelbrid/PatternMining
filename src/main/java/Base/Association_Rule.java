package Base;

import org.jcp.xml.dsig.internal.dom.Utils;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Association_Rule {
	private Frequent_Itemset parent;
	private Frequent_Itemset leftItemset;
	private Frequent_Itemset rightItemset;
	private int supportCount =0;
	private double support;
	private double confidence;
	
	public Association_Rule(Frequent_Itemset left, Frequent_Itemset right, Frequent_Itemset parent){
		this.parent = parent;
		this.leftItemset = left;
		this.rightItemset = right;
		this.supportCount = parent.supportCount;
		this.support = parent.support;
		this.confidence = parent.supportCount / (float) left.supportCount;
	}

	public int getSupportCount(){
		return supportCount;
	}

	public double getSupport(){
        return support;
    }

    public double getConfidence(){
        return confidence;
    }

	public String toString(){
		String outstring = "";
		outstring += leftItemset +" => "+ rightItemset;
		return outstring;
	}

	public static ArrayList<Association_Rule> generateAssertionRules(Iterable<Frequent_Itemset> frequentItemsets){
        // Create a hashtable for the itemsets' sets
        // Then go through all subsets of each FIS to create the rules
        HashMap<HashSet, Frequent_Itemset> lookupMap = new HashMap<HashSet, Frequent_Itemset>();
        ArrayList<Association_Rule> toReturn = new ArrayList<Association_Rule>();
        for(Frequent_Itemset fis : frequentItemsets){
            HashSet<String> itemHashSet = new HashSet<String>(fis.itemset);
            lookupMap.put(itemHashSet, fis);
        }
        for(Frequent_Itemset thisItemset : frequentItemsets){
            ArrayList<ArrayList> subsets = powerSet(thisItemset.itemset);
            for(ArrayList<String> subset : subsets){
                if( subset.size() == 0 || subset.size() == thisItemset.itemset.size())
                    continue;
                HashSet<String> hashSubset = new HashSet<String>(subset);
                HashSet<String> complement = new HashSet<String>();
                for(String item : thisItemset.itemset){
                    if(!hashSubset.contains(item))
                        complement.add(item);
                }

                Frequent_Itemset left = lookupMap.get(hashSubset);
                Frequent_Itemset right = lookupMap.get(complement);
                Association_Rule rule = new Association_Rule(left, right, thisItemset);
                toReturn.add(rule);
            }
        }
        return toReturn;
    }

    private static ArrayList<ArrayList> powerSet(ArrayList<String> curSet){
        if(!curSet.isEmpty()){
            ArrayList<String> copy = (ArrayList<String>) curSet.clone();
            String head = copy.remove(0);

            ArrayList<ArrayList> subPowerSet = powerSet(copy);
            ArrayList<ArrayList> addedLists = new ArrayList<ArrayList>();
            for(ArrayList<String> l : subPowerSet){
                ArrayList<String> lClone = (ArrayList<String>) l.clone();
                lClone.add(head);
                addedLists.add(lClone);
            }
            subPowerSet.addAll(addedLists);
            return subPowerSet;
        }
        else{
            ArrayList<ArrayList> toReturn = new ArrayList<ArrayList>();
            toReturn.add(new ArrayList<String>());
            return toReturn;
        }
    }
}
