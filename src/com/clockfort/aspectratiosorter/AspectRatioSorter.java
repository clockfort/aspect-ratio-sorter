package com.clockfort.aspectratiosorter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class AspectRatioSorter {

	public static List<ImageDimension> commonAspectRatios = new ArrayList<ImageDimension>();
	
	public static void main(String[] args) {
		String folderName = "";
		if (args.length == 1) {
			folderName = args[0];
		} else {
			System.out.println("Usage: AspectRatioSorter <folder>");
			System.exit(1);
		}
		
		File folder = new File(folderName);
		
		if (!folder.isDirectory()) {
			System.exit(2);
		}
		
		populateWithCommonValues(commonAspectRatios);
		
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
				System.out.println("Trying file " + file);
				BufferedImage image;
				boolean movedFile = false;
				try {
					image = ImageIO.read(file);
					ImageDimension imageDimension = new ImageDimension(image);
					System.out.println("Image has dimensions " + imageDimension);
					for (ImageDimension commonAspectRatio : commonAspectRatios) {
						if (imageDimension.hasSameApproximateRatio(commonAspectRatio)) {
							File subDirectory = new File(folder, commonAspectRatio.toString());
							subDirectory.mkdir();
							if (file.renameTo(new File(subDirectory + File.separator + file.getName()))) {
								System.out.println("Moved " + file.getName());
								movedFile = true;
							} else {
								System.out.println("Error moving file " + file.getName());
							}
						}
						if (movedFile) {
							System.out.println("Moved file " + file);
							movedFile = false;
							break;
						}
					}
				} catch (IOException io) {
					System.out.println("Couldn't read " + file.getName());
				}
			}
		}
		System.exit(0);
	}
	
	public static void populateWithCommonValues(List<ImageDimension> aspectRatios) {
		aspectRatios.add(new ImageDimension(3.5, 5.0));
		aspectRatios.add(new ImageDimension(4.0, 5.0));
		aspectRatios.add(new ImageDimension(4.0, 6.0));
		aspectRatios.add(new ImageDimension(5.0, 5.0));
		aspectRatios.add(new ImageDimension(5.0, 7.0));
		aspectRatios.add(new ImageDimension(7.0, 10.0));
		aspectRatios.add(new ImageDimension(8.0, 10.0));
		aspectRatios.add(new ImageDimension(8.0, 12.0));
		aspectRatios.add(new ImageDimension(8.0, 16.0));
	}
}
