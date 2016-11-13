import Base.Frequent_Itemset;
import Base.Association_Rule;
import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

//import static org.junit.Assert.assertNotEquals
import static org.junit.Assert.*;

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

    @Test
    public void checkPowerSet() throws Exception{
        ArrayList<String> baseSet = new ArrayList<String>();
        baseSet.add("A");
        baseSet.add("B");
        baseSet.add("C");
        baseSet.add("D");
//        Association_Rule dummy = new Association_Rule(Null, Null, Null);
        Method powerSet = Association_Rule.class.getDeclaredMethod("powerSet", ArrayList.class);
        powerSet.setAccessible(true);
        ArrayList<ArrayList> powerSets = (ArrayList<ArrayList>) powerSet.invoke(null, baseSet);
        assertEquals(powerSets.size()*1.0, Math.pow(2.0, 4.0), 0.001);

        ArrayList<ArrayList> expected = new ArrayList<ArrayList>(Arrays.asList(

        ));
    }

    @Test
    public void checkMakeRules(){
        Frequent_Itemset.setDatabaseLength(100);

        Frequent_Itemset itemset1 = new Frequent_Itemset(new ArrayList<String>(Arrays.asList("Fish")), 50);
        Frequent_Itemset itemset2 = new Frequent_Itemset(new ArrayList<String>(Arrays.asList("Beef")), 40);
        Frequent_Itemset itemset3 = new Frequent_Itemset(new ArrayList<String>(Arrays.asList("Chicken")), 40);
        Frequent_Itemset itemset4 = new Frequent_Itemset(new ArrayList<String>(Arrays.asList("Fish", "Beef")), 40);
        Frequent_Itemset itemset5 = new Frequent_Itemset(new ArrayList<String>(Arrays.asList("Fish", "Chicken")), 40);
        Frequent_Itemset itemset6 = new Frequent_Itemset(new ArrayList<String>(Arrays.asList("Beef", "Chicken")), 40);
        Frequent_Itemset itemset7 = new Frequent_Itemset(
                new ArrayList<String>(Arrays.asList("Fish", "Beef", "Chicken")), 20);

        ArrayList<Frequent_Itemset> knownFreqentItemsets = new ArrayList<Frequent_Itemset>(Arrays.asList(
                itemset1, itemset2, itemset3, itemset4, itemset5, itemset6, itemset7
        ));
        ArrayList<Association_Rule> rules = Association_Rule.generateAssertionRules(knownFreqentItemsets);

        Association_Rule expectedRule1 = new Association_Rule(itemset1, itemset2, itemset4);
        Association_Rule expectedRule2 = new Association_Rule(itemset2, itemset1, itemset4);
        Association_Rule expectedRule3 = new Association_Rule(itemset4, itemset3, itemset7);
        Association_Rule expectedRule4 = new Association_Rule(itemset3, itemset4, itemset7);
        assertEquals(12, rules.size());
        boolean rule1in=false, rule2in=false, rule3in=false, rule4in=false;
        for(Association_Rule rule : rules){
            if(expectedRule1.toString().equals(rule.toString()))
                rule1in = true;
            if(expectedRule2.toString().equals(rule.toString()))
                rule2in = true;
            if(expectedRule3.toString().equals(rule.toString()))
                rule3in = true;
            if(expectedRule4.toString().equals(rule.toString()))
                rule4in = true;
        }

        assertTrue(rule1in);
        assertTrue(rule2in);
        assertTrue(rule3in);
        assertTrue(rule4in);
    }
}
