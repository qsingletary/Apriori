/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aprioriimplementation;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Katherine Lemmert and Quinn Singletary
 */
public class FrequentItemset<I> 
{
    private final List<Set<I>> frequentItemsets;
    private final Map<Set<I>, Integer> supCountMap;
    private final double minSupport;
    private final int numOfTransactions;

    FrequentItemset(List<Set<I>> frequentItemsets, Map<Set<I>, Integer> supCountMap, double minSupport, int transactionNum) 
    {
        this.frequentItemsets = frequentItemsets;
        this.supCountMap = supCountMap;
        this.minSupport = minSupport;
        this.numOfTransactions = transactionNum;
    }

    public List<Set<I>> getFrequentItemsets() 
    {
        return frequentItemsets;
    }

    public Map<Set<I>, Integer> getSupCountMap() 
    {
        return supCountMap;
    }

    public double getMinSupport() 
    {
        return minSupport;
    }

    public int getTransactionNum() 
    {
        return numOfTransactions;
    }

    public double getSupport(Set<I> itemset) 
    {
        return 1.0 * supCountMap.get(itemset) / numOfTransactions;
    }
}
