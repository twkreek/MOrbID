/*
 * 
	MOrbID - Molecular Orbital Interactive Display

MOrbID is Copyright (c) 1996-2014 by Thomas W. Kreek


Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

 */
package com.bobandthomas.Morbid.graphics;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.bobandthomas.Morbid.utils.*;

// TODO: Auto-generated Javadoc
/**
 * The Class GobPoly.
 * 
 * @author Thomas Kreek
 */
public class GobPoly extends Gob implements List<Vertex>{

	/**
	 * The Enum GobPolyType.
	 * 
	 * @author Thomas Kreek
	 */
	public enum GobPolyType {
		
		/** The Arrows. */
		Arrows,
		
		/** The Bezier. */
		Bezier, 
		
		/** The B spline. */
		BSpline, 
		
		/** The Closed. */
		Closed, 
		
		/** The Connected. */
		Connected, 
		
		/** The Lines. */
		Lines, 
		
		/** The Nurbs. */
		Nurbs, 
		
		/** The Points. */
		Points, 
		
		/** The Polygon. */
		Polygon, 
		
		/** The Quads. */
		Quads, 
		
		/** The Segments. */
		Segments,
		
		/** The T mesh fan. */
		TMeshFan, 
		
		/** The T mesh strip. */
		TMeshStrip, 
		
		/** The Triangles. */
		Triangles
	}

	/** The poly type. */
	GobPolyType polyType;
	
	/** The vertex list. */
	private VertexList vertexList;
	
	/** The smooth. */
	boolean smooth;

	/**
	 * Instantiates a new gob poly.
	 */
	public GobPoly() {
		polyType = GobPolyType.Closed;
		vertexList = new VertexList();
	}
	

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.Gob#Type()
	 */
	@Override
	public GobType Type() {
		return GobType.Poly;
	}

	/**
	 * Sets the poly type.
	 * 
	 * @param gpt
	 *            the gpt
	 */
	public void SetPolyType(GobPolyType gpt) {
		polyType = gpt;
	}

	/**
	 * Gets the poly type.
	 * 
	 * @return the gob poly type
	 */
	public GobPolyType GetPolyType() {
		return polyType;
	}

	/**
	 * Adds the point.
	 * 
	 * @param p
	 *            the p
	 * @param n1
	 *            the n1
	 */
	public void AddPoint(Point3D p, Vector3D n1) {
		vertexList.add(new Vertex(p,n1));
	}

	/**
	 * Adds the point.
	 * 
	 * @param p
	 *            the p
	 */
	public void AddPoint(Point3D p) {
		AddPoint(p, new Vector3D(0, 0, 0));
	}

	/**
	 * Adds the point.
	 * 
	 * @param p
	 *            the p
	 * @param cq
	 *            the cq
	 */
	public void AddPoint(Point3D p, ColorQuad cq) {
		AddPoint(p, cq, new Vector3D(0, 0, 0));
	}

	/**
	 * Adds the point.
	 * 
	 * @param p
	 *            the p
	 * @param cq
	 *            the cq
	 * @param n1
	 *            the n1
	 */
	public void AddPoint(Point3D p, ColorQuad cq, Vector3D n1) {
		vertexList.add(new Vertex(p,cq,n1));
	}
	
	/**
	 * Adds the vertex.
	 * 
	 * @param vertex
	 *            the vertex
	 */
	public void AddVertex(Vertex vertex) {
		vertexList.add(vertex);
		
		}


	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.Gob#center()
	 */
	@Override
	public Point3D center() {
		return vertexList.getCenter();
	}
	
	/* (non-Javadoc)
	 * @see java.util.List#size()
	 */
	public int size()
	{
		return vertexList.size();
	}
		
	/**
	 * Gets the vertices.
	 * 
	 * @return the vertices
	 */
	public VertexList getVertices()
	{
		return vertexList;
	}

	/**
	 * Checks if is checks if it has colors.
	 * 
	 * @return true, if is checks if it has colors
	 */
	public boolean isHasColors() {
		return vertexList.hasColors;
	}

	/**
	 * Checks if is checks if it has normals.
	 * 
	 * @return true, if is checks if it has normals
	 */
	public boolean isHasNormals() {
		return vertexList.hasNormals;
	}
// {{ Delegate vertexList
	/* (non-Javadoc)
 * @see java.util.List#add(int, java.lang.Object)
 */
public void add(int index, Vertex element) {
		vertexList.add(index, element);
	}


	/* (non-Javadoc)
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(Vertex p) {
		return vertexList.add(p);
	}


	/* (non-Javadoc)
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends Vertex> c) {
		return vertexList.addAll(c);
	}


	/* (non-Javadoc)
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection<? extends Vertex> c) {
		return vertexList.addAll(index, c);
	}


	/* (non-Javadoc)
	 * @see java.util.List#clear()
	 */
	public void clear() {
		vertexList.clear();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		return vertexList.clone();
	}


	/* (non-Javadoc)
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		return vertexList.contains(o);
	}


	/* (non-Javadoc)
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c) {
		return vertexList.containsAll(c);
	}


	/**
	 * Ensure capacity.
	 * 
	 * @param minCapacity
	 *            the min capacity
	 */
	public void ensureCapacity(int minCapacity) {
		vertexList.ensureCapacity(minCapacity);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return vertexList.equals(o);
	}


	/* (non-Javadoc)
	 * @see java.util.List#get(int)
	 */
	public Vertex get(int index) {
		return vertexList.get(index);
	}


	/**
	 * Checks if it has normals.
	 * 
	 * @return true, if successful
	 */
	public boolean hasNormals() {
		return vertexList.hasNormals();
	}


	/**
	 * Checks if it has colors.
	 * 
	 * @return true, if successful
	 */
	public boolean hasColors() {
		return vertexList.hasColors();
	}


	/**
	 * Gets the min.
	 * 
	 * @return the min
	 */
	public Point3D getMin() {
		return vertexList.getMin();
	}


	/**
	 * Gets the max.
	 * 
	 * @return the max
	 */
	public Point3D getMax() {
		return vertexList.getMax();
	}


	/**
	 * Gets the center.
	 * 
	 * @return the center
	 */
	public Point3D getCenter() {
		return vertexList.getCenter();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return vertexList.hashCode();
	}


	/* (non-Javadoc)
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o) {
		return vertexList.indexOf(o);
	}


	/* (non-Javadoc)
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return vertexList.isEmpty();
	}


	/* (non-Javadoc)
	 * @see java.util.List#iterator()
	 */
	public Iterator<Vertex> iterator() {
		return vertexList.iterator();
	}


	/* (non-Javadoc)
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o) {
		return vertexList.lastIndexOf(o);
	}


	/* (non-Javadoc)
	 * @see java.util.List#listIterator()
	 */
	public ListIterator<Vertex> listIterator() {
		return vertexList.listIterator();
	}


	/* (non-Javadoc)
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator<Vertex> listIterator(int index) {
		return vertexList.listIterator(index);
	}


	/* (non-Javadoc)
	 * @see java.util.List#remove(int)
	 */
	public Vertex remove(int index) {
		return vertexList.remove(index);
	}


	/* (non-Javadoc)
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		return vertexList.remove(o);
	}


	/* (non-Javadoc)
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		return vertexList.removeAll(c);
	}


	/* (non-Javadoc)
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c) {
		return vertexList.retainAll(c);
	}


	/* (non-Javadoc)
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public Vertex set(int index, Vertex element) {
		return vertexList.set(index, element);
	}


	/* (non-Javadoc)
	 * @see java.util.List#subList(int, int)
	 */
	public List<Vertex> subList(int fromIndex, int toIndex) {
		return vertexList.subList(fromIndex, toIndex);
	}


	/* (non-Javadoc)
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray() {
		return vertexList.toArray();
	}


	/* (non-Javadoc)
	 * @see java.util.List#toArray(java.lang.Object[])
	 */
	public <T> T[] toArray(T[] a) {
		return vertexList.toArray(a);
	}


	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.CLoadableItem#toString()
	 */
	public String toString() {
		return vertexList.toString();
	}


	/**
	 * Trim to size.
	 */
	public void trimToSize() {
		vertexList.trimToSize();
	}

	//}}
};
