/* COPYRIGHT Creative Commons Attribution-Non Commercial 4.0 International (CC BY-NC 4.0) */

/**
 * A class for generating neuron montage summary table for all overlapped traces.
 *
 * @author Aslan Satary Dizaji <asataryd@umich.edu>
 * <https://sites.google.com/a/umich.edu/aslansdizaji>
 * <https://github.com/aslansd/TraceMontage>
 * @version 1.1 Created on 2015/05 (last modified on 2018/12)
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class NeuronMontageSummary extends javax.swing.JFrame {

    public NeuronMontageSummary(java.io.File filedataMontage, List<String[]> matchedOverlappedTraceAnalysis, List<String[]> tracedataNeuronBranch1, List<String[]> tracedataNeuronBranch2) {

        String[] columnNeuronMontageSummary = {"Data-set #1 Branch", "Data-set #1 Type", "Data-set #2 Branch", "Data-set #2 Type", "Number of Overlapped Points of Two Branches", "Overlapped Length / Data-set #1 Branch Length", "Overlapped Length / Data-set #2 Branch Length", "Relative Difference of Intensities among Pair of Channels", "Montaged Data-set Branch"};
        String[][] dataNeuronMontageSummary = new String[matchedOverlappedTraceAnalysis.size()][9];

        int counterSimilarType = 0;

        double[] overlappedPointsBranch12 = new double[matchedOverlappedTraceAnalysis.size()];
        double[] ratioOverlappedBranch1 = new double[matchedOverlappedTraceAnalysis.size()];
        double[] ratioOverlappedBranch2 = new double[matchedOverlappedTraceAnalysis.size()];
        
        double[][] colorOverlappedBranch = new double[matchedOverlappedTraceAnalysis.get(0)[7].split(", ").length][matchedOverlappedTraceAnalysis.size()];

        for (int i = 0; i < matchedOverlappedTraceAnalysis.size(); i++) {
            
            dataNeuronMontageSummary[i][1] = matchedOverlappedTraceAnalysis.get(i)[1];
            dataNeuronMontageSummary[i][3] = matchedOverlappedTraceAnalysis.get(i)[3];

            if (dataNeuronMontageSummary[i][1].equals(dataNeuronMontageSummary[i][3])) {
                counterSimilarType++;
            }

            dataNeuronMontageSummary[i][4] = matchedOverlappedTraceAnalysis.get(i)[4];
            overlappedPointsBranch12[i] = Double.parseDouble(matchedOverlappedTraceAnalysis.get(i)[4]);

            if (Double.parseDouble(matchedOverlappedTraceAnalysis.get(i)[5]) > 1) {
                dataNeuronMontageSummary[i][5] = "1.0000";
                ratioOverlappedBranch1[i] = 1;
            } else {
                dataNeuronMontageSummary[i][5] = matchedOverlappedTraceAnalysis.get(i)[5];
                ratioOverlappedBranch1[i] = Double.parseDouble(matchedOverlappedTraceAnalysis.get(i)[5]);
            }

            if (Double.parseDouble(matchedOverlappedTraceAnalysis.get(i)[6]) > 1) {
                dataNeuronMontageSummary[i][6] = "1.0000";
                ratioOverlappedBranch2[i] = 1;
            } else {
                dataNeuronMontageSummary[i][6] = matchedOverlappedTraceAnalysis.get(i)[6];
                ratioOverlappedBranch2[i] = Double.parseDouble(matchedOverlappedTraceAnalysis.get(i)[6]);
            }

            dataNeuronMontageSummary[i][7] = matchedOverlappedTraceAnalysis.get(i)[7];

            String[] colorOverlppedBranchTemp = matchedOverlappedTraceAnalysis.get(i)[7].split(", ");
            for (int j = 0; j < colorOverlppedBranchTemp.length; j++) {
                colorOverlappedBranch[j][i] = Double.parseDouble(colorOverlppedBranchTemp[j]);
            }

            dataNeuronMontageSummary[i][0] = matchedOverlappedTraceAnalysis.get(i)[0];
            dataNeuronMontageSummary[i][2] = matchedOverlappedTraceAnalysis.get(i)[2];
            dataNeuronMontageSummary[i][8] = matchedOverlappedTraceAnalysis.get(i)[8];

            String[] dataNeuronMontageSummaryTemp0 = matchedOverlappedTraceAnalysis.get(i)[0].split("-");
            dataNeuronMontageSummary[i][0] = dataNeuronMontageSummaryTemp0[0];
            for (int j = 1; j < dataNeuronMontageSummaryTemp0.length; j++) {
                dataNeuronMontageSummary[i][0] = dataNeuronMontageSummary[i][0].concat("--").concat(dataNeuronMontageSummaryTemp0[j]);
            }

            String[] dataNeuronMontageSummaryTemp2 = matchedOverlappedTraceAnalysis.get(i)[2].split("-");
            dataNeuronMontageSummary[i][2] = dataNeuronMontageSummaryTemp2[0];
            for (int j = 1; j < dataNeuronMontageSummaryTemp2.length; j++) {
                dataNeuronMontageSummary[i][2] = dataNeuronMontageSummary[i][2].concat("--").concat(dataNeuronMontageSummaryTemp2[j]);
            }

            String[] dataNeuronMontageSummaryTemp8 = matchedOverlappedTraceAnalysis.get(i)[8].split("-");
            dataNeuronMontageSummary[i][8] = dataNeuronMontageSummaryTemp8[0];
            for (int j = 1; j < dataNeuronMontageSummaryTemp8.length; j++) {
                dataNeuronMontageSummary[i][8] = dataNeuronMontageSummary[i][8].concat("--").concat(dataNeuronMontageSummaryTemp8[j]);
            }
        }

        JTable tableNeuronMontageSummary  = new JTable(dataNeuronMontageSummary, columnNeuronMontageSummary);

        this.add(new JScrollPane(tableNeuronMontageSummary));
        this.setTitle("Neuron Montage Summary Table");
        this.pack();
        this.setVisible(true);

        try {
            BufferedWriter tableNeuronMontageSummaryBW = new BufferedWriter(new FileWriter(new File(filedataMontage.getAbsoluteFile().getParentFile().getAbsolutePath(), filedataMontage.getName() + "-NeuronMontageSummary.xls")));

            for (int i = 0 ; i < tableNeuronMontageSummary.getColumnCount() ; i++) {
                tableNeuronMontageSummaryBW.write(tableNeuronMontageSummary.getColumnName(i));
                tableNeuronMontageSummaryBW.write("\t");
            }

            for (int i = 0 ; i < tableNeuronMontageSummary.getRowCount(); i++) {
                 tableNeuronMontageSummaryBW.newLine();

                 for (int j = 0 ; j < tableNeuronMontageSummary.getColumnCount(); j++) {
                     tableNeuronMontageSummaryBW.write(tableNeuronMontageSummary.getValueAt(i,j).toString());
                     tableNeuronMontageSummaryBW.write("\t");
                 }
            }

            tableNeuronMontageSummaryBW.newLine();
            tableNeuronMontageSummaryBW.newLine();
            tableNeuronMontageSummaryBW.write("Ratio of merged branches to all branches in data-set #1 :\n\t");
            tableNeuronMontageSummaryBW.write(String.format("%.2f", ((double) tableNeuronMontageSummary.getRowCount()) / ((double) tracedataNeuronBranch1.size())));
            tableNeuronMontageSummaryBW.write("\t");
            tableNeuronMontageSummaryBW.write("Ratio of merged branches to all branches in data-set #2 :");
            tableNeuronMontageSummaryBW.write("\t");
            tableNeuronMontageSummaryBW.write(String.format("%.2f", ((double) tableNeuronMontageSummary.getRowCount()) / ((double) tracedataNeuronBranch2.size())));

            tableNeuronMontageSummaryBW.newLine();
            tableNeuronMontageSummaryBW.newLine();
            tableNeuronMontageSummaryBW.write("Ratio of merged branches with similar types to all merged branches :");
            tableNeuronMontageSummaryBW.write("\t");
            tableNeuronMontageSummaryBW.write(String.format("%.2f", ((double) counterSimilarType) / ((double) tableNeuronMontageSummary.getRowCount())));

            tableNeuronMontageSummaryBW.newLine();
            tableNeuronMontageSummaryBW.newLine();
            tableNeuronMontageSummaryBW.write("Mean and Standard Error of the number of overlapped points of the pairs of merged branches from both data-sets :");
            tableNeuronMontageSummaryBW.write("\t");
            tableNeuronMontageSummaryBW.write(String.valueOf(Math.round(getMean(overlappedPointsBranch12))));
            tableNeuronMontageSummaryBW.write("\u00B1");
            tableNeuronMontageSummaryBW.write(String.valueOf(Math.round(getStandardError(overlappedPointsBranch12))));

            tableNeuronMontageSummaryBW.newLine();
            tableNeuronMontageSummaryBW.newLine();
            tableNeuronMontageSummaryBW.write("Mean and Standard Error of the ratio of the overlapped length to the whole length of the merged branches from data-set #1 :");
            tableNeuronMontageSummaryBW.write("\t");
            tableNeuronMontageSummaryBW.write(String.format("%.2f", getMean(ratioOverlappedBranch1)));
            tableNeuronMontageSummaryBW.write("\u00B1");
            tableNeuronMontageSummaryBW.write(String.format("%.2f", getStandardError(ratioOverlappedBranch1)));

            tableNeuronMontageSummaryBW.newLine();
            tableNeuronMontageSummaryBW.newLine();
            tableNeuronMontageSummaryBW.write("Mean and Standard Error of the ratio of the overlapped length to the whole length of the merged branches from data-set #2 :");
            tableNeuronMontageSummaryBW.write("\t");
            tableNeuronMontageSummaryBW.write(String.format("%.2f", getMean(ratioOverlappedBranch2)));
            tableNeuronMontageSummaryBW.write("\u00B1");
            tableNeuronMontageSummaryBW.write(String.format("%.2f", getStandardError(ratioOverlappedBranch2)));

            tableNeuronMontageSummaryBW.newLine();
            tableNeuronMontageSummaryBW.newLine();
            
            for (int i = 0; i < matchedOverlappedTraceAnalysis.get(0)[7].split(", ").length; i++) {
                tableNeuronMontageSummaryBW.write("Mean and Standard Error of the relative difference of intensities between the pair of channel #" + ((Integer)(i + 1)).toString() + " :");
                tableNeuronMontageSummaryBW.write("\t");
                tableNeuronMontageSummaryBW.write(String.format("%.2f", getMean(colorOverlappedBranch[i])));
                tableNeuronMontageSummaryBW.write("\u00B1");
                tableNeuronMontageSummaryBW.write(String.format("%.2f", getStandardError(colorOverlappedBranch[i])));
                tableNeuronMontageSummaryBW.write("\t");
            }

            tableNeuronMontageSummaryBW.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(final java.io.File filedataMontage, final ArrayList<String[]> matchedOverlappedTraceAnalysis, final ArrayList<String[]> tracedataNeuronBranch1, final ArrayList<String[]> tracedataNeuronBranch2) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NeuronMontageSummary(filedataMontage, matchedOverlappedTraceAnalysis, tracedataNeuronBranch1, tracedataNeuronBranch2).setVisible(true);
            }
        });
    }

    private double getMean(double[] data) {
        double sum = 0.0;
        
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
            
        return sum / data.length;
    }
    
    private double getStandardError(double[] data) {
        double mean = getMean(data);
        double sum = 0.0, t = 0;
        
        for (int i = 0; i < data.length; i++) { // (double a : data) {
            t = data[i] - mean;
            sum += t*t;
        }

        return Math.sqrt(sum) / data.length;
    }
}
