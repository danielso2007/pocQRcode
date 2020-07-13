package br.com.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.krysalis.barcode4j.impl.int2of5.Interleaved2Of5Bean;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class Main {

	public static void main(String[] args) {
		try {
			String ean13 = "789100031550";
			String codigoBarras = "23791830000000100562373050014106163100044920"; // Obtido de um boleto válido.
			String linhaDigitavel = "23792373206001410618513035885600383270000014027";

			System.out.println("Gerando código EAN13 Barcode4j Library...");
			Main.generateEAN13BarcodeImageBarcode4jLibrary(ean13);

			System.out.println("Gerando código de barras Barcode4j Library...");
			Main.generateInterleaved2Of5BeanBarcode4jLibrary(codigoBarras);

			System.out.println("QRCode ZXing Library...");
			Main.generateQRCodeImage(linhaDigitavel);
			
			System.out.println("Gerando Base64 de imagem QRCode ZXing Library...");
			System.out.println(Main.generateBase64QRCodeImage(linhaDigitavel));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void generateEAN13BarcodeImageBarcode4jLibrary(String barcodeText) throws IOException {
		EAN13Bean barcodeGenerator = new EAN13Bean();
		BitmapCanvasProvider canvas = new BitmapCanvasProvider(160, BufferedImage.TYPE_BYTE_BINARY, false, 0);

		barcodeGenerator.generateBarcode(canvas, barcodeText);

		final File f = new File(System.getProperty("user.home") + "/EAN13BarcodeImageBarcode4jLibrary.jpg");
		ImageIO.write(canvas.getBufferedImage(), "jpg", f);
	}

	public static void generateInterleaved2Of5BeanBarcode4jLibrary(String barcodeText) throws IOException {
		Interleaved2Of5Bean barcodeGenerator = new Interleaved2Of5Bean();
		BitmapCanvasProvider canvas = new BitmapCanvasProvider(160, BufferedImage.TYPE_BYTE_BINARY, false, 0);

		barcodeGenerator.generateBarcode(canvas, barcodeText);

		final File f = new File(System.getProperty("user.home") + "/CodigoBarrasInterleaved2Of5BeanBarcode4jLibrary.jpg");
		ImageIO.write(canvas.getBufferedImage(), "jpg", f);
	}

	public static void generateQRCodeImage(String barcodeText) throws Exception {
		QRCodeWriter barcodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);

		BufferedImage bi = MatrixToImageWriter.toBufferedImage(bitMatrix);

		final File f = new File(System.getProperty("user.home") + "/qrcodeZXingLibrary.jpg");
		ImageIO.write(bi, "jpg", f);
	}
	
	public static String generateBase64QRCodeImage(String barcodeText) throws Exception {
		QRCodeWriter barcodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);

		BufferedImage bi = MatrixToImageWriter.toBufferedImage(bitMatrix);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		ImageIO.write(bi, "jpg", os);
		String base64 = Base64.getEncoder().encodeToString(os.toByteArray());
		
		return base64;
	}
}
