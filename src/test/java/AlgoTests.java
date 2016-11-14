/**
 * Created by Ian on 11/9/2016.
 */
import FPGrowth.FP_Growth_Main;
import FPGrowth.FP_Node;
import FPGrowth.FP_Tree;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import static org.junit.Assert.*;

public class AlgoTests {
    @Test
    public void checkReadBaskets() throws Exception{
        Method readBaskets = FP_Tree.class.getDeclaredMethod("readBaskets", FileReader.class);
        readBaskets.setAccessible(true);
        ArrayList<ArrayList> baskets = (ArrayList<ArrayList>) readBaskets.invoke(null, new FileReader("src/test/testDataFiles/test_baskets.txt"));
        assertEquals(baskets.size(), 7);
        ArrayList<String> expectedBasket1 = new ArrayList<String>(Arrays.asList("a", "b", "c"));
        ArrayList<String> expectedBasket7 = new ArrayList<String>(Arrays.asList("a", "c"));
        ArrayList<String> expectedBasket5 = new ArrayList<String>(Arrays.asList("alpha", "beta"));

        assertArrayEquals(expectedBasket1.toArray(), baskets.get(0).toArray());
        assertArrayEquals(expectedBasket7.toArray(), baskets.get(6).toArray());
        assertArrayEquals(expectedBasket5.toArray(), baskets.get(4).toArray());
    }

    @Test
    public void checkFPTreeStructure() throws Exception{
        FP_Tree fpTree = buildDefaultTree();

        Field root = FP_Tree.class.getDeclaredField("root");
        root.setAccessible(true);
        FP_Node rootNode = (FP_Node) root.get(fpTree);
        assertTrue(rootNode.has_child("a"));
        assertTrue(rootNode.has_child("alpha"));
        assertTrue(rootNode.has_child("b"));

        FP_Node aNode = rootNode.get_child("a");
        assertTrue(aNode.has_child("b"));
        assertTrue(aNode.has_child("c"));

        FP_Node abNode = aNode.get_child("b");
        assertTrue(abNode.has_child("c"));

        FP_Node abcNode = abNode.get_child("c");


        FP_Node bNode = rootNode.get_child("b");
        FP_Node bcNode = bNode.get_child("c");

        Field sibling = FP_Node.class.getDeclaredField("sibling");
        sibling.setAccessible(true);
        FP_Node bcSibling = (FP_Node) sibling.get(bcNode);
        assertEquals(abcNode, bcSibling);
//        assertTrue(rootNode.has_child("b"));
    }

    @Test
    public void checkFPTreeHeaderTable() throws Exception{

        FP_Tree fpTree = buildDefaultTree();

        Field header_table = FP_Tree.class.getDeclaredField("header_table");
        header_table.setAccessible(true);

        Hashtable<String, FP_Node> fpHeaderTable = (Hashtable<String, FP_Node>) header_table.get(fpTree);
        FP_Node cHeaderNode = fpHeaderTable.get("c");
        assertEquals(sumSiblingNodes(cHeaderNode), 3);

        FP_Node bHeaderNode = fpHeaderTable.get("b");
        assertEquals(sumSiblingNodes(bHeaderNode), 3);

        FP_Node aHeaderNode = fpHeaderTable.get("a");
        assertEquals(sumSiblingNodes(aHeaderNode), 5);

    }

    private static int sumSiblingNodes(FP_Node headNode) throws Exception{
        Field sibling = FP_Node.class.getDeclaredField("sibling");
        sibling.setAccessible(true);

        FP_Node curNode = headNode;
        int sum = curNode.getCount();
        while(sibling.get(curNode) != null) {
            curNode = (FP_Node) sibling.get(curNode);
            sum += curNode.getCount();
        }
        return sum;
    }

    private static FP_Tree buildDefaultTree(){
        ArrayList<String> expectedBasket1 = new ArrayList<String>(Arrays.asList("a", "b", "c"));
        ArrayList<String> expectedBasket2 = new ArrayList<String>(Arrays.asList("b", "c", "d"));
        ArrayList<String> expectedBasket3 = new ArrayList<String>(Arrays.asList("a"));
        ArrayList<String> expectedBasket4 = new ArrayList<String>(Arrays.asList("a"));
        ArrayList<String> expectedBasket5 = new ArrayList<String>(Arrays.asList("alpha", "beta"));
        ArrayList<String> expectedBasket6 = new ArrayList<String>(Arrays.asList("a", "b"));
        ArrayList<String> expectedBasket7 = new ArrayList<String>(Arrays.asList("a", "c"));

        ArrayList<ArrayList> baskets = new ArrayList<ArrayList>(Arrays.asList(expectedBasket1, expectedBasket2,
                expectedBasket3, expectedBasket4, expectedBasket5, expectedBasket6, expectedBasket7));

        return new FP_Tree(baskets, 1);
    }

    @Test
    public void checkConditionalTreeStructure() throws Exception{
        FP_Tree fpTree = buildDefaultTree();
        Field root = FP_Tree.class.getDeclaredField("root");
        root.setAccessible(true);

        FP_Tree condTreeB = fpTree.Conditional_Tree("b", 1);
        FP_Node condRootNodeB = (FP_Node) root.get(condTreeB);
        assertTrue(condRootNodeB.has_child("a"));
        assertTrue(condRootNodeB.has_child("c")); // probably shouldn't be true. Should be child of a

        FP_Tree condTree = fpTree.Conditional_Tree("a", 1);
        FP_Node condRootNode = (FP_Node) root.get(condTree);
        assertTrue(condRootNode.has_child("b"));
        assertTrue(condRootNode.has_child("c"));

        FP_Node bNode = condRootNode.get_child("b");
        assertTrue(bNode.has_child("c"));

        FP_Node bcNode = bNode.get_child("c");

        FP_Node cNode = condRootNode.get_child("c");

        Field sibling = FP_Node.class.getDeclaredField("sibling");
        sibling.setAccessible(true);
        FP_Node bcSibling = (FP_Node) sibling.get(bcNode);
        assertEquals(cNode, bcSibling);
    }
}
