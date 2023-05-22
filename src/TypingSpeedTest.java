import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.Duration;
import java.time.Instant;


public class TypingSpeedTest extends JFrame implements KeyListener {
    private JLabel sentenceLabel;
    private JLabel speedLabel;
    private String sentence = "воспользуйтесь моим тестом чтобы проверить скорость печати на клавиатуре";
    private int currentCharIndex = 0;
    private Instant startTime;

    public TypingSpeedTest() {
        setTitle("Typing Speed Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10,10,1090,150);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        sentenceLabel = new JLabel(sentence);
        sentenceLabel.setFont(new Font("Arial",Font.PLAIN, 20));
        panel.add(sentenceLabel);
        speedLabel = new JLabel("");
        speedLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(speedLabel);
        add(panel);

        addKeyListener(this);
        setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (currentCharIndex == 0) {
            startTime = Instant.now();
        }

        if (e.getKeyChar() == sentence.charAt(currentCharIndex)) {
            sentenceLabel.setText(getColoredSentence(currentCharIndex + 1));
            currentCharIndex++;
        } else {
            sentenceLabel.setForeground(Color.RED);

        }

        if (currentCharIndex == sentence.length()) {
            removeKeyListener(this);
            Instant endTime = Instant.now();
            long durationMillis = Duration.between(startTime, endTime).toMillis();
            double cpm = (double) sentence.length() / durationMillis * 60000;
            speedLabel.setText(String.format("Твоя скорость печати: %.0f символов в минуту", cpm));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    private String getColoredSentence(int endIndex) {
        String coloredSentence = "<html><font color='green'>";
        coloredSentence += sentence.substring(0, endIndex);
        coloredSentence += "</font>";
        coloredSentence += sentence.substring(endIndex);
        return coloredSentence;
    }

    public static void main(String[] args) {
        new TypingSpeedTest();
    }
}