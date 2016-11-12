import Base.Frequent_Itemset;
import Base.Association_Rule;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.assertNotEquals
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
/**
 * Created by Ian on 11/12/2016.
 */
public class BaseTests {
    @Test
    public void checkBasicConstructor(){
        ArrayList<String> itemset = new ArrayList<String>(Arrays.asList("Fish", "Beef", "beef"));
        int supportCount = 20;
        Frequent_Itemset myItemsetObject = new Frequent_Itemset(itemset, supportCount);
        assertNotNull(myItemsetObject.itemset);
        assertNotEquals(myItemsetObject.itemset, itemset);  // these should be clones
    }

    @Test
    public void checkConstructorSorting(){
        ArrayList<String> itemset = new ArrayList<String>(Arrays.asList("Fish", "Beef", "beef"));
        int supportCount = 20;
        Frequent_Itemset myItemsetObject = new Frequent_Itemset(itemset, supportCount);
        ArrayList<String> sortedItemset = new ArrayList<String>(Arrays.asList("Beef", "beef", "Fish"));
        assertArrayEquals(myItemsetObject.itemset.toArray(), sortedItemset.toArray());
    }

    @Test
    public void checkMatching(){
        ArrayList<String> itemset = new ArrayList<String>(Arrays.asList("Fish", "beef", "Beef", "Chicken"));
        ArrayList<String> itemset2 = new ArrayList<String>(Arrays.asList("Chicken", "beef", "Beef", "Fish"));
        Frequent_Itemset fitemset = new Frequent_Itemset(itemset, 30);
        Frequent_Itemset fitemset2 = new Frequent_Itemset(itemset2, 20);

        assertArrayEquals(fitemset.itemset.toArray(), fitemset2.itemset.toArray());
    }

    @Test
    public void checkToString(){
        ArrayList<String> itemset = new ArrayList<String>(Arrays.asList("Fish", "Beef", "Chicken"));
        Frequent_Itemset frequent_itemset = new Frequent_Itemset(itemset, 1);
        assertEquals(frequent_itemset.toString(), "{Beef, Chicken, Fish}");
    }

    @Test
    public void checkRuleSupport(){
        Frequent_Itemset.setDatabaseLength(100);
        Frequent_Itemset fitemset = new Frequent_Itemset(
                new ArrayList<String>(Arrays.asList("Fish", "Beef", "beef")), 20);
        Frequent_Itemset litemset = new Frequent_Itemset(new ArrayList<String>(Arrays.asList("Fish")), 50);
        Frequent_Itemset ritemset = new Frequent_Itemset(new ArrayList<String>(Arrays.asList("Beef", "beef")), 40);
        Association_Rule rule = new Association_Rule(litemset, ritemset, fitemset);
        assertEquals(rule.getSupportCount(), 20);
        assertEquals(rule.getSupport(), 0.2, 0.0001);
    }

    @Test
    public void checkRuleConfidence(){
        Frequent_Itemset.setDatabaseLength(100);
        Frequent_Itemset fitemset = new Frequent_Itemset(
                new ArrayList<String>(Arrays.asList("Fish", "Beef", "beef")), 20);
        Frequent_Itemset litemset = new Frequent_Itemset(new ArrayList<String>(Arrays.asList("Fish")), 50);
        Frequent_Itemset ritemset = new Frequent_Itemset(new ArrayList<String>(Arrays.asList("Beef", "Chicken")), 40);
        Association_Rule rule = new Association_Rule(litemset, ritemset, fitemset);
        assertEquals(rule.getConfidence(), 20/(float)50, 0.0001);
    }

    @Test
    public void checkRuleToString(){
        Frequent_Itemset.setDatabaseLength(100);
        Frequent_Itemset fitemset = new Frequent_Itemset(
                new ArrayList<String>(Arrays.asList("Fish", "Beef", "Chicken")), 20);
        Frequent_Itemset litemset = new Frequent_Itemset(new ArrayList<String>(Arrays.asList("Fish")), 50);
        Frequent_Itemset ritemset = new Frequent_Itemset(new ArrayList<String>(Arrays.asList("Beef", "Chicken")), 40);
        Association_Rule rule = new Association_Rule(litemset, ritemset, fitemset);
        assertEquals(rule.toString(), "{Fish} => {Beef, Chicken}");
    }
}
