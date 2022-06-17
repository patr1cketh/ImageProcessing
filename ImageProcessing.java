/*
 * This project is forked from boilerplate provided by codecadamy.com
 */
 

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;

public class ImageProcessing {
	
    public static void main(String[] args) {
    // The provided images are apple.jpg, flower.jpg, and kitten.jpg
		int[][] imageData = imgToTwoD("./apple.jpg");
    // Or load your own image using a URL!
		//int[][] imageData = imgToTwoD("https://content.codecademy.com/projects/project_thumbnails/phaser/bug-dodger.png");
		//viewImageData(imageData);
    
    //trim borders
		//int[][] trimmed = trimBorders(imageData, 60);
		//twoDToImage(trimmed, "./trimmed_apple.jpg");

    //negative
        //int[][] negative = negativeColor(imageData);
        //twoDToImage(negative, "./negative_apple.jpg");

    //stretch horizontally
        //int[][] stretched = stretchHorizontally(imageData);
        //twoDToImage(stretched, "./stretched_apple.jpg");

    //shrink vertically
        // int[][] shrunk = shrinkVertically(imageData);
        // twoDToImage(shrunk, "./shrunk_apple.jpg");

    //invert
       // int[][] inverted = invertImage(imageData);
       // twoDToImage(inverted, "./inverted_apple.jpg");

    //color filter
        int[][] filtered = colorFilter(imageData, 100, 77, -130);
        twoDToImage(filtered, "./filtered_apple.jpg");


		// int[][] allFilters = stretchHorizontally(shrinkVertically(colorFilter(negativeColor(trimBorders(invertImage(imageData), 50)), 200, 20, 40)));
		// Painting with pixels
	}

	// Image Processing Methods

    // trim borders
	public static int[][] trimBorders(int[][] imageTwoD, int pixelCount) {
		
		if (imageTwoD.length > pixelCount * 2 && imageTwoD[0].length > pixelCount * 2) {
			int[][] trimmedImg = new int[imageTwoD.length - pixelCount * 2][imageTwoD[0].length - pixelCount * 2];
			for (int i = 0; i < trimmedImg.length; i++) {
				for (int j = 0; j < trimmedImg[i].length; j++) {
					trimmedImg[i][j] = imageTwoD[i + pixelCount][j + pixelCount];
				}
			}
			return trimmedImg;
		} else {
			System.out.println("Cannot trim that many pixels from the given image.");
			return imageTwoD;
		}
	}

    // negative
	public static int[][] negativeColor(int[][] imageTwoD) {
		
    int[][] negImage = new int[imageTwoD.length][imageTwoD[0].length];
    for (int i = 0; i < imageTwoD.length; i++) {
      for (int j = 0; j < imageTwoD[0].length; j++) {
        int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
        rgba[0] = 255 - rgba[0];
        rgba[1] = 255 - rgba[1];
        rgba[2] = 255 - rgba[2];
        negImage[i][j] = getColorIntValFromRGBA(rgba);
      }
    }
		return negImage;
	}
    
	public static int[][] stretchHorizontally(int[][] imageTwoD) {
		
    int[][] stretchedImage = new int[imageTwoD.length][imageTwoD[0].length*2];
    //additional iterator for extra pixels
    int it = 0;
    for (int i = 0; i < imageTwoD.length; i++) {
      for (int j = 0; j < imageTwoD[0].length; j++) {
        it = j * 2;
        stretchedImage[i][it] = imageTwoD[i][j];
        stretchedImage[i][it + 1] = imageTwoD[i][j];
      }
    }
		return stretchedImage;
	}
	public static int[][] shrinkVertically(int[][] imageTwoD) {
		int[][] shrunkImage = new int[imageTwoD.length / 2][imageTwoD[0].length];
    //column-major order    
    for (int i = 0; i < imageTwoD[0].length; i++) {
      //inner loop iterator increments by 2
      //-1 to length in case total pixels is odd number
      for (int j = 0; j < imageTwoD.length-1; j+=2) {
        //copy pixel to row index / 2 in new image
        shrunkImage[j/2][i] = imageTwoD[j][i];
      }
    }
		return shrunkImage;
	}
	public static int[][] invertImage(int[][] imageTwoD) {
		// TODO: Fill in the code for this method
    int[][] invertedImage = new int[imageTwoD.length][imageTwoD[0].length];
    for (int i = 0; i < imageTwoD.length; i++) {
      for (int j = 0; j < imageTwoD[0].length; j++) {
        invertedImage[i][j] = imageTwoD[(imageTwoD.length-1)-i][(imageTwoD[i].length-1)-j];
      }
    }
		return invertedImage;
	}
	public static int[][] colorFilter(int[][] imageTwoD, int redChangeValue, int greenChangeValue, int blueChangeValue) {
		// TODO: Fill in the code for this method
    int[][] filteredImage = new int[imageTwoD.length][imageTwoD[0].length];
    for (int i = 0; i < imageTwoD.length; i++) {
       for (int j = 0; j < imageTwoD[0].length; j++) {
         //get rgb values from pixel
         int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
         //add modifiers
         int newRed = rgba[0] + redChangeValue;
         int newGreen = rgba[1] + greenChangeValue;
         int newBlue = rgba[2] + blueChangeValue;
         //check the rgb values are with range 0 - 255
         if (newRed > 255) {
           newRed = 255;
         } else if (newRed < 0) {
           newRed = 0;
         }
         if (newGreen > 255) {
           newGreen = 255;
         } else if (newGreen < 0) {
           newGreen = 0;
         }
         if (newBlue > 255) {
           newBlue = 255;
         } else if (newBlue < 0) {
           newBlue = 0;
         }
         //set rgba array values to new values
         rgba[0] = newRed;
         rgba[1] = newGreen;
         rgba[2] = newBlue;
         //convert back to int and set pixel value of new image
         filteredImage[i][j] = getColorIntValFromRGBA(rgba);
       }
    }
		return filteredImage;
	}
	// Painting Methods
	public static int[][] paintRandomImage(int[][] canvas) {
		// TODO: Fill in the code for this method
    // int[][] invertedImage = new int[imageTwoD.length][imageTwoD[0].length];
    // for (int i = 0; i < imageTwoD.length; i++) {
    //   for (int j = 0; j < imageTwoD[0].length; j++) {
    //     invertedImage[i][j] = imageTwoD[(imageTwoD.length-1)-i][(imageTwoD[i].length-1)-j];
		return null;
	}
	public static int[][] paintRectangle(int[][] canvas, int width, int height, int rowPosition, int colPosition, int color) {
		// TODO: Fill in the code for this method
    // int[][] invertedImage = new int[imageTwoD.length][imageTwoD[0].length];
    // for (int i = 0; i < imageTwoD.length; i++) {
    //   for (int j = 0; j < imageTwoD[0].length; j++) {
    //     invertedImage[i][j] = imageTwoD[(imageTwoD.length-1)-i][(imageTwoD[i].length-1)-j];
		return null;
	}
	public static int[][] generateRectangles(int[][] canvas, int numRectangles) {
		// TODO: Fill in the code for this method
    // int[][] invertedImage = new int[imageTwoD.length][imageTwoD[0].length];
    // for (int i = 0; i < imageTwoD.length; i++) {
    //   for (int j = 0; j < imageTwoD[0].length; j++) {
    //     invertedImage[i][j] = imageTwoD[(imageTwoD.length-1)-i][(imageTwoD[i].length-1)-j];
		return null;
	}
	// Utility Methods
	public static int[][] imgToTwoD(String inputFileOrLink) {
		try {
			BufferedImage image = null;
			if (inputFileOrLink.substring(0, 4).toLowerCase().equals("http")) {
				URL imageUrl = new URL(inputFileOrLink);
				image = ImageIO.read(imageUrl);
				if (image == null) {
					System.out.println("Failed to get image from provided URL.");
				}
			} else {
				image = ImageIO.read(new File(inputFileOrLink));
			}
			int imgRows = image.getHeight();
			int imgCols = image.getWidth();
			int[][] pixelData = new int[imgRows][imgCols];
			for (int i = 0; i < imgRows; i++) {
				for (int j = 0; j < imgCols; j++) {
					pixelData[i][j] = image.getRGB(j, i);
				}
			}
			return pixelData;
		} catch (Exception e) {
			System.out.println("Failed to load image: " + e.getLocalizedMessage());
			return null;
		}
	}
	public static void twoDToImage(int[][] imgData, String fileName) {
		try {
			int imgRows = imgData.length;
			int imgCols = imgData[0].length;
			BufferedImage result = new BufferedImage(imgCols, imgRows, BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < imgRows; i++) {
				for (int j = 0; j < imgCols; j++) {
					result.setRGB(j, i, imgData[i][j]);
				}
			}
			File output = new File(fileName);
			ImageIO.write(result, "jpg", output);
		} catch (Exception e) {
			System.out.println("Failed to save image: " + e.getLocalizedMessage());
		}
	}
	public static int[] getRGBAFromPixel(int pixelColorValue) {
		Color pixelColor = new Color(pixelColorValue);
		return new int[] { pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelColor.getAlpha() };
	}
	public static int getColorIntValFromRGBA(int[] colorData) {
		if (colorData.length == 4) {
			Color color = new Color(colorData[0], colorData[1], colorData[2], colorData[3]);
			return color.getRGB();
		} else {
			System.out.println("Incorrect number of elements in RGBA array.");
			return -1;
		}
	}
	public static void viewImageData(int[][] imageTwoD) {
		if (imageTwoD.length > 3 && imageTwoD[0].length > 3) {
			int[][] rawPixels = new int[3][3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					rawPixels[i][j] = imageTwoD[i][j];
				}
			}
			System.out.println("Raw pixel data from the top left corner.");
			System.out.print(Arrays.deepToString(rawPixels).replace("],", "],\n") + "\n");
			int[][][] rgbPixels = new int[3][3][4];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					rgbPixels[i][j] = getRGBAFromPixel(imageTwoD[i][j]);
				}
			}
			System.out.println();
			System.out.println("Extracted RGBA pixel data from top the left corner.");
			for (int[][] row : rgbPixels) {
				System.out.print(Arrays.deepToString(row) + System.lineSeparator());
			}
		} else {
			System.out.println("The image is not large enough to extract 9 pixels from the top left corner");
		}
	}
}