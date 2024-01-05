
public class Proection {
	
	// Fields
	
	public boolean inside, below, above;
	public Point point, pointI, pointJ;
	public double coefI, coefJ;
	public Vector vI, vJ;
	
	// Constructor
	
	public Proection(Point point, boolean inside, boolean below, boolean above, double coefI, double coefJ, Point pointI, Point pointJ, Vector vI, Vector vJ){
		
		this.inside = inside;
		this.below = below;
		this.above = above;
		this.point = point;
		this.coefI = coefI;
		this.coefJ = coefJ;
		this.pointI = pointI;
		this.pointJ = pointJ;
		this.vI = vI;
		this.vJ = vJ;
	
	}
	
}
