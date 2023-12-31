// класс AbstractTabulatedFunction реализующий интерфейс TabulatedFunction
package packFunctions;

import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {
    protected int count;

    protected abstract int floorIndexOfX(double x);

    protected abstract double extrapolateLeft(double x);

    protected abstract double extrapolateRight(double x);

    protected abstract double interpolate(double x, int floorIndex);

    public static void checkLengthIsTheSame(double[] xValues, double[] yValues) {
        if (xValues.length != yValues.length) {
            throw new DifferentLengthOfArraysException("Arrays have different length");
        }
    }

    public static void checkIsSorted(double[] xValues) {
        for (int i = 0; i < xValues.length-1; ++i) {
            if (xValues[i] > xValues[i+1]) {
                throw new ArrayIsNotSortedException("Array is not sorted");
            }
        }
    }

    protected double interpolate(double x,double leftX, double rightX, double leftY, double rightY) {
        double v =(x-leftX);
        double u = ((rightY - leftY)/(rightX - leftX));
        return leftY + u*v;
    }

    public double apply(double x) {
        if (x < leftBound()) {
            return extrapolateLeft(x);
        } else if (x > rightBound()) {
            return extrapolateRight(x);
        } else {
            int floorIndex = floorIndexOfX(x);
            int index = indexOfX(x);
            if (index != -1) {
                return getY(index);
            }
            else {
                return interpolate(x, floorIndex);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(); // Создаем новый объект StringBuilder для формирования строки
        s.append(getClass().getSimpleName() + " size = " + count + "\n"); // Добавляем название класса и количество элементов в массиве
        for(Point point : this){ // Проходим по каждой точке в массиве и добавляем их координаты в строку
            s.append("[" + point.x + "; " + point.y + "]" + "\n");
        }
        s.deleteCharAt(s.length()-1); // Удаляем последний символ (символ переноса строки) из строки
        return s.toString();
    }

}