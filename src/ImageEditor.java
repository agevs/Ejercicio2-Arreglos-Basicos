public class ImageEditor {
    private Image og;

    public ImageEditor(Image og) {
        this.og = og;
    }

    public Image negative() {
        Image transformed = new Image(og.getHeight(), og.getWidth());

        for (int row = 0; row < og.getHeight(); row++) {
            for (int col = 0; col < og.getWidth(); col++) {
                Pixel p = og.getPixel(row, col);

                int r = 255 - p.getR();
                int g = 255 - p.getG();
                int b = 255 - p.getB();

                transformed.setPixel(row, col, new Pixel(r, g, b));
            }
        }

        return transformed;
    }

    public Image grayscale() {
        Image transformed = new Image(og.getHeight(), og.getWidth());

        for (int row = 0; row < og.getHeight(); row++) {
            for (int col = 0; col < og.getWidth(); col++) {
                Pixel p = og.getPixel(row, col);

                int average = (p.getR() + p.getG() + p.getB()) / 3;

                transformed.setPixel(
                    row,
                    col,
                    new Pixel(average, average, average)
                );
            }
        }

        return transformed;
    }

    public Image keepOnlyChannel(int channel) {
        Image transformed = new Image(og.getHeight(), og.getWidth());

        for (int row = 0; row < og.getHeight(); row++) {
            for (int col = 0; col < og.getWidth(); col++) {
                Pixel p = og.getPixel(row, col);

                int r = 0;
                int g = 0;
                int b = 0;

                if (channel == 0) {
                    r = p.getR();
                } else if (channel == 1) {
                    g = p.getG();
                } else if (channel == 2) {
                    b = p.getB();
                }

                transformed.setPixel(row, col, new Pixel(r, g, b));
            }
        }

        return transformed;
    }

    public Image brightness(int amount) {
        Image transformed = new Image(og.getHeight(), og.getWidth());

        for (int row = 0; row < og.getHeight(); row++) {
            for (int col = 0; col < og.getWidth(); col++) {
                Pixel p = og.getPixel(row, col);

                int r = p.getR() + amount;
                int g = p.getG() + amount;
                int b = p.getB() + amount;

                transformed.setPixel(row, col, new Pixel(r, g, b));
            }
        }

        return transformed;
    }

    public Image blackAndWhite(int limit) {
        Image transformed = new Image(og.getHeight(), og.getWidth());

        for (int row = 0; row < og.getHeight(); row++) {
            for (int col = 0; col < og.getWidth(); col++) {
                Pixel p = og.getPixel(row, col);

                int average = (p.getR() + p.getG() + p.getB()) / 3;

                if (average > limit) {
                    transformed.setPixel(
                        row,
                        col,
                        new Pixel(255, 255, 255)
                    );
                } else {
                    transformed.setPixel(
                        row,
                        col,
                        new Pixel(0, 0, 0)
                    );
                }
            }
        }

        return transformed;
    }

    public Image mirrorHorizontal() {
        Image transformed = new Image(og.getHeight(), og.getWidth());

        for (int row = 0; row < og.getHeight(); row++) {
            for (int col = 0; col < og.getWidth(); col++) {
                Pixel p = og.getPixel(row, col);

                int newCol = og.getWidth() - 1 - col;

                transformed.setPixel(
                    row,
                    newCol,
                    new Pixel(p.getR(), p.getG(), p.getB())
                );
            }
        }

        return transformed;
    }

    public Image rotate90() {
        Image transformed = new Image(og.getWidth(), og.getHeight());

        for (int row = 0; row < og.getHeight(); row++) {
            for (int col = 0; col < og.getWidth(); col++) {
                Pixel p = og.getPixel(row, col);

                int newRow = col;
                int newCol = og.getHeight() - 1 - row;

                transformed.setPixel(
                    newRow,
                    newCol,
                    new Pixel(p.getR(), p.getG(), p.getB())
                );
            }
        }

        return transformed;
    }

    public void blur() {
        // TODO: optional
    }
}