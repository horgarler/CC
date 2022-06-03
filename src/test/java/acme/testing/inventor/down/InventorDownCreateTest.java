package acme.testing.inventor.down;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorDownCreateTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/down/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void positiveTest (final int recordIndex, final String code, 
		final String items, final String subject, final String explanation, final String startPeriod, final String endPeriod,
		final String quantity, final String additionalInfo) {
		
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my down");
		
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkButtonExists("Create");
		super.clickOnButton("Create");
		
		super.checkFormExists();
		
		// XXP
		
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(moment);
		
        final String year = String.valueOf(calendar.get(Calendar.YEAR));
        final char[] digitsYear = year.toCharArray();
        final String ten = digitsYear[2] + "0";
        final String one = digitsYear[0] + "";
        final Integer yearTwoDigits = Integer.parseInt(ten) + Integer.parseInt(one);
        
        final Integer month = calendar.get(Calendar.MONTH) + 1;
        final Integer day = calendar.get(Calendar.DAY_OF_MONTH);
        
        String monthS = String.valueOf(month);
        if (month <= 9) {
        	monthS = "0" + monthS;
        }
        
        String dayS = String.valueOf(day);
        if (day <= 9) {
        	dayS = "0" + dayS;
        }
        
        final String codeToday = "zz:" + yearTwoDigits + ":" + monthS + ":" + code + ":" + dayS;
        
        super.fillInputBoxIn("code", codeToday);
		super.fillInputBoxIn("items", items);
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("explanation", explanation);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("quantity", quantity);
		super.fillInputBoxIn("additionalInfo", additionalInfo);
		super.clickOnSubmit("Create");
		
		super.checkListingExists();
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		
		super.checkInputBoxHasValue("code", codeToday);
		super.checkInputBoxHasValue("item", items);
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("explanation", explanation);
		super.checkInputBoxHasValue("startPeriod", startPeriod);
		super.checkInputBoxHasValue("endPeriod", endPeriod);
		super.checkInputBoxHasValue("quantity", quantity);
		super.checkInputBoxHasValue("additionalInfo", additionalInfo);
		
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/down/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeTest(final String code, 
		final String items, final String subject, final String explanation, final String startPeriod, final String endPeriod,
		final String quantity, final String additionalInfo) {
		
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my down");
		
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkButtonExists("Create");
		super.clickOnButton("Create");
		
		super.checkFormExists();
		
		// XXP
		
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(moment);
		
        final String year = String.valueOf(calendar.get(Calendar.YEAR));
        final char[] digitsYear = year.toCharArray();
        final String ten = digitsYear[2] + "0";
        final String one = digitsYear[0] + "";
        final Integer yearTwoDigits = Integer.parseInt(ten) + Integer.parseInt(one);
        
        final Integer month = calendar.get(Calendar.MONTH) + 1;
        final Integer day = calendar.get(Calendar.DAY_OF_MONTH);
        
        String monthS = String.valueOf(month);
        if (month <= 9) {
        	monthS = "0" + monthS;
        }
        
        String dayS = String.valueOf(day);
        if (day <= 9) {
        	dayS = "0" + dayS;
        }
        
        final String codeToday = "zz:" + yearTwoDigits + ":" + monthS + ":" + code + ":" + dayS;
        
        super.fillInputBoxIn("code", codeToday);
		super.fillInputBoxIn("items", items);
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("explanation", explanation);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("quantity", quantity);
		super.fillInputBoxIn("additionalInfo", additionalInfo);
		super.clickOnSubmit("Create");
	
		super.checkNotPanicExists();
		super.checkErrorsExist();

		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/down/create-negative-optionless.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeTestOptionless(final String code, final String items, final String subject, final String explanation, final String startPeriod, 
		final String endPeriod,	final String quantity, final String additionalInfo) {
		
		super.signIn("inventor3", "inventor3");
		super.clickOnMenu("Inventor", "List my down");
		
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkButtonExists("Create");
		super.clickOnButton("Create");
		
		super.checkFormExists();
		
		// XXP
		
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(moment);
		
        final String year = String.valueOf(calendar.get(Calendar.YEAR));
        final char[] digitsYear = year.toCharArray();
        final String ten = digitsYear[2] + "0";
        final String one = digitsYear[0] + "";
        final Integer yearTwoDigits = Integer.parseInt(ten) + Integer.parseInt(one);
        
        final Integer month = calendar.get(Calendar.MONTH) + 1;
        final Integer day = calendar.get(Calendar.DAY_OF_MONTH);
        
        String monthS = String.valueOf(month);
        if (month <= 9) {
        	monthS = "0" + monthS;
        }
        
        String dayS = String.valueOf(day);
        if (day <= 9) {
        	dayS = "0" + dayS;
        }
        
        final String codeToday = "zz:" + yearTwoDigits + ":" + monthS + ":" + code + ":" + dayS;
        
        super.fillInputBoxIn("code", codeToday);
		super.fillInputBoxIn("items", items);
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("explanation", explanation);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("quantity", quantity);
		super.fillInputBoxIn("additionalInfo", additionalInfo);
		super.clickOnSubmit("Create");
	
		super.checkNotPanicExists();
		super.checkErrorsExist();

		super.signOut();
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/down/create-negative-date.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(15)
	public void negativeTestDate(final String code, 
		final String items, final String subject, final String explanation, final String startPeriod, final String endPeriod,
		final String quantity, final String additionalInfo) {
		
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List my down");
		
		super.checkListingExists();
		super.sortListing(0, "asc");
		
		super.checkButtonExists("Create");
		super.clickOnButton("Create");
		
		super.checkFormExists();
		
        final String codeToday = "99-02-01:";
		
		super.fillInputBoxIn("code", codeToday + code);
		super.fillInputBoxIn("items", items);
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("explanation", explanation);
		super.fillInputBoxIn("startPeriod", startPeriod);
		super.fillInputBoxIn("endPeriod", endPeriod);
		super.fillInputBoxIn("quantity", quantity);
		super.fillInputBoxIn("additionalInfo", additionalInfo);
		super.clickOnSubmit("Create");
	
		super.checkNotPanicExists();
		super.checkErrorsExist();

		super.signOut();
	}
	
    @Test
    @Order(30)
    public void hackingTest() {
        super.checkNotLinkExists("Account");
        super.navigate("/inventor/down/create");
        super.checkPanicExists();

        super.signIn("administrator", "administrator");
        super.navigate("/inventor/down/create");
        super.checkPanicExists();
        super.signOut();

        super.signIn("patron1", "patron1");
        super.navigate("/inventor/down/create");
        super.checkPanicExists();
        super.signOut();
    }
}