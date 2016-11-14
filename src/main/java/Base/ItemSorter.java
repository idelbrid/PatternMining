package Base;

import java.util.Comparator;

/**
 * Created by Ian on 11/13/2016.
 */
public class ItemSorter implements Comparator<String> {
    public ItemSorter(){

    }
    public int compare(String s1, String s2){
        return s1.compareTo(s2);
    }
}
