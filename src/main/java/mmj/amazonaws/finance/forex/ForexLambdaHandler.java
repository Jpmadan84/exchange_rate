package mmj.amazonaws.finance.forex;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;


public class ForexLambdaHandler  {

	private static final String endpoint ="https://api.exchangeratesapi.io/latest?base=JPY&symbols=INR";
    public void handleRequest(Context context) {
        context.getLogger().log("Hello from Lambda!");
        Client client =ClientBuilder.newClient();
        RestResponse rr=client.target(endpoint).request(MediaType.APPLICATION_JSON).get(RestResponse.class);
        Double rate = rr.getRates().getInr();
        AmazonDynamoDB dbClient =AmazonDynamoDBClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_1).build();
        DynamoDB dynamoDB =new DynamoDB(dbClient);
        Table table=dynamoDB.getTable("tbl_exchange_rate");
        table.putItem(new Item().withPrimaryKey("timestamp",LocalDateTime.now(ZoneId.of("Asia/Tokyo")).toString()).with("rate", rate));
        System.out.println(rate);       
       
    }

}
