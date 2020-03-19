package mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class UserDrawReadHBaseMapReduce {
    public static class UserDrawReadHBaseMapper extends TableMapper<Text, NullWritable>{
        @Override
        protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
            StringBuffer sb = new StringBuffer(Bytes.toString(key.get(),key.getOffset(),key.getLength()) + "|");
            byte[] maleByte = value.getValue(Bytes.toBytes("f1"), Bytes.toBytes("male"));
            if(maleByte != null && maleByte.length != 0) {
                sb.append(Bytes.toDouble(maleByte)+"|");
            }
            byte[] femaleByte = value.getValue(Bytes.toBytes("f1"), Bytes.toBytes("female"));
            if(femaleByte != null && femaleByte.length != 0) {
                sb.append(Bytes.toDouble(femaleByte)+"|");
            }

            byte[] age1Byte = value.getValue(Bytes.toBytes("f1"), Bytes.toBytes("age1"));
            if(age1Byte != null && age1Byte.length != 0) {
                sb.append(Bytes.toDouble(age1Byte)+"|");
            }
            byte[] age2Byte = value.getValue(Bytes.toBytes("f1"), Bytes.toBytes("age2"));
            if(age2Byte != null && age2Byte.length != 0) {
                sb.append(Bytes.toDouble(age2Byte)+"|");
            }
            byte[] age3Byte = value.getValue(Bytes.toBytes("f1"), Bytes.toBytes("age3"));
            if(age3Byte != null && age3Byte.length != 0) {
                sb.append(Bytes.toDouble(age3Byte)+"|");
            }
            byte[] age4Byte = value.getValue(Bytes.toBytes("f1"), Bytes.toBytes("age4"));
            if(age4Byte != null && age4Byte.length != 0) {
                sb.append(Bytes.toDouble(age4Byte)+"|");
            }
            byte[] age5Byte = value.getValue(Bytes.toBytes("f1"), Bytes.toBytes("age5"));
            if(age5Byte != null && age5Byte.length != 0) {
                sb.append(Bytes.toDouble(age5Byte)+"|");
            }
            context.write(new Text(sb.toString()), NullWritable.get());
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum","node4:2181");
        Job job = Job.getInstance(conf);

        job.setJobName("readhbasedemo");
        job.setJarByClass(UserDrawReadHBaseMapReduce.class);
        Scan scan = new Scan();
        //初始化hbasemapper
        TableMapReduceUtil.initTableMapperJob("ns6:t_draw",scan,UserDrawReadHBaseMapper.class,Text.class,NullWritable.class,job);

        job.setNumReduceTasks(0);

        FileOutputFormat.setOutputPath(job, new Path("file:///C:\\yarnData\\userDraw\\fromhbase"));

        job.waitForCompletion(true);


    }
}
