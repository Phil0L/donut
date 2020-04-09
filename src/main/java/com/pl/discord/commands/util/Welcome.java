package com.pl.discord.commands.util;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.pl.discord.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import javax.imageio.ImageIO;
import javax.security.auth.login.CredentialException;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Welcome extends Command {

    public Welcome() {
        super.name = "welcome";
        super.aliases = new String[]{};
        super.category = new Category("Utilities");
        super.arguments = "[text]";
        super.help = "%welcome [text] : creates the welcome image";
    }

    @Override
    protected void execute(CommandEvent event) {
        Main.log(event, "Welcome");

        BufferedImage name = createNameImage(event.getArgs());
        name = rotate(name);
        String filepath = "./data/" + event.getGuild().getId();
        BufferedImage back = getBackImage();
        BufferedImage combined = combineImages(back, name);
        saveImage(combined, filepath);
        sendFile(filepath + "/welcome.png", event, event.getArgs());
        deleteFile(filepath + "/welcome.png");
    }

    public static void test() {
        BufferedImage name = createNameImage("Testing..");
        name = rotate(name);
        String filepath = "./data/media";
        BufferedImage back = loadImage("./data/media/Banner.png");

        BufferedImage combined = combineImages(back, name);
        saveImage(combined, filepath);
    }

    public static void enter(CommandEvent event, String text, Member member) {
        BufferedImage name = createNameImage(text);
        name = rotate(name);
        BufferedImage pb = getProfileImage(member);
        String filepath = "./data/" + event.getGuild().getId();
        BufferedImage combined = null;
        if (pb == null){
            BufferedImage back = getBackImage();
            combined = combineImages(back, name);
        }else{
            pb = resize(pb);
            BufferedImage back = getBackImage();
            combined = combineImages(back, name, pb);
        }
        saveImage(combined, filepath);
        sendFile(filepath + "/welcome.png", event, text);
        deleteFile(filepath + "/welcome.png");
    }

    private static BufferedImage rotate(BufferedImage image) {
        double rotationRequired = Math.toRadians(5);
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image, null);
    }

    private static BufferedImage resize(BufferedImage image){
        int scaledWidth = 500;
        int scaledHeight = scaledWidth;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledBI.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    private static BufferedImage getProfileImage(Member member) {
        String url = member.getUser().getAvatarUrl();
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BufferedImage getBackImage() {
        String url = "https://cdn.discordapp.com/attachments/404212693869002752/686987548849078282/Banner-v.2.0.png";
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BufferedImage createNameImage(String name) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("AcmeFont", Font.PLAIN, resizeFontSize(name));
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        g2d.dispose();

        img = new BufferedImage(fm.stringWidth(name), fm.getHeight(), BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        g2d.setColor(new Color(107, 72, 52));
        g2d.drawString(name, 0, fm.getAscent());
        g2d.dispose();
        return img;
    }

    private static BufferedImage combineImages(BufferedImage img1, BufferedImage img2) {

        int w = img1.getWidth();
        int h = img1.getHeight();
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics g = combined.getGraphics();
        g.drawImage(img1, 0, 0, null);
        g.drawImage(img2, 2180 * 2 - img2.getWidth() / 2, 340 * 2 - img2.getHeight() / 2, null);

        return combined;
    }

    private static BufferedImage combineImages(BufferedImage img1, BufferedImage img2, BufferedImage img3) {

        int w = img1.getWidth();
        int h = img1.getHeight();

        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics g = combined.getGraphics();
        g.drawImage(img1, 0, 0, null);
        g.drawImage(img2, 2180 * 2 - img2.getWidth() / 2, 340 * 2 - img2.getHeight() / 2, null);
        g.drawImage(img3, 1600 * 2 - img3.getWidth() / 2, 315 * 2 - img3.getHeight() / 2, null);

        return combined;
    }

    private static void saveImage(BufferedImage image, String filepath) {
        try {
            ImageIO.write(image, "PNG", new File(filepath + "/welcome.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage loadImage(String filepath) {
        try {
            BufferedImage img = ImageIO.read(new File(filepath));
            return img;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void sendFile(String filepath, CommandEvent event, String name) {
        event.getTextChannel().sendMessage("Welcome " + name).addFile(new File(filepath)).queue();
    }

    private static void deleteFile(String filepath) {
        File file = new File(filepath);
        file.delete();
    }

    private static int resizeFontSize(String text){
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("AcmeFont", Font.PLAIN, 200);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        g2d.dispose();
        if (fm.stringWidth(text) < 800){
            return 200;
        }else {
            while (fm.stringWidth(text) > 800){
                int size = font.getSize();
                font = new Font("AcmeFont", Font.PLAIN, size - 10);
                g2d.setFont(font);
                fm = g2d.getFontMetrics();
                g2d.dispose();
            }
            return font.getSize();
        }

    }

    public static void getFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] listOfFonts = ge.getAvailableFontFamilyNames();
        System.out.println(Arrays.toString(listOfFonts));
    }
}
