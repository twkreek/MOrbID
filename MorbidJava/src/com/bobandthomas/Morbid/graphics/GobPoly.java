package com.bobandthomas.Morbid.graphics;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.bobandthomas.Morbid.utils.*;

public class GobPoly extends Gob implements List<Vertex>{

	public enum GobPolyType {
		Arrows,
		Bezier, 
		BSpline, 
		Closed, 
		Connected, 
		Lines, 
		Nurbs, 
		Points, 
		Polygon, 
		Quads, 
		Segments,
		TMeshFan, 
		TMeshStrip, 
		Triangles
	}

	GobPolyType polyType;
	private VertexList vertexList;
	boolean smooth;

	public GobPoly() {
		polyType = GobPolyType.Closed;
		vertexList = new VertexList();
	}
	

	@Override
	public GobType Type() {
		return GobType.Poly;
	}

	public void SetPolyType(GobPolyType gpt) {
		polyType = gpt;
	}

	public GobPolyType GetPolyType() {
		return polyType;
	}

	public void AddPoint(Point3D p, Vector3D n1) {
		vertexList.add(new Vertex(p,n1));
	}

	public void AddPoint(Point3D p) {
		AddPoint(p, new Vector3D(0, 0, 0));
	}

	public void AddPoint(Point3D p, ColorQuad cq) {
		AddPoint(p, cq, new Vector3D(0, 0, 0));
	}

	public void AddPoint(Point3D p, ColorQuad cq, Vector3D n1) {
		vertexList.add(new Vertex(p,cq,n1));
	}
	public void AddVertex(Vertex vertex) {
		vertexList.add(vertex);
		
		}


	@Override
	public Point3D center() {
		return vertexList.getCenter();
	}
	
	public int size()
	{
		return vertexList.size();
	}
	
	public VertexList getVertices()
	{
		return vertexList;
	}

	public boolean isHasColors() {
		return vertexList.hasColors;
	}

	public boolean isHasNormals() {
		return vertexList.hasNormals;
	}
// {{ Delegate vertexList
	public void add(int index, Vertex element) {
		vertexList.add(index, element);
	}


	public boolean add(Vertex p) {
		return vertexList.add(p);
	}


	public boolean addAll(Collection<? extends Vertex> c) {
		return vertexList.addAll(c);
	}


	public boolean addAll(int index, Collection<? extends Vertex> c) {
		return vertexList.addAll(index, c);
	}


	public void clear() {
		vertexList.clear();
	}


	public Object clone() {
		return vertexList.clone();
	}


	public boolean contains(Object o) {
		return vertexList.contains(o);
	}


	public boolean containsAll(Collection<?> c) {
		return vertexList.containsAll(c);
	}


	public void ensureCapacity(int minCapacity) {
		vertexList.ensureCapacity(minCapacity);
	}


	public boolean equals(Object o) {
		return vertexList.equals(o);
	}


	public Vertex get(int index) {
		return vertexList.get(index);
	}


	public boolean hasNormals() {
		return vertexList.hasNormals();
	}


	public boolean hasColors() {
		return vertexList.hasColors();
	}


	public Point3D getMin() {
		return vertexList.getMin();
	}


	public Point3D getMax() {
		return vertexList.getMax();
	}


	public Point3D getCenter() {
		return vertexList.getCenter();
	}


	public int hashCode() {
		return vertexList.hashCode();
	}


	public int indexOf(Object o) {
		return vertexList.indexOf(o);
	}


	public boolean isEmpty() {
		return vertexList.isEmpty();
	}


	public Iterator<Vertex> iterator() {
		return vertexList.iterator();
	}


	public int lastIndexOf(Object o) {
		return vertexList.lastIndexOf(o);
	}


	public ListIterator<Vertex> listIterator() {
		return vertexList.listIterator();
	}


	public ListIterator<Vertex> listIterator(int index) {
		return vertexList.listIterator(index);
	}


	public Vertex remove(int index) {
		return vertexList.remove(index);
	}


	public boolean remove(Object o) {
		return vertexList.remove(o);
	}


	public boolean removeAll(Collection<?> c) {
		return vertexList.removeAll(c);
	}


	public boolean retainAll(Collection<?> c) {
		return vertexList.retainAll(c);
	}


	public Vertex set(int index, Vertex element) {
		return vertexList.set(index, element);
	}


	public List<Vertex> subList(int fromIndex, int toIndex) {
		return vertexList.subList(fromIndex, toIndex);
	}


	public Object[] toArray() {
		return vertexList.toArray();
	}


	public <T> T[] toArray(T[] a) {
		return vertexList.toArray(a);
	}


	public String toString() {
		return vertexList.toString();
	}


	public void trimToSize() {
		vertexList.trimToSize();
	}

	//}}
};
