package gotozero.UI;

import gotozero.cam;
import gotozero.sprite;

import java.awt.*;

public class InterfazUsuario {

    private static final Image dialogAvatarBox = new sprite("img/locutorImageBox.png").getImg();
    private static final Image dialogBox = new sprite("img/dialogBox.png").getImg();

    public static void drawInterfazSuperv(Graphics g, int width, int height, cam Camara){
        g.setColor(Color.red);
        g.fillRect (10, 10,Camara.getMageLife()*((width >> 1) - 10)/100, 15);

        g.setColor(Color.BLACK);
        g.drawString("" + Camara.getMago().getLife(), 50, 100);

        /*g.setColor(Color.BLACK);
        g.drawString("" + Integer.toBinaryString(teclado), 50, 150);
        */

        g.setColor(Color.BLACK);
        g.drawString("Dir Player: " + Camara.getMago().getDir(), 50, 200);


        g.setColor(Color.cyan);
        g.fillRect (width - 48, height - 32 - Camara.getMageSed()*64/100, 32, Camara.getMageSed()*64/100);

        g.drawImage(sprite.BotellaSed.getImg(),width - 48-4, height - 32 +4 - 80,null);

        for (byte i = 0; i < 10; i++){
            g.setColor(Color.darkGray);
            g.fillRect (width - 72 - 180 + i*20,height - 32 - 4 - 16, 16, 16);

            if(i*10 < Camara.getMageHambre()){
                g.setColor(Color.orange);
                g.fillRect (width - 72 - 180 + i*20 +1 ,height - 32 - 4 - 15, 14, 14);
                g.drawImage(sprite.ConPollo.getImg(), width - 72 - 180 + i*20, height - 32 - 4 - 16, null);
            } else {
                g.drawImage(sprite.SinPollo.getImg(),width - 72 - 180 + i*20, height - 32 - 4 - 16, null);
            }
        }
    }


    public static void Conversacion(Graphics g, int width, int height, cam Camara) {
        g.setColor(Color.WHITE);

        g.drawImage(Camara.getImagLocutor(),10 + (dialogAvatarBox.getWidth(null) - Camara.getImagLocutor().getWidth(null)) / 2, height - 10 - (dialogAvatarBox.getHeight(null) + Camara.getImagLocutor().getHeight(null)) / 2, null);
        g.drawImage(dialogAvatarBox,10, height - 10 - dialogAvatarBox.getHeight(null), null);

        g.drawImage(dialogBox,20 + dialogAvatarBox.getWidth(null), height - 10 - dialogBox.getHeight(null), null);

        g.drawString(Camara.getLocutorName() + ":", 30 + dialogAvatarBox.getWidth(null), height - dialogBox.getHeight(null) + 10);
        g.drawString(Camara.getDialog(), 30 + dialogAvatarBox.getWidth(null), height - dialogBox.getHeight(null) + 25);
    }






}
