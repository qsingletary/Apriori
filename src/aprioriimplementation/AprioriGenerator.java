/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aprioriimplementation;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;


/**
 *
 * @author Katherine Lemmert and Quinn Singletary
 */
public class AprioriGenerator<I> 
{
    //global variables
    protected int candidateCount = 0;
    protected int listCount = 1;
    
    /**
     * This generates a list of frequent itemsets
     * 
     * @param transactionList: the list of transactions to prune
     * @param minSupport: minimum support
     * @return frequentItemset<>: result from finding the frequent itemsets
     */
    public FrequentItemset<I> generate(List<Set<I>> transactionList, double minSupport) 
    {
        Objects.requireNonNull(transactionList, "The list of itemsets is empty.");
        checkSupport(minSupport);
        
        //check to see if the lsit is empty
        if (transactionList.isEmpty()) 
        {
            return null;
        }

        //this will map each itemset to its support count (number of times an itemset will appear in the transaction list)
        Map<Set<I>, Integer> supCountMap = new HashMap<>();

        //get the list of 1-frequent itemset
        List<Set<I>> frequentItems = findFrequentItems(transactionList, supCountMap, minSupport);

        //maps to the list of frequent k-itemsets. 
        Map<Integer, List<Set<I>>> map = new HashMap<>();
        map.put(1, frequentItems);

        //k is an iterator for the candidates
        int k = 1;

        do 
        {
            ++k;

            //generate the candidates.
            List<Set<I>> candidList = generateCandidates(map.get(k - 1));

            //for each transaction in the list, create a candidate list and store in the support map
            for (Set<I> transaction : transactionList) 
            {
                List<Set<I>> candidList2 = subset(candidList, transaction);
                
                for (Set<I> itemset : candidList2) 
                {
                    //map keys to values
                    supCountMap.put(itemset, supCountMap.getOrDefault(itemset, 0) + 1);
                }
            }
            //get next itemset
            map.put(k, getNextItemsets(candidList, supCountMap, minSupport, transactionList.size()));

        } while (!map.get(k).isEmpty());
        
        return new FrequentItemset<>(extractFrequentItemsets(map), supCountMap, minSupport, transactionList.size());
    }

    /**
     * concatenates all the lists of frequent itemsets into one arraylist
     * 
     * @param  map: maps itemset size to list of frequent itemsets of that specific size
     * @return the list of all frequent itemsets.
     */
    private List<Set<I>> extractFrequentItemsets(Map<Integer, List<Set<I>>> map) 
    {
        List<Set<I>> temp = new ArrayList<>();
        //goes through all the lists
        for (List<Set<I>> itemsetList : map.values()) 
        {
            //add to new list
            temp.addAll(itemsetList);
        }

        return temp;
    }

    /**
     * gathers all frequent candidate itemsets into a single list
     * 
     * @param candidList: list of candidate itemsets
     * @param supCountMap: map of each itemset to its support count
     * @param minSupport: min support
     * @param transactions: total num of transactions
     * @return temp: arraylist of frequent itemsets for candidate list
     */
    private List<Set<I>> getNextItemsets(List<Set<I>> candidList, Map<Set<I>, Integer> supCountMap, double minSupport, int transactions) 
    {
        List<Set<I>> temp = new ArrayList<>(candidList.size());
        
        //go through itemlist
        for (Set<I> itemset : candidList) 
        {
            if (supCountMap.containsKey(itemset)) 
            {
                int supCount = supCountMap.get(itemset);
                double support = 1.0 * supCount / transactions;

                if (support >= minSupport) 
                {
                    temp.add(itemset);
                }
            }
        }
        
        return temp;
    }

    /**
     * makes list of itemsets that are subsets of transaction
     * 
     * @param candidList: the list of candidate itemsets
     * @param transaction:   the transaction to test against
     * @return temp: arraylist of itemsets that are subsets of transaction
     */
    private List<Set<I>> subset(List<Set<I>> candidList, Set<I> transaction) 
    {
        List<Set<I>> temp = new ArrayList<>(candidList.size());

        for (Set<I> candidate : candidList) 
        {
            //compares transaction with the candidate itemsets
            if (transaction.containsAll(candidate)) 
            {
                temp.add(candidate);
            }
        }

        return temp;
    }

    /**
     * makes the next candidates (F(k - 1) * F(k - 1) method)
     * 
     * @param itemsetList: the list of source itemsets, each of size k.
     * @return temp: arraylist of candidates each of size k + 1.
     */
    private List<Set<I>> generateCandidates(List<Set<I>> itemsetList) 
    {
        List<List<I>> tempList = new ArrayList<>(itemsetList.size());
        
        //add to temp list containing source itemsets
        for (Set<I> itemset : itemsetList) 
        {
            List<I> l = new ArrayList<>(itemset);
            Collections.<I>sort(l, ITEM_COMPARATOR);
            tempList.add(l);
        }
        
        //get size of list
        int size = tempList.size();

        List<Set<I>> temp = new ArrayList<>(size);

        //merge the itemsets together and add them if they are candidate
        for (int i = 0; i < size; ++i) 
        {
            for (int j = i + 1; j < size; ++j) 
            {
                Set<I> candidate = mergeItemsets(tempList.get(i), tempList.get(j));

                if (candidate != null) 
                {
                    temp.add(candidate);
                }
            }
        }
        //print out Ci and Li (candidate and list)
        if(candidateCount > 1)
        {
            System.out.printf("C%d: %9s\n", candidateCount, itemsetList);
        }
        candidateCount++;
        System.out.printf("L%d: %9s\n", listCount, itemsetList);
        listCount++;
        
        return temp;
    }

    /**
     * constructs next itemset candidate
     *
     * @param set1: first itemset list
     * @param set2: second itemset list
     * @return temp: arraylist of merged candidate else null if didn't work
     */
    private Set<I> mergeItemsets(List<I> set1, List<I> set2) 
    {
        int length = set1.size();

        //if set1 and set2 are not equal, return null
        for (int i = 0; i < length - 1; ++i) 
        {
            if (!set1.get(i).equals(set2.get(i))) 
            {
                return null;
            }
        }

        //if the lengths of the sets are equal, return null
        if (set1.get(length - 1).equals(set2.get(length - 1))) 
        {
            return null;
        }

        Set<I> temp = new HashSet<>(length + 1);

        //else add the first set to the temp arraylist
        for (int i = 0; i < length - 1; ++i) 
        {
            temp.add(set1.get(i));
        }

        //merge
        temp.add(set1.get(length - 1));
        temp.add(set2.get(length - 1));
        
        return temp;
    }

    /**
     * a comparator interface used to order the objects
     * 
     * @return sorted objects
     */
    private static final Comparator ITEM_COMPARATOR = new Comparator() 
    {

        @Override
        public int compare(Object o1, Object o2) 
        {
            return ((Comparable) o1).compareTo(o2);
        }

    };

    /**
     * makes frequent itemsets of size 1.
     * 
     * @param itemsetList: the whole database
     * @param supCountMap: map of each itemset to its support count
     * @param minSupport: min support.
     * @return frequentItemsets: the list of frequent 1-itemsets.
     */
    private List<Set<I>> findFrequentItems(List<Set<I>> itemsetList, Map<Set<I>, Integer> supCountMap, double minSupport) {
        Map<I, Integer> map = new HashMap<>();

        //count the num of sup counts of each item.
        for (Set<I> itemset : itemsetList) 
        {
            for (I item : itemset) 
            {
                Set<I> temp = new HashSet<>(1);
                temp.add(item);

                if (supCountMap.containsKey(temp)) 
                {
                    supCountMap.put(temp, supCountMap.get(temp) + 1);
                } 
                else 
                {
                    supCountMap.put(temp, 1);
                }

                map.put(item, map.getOrDefault(item, 0) + 1);
            }
        }
        //this prints database
        System.out.printf("C%d: %9s\n", candidateCount, itemsetList);
        candidateCount++;
        List<Set<I>> frequentItemsets = new ArrayList<>();

        //prune
        for (Map.Entry<I, Integer> entry : map.entrySet()) 
        {
            if (1.0 * entry.getValue() / map.size() >= minSupport) 
            {
                Set<I> itemset = new HashSet<>(1);
                itemset.add(entry.getKey());
                frequentItemsets.add(itemset);
            }
        }

        return frequentItemsets;
    }

    /**
     * checks to make sure that the min support isn't too big, too small, or invalid input
     * 
     * @param support: support percentage
     */
    private void checkSupport(double support) 
    {
        if (Double.isNaN(support)) 
        {
            throw new IllegalArgumentException("The input support is invalid.");
        }

        if (support > 1.0) 
        {
            throw new IllegalArgumentException(
                    "The input support is too large: " + support + ", " +
                    "should be at most 1.0");
        }

        if (support < 0.0) 
        {
            throw new IllegalArgumentException(
                    "The input support is too small: " + support + ", " +
                    "should be at least 0.0");
        }
    }
}
