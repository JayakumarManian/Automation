//package com.selenium.examples;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import com.selenium.aut.RegistrationForm;
//
//import java.lang.reflect.InvocationTargetException;
//
//import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
//import org.assertj.swing.edt.GuiActionRunner;
//import org.assertj.swing.fixture.FrameFixture;
//
//
//public class RegistrationFormTest  {
//	
//	 private FrameFixture window;
//	
//	@BeforeClass
//	  public static void setUpOnce() {
//	    FailOnThreadViolationRepaintManager.install();
//	  }
//	
//	@Before
//	public void before() {
//		RegistrationForm frame = GuiActionRunner.execute(() -> new RegistrationForm());
//		window = new FrameFixture(frame);
//		window.show();
//	
//	}
//
//	@Test 
//	public void shouldChangeLabelOnButtonClick() throws InterruptedException, InvocationTargetException {
//		window.maximize();
//		window.textBox("username").enterText("Jayakumar Manian");
//		window.textBox("mobileno").enterText("9791444078");	
//		window.radioButton("male").click();
//		window.comboBox("date").selectItem("18");
//		window.comboBox("month").selectItem("May");
//		window.comboBox("year").selectItem("1997");
//		window.textBox("address").enterText("CSS Corp \n MPEZ, Chennai");
//		window.checkBox("term").check();
//		window.button("submit").click();
//		String userInfo = window.textBox("textarea").text();
//		window.textBox("textarea").requireText(userInfo);
//	}
//	
//	@After
//	  public void tearDown() {	
//		  window.cleanUp();
//	  }
//	
//	
//}
