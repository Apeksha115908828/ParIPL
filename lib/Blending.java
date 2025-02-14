import org.hipi.image.FloatImage;
import java.awt.*;
import java.io.IOException;
import java.io.*;

/*
 * @author
 * 
 * */


/*
 * Contains function useful for dealing with blending of two floatimages
 **/
public static FloatImage Blending(FloatImage value1,FloatImage value2,float blendratio) 
{

	/*
	 * Applies blending using given blending ratio to two floatimages of identical size
	 * 
	 * @params FloatImage value1 : Input first Image in FloatImage format
	 * FloatImage value2 : Input second Image in FloatImage format
	 * float blendratio : value of blending ratio (value lies between 0 to 1)
	 * Return value FloatImage : Modified FloatImage which is the output to be returned
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
			filtered[(j*w+i)*3+0] = (blendratio)(valData1[(j*w+i)*3+0]*255) + (1-blendratio)(valData2[(j*w+i)*3+0]*255);
			if(filtered[(j*w+i)*3+0]>255)
			{
				filtered[(j*w+i)*3+0]=255;	
			}

			filtered[(j*w+i)*3+1] = (blendratio)(valData1[(j*w+i)*3+1]*255) + (1-blendratio)(valData2[(j*w+i)*3+1]*255);
			if(filtered[(j*w+i)*3+1]>255)
			{
				filtered[(j*w+i)*3+1]=255;	
			}

			filtered[(j*w+i)*3+2] = (blendratio)(valData1[(j*w+i)*3+2]*255) + (1-blendratio)(valData2[(j*w+i)*3+2]*255);
			if(filtered[(j*w+i)*3+2]>255)
			{
				filtered[(j*w+i)*3+2]=255;	
			}
		}
	}
	// Converting the filtered array back to floatimage
	FloatImage result = new FloatImage(w, h, 3,filtered);
	return(result);
}
