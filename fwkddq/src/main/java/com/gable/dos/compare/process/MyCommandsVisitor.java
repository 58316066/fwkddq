package com.gable.dos.compare.process;

import org.apache.commons.text.diff.CommandVisitor;

public class MyCommandsVisitor implements CommandVisitor<Character> {

	public String left = "";
	public String right = "";

	@Override
	public void visitKeepCommand(Character c) {
		// Character is present in both files.
		left = left + c;
		right = right + c;
	}

	@Override
	public void visitInsertCommand(Character c) {
		/*
		 * Character is present in right file but not in left. Method name
		 * 'InsertCommand' means, c need to insert it into left to match right.
		 */
		right = right + "(" + c + ")";
	}

	@Override
	public void visitDeleteCommand(Character c) {
		/*
		 * Character is present in left file but not right. Method name 'DeleteCommand'
		 * means, c need to be deleted from left to match right.
		 */
		left = left + "{" + c + "}";
	}
}
