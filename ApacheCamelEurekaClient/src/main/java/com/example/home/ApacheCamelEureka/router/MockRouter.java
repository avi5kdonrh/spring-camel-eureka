package com.example.home.ApacheCamelEureka.router;

import gs_producing_web_service.GetCountryRequest;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.soap.SoapJaxbDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class MockRouter extends RouteBuilder {

    public static final String URL_DISCOVER_BOOKS = "%s/books?bridgeEndpoint=true";

    @Autowired
    private DiscoveryClient discoveryClient;



    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);
        SoapJaxbDataFormat soapJaxbDataFormat = new SoapJaxbDataFormat();
        soapJaxbDataFormat.setContextPath("gs_producing_web_service");
        soapJaxbDataFormat.setContentTypeHeader(false);

        rest("/serviceCall").get().produces(MediaType.APPLICATION_XML_VALUE).route()
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .process(exchange -> {
                    GetCountryRequest getCountryRequest = new GetCountryRequest();
                    getCountryRequest.setName("Spain");
                    exchange.getIn().setBody(getCountryRequest);
                })
                .marshal(soapJaxbDataFormat)
        .serviceCall("discovery-client/ws?bridgeEndpoint=true")
        //Is it becouse http endpoint produces a Stream as the body and once the stream is read, it is no longer available.
        .convertBodyTo(String.class)
        .log("Body: ${body}");

        rest("/discoveryCall").get().produces(MediaType.TEXT_XML_VALUE).route()
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setBody(constant("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                        "\t\t\t\t  xmlns:gs=\"http://spring.io/guides/gs-producing-web-service\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <gs:getCountryRequest>\n" +
                        "         <gs:name>Spain</gs:name>\n" +
                        "      </gs:getCountryRequest>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>"))
                .to(String.format("%s/ws?bridgeEndpoint=true", discoveryClient.getInstances("discovery-client").get(0).getUri()))
        //Is it becouse http endpoint produces a Stream as the body and once the stream is read, it is no longer available.
        .convertBodyTo(String.class)
        .log("Body: ${body}");
    }

}
