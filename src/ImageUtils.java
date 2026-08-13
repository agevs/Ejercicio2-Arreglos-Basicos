import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageUtils {
    public static Image load(String filename) {
        try {
            BufferedImage img = ImageIO.read(new File(filename));

            // couldnt read image
            if (img == null) {
                return null;
            }

            int height = img.getHeight();
            int width = img.getWidth();

            // Create pixel matrix here with appropiate dimensions.
            Pixel[][] pixels;

            // loop over BufferedImage
            // int packed = img.getRGB(row, col);
            // int r = (packed >> 16) & 0xFF;
            // int g = (packed >> 8) & 0xFF;
            // int b = packed & 0xFF;

            return new Image(pixels);
        } catch (IOException e) {
            System.out.println("Couldn't open image at: '" + filename + "': " + e.getMessage());
            return null;
        }
    }

    public static void save(Image image, String filename) {
        int height = image.getHeight();
        int width = image.getWidth();

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Pixel pixel = image.getPixel(row, col);

                // int r = pixel.r & 0xFF;
                // int g = pixel.g & 0xFF;
                // int b = pixel.b & 0xFF;
                // img.setRGB(row, col, (r << 16) | (g << 8) | b);
            }
        }

        try {
            File file = new File(filename);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            String format = "png";
            int dot = filename.lastIndexOf('.');
            if (dot != -1 && dot < filename.length() - 1) {
                format = filename.substring(dot + 1).toLowerCase();
            }

            ImageIO.write(img, format, file);
            System.out.println("Saved: " + filename + "  (" + width + " x " + height + ")");

        } catch (IOException e) {
            System.out.println("ERROR: could not save '" + filename + "': " + e.getMessage());
        }
    }
}
