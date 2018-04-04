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
    private final List<Set<I>> frequentItemsetList;
    private final Map<Set<I>, Integer> supportCountMap;
    private final double minimumSupport;
    private final int numberOfTransactions;

    FrequentItemset(List<Set<I>> frequentItemsetList, Map<Set<I>, Integer> supportCountMap, double minimumSupport, int transactionNumber) 
    {
        this.frequentItemsetList = frequentItemsetList;
        this.supportCountMap = supportCountMap;
        this.minimumSupport = minimumSupport;
        this.numberOfTransactions = transactionNumber;
    }

    public List<Set<I>> getFrequentItemsetList() 
    {
        return frequentItemsetList;
    }

    public Map<Set<I>, Integer> getSupportCountMap() 
    {
        return supportCountMap;
    }

    public double getMinimumSupport() 
    {
        return minimumSupport;
    }

    public int getTransactionNumber() 
    {
        return numberOfTransactions;
    }

    public double getSupport(Set<I> itemset) 
    {
        return 1.0 * supportCountMap.get(itemset) / numberOfTransactions;
    }
}
