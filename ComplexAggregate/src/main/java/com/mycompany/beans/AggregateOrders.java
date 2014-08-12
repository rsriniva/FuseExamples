package com.mycompany.beans;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class AggregateOrders implements AggregationStrategy{

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange ) {
		
        if (oldExchange == null) {
            return newExchange;
        }
 
        String oldBody = oldExchange.getIn().getBody(String.class);
        String newBody = newExchange.getIn().getBody(String.class);
        newExchange.getOut().setBody(oldBody  + newBody);
        newExchange.getIn().setBody(oldBody  + newBody);
        return newExchange;
	}
	

}
