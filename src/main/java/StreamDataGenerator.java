import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.*;

/****************************************************************************
 * This Generator generates a series of data files in the raw_data folder
 * It is an audit trail data source.
 * This can be used for streaming consumption of data by Flink
 ****************************************************************************/

public class StreamDataGenerator implements Runnable {


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) {

    }

    public void run() {

        try {
            //Setup Kafka Client
            Properties kafkaProps = new Properties();
            kafkaProps.put("bootstrap.servers","localhost:9092");

            kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

            Producer<String,String> myProducer = new KafkaProducer<String, String>(kafkaProps);

            Random random = new Random();

            int r,g,b,x,y;
            for(int i=0; i < 100000; i++) {

                r = random.nextInt(256);
                g = random.nextInt(256);
                b = random.nextInt(256);

                x = random.nextInt(Screen.heigth+1);
                y = random.nextInt(Screen.heigth+1);

                String[] csvText = {""+x,""+y,""+r,""+g,""+b};

                ProducerRecord<String, String> record =
                        new ProducerRecord<String,String>(
                                "flink.kafka.streaming.source",
                                String.join(",", csvText)  );

                RecordMetadata rmd = myProducer.send(record).get();

                //System.out.println(ANSI_PURPLE + "Kafka Stream Generator : Sending Event : " + String.join(",", csvText)  + ANSI_RESET);

                Thread.sleep(50);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
