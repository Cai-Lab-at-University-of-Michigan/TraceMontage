/* COPYRIGHT Creative Commons Attribution-Non Commercial 4.0 International (CC BY-NC 4.0) */

/**
 * A class for reading the traced image from a tif-file.
 *
 * @author Aslan Satary Dizaji <asataryd@umich.edu>
 * <https://sites.google.com/a/umich.edu/aslansdizaji>
 * <https://github.com/aslansd/TraceMontage>
 * @version 1.1 Created on 2015/05 (last modified on 2018/12)
 */

import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageProcessor;
import java.util.ArrayList;
import java.util.List;

public class ReadTraceImageFile {

    private ArrayList<Double[]> tracedataNeuronColor = new ArrayList<>();
    private int imageSizeWidth;
    private int imageSizeHeight;
    private boolean traceimageFlag;

    public ReadTraceImageFile(ImagePlus image, List<String[]> tracedataNeuronBranch, List<Integer[][]> tracedataNeuronCoordinate) {

        try {
            int[] imageDimension = image.getDimensions();
            ImageStack imageStackNeuronColor = image.getStack();

            imageSizeWidth = imageDimension[0];
            imageSizeHeight = imageDimension[1];
            
            for (int i = 0; i < tracedataNeuronBranch.size(); i++) {
                
                if (tracedataNeuronBranch.get(i)[0].equals("Neurite")) {
                    int[] tracedataNeuronColorTemp1 = new int[imageDimension[2]];
                    Double[] tracedataNeuronColorTemp2 = new Double[imageDimension[2]];
                    
                    for (int j = 1; j <= imageDimension[2]; j++) {
                        for (int k = 0; k < tracedataNeuronCoordinate.get(i).length; k++) {
                            int index = image.getStackIndex(j, tracedataNeuronCoordinate.get(i)[k][2], 1);
                            ImageProcessor imageProcessorNeuronColor = imageStackNeuronColor.getProcessor(index);
                            tracedataNeuronColorTemp1[j - 1] = tracedataNeuronColorTemp1[j - 1] + imageProcessorNeuronColor.getPixel(tracedataNeuronCoordinate.get(i)[k][0], tracedataNeuronCoordinate.get(i)[k][1]);
                        }

                        tracedataNeuronColorTemp2[j - 1] = (double) tracedataNeuronColorTemp1[j - 1] / tracedataNeuronCoordinate.get(i).length;
                    }
                    
                    tracedataNeuronColor.add(tracedataNeuronColorTemp2);

                } else if (! tracedataNeuronBranch.get(i)[0].equals("Neurite")) {
                    Double[] tracedataNeuronColorTemp = new Double[imageDimension[2]];
                    
                    for (int j = 1; j <= imageDimension[2]; j++) {
                         tracedataNeuronColorTemp[j - 1] = Double.NaN;
                    }
                    
                    tracedataNeuronColor.add(tracedataNeuronColorTemp);
                }
            }

            traceimageFlag = true;
            image.close();            
        } catch (Exception e) {
            traceimageFlag = false;
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Double[]> getTracedataNeuronColor() {
        return tracedataNeuronColor;
    }

    public Integer getImageSizeWidth() {
        return imageSizeWidth;
    }

    public Integer getImageSizeHeight() {
        return imageSizeHeight;
    }

    public boolean getTraceimageFlag() {
        return traceimageFlag;
    }

}
