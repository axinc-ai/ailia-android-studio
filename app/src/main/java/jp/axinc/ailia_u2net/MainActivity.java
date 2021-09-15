package jp.axinc.ailia_u2net;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import axip.ailia.*;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.content.res.Resources;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import java.nio.ByteBuffer;
import java.util.EnumSet;

public class MainActivity extends AppCompatActivity {
    //Load prototxt and onnx file to byte array
    byte[] inputStreamToByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try(BufferedOutputStream out = new BufferedOutputStream(bout)) {
            byte[] buf = new byte[128];
            int n = 0;
            while ((n = in.read(buf)) > 0) {
                out.write(buf, 0, n);
            }
        }

        return bout.toByteArray();
    }

    byte[] loadRawFile(int resourceId) throws IOException {
        Resources resources = this.getResources();
        try (InputStream in = resources.openRawResource(resourceId)) {
            return inputStreamToByteArray(in);
        }
    }

    //Load test image as byte array
    byte[] loadRawImage(Bitmap bmp) throws IOException {
        int w = bmp.getWidth(), h  = bmp.getHeight();
        int[] pixels = new int[w * h];
        bmp.getPixels(pixels, 0, w, 0, 0, w, h);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bout);
        for (int i : pixels) {
            out.writeByte((i>>16)&0xff);    //r
            out.writeByte((i>>8)&0xff);     //g
            out.writeByte((i>>0)&0xff);     //b
            out.writeByte((i>>24)&0xff);    //a
        }
        return bout.toByteArray();
    }

    //preprocesss of u2net
    //  input : input image (byte array) (rgba)
    //  output : cnn input (float) (channel first) (bgr)
    void preprocess(float [] dst,int dst_width,int dst_height,byte [] src,int src_width,int src_height)
    {
        //java byte range is -128 - 127
        //so we need to use &0xff for cast

        int max_value=1;
        for(int y=0;y<src_height;y++){
            for(int x=0;x<src_width;x++){
                for(int rgb=0;rgb<3;rgb++){
                    int value=(src[y*src_width*4+x*4+rgb] & 0xff);
                    if(max_value<value){
                        max_value=value;
                    }
                }
            }
        }

        float [] mean = {0.485f, 0.456f, 0.406f};
        float [] std = {0.229f, 0.224f, 0.225f};

        //src : channel last (bgr order)
        //dst : channel first (bgr order)

        for(int y=0;y<dst_height;y++){
            for(int x=0;x<dst_width;x++){
                float sx_f=x*src_width/dst_width;
                float sy_f=y*src_height/dst_height;

                int sx1=(int)sx_f;
                int sy1=(int)sy_f;
                int sx2=((sx1+1) < (src_width -1)) ? sx1+1:src_width-1;
                int sy2=((sy1+1) < (src_height-1)) ? sy1+1:src_height-1;

                int a=(int)(sx_f-sx1);
                int b=(int)(sy_f-sy1);

                for(int i=0;i<3;i++){
                    //bilinear
                    int v1=(src[sy1*src_width*4+sx1*4+i] & 0xff);
                    int v2=(src[sy1*src_width*4+sx2*4+i] & 0xff);
                    int v3=(src[sy2*src_width*4+sx1*4+i] & 0xff);
                    int v4=(src[sy2*src_width*4+sx2*4+i] & 0xff);
                    int v=(int)((v1*(1-a)+v2*a)*(1-b)+(v3*(1-a)+v4*a)*b);
                    int i_bgr=2-i;  //rgb to bgr
                    dst[y*dst_width+x+i_bgr*dst_width*dst_height]=(1.0f*v/max_value - mean[i]) / std[i];
                }
            }
        }
    }

    //postprocess of u2net
    //  input : cnn output (float) (channel first) (alpha)
    //  output : output image (byte array) (rgba)
    void postprocess(byte [] dst,int dst_width,int dst_height,float [] src,int src_width,int src_height)
    {
        float min =  Float.MAX_VALUE;
        float max = -Float.MAX_VALUE;
        for(int y=0;y<src_height;y++){
            for(int x=0;x<src_width;x++){
                float value=src[y*src_width+x];
                if(min>value){
                    min=value;
                }
                if(max<value){
                    max=value;
                }
            }
        }

        for(int y=0;y<dst_height;y++){
            for(int x=0;x<dst_width;x++){
                //bilinear
                float sx_f=x*src_width/dst_width;
                float sy_f=y*src_height/dst_height;

                int sx1=(int)sx_f;
                int sy1=(int)sy_f;
                int sx2=((sx1+1) < (src_width -1)) ? sx1+1:src_width-1;
                int sy2=((sy1+1) < (src_height-1)) ? sy1+1:src_height-1;

                int a=(int)(sx_f-sx1);
                int b=(int)(sy_f-sy1);

                float v1=src[sy1*src_width+sx1];
                float v2=src[sy1*src_width+sx2];
                float v3=src[sy2*src_width+sx1];
                float v4=src[sy2*src_width+sx2];
                float v=(v1*(1-a)+v2*a)*(1-b)+(v3*(1-a)+v4*a)*b;

                float alpha_value = ((v-min) / (max-min));

                for(int i=0;i<3;i++){
                    int i_rgb=2-i;  //bgr to rgb
                    int adr = y*dst_width*4+x*4+i_rgb;
                    int va = (int)((int)(dst[adr] & 0xff) * alpha_value);
                    dst[adr] = (byte)va;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ailia_u2net(savedInstanceState);
    }

    protected void ailia_u2net(Bundle savedInstanceState) {
        try {
            //mode
            boolean lowMemoryMode = false;
            boolean updateApiMode = true;

            //set temporary cache path for gpu
            Ailia.SetTemporaryCachePath(getCacheDir().getAbsolutePath());

            //load test image
            final int imageId = R.raw.input;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), imageId, options);
            byte[] img = loadRawImage(bmp);
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            Log.i("AILIA_Main","input image : "+width+" , "+height);

            //display test image
            Bitmap bitmap = Bitmap.createBitmap(width , height, Bitmap.Config.ARGB_8888);
            bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(img));

            ImageView image = (ImageView) findViewById(R.id.u2net_image_view);
            image.setImageBitmap(bitmap);

            //create ailia instance
            int envId = 0;
            AiliaModel ailia;
            if(lowMemoryMode) {
                // low memory mode
                EnumSet<AiliaMemoryMode> memory_mode = AiliaModel.getMemoryMode(true, true, true, false);
                ailia = new AiliaModel(envId, Ailia.MULTITHREAD_AUTO, memory_mode,
                        loadRawFile(R.raw.u2netp_opset11_proto), loadRawFile(R.raw.u2netp_opset11_weight));
            }else {
                // normal memory mode
                ailia = new AiliaModel(envId, Ailia.MULTITHREAD_AUTO,
                        loadRawFile(R.raw.u2netp_opset11_proto), loadRawFile(R.raw.u2netp_opset11_weight));
            }

            //prepare input and output buffer
            AiliaShape input_shape;
            AiliaShape output_shape;
            if(!updateApiMode) {
                // single input and output api
                input_shape = ailia.getInputShape();
                output_shape = ailia.getOutputShape();
            }else{
                // multiple input and output api
                input_shape = ailia.getBlobShape(ailia.getBlobIndexByInputIndex(0));
                output_shape = ailia.getBlobShape(ailia.getBlobIndexByOutputIndex(0));
            }
            Log.i("AILIA_Main", "input shape : " + input_shape.x+" "+input_shape.y+" "+input_shape.z+" "+input_shape.w+" "+input_shape.dim);
            Log.i("AILIA_Main", "output shape : " + output_shape.x+" "+output_shape.y+" "+output_shape.z+" "+output_shape.w+" "+output_shape.dim);

            int input_size = input_shape.x*input_shape.y*input_shape.z*input_shape.w;
            float [] input_buf = new float[input_size];

            preprocess(input_buf, input_shape.x, input_shape.y, img, width, height);

            int preds_size = output_shape.x*output_shape.y*output_shape.z*output_shape.w;
            float [] output_buf = new float[preds_size];

            //compute
            int float_to_byte = 4;
            if(!updateApiMode) {
                // single input and output api
                Log.i("AILIA_Main", "predict API mode");
                ailia.predict(output_buf, preds_size * float_to_byte, input_buf, input_size * float_to_byte);
            }else{
                // multiple input and output api
                Log.i("AILIA_Main", "update API mode");
                ailia.setInputBlobData(input_buf,input_size*float_to_byte,ailia.getBlobIndexByInputIndex(0)); // if the model has multiple input, please repeat this line
                ailia.update();
                ailia.getBlobData(output_buf,preds_size*float_to_byte,ailia.getBlobIndexByOutputIndex(0));
            }

            //postprocessing
            postprocess(img, width, height, output_buf, output_shape.x, output_shape.y);

            Log.i("AILIA_Main","finish prediction");

            //display output
            bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(img));
            image.setImageBitmap(bitmap);

            //close is called from AutoCloseable
        }
        catch (Exception e) {
            Log.i("AILIA_Error", e.getClass().getName() + ": " + e.getMessage());
        }
    }

    //Important : load ailia library
    static {
        System.loadLibrary("ailia");
    }
}
