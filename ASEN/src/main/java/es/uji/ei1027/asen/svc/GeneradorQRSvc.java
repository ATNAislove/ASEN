package es.uji.ei1027.asen.svc;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class GeneradorQRSvc implements GeneradorQRService{

    public String crearQR(String datos, int ancho, int altura) throws IOException, WriterException {
        BitMatrix matrix;
        Writer escritor = new QRCodeWriter();
        matrix = escritor.encode(datos, BarcodeFormat.QR_CODE, ancho, altura);

        BufferedImage imagen = new BufferedImage(ancho, altura, BufferedImage.TYPE_INT_RGB);

        for(int y = 0; y < altura; y++) {
            for(int x = 0; x < ancho; x++) {
                int grayValue = (matrix.get(x, y) ? 0 : 1) & 0xff;
                imagen.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
            }
        }

        return convertirQR(imagen);
    }
    public String convertirQR(BufferedImage bufferedImage) throws IOException {
        ImageIO.write(bufferedImage,"png",new File("tmpImage.png"));
        byte[] imageBytes = Files.readAllBytes(Paths.get("tmpImage.png"));
        Base64.Encoder encoder = Base64.getEncoder();

        String encoding = "data:image/png;base64," + encoder.encodeToString(imageBytes);
        return encoding;
    }
}
