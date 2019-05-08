/* COPYRIGHT Creative Commons Attribution-Non Commercial 4.0 International (CC BY-NC 4.0) */

/**
 * A custom zip/swc FileFilter for FileChooser.
 *
 * @author Aslan Satary Dizaji <asataryd@umich.edu>
 * <https://sites.google.com/a/umich.edu/aslansdizaji>
 * <https://github.com/aslansd/TraceMontage>
 * @version 1.1 Created on 2015/05 (last modified on 2018/12)
 */

import java.io.File;

public class CustomZipSWCFileFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        return file.isDirectory() || file.getAbsolutePath().endsWith(".zip") || file.getAbsolutePath().endsWith(".swc");
    }

    @Override
    public String getDescription() {
        return "Documents (*.zip/swc)";
    }

}
