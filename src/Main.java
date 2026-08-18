public class Main {

    public static void main(String[] args) {

        // Validate required arguments.
        if (args.length < 3) {
            System.out.println("ERROR: faltan argumentos.");
            showUsage();
            return;
        }

        String inputPath = args[0];
        String outputPath = args[1];
        String filter = args[2].toLowerCase();

        // The original image must never be overwritten.
        if (inputPath.equals(outputPath)) {
            System.out.println(
                "ERROR: el archivo de salida debe ser diferente al archivo de entrada."
            );
            showUsage();
            return;
        }

        // Validate filter name before processing the image.
        if (!isValidFilter(filter)) {
            System.out.println("ERROR: filtro no soportado: " + filter);
            showUsage();
            return;
        }

        // Load image.
        Image image = ImageUtils.load(inputPath);

        if (image == null) {
            System.out.println(
                "ERROR: no se pudo abrir la imagen de entrada."
            );
            showUsage();
            return;
        }

        // General information about the input image.
        System.out.println("Ancho: " + image.getWidth());
        System.out.println("Alto: " + image.getHeight());
        System.out.println(
            "Cantidad total de pixeles: "
            + (image.getWidth() * image.getHeight())
        );

        // Make an independent copy of the original image.
        Image originalCopy = copyImage(image);

        ImageEditor editor = new ImageEditor(image);
        Image output = null;

        // Select requested filter.
        switch (filter) {

            case "grises":
                output = editor.grayscale();
                break;

            case "negativo":
                output = editor.negative();
                break;

            case "rojo":
                output = editor.keepOnlyChannel(0);
                break;

            case "verde":
                output = editor.keepOnlyChannel(1);
                break;

            case "azul":
                output = editor.keepOnlyChannel(2);
                break;

            case "brillo":
                int amount = 30;

                if (args.length >= 4) {
                    try {
                        amount = Integer.parseInt(args[3]);
                    } catch (NumberFormatException e) {
                        System.out.println(
                            "ERROR: la cantidad de brillo debe ser un numero valido."
                        );
                        showUsage();
                        return;
                    }
                }

                output = editor.brightness(amount);
                break;

            case "umbral":
                int limit = 128;

                if (args.length >= 4) {
                    try {
                        limit = Integer.parseInt(args[3]);
                    } catch (NumberFormatException e) {
                        System.out.println(
                            "ERROR: el limite del umbral debe ser un numero valido."
                        );
                        showUsage();
                        return;
                    }
                }

                output = editor.blackAndWhite(limit);
                break;

            case "espejo":
                output = editor.mirrorHorizontal();
                break;

            case "rotar":
                output = editor.rotate90();
                break;
        }

        // Brightness comparison.
        double brightnessBefore = averageBrightness(originalCopy);
        double brightnessAfter = averageBrightness(output);

        System.out.printf(
            "Brillo promedio antes: %.2f%n",
            brightnessBefore
        );

        System.out.printf(
            "Brillo promedio despues: %.2f%n",
            brightnessAfter
        );

        // Find lightest and darkest pixels.
        printExtremePixels(output);

        // Save result.
        ImageUtils.save(output, outputPath);

        System.out.println(
            "Imagen generada correctamente: " + outputPath
        );

        System.out.println(
            "Dimensiones finales: "
            + output.getWidth()
            + " x "
            + output.getHeight()
        );
    }


    private static boolean isValidFilter(String filter) {
        return filter.equals("grises")
            || filter.equals("negativo")
            || filter.equals("rojo")
            || filter.equals("verde")
            || filter.equals("azul")
            || filter.equals("brillo")
            || filter.equals("umbral")
            || filter.equals("espejo")
            || filter.equals("rotar");
    }


    private static Image copyImage(Image image) {
        Image copy = new Image(
            image.getHeight(),
            image.getWidth()
        );

        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {

                Pixel p = image.getPixel(row, col);

                copy.setPixel(
                    row,
                    col,
                    new Pixel(
                        p.getR(),
                        p.getG(),
                        p.getB()
                    )
                );
            }
        }

        return copy;
    }


    private static double averageBrightness(Image image) {
        double total = 0;

        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {

                Pixel p = image.getPixel(row, col);

                total += (
                    p.getR()
                    + p.getG()
                    + p.getB()
                ) / 3.0;
            }
        }

        return total
            / (image.getHeight() * image.getWidth());
    }


    private static void printExtremePixels(Image image) {

        int lightestRow = 0;
        int lightestCol = 0;

        int darkestRow = 0;
        int darkestCol = 0;

        Pixel first = image.getPixel(0, 0);

        double lightestBrightness =
            (first.getR() + first.getG() + first.getB()) / 3.0;

        double darkestBrightness = lightestBrightness;

        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {

                Pixel p = image.getPixel(row, col);

                double brightness =
                    (p.getR() + p.getG() + p.getB()) / 3.0;

                if (brightness > lightestBrightness) {
                    lightestBrightness = brightness;
                    lightestRow = row;
                    lightestCol = col;
                }

                if (brightness < darkestBrightness) {
                    darkestBrightness = brightness;
                    darkestRow = row;
                    darkestCol = col;
                }
            }
        }

        System.out.println(
            "Pixel mas claro: fila "
            + lightestRow
            + ", columna "
            + lightestCol
        );

        System.out.println(
            "Pixel mas oscuro: fila "
            + darkestRow
            + ", columna "
            + darkestCol
        );
    }


    private static void showUsage() {
        System.out.println(
            "Uso: java -cp bin Main "
            + "<entrada> <salida> <filtro> [parametro]"
        );

        System.out.println(
            "Filtros: grises, negativo, rojo, verde, azul, "
            + "brillo, umbral, espejo, rotar"
        );
    }
}
