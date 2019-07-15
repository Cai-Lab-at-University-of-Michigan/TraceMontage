/* COPYRIGHT Creative Commons Attribution-Non Commercial 4.0 International (CC BY-NC 4.0) */

/**
 * A class for modifying the coordinate, type, radius, synapse, and connection of an overlapped branch.
 *
 * @author Aslan Satary Dizaji <asataryd@umich.edu>
 * <https://sites.google.com/a/umich.edu/aslansdizaji>
 * <https://github.com/aslansd/TraceMontage>
 * @version 1.1 Created on 2015/05 (last modified on 2018/12)
 */

public class ModifyBranchAllProperties {

    private Integer[][] modifyBranchCoordinate1;
    private String[] modifyBranchType1;
    private Integer[] modifyBranchRadius1;
    private Integer[] modifyBranchSynapse1;
    private String[] modifyBranchConnection1;

    public ModifyBranchAllProperties(Integer[][] coordinateBranch1, Integer[][] coordinateBranch2, String[] typeBranch1, String[] typeBranch2, Integer[] radiusBranch1, Integer[] radiusBranch2, Integer[] synapseBranch1, Integer[] synapseBranch2, String[] connectionBranch1, String[] connectionBranch2, int flag1, int flag2, int flag3, int flag4, int indexStart1, int indexStart2, int indexEnd1, int indexEnd2) {

        if (flag1 == 1 && flag2 == 2) {

            modifyBranchCoordinate1 = coordinateBranch1;

            for (int i = 0; i < typeBranch1.length; i++) {
                if (typeBranch1[i].contains(":")) {
                    typeBranch1[i] = typeBranch1[i].concat(":data1");
                }
            }

            modifyBranchType1 = typeBranch1;
            modifyBranchRadius1 = radiusBranch1;
            modifyBranchSynapse1 = synapseBranch1;

            for (int i = 0; i < connectionBranch1.length; i++) {
                if (! connectionBranch1[i].equals("0")) {
                    connectionBranch1[i] = connectionBranch1[i].concat("#data1");
                }
            }

            modifyBranchConnection1 = connectionBranch1;

        } else if (flag1 == 1 && flag2 == 0) {

            IntegerCoordinateLine integerCoordinateLineInstance = new IntegerCoordinateLine(coordinateBranch1[indexEnd1], coordinateBranch2[indexEnd2]);
            Integer[][] coordinateSegment = integerCoordinateLineInstance.getIntegerCoordinateLine();

            int coordinateSegmentSize = coordinateSegment.length - 2;
            if( coordinateSegmentSize < 0 ) coordinateSegmentSize = 0;
            
            String[] typeSegment = new String[coordinateSegmentSize];
            Integer[] radiusSegment = new Integer[coordinateSegmentSize];
            Integer[] synapseSegment = new Integer[coordinateSegmentSize];
            String[] connectionSegment = new String[coordinateSegmentSize];

            for (int i = 0; i < coordinateSegment.length - 2; i++) {
                typeSegment[i] = typeBranch1[0].split(":")[0];
                radiusSegment[i] = 0;
                synapseSegment[i] = 0;
                connectionSegment[i] = "0";
            }

            if (indexStart2 <= indexEnd2) {
                int coordinateBranchSize = indexEnd1 + coordinateSegment.length + coordinateBranch2.length - indexEnd2 - 1;

                Integer[][] modifyBranchCoordinateTemp1 = new Integer[coordinateBranchSize][3];
                System.arraycopy(coordinateBranch1, 0, modifyBranchCoordinateTemp1, 0, indexEnd1);
                System.arraycopy(coordinateSegment, 0, modifyBranchCoordinateTemp1, indexEnd1, coordinateSegment.length);
                System.arraycopy(coordinateBranch2, indexEnd2 + 1, modifyBranchCoordinateTemp1, indexEnd1 + coordinateSegment.length, coordinateBranch2.length - indexEnd2 - 1);
                modifyBranchCoordinate1 = modifyBranchCoordinateTemp1;

                for (int i = 0; i < typeBranch1.length; i++) {
                    if (typeBranch1[i].contains(":")) {
                        typeBranch1[i] = typeBranch1[i].concat(":data1");
                    }
                }

                for (int i = 0; i < typeBranch2.length; i++) {
                    if (typeBranch2[i].contains(":")) {
                        typeBranch2[i] = typeBranch1[0].split(":")[0].concat(typeBranch2[i].split(":")[1]).concat(":data2");
                    } else {
                        typeBranch2[i] = typeBranch1[0].split(":")[0];
                    }
                }

                String[] modifyBranchTypeTemp1 = new String[coordinateBranchSize];
                System.arraycopy(typeBranch1, 0, modifyBranchTypeTemp1, 0, indexEnd1 + 1);
                System.arraycopy(typeSegment, 0, modifyBranchTypeTemp1, indexEnd1 + 1, typeSegment.length);
                System.arraycopy(typeBranch2, indexEnd2, modifyBranchTypeTemp1, indexEnd1 + typeSegment.length + 1, typeBranch2.length - indexEnd2);
                modifyBranchType1 = modifyBranchTypeTemp1;

                Integer[] modifyBranchRadiusTemp1 = new Integer[coordinateBranchSize];
                System.arraycopy(radiusBranch1, 0, modifyBranchRadiusTemp1, 0, indexEnd1 + 1);
                System.arraycopy(radiusSegment, 0, modifyBranchRadiusTemp1, indexEnd1 + 1, radiusSegment.length);
                System.arraycopy(radiusBranch2, indexEnd2, modifyBranchRadiusTemp1, indexEnd1 + radiusSegment.length + 1, radiusBranch2.length - indexEnd2);
                modifyBranchRadius1 = modifyBranchRadiusTemp1;

                Integer[] modifyBranchSynapseTemp1 = new Integer[coordinateBranchSize];
                System.arraycopy(synapseBranch1, 0, modifyBranchSynapseTemp1, 0, indexEnd1 + 1);
                System.arraycopy(synapseSegment, 0, modifyBranchSynapseTemp1, indexEnd1 + 1, synapseSegment.length);
                System.arraycopy(synapseBranch2, indexEnd2, modifyBranchSynapseTemp1, indexEnd1 + synapseSegment.length + 1, synapseBranch2.length - indexEnd2);
                modifyBranchSynapse1 = modifyBranchSynapseTemp1;

                for (int i = 0; i < connectionBranch1.length; i++) {
                    if (! connectionBranch1[i].equals("0")) {
                        connectionBranch1[i] = connectionBranch1[i].concat("#data1");
                    }
                }

                for (int i = 0; i < connectionBranch2.length; i++) {
                    if (! connectionBranch2[i].equals("0")) {
                        connectionBranch2[i] = connectionBranch2[i].concat("#data2");
                    }
                }

                String[] modifyBranchConnectionTemp1 = new String[coordinateBranchSize];
                System.arraycopy(connectionBranch1, 0, modifyBranchConnectionTemp1, 0, indexEnd1 + 1);
                System.arraycopy(connectionSegment, 0, modifyBranchConnectionTemp1, indexEnd1 + 1, connectionSegment.length);
                System.arraycopy(connectionBranch2, indexEnd2, modifyBranchConnectionTemp1, indexEnd1 + connectionSegment.length + 1, connectionBranch2.length - indexEnd2);
                modifyBranchConnection1 = modifyBranchConnectionTemp1;
            } else {
                int coordinateBranchSize = indexEnd1 + coordinateSegment.length + indexEnd2;

                Integer[][] modifyBranchCoordinateTemp1 = new Integer[coordinateBranchSize][3];
                String[] modifyBranchTypeTemp1 = new String[coordinateBranchSize];
                Integer[] modifyBranchRadiusTemp1 = new Integer[coordinateBranchSize];
                Integer[] modifyBranchSynapseTemp1 = new Integer[coordinateBranchSize];
                String[] modifyBranchConnectionTemp1 = new String[coordinateBranchSize];

                Integer[][] modifyBranchCoordinateTemp2 = new Integer[indexEnd2][3];
                String[] modifyBranchTypeTemp2 = new String[indexEnd2 + 1];
                Integer[] modifyBranchRadiusTemp2 = new Integer[indexEnd2 + 1];
                Integer[] modifyBranchSynapseTemp2 = new Integer[indexEnd2 + 1];
                String[] modifyBranchConnectionTemp2 = new String[indexEnd2 + 1];

                modifyBranchTypeTemp2[0] = typeBranch2[indexEnd2];
                modifyBranchRadiusTemp2[0] = radiusBranch2[indexEnd2];
                modifyBranchSynapseTemp2[0] = synapseBranch2[indexEnd2];
                modifyBranchConnectionTemp2[0] = connectionBranch2[indexEnd2];

                for (int i = 0; i < indexEnd2; i++) {
                    modifyBranchCoordinateTemp2[i] = coordinateBranch2[indexEnd2 - i - 1];
                    modifyBranchTypeTemp2[i + 1] = typeBranch2[indexEnd2 - i - 1];
                    modifyBranchRadiusTemp2[i + 1] = radiusBranch2[indexEnd2 - i - 1];
                    modifyBranchSynapseTemp2[i + 1] = synapseBranch2[indexEnd2 - i - 1];
                    modifyBranchConnectionTemp2[i + 1] = connectionBranch2[indexEnd2 - i - 1];
                }

                System.arraycopy(coordinateBranch1, 0, modifyBranchCoordinateTemp1, 0, indexEnd1);
                System.arraycopy(coordinateSegment, 0, modifyBranchCoordinateTemp1, indexEnd1, coordinateSegment.length);
                System.arraycopy(modifyBranchCoordinateTemp2, 0, modifyBranchCoordinateTemp1, indexEnd1 + coordinateSegment.length, indexEnd2);
                modifyBranchCoordinate1 = modifyBranchCoordinateTemp1;

                for (int i = 0; i < typeBranch1.length; i++) {
                    if (typeBranch1[i].contains(":")) {
                        typeBranch1[i] = typeBranch1[i].concat(":data1");
                    }
                }

                for (int i = 0; i < modifyBranchTypeTemp2.length; i++) {
                    if (modifyBranchTypeTemp2[i].contains(":")) {
                        modifyBranchTypeTemp2[i] = typeBranch1[0].split(":")[0].concat(modifyBranchTypeTemp2[i].split(":")[1]).concat(":data2");
                    } else {
                        modifyBranchTypeTemp2[i] = typeBranch1[0].split(":")[0];
                    }
                }

                System.arraycopy(typeBranch1, 0, modifyBranchTypeTemp1, 0, indexEnd1 + 1);
                System.arraycopy(typeSegment, 0, modifyBranchTypeTemp1, indexEnd1 + 1, typeSegment.length);
                System.arraycopy(modifyBranchTypeTemp2, 0, modifyBranchTypeTemp1, indexEnd1 + typeSegment.length + 1, indexEnd2 + 1);
                modifyBranchType1 = modifyBranchTypeTemp1;

                System.arraycopy(radiusBranch1, 0, modifyBranchRadiusTemp1, 0, indexEnd1 + 1);
                System.arraycopy(radiusSegment, 0, modifyBranchRadiusTemp1, indexEnd1 + 1, radiusSegment.length);
                System.arraycopy(modifyBranchRadiusTemp2, 0, modifyBranchRadiusTemp1, indexEnd1 + radiusSegment.length + 1, indexEnd2 + 1);
                modifyBranchRadius1 = modifyBranchRadiusTemp1;

                System.arraycopy(synapseBranch1, 0, modifyBranchSynapseTemp1, 0, indexEnd1 + 1);
                System.arraycopy(synapseSegment, 0, modifyBranchSynapseTemp1, indexEnd1 + 1, synapseSegment.length);
                System.arraycopy(modifyBranchSynapseTemp2, 0, modifyBranchSynapseTemp1, indexEnd1 + synapseSegment.length + 1, indexEnd2 + 1);
                modifyBranchSynapse1 = modifyBranchSynapseTemp1;

                for (int i = 0; i < connectionBranch1.length; i++) {
                    if (! connectionBranch1[i].equals("0")) {
                        connectionBranch1[i] = connectionBranch1[i].concat("#data1");
                    }
                }

                for (int i = 0; i < modifyBranchConnectionTemp2.length; i++) {
                    if (! modifyBranchConnectionTemp2[i].equals("0")) {
                        modifyBranchConnectionTemp2[i] = modifyBranchConnectionTemp2[i].concat("#data2");
                    }
                }

                System.arraycopy(connectionBranch1, 0, modifyBranchConnectionTemp1, 0, indexEnd1 + 1);
                System.arraycopy(connectionSegment, 0, modifyBranchConnectionTemp1, indexEnd1 + 1, connectionSegment.length);
                System.arraycopy(modifyBranchConnectionTemp2, 0, modifyBranchConnectionTemp1, indexEnd1 + connectionSegment.length + 1, indexEnd2 + 1);
                modifyBranchConnection1 = modifyBranchConnectionTemp1;
            }

        } else if (flag1 == 0 && flag2 == 2) {

            IntegerCoordinateLine integerCoordinateLineInstance = new IntegerCoordinateLine(coordinateBranch2[indexStart2], coordinateBranch1[indexStart1]);
            Integer[][] coordinateSegment = integerCoordinateLineInstance.getIntegerCoordinateLine();

            String[] typeSegment = new String[coordinateSegment.length - 2];
            Integer[] radiusSegment = new Integer[coordinateSegment.length - 2];
            Integer[] synapseSegment = new Integer[coordinateSegment.length - 2];
            String[] connectionSegment = new String[coordinateSegment.length - 2];

            for (int i = 0; i < coordinateSegment.length - 2; i++) {
                typeSegment[i] = typeBranch1[0].split(":")[0];
                radiusSegment[i] = 0;
                synapseSegment[i] = 0;
                connectionSegment[i] = "0";
            }

            if (indexStart2 <= indexEnd2) {
                int coordinateBranchSize = coordinateBranch1.length - indexStart1 + coordinateSegment.length + indexStart2 - 1;

                Integer[][] modifyBranchCoordinateTemp1 = new Integer[coordinateBranchSize][3];
                System.arraycopy(coordinateBranch2, 0, modifyBranchCoordinateTemp1, 0, indexStart2);
                System.arraycopy(coordinateSegment, 0, modifyBranchCoordinateTemp1, indexStart2, coordinateSegment.length);
                System.arraycopy(coordinateBranch1, indexStart1 + 1, modifyBranchCoordinateTemp1, indexStart2 + coordinateSegment.length, coordinateBranch1.length - indexStart1 - 1);
                modifyBranchCoordinate1 = modifyBranchCoordinateTemp1;

                for (int i = 0; i < typeBranch1.length; i++) {
                    if (typeBranch1[i].contains(":")) {
                        typeBranch1[i] = typeBranch1[i].concat(":data1");
                    }
                }

                for (int i = 0; i < typeBranch2.length; i++) {
                    if (typeBranch2[i].contains(":")) {
                        typeBranch2[i] = typeBranch1[0].split(":")[0].concat(typeBranch2[i].split(":")[1]).concat(":data2");
                    } else {
                        typeBranch2[i] = typeBranch1[0].split(":")[0];
                    }
                }

                String[] modifyBranchTypeTemp1 = new String[coordinateBranchSize];
                System.arraycopy(typeBranch2, 0, modifyBranchTypeTemp1, 0, indexStart2 + 1);
                System.arraycopy(typeSegment, 0, modifyBranchTypeTemp1, indexStart2 + 1, typeSegment.length);
                System.arraycopy(typeBranch1, indexStart1, modifyBranchTypeTemp1, indexStart2 + typeSegment.length + 1, typeBranch1.length - indexStart1);
                modifyBranchType1 = modifyBranchTypeTemp1;

                Integer[] modifyBranchRadiusTemp1 = new Integer[coordinateBranchSize];
                System.arraycopy(radiusBranch2, 0, modifyBranchRadiusTemp1, 0, indexStart2 + 1);
                System.arraycopy(radiusSegment, 0, modifyBranchRadiusTemp1, indexStart2 + 1, radiusSegment.length);
                System.arraycopy(radiusBranch1, indexStart1, modifyBranchRadiusTemp1, indexStart2 + radiusSegment.length + 1, radiusBranch1.length - indexStart1);
                modifyBranchRadius1 = modifyBranchRadiusTemp1;

                Integer[] modifyBranchSynapseTemp1 = new Integer[coordinateBranchSize];
                System.arraycopy(synapseBranch2, 0, modifyBranchSynapseTemp1, 0, indexStart2 + 1);
                System.arraycopy(synapseSegment, 0, modifyBranchSynapseTemp1, indexStart2 + 1, synapseSegment.length);
                System.arraycopy(synapseBranch1, indexStart1, modifyBranchSynapseTemp1, indexStart2 + synapseSegment.length + 1, synapseBranch1.length - indexStart1);
                modifyBranchSynapse1 = modifyBranchSynapseTemp1;

                for (int i = 0; i < connectionBranch1.length; i++) {
                    if (! connectionBranch1[i].equals("0")) {
                        connectionBranch1[i] = connectionBranch1[i].concat("#data1");
                    }
                }

                for (int i = 0; i < connectionBranch2.length; i++) {
                    if (! connectionBranch2[i].equals("0")) {
                        connectionBranch2[i] = connectionBranch2[i].concat("#data2");
                    }
                }

                String[] modifyBranchConnectionTemp1 = new String[coordinateBranchSize];
                System.arraycopy(connectionBranch2, 0, modifyBranchConnectionTemp1, 0, indexStart2 + 1);
                System.arraycopy(connectionSegment, 0, modifyBranchConnectionTemp1, indexStart2 + 1, connectionSegment.length);
                System.arraycopy(connectionBranch1, indexStart1, modifyBranchConnectionTemp1, indexStart2 + connectionSegment.length + 1, connectionBranch1.length - indexStart1);
                modifyBranchConnection1 = modifyBranchConnectionTemp1;
            } else {
                int coordinateBranchSize = coordinateBranch1.length - indexStart1 + coordinateSegment.length + coordinateBranch2.length - indexStart2 - 2;

                Integer[][] modifyBranchCoordinateTemp1 = new Integer[coordinateBranchSize][3];
                String[] modifyBranchTypeTemp1 = new String[coordinateBranchSize];
                Integer[] modifyBranchRadiusTemp1 = new Integer[coordinateBranchSize];
                Integer[] modifyBranchSynapseTemp1 = new Integer[coordinateBranchSize];
                String[] modifyBranchConnectionTemp1 = new String[coordinateBranchSize];

                Integer[][] modifyBranchCoordinateTemp2 = new Integer[coordinateBranch2.length - indexStart2 - 1][3];
                String[] modifyBranchTypeTemp2 = new String[coordinateBranch2.length - indexStart2];
                Integer[] modifyBranchRadiusTemp2 = new Integer[coordinateBranch2.length - indexStart2];
                Integer[] modifyBranchSynapseTemp2 = new Integer[coordinateBranch2.length - indexStart2];
                String[] modifyBranchConnectionTemp2 = new String[coordinateBranch2.length - indexStart2];

                modifyBranchTypeTemp2[modifyBranchTypeTemp2.length - 1] = typeBranch2[indexStart2];
                modifyBranchRadiusTemp2[modifyBranchRadiusTemp2.length - 1] = radiusBranch2[indexStart2];
                modifyBranchSynapseTemp2[modifyBranchSynapseTemp2.length - 1] = synapseBranch2[indexStart2];
                modifyBranchConnectionTemp2[modifyBranchConnectionTemp2.length - 1] = connectionBranch2[indexStart2];

                for (int i = 0; i < coordinateBranch2.length - indexStart2 - 1; i++) {
                    modifyBranchCoordinateTemp2[i] = coordinateBranch2[coordinateBranch2.length - i - 1];
                    modifyBranchTypeTemp2[i] = typeBranch2[typeBranch2.length - i - 1];
                    modifyBranchRadiusTemp2[i] = radiusBranch2[radiusBranch2.length - i - 1];
                    modifyBranchSynapseTemp2[i] = synapseBranch2[synapseBranch2.length - i - 1];
                    modifyBranchConnectionTemp2[i] = connectionBranch2[connectionBranch2.length - i - 1];
                }

                System.arraycopy(modifyBranchCoordinateTemp2, 0, modifyBranchCoordinateTemp1, 0, coordinateBranch2.length - indexStart2 - 1);
                System.arraycopy(coordinateSegment, 0, modifyBranchCoordinateTemp1, coordinateBranch2.length - indexStart2 - 1, coordinateSegment.length);
                System.arraycopy(coordinateBranch1, indexStart1 + 1, modifyBranchCoordinateTemp1, coordinateBranch2.length - indexStart2 + coordinateSegment.length - 1, coordinateBranch1.length - indexStart1 - 1);
                modifyBranchCoordinate1 = modifyBranchCoordinateTemp1;

                for (int i = 0; i < typeBranch1.length; i++) {
                    if (typeBranch1[i].contains(":")) {
                        typeBranch1[i] = typeBranch1[i].concat(":data1");
                    }
                }

                for (int i = 0; i < modifyBranchTypeTemp2.length; i++) {
                    if (modifyBranchTypeTemp2[i].contains(":")) {
                        modifyBranchTypeTemp2[i] = typeBranch1[0].split(":")[0].concat(modifyBranchTypeTemp2[i].split(":")[1]).concat(":data2");
                    } else {
                        modifyBranchTypeTemp2[i] = typeBranch1[0].split(":")[0].concat(":data2");
                    }
                }

                System.arraycopy(modifyBranchTypeTemp2, 0, modifyBranchTypeTemp1, 0, typeBranch2.length - indexStart2);
                System.arraycopy(typeSegment, 0, modifyBranchTypeTemp1, typeBranch2.length - indexStart2, typeSegment.length);
                System.arraycopy(typeBranch1, indexStart1, modifyBranchTypeTemp1, typeBranch2.length - indexStart2 + typeSegment.length, typeBranch1.length - indexStart1);
                modifyBranchType1 = modifyBranchTypeTemp1;

                System.arraycopy(modifyBranchRadiusTemp2, 0, modifyBranchRadiusTemp1, 0, radiusBranch2.length - indexStart2);
                System.arraycopy(radiusSegment, 0, modifyBranchRadiusTemp1, radiusBranch2.length - indexStart2, radiusSegment.length);
                System.arraycopy(radiusBranch1, indexStart1, modifyBranchRadiusTemp1, radiusBranch2.length - indexStart2 + radiusSegment.length, radiusBranch1.length - indexStart1);
                modifyBranchRadius1 = modifyBranchRadiusTemp1;

                System.arraycopy(modifyBranchSynapseTemp2, 0, modifyBranchSynapseTemp1, 0, synapseBranch2.length - indexStart2);
                System.arraycopy(synapseSegment, 0, modifyBranchSynapseTemp1, synapseBranch2.length - indexStart2, synapseSegment.length);
                System.arraycopy(synapseBranch1, indexStart1, modifyBranchSynapseTemp1, synapseBranch2.length - indexStart2 + synapseSegment.length, synapseBranch1.length - indexStart1);
                modifyBranchSynapse1 = modifyBranchSynapseTemp1;

                for (int i = 0; i < connectionBranch1.length; i++) {
                    if (! connectionBranch1[i].equals("0")) {
                        connectionBranch1[i] = connectionBranch1[i].concat("#data1");
                    }
                }

                for (int i = 0; i < modifyBranchConnectionTemp2.length; i++) {
                    if (! modifyBranchConnectionTemp2[i].equals("0")) {
                        modifyBranchConnectionTemp2[i] = modifyBranchConnectionTemp2[i].concat("#data2");
                    }
                }

                System.arraycopy(modifyBranchConnectionTemp2, 0, modifyBranchConnectionTemp1, 0, connectionBranch2.length - indexStart2);
                System.arraycopy(connectionSegment, 0, modifyBranchConnectionTemp1, connectionBranch2.length - indexStart2, connectionSegment.length);
                System.arraycopy(connectionBranch1, indexStart1, modifyBranchConnectionTemp1, connectionBranch2.length - indexStart2 + connectionSegment.length, connectionBranch1.length - indexStart1);
                modifyBranchConnection1 = modifyBranchConnectionTemp1;
            }

        } else if (flag1 == 0 && flag2 == 0) {

            if (flag3 == 1 && flag4 == 2) {

                modifyBranchCoordinate1 = coordinateBranch2;

                for (int i = 0; i < typeBranch2.length; i++) {
                    if (typeBranch2[i].contains(":")) {
                        typeBranch2[i] = typeBranch2[i].concat(":data2");
                    }
                }

                modifyBranchType1 = typeBranch2;
                modifyBranchRadius1 = radiusBranch2;
                modifyBranchSynapse1 = synapseBranch2;

                for (int i = 0; i < connectionBranch2.length; i++) {
                    if (! connectionBranch2[i].equals("0")) {
                        connectionBranch2[i] = connectionBranch2[i].concat("#data2");
                    }
                }

                modifyBranchConnection1 = connectionBranch2;

            } else if (flag3 == 1 && flag4 == 0) {

                if (indexStart2 <= indexEnd2) {
                    IntegerCoordinateLine integerCoordinateLineInstance = new IntegerCoordinateLine(coordinateBranch2[indexStart2], coordinateBranch1[indexStart1]);
                    Integer[][] coordinateSegment = integerCoordinateLineInstance.getIntegerCoordinateLine();

                    String[] typeSegment = new String[coordinateSegment.length - 2];
                    Integer[] radiusSegment = new Integer[coordinateSegment.length - 2];
                    Integer[] synapseSegment = new Integer[coordinateSegment.length - 2];
                    String[] connectionSegment = new String[coordinateSegment.length - 2];

                    for (int i = 0; i < coordinateSegment.length - 2; i++) {
                        typeSegment[i] = typeBranch1[0].split(":")[0];
                        radiusSegment[i] = 0;
                        synapseSegment[i] = 0;
                        connectionSegment[i] = "0";
                    }

                    int coordinateBranchSize = coordinateBranch1.length - indexStart1 + coordinateSegment.length + indexStart2 - 1;

                    Integer[][] modifyBranchCoordinateTemp1 = new Integer[coordinateBranchSize][3];
                    System.arraycopy(coordinateBranch2, 0, modifyBranchCoordinateTemp1, 0, indexStart2);
                    System.arraycopy(coordinateSegment, 0, modifyBranchCoordinateTemp1, indexStart2, coordinateSegment.length);
                    System.arraycopy(coordinateBranch1, indexStart1 + 1, modifyBranchCoordinateTemp1, indexStart2 + coordinateSegment.length, coordinateBranch1.length - indexStart1 - 1);
                    modifyBranchCoordinate1 = modifyBranchCoordinateTemp1;

                    for (int i = 0; i < typeBranch1.length; i++) {
                        if (typeBranch1[i].contains(":")) {
                            typeBranch1[i] = typeBranch1[i].concat(":data1");
                        }
                    }

                    for (int i = 0; i < typeBranch2.length; i++) {
                        if (typeBranch2[i].contains(":")) {
                            typeBranch2[i] = typeBranch1[0].split(":")[0].concat(typeBranch2[i].split(":")[1]).concat(":data2");
                        } else {
                            typeBranch2[i] = typeBranch1[0].split(":")[0];
                        }
                    }

                    String[] modifyBranchTypeTemp1 = new String[coordinateBranchSize];
                    System.arraycopy(typeBranch2, 0, modifyBranchTypeTemp1, 0, indexStart2 + 1);
                    System.arraycopy(typeSegment, 0, modifyBranchTypeTemp1, indexStart2 + 1, typeSegment.length);
                    System.arraycopy(typeBranch1, indexStart1, modifyBranchTypeTemp1, indexStart2 + typeSegment.length + 1, typeBranch1.length - indexStart1);
                    modifyBranchType1 = modifyBranchTypeTemp1;

                    Integer[] modifyBranchRadiusTemp1 = new Integer[coordinateBranchSize];
                    System.arraycopy(radiusBranch2, 0, modifyBranchRadiusTemp1, 0, indexStart2 + 1);
                    System.arraycopy(radiusSegment, 0, modifyBranchRadiusTemp1, indexStart2 + 1, radiusSegment.length);
                    System.arraycopy(radiusBranch1, indexStart1, modifyBranchRadiusTemp1, indexStart2 + radiusSegment.length + 1, radiusBranch1.length - indexStart1);
                    modifyBranchRadius1 = modifyBranchRadiusTemp1;

                    Integer[] modifyBranchSynapseTemp1 = new Integer[coordinateBranchSize];
                    System.arraycopy(synapseBranch2, 0, modifyBranchSynapseTemp1, 0, indexStart2 + 1);
                    System.arraycopy(synapseSegment, 0, modifyBranchSynapseTemp1, indexStart2 + 1, synapseSegment.length);
                    System.arraycopy(synapseBranch1, indexStart1, modifyBranchSynapseTemp1, indexStart2 + synapseSegment.length + 1, synapseBranch1.length - indexStart1);
                    modifyBranchSynapse1 = modifyBranchSynapseTemp1;

                    for (int i = 0; i < connectionBranch1.length; i++) {
                        if (! connectionBranch1[i].equals("0")) {
                            connectionBranch1[i] = connectionBranch1[i].concat("#data1");
                        }
                    }

                    for (int i = 0; i < connectionBranch2.length; i++) {
                        if (! connectionBranch2[i].equals("0")) {
                            connectionBranch2[i] = connectionBranch2[i].concat("#data2");
                        }
                    }

                    String[] modifyBranchConnectionTemp1 = new String[coordinateBranchSize];
                    System.arraycopy(connectionBranch2, 0, modifyBranchConnectionTemp1, 0, indexStart2 + 1);
                    System.arraycopy(connectionSegment, 0, modifyBranchConnectionTemp1, indexStart2 + 1, connectionSegment.length);
                    System.arraycopy(connectionBranch1, indexStart1, modifyBranchConnectionTemp1, indexStart2 + connectionSegment.length + 1, connectionBranch1.length - indexStart1);
                    modifyBranchConnection1 = modifyBranchConnectionTemp1;
                } else {
                    IntegerCoordinateLine integerCoordinateLineInstance = new IntegerCoordinateLine(coordinateBranch1[indexEnd1], coordinateBranch2[indexEnd2]);
                    Integer[][] coordinateSegment = integerCoordinateLineInstance.getIntegerCoordinateLine();

                    String[] typeSegment = new String[coordinateSegment.length - 2];
                    Integer[] radiusSegment = new Integer[coordinateSegment.length - 2];
                    Integer[] synapseSegment = new Integer[coordinateSegment.length - 2];
                    String[] connectionSegment = new String[coordinateSegment.length - 2];

                    for (int i = 0; i < coordinateSegment.length - 2; i++) {
                        typeSegment[i] = typeBranch1[0].split(":")[0];
                        radiusSegment[i] = 0;
                        synapseSegment[i] = 0;
                        connectionSegment[i] = "0";
                    }

                    int coordinateBranchSize = indexEnd1 + coordinateSegment.length + indexEnd2;

                    Integer[][] modifyBranchCoordinateTemp1 = new Integer[coordinateBranchSize][3];
                    String[] modifyBranchTypeTemp1 = new String[coordinateBranchSize];
                    Integer[] modifyBranchRadiusTemp1 = new Integer[coordinateBranchSize];
                    Integer[] modifyBranchSynapseTemp1 = new Integer[coordinateBranchSize];
                    String[] modifyBranchConnectionTemp1 = new String[coordinateBranchSize];

                    Integer[][] modifyBranchCoordinateTemp2 = new Integer[indexEnd2][3];
                    String[] modifyBranchTypeTemp2 = new String[indexEnd2 + 1];
                    Integer[] modifyBranchRadiusTemp2 = new Integer[indexEnd2 + 1];
                    Integer[] modifyBranchSynapseTemp2 = new Integer[indexEnd2 + 1];
                    String[] modifyBranchConnectionTemp2 = new String[indexEnd2 + 1];

                    modifyBranchTypeTemp2[0] = typeBranch2[indexEnd2];
                    modifyBranchRadiusTemp2[0] = radiusBranch2[indexEnd2];
                    modifyBranchSynapseTemp2[0] = synapseBranch2[indexEnd2];
                    modifyBranchConnectionTemp2[0] = connectionBranch2[indexEnd2];

                    for (int i = 0; i < indexEnd2; i++) {
                        modifyBranchCoordinateTemp2[i] = coordinateBranch2[indexEnd2 - i - 1];
                        modifyBranchTypeTemp2[i + 1] = typeBranch2[indexEnd2 - i - 1];
                        modifyBranchRadiusTemp2[i + 1] = radiusBranch2[indexEnd2 - i - 1];
                        modifyBranchSynapseTemp2[i + 1] = synapseBranch2[indexEnd2 - i - 1];
                        modifyBranchConnectionTemp2[i + 1] = connectionBranch2[indexEnd2 - i - 1];
                    }

                    System.arraycopy(coordinateBranch1, 0, modifyBranchCoordinateTemp1, 0, indexEnd1);
                    System.arraycopy(coordinateSegment, 0, modifyBranchCoordinateTemp1, indexEnd1, coordinateSegment.length);
                    System.arraycopy(modifyBranchCoordinateTemp2, 0, modifyBranchCoordinateTemp1, indexEnd1 + coordinateSegment.length, indexEnd2);
                    modifyBranchCoordinate1 = modifyBranchCoordinateTemp1;

                    for (int i = 0; i < typeBranch1.length; i++) {
                        if (typeBranch1[i].contains(":")) {
                            typeBranch1[i] = typeBranch1[i].concat(":data1");
                        }
                    }

                    for (int i = 0; i < modifyBranchTypeTemp2.length; i++) {
                        if (modifyBranchTypeTemp2[i].contains(":")) {
                            modifyBranchTypeTemp2[i] = typeBranch1[0].split(":")[0].concat(modifyBranchTypeTemp2[i].split(":")[1]).concat(":data2");
                        } else {
                            modifyBranchTypeTemp2[i] = typeBranch1[0].split(":")[0];
                        }
                    }

                    System.arraycopy(typeBranch1, 0, modifyBranchTypeTemp1, 0, indexEnd1 + 1);
                    System.arraycopy(typeSegment, 0, modifyBranchTypeTemp1, indexEnd1 + 1, typeSegment.length);
                    System.arraycopy(modifyBranchTypeTemp2, 0, modifyBranchTypeTemp1, indexEnd1 + typeSegment.length + 1, indexEnd2 + 1);
                    modifyBranchType1 = modifyBranchTypeTemp1;

                    System.arraycopy(radiusBranch1, 0, modifyBranchRadiusTemp1, 0, indexEnd1 + 1);
                    System.arraycopy(radiusSegment, 0, modifyBranchRadiusTemp1, indexEnd1 + 1, radiusSegment.length);
                    System.arraycopy(modifyBranchRadiusTemp2, 0, modifyBranchRadiusTemp1, indexEnd1 + radiusSegment.length + 1, indexEnd2 + 1);
                    modifyBranchRadius1 = modifyBranchRadiusTemp1;

                    System.arraycopy(synapseBranch1, 0, modifyBranchSynapseTemp1, 0, indexEnd1 + 1);
                    System.arraycopy(synapseSegment, 0, modifyBranchSynapseTemp1, indexEnd1 + 1, synapseSegment.length);
                    System.arraycopy(modifyBranchSynapseTemp2, 0, modifyBranchSynapseTemp1, indexEnd1 + synapseSegment.length + 1, indexEnd2 + 1);
                    modifyBranchSynapse1 = modifyBranchSynapseTemp1;

                    for (int i = 0; i < connectionBranch1.length; i++) {
                        if (! connectionBranch1[i].equals("0")) {
                            connectionBranch1[i] = connectionBranch1[i].concat("#data1");
                        }
                    }

                    for (int i = 0; i < modifyBranchConnectionTemp2.length; i++) {
                        if (! modifyBranchConnectionTemp2[i].equals("0")) {
                            modifyBranchConnectionTemp2[i] = modifyBranchConnectionTemp2[i].concat("#data2");
                        }
                    }

                    System.arraycopy(connectionBranch1, 0, modifyBranchConnectionTemp1, 0, indexEnd1 + 1);
                    System.arraycopy(connectionSegment, 0, modifyBranchConnectionTemp1, indexEnd1 + 1, connectionSegment.length);
                    System.arraycopy(modifyBranchConnectionTemp2, 0, modifyBranchConnectionTemp1, indexEnd1 + connectionSegment.length + 1, indexEnd2 + 1);
                    modifyBranchConnection1 = modifyBranchConnectionTemp1;
                }

            } else if (flag3 == 0 && flag4 == 2) {

                if (indexStart2 <= indexEnd2) {
                    IntegerCoordinateLine integerCoordinateLineInstance = new IntegerCoordinateLine(coordinateBranch1[indexEnd1], coordinateBranch2[indexEnd2]);
                    Integer[][] coordinateSegment = integerCoordinateLineInstance.getIntegerCoordinateLine();

                    String[] typeSegment = new String[coordinateSegment.length - 2];
                    Integer[] radiusSegment = new Integer[coordinateSegment.length - 2];
                    Integer[] synapseSegment = new Integer[coordinateSegment.length - 2];
                    String[] connectionSegment = new String[coordinateSegment.length - 2];

                    for (int i = 0; i < coordinateSegment.length - 2; i++) {
                        typeSegment[i] = typeBranch1[0].split(":")[0];
                        radiusSegment[i] = 0;
                        synapseSegment[i] = 0;
                        connectionSegment[i] = "0";
                    }

                    int coordinateBranchSize = indexEnd1 + coordinateSegment.length + coordinateBranch2.length - indexEnd2 - 1;

                    Integer[][] modifyBranchCoordinateTemp1 = new Integer[coordinateBranchSize][3];
                    System.arraycopy(coordinateBranch1, 0, modifyBranchCoordinateTemp1, 0, indexEnd1);
                    System.arraycopy(coordinateSegment, 0, modifyBranchCoordinateTemp1, indexEnd1, coordinateSegment.length);
                    System.arraycopy(coordinateBranch2, indexEnd2 + 1, modifyBranchCoordinateTemp1, indexEnd1 + coordinateSegment.length, coordinateBranch2.length - indexEnd2 - 1);
                    modifyBranchCoordinate1 = modifyBranchCoordinateTemp1;

                    for (int i = 0; i < typeBranch1.length; i++) {
                        if (typeBranch1[i].contains(":")) {
                            typeBranch1[i] = typeBranch1[i].concat(":data1");
                        }
                    }

                    for (int i = 0; i < typeBranch2.length; i++) {
                        if (typeBranch2[i].contains(":")) {
                            typeBranch2[i] = typeBranch1[0].split(":")[0].concat(typeBranch2[i].split(":")[1]).concat(":data2");
                        } else {
                            typeBranch2[i] = typeBranch1[0].split(":")[0];
                        }
                    }

                    String[] modifyBranchTypeTemp1 = new String[coordinateBranchSize];
                    System.arraycopy(typeBranch1, 0, modifyBranchTypeTemp1, 0, indexEnd1 + 1);
                    System.arraycopy(typeSegment, 0, modifyBranchTypeTemp1, indexEnd1 + 1, typeSegment.length);
                    System.arraycopy(typeBranch2, indexEnd2, modifyBranchTypeTemp1, indexEnd1 + typeSegment.length + 1, typeBranch2.length - indexEnd2);
                    modifyBranchType1 = modifyBranchTypeTemp1;

                    Integer[] modifyBranchRadiusTemp1 = new Integer[coordinateBranchSize];
                    System.arraycopy(radiusBranch1, 0, modifyBranchRadiusTemp1, 0, indexEnd1 + 1);
                    System.arraycopy(radiusSegment, 0, modifyBranchRadiusTemp1, indexEnd1 + 1, radiusSegment.length);
                    System.arraycopy(radiusBranch2, indexEnd2, modifyBranchRadiusTemp1, indexEnd1 + radiusSegment.length + 1, radiusBranch2.length - indexEnd2);
                    modifyBranchRadius1 = modifyBranchRadiusTemp1;

                    Integer[] modifyBranchSynapseTemp1 = new Integer[coordinateBranchSize];
                    System.arraycopy(synapseBranch1, 0, modifyBranchSynapseTemp1, 0, indexEnd1 + 1);
                    System.arraycopy(synapseSegment, 0, modifyBranchSynapseTemp1, indexEnd1 + 1, synapseSegment.length);
                    System.arraycopy(synapseBranch2, indexEnd2, modifyBranchSynapseTemp1, indexEnd1 + synapseSegment.length + 1, synapseBranch2.length - indexEnd2);
                    modifyBranchSynapse1 = modifyBranchSynapseTemp1;

                    for (int i = 0; i < connectionBranch1.length; i++) {
                        if (! connectionBranch1[i].equals("0")) {
                            connectionBranch1[i] = connectionBranch1[i].concat("#data1");
                        }
                    }

                    for (int i = 0; i < connectionBranch2.length; i++) {
                        if (! connectionBranch2[i].equals("0")) {
                            connectionBranch2[i] = connectionBranch2[i].concat("#data2");
                        }
                    }

                    String[] modifyBranchConnectionTemp1 = new String[coordinateBranchSize];
                    System.arraycopy(connectionBranch1, 0, modifyBranchConnectionTemp1, 0, indexEnd1 + 1);
                    System.arraycopy(connectionSegment, 0, modifyBranchConnectionTemp1, indexEnd1 + 1, connectionSegment.length);
                    System.arraycopy(connectionBranch2, indexEnd2, modifyBranchConnectionTemp1, indexEnd1 + connectionSegment.length + 1, connectionBranch2.length - indexEnd2);
                    modifyBranchConnection1 = modifyBranchConnectionTemp1;
                } else {
                    IntegerCoordinateLine integerCoordinateLineInstance = new IntegerCoordinateLine(coordinateBranch2[indexStart2], coordinateBranch1[indexStart1]);
                    Integer[][] coordinateSegment = integerCoordinateLineInstance.getIntegerCoordinateLine();

                    String[] typeSegment = new String[coordinateSegment.length - 2];
                    Integer[] radiusSegment = new Integer[coordinateSegment.length - 2];
                    Integer[] synapseSegment = new Integer[coordinateSegment.length - 2];
                    String[] connectionSegment = new String[coordinateSegment.length - 2];

                    for (int i = 0; i < coordinateSegment.length - 2; i++) {
                        typeSegment[i] = typeBranch1[0].split(":")[0];
                        radiusSegment[i] = 0;
                        synapseSegment[i] = 0;
                        connectionSegment[i] = "0";
                    }

                    int coordinateBranchSize = coordinateBranch1.length - indexStart1 + coordinateSegment.length + coordinateBranch2.length - indexStart2 - 2;

                    Integer[][] modifyBranchCoordinateTemp1 = new Integer[coordinateBranchSize][3];
                    String[] modifyBranchTypeTemp1 = new String[coordinateBranchSize];
                    Integer[] modifyBranchRadiusTemp1 = new Integer[coordinateBranchSize];
                    Integer[] modifyBranchSynapseTemp1 = new Integer[coordinateBranchSize];
                    String[] modifyBranchConnectionTemp1 = new String[coordinateBranchSize];

                    Integer[][] modifyBranchCoordinateTemp2 = new Integer[coordinateBranch2.length - indexStart2 - 1][3];
                    String[] modifyBranchTypeTemp2 = new String[coordinateBranch2.length - indexStart2];
                    Integer[] modifyBranchRadiusTemp2 = new Integer[coordinateBranch2.length - indexStart2];
                    Integer[] modifyBranchSynapseTemp2 = new Integer[coordinateBranch2.length - indexStart2];
                    String[] modifyBranchConnectionTemp2 = new String[coordinateBranch2.length - indexStart2];

                    modifyBranchTypeTemp2[modifyBranchTypeTemp2.length - 1] = typeBranch2[indexStart2];
                    modifyBranchRadiusTemp2[modifyBranchRadiusTemp2.length - 1] = radiusBranch2[indexStart2];
                    modifyBranchSynapseTemp2[modifyBranchSynapseTemp2.length - 1] = synapseBranch2[indexStart2];
                    modifyBranchConnectionTemp2[modifyBranchConnectionTemp2.length - 1] = connectionBranch2[indexStart2];

                    for (int i = 0; i < coordinateBranch2.length - indexStart2 - 1; i++) {
                        modifyBranchCoordinateTemp2[i] = coordinateBranch2[coordinateBranch2.length - i - 1];
                        modifyBranchTypeTemp2[i] = typeBranch2[typeBranch2.length - i - 1];
                        modifyBranchRadiusTemp2[i] = radiusBranch2[radiusBranch2.length - i - 1];
                        modifyBranchSynapseTemp2[i] = synapseBranch2[synapseBranch2.length - i - 1];
                        modifyBranchConnectionTemp2[i] = connectionBranch2[connectionBranch2.length - i - 1];
                    }

                    System.arraycopy(modifyBranchCoordinateTemp2, 0, modifyBranchCoordinateTemp1, 0, coordinateBranch2.length - indexStart2 - 1);
                    System.arraycopy(coordinateSegment, 0, modifyBranchCoordinateTemp1, coordinateBranch2.length - indexStart2 - 1, coordinateSegment.length);
                    System.arraycopy(coordinateBranch1, indexStart1 + 1, modifyBranchCoordinateTemp1, coordinateBranch2.length - indexStart2 + coordinateSegment.length - 1, coordinateBranch1.length - indexStart1 - 1);
                    modifyBranchCoordinate1 = modifyBranchCoordinateTemp1;

                    for (int i = 0; i < typeBranch1.length; i++) {
                        if (typeBranch1[i].contains(":")) {
                            typeBranch1[i] = typeBranch1[i].concat(":data1");
                        }
                    }

                    for (int i = 0; i < modifyBranchTypeTemp2.length; i++) {
                        if (modifyBranchTypeTemp2[i].contains(":")) {
                            modifyBranchTypeTemp2[i] = typeBranch1[0].split(":")[0].concat(modifyBranchTypeTemp2[i].split(":")[1]).concat(":data2");
                        } else {
                            modifyBranchTypeTemp2[i] = typeBranch1[0].split(":")[0];
                        }
                    }

                    System.arraycopy(modifyBranchTypeTemp2, 0, modifyBranchTypeTemp1, 0, typeBranch2.length - indexStart2);
                    System.arraycopy(typeSegment, 0, modifyBranchTypeTemp1, typeBranch2.length - indexStart2, typeSegment.length);
                    System.arraycopy(typeBranch1, indexStart1, modifyBranchTypeTemp1, typeBranch2.length - indexStart2 + typeSegment.length, typeBranch1.length - indexStart1);
                    modifyBranchType1 = modifyBranchTypeTemp1;

                    System.arraycopy(modifyBranchRadiusTemp2, 0, modifyBranchRadiusTemp1, 0, radiusBranch2.length - indexStart2);
                    System.arraycopy(radiusSegment, 0, modifyBranchRadiusTemp1, radiusBranch2.length - indexStart2, radiusSegment.length);
                    System.arraycopy(radiusBranch1, indexStart1, modifyBranchRadiusTemp1, radiusBranch2.length - indexStart2 + radiusSegment.length, radiusBranch1.length - indexStart1);
                    modifyBranchRadius1 = modifyBranchRadiusTemp1;

                    System.arraycopy(modifyBranchSynapseTemp2, 0, modifyBranchSynapseTemp1, 0, synapseBranch2.length - indexStart2);
                    System.arraycopy(synapseSegment, 0, modifyBranchSynapseTemp1, synapseBranch2.length - indexStart2, synapseSegment.length);
                    System.arraycopy(synapseBranch1, indexStart1, modifyBranchSynapseTemp1, synapseBranch2.length - indexStart2 + synapseSegment.length, synapseBranch1.length - indexStart1);
                    modifyBranchSynapse1 = modifyBranchSynapseTemp1;

                    for (int i = 0; i < connectionBranch1.length; i++) {
                        if (! connectionBranch1[i].equals("0")) {
                            connectionBranch1[i] = connectionBranch1[i].concat("#data1");
                        }
                    }

                    for (int i = 0; i < modifyBranchConnectionTemp2.length; i++) {
                        if (! modifyBranchConnectionTemp2[i].equals("0")) {
                            modifyBranchConnectionTemp2[i] = modifyBranchConnectionTemp2[i].concat("#data2");
                        }
                    }

                    System.arraycopy(modifyBranchConnectionTemp2, 0, modifyBranchConnectionTemp1, 0, connectionBranch2.length - indexStart2);
                    System.arraycopy(connectionSegment, 0, modifyBranchConnectionTemp1, connectionBranch2.length - indexStart2, connectionSegment.length);
                    System.arraycopy(connectionBranch1, indexStart1, modifyBranchConnectionTemp1, connectionBranch2.length - indexStart2 + connectionSegment.length, connectionBranch1.length - indexStart1);
                    modifyBranchConnection1 = modifyBranchConnectionTemp1;
                }

            } else if (flag3 == 0 && flag4 == 0) {

                if ((indexStart1 + indexEnd1) / 2 <= coordinateBranch1.length / 2) {

                    IntegerCoordinateLine integerCoordinateLineInstance = new IntegerCoordinateLine(coordinateBranch1[indexEnd1], coordinateBranch2[indexEnd2]);
                    Integer[][] coordinateSegment = integerCoordinateLineInstance.getIntegerCoordinateLine();

                    String[] typeSegment = new String[coordinateSegment.length - 2];
                    Integer[] radiusSegment = new Integer[coordinateSegment.length - 2];
                    Integer[] synapseSegment = new Integer[coordinateSegment.length - 2];
                    String[] connectionSegment = new String[coordinateSegment.length - 2];

                    for (int i = 0; i < coordinateSegment.length - 2; i++) {
                        typeSegment[i] = typeBranch1[0].split(":")[0];
                        radiusSegment[i] = 0;
                        synapseSegment[i] = 0;
                        connectionSegment[i] = "0";
                    }

                    if (indexStart2 <= indexEnd2) {
                        int coordinateBranchSize = indexEnd1 + coordinateSegment.length + coordinateBranch2.length - indexEnd2 - 1;

                        Integer[][] modifyBranchCoordinateTemp1 = new Integer[coordinateBranchSize][3];
                        System.arraycopy(coordinateBranch1, 0, modifyBranchCoordinateTemp1, 0, indexEnd1);
                        System.arraycopy(coordinateSegment, 0, modifyBranchCoordinateTemp1, indexEnd1, coordinateSegment.length);
                        System.arraycopy(coordinateBranch2, indexEnd2 + 1, modifyBranchCoordinateTemp1, indexEnd1 + coordinateSegment.length, coordinateBranch2.length - indexEnd2 - 1);
                        modifyBranchCoordinate1 = modifyBranchCoordinateTemp1;

                        for (int i = 0; i < typeBranch1.length; i++) {
                            if (typeBranch1[i].contains(":")) {
                                typeBranch1[i] = typeBranch1[i].concat(":data1");
                            }
                        }

                        for (int i = 0; i < typeBranch2.length; i++) {
                            if (typeBranch2[i].contains(":")) {
                                typeBranch2[i] = typeBranch1[0].split(":")[0].concat(typeBranch2[i].split(":")[1]).concat(":data2");
                            } else {
                                typeBranch2[i] = typeBranch1[0].split(":")[0];
                            }
                        }

                        String[] modifyBranchTypeTemp1 = new String[coordinateBranchSize];
                        System.arraycopy(typeBranch1, 0, modifyBranchTypeTemp1, 0, indexEnd1 + 1);
                        System.arraycopy(typeSegment, 0, modifyBranchTypeTemp1, indexEnd1 + 1, typeSegment.length);
                        System.arraycopy(typeBranch2, indexEnd2, modifyBranchTypeTemp1, indexEnd1 + typeSegment.length + 1, typeBranch2.length - indexEnd2);
                        modifyBranchType1 = modifyBranchTypeTemp1;

                        Integer[] modifyBranchRadiusTemp1 = new Integer[coordinateBranchSize];
                        System.arraycopy(radiusBranch1, 0, modifyBranchRadiusTemp1, 0, indexEnd1 + 1);
                        System.arraycopy(radiusSegment, 0, modifyBranchRadiusTemp1, indexEnd1 + 1, radiusSegment.length);
                        System.arraycopy(radiusBranch2, indexEnd2, modifyBranchRadiusTemp1, indexEnd1 + radiusSegment.length + 1, radiusBranch2.length - indexEnd2);
                        modifyBranchRadius1 = modifyBranchRadiusTemp1;

                        Integer[] modifyBranchSynapseTemp1 = new Integer[coordinateBranchSize];
                        System.arraycopy(synapseBranch1, 0, modifyBranchSynapseTemp1, 0, indexEnd1 + 1);
                        System.arraycopy(synapseSegment, 0, modifyBranchSynapseTemp1, indexEnd1 + 1, synapseSegment.length);
                        System.arraycopy(synapseBranch2, indexEnd2, modifyBranchSynapseTemp1, indexEnd1 + synapseSegment.length + 1, synapseBranch2.length - indexEnd2);
                        modifyBranchSynapse1 = modifyBranchSynapseTemp1;

                        for (int i = 0; i < connectionBranch1.length; i++) {
                            if (! connectionBranch1[i].equals("0")) {
                                connectionBranch1[i] = connectionBranch1[i].concat("#data1");
                            }
                        }

                        for (int i = 0; i < connectionBranch2.length; i++) {
                            if (! connectionBranch2[i].equals("0")) {
                                connectionBranch2[i] = connectionBranch2[i].concat("#data2");
                            }
                        }

                        String[] modifyBranchConnectionTemp1 = new String[coordinateBranchSize];
                        System.arraycopy(connectionBranch1, 0, modifyBranchConnectionTemp1, 0, indexEnd1 + 1);
                        System.arraycopy(connectionSegment, 0, modifyBranchConnectionTemp1, indexEnd1 + 1, connectionSegment.length);
                        System.arraycopy(connectionBranch2, indexEnd2, modifyBranchConnectionTemp1, indexEnd1 + connectionSegment.length + 1, connectionBranch2.length - indexEnd2);
                        modifyBranchConnection1 = modifyBranchConnectionTemp1;
                    } else {
                        int coordinateBranchSize = indexEnd1 + coordinateSegment.length + indexEnd2;

                        Integer[][] modifyBranchCoordinateTemp1 = new Integer[coordinateBranchSize][3];
                        String[] modifyBranchTypeTemp1 = new String[coordinateBranchSize];
                        Integer[] modifyBranchRadiusTemp1 = new Integer[coordinateBranchSize];
                        Integer[] modifyBranchSynapseTemp1 = new Integer[coordinateBranchSize];
                        String[] modifyBranchConnectionTemp1 = new String[coordinateBranchSize];

                        Integer[][] modifyBranchCoordinateTemp2 = new Integer[indexEnd2][3];
                        String[] modifyBranchTypeTemp2 = new String[indexEnd2 + 1];
                        Integer[] modifyBranchRadiusTemp2 = new Integer[indexEnd2 + 1];
                        Integer[] modifyBranchSynapseTemp2 = new Integer[indexEnd2 + 1];
                        String[] modifyBranchConnectionTemp2 = new String[indexEnd2 + 1];

                        modifyBranchTypeTemp2[0] = typeBranch2[indexEnd2];
                        modifyBranchRadiusTemp2[0] = radiusBranch2[indexEnd2];
                        modifyBranchSynapseTemp2[0] = synapseBranch2[indexEnd2];
                        modifyBranchConnectionTemp2[0] = connectionBranch2[indexEnd2];

                        for (int i = 0; i < indexEnd2; i++) {
                            modifyBranchCoordinateTemp2[i] = coordinateBranch2[indexEnd2 - i - 1];
                            modifyBranchTypeTemp2[i + 1] = typeBranch2[indexEnd2 - i - 1];
                            modifyBranchRadiusTemp2[i + 1] = radiusBranch2[indexEnd2 - i - 1];
                            modifyBranchSynapseTemp2[i + 1] = synapseBranch2[indexEnd2 - i - 1];
                            modifyBranchConnectionTemp2[i + 1] = connectionBranch2[indexEnd2 - i - 1];
                        }

                        System.arraycopy(coordinateBranch1, 0, modifyBranchCoordinateTemp1, 0, indexEnd1);
                        System.arraycopy(coordinateSegment, 0, modifyBranchCoordinateTemp1, indexEnd1, coordinateSegment.length);
                        System.arraycopy(modifyBranchCoordinateTemp2, 0, modifyBranchCoordinateTemp1, indexEnd1 + coordinateSegment.length, indexEnd2);
                        modifyBranchCoordinate1 = modifyBranchCoordinateTemp1;

                        for (int i = 0; i < typeBranch1.length; i++) {
                            if (typeBranch1[i].contains(":")) {
                                typeBranch1[i] = typeBranch1[i].concat(":data1");
                            }
                        }

                        for (int i = 0; i < modifyBranchTypeTemp2.length; i++) {
                            if (modifyBranchTypeTemp2[i].contains(":")) {
                                modifyBranchTypeTemp2[i] = typeBranch1[0].split(":")[0].concat(modifyBranchTypeTemp2[i].split(":")[1]).concat(":data2");
                            } else {
                                modifyBranchTypeTemp2[i] = typeBranch1[0].split(":")[0];
                            }
                        }

                        System.arraycopy(typeBranch1, 0, modifyBranchTypeTemp1, 0, indexEnd1 + 1);
                        System.arraycopy(typeSegment, 0, modifyBranchTypeTemp1, indexEnd1 + 1, typeSegment.length);
                        System.arraycopy(modifyBranchTypeTemp2, 0, modifyBranchTypeTemp1, indexEnd1 + typeSegment.length + 1, indexEnd2 + 1);
                        modifyBranchType1 = modifyBranchTypeTemp1;

                        System.arraycopy(radiusBranch1, 0, modifyBranchRadiusTemp1, 0, indexEnd1 + 1);
                        System.arraycopy(radiusSegment, 0, modifyBranchRadiusTemp1, indexEnd1 + 1, radiusSegment.length);
                        System.arraycopy(modifyBranchRadiusTemp2, 0, modifyBranchRadiusTemp1, indexEnd1 + radiusSegment.length + 1, indexEnd2 + 1);
                        modifyBranchRadius1 = modifyBranchRadiusTemp1;

                        System.arraycopy(synapseBranch1, 0, modifyBranchSynapseTemp1, 0, indexEnd1 + 1);
                        System.arraycopy(synapseSegment, 0, modifyBranchSynapseTemp1, indexEnd1 + 1, synapseSegment.length);
                        System.arraycopy(modifyBranchSynapseTemp2, 0, modifyBranchSynapseTemp1, indexEnd1 + synapseSegment.length + 1, indexEnd2 + 1);
                        modifyBranchSynapse1 = modifyBranchSynapseTemp1;

                        for (int i = 0; i < connectionBranch1.length; i++) {
                            if (! connectionBranch1[i].equals("0")) {
                                connectionBranch1[i] = connectionBranch1[i].concat("#data1");
                            }
                        }

                        for (int i = 0; i < modifyBranchConnectionTemp2.length; i++) {
                            if (! modifyBranchConnectionTemp2[i].equals("0")) {
                                modifyBranchConnectionTemp2[i] = modifyBranchConnectionTemp2[i].concat("#data2");
                            }
                        }

                        System.arraycopy(connectionBranch1, 0, modifyBranchConnectionTemp1, 0, indexEnd1 + 1);
                        System.arraycopy(connectionSegment, 0, modifyBranchConnectionTemp1, indexEnd1 + 1, connectionSegment.length);
                        System.arraycopy(modifyBranchConnectionTemp2, 0, modifyBranchConnectionTemp1, indexEnd1 + connectionSegment.length + 1, indexEnd2 + 1);
                        modifyBranchConnection1 = modifyBranchConnectionTemp1;
                    }

                } else {

                    IntegerCoordinateLine integerCoordinateLineInstance = new IntegerCoordinateLine(coordinateBranch2[indexStart2], coordinateBranch1[indexStart1]);
                    Integer[][] coordinateSegment = integerCoordinateLineInstance.getIntegerCoordinateLine();

                    String[] typeSegment = new String[coordinateSegment.length - 2];
                    Integer[] radiusSegment = new Integer[coordinateSegment.length - 2];
                    Integer[] synapseSegment = new Integer[coordinateSegment.length - 2];
                    String[] connectionSegment = new String[coordinateSegment.length - 2];

                    for (int i = 0; i < coordinateSegment.length - 2; i++) {
                        typeSegment[i] = typeBranch1[0].split(":")[0];
                        radiusSegment[i] = 0;
                        synapseSegment[i] = 0;
                        connectionSegment[i] = "0";
                    }

                    if (indexStart2 <= indexEnd2) {
                        int coordinateBranchSize = coordinateBranch1.length - indexStart1 + coordinateSegment.length + indexStart2 - 1;

                        Integer[][] modifyBranchCoordinateTemp1 = new Integer[coordinateBranchSize][3];
                        System.arraycopy(coordinateBranch2, 0, modifyBranchCoordinateTemp1, 0, indexStart2);
                        System.arraycopy(coordinateSegment, 0, modifyBranchCoordinateTemp1, indexStart2, coordinateSegment.length);
                        System.arraycopy(coordinateBranch1, indexStart1 + 1, modifyBranchCoordinateTemp1, indexStart2 + coordinateSegment.length, coordinateBranch1.length - indexStart1 - 1);
                        modifyBranchCoordinate1 = modifyBranchCoordinateTemp1;

                        for (int i = 0; i < typeBranch1.length; i++) {
                            if (typeBranch1[i].contains(":")) {
                                typeBranch1[i] = typeBranch1[i].concat(":data1");
                            }
                        }

                        for (int i = 0; i < typeBranch2.length; i++) {
                            if (typeBranch2[i].contains(":")) {
                                typeBranch2[i] = typeBranch1[0].split(":")[0].concat(typeBranch2[i].split(":")[1]).concat(":data2");
                            } else {
                                typeBranch2[i] = typeBranch1[0].split(":")[0];
                            }
                        }

                        String[] modifyBranchTypeTemp1 = new String[coordinateBranchSize];
                        System.arraycopy(typeBranch2, 0, modifyBranchTypeTemp1, 0, indexStart2 + 1);
                        System.arraycopy(typeSegment, 0, modifyBranchTypeTemp1, indexStart2 + 1, typeSegment.length);
                        System.arraycopy(typeBranch1, indexStart1, modifyBranchTypeTemp1, indexStart2 + typeSegment.length + 1, typeBranch1.length - indexStart1);
                        modifyBranchType1 = modifyBranchTypeTemp1;

                        Integer[] modifyBranchRadiusTemp1 = new Integer[coordinateBranchSize];
                        System.arraycopy(radiusBranch2, 0, modifyBranchRadiusTemp1, 0, indexStart2 + 1);
                        System.arraycopy(radiusSegment, 0, modifyBranchRadiusTemp1, indexStart2 + 1, radiusSegment.length);
                        System.arraycopy(radiusBranch1, indexStart1, modifyBranchRadiusTemp1, indexStart2 + radiusSegment.length + 1, radiusBranch1.length - indexStart1);
                        modifyBranchRadius1 = modifyBranchRadiusTemp1;

                        Integer[] modifyBranchSynapseTemp1 = new Integer[coordinateBranchSize];
                        System.arraycopy(synapseBranch2, 0, modifyBranchSynapseTemp1, 0, indexStart2 + 1);
                        System.arraycopy(synapseSegment, 0, modifyBranchSynapseTemp1, indexStart2 + 1, synapseSegment.length);
                        System.arraycopy(synapseBranch1, indexStart1, modifyBranchSynapseTemp1, indexStart2 + synapseSegment.length + 1, synapseBranch1.length - indexStart1);
                        modifyBranchSynapse1 = modifyBranchSynapseTemp1;

                        for (int i = 0; i < connectionBranch1.length; i++) {
                            if (! connectionBranch1[i].equals("0")) {
                                connectionBranch1[i] = connectionBranch1[i].concat("#data1");
                            }
                        }

                        for (int i = 0; i < connectionBranch2.length; i++) {
                            if (! connectionBranch2[i].equals("0")) {
                                connectionBranch2[i] = connectionBranch2[i].concat("#data2");
                            }
                        }

                        String[] modifyBranchConnectionTemp1 = new String[coordinateBranchSize];
                        System.arraycopy(connectionBranch2, 0, modifyBranchConnectionTemp1, 0, indexStart2 + 1);
                        System.arraycopy(connectionSegment, 0, modifyBranchConnectionTemp1, indexStart2 + 1, connectionSegment.length);
                        System.arraycopy(connectionBranch1, indexStart1, modifyBranchConnectionTemp1, indexStart2 + connectionSegment.length + 1, connectionBranch1.length - indexStart1);
                        modifyBranchConnection1 = modifyBranchConnectionTemp1;
                    } else {
                        int coordinateBranchSize = coordinateBranch1.length - indexStart1 + coordinateSegment.length + coordinateBranch2.length - indexStart2 - 2;

                        Integer[][] modifyBranchCoordinateTemp1 = new Integer[coordinateBranchSize][3];
                        String[] modifyBranchTypeTemp1 = new String[coordinateBranchSize];
                        Integer[] modifyBranchRadiusTemp1 = new Integer[coordinateBranchSize];
                        Integer[] modifyBranchSynapseTemp1 = new Integer[coordinateBranchSize];
                        String[] modifyBranchConnectionTemp1 = new String[coordinateBranchSize];

                        Integer[][] modifyBranchCoordinateTemp2 = new Integer[coordinateBranch2.length - indexStart2 - 1][3];
                        String[] modifyBranchTypeTemp2 = new String[coordinateBranch2.length - indexStart2];
                        Integer[] modifyBranchRadiusTemp2 = new Integer[coordinateBranch2.length - indexStart2];
                        Integer[] modifyBranchSynapseTemp2 = new Integer[coordinateBranch2.length - indexStart2];
                        String[] modifyBranchConnectionTemp2 = new String[coordinateBranch2.length - indexStart2];

                        modifyBranchTypeTemp2[modifyBranchTypeTemp2.length - 1] = typeBranch2[indexStart2];
                        modifyBranchRadiusTemp2[modifyBranchRadiusTemp2.length - 1] = radiusBranch2[indexStart2];
                        modifyBranchSynapseTemp2[modifyBranchSynapseTemp2.length - 1] = synapseBranch2[indexStart2];
                        modifyBranchConnectionTemp2[modifyBranchConnectionTemp2.length - 1] = connectionBranch2[indexStart2];

                        for (int i = 0; i < coordinateBranch2.length - indexStart2 - 1; i++) {
                            modifyBranchCoordinateTemp2[i] = coordinateBranch2[coordinateBranch2.length - i - 1];
                            modifyBranchTypeTemp2[i] = typeBranch2[typeBranch2.length - i - 1];
                            modifyBranchRadiusTemp2[i] = radiusBranch2[radiusBranch2.length - i - 1];
                            modifyBranchSynapseTemp2[i] = synapseBranch2[synapseBranch2.length - i - 1];
                            modifyBranchConnectionTemp2[i] = connectionBranch2[connectionBranch2.length - i - 1];
                        }

                        System.arraycopy(modifyBranchCoordinateTemp2, 0, modifyBranchCoordinateTemp1, 0, coordinateBranch2.length - indexStart2 - 1);
                        System.arraycopy(coordinateSegment, 0, modifyBranchCoordinateTemp1, coordinateBranch2.length - indexStart2 - 1, coordinateSegment.length);
                        System.arraycopy(coordinateBranch1, indexStart1 + 1, modifyBranchCoordinateTemp1, coordinateBranch2.length - indexStart2 + coordinateSegment.length - 1, coordinateBranch1.length - indexStart1 - 1);
                        modifyBranchCoordinate1 = modifyBranchCoordinateTemp1;

                        for (int i = 0; i < typeBranch1.length; i++) {
                            if (typeBranch1[i].contains(":")) {
                                typeBranch1[i] = typeBranch1[i].concat(":data1");
                            }
                        }

                        for (int i = 0; i < modifyBranchTypeTemp2.length; i++) {
                            if (modifyBranchTypeTemp2[i].contains(":")) {
                                modifyBranchTypeTemp2[i] = typeBranch1[0].split(":")[0].concat(modifyBranchTypeTemp2[i].split(":")[1]).concat(":data2");
                            } else {
                                modifyBranchTypeTemp2[i] = typeBranch1[0].split(":")[0];
                            }
                        }

                        System.arraycopy(modifyBranchTypeTemp2, 0, modifyBranchTypeTemp1, 0, typeBranch2.length - indexStart2);
                        System.arraycopy(typeSegment, 0, modifyBranchTypeTemp1, typeBranch2.length - indexStart2, typeSegment.length);
                        System.arraycopy(typeBranch1, indexStart1, modifyBranchTypeTemp1, typeBranch2.length - indexStart2 + typeSegment.length, typeBranch1.length - indexStart1);
                        modifyBranchType1 = modifyBranchTypeTemp1;

                        System.arraycopy(modifyBranchRadiusTemp2, 0, modifyBranchRadiusTemp1, 0, radiusBranch2.length - indexStart2);
                        System.arraycopy(radiusSegment, 0, modifyBranchRadiusTemp1, radiusBranch2.length - indexStart2, radiusSegment.length);
                        System.arraycopy(radiusBranch1, indexStart1, modifyBranchRadiusTemp1, radiusBranch2.length - indexStart2 + radiusSegment.length, radiusBranch1.length - indexStart1);
                        modifyBranchRadius1 = modifyBranchRadiusTemp1;

                        System.arraycopy(modifyBranchSynapseTemp2, 0, modifyBranchSynapseTemp1, 0, synapseBranch2.length - indexStart2);
                        System.arraycopy(synapseSegment, 0, modifyBranchSynapseTemp1, synapseBranch2.length - indexStart2, synapseSegment.length);
                        System.arraycopy(synapseBranch1, indexStart1, modifyBranchSynapseTemp1, synapseBranch2.length - indexStart2 + synapseSegment.length, synapseBranch1.length - indexStart1);
                        modifyBranchSynapse1 = modifyBranchSynapseTemp1;

                        for (int i = 0; i < connectionBranch1.length; i++) {
                            if (! connectionBranch1[i].equals("0")) {
                                connectionBranch1[i] = connectionBranch1[i].concat("#data1");
                            }
                        }

                        for (int i = 0; i < modifyBranchConnectionTemp2.length; i++) {
                            if (! modifyBranchConnectionTemp2[i].equals("0")) {
                                modifyBranchConnectionTemp2[i] = modifyBranchConnectionTemp2[i].concat("#data2");
                            }
                        }

                        System.arraycopy(modifyBranchConnectionTemp2, 0, modifyBranchConnectionTemp1, 0, connectionBranch2.length - indexStart2);
                        System.arraycopy(connectionSegment, 0, modifyBranchConnectionTemp1, connectionBranch2.length - indexStart2, connectionSegment.length);
                        System.arraycopy(connectionBranch1, indexStart1, modifyBranchConnectionTemp1, connectionBranch2.length - indexStart2 + connectionSegment.length, connectionBranch1.length - indexStart1);
                        modifyBranchConnection1 = modifyBranchConnectionTemp1;
                    }
                }
            }
        }
    }

    public Integer[][] getModifyBranchCoordinate1() {
        return modifyBranchCoordinate1;
    }

    public String[] getModifyBranchType1() {
        return modifyBranchType1;
    }

    public Integer[] getModifyBranchRadius1() {
        return modifyBranchRadius1;
    }

    public Integer[] getModifyBranchSynapse1() {
        return modifyBranchSynapse1;
    }

    public String[] getModifyBranchConnection1() {
        return modifyBranchConnection1;
    }

}
