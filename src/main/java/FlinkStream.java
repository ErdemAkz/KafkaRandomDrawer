import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;



public class FlinkStream implements Runnable,Serializable {

    public FlinkStream(){
        new Thread(this).start();
    }

    @Override
    public void run(){

        try {

            final StreamExecutionEnvironment streamEnv = StreamExecutionEnvironment.getExecutionEnvironment();

            Properties properties = new Properties();
            properties.setProperty("bootstrap.servers", "localhost:9092");
            properties.setProperty("group.id", "flink.learn.realtime");

            //Setup a Kafka Consumer on Flink
            FlinkKafkaConsumer<String> kafkaConsumer =
                    new FlinkKafkaConsumer<>
                            ("flink.kafka.streaming.source", //topic
                                    new SimpleStringSchema(), //Schema for data
                                    properties); //connection properties

            //Setup to receive only new messages
            kafkaConsumer.setStartFromLatest();

            //Create the data stream
            DataStream<String> kafkaTrailStr = streamEnv.addSource(kafkaConsumer);

            CollectSinkPanel sink = new CollectSinkPanel();
            kafkaTrailStr.addSink(sink);


            /*
            DataStream<Tuple5<Integer,Integer,Integer,Integer,Integer>> kafkaTrailObj = kafkaTrailStr
                    .map(new MapFunction<String,Tuple5<Integer,Integer,Integer,Integer,Integer>>() {
                        @Override
                        public Tuple5<Integer,Integer,Integer,Integer,Integer> map(String dataStr) {
                            System.out.println("--- Received Kafka Record : " + dataStr);
                            String[] strings = dataStr.split(",");
                            //pixel.add(new int[]{1,2,3,4,5});

                            return new Tuple5<Integer,Integer,Integer,Integer,Integer>(
                                    Integer.valueOf(strings[0]),
                                    Integer.valueOf(strings[1]),
                                    Integer.valueOf(strings[2]),
                                    Integer.valueOf(strings[3]),
                                    Integer.valueOf(strings[4])
                            );
                        }
                    });


           kafkaTrailObj.map(new MapFunction<Tuple5<Integer, Integer, Integer, Integer, Integer>, Object>() {
               private transient Integer x,y,r,b,g;
               @Override
               public Object map(Tuple5<Integer, Integer, Integer, Integer, Integer> number) throws Exception {

                   x = number.f0;
                   y = number.f1;
                   r = number.f2;
                   g = number.f3;
                   b = number.f4;

                   System.out.println("Session Summary : "
                           + (new Date()).toString()
                           + " x : " + number.f0
                           + " || y : " + number.f1
                           + " || r : " + number.f2
                           + " || g : " + number.f3
                           + " || b : " + number.f4);

                   return null;
               }
           });
               */

            /****************************************************************************
             *                  Setup data source and execute the Flink pipeline
             ****************************************************************************/

            //Start the Kafka Stream generator on a separate thread
            //Utils.printHeader("Starting Kafka Data Generator...");
            System.out.println("Starting Kafka Data Generator...");
            Thread StreamThread = new Thread(new StreamDataGenerator());
            StreamThread.start();

            streamEnv.setParallelism(1);
            // execute the streaming pipeline
            streamEnv.execute("Paint Events");
            new frame_of_project();
        }catch (Exception e){
            e.printStackTrace();
        }



    }

}
