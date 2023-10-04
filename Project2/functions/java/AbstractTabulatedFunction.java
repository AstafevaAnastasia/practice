public abstract class AbstractTabulatedFunction implements TabulatedFunction {
    protected int count;

    protected abstract int floorIndexOfX(double x);

    protected abstract double extrapolateLeft(double x);

    protected abstract double extrapolateRight(double x);

    protected abstract double interpolate(double x, int floorIndex);

    @Override
    public double apply(double x) {
        if (x < leftBound()) {
            return extrapolateLeft(x);
        } else if (x > rightBound()) {
            return extrapolateRight(x);
        } else {
            int floorIndex = floorIndexOfX(x);
            if (floorIndex < 0) {
                return getY(0);
            } else if (floorIndex >= count - 1) {
                return getY(count - 1);
            } else {
                return interpolate(x, floorIndex);
            }
        }
    }

}