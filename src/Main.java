public class Main {
    public static void main(String[] args) {
        // cargar imagen
        Image image = ImageUtils.load("input.png");

        // Apply requested filter
        ImageEditor editor = new ImageEditor(image);

        // Save transformed image.
        ImageUtils.save(output, "output.png");
    }
}
