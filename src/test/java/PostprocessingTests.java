import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//import java.lang.Math
import Postprocessing.Postprocessing_Main;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Ian on 11/9/2016.
 */
public class PostprocessingTests {
    @Test
    public void powerSetTest(){
        ArrayList<String> baseSet = new ArrayList<String>();
        baseSet.add("A");
        baseSet.add("B");
        baseSet.add("C");
        baseSet.add("D");

        ArrayList<ArrayList> powerSet = Postprocessing_Main.powerSet(baseSet);
        assertEquals(powerSet.size()*1.0, Math.pow(2.0, 4.0), 0.001);


        ArrayList<ArrayList> expected = new ArrayList<ArrayList>();
    }

////    public static Object
//    public static HashSet iterableToSet(Iterable it){
//        HashSet set = new HashSet();
//        for(Object item : it)
//            set.add(item);
//        return set;
//    }
//    public static void assertSetEqual(Iterable<Object> l, Iterable<Object> r){
//        HashSet setL = new HashSet();
//        for(Object item : l)
//            setL.add(item);
//        HashSet setR = new HashSet();
//        for(Object item : r)
//            setR.add(item);
//        assertTrue(setL.equals(setR));
//    }
}
