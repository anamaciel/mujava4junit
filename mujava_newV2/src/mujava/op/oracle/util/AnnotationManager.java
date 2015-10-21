package mujava.op.oracle.util;

import java.lang.annotation.Annotation;

public class AnnotationManager {
	
	public String annotation;
	public int line;
	
	
	public AnnotationManager(String annotation, int line) {
		this.annotation = annotation;
		this.line = line;
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
}
