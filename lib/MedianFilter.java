import org.hipi.image.FloatImage;
import java.awt.*;
import java.util.Arrays;
import javax.swing.JFrame;
import java.io.IOException;
import java.io.*;


public static int findmedian(int numarray[],int n)
{
	/*
	*Finds median of the given integer array 
	*	
	*@params int numarray[] : integer array
	* int n : size of the array
	*@return int : The median value of the array	
	*
	*/
	Arrays.sort(numarray);
	int median;
	if (n % 2 == 0)
	{            
	        median = (numarray[n/2] + numarray[(n/2) - 1])/2;
        }
	else
	{
           median = numarray[n/2];
        }
    	return(median);
}

/*
 * Contains function useful to blur the image by Median filtering
 **/
public static FloatImage filtermed(FloatImage value,int size) 
{
	/*
	 * Applies Median filter i.e. blurring effect to the given FloatImage
	 * 
	 * @params FloatImage value : Input Image in FloatImage format
	 * int size : size of median filter 
	 * Return value FloatImage : Modified FloatImage which is the output to be returned
	 * 
	 */

	//Filters for R,G,B
	int filter1[] = new int[1000];
	int filter2[] = new int[1000];
	int filter3[] = new int[1000];

	// Get dimensions of image
	int w = value.getWidth();
	int h = value.getHeight();

	// Get pointer to image data
	float[] valData = value.getData();


	// Initialize w*h*3 element float array to hold modified pixel value
	float[] filtered = new float[w*h*3];
	int k,l;

	// Traverse image pixel data in raster-scan order and update
	for (int j = 0; j < h; j++) 
	{
        	for (int i = 0; i < w; i++) 
		{
			for(k=j-(size/2);k<=j+(size/2);k++)
			{
				for(l=i-(size/2);l<=i+(size/2);l++)
				{
					if(k>=0 && k<h && l>=0 && l<w)
					{

						filter1[(k-(j-(size/2)))*size+(l-(i-(size/2)))]=(int)((float)valData[(k*w + l)*3+0]*(float)255.0);
						filter2[(k-(j-(size/2)))*size+(l-(i-(size/2)))]=(int)((float)valData[(k*w + l)*3+1]*(float)255.0);
						filter3[(k-(j-(size/2)))*size+(l-(i-(size/2)))]=(int)((float)valData[(k*w + l)*3+2]*(float)255.0);
					}
				}
			}
			int median1;
			median1 = findmedian(filter1,size*size);
			int median2;
			median2 = findmedian(filter2,size*size);    
			int median3;
			median3 = findmedian(filter3,size*size);

			filtered[(j*w+i)*3+0]=(float)median1;
			filtered[(j*w+i)*3+1]=(float)median2;
			filtered[(j*w+i)*3+2]=(float)median3;
	    	}
	}
	
	//Converting the filtered array of modified image back to FloatImage 	
	FloatImage result = new FloatImage( w,h, 3,filtered);
	return(result);

}
