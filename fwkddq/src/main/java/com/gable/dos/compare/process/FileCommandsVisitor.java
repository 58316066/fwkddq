package com.gable.dos.compare.process;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.text.diff.CommandVisitor;


public class FileCommandsVisitor implements CommandVisitor<Character> {

	public static String output = "aa";
	// Spans with red & green highlights to put highlighted characters in HTML
	private static final String DELETION = "<span style=\"background-color: #FB504B\">${text}</span>";
	private static final String INSERTION = "<span style=\"background-color: #45EA85\">${text}</span>";

	private String left = "";
	private String right = "";
	public static boolean checkStatusCompare = true;


	@Override
	public void visitKeepCommand(Character c) {

		// For new line use <br/> so that in HTML also it shows on next line.
		String toAppend = "\n".equals("" + c) ? "<br/>" : "" + c;
		// KeepCommand means c present in both left & right. So add this to both without
		// any
		// highlight.
		left = left + toAppend;
		right = right + toAppend;
	}

	@Override
	public void visitInsertCommand(Character c) {
		checkStatusCompare = false;
		// For new line use <br/> so that in HTML also it shows on next line.
		String toAppend = "\n".equals("" + c) ? "<br/>" : "" + c;
		// InsertCommand means character is present in right file but not in left. Show
		// with green highlight on right.
		right = right + INSERTION.replace("${text}", "" + toAppend);
	}

	@Override
	public void visitDeleteCommand(Character c) {
		checkStatusCompare = false;
		// For new line use <br/> so that in HTML also it shows on next line.
		String toAppend = "\n".equals("" + c) ? "<br/>" : "" + c;
		// DeleteCommand means character is present in left file but not in right. Show
		// with red highlight on left.
		left = left + DELETION.replace("${text}", "" + toAppend);
	}

	public void generateHTML() throws IOException {

		// Get template & replace placeholders with left & right variables with actual
		// comparison
		String template = FileUtils.readFileToString(new File("difftemplate.html"), "utf-8");
		String out1 = template.replace("${left}", left);
		output = out1.replace("${right}", right);

		// Write file to disk.
		FileUtils.write(new File("C:\\Users\\Admin\\Desktop\\finalDiff.html"), output, "utf-8");
		System.out.println("HTML diff generated.");
	}
}