package solver;

public class LinearEquationSolver {

    // Метод Гауса
    public static double[] gaussianElimination(double[][] A, double[] b) {
        int n = A.length;
        double[][] augmentedMatrix = new double[n][n + 1];

        // Доповнена матриця
        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i], 0, augmentedMatrix[i], 0, n);
            augmentedMatrix[i][n] = b[i];
        }

        // Віднімаємо
        for (int i = 0; i < n; i++) {
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(augmentedMatrix[k][i]) > Math.abs(augmentedMatrix[maxRow][i])) {
                    maxRow = k;
                }
            }
            // Міняємо рядки
            double[] temp = augmentedMatrix[i];
            augmentedMatrix[i] = augmentedMatrix[maxRow];
            augmentedMatrix[maxRow] = temp;

            // Віднімаємо
            for (int k = i + 1; k < n; k++) {
                double factor = augmentedMatrix[k][i] / augmentedMatrix[i][i];
                for (int j = i; j <= n; j++) {
                    augmentedMatrix[k][j] -= factor * augmentedMatrix[i][j];
                }
            }
        }

        // Виділяємо розв'язок
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            x[i] = augmentedMatrix[i][n] / augmentedMatrix[i][i];
            for (int k = i - 1; k >= 0; k--) {
                augmentedMatrix[k][n] -= augmentedMatrix[k][i] * x[i];
            }
        }
        return x;
    }

    // Метод матриць
    public static double[] matrixMethod(double[][] A, double[] b) {
        int n = A.length;
        double detA = determinant(A);
        if (detA == 0) {
            throw new IllegalArgumentException("System has no unique solution.");
        }

        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            double[][] modifiedMatrix = replaceColumn(A, b, i);
            x[i] = determinant(modifiedMatrix) / detA;
        }
        return x;
    }

    // обрахування детермінанту
    public static double determinant(double[][] matrix) {
        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0];
        }
        if (n == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        double det = 0;
        for (int i = 0; i < n; i++) {
            double[][] subMatrix = new double[n - 1][n - 1];
            for (int row = 1; row < n; row++) {
                for (int col = 0, subCol = 0; col < n; col++) {
                    if (col == i) continue;
                    subMatrix[row - 1][subCol++] = matrix[row][col];
                }
            }
            det += Math.pow(-1, i) * matrix[0][i] * determinant(subMatrix);
        }
        return det;
    }

    // замінити колонку вектором
    public static double[][] replaceColumn(double[][] matrix, double[] column, int colIndex) {
        int n = matrix.length;
        double[][] modifiedMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, modifiedMatrix[i], 0, n);
            modifiedMatrix[i][colIndex] = column[i];
        }
        return modifiedMatrix;
    }

    public static void main(String[] args) {

    }
}


