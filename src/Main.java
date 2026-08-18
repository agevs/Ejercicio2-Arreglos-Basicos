public class Main {
    public static void main(String[] args) {
        // cargar imagen
        Image image = ImageUtils.load("data/input.png");

        // Apply requested filter
        ImageEditor editor = new ImageEditor(image);
        Image output = editor.negative();

        // Save transformed image.
        ImageUtils.save(output, "data/output.jpg");
    }
}
