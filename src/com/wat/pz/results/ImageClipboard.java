package com.wat.pz.results;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ImageClipboard implements Transferable {
	private Image image = null;

	public ImageClipboard(Image image) {
		this.image = image;
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (flavor.equals(DataFlavor.imageFlavor)) {
			return image;
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
		
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {

		return new DataFlavor[] { DataFlavor.imageFlavor };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {

		return flavor.equals(DataFlavor.imageFlavor);
	}

}
