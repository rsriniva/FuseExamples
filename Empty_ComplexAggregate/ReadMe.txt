Script for running through each of the components of this project.


1 – Create the REST interface
=============================
1. Define the REST Resource
- Already located in the resource class

2. Define the rest server
<cxf:rsServer id="restFrontend" address="http://localhost:9090/route" serviceClass="com.mycompany.camel.cxf.GetQuoteResource" />   

2 – Add “from” REST endpoint to the route
<from uri="cxfrs:bean:restFrontend"/>

Test the route.

2 – Split the message
=====================
split the message based on quote/company and send to external service.
        <split strategyRef="aggregateOrdersBean">
            <xpath>/quote/company</xpath>
            <to uri="direct:serviceProxy1"/>
        </split>

Test the splitter.html

3  - Implement the service call
===============================
- Add a transformation to transform to the getQuote service message.
<to uri="xslt:file:///home/graeme/.workspace/demo/ComplexAggregator/src/main/resources/xsl/quote.xsl"/>


- Set some headers
        <removeHeaders pattern="CamelHttp*"/>
        <setHeader headerName="Content-Type">
            <constant>text/xml</constant>
        </setHeader>

- Add the endpoint reference for the webservice (within camel context)
    <endpoint uri="http://www.webservicex.net/stockquote.asmx" id="callQuoteWebService"/>
	
- Call the webservice
	<to ref="callQuoteWebService"/>

- Test

- Extract the body of the soap requested
        <setBody>
            <xpath resultType="java.lang.String">
    			/soap:Envelope/soap:Body/ws:GetQuoteResponse/ws:GetQuoteResult
   			</xpath>
        </setBody>

- Test


4 – Aggregating the Results
===========================
- Show the aggregator web page
- We want to extract the orderid and numorders for aggregating.

        <setHeader headerName="orderid">
            <xpath>/quote/@orderid</xpath>
        </setHeader>
        <setHeader headerName="numorders">
            <xpath>/quote/@numorders</xpath>
        </setHeader>

- Then add the aggregator
- Add a new direct:aggregate route
<to uri="direct:aggregator"/>

- Add the bean referenced
 <bean id="aggregateOrdersBean" class="com.mycompany.beans.AggregateOrders"/>	

- Add the aggregator
        <aggregate strategyRef="aggregateOrdersBean">
            <correlationExpression>
                <header>orderid</header>
            </correlationExpression>
            <completionSize>
                <header>numorders</header>
            </completionSize>
            <log message="aggregated quote ${body}"/>
        </aggregate>

- Add the strategy ref to the splitter
 <split strategyRef="aggregateOrdersBean">

==========================================================

Properties
<cm:property-placeholder id="myConfig" persistent-id="io.fabric8.mytest" />

<propertyPlaceholder location="file:/home/graeme/.workspace/demo/NewProject/src/main/fabric8/io.fabric8.mytest.properties" id="properties"/>

