import org.hipi.image.FloatImage;
import java.awt.*;
import java.util.Arrays;
import javax.swing.JFrame;
import java.io.IOException;
import java.io.*;

/*
 * Contains function useful for Otsu Thresholding of given FloatImage
**/
public static FloatImage OtsuThresh_RGB(FloatImage value) 
{
	/*
	 * Applies thresholding on the basis of given threshold values
	 * 
	 * @params FloatImage value : Input Image in FloatImage format
	 * 
	 * Return value FloatImage : Modified FloatImage which is the output to be returned
	 * 
	 */
	
	// Get dimensions of image
	int w = value.getWidth();
	int h = value.getHeight();

	// Get pointer to image data
	float[] valData = value.getData();

	// Initialize w*h*3 element float array to hold threshold pixel value
	int[] thresholdData = new int[w*h*3];

	char r=R;
	char g=G;
	char b=B;

	//get histogram for r,g,b components
	int[] histDataR = getHistogram(valData,w,h,R);
	int[] histDataG = getHistogram(valData,w,h,G);
	int[] histDataB = getHistogram(valData,w,h,B);
	
	// Total number of pixels
	int total = h*w;

	//calculate threshold values
	int Tr = getThresholdValue(histDataR,total);
	int Tg = getThresholdValue(histDataG,total);
	int Tb = getThresholdValue(histDataB,total);

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
	FloatImage result = new FloatImage(w,h, 3,thresholdData);
	return(result);
}



public static int[] getHistogram(float[] valData,int width,int height,char color) 
{
        int[] histogram = new int[256];
	
	if(color=='R')
	{
		for (int j=0;j<h;j++)
		{
			for(int i=0;i<w;i++) 
			{
			    histogram[(int)(valData[(j*w+i)*3+0]*255)]++;
			}
		}
	}
	else if(color=='G')
	{
		for (int j=0;j<h;j++)
		{
			for(int i=0;i<w;i++) 
			{
			    histogram[(int)(valData[(j*w+i)*3+1]*255)]++;
			}
		}
	}
	else 
	{
		for (int j=0;j<h;j++)
		{
			for(int i=0;i<w;i++) 
			{
			    histogram[(int)(valData[(j*w+i)*3+2]*255)]++;
			}
		}
	}
 	return histogram;
}

public static int getThresholdValue(int[] histData,int total)
{
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
		// Weight Background
		wB = wB + histData[t];           
		if (wB == 0) 
		{
			continue;
		}
		// Weight Foreground
		wF = total - wB;                 
		if (wF == 0)
		{
			break;
		}

		sumB = (float)(sumB + (t * histData[t]));

		// Mean Background
		float mB = sumB / wB;  
		// Mean Foreground         
		float mF = (sum - sumB) / wF;    

		// Calculate Between Class Variance
		float varBetween = (float)wB * (float)wF * (mB - mF) * (mB - mF);

		// Check if new maximum found
		if (varBetween > varMax) 
		{
			varMax = varBetween;
			threshold = t;
		}
	}
	return threshold;	
}		
