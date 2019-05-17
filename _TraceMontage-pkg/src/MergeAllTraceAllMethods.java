/* COPYRIGHT Creative Commons Attribution-Non Commercial 4.0 International (CC BY-NC 4.0) */

/**
 * A class for merging all traces of one image or two adjacent images.
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

public class MergeAllTraceAllMethods {

    private ArrayList<String[]> tracedataMontageNeuronTag = new ArrayList<String[]>();
    private ArrayList<String[]> tracedataMontageNeuronBranch = new ArrayList<String[]>();
    private ArrayList<Integer[][]> tracedataMontageNeuronCoordinate = new ArrayList<Integer[][]>();
    private ArrayList<String[]> tracedataMontageNeuronType = new ArrayList<String[]>();
    private ArrayList<Integer[]> tracedataMontageNeuronRadius = new ArrayList<Integer[]>();
    private ArrayList<Integer[]> tracedataMontageNeuronSynapse = new ArrayList<Integer[]>();
    private ArrayList<String[]> tracedataMontageNeuronConnection = new ArrayList<String[]>();
    
    private ArrayList<String[]> matchedOverlappedTraceAnalysisModified = new ArrayList<String[]>();

    public MergeAllTraceAllMethods(Integer montageMethodFlag, List<String[]> matchedOverlappedTraceBranch, List<String[]> matchedOverlappedTraceAnalysis, List<String[]> tracedataNeuronTag1, List<String[]> tracedataNeuronBranch1, List<Integer[][]> tracedataNeuronCoordinateModified1, List<String[]> tracedataNeuronTypeModified1, List<Integer[]> tracedataNeuronRadiusModified1, List<Integer[]> tracedataNeuronSynapseModified1, List<String[]> tracedataNeuronConnectionModified1, List<String[]> tracedataNeuronTag2, List<String[]> tracedataNeuronBranch2, List<Integer[][]> tracedataNeuronCoordinate2, List<String[]> tracedataNeuronType2, List<Integer[]> tracedataNeuronRadius2, List<Integer[]> tracedataNeuronSynapse2, List<String[]> tracedataNeuronConnection2) {

        if (montageMethodFlag == 1 || montageMethodFlag == 2) {

            Integer neuronNumber = 0;
            Integer neuronNumberTag = 0;
           
            ArrayList<Integer[]> tracedataMontageNeuronMap12 = new ArrayList<Integer[]>();
            ArrayList<String[]> tracedataMontageNeuronMap1 = new ArrayList<String[]>();
            ArrayList<String[]> tracedataMontageNeuronMap2 = new ArrayList<String[]>();

            ArrayList<Integer[]> tracedataMontageSpineMap12 = new ArrayList<Integer[]>();

            ArrayList<Integer> tracedataMontageIndex1 = new ArrayList<Integer>();
            ArrayList<Integer> tracedataMontageIndex2 = new ArrayList<Integer>();
            
            for (int i = 0; i < tracedataNeuronBranch1.size(); i++) {

                boolean flag1 = false;

                for (int j = 0; j < matchedOverlappedTraceBranch.size(); j++) {
                    if (! tracedataNeuronBranch1.get(i)[0].equals("Spine") && tracedataNeuronBranch1.get(i)[1].equals(matchedOverlappedTraceBranch.get(j)[1])) {
                        flag1 = true;
                        break;
                    }
                }

                if (flag1 == true) {
                    tracedataMontageIndex1.add(i);
                } else {

                    if (! tracedataNeuronBranch1.get(i)[0].equals("Spine")) {

                        if (Integer.parseInt(tracedataNeuronBranch1.get(i)[1]) != neuronNumberTag) {
                            neuronNumber++;
                            neuronNumberTag = Integer.parseInt(tracedataNeuronBranch1.get(i)[1]);

                            String[] tracedataMontageNeuronTagTemp1 = new String[2];
                            tracedataMontageNeuronTagTemp1[0] = neuronNumber.toString();

                            for (int j = 0; j < tracedataNeuronTag1.size(); j++) {
                                if (tracedataNeuronTag1.get(j)[0].equals(tracedataNeuronBranch1.get(i)[1])) {
                                    if (! tracedataNeuronTag1.get(j)[1].isEmpty()) {
                                        tracedataMontageNeuronTagTemp1[1] = tracedataNeuronTag1.get(j)[1].concat(";data1");
                                        break;
                                    } else {
                                        tracedataMontageNeuronTagTemp1[1] = "data1";
                                        break;
                                    }
                                }
                            }

                            tracedataMontageNeuronTag.add(tracedataMontageNeuronTagTemp1);
                        }

                        String[] tracedataMontageNeuronBranchTemp1 = new String[3];
                        tracedataMontageNeuronBranchTemp1[0] = tracedataNeuronBranch1.get(i)[0];
                        tracedataMontageNeuronBranchTemp1[1] = Integer.toString(neuronNumber);
                        tracedataMontageNeuronBranchTemp1[2] = tracedataNeuronBranch1.get(i)[2];

                        tracedataMontageNeuronBranch.add(tracedataMontageNeuronBranchTemp1);
                        tracedataMontageNeuronCoordinate.add(tracedataNeuronCoordinateModified1.get(i));
                        tracedataMontageNeuronType.add(tracedataNeuronTypeModified1.get(i));
                        tracedataMontageNeuronRadius.add(tracedataNeuronRadiusModified1.get(i));
                        tracedataMontageNeuronSynapse.add(tracedataNeuronSynapseModified1.get(i));
                        tracedataMontageNeuronConnection.add(tracedataNeuronConnectionModified1.get(i));

                        Integer[] tracedataMontageNeuronMapTemp12 = new Integer[3];
                        tracedataMontageNeuronMapTemp12[0] = 1;
                        tracedataMontageNeuronMapTemp12[1] = tracedataMontageNeuronMap1.size();
                        tracedataMontageNeuronMapTemp12[2] = null;
                        tracedataMontageNeuronMap12.add(tracedataMontageNeuronMapTemp12);

                        String[] tracedataMontageNeuronMapTemp1 = new String[2];

                        if (tracedataNeuronBranch1.get(i)[0].equals("Soma")) {
                            tracedataMontageNeuronMapTemp1[0] = tracedataNeuronBranch1.get(i)[1].concat(":").concat(tracedataNeuronBranch1.get(i)[2]);
                            tracedataMontageNeuronMapTemp1[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(":").concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);
                        } else if (tracedataNeuronBranch1.get(i)[0].equals("Neurite")) {
                            tracedataMontageNeuronMapTemp1[0] = tracedataNeuronBranch1.get(i)[1].concat(tracedataNeuronBranch1.get(i)[2]);
                            tracedataMontageNeuronMapTemp1[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);
                        }

                        tracedataMontageNeuronMap1.add(tracedataMontageNeuronMapTemp1);

                    } else {
                        
                        Integer[] tracedataMontageSpineMapTemp12 = new Integer[3];
                        tracedataMontageSpineMapTemp12[0] = 1;
                        tracedataMontageSpineMapTemp12[1] = i;
                        tracedataMontageSpineMapTemp12[2] = Integer.parseInt(tracedataNeuronBranch1.get(i)[1]);
                        tracedataMontageSpineMap12.add(tracedataMontageSpineMapTemp12);
                    }
                }
            }

            Integer neuronCounter2 = 0;
            
            for (int i = 0; i < tracedataNeuronBranch2.size(); i++) {

                boolean flag2 = false;

                for (int j = 0; j < matchedOverlappedTraceBranch.size(); j++) {
                    if (! tracedataNeuronBranch2.get(i)[0].equals("Spine") && tracedataNeuronBranch2.get(i)[1].equals(matchedOverlappedTraceBranch.get(j)[4])) {
                        flag2 = true;
                        break;
                    }
                }

                if (flag2 == true) {
                    tracedataMontageIndex2.add(i);
                } else {
                   
                    if (! tracedataNeuronBranch2.get(i)[0].equals("Spine")) {
                        neuronCounter2++;
                    
                        if ((Integer.parseInt(tracedataNeuronBranch2.get(i)[1]) != neuronNumberTag) || (neuronCounter2 == 1)) {
                            neuronNumber++;
                            neuronNumberTag = Integer.parseInt(tracedataNeuronBranch2.get(i)[1]);

                            String[] tracedataMontageNeuronTagTemp2 = new String[2];
                            tracedataMontageNeuronTagTemp2[0] = neuronNumber.toString();
 
                            for (int j = 0; j < tracedataNeuronTag2.size(); j++) {
                                if (tracedataNeuronTag2.get(j)[0].equals(tracedataNeuronBranch2.get(i)[1])) {
                                    if (! tracedataNeuronTag2.get(j)[1].isEmpty()) {
                                        tracedataMontageNeuronTagTemp2[1] = tracedataNeuronTag2.get(j)[1].concat(";data2");
                                        break;
                                    } else {
                                        tracedataMontageNeuronTagTemp2[1] = "data2";
                                        break;
                                    }
                                }
                            }

                            tracedataMontageNeuronTag.add(tracedataMontageNeuronTagTemp2);
                        }

                        String[] tracedataMontageNeuronBranchTemp2= new String[3];
                        tracedataMontageNeuronBranchTemp2[0] = tracedataNeuronBranch2.get(i)[0];
                        tracedataMontageNeuronBranchTemp2[1] = Integer.toString(neuronNumber);
                        tracedataMontageNeuronBranchTemp2[2] = tracedataNeuronBranch2.get(i)[2];

                        tracedataMontageNeuronBranch.add(tracedataMontageNeuronBranchTemp2);
                        tracedataMontageNeuronCoordinate.add(tracedataNeuronCoordinate2.get(i));
                        tracedataMontageNeuronType.add(tracedataNeuronType2.get(i));
                        tracedataMontageNeuronRadius.add(tracedataNeuronRadius2.get(i));
                        tracedataMontageNeuronSynapse.add(tracedataNeuronSynapse2.get(i));
                        tracedataMontageNeuronConnection.add(tracedataNeuronConnection2.get(i));

                        Integer[] tracedataMontageNeuronMapTemp12 = new Integer[3];
                        tracedataMontageNeuronMapTemp12[0] = 2;
                        tracedataMontageNeuronMapTemp12[1] = null;
                        tracedataMontageNeuronMapTemp12[2] = tracedataMontageNeuronMap2.size();
                        tracedataMontageNeuronMap12.add(tracedataMontageNeuronMapTemp12);

                        String[] tracedataMontageNeuronMapTemp2 = new String[2];

                        if (tracedataNeuronBranch2.get(i)[0].equals("Soma")) {
                            tracedataMontageNeuronMapTemp2[0] = tracedataNeuronBranch2.get(i)[1].concat(":").concat(tracedataNeuronBranch2.get(i)[2]);
                            tracedataMontageNeuronMapTemp2[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(":").concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);
                        } else if (tracedataNeuronBranch2.get(i)[0].equals("Neurite")) {
                            tracedataMontageNeuronMapTemp2[0] = tracedataNeuronBranch2.get(i)[1].concat(tracedataNeuronBranch2.get(i)[2]);
                            tracedataMontageNeuronMapTemp2[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);
                        }

                        tracedataMontageNeuronMap2.add(tracedataMontageNeuronMapTemp2);
                        
                    } else {

                        Integer[] tracedataMontageSpineMapTemp12 = new Integer[3];
                        tracedataMontageSpineMapTemp12[0] = 2;
                        tracedataMontageSpineMapTemp12[1] = i;
                        tracedataMontageSpineMapTemp12[2] = Integer.parseInt(tracedataNeuronBranch2.get(i)[1]);
                        tracedataMontageSpineMap12.add(tracedataMontageSpineMapTemp12);
                    }
                }
            }

            System.out.println("Size of tracedataMontageNeuronMap12: " + tracedataMontageNeuronMap12.size());
            System.out.println("Size of tracedataMontageNeuronMap1: " + tracedataMontageNeuronMap1.size());
            System.out.println("Size of tracedataMontageNeuronMap2: " + tracedataMontageNeuronMap2.size());

            System.out.println("Size of tracedataMontageSpineMap12: " + tracedataMontageSpineMap12.size());

            System.out.println("Size of tracedataMontageIndex1: " + tracedataMontageIndex1.size());
            System.out.println("Size of tracedataMontageIndex2: " + tracedataMontageIndex2.size());

            Set<Integer> matchedOverlappedTraceIndex = new HashSet<Integer>();

            for (int i = 0; i < matchedOverlappedTraceBranch.size(); i++) {

                if (! matchedOverlappedTraceIndex.contains(i)) {

                    neuronNumber++;

                    Set<Integer> matchedOverlappedTraceIndexTemp = new HashSet<Integer>();

                    ArrayList<String[]> tracedataMontageSomaMap = new ArrayList<String[]>();
                    ArrayList<String[]> tracedataMontageNeuriteMap = new ArrayList<String[]>();
                    
                    ArrayList<Integer[][]> tracedataMontageSomaCoordinate = new ArrayList<Integer[][]>();
                    ArrayList<String[]> tracedataMontageSomaType = new ArrayList<String[]>();
                    ArrayList<Integer[]> tracedataMontageSomaRadius = new ArrayList<Integer[]>();
                    ArrayList<Integer[]> tracedataMontageSomaSynapse = new ArrayList<Integer[]>();
                    ArrayList<String[]> tracedataMontageSomaConnection = new ArrayList<String[]>();

                    ArrayList<Integer[][]> tracedataMontageNeuriteCoordinate = new ArrayList<Integer[][]>();
                    ArrayList<String[]> tracedataMontageNeuriteType = new ArrayList<String[]>();
                    ArrayList<Integer[]> tracedataMontageNeuriteRadius = new ArrayList<Integer[]>();
                    ArrayList<Integer[]> tracedataMontageNeuriteSynapse = new ArrayList<Integer[]>();
                    ArrayList<String[]> tracedataMontageNeuriteConnection = new ArrayList<String[]>();

                    ArrayList<Integer[]> tracedataMergingNeuronIndex1 = new ArrayList<Integer[]>();
                    ArrayList<Integer[]> tracedataMergingNeuronIndex2 = new ArrayList<Integer[]>();
                    ArrayList<Integer[][]> tracedataMergingNeuronIndex12 = new ArrayList<Integer[][]>();

                    Set<Integer> matchedOverlappedTraceColumn1 = new HashSet<Integer>();
                    Set<Integer> matchedOverlappedTraceColumn4 = new HashSet<Integer>();

                    Set<String> matchedAllOverlappedTraceColumn2 = new HashSet<String>();
                    Set<String> matchedAllOverlappedTraceColumn5 = new HashSet<String>();

                    ArrayList<Integer[]> matchedOverlappedTraceColumn0 = new ArrayList<Integer[]>();
                    ArrayList<Integer[]> matchedOverlappedTraceColumn3 = new ArrayList<Integer[]>();
                    
                    ArrayList<String[]> matchedOverlappedTraceColumn2 = new ArrayList<String[]>();
                    ArrayList<String[]> matchedOverlappedTraceColumn5 = new ArrayList<String[]>();

                    matchedOverlappedTraceColumn1.add(Integer.parseInt(matchedOverlappedTraceBranch.get(i)[1]));
                    matchedOverlappedTraceColumn4.add(Integer.parseInt(matchedOverlappedTraceBranch.get(i)[4]));

                    Integer matchedOverlappedTraceNeuronNumber = matchedOverlappedTraceColumn1.size() + matchedOverlappedTraceColumn4.size();
                    boolean flagLoop1 = true;

                    while (((matchedOverlappedTraceColumn1.size() + matchedOverlappedTraceColumn4.size()) > matchedOverlappedTraceNeuronNumber) || (flagLoop1 == true)) {

                        matchedOverlappedTraceNeuronNumber = matchedOverlappedTraceColumn1.size() + matchedOverlappedTraceColumn4.size();
                        flagLoop1 = false;

                        for (int j = 0; j < matchedOverlappedTraceBranch.size(); j++) {

                            if (matchedOverlappedTraceColumn1.contains(Integer.parseInt(matchedOverlappedTraceBranch.get(j)[1]))) {
                                matchedOverlappedTraceIndexTemp.add(j);
                                matchedOverlappedTraceColumn4.add(Integer.parseInt(matchedOverlappedTraceBranch.get(j)[4]));
                            }

                            if (matchedOverlappedTraceColumn4.contains(Integer.parseInt(matchedOverlappedTraceBranch.get(j)[4]))) {
                                matchedOverlappedTraceIndexTemp.add(j);
                                matchedOverlappedTraceColumn1.add(Integer.parseInt(matchedOverlappedTraceBranch.get(j)[1]));
                            }
                        }
                    }

                    for (Integer j : matchedOverlappedTraceIndexTemp) {
                        matchedOverlappedTraceIndex.add(j);
                    }

                    for (Integer j : matchedOverlappedTraceIndexTemp) {

                        if (! matchedAllOverlappedTraceColumn2.contains(matchedOverlappedTraceBranch.get(j)[2]) && ! matchedAllOverlappedTraceColumn5.contains(matchedOverlappedTraceBranch.get(j)[5])) {

                            Set<Integer> matchedOverlappedTraceColumnTemp0 = new HashSet<Integer>();
                            Set<Integer> matchedOverlappedTraceColumnTemp3 = new HashSet<Integer>();
                            Set<String> matchedOverlappedTraceColumnTemp2 = new HashSet<String>();
                            Set<String> matchedOverlappedTraceColumnTemp5 = new HashSet<String>();

                            matchedOverlappedTraceColumnTemp2.add(matchedOverlappedTraceBranch.get(j)[2]);
                            matchedOverlappedTraceColumnTemp5.add(matchedOverlappedTraceBranch.get(j)[5]);

                            Integer matchedOverlappedTraceBranchNumber = matchedOverlappedTraceColumnTemp2.size() + matchedOverlappedTraceColumnTemp5.size();
                            boolean flagLoop2 = true;

                            while (((matchedOverlappedTraceColumnTemp2.size() + matchedOverlappedTraceColumnTemp5.size()) > matchedOverlappedTraceBranchNumber) || (flagLoop2 == true)) {

                                matchedOverlappedTraceBranchNumber = matchedOverlappedTraceColumnTemp2.size() + matchedOverlappedTraceColumnTemp5.size();
                                flagLoop2 = false;

                                for (Integer k : matchedOverlappedTraceIndexTemp) {

                                    if (matchedOverlappedTraceColumnTemp2.contains(matchedOverlappedTraceBranch.get(k)[2])) {

                                        matchedOverlappedTraceColumnTemp0.add(Integer.parseInt(matchedOverlappedTraceBranch.get(k)[0]));
                                        matchedOverlappedTraceColumnTemp3.add(Integer.parseInt(matchedOverlappedTraceBranch.get(k)[3]));

                                        matchedOverlappedTraceColumnTemp5.add(matchedOverlappedTraceBranch.get(k)[5]);

                                        matchedAllOverlappedTraceColumn2.add(matchedOverlappedTraceBranch.get(k)[2]);
                                        matchedAllOverlappedTraceColumn5.add(matchedOverlappedTraceBranch.get(k)[5]);
                                    }

                                    if (matchedOverlappedTraceColumnTemp5.contains(matchedOverlappedTraceBranch.get(k)[5])) {

                                        matchedOverlappedTraceColumnTemp0.add(Integer.parseInt(matchedOverlappedTraceBranch.get(k)[0]));
                                        matchedOverlappedTraceColumnTemp3.add(Integer.parseInt(matchedOverlappedTraceBranch.get(k)[3]));
                                        
                                        matchedOverlappedTraceColumnTemp2.add(matchedOverlappedTraceBranch.get(k)[2]);

                                        matchedAllOverlappedTraceColumn2.add(matchedOverlappedTraceBranch.get(k)[2]);
                                        matchedAllOverlappedTraceColumn5.add(matchedOverlappedTraceBranch.get(k)[5]);
                                    }
                                }
                            }

                            Integer[] matchedOverlappedTraceColumnTempTemp0 = matchedOverlappedTraceColumnTemp0.toArray(new Integer[matchedOverlappedTraceColumnTemp0.size()]);
                            Integer[] matchedOverlappedTraceColumnTempTemp3 = matchedOverlappedTraceColumnTemp3.toArray(new Integer[matchedOverlappedTraceColumnTemp3.size()]);
                            String[] matchedOverlappedTraceColumnTempTemp2 = matchedOverlappedTraceColumnTemp2.toArray(new String[matchedOverlappedTraceColumnTemp2.size()]);
                            String[] matchedOverlappedTraceColumnTempTemp5 = matchedOverlappedTraceColumnTemp5.toArray(new String[matchedOverlappedTraceColumnTemp5.size()]);

                            matchedOverlappedTraceColumn0.add(matchedOverlappedTraceColumnTempTemp0);
                            matchedOverlappedTraceColumn3.add(matchedOverlappedTraceColumnTempTemp3);
                            matchedOverlappedTraceColumn2.add(matchedOverlappedTraceColumnTempTemp2);
                            matchedOverlappedTraceColumn5.add(matchedOverlappedTraceColumnTempTemp5);

                            ArrayList<Integer> tracedataMergingNeuronIndexTemp1 = new ArrayList<Integer>();
                            ArrayList<Integer> tracedataMergingNeuronIndexTemp2 = new ArrayList<Integer>();
                            ArrayList<Integer[]> tracedataMergingNeuronIndexTemp12 = new ArrayList<Integer[]>();

                            for (int k = 0; k < tracedataMontageIndex1.size(); k++) {
                                if (tracedataNeuronBranch1.get(tracedataMontageIndex1.get(k))[0].equals("Neurite")) {
                                    if (matchedOverlappedTraceColumnTemp2.contains(tracedataNeuronBranch1.get(tracedataMontageIndex1.get(k))[1].concat("-").concat(tracedataNeuronBranch1.get(tracedataMontageIndex1.get(k))[2].split("-")[1]))) {
                                        tracedataMergingNeuronIndexTemp1.add(tracedataMontageIndex1.get(k));
                                    }
                                }
                            }

                            for (int k = 0; k < tracedataMontageIndex2.size(); k++) {
                                if (tracedataNeuronBranch2.get(tracedataMontageIndex2.get(k))[0].equals("Neurite") && ! matchedOverlappedTraceColumnTemp3.contains(tracedataMontageIndex2.get(k))) {
                                    if (matchedOverlappedTraceColumnTemp5.contains(tracedataNeuronBranch2.get(tracedataMontageIndex2.get(k))[1].concat("-").concat(tracedataNeuronBranch2.get(tracedataMontageIndex2.get(k))[2].split("-")[1]))) {
                                        tracedataMergingNeuronIndexTemp2.add(tracedataMontageIndex2.get(k));
                                    }
                                }
                            }

                            for (Integer k : matchedOverlappedTraceIndexTemp) {
                                if (matchedOverlappedTraceColumnTemp2.contains(matchedOverlappedTraceBranch.get(k)[2]) && matchedOverlappedTraceColumnTemp5.contains(matchedOverlappedTraceBranch.get(k)[5])) {
                                    Integer[] tracedataMergingNeuronIndexTempTemp12 = new Integer[2];
                                    tracedataMergingNeuronIndexTempTemp12[0] = Integer.parseInt(matchedOverlappedTraceBranch.get(k)[0]);
                                    tracedataMergingNeuronIndexTempTemp12[1] = Integer.parseInt(matchedOverlappedTraceBranch.get(k)[3]);
                                    tracedataMergingNeuronIndexTemp12.add(tracedataMergingNeuronIndexTempTemp12);
                                }
                            }

                            Integer[] tracedataMergingNeuronIndexTempTemp1 = tracedataMergingNeuronIndexTemp1.toArray(new Integer[tracedataMergingNeuronIndexTemp1.size()]);
                            Integer[] tracedataMergingNeuronIndexTempTemp2 = tracedataMergingNeuronIndexTemp2.toArray(new Integer[tracedataMergingNeuronIndexTemp2.size()]);
                            Integer[][] tracedataMergingNeuronIndexTempTemp12 = tracedataMergingNeuronIndexTemp12.toArray(new Integer[tracedataMergingNeuronIndexTemp12.size()][2]);

                            tracedataMergingNeuronIndex1.add(tracedataMergingNeuronIndexTempTemp1);
                            tracedataMergingNeuronIndex2.add(tracedataMergingNeuronIndexTempTemp2);
                            tracedataMergingNeuronIndex12.add(tracedataMergingNeuronIndexTempTemp12);
                        }
                    }

                    System.out.println("Size of tracedataMergingNeuronIndex12: " + tracedataMergingNeuronIndex12.size());
                    System.out.println("Size of tracedataMergingNeuronIndex1: " + tracedataMergingNeuronIndex1.size());
                    System.out.println("Size of tracedataMergingNeuronIndex2: " + tracedataMergingNeuronIndex2.size());

                    ArrayList<Integer> tracedataMontageSomaIndex1 = new ArrayList<Integer>();
                    ArrayList<Integer> tracedataMontageSomaIndex2 = new ArrayList<Integer>();

                    for (int j = 0; j < tracedataMontageIndex1.size(); j++) {
                        if (matchedOverlappedTraceColumn1.contains(Integer.parseInt(tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[1]))) {
                            if (tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[0].equals("Soma")) {
                                tracedataMontageSomaIndex1.add(tracedataMontageIndex1.get(j));
                            }
                        }
                    }

                    for (int j = 0; j < tracedataMontageIndex2.size(); j++) {
                        if (matchedOverlappedTraceColumn4.contains(Integer.parseInt(tracedataNeuronBranch2.get(tracedataMontageIndex2.get(j))[1]))) {
                            if (tracedataNeuronBranch2.get(tracedataMontageIndex2.get(j))[0].equals("Soma")) {
                                tracedataMontageSomaIndex2.add(tracedataMontageIndex2.get(j));
                            }
                        }
                    }

                    if (tracedataMontageSomaIndex1.size() > 0 && tracedataMontageSomaIndex2.isEmpty()) {

                        for (int j = 0; j < tracedataMontageSomaIndex1.size(); j++) {
                        
                            String[] tracedataMontageSomaMapTemp1 = new String[2];
                            tracedataMontageSomaMapTemp1[0] = "1";
                            tracedataMontageSomaMapTemp1[1] = tracedataNeuronBranch1.get(tracedataMontageSomaIndex1.get(j))[1].concat(":").concat(tracedataNeuronBranch1.get(tracedataMontageSomaIndex1.get(j))[2]);
                            tracedataMontageSomaMap.add(tracedataMontageSomaMapTemp1);

                            tracedataMontageSomaCoordinate.add(tracedataNeuronCoordinateModified1.get(tracedataMontageSomaIndex1.get(j)));
                            tracedataMontageSomaType.add(tracedataNeuronTypeModified1.get(tracedataMontageSomaIndex1.get(j)));
                            tracedataMontageSomaRadius.add(tracedataNeuronRadiusModified1.get(tracedataMontageSomaIndex1.get(j)));
                            tracedataMontageSomaSynapse.add(tracedataNeuronSynapseModified1.get(tracedataMontageSomaIndex1.get(j)));
                            tracedataMontageSomaConnection.add(tracedataNeuronConnectionModified1.get(tracedataMontageSomaIndex1.get(j)));   
                        }
                        
                    } else if (tracedataMontageSomaIndex1.isEmpty() && tracedataMontageSomaIndex2.size() > 0) {

                        for (int j = 0; j < tracedataMontageSomaIndex2.size(); j++) {

                            String[] tracedataMontageSomaMapTemp2 = new String[2];
                            tracedataMontageSomaMapTemp2[0] = "2";
                            tracedataMontageSomaMapTemp2[1] = tracedataNeuronBranch2.get(tracedataMontageSomaIndex2.get(j))[1].concat(":").concat(tracedataNeuronBranch2.get(tracedataMontageSomaIndex2.get(j))[2]);
                            tracedataMontageSomaMap.add(tracedataMontageSomaMapTemp2);

                            tracedataMontageSomaCoordinate.add(tracedataNeuronCoordinate2.get(tracedataMontageSomaIndex2.get(j)));
                            tracedataMontageSomaType.add(tracedataNeuronType2.get(tracedataMontageSomaIndex2.get(j)));
                            tracedataMontageSomaRadius.add(tracedataNeuronRadius2.get(tracedataMontageSomaIndex2.get(j)));
                            tracedataMontageSomaSynapse.add(tracedataNeuronSynapse2.get(tracedataMontageSomaIndex2.get(j)));
                            tracedataMontageSomaConnection.add(tracedataNeuronConnection2.get(tracedataMontageSomaIndex2.get(j)));
                        }

                    } else if (tracedataMontageSomaIndex1.size() > 0 && tracedataMontageSomaIndex2.size() > 0) {

                        Integer tracedataMontageSomaZStart1 = Integer.parseInt(tracedataNeuronBranch1.get(tracedataMontageSomaIndex1.get(0))[2]);
                        Integer tracedataMontageSomaZEnd1 = Integer.parseInt(tracedataNeuronBranch1.get(tracedataMontageSomaIndex1.get(tracedataMontageSomaIndex1.size() - 1))[2]);
                        Integer tracedataMontageSomaZStart2 = Integer.parseInt(tracedataNeuronBranch2.get(tracedataMontageSomaIndex2.get(0))[2]);
                        Integer tracedataMontageSomaZEnd2 = Integer.parseInt(tracedataNeuronBranch2.get(tracedataMontageSomaIndex2.get(tracedataMontageSomaIndex2.size() - 1))[2]);

                        if (tracedataMontageSomaZStart1 <= tracedataMontageSomaZStart2) {

                            for (int j = 0; j < tracedataMontageSomaIndex1.size(); j++) {

                                String[] tracedataMontageSomaMapTemp1 = new String[2];
                                tracedataMontageSomaMapTemp1[0] = "1";
                                tracedataMontageSomaMapTemp1[1] = tracedataNeuronBranch1.get(tracedataMontageSomaIndex1.get(j))[1].concat(":").concat(tracedataNeuronBranch1.get(tracedataMontageSomaIndex1.get(j))[2]);
                                tracedataMontageSomaMap.add(tracedataMontageSomaMapTemp1);

                                tracedataMontageSomaCoordinate.add(tracedataNeuronCoordinateModified1.get(tracedataMontageSomaIndex1.get(j)));
                                tracedataMontageSomaType.add(tracedataNeuronTypeModified1.get(tracedataMontageSomaIndex1.get(j)));
                                tracedataMontageSomaRadius.add(tracedataNeuronRadiusModified1.get(tracedataMontageSomaIndex1.get(j)));
                                tracedataMontageSomaSynapse.add(tracedataNeuronSynapseModified1.get(tracedataMontageSomaIndex1.get(j)));
                                tracedataMontageSomaConnection.add(tracedataNeuronConnectionModified1.get(tracedataMontageSomaIndex1.get(j)));
                            }

                            if ((tracedataMontageSomaZEnd1 - tracedataMontageSomaZStart2 + 1) >= 0) {
                                for (int j = tracedataMontageSomaZEnd1 - tracedataMontageSomaZStart2 + 1; j < tracedataMontageSomaIndex2.size(); j++) {

                                    String[] tracedataMontageSomaMapTemp2 = new String[2];
                                    tracedataMontageSomaMapTemp2[0] = "2";
                                    tracedataMontageSomaMapTemp2[1] = tracedataNeuronBranch2.get(tracedataMontageSomaIndex2.get(j))[1].concat(":").concat(tracedataNeuronBranch2.get(tracedataMontageSomaIndex2.get(j))[2]);
                                    tracedataMontageSomaMap.add(tracedataMontageSomaMapTemp2);

                                    tracedataMontageSomaCoordinate.add(tracedataNeuronCoordinate2.get(tracedataMontageSomaIndex2.get(j)));
                                    tracedataMontageSomaType.add(tracedataNeuronType2.get(tracedataMontageSomaIndex2.get(j)));
                                    tracedataMontageSomaRadius.add(tracedataNeuronRadius2.get(tracedataMontageSomaIndex2.get(j)));
                                    tracedataMontageSomaSynapse.add(tracedataNeuronSynapse2.get(tracedataMontageSomaIndex2.get(j)));
                                    tracedataMontageSomaConnection.add(tracedataNeuronConnection2.get(tracedataMontageSomaIndex2.get(j)));
                                }
                            }

                        } else if (tracedataMontageSomaZStart2 < tracedataMontageSomaZStart1) {

                            for (int j = 0; j < tracedataMontageSomaIndex2.size(); j++) {

                                String[] tracedataMontageSomaMapTemp2 = new String[2];
                                tracedataMontageSomaMapTemp2[0] = "2";
                                tracedataMontageSomaMapTemp2[1] = tracedataNeuronBranch2.get(tracedataMontageSomaIndex2.get(j))[1].concat(":").concat(tracedataNeuronBranch2.get(tracedataMontageSomaIndex2.get(j))[2]);
                                tracedataMontageSomaMap.add(tracedataMontageSomaMapTemp2);

                                tracedataMontageSomaCoordinate.add(tracedataNeuronCoordinate2.get(tracedataMontageSomaIndex2.get(j)));
                                tracedataMontageSomaType.add(tracedataNeuronType2.get(tracedataMontageSomaIndex2.get(j)));
                                tracedataMontageSomaRadius.add(tracedataNeuronRadius2.get(tracedataMontageSomaIndex2.get(j)));
                                tracedataMontageSomaSynapse.add(tracedataNeuronSynapse2.get(tracedataMontageSomaIndex2.get(j)));
                                tracedataMontageSomaConnection.add(tracedataNeuronConnection2.get(tracedataMontageSomaIndex2.get(j)));
                            }

                            if ((tracedataMontageSomaZEnd2 - tracedataMontageSomaZStart1 + 1) >= 0) {
                                for (int j = tracedataMontageSomaZEnd2 - tracedataMontageSomaZStart1 + 1; j < tracedataMontageSomaIndex1.size(); j++) {

                                    String[] tracedataMontageSomaMapTemp1 = new String[2];
                                    tracedataMontageSomaMapTemp1[0] = "1";
                                    tracedataMontageSomaMapTemp1[1] = tracedataNeuronBranch1.get(tracedataMontageSomaIndex1.get(j))[1].concat(":").concat(tracedataNeuronBranch1.get(tracedataMontageSomaIndex1.get(j))[2]);
                                    tracedataMontageSomaMap.add(tracedataMontageSomaMapTemp1);

                                    tracedataMontageSomaCoordinate.add(tracedataNeuronCoordinateModified1.get(tracedataMontageSomaIndex1.get(j)));
                                    tracedataMontageSomaType.add(tracedataNeuronTypeModified1.get(tracedataMontageSomaIndex1.get(j)));
                                    tracedataMontageSomaRadius.add(tracedataNeuronRadiusModified1.get(tracedataMontageSomaIndex1.get(j)));
                                    tracedataMontageSomaSynapse.add(tracedataNeuronSynapseModified1.get(tracedataMontageSomaIndex1.get(j)));
                                    tracedataMontageSomaConnection.add(tracedataNeuronConnectionModified1.get(tracedataMontageSomaIndex1.get(j)));
                                }
                            }
                        }
                    }
                   
                    for (int j = 0; j < tracedataMontageIndex1.size(); j++) {
                        if (matchedOverlappedTraceColumn1.contains(Integer.parseInt(tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[1]))) {
                            if (tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[0].equals("Neurite") && ! matchedAllOverlappedTraceColumn2.contains(tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[1].concat("-").concat(tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[2].split("-")[1]))) {

                                String[] tracedataMontageNeuriteMapTemp1 = new String[3];
                                tracedataMontageNeuriteMapTemp1[0] = "1";
                                tracedataMontageNeuriteMapTemp1[1] = tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[1];
                                tracedataMontageNeuriteMapTemp1[2] = tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[2];
                                tracedataMontageNeuriteMap.add(tracedataMontageNeuriteMapTemp1);

                                tracedataMontageNeuriteCoordinate.add(tracedataNeuronCoordinateModified1.get(tracedataMontageIndex1.get(j)));
                                tracedataMontageNeuriteType.add(tracedataNeuronTypeModified1.get(tracedataMontageIndex1.get(j)));
                                tracedataMontageNeuriteRadius.add(tracedataNeuronRadiusModified1.get(tracedataMontageIndex1.get(j)));
                                tracedataMontageNeuriteSynapse.add(tracedataNeuronSynapseModified1.get(tracedataMontageIndex1.get(j)));
                                tracedataMontageNeuriteConnection.add(tracedataNeuronConnectionModified1.get(tracedataMontageIndex1.get(j)));
                            }
                        }
                    }

                    for (int j = 0; j < tracedataMontageIndex2.size(); j++) {
                        if (matchedOverlappedTraceColumn4.contains(Integer.parseInt(tracedataNeuronBranch2.get(tracedataMontageIndex2.get(j))[1]))) {
                            if (tracedataNeuronBranch2.get(tracedataMontageIndex2.get(j))[0].equals("Neurite") && ! matchedAllOverlappedTraceColumn5.contains(tracedataNeuronBranch2.get(tracedataMontageIndex2.get(j))[1].concat("-").concat(tracedataNeuronBranch2.get(tracedataMontageIndex2.get(j))[2].split("-")[1]))) {

                                String[] tracedataMontageNeuriteMapTemp2 = new String[3];
                                tracedataMontageNeuriteMapTemp2[0] = "2";
                                tracedataMontageNeuriteMapTemp2[1] = tracedataNeuronBranch2.get(tracedataMontageIndex2.get(j))[1];
                                tracedataMontageNeuriteMapTemp2[2] = tracedataNeuronBranch2.get(tracedataMontageIndex2.get(j))[2];
                                tracedataMontageNeuriteMap.add(tracedataMontageNeuriteMapTemp2);

                                tracedataMontageNeuriteCoordinate.add(tracedataNeuronCoordinate2.get(tracedataMontageIndex2.get(j)));
                                tracedataMontageNeuriteType.add(tracedataNeuronType2.get(tracedataMontageIndex2.get(j)));
                                tracedataMontageNeuriteRadius.add(tracedataNeuronRadius2.get(tracedataMontageIndex2.get(j)));
                                tracedataMontageNeuriteSynapse.add(tracedataNeuronSynapse2.get(tracedataMontageIndex2.get(j)));
                                tracedataMontageNeuriteConnection.add(tracedataNeuronConnection2.get(tracedataMontageIndex2.get(j)));
                            }
                        }
                    }

                    String[] tracedataMontageNeuronTagTemp = new String[2];
                    tracedataMontageNeuronTagTemp[0] = neuronNumber.toString();

                    for (int j = 0; j < tracedataNeuronTag1.size(); j++) {
                        if (matchedOverlappedTraceColumn1.contains(Integer.parseInt(tracedataNeuronTag1.get(j)[0]))) {
                            if (! tracedataNeuronTag1.get(j)[1].isEmpty()) {
                                tracedataMontageNeuronTagTemp[1] = tracedataNeuronTag1.get(j)[1].concat(";data1;data2");
                                break;
                            } else {
                                tracedataMontageNeuronTagTemp[1] = "data1;data2";
                                break;
                            }
                        }
                    }

                    tracedataMontageNeuronTag.add(tracedataMontageNeuronTagTemp);

                    for (int j = 0; j < tracedataMontageSomaMap.size(); j++) {

                        String[] tracedataMontageSomaBranchTemp = new String[3];
                        tracedataMontageSomaBranchTemp[0] = "Soma";
                        tracedataMontageSomaBranchTemp[1] = Integer.toString(neuronNumber);
                        tracedataMontageSomaBranchTemp[2] = tracedataMontageSomaMap.get(j)[1].split(":")[1];

                        tracedataMontageNeuronBranch.add(tracedataMontageSomaBranchTemp);
                        tracedataMontageNeuronCoordinate.add(tracedataMontageSomaCoordinate.get(j));
                        tracedataMontageNeuronType.add(tracedataMontageSomaType.get(j));
                        tracedataMontageNeuronRadius.add(tracedataMontageSomaRadius.get(j));
                        tracedataMontageNeuronSynapse.add(tracedataMontageSomaSynapse.get(j));
                        tracedataMontageNeuronConnection.add(tracedataMontageSomaConnection.get(j));

                        String[] tracedataMontageNeuronMapTemp = new String[2];
                        tracedataMontageNeuronMapTemp[0] = tracedataMontageSomaMap.get(j)[1];
                        tracedataMontageNeuronMapTemp[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(":").concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);

                        if (tracedataMontageSomaMap.get(j)[0].equals("1")) {

                            Integer[] tracedataMontageNeuronMapTemp12 = new Integer[3];
                            tracedataMontageNeuronMapTemp12[0] = 1;
                            tracedataMontageNeuronMapTemp12[1] = tracedataMontageNeuronMap1.size();
                            tracedataMontageNeuronMapTemp12[2] = null;
                            tracedataMontageNeuronMap12.add(tracedataMontageNeuronMapTemp12);

                            tracedataMontageNeuronMap1.add(tracedataMontageNeuronMapTemp);

                        } else if (tracedataMontageSomaMap.get(j)[0].equals("2")) {
                            
                            Integer[] tracedataMontageNeuronMapTemp12 = new Integer[3];
                            tracedataMontageNeuronMapTemp12[0] = 2;
                            tracedataMontageNeuronMapTemp12[1] = null;
                            tracedataMontageNeuronMapTemp12[2] = tracedataMontageNeuronMap2.size();
                            tracedataMontageNeuronMap12.add(tracedataMontageNeuronMapTemp12);

                            tracedataMontageNeuronMap2.add(tracedataMontageNeuronMapTemp);
                        }
                    }

                    String[] neuriteBranchOld = {"0", "0", "0"};
                    Integer neuriteBranchNew = 0;

                    for (int j = 0; j < tracedataMontageNeuriteMap.size(); j++) {

                        String[] tracedataMontageNeuriteBranchTemp = new String[3];
                        tracedataMontageNeuriteBranchTemp[0] = "Neurite";
                        tracedataMontageNeuriteBranchTemp[1] = Integer.toString(neuronNumber);
                        
                        if (! neuriteBranchOld[0].equals(tracedataMontageNeuriteMap.get(j)[0]) || ! neuriteBranchOld[1].equals(tracedataMontageNeuriteMap.get(j)[1]) || ! neuriteBranchOld[2].equals(tracedataMontageNeuriteMap.get(j)[2].split("-")[1])) {
                            neuriteBranchNew++;

                            neuriteBranchOld[0] = tracedataMontageNeuriteMap.get(j)[0];
                            neuriteBranchOld[1] = tracedataMontageNeuriteMap.get(j)[1];
                            neuriteBranchOld[2] = tracedataMontageNeuriteMap.get(j)[2].split("-")[1];
                        }

                        if (tracedataMontageNeuriteMap.get(j)[2].split("-").length > 2) {
                            tracedataMontageNeuriteBranchTemp[2] = "-".concat(neuriteBranchNew.toString()).concat("-").concat(tracedataMontageNeuriteMap.get(j)[2].split("-", 3)[2]);
                        } else {
                            tracedataMontageNeuriteBranchTemp[2] = "-".concat(neuriteBranchNew.toString());
                        }

                        tracedataMontageNeuronBranch.add(tracedataMontageNeuriteBranchTemp);
                        tracedataMontageNeuronCoordinate.add(tracedataMontageNeuriteCoordinate.get(j));
                        tracedataMontageNeuronType.add(tracedataMontageNeuriteType.get(j));
                        tracedataMontageNeuronRadius.add(tracedataMontageNeuriteRadius.get(j));
                        tracedataMontageNeuronSynapse.add(tracedataMontageNeuriteSynapse.get(j));
                        tracedataMontageNeuronConnection.add(tracedataMontageNeuriteConnection.get(j));

                        String[] tracedataMontageNeuronMapTemp = new String[2];
                        tracedataMontageNeuronMapTemp[0] = tracedataMontageNeuriteMap.get(j)[1].concat(tracedataMontageNeuriteMap.get(j)[2]);
                        tracedataMontageNeuronMapTemp[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);

                        if (tracedataMontageNeuriteMap.get(j)[0].equals("1")) {

                            Integer[] tracedataMontageNeuronMapTemp12 = new Integer[3];
                            tracedataMontageNeuronMapTemp12[0] = 1;
                            tracedataMontageNeuronMapTemp12[1] = tracedataMontageNeuronMap1.size();
                            tracedataMontageNeuronMapTemp12[2] = null;
                            tracedataMontageNeuronMap12.add(tracedataMontageNeuronMapTemp12);

                            tracedataMontageNeuronMap1.add(tracedataMontageNeuronMapTemp);

                        } else if (tracedataMontageNeuriteMap.get(j)[0].equals("2")) {

                            Integer[] tracedataMontageNeuronMapTemp12 = new Integer[3];
                            tracedataMontageNeuronMapTemp12[0] = 2;
                            tracedataMontageNeuronMapTemp12[1] = null;
                            tracedataMontageNeuronMapTemp12[2] = tracedataMontageNeuronMap2.size();
                            tracedataMontageNeuronMap12.add(tracedataMontageNeuronMapTemp12);

                            tracedataMontageNeuronMap2.add(tracedataMontageNeuronMapTemp);
                        }
                    }

                    System.out.println("Size of tracedataMontageNeuronMap12: " + tracedataMontageNeuronMap12.size());
                    System.out.println("Size of tracedataMontageNeuronMap1: " + tracedataMontageNeuronMap1.size());
                    System.out.println("Size of tracedataMontageNeuronMap2: " + tracedataMontageNeuronMap2.size());
                    
                    for (int j = 0; j < tracedataMergingNeuronIndex1.size(); j++) {

                        neuriteBranchNew++;

                        ArrayList<Integer[]> mergingNeuriteMap = new ArrayList<Integer[]>();

                        ArrayList<Integer[][]> tracedataMergingNeuronCoordinate = new ArrayList<Integer[][]>();
                        ArrayList<String[]> tracedataMergingNeuronType = new ArrayList<String[]>();
                        ArrayList<Integer[]> tracedataMergingNeuronRadius = new ArrayList<Integer[]>();
                        ArrayList<Integer[]> tracedataMergingNeuronSynapse = new ArrayList<Integer[]>();
                        ArrayList<String[]> tracedataMergingNeuronConnection = new ArrayList<String[]>();

                        for (int k = 0; k < tracedataMergingNeuronIndex1.get(j).length; k++) {

                            Integer[] mergingNeuriteMapTemp1 = new Integer[2];
                            mergingNeuriteMapTemp1[0] = 1;
                            mergingNeuriteMapTemp1[1] = k;
                            mergingNeuriteMap.add(mergingNeuriteMapTemp1);

                            tracedataMergingNeuronCoordinate.add(tracedataNeuronCoordinateModified1.get(tracedataMergingNeuronIndex1.get(j)[k]));
                            tracedataMergingNeuronType.add(tracedataNeuronTypeModified1.get(tracedataMergingNeuronIndex1.get(j)[k]));
                            tracedataMergingNeuronRadius.add(tracedataNeuronRadiusModified1.get(tracedataMergingNeuronIndex1.get(j)[k]));
                            tracedataMergingNeuronSynapse.add(tracedataNeuronSynapseModified1.get(tracedataMergingNeuronIndex1.get(j)[k]));
                            tracedataMergingNeuronConnection.add(tracedataNeuronConnectionModified1.get(tracedataMergingNeuronIndex1.get(j)[k]));
                        }

                        for (int k = 0; k < tracedataMergingNeuronIndex2.get(j).length; k++) {

                            Integer[] mergingNeuriteMapTemp2 = new Integer[2];
                            mergingNeuriteMapTemp2[0] = 2;
                            mergingNeuriteMapTemp2[1] = k;
                            mergingNeuriteMap.add(mergingNeuriteMapTemp2);

                            tracedataMergingNeuronCoordinate.add(tracedataNeuronCoordinate2.get(tracedataMergingNeuronIndex2.get(j)[k]));
                            tracedataMergingNeuronType.add(tracedataNeuronType2.get(tracedataMergingNeuronIndex2.get(j)[k]));
                            tracedataMergingNeuronRadius.add(tracedataNeuronRadius2.get(tracedataMergingNeuronIndex2.get(j)[k]));
                            tracedataMergingNeuronSynapse.add(tracedataNeuronSynapse2.get(tracedataMergingNeuronIndex2.get(j)[k]));
                            tracedataMergingNeuronConnection.add(tracedataNeuronConnection2.get(tracedataMergingNeuronIndex2.get(j)[k]));
                        }

                        ArrayList<Integer[]> tracedataMergingNeuronCoordinateBranch = new ArrayList<Integer[]>();
                        ArrayList<Integer[][]> tracedataMergingNeuronVertexBranch = new ArrayList<Integer[][]>();
                        Integer[] tracedataMergingNeuronCoordinateVertexIndex = {0,0};

                        for (int k = 0; k < tracedataMergingNeuronCoordinate.size(); k++) {

                            Set<Integer> tracedataMergingNeuronCoordinateBranchTemp = new HashSet<Integer>();
                            ArrayList<Integer[]> tracedataMergingNeuronVertexBranchTemp = new ArrayList<Integer[]>();

                            int tracedataMergingNeuronVertexBranchTempSize = tracedataMergingNeuronVertexBranchTemp.size();

                            tracedataMergingNeuronVertexBranchTemp.add(tracedataMergingNeuronCoordinate.get(k)[0]);
                            tracedataMergingNeuronVertexBranchTemp.add(tracedataMergingNeuronCoordinate.get(k)[tracedataMergingNeuronCoordinate.get(k).length - 1]);

                            while (tracedataMergingNeuronVertexBranchTemp.size() > tracedataMergingNeuronVertexBranchTempSize) {

                                tracedataMergingNeuronVertexBranchTempSize = tracedataMergingNeuronVertexBranchTemp.size();

                                Set<Integer> tracedataMergingNeuronCoordinateBranchTempTemp = new HashSet<Integer>();

                                for (int kk = 0; kk < tracedataMergingNeuronCoordinate.size(); kk++) {
                                    for (int l = 0; l < tracedataMergingNeuronVertexBranchTemp.size(); l++) {
                                        if (Arrays.equals(tracedataMergingNeuronCoordinate.get(kk)[0], tracedataMergingNeuronVertexBranchTemp.get(l)) || Arrays.equals(tracedataMergingNeuronCoordinate.get(kk)[tracedataMergingNeuronCoordinate.get(kk).length - 1], tracedataMergingNeuronVertexBranchTemp.get(l))) {
                                            tracedataMergingNeuronCoordinateBranchTempTemp.add(kk);
                                        }
                                    }
                                }

                                for (Integer kk : tracedataMergingNeuronCoordinateBranchTempTemp) {

                                    boolean flagStart = false;
                                    boolean flagEnd = false;

                                    for (int l = 0; l < tracedataMergingNeuronVertexBranchTemp.size(); l++) {
                                        if (Arrays.equals(tracedataMergingNeuronCoordinate.get(kk)[0], tracedataMergingNeuronVertexBranchTemp.get(l))) {
                                            flagStart = true;
                                        } else if (Arrays.equals(tracedataMergingNeuronCoordinate.get(kk)[tracedataMergingNeuronCoordinate.get(kk).length - 1], tracedataMergingNeuronVertexBranchTemp.get(l))) {
                                            flagEnd = true;
                                        }
                                    }

                                    if (flagStart == false) {
                                        tracedataMergingNeuronVertexBranchTemp.add(tracedataMergingNeuronCoordinate.get(kk)[0]);
                                    }

                                    if (flagEnd == false) {
                                        tracedataMergingNeuronVertexBranchTemp.add(tracedataMergingNeuronCoordinate.get(kk)[tracedataMergingNeuronCoordinate.get(kk).length - 1]);
                                    }
                                }

                                tracedataMergingNeuronCoordinateBranchTemp.addAll(tracedataMergingNeuronCoordinateBranchTempTemp);
                            }

                            Integer[] tracedataMergingNeuronCoordinateBranchTempTemp = tracedataMergingNeuronCoordinateBranchTemp.toArray(new Integer[tracedataMergingNeuronCoordinateBranchTemp.size()]);
                            Integer[][] tracedataMergingNeuronVertexBranchTempTemp = tracedataMergingNeuronVertexBranchTemp.toArray(new Integer[tracedataMergingNeuronVertexBranchTemp.size()][3]);
                           
                            tracedataMergingNeuronCoordinateBranch.add(tracedataMergingNeuronCoordinateBranchTempTemp);
                            tracedataMergingNeuronVertexBranch.add(tracedataMergingNeuronVertexBranchTempTemp);

                            if ((Math.floor((tracedataMergingNeuronCoordinateBranchTemp.size() + 1) / 2) == (((double) (tracedataMergingNeuronCoordinateBranchTemp.size() + 1)) / 2)) &&
                                    (tracedataMergingNeuronVertexBranchTemp.size() == (tracedataMergingNeuronCoordinateBranchTemp.size() + 1)) &&
                                    (tracedataMergingNeuronVertexBranchTemp.size() > tracedataMergingNeuronCoordinateVertexIndex[1])) {

                                tracedataMergingNeuronCoordinateVertexIndex[0] = k;
                                tracedataMergingNeuronCoordinateVertexIndex[1] = tracedataMergingNeuronVertexBranchTemp.size();
                            }
                        }
                        
                        ArrayList<Integer[]> mergingNeuriteMapNew = new ArrayList<Integer[]>();

                        ArrayList<Integer[][]> tracedataMergingNeuronCoordinateNew = new ArrayList<Integer[][]>();
                        ArrayList<String[]> tracedataMergingNeuronTypeNew = new ArrayList<String[]>();
                        ArrayList<Integer[]> tracedataMergingNeuronRadiusNew = new ArrayList<Integer[]>();
                        ArrayList<Integer[]> tracedataMergingNeuronSynapseNew = new ArrayList<Integer[]>();
                        ArrayList<String[]> tracedataMergingNeuronConnectionNew = new ArrayList<String[]>();

                        for (int k = 0; k < tracedataMergingNeuronCoordinateBranch.get(tracedataMergingNeuronCoordinateVertexIndex[0]).length; k++) {

                            mergingNeuriteMapNew.add(mergingNeuriteMap.get(tracedataMergingNeuronCoordinateBranch.get(tracedataMergingNeuronCoordinateVertexIndex[0])[k]));

                            tracedataMergingNeuronCoordinateNew.add(tracedataMergingNeuronCoordinate.get(tracedataMergingNeuronCoordinateBranch.get(tracedataMergingNeuronCoordinateVertexIndex[0])[k]));
                            tracedataMergingNeuronTypeNew.add(tracedataMergingNeuronType.get(tracedataMergingNeuronCoordinateBranch.get(tracedataMergingNeuronCoordinateVertexIndex[0])[k]));
                            tracedataMergingNeuronRadiusNew.add(tracedataMergingNeuronRadius.get(tracedataMergingNeuronCoordinateBranch.get(tracedataMergingNeuronCoordinateVertexIndex[0])[k]));
                            tracedataMergingNeuronSynapseNew.add(tracedataMergingNeuronSynapse.get(tracedataMergingNeuronCoordinateBranch.get(tracedataMergingNeuronCoordinateVertexIndex[0])[k]));
                            tracedataMergingNeuronConnectionNew.add(tracedataMergingNeuronConnection.get(tracedataMergingNeuronCoordinateBranch.get(tracedataMergingNeuronCoordinateVertexIndex[0])[k]));
                        }

                        mergingNeuriteMap.clear();

                        tracedataMergingNeuronCoordinate.clear();
                        tracedataMergingNeuronType.clear();
                        tracedataMergingNeuronRadius.clear();
                        tracedataMergingNeuronSynapse.clear();
                        tracedataMergingNeuronConnection.clear();

                        mergingNeuriteMap.addAll(mergingNeuriteMapNew);

                        tracedataMergingNeuronCoordinate.addAll(tracedataMergingNeuronCoordinateNew);
                        tracedataMergingNeuronType.addAll(tracedataMergingNeuronTypeNew);
                        tracedataMergingNeuronRadius.addAll(tracedataMergingNeuronRadiusNew);
                        tracedataMergingNeuronSynapse.addAll(tracedataMergingNeuronSynapseNew);
                        tracedataMergingNeuronConnection.addAll(tracedataMergingNeuronConnectionNew);
                        
                        ArrayList<Integer[]> tracedataMergingNeuronVertex = new ArrayList<Integer[]>();
                        tracedataMergingNeuronVertex.addAll(Arrays.asList(tracedataMergingNeuronVertexBranch.get(tracedataMergingNeuronCoordinateVertexIndex[0])));

                        System.out.println("Size of tracedataMergingNeuronVertex: " + tracedataMergingNeuronVertex.size());
                        System.out.println("Size of tracedataMergingNeuronCoordinate: " + tracedataMergingNeuronCoordinate.size());
                        
                        for (int k = 0; k < tracedataMergingNeuronVertex.size(); k++) {
                            System.out.println("tracedataMergingNeuronVertex: " + tracedataMergingNeuronVertex.get(k)[0] + " " + tracedataMergingNeuronVertex.get(k)[1] + " " + tracedataMergingNeuronVertex.get(k)[2]);
                        }

                        Integer rootVertex = 0;
                        Integer rootEdge = 0;

                        for (int k = 0; k < tracedataMergingNeuronVertex.size(); k++) {

                            ArrayList<Integer> tracedataMergingNeuronVertexEdge = new ArrayList<Integer>();

                            for (int l = 0; l < tracedataMergingNeuronCoordinate.size(); l++) {
                                if (Arrays.equals(tracedataMergingNeuronCoordinate.get(l)[0], tracedataMergingNeuronVertex.get(k))) {
                                    tracedataMergingNeuronVertexEdge.add(l);
                                } else if (Arrays.equals(tracedataMergingNeuronCoordinate.get(l)[tracedataMergingNeuronCoordinate.get(l).length - 1], tracedataMergingNeuronVertex.get(k))) {
                                    tracedataMergingNeuronVertexEdge.add(l);
                                }
                            }

                            if (tracedataMergingNeuronVertexEdge.size() == 1) {
                                rootVertex = k;
                                rootEdge = tracedataMergingNeuronVertexEdge.get(0);
                                break;
                            }
                        }

                        ArrayList<Integer> tracedataMergingNeuronIndexOrdered = new ArrayList<Integer>();
                        tracedataMergingNeuronIndexOrdered.add(rootEdge);

                        ArrayList<String> tracedataMergingNeuronBranchOrdered = new ArrayList<String>();
                        tracedataMergingNeuronBranchOrdered.add("-".concat((neuriteBranchNew).toString()));
                        
                        ArrayList<Integer[][]> tracedataMergingNeuronCoordinateOrdered = new ArrayList<Integer[][]>();
                        ArrayList<String[]> tracedataMergingNeuronTypeOrdered = new ArrayList<String[]>();
                        ArrayList<Integer[]> tracedataMergingNeuronRadiusOrdered = new ArrayList<Integer[]>();
                        ArrayList<Integer[]> tracedataMergingNeuronSynapseOrdered = new ArrayList<Integer[]>();
                        ArrayList<String[]> tracedataMergingNeuronConnectionOrdered = new ArrayList<String[]>();

                        if (Arrays.equals(tracedataMergingNeuronCoordinate.get(rootEdge)[0], tracedataMergingNeuronVertex.get(rootVertex))) {

                            tracedataMergingNeuronCoordinateOrdered.add(tracedataMergingNeuronCoordinate.get(rootEdge));
                            tracedataMergingNeuronTypeOrdered.add(tracedataMergingNeuronType.get(rootEdge));
                            tracedataMergingNeuronRadiusOrdered.add(tracedataMergingNeuronRadius.get(rootEdge));
                            tracedataMergingNeuronSynapseOrdered.add(tracedataMergingNeuronSynapse.get(rootEdge));
                            tracedataMergingNeuronConnectionOrdered.add(tracedataMergingNeuronConnection.get(rootEdge));

                        } else {

                            Integer[][] tracedataMergingNeuronCoordinateOrderedTemp =new Integer[tracedataMergingNeuronCoordinate.get(rootEdge).length][3];
                            String[] tracedataMergingNeuronTypeOrderedTemp =new String[tracedataMergingNeuronType.get(rootEdge).length];
                            Integer[] tracedataMergingNeuronRadiusOrderedTemp =new Integer[tracedataMergingNeuronRadius.get(rootEdge).length];
                            Integer[] tracedataMergingNeuronSynapseOrderedTemp =new Integer[tracedataMergingNeuronSynapse.get(rootEdge).length];
                            String[] tracedataMergingNeuronConnectionOrderedTemp =new String[tracedataMergingNeuronConnection.get(rootEdge).length];

                            for (int k = 0; k < tracedataMergingNeuronCoordinate.get(rootEdge).length; k++) {
                                tracedataMergingNeuronCoordinateOrderedTemp[k] = tracedataMergingNeuronCoordinate.get(rootEdge)[tracedataMergingNeuronCoordinate.get(rootEdge).length - k - 1];
                                tracedataMergingNeuronTypeOrderedTemp[k] = tracedataMergingNeuronType.get(rootEdge)[tracedataMergingNeuronType.get(rootEdge).length - k - 1];
                                tracedataMergingNeuronRadiusOrderedTemp[k] = tracedataMergingNeuronRadius.get(rootEdge)[tracedataMergingNeuronRadius.get(rootEdge).length - k - 1];
                                tracedataMergingNeuronSynapseOrderedTemp[k] = tracedataMergingNeuronSynapse.get(rootEdge)[tracedataMergingNeuronSynapse.get(rootEdge).length - k - 1];
                                tracedataMergingNeuronConnectionOrderedTemp[k] = tracedataMergingNeuronConnection.get(rootEdge)[tracedataMergingNeuronConnection.get(rootEdge).length - k - 1];
                            }

                            tracedataMergingNeuronCoordinateOrdered.add(tracedataMergingNeuronCoordinateOrderedTemp);
                            tracedataMergingNeuronTypeOrdered.add(tracedataMergingNeuronTypeOrderedTemp);
                            tracedataMergingNeuronRadiusOrdered.add(tracedataMergingNeuronRadiusOrderedTemp);
                            tracedataMergingNeuronSynapseOrdered.add(tracedataMergingNeuronSynapseOrderedTemp);
                            tracedataMergingNeuronConnectionOrdered.add(tracedataMergingNeuronConnectionOrderedTemp);
                        }

                        Set<Integer> remainingEdgeSet = new HashSet<Integer>();
                        for (int k = 0; k < tracedataMergingNeuronCoordinate.size(); k++) {
                            remainingEdgeSet.add(k);
                        }
                        remainingEdgeSet.remove(rootEdge);

                        Integer lastItemIndex = 0;

                        while (remainingEdgeSet.size() > 0) {

                            System.out.println("Size of remainigEdgeSet: " + remainingEdgeSet.size());
                            boolean flagLoop = false;

                            for (Integer k : remainingEdgeSet) {

                                if (Arrays.equals(tracedataMergingNeuronCoordinateOrdered.get(lastItemIndex)[tracedataMergingNeuronCoordinateOrdered.get(lastItemIndex).length - 1], tracedataMergingNeuronCoordinate.get(k)[0])) {

                                    tracedataMergingNeuronIndexOrdered.add(k);
                                    remainingEdgeSet.remove(k);

                                    tracedataMergingNeuronBranchOrdered.add(tracedataMergingNeuronBranchOrdered.get(lastItemIndex).concat("-1"));
                                    tracedataMergingNeuronCoordinateOrdered.add(tracedataMergingNeuronCoordinate.get(k));
                                    tracedataMergingNeuronTypeOrdered.add(tracedataMergingNeuronType.get(k));
                                    tracedataMergingNeuronRadiusOrdered.add(tracedataMergingNeuronRadius.get(k));
                                    tracedataMergingNeuronSynapseOrdered.add(tracedataMergingNeuronSynapse.get(k));
                                    tracedataMergingNeuronConnectionOrdered.add(tracedataMergingNeuronConnection.get(k));

                                    lastItemIndex++;
                                    flagLoop = true;
                                    break;
                                    
                                } else if (Arrays.equals(tracedataMergingNeuronCoordinateOrdered.get(lastItemIndex)[tracedataMergingNeuronCoordinateOrdered.get(lastItemIndex).length - 1], tracedataMergingNeuronCoordinate.get(k)[tracedataMergingNeuronCoordinate.get(k).length - 1])) {

                                    Integer[][] tracedataMergingNeuronCoordinateOrderedTemp =new Integer[tracedataMergingNeuronCoordinate.get(k).length][3];
                                    String[] tracedataMergingNeuronTypeOrderedTemp =new String[tracedataMergingNeuronType.get(k).length];
                                    Integer[] tracedataMergingNeuronRadiusOrderedTemp =new Integer[tracedataMergingNeuronRadius.get(k).length];
                                    Integer[] tracedataMergingNeuronSynapseOrderedTemp =new Integer[tracedataMergingNeuronSynapse.get(k).length];
                                    String[] tracedataMergingNeuronConnectionOrderedTemp =new String[tracedataMergingNeuronConnection.get(k).length];

                                    for (int l = 0; l < tracedataMergingNeuronCoordinate.get(k).length; l++) {
                                        tracedataMergingNeuronCoordinateOrderedTemp[l] = tracedataMergingNeuronCoordinate.get(k)[tracedataMergingNeuronCoordinate.get(k).length - l - 1];
                                        tracedataMergingNeuronTypeOrderedTemp[l] = tracedataMergingNeuronType.get(k)[tracedataMergingNeuronType.get(k).length - l - 1];
                                        tracedataMergingNeuronRadiusOrderedTemp[l] = tracedataMergingNeuronRadius.get(k)[tracedataMergingNeuronRadius.get(k).length - l - 1];
                                        tracedataMergingNeuronSynapseOrderedTemp[l] = tracedataMergingNeuronSynapse.get(k)[tracedataMergingNeuronSynapse.get(k).length - l - 1];
                                        tracedataMergingNeuronConnectionOrderedTemp[l] = tracedataMergingNeuronConnection.get(k)[tracedataMergingNeuronConnection.get(k).length - l - 1];
                                    }

                                    tracedataMergingNeuronIndexOrdered.add(k);
                                    remainingEdgeSet.remove(k);

                                    tracedataMergingNeuronBranchOrdered.add(tracedataMergingNeuronBranchOrdered.get(lastItemIndex).concat("-1"));
                                    tracedataMergingNeuronCoordinateOrdered.add(tracedataMergingNeuronCoordinateOrderedTemp);
                                    tracedataMergingNeuronTypeOrdered.add(tracedataMergingNeuronTypeOrderedTemp);
                                    tracedataMergingNeuronRadiusOrdered.add(tracedataMergingNeuronRadiusOrderedTemp);
                                    tracedataMergingNeuronSynapseOrdered.add(tracedataMergingNeuronSynapseOrderedTemp);
                                    tracedataMergingNeuronConnectionOrdered.add(tracedataMergingNeuronConnectionOrderedTemp);

                                    lastItemIndex++;
                                    flagLoop = true;
                                    break;
                                }
                            }

                            if (flagLoop == false) {

                                OuterLoop:
                                for (int k = lastItemIndex - 1; k >= 0; k--) {

                                    for (Integer l : remainingEdgeSet) {

                                        if (Arrays.equals(tracedataMergingNeuronCoordinateOrdered.get(k)[tracedataMergingNeuronCoordinateOrdered.get(k).length - 1], tracedataMergingNeuronCoordinate.get(l)[0])) {

                                            tracedataMergingNeuronIndexOrdered.add(l);
                                            remainingEdgeSet.remove(l);

                                            tracedataMergingNeuronBranchOrdered.add(tracedataMergingNeuronBranchOrdered.get(k).concat("-2"));
                                            tracedataMergingNeuronCoordinateOrdered.add(tracedataMergingNeuronCoordinate.get(l));
                                            tracedataMergingNeuronTypeOrdered.add(tracedataMergingNeuronType.get(l));
                                            tracedataMergingNeuronRadiusOrdered.add(tracedataMergingNeuronRadius.get(l));
                                            tracedataMergingNeuronSynapseOrdered.add(tracedataMergingNeuronSynapse.get(l));
                                            tracedataMergingNeuronConnectionOrdered.add(tracedataMergingNeuronConnection.get(l));

                                            lastItemIndex++;
                                            flagLoop = true;
                                            break OuterLoop;

                                        } else if (Arrays.equals(tracedataMergingNeuronCoordinateOrdered.get(k)[tracedataMergingNeuronCoordinateOrdered.get(k).length - 1], tracedataMergingNeuronCoordinate.get(l)[tracedataMergingNeuronCoordinate.get(l).length - 1])) {

                                            Integer[][] tracedataMergingNeuronCoordinateOrderedTemp =new Integer[tracedataMergingNeuronCoordinate.get(l).length][3];
                                            String[] tracedataMergingNeuronTypeOrderedTemp =new String[tracedataMergingNeuronType.get(l).length];
                                            Integer[] tracedataMergingNeuronRadiusOrderedTemp =new Integer[tracedataMergingNeuronRadius.get(l).length];
                                            Integer[] tracedataMergingNeuronSynapseOrderedTemp =new Integer[tracedataMergingNeuronSynapse.get(l).length];
                                            String[] tracedataMergingNeuronConnectionOrderedTemp =new String[tracedataMergingNeuronConnection.get(l).length];

                                            for (int ll = 0; ll < tracedataMergingNeuronCoordinate.get(l).length; ll++) {
                                                tracedataMergingNeuronCoordinateOrderedTemp[ll] = tracedataMergingNeuronCoordinate.get(l)[tracedataMergingNeuronCoordinate.get(l).length - ll - 1];
                                                tracedataMergingNeuronTypeOrderedTemp[ll] = tracedataMergingNeuronType.get(l)[tracedataMergingNeuronType.get(l).length - ll - 1];
                                                tracedataMergingNeuronRadiusOrderedTemp[ll] = tracedataMergingNeuronRadius.get(l)[tracedataMergingNeuronRadius.get(l).length - ll - 1];
                                                tracedataMergingNeuronSynapseOrderedTemp[ll] = tracedataMergingNeuronSynapse.get(l)[tracedataMergingNeuronSynapse.get(l).length - ll - 1];
                                                tracedataMergingNeuronConnectionOrderedTemp[ll] = tracedataMergingNeuronConnection.get(l)[tracedataMergingNeuronConnection.get(l).length - ll - 1];
                                            }

                                            tracedataMergingNeuronIndexOrdered.add(l);
                                            remainingEdgeSet.remove(l);

                                            tracedataMergingNeuronBranchOrdered.add(tracedataMergingNeuronBranchOrdered.get(k).concat("-2"));
                                            tracedataMergingNeuronCoordinateOrdered.add(tracedataMergingNeuronCoordinateOrderedTemp);
                                            tracedataMergingNeuronTypeOrdered.add(tracedataMergingNeuronTypeOrderedTemp);
                                            tracedataMergingNeuronRadiusOrdered.add(tracedataMergingNeuronRadiusOrderedTemp);
                                            tracedataMergingNeuronSynapseOrdered.add(tracedataMergingNeuronSynapseOrderedTemp);
                                            tracedataMergingNeuronConnectionOrdered.add(tracedataMergingNeuronConnectionOrderedTemp);

                                            lastItemIndex++;
                                            flagLoop = true;
                                            break OuterLoop;
                                        }
                                    }
                                }
                            }
                        }

                        for (int k = 0; k < tracedataMergingNeuronIndexOrdered.size(); k++) {

                            String[] tracedataMergingNeuronBranchTemp = new String[3];
                            tracedataMergingNeuronBranchTemp[0] = "Neurite";
                            tracedataMergingNeuronBranchTemp[1] = Integer.toString(neuronNumber);
                            tracedataMergingNeuronBranchTemp[2] = tracedataMergingNeuronBranchOrdered.get(k);

                            tracedataMontageNeuronBranch.add(tracedataMergingNeuronBranchTemp);
                            tracedataMontageNeuronCoordinate.add(tracedataMergingNeuronCoordinateOrdered.get(k));
                            tracedataMontageNeuronType.add(tracedataMergingNeuronTypeOrdered.get(k));
                            tracedataMontageNeuronRadius.add(tracedataMergingNeuronRadiusOrdered.get(k));
                            tracedataMontageNeuronSynapse.add(tracedataMergingNeuronSynapseOrdered.get(k));
                            tracedataMontageNeuronConnection.add(tracedataMergingNeuronConnectionOrdered.get(k));

                            if (mergingNeuriteMap.get(tracedataMergingNeuronIndexOrdered.get(k))[0] == 1) {
                                
                                int index1 = mergingNeuriteMap.get(tracedataMergingNeuronIndexOrdered.get(k))[1];
                                int index2 = 0;

                                boolean flagIndex2 = false;
                                
                                for (int l = 0; l < tracedataMergingNeuronIndex12.get(j).length; l++) {
                                    if (tracedataMergingNeuronIndex12.get(j)[l][0].equals(tracedataMergingNeuronIndex1.get(j)[index1])) {
                                        index2 = tracedataMergingNeuronIndex12.get(j)[l][1];
                                        flagIndex2 = true;
                                        break;
                                    }
                                }
                                
                                if (flagIndex2 == false) {

                                    String[] tracedataMontageNeuronMapTemp1 = new String[2];
                                    tracedataMontageNeuronMapTemp1[0] = tracedataNeuronBranch1.get(tracedataMergingNeuronIndex1.get(j)[index1])[1].concat(tracedataNeuronBranch1.get(tracedataMergingNeuronIndex1.get(j)[index1])[2]);
                                    tracedataMontageNeuronMapTemp1[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);

                                    Integer[] tracedataMontageNeuronMapTemp12 = new Integer[3];
                                    tracedataMontageNeuronMapTemp12[0] = 1;
                                    tracedataMontageNeuronMapTemp12[1] = tracedataMontageNeuronMap1.size();
                                    tracedataMontageNeuronMapTemp12[2] = null;
                                    tracedataMontageNeuronMap12.add(tracedataMontageNeuronMapTemp12);

                                    tracedataMontageNeuronMap1.add(tracedataMontageNeuronMapTemp1);

                                } else {

                                    String[] tracedataMontageNeuronMapTemp1 = new String[2];
                                    tracedataMontageNeuronMapTemp1[0] = tracedataNeuronBranch1.get(tracedataMergingNeuronIndex1.get(j)[index1])[1].concat(tracedataNeuronBranch1.get(tracedataMergingNeuronIndex1.get(j)[index1])[2]);
                                    tracedataMontageNeuronMapTemp1[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);

                                    String[] tracedataMontageNeuronMapTemp2 = new String[2];
                                    tracedataMontageNeuronMapTemp2[0] = tracedataNeuronBranch2.get(index2)[1].concat(tracedataNeuronBranch2.get(index2)[2]);
                                    tracedataMontageNeuronMapTemp2[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);

                                    Integer[] tracedataMontageNeuronMapTemp12 = new Integer[3];
                                    tracedataMontageNeuronMapTemp12[0] = 12;
                                    tracedataMontageNeuronMapTemp12[1] = tracedataMontageNeuronMap1.size();
                                    tracedataMontageNeuronMapTemp12[2] = tracedataMontageNeuronMap2.size();
                                    tracedataMontageNeuronMap12.add(tracedataMontageNeuronMapTemp12);

                                    tracedataMontageNeuronMap1.add(tracedataMontageNeuronMapTemp1);
                                    tracedataMontageNeuronMap2.add(tracedataMontageNeuronMapTemp2);
                                }

                            } else if (mergingNeuriteMap.get(tracedataMergingNeuronIndexOrdered.get(k))[0] == 2) {
                                
                                int index2 = mergingNeuriteMap.get(tracedataMergingNeuronIndexOrdered.get(k))[1];

                                String[] tracedataMontageNeuronMapTemp2 = new String[2];
                                tracedataMontageNeuronMapTemp2[0] = tracedataNeuronBranch2.get(tracedataMergingNeuronIndex2.get(j)[index2])[1].concat(tracedataNeuronBranch2.get(tracedataMergingNeuronIndex2.get(j)[index2])[2]);
                                tracedataMontageNeuronMapTemp2[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);

                                Integer[] tracedataMontageNeuronMapTemp12 = new Integer[3];
                                tracedataMontageNeuronMapTemp12[0] = 2;
                                tracedataMontageNeuronMapTemp12[1] = null;
                                tracedataMontageNeuronMapTemp12[2] = tracedataMontageNeuronMap2.size();
                                tracedataMontageNeuronMap12.add(tracedataMontageNeuronMapTemp12);

                                tracedataMontageNeuronMap2.add(tracedataMontageNeuronMapTemp2);
                            }
                        }
                    }
                }
            }

            System.out.println("Size of tracedataMontageNeuronMap12: " + tracedataMontageNeuronMap12.size());
            System.out.println("Size of tracedataMontageNeuronMap1: " + tracedataMontageNeuronMap1.size());
            System.out.println("Size of tracedataMontageNeuronMap2: " + tracedataMontageNeuronMap2.size());

            Integer spineNumber = 0;

            for (int i = 0; i < tracedataMontageNeuronBranch.size(); i++) {
                for (int j = 0; j < tracedataMontageNeuronType.get(i).length; j++) {

                    if (tracedataMontageNeuronType.get(i)[j].contains(":") && tracedataMontageNeuronType.get(i)[j].contains("#")) {
                        
                        String[] typeOldTemp1 = tracedataMontageNeuronType.get(i)[j].split(":");

                        if (typeOldTemp1.length == 2) {

                            String typeOldTemp2 = typeOldTemp1[1].split("#")[1];

                            for (int k = 0; k < tracedataMontageSpineMap12.size(); k++) {

                                if (tracedataMontageSpineMap12.get(k)[0] == 1 && tracedataMontageSpineMap12.get(k)[2] == Integer.parseInt(typeOldTemp2)) {
                                    spineNumber++;
                                    tracedataMontageNeuronType.get(i)[j] = typeOldTemp1[0].concat(":Spine#").concat(spineNumber.toString());

                                    String[] tracedataMontageNeuronBranchTemp1 = new String[3];
                                    tracedataMontageNeuronBranchTemp1[0] = "Spine";
                                    tracedataMontageNeuronBranchTemp1[1] = Integer.toString(spineNumber);
                                    tracedataMontageNeuronBranchTemp1[2] = null;

                                    tracedataMontageNeuronBranch.add(tracedataMontageNeuronBranchTemp1);
                                    tracedataMontageNeuronCoordinate.add(tracedataNeuronCoordinateModified1.get(tracedataMontageSpineMap12.get(k)[1]));
                                    tracedataMontageNeuronType.add(tracedataNeuronTypeModified1.get(tracedataMontageSpineMap12.get(k)[1]));
                                    tracedataMontageNeuronRadius.add(tracedataNeuronRadiusModified1.get(tracedataMontageSpineMap12.get(k)[1]));
                                    tracedataMontageNeuronSynapse.add(tracedataNeuronSynapseModified1.get(tracedataMontageSpineMap12.get(k)[1]));
                                    tracedataMontageNeuronConnection.add(tracedataNeuronConnectionModified1.get(tracedataMontageSpineMap12.get(k)[1]));

                                    Integer[] tracedataMontageNeuronMapTemp12 = new Integer[3];
                                    tracedataMontageNeuronMapTemp12[0] = 1;
                                    tracedataMontageNeuronMapTemp12[1] = tracedataMontageNeuronMap1.size();
                                    tracedataMontageNeuronMapTemp12[2] = null;
                                    tracedataMontageNeuronMap12.add(tracedataMontageNeuronMapTemp12);

                                    String[] tracedataMontageNeuronMapTemp1 = new String[2];
                                    tracedataMontageNeuronMapTemp1[0] = tracedataNeuronBranch1.get(tracedataMontageSpineMap12.get(k)[1])[1];
                                    tracedataMontageNeuronMapTemp1[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1];
                                    tracedataMontageNeuronMap1.add(tracedataMontageNeuronMapTemp1);

                                    break;
                                    
                                } else if (tracedataMontageSpineMap12.get(k)[0] == 2 && tracedataMontageSpineMap12.get(k)[2] == Integer.parseInt(typeOldTemp2)) {
                                    spineNumber++;
                                    tracedataMontageNeuronType.get(i)[j] = typeOldTemp1[0].concat(":Spine#").concat(spineNumber.toString());

                                    String[] tracedataMontageNeuronBranchTemp2 = new String[3];
                                    tracedataMontageNeuronBranchTemp2[0] = "Spine";
                                    tracedataMontageNeuronBranchTemp2[1] = Integer.toString(spineNumber);
                                    tracedataMontageNeuronBranchTemp2[2] = null;

                                    tracedataMontageNeuronBranch.add(tracedataMontageNeuronBranchTemp2);
                                    tracedataMontageNeuronCoordinate.add(tracedataNeuronCoordinate2.get(tracedataMontageSpineMap12.get(k)[1]));
                                    tracedataMontageNeuronType.add(tracedataNeuronType2.get(tracedataMontageSpineMap12.get(k)[1]));
                                    tracedataMontageNeuronRadius.add(tracedataNeuronRadius2.get(tracedataMontageSpineMap12.get(k)[1]));
                                    tracedataMontageNeuronSynapse.add(tracedataNeuronSynapse2.get(tracedataMontageSpineMap12.get(k)[1]));
                                    tracedataMontageNeuronConnection.add(tracedataNeuronConnection2.get(tracedataMontageSpineMap12.get(k)[1]));

                                    Integer[] tracedataMontageNeuronMapTemp12 = new Integer[3];
                                    tracedataMontageNeuronMapTemp12[0] = 2;
                                    tracedataMontageNeuronMapTemp12[1] = null;
                                    tracedataMontageNeuronMapTemp12[2] = tracedataMontageNeuronMap2.size();
                                    tracedataMontageNeuronMap12.add(tracedataMontageNeuronMapTemp12);

                                    String[] tracedataMontageNeuronMapTemp2 = new String[2];
                                    tracedataMontageNeuronMapTemp2[0] = tracedataNeuronBranch2.get(tracedataMontageSpineMap12.get(k)[1])[1];
                                    tracedataMontageNeuronMapTemp2[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1];
                                    tracedataMontageNeuronMap2.add(tracedataMontageNeuronMapTemp2);

                                    break;
                                }
                            }
                            
                        } else if (typeOldTemp1.length == 3) {
                            
                            String typeOldTemp2 = typeOldTemp1[1].split("#")[1];

                            for (int k = 0; k < tracedataMontageSpineMap12.size(); k++) {

                                if (typeOldTemp1[2].equals("data1") && tracedataMontageSpineMap12.get(k)[0] == 1 && tracedataMontageSpineMap12.get(k)[2] == Integer.parseInt(typeOldTemp2)) {
                                    spineNumber++;
                                    tracedataMontageNeuronType.get(i)[j] = typeOldTemp1[0].concat(":Spine#").concat(spineNumber.toString());

                                    String[] tracedataMontageNeuronBranchTemp1 = new String[3];
                                    tracedataMontageNeuronBranchTemp1[0] = "Spine";
                                    tracedataMontageNeuronBranchTemp1[1] = Integer.toString(spineNumber);
                                    tracedataMontageNeuronBranchTemp1[2] = null;

                                    tracedataMontageNeuronBranch.add(tracedataMontageNeuronBranchTemp1);
                                    tracedataMontageNeuronCoordinate.add(tracedataNeuronCoordinateModified1.get(tracedataMontageSpineMap12.get(k)[1]));
                                    tracedataMontageNeuronType.add(tracedataNeuronTypeModified1.get(tracedataMontageSpineMap12.get(k)[1]));
                                    tracedataMontageNeuronRadius.add(tracedataNeuronRadiusModified1.get(tracedataMontageSpineMap12.get(k)[1]));
                                    tracedataMontageNeuronSynapse.add(tracedataNeuronSynapseModified1.get(tracedataMontageSpineMap12.get(k)[1]));
                                    tracedataMontageNeuronConnection.add(tracedataNeuronConnectionModified1.get(tracedataMontageSpineMap12.get(k)[1]));

                                    Integer[] tracedataMontageNeuronMapTemp12 = new Integer[3];
                                    tracedataMontageNeuronMapTemp12[0] = 1;
                                    tracedataMontageNeuronMapTemp12[1] = tracedataMontageNeuronMap1.size();
                                    tracedataMontageNeuronMapTemp12[2] = null;
                                    tracedataMontageNeuronMap12.add(tracedataMontageNeuronMapTemp12);

                                    String[] tracedataMontageNeuronMapTemp1 = new String[2];
                                    tracedataMontageNeuronMapTemp1[0] = tracedataNeuronBranch1.get(tracedataMontageSpineMap12.get(k)[1])[1];
                                    tracedataMontageNeuronMapTemp1[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1];
                                    tracedataMontageNeuronMap1.add(tracedataMontageNeuronMapTemp1);

                                    break;

                                } else if (typeOldTemp1[2].equals("data2") && tracedataMontageSpineMap12.get(k)[0] == 2 && tracedataMontageSpineMap12.get(k)[2] == Integer.parseInt(typeOldTemp2)) {
                                    spineNumber++;
                                    tracedataMontageNeuronType.get(i)[j] = typeOldTemp1[0].concat(":Spine#").concat(spineNumber.toString());

                                    String[] tracedataMontageNeuronBranchTemp2 = new String[3];
                                    tracedataMontageNeuronBranchTemp2[0] = "Spine";
                                    tracedataMontageNeuronBranchTemp2[1] = Integer.toString(spineNumber);
                                    tracedataMontageNeuronBranchTemp2[2] = null;

                                    tracedataMontageNeuronBranch.add(tracedataMontageNeuronBranchTemp2);
                                    tracedataMontageNeuronCoordinate.add(tracedataNeuronCoordinate2.get(tracedataMontageSpineMap12.get(k)[1]));
                                    tracedataMontageNeuronType.add(tracedataNeuronType2.get(tracedataMontageSpineMap12.get(k)[1]));
                                    tracedataMontageNeuronRadius.add(tracedataNeuronRadius2.get(tracedataMontageSpineMap12.get(k)[1]));
                                    tracedataMontageNeuronSynapse.add(tracedataNeuronSynapse2.get(tracedataMontageSpineMap12.get(k)[1]));
                                    tracedataMontageNeuronConnection.add(tracedataNeuronConnection2.get(tracedataMontageSpineMap12.get(k)[1]));

                                    Integer[] tracedataMontageNeuronMapTemp12 = new Integer[3];
                                    tracedataMontageNeuronMapTemp12[0] = 2;
                                    tracedataMontageNeuronMapTemp12[1] = null;
                                    tracedataMontageNeuronMapTemp12[2] = tracedataMontageNeuronMap2.size();
                                    tracedataMontageNeuronMap12.add(tracedataMontageNeuronMapTemp12);

                                    String[] tracedataMontageNeuronMapTemp2 = new String[2];
                                    tracedataMontageNeuronMapTemp2[0] = tracedataNeuronBranch2.get(tracedataMontageSpineMap12.get(k)[1])[1];
                                    tracedataMontageNeuronMapTemp2[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1];
                                    tracedataMontageNeuronMap2.add(tracedataMontageNeuronMapTemp2);

                                    break;
                                }
                            }
                        }
                    }
                }
            }

            System.out.println("Size of tracedataMontageNeuronMap12: " + tracedataMontageNeuronMap12.size());
            System.out.println("Size of tracedataMontageNeuronMap1: " + tracedataMontageNeuronMap1.size());
            System.out.println("Size of tracedataMontageNeuronMap2: " + tracedataMontageNeuronMap2.size());

            ArrayList<String[]> tracedataMontageNeuronConnectionModified = new ArrayList<String[]>();
            tracedataMontageNeuronConnectionModified.addAll(tracedataMontageNeuronConnection);
            
            for (int i = 0; i < tracedataMontageNeuronMap12.size(); i++) {

                if (tracedataMontageNeuronMap12.get(i)[0] == 1) {

                    String connectionOld = tracedataMontageNeuronMap1.get(tracedataMontageNeuronMap12.get(i)[1])[0];
                    String connectionNew = tracedataMontageNeuronMap1.get(tracedataMontageNeuronMap12.get(i)[1])[1];

                    for (int j = 0; j < tracedataMontageNeuronConnection.size(); j++) {
                        if (tracedataMontageNeuronMap12.get(j)[0] == 1) {
                            for (int k = 0; k < tracedataMontageNeuronConnection.get(j).length; k++) {
                                if (! tracedataMontageNeuronConnection.get(j)[k].equals("0")) {

                                    String[] connectionOldTemp  = tracedataMontageNeuronConnection.get(j)[k].split("#");

                                    if (connectionOldTemp[1].equals(connectionOld)) {
                                        tracedataMontageNeuronConnectionModified.get(j)[k] = connectionOldTemp[0].concat("#").concat(connectionNew).concat("#").concat(connectionOldTemp[2]);
                                    }
                                }
                            }
                        }
                    }

                } else if (tracedataMontageNeuronMap12.get(i)[0] == 2) {

                    String connectionOld = tracedataMontageNeuronMap2.get(tracedataMontageNeuronMap12.get(i)[2])[0];
                    String connectionNew = tracedataMontageNeuronMap2.get(tracedataMontageNeuronMap12.get(i)[2])[1];

                    for (int j = 0; j < tracedataMontageNeuronConnection.size(); j++) {
                        if (tracedataMontageNeuronMap12.get(j)[0] == 2) {
                            for (int k = 0; k < tracedataMontageNeuronConnection.get(j).length; k++) {
                                if (! tracedataMontageNeuronConnection.get(j)[k].equals("0")) {

                                    String[] connectionOldTemp  = tracedataMontageNeuronConnection.get(j)[k].split("#");

                                    if (connectionOldTemp[1].equals(connectionOld)) {
                                        tracedataMontageNeuronConnectionModified.get(j)[k] = connectionOldTemp[0].concat("#").concat(connectionNew).concat("#").concat(connectionOldTemp[2]);
                                    }
                                }
                            }
                        }
                    }

                } else if (tracedataMontageNeuronMap12.get(i)[0] == 12) {

                    String connectionOld1 = tracedataMontageNeuronMap1.get(tracedataMontageNeuronMap12.get(i)[1])[0];
                    String connectionOld2 = tracedataMontageNeuronMap2.get(tracedataMontageNeuronMap12.get(i)[2])[0];

                    String connectionNew = tracedataMontageNeuronMap1.get(tracedataMontageNeuronMap12.get(i)[1])[1];

                    for (int j = 0; j < tracedataMontageNeuronConnection.size(); j++) {
                        if (tracedataMontageNeuronMap12.get(j)[0] == 12) {
                            for (int k = 0; k < tracedataMontageNeuronConnection.get(j).length; k++) {
                                if (! tracedataMontageNeuronConnection.get(j)[k].equals("0")) {

                                    String[] connectionOldTemp  = tracedataMontageNeuronConnection.get(j)[k].split("#");

                                    if ((connectionOldTemp[1].equals(connectionOld1) && connectionOldTemp[3].equals("data1")) ||
                                            (connectionOldTemp[1].equals(connectionOld2) && connectionOldTemp[3].equals("data2"))) {
                                        tracedataMontageNeuronConnectionModified.get(j)[k] = connectionOldTemp[0].concat("#").concat(connectionNew).concat("#").concat(connectionOldTemp[2]);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            tracedataMontageNeuronConnection.clear();
            tracedataMontageNeuronConnection.addAll(tracedataMontageNeuronConnectionModified);
            
            for (int i = 0; i < tracedataMontageNeuronConnection.size(); i++) {

                String connectedBranchPrimary;

                if (tracedataMontageNeuronBranch.get(i)[0].equals("Soma")) {
                    connectedBranchPrimary = tracedataMontageNeuronBranch.get(i)[1].concat(":").concat(tracedataMontageNeuronBranch.get(i)[2]);
                } else if (tracedataMontageNeuronBranch.get(i)[0].equals("Neurite")) {
                    connectedBranchPrimary = tracedataMontageNeuronBranch.get(i)[1].concat(tracedataMontageNeuronBranch.get(i)[2]);
                } else {
                    connectedBranchPrimary = tracedataMontageNeuronBranch.get(i)[1];
                }
                
                Integer connectionCounter1 = 0;

                for (int j = 0; j < tracedataMontageNeuronConnection.get(i).length; j++) {

                    if (! tracedataMontageNeuronConnection.get(i)[j].equals("0")) {

                        connectionCounter1 = connectionCounter1 + 1;
                        String connectedBranchSecondary = tracedataMontageNeuronConnection.get(i)[j].split("#")[1];

                        for (int k = 0; k < tracedataMontageNeuronConnection.size(); k++) {

                            String connectedBranchTemp1;

                            if (tracedataMontageNeuronBranch.get(k)[0].equals("Soma")) {
                                connectedBranchTemp1 = tracedataMontageNeuronBranch.get(k)[1].concat(":").concat(tracedataMontageNeuronBranch.get(k)[2]);
                            } else if (tracedataMontageNeuronBranch.get(k)[0].equals("Neurite")) {
                                connectedBranchTemp1 = tracedataMontageNeuronBranch.get(k)[1].concat(tracedataMontageNeuronBranch.get(k)[2]);
                            } else {
                                connectedBranchTemp1 = tracedataMontageNeuronBranch.get(k)[1];
                            }

                            if (connectedBranchSecondary.equals(connectedBranchTemp1)) {

                                Integer connectionCounter2 = 0;

                                for (int l = 0; l < tracedataMontageNeuronConnection.get(k).length; l++) {

                                    if (! tracedataMontageNeuronConnection.get(k)[l].equals("0")) {

                                        connectionCounter2 = connectionCounter2 + 1;

                                        String connectedBranchTemp2 = tracedataMontageNeuronConnection.get(k)[l].split("#")[1];

                                        if (connectedBranchPrimary.equals(connectedBranchTemp2)) {
                                            tracedataMontageNeuronConnection.get(i)[j] = (connectionCounter1.toString()).concat("#").concat(connectedBranchSecondary).concat("#").concat(connectionCounter2.toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            matchedOverlappedTraceAnalysisModified.addAll(matchedOverlappedTraceAnalysis);

            for (int i = 0; i < matchedOverlappedTraceBranch.size(); i++) {
                String oldBranch1 = tracedataNeuronBranch1.get(Integer.parseInt(matchedOverlappedTraceBranch.get(i)[0]))[1].concat(tracedataNeuronBranch1.get(Integer.parseInt(matchedOverlappedTraceBranch.get(i)[0]))[2]);

                for (int j = 0; j < tracedataMontageNeuronMap1.size(); j++) {
                    if (oldBranch1.equals(tracedataMontageNeuronMap1.get(j)[0])) {
                        matchedOverlappedTraceAnalysisModified.get(i)[8] = tracedataMontageNeuronMap1.get(j)[1];
                        break;
                    }
                }
            }

            System.out.println("tracedataMontageNeuronMap12: ");
            for (int i = 0; i < tracedataMontageNeuronMap12.size(); i++) {
               System.out.println(tracedataMontageNeuronMap12.get(i)[0] + " " + tracedataMontageNeuronMap12.get(i)[1] + " " + tracedataMontageNeuronMap12.get(i)[2]);
            }

            System.out.println("tracedataMontageNeuronMap1: ");
            for (int i = 0; i < tracedataMontageNeuronMap1.size(); i++) {
               System.out.println(tracedataMontageNeuronMap1.get(i)[0] + " " + tracedataMontageNeuronMap1.get(i)[1]);
            }

            System.out.println("tracedataMontageNeuronMap2: ");
            for (int i = 0; i < tracedataMontageNeuronMap2.size(); i++) {
               System.out.println(tracedataMontageNeuronMap2.get(i)[0] + " " + tracedataMontageNeuronMap2.get(i)[1]);
            }

            System.out.println("Size of tracedataMontageNeuronTag: " + tracedataMontageNeuronTag.size());
            System.out.println("Size of tracedataMontageNeuronBranch: " + tracedataMontageNeuronBranch.size());
            System.out.println("Size of tracedataMontageNeuronCoordinate: " + tracedataMontageNeuronCoordinate.size());
            System.out.println("Size of tracedataMontageNeuronType: " + tracedataMontageNeuronType.size());
            System.out.println("Size of tracedataMontageNeuronRadius: " + tracedataMontageNeuronRadius.size());
            System.out.println("Size of tracedataMontageNeuronSynapse: " + tracedataMontageNeuronSynapse.size());
            System.out.println("Size of tracedataMontageNeuronConnection: " + tracedataMontageNeuronConnection.size());
            
        } else if (montageMethodFlag == 3) {

            Integer neuronNumber = 0;
            Integer neuronNumberTag = 0;

            ArrayList<Integer[]> tracedataMontageNeuronMap11 = new ArrayList<Integer[]>();
            ArrayList<String[]> tracedataMontageNeuronMap1 = new ArrayList<String[]>();
            
            ArrayList<Integer[]> tracedataMontageSpineMap11 = new ArrayList<Integer[]>();

            ArrayList<Integer> tracedataMontageIndex1 = new ArrayList<Integer>();

            for (int i = 0; i < tracedataNeuronBranch1.size(); i++) {

                boolean flag1 = false;

                for (int j = 0; j < matchedOverlappedTraceBranch.size(); j++) {
                    if (! tracedataNeuronBranch1.get(i)[0].equals("Spine") && (tracedataNeuronBranch1.get(i)[1].equals(matchedOverlappedTraceBranch.get(j)[1]) || tracedataNeuronBranch1.get(i)[1].equals(matchedOverlappedTraceBranch.get(j)[4]))) {
                        flag1 = true;
                        break;
                    }
                }

                if (flag1 == true) {
                    tracedataMontageIndex1.add(i);
                } else {

                    if (! tracedataNeuronBranch1.get(i)[0].equals("Spine")) {

                        if (Integer.parseInt(tracedataNeuronBranch1.get(i)[1]) != neuronNumberTag) {
                            neuronNumber++;
                            neuronNumberTag = Integer.parseInt(tracedataNeuronBranch1.get(i)[1]);

                            String[] tracedataMontageNeuronTagTemp1 = new String[2];
                            tracedataMontageNeuronTagTemp1[0] = neuronNumber.toString();

                            for (int j = 0; j < tracedataNeuronTag1.size(); j++) {
                                if (tracedataNeuronTag1.get(j)[0].equals(tracedataNeuronBranch1.get(i)[1])) {
                                    if (! tracedataNeuronTag1.get(j)[1].isEmpty()) {
                                        tracedataMontageNeuronTagTemp1[1] = tracedataNeuronTag1.get(j)[1].concat(";original");
                                        break;
                                    } else {
                                        tracedataMontageNeuronTagTemp1[1] = "original";
                                        break;
                                    }
                                }
                            }

                            tracedataMontageNeuronTag.add(tracedataMontageNeuronTagTemp1);
                        }

                        String[] tracedataMontageNeuronBranchTemp1 = new String[3];
                        tracedataMontageNeuronBranchTemp1[0] = tracedataNeuronBranch1.get(i)[0];
                        tracedataMontageNeuronBranchTemp1[1] = Integer.toString(neuronNumber);
                        tracedataMontageNeuronBranchTemp1[2] = tracedataNeuronBranch1.get(i)[2];

                        tracedataMontageNeuronBranch.add(tracedataMontageNeuronBranchTemp1);
                        tracedataMontageNeuronCoordinate.add(tracedataNeuronCoordinateModified1.get(i));
                        tracedataMontageNeuronType.add(tracedataNeuronTypeModified1.get(i));
                        tracedataMontageNeuronRadius.add(tracedataNeuronRadiusModified1.get(i));
                        tracedataMontageNeuronSynapse.add(tracedataNeuronSynapseModified1.get(i));
                        tracedataMontageNeuronConnection.add(tracedataNeuronConnectionModified1.get(i));

                        Integer[] tracedataMontageNeuronMapTemp11 = new Integer[3];
                        tracedataMontageNeuronMapTemp11[0] = 1;
                        tracedataMontageNeuronMapTemp11[1] = tracedataMontageNeuronMap1.size();
                        tracedataMontageNeuronMapTemp11[2] = null;
                        tracedataMontageNeuronMap11.add(tracedataMontageNeuronMapTemp11);

                        String[] tracedataMontageNeuronMapTemp1 = new String[2];

                        if (tracedataNeuronBranch1.get(i)[0].equals("Soma")) {
                            tracedataMontageNeuronMapTemp1[0] = tracedataNeuronBranch1.get(i)[1].concat(":").concat(tracedataNeuronBranch1.get(i)[2]);
                            tracedataMontageNeuronMapTemp1[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(":").concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);
                        } else if (tracedataNeuronBranch1.get(i)[0].equals("Neurite")) {
                            tracedataMontageNeuronMapTemp1[0] = tracedataNeuronBranch1.get(i)[1].concat(tracedataNeuronBranch1.get(i)[2]);
                            tracedataMontageNeuronMapTemp1[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);
                        }

                        tracedataMontageNeuronMap1.add(tracedataMontageNeuronMapTemp1);
                        
                    } else {

                        Integer[] tracedataMontageSpineMapTemp11 = new Integer[3];
                        tracedataMontageSpineMapTemp11[0] = 1;
                        tracedataMontageSpineMapTemp11[1] = i;
                        tracedataMontageSpineMapTemp11[2] = Integer.parseInt(tracedataNeuronBranch1.get(i)[1]);
                        tracedataMontageSpineMap11.add(tracedataMontageSpineMapTemp11);
                    }
                }
            }

            System.out.println("Size of tracedataMontageNeuronMap11: " + tracedataMontageNeuronMap11.size());
            System.out.println("Size of tracedataMontageNeuronMap1: " + tracedataMontageNeuronMap1.size());

            System.out.println("Size of tracedataMontageSpineMap11: " + tracedataMontageSpineMap11.size());

            System.out.println("Size of tracedataMontageIndex1: " + tracedataMontageIndex1.size());

            Set<Integer> matchedOverlappedTraceIndex = new HashSet<Integer>();

            for (int i = 0; i < matchedOverlappedTraceBranch.size(); i++) {

                if (! matchedOverlappedTraceIndex.contains(i)) {

                    neuronNumber++;

                    Set<Integer> matchedOverlappedTraceIndexTemp = new HashSet<Integer>();

                    ArrayList<String[]> tracedataMontageSomaMap = new ArrayList<String[]>();
                    ArrayList<String[]> tracedataMontageNeuriteMap = new ArrayList<String[]>();

                    ArrayList<Integer[][]> tracedataMontageSomaCoordinate = new ArrayList<Integer[][]>();
                    ArrayList<String[]> tracedataMontageSomaType = new ArrayList<String[]>();
                    ArrayList<Integer[]> tracedataMontageSomaRadius = new ArrayList<Integer[]>();
                    ArrayList<Integer[]> tracedataMontageSomaSynapse = new ArrayList<Integer[]>();
                    ArrayList<String[]> tracedataMontageSomaConnection = new ArrayList<String[]>();

                    ArrayList<Integer[][]> tracedataMontageNeuriteCoordinate = new ArrayList<Integer[][]>();
                    ArrayList<String[]> tracedataMontageNeuriteType = new ArrayList<String[]>();
                    ArrayList<Integer[]> tracedataMontageNeuriteRadius = new ArrayList<Integer[]>();
                    ArrayList<Integer[]> tracedataMontageNeuriteSynapse = new ArrayList<Integer[]>();
                    ArrayList<String[]> tracedataMontageNeuriteConnection = new ArrayList<String[]>();

                    ArrayList<Integer[]> tracedataMergingNeuronIndex1 = new ArrayList<Integer[]>();
                    ArrayList<Integer[][]> tracedataMergingNeuronIndex11 = new ArrayList<Integer[][]>();

                    Set<Integer> matchedOverlappedTraceColumn14 = new HashSet<Integer>();
                    Set<String> matchedAllOverlappedTraceColumn25 = new HashSet<String>();

                    ArrayList<Integer[]> matchedOverlappedTraceColumn0 = new ArrayList<Integer[]>();
                    ArrayList<Integer[]> matchedOverlappedTraceColumn3 = new ArrayList<Integer[]>();

                    ArrayList<String[]> matchedOverlappedTraceColumn25 = new ArrayList<String[]>();

                    matchedOverlappedTraceColumn14.add(Integer.parseInt(matchedOverlappedTraceBranch.get(i)[1]));
                    matchedOverlappedTraceColumn14.add(Integer.parseInt(matchedOverlappedTraceBranch.get(i)[4]));

                    Integer matchedOverlappedTraceNeuronNumber = matchedOverlappedTraceColumn14.size();
                    boolean flagLoop1 = true;

                    while ((matchedOverlappedTraceColumn14.size() > matchedOverlappedTraceNeuronNumber) || (flagLoop1 == true)) {

                        matchedOverlappedTraceNeuronNumber = matchedOverlappedTraceColumn14.size();
                        flagLoop1 = false;

                        for (int j = 0; j < matchedOverlappedTraceBranch.size(); j++) {

                            if (matchedOverlappedTraceColumn14.contains(Integer.parseInt(matchedOverlappedTraceBranch.get(j)[1]))) {
                                matchedOverlappedTraceIndexTemp.add(j);
                                matchedOverlappedTraceColumn14.add(Integer.parseInt(matchedOverlappedTraceBranch.get(j)[4]));
                            }

                            if (matchedOverlappedTraceColumn14.contains(Integer.parseInt(matchedOverlappedTraceBranch.get(j)[4]))) {
                                matchedOverlappedTraceIndexTemp.add(j);
                                matchedOverlappedTraceColumn14.add(Integer.parseInt(matchedOverlappedTraceBranch.get(j)[1]));
                            }
                        }
                    }

                    for (Integer j : matchedOverlappedTraceIndexTemp) {
                        matchedOverlappedTraceIndex.add(j);
                    }

                    for (Integer j : matchedOverlappedTraceIndexTemp) {

                        if (! matchedAllOverlappedTraceColumn25.contains(matchedOverlappedTraceBranch.get(j)[2]) && ! matchedAllOverlappedTraceColumn25.contains(matchedOverlappedTraceBranch.get(j)[5])) {

                            Set<Integer> matchedOverlappedTraceColumnTemp0 = new HashSet<Integer>();
                            Set<Integer> matchedOverlappedTraceColumnTemp3 = new HashSet<Integer>();
                            Set<String> matchedOverlappedTraceColumnTemp25 = new HashSet<String>();

                            matchedOverlappedTraceColumnTemp25.add(matchedOverlappedTraceBranch.get(j)[2]);
                            matchedOverlappedTraceColumnTemp25.add(matchedOverlappedTraceBranch.get(j)[5]);

                            Integer matchedOverlappedTraceBranchNumber = matchedOverlappedTraceColumnTemp25.size();
                            boolean flagLoop2 = true;

                            while ((matchedOverlappedTraceColumnTemp25.size() > matchedOverlappedTraceBranchNumber) || (flagLoop2 == true)) {

                                matchedOverlappedTraceBranchNumber = matchedOverlappedTraceColumnTemp25.size();
                                flagLoop2 = false;

                                for (Integer k : matchedOverlappedTraceIndexTemp) {

                                    if (matchedOverlappedTraceColumnTemp25.contains(matchedOverlappedTraceBranch.get(k)[2])) {

                                        matchedOverlappedTraceColumnTemp0.add(Integer.parseInt(matchedOverlappedTraceBranch.get(k)[0]));
                                        matchedOverlappedTraceColumnTemp3.add(Integer.parseInt(matchedOverlappedTraceBranch.get(k)[3]));

                                        matchedOverlappedTraceColumnTemp25.add(matchedOverlappedTraceBranch.get(k)[5]);

                                        matchedAllOverlappedTraceColumn25.add(matchedOverlappedTraceBranch.get(k)[2]);
                                        matchedAllOverlappedTraceColumn25.add(matchedOverlappedTraceBranch.get(k)[5]);
                                    }

                                    if (matchedOverlappedTraceColumnTemp25.contains(matchedOverlappedTraceBranch.get(k)[5])) {

                                        matchedOverlappedTraceColumnTemp0.add(Integer.parseInt(matchedOverlappedTraceBranch.get(k)[0]));
                                        matchedOverlappedTraceColumnTemp3.add(Integer.parseInt(matchedOverlappedTraceBranch.get(k)[3]));

                                        matchedOverlappedTraceColumnTemp25.add(matchedOverlappedTraceBranch.get(k)[2]);

                                        matchedAllOverlappedTraceColumn25.add(matchedOverlappedTraceBranch.get(k)[2]);
                                        matchedAllOverlappedTraceColumn25.add(matchedOverlappedTraceBranch.get(k)[5]);
                                    }
                                }
                            }

                            Integer[] matchedOverlappedTraceColumnTempTemp0 = matchedOverlappedTraceColumnTemp0.toArray(new Integer[matchedOverlappedTraceColumnTemp0.size()]);
                            Integer[] matchedOverlappedTraceColumnTempTemp3 = matchedOverlappedTraceColumnTemp3.toArray(new Integer[matchedOverlappedTraceColumnTemp3.size()]);
                            String[] matchedOverlappedTraceColumnTempTemp25 = matchedOverlappedTraceColumnTemp25.toArray(new String[matchedOverlappedTraceColumnTemp25.size()]);

                            matchedOverlappedTraceColumn0.add(matchedOverlappedTraceColumnTempTemp0);
                            matchedOverlappedTraceColumn3.add(matchedOverlappedTraceColumnTempTemp3);
                            matchedOverlappedTraceColumn25.add(matchedOverlappedTraceColumnTempTemp25);

                            ArrayList<Integer> tracedataMergingNeuronIndexTemp1 = new ArrayList<Integer>();
                            ArrayList<Integer[]> tracedataMergingNeuronIndexTemp11 = new ArrayList<Integer[]>();

                            for (int k = 0; k < tracedataMontageIndex1.size(); k++) {
                                if (tracedataNeuronBranch1.get(tracedataMontageIndex1.get(k))[0].equals("Neurite") && ! matchedOverlappedTraceColumnTemp3.contains(tracedataMontageIndex1.get(k))) {
                                    if (matchedOverlappedTraceColumnTemp25.contains(tracedataNeuronBranch1.get(tracedataMontageIndex1.get(k))[1].concat("-").concat(tracedataNeuronBranch1.get(tracedataMontageIndex1.get(k))[2].split("-")[1]))) {
                                        tracedataMergingNeuronIndexTemp1.add(tracedataMontageIndex1.get(k));
                                    }
                                }
                            }

                            for (Integer k : matchedOverlappedTraceIndexTemp) {
                                if (matchedOverlappedTraceColumnTemp25.contains(matchedOverlappedTraceBranch.get(k)[2]) && matchedOverlappedTraceColumnTemp25.contains(matchedOverlappedTraceBranch.get(k)[5])) {
                                    Integer[] tracedataMergingNeuronIndexTempTemp11 = new Integer[2];
                                    tracedataMergingNeuronIndexTempTemp11[0] = Integer.parseInt(matchedOverlappedTraceBranch.get(k)[0]);
                                    tracedataMergingNeuronIndexTempTemp11[1] = Integer.parseInt(matchedOverlappedTraceBranch.get(k)[3]);
                                    tracedataMergingNeuronIndexTemp11.add(tracedataMergingNeuronIndexTempTemp11);
                                }
                            }

                            Integer[] tracedataMergingNeuronIndexTempTemp1 = tracedataMergingNeuronIndexTemp1.toArray(new Integer[tracedataMergingNeuronIndexTemp1.size()]);
                            Integer[][] tracedataMergingNeuronIndexTempTemp11 = tracedataMergingNeuronIndexTemp11.toArray(new Integer[tracedataMergingNeuronIndexTemp11.size()][2]);

                            tracedataMergingNeuronIndex1.add(tracedataMergingNeuronIndexTempTemp1);
                            tracedataMergingNeuronIndex11.add(tracedataMergingNeuronIndexTempTemp11);
                        }
                    }

                    System.out.println("Size of tracedataMergingNeuronIndex11: " + tracedataMergingNeuronIndex11.size());
                    System.out.println("Size of tracedataMergingNeuronIndex1: " + tracedataMergingNeuronIndex1.size());

                    Set<Integer> tracedataMontageSomaZ = new HashSet<Integer>();

                    for (int j = 0; j < tracedataMontageIndex1.size(); j++) {
                        if (matchedOverlappedTraceColumn14.contains(Integer.parseInt(tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[1]))) {
                            if (tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[0].equals("Soma") && ! tracedataMontageSomaZ.contains(Integer.parseInt(tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[2]))) {

                                tracedataMontageSomaZ.add(Integer.parseInt(tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[2]));

                                String[] tracedataMontageSomaMapTemp1 = new String[2];
                                tracedataMontageSomaMapTemp1[0] = tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[1];
                                tracedataMontageSomaMapTemp1[1] = tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[2];
                                tracedataMontageSomaMap.add(tracedataMontageSomaMapTemp1);

                                tracedataMontageSomaCoordinate.add(tracedataNeuronCoordinateModified1.get(tracedataMontageIndex1.get(j)));
                                tracedataMontageSomaType.add(tracedataNeuronTypeModified1.get(tracedataMontageIndex1.get(j)));
                                tracedataMontageSomaRadius.add(tracedataNeuronRadiusModified1.get(tracedataMontageIndex1.get(j)));
                                tracedataMontageSomaSynapse.add(tracedataNeuronSynapseModified1.get(tracedataMontageIndex1.get(j)));
                                tracedataMontageSomaConnection.add(tracedataNeuronConnectionModified1.get(tracedataMontageIndex1.get(j)));

                            } else if (tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[0].equals("Neurite") && ! matchedAllOverlappedTraceColumn25.contains(tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[1].concat("-").concat(tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[2].split("-")[1]))) {

                                String[] tracedataMontageNeuriteMapTemp1 = new String[2];
                                tracedataMontageNeuriteMapTemp1[0] = tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[1];
                                tracedataMontageNeuriteMapTemp1[1] = tracedataNeuronBranch1.get(tracedataMontageIndex1.get(j))[2];
                                tracedataMontageNeuriteMap.add(tracedataMontageNeuriteMapTemp1);

                                tracedataMontageNeuriteCoordinate.add(tracedataNeuronCoordinateModified1.get(tracedataMontageIndex1.get(j)));
                                tracedataMontageNeuriteType.add(tracedataNeuronTypeModified1.get(tracedataMontageIndex1.get(j)));
                                tracedataMontageNeuriteRadius.add(tracedataNeuronRadiusModified1.get(tracedataMontageIndex1.get(j)));
                                tracedataMontageNeuriteSynapse.add(tracedataNeuronSynapseModified1.get(tracedataMontageIndex1.get(j)));
                                tracedataMontageNeuriteConnection.add(tracedataNeuronConnectionModified1.get(tracedataMontageIndex1.get(j)));
                            }
                        }
                    }

                    String[] tracedataMontageNeuronTagTemp = new String[2];
                    tracedataMontageNeuronTagTemp[0] = neuronNumber.toString();

                    for (int j = 0; j < tracedataNeuronTag1.size(); j++) {
                        if (matchedOverlappedTraceColumn14.contains(Integer.parseInt(tracedataNeuronTag1.get(j)[0]))) {
                            if (! tracedataNeuronTag1.get(j)[1].isEmpty()) {
                                tracedataMontageNeuronTagTemp[1] = tracedataNeuronTag1.get(j)[1].concat(";modified");
                                break;
                            } else {
                                tracedataMontageNeuronTagTemp[1] = "modified";
                                break;
                            }
                        }
                    }

                    tracedataMontageNeuronTag.add(tracedataMontageNeuronTagTemp);

                    for (int j = 0; j < tracedataMontageSomaMap.size(); j++) {

                        String[] tracedataMontageSomaBranchTemp = new String[3];
                        tracedataMontageSomaBranchTemp[0] = "Soma";
                        tracedataMontageSomaBranchTemp[1] = Integer.toString(neuronNumber);
                        tracedataMontageSomaBranchTemp[2] = tracedataMontageSomaMap.get(j)[1];

                        tracedataMontageNeuronBranch.add(tracedataMontageSomaBranchTemp);
                        tracedataMontageNeuronCoordinate.add(tracedataMontageSomaCoordinate.get(j));
                        tracedataMontageNeuronType.add(tracedataMontageSomaType.get(j));
                        tracedataMontageNeuronRadius.add(tracedataMontageSomaRadius.get(j));
                        tracedataMontageNeuronSynapse.add(tracedataMontageSomaSynapse.get(j));
                        tracedataMontageNeuronConnection.add(tracedataMontageSomaConnection.get(j));

                        String[] tracedataMontageNeuronMapTemp1 = new String[2];
                        tracedataMontageNeuronMapTemp1[0] = tracedataMontageSomaMap.get(j)[0].concat(":").concat(tracedataMontageSomaMap.get(j)[1]);
                        tracedataMontageNeuronMapTemp1[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(":").concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);

                        Integer[] tracedataMontageNeuronMapTemp11 = new Integer[3];
                        tracedataMontageNeuronMapTemp11[0] = 1;
                        tracedataMontageNeuronMapTemp11[1] = tracedataMontageNeuronMap1.size();
                        tracedataMontageNeuronMapTemp11[2] = null;
                        tracedataMontageNeuronMap11.add(tracedataMontageNeuronMapTemp11);

                        tracedataMontageNeuronMap1.add(tracedataMontageNeuronMapTemp1);
                    }

                    String[] neuriteBranchOld = {"0", "0"};
                    Integer neuriteBranchNew = 0;

                    for (int j = 0; j < tracedataMontageNeuriteMap.size(); j++) {

                        String[] tracedataMontageNeuriteBranchTemp = new String[3];
                        tracedataMontageNeuriteBranchTemp[0] = "Neurite";
                        tracedataMontageNeuriteBranchTemp[1] = Integer.toString(neuronNumber);

                        if (! neuriteBranchOld[0].equals(tracedataMontageNeuriteMap.get(j)[0]) || ! neuriteBranchOld[1].equals(tracedataMontageNeuriteMap.get(j)[1].split("-")[1])) {
                            neuriteBranchNew++;

                            neuriteBranchOld[0] = tracedataMontageNeuriteMap.get(j)[0];
                            neuriteBranchOld[1] = tracedataMontageNeuriteMap.get(j)[1].split("-")[1];
                        }

                        if (tracedataMontageNeuriteMap.get(j)[1].split("-").length > 2) {
                            tracedataMontageNeuriteBranchTemp[2] = "-".concat(neuriteBranchNew.toString()).concat("-").concat(tracedataMontageNeuriteMap.get(j)[1].split("-", 3)[2]);
                        } else {
                            tracedataMontageNeuriteBranchTemp[2] = "-".concat(neuriteBranchNew.toString());
                        }

                        tracedataMontageNeuronBranch.add(tracedataMontageNeuriteBranchTemp);
                        tracedataMontageNeuronCoordinate.add(tracedataMontageNeuriteCoordinate.get(j));
                        tracedataMontageNeuronType.add(tracedataMontageNeuriteType.get(j));
                        tracedataMontageNeuronRadius.add(tracedataMontageNeuriteRadius.get(j));
                        tracedataMontageNeuronSynapse.add(tracedataMontageNeuriteSynapse.get(j));
                        tracedataMontageNeuronConnection.add(tracedataMontageNeuriteConnection.get(j));

                        String[] tracedataMontageNeuronMapTemp1 = new String[2];
                        tracedataMontageNeuronMapTemp1[0] = tracedataMontageNeuriteMap.get(j)[0].concat(tracedataMontageNeuriteMap.get(j)[1]);
                        tracedataMontageNeuronMapTemp1[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);

                        Integer[] tracedataMontageNeuronMapTemp11 = new Integer[3];
                        tracedataMontageNeuronMapTemp11[0] = 1;
                        tracedataMontageNeuronMapTemp11[1] = tracedataMontageNeuronMap1.size();
                        tracedataMontageNeuronMapTemp11[2] = null;
                        tracedataMontageNeuronMap11.add(tracedataMontageNeuronMapTemp11);

                        tracedataMontageNeuronMap1.add(tracedataMontageNeuronMapTemp1);
                    }

                    System.out.println("Size of tracedataMontageNeuronMap11: " + tracedataMontageNeuronMap11.size());
                    System.out.println("Size of tracedataMontageNeuronMap1: " + tracedataMontageNeuronMap1.size());
                    
                    for (int j = 0; j < tracedataMergingNeuronIndex1.size(); j++) {

                        neuriteBranchNew++;

                        ArrayList<Integer[][]> tracedataMergingNeuronCoordinate = new ArrayList<Integer[][]>();
                        ArrayList<String[]> tracedataMergingNeuronType = new ArrayList<String[]>();
                        ArrayList<Integer[]> tracedataMergingNeuronRadius = new ArrayList<Integer[]>();
                        ArrayList<Integer[]> tracedataMergingNeuronSynapse = new ArrayList<Integer[]>();
                        ArrayList<String[]> tracedataMergingNeuronConnection = new ArrayList<String[]>();

                        for (int k = 0; k < tracedataMergingNeuronIndex1.get(j).length; k++) {

                            tracedataMergingNeuronCoordinate.add(tracedataNeuronCoordinateModified1.get(tracedataMergingNeuronIndex1.get(j)[k]));
                            tracedataMergingNeuronType.add(tracedataNeuronTypeModified1.get(tracedataMergingNeuronIndex1.get(j)[k]));
                            tracedataMergingNeuronRadius.add(tracedataNeuronRadiusModified1.get(tracedataMergingNeuronIndex1.get(j)[k]));
                            tracedataMergingNeuronSynapse.add(tracedataNeuronSynapseModified1.get(tracedataMergingNeuronIndex1.get(j)[k]));
                            tracedataMergingNeuronConnection.add(tracedataNeuronConnectionModified1.get(tracedataMergingNeuronIndex1.get(j)[k]));
                        }

                        ArrayList<Integer[]> tracedataMergingNeuronCoordinateBranch = new ArrayList<Integer[]>();
                        ArrayList<Integer[][]> tracedataMergingNeuronVertexBranch = new ArrayList<Integer[][]>();
                        Integer[] tracedataMergingNeuronCoordinateVertexIndex = {0,0};

                        for (int k = 0; k < tracedataMergingNeuronCoordinate.size(); k++) {

                            Set<Integer> tracedataMergingNeuronCoordinateBranchTemp = new HashSet<Integer>();
                            ArrayList<Integer[]> tracedataMergingNeuronVertexBranchTemp = new ArrayList<Integer[]>();

                            int tracedataMergingNeuronVertexBranchTempSize = tracedataMergingNeuronVertexBranchTemp.size();

                            tracedataMergingNeuronVertexBranchTemp.add(tracedataMergingNeuronCoordinate.get(k)[0]);
                            tracedataMergingNeuronVertexBranchTemp.add(tracedataMergingNeuronCoordinate.get(k)[tracedataMergingNeuronCoordinate.get(k).length - 1]);

                            while (tracedataMergingNeuronVertexBranchTemp.size() > tracedataMergingNeuronVertexBranchTempSize) {

                                tracedataMergingNeuronVertexBranchTempSize = tracedataMergingNeuronVertexBranchTemp.size();

                                Set<Integer> tracedataMergingNeuronCoordinateBranchTempTemp = new HashSet<Integer>();

                                for (int kk = 0; kk < tracedataMergingNeuronCoordinate.size(); kk++) {
                                    for (int l = 0; l < tracedataMergingNeuronVertexBranchTemp.size(); l++) {
                                        if (Arrays.equals(tracedataMergingNeuronCoordinate.get(kk)[0], tracedataMergingNeuronVertexBranchTemp.get(l)) || Arrays.equals(tracedataMergingNeuronCoordinate.get(kk)[tracedataMergingNeuronCoordinate.get(kk).length - 1], tracedataMergingNeuronVertexBranchTemp.get(l))) {
                                            tracedataMergingNeuronCoordinateBranchTempTemp.add(kk);
                                        }
                                    }
                                }

                                for (Integer kk : tracedataMergingNeuronCoordinateBranchTempTemp) {

                                    boolean flagStart = false;
                                    boolean flagEnd = false;

                                    for (int l = 0; l < tracedataMergingNeuronVertexBranchTemp.size(); l++) {
                                        if (Arrays.equals(tracedataMergingNeuronCoordinate.get(kk)[0], tracedataMergingNeuronVertexBranchTemp.get(l))) {
                                            flagStart = true;
                                        } else if (Arrays.equals(tracedataMergingNeuronCoordinate.get(kk)[tracedataMergingNeuronCoordinate.get(kk).length - 1], tracedataMergingNeuronVertexBranchTemp.get(l))) {
                                            flagEnd = true;
                                        }
                                    }

                                    if (flagStart == false) {
                                        tracedataMergingNeuronVertexBranchTemp.add(tracedataMergingNeuronCoordinate.get(kk)[0]);
                                    }

                                    if (flagEnd == false) {
                                        tracedataMergingNeuronVertexBranchTemp.add(tracedataMergingNeuronCoordinate.get(kk)[tracedataMergingNeuronCoordinate.get(kk).length - 1]);
                                    }
                                }

                                tracedataMergingNeuronCoordinateBranchTemp.addAll(tracedataMergingNeuronCoordinateBranchTempTemp);
                            }

                            Integer[] tracedataMergingNeuronCoordinateBranchTempTemp = tracedataMergingNeuronCoordinateBranchTemp.toArray(new Integer[tracedataMergingNeuronCoordinateBranchTemp.size()]);
                            Integer[][] tracedataMergingNeuronVertexBranchTempTemp = tracedataMergingNeuronVertexBranchTemp.toArray(new Integer[tracedataMergingNeuronVertexBranchTemp.size()][3]);

                            tracedataMergingNeuronCoordinateBranch.add(tracedataMergingNeuronCoordinateBranchTempTemp);
                            tracedataMergingNeuronVertexBranch.add(tracedataMergingNeuronVertexBranchTempTemp);

                            if ((Math.floor((tracedataMergingNeuronCoordinateBranchTemp.size() + 1) / 2) == (((double) (tracedataMergingNeuronCoordinateBranchTemp.size() + 1)) / 2)) &&
                                    (tracedataMergingNeuronVertexBranchTemp.size() == (tracedataMergingNeuronCoordinateBranchTemp.size() + 1)) &&
                                    (tracedataMergingNeuronVertexBranchTemp.size() > tracedataMergingNeuronCoordinateVertexIndex[1])) {

                                tracedataMergingNeuronCoordinateVertexIndex[0] = k;
                                tracedataMergingNeuronCoordinateVertexIndex[1] = tracedataMergingNeuronVertexBranchTemp.size();
                            }
                        }

                        ArrayList<Integer[][]> tracedataMergingNeuronCoordinateNew = new ArrayList<Integer[][]>();
                        ArrayList<String[]> tracedataMergingNeuronTypeNew = new ArrayList<String[]>();
                        ArrayList<Integer[]> tracedataMergingNeuronRadiusNew = new ArrayList<Integer[]>();
                        ArrayList<Integer[]> tracedataMergingNeuronSynapseNew = new ArrayList<Integer[]>();
                        ArrayList<String[]> tracedataMergingNeuronConnectionNew = new ArrayList<String[]>();

                        for (int k = 0; k < tracedataMergingNeuronCoordinateBranch.get(tracedataMergingNeuronCoordinateVertexIndex[0]).length; k++) {

                            tracedataMergingNeuronCoordinateNew.add(tracedataMergingNeuronCoordinate.get(tracedataMergingNeuronCoordinateBranch.get(tracedataMergingNeuronCoordinateVertexIndex[0])[k]));
                            tracedataMergingNeuronTypeNew.add(tracedataMergingNeuronType.get(tracedataMergingNeuronCoordinateBranch.get(tracedataMergingNeuronCoordinateVertexIndex[0])[k]));
                            tracedataMergingNeuronRadiusNew.add(tracedataMergingNeuronRadius.get(tracedataMergingNeuronCoordinateBranch.get(tracedataMergingNeuronCoordinateVertexIndex[0])[k]));
                            tracedataMergingNeuronSynapseNew.add(tracedataMergingNeuronSynapse.get(tracedataMergingNeuronCoordinateBranch.get(tracedataMergingNeuronCoordinateVertexIndex[0])[k]));
                            tracedataMergingNeuronConnectionNew.add(tracedataMergingNeuronConnection.get(tracedataMergingNeuronCoordinateBranch.get(tracedataMergingNeuronCoordinateVertexIndex[0])[k]));
                        }

                        tracedataMergingNeuronCoordinate.clear();
                        tracedataMergingNeuronType.clear();
                        tracedataMergingNeuronRadius.clear();
                        tracedataMergingNeuronSynapse.clear();
                        tracedataMergingNeuronConnection.clear();

                        tracedataMergingNeuronCoordinate.addAll(tracedataMergingNeuronCoordinateNew);
                        tracedataMergingNeuronType.addAll(tracedataMergingNeuronTypeNew);
                        tracedataMergingNeuronRadius.addAll(tracedataMergingNeuronRadiusNew);
                        tracedataMergingNeuronSynapse.addAll(tracedataMergingNeuronSynapseNew);
                        tracedataMergingNeuronConnection.addAll(tracedataMergingNeuronConnectionNew);

                        ArrayList<Integer[]> tracedataMergingNeuronVertex = new ArrayList<Integer[]>();
                        tracedataMergingNeuronVertex.addAll(Arrays.asList(tracedataMergingNeuronVertexBranch.get(tracedataMergingNeuronCoordinateVertexIndex[0])));

                        System.out.println("Size of tracedataMergingNeuronVertex: " + tracedataMergingNeuronVertex.size());
                        System.out.println("Size of tracedataMergingNeuronCoordinate: " + tracedataMergingNeuronCoordinate.size());

                        for (int k = 0; k < tracedataMergingNeuronVertex.size(); k++) {
                            System.out.println("tracedataMergingNeuronVertex: " + tracedataMergingNeuronVertex.get(k)[0] + " " + tracedataMergingNeuronVertex.get(k)[1] + " " + tracedataMergingNeuronVertex.get(k)[2]);
                        }

                        Integer rootVertex = 0;
                        Integer rootEdge = 0;

                        for (int k = 0; k < tracedataMergingNeuronVertex.size(); k++) {

                            ArrayList<Integer> tracedataMergingNeuronVertexEdge = new ArrayList<Integer>();

                            for (int l = 0; l < tracedataMergingNeuronCoordinate.size(); l++) {
                                if (Arrays.equals(tracedataMergingNeuronCoordinate.get(l)[0], tracedataMergingNeuronVertex.get(k))) {
                                    tracedataMergingNeuronVertexEdge.add(l);
                                } else if (Arrays.equals(tracedataMergingNeuronCoordinate.get(l)[tracedataMergingNeuronCoordinate.get(l).length - 1], tracedataMergingNeuronVertex.get(k))) {
                                    tracedataMergingNeuronVertexEdge.add(l);
                                }
                            }

                            if (tracedataMergingNeuronVertexEdge.size() == 1) {
                                rootVertex = k;
                                rootEdge = tracedataMergingNeuronVertexEdge.get(0);
                                break;
                            }
                        }

                        ArrayList<Integer> tracedataMergingNeuronIndexOrdered = new ArrayList<Integer>();
                        tracedataMergingNeuronIndexOrdered.add(rootEdge);

                        ArrayList<String> tracedataMergingNeuronBranchOrdered = new ArrayList<String>();
                        tracedataMergingNeuronBranchOrdered.add("-".concat((neuriteBranchNew).toString()));

                        ArrayList<Integer[][]> tracedataMergingNeuronCoordinateOrdered = new ArrayList<Integer[][]>();
                        ArrayList<String[]> tracedataMergingNeuronTypeOrdered = new ArrayList<String[]>();
                        ArrayList<Integer[]> tracedataMergingNeuronRadiusOrdered = new ArrayList<Integer[]>();
                        ArrayList<Integer[]> tracedataMergingNeuronSynapseOrdered = new ArrayList<Integer[]>();
                        ArrayList<String[]> tracedataMergingNeuronConnectionOrdered = new ArrayList<String[]>();

                        if (Arrays.equals(tracedataMergingNeuronCoordinate.get(rootEdge)[0], tracedataMergingNeuronVertex.get(rootVertex))) {

                            tracedataMergingNeuronCoordinateOrdered.add(tracedataMergingNeuronCoordinate.get(rootEdge));
                            tracedataMergingNeuronTypeOrdered.add(tracedataMergingNeuronType.get(rootEdge));
                            tracedataMergingNeuronRadiusOrdered.add(tracedataMergingNeuronRadius.get(rootEdge));
                            tracedataMergingNeuronSynapseOrdered.add(tracedataMergingNeuronSynapse.get(rootEdge));
                            tracedataMergingNeuronConnectionOrdered.add(tracedataMergingNeuronConnection.get(rootEdge));

                        } else {

                            Integer[][] tracedataMergingNeuronCoordinateOrderedTemp =new Integer[tracedataMergingNeuronCoordinate.get(rootEdge).length][3];
                            String[] tracedataMergingNeuronTypeOrderedTemp =new String[tracedataMergingNeuronType.get(rootEdge).length];
                            Integer[] tracedataMergingNeuronRadiusOrderedTemp =new Integer[tracedataMergingNeuronRadius.get(rootEdge).length];
                            Integer[] tracedataMergingNeuronSynapseOrderedTemp =new Integer[tracedataMergingNeuronSynapse.get(rootEdge).length];
                            String[] tracedataMergingNeuronConnectionOrderedTemp =new String[tracedataMergingNeuronConnection.get(rootEdge).length];

                            for (int k = 0; k < tracedataMergingNeuronCoordinate.get(rootEdge).length; k++) {
                                tracedataMergingNeuronCoordinateOrderedTemp[k] = tracedataMergingNeuronCoordinate.get(rootEdge)[tracedataMergingNeuronCoordinate.get(rootEdge).length - k - 1];
                                tracedataMergingNeuronTypeOrderedTemp[k] = tracedataMergingNeuronType.get(rootEdge)[tracedataMergingNeuronType.get(rootEdge).length - k - 1];
                                tracedataMergingNeuronRadiusOrderedTemp[k] = tracedataMergingNeuronRadius.get(rootEdge)[tracedataMergingNeuronRadius.get(rootEdge).length - k - 1];
                                tracedataMergingNeuronSynapseOrderedTemp[k] = tracedataMergingNeuronSynapse.get(rootEdge)[tracedataMergingNeuronSynapse.get(rootEdge).length - k - 1];
                                tracedataMergingNeuronConnectionOrderedTemp[k] = tracedataMergingNeuronConnection.get(rootEdge)[tracedataMergingNeuronConnection.get(rootEdge).length - k - 1];
                            }

                            tracedataMergingNeuronCoordinateOrdered.add(tracedataMergingNeuronCoordinateOrderedTemp);
                            tracedataMergingNeuronTypeOrdered.add(tracedataMergingNeuronTypeOrderedTemp);
                            tracedataMergingNeuronRadiusOrdered.add(tracedataMergingNeuronRadiusOrderedTemp);
                            tracedataMergingNeuronSynapseOrdered.add(tracedataMergingNeuronSynapseOrderedTemp);
                            tracedataMergingNeuronConnectionOrdered.add(tracedataMergingNeuronConnectionOrderedTemp);
                        }

                        Set<Integer> remainingEdgeSet = new HashSet<Integer>();
                        for (int k = 0; k < tracedataMergingNeuronCoordinate.size(); k++) {
                            remainingEdgeSet.add(k);
                        }
                        remainingEdgeSet.remove(rootEdge);

                        Integer lastItemIndex = 0;

                        while (remainingEdgeSet.size() > 0) {

                            System.out.println("Size of remainigEdgeSet: " + remainingEdgeSet.size());
                            boolean flagLoop = false;

                            for (Integer k : remainingEdgeSet) {

                                if (Arrays.equals(tracedataMergingNeuronCoordinateOrdered.get(lastItemIndex)[tracedataMergingNeuronCoordinateOrdered.get(lastItemIndex).length - 1], tracedataMergingNeuronCoordinate.get(k)[0])) {

                                    tracedataMergingNeuronIndexOrdered.add(k);
                                    remainingEdgeSet.remove(k);

                                    tracedataMergingNeuronBranchOrdered.add(tracedataMergingNeuronBranchOrdered.get(lastItemIndex).concat("-1"));
                                    tracedataMergingNeuronCoordinateOrdered.add(tracedataMergingNeuronCoordinate.get(k));
                                    tracedataMergingNeuronTypeOrdered.add(tracedataMergingNeuronType.get(k));
                                    tracedataMergingNeuronRadiusOrdered.add(tracedataMergingNeuronRadius.get(k));
                                    tracedataMergingNeuronSynapseOrdered.add(tracedataMergingNeuronSynapse.get(k));
                                    tracedataMergingNeuronConnectionOrdered.add(tracedataMergingNeuronConnection.get(k));

                                    lastItemIndex++;
                                    flagLoop = true;
                                    break;

                                } else if (Arrays.equals(tracedataMergingNeuronCoordinateOrdered.get(lastItemIndex)[tracedataMergingNeuronCoordinateOrdered.get(lastItemIndex).length - 1], tracedataMergingNeuronCoordinate.get(k)[tracedataMergingNeuronCoordinate.get(k).length - 1])) {

                                    Integer[][] tracedataMergingNeuronCoordinateOrderedTemp =new Integer[tracedataMergingNeuronCoordinate.get(k).length][3];
                                    String[] tracedataMergingNeuronTypeOrderedTemp =new String[tracedataMergingNeuronType.get(k).length];
                                    Integer[] tracedataMergingNeuronRadiusOrderedTemp =new Integer[tracedataMergingNeuronRadius.get(k).length];
                                    Integer[] tracedataMergingNeuronSynapseOrderedTemp =new Integer[tracedataMergingNeuronSynapse.get(k).length];
                                    String[] tracedataMergingNeuronConnectionOrderedTemp =new String[tracedataMergingNeuronConnection.get(k).length];

                                    for (int l = 0; l < tracedataMergingNeuronCoordinate.get(k).length; l++) {
                                        tracedataMergingNeuronCoordinateOrderedTemp[l] = tracedataMergingNeuronCoordinate.get(k)[tracedataMergingNeuronCoordinate.get(k).length - l - 1];
                                        tracedataMergingNeuronTypeOrderedTemp[l] = tracedataMergingNeuronType.get(k)[tracedataMergingNeuronType.get(k).length - l - 1];
                                        tracedataMergingNeuronRadiusOrderedTemp[l] = tracedataMergingNeuronRadius.get(k)[tracedataMergingNeuronRadius.get(k).length - l - 1];
                                        tracedataMergingNeuronSynapseOrderedTemp[l] = tracedataMergingNeuronSynapse.get(k)[tracedataMergingNeuronSynapse.get(k).length - l - 1];
                                        tracedataMergingNeuronConnectionOrderedTemp[l] = tracedataMergingNeuronConnection.get(k)[tracedataMergingNeuronConnection.get(k).length - l - 1];
                                    }

                                    tracedataMergingNeuronIndexOrdered.add(k);
                                    remainingEdgeSet.remove(k);

                                    tracedataMergingNeuronBranchOrdered.add(tracedataMergingNeuronBranchOrdered.get(lastItemIndex).concat("-1"));
                                    tracedataMergingNeuronCoordinateOrdered.add(tracedataMergingNeuronCoordinateOrderedTemp);
                                    tracedataMergingNeuronTypeOrdered.add(tracedataMergingNeuronTypeOrderedTemp);
                                    tracedataMergingNeuronRadiusOrdered.add(tracedataMergingNeuronRadiusOrderedTemp);
                                    tracedataMergingNeuronSynapseOrdered.add(tracedataMergingNeuronSynapseOrderedTemp);
                                    tracedataMergingNeuronConnectionOrdered.add(tracedataMergingNeuronConnectionOrderedTemp);

                                    lastItemIndex++;
                                    flagLoop = true;
                                    break;
                                }
                            }

                            if (flagLoop == false) {

                                OuterLoop:
                                for (int k = lastItemIndex - 1; k >= 0; k--) {

                                    for (Integer l : remainingEdgeSet) {

                                        if (Arrays.equals(tracedataMergingNeuronCoordinateOrdered.get(k)[tracedataMergingNeuronCoordinateOrdered.get(k).length - 1], tracedataMergingNeuronCoordinate.get(l)[0])) {

                                            tracedataMergingNeuronIndexOrdered.add(l);
                                            remainingEdgeSet.remove(l);

                                            tracedataMergingNeuronBranchOrdered.add(tracedataMergingNeuronBranchOrdered.get(k).concat("-2"));
                                            tracedataMergingNeuronCoordinateOrdered.add(tracedataMergingNeuronCoordinate.get(l));
                                            tracedataMergingNeuronTypeOrdered.add(tracedataMergingNeuronType.get(l));
                                            tracedataMergingNeuronRadiusOrdered.add(tracedataMergingNeuronRadius.get(l));
                                            tracedataMergingNeuronSynapseOrdered.add(tracedataMergingNeuronSynapse.get(l));
                                            tracedataMergingNeuronConnectionOrdered.add(tracedataMergingNeuronConnection.get(l));

                                            lastItemIndex++;
                                            flagLoop = true;
                                            break OuterLoop;

                                        } else if (Arrays.equals(tracedataMergingNeuronCoordinateOrdered.get(k)[tracedataMergingNeuronCoordinateOrdered.get(k).length - 1], tracedataMergingNeuronCoordinate.get(l)[tracedataMergingNeuronCoordinate.get(l).length - 1])) {

                                            Integer[][] tracedataMergingNeuronCoordinateOrderedTemp =new Integer[tracedataMergingNeuronCoordinate.get(l).length][3];
                                            String[] tracedataMergingNeuronTypeOrderedTemp =new String[tracedataMergingNeuronType.get(l).length];
                                            Integer[] tracedataMergingNeuronRadiusOrderedTemp =new Integer[tracedataMergingNeuronRadius.get(l).length];
                                            Integer[] tracedataMergingNeuronSynapseOrderedTemp =new Integer[tracedataMergingNeuronSynapse.get(l).length];
                                            String[] tracedataMergingNeuronConnectionOrderedTemp =new String[tracedataMergingNeuronConnection.get(l).length];

                                            for (int ll = 0; ll < tracedataMergingNeuronCoordinate.get(l).length; ll++) {
                                                tracedataMergingNeuronCoordinateOrderedTemp[ll] = tracedataMergingNeuronCoordinate.get(l)[tracedataMergingNeuronCoordinate.get(l).length - ll - 1];
                                                tracedataMergingNeuronTypeOrderedTemp[ll] = tracedataMergingNeuronType.get(l)[tracedataMergingNeuronType.get(l).length - ll - 1];
                                                tracedataMergingNeuronRadiusOrderedTemp[ll] = tracedataMergingNeuronRadius.get(l)[tracedataMergingNeuronRadius.get(l).length - ll - 1];
                                                tracedataMergingNeuronSynapseOrderedTemp[ll] = tracedataMergingNeuronSynapse.get(l)[tracedataMergingNeuronSynapse.get(l).length - ll - 1];
                                                tracedataMergingNeuronConnectionOrderedTemp[ll] = tracedataMergingNeuronConnection.get(l)[tracedataMergingNeuronConnection.get(l).length - ll - 1];
                                            }

                                            tracedataMergingNeuronIndexOrdered.add(l);
                                            remainingEdgeSet.remove(l);

                                            tracedataMergingNeuronBranchOrdered.add(tracedataMergingNeuronBranchOrdered.get(k).concat("-2"));
                                            tracedataMergingNeuronCoordinateOrdered.add(tracedataMergingNeuronCoordinateOrderedTemp);
                                            tracedataMergingNeuronTypeOrdered.add(tracedataMergingNeuronTypeOrderedTemp);
                                            tracedataMergingNeuronRadiusOrdered.add(tracedataMergingNeuronRadiusOrderedTemp);
                                            tracedataMergingNeuronSynapseOrdered.add(tracedataMergingNeuronSynapseOrderedTemp);
                                            tracedataMergingNeuronConnectionOrdered.add(tracedataMergingNeuronConnectionOrderedTemp);

                                            lastItemIndex++;
                                            flagLoop = true;
                                            break OuterLoop;
                                        }
                                    }
                                }
                            }
                        }

                        for (int k = 0; k < tracedataMergingNeuronIndexOrdered.size(); k++) {

                            String[] tracedataMergingNeuronBranchTemp = new String[3];
                            tracedataMergingNeuronBranchTemp[0] = "Neurite";
                            tracedataMergingNeuronBranchTemp[1] = Integer.toString(neuronNumber);
                            tracedataMergingNeuronBranchTemp[2] = tracedataMergingNeuronBranchOrdered.get(k);

                            tracedataMontageNeuronBranch.add(tracedataMergingNeuronBranchTemp);
                            tracedataMontageNeuronCoordinate.add(tracedataMergingNeuronCoordinateOrdered.get(k));
                            tracedataMontageNeuronType.add(tracedataMergingNeuronTypeOrdered.get(k));
                            tracedataMontageNeuronRadius.add(tracedataMergingNeuronRadiusOrdered.get(k));
                            tracedataMontageNeuronSynapse.add(tracedataMergingNeuronSynapseOrdered.get(k));
                            tracedataMontageNeuronConnection.add(tracedataMergingNeuronConnectionOrdered.get(k));

                            int index1 = tracedataMergingNeuronIndexOrdered.get(k);
                            int index2 = 0;

                            boolean flagIndex2 = false;

                            for (int l = 0; l < tracedataMergingNeuronIndex11.get(j).length; l++) {
                                if (tracedataMergingNeuronIndex11.get(j)[l][0].equals(tracedataMergingNeuronIndex1.get(j)[index1])) {
                                    index2 = tracedataMergingNeuronIndex11.get(j)[l][1];
                                    flagIndex2 = true;
                                    break;
                                }
                            }

                            if (flagIndex2 == false) {

                                String[] tracedataMontageNeuronMapTemp1 = new String[2];
                                tracedataMontageNeuronMapTemp1[0] = tracedataNeuronBranch1.get(tracedataMergingNeuronIndex1.get(j)[index1])[1].concat(tracedataNeuronBranch1.get(tracedataMergingNeuronIndex1.get(j)[index1])[2]);
                                tracedataMontageNeuronMapTemp1[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);

                                Integer[] tracedataMontageNeuronMapTemp11 = new Integer[3];
                                tracedataMontageNeuronMapTemp11[0] = 1;
                                tracedataMontageNeuronMapTemp11[1] = tracedataMontageNeuronMap1.size();
                                tracedataMontageNeuronMapTemp11[2] = null;
                                tracedataMontageNeuronMap11.add(tracedataMontageNeuronMapTemp11);

                                tracedataMontageNeuronMap1.add(tracedataMontageNeuronMapTemp1);

                            } else {

                                String[] tracedataMontageNeuronMapTemp1 = new String[2];
                                tracedataMontageNeuronMapTemp1[0] = tracedataNeuronBranch1.get(tracedataMergingNeuronIndex1.get(j)[index1])[1].concat(tracedataNeuronBranch1.get(tracedataMergingNeuronIndex1.get(j)[index1])[2]);
                                tracedataMontageNeuronMapTemp1[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);

                                String[] tracedataMontageNeuronMapTemp2 = new String[2];
                                tracedataMontageNeuronMapTemp2[0] = tracedataNeuronBranch1.get(index2)[1].concat(tracedataNeuronBranch1.get(index2)[2]);
                                tracedataMontageNeuronMapTemp2[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1].concat(tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[2]);

                                Integer[] tracedataMontageNeuronMapTemp11 = new Integer[3];
                                tracedataMontageNeuronMapTemp11[0] = 11;
                                tracedataMontageNeuronMapTemp11[1] = tracedataMontageNeuronMap1.size();
                                tracedataMontageNeuronMapTemp11[2] = tracedataMontageNeuronMap1.size() + 1;
                                tracedataMontageNeuronMap11.add(tracedataMontageNeuronMapTemp11);

                                tracedataMontageNeuronMap1.add(tracedataMontageNeuronMapTemp1);
                                tracedataMontageNeuronMap1.add(tracedataMontageNeuronMapTemp2);
                            }
                        }
                    }
                }
            }

            System.out.println("Size of tracedataMontageNeuronMap11: " + tracedataMontageNeuronMap11.size());
            System.out.println("Size of tracedataMontageNeuronMap1: " + tracedataMontageNeuronMap1.size());

            Integer spineNumber = 0;

            for (int i = 0; i < tracedataMontageNeuronBranch.size(); i++) {
                for (int j = 0; j < tracedataMontageNeuronType.get(i).length; j++) {

                    if (tracedataMontageNeuronType.get(i)[j].contains(":") && tracedataMontageNeuronType.get(i)[j].contains("#")) {

                        String[] typeOldTemp1 = tracedataMontageNeuronType.get(i)[j].split(":");

                        String typeOldTemp2 = typeOldTemp1[1].split("#")[1];

                        for (int k = 0; k < tracedataMontageSpineMap11.size(); k++) {

                            if (tracedataMontageSpineMap11.get(k)[2] == Integer.parseInt(typeOldTemp2)) {
                                spineNumber++;
                                tracedataMontageNeuronType.get(i)[j] = typeOldTemp1[0].concat(":Spine#").concat(spineNumber.toString());

                                String[] tracedataMontageNeuronBranchTemp1 = new String[3];
                                tracedataMontageNeuronBranchTemp1[0] = "Spine";
                                tracedataMontageNeuronBranchTemp1[1] = Integer.toString(spineNumber);
                                tracedataMontageNeuronBranchTemp1[2] = null;

                                tracedataMontageNeuronBranch.add(tracedataMontageNeuronBranchTemp1);
                                tracedataMontageNeuronCoordinate.add(tracedataNeuronCoordinateModified1.get(tracedataMontageSpineMap11.get(k)[1]));
                                tracedataMontageNeuronType.add(tracedataNeuronTypeModified1.get(tracedataMontageSpineMap11.get(k)[1]));
                                tracedataMontageNeuronRadius.add(tracedataNeuronRadiusModified1.get(tracedataMontageSpineMap11.get(k)[1]));
                                tracedataMontageNeuronSynapse.add(tracedataNeuronSynapseModified1.get(tracedataMontageSpineMap11.get(k)[1]));
                                tracedataMontageNeuronConnection.add(tracedataNeuronConnectionModified1.get(tracedataMontageSpineMap11.get(k)[1]));

                                Integer[] tracedataMontageNeuronMapTemp11 = new Integer[3];
                                tracedataMontageNeuronMapTemp11[0] = 1;
                                tracedataMontageNeuronMapTemp11[1] = tracedataMontageNeuronMap1.size();
                                tracedataMontageNeuronMapTemp11[2] = null;
                                tracedataMontageNeuronMap11.add(tracedataMontageNeuronMapTemp11);

                                String[] tracedataMontageNeuronMapTemp1 = new String[2];
                                tracedataMontageNeuronMapTemp1[0] = tracedataNeuronBranch1.get(tracedataMontageSpineMap11.get(k)[1])[1];
                                tracedataMontageNeuronMapTemp1[1] = tracedataMontageNeuronBranch.get(tracedataMontageNeuronBranch.size() - 1)[1];
                                tracedataMontageNeuronMap1.add(tracedataMontageNeuronMapTemp1);

                                break;
                            }
                        }
                    }
                }
            }

            System.out.println("Size of tracedataMontageNeuronMap11: " + tracedataMontageNeuronMap11.size());
            System.out.println("Size of tracedataMontageNeuronMap1: " + tracedataMontageNeuronMap1.size());

            ArrayList<String[]> tracedataMontageNeuronConnectionModified = new ArrayList<String[]>();
            tracedataMontageNeuronConnectionModified.addAll(tracedataMontageNeuronConnection);

            for (int i = 0; i < tracedataMontageNeuronMap11.size(); i++) {

                if (tracedataMontageNeuronMap11.get(i)[0] == 1) {

                    String connectionOld = tracedataMontageNeuronMap1.get(tracedataMontageNeuronMap11.get(i)[1])[0];
                    String connectionNew = tracedataMontageNeuronMap1.get(tracedataMontageNeuronMap11.get(i)[1])[1];

                    for (int j = 0; j < tracedataMontageNeuronConnection.size(); j++) {
                        if (tracedataMontageNeuronMap11.get(j)[0] == 1) {
                            for (int k = 0; k < tracedataMontageNeuronConnection.get(j).length; k++) {
                                if (! tracedataMontageNeuronConnection.get(j)[k].equals("0")) {

                                    String[] connectionOldTemp  = tracedataMontageNeuronConnection.get(j)[k].split("#");

                                    if (connectionOldTemp[1].equals(connectionOld)) {
                                        tracedataMontageNeuronConnectionModified.get(j)[k] = connectionOldTemp[0].concat("#").concat(connectionNew).concat("#").concat(connectionOldTemp[2]);
                                    }
                                }
                            }
                        }
                    }

                } else if (tracedataMontageNeuronMap11.get(i)[0] == 11) {

                    String connectionOld1 = tracedataMontageNeuronMap1.get(tracedataMontageNeuronMap11.get(i)[1])[0];
                    String connectionOld2 = tracedataMontageNeuronMap1.get(tracedataMontageNeuronMap11.get(i)[2])[0];

                    String connectionNew = tracedataMontageNeuronMap1.get(tracedataMontageNeuronMap11.get(i)[1])[1];

                    for (int j = 0; j < tracedataMontageNeuronConnection.size(); j++) {
                        if (tracedataMontageNeuronMap11.get(j)[0] == 11) {
                            for (int k = 0; k < tracedataMontageNeuronConnection.get(j).length; k++) {
                                if (! tracedataMontageNeuronConnection.get(j)[k].equals("0")) {

                                    String[] connectionOldTemp  = tracedataMontageNeuronConnection.get(j)[k].split("#");

                                    if ((connectionOldTemp[1].equals(connectionOld1) && connectionOldTemp[3].equals("data1")) ||
                                            (connectionOldTemp[1].equals(connectionOld2) && connectionOldTemp[3].equals("data2"))) {
                                        tracedataMontageNeuronConnectionModified.get(j)[k] = connectionOldTemp[0].concat("#").concat(connectionNew).concat("#").concat(connectionOldTemp[2]);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            tracedataMontageNeuronConnection.clear();
            tracedataMontageNeuronConnection.addAll(tracedataMontageNeuronConnectionModified);

            for (int i = 0; i < tracedataMontageNeuronConnection.size(); i++) {

                String connectedBranchPrimary;

                if (tracedataMontageNeuronBranch.get(i)[0].equals("Soma")) {
                    connectedBranchPrimary = tracedataMontageNeuronBranch.get(i)[1].concat(":").concat(tracedataMontageNeuronBranch.get(i)[2]);
                } else if (tracedataMontageNeuronBranch.get(i)[0].equals("Neurite")) {
                    connectedBranchPrimary = tracedataMontageNeuronBranch.get(i)[1].concat(tracedataMontageNeuronBranch.get(i)[2]);
                } else {
                    connectedBranchPrimary = tracedataMontageNeuronBranch.get(i)[1];
                }

                Integer connectionCounter1 = 0;

                for (int j = 0; j < tracedataMontageNeuronConnection.get(i).length; j++) {

                    if (! tracedataMontageNeuronConnection.get(i)[j].equals("0")) {

                        connectionCounter1 = connectionCounter1 + 1;
                        String connectedBranchSecondary = tracedataMontageNeuronConnection.get(i)[j].split("#")[1];

                        for (int k = 0; k < tracedataMontageNeuronConnection.size(); k++) {

                            String connectedBranchTemp1;

                            if (tracedataMontageNeuronBranch.get(k)[0].equals("Soma")) {
                                connectedBranchTemp1 = tracedataMontageNeuronBranch.get(k)[1].concat(":").concat(tracedataMontageNeuronBranch.get(k)[2]);
                            } else if (tracedataMontageNeuronBranch.get(k)[0].equals("Neurite")) {
                                connectedBranchTemp1 = tracedataMontageNeuronBranch.get(k)[1].concat(tracedataMontageNeuronBranch.get(k)[2]);
                            } else {
                                connectedBranchTemp1 = tracedataMontageNeuronBranch.get(k)[1];
                            }

                            if (connectedBranchSecondary.equals(connectedBranchTemp1)) {

                                Integer connectionCounter2 = 0;

                                for (int l = 0; l < tracedataMontageNeuronConnection.get(k).length; l++) {

                                    if (! tracedataMontageNeuronConnection.get(k)[l].equals("0")) {

                                        connectionCounter2 = connectionCounter2 + 1;

                                        String connectedBranchTemp2 = tracedataMontageNeuronConnection.get(k)[l].split("#")[1];

                                        if (connectedBranchPrimary.equals(connectedBranchTemp2)) {
                                            tracedataMontageNeuronConnection.get(i)[j] = (connectionCounter1.toString()).concat("#").concat(connectedBranchSecondary).concat("#").concat(connectionCounter2.toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            matchedOverlappedTraceAnalysisModified.addAll(matchedOverlappedTraceAnalysis);

            for (int i = 0; i < matchedOverlappedTraceBranch.size(); i++) {
                String oldBranch1 = tracedataNeuronBranch1.get(Integer.parseInt(matchedOverlappedTraceBranch.get(i)[0]))[1].concat(tracedataNeuronBranch1.get(Integer.parseInt(matchedOverlappedTraceBranch.get(i)[0]))[2]);

                for (int j = 0; j < tracedataMontageNeuronMap1.size(); j++) {
                    if (oldBranch1.equals(tracedataMontageNeuronMap1.get(j)[0])) {
                        matchedOverlappedTraceAnalysisModified.get(i)[8] = tracedataMontageNeuronMap1.get(j)[1];
                        break;
                    }
                }
            }

            System.out.println("tracedataMontageNeuronMap11: ");
            for (int i = 0; i < tracedataMontageNeuronMap11.size(); i++) {
               System.out.println(tracedataMontageNeuronMap11.get(i)[0] + " " + tracedataMontageNeuronMap11.get(i)[1] + " " + tracedataMontageNeuronMap11.get(i)[2]);
            }

            System.out.println("tracedataMontageNeuronMap1: ");
            for (int i = 0; i < tracedataMontageNeuronMap1.size(); i++) {
               System.out.println(tracedataMontageNeuronMap1.get(i)[0] + " " + tracedataMontageNeuronMap1.get(i)[1]);
            }

            System.out.println("Size of tracedataMontageNeuronTag: " + tracedataMontageNeuronTag.size());
            System.out.println("Size of tracedataMontageNeuronBranch: " + tracedataMontageNeuronBranch.size());
            System.out.println("Size of tracedataMontageNeuronCoordinate: " + tracedataMontageNeuronCoordinate.size());
            System.out.println("Size of tracedataMontageNeuronType: " + tracedataMontageNeuronType.size());
            System.out.println("Size of tracedataMontageNeuronRadius: " + tracedataMontageNeuronRadius.size());
            System.out.println("Size of tracedataMontageNeuronSynapse: " + tracedataMontageNeuronSynapse.size());
            System.out.println("Size of tracedataMontageNeuronConnection: " + tracedataMontageNeuronConnection.size());
        }
    }

    public ArrayList<String[]> getTracedataMontageNeuronTag() {
        return tracedataMontageNeuronTag;
    }

    public ArrayList<String[]> getTracedataMontageNeuronBranch() {
        return tracedataMontageNeuronBranch;
    }

    public ArrayList<Integer[][]> getTracedataMontageNeuronCoordinate() {
        return tracedataMontageNeuronCoordinate;
    }

    public ArrayList<String[]> getTracedataMontageNeuronType() {
        return tracedataMontageNeuronType;
    }

    public ArrayList<Integer[]> getTracedataMontageNeuronRadius() {
        return tracedataMontageNeuronRadius;
    }

    public ArrayList<Integer[]> getTracedataMontageNeuronSynapse() {
        return tracedataMontageNeuronSynapse;
    }

    public ArrayList<String[]> getTracedataMontageNeuronConnection() {
        return tracedataMontageNeuronConnection;
    }

    public ArrayList<String[]> getMatchedOverlappedTraceAnalysisModified() {
        return matchedOverlappedTraceAnalysisModified;
    }

}
