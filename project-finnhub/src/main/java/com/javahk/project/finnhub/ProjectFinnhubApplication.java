package com.javahk.project.finnhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectFinnhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectFinnhubApplication.class, args);

		// during SpringApplication.run(), one of the thing that cannot avoid is run
		// commandline runner. In this project, appstartrunner extends commandline runner
		// and I don't want the test run the code in appstartrunner
		// so would add @ActiveProfile("test") to all the test java file 
		// and add @profile("!test") to the appstartrunner to stop it run when perform test
	}

}
