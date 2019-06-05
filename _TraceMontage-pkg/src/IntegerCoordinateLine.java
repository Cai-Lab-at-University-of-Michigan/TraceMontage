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
    
    private final Integer[][] integerCoordinateLineInt;

    /*
        calculates the gradient product of a given coordinate
    */
    private double grad( Integer[] start, Integer[] end, int axis1, int axis2 ) {
        double out = end[axis1] - start[axis1];
        out /= end[axis2] - start[axis2];
        
        return out;
    }
    
    public IntegerCoordinateLine(Integer[] start, Integer[] end) {
        List<Integer[]> integerCoordinateLine;
        integerCoordinateLine = new ArrayList<>();

        int dx = Math.abs(end[0] - start[0]);
        int dy = Math.abs(end[1] - start[1]);
        int dz = Math.abs(end[2] - start[2]);

        int sx = (int) Math.signum(end[0] - start[0] );
        int sy = (int) Math.signum(end[1] - start[1] );
        int sz = (int) Math.signum(end[2] - start[2] );
        
        double myx = grad(start, end, 1, 0 );
        double mxy = (myx != 0) ? 1.0 / myx : 0;
        double mzx = grad(start, end, 2, 0 );
        double mxz = (mzx != 0) ? 1.0 / mzx : 0;
        double mzy = grad(start, end, 2, 1 );
        double myz = (mzy != 0) ? 1.0 / mzy : 0;

        if (dx == 0 && dy == 0 && dz == 0) {
            integerCoordinateLine.add(start);
        } else if (dx >= dy && dx >= dz) {
            for (int x = start[0]; x < end[0]; x += sx) {
                int y = (int) Math.round(start[1] + myx * (x - start[0]));
                int z = (int) Math.round(start[2] + mzx * (x - start[0]));
                integerCoordinateLine.add( new Integer[] {x,y,z} );
            }
        } else if (dy >= dx && dy >= dz) {
            for (int y = start[1]; y < end[1]; y += sy) {
                int x = (int) Math.round(start[0] + mxy * (y - start[1]));
                int z = (int) Math.round(start[2] + mzy * (y - start[1]));
                integerCoordinateLine.add( new Integer[] {x,y,z} );
            }
        } else if (dz >= dx && dz >= dy) {
            for (int z = start[2]; z < end[2]; z += sz) {
                int x = (int) Math.round(start[0] + mxz * (z - start[2]));
                int y = (int) Math.round(start[1] + myz * (z - start[2]));
                integerCoordinateLine.add( new Integer[] {x,y,z} );
            }
        }
        integerCoordinateLine.add( end );
        
        // Copy temp. array to the static array
        this.integerCoordinateLineInt = new Integer[integerCoordinateLine.size()][];
        
        for( int i = 0; i < integerCoordinateLine.size(); i++ ) {
            Integer [] a = integerCoordinateLine.get(i);
            this.integerCoordinateLineInt[i] = a.clone();
        }
        
        // Delete the old temp. array
        System.gc();
    }

    public Integer[][] getIntegerCoordinateLine() {               
        return this.integerCoordinateLineInt;
    }

}
