
public class Matrix3 {
	private float[][] matrix;
	
	public Matrix3(float[][] matrix) {
		this.matrix = matrix;
	}
	
	public static Matrix3 rotationMatrixX(float rad) {
		float[][] m = {
				{1, 0, 0},
				{0, (float) Math.cos(rad), (float) -Math.sin(rad)},
				{0, (float) Math.sin(rad), (float) Math.cos(rad)}
		};
		
		return new Matrix3(m);
	}
	public static Matrix3 rotationMatrixY(float rad) {
		float[][] m = {
				{(float) Math.cos(rad), 0,(float) Math.sin(rad)},
				{0, 1, 0},
				{-(float) Math.sin(rad),0, (float) Math.cos(rad)}
		};
		
		return new Matrix3(m);
	}
	public static Matrix3 rotationMatrixZ(float rad) {
		float[][] m = {
				{(float) Math.cos(rad), -(float) Math.sin(rad), 0},
				{(float) Math.sin(rad), (float) Math.cos(rad), 0},
				{0, 0, 1}
		};
		
		return new Matrix3(m);
	}
	
	public Matrix3 multiply(Matrix3 other) {
		
		return null;
	}
	
	public float getVal(int i, int j) {
		return matrix[i][j];
	}
}
