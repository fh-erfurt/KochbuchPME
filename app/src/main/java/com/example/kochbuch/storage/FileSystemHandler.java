package com.example.kochbuch.storage;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class FileSystemHandler {

    private static final DateFormat format = DateFormat.getDateInstance();
    public static final String PREFIX_RECIPE_IMAGE_FILE = "RECIPE_PICTURE_";
    public static final String EXTENSION_RECIPE_IMAGE_FILE = ".jpg";



    public static File createImageFile(Context context) throws IOException {
        return createFile( context, PREFIX_RECIPE_IMAGE_FILE,
                EXTENSION_RECIPE_IMAGE_FILE, Environment.DIRECTORY_PICTURES );
    }

    public static boolean deleteFile( String filename ) {
        File file = new File( filename);
        return file.delete();
    }

    /*
        Private Helper to create files
     */
    private static File createFile( Context context, String prefix, String extension,
                                    String subdirectory ) throws IOException {
        String timeStamp = format.format(new Date());
        String imageFileName = prefix + timeStamp + "_";
        File storageDir = context.getExternalFilesDir( subdirectory );

        return File.createTempFile( imageFileName, extension, storageDir );
    }
}
