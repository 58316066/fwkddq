package com.gable.dos.ddq.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.*;

@Data
@XmlRootElement(name = "ExamResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExamResult {

	@XmlElement(name = "studentName")
	private String studentName;

	@XmlElement(name = "dob")
	private Date dob;

	@XmlElement(name = "percentage")
	private double percentage;

	// public String getStudentName() {
	// 	return studentName;
	// }

	// public void setStudentName(String studentName) {
	// 	this.studentName = studentName;
	// }

	// @XmlJavaTypeAdapter(type = LocalDate.class, value = com.gable.dos.ddq.springbatch.LocalDateAdapter.class)
	// public LocalDate getDob() {
	// 	return dob;
	// }

	// public void setDob(LocalDate dob) {
	// 	this.dob = dob;
	// }

	// public double getPercentage() {
	// 	return percentage;
	// }

	// public void setPercentage(double percentage) {
	// 	this.percentage = percentage;
	// }

	// @Override
	// public String toString() {
	// 	return "ExamResult [studentName=" + studentName + ", dob=" + dob
	// 			+ ", percentage=" + percentage + "]";
	// }
}
