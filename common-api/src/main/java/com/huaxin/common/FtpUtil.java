package com.huaxin.common;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;

import com.jcraft.jsch.*;
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;

public class FtpUtil {

        private static final int DEF_WAIT_SECONDS = 30;

        public static boolean uploadFile(String host, int port, String username, String password, String basePath,
                                         String filePath, String filename, InputStream input) throws SftpException{
            boolean result=false;
            Session session = openSession(host,port, username, password, DEF_WAIT_SECONDS);
            ChannelSftp sftp=openChannelSftp(session);
            try {
                sftp.cd(basePath);
                sftp.cd(filePath);
            } catch (SftpException e) {
                //目录不存在，则创建文件夹
                String [] dirs=filePath.split("/");
                String tempPath=basePath;
                for(String dir:dirs){
                    if(null== dir || "".equals(dir)) continue;
                    tempPath+="/"+dir;
                    try{
                        sftp.cd(tempPath);
                    }catch(Exception ex){
                        sftp.mkdir(tempPath);
                        sftp.cd(tempPath);
                    }
                }
            }
            sftp.put(input, filename);  //上传文件
            String imgsrc=basePath+filePath+"/"+filename;
            String imgdist=basePath+filePath+"/01/"+filename;
//            reduceImg(imgsrc,imgdist,0,0,1.0f);
//            File oldFile=new File(imgsrc);
//            File newFile=new File(imgsrc);
            //zipImageFile(oldFile,newFile,16,9,1f);
            return true;
        }

    /**
     * 根据设置的宽高等比例压缩图片文件<br> 先保存原文件，再压缩、上传
     * @param oldFile 要进行压缩的文件
     * @param newFile 新文件
     * @param width 宽度 //设置宽度时（高度传入0，等比例缩放）
     * @param height 高度 //设置高度时（宽度传入0，等比例缩放）
     * @param quality 质量
     * @return 返回压缩后的文件的全路径
     */
    private static String zipImageFile(File oldFile,File newFile, int width, int height,float quality) {
        if (oldFile == null) {
            return null;
        }
        try {
            /** 对服务器上的临时文件进行处理 */
            Image srcFile = ImageIO.read(oldFile);
            int w = srcFile.getWidth(null);
            int h = srcFile.getHeight(null);
            double bili;
            if(width>0){
                bili=width/(double)w;
                height = (int) (h*bili);
            }else{
                if(height>0){
                    bili=height/(double)h;
                    width = (int) (w*bili);
                }
            }
            String srcImgPath = newFile.getAbsoluteFile().toString();
            System.out.println(srcImgPath);
            String subfix = "jpg";
            subfix = srcImgPath.substring(srcImgPath.lastIndexOf(".")+1,srcImgPath.length());
            BufferedImage buffImg = null;
            if(subfix.equals("png")){
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            }else{
                buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            }
            Graphics2D graphics = buffImg.createGraphics();
            graphics.setBackground(new Color(255,255,255));
            graphics.setColor(new Color(255,255,255));
            graphics.fillRect(0, 0, width, height);
            graphics.drawImage(srcFile.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            ImageIO.write(buffImg, subfix, new File(srcImgPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile.getAbsolutePath();
    }

        /**
         * 创建服务器连接
         *
         * @param host
         *            主机
         * @param user
         *            用户
         * @param password
         *            密码
         * @param waitSeconds
         *            等待秒数
         * @return
         */
        private static Session openSession(String host,int port, String user, String password, int waitSeconds) {
            Session session = null;
            try {
                JSch jsch = new JSch();
                session = jsch.getSession(user, host, port);
                noCheckHostKey(session);
                session.setPassword(password);
                // 这个设置很重要，必须要设置等待时长为大于等于2分钟
                session.connect(waitSeconds * 1000);
                if (!session.isConnected()) {
                    throw new IOException("We can't connection to[" + host + "]");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return session;
        }

        /**
         * 不作检查主机键值
         *
         * @param session
         */
        private static void noCheckHostKey(Session session) {
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
        }

        /**
         * 连接shell
         *
         * @param session
         *            session
         * @return {@link ChannelShell}
         */
        private static ChannelShell openChannelShell(Session session) {
            ChannelShell channel = null;
            try {
                channel = (ChannelShell) session.openChannel("shell");
                channel.setEnv("LANG", "en_US.UTF-8");
                channel.setAgentForwarding(false);
                channel.setPtySize(500, 500, 1000, 1000);
                channel.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (channel == null) {
                throw new IllegalArgumentException("The channle init was wrong");
            }
            return channel;
        }

        /**
         * 连接sftp
         *
         * @param session
         * @return {@link ChannelSftp}
         */
        private static ChannelSftp openChannelSftp(Session session) {
            ChannelSftp channel = null;
            try {
                channel = (ChannelSftp) session.openChannel("sftp");
                channel.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return channel;
        }

//    public static void reduceImg(String imgsrc, String imgdist, int widthdist, int heightdist, Float rate) {
//        try {
//            File srcfile = new File(imgsrc);
//            // 检查图片文件是否存在
//            if (!srcfile.exists()) {
//                System.out.println("文件不存在");
//            }
//            // 如果比例不为空则说明是按比例压缩
//            if (rate != null && rate > 0) {
//                //获得源图片的宽高存入数组中
//                int[] results = getImgWidthHeight(srcfile);
//                if (results == null || results[0] == 0 || results[1] == 0) {
//                    return;
//                } else {
//                    //按比例缩放或扩大图片大小，将浮点型转为整型
//                    widthdist = (int) (results[0] * rate);
//                    heightdist = (int) (widthdist/16*9);
//                }
//            }
//            // 开始读取文件并进行压缩
//            Image src = ImageIO.read(srcfile);
//
//            // 构造一个类型为预定义图像类型之一的 BufferedImage
//            BufferedImage tag = new BufferedImage((int) widthdist, (int) heightdist, BufferedImage.TYPE_INT_RGB);
//
//            //绘制图像  getScaledInstance表示创建此图像的缩放版本，返回一个新的缩放版本Image,按指定的width,height呈现图像
//            //Image.SCALE_SMOOTH,选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
//            tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist, Image.SCALE_SMOOTH), 0, 0, null);
//            //创建文件输出流
//            FileOutputStream out = new FileOutputStream(imgdist);
//            //将图片按JPEG压缩，保存到out中
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            encoder.encode(tag);
//            //关闭文件输出流
//            out.close();
//        } catch (Exception ef) {
//            ef.printStackTrace();
//        }
//    }

    /**
     * 获取图片宽度和高度
     *
     * @param
     * @return 返回图片的宽度
     */
    public static int[] getImgWidthHeight(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = { 0, 0 };
        try {
            // 获得文件输入流
            is = new FileInputStream(file);
            // 从流里将图片写入缓冲图片区
            src = ImageIO.read(is);
            result[0] =src.getWidth(null); // 得到源图片宽
            result[1] =src.getHeight(null);// 得到源图片高
            is.close();  //关闭输入流
        } catch (Exception ef) {
            ef.printStackTrace();
        }

        return result;
    }
}

    /**
     * Description: 向FTP服务器上传文件
     * @param host FTP服务器ip
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param basePath FTP服务器基础目录,/home/ftpuser/images
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2018/05/28。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input 输入流
     * @return 成功返回true，否则返回false
     */
//    public static boolean uploadFile(String host, int port, String username, String password, String basePath,
//                                     String filePath, String filename, InputStream input) {
//        boolean result = false;
//        channel = getChannel();
//        if (channel == null || session == null || sftp == null) {
//            return false;
//        }
//
//        try {
//            try {
//                sftp.cd(directory); // 进入目录
//            } catch (SftpException sException) {
//                if (sftp.SSH_FX_NO_SUCH_FILE == sException.id) { // 指定上传路径不存在
//                    sftp.mkdir(directory);// 创建目录
//                    sftp.cd(directory); // 进入目录
//                }
//            }
//
//            File file = new File(uploadFile);
//            InputStream in = new FileInputStream(file);
//
//            sftp.put(in, file.getName());
//            in.close();
//
//        } catch (Exception e) {
//            throw new Exception(e.getMessage(), e);
//        } finally {
//            disConn();
//        }
//
//        return true;
//    }
//
//    private static void closeChannel(Channel channel) {
//        if (channel != null) {
//            if (channel.isConnected()) {
//                channel.disconnect();
//            }
//        }
//    }
//
//    private static void closeSession(Session session) {
//        if (session != null) {
//            if (session.isConnected()) {
//                session.disconnect();
//            }
//        }
//    }
//}
