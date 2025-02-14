import org.hipi.image.FloatImage;
import java.awt.*;
import java.io.IOException;
import java.io.*;

/*
 * @author
 * 
 * */


/*
 * Contains function useful for dealing with divison of a constant value to floatimage
 **/
public static FloatImage DivideConstant(FloatImage value,int constant) 
{

	/*
	 * Applies divison by a constant value to each pixel value of floatimage
	 * 
	 * @params FloatImage value : Input Image in FloatImage format
	 * int constant : constant value as a divisor
	 * 
	 */
	
	// Get dimensions of image
	int w = value.getWidth();
	int h = value.getHeight();

	// Get pointer to image data
	float[] valData = value.getData();

	// Initialize w*h*3 element float array to hold modified pixel value
	float[] filtered = new float[w*h*3];


    	// Traverse image pixel data in raster-scan order and update
	for (int j = 0; j < h; j++) 
	{
		for (int i = 0; i < w; i++) 
		{
			filtered[(j*w+i)*3+0] = (valData[(j*w+i)*3+0]*255) / constant;
			
			filtered[(j*w+i)*3+1] = (valData[(j*w+i)*3+1]*255) / constant;
			
			filtered[(j*w+i)*3+2] = (valData[(j*w+i)*3+2]*255) / constant;
			
		}
	}
	// Converting the filtered array back to floatimage
	//FloatImage result : Modified FloatImage where the output has to be returned
	FloatImage result = new FloatImage( w,h, 3,filtered);
	return result;
	
}
