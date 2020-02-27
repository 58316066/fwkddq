package com.gable.dos.compare;

import org.apache.commons.text.diff.StringsComparator;

import com.gable.dos.compare.process.MyCommandsVisitor;

public class SimpleDiff {

	public static void main(String[] args) {
		// Create a diff comparator with two inputs strings.
		StringsComparator comparator = new StringsComparator("Its All Binary.", "Its All fun.");
		// Initialize custom visitor and visit char by char.
		MyCommandsVisitor myCommandsVisitor = new MyCommandsVisitor();
		comparator.getScript().visit(myCommandsVisitor);
		// Print final diff.
		System.out.println("FINAL DIFF = " + myCommandsVisitor.left + " | " + myCommandsVisitor.right);
	}

}
