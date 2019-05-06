/* COPYRIGHT Creative Commons Attribution-Non Commercial 4.0 International (CC BY-NC 4.0) */

/**
 * A class for reading the tracing results from a data-file (zip/swc).
 *
 * @author Aslan Satary Dizaji <asataryd@umich.edu>
 * <https://sites.google.com/a/umich.edu/aslansdizaji>
 * <https://github.com/aslansd/TraceMontage>
 * @version 1.1 Created on 2015/05 (last modified on 2018/12)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ReadTraceDataFile {

    private String tracedataStatus;
    private String tracedataHeader;
    private Double[] imageResolution = new Double[3];
    
    private ArrayList<String[]> tracedataNeuronTag = new ArrayList<String[]>();
    private ArrayList<String[]> tracedataNeuronBranch = new ArrayList<String[]>();
    private ArrayList<Integer[][]> tracedataNeuronCoordinate = new ArrayList<Integer[][]>();
    private ArrayList<String[]> tracedataNeuronType = new ArrayList<String[]>();
    private ArrayList<Integer[]> tracedataNeuronRadius = new ArrayList<Integer[]>();
    private ArrayList<Integer[]> tracedataNeuronSynapse = new ArrayList<Integer[]>();
    private ArrayList<String[]> tracedataNeuronConnection = new ArrayList<String[]>();

    private boolean tracedataFlag;

    public ReadTraceDataFile(File filedata, Integer tracedataFileTypeFlag) throws IOException {

        if (tracedataFileTypeFlag == 1) {

            ZipFile filedataZip = new ZipFile(filedata);

            try {

                Enumeration<? extends ZipEntry> filedataZipFileEntry = filedataZip.entries();

                while (filedataZipFileEntry.hasMoreElements()) {

                    ZipEntry tracedataZipEntry = filedataZipFileEntry.nextElement();

                    if (tracedataZipEntry.getName().endsWith(".txt")) {

                        String tracedatafileName = tracedataZipEntry.getName();
                        String[] tracedatafileNamePart = tracedatafileName.split("-");

                        if (tracedatafileNamePart[tracedatafileNamePart.length - 1].equals("status.txt")) {

                            try {
                                InputStream tracedataIS = filedataZip.getInputStream(tracedataZipEntry);
                                InputStreamReader tracedataISR = new InputStreamReader(tracedataIS, "UTF-8");
                                BufferedReader tracedataBR = new BufferedReader(tracedataISR);

                                StringBuilder tracedataLine = new StringBuilder();
                                String tracedataLineTemp = tracedataBR.readLine();

                                while (tracedataLineTemp != null) {
                                    tracedataLine.append(tracedataLineTemp).append("\r");
                                    tracedataLineTemp = tracedataBR.readLine();
                                }

                                tracedataStatus = tracedataLine.toString();
                                tracedataFlag = true;

                                tracedataBR.close();
                                tracedataISR.close();
                                tracedataIS.close();
                            } catch (IOException e) {
                                tracedataFlag = false;
                                System.out.println(e.getMessage());
                            }

                        } else if (tracedatafileNamePart[tracedatafileNamePart.length - 1].equals("data.txt")) {

                            try {
                                InputStream tracedataIS = filedataZip.getInputStream(tracedataZipEntry);
                                InputStreamReader tracedataISR = new InputStreamReader(tracedataIS, "UTF-8");
                                BufferedReader tracedataBR = new BufferedReader(tracedataISR);

                                String tracedataFileTextFormat = "New";
                                
                                StringBuilder tracedataLine1 = new StringBuilder();
                                String tracedataLine1Temp;

                                for (int i = 0; i < 6; i++) {

                                    tracedataLine1Temp = tracedataBR.readLine();

                                    if (tracedataLine1Temp.contains("SomaChannel")) {
                                        tracedataFileTextFormat = "Old";
                                    } else {
                                        tracedataLine1.append(tracedataLine1Temp).append("\r");
                                    }
                                }

                                if (tracedataFileTextFormat.equals("Old")) {
                                    tracedataLine1.append("xyzResolutions: 0.094 0.094 0.25 ").append("\r");
                                }

                                tracedataHeader = tracedataLine1.toString();

                                if (tracedataFileTextFormat.equals("Old")) {

                                    Pattern patternNeuronBranchTemplate = Pattern.compile("Neuron (\\d+)((-\\d)*)");
                                    Pattern patternNeuronCoordinateTemplate = Pattern.compile("POINT: (\\d+) (\\d+) (\\d+) 0 0");

                                    String tracedataLine2 = tracedataBR.readLine();

                                    Matcher matcherNeuronBranchTemplate = patternNeuronBranchTemplate.matcher(tracedataLine2);
                                    boolean matcherNeuronBranchFlag = matcherNeuronBranchTemplate.find();

                                    while (matcherNeuronBranchFlag) {

                                        String[] tracedataNeuronTagTemp = new String[2];
                                        tracedataNeuronTagTemp[0] = matcherNeuronBranchTemplate.group(1);
                                        tracedataNeuronTagTemp[1] = "";
                                        tracedataNeuronTag.add(tracedataNeuronTagTemp);

                                        int neuronNumber = Integer.parseInt(matcherNeuronBranchTemplate.group(1));

                                        while (matcherNeuronBranchFlag && neuronNumber == Integer.parseInt(matcherNeuronBranchTemplate.group(1))) {

                                            String[] tracedataNeuronBranchTemp = new String[3];
                                            tracedataNeuronBranchTemp[0] = "Neurite";
                                            tracedataNeuronBranchTemp[1] = matcherNeuronBranchTemplate.group(1);
                                            tracedataNeuronBranchTemp[2] = ("-1").concat(matcherNeuronBranchTemplate.group(2));
                                            tracedataNeuronBranch.add(tracedataNeuronBranchTemp);

                                            tracedataLine2 = tracedataBR.readLine();
                                            
                                            Matcher matcherNeuronCoordinateTemplate = patternNeuronCoordinateTemplate.matcher(tracedataLine2);

                                            ArrayList<Integer[]> tracedataNeuronCoordinateTemp1 = new ArrayList<Integer[]>();
                                            ArrayList<String> tracedataNeuronTypeTemp1 = new ArrayList<String>();
                                            ArrayList<Integer> tracedataNeuronRadiusTemp1 = new ArrayList<Integer>();
                                            ArrayList<Integer> tracedataNeuronSynapseTemp1 = new ArrayList<Integer>();
                                            ArrayList<String> tracedataNeuronConnectionTemp1 = new ArrayList<String>();

                                            while (matcherNeuronCoordinateTemplate.find()) {

                                                Integer[] tracedataNeuronCoordinateTemp123 = new Integer[3];
                                                tracedataNeuronCoordinateTemp123[0] = Integer.parseInt(matcherNeuronCoordinateTemplate.group(1));
                                                tracedataNeuronCoordinateTemp123[1] = Integer.parseInt(matcherNeuronCoordinateTemplate.group(2));
                                                tracedataNeuronCoordinateTemp123[2] = Integer.parseInt(matcherNeuronCoordinateTemplate.group(3));
                                                tracedataNeuronCoordinateTemp1.add(tracedataNeuronCoordinateTemp123);

                                                tracedataNeuronTypeTemp1.add("Neurite");
                                                tracedataNeuronRadiusTemp1.add(0);
                                                tracedataNeuronSynapseTemp1.add(0);
                                                tracedataNeuronConnectionTemp1.add("0");
                                            }

                                            Integer[][] tracedataNeuronCoordinateTemp2 = new Integer[tracedataNeuronCoordinateTemp1.size()][3];
                                            String[] tracedataNeuronTypeTemp2 = new String[tracedataNeuronTypeTemp1.size()];
                                            Integer[] tracedataNeuronRadiusTemp2 = new Integer[tracedataNeuronRadiusTemp1.size()];
                                            Integer[] tracedataNeuronSynapseTemp2 = new Integer[tracedataNeuronSynapseTemp1.size()];
                                            String[] tracedataNeuronConnectionTemp2 = new String[tracedataNeuronConnectionTemp1.size()];

                                            for (int i = 0; i < tracedataNeuronCoordinateTemp1.size(); i++) {
                                                tracedataNeuronCoordinateTemp2[i] = tracedataNeuronCoordinateTemp1.get(i);
                                                tracedataNeuronTypeTemp2[i] = tracedataNeuronTypeTemp1.get(i);
                                                tracedataNeuronRadiusTemp2[i] = tracedataNeuronRadiusTemp1.get(i);
                                                tracedataNeuronSynapseTemp2[i] = tracedataNeuronSynapseTemp1.get(i);
                                                tracedataNeuronConnectionTemp2[i] = tracedataNeuronConnectionTemp1.get(i);
                                            }

                                            tracedataNeuronCoordinate.add(tracedataNeuronCoordinateTemp2);
                                            tracedataNeuronType.add(tracedataNeuronTypeTemp2);
                                            tracedataNeuronRadius.add(tracedataNeuronRadiusTemp2);
                                            tracedataNeuronSynapse.add(tracedataNeuronSynapseTemp2);
                                            tracedataNeuronConnection.add(tracedataNeuronConnectionTemp2);

                                            tracedataLine2 = tracedataBR.readLine();

                                            if (tracedataLine2 != null && ! tracedataLine2.isEmpty()) {
                                                matcherNeuronBranchTemplate = patternNeuronBranchTemplate.matcher(tracedataLine2);
                                                matcherNeuronBranchFlag = matcherNeuronBranchTemplate.find();
                                            } else {
                                                matcherNeuronBranchFlag = false;
                                            }
                                        }
                                    }

                                } else if (tracedataFileTextFormat.equals("New")) {

                                    Pattern patternNeuronBranchTemplate1 = Pattern.compile("Neuron (\\d+)/?(\\S*)");
                                    Pattern patternNeuronBranchTemplate2 = Pattern.compile("Soma (\\d+):(\\d+)");
                                    Pattern patternNeuronBranchTemplate3 = Pattern.compile("Neurite (\\d+)((-\\d+)+)");
                                    Pattern patternNeuronBranchTemplate4 = Pattern.compile("Spine (\\d+)");

                                    Pattern patternNeuronCoordinateTemplate = Pattern.compile("POINT: (\\S+) (\\d+) (\\d+) (\\d+) (\\d+) (\\d) (\\S+)");

                                    String tracedataLine2;

                                    while ((tracedataLine2 = tracedataBR.readLine()) != null) {

                                        Matcher matcherNeuronBranchTemplate1 = patternNeuronBranchTemplate1.matcher(tracedataLine2);
                                        boolean neuronFlag = matcherNeuronBranchTemplate1.find();

                                        Matcher matcherNeuronBranchTemplate4 = patternNeuronBranchTemplate4.matcher(tracedataLine2);
                                        boolean spineFlag = matcherNeuronBranchTemplate4.find();

                                        if (neuronFlag == true) {

                                            String[] tracedataNeuronTagTemp = new String[2];
                                            tracedataNeuronTagTemp[0] = matcherNeuronBranchTemplate1.group(1);
                                            tracedataNeuronTagTemp[1] = matcherNeuronBranchTemplate1.group(2);
                                            tracedataNeuronTag.add(tracedataNeuronTagTemp);

                                            tracedataLine2 = tracedataBR.readLine();

                                            Matcher matcherNeuronBranchTemplate2 = patternNeuronBranchTemplate2.matcher(tracedataLine2);
                                            boolean matcherNeuronBranchSomaFlag = matcherNeuronBranchTemplate2.find();
                                            boolean somaFlag = matcherNeuronBranchSomaFlag;

                                            while (matcherNeuronBranchSomaFlag) {

                                                String[] tracedataNeuronBranchTemp = new String[3];
                                                tracedataNeuronBranchTemp[0] = "Soma";
                                                tracedataNeuronBranchTemp[1] = matcherNeuronBranchTemplate2.group(1);
                                                tracedataNeuronBranchTemp[2] = matcherNeuronBranchTemplate2.group(2);
                                                tracedataNeuronBranch.add(tracedataNeuronBranchTemp);

                                                tracedataLine2 = tracedataBR.readLine();

                                                Matcher matcherNeuronCoordinateTemplate = patternNeuronCoordinateTemplate.matcher(tracedataLine2);

                                                ArrayList<Integer[]> tracedataNeuronCoordinateTemp1 = new ArrayList<Integer[]>();
                                                ArrayList<String> tracedataNeuronTypeTemp1 = new ArrayList<String>();
                                                ArrayList<Integer> tracedataNeuronRadiusTemp1 = new ArrayList<Integer>();
                                                ArrayList<Integer> tracedataNeuronSynapseTemp1 = new ArrayList<Integer>();
                                                ArrayList<String> tracedataNeuronConnectionTemp1 = new ArrayList<String>();

                                                while (matcherNeuronCoordinateTemplate.find()) {

                                                    Integer[] matcherNeuronCoordinateGroup234 = new Integer[3];
                                                    matcherNeuronCoordinateGroup234[0] = Integer.parseInt(matcherNeuronCoordinateTemplate.group(2));
                                                    matcherNeuronCoordinateGroup234[1] = Integer.parseInt(matcherNeuronCoordinateTemplate.group(3));
                                                    matcherNeuronCoordinateGroup234[2] = Integer.parseInt(matcherNeuronCoordinateTemplate.group(4));
                                                    tracedataNeuronCoordinateTemp1.add(matcherNeuronCoordinateGroup234);

                                                    tracedataNeuronTypeTemp1.add(matcherNeuronCoordinateTemplate.group(1));
                                                    tracedataNeuronRadiusTemp1.add(Integer.parseInt(matcherNeuronCoordinateTemplate.group(5)));
                                                    tracedataNeuronSynapseTemp1.add(Integer.parseInt(matcherNeuronCoordinateTemplate.group(6)));
                                                    tracedataNeuronConnectionTemp1.add(matcherNeuronCoordinateTemplate.group(7));
                                                }

                                                Integer[][] tracedataNeuronCoordinateTemp2 = new Integer[tracedataNeuronCoordinateTemp1.size()][3];
                                                String[] tracedataNeuronTypeTemp2 = new String[tracedataNeuronTypeTemp1.size()];
                                                Integer[] tracedataNeuronRadiusTemp2 = new Integer[tracedataNeuronRadiusTemp1.size()];
                                                Integer[] tracedataNeuronSynapseTemp2 = new Integer[tracedataNeuronSynapseTemp1.size()];
                                                String[] tracedataNeuronConnectionTemp2 = new String[tracedataNeuronConnectionTemp1.size()];

                                                for (int i = 0; i < tracedataNeuronCoordinateTemp1.size(); i++) {
                                                    tracedataNeuronCoordinateTemp2[i] = tracedataNeuronCoordinateTemp1.get(i);
                                                    tracedataNeuronTypeTemp2[i] = tracedataNeuronTypeTemp1.get(i);
                                                    tracedataNeuronRadiusTemp2[i] = tracedataNeuronRadiusTemp1.get(i);
                                                    tracedataNeuronSynapseTemp2[i] = tracedataNeuronSynapseTemp1.get(i);
                                                    tracedataNeuronConnectionTemp2[i] = tracedataNeuronConnectionTemp1.get(i);
                                                }

                                                tracedataNeuronCoordinate.add(tracedataNeuronCoordinateTemp2);
                                                tracedataNeuronType.add(tracedataNeuronTypeTemp2);
                                                tracedataNeuronRadius.add(tracedataNeuronRadiusTemp2);
                                                tracedataNeuronSynapse.add(tracedataNeuronSynapseTemp2);
                                                tracedataNeuronConnection.add(tracedataNeuronConnectionTemp2);

                                                tracedataLine2 = tracedataBR.readLine();

                                                matcherNeuronBranchTemplate2 = patternNeuronBranchTemplate2.matcher(tracedataLine2);
                                                matcherNeuronBranchSomaFlag = matcherNeuronBranchTemplate2.find();
                                            }

                                            if (somaFlag == false) {
                                                tracedataLine2 = tracedataBR.readLine();
                                            }

                                            Matcher matcherNeuronBranchTemplate3 = patternNeuronBranchTemplate3.matcher(tracedataLine2);
                                            boolean matcherNeuronBranchNeuriteFlag = matcherNeuronBranchTemplate3.find();

                                            while (matcherNeuronBranchNeuriteFlag) {

                                                String[] tracedataNeuronBranchTemp = new String[3];
                                                tracedataNeuronBranchTemp[0] = "Neurite";
                                                tracedataNeuronBranchTemp[1] = matcherNeuronBranchTemplate3.group(1);
                                                tracedataNeuronBranchTemp[2] = matcherNeuronBranchTemplate3.group(2);
                                                tracedataNeuronBranch.add(tracedataNeuronBranchTemp);

                                                tracedataLine2 = tracedataBR.readLine();

                                                Matcher matcherNeuronCoordinateTemplate = patternNeuronCoordinateTemplate.matcher(tracedataLine2);

                                                ArrayList<Integer[]> tracedataNeuronCoordinateTemp1 = new ArrayList<Integer[]>();
                                                ArrayList<String> tracedataNeuronTypeTemp1 = new ArrayList<String>();
                                                ArrayList<Integer> tracedataNeuronRadiusTemp1 = new ArrayList<Integer>();
                                                ArrayList<Integer> tracedataNeuronSynapseTemp1 = new ArrayList<Integer>();
                                                ArrayList<String> tracedataNeuronConnectionTemp1 = new ArrayList<String>();

                                                while (matcherNeuronCoordinateTemplate.find()) {

                                                    Integer[] matcherNeuronCoordinateGroup234 = new Integer[3];
                                                    matcherNeuronCoordinateGroup234[0] = Integer.parseInt(matcherNeuronCoordinateTemplate.group(2));
                                                    matcherNeuronCoordinateGroup234[1] = Integer.parseInt(matcherNeuronCoordinateTemplate.group(3));
                                                    matcherNeuronCoordinateGroup234[2] = Integer.parseInt(matcherNeuronCoordinateTemplate.group(4));
                                                    tracedataNeuronCoordinateTemp1.add(matcherNeuronCoordinateGroup234);

                                                    tracedataNeuronTypeTemp1.add(matcherNeuronCoordinateTemplate.group(1));
                                                    tracedataNeuronRadiusTemp1.add(Integer.parseInt(matcherNeuronCoordinateTemplate.group(5)));
                                                    tracedataNeuronSynapseTemp1.add(Integer.parseInt(matcherNeuronCoordinateTemplate.group(6)));
                                                    tracedataNeuronConnectionTemp1.add(matcherNeuronCoordinateTemplate.group(7));
                                                }

                                                Integer[][] tracedataNeuronCoordinateTemp2 = new Integer[tracedataNeuronCoordinateTemp1.size()][3];
                                                String[] tracedataNeuronTypeTemp2 = new String[tracedataNeuronTypeTemp1.size()];
                                                Integer[] tracedataNeuronRadiusTemp2 = new Integer[tracedataNeuronRadiusTemp1.size()];
                                                Integer[] tracedataNeuronSynapseTemp2 = new Integer[tracedataNeuronSynapseTemp1.size()];
                                                String[] tracedataNeuronConnectionTemp2 = new String[tracedataNeuronConnectionTemp1.size()];

                                                for (int i = 0; i < tracedataNeuronCoordinateTemp1.size(); i++) {
                                                    tracedataNeuronCoordinateTemp2[i] = tracedataNeuronCoordinateTemp1.get(i);
                                                    tracedataNeuronTypeTemp2[i] = tracedataNeuronTypeTemp1.get(i);
                                                    tracedataNeuronRadiusTemp2[i] = tracedataNeuronRadiusTemp1.get(i);
                                                    tracedataNeuronSynapseTemp2[i] = tracedataNeuronSynapseTemp1.get(i);
                                                    tracedataNeuronConnectionTemp2[i] = tracedataNeuronConnectionTemp1.get(i);
                                                }

                                                tracedataNeuronCoordinate.add(tracedataNeuronCoordinateTemp2);
                                                tracedataNeuronType.add(tracedataNeuronTypeTemp2);
                                                tracedataNeuronRadius.add(tracedataNeuronRadiusTemp2);
                                                tracedataNeuronSynapse.add(tracedataNeuronSynapseTemp2);
                                                tracedataNeuronConnection.add(tracedataNeuronConnectionTemp2);

                                                tracedataLine2 = tracedataBR.readLine();

                                                matcherNeuronBranchTemplate3 = patternNeuronBranchTemplate3.matcher(tracedataLine2);
                                                matcherNeuronBranchNeuriteFlag = matcherNeuronBranchTemplate3.find();
                                            }

                                        } else if (spineFlag == true) {

                                            boolean matcherNeuronBranchSpineFlag = spineFlag;

                                            while (matcherNeuronBranchSpineFlag) {

                                                String[] tracedataNeuronBranchTemp = new String[3];
                                                tracedataNeuronBranchTemp[0] = "Spine";
                                                tracedataNeuronBranchTemp[1] = matcherNeuronBranchTemplate4.group(1);
                                                tracedataNeuronBranchTemp[2] = null;
                                                tracedataNeuronBranch.add(tracedataNeuronBranchTemp);

                                                tracedataLine2 = tracedataBR.readLine();

                                                Matcher matcherNeuronCoordinateTemplate = patternNeuronCoordinateTemplate.matcher(tracedataLine2);

                                                ArrayList<Integer[]> tracedataNeuronCoordinateTemp1 = new ArrayList<Integer[]>();
                                                ArrayList<String> tracedataNeuronTypeTemp1 = new ArrayList<String>();
                                                ArrayList<Integer> tracedataNeuronRadiusTemp1 = new ArrayList<Integer>();
                                                ArrayList<Integer> tracedataNeuronSynapseTemp1 = new ArrayList<Integer>();
                                                ArrayList<String> tracedataNeuronConnectionTemp1 = new ArrayList<String>();

                                                while (matcherNeuronCoordinateTemplate.find()) {

                                                    Integer[] matcherNeuronCoordinateGroup234 = new Integer[3];
                                                    matcherNeuronCoordinateGroup234[0] = Integer.parseInt(matcherNeuronCoordinateTemplate.group(2));
                                                    matcherNeuronCoordinateGroup234[1] = Integer.parseInt(matcherNeuronCoordinateTemplate.group(3));
                                                    matcherNeuronCoordinateGroup234[2] = Integer.parseInt(matcherNeuronCoordinateTemplate.group(4));
                                                    tracedataNeuronCoordinateTemp1.add(matcherNeuronCoordinateGroup234);

                                                    tracedataNeuronTypeTemp1.add(matcherNeuronCoordinateTemplate.group(1));
                                                    tracedataNeuronRadiusTemp1.add(Integer.parseInt(matcherNeuronCoordinateTemplate.group(5)));
                                                    tracedataNeuronSynapseTemp1.add(Integer.parseInt(matcherNeuronCoordinateTemplate.group(6)));
                                                    tracedataNeuronConnectionTemp1.add(matcherNeuronCoordinateTemplate.group(7));
                                                }

                                                Integer[][] tracedataNeuronCoordinateTemp2 = new Integer[tracedataNeuronCoordinateTemp1.size()][3];
                                                String[] tracedataNeuronTypeTemp2 = new String[tracedataNeuronTypeTemp1.size()];
                                                Integer[] tracedataNeuronRadiusTemp2 = new Integer[tracedataNeuronRadiusTemp1.size()];
                                                Integer[] tracedataNeuronSynapseTemp2 = new Integer[tracedataNeuronSynapseTemp1.size()];
                                                String[] tracedataNeuronConnectionTemp2 = new String[tracedataNeuronConnectionTemp1.size()];

                                                for (int i = 0; i < tracedataNeuronCoordinateTemp1.size(); i++) {
                                                    tracedataNeuronCoordinateTemp2[i] = tracedataNeuronCoordinateTemp1.get(i);
                                                    tracedataNeuronTypeTemp2[i] = tracedataNeuronTypeTemp1.get(i);
                                                    tracedataNeuronRadiusTemp2[i] = tracedataNeuronRadiusTemp1.get(i);
                                                    tracedataNeuronSynapseTemp2[i] = tracedataNeuronSynapseTemp1.get(i);
                                                    tracedataNeuronConnectionTemp2[i] = tracedataNeuronConnectionTemp1.get(i);
                                                }

                                                tracedataNeuronCoordinate.add(tracedataNeuronCoordinateTemp2);
                                                tracedataNeuronType.add(tracedataNeuronTypeTemp2);
                                                tracedataNeuronRadius.add(tracedataNeuronRadiusTemp2);
                                                tracedataNeuronSynapse.add(tracedataNeuronSynapseTemp2);
                                                tracedataNeuronConnection.add(tracedataNeuronConnectionTemp2);

                                                tracedataLine2 = tracedataBR.readLine();

                                                if (tracedataLine2 != null && ! tracedataLine2.isEmpty()) {
                                                    matcherNeuronBranchTemplate4 = patternNeuronBranchTemplate4.matcher(tracedataLine2);
                                                    matcherNeuronBranchSpineFlag = matcherNeuronBranchTemplate4.find();
                                                } else {
                                                    matcherNeuronBranchSpineFlag = false;
                                                }
                                            }
                                        }
                                    }
                                }
                                
                                tracedataFlag = true;

                                tracedataBR.close();
                                tracedataISR.close();
                                tracedataIS.close();
                            } catch (IOException e) {
                                tracedataFlag = false;
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                }
            } finally {
                filedataZip.close();
            }
            
        } else if (tracedataFileTypeFlag == 2) {

            try {
                FileInputStream tracedataFIS = new FileInputStream(filedata);
                InputStreamReader tracedataISR = new InputStreamReader(tracedataFIS, "UTF-8");
                BufferedReader tracedataBR = new BufferedReader(tracedataISR);

                StringBuilder tracedataLine1 = new StringBuilder();
                String tracedataLine1Temp = tracedataBR.readLine();

                while (tracedataLine1Temp.contains("#")) {

                    if (tracedataLine1Temp.contains("resolutions")) {
                        
                        Pattern patternResolutions = Pattern.compile("([0-9]+(\\.[0-9]{1,3})?)");
                        Matcher matcherResolutions = patternResolutions.matcher(tracedataLine1Temp);

                        for (int i = 0; i < 3; i++) {
                            matcherResolutions.find();
                            imageResolution[i] = Double.parseDouble(matcherResolutions.group(1));
                        }
                    }

                    if (tracedataLine1Temp.contains("standard")) {
                        tracedataStatus = "standard";
                    } else if (tracedataLine1Temp.contains("extended")) {
                        tracedataStatus = "extended";
                    }

                    tracedataLine1.append(tracedataLine1Temp).append("\r");
                    tracedataLine1Temp = tracedataBR.readLine();
                }

                tracedataLine1.append(tracedataLine1Temp).append("\r");
                tracedataHeader = tracedataLine1.toString();

                String[] tracedataNeuronTagTemp = new String[2];
                tracedataNeuronTagTemp[0] = "1";
                tracedataNeuronTagTemp[1] = "";
                tracedataNeuronTag.add(tracedataNeuronTagTemp);

                ArrayList<String[]> neuronBranchPointMap = new ArrayList<String[]>();

                int somaZPlaneCounter = 0;
                int somaZPlaneOld = 0;

                int neuritePrimaryBranchCounter = 0;
                int spineBranchCounter = 0;

                String branchType = "";
                Integer branchTypeOld = (-1);

                if (tracedataStatus.equals("standard")) {

                    String tracedataLine2 = tracedataBR.readLine();
                    String[] matcherNeuronPointStandard = tracedataLine2.split(" ");

                    boolean flagOuterLoop = true;

                    Integer matcherNeuronPointStandardNumber = Integer.parseInt(matcherNeuronPointStandard[0]);
                    Integer matcherNeuronPointStandardType = Integer.parseInt(matcherNeuronPointStandard[1]);
                    Integer matcherNeuronPointStandardParent = Integer.parseInt(matcherNeuronPointStandard[6]);

                    while (flagOuterLoop) {

                        String[] tracedataNeuronBranchTemp = new String[3];
                        String[] neuronBranchPointMapTemp = new String[4];

                        ArrayList<Integer[]> tracedataNeuronCoordinateTemp1 = new ArrayList<Integer[]>();
                        ArrayList<String> tracedataNeuronTypeTemp1 = new ArrayList<String>();
                        ArrayList<Integer> tracedataNeuronRadiusTemp1 = new ArrayList<Integer>();
                        ArrayList<Integer> tracedataNeuronSynapseTemp1 = new ArrayList<Integer>();
                        ArrayList<String> tracedataNeuronConnectionTemp1 = new ArrayList<String>();

                        boolean flagInnerLoop = true;

                        while (flagInnerLoop) {

                            if (matcherNeuronPointStandardType == 1) {

                                branchType = "Soma";
                                somaZPlaneCounter = (int) Math.round(Double.parseDouble(matcherNeuronPointStandard[4]) / imageResolution[2]);
                                
                                if (somaZPlaneOld != somaZPlaneCounter) {

                                    branchTypeOld = matcherNeuronPointStandardType;
                                    somaZPlaneOld = somaZPlaneCounter;
                                    
                                    tracedataNeuronBranchTemp[0] = "Soma";
                                    tracedataNeuronBranchTemp[1] = "1";
                                    tracedataNeuronBranchTemp[2] = ((Integer) (somaZPlaneCounter)).toString();

                                    tracedataNeuronBranch.add(tracedataNeuronBranchTemp);

                                    if (neuronBranchPointMap.size() > 0) {
                                        neuronBranchPointMap.get(neuronBranchPointMap.size() - 1)[3] = ((Integer) (matcherNeuronPointStandardNumber - 1)).toString();
                                    }

                                    neuronBranchPointMapTemp[0] = "Soma";
                                    neuronBranchPointMapTemp[1] = tracedataNeuronBranchTemp[1].concat(":").concat(tracedataNeuronBranchTemp[2]);
                                    neuronBranchPointMapTemp[2] = matcherNeuronPointStandardNumber.toString();
                                    neuronBranchPointMapTemp[3] = ((Integer) (Integer.MAX_VALUE)).toString();

                                    neuronBranchPointMap.add(neuronBranchPointMapTemp);
                                }

                            } else if (matcherNeuronPointStandardType == 0 ||
                                       matcherNeuronPointStandardType == 2 ||
                                       matcherNeuronPointStandardType == 3 ||
                                       matcherNeuronPointStandardType == 4 ||
                                       matcherNeuronPointStandardType == 7) {

                                if (matcherNeuronPointStandardType == 0) {
                                    branchType = "Neurite";
                                } else if (matcherNeuronPointStandardType == 2) {
                                    branchType = "Axon";
                                } else if (matcherNeuronPointStandardType == 3) {
                                    branchType = "Dendrite";
                                } else if (matcherNeuronPointStandardType == 4) {
                                    branchType = "Apical";
                                } else if (matcherNeuronPointStandardType == 7) {
                                    branchType = "Spine";
                                }

                                if (matcherNeuronPointStandardType != branchTypeOld) {

                                    branchTypeOld = matcherNeuronPointStandardType;
                                    int branchParentIndex = (-1);
                                    
                                    for (int i = 0; i < neuronBranchPointMap.size(); i++) {
                                        if (matcherNeuronPointStandardParent >= Integer.parseInt(neuronBranchPointMap.get(i)[2]) && matcherNeuronPointStandardParent <= Integer.parseInt(neuronBranchPointMap.get(i)[3])) {
                                            if (neuronBranchPointMap.get(i)[0].equals("Neurite")) {
                                                branchParentIndex = i;
                                                break;
                                            }
                                        }
                                    }
                                    
                                    if (matcherNeuronPointStandardType == 0 ||
                                        matcherNeuronPointStandardType == 2 ||
                                        matcherNeuronPointStandardType == 3 ||
                                        matcherNeuronPointStandardType == 4) {

                                        tracedataNeuronBranchTemp[0] = "Neurite";
                                        tracedataNeuronBranchTemp[1] = "1";

                                        if (branchParentIndex == (-1)) {

                                            neuritePrimaryBranchCounter++;
                                            tracedataNeuronBranchTemp[2] = ("-").concat(((Integer) (neuritePrimaryBranchCounter)).toString());

                                        } else {

                                            boolean flagFirstBranch = false;

                                            for (int i = branchParentIndex + 1; i < neuronBranchPointMap.size(); i++) {
                                                if ((neuronBranchPointMap.get(branchParentIndex)[1].concat("-1")).equals(neuronBranchPointMap.get(i)[1])) {
                                                    flagFirstBranch = true;
                                                    break;
                                                }
                                            }

                                            if (flagFirstBranch == false) {
                                                tracedataNeuronBranchTemp[2] = ("-").concat((neuronBranchPointMap.get(branchParentIndex)[1].concat("-1")).split("-", 2)[1]);
                                            } else {
                                                tracedataNeuronBranchTemp[2] = ("-").concat((neuronBranchPointMap.get(branchParentIndex)[1].concat("-2")).split("-", 2)[1]);
                                            }
                                        }

                                        tracedataNeuronBranch.add(tracedataNeuronBranchTemp);

                                        if (neuronBranchPointMap.size() > 0) {
                                            neuronBranchPointMap.get(neuronBranchPointMap.size() - 1)[3] = ((Integer) (matcherNeuronPointStandardNumber - 1)).toString();
                                        }

                                        neuronBranchPointMapTemp[0] = "Neurite";
                                        neuronBranchPointMapTemp[1] = tracedataNeuronBranchTemp[1].concat(tracedataNeuronBranchTemp[2]);
                                        neuronBranchPointMapTemp[2] = matcherNeuronPointStandardNumber.toString();
                                        neuronBranchPointMapTemp[3] = ((Integer) (Integer.MAX_VALUE)).toString();

                                        neuronBranchPointMap.add(neuronBranchPointMapTemp);
                                        
                                    } else if (matcherNeuronPointStandardType == 7) {

                                        spineBranchCounter++;

                                        tracedataNeuronBranchTemp[0] = "Spine";
                                        tracedataNeuronBranchTemp[1] = ((Integer) (spineBranchCounter)).toString();
                                        tracedataNeuronBranchTemp[2] = null;

                                        tracedataNeuronBranch.add(tracedataNeuronBranchTemp);

                                        if (branchParentIndex != (-1)) {
                                            int branchParentSubIndex = matcherNeuronPointStandardParent - Integer.parseInt(neuronBranchPointMap.get(branchParentIndex)[2]);
                                            tracedataNeuronType.get(branchParentIndex)[branchParentSubIndex] = tracedataNeuronType.get(branchParentIndex)[branchParentSubIndex].concat(":Spine#").concat(tracedataNeuronBranchTemp[1]);
                                        }

                                        if (neuronBranchPointMap.size() > 0) {
                                            neuronBranchPointMap.get(neuronBranchPointMap.size() - 1)[3] = ((Integer) (matcherNeuronPointStandardNumber - 1)).toString();
                                        }

                                        neuronBranchPointMapTemp[0] = "Spine";
                                        neuronBranchPointMapTemp[1] = tracedataNeuronBranchTemp[1];
                                        neuronBranchPointMapTemp[2] = matcherNeuronPointStandardNumber.toString();
                                        neuronBranchPointMapTemp[3] = ((Integer) (Integer.MAX_VALUE)).toString();

                                        neuronBranchPointMap.add(neuronBranchPointMapTemp);
                                    }
                                }

                            } else if (matcherNeuronPointStandardType == 5 ||
                                       matcherNeuronPointStandardType == 6) {

                                flagInnerLoop = false;
                                branchTypeOld = matcherNeuronPointStandardType;

                                if (tracedataNeuronTypeTemp1.size() > 0) {
                                    branchType = tracedataNeuronTypeTemp1.get(0);
                                } else {
                                    branchType = "Neurite";
                                }
                            }

                            Integer[] matcherNeuronCoordinateGroup234 = new Integer[3];
                            matcherNeuronCoordinateGroup234[0] = (int) Math.round(Double.parseDouble(matcherNeuronPointStandard[2]) / imageResolution[0]);
                            matcherNeuronCoordinateGroup234[1] = (int) Math.round(Double.parseDouble(matcherNeuronPointStandard[3]) / imageResolution[1]);
                            matcherNeuronCoordinateGroup234[2] = (int) Math.round(Double.parseDouble(matcherNeuronPointStandard[4]) / imageResolution[2]);
                            tracedataNeuronCoordinateTemp1.add(matcherNeuronCoordinateGroup234);

                            tracedataNeuronTypeTemp1.add(branchType);
                            tracedataNeuronRadiusTemp1.add((int) Math.round(Double.parseDouble(matcherNeuronPointStandard[5]) / Math.sqrt(Math.pow(imageResolution[0], 2) + Math.pow(imageResolution[1], 2) + Math.pow(imageResolution[2], 2))));
                            tracedataNeuronSynapseTemp1.add(0);
                            tracedataNeuronConnectionTemp1.add("0");

                            tracedataLine2 = tracedataBR.readLine();

                            if (tracedataLine2 != null) {
                                matcherNeuronPointStandard = tracedataLine2.split(" ");
                                flagOuterLoop = true;
                            } else {
                                flagOuterLoop = false;
                            }

                            if (flagOuterLoop) {

                                matcherNeuronPointStandardNumber = Integer.parseInt(matcherNeuronPointStandard[0]);
                                matcherNeuronPointStandardType = Integer.parseInt(matcherNeuronPointStandard[1]);
                                matcherNeuronPointStandardParent = Integer.parseInt(matcherNeuronPointStandard[6]);

                                if (branchTypeOld == 1 && matcherNeuronPointStandardType == 1) {
                                    if (somaZPlaneOld != (int) Math.round(Double.parseDouble(matcherNeuronPointStandard[4]) / imageResolution[2])) {
                                        flagInnerLoop = false;
                                    }
                                } else if (branchTypeOld == 1 && matcherNeuronPointStandardType != 1) {
                                    flagInnerLoop = false;
                                }
                            }
                        }

                        Integer[][] tracedataNeuronCoordinateTemp2 = new Integer[tracedataNeuronCoordinateTemp1.size()][3];
                        String[] tracedataNeuronTypeTemp2 = new String[tracedataNeuronTypeTemp1.size()];
                        Integer[] tracedataNeuronRadiusTemp2 = new Integer[tracedataNeuronRadiusTemp1.size()];
                        Integer[] tracedataNeuronSynapseTemp2 = new Integer[tracedataNeuronSynapseTemp1.size()];
                        String[] tracedataNeuronConnectionTemp2 = new String[tracedataNeuronConnectionTemp1.size()];

                        for (int i = 0; i < tracedataNeuronCoordinateTemp1.size(); i++) {
                            tracedataNeuronCoordinateTemp2[i] = tracedataNeuronCoordinateTemp1.get(i);
                            tracedataNeuronTypeTemp2[i] = tracedataNeuronTypeTemp1.get(i);
                            tracedataNeuronRadiusTemp2[i] = tracedataNeuronRadiusTemp1.get(i);
                            tracedataNeuronSynapseTemp2[i] = tracedataNeuronSynapseTemp1.get(i);
                            tracedataNeuronConnectionTemp2[i] = tracedataNeuronConnectionTemp1.get(i);
                        }

                        tracedataNeuronCoordinate.add(tracedataNeuronCoordinateTemp2);
                        tracedataNeuronType.add(tracedataNeuronTypeTemp2);
                        tracedataNeuronRadius.add(tracedataNeuronRadiusTemp2);
                        tracedataNeuronSynapse.add(tracedataNeuronSynapseTemp2);
                        tracedataNeuronConnection.add(tracedataNeuronConnectionTemp2);                        
                    }

                } else if (tracedataStatus.equals("extended")) {

                    String tracedataLine2 = tracedataBR.readLine();
                    String[] matcherNeuronPointExtended = tracedataLine2.split(" ");

                    boolean flagOuterLoop = true;

                    Integer matcherNeuronPointExtendedNumber = Integer.parseInt(matcherNeuronPointExtended[0]);
                    Integer matcherNeuronPointExtendedType = Integer.parseInt(matcherNeuronPointExtended[1]);
                    Integer matcherNeuronPointExtendedParent = Integer.parseInt(matcherNeuronPointExtended[6]);

                    while (flagOuterLoop) {

                        String[] tracedataNeuronBranchTemp = new String[3];
                        String[] neuronBranchPointMapTemp = new String[4];

                        ArrayList<Integer[]> tracedataNeuronCoordinateTemp1 = new ArrayList<Integer[]>();
                        ArrayList<String> tracedataNeuronTypeTemp1 = new ArrayList<String>();
                        ArrayList<Integer> tracedataNeuronRadiusTemp1 = new ArrayList<Integer>();
                        ArrayList<Integer> tracedataNeuronSynapseTemp1 = new ArrayList<Integer>();
                        ArrayList<String> tracedataNeuronConnectionTemp1 = new ArrayList<String>();

                        boolean flagInnerLoop = true;

                        while (flagInnerLoop) {
                            
                            if (matcherNeuronPointExtendedType == 1) {

                                branchType = "Soma";
                                somaZPlaneCounter = (int) Math.round(Double.parseDouble(matcherNeuronPointExtended[4]) / imageResolution[2]);

                                if (somaZPlaneOld != somaZPlaneCounter) {

                                    branchTypeOld = matcherNeuronPointExtendedType;
                                    somaZPlaneOld = somaZPlaneCounter;

                                    tracedataNeuronBranchTemp[0] = "Soma";
                                    tracedataNeuronBranchTemp[1] = "1";
                                    tracedataNeuronBranchTemp[2] = ((Integer) (somaZPlaneCounter)).toString();

                                    tracedataNeuronBranch.add(tracedataNeuronBranchTemp);

                                    if (neuronBranchPointMap.size() > 0) {
                                        neuronBranchPointMap.get(neuronBranchPointMap.size() - 1)[3] = ((Integer) (matcherNeuronPointExtendedNumber - 1)).toString();
                                    }

                                    neuronBranchPointMapTemp[0] = "Soma";
                                    neuronBranchPointMapTemp[1] = tracedataNeuronBranchTemp[1].concat(":").concat(tracedataNeuronBranchTemp[2]);
                                    neuronBranchPointMapTemp[2] = matcherNeuronPointExtendedNumber.toString();
                                    neuronBranchPointMapTemp[3] = ((Integer) (Integer.MAX_VALUE)).toString();

                                    neuronBranchPointMap.add(neuronBranchPointMapTemp);
                                }

                            } else if (matcherNeuronPointExtendedType == 0 ||
                                       matcherNeuronPointExtendedType == 2 ||
                                       matcherNeuronPointExtendedType == 3 ||
                                       matcherNeuronPointExtendedType == 4 ||
                                       matcherNeuronPointExtendedType == 7) {

                                if (matcherNeuronPointExtendedType == 0) {
                                    branchType = "Neurite";
                                } else if (matcherNeuronPointExtendedType == 2) {
                                    branchType = "Axon";
                                } else if (matcherNeuronPointExtendedType == 3) {
                                    branchType = "Dendrite";
                                } else if (matcherNeuronPointExtendedType == 4) {
                                    branchType = "Apical";
                                } else if (matcherNeuronPointExtendedType == 7) {
                                    branchType = "Spine";
                                }

                                if (matcherNeuronPointExtendedType != branchTypeOld) {

                                    branchTypeOld = matcherNeuronPointExtendedType;
                                    int branchParentIndex = (-1);

                                    for (int i = 0; i < neuronBranchPointMap.size(); i++) {
                                        if (matcherNeuronPointExtendedParent >= Integer.parseInt(neuronBranchPointMap.get(i)[2]) && matcherNeuronPointExtendedParent <= Integer.parseInt(neuronBranchPointMap.get(i)[3])) {
                                            if (neuronBranchPointMap.get(i)[0].equals("Neurite")) {
                                                branchParentIndex = i;
                                                break;
                                            }
                                        }
                                    }

                                    if (matcherNeuronPointExtendedType == 0 ||
                                        matcherNeuronPointExtendedType == 2 ||
                                        matcherNeuronPointExtendedType == 3 ||
                                        matcherNeuronPointExtendedType == 4) {

                                        tracedataNeuronBranchTemp[0] = "Neurite";
                                        tracedataNeuronBranchTemp[1] = "1";

                                        if (branchParentIndex == (-1)) {

                                            neuritePrimaryBranchCounter++;
                                            tracedataNeuronBranchTemp[2] = ("-").concat(((Integer) (neuritePrimaryBranchCounter)).toString());

                                        } else {

                                            boolean flagFirstBranch = false;

                                            for (int i = branchParentIndex + 1; i < neuronBranchPointMap.size(); i++) {
                                                if ((neuronBranchPointMap.get(branchParentIndex)[1].concat("-1")).equals(neuronBranchPointMap.get(i)[1])) {
                                                    flagFirstBranch = true;
                                                    break;
                                                }
                                            }

                                            if (flagFirstBranch == false) {
                                                tracedataNeuronBranchTemp[2] = ("-").concat((neuronBranchPointMap.get(branchParentIndex)[1].concat("-1")).split("-", 2)[1]);
                                            } else {
                                                tracedataNeuronBranchTemp[2] = ("-").concat((neuronBranchPointMap.get(branchParentIndex)[1].concat("-2")).split("-", 2)[1]);
                                            }
                                        }

                                        tracedataNeuronBranch.add(tracedataNeuronBranchTemp);

                                        if (neuronBranchPointMap.size() > 0) {
                                            neuronBranchPointMap.get(neuronBranchPointMap.size() - 1)[3] = ((Integer) (matcherNeuronPointExtendedNumber - 1)).toString();
                                        }

                                        neuronBranchPointMapTemp[0] = "Neurite";
                                        neuronBranchPointMapTemp[1] = tracedataNeuronBranchTemp[1].concat(tracedataNeuronBranchTemp[2]);
                                        neuronBranchPointMapTemp[2] = matcherNeuronPointExtendedNumber.toString();
                                        neuronBranchPointMapTemp[3] = ((Integer) (Integer.MAX_VALUE)).toString();

                                        neuronBranchPointMap.add(neuronBranchPointMapTemp);

                                    } else if (matcherNeuronPointExtendedType == 7) {

                                        spineBranchCounter++;

                                        tracedataNeuronBranchTemp[0] = "Spine";
                                        tracedataNeuronBranchTemp[1] = ((Integer) (spineBranchCounter)).toString();
                                        tracedataNeuronBranchTemp[2] = null;

                                        tracedataNeuronBranch.add(tracedataNeuronBranchTemp);

                                        if (branchParentIndex != (-1)) {
                                            int branchParentSubIndex = matcherNeuronPointExtendedParent - Integer.parseInt(neuronBranchPointMap.get(branchParentIndex)[2]);
                                            tracedataNeuronType.get(branchParentIndex)[branchParentSubIndex] = tracedataNeuronType.get(branchParentIndex)[branchParentSubIndex].concat(":Spine#").concat(tracedataNeuronBranchTemp[1]);
                                        }

                                        if (neuronBranchPointMap.size() > 0) {
                                            neuronBranchPointMap.get(neuronBranchPointMap.size() - 1)[3] = ((Integer) (matcherNeuronPointExtendedNumber - 1)).toString();
                                        }

                                        neuronBranchPointMapTemp[0] = "Spine";
                                        neuronBranchPointMapTemp[1] = tracedataNeuronBranchTemp[1];
                                        neuronBranchPointMapTemp[2] = matcherNeuronPointExtendedNumber.toString();
                                        neuronBranchPointMapTemp[3] = ((Integer) (Integer.MAX_VALUE)).toString();

                                        neuronBranchPointMap.add(neuronBranchPointMapTemp);
                                    }
                                }

                            } else if (matcherNeuronPointExtendedType == 5 ||
                                       matcherNeuronPointExtendedType == 6) {

                                flagInnerLoop = false;
                                branchTypeOld = matcherNeuronPointExtendedType;

                                if (tracedataNeuronTypeTemp1.size() > 0) {
                                    branchType = tracedataNeuronTypeTemp1.get(0);
                                } else {
                                    branchType = "Neurite";
                                }
                            }

                            Integer[] matcherNeuronCoordinateGroup234 = new Integer[3];
                            matcherNeuronCoordinateGroup234[0] = (int) Math.round(Double.parseDouble(matcherNeuronPointExtended[2]) / imageResolution[0]);
                            matcherNeuronCoordinateGroup234[1] = (int) Math.round(Double.parseDouble(matcherNeuronPointExtended[3]) / imageResolution[1]);
                            matcherNeuronCoordinateGroup234[2] = (int) Math.round(Double.parseDouble(matcherNeuronPointExtended[4]) / imageResolution[2]);
                            tracedataNeuronCoordinateTemp1.add(matcherNeuronCoordinateGroup234);

                            tracedataNeuronTypeTemp1.add(branchType);
                            tracedataNeuronRadiusTemp1.add((int) Math.round(Double.parseDouble(matcherNeuronPointExtended[5]) / Math.sqrt(Math.pow(imageResolution[0], 2) + Math.pow(imageResolution[1], 2) + Math.pow(imageResolution[2], 2))));
                            tracedataNeuronSynapseTemp1.add(Integer.parseInt(matcherNeuronPointExtended[7]));
                            tracedataNeuronConnectionTemp1.add("0");

                            tracedataLine2 = tracedataBR.readLine();

                            if (tracedataLine2 != null) {
                                matcherNeuronPointExtended = tracedataLine2.split(" ");
                                flagOuterLoop = true;
                            } else {
                                flagOuterLoop = false;
                            }
                            
                            if (flagOuterLoop) {

                                matcherNeuronPointExtendedNumber = Integer.parseInt(matcherNeuronPointExtended[0]);
                                matcherNeuronPointExtendedType = Integer.parseInt(matcherNeuronPointExtended[1]);
                                matcherNeuronPointExtendedParent = Integer.parseInt(matcherNeuronPointExtended[6]);

                                if (branchTypeOld == 1 && matcherNeuronPointExtendedType == 1) {
                                    if (somaZPlaneOld != (int) Math.round(Double.parseDouble(matcherNeuronPointExtended[4]) / imageResolution[2])) {
                                        flagInnerLoop = false;
                                    }
                                } else if (branchTypeOld == 1 && matcherNeuronPointExtendedType != 1) {
                                    flagInnerLoop = false;
                                }
                            }
                        }

                        Integer[][] tracedataNeuronCoordinateTemp2 = new Integer[tracedataNeuronCoordinateTemp1.size()][3];
                        String[] tracedataNeuronTypeTemp2 = new String[tracedataNeuronTypeTemp1.size()];
                        Integer[] tracedataNeuronRadiusTemp2 = new Integer[tracedataNeuronRadiusTemp1.size()];
                        Integer[] tracedataNeuronSynapseTemp2 = new Integer[tracedataNeuronSynapseTemp1.size()];
                        String[] tracedataNeuronConnectionTemp2 = new String[tracedataNeuronConnectionTemp1.size()];

                        for (int i = 0; i < tracedataNeuronCoordinateTemp1.size(); i++) {
                            tracedataNeuronCoordinateTemp2[i] = tracedataNeuronCoordinateTemp1.get(i);
                            tracedataNeuronTypeTemp2[i] = tracedataNeuronTypeTemp1.get(i);
                            tracedataNeuronRadiusTemp2[i] = tracedataNeuronRadiusTemp1.get(i);
                            tracedataNeuronSynapseTemp2[i] = tracedataNeuronSynapseTemp1.get(i);
                            tracedataNeuronConnectionTemp2[i] = tracedataNeuronConnectionTemp1.get(i);
                        }

                        tracedataNeuronCoordinate.add(tracedataNeuronCoordinateTemp2);
                        tracedataNeuronType.add(tracedataNeuronTypeTemp2);
                        tracedataNeuronRadius.add(tracedataNeuronRadiusTemp2);
                        tracedataNeuronSynapse.add(tracedataNeuronSynapseTemp2);
                        tracedataNeuronConnection.add(tracedataNeuronConnectionTemp2);
                    }
                }

                tracedataFlag = true;

                tracedataBR.close();
                tracedataISR.close();
                tracedataFIS.close();
            } catch (IOException e) {
                tracedataFlag = false;
                System.out.println(e.getMessage());
            }
        }

        for (int i = 0; i < tracedataNeuronBranch.size(); i++) {
            if (tracedataNeuronBranch.get(i)[0].equals("Neurite")) {

                int binaryBranchCount = 0;
                Integer[] binaryBranchIndex = new Integer[2];
                String referenceBranch = tracedataNeuronBranch.get(i)[1].concat(tracedataNeuronBranch.get(i)[2]);

                for (int j = i + 1; j < tracedataNeuronBranch.size(); j++) {
                    if (tracedataNeuronBranch.get(j)[0].equals("Neurite")) {
                        if ((referenceBranch.concat("-1")).equals(tracedataNeuronBranch.get(j)[1].concat(tracedataNeuronBranch.get(j)[2])) ||
                            (referenceBranch.concat("-2")).equals(tracedataNeuronBranch.get(j)[1].concat(tracedataNeuronBranch.get(j)[2]))) {

                            if (! Arrays.equals(tracedataNeuronCoordinate.get(i)[tracedataNeuronCoordinate.get(i).length - 1], tracedataNeuronCoordinate.get(j)[0])) {
                                binaryBranchIndex[binaryBranchCount] = j;
                                binaryBranchCount++;
                            }
                        }

                        if (binaryBranchCount == 2) {
                            break;
                        }
                    }
                }

                for (int j = 0; j < binaryBranchCount; j++) {

                    Integer[][] coordinateUpdate = new Integer[tracedataNeuronCoordinate.get(binaryBranchIndex[j]).length + 1][3];
                    String[] typeUpdate = new String[tracedataNeuronType.get(binaryBranchIndex[j]).length + 1];
                    Integer[] radiusUpdate = new Integer[tracedataNeuronRadius.get(binaryBranchIndex[j]).length + 1];
                    Integer[] synapseUpdate = new Integer[tracedataNeuronSynapse.get(binaryBranchIndex[j]).length + 1];
                    String[] connectionUpdate = new String[tracedataNeuronConnection.get(binaryBranchIndex[j]).length + 1];

                    coordinateUpdate[0] = tracedataNeuronCoordinate.get(i)[tracedataNeuronCoordinate.get(i).length - 1];
                    typeUpdate[0] = tracedataNeuronType.get(binaryBranchIndex[j])[0].split(":")[0];
                    radiusUpdate[0] = tracedataNeuronRadius.get(binaryBranchIndex[j])[0];
                    synapseUpdate[0] = 0;
                    connectionUpdate[0] = "0";

                    for (int k = 0; k < tracedataNeuronCoordinate.get(binaryBranchIndex[j]).length; k++) {
                        coordinateUpdate[k + 1] = tracedataNeuronCoordinate.get(binaryBranchIndex[j])[k];
                        typeUpdate[k + 1] = tracedataNeuronType.get(binaryBranchIndex[j])[k];
                        radiusUpdate[k + 1] = tracedataNeuronRadius.get(binaryBranchIndex[j])[k];
                        synapseUpdate[k + 1] = tracedataNeuronSynapse.get(binaryBranchIndex[j])[k];
                        connectionUpdate[k + 1] = tracedataNeuronConnection.get(binaryBranchIndex[j])[k];
                    }

                    tracedataNeuronCoordinate.set(binaryBranchIndex[j], coordinateUpdate);
                    tracedataNeuronType.set(binaryBranchIndex[j], typeUpdate);
                    tracedataNeuronRadius.set(binaryBranchIndex[j], radiusUpdate);
                    tracedataNeuronSynapse.set(binaryBranchIndex[j], synapseUpdate);
                    tracedataNeuronConnection.set(binaryBranchIndex[j], connectionUpdate);
                }
            }
        }
    }

    public String getTracedataStatus() {
        return tracedataStatus;
    }

    public String getTracedataHeader() {
        return tracedataHeader;
    }

    public Double[] getImageResolution() {
        return imageResolution;
    }

    public ArrayList<String[]> getTracedataNeuronTag() {
        return tracedataNeuronTag;
    }

    public ArrayList<String[]> getTracedataNeuronBranch() {
        return tracedataNeuronBranch;
    }

    public ArrayList<Integer[][]> getTracedataNeuronCoordinate() {
        return tracedataNeuronCoordinate;
    }

    public ArrayList<String[]> getTracedataNeuronType() {
        return tracedataNeuronType;
    }

    public ArrayList<Integer[]> getTracedataNeuronRadius() {
        return tracedataNeuronRadius;
    }

    public ArrayList<Integer[]> getTracedataNeuronSynapse() {
        return tracedataNeuronSynapse;
    }

    public ArrayList<String[]> getTracedataNeuronConnection() {
        return tracedataNeuronConnection;
    }

    public boolean getTracedataFlag() {
        return tracedataFlag;
    }

}
