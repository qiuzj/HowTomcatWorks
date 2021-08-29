package ex01.pyrmont;

import java.io.InputStream;
import java.io.IOException;

public class Request {

  private InputStream input;
  private String uri;

  public Request(InputStream input) {
    this.input = input;
  }

  public void parse() {
    // Read a set of characters from the socket
    StringBuffer request = new StringBuffer(2048);
    int i;
    byte[] buffer = new byte[10240];
    try {
      i = input.read(buffer); // 一次性读出所有HTTP请求信息（请求行、请求头、消息体）
    }
    catch (IOException e) {
      e.printStackTrace();
      i = -1;
    }
    for (int j=0; j<i; j++) {
      request.append((char) buffer[j]);
    }
    System.out.print(request.toString());
    uri = parseUri(request.toString());
  }

  /**
   * 解析出URI
   *
   * @param requestString
   * @return
   */
  private String parseUri(String requestString) {
    int index1, index2;
    index1 = requestString.indexOf(' '); // HTTP请求行的第1个空格位置
    if (index1 != -1) {
      index2 = requestString.indexOf(' ', index1 + 1); // HTTP请求行的第2个空格位置
      if (index2 > index1)
        return requestString.substring(index1 + 1, index2); // 截取URI
    }
    return null;
  }

  public String getUri() {
    return uri;
  }

}