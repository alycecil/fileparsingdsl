# Language Guide 
## Input/Output File Specification Domain Specific Language
[Alice Cecil](https://github.com/alycecil) 
3/12/2017


This language defines

#Base Definitions 
## {Identifier}
These are your business names or definitions on which we are writing, reading or operating on.

Examples
Last Name, First Name, Claim Number, Date of Injurty


## {Literal}
A static value. 

##Common Notes
Depending on your file source, your fields will be more than likely read as a string. 

# Input from file to a value

`[` `{Input Location}` `->` `{Identifier}` *( `{Type Definitions}` )* `]`

## `{Input Location}`

### FOR FIXED WIDTH FILES
[X] TODO
[ ] DONE

By Ones indexed positions
- { Start as `[:digit:]+` } `,` { End as `[:digit:]+` }
-- For a 10 length field starting at position 1 and ending before character 11 ```1,11```


By field length
- LENGTH { length as `[:digit:]+` }
-- For a length 10 field starting at the end of the last ```LENGTH 10```


### FOR CSV
[X] TODO
[ ] DONE

By Ones indexed positions
-- { position as `[:digit:]+` }
--- For position 8 ```8```


### FOR EXCEL
[X] TODO
[ ] DONE

By Position Letter 
-- { position as `[:alpha:]+` }
--- For position A ```A```
--- For position HD ```hd```


By Ones indexed positions
-- { position as `[:digit:]+` }
--- For position 8 ```8```


### FOR XML
[X] TODO
[ ] DONE

By relative xpath

Sample Xml
```<root>
   <record>
        <number>1</number>
   </record>
   <record>
        <number>999</number>
        <string>Value</string>
   </record>
</root>
```


```
root record path
```

### FOR JSON
[X] TODO
[ ] DONE

By relative path

Sample 
``` { 
}
```

```
root record path
```

## `->` Operator
[ ] TODO
[X] DONE

End of `{Input Location}`

NOP

## `{Identifier}`
[X] TODO
[ ] DONE


## Optional `{Type Definitions}`
[X] TODO
[ ] DONE