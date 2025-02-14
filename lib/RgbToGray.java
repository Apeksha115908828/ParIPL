import org.hipi.image.FloatImage;
import java.awt.*;
import java.io.IOException;
import java.io.*;

/*
 * @author
 * 
 * */


/*
 * Contains function useful for converting the rgb scale floatimage to the gray scale floatimage
 **/

public static FloatImage RgbToGray(FloatImage value)
{

	/*
	 * Converts the given floatimage to gray scale floatimage
	 * 
	 @ params FloatImage value : Input Image in FloatImage format
	 * Return value FloatImage : Grayscale FloatImage which is the output to be returned
	 */
   	// Get dimensions of image
	int w = value.getWidth();
	int h = value.getHeight();

	// Get pointer to image data
	float[] valData = value.getData();

	// Initialize w*h element float array to hold gray pixel value
	float[] grayData = new float[w*h];

	// Traverse image pixel data in raster-scan order and update
	for (int j = 0; j < h; j++) {
 		for (int i = 0; i < w; i++) {
			grayData[j*w+i] = (float)0.299*valData[(j*w+i)*3+0]+(float)0.587*valData[(j*w+i)*3+1]+(float)0.114*valData[(j*w+i)*3+2]; // RGB
		}
	}

	// Converting the filtered array of modified image back to floatimage		
	FloatImage result = new FloatImage( w,h, 1,grayData);
	return(result);
} 

