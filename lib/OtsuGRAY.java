import org.hipi.image.FloatImage;
import java.awt.*;
import java.util.Arrays;
import javax.swing.JFrame;
import java.io.IOException;
import java.io.*;

/*
 * Contains function useful for Otsu Thresholding of given FloatImage
**/
public static FloatImage OtsuThresh_Gray(FloatImage value) 
{
	/*
	 * Applies thresholding on the basis of given threshold values
	 * 
	 * @params FloatImage value : Input Image in FloatImage format
	 * 
	 * 
	 */
	// Get dimensions of image
	int w = value.getWidth();
	int h = value.getHeight();

	// Get pointer to image data
	float[] valData = value.getData();

	// Initialize w*h element float array to hold threshold pixel value
	float[] thresholdData = new float[w*h];

	//get histogram
	int[] histData = getHistogram(valData);

	// Total number of pixels
	int total = valData.length;

	float sum = 0;
	for (int t=0 ; t<256 ; t++)
	{
	 	sum = sum + (t * histData[t]);
	}

	float sumB = 0;
	int wB = 0;
	int wF = 0;

	float varMax = 0;
	int threshold = 0;

	for (int t=0 ; t<256 ; t++) 
	{
		wB = wB + histData[t];               // Weight Background
		if (wB == 0) 
		{
			continue;
		}
		wF = total - wB;                 // Weight Foreground
		if (wF == 0)
		{
			break;
		}

		sumB = (float)(sumB + (t * histData[t]));

		float mB = sumB / wB;            // Mean Background
		float mF = (sum - sumB) / wF;    // Mean Foreground

		// Calculate Between Class Variance
		float varBetween = (float)wB * (float)wF * (mB - mF) * (mB - mF);

		// Check if new maximum found
		if (varBetween > varMax) 
		{
			varMax = varBetween;
			threshold = t;
		}
	}
	for(int j=0;j<h;j++)
	{
		for(int i=0;i<w;i++)
		{
			if(valData[j*w + i]<threshold)
			{
				thresholdData[j*w + i] = 0.0;				
			}
			else
			{
				thresholdData[j*w + i] = 255.0;				
			}
		}
	}

	
	//Converting the filtered array of modified image back to FloatImage 
	 // FloatImage finaldata : Output FloatImage where the output has to be returned	
	FloatImage result = new FloatImage( w,h, 1,thresholdData);
	return result;
	
}


public static int[] getHistogram(float[] valData) {
        int[] histogram = new int[256];
 
        for (int index = 0; index < valData.length; index++) 
	{
            histogram[(int)(valData[index]*255)]++;
        }
        return histogram;
    }

		
