
public class Vector3 {
	private float x, y, z;
	
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(float a) {
		this.x = a;
		this.y = a;
		this.z = a;
	}
	
	public Vector3 add(Vector3 other) {
		return new Vector3(other.x()+x, other.y() + y, other.z() + z);
	}
	
	public Vector3 subtract(Vector3 other) {
		return new Vector3(x - other.x(), y - other.y(), z - other.z());
	}
	
	public Vector3 multiply(Vector3 other) {
		return new Vector3(other.x()*x, other.y()*y, other.z() * z);
	}
	
	public Vector3 multiply(Matrix3 matrix) {
		float x = x()*matrix.getVal(0,0) +  y()*matrix.getVal(0, 1) + z()*matrix.getVal(0, 2);
		float y = x()*matrix.getVal(1,0) +  y()*matrix.getVal(1, 1)+ z()*matrix.getVal(1, 2);
		float z = x()*matrix.getVal(2,0) +  y()*matrix.getVal(2, 1) + z()*matrix.getVal(2, 2);
		
		return new Vector3(x, y, z);
	}
	
	public float dot(Vector3 other) {
		float result = 0;
		
		result += x*other.x;
		result += y*other.y;
		result += z*other.z;
		
		return result;
	}
	
	public Vector3 cross(Vector3 b) {
		return new Vector3(
			y * b.z - z * b.y, 
	        z * b.x - x * b.z, 
	        x * b.y - y * b.x 
		);
	}
	
	public Vector3 scale(float scalar) {
		return new Vector3(x*scalar, y*scalar, z*scalar);
	}
	
	public float magnitude() {
		return (float) Math.sqrt(x*x + y*y + z*z);
	}
	
	public Vector3 normalize() {
		return new Vector3(x / magnitude(), y / magnitude(), z / magnitude());
	}
	
	public Vector3 rotate(float xd, float yd, float zd) {
		Matrix3 xRot = Matrix3.rotationMatrixX((float) Math.toRadians(xd));
		Matrix3 yRot = Matrix3.rotationMatrixY((float) Math.toRadians(yd));
		Matrix3 zRot = Matrix3.rotationMatrixZ((float) Math.toRadians(zd));
		
		return multiply(zRot).multiply(yRot).multiply(xRot);
	}
	
	public Vector3 rotate(Vector3 deg) {
		return rotate(deg.x(), deg.y(), deg.z());
	}
	
	public float x() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float y() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float z() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public String toString() {
		return String.format("Vec3(%.2f, %.2f, %.2f)", x,y,z);
	}
}
