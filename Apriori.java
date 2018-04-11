package apriori;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Quinn Singletary
 * @author Katherine Lemmert April 4, 2018
 */
public class Apriori {

    /* The list of current itemsets */
    private HashMap<Integer, String[]> transactions;
    /* Total number of transactions in the database */
    private int totalTransactions;
    /* The input file containing a list of transactions */
    private String transaFile;
    /* Total number of items in the database */
    private int totalItems;
    /* The minimum support count threshold */
    private int minSup;

    public static void main(String[] args) throws IOException {
        Apriori ap = new Apriori();
        ap.process();
    }

    public void process() throws IOException {
        configure();
        
    }

    /*
     * This method generates the totalTransactions and totalItems
     */
    public void configure() throws IOException {
        transactions = new HashMap<>();
        transaFile = "database4.txt";
        String readLine = null;
        String[] data = {};
        int tid = 1;

        /* Declare arrays for each tid in the HashMap */
        for (int i = 0; i < totalTransactions; i++) {
            transactions.put(i, new String[]{});
        }

        /* Read in the input transaction file data */
        try {
            BufferedReader br = new BufferedReader(new FileReader(transaFile));
            /* Generate totalTransactions; store arrays of data in HashMap */
            while ((readLine = br.readLine()) != null) {
                data = readLine.split(",");
                transactions.put(tid, data);
                tid++;
                totalTransactions++;
            }

            /* Store the total number of items; subtract 1 to disregard tid */
            totalItems = data.length - 1;

        } catch (FileNotFoundException e) {
        }
    }
    
    
}

