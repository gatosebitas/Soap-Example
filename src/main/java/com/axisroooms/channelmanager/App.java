package com.axisroooms.channelmanager;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmit;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.model.iface.Response;

public class App {
    public static void main(String[] args) {
        try {
            WsdlProject project = new WsdlProject();

            // this is locally hosted file
            String url = "file:/home/tech/Hotel Travel dot com/service.wsdl";
            WsdlInterface iface = WsdlInterfaceFactory.importWsdl(project, url, true)[0];

            // to print all operations supported by wsdl
            /*
             * for (Operation operation : iface.getOperationList()) {
             * WsdlOperation op = (WsdlOperation) operation;
             * System.out.println("OP:" + op.getName());
             * System.out.println(op.createRequest(true));
             * System.out.println("Response:");
             * System.out.println(op.createResponse(true)); }
             */

            // get valid operationName to perform on
            String operationName = "FetchRoomAllotment";
            WsdlOperation operation = iface.getOperationByName(operationName);
            // create a new empty request for that operation
            // replace ** with your credentials
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
                    + "  <soap:Header>\n" + "    <AuthenticationHeader xmlns=\"http://www.HotelTravel.com/\">\n"
                    + "      <Username>*****</Username>\n" + "      <Password>*****</Password>\n"
                    + "      <Hotelcode>KBILPL</Hotelcode>\n" + "    </AuthenticationHeader>\n" + "  </soap:Header>\n"
                    + "  <soap:Body>\n" + "    <FetchHotelAllotment xmlns=\"http://www.HotelTravel.com/\">\n"
                    + "      <xmlRequest>\n" + "         <Rooms>\n" + "            <Room Code=\"3\">\n"
                    + "            </Room>\n" + "        </Rooms>\n" + "      </xmlRequest>\n"
                    + "    </FetchHotelAllotment>\n" + "  </soap:Body>\n" + "</soap:Envelope>";

            WsdlRequest request = operation.addNewRequest("name");
            request.setRequestContent(xml);

            /*
             * request.setWssPasswordType("PasswordText");
             * request.setUsername(username); request.setPassword(password);
             */
            // request.setWssTimeToLive("10000");

            WsdlSubmit<WsdlRequest> submit = request.submit(new WsdlSubmitContext(project), false);
            // wait for the response
            Response response = submit.getResponse();
            System.out.println("response ::\n" + response);

            // print the response
            String content = response.getRequestContent();
            System.out.println("request content::\n" + content);
            System.out.println("response content::\n" + response.getContentAsString());

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            // Your Code to handle exception.
        }
    }
}
