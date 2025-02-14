import org.hipi.image.FloatImage;
import java.awt.*;
import java.util.Arrays;
import javax.swing.JFrame;
import java.io.IOException;
import java.io.*;

/*
 * Contains function useful for Thresholding of given FloatImage
**/
public static FloatImage Threshold_RGB(FloatImage value,int Tr,int Tg,int Tb) 
{
	/*
	 * Applies thresholding on the basis of given threshold values
	 * 
	 * @params FloatImage value : Input Image in FloatImage format
	 * int Tr : Threshold Value for Red colour
	 * int Tg : Threshold Value for Green colour
	 * int Tb : Threshold Value for Blue colour
	 * Return value FloatImage : Modified FloatImage which is the output to be returned
	 * 
	*/
	// Get dimensions of image
	int w = value.getWidth();
	int h = value.getHeight();

	// Get pointer to image data
	float[] valData = value.getData();

	// Initialize w*h*3 element float array to hold threshold pixel value
	float[] thresholdData = new float[w*h*3];

	// Traverse image pixel data in raster-scan order and update
	for (int j = 0; j < h; j++) 
	{
		for (int i = 0; i < w; i++) 
		{
			if((int)(valData[(j*w+i)*3 + 0]*255) > Tr)
			{
				thresholdData[(j*w+i)*3 + 0] = 255.0;
			}
			else
			{
				thresholdData[(j*w+i)*3 + 0] = 0.0;
			}
			if((int)(valData[(j*w+i)*3 + 1]*255) > Tg)
			{
				thresholdData[(j*w+i)*3 + 1] = 255.0;
			}
			else
			{
				thresholdData[(j*w+i)*3 + 1] = 0.0;
			}
			if((int)(valData[(j*w+i)*3 + 2]*255) > Tb)
			{
				thresholdData[(j*w+i)*3 + 2] = 255.0;
			}
			else
			{
				thresholdData[(j*w+i)*3 + 2] = 0.0;
			}
		}
	}
	//Converting the filtered array of modified image back to FloatImage 	
	FloatImage result = new FloatImage(w,h, 3,threshold);
	return(result);
}
		
