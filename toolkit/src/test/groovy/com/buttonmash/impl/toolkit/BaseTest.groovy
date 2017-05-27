package com.buttonmash.impl.toolkit;

import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class BaseTest {
    @Test(dataProvider = 'dummyData')
    public void dummy(testname){}


    @DataProvider(name='dummyData')
    public Object[][] dummyData(){
        [
                ['Dummy Test Project'],
        ] as Object[][]
    }
    
}
