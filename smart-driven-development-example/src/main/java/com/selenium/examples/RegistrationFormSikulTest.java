package com.selenium.examples;

import java.net.URISyntaxException;
import java.net.URL;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

public class RegistrationFormSikulTest {

	private Screen screen;
    private String basePath;
 
    public RegistrationFormSikulTest() throws URISyntaxException {
        screen = new Screen();
        URL resourceFolderURL = this.getClass().getClassLoader().getResource("images");
        basePath = resourceFolderURL.toURI().getPath() + "/";
    }
 
    private void startTest() throws FindFailed {
        clickJarAndOpenSwingApp();
        typeRegisterFormAndSubmit();
        appClose();
    }
 
    private void clickJarAndOpenSwingApp() throws FindFailed {
        screen.click(basePath + "jar_start.png");
        screen.wait(1.0); //need delay to allow animation to bring start menu
    }
 
    private void typeRegisterFormAndSubmit() throws FindFailed {
        screen.click(basePath + "name_textfield.png");
        screen.type("Jayakumar Manian");
       // screen.type("s", KeyModifier.CTRL);
        screen.click(basePath + "mobile_textfield.png");
        screen.type("9791444078");
        screen.click(basePath + "address_textfield.png");
        screen.type("CSS Corp , MPEZ Chenai");
        screen.click(basePath + "term_checkbox.png");
        screen.click(basePath + "submit_btn.png");
        System.out.println("Address : "+ screen.find(basePath + "full_address.png").text());
    }
 
    private void appClose() throws FindFailed {
    	 screen.click(basePath + "close_app.png");
	}
    
    public static void main(String... args) throws FindFailed, URISyntaxException {
    	RegistrationFormSikulTest sikuliAutomation = new RegistrationFormSikulTest();
        sikuliAutomation.startTest();
    }
}


