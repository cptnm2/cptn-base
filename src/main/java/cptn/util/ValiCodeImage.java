package cptn.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author WangYZ
 * 
 */
public class ValiCodeImage implements Serializable {

	public static final int DISTRACT_COUNT = 80; // Distract line count

	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(ValiCodeImage.class);
	
	private BufferedImage theImage;
	
	private int width;
	private int height; // Image height

	private int fixedTop = 4;
	private int padding = 3; // left and right padding
	private int sep = 3; // sep width between chars

	private int ascend;
	private int baseline;

	private Color background = new Color(0xEEEEEE); // 浅灰
	private Color foreground = new Color(0x000000); // 黑色

	private String fontName = "sans-serif";
	private Font textFont;
	
	private String code;

	public ValiCodeImage(String code, int fontSize) {
		this.code = code;
		
		this.textFont = new Font(fontName, Font.BOLD, fontSize);
		
		calcCanvasMetrics();
	}

	private BufferedImage create() {
		Random random = new Random();

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		
		try {
			g.setFont(textFont);

			// 填充背景
			g.setColor(background);
			g.fillRect(0, 0, width, height);
			
			// 干扰线
			for (int i = 0; i < DISTRACT_COUNT; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				
				g.setColor(getRandColor(0, 255));
				g.drawLine(x, y, x + xl, y + yl);
			}
	
			// 校验码
			char ch;
			int cw; // Char width
			int pos = padding; // String start position X
			
			for (int i = 0; i < code.length(); i++) {
				ch = code.charAt(i);
				cw = g.getFontMetrics().charWidth(ch);
				
				g.setColor(getRandColor(1, 100));
				g.drawString(String.valueOf(ch), pos, baseline);
				
				pos += cw + sep;
			}

			// 边框
			g.setColor(foreground);
			g.drawRect(0, 0, width - 1, height - 1);

			image.flush();
		}
		catch (Exception ex) {
			log.warn("", ex);
		}
		finally {
			g.dispose();
		}
		
		return image;
	}
	
	private void calcCanvasMetrics() {
		BufferedImage test = new BufferedImage(100, 36, BufferedImage.TYPE_INT_RGB);
		Graphics g = test.getGraphics();
		FontMetrics fm = g.getFontMetrics(textFont);
		
		int sum = 0;
		char c;
		int cw; // char width
		
		for (int i = 0; i < code.length(); i++) {
			c = code.charAt(i);
			cw = fm.charWidth(c);
			
			if (i > 0) {
				sum += sep;
			}
			
			sum += cw;
		}
		
		width = sum + padding * 2;
		height = fm.getAscent() + fm.getDescent() + padding * 2 - fixedTop;
		ascend = fm.getAscent();
		baseline = ascend + padding - fixedTop;
		
		g.dispose();
	}

	private Color getRandColor(int min, int max) {
		Random random = new Random();

		if (min < 0) {
			min = 0;
		}
		if (min > 255) {
			min = 255;
		}

		if (max < 0) {
			max = 0;
		}
		if (max > 255) {
			max = 255;
		}
		
		int r = min + random.nextInt(max - min);
		int g = min + random.nextInt(max - min);
		int b = min + random.nextInt(max - min);

		return new Color(r, g, b);
	}

	public BufferedImage getImage() {
		if (theImage == null) {
			theImage = create();
		}
		
		return theImage;
	}
}
