import org.hipi.image.FloatImage;
import java.awt.*;
import java.io.IOException;
import java.io.*;

/*
 * @author
 * 
 * */


/*
 * Contains function useful for dealing with subtraction of two floatimages
 **/
public static FloatImage Subtract2Images(FloatImage value1,FloatImage value2) 
{

	/*
	 * Applies subtraction of pixel value of two floatimages of identical size
	 * 
	 * @params FloatImage value1 : Input first Image in FloatImage format from which second floatimage will subtracted
	 * FloatImage value2 : Input second Image in FloatImage format which is to be subtracted
	 * Return value FloatImage : Modified FloatImage which is the output to be returned
	 * 
	 */
	
	// Get dimensions of image1(=image2)
	int w = value1.getWidth();
	int h = value1.getHeight();

	// Get pointer to image1 data
	float[] valData1 = value1.getData();

	// Get pointer to image2 data
	float[] valData2 = value2.getData();

	// Initialize w*h*3 element float array to hold modified pixel value
	float[] filtered = new float[w*h*3];

    	// Traverse image pixel data in raster-scan order and update
	for (int j = 0; j < h; j++) 
	{
		for (int i = 0; i < w; i++) 
		{
			filtered[(j*w+i)*3+0] = valData1[(j*w+i)*3+0]*255 - valData2[(j*w+i)*3+0]*255;
			if(filtered[(j*w+i)*3+0]<0)
			{
				filtered[(j*w+i)*3+0]=0;	
			}

			filtered[(j*w+i)*3+1] = valData1[(j*w+i)*3+1]*255 - valData2[(j*w+i)*3+1]*255;
			if(filtered[(j*w+i)*3+1]<0)
			{
				filtered[(j*w+i)*3+1]=0;	
			}

			filtered[(j*w+i)*3+2] = valData1[(j*w+i)*3+2]*255 - valData2[(j*w+i)*3+2]*255;
			if(filtered[(j*w+i)*3+2]<0)
			{
				filtered[(j*w+i)*3+2]=0;	
			}
			
		}
	}
	// Converting the filtered array back to floatimage
	FloatImage result = new FloatImage( w,h, 3,filtered);
	return(result);
}
