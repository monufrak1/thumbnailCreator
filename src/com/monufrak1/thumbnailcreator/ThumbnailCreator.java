package com.monufrak1.thumbnailcreator;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ThumbnailCreator {
	private static final String FILE_EXTENSION = "jpg";
	
	/**
	 * Creates a new thumbnail image scaled to the specified size.
	 * 
	 * @param filePath - path to source image
	 * @param outputFilePath - path to output file to create
	 * @param thumbnailWidth - width in pixels of the image to create
	 * @param thumbnailHeight - height in pixels of the image to create
	 */
	private static void createThumbnail(String filePath, String outputFilePath, int thumbnailWidth, int thumbnailHeight) {
	    try {
	        BufferedImage sourceImage = ImageIO.read(new File(filePath));
	        int width = sourceImage.getWidth();
	        int height = sourceImage.getHeight();

	        if(width>height) {
	            BufferedImage img = new BufferedImage(thumbnailWidth, thumbnailHeight, BufferedImage.TYPE_INT_RGB);
	            Image scaledImage = sourceImage.getScaledInstance(thumbnailWidth, thumbnailHeight, Image.SCALE_SMOOTH);
	            img.createGraphics().drawImage(scaledImage, 0, 0, null);

	            ImageIO.write(img, FILE_EXTENSION, new File(outputFilePath));
	        } else {
	            BufferedImage img = new BufferedImage(thumbnailWidth, thumbnailWidth, BufferedImage.TYPE_INT_RGB);
	            Image scaledImage = sourceImage.getScaledInstance(thumbnailWidth, thumbnailWidth, Image.SCALE_SMOOTH);
	            img.createGraphics().drawImage(scaledImage, 0, 0, null);

	            ImageIO.write(img, FILE_EXTENSION, new File(outputFilePath));
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * @param args - usage: src_dir thumbnail_dir width height
	 */
	public static void main(String[] args) {
		if(args == null || args.length != 4) {
			System.out.println("usage: <src_dir> <thumbnail_dir> <width> <height>");
			System.exit(1);
		}
		
		String srcDir = args[0];
		String thumbsDir = args[1];
		int width = Integer.parseInt(args[2]);
		int height = Integer.parseInt(args[3]);
		
		// Get image files
		File srcDirFile = new File(srcDir);
		File[] srcImages = srcDirFile.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(FILE_EXTENSION);
			}
		});
		
		// Create thumbnail directory
		File thumbnailDir = new File(thumbsDir);
		if(!thumbnailDir.exists()) {
			if(thumbnailDir.mkdir()) {
				System.out.println("Created thumbnail directory: " + thumbnailDir.getPath());
			}
		}
		
		for(File imageFile : srcImages) {
			System.out.println("Creating thumbnail for: " + imageFile.getPath());
			
			// Create thumbnail for image
			createThumbnail(imageFile.getAbsolutePath(), thumbsDir + File.separator + imageFile.getName(),
					width, height);
		}
	}

}
