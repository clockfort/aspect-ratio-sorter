package com.clockfort.aspectratiosorter;

import java.awt.image.BufferedImage;

public class ImageDimension {
	Double width, height;
	public static final double allowedDivergence = 0.02;
	
	public ImageDimension(Double width, Double height) {
		this.width = width;
		this.height = height;
	}
	
	public ImageDimension(BufferedImage image) {
		this.width = (double) image.getWidth();
		this.height = (double) image.getHeight();
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}
	
	public ImageDimension getRotatedVersion() {
		return new ImageDimension(height, width);
	}
	
	public double getAspectRatio() {
		return width/height;
	}
	
	public boolean hasSameApproximateRatio(ImageDimension other) {
		double otherAspectRatio = other.getAspectRatio();
		double otherAspectRatioRotated = other.getRotatedVersion().getAspectRatio();
		double ourAspectRatio = getAspectRatio();
		double upperBoundary = ourAspectRatio * (1 + allowedDivergence);
		double lowerBoundary = ourAspectRatio * (1 - allowedDivergence);
		
		if (otherAspectRatio < upperBoundary && otherAspectRatio > lowerBoundary) {
			return true;
		} else if (otherAspectRatioRotated < upperBoundary && otherAspectRatioRotated > lowerBoundary){
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return String.format("%.1fx%.1f", width, height);
	}
}
