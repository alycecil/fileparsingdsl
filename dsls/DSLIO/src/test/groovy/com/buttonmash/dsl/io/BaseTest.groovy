package com.buttonmash.dsl.io

import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class BaseTest {


    @Test(dataProvider = 'dummyData')
    public void dummy(testname){}


    @DataProvider(name='dummyData')
    public Object[][] dummyData(){
        [
                ['Dummy Test Data IO'],
        ]as Object[][]
    }

}
