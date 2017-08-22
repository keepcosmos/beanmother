### Setter / Add Method Map
* find value setter or add
    * Parameter size == 1
        * Find Converter
        * Check Matching type
        * Check settable
            * Set
        * Find Initializer
        * Initialize
            -> Go to first
        * Assign
    * Parameter size > 1
        * is fixture array or Map & check size
            * Find Converters
                * Convert
            * Check Matching type
                * Assign
            * Find Initializer
            * Initialize
                -> Go to first
            * Assign
    * Parameter size == 0 -> Fail
    
* Fallback


### Field Map

* find field

 
2-1. parameter size == 1
     if Check Converter
         
2-2. parameter size > 1
     3. fixture is array
         Check param size and check type
     4. fixture is map
         Check param size and check type
     5. else
         -> Fallback
2-3. parameter size == 0
 Fallback
 Fallback FieldSetting