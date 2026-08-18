/*
* The Pixel class represents an RGB pixel.
* We use `int` as the data type to back up every
* color channel.
*/
public class Pixel {
    private int r;
    private int g;
    private int b;

    public Pixel(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB(){
        return b;
    }

    public void setR(int r) {
        this.r = clamp(r);
    }

    public void setG(int g) {
        this.g = clamp(g);
    }

    public void setB(int b) {
        this.b = clamp(b);
    }

    private int clamp(int value) {
        if (value < 0) {
            return 0;
        }
        if (value > 255) {
            return 255;
        }

        return value;
    }
}
