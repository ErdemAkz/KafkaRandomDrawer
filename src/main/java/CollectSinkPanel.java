import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class CollectSinkPanel extends JPanel implements SinkFunction<String> {


    //public static final List<String> values = new CopyOnWriteArrayList<>();
    public static ArrayList<int[]> pixel = new ArrayList<>();

    @Override
    public synchronized void invoke(String value) throws Exception {

        String[] strings = value.split(",");
        pixel.add(new int[]{Integer.valueOf(strings[0]), Integer.valueOf(strings[1]),
                Integer.valueOf(strings[2]), Integer.valueOf(strings[3]), Integer.valueOf(strings[4])});

    }

    public void paint(Graphics graphics){
        super.paint(graphics);

        for (int i=0;i<pixel.size();i++){
            Color  myColor = new Color(pixel.get(i)[2],pixel.get(i)[3],pixel.get(i)[4]);
            graphics.setColor(myColor);
            graphics.drawOval(pixel.get(i)[0],pixel.get(i)[1],1,1);
        }

    }


}
