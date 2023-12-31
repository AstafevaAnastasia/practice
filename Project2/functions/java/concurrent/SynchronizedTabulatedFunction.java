package concurrent;

import packFunctions.Point;
import packFunctions.TabulatedFunction;
import operations.TabulatedFunctionOperationService;
import java.util.NoSuchElementException;

import java.util.Iterator;

public class SynchronizedTabulatedFunction implements TabulatedFunction {

    private final TabulatedFunction tabulatedFunction;

    public SynchronizedTabulatedFunction(TabulatedFunction tabulatedFunction) {
        this.tabulatedFunction = tabulatedFunction;
    }
    @Override
    public double apply(double x) {
        synchronized (tabulatedFunction) {
            return tabulatedFunction.apply(x);
        }
    }

    @Override
    public int getCount() {
        synchronized (tabulatedFunction) {
            return tabulatedFunction.getCount();
        }
    }

    @Override
    public double getX(int index) {
        synchronized (tabulatedFunction) {
        return tabulatedFunction.getX(index);
        }
    }

    @Override
    public double getY(int index) {
        synchronized (tabulatedFunction) {
            return tabulatedFunction.getY(index);
        }
    }

    @Override
    public void setY(int index, double value) {
        synchronized (tabulatedFunction) {
            tabulatedFunction.setY(index, value);
        }
    }

    @Override
    public int indexOfX(double x) {
        synchronized (tabulatedFunction) {
            return tabulatedFunction.indexOfX(x);
        }
    }

    @Override
    public int indexOfY(double y) {
        synchronized (tabulatedFunction) {
            return tabulatedFunction.indexOfY(y);
        }
    }

    @Override
    public double leftBound() {
        synchronized (tabulatedFunction) {
            return tabulatedFunction.leftBound();
        }
    }

    @Override
    public double rightBound() {
        synchronized (tabulatedFunction) {
            return tabulatedFunction.rightBound();
        }
    }

    @Override
    public Iterator<Point> iterator() {
        // ���� ������������� �� ������� tabulatedFunction
        synchronized (tabulatedFunction) {
            // �������� ����� ����� ������� � ������� ������ asPoints() �� ������ TabulatedFunctionOperationService
            Point[] copy = TabulatedFunctionOperationService.asPoints(tabulatedFunction);
            // ����������� ������ ��������� ��� ����� ����� �������
            return new Iterator<Point>() {
                private int currentIndex = 0; // ������ ������� ����� � �����
                @Override
                public boolean hasNext() {
                    return currentIndex < copy.length; // �������� ������� ��������� ����� � �����
                }

                @Override
                public Point next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return copy[currentIndex++];  // ��������� ��������� ����� �� �����
                }
            };
        }
    }

    public interface Operation<T> {

        T apply(SynchronizedTabulatedFunction synchronizedTabulatedFunction);
    }

    public <T> T doSynchronously(Operation<T> operation) {
        synchronized (this) {
            return operation.apply(this);
        }
    }
}

