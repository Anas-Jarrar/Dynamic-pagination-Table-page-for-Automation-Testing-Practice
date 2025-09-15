package org.example;

import Pages.MainPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Comparator;
import java.util.List;

public class AppTest extends BaseTest {
    private MainPage mainPage;

    @Test(priority = 1, enabled = true)
    public void testSearchFunctionality()  {
        mainPage = new MainPage(driver,wait);
        navigate(MAIN_URL);
        mainPage.scrollDown();
        String expectedStudentName = "Alice Johnson";
        String actualStudentName = mainPage.searchFieldVerfiy(expectedStudentName);
        Assert.assertEquals(actualStudentName,expectedStudentName,""+expectedStudentName+"does not match the first element in the table" );
    }

    @Test(priority = 2, enabled = true)
    public void testNoMatchingResults() {
        mainPage = new MainPage(driver,wait);
        navigate(MAIN_URL);
        mainPage.scrollDown();
        String nonExistentName = "NoResult123";
        String expectedNoDataMessage="No matching records found";
        String actualNoData = mainPage.searchFieldVerfiy(nonExistentName);
        Assert.assertEquals(actualNoData, expectedNoDataMessage, ""+expectedNoDataMessage+"does not show correctly");
    }

    @Test(priority = 3, enabled = true)
    public void verifyTotalNumberOfPagesBasedOnEntries() {
        mainPage = new MainPage(driver,wait);
        navigate(MAIN_URL);
        mainPage.scrollDown();
        Assert.assertEquals(mainPage.getExpectedNumbPages(), mainPage.getActualNumofPages(),"There number of pages incorrect or the infomessage contains error");
    }

    @Test(priority = 4, enabled = true)
    public void testPreviousNextButtonFunctionality() throws InterruptedException {
        mainPage = new MainPage(driver,wait);
        navigate(MAIN_URL);
        mainPage.scrollDown();
        Thread.sleep(1000);
        List<String> capturePageData1 = mainPage.capturePageData();
        mainPage.clickNextButton();
        List<String> capturePageData2 = mainPage.capturePageData();
        Assert.assertNotEquals(capturePageData1, capturePageData2,"The data of page does not update accordingly ");
    }

    @Test(priority = 5,enabled = true)
    public void confirmNoActionForDisabledButtons() throws InterruptedException {
        mainPage = new MainPage(driver,wait);
        navigate(MAIN_URL);
        mainPage.scrollDown();
        Thread.sleep(1000);
        while (!mainPage.isNextDisabled()) {
            mainPage.clickNextButton();
        }
        Assert.assertTrue(mainPage.isNextDisabled(),"Next button is not disabled");
        while (!mainPage.isPreviousDisabled()) {
            mainPage.clickPreviousButton();
        }
        Assert.assertTrue(mainPage.isPreviousDisabled(), "Previous button is not disabled");
    }

    @Test(priority = 6, enabled = true)
    public void testSortingByStudentNameContent()throws InterruptedException {
        mainPage = new MainPage(driver, wait);
        navigate(MAIN_URL);
        mainPage.scrollDown();
        mainPage.selectEntryNums("10");
        List<String> expectedstudentNameDescending =mainPage.getColumnValues();
        expectedstudentNameDescending.sort(Comparator.reverseOrder());
        Thread.sleep(1000);
        mainPage.clickStudentNameHeader();
        List<String> actualDescendingOrder = mainPage.getColumnValues();
        Assert.assertEquals(actualDescendingOrder, expectedstudentNameDescending,
                "Table is not sorted in descending order");

        List<String> expectedSortedAscending = mainPage.getColumnValues();
        expectedSortedAscending.sort(Comparator.naturalOrder());
        mainPage.clickStudentNameHeader();
        List<String> actualAscendingOrder = mainPage.getColumnValues();
        Assert.assertEquals(actualAscendingOrder, expectedSortedAscending,
                "Table is not sorted in ascending order");
    }


    @Test(priority = 7,enabled = true)
    public void testEntriesDropdown() throws InterruptedException {
        mainPage = new MainPage(driver,wait);
        navigate(MAIN_URL);
        mainPage.scrollDown();
        String ExpectedTheNumOfEntries="5";
        mainPage.selectEntryNums(ExpectedTheNumOfEntries);
        Assert.assertEquals(mainPage.getNumOfTableRows(), ExpectedTheNumOfEntries,"Number of table rows does not match number of entries");
    }
    @Test(priority = 8,enabled = true)
    public void verifySearchPaginationWorksTogether() throws InterruptedException {
        mainPage = new MainPage(driver,wait);
        boolean notfound = false;
        String searchTerm = "Female";
        navigate(MAIN_URL);
        mainPage.scrollDown();
        Thread.sleep(1000);
        mainPage.setSearchField(searchTerm);
        while (true) {
            List<String> rows = mainPage.capturePageData();
            for (String row : rows) {
                if (!row.toLowerCase().contains(searchTerm.toLowerCase())) {
                    notfound = true;
                    break;
                }
            }
            if (mainPage.isNextDisabled()) {
                break;
            }
            mainPage.clickNextButton();
        }
        Assert.assertFalse(notfound, "One or more rows did not contain the search term: " + searchTerm);
    }

}
