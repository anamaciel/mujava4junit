package mujava.op.oracle.util;

import java.lang.annotation.Annotation;

public class AnnotationManager {
	
	private String annotation;
	private int line;
	private int number;
	
	
	public AnnotationManager(String annotation, int line, int number) {
		this.annotation = annotation;
		this.line = line;
		this.number = number;
	}


	public String getAnnotation() {
		return annotation;
	}


	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}


	public int getLine() {
		return line;
	}


	public void setLine(int line) {
		this.line = line;
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
