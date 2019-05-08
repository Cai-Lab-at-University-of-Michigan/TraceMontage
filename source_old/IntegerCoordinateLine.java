/* COPYRIGHT Creative Commons Attribution-Non Commercial 4.0 International (CC BY-NC 4.0) */

/**
 * A class for drawing integer-coordinate line between two points.
 *
 * @author Aslan Satary Dizaji <asataryd@umich.edu>
 * <https://sites.google.com/a/umich.edu/aslansdizaji>
 * <https://github.com/aslansd/TraceMontage>
 * @version 1.1 Created on 2015/05 (last modified on 2018/12)
 */

import java.util.ArrayList;

public class IntegerCoordinateLine {
    
    private ArrayList<Integer[]> integerCoordinateLine = new ArrayList<Integer[]>();

    public IntegerCoordinateLine(Integer[] departurePoint, Integer[] destinationPoint) {

        Integer dx = Math.abs(destinationPoint[0] - departurePoint[0]);
        Integer dy = Math.abs(destinationPoint[1] - departurePoint[1]);
        Integer dz = Math.abs(destinationPoint[2] - departurePoint[2]);

        Integer sx = (int) Math.signum((double) (destinationPoint[0] - departurePoint[0]));
        Integer sy = (int) Math.signum((double) (destinationPoint[1] - departurePoint[1]));
        Integer sz = (int) Math.signum((double) (destinationPoint[2] - departurePoint[2]));

        if (dx == 0 && dy == 0 && dz == 0) {
            integerCoordinateLine.add(departurePoint);
            integerCoordinateLine.add(destinationPoint);
        } else if (dx >= dy && dx >= dz) {

            Double myx = (double) ((destinationPoint[1] - departurePoint[1])/(destinationPoint[0] - departurePoint[0]));
            Double mzx = (double) ((destinationPoint[2] - departurePoint[2])/(destinationPoint[0] - departurePoint[0]));

            for (int x = departurePoint[0]; x != destinationPoint[0]; x = x + sx) {
                Integer y = (int) Math.round(departurePoint[1] + myx * (x - departurePoint[0]));
                Integer z = (int) Math.round(departurePoint[2] + mzx * (x - departurePoint[0]));

                Integer[] integerCoordinateLineTemp = new Integer[3];
                integerCoordinateLineTemp[0] = x;
                integerCoordinateLineTemp[1] = y;
                integerCoordinateLineTemp[2] = z;

                integerCoordinateLine.add(integerCoordinateLineTemp);
            }

            integerCoordinateLine.add(destinationPoint);

        } else if (dy >= dx && dy >= dz) {

            Double mxy = (double) ((destinationPoint[0] - departurePoint[0])/(destinationPoint[1] - departurePoint[1]));
            Double mzy = (double) ((destinationPoint[2] - departurePoint[2])/(destinationPoint[1] - departurePoint[1]));

            for (int y = departurePoint[1]; y != destinationPoint[1]; y = y + sy) {
                Integer x = (int) Math.round(departurePoint[0] + mxy * (y - departurePoint[1]));
                Integer z = (int) Math.round(departurePoint[2] + mzy * (y - departurePoint[1]));

                Integer[] integerCoordinateLineTemp = new Integer[3];
                integerCoordinateLineTemp[0] = x;
                integerCoordinateLineTemp[1] = y;
                integerCoordinateLineTemp[2] = z;

                integerCoordinateLine.add(integerCoordinateLineTemp);
            }

            integerCoordinateLine.add(destinationPoint);

        } else if (dz >= dx && dz >= dy) {

            Double mxz = (double) ((destinationPoint[0] - departurePoint[0])/(destinationPoint[2] - departurePoint[2]));
            Double myz = (double) ((destinationPoint[1] - departurePoint[1])/(destinationPoint[2] - departurePoint[2]));

            for (int z = departurePoint[2]; z != destinationPoint[2]; z = z + sz) {
                Integer x = (int) Math.round(departurePoint[0] + mxz * (z - departurePoint[2]));
                Integer y = (int) Math.round(departurePoint[1] + myz * (z - departurePoint[2]));

                Integer[] integerCoordinateLineTemp = new Integer[3];
                integerCoordinateLineTemp[0] = x;
                integerCoordinateLineTemp[1] = y;
                integerCoordinateLineTemp[2] = z;

                integerCoordinateLine.add(integerCoordinateLineTemp);
            }

            integerCoordinateLine.add(destinationPoint);
        }
    }

    public Integer[][] getIntegerCoordinateLine() {
        
        Integer[][] integerCoordinateLineArray = new Integer[integerCoordinateLine.size()][3];
        
        for (int i = 0; i < integerCoordinateLine.size(); i++) {
            integerCoordinateLineArray[i] = integerCoordinateLine.get(i);
        }
               
        return integerCoordinateLineArray;
    }

}
