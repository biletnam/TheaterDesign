package com.mobiletheatertech.plot;

public class Solid
{
    double height;
    double width;
    double depth;
	
	public Solid( double width, double depth, double height )
	{
		this.height = height;
		this.width = width;
		this.depth = depth;
	}
	
	public Double height()
	{
		return height;
	}
	
	public Double width()
	{
		return width;
	}
	
	public Double depth()
	{
		return depth;
	}

    public String toString() {
        return "Solid: "+height+", "+width+", "+depth+".";
    }
}
