/* COPYRIGHT Creative Commons Attribution-Non Commercial 4.0 International (CC BY-NC 4.0) */

/**
 * A class for writing the tracing results to a data-file (zip/swc).
 *
 * @author Aslan Satary Dizaji <asataryd@umich.edu>
 * <https://sites.google.com/a/umich.edu/aslansdizaji>
 * <https://github.com/aslansd/TraceMontage>
 * @version 1.1 Created on 2015/05 (last modified on 2018/12)
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class WriteTraceDataFile {

    public WriteTraceDataFile(File filedataMontage,
            Integer tracedataFileTypeFlag,
            String tracedataMontageStatus,
            String tracedataMontageHeader,
            Double[] imageMontageResolution,
            List<String[]> tracedataMontageNeuronTag,
            List<String[]> tracedataMontageNeuronBranch,
            List<Integer[][]> tracedataMontageNeuronCoordinate,
            List<String[]> tracedataMontageNeuronType,
            List<Integer[]> tracedataMontageNeuronRadius,
            List<Integer[]> tracedataMontageNeuronSynapse,
            List<String[]> tracedataMontageNeuronConnection) throws IOException {

        if (tracedataFileTypeFlag == 1) {

            try {
                FileOutputStream tracedataFOS = new FileOutputStream(filedataMontage + ".zip");
                ZipOutputStream tracedataZOS = new ZipOutputStream(tracedataFOS);

                ZipEntry tracedataZipEntry = new ZipEntry(filedataMontage.getName() + "-status.txt");
                tracedataZOS.putNextEntry(tracedataZipEntry);
                OutputStreamWriter tracedataOSW = new OutputStreamWriter(tracedataZOS, "UTF-8");
                BufferedWriter tracedataBW = new BufferedWriter(tracedataOSW);

                tracedataBW.write(tracedataMontageStatus);
                tracedataBW.flush();
                
                tracedataZipEntry = new ZipEntry(filedataMontage.getName() + "-data.txt");
                tracedataZOS.putNextEntry(tracedataZipEntry);
                tracedataOSW = new OutputStreamWriter(tracedataZOS, "UTF-8");
                tracedataBW = new BufferedWriter(tracedataOSW);

                tracedataBW.write(tracedataMontageHeader);
                StringBuilder tracedataMontageSB = new StringBuilder();

                int outsideCounter = 0;

                for (int i = 0; i < tracedataMontageNeuronTag.size(); i++) {

                    if (! tracedataMontageNeuronTag.get(i)[1].isEmpty()) {
                        tracedataMontageSB.append("Neuron ").append(tracedataMontageNeuronTag.get(i)[0]).append("/").append(tracedataMontageNeuronTag.get(i)[1]).append("\r");
                    } else {
                        tracedataMontageSB.append("Neuron ").append(tracedataMontageNeuronTag.get(i)[0]).append("\r");
                    }

                    tracedataBW.write(tracedataMontageSB.toString());
                    tracedataMontageSB = new StringBuilder();

                    String neuronNumber = tracedataMontageNeuronTag.get(i)[0];
                    int insideCounter = 0;

                    while ((outsideCounter < tracedataMontageNeuronBranch.size()) && (! tracedataMontageNeuronBranch.get(outsideCounter)[0].equals("Spine")) && (tracedataMontageNeuronBranch.get(outsideCounter)[1].equals(neuronNumber))) {

                        if (insideCounter == 0 && tracedataMontageNeuronBranch.get(outsideCounter)[0].equals("Neurite")) {
                            tracedataMontageSB.append("Soma ").append(neuronNumber).append(":-1\r");
                            tracedataMontageSB.append("Neurite ").append(neuronNumber).append(tracedataMontageNeuronBranch.get(outsideCounter)[2]).append("\r");
                        } else if (tracedataMontageNeuronBranch.get(outsideCounter)[0].equals("Soma")) {
                            tracedataMontageSB.append("Soma ").append(neuronNumber).append(":").append(tracedataMontageNeuronBranch.get(outsideCounter)[2]).append("\r");
                        } else if (insideCounter != 0 && tracedataMontageNeuronBranch.get(outsideCounter)[0].equals("Neurite")) {
                            tracedataMontageSB.append("Neurite ").append(neuronNumber).append(tracedataMontageNeuronBranch.get(outsideCounter)[2]).append("\r");
                        }

                        tracedataBW.write(tracedataMontageSB.toString());
                        tracedataMontageSB = new StringBuilder();

                        Integer[][] tracedataMontageNeuronCoordinateTemp = tracedataMontageNeuronCoordinate.get(outsideCounter);
                        String[] tracedataMontageNeuronTypeTemp = tracedataMontageNeuronType.get(outsideCounter);
                        Integer[] tracedataMontageNeuronRadiusTemp = tracedataMontageNeuronRadius.get(outsideCounter);
                        Integer[] tracedataMontageNeuronSynapseTemp = tracedataMontageNeuronSynapse.get(outsideCounter);
                        String[] tracedataMontageNeuronConnectionTemp = tracedataMontageNeuronConnection.get(outsideCounter);

                        for (int j = 0; j < tracedataMontageNeuronCoordinateTemp.length; j++) {

                            tracedataMontageSB.append("POINT: ").append(tracedataMontageNeuronTypeTemp[j]).append(" ");
                            tracedataMontageSB.append(tracedataMontageNeuronCoordinateTemp[j][0].toString()).append(" ").append(tracedataMontageNeuronCoordinateTemp[j][1].toString()).append(" ").append(tracedataMontageNeuronCoordinateTemp[j][2].toString()).append(" ");
                            tracedataMontageSB.append(tracedataMontageNeuronRadiusTemp[j]).append(" ");
                            tracedataMontageSB.append(tracedataMontageNeuronSynapseTemp[j]).append(" ");
                            tracedataMontageSB.append(tracedataMontageNeuronConnectionTemp[j]).append(" ");
                        }

                        tracedataMontageSB.append("END\r");

                        tracedataBW.write(tracedataMontageSB.toString());
                        tracedataMontageSB = new StringBuilder();

                        outsideCounter++;
                        insideCounter++;
                    }

                    tracedataMontageSB.append("ENDneuron\r");

                    tracedataBW.write(tracedataMontageSB.toString());
                    tracedataMontageSB = new StringBuilder();
                }

                while ((outsideCounter < tracedataMontageNeuronBranch.size()) && (tracedataMontageNeuronBranch.get(outsideCounter)[0].equals("Spine"))) {

                    tracedataMontageSB.append("Spine ").append(tracedataMontageNeuronBranch.get(outsideCounter)[1]).append("\r");

                    tracedataBW.write(tracedataMontageSB.toString());
                    tracedataMontageSB = new StringBuilder();

                    Integer[][] tracedataMontageNeuronCoordinateTemp = tracedataMontageNeuronCoordinate.get(outsideCounter);
                    String[] tracedataMontageNeuronTypeTemp = tracedataMontageNeuronType.get(outsideCounter);
                    Integer[] tracedataMontageNeuronRadiusTemp = tracedataMontageNeuronRadius.get(outsideCounter);
                    Integer[] tracedataMontageNeuronSynapseTemp = tracedataMontageNeuronSynapse.get(outsideCounter);
                    String[] tracedataMontageNeuronConnectionTemp = tracedataMontageNeuronConnection.get(outsideCounter);

                    for (int j = 0; j < tracedataMontageNeuronCoordinateTemp.length; j++) {

                        tracedataMontageSB.append("POINT: ").append(tracedataMontageNeuronTypeTemp[j]).append(" ");
                        tracedataMontageSB.append(tracedataMontageNeuronCoordinateTemp[j][0].toString()).append(" ").append(tracedataMontageNeuronCoordinateTemp[j][1].toString()).append(" ").append(tracedataMontageNeuronCoordinateTemp[j][2].toString()).append(" ");
                        tracedataMontageSB.append(tracedataMontageNeuronRadiusTemp[j]).append(" ");
                        tracedataMontageSB.append(tracedataMontageNeuronSynapseTemp[j]).append(" ");
                        tracedataMontageSB.append(tracedataMontageNeuronConnectionTemp[j]).append(" ");
                    }

                    tracedataMontageSB.append("ENDspine\r");

                    tracedataBW.write(tracedataMontageSB.toString());
                    tracedataMontageSB = new StringBuilder();

                    outsideCounter++;
                }

                tracedataBW.close();
                tracedataOSW.close();
                tracedataZOS.close();
                tracedataFOS.close();       
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            
        } else if (tracedataFileTypeFlag == 2) {

            try {
                FileOutputStream tracedataFOS = new FileOutputStream(filedataMontage + ".swc");
                OutputStreamWriter tracedataOSW = new OutputStreamWriter(tracedataFOS, "UTF-8");
                BufferedWriter tracedataBW = new BufferedWriter(tracedataOSW);

                DateFormat todayDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date todayDate = new Date();

                StringReader tracedataMontageHeaderSR = new StringReader(tracedataMontageHeader);
                BufferedReader tracedataMontageHeaderBR = new BufferedReader(tracedataMontageHeaderSR);
                StringBuilder tracedataMontageHeaderSB = new StringBuilder();

                tracedataMontageHeaderSB.append("# CREATION_DATE ").append(todayDateFormat.format(todayDate)).append("\r");
                tracedataMontageHeaderSB.append("# ").append(filedataMontage.getName()).append(".swc").append("\r");
                tracedataMontageHeaderSB.append("# CREATED BY TraceMontage").append("\r");
                tracedataMontageHeaderSB.append("# Aslan Satary Dizaji, University of Michigan (asataryd@umich.edu)").append("\r");

                String tracedataMontageHeaderLine;

                for (int i = 0; i < 4; i++) {
                    tracedataMontageHeaderLine = tracedataMontageHeaderBR.readLine();
                }

                tracedataMontageHeaderLine = tracedataMontageHeaderBR.readLine();

                while (tracedataMontageHeaderLine.contains("#")) {
                    tracedataMontageHeaderSB.append(tracedataMontageHeaderLine).append("\r");
                    tracedataMontageHeaderLine = tracedataMontageHeaderBR.readLine();
                }

                tracedataMontageHeaderSB.append(tracedataMontageHeaderLine).append("\r");

                tracedataBW.write(tracedataMontageHeaderSB.toString());

                ArrayList<String[]> neuronBranchPointMap = new ArrayList<String[]>();

                int coordinatePointIdentifier = 0;
                String branchTypeIdentifier = "";

                if (tracedataMontageStatus.equals("standard")) {

                    StringBuilder tracedataMontageSB = new StringBuilder();

                    for (int i = 0; i < tracedataMontageNeuronBranch.size(); i++) {

                        String[] neuronBranchPointMapTemp = new String[4];

                        if (tracedataMontageNeuronType.get(i)[0].split(":")[0].equals("Neurite")) {
                            branchTypeIdentifier = "0";
                        } else if (tracedataMontageNeuronType.get(i)[0].split(":")[0].equals("Soma")) {
                            branchTypeIdentifier = "1";
                        } else if (tracedataMontageNeuronType.get(i)[0].split(":")[0].equals("Axon")) {
                            branchTypeIdentifier = "2";
                        } else if (tracedataMontageNeuronType.get(i)[0].split(":")[0].equals("Dendrite")) {
                            branchTypeIdentifier = "3";
                        } else if (tracedataMontageNeuronType.get(i)[0].split(":")[0].equals("Apical")) {
                            branchTypeIdentifier = "4";
                        } else if (tracedataMontageNeuronType.get(i)[0].split(":")[0].equals("Spine")) {
                            branchTypeIdentifier = "7";
                        }

                        if (tracedataMontageNeuronBranch.get(i)[0].equals("Soma")) {

                            String coordinateX = "";
                            String coordinateY = "";
                            String coordinateZ = "";
                            String radiusXYZ = "";

                            neuronBranchPointMapTemp[0] = "Soma";
                            neuronBranchPointMapTemp[1] = tracedataMontageNeuronBranch.get(i)[1].concat(":").concat(tracedataMontageNeuronBranch.get(i)[2]);
                            neuronBranchPointMapTemp[2] = ((Integer) (coordinatePointIdentifier + 1)).toString();

                            for (int j = 0; j < tracedataMontageNeuronCoordinate.get(i).length; j++) {

                                coordinatePointIdentifier++;

                                coordinateX = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][0] * imageMontageResolution[0]);
                                coordinateY = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][1] * imageMontageResolution[1]);
                                coordinateZ = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][2] * imageMontageResolution[2]);
                                radiusXYZ = String.format("%.3f", (double) tracedataMontageNeuronRadius.get(i)[j] * Math.sqrt(Math.pow(imageMontageResolution[0], 2) + Math.pow(imageMontageResolution[1], 2) + Math.pow(imageMontageResolution[2], 2)));

                                tracedataMontageSB.append(((Integer) (coordinatePointIdentifier)).toString()).append(" ").append(branchTypeIdentifier).append(" ");
                                tracedataMontageSB.append(coordinateX).append(" ").append(coordinateY).append(" ").append(coordinateZ).append(" ").append(radiusXYZ).append(" ");
                                tracedataMontageSB.append(((Integer) (-1)).toString()).append(" ").append("\r");
                            }

                            neuronBranchPointMapTemp[3] = ((Integer) (coordinatePointIdentifier)).toString();
                            neuronBranchPointMap.add(neuronBranchPointMapTemp);

                            tracedataBW.write(tracedataMontageSB.toString());
                            tracedataMontageSB = new StringBuilder();

                        } else if (tracedataMontageNeuronBranch.get(i)[0].equals("Neurite")) {

                            String coordinateX = "";
                            String coordinateY = "";
                            String coordinateZ = "";
                            String radiusXYZ = "";

                            String parentPointIdentifierStart = ((Integer) (-1)).toString();
                            String branchTypeIdentifierEnd = ((Integer) (6)).toString();

                            neuronBranchPointMapTemp[0] = "Neurite";
                            neuronBranchPointMapTemp[1] = tracedataMontageNeuronBranch.get(i)[1].concat(tracedataMontageNeuronBranch.get(i)[2]);
                            neuronBranchPointMapTemp[2] = ((Integer) (coordinatePointIdentifier + 1)).toString();

                            for (int j = 0; j < neuronBranchPointMap.size(); j++) {
                                if (neuronBranchPointMap.get(j)[0].equals("Neurite")) {
                                    if ((neuronBranchPointMap.get(j)[1].concat("-1")).equals(neuronBranchPointMapTemp[1]) ||
                                        (neuronBranchPointMap.get(j)[1].concat("-2")).equals(neuronBranchPointMapTemp[1])) {
                                        parentPointIdentifierStart = neuronBranchPointMap.get(j)[3];
                                        break;
                                    }
                                }
                            }

                            coordinatePointIdentifier++;

                            coordinateX = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[0][0] * imageMontageResolution[0]);
                            coordinateY = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[0][1] * imageMontageResolution[1]);
                            coordinateZ = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[0][2] * imageMontageResolution[2]);
                            radiusXYZ = String.format("%.3f", (double) tracedataMontageNeuronRadius.get(i)[0] * Math.sqrt(Math.pow(imageMontageResolution[0], 2) + Math.pow(imageMontageResolution[1], 2) + Math.pow(imageMontageResolution[2], 2)));

                            tracedataMontageSB.append(((Integer) (coordinatePointIdentifier)).toString()).append(" ").append(branchTypeIdentifier).append(" ");
                            tracedataMontageSB.append(coordinateX).append(" ").append(coordinateY).append(" ").append(coordinateZ).append(" ").append(radiusXYZ).append(" ");
                            tracedataMontageSB.append(parentPointIdentifierStart).append(" ").append("\r");

                            for (int j = 1; j < tracedataMontageNeuronCoordinate.get(i).length - 1; j++) {

                                coordinatePointIdentifier++;

                                coordinateX = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][0] * imageMontageResolution[0]);
                                coordinateY = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][1] * imageMontageResolution[1]);
                                coordinateZ = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][2] * imageMontageResolution[2]);
                                radiusXYZ = String.format("%.3f", (double) tracedataMontageNeuronRadius.get(i)[j] * Math.sqrt(Math.pow(imageMontageResolution[0], 2) + Math.pow(imageMontageResolution[1], 2) + Math.pow(imageMontageResolution[2], 2)));

                                tracedataMontageSB.append(((Integer) (coordinatePointIdentifier)).toString()).append(" ").append(branchTypeIdentifier).append(" ");
                                tracedataMontageSB.append(coordinateX).append(" ").append(coordinateY).append(" ").append(coordinateZ).append(" ").append(radiusXYZ).append(" ");
                                tracedataMontageSB.append(((Integer) (coordinatePointIdentifier - 1)).toString()).append(" ").append("\r");
                            }

                            for (int j = i + 1; j < tracedataMontageNeuronBranch.size(); j++) {
                                if (tracedataMontageNeuronBranch.get(j)[0].equals("Neurite")) {
                                    if ((neuronBranchPointMapTemp[1].concat("-1")).equals(tracedataMontageNeuronBranch.get(j)[1].concat(tracedataMontageNeuronBranch.get(j)[2])) ||
                                        (neuronBranchPointMapTemp[1].concat("-2")).equals(tracedataMontageNeuronBranch.get(j)[1].concat(tracedataMontageNeuronBranch.get(j)[2]))) {
                                        branchTypeIdentifierEnd = ((Integer) (5)).toString();
                                        break;
                                    }
                                }
                            }

                            coordinatePointIdentifier++;

                            coordinateX = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[tracedataMontageNeuronCoordinate.get(i).length - 1][0] * imageMontageResolution[0]);
                            coordinateY = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[tracedataMontageNeuronCoordinate.get(i).length - 1][1] * imageMontageResolution[1]);
                            coordinateZ = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[tracedataMontageNeuronCoordinate.get(i).length - 1][2] * imageMontageResolution[2]);
                            radiusXYZ = String.format("%.3f", (double) tracedataMontageNeuronRadius.get(i)[tracedataMontageNeuronRadius.get(i).length - 1] * Math.sqrt(Math.pow(imageMontageResolution[0], 2) + Math.pow(imageMontageResolution[1], 2) + Math.pow(imageMontageResolution[2], 2)));

                            tracedataMontageSB.append(((Integer) (coordinatePointIdentifier)).toString()).append(" ").append(branchTypeIdentifierEnd).append(" ");
                            tracedataMontageSB.append(coordinateX).append(" ").append(coordinateY).append(" ").append(coordinateZ).append(" ").append(radiusXYZ).append(" ");
                            tracedataMontageSB.append(((Integer) (coordinatePointIdentifier - 1)).toString()).append(" ").append("\r");

                            neuronBranchPointMapTemp[3] = ((Integer) (coordinatePointIdentifier)).toString();
                            neuronBranchPointMap.add(neuronBranchPointMapTemp);

                            tracedataBW.write(tracedataMontageSB.toString());
                            tracedataMontageSB = new StringBuilder();

                        } else if (tracedataMontageNeuronBranch.get(i)[0].equals("Spine")) {

                            String coordinateX = "";
                            String coordinateY = "";
                            String coordinateZ = "";
                            String radiusXYZ = "";

                            String parentPointIdentifierStart = ((Integer) (-1)).toString();

                            neuronBranchPointMapTemp[0] = "Spine";
                            neuronBranchPointMapTemp[1] = tracedataMontageNeuronBranch.get(i)[1];
                            neuronBranchPointMapTemp[2] = ((Integer) (coordinatePointIdentifier + 1)).toString();

                            OuterLoop:
                            for (int j = 0; j < tracedataMontageNeuronBranch.size(); j++) {
                                if (tracedataMontageNeuronBranch.get(j)[0].equals("Neurite")) {
                                    for (int k = 0; k < tracedataMontageNeuronType.get(j).length; k++) {
                                        if (tracedataMontageNeuronType.get(j)[k].contains("Spine")) {
                                            if (((tracedataMontageNeuronType.get(j)[k].split(":")[1]).split("#")[1]).equals(neuronBranchPointMapTemp[1])) {
                                                Integer parentPointIdentifierStartTemp = Integer.parseInt(neuronBranchPointMap.get(j)[2]) + k;
                                                parentPointIdentifierStart = (parentPointIdentifierStartTemp).toString();
                                                break OuterLoop;
                                            }
                                        }
                                    }
                                }
                            }

                            coordinatePointIdentifier++;

                            coordinateX = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[0][0] * imageMontageResolution[0]);
                            coordinateY = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[0][1] * imageMontageResolution[1]);
                            coordinateZ = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[0][2] * imageMontageResolution[2]);
                            radiusXYZ = String.format("%.3f", (double) tracedataMontageNeuronRadius.get(i)[0] * Math.sqrt(Math.pow(imageMontageResolution[0], 2) + Math.pow(imageMontageResolution[1], 2) + Math.pow(imageMontageResolution[2], 2)));

                            tracedataMontageSB.append(((Integer) (coordinatePointIdentifier)).toString()).append(" ").append(branchTypeIdentifier).append(" ");
                            tracedataMontageSB.append(coordinateX).append(" ").append(coordinateY).append(" ").append(coordinateZ).append(" ").append(radiusXYZ).append(" ");
                            tracedataMontageSB.append(parentPointIdentifierStart).append(" ").append("\r");

                            for (int j = 1; j < tracedataMontageNeuronCoordinate.get(i).length - 1; j++) {

                                coordinatePointIdentifier++;

                                coordinateX = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][0] * imageMontageResolution[0]);
                                coordinateY = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][1] * imageMontageResolution[1]);
                                coordinateZ = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][2] * imageMontageResolution[2]);
                                radiusXYZ = String.format("%.3f", (double) tracedataMontageNeuronRadius.get(i)[j] * Math.sqrt(Math.pow(imageMontageResolution[0], 2) + Math.pow(imageMontageResolution[1], 2) + Math.pow(imageMontageResolution[2], 2)));

                                tracedataMontageSB.append(((Integer) (coordinatePointIdentifier)).toString()).append(" ").append(branchTypeIdentifier).append(" ");
                                tracedataMontageSB.append(coordinateX).append(" ").append(coordinateY).append(" ").append(coordinateZ).append(" ").append(radiusXYZ).append(" ");
                                tracedataMontageSB.append(((Integer) (coordinatePointIdentifier - 1)).toString()).append(" ").append("\r");
                            }

                            coordinatePointIdentifier++;

                            coordinateX = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[tracedataMontageNeuronCoordinate.get(i).length - 1][0] * imageMontageResolution[0]);
                            coordinateY = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[tracedataMontageNeuronCoordinate.get(i).length - 1][1] * imageMontageResolution[1]);
                            coordinateZ = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[tracedataMontageNeuronCoordinate.get(i).length - 1][2] * imageMontageResolution[2]);
                            radiusXYZ = String.format("%.3f", (double) tracedataMontageNeuronRadius.get(i)[tracedataMontageNeuronRadius.get(i).length - 1] * Math.sqrt(Math.pow(imageMontageResolution[0], 2) + Math.pow(imageMontageResolution[1], 2) + Math.pow(imageMontageResolution[2], 2)));

                            tracedataMontageSB.append(((Integer) (coordinatePointIdentifier)).toString()).append(" ").append(((Integer) (6)).toString()).append(" ");
                            tracedataMontageSB.append(coordinateX).append(" ").append(coordinateY).append(" ").append(coordinateZ).append(" ").append(radiusXYZ).append(" ");
                            tracedataMontageSB.append(((Integer) (coordinatePointIdentifier - 1)).toString()).append(" ").append("\r");

                            neuronBranchPointMapTemp[3] = ((Integer) (coordinatePointIdentifier)).toString();
                            neuronBranchPointMap.add(neuronBranchPointMapTemp);

                            tracedataBW.write(tracedataMontageSB.toString());
                            tracedataMontageSB = new StringBuilder();
                        }
                    }

                } else if (tracedataMontageStatus.equals("extended")) {

                    StringBuilder tracedataMontageSB = new StringBuilder();

                    for (int i = 0; i < tracedataMontageNeuronBranch.size(); i++) {

                        String[] neuronBranchPointMapTemp = new String[4];

                        if (tracedataMontageNeuronType.get(i)[0].split(":")[0].equals("Neurite")) {
                            branchTypeIdentifier = "0";
                        } else if (tracedataMontageNeuronType.get(i)[0].split(":")[0].equals("Soma")) {
                            branchTypeIdentifier = "1";
                        } else if (tracedataMontageNeuronType.get(i)[0].split(":")[0].equals("Axon")) {
                            branchTypeIdentifier = "2";
                        } else if (tracedataMontageNeuronType.get(i)[0].split(":")[0].equals("Dendrite")) {
                            branchTypeIdentifier = "3";
                        } else if (tracedataMontageNeuronType.get(i)[0].split(":")[0].equals("Apical")) {
                            branchTypeIdentifier = "4";
                        } else if (tracedataMontageNeuronType.get(i)[0].split(":")[0].equals("Spine")) {
                            branchTypeIdentifier = "7";
                        }

                        if (tracedataMontageNeuronBranch.get(i)[0].equals("Soma")) {

                            String coordinateX = "";
                            String coordinateY = "";
                            String coordinateZ = "";
                            String radiusXYZ = "";

                            neuronBranchPointMapTemp[0] = "Soma";
                            neuronBranchPointMapTemp[1] = tracedataMontageNeuronBranch.get(i)[1].concat(":").concat(tracedataMontageNeuronBranch.get(i)[2]);
                            neuronBranchPointMapTemp[2] = ((Integer) (coordinatePointIdentifier + 1)).toString();

                            for (int j = 0; j < tracedataMontageNeuronCoordinate.get(i).length; j++) {

                                coordinatePointIdentifier++;

                                coordinateX = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][0] * imageMontageResolution[0]);
                                coordinateY = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][1] * imageMontageResolution[1]);
                                coordinateZ = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][2] * imageMontageResolution[2]);
                                radiusXYZ = String.format("%.3f", (double) tracedataMontageNeuronRadius.get(i)[j] * Math.sqrt(Math.pow(imageMontageResolution[0], 2) + Math.pow(imageMontageResolution[1], 2) + Math.pow(imageMontageResolution[2], 2)));

                                tracedataMontageSB.append(((Integer) (coordinatePointIdentifier)).toString()).append(" ").append(branchTypeIdentifier).append(" ");
                                tracedataMontageSB.append(coordinateX).append(" ").append(coordinateY).append(" ").append(coordinateZ).append(" ").append(radiusXYZ).append(" ");
                                tracedataMontageSB.append(((Integer) (-1)).toString()).append(" ");
                                tracedataMontageSB.append(((Integer) (tracedataMontageNeuronSynapse.get(i)[j])).toString()).append("\r");
                            }

                            neuronBranchPointMapTemp[3] = ((Integer) (coordinatePointIdentifier)).toString();
                            neuronBranchPointMap.add(neuronBranchPointMapTemp);

                            tracedataBW.write(tracedataMontageSB.toString());
                            tracedataMontageSB = new StringBuilder();

                        } else if (tracedataMontageNeuronBranch.get(i)[0].equals("Neurite")) {

                            String coordinateX = "";
                            String coordinateY = "";
                            String coordinateZ = "";
                            String radiusXYZ = "";

                            String parentPointIdentifierStart = ((Integer) (-1)).toString();
                            String branchTypeIdentifierEnd = ((Integer) (6)).toString();

                            neuronBranchPointMapTemp[0] = "Neurite";
                            neuronBranchPointMapTemp[1] = tracedataMontageNeuronBranch.get(i)[1].concat(tracedataMontageNeuronBranch.get(i)[2]);
                            neuronBranchPointMapTemp[2] = ((Integer) (coordinatePointIdentifier + 1)).toString();

                            for (int j = 0; j < neuronBranchPointMap.size(); j++) {
                                if (neuronBranchPointMap.get(j)[0].equals("Neurite")) {
                                    if ((neuronBranchPointMap.get(j)[1].concat("-1")).equals(neuronBranchPointMapTemp[1]) ||
                                        (neuronBranchPointMap.get(j)[1].concat("-2")).equals(neuronBranchPointMapTemp[1])) {
                                        parentPointIdentifierStart = neuronBranchPointMap.get(j)[3];
                                        break;
                                    }
                                }
                            }

                            coordinatePointIdentifier++;

                            coordinateX = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[0][0] * imageMontageResolution[0]);
                            coordinateY = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[0][1] * imageMontageResolution[1]);
                            coordinateZ = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[0][2] * imageMontageResolution[2]);
                            radiusXYZ = String.format("%.3f", (double) tracedataMontageNeuronRadius.get(i)[0] * Math.sqrt(Math.pow(imageMontageResolution[0], 2) + Math.pow(imageMontageResolution[1], 2) + Math.pow(imageMontageResolution[2], 2)));

                            tracedataMontageSB.append(((Integer) (coordinatePointIdentifier)).toString()).append(" ").append(branchTypeIdentifier).append(" ");
                            tracedataMontageSB.append(coordinateX).append(" ").append(coordinateY).append(" ").append(coordinateZ).append(" ").append(radiusXYZ).append(" ");
                            tracedataMontageSB.append(parentPointIdentifierStart).append(" ");
                            tracedataMontageSB.append(((Integer) (tracedataMontageNeuronSynapse.get(i)[0])).toString()).append("\r");

                            for (int j = 1; j < tracedataMontageNeuronCoordinate.get(i).length - 1; j++) {

                                coordinatePointIdentifier++;

                                coordinateX = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][0] * imageMontageResolution[0]);
                                coordinateY = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][1] * imageMontageResolution[1]);
                                coordinateZ = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][2] * imageMontageResolution[2]);
                                radiusXYZ = String.format("%.3f", (double) tracedataMontageNeuronRadius.get(i)[j] * Math.sqrt(Math.pow(imageMontageResolution[0], 2) + Math.pow(imageMontageResolution[1], 2) + Math.pow(imageMontageResolution[2], 2)));

                                tracedataMontageSB.append(((Integer) (coordinatePointIdentifier)).toString()).append(" ").append(branchTypeIdentifier).append(" ");
                                tracedataMontageSB.append(coordinateX).append(" ").append(coordinateY).append(" ").append(coordinateZ).append(" ").append(radiusXYZ).append(" ");
                                tracedataMontageSB.append(((Integer) (coordinatePointIdentifier - 1)).toString()).append(" ");
                                tracedataMontageSB.append(((Integer) (tracedataMontageNeuronSynapse.get(i)[j])).toString()).append("\r");
                            }

                            for (int j = i + 1; j < tracedataMontageNeuronBranch.size(); j++) {
                                if (tracedataMontageNeuronBranch.get(j)[0].equals("Neurite")) {
                                    if ((neuronBranchPointMapTemp[1].concat("-1")).equals(tracedataMontageNeuronBranch.get(j)[1].concat(tracedataMontageNeuronBranch.get(j)[2])) ||
                                        (neuronBranchPointMapTemp[1].concat("-2")).equals(tracedataMontageNeuronBranch.get(j)[1].concat(tracedataMontageNeuronBranch.get(j)[2]))) {
                                        branchTypeIdentifierEnd = ((Integer) (5)).toString();
                                        break;
                                    }
                                }
                            }

                            coordinatePointIdentifier++;

                            coordinateX = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[tracedataMontageNeuronCoordinate.get(i).length - 1][0] * imageMontageResolution[0]);
                            coordinateY = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[tracedataMontageNeuronCoordinate.get(i).length - 1][1] * imageMontageResolution[1]);
                            coordinateZ = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[tracedataMontageNeuronCoordinate.get(i).length - 1][2] * imageMontageResolution[2]);
                            radiusXYZ = String.format("%.3f", (double) tracedataMontageNeuronRadius.get(i)[tracedataMontageNeuronRadius.get(i).length - 1] * Math.sqrt(Math.pow(imageMontageResolution[0], 2) + Math.pow(imageMontageResolution[1], 2) + Math.pow(imageMontageResolution[2], 2)));

                            tracedataMontageSB.append(((Integer) (coordinatePointIdentifier)).toString()).append(" ").append(branchTypeIdentifierEnd).append(" ");
                            tracedataMontageSB.append(coordinateX).append(" ").append(coordinateY).append(" ").append(coordinateZ).append(" ").append(radiusXYZ).append(" ");
                            tracedataMontageSB.append(((Integer) (coordinatePointIdentifier - 1)).toString()).append(" ");
                            tracedataMontageSB.append(((Integer) (tracedataMontageNeuronSynapse.get(i)[tracedataMontageNeuronSynapse.get(i).length - 1])).toString()).append("\r");

                            neuronBranchPointMapTemp[3] = ((Integer) (coordinatePointIdentifier)).toString();
                            neuronBranchPointMap.add(neuronBranchPointMapTemp);

                            tracedataBW.write(tracedataMontageSB.toString());
                            tracedataMontageSB = new StringBuilder();

                        } else if (tracedataMontageNeuronBranch.get(i)[0].equals("Spine")) {

                            String coordinateX = "";
                            String coordinateY = "";
                            String coordinateZ = "";
                            String radiusXYZ = "";

                            String parentPointIdentifierStart = ((Integer) (-1)).toString();

                            neuronBranchPointMapTemp[0] = "Spine";
                            neuronBranchPointMapTemp[1] = tracedataMontageNeuronBranch.get(i)[1];
                            neuronBranchPointMapTemp[2] = ((Integer) (coordinatePointIdentifier + 1)).toString();

                            OuterLoop:
                            for (int j = 0; j < tracedataMontageNeuronBranch.size(); j++) {
                                if (tracedataMontageNeuronBranch.get(j)[0].equals("Neurite")) {
                                    for (int k = 0; k < tracedataMontageNeuronType.get(j).length; k++) {
                                        if (tracedataMontageNeuronType.get(j)[k].contains("Spine")) {
                                            if (((tracedataMontageNeuronType.get(j)[k].split(":")[1]).split("#")[1]).equals(neuronBranchPointMapTemp[1])) {
                                                Integer parentPointIdentifierStartTemp = Integer.parseInt(neuronBranchPointMap.get(j)[2]) + k;
                                                parentPointIdentifierStart = (parentPointIdentifierStartTemp).toString();
                                                break OuterLoop;
                                            }
                                        }
                                    }
                                }
                            }

                            coordinatePointIdentifier++;

                            coordinateX = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[0][0] * imageMontageResolution[0]);
                            coordinateY = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[0][1] * imageMontageResolution[1]);
                            coordinateZ = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[0][2] * imageMontageResolution[2]);
                            radiusXYZ = String.format("%.3f", (double) tracedataMontageNeuronRadius.get(i)[0] * Math.sqrt(Math.pow(imageMontageResolution[0], 2) + Math.pow(imageMontageResolution[1], 2) + Math.pow(imageMontageResolution[2], 2)));

                            tracedataMontageSB.append(((Integer) (coordinatePointIdentifier)).toString()).append(" ").append(branchTypeIdentifier).append(" ");
                            tracedataMontageSB.append(coordinateX).append(" ").append(coordinateY).append(" ").append(coordinateZ).append(" ").append(radiusXYZ).append(" ");
                            tracedataMontageSB.append(parentPointIdentifierStart).append(" ");
                            tracedataMontageSB.append(((Integer) (tracedataMontageNeuronSynapse.get(i)[0])).toString()).append("\r");

                            for (int j = 1; j < tracedataMontageNeuronCoordinate.get(i).length - 1; j++) {

                                coordinatePointIdentifier++;

                                coordinateX = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][0] * imageMontageResolution[0]);
                                coordinateY = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][1] * imageMontageResolution[1]);
                                coordinateZ = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[j][2] * imageMontageResolution[2]);
                                radiusXYZ = String.format("%.3f", (double) tracedataMontageNeuronRadius.get(i)[j] * Math.sqrt(Math.pow(imageMontageResolution[0], 2) + Math.pow(imageMontageResolution[1], 2) + Math.pow(imageMontageResolution[2], 2)));

                                tracedataMontageSB.append(((Integer) (coordinatePointIdentifier)).toString()).append(" ").append(branchTypeIdentifier).append(" ");
                                tracedataMontageSB.append(coordinateX).append(" ").append(coordinateY).append(" ").append(coordinateZ).append(" ").append(radiusXYZ).append(" ");
                                tracedataMontageSB.append(((Integer) (coordinatePointIdentifier - 1)).toString()).append(" ");
                                tracedataMontageSB.append(((Integer) (tracedataMontageNeuronSynapse.get(i)[j])).toString()).append("\r");
                            }

                            coordinatePointIdentifier++;

                            coordinateX = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[tracedataMontageNeuronCoordinate.get(i).length - 1][0] * imageMontageResolution[0]);
                            coordinateY = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[tracedataMontageNeuronCoordinate.get(i).length - 1][1] * imageMontageResolution[1]);
                            coordinateZ = String.format("%.3f", (double) tracedataMontageNeuronCoordinate.get(i)[tracedataMontageNeuronCoordinate.get(i).length - 1][2] * imageMontageResolution[2]);
                            radiusXYZ = String.format("%.3f", (double) tracedataMontageNeuronRadius.get(i)[tracedataMontageNeuronRadius.get(i).length - 1] * Math.sqrt(Math.pow(imageMontageResolution[0], 2) + Math.pow(imageMontageResolution[1], 2) + Math.pow(imageMontageResolution[2], 2)));

                            tracedataMontageSB.append(((Integer) (coordinatePointIdentifier)).toString()).append(" ").append(((Integer) (6)).toString()).append(" ");
                            tracedataMontageSB.append(coordinateX).append(" ").append(coordinateY).append(" ").append(coordinateZ).append(" ").append(radiusXYZ).append(" ");
                            tracedataMontageSB.append(((Integer) (coordinatePointIdentifier - 1)).toString()).append(" ");
                            tracedataMontageSB.append(((Integer) (tracedataMontageNeuronSynapse.get(i)[tracedataMontageNeuronSynapse.get(i).length - 1])).toString()).append("\r");

                            neuronBranchPointMapTemp[3] = ((Integer) (coordinatePointIdentifier)).toString();
                            neuronBranchPointMap.add(neuronBranchPointMapTemp);

                            tracedataBW.write(tracedataMontageSB.toString());
                            tracedataMontageSB = new StringBuilder();
                        }
                    }
                }

                tracedataBW.close();
                tracedataOSW.close();
                tracedataFOS.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
