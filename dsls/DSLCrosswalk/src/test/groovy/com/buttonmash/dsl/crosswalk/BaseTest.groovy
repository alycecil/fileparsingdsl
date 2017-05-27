package com.buttonmash.dsl.crosswalk

import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class BaseTest {


    @Test(dataProvider = 'dummyData')
    public void dummy(testname){}


    @DataProvider(name='dummyData')
    public Object[][] dummyData(){
        [
                ['Dummy Test Data XWalk'],
        ]as Object[][]
    }

}
