package com.eyesmart.testapplication.android;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Build;
import android.widget.MediaController;
import android.widget.VideoView;

import com.eyesmart.testapplication.R;

import java.io.IOException;
import java.util.HashMap;

/**
 * 多媒体：音频、视频、相机
 */

public class TestMedia {
    static Context context;

    void test() throws IOException {
        testMediaPlayer();          //MediaPayer，播放音频、视频
        testSoundPool(R.raw.test);  //SoundPool，播放音频
        testVideoView();            //VideoView，播放视频

        testMediaRecorder();        //MediaRecorder,录制音频、视频
    }

    /**
     * 资源占用较高，延迟时间较长
     * 不支持多个音频同时播放
     */
    private void testMediaPlayer() throws IOException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //mediaPlayer.setOnCompletionListener(beepListener);//可设置多种监听（完毕、错误、prepare、seekTo时等）
        //mediaPlayer.setVolume(0.50f, 0.50f);              //设置音量
        //mediaPlayer.setDisplay(new SurfaceView(context).getHolder());                      //通过SurfaceView播放视频，surfaceCreated时设置
        //mediaPlayer.setSurface(new Surface(new TextureView(context).getSurfaceTexture())); //通过TextureView播放视频，onSurfaceTextureAvailable时设置
        mediaPlayer.setDataSource("/mnt/sdcard/test.mp3");  //装载音频
        mediaPlayer.prepare();                              //装载后必须准备声音，异步prepareAsync()
        mediaPlayer.start();                                //播放

        mediaPlayer.release();                              //释放媒体资源
        mediaPlayer.stop();                                 //停止
        mediaPlayer.pause();                                //暂停
        mediaPlayer.seekTo(0);                              //到指定的时间位置
        mediaPlayer.isPlaying();                            //是否正在播放

        //装载
//        mediaPlayer.setDataSource(context, Uri.parse("http://www.crazyit.org/abc.mp3"));//可以装载网络音频文件
//        AssetFileDescriptor fd = context.getResources().openRawResourceFd(R.raw.test);
//        mediaPlayer.setDataSource(fd.getFileDescriptor());
//        mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());//装载fd所代表的文件，从offset开始、长度为length的文件内容
//        fd.close();
        //可设置多种音效
    }

    /**
     * 适合播放密集、短促的音效
     * 资源占用小，延迟小
     * 支持同时播放
     */
    private void testSoundPool(int rawId) {
        HashMap<Integer, Integer> soundMap = new HashMap<>();
        /**1、创建SoundPool*/
        SoundPool soundPool;
        if (Build.VERSION.SDK_INT >= 21) {
            //AudioAttributes是一个封装音频各种属性的类
            AudioAttributes attr = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)               //音效使用场景
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC) //音效类型
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)     //设置音频流的合适属性
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attr)
                    .setMaxStreams(1)                                   //同时播放音效个数
                    .build();
        } else {
            //参数：1、同时播放音效个数，2、声音类型，第三个是声音品质（目前无作用）
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
        /**2、加载音频*/
        //用HashMap帮助记忆id（即传入池中的顺序）
        soundMap.put(R.raw.test, soundPool.load(context, R.raw.test, 1));//1为优先级（目前无作用）
        //播放是异步方式，不会阻塞主线程
        //参数：1、Map中取值（即传入池中的顺序） 2、左侧音量 3、右侧音量 4、优先级 5、重播次数 -1带表永远循环 6、播放速度
        int streamID = soundPool.play(soundMap.get(rawId), 1, 1, 0, 0, 1);//streamID非0时为成功
        //优先级参数：流的优先级，值越大优先级高，影响当同时播放数量超出了最大支持数时SoundPool对该流的处理
        //最后一个参数播放比率，范围0.5到2，通常为1表示正常播放

        soundPool.pause(streamID);  //暂停，传入streamID
        soundPool.resume(streamID); //继续播放
        soundPool.autoPause();      //暂停所有
        soundPool.autoResume();
        soundPool.stop(streamID);   //停止
        soundPool.release();        //回收Pool中的资源
    }

    /**
     * VideoView组件，用于播放视频
     */
    private void testVideoView() {
        VideoView videoView = new VideoView(context);
        videoView.setVideoPath("/mnt/sdcard/test.mp4");         //设置视频资源
        //videoView.setVideoURI(Uri.parse("http://www.crazyit.org/abc.mp3"));
        new MediaController(context).setMediaPlayer(videoView); //添加MediaController，提供图形控制界面

        videoView.start();          //视频控制等方法……
        videoView.pause();
        videoView.stopPlayback();
    }

    /**
     * MediaRecorder，用于录制音频、视频
     */
    private void testMediaRecorder() throws IOException {
        //初始状态
        MediaRecorder recorder = new MediaRecorder();
        //已初始化状态
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);         //设置声音来源，MIC为麦克风
        //数据源配置状态，这期间可以设定编码方式、输出文件、屏幕旋转、预览显示等等
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); //设置输出格式（必须在设置编码格式之前）
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);    //设置编码格式
        recorder.setOutputFile("/mnt/sdcard/test.amr");                 //设置文件保存位置
        recorder.prepare();

        recorder.start();
        recorder.stop();
        recorder.reset();       //回到初始状态
        recorder.release();
    }
}