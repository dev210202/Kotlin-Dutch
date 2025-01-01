package com.dutch2019;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleWebServer {

    private static Bitmap getScreenshotSubset(Bitmap screenshot, int x, int y, int width, int height) {
        return Bitmap.createBitmap(screenshot, x, y, width, height);
    }

    public static void serveScreenshotSubset(Bitmap screenshot, int x, int y, int width, int height) throws IOException {
        Bitmap subset = getScreenshotSubset(screenshot, x, y, width, height);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        subset.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("서버가 시작되었습니다. http://localhost:" + port);


        try {
            new ProcessBuilder("open", "http://localhost:"+port).start();
        } catch (IOException e) {
            System.err.println("URL 열기 실패: " + e.getMessage());
        }
        // 클라이언트 연결 대기
        Socket clientSocket = serverSocket.accept();

        // HTTP 응답 작성
        OutputStream out = clientSocket.getOutputStream();
        String httpResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: image/png\r\n" +
                "Content-Length: " + imageBytes.length + "\r\n" +
                "\r\n";
        out.write(httpResponse.getBytes());
        out.write(imageBytes);

        // 리소스 정리
        out.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("이미지 전송이 완료되었습니다.");


    }


}