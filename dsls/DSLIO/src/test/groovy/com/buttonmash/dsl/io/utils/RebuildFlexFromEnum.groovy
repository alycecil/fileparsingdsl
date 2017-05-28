/**
 * Created by alyce on 5/27/2017.
 */

package com.buttonmash.dsl.io.utils

import com.buttonmash.dsl.io.LogicKeywords

LogicKeywords.values().each {
    println """\t"${it.name()}"\t|"""
}
