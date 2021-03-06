/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WheatGBS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Arrays;
import utils.IOUtils;

/**
 *
 * @author feilu
 */
public class WheatGBSGocp1 {
    
    public WheatGBSGocp1 () {
        this.testLibraryUniformity();
    }
    
    public void testLibraryUniformity () {
        String barcodeFileS = "/Users/Aoyue/Documents/IGDB/4-PHD/wheat/GBS取样/02IlluminaSeq/barcode/20180601_GBSLibrarybarcode.txt";
        String r1FileS = "/Users/Aoyue/Downloads/20180601-51-NEB12_TKD180600155_1.clean.fq";
        String r2FileS = "/Users/Aoyue/Downloads/20180601-51-NEB12_TKD180600155_1.clean.fq";
        String outFileS = "/Users/Aoyue/Documents/IGDB/4-PHD/wheat/GBS取样/02IlluminaSeq/result/uniformity1.txt";
        
        PEBarcodeParsercp pbp = new PEBarcodeParsercp(barcodeFileS);
        String[] taxa = pbp.getTaxa();
        int[] taxaCnt = new int[taxa.length];
        int nullCnt = 0;
        try {
            BufferedReader br1 = IOUtils.getTextReader(r1FileS);
            BufferedReader br2 = IOUtils.getTextReader(r2FileS);
            String temp1 = null;
            String temp2 = null;
            while ((temp1 = br1.readLine()) != null) {
                temp2 = br2.readLine();
                String taxon = pbp.getTaxonFromReads(br1.readLine(), br2.readLine());
                br1.readLine();br1.readLine();
                br2.readLine();br2.readLine();
                if (taxon == null) {
                    nullCnt++;
                }
                else {
                    int index = Arrays.binarySearch(taxa, taxon);
                    taxaCnt[index]++;
                }
            }
            br1.close();
            br2.close();
            BufferedWriter bw = IOUtils.getTextWriter(outFileS);
            bw.write("Taxa\tCount");
            bw.newLine();
            for (int i = 0; i < taxa.length; i++) {
                bw.write(taxa[i]+"\t"+String.valueOf(taxaCnt[i]));
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    public static void main (String[] args) {
        new WheatGBSGocp1 ();
    }
    
}
