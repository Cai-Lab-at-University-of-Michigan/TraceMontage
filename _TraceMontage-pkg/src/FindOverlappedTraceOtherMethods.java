/* COPYRIGHT Creative Commons Attribution-Non Commercial 4.0 International (CC BY-NC 4.0) */

/**
 * A class for finding pairs of overlapped traces from one image based on four criteria.
 *
 * @author Aslan Satary Dizaji <asataryd@umich.edu>
 * <https://sites.google.com/a/umich.edu/aslansdizaji>
 * <https://github.com/aslansd/TraceMontage>
 * @version 1.1 Created on 2015/05 (last modified on 2018/12)
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindOverlappedTraceOtherMethods {
    
    private List<String[]> matchedOverlappedTraceBranch = new ArrayList<>();
    private List<String[]> matchedOverlappedTraceAnalysis = new ArrayList<>();
    private List<Integer[][]> matchedOverlappedTraceCoordinate = new ArrayList<>();

    private List<Integer[][]> tracedataNeuronCoordinateModified1 = new ArrayList<>();
    private List<String[]> tracedataNeuronTypeModified1 = new ArrayList<>();
    private List<Integer[]> tracedataNeuronRadiusModified1 = new ArrayList<>();
    private List<Integer[]> tracedataNeuronSynapseModified1 = new ArrayList<>();
    private List<String[]> tracedataNeuronConnectionModified1 = new ArrayList<>();

    public FindOverlappedTraceOtherMethods(Integer montageMethodFlag, List<String[]> tracedataNeuronBranch1, List<Double[]> tracedataNeuronColor1, List<Integer[][]> tracedataNeuronCoordinate1, List<String[]> tracedataNeuronType1, List<Integer[]> tracedataNeuronRadius1, List<Integer[]> tracedataNeuronSynapse1, List<String[]> tracedataNeuronConnection1, List<String[]> tracedataNeuronBranch2, List<Double[]> tracedataNeuronColor2, List<Integer[][]> tracedataNeuronCoordinate2, List<String[]> tracedataNeuronType2, List<Integer[]> tracedataNeuronRadius2, List<Integer[]> tracedataNeuronSynapse2, List<String[]> tracedataNeuronConnection2, Double colorToleranceValue, Integer positionTolerancePixelValue, Integer positionTolerancePointValue, Double positionToleranceRatioValue) {

        ArrayList<String[]> tracedataNeuronBranchSelected1 = new ArrayList<String[]>();
        ArrayList<String[]> tracedataNeuronBranchSelected2 = new ArrayList<String[]>();

        for (int i = 0; i < tracedataNeuronCoordinate1.size(); i++) {

            tracedataNeuronCoordinateModified1.add(tracedataNeuronCoordinate1.get(i));
            tracedataNeuronTypeModified1.add(tracedataNeuronType1.get(i));
            tracedataNeuronRadiusModified1.add(tracedataNeuronRadius1.get(i));
            tracedataNeuronSynapseModified1.add(tracedataNeuronSynapse1.get(i));
            tracedataNeuronConnectionModified1.add(tracedataNeuronConnection1.get(i));

            if (tracedataNeuronBranch1.get(i)[0].equals("Neurite")) {
                String[] tracedataNeuronBranchSelectedTemp = new String[3];
                tracedataNeuronBranchSelectedTemp[0] = Integer.toString(i);
                tracedataNeuronBranchSelectedTemp[1] = tracedataNeuronBranch1.get(i)[1];
                tracedataNeuronBranchSelectedTemp[2] = tracedataNeuronBranch1.get(i)[2].split("-")[1];
                tracedataNeuronBranchSelected1.add(tracedataNeuronBranchSelectedTemp);
            }
        }

        for (int i = 0; i < tracedataNeuronCoordinate2.size(); i++) {

            if (tracedataNeuronBranch2.get(i)[0].equals("Neurite")) {
                String[] tracedataNeuronBranchSelectedTemp = new String[3];
                tracedataNeuronBranchSelectedTemp[0] = Integer.toString(i);
                tracedataNeuronBranchSelectedTemp[1] = tracedataNeuronBranch2.get(i)[1];
                tracedataNeuronBranchSelectedTemp[2] = tracedataNeuronBranch2.get(i)[2].split("-")[1];
                tracedataNeuronBranchSelected2.add(tracedataNeuronBranchSelectedTemp);
            }
        }

        System.out.println("Size of tracedataNeuronBranchSelected1: " + tracedataNeuronBranchSelected1.size());
        System.out.println("Size of tracedataNeuronBranchSelected2: " + tracedataNeuronBranchSelected2.size());

        ArrayList<String[]> matchedOverlappedTraceBranchOriginal = new ArrayList<String[]>();
        ArrayList<String[]> matchedOverlappedTraceAnalysisOriginal = new ArrayList<String[]>();
        ArrayList<Integer[][]> matchedOverlappedTraceCoordinateOriginal = new ArrayList<Integer[][]>();

        Integer colorDimension = tracedataNeuronColor1.get(0).length;
        Double positionTolerancePixelValueSquare = Math.pow(positionTolerancePixelValue, 2);
        
        for (int i = 0; i < tracedataNeuronBranchSelected1.size(); i++) {
            for (int j = 0; j < tracedataNeuronBranchSelected2.size(); j++) {

                boolean  flag = true;

                for (int k = 0; k < colorDimension; k++) {
                    if ((Math.abs(tracedataNeuronColor1.get(Integer.parseInt(tracedataNeuronBranchSelected1.get(i)[0]))[k] - tracedataNeuronColor2.get(Integer.parseInt(tracedataNeuronBranchSelected2.get(j)[0]))[k]) / Math.max(tracedataNeuronColor1.get(Integer.parseInt(tracedataNeuronBranchSelected1.get(i)[0]))[k], tracedataNeuronColor2.get(Integer.parseInt(tracedataNeuronBranchSelected2.get(j)[0]))[k])) > colorToleranceValue) {
                        flag = false;
                        break;
                    }
                }

                if (montageMethodFlag == 3 && Integer.parseInt(tracedataNeuronBranchSelected1.get(i)[0]) == Integer.parseInt(tracedataNeuronBranchSelected2.get(j)[0])) {
                    flag = false;
                }

                if (flag == true) {

                    Integer[][] coordinateTemp1 = tracedataNeuronCoordinate1.get(Integer.parseInt(tracedataNeuronBranchSelected1.get(i)[0]));
                    Integer[][] coordinateTemp2 = tracedataNeuronCoordinate2.get(Integer.parseInt(tracedataNeuronBranchSelected2.get(j)[0]));

                    ArrayList<Integer[]> indexCoordinateTemp = new ArrayList<Integer[]>();

                    Integer index2 = 0;
                    Double distance = 0.0;
                    Double minimumDistance = Double.POSITIVE_INFINITY;

                    for (int l = 0; l < coordinateTemp2.length; l++) {

                        distance = Math.pow(coordinateTemp1[0][0] - coordinateTemp2[l][0], 2) + Math.pow(coordinateTemp1[0][1] - coordinateTemp2[l][1], 2) + Math.pow(coordinateTemp1[0][2] - coordinateTemp2[l][2], 2);
                        if (distance <= minimumDistance) {
                            index2 = l;
                            minimumDistance = distance;
                        }
                    }

                    if (minimumDistance <= positionTolerancePixelValueSquare) {

                        Integer[] index12 = new Integer[2];
                        index12[0] = 0;
                        index12[1] = index2;
                        indexCoordinateTemp.add(index12);
                    }

                    for (int k = 1; k < coordinateTemp1.length; k++) {

                        Integer lowerBound = index2 - Math.round((float) positionTolerancePointValue / 2);
                        if (lowerBound < 0) {
                            lowerBound = 0;
                        }

                        Integer upperBound = index2 + Math.round((float) positionTolerancePointValue / 2);
                        if (upperBound > coordinateTemp2.length - 1) {
                            upperBound = coordinateTemp2.length - 1;
                        }

                        index2 = 0;
                        distance = 0.0;
                        minimumDistance = Double.POSITIVE_INFINITY;

                        for (int l = lowerBound; l <= upperBound; l++) {

                            distance = Math.pow(coordinateTemp1[k][0] - coordinateTemp2[l][0], 2) + Math.pow(coordinateTemp1[k][1] - coordinateTemp2[l][1], 2) + Math.pow(coordinateTemp1[k][2] - coordinateTemp2[l][2], 2);
                            if (distance <= minimumDistance) {
                                index2 = l;
                                minimumDistance = distance;
                            }
                        }

                        if (minimumDistance <= positionTolerancePixelValueSquare) {

                            Integer[] index12 = new Integer[2];
                            index12[0] = k;
                            index12[1] = index2;
                            indexCoordinateTemp.add(index12);
                        }
                    }

                    if (indexCoordinateTemp.size() >= positionTolerancePointValue || ((double) indexCoordinateTemp.size()) / ((double) Math.min(coordinateTemp1.length, coordinateTemp2.length)) >= positionToleranceRatioValue) {

                        String[] matchedOverlappedTraceBranchTemp = new String[6];
                        matchedOverlappedTraceBranchTemp[0] = tracedataNeuronBranchSelected1.get(i)[0];
                        matchedOverlappedTraceBranchTemp[1] = tracedataNeuronBranchSelected1.get(i)[1];
                        matchedOverlappedTraceBranchTemp[2] = tracedataNeuronBranchSelected1.get(i)[1].concat("-").concat(tracedataNeuronBranchSelected1.get(i)[2]);
                        matchedOverlappedTraceBranchTemp[3] = tracedataNeuronBranchSelected2.get(j)[0];
                        matchedOverlappedTraceBranchTemp[4] = tracedataNeuronBranchSelected2.get(j)[1];
                        matchedOverlappedTraceBranchTemp[5] = tracedataNeuronBranchSelected2.get(j)[1].concat("-").concat(tracedataNeuronBranchSelected2.get(j)[2]);
                        matchedOverlappedTraceBranchOriginal.add(matchedOverlappedTraceBranchTemp);

                        String[] matchedOverlappedTraceAnalysisTemp = new String[9];

                        matchedOverlappedTraceAnalysisTemp[0] = tracedataNeuronBranch1.get(Integer.parseInt(tracedataNeuronBranchSelected1.get(i)[0]))[1].concat(tracedataNeuronBranch1.get(Integer.parseInt(tracedataNeuronBranchSelected1.get(i)[0]))[2]);
                        matchedOverlappedTraceAnalysisTemp[1] = tracedataNeuronType1.get(Integer.parseInt(tracedataNeuronBranchSelected1.get(i)[0]))[0].split(":")[0];
                        matchedOverlappedTraceAnalysisTemp[2] = tracedataNeuronBranch2.get(Integer.parseInt(tracedataNeuronBranchSelected2.get(j)[0]))[1].concat(tracedataNeuronBranch2.get(Integer.parseInt(tracedataNeuronBranchSelected2.get(j)[0]))[2]);
                        matchedOverlappedTraceAnalysisTemp[3] = tracedataNeuronType2.get(Integer.parseInt(tracedataNeuronBranchSelected2.get(j)[0]))[0].split(":")[0];
                        matchedOverlappedTraceAnalysisTemp[4] = ((Integer)indexCoordinateTemp.size()).toString();
                        matchedOverlappedTraceAnalysisTemp[5] = String.format("%.4f", ((double) indexCoordinateTemp.size()) / ((double) coordinateTemp1.length));
                        matchedOverlappedTraceAnalysisTemp[6] = String.format("%.4f", ((double) indexCoordinateTemp.size()) / ((double) coordinateTemp2.length));

                        String matchedOverlappedTraceAnalysisColor = String.format("%.2f", Math.abs(tracedataNeuronColor1.get(Integer.parseInt(tracedataNeuronBranchSelected1.get(i)[0]))[0] - tracedataNeuronColor2.get(Integer.parseInt(tracedataNeuronBranchSelected2.get(j)[0]))[0]) / Math.max(tracedataNeuronColor1.get(Integer.parseInt(tracedataNeuronBranchSelected1.get(i)[0]))[0], tracedataNeuronColor2.get(Integer.parseInt(tracedataNeuronBranchSelected2.get(j)[0]))[0]));
                        for (int k = 1; k < colorDimension; k++) {
                            matchedOverlappedTraceAnalysisColor = matchedOverlappedTraceAnalysisColor.concat(", ").concat(String.format("%.2f", Math.abs(tracedataNeuronColor1.get(Integer.parseInt(tracedataNeuronBranchSelected1.get(i)[0]))[k] - tracedataNeuronColor2.get(Integer.parseInt(tracedataNeuronBranchSelected2.get(j)[0]))[k]) / Math.max(tracedataNeuronColor1.get(Integer.parseInt(tracedataNeuronBranchSelected1.get(i)[0]))[k], tracedataNeuronColor2.get(Integer.parseInt(tracedataNeuronBranchSelected2.get(j)[0]))[k])));
                        }
                        matchedOverlappedTraceAnalysisTemp[7] = matchedOverlappedTraceAnalysisColor;

                        matchedOverlappedTraceAnalysisTemp[8] = "NA";

                        matchedOverlappedTraceAnalysisOriginal.add(matchedOverlappedTraceAnalysisTemp);

                        Integer[][] matchedOverlappedTraceCoordinateTemp = new Integer[indexCoordinateTemp.size()][2];
                        for (int k = 0; k < indexCoordinateTemp.size(); k++) {
                            matchedOverlappedTraceCoordinateTemp[k] = indexCoordinateTemp.get(k);
                        }
                        matchedOverlappedTraceCoordinateOriginal.add(matchedOverlappedTraceCoordinateTemp);
                    }
                }
            }
        }
        
        System.out.println("Size of matchedOverlappedTraceBranchOriginal: " + matchedOverlappedTraceBranchOriginal.size());
        System.out.println("Size of matchedOverlappedTraceAnalysisOriginal: " + matchedOverlappedTraceAnalysisOriginal.size());
        System.out.println("Size of matchedOverlappedTraceCoordinateOriginal: " + matchedOverlappedTraceCoordinateOriginal.size());

        Integer[] matchedOverlappedTraceBranchUniqueTemp1 = new Integer[matchedOverlappedTraceBranchOriginal.size()];
        Integer[] matchedOverlappedTraceBranchUniqueTemp2 = new Integer[matchedOverlappedTraceBranchOriginal.size()];

        for (int i = 0; i < matchedOverlappedTraceBranchOriginal.size(); i++) {

            ArrayList<Integer[]> matchedOverlappedTraceBranchRepeated1 = new ArrayList<Integer[]>();
            ArrayList<Integer[]> matchedOverlappedTraceBranchRepeated2 = new ArrayList<Integer[]>();

            for (int j = 0; j < matchedOverlappedTraceBranchOriginal.size(); j++) {

                if (matchedOverlappedTraceBranchOriginal.get(i)[0].equals(matchedOverlappedTraceBranchOriginal.get(j)[0])) {
                    Integer[] matchedOverlappedTraceBranchRepeatedTemp1 = new Integer[2];

                    matchedOverlappedTraceBranchRepeatedTemp1[0] = j;
                    matchedOverlappedTraceBranchRepeatedTemp1[1] = matchedOverlappedTraceCoordinateOriginal.get(j).length;

                    matchedOverlappedTraceBranchRepeated1.add(matchedOverlappedTraceBranchRepeatedTemp1);
                }

                if (matchedOverlappedTraceBranchOriginal.get(i)[3].equals(matchedOverlappedTraceBranchOriginal.get(j)[3])) {
                    Integer[] matchedOverlappedTraceBranchRepeatedTemp2 = new Integer[2];

                    matchedOverlappedTraceBranchRepeatedTemp2[0] = j;
                    matchedOverlappedTraceBranchRepeatedTemp2[1] = matchedOverlappedTraceCoordinateOriginal.get(j).length;

                    matchedOverlappedTraceBranchRepeated2.add(matchedOverlappedTraceBranchRepeatedTemp2);
                }
            }

            boolean flagStartEnd1 = false;
            boolean flagStartEnd2 = false;

            Integer overlappedLengthBranchRepeatedMaximum1 = 0;
            Integer overlappedLengthBranchRepeatedMaximum2 = 0;

            for (int j = 0; j < matchedOverlappedTraceBranchRepeated1.size(); j++) {

                Integer flagStart1 = 0;
                Integer flagEnd1 = 0;

                Integer repeatedBranchIndex2 = Integer.parseInt(matchedOverlappedTraceBranchOriginal.get(matchedOverlappedTraceBranchRepeated1.get(j)[0])[3]);

                for (int k = 0; k < tracedataNeuronBranch2.size(); k++) {

                    if (tracedataNeuronBranch2.get(k)[0].equals("Neurite") && matchedOverlappedTraceBranchOriginal.get(matchedOverlappedTraceBranchRepeated1.get(j)[0])[5].equals(tracedataNeuronBranch2.get(k)[1].concat(tracedataNeuronBranch2.get(k)[2].split("-")[1]))) {
                        if (Arrays.equals(tracedataNeuronCoordinate2.get(repeatedBranchIndex2)[0], tracedataNeuronCoordinate2.get(k)[tracedataNeuronCoordinate2.get(k).length - 1])) {
                            flagStart1++;
                        } else if (Arrays.equals(tracedataNeuronCoordinate2.get(repeatedBranchIndex2)[tracedataNeuronCoordinate2.get(repeatedBranchIndex2).length - 1], tracedataNeuronCoordinate2.get(k)[0])) {
                            flagEnd1++;
                        }
                    }
                }

                if (flagStart1 == 1 && flagEnd1 == 2) {
                    if (matchedOverlappedTraceBranchRepeated1.get(j)[1] > overlappedLengthBranchRepeatedMaximum1) {

                        matchedOverlappedTraceBranchUniqueTemp1[i] = matchedOverlappedTraceBranchRepeated1.get(j)[0];
                        overlappedLengthBranchRepeatedMaximum1 = matchedOverlappedTraceBranchRepeated1.get(j)[1];

                        flagStartEnd1 = true;
                    }
                }
            }

            if (flagStartEnd1 == false) {
                for (int j = 0; j < matchedOverlappedTraceBranchRepeated1.size(); j++) {
                    if (matchedOverlappedTraceBranchRepeated1.get(j)[1] > overlappedLengthBranchRepeatedMaximum1) {

                        matchedOverlappedTraceBranchUniqueTemp1[i] = matchedOverlappedTraceBranchRepeated1.get(j)[0];
                        overlappedLengthBranchRepeatedMaximum1 = matchedOverlappedTraceBranchRepeated1.get(j)[1];
                    }
                }
            }

            for (int j = 0; j < matchedOverlappedTraceBranchRepeated2.size(); j++) {

                Integer flagStart2 = 0;
                Integer flagEnd2 = 0;

                Integer repeatedBranchIndex1 = Integer.parseInt(matchedOverlappedTraceBranchOriginal.get(matchedOverlappedTraceBranchRepeated2.get(j)[0])[0]);

                for (int k = 0; k < tracedataNeuronBranch1.size(); k++) {

                    if (tracedataNeuronBranch1.get(k)[0].equals("Neurite") && matchedOverlappedTraceBranchOriginal.get(matchedOverlappedTraceBranchRepeated2.get(j)[0])[2].equals(tracedataNeuronBranch1.get(k)[1].concat(tracedataNeuronBranch1.get(k)[2].split("-")[1]))) {
                        if (Arrays.equals(tracedataNeuronCoordinate1.get(repeatedBranchIndex1)[0], tracedataNeuronCoordinate1.get(k)[tracedataNeuronCoordinate1.get(k).length - 1])) {
                            flagStart2++;
                        } else if (Arrays.equals(tracedataNeuronCoordinate1.get(repeatedBranchIndex1)[tracedataNeuronCoordinate1.get(repeatedBranchIndex1).length - 1], tracedataNeuronCoordinate1.get(k)[0])) {
                            flagEnd2++;
                        }
                    }
                }

                if (flagStart2 == 1 && flagEnd2 == 2) {
                    if (matchedOverlappedTraceBranchRepeated2.get(j)[1] > overlappedLengthBranchRepeatedMaximum2) {

                        matchedOverlappedTraceBranchUniqueTemp2[i] = matchedOverlappedTraceBranchRepeated2.get(j)[0];
                        overlappedLengthBranchRepeatedMaximum2 = matchedOverlappedTraceBranchRepeated2.get(j)[1];

                        flagStartEnd2 = true;
                    }
                }
            }

            if (flagStartEnd2 == false) {
                for (int j = 0; j < matchedOverlappedTraceBranchRepeated2.size(); j++) {
                    if (matchedOverlappedTraceBranchRepeated2.get(j)[1] > overlappedLengthBranchRepeatedMaximum2) {

                        matchedOverlappedTraceBranchUniqueTemp2[i] = matchedOverlappedTraceBranchRepeated2.get(j)[0];
                        overlappedLengthBranchRepeatedMaximum2 = matchedOverlappedTraceBranchRepeated2.get(j)[1];
                    }
                }
            }
        }

        Set<Integer> matchedOverlappedTraceBranchUnique1 = new HashSet<Integer>();
        Set<Integer> matchedOverlappedTraceBranchUnique2 = new HashSet<Integer>();

        for (int i = 0; i < matchedOverlappedTraceBranchOriginal.size(); i++) {
            matchedOverlappedTraceBranchUnique1.add(matchedOverlappedTraceBranchUniqueTemp1[i]);
            matchedOverlappedTraceBranchUnique2.add(matchedOverlappedTraceBranchUniqueTemp2[i]);
        }

        matchedOverlappedTraceBranchUnique1.retainAll(matchedOverlappedTraceBranchUnique2);

        Integer[] matchedOverlappedTraceBranchUnique = new Integer[matchedOverlappedTraceBranchUnique1.size()];
        matchedOverlappedTraceBranchUnique = matchedOverlappedTraceBranchUnique1.toArray(matchedOverlappedTraceBranchUnique);
        Arrays.sort(matchedOverlappedTraceBranchUnique);

        for (int i = 0; i < matchedOverlappedTraceBranchUnique.length; i++) {
            matchedOverlappedTraceBranch.add(matchedOverlappedTraceBranchOriginal.get(matchedOverlappedTraceBranchUnique[i]));
            matchedOverlappedTraceAnalysis.add(matchedOverlappedTraceAnalysisOriginal.get(matchedOverlappedTraceBranchUnique[i]));
            matchedOverlappedTraceCoordinate.add(matchedOverlappedTraceCoordinateOriginal.get(matchedOverlappedTraceBranchUnique[i]));
        }

        System.out.println("Size of matchedOverlappedTraceBranch: " + matchedOverlappedTraceBranch.size());
        System.out.println("Size of matchedOverlappedTraceAnalysis: " + matchedOverlappedTraceAnalysis.size());
        System.out.println("Size of matchedOverlappedTraceCoordinate: " + matchedOverlappedTraceCoordinate.size());

        if (montageMethodFlag == 3) {
            
            Set<Integer> matchedOverlappedTraceBranchCommonIndex = new HashSet<Integer>();

            for (int i = 0; i < matchedOverlappedTraceBranch.size(); i++) {
                for (int j = i + 1; j < matchedOverlappedTraceBranch.size(); j++) {
                    if (matchedOverlappedTraceBranch.get(i)[0].equals(matchedOverlappedTraceBranch.get(j)[3])) {
                        matchedOverlappedTraceBranchCommonIndex.add(j);
                    }
                }
            }

            ArrayList<String[]> matchedOverlappedTraceBranchModified = new ArrayList<String[]>();
            ArrayList<String[]> matchedOverlappedTraceAnalysisModified = new ArrayList<String[]>();
            ArrayList<Integer[][]> matchedOverlappedTraceCoordinateModified = new ArrayList<Integer[][]>();

            for (int i = 0; i < matchedOverlappedTraceBranch.size(); i++) {
                if (! matchedOverlappedTraceBranchCommonIndex.contains(i)) {
                    matchedOverlappedTraceBranchModified.add(matchedOverlappedTraceBranch.get(i));
                    matchedOverlappedTraceAnalysisModified.add(matchedOverlappedTraceAnalysis.get(i));
                    matchedOverlappedTraceCoordinateModified.add(matchedOverlappedTraceCoordinate.get(i));
                }
            }

            System.out.println("Size of matchedOverlappedTraceBranchModified: " + matchedOverlappedTraceBranchModified.size());

            matchedOverlappedTraceBranch.clear();
            matchedOverlappedTraceBranch.addAll(matchedOverlappedTraceBranchModified);

            matchedOverlappedTraceAnalysis.clear();
            matchedOverlappedTraceAnalysis.addAll(matchedOverlappedTraceAnalysisModified);

            matchedOverlappedTraceCoordinate.clear();
            matchedOverlappedTraceCoordinate.addAll(matchedOverlappedTraceCoordinateModified);
        }

        for (int i = 0; i < matchedOverlappedTraceBranch.size(); i++) {

            ArrayList<Integer> matchedOverlappedTraceBranchNeuronIndex1 = new ArrayList<Integer>();
            ArrayList<Integer> matchedOverlappedTraceBranchNeuronIndex2 = new ArrayList<Integer>();

            for (int j = 0; j < tracedataNeuronBranch1.size(); j++) {
                if (tracedataNeuronBranch1.get(j)[0].equals("Neurite") && matchedOverlappedTraceBranch.get(i)[2].equals(tracedataNeuronBranch1.get(j)[1].concat("-").concat(tracedataNeuronBranch1.get(j)[2].split("-")[1]))) {
                    matchedOverlappedTraceBranchNeuronIndex1.add(j);
                }
            }

            for (int j = 0; j < tracedataNeuronBranch2.size(); j++) {
                if (tracedataNeuronBranch2.get(j)[0].equals("Neurite") && matchedOverlappedTraceBranch.get(i)[5].equals(tracedataNeuronBranch2.get(j)[1].concat("-").concat(tracedataNeuronBranch2.get(j)[2].split("-")[1]))) {
                    matchedOverlappedTraceBranchNeuronIndex2.add(j);
                }
            }

            int flag1 = 0;
            int flag2 = 0;
            int flag3 = 0;
            int flag4 = 0;

            int indexReference1 = Integer.parseInt(matchedOverlappedTraceBranch.get(i)[0]);
            int indexReference2 = Integer.parseInt(matchedOverlappedTraceBranch.get(i)[3]);

            Integer[][] coordinateBranch1 = tracedataNeuronCoordinate1.get(indexReference1);
            Integer[][] coordinateBranch2 = tracedataNeuronCoordinate2.get(indexReference2);
            String[] typeBranch1 = tracedataNeuronType1.get(indexReference1);
            String[] typeBranch2 = tracedataNeuronType2.get(indexReference2);
            Integer[] radiusBranch1 = tracedataNeuronRadius1.get(indexReference1);
            Integer[] radiusBranch2 = tracedataNeuronRadius2.get(indexReference2);
            Integer[] synapseBranch1 = tracedataNeuronSynapse1.get(indexReference1);
            Integer[] synapseBranch2 = tracedataNeuronSynapse2.get(indexReference2);
            String[] connectionBranch1 = tracedataNeuronConnection1.get(indexReference1);
            String[] connectionBranch2 = tracedataNeuronConnection2.get(indexReference2);

            for (int j = matchedOverlappedTraceBranchNeuronIndex1.get(0); j < indexReference1; j++) {
                if (tracedataNeuronBranch1.get(j)[0].equals("Neurite") && Arrays.equals(tracedataNeuronCoordinate1.get(indexReference1)[0], tracedataNeuronCoordinate1.get(j)[tracedataNeuronCoordinate1.get(j).length - 1])) {
                    flag1++;
                }
            }

            for (int j = indexReference1 + 1; j <= matchedOverlappedTraceBranchNeuronIndex1.get(matchedOverlappedTraceBranchNeuronIndex1.size() - 1); j++) {
                if (tracedataNeuronBranch1.get(j)[0].equals("Neurite") && Arrays.equals(tracedataNeuronCoordinate1.get(indexReference1)[tracedataNeuronCoordinate1.get(indexReference1).length - 1], tracedataNeuronCoordinate1.get(j)[0])) {
                    flag2++;
                }
            }

            for (int j = matchedOverlappedTraceBranchNeuronIndex2.get(0); j < indexReference2; j++) {
                if (tracedataNeuronBranch2.get(j)[0].equals("Neurite") && Arrays.equals(tracedataNeuronCoordinate2.get(indexReference2)[0], tracedataNeuronCoordinate2.get(j)[tracedataNeuronCoordinate2.get(j).length - 1])) {
                    flag3++;
                }
            }

            for (int j = indexReference2 + 1; j <= matchedOverlappedTraceBranchNeuronIndex2.get(matchedOverlappedTraceBranchNeuronIndex2.size() - 1); j++) {
                if (tracedataNeuronBranch2.get(j)[0].equals("Neurite") && Arrays.equals(tracedataNeuronCoordinate2.get(indexReference2)[tracedataNeuronCoordinate2.get(indexReference2).length - 1], tracedataNeuronCoordinate2.get(j)[0])) {
                    flag4++;
                }
            }

            int indexStart1 = matchedOverlappedTraceCoordinate.get(i)[0][0];
            int indexStart2 = matchedOverlappedTraceCoordinate.get(i)[0][1];
            int indexEnd1 = matchedOverlappedTraceCoordinate.get(i)[matchedOverlappedTraceCoordinate.get(i).length - 1][0];
            int indexEnd2 = matchedOverlappedTraceCoordinate.get(i)[matchedOverlappedTraceCoordinate.get(i).length - 1][1];

            ModifyBranchAllProperties ModifyBranchAllPropertiesInstance = new ModifyBranchAllProperties(coordinateBranch1, coordinateBranch2, typeBranch1, typeBranch2, radiusBranch1, radiusBranch2, synapseBranch1, synapseBranch2, connectionBranch1, connectionBranch2, flag1, flag2, flag3, flag4, indexStart1, indexStart2, indexEnd1, indexEnd2);

            tracedataNeuronCoordinateModified1.set(indexReference1, ModifyBranchAllPropertiesInstance.getModifyBranchCoordinate1());
            tracedataNeuronTypeModified1.set(indexReference1, ModifyBranchAllPropertiesInstance.getModifyBranchType1());
            tracedataNeuronRadiusModified1.set(indexReference1, ModifyBranchAllPropertiesInstance.getModifyBranchRadius1());
            tracedataNeuronSynapseModified1.set(indexReference1, ModifyBranchAllPropertiesInstance.getModifyBranchSynapse1());
            tracedataNeuronConnectionModified1.set(indexReference1, ModifyBranchAllPropertiesInstance.getModifyBranchConnection1());

            System.out.println("Flags: " + flag1 + " ; " + flag2 + " ; " + flag3 + " ; " + flag4);
            System.out.println("The difference of the size of 'tracedataNeuronCoordinate1' before and after modification: " + (tracedataNeuronCoordinateModified1.get(indexReference1).length - tracedataNeuronCoordinate1.get(indexReference1).length));
        }

        System.out.println("Size of tracedataNeuronCoordinateModified1: " + tracedataNeuronCoordinateModified1.size());
        System.out.println("Size of tracedataNeuronCoordinate2: " + tracedataNeuronCoordinate2.size());
    }

    public List<String[]> getMatchedOverlappedTraceBranch() {
        return matchedOverlappedTraceBranch;
    }

    public List<String[]> getMatchedOverlappedTraceAnalysis() {
        return matchedOverlappedTraceAnalysis;
    }

    public List<Integer[][]> getMatchedOverlappedTraceCoordinate() {
        return matchedOverlappedTraceCoordinate;
    }

    public List<Integer[][]> getTracedataNeuronCoordinateModified1() {
        return tracedataNeuronCoordinateModified1;
    }

    public List<String[]> getTracedataNeuronTypeModified1() {
        return tracedataNeuronTypeModified1;
    }

    public List<Integer[]> getTracedataNeuronRadiusModified1() {
        return tracedataNeuronRadiusModified1;
    }

    public List<Integer[]> getTracedataNeuronSynapseModified1() {
        return tracedataNeuronSynapseModified1;
    }

    public List<String[]> getTracedataNeuronConnectionModified1() {
        return tracedataNeuronConnectionModified1;
    }

}
