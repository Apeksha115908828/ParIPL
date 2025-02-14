import org.hipi.image.FloatImage;
import java.awt.*;
import java.util.Arrays;
import javax.swing.JFrame;
import java.io.IOException;
import java.io.*;


/*
 * Contains function useful to reduce noise by conservative filtering
 **/

public static FloatImage Conservative_Filter(FloatImage value) 
{
	/*
	 * Converts input FloatImage to conservative filtered image 
	 * @params FloatImage value : Input Image in FloatImage format
	 * 
	 */
	
		
	//Filters for R,G,B
	int filter1[] = new int[1000];  //for red band
	int filter2[] = new int[1000];  //for green band
	int filter3[] = new int[1000];  //for blue band

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
			int min1,max1;
			min1=findmin(filter1,size*size,(int)((float)(valData[(j*w+i)*3+0])*255));
			max1=findmax(filter1,size*size,(int)((float)(valData[(j*w+i)*3+0])*255));
			int min2,max2;
			min2=findmin(filter2,size*size,(int)((float)(valData[(j*w+i)*3+1])*255));
			max2=findmax(filter2,size*size,(int)((float)(valData[(j*w+i)*3+1])*255));
			int min3,max3;
			min3=findmin(filter3,size*size,(int)((float)(valData[(j*w+i)*3+2])*255));
			max3=findmax(filter3,size*size,(int)((float)(valData[(j*w+i)*3+2])*255));	

			if((float)(valData[(j*w+i)*3+0]*255)<min1)
			{
			       filtered[(j*w+i)*3+0]=(float)min1;
			}
			else if(((int)(float)valData[(j*w+i)*3+0]*255)>max1)
			{	
				filtered[(j*w+i)*3+0]=(float)max1;
			}

			if(((int)(float)valData[(j*w+i)*3+1]*255)<min2)
			{
				filtered[(j*w+i)*3+1]=(float)min2;
			}
			else if(((int)(float)valData[(j*w+i)*3+1]*255)>max2)
			{	
				filtered[(j*w+i)*3+1]=(float)max2;
			}

			if(((int)(float)valData[(j*w+i)*3+2]*255)<min3)
			{
				filtered[(j*w+i)*3+2]=(float)min3;
			}
			else if(((int)(float)valData[(j*w+i)*3+2]*255)>max3)
			{	
				filtered[(j*w+i)*3+2]=(float)max3;
			}

		}
	}
	//Converting the filtered array of modified image back to FloatImage
	//FloatImage result : Output FloatImage where the output has to be returned 
	FloatImage result = new FloatImage(w,h,3,filtered);
	return result;
	
}

//Finds minimum in the array except the midval
public static int findmin(int array[],int n,int midval)
{
	Arrays.sort(array);
	int min=array[0];
	if(min == midval)
	{
		min=array[1];
	}
	return min;
}	
//Find maximum in the array except the midval
public static int findmax(int array[],int n,int midval)
{	
	Arrays.sort(array);

	int max=array[n-1];
	if(max == midval)
	{
		max=array[n-2];	
	}
	return max;
}	
