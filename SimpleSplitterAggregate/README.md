- There is a timer route that just manually constructs an xml message containing <orders><order>order1</order><order>order2</order></orders> and fires this to the main route every 10 seconds.
- The main route splits the orders and sends each order to the k8proxy route (this is where you would put a web service all)
- The k8proxy just changes the xml to add a quote - 
        <quote>
                <order>order1</order>
                <price>£150.49</price>
        </quote>
- The splitter has an aggregator atttached defined by the strategyRef="myStrategy" (myStrategy references a bean which implements org.apache.camel.processor.aggregate.AggregationStrategy)
- The aggregator uses java to combine the exchanges

If using the zip, unpack it and then in eclipse, right click on the projects explorer view and import -> maven -> existing maven projects -> [root directory of where you have unzipped the project]
- Run the project (right click on the camel-contect.xml and "run as local camel context (without tests)"
- The log will show the orders being split and then aggregated (repeated every 10 seconds).
