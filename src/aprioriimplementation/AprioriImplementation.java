/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aprioriimplementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 *
 * @author Katherine Lemmert and Quinn Singletary
 */
public class AprioriImplementation 
{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        AprioriGenerator<String> generator = new AprioriGenerator<>();

        List<Set<String>> itemsetList = new ArrayList<>();
        
        //open excel file and place data into map

/**     THIS IS TO BE REPLACED, BUT GOOD TEST CODE
        itemsetList.add(new HashSet<>(Arrays.asList("a", "b")));
        itemsetList.add(new HashSet<>(Arrays.asList("b", "c", "d")));
        itemsetList.add(new HashSet<>(Arrays.asList("a", "c", "d", "e")));
        itemsetList.add(new HashSet<>(Arrays.asList("a", "d", "e")));
        itemsetList.add(new HashSet<>(Arrays.asList("a", "b", "c")));

        itemsetList.add(new HashSet<>(Arrays.asList("a", "b", "c", "d")));
        itemsetList.add(new HashSet<>(Arrays.asList("a")));
        itemsetList.add(new HashSet<>(Arrays.asList("a", "b", "c")));
        itemsetList.add(new HashSet<>(Arrays.asList("a", "b", "d")));
        itemsetList.add(new HashSet<>(Arrays.asList("b", "c", "e")));
**/
        FrequentItemset<String> data = generator.generate(itemsetList, 0.2);
        int i = 1;
/**     DELETE THIS EVENTUALLY, NOT PRINTING FORMAT I WANT TO USE
        for (Set<String> itemset : data.getFrequentItemsetList()) {
            System.out.printf("%2d: %9s, support: %1.1f\n",
                              i++, 
                              itemset,
                              data.getSupport(itemset));
        }
**/
    }
    
}
