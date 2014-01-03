package aa.aa.aa;

import java.io.IOException;

public class lbp 
{
	public static int IMAGESIZE_ROI=25;
	public static int IMAGESIZE=100;
	int  []UniformPattern59=
		{
			1,   2,   3,   4,   5,   0,   6,   7,   8,   0,   0,   0,   9,   0,  10,  11,
			12,   0,   0,   0,   0,   0,   0,   0,  13,   0,   0,   0,  14,   0,  15,  16,
			17,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
			18,   0,   0,   0,   0,   0,   0,   0,  19,   0,   0,   0,  20,   0,  21,  22,
			23,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
			0,    0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
			24,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
			25,   0,   0,   0,   0,   0,   0,   0,  26,   0,   0,   0,  27,   0,  28,  29,
			30,  31,   0,  32,   0,   0,   0,  33,   0,   0,   0,   0,   0,   0,   0,  34,
			0,    0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  35,
			0,    0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,
			0,    0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  36,
			37,  38,   0,  39,   0,   0,   0,  40,   0,   0,   0,   0,   0,   0,   0,  41,
			0,    0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,  42,
			43,  44,   0,  45,   0,   0,   0,  46,   0,   0,   0,   0,   0,   0,   0,  47,
			48,  49,   0,  50,   0,   0,   0,  51,  52,  53,   0,  54,  55,  56,  57,  58
		};
	
	public void grid(int j[],int width,int heigh) throws IOException
	{
		int[] a=new int[IMAGESIZE_ROI*IMAGESIZE_ROI];
		int count=0;
		for (int x_steps=0; x_steps!=IMAGESIZE/IMAGESIZE_ROI; ++x_steps)
		{
			for (int y_steps=0; y_steps!=IMAGESIZE/IMAGESIZE_ROI; ++y_steps)
			{
				for (int y=0; y!=IMAGESIZE_ROI; y++)
				{
					for (int x=0; x!=IMAGESIZE_ROI; x++)
					{
						a[count++]=j[(x_steps*IMAGESIZE/IMAGESIZE_ROI*625)+y_steps*625+y*IMAGESIZE_ROI+x];
						if(count==625)
							{
								count=0;
							}
					}
				}
				cal_lbp( a );																					
			}
		}
		
	}
	public void cal_lbp(int j[])
	{
		int[] hist = new int[256];
		for(int i=0;i!=256;++i)
		{
			hist[i]=0;
		}
		int lbp_59bins = 0;
		lbp_histogram( j,IMAGESIZE_ROI,IMAGESIZE_ROI,hist,0);

		for (int i=0; i!=256;i++)
		{
			if ( UniformPattern59[i] != 0 )
			{
				IO tmp = new IO();
				try {
					tmp.saveFileToSDCard("result.txt", Integer.toString(hist[i]));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//printf("%d ",hist[i]);
			}
			else
			{
				lbp_59bins += hist[i];
			}
		}
		
		IO tmp1 = new IO();
		try {
			tmp1.saveFileToSDCard("result.txt", Integer.toString(lbp_59bins));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//printf("%d ",lbp_59bins);
	}
	void lbp_histogram(int a[],int rows,int columns,int result[],int flag)
	{
		
		int predicate = 1 ;
		int leap = columns*predicate;
		
		int
			p0 = 0,
			p1 = p0 + predicate,
			p2 = p1 + predicate,
			p3 = p2 + leap,
			p4 = p3 + leap,
			p5 = p4 - predicate,
			p6 = p5 - predicate,
			p7 = p6 - leap,
			center = p7 + predicate;
		int value;
		int pred2 = predicate << 1;
		int r,c;
				
		if (flag==0)
			{
				for (r=0;r<rows-pred2;r++)
					{
						for (c=0;c<columns-pred2;c++)
							{
								value = 0;
								value |= ((a[center] - a[p0] - 1) & 0x80000000) >> (31-0);
								p0++;
								value |= ((a[center] - a[p1] - 1) & 0x80000000) >> (31-1);
								p1++;
								value |= ((a[center] - a[p2] - 1) & 0x80000000) >> (31-2);
								p2++;
								value |= ((a[center] - a[p3] - 1) & 0x80000000) >> (31-3);
								p3++;
								value |= ((a[center] - a[p4] - 1) & 0x80000000) >> (31-4);
								p4++;
								value |= ((a[center] - a[p5] - 1) & 0x80000000) >> (31-5);
								p5++;
								value |= ((a[center] - a[p6] - 1) & 0x80000000) >> (31-6);
								p6++;
								value |= ((a[center] - a[p7] - 1) & 0x80000000) >> (31-7);
								p7++;
								center++;
								/*
								if(value<0)
								{
									IO tmp = new IO();
									try {
										tmp.saveFileToSDCard("result.txt", "OK");
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								*/
								//result[0]=0;
								result[value+128]++; 
							}
						p0 += pred2;
						p1 += pred2;
						p2 += pred2;
						p3 += pred2;
						p4 += pred2;
						p5 += pred2;
						p6 += pred2;
						p7 += pred2;
						center += pred2;
					}
			}	
	}
}
