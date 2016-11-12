package Base;

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
	

}
