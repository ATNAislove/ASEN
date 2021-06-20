package es.uji.ei1027.asen.svc;

import com.google.zxing.WriterException;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface GeneradorQRService {
    public String crearQR(String datos, int ancho, int altura) throws IOException, WriterException;
    public String convertirQR(BufferedImage bufferedImage) throws IOException;
}
