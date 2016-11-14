package FPGrowth;

import java.util.ArrayList;


public class FP_Node {

	ArrayList<FP_Node> children;
	//main.FP_Node node_link;
	String value;
	int count;
	FP_Node sibling,parent;
	
	public FP_Node(){
		children = new ArrayList<FP_Node>();
		count = 0;
		value = "";
	}

	public FP_Node(String value, int count){
        this();
        this.count = count;
        this.value = value;
    }

    public int getCount(){
        return this.count;
    }

    public String getValue(){
        return this.value;
    }

	public boolean has_child(String str){
		
		for(FP_Node node : children)
			if(node.value.equals(str))
				return true;
		return false;
	}
	
	public FP_Node get_child(String str){
		for(FP_Node node : children)
			if(node.value.equals(str))
				return node;
		System.err.println("No such child found.");
		return null;
	}
	
	public boolean has_descendent(String desc){
		if(this.value.equals(desc))
			return true;
		if(children == null)
			return false;
		else{
			for(FP_Node child : children){
				if(child.has_descendent(desc))
					return true;
			}
			return false;
		}
	}
	
	public String toString(){
		String outstring = "(";
		outstring += value;
		outstring += ": " + count;
		outstring += ", [";
		outstring += parent.value;
//		for(main.FP_Node c : children){
//			outstring += c.value;
//		}
		outstring += "]";
		outstring += ")";
		return outstring;
	}
	 
	public FP_Node copy(){
		FP_Node toReturn = new FP_Node();
		toReturn.count = this.count;
		toReturn.parent = this.parent;
		toReturn.value = this.value;
		toReturn.sibling = this.sibling;
		toReturn.children = this.children;
		return toReturn;
				
	}

	public void addSubtree(FP_Node other, int minCount){
        for(FP_Node child : other.children){
            if(child.getCount() >= minCount) {
                if (this.has_child(child.getValue())) {
                    FP_Node thisChild = this.get_child(child.getValue());
                    thisChild.count += child.getCount();

                }
                else {
                    FP_Node newThisChild = new FP_Node(child.getValue(), child.getCount());
                    this.children.add(newThisChild);
                    newThisChild.parent = this;
                }
                this.get_child(child.getValue()).addSubtree(child, minCount);  // Recursive call on children
            }
            else{
                continue;
            }
        }
    }
	
}
