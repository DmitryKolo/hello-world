
public class FrontLeftTopInBasis {
	
	// Fields
	public int front, left, top;
	
	// Constructor
	
	public FrontLeftTopInBasis(int f, int l, int t){
		
		front = f;
		left = l;
		top = t;
	}
	
	public FrontLeftTopInBasis(Cube cube){
		
		double maxAbsX = 0, maxAbsY = 0;
		int axisIndexMaxAbsX = 0, axisIndexMaxAbsY = 0;
		
		double tentativeMaxAbsX = 0, tentativeMaxAbsY = 0;
		int tentativeAxisIndexMaxAbsX = 0, tentativeAxisIndexMaxAbsY = 0;
		
		for(int i = 0; i < Cube.DIMENSION; i++){
			
			Vector vector = cube.axis[i].vector;
			
			double absX = Math.abs(vector.dx);
			double absY = Math.abs(vector.dy);
			
			if(absX > maxAbsX){
				tentativeMaxAbsX = absX;
				tentativeAxisIndexMaxAbsX = i;
			}
			if(absY > maxAbsY){
				tentativeMaxAbsY = absY;
				tentativeAxisIndexMaxAbsY = i;
			}
			
			if(i == tentativeAxisIndexMaxAbsX)
				if(i != tentativeAxisIndexMaxAbsY || tentativeMaxAbsX > tentativeMaxAbsY){
					maxAbsX = absX;
					axisIndexMaxAbsX = i;
				}
			if(i == tentativeAxisIndexMaxAbsY)
				if(i != tentativeAxisIndexMaxAbsX || tentativeMaxAbsY> tentativeMaxAbsX){
					maxAbsY = absY;
					axisIndexMaxAbsY = i;
				}
		}
		
//		if(cube.axis[axisIndexMaxAbsY].vector.dy > 0)
//			top = axisIndexMaxAbsY;
//		else 
//			top = Cube.DIMENSION + axisIndexMaxAbsY; 
		
		if(cube.axis[axisIndexMaxAbsX].vector.dx > 0){
			front = axisIndexMaxAbsX;
			left = Cube.DIMENSION - axisIndexMaxAbsX - axisIndexMaxAbsY;
			if(cube.axis[left].vector.dx > 0) left = Cube.DIMENSION + left;
		}
		else{
			left = axisIndexMaxAbsX; 
			front = Cube.DIMENSION - axisIndexMaxAbsX - axisIndexMaxAbsY;
			if(cube.axis[front].vector.dx < 0) front = Cube.DIMENSION + front;
		}
		
		if(cube.axis[axisIndexMaxAbsY].vector.dy > 0){
			top = axisIndexMaxAbsY;
			int tmp = left;
			left = front;
			front = tmp;
		}
		else{ 
			top = Cube.DIMENSION + axisIndexMaxAbsY; 
		}
		
		
	}

	// Functions
	
}
