package testesSelenium;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SeleniumTest.class, SeleniumTestAlert.class })
public class SeleniumTestSuite {

}
