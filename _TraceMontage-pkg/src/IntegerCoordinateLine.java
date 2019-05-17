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
import java.util.List;

public class IntegerCoordinateLine {
    
    private List<Integer[]> integerCoordinateLine;
    private Integer[][] integerCoordinateLineInt;

    public IntegerCoordinateLine(Integer[] departurePoint, Integer[] destinationPoint) {
        this.integerCoordinateLine = new ArrayList<>();

        int dx = Math.abs(destinationPoint[0] - departurePoint[0]);
        int dy = Math.abs(destinationPoint[1] - departurePoint[1]);
        int dz = Math.abs(destinationPoint[2] - departurePoint[2]);

        int sx = (int) Math.signum( destinationPoint[0] - departurePoint[0] );
        int sy = (int) Math.signum( destinationPoint[1] - departurePoint[1] );
        int sz = (int) Math.signum( destinationPoint[2] - departurePoint[2] );

        if (dx == 0 && dy == 0 && dz == 0) {
            integerCoordinateLine.add(departurePoint);
        } else if (dx >= dy && dx >= dz) {
            double myx = (double) ((destinationPoint[1] - departurePoint[1])/(destinationPoint[0] - departurePoint[0]));
            double mzx = (double) ((destinationPoint[2] - departurePoint[2])/(destinationPoint[0] - departurePoint[0]));

            for (int x = departurePoint[0]; x != destinationPoint[0]; x = x + sx) {
                int y = (int) Math.round(departurePoint[1] + myx * (x - departurePoint[0]));
                int z = (int) Math.round(departurePoint[2] + mzx * (x - departurePoint[0]));

                integerCoordinateLine.add( new Integer[] {x,y,z} );
            }
        } else if (dy >= dx && dy >= dz) {
            double mxy = (double) ((destinationPoint[0] - departurePoint[0])/(destinationPoint[1] - departurePoint[1]));
            double mzy = (double) ((destinationPoint[2] - departurePoint[2])/(destinationPoint[1] - departurePoint[1]));

            for (int y = departurePoint[1]; y != destinationPoint[1]; y = y + sy) {
                int x = (int) Math.round(departurePoint[0] + mxy * (y - departurePoint[1]));
                int z = (int) Math.round(departurePoint[2] + mzy * (y - departurePoint[1]));

                integerCoordinateLine.add( new Integer[] {x,y,z} );
            }
        } else if (dz >= dx && dz >= dy) {

            double mxz = (double) ((destinationPoint[0] - departurePoint[0])/(destinationPoint[2] - departurePoint[2]));
            double myz = (double) ((destinationPoint[1] - departurePoint[1])/(destinationPoint[2] - departurePoint[2]));

            for (int z = departurePoint[2]; z != destinationPoint[2]; z = z + sz) {
                int x = (int) Math.round(departurePoint[0] + mxz * (z - departurePoint[2]));
                int y = (int) Math.round(departurePoint[1] + myz * (z - departurePoint[2]));

                integerCoordinateLine.add( new Integer[] {x,y,z} );
            }
        }
        
        this.integerCoordinateLine.add(destinationPoint);
        this.integerCoordinateLineInt = (Integer[][]) integerCoordinateLine.toArray();
    }

    public Integer[][] getIntegerCoordinateLine() {               
        return this.integerCoordinateLineInt;
    }

}
